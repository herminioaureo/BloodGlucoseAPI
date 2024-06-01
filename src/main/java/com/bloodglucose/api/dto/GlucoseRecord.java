package com.bloodglucose.api.dto;

import java.util.Date;

public record GlucoseRecord(String meal, Integer value, Date datetime) {

}
