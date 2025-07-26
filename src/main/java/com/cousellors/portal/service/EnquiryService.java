package com.cousellors.portal.service;

import java.util.List;

import com.cousellors.portal.dto.DashboardDto;
import com.cousellors.portal.dto.EnquiryDto;

public interface EnquiryService {
	
	public DashboardDto getDashboardInfo(Integer counsellerId);
	
	public boolean upsertEnquiry(EnquiryDto enquiryDto,Integer counsellorId);
	
	public List<EnquiryDto> getAllEnquiry(Integer counsellorsId);
	
	public List<EnquiryDto> filterEnquiry(EnquiryDto enquiryDto,Integer cousellorsId);
	
	public EnquiryDto getEnquiry(Integer enqId);
	
	

}
