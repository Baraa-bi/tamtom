package com.example.Controllers;

import com.example.Models.Comments;
import com.example.Models.User;
import com.example.Models.UserPosts;
import com.example.Repositories.MongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by baraa on 12/11/2016.
 */


@Controller
@SessionAttributes("user")
public class LoginController {

    private MongoRepo mongoRepo;
    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoRepo(MongoRepo mongoRepo) {
        this.mongoRepo = mongoRepo;
    }
    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping("/login")
    public String login(User user, Model model, @CookieValue(value = "user1", required = false) String userName) {
        if (userName != null){
            user = mongoRepo.findOne(userName);
            model.addAttribute("photo", "http://localhost:9090/getphoto/" + user.getUserName());
            model.addAttribute("user", user);
            return "home";
        }
            return "login";
    }

    @RequestMapping("/login2")
    public String login2( User user, Model model, HttpServletResponse response, @RequestParam(value = "boolean", required = false) String s) {
        if (s != null) {
            response.addCookie(new Cookie("user1", user.getUserName()));
        }
        User user1 = mongoRepo.findOne(user.getUserName());
        if (user1 == null || !user1.getPassword().equals(user.getPassword())) {
            return "redirect:login";
        } else {
            user = mongoRepo.findOne(user.getUserName());


           long count= mongoTemplate.count(new Query(Criteria.where("userName").is(user.getUserName())),
                    UserPosts.class);

            model.addAttribute("postsNo",count);
            model.addAttribute("photo", "http://localhost:9090/getphoto/" + user.getUserName());
            model.addAttribute("user", user);
            return "redirect:home";
        }
    }

    @RequestMapping("/logout")
    public String logout(Model model, @CookieValue(value = "user1", required = false) String userName, HttpServletResponse response
            , SessionStatus status, WebRequest request) {
        Cookie cookie = new Cookie("user1", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        model.addAttribute("photo", "");
        status.setComplete();
        request.removeAttribute("user", WebRequest.SCOPE_SESSION);
        return "redirect:home";
    }

    @RequestMapping("/oo")
    @ResponseBody
    public List<UserPosts> getPosts(@ModelAttribute("posts")List<UserPosts> list)
    {
        return list;
    }
}
