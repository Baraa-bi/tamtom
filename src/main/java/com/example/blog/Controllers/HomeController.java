package com.example.Controllers;

import com.example.Models.User;
import com.example.Models.UserPosts;
import com.example.Repositories.MongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by baraa on 12/11/2016.
 */

@Controller
@SessionAttributes("user")
public class HomeController {

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

    @RequestMapping("/home")
    public String home(User user , Model model,@CookieValue(value = "user1", required = false) String userName)
    {
        if (userName != null) {
            user = mongoRepo.findOne(userName);


            long count= mongoTemplate.count(new Query(Criteria.where("userName").is(user.getUserName())),
                    UserPosts.class);

            model.addAttribute("postsNo",count);
            model.addAttribute("photo", "http://localhost:9090/getphoto/" + user.getUserName());
            model.addAttribute("user", user);
            return "/home";
        }
        if(user.getUserName()!=null)
        {

            long count= mongoTemplate.count(new Query(Criteria.where("userName").is(user.getUserName())),
                    UserPosts.class);

            model.addAttribute("postsNo",count);
            model.addAttribute("photo", "http://localhost:9090/getphoto/" + user.getUserName());
            model.addAttribute("user", user);
            return "/home";
        }
        return "/home";
    }

}
