package com.bloodglucose.api.core.dto;

import java.util.Date;

public record GlucoseRecord(Long id, String meal, Integer value, Date datetime) {

}
