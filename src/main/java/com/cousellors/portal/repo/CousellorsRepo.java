package com.cousellors.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cousellors.portal.dto.CounsellorsDto;
import com.cousellors.portal.entities.Counsellors;
import java.util.List;
import java.util.Optional;


public interface CousellorsRepo extends JpaRepository<Counsellors,Integer> {
	
	
	public Counsellors  findByEmailAndPassword(String email,String password);
	
	//select * from counsellors where email=:email
	public Optional<Counsellors> findByEmail(String email);
	
	

}
