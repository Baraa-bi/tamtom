
package com.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class Cont {
	
	@Autowired
	MongoRepo mongo;
	
	@RequestMapping("/login")
	public String login()
	{
		return "login";
	}
	
	@RequestMapping ("/login2")
	public String login2(@ModelAttribute User user, Model model)
	{
		mongo.save(user);
		model.addAttribute("user",mongo.findAll());	
		return "page1";
	}
		
	@ModelAttribute
	public void magic(Model model)
	{
		model.addAttribute("message","Welcome to Our Registration System");
	}
}



// MongoDB Repository 


interface MongoRepo extends MongoRepository <User, String> {
		
			User findByFirstName (String name);
}




@RestController
class contt {
	
	@Autowired
	MongoRepo mongo ;
	
	@RequestMapping("/get/{id}")
	public User getUser(@PathVariable("id") String id )
	{
	
		return mongo.findOne(id);
	}
	
	
	@RequestMapping("/save")
	public User saveUser(@RequestParam("firstName") String firstName , @RequestParam("lastName") String lastName )
	{
			mongo.save(new User(firstName,lastName));
			return mongo.findByFirstName(firstName);
	}
	
	
	@RequestMapping(value="/getAll")
	public Iterable<User> getAllUsers()
	{
		return mongo.findAll();
	}
	
	
}


