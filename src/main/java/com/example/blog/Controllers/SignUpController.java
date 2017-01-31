package com.example.Controllers;

import com.example.Models.User;
import com.example.Repositories.MongoRepo;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by baraa on 12/11/2016.
 */

@Controller
@SessionAttributes("user")
public class SignUpController {


    private MongoRepo mongoRepo;
    private GridFsTemplate gridFsTemplate;

    @Autowired
    public void setMongoRepo(MongoRepo mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    @Autowired
    public void setGridFsTemplate(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    @RequestMapping("/signUp")
    public String signUp(@ModelAttribute User user, Model model)
    {

            return "signUp";
    }


    @RequestMapping("/signUp2")

    public String signUp2(@ModelAttribute User user, BindingResult result, @RequestParam(value = "file",required = false)MultipartFile file,
                            Model model)
    {
        if(mongoRepo.findOne(user.getUserName())!=null ||user.getUserName().isEmpty())
        {
            model.addAttribute("user",user);
            return "/signup";
        }
        else {
            if(file!=null) {
                InputStream inputStream = null;
                try {

                    inputStream = new ByteArrayInputStream(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gridFsTemplate.store(inputStream, user.getUserName(), "image/png");
            }
            mongoRepo.insert(user);
            model.addAttribute("photo" ,"http://localhost:9090/getphoto/"+user.getUserName()) ;
            return "redirect:home";
        }
    }

        @RequestMapping("/getphoto/{userName}")
    public ResponseEntity getphoto(@PathVariable("userName") String userName) {
       if (userName.equals("null"))
            return new ResponseEntity("There is no photo", new HttpHeaders(), HttpStatus.OK);
        else {
            GridFSDBFile result = gridFsTemplate.findOne(
                    new Query().addCriteria(Criteria.where("filename").is(userName)));
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





}
