package com.ctr.controller;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ctr.data.CanvasjsChartData;
import com.ctr.domain.csv_nameVO;
import com.ctr.services.CanvasjsChartService;
import com.ctr.util.AsyncConfig;
import com.ctr.util.fileInput;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@Autowired
	private CanvasjsChartService canvasjsChartService;

	@Resource(name="asyncTaskService")
	private CanvasjsChartData asyncTaskService;

	@Resource(name="asyncConfig")
	private AsyncConfig asyncConfig;

	@Inject
	CanvasjsChartService adminService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale local, Model model) throws Exception {
		logger.info("Welcome home! The client locale is {}.", local);
		List<csv_nameVO> list = null;
		list = adminService.list();
		model.addAttribute("list",list);
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("ableToRunThread",asyncConfig.checkSampleTaskExecute());
		return mav;
	}

	@RequestMapping(value = "/testing", method = RequestMethod.POST)
	public ModelAndView testing(ModelMap modelMap,Model model) {
		ModelAndView mav = new ModelAndView("jsonView");
		boolean Switch = false;
		Future<String> f = null;
		try {
			//Task 실행가능 여부 확
			if(asyncConfig.checkSampleTaskExecute()) {
				f = asyncTaskService.jobRunningInBackground();
			}else {
				System.out.println("Thread 개수 초과");
			}
		} catch (Exception e) {
			System.out.println("Thread Err :: " + e.getMessage());
		}
		
		try {
			while(!Switch) {
				if(f.get().equals("End")) {
					Switch = true;
					System.out.println(f);
					model.addAttribute("END");
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView csvupdate(Model model ,csv_nameVO vo ,ModelMap modelMap) throws Exception {
		logger.info("post csvfile");

		List<csv_nameVO> list = null;
		list = adminService.list();
		model.addAttribute("list",list);

		ModelAndView mav = new ModelAndView("home");
		try {
			//Task 실행가능 여부 확
			if(asyncConfig.checkSampleTaskExecute()) {
				asyncTaskService.readcsv();
//				int total = canvasjsChartService.total();
//				modelMap.addAttribute("total", total);
			}else {
				System.out.println("Thread 개수 초과");
			}
		} catch (Exception e) {
			System.out.println("Thread Err :: " + e.getMessage());
		}
		
		return mav;
	}
}


//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) throws Exception {
//		logger.info("Welcome home! The client locale is {}.", locale);
//
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//
//		model.addAttribute("serverTime", formattedDate );
//
//	List<csv_nameVO> list = null;
//	list = adminService.list();
//	model.addAttribute("list",list);
//
//		return "home";
//	}


//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public String csvupdate(Locale locale, Model model ,csv_colVO vo) throws Exception {
//
//		String[] csvfile = fileInput.readcsv();
//		for(int i=0;i<csvfile.length;i++) {
//			System.out.println(i + " " + csvfile[i] + " ");
//		}
//		System.out.println();
//
//		vo.setBid_time(csvfile[1]);
//
//		adminService.register(vo);
//		
//		return "home";
//
//	}

