package com.bloodglucose.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bloodglucose.api.dto.GlucoseRecord;
import com.bloodglucose.api.service.GlucoseService;
import com.bloodglucose.api.util.GlucoseEnum;
import com.bloodglucose.api.validator.GlucoseValidator;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

@RestController
@RequestMapping("glucoseapi")
public class Glucose {
	
	@Autowired
	private GlucoseService service;
	
	@Autowired
	private GlucoseValidator validator;
	
	@PostMapping(path = "glucose",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
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

	@GetMapping (path = "listAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity listAll() {

		try {
			List<GlucoseRecord> list = service.getGlucoseList();
			return ResponseEntity.status(HttpStatus.OK).body(list);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	/**
	 * Metodo responsavel por retornar os valores da glicemia dado uma refeicao. <br>
	 * Exemplo de url: <code>http://localhost:8080/glucoseapi/listByMeal?meal=lanche</code>
	 * @param meal
	 * @return lista de glicemias daquela refeicao
	 */
	@GetMapping (path = "listByMeal", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity listByMeal(String meal) {

		if (StringUtils.isNotEmpty(meal)) {
			try {
				List<GlucoseRecord> list = service.getGlucoseListByMeal(meal);
				return ResponseEntity.status(HttpStatus.OK).body(list);

			} catch (Exception ex) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refeicao nao informada");
		}
	}
}
