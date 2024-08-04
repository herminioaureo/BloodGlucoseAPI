package com.bloodglucose.api.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bloodglucose.api.core.dto.GlucoseRecord;
import com.bloodglucose.api.port.in.GlucosePortIn;
import com.bloodglucose.api.core.util.GlucoseEnum;
import com.bloodglucose.api.core.validator.GlucoseValidator;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("glucoseapi")
public class GlucoseAdapterIn {
	
	@Autowired
	private GlucosePortIn portIn;

	@Autowired
	private GlucoseValidator validator;

	static final Logger logger = LoggerFactory.getLogger(GlucoseAdapterIn.class);

	@PostMapping(path = "save",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity save(@RequestBody GlucoseRecord glucose) {
		 try {
			 logger.info("Vai verificar se os dados estao validos... Glucose: ".concat(glucose.toString()));
			 String validationMeessage = validator.validateValues(glucose);
			 logger.info("Resultado da validacao: ".concat(validationMeessage));
			 if (GlucoseEnum.OK.toString().equals(validationMeessage)) {
				 logger.info("Vai salvar glucose no banco de dados");
				 portIn.saveGlucose(glucose);
				 logger.info("Glicose salva no banco de dados.");
			 } else {
				 logger.info("Requisicao invalida... ".concat(validationMeessage));
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMeessage);
			 }
			 logger.info("Glicose salva no banco de dados");
			 return ResponseEntity.status(HttpStatus.OK).build();
			 
		 } catch (Exception ex) {
			 logger.error("Erro ao processar glucose... ".concat(ex.getMessage()));
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
			logger.info("Recuperando todas as glicemias... ");
			List<GlucoseRecord> list = portIn.getGlucoseList();
			logger.info("Glicemias retornadas: " + list.size());
			return ResponseEntity.status(HttpStatus.OK).body(list);

		} catch (Exception ex) {
			logger.error("Erro ao retornar glicemias do banco de dados... ".concat(String.valueOf(ex)));
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
				logger.info("Recuperando todas as glicemias... refeicao: ".concat(meal));
				List<GlucoseRecord> list = portIn.getGlucoseListByMeal(meal);
				logger.info("Glicemias retornadas: " + list.size());
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
	 * @return lista de refeicoes daquela data
	 */
	@GetMapping (path = "findByDate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findByDate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

		if (date != null) {
			try {
				logger.info("Recuperando todas as glicemias... data: ".concat(date.toString()));
				List<GlucoseRecord> list = portIn.getGlucoseListByDate(date);
				logger.info("Glicemias retornadas: " + list.size());
				return ResponseEntity.status(HttpStatus.OK).body(list);

			} catch (Exception ex) {
				logger.error("Erro ao retornar glicemias do banco de dados... " + ex);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data nao informada");
		}
	}

	/**
	 * Metodo responsavel por retornar os valores da glicemia dado um intervalo de datas. <br>
	 * Exemplo de url: <code>http://localhost:8080/glucoseapi/findByDateBetween?startDate=2024-07-13&endDate=2024-07-14</code>
	 * @param startDate
	 * @param endDate
	 * @return lista daquelas refeicoes daquelas datas
	 */
	@GetMapping (path = "findByDateBetween", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findByDateBetween(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
											@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {

		if (startDate != null && endDate != null) {
			try {
				logger.info("Recuperando todas as glicemias...");
				logger.info("Data inicial: ".concat(startDate.toString()));
				logger.info("Data final: ".concat(endDate.toString()));
				List<GlucoseRecord> list = portIn.getGlucoseListByDateBetween(startDate, endDate);
				logger.info("Glicemias retornadas: " + list.size());
				return ResponseEntity.status(HttpStatus.OK).body(list);

			} catch (Exception ex) {
				logger.error("Erro ao retornar glicemias do banco de dados... " + ex);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datas nao informadas");
		}
	}

	/**
	 * Metodo responsavel por retornar os valores da glicemia dado uma refeicao e uma data. <br>
	 * Exemplo de url: <code>http://localhost:8080/glucoseapi/findByDateAndMeal?date=2024-07-13&meal=almoco</code>
	 * @param meal
	 * @param date
	 * @return
	 */
	@GetMapping (path = "findByDateAndMeal", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findByDateAndMeal(@RequestParam String meal,
											@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

		if (meal != null && date != null) {
			try {
				logger.info("Recuperando todas as glicemias...");
				logger.info("Refeicao: ".concat(meal));
				logger.info("Data: ".concat(date.toString()));
				List<GlucoseRecord> list =  portIn.getGlucoseListByDateAndMeal(date, meal);
				logger.info("Glicemias retornadas: " + list.size());
				return ResponseEntity.status(HttpStatus.OK).body(list);

			} catch (Exception ex) {
				logger.error("Erro ao retornar glicemias do banco de dados... " + ex);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametros invalidos");
		}

	}


	/**
	 * Metodo responsavel por retornar os valores da glicemia dado uma refeicao e um intervalo de datas. <br>
	 * Exemplo de url: <code>http://localhost:8080/glucoseapi/findByDateBetweenAndMeal?startDate=2024-07-13&endDate=2024-07-14&meal=almoco</code>
	 * @param meal
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GetMapping (path = "findByDateBetweenAndMeal", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findByDateBetweenAndMeal(@RequestParam String meal,
												   @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
												   @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {

		if (meal != null && startDate != null && endDate != null) {
			try {
				logger.info("Recuperando todas as glicemias...");
				logger.info("Refeicao: ".concat(meal));
				logger.info("Data inicial: ".concat(startDate.toString()));
				logger.info("Data final: ".concat(endDate.toString()));
				List<GlucoseRecord> list =  portIn.getGlucoseListByDateBetweenAndMeal(startDate, endDate, meal);
				logger.info("Glicemias retornadas: " + list.size());
				return ResponseEntity.status(HttpStatus.OK).body(list);
			} catch (Exception ex) {
				logger.error("Erro ao retornar glicemias do banco de dados... " + ex);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametros invalidos");
		}
	}

	/**
	 * Metodo responsavel por deletar uma glicemia dado um ID<br>
	 * A url sera <code>http://localhost:8080/glucoseapi/deleteById</code> e o ID deve ser passado o numero no body, sem ser json, so o numero
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "deleteById",
			       consumes = MediaType.APPLICATION_JSON_VALUE,
			       produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteById(@RequestBody Long id) {
		try {
			logger.info("Removendo glicemia com ID: " + id);
			if (id != null && id > 0) {
				portIn.deleteById(id);
				logger.info("Glicemia removida");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID invalido");
			}
			return ResponseEntity.status(HttpStatus.OK).build();

		} catch (Exception ex) {
			logger.error("Erro ao retornar glicemias do banco de dados... " + ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
}
