package his.loadprofile.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import his.loadprofile.repo.SimConfigReopsitory;
import his.loadprofile.model.SimConfig;

@Controller
public class HistoryController {
	
	@Autowired
	private SimConfigReopsitory simConfigReopsitory;
	
	@RequestMapping("/sim-history")
	public String index(Map<String, Object> model) 
	{
		List<SimConfig> simConfigs = simConfigReopsitory.findAll();
		model.put("simConfigs", simConfigs);
		return "history";
	}

}
