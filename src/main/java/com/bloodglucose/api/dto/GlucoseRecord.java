package com.bloodglucose.api.dto;

import java.util.Date;

public record GlucoseRecord(Long id, String meal, Integer value, Date datetime) {

}
