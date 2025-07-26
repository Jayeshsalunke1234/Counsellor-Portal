package com.cousellors.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cousellors.portal.dto.DashboardDto;
import com.cousellors.portal.dto.EnquiryDto;
import com.cousellors.portal.entities.Enquiry;
import java.util.List;
import com.cousellors.portal.entities.Counsellors;


public interface EnquiryRepo extends JpaRepository<Enquiry, Integer>{
	
	
	
	public Enquiry findByCounsellorCounsellorId(Integer counsellorId);
	

		

}
