package com.bloodglucose.api.core.validator;

import org.springframework.stereotype.Service;

import com.bloodglucose.api.core.dto.GlucoseRecord;
import com.bloodglucose.api.core.util.GlucoseEnum;

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
