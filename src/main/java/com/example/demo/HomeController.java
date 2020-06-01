package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@Autowired
	Coronavirusdata c;
	
	@GetMapping("/")
	public String home(Model model)
	{
		List<Locationstats> allstats=c.getAllstats();
		
		int totalcases=allstats.stream().mapToInt(stat->stat.getLatesttotal()).sum();
		model.addAttribute("totalReportedCases",totalcases);
		
		model.addAttribute("locationstats",allstats);
		//locationstats goes in html 
		//another variable is used there to loop through
		return "home.html";
	}

}
