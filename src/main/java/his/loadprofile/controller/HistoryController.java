package his.loadprofile.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HistoryController {
	
	@RequestMapping("/sim-history")
	public String index(Map<String, Object> model) 
	{
		
		return "history";
	}

}
