package com.bloodglucose.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloodglucose.api.dto.GlucoseRecord;
import com.bloodglucose.api.service.GlucoseService;
import com.bloodglucose.api.util.GlucoseEnum;
import com.bloodglucose.api.validator.GlucoseValidator;

@RestController
@RequestMapping("glucoseapi")
public class Glucose {
	
	@Autowired
	private GlucoseService service;
	
	@Autowired
	private GlucoseValidator validator;
	
	@PostMapping(path = "glucose", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity save(@RequestBody GlucoseRecord glucose) {

		 try {
			 String validationMeessage = validator.validateValues(glucose);
			 
			 if (GlucoseEnum.OK.toString().equals(validationMeessage)) {
				 service.saveGlucose(glucose);
			 } else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMeessage);
			 }
			 
			return ResponseEntity.status(HttpStatus.OK).build();
			 
		 } catch (Exception ex) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		 }
		 
	 }

}
