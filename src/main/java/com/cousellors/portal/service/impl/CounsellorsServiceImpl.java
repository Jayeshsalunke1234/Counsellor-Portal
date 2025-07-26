package com.cousellors.portal.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cousellors.portal.dto.CounsellorsDto;
import com.cousellors.portal.entities.Counsellors;
import com.cousellors.portal.repo.CousellorsRepo;
import com.cousellors.portal.service.CounsellorsService;

@Service
public class CounsellorsServiceImpl  implements CounsellorsService{
	
	@Autowired
   private CousellorsRepo cousellorsRepo;

	@Override
	public CounsellorsDto login(String email, String password) {
		   Counsellors entity = cousellorsRepo.findByEmailAndPassword(email, password);
		   if(entity!=null) {
			   CounsellorsDto dto=new CounsellorsDto();
			   BeanUtils.copyProperties(entity, dto);
			   return dto;
		   }
		   
		return null;
	}

	@Override
	public boolean isEmailUnique(String email) {
		Optional<Counsellors> byEmail = cousellorsRepo.findByEmail(email);
		
		if(byEmail.isPresent()) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean register(CounsellorsDto counsellorsDto) {
		Counsellors entity=new Counsellors();
		BeanUtils.copyProperties(counsellorsDto, entity);
		Counsellors saveEntity = cousellorsRepo.save(entity);
		
		return saveEntity.getCounsellorId() != null;
	}

}
