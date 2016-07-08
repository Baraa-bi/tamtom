package com.example.Social;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Social.JpaRepository;



@Controller
public class SignUp {

	@Autowired
	JpaRepository jpaRepo;

	@ModelAttribute
	public void defaultData(Model model)
	{
		model.addAttribute("users",jpaRepo.findAll());
		model.addAttribute("Title","Welcome To The Judge");
	}
	
	
	
	@RequestMapping("/signUp")
	public String signUp( @Valid User user ,BindingResult result ,@RequestParam(value="password2") String password2 ,  Model model)
	{
		if(result.hasErrors())
		{
			model.addAttribute("user",user);
			return "home";
		}
		else
			{
			jpaRepo.save(user);
			return "user";
			}
	}
	
	

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		public static boolean validate(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		        return matcher.find();
		}
}
