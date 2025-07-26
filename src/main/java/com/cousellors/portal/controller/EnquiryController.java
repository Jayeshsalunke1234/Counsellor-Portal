package com.cousellors.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cousellors.portal.dto.EnquiryDto;
import com.cousellors.portal.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
   private EnquiryService enquiryService;
	
	@GetMapping("/enquiry")
	public String showAddEnqs(Model model) {
		
		EnquiryDto enquiryDto =new EnquiryDto();
		model.addAttribute("enqs" , enquiryDto);
		
		return "addEnqs";
	}
	
	
	@PostMapping("/enquiry")
	public String handleAddEnqus(@ModelAttribute("enqs") EnquiryDto enqs,HttpServletRequest request ,Model model) {
		HttpSession session = request.getSession(false);
		Integer cId = (Integer) session.getAttribute("counsellorId");
		
	boolean saveEnqs = enquiryService.upsertEnquiry(enqs, cId);
	  
	    if(saveEnqs) {
	    	model.addAttribute("smsg","Enquiry Added Successfully..");
	    }else {
			model.addAttribute("emsg","Enquiry not added..");
		}
	
		return "addEnqs";
	}
	
	@GetMapping("/enquiries")
	public String showEnqs(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
	Integer cId =(Integer) session.getAttribute("counsellorId");
	  List<EnquiryDto> enqs = enquiryService.getAllEnquiry(cId);
	  model.addAttribute("enquiries",enqs);
	  
	  EnquiryDto filterDto =new EnquiryDto();
	  model.addAttribute("filterDto",filterDto);
		return "viewEnqs";
	}
	
	
	@PostMapping("/filter-enqs")
	public String handlesearchForm( @ModelAttribute("filterDto") EnquiryDto filterDto, Model model,HttpServletRequest request) {
		 HttpSession session = request.getSession(false);
		 Integer cId =(Integer) session.getAttribute("counsellorId");
		
		List<EnquiryDto> enqList = enquiryService.filterEnquiry(filterDto, cId);
	
		
		model.addAttribute("enquiries",enqList);
		
		return "viewEnqs";
	}  
	
	
	@GetMapping("/edit-enquiry")
	public String showEdit(@RequestParam("enqId") Integer enqId ,Model model, HttpServletRequest request) {
       
		
		  EnquiryDto enquiry = enquiryService.getEnquiry(enqId);
		  model.addAttribute("enqs", enquiry);
		  
		  
		  
		return "addEnqs";
		
	}
	

}
