package com.ctr.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ctr.domain.csv_colVO;
import com.ctr.services.CanvasjsChartService;
 
@Controller
@RequestMapping("/chart")
public class CanvasjsChartController {
	 
	@Autowired
	private CanvasjsChartService canvasjsChartService;
 
	@RequestMapping(method = RequestMethod.GET)
	public String springMVC(ModelMap modelMap) throws Exception {
		List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData();
		modelMap.addAttribute("dataPointsList", canvasjsDataList);
		int total = canvasjsChartService.total();
		modelMap.addAttribute("total", total);
		return "chart";
	}
}