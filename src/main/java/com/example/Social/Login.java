package com.example.Social;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.example.Social.JpaRepository;
import org.springframework.web.client.RestTemplate;


@Controller
public class Login {
	
	@Autowired
	JpaRepository jpaRepo;

	@ModelAttribute
	public void defaultData(Model model)
	{
		model.addAttribute("users",jpaRepo.findAll());
		model.addAttribute("Title","Welcome To The Judge");
	}
	
	
	
	@InitBinder
	public void init (WebDataBinder binder)
	{
	binder.setDisallowedFields(new String[]{"phoneNo"});	
	}
	
	@RequestMapping("/judge")
	public String login(User user ,Locale locale , Model model)
	{
		model.addAttribute("locale",locale);
		return "home";
	}
	
	
	@RequestMapping("/visitUser")
	public String visit(@RequestParam(value="id") Long id , Model model)
	{
		model.addAttribute("user", jpaRepo.findOne(id));
		return "visitUser";
	}
	
	@RequestMapping("/signIn")
	public String login2( Model model , @ModelAttribute User user)
	{
		if(jpaRepo.findByUserNameAndPassword(user.getUserName(), user.getPassword())==null)
		{
			model.addAttribute("invalid","invalid userName or Password");
			return "home";
		}
		else
			{
			model.addAttribute("user",jpaRepo.findByUserName(user.getUserName()));
			return "user";
			} 
	}
@RequestMapping("/post")
	public String codeforces(@RequestParam(value = "handle" , defaultValue ="tourist") String handle,Model model , @ModelAttribute User user) throws JSONException
	{
		RestTemplate restTemplate = new RestTemplate();
		String text = restTemplate.getForObject("http://codeforces.com/api/user.status?handle=" + handle + "&from=1&count=100000", String.class);

		if(text==null||text.isEmpty())
			return "user";

		JSONObject object = new JSONObject(text);

			JSONArray array = object.getJSONArray("result");

			HashMap<String, String> map = new HashMap<>();
			for (int i = 0; i < array.length(); i++) {
				if (array.getJSONObject(i).getString("verdict").equals("OK")) {
					String s = array.getJSONObject(i).getJSONObject("problem").getString("index");
					String s1 = array.getJSONObject(i).getString("contestId");
					if(s1.length()>4) continue;
					String problemName = array.getJSONObject(i).getJSONObject("problem").getString("name");
					map.put("http://codeforces.com/problemset/problem/" + s1 + "/" + s, problemName);
				}

			}

			String text1 = restTemplate.getForObject("http://codeforces.com/api/user.info?handles=" + handle, String.class);
			JSONObject object1 = new JSONObject(text1).getJSONArray("result").getJSONObject(0);
			model.addAttribute("firstName", object1.isNull("firstName") ? "" : object1.getString("firstName"));
			model.addAttribute("lastName", object1.isNull("lastName") ? "" : object1.getString("lastName"));
			model.addAttribute("handle", object1.isNull("handle") ? "" : object1.getString("handle"));
			model.addAttribute("titlePhoto", object1.getString("titlePhoto"));
			model.addAttribute("organization", object1.isNull("organization") ? "" : object1.getString("organization"));
			model.addAttribute("rank", object1.isNull("rank") ? "" : object1.getString("rank"));
			model.addAttribute("rating", object1.isNull("rating") ? "" : object1.getString("rating"));
			model.addAttribute("problemsSolved", map.size());


			model.addAttribute("problems", map);

			return "user";
	}



		
	
	
}
