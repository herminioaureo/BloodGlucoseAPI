package com.bloodglucose.api.validator;

import dto.GlucoseRecord;

public class GlucoseValidator {
	
	public String validateValues(GlucoseRecord glucose) {
		
		if (glucose.meal() == null || "".equals(glucose.meal())) {
			return " .: refeicao nao informada :. ";
		} else if (glucose.value() == null) {
			return " .: glicose nao informada :. ";
		}
		
		return "OK";
	}
	

}
