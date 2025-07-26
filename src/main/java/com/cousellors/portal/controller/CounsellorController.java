package com.cousellors.portal.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cousellors.portal.dto.CounsellorsDto;
import com.cousellors.portal.dto.DashboardDto;

import com.cousellors.portal.service.CounsellorsService;
import com.cousellors.portal.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	@Autowired
	private CounsellorsService counsellorsService;
	
	@Autowired
	private EnquiryService enquiryService;
	
	@GetMapping("/")
	public String indexPage(Model model) {
		
		CounsellorsDto counsellorsDto=new CounsellorsDto();
		model.addAttribute("cousellor", counsellorsDto);
		return "index";
	}
	
	
	@PostMapping("/login")
	public String handleLogin(CounsellorsDto counsellorsDto,Model model ,HttpServletRequest request) {
		CounsellorsDto dto = counsellorsService.login(counsellorsDto.getEmail(), counsellorsDto.getPassword());
		if(dto!=null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", dto.getCounsellorId());
			
			return "redirect:dashboard";
			
		}else {
			model.addAttribute("emsg","Invalid Credential");
			CounsellorsDto counsellor=new CounsellorsDto();
			model.addAttribute("cousellor", counsellor);
			return "index";
		}			
	
	}
	
	@GetMapping("/dashboard")
	public String buildDashboard(Model model,HttpServletRequest request) {
		              HttpSession session = request.getSession(false);  
		              int cId =(Integer) session.getAttribute("counsellorId");
		              DashboardDto dash = enquiryService.getDashboardInfo(cId);
		              
		              
		              model.addAttribute("dashboardInfo",dash);
		              
		
		return "dashboard";
	}
	
	@GetMapping("/logout")
	public String handlelogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate();
		
		return "redirect:/";
	}
	
	
	
	@GetMapping("/register")
	public String register(Model model) {
		CounsellorsDto counsellorsDto=new CounsellorsDto();
		model.addAttribute("counsellorDto",counsellorsDto);
	
		return "registerPage";
	}
	
	@PostMapping("/register")
	public String handelRegistration(@ModelAttribute("counsellorDto") CounsellorsDto counsellorsDto,Model model) {
		boolean status = counsellorsService.isEmailUnique(counsellorsDto.getEmail());
		
		 if(status) {
			 boolean register = counsellorsService.register(counsellorsDto);
			 if (register) {
				 
				 model.addAttribute("smsg","Registration Sucessfull");	
				 
			}else {
				
				 model.addAttribute("emsg","Registration Failed");
			}
	 }
		 else {
			 
			model.addAttribute("emsg", "Email is duplicate");
		}
		 
		return "registerPage";
		}

}
