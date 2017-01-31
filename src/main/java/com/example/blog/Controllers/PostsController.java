package com.example.Controllers;

import com.example.Models.Comments;
import com.example.Models.User;
import com.example.Models.UserPosts;
import com.example.Repositories.PostsMongoRepo;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * Created by baraa on 12/11/2016.
 */

@Controller
@SessionAttributes({"user","post"})
public class PostsController {


    private PostsMongoRepo mongoRepo;
    private GridFsTemplate gridFsTemplate;
    private MongoTemplate mongoTemplate;

    @Autowired
    public void setGridFsTemplate(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    @Autowired
    public void setMongoRepo(PostsMongoRepo mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping("/post")
    public String post(@ModelAttribute UserPosts userPosts ,@ModelAttribute User user, Model model)
    {
        if(user.getUserName()==null||user.getUserName().isEmpty())
        {
            return "redirect:login";
        }
        else
        return "/post";
    }


    @RequestMapping("/post2")
    public String post2(@ModelAttribute UserPosts userPosts , @ModelAttribute User user , @RequestParam(required = false,value = "file")MultipartFile file, Model model) throws UnsupportedEncodingException {
        if(userPosts.getPostTitle().isEmpty())
            return "redirect:post";

        userPosts.setUserName(user.getUserName());
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        gridFsTemplate.store(inputStream,userPosts.getPostTitle(),"image/png");
        mongoRepo.insert(userPosts);

        return "redirect:/showPost/"+userPosts.getPostTitle();
    }



    @RequestMapping("/getPostPhoto/{postTitle}")
    public ResponseEntity getphoto(@PathVariable("postTitle") String postTitle) {
        if (postTitle.equals("null"))
            return new ResponseEntity("There is no photo", new HttpHeaders(), HttpStatus.OK);
        else {
            GridFSDBFile result = gridFsTemplate.findOne(
                    new Query().addCriteria(where("filename").is(postTitle)));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                result.writeTo(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = stream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
        }
    }

    @RequestMapping("showPost/{postTitle}")
    public String showPost(@PathVariable("postTitle") String postTitle, Model model) throws UnsupportedEncodingException {
        postTitle=new String(postTitle.getBytes(),"UTF-8");
        UserPosts post = mongoRepo.findOne(postTitle);
        model.addAttribute("post",post);
        return "postShow";
    }

    @RequestMapping("/addComment")
    public String addComment(Model model,@RequestParam("comment") String comment ,@SessionAttribute UserPosts post,@SessionAttribute User user) throws UnsupportedEncodingException {
        if(user.getUserName()==null)
            return "redirect:login";
        else {
            Comments comments = new Comments(user.getUserName(), comment);
            mongoTemplate.upsert(new Query(Criteria.where("_id").is(post.getPostTitle())),
                    new Update().push("comments", comments),
                    UserPosts.class);

            return "redirect:/showPost/"+ post.getPostTitle();
        }
    }

}
