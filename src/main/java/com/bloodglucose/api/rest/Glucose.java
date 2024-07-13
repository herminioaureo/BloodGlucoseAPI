package com.bloodglucose.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bloodglucose.api.dto.GlucoseRecord;
import com.bloodglucose.api.service.GlucoseService;
import com.bloodglucose.api.util.GlucoseEnum;
import com.bloodglucose.api.validator.GlucoseValidator;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
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

	/**
	 * Metodo responsavel por retornar todos os valores da glicemia. <br>
	 * Exemplo de url: <code>http://localhost:8080/glucoseapi/findAll</code>
	 * @return lista com todas as glicemias
	 */
	@GetMapping (path = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findAll() {

		try {
			List<GlucoseRecord> list = service.getGlucoseList();
			return ResponseEntity.status(HttpStatus.OK).body(list);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	/**
	 * Metodo responsavel por retornar os valores da glicemia dado uma refeicao. <br>
	 * Exemplo de url: <code>http://localhost:8080/glucoseapi/findByMeal?meal=lanche</code>
	 * @param meal
	 * @return lista de glicemias daquela refeicao
	 */
	@GetMapping (path = "findByMeal", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findByMeal(@RequestParam String meal) {

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

	/**
	 * Metodo responsavel por retornar os valores da glicemia dado uma data. <br>
	 * Exemplo de url: <code>http://localhost:8080/glucoseapi/findByDate?date=2024-07-13</code>
	 * @param date
	 * @return
	 */
	@GetMapping (path = "findByDate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findByDate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

		if (date != null) {
			try {
				List<GlucoseRecord> list = service.getGlucoseListByDate(date);
				return ResponseEntity.status(HttpStatus.OK).body(list);

			} catch (Exception ex) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data nao informada");
		}
	}

	/**
	 * Metodo responsavel por retornar os valores da glicemia dado uma data. <br>
	 * Exemplo de url: <code>http://localhost:8080/glucoseapi/findByDateBetween?startDate=2024-07-13&endDate=2024-07-14</code>
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GetMapping (path = "findByDateBetween", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findByDateBetween(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
											@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {

		if (startDate != null && endDate != null) {
			try {
				List<GlucoseRecord> list = service.getGlucoseListByDateBetwee(startDate, endDate);
				return ResponseEntity.status(HttpStatus.OK).body(list);

			} catch (Exception ex) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datas nao informadas");
		}
	}
}
