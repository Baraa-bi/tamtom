package com.example.Controllers;

/**
 * Created by baraa on 12/11/2016.
 */

import com.example.Models.User;
import com.example.Repositories.PostsMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@ControllerAdvice
public class ControllerAdv {

    PostsMongoRepo mongoRepo;

    @Autowired
    public void setMongoRepo(PostsMongoRepo mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    @ModelAttribute
    public void defaultData(Model model)
    {
        model.addAttribute("posts",mongoRepo.findAll());
    }


      //  @ExceptionHandler(value = Exception.class)
        public String exception()
        {
            return "exception";
        }

}
