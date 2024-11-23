package com.bloodglucose.api.core.dto;

import com.bloodglucose.api.core.util.RoleName;

public record CreateUserRecord(String email, String password, RoleName role) { }
