package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by baraa on 2/23/2017.
 */
@Controller
public class Test {


    @RequestMapping("/ColoredRain")
    public String rain()
    {
        return "index";
    }

}
