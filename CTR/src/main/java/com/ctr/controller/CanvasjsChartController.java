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
		int ios = canvasjsChartService.ios();
		int android = canvasjsChartService.android();
		int unkwon = canvasjsChartService.unkwon();
		
		int mk_apple = canvasjsChartService.apple();
		int mk_samsung = canvasjsChartService.samsung();
		int mk_lg = canvasjsChartService.lg();
		int mk_unknown = canvasjsChartService.unknown();
		
		int ck02 = canvasjsChartService.ck02();
		int ck04 = canvasjsChartService.ck04();
		int ck06 = canvasjsChartService.ck06();
		int ck08 = canvasjsChartService.ck08();
		int ck10 = canvasjsChartService.ck10();
		int ck12 = canvasjsChartService.ck12();
		int ck14 = canvasjsChartService.ck14();
		int ck16 = canvasjsChartService.ck16();
		int ck18 = canvasjsChartService.ck18();
		int ck20 = canvasjsChartService.ck20();
		int ck22 = canvasjsChartService.ck22();
		int ck24 = canvasjsChartService.ck24();

		modelMap.addAttribute("total", total);
		modelMap.addAttribute("ios", ios);
		modelMap.addAttribute("android", android);
		modelMap.addAttribute("unkwon", unkwon);
		
		modelMap.addAttribute("mk_apple", mk_apple );
		modelMap.addAttribute("mk_samsung", mk_samsung );
		modelMap.addAttribute("mk_lg", mk_lg );
		modelMap.addAttribute("mk_unknown", mk_unknown);
		
		modelMap.addAttribute("ck02",ck02);
		modelMap.addAttribute("ck04",ck04);
		modelMap.addAttribute("ck06",ck06);
		modelMap.addAttribute("ck08",ck08);
		modelMap.addAttribute("ck10",ck10);
		modelMap.addAttribute("ck12",ck12);
		modelMap.addAttribute("ck14",ck14);
		modelMap.addAttribute("ck16",ck16);
		modelMap.addAttribute("ck18",ck18);
		modelMap.addAttribute("ck20",ck20);
		modelMap.addAttribute("ck22",ck22);
		modelMap.addAttribute("ck24",ck24);
		
		return "chart";
	}
}