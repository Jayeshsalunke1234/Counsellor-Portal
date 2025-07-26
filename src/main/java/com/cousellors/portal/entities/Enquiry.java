package com.cousellors.portal.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Enquiry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enquiryId;
	private String name;
	private Long phno;
	private String classMode;
	private String courseName;
	private String enqstatus;
	
	@CreationTimestamp
	private LocalDate createAt;
	
	@UpdateTimestamp
	private LocalDate updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "counsellor_id")
	private Counsellors counsellor;


}
