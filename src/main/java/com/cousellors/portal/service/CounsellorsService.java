package com.cousellors.portal.service;



import com.cousellors.portal.dto.CounsellorsDto;


public interface CounsellorsService {
	
	public CounsellorsDto login(String email,String password);
	public boolean isEmailUnique(String email);
	public boolean register(CounsellorsDto counsellorsDto);
	

}
