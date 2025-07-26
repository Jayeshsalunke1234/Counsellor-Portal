package com.cousellors.portal.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.cousellors.portal.dto.DashboardDto;
import com.cousellors.portal.dto.EnquiryDto;
import com.cousellors.portal.entities.Counsellors;
import com.cousellors.portal.entities.Enquiry;
import com.cousellors.portal.repo.CousellorsRepo;
import com.cousellors.portal.repo.EnquiryRepo;
import com.cousellors.portal.service.EnquiryService;

@Service
public class EnquiryServiceImple implements EnquiryService{
	
	@Autowired
	private EnquiryRepo enquiryRepo;
	
	@Autowired
	private CousellorsRepo cousellorsRepo;

	@Override
	public DashboardDto getDashboardInfo(Integer counsellerId) {
	   // Enquiry entity = enquiryRepo.findByCounsellorCounsellorId(counsellerId);
		Counsellors centity=new Counsellors();
		centity.setCounsellorId(counsellerId);
		Enquiry enq=new Enquiry();
		enq.setCounsellor(centity);
	     List<Enquiry> enqList = enquiryRepo.findAll(Example.of(enq));
	     int total = enqList.size();
	     int open = enqList.stream()
	    		    .filter(e -> "Open".equalsIgnoreCase(e.getEnqstatus()))
	    		    .collect(Collectors.toList()).size();

	    		int enrolled = enqList.stream()
	    		    .filter(e -> "Enrolled".equalsIgnoreCase(e.getEnqstatus()))
	    		    .collect(Collectors.toList()).size();

	    		int lost = enqList.stream()
	    		    .filter(e -> "Lost".equalsIgnoreCase(e.getEnqstatus()))
	    		    .collect(Collectors.toList()).size();

//	    int total = enqList.size();
//	    int open = enqList.stream().filter(e->e.getEnqstatus().equals("Open")).collect(Collectors.toList()).size();
//	   int enrolled = enqList.stream().filter(e->e.getEnqstatus().equals("Enrolled")).collect(Collectors.toList()).size();
//	    int lost = enqList.stream().filter(e->e.getEnqstatus().equals("Lost")).collect(Collectors.toList()).size();
//	              
	              return DashboardDto.builder()
	                           .totalEnqs(total)
	                           .openEnqs(open)
	                           .enrolledEnqs(enrolled)
	                           .lostEnqs(lost)
	                           .build();
	
		
	}

	@Override
	public boolean upsertEnquiry(EnquiryDto enquiryDto, Integer counsellorId) {
		Enquiry entity =new Enquiry();
		BeanUtils.copyProperties(enquiryDto, entity);
	    Counsellors councellor = cousellorsRepo.findById(counsellorId).orElseThrow();	
	    entity.setCounsellor(councellor);
	    Enquiry saveEnquiry = enquiryRepo.save(entity);
		
		return saveEnquiry.getEnquiryId() != null;
	}

	@Override
	public List<EnquiryDto> getAllEnquiry(Integer counsellorsId) {
		List<EnquiryDto> dtolist =new ArrayList<EnquiryDto>();
		
		Counsellors centity =new Counsellors();
		centity.setCounsellorId(counsellorsId);
		
		Enquiry entity =new Enquiry();
		entity.setCounsellor(centity);
		List<Enquiry> eList = enquiryRepo.findAll(Example.of(entity));
		
		eList.forEach(e->{
			EnquiryDto dto=new EnquiryDto();
			BeanUtils.copyProperties(e, dto);
			dtolist.add(dto);
		});
		
		return dtolist;
		     
	}

	@Override
	public List<EnquiryDto> filterEnquiry(EnquiryDto enquiryDto, Integer cousellorsId) {
		Enquiry entity=new Enquiry();
		
		if(enquiryDto.getClassMode() != null && !enquiryDto.getClassMode().equals("")){
			entity.setClassMode(enquiryDto.getClassMode());
		}
		if(enquiryDto.getCourseName() != null && !enquiryDto.getCourseName().equals("")) {
			entity.setCourseName(enquiryDto.getCourseName());
		}
		if(enquiryDto.getEnqstatus() != null && !enquiryDto.getEnqstatus().equals("")) {
			entity.setEnqstatus(enquiryDto.getEnqstatus());
		}
		
		Counsellors centity = cousellorsRepo.findById(cousellorsId).orElseThrow();
		
		entity.setCounsellor(centity);
		  List<Enquiry> elist = enquiryRepo.findAll(Example.of(entity));
		  
		  List<EnquiryDto> dtolist=new ArrayList<EnquiryDto>();
		  
		  elist.forEach(e ->{ 
		          EnquiryDto dto= new EnquiryDto();
		          BeanUtils.copyProperties(e, dto);
		          dtolist.add(dto);
		  });
		  		
		return dtolist;
	}

	@Override
	public EnquiryDto getEnquiry(Integer enqId) {
		    Enquiry entity = enquiryRepo.findById(enqId).orElseThrow();
	          
	           EnquiryDto dto=new EnquiryDto();
	           BeanUtils.copyProperties(entity, dto);
	           
		return dto;
	}

}
