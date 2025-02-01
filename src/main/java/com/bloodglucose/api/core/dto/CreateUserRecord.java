package com.bloodglucose.api.core.dto;

import com.bloodglucose.api.core.util.RoleName;

public record CreateUserRecord(String username, String password, RoleName role) { }
