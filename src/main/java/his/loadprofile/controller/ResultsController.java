package his.loadprofile.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResultsController {
	
	@RequestMapping("/sim-results")
	public String index(Map<String, Object> model) 
	{
		
		return "results";
	}

}
