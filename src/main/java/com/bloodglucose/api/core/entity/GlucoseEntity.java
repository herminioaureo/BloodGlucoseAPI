package com.bloodglucose.api.core.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

@Entity(name = "tbl_glucose")
@NamedQuery(name="tbl_glucose.findAll", query="SELECT g FROM tbl_glucose g")
//@NamedQuery(name="Glucose.findByMeal", query="SELECT g FROM Glucose g WHERE g.meal = ?")
public class GlucoseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idglucose;
	private String meal;
	private Integer value;
	private Date date;
	private String uuid;
	
	public GlucoseEntity() {}
	
	public GlucoseEntity(String meal, Integer value, Date date) {
		this.setMeal(meal);
		this.setValue(value);
		
		if (date != null)
			this.setDate(date);
		 else
			this.setDate(new Date());
	}
	
	public Long getIdglucose() {
		return idglucose;
	}
	
	public void setIdglucose(Long idglucose) {
		this.idglucose = idglucose;
	}
	
	public String getMeal() {
		return meal;
	}
	
	public void setMeal(String meal) {
		this.meal = meal;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
