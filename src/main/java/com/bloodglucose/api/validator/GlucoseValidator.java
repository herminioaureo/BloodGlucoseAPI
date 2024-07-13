package com.bloodglucose.api.validator;

import org.springframework.stereotype.Service;

import com.bloodglucose.api.dto.GlucoseRecord;
import com.bloodglucose.api.util.GlucoseEnum;

@Service
public class GlucoseValidator {
	
	public String validateValues(GlucoseRecord glucose) {
		
		if (glucose.meal() == null || "".equals(glucose.meal())) {
			return GlucoseEnum.REFEICAO_NAO_INFORMADA.toString();
		} else if (glucose.value() == null) {
			return GlucoseEnum.GLICOSE_NAO_INFORMADA.toString();
		}
		
		return GlucoseEnum.OK.toString();
	}

}
