package com.bloodglucose.api.core.dto;

import com.bloodglucose.api.core.entity.RoleEntity;

import java.util.List;

public record RecoveryUserRecord(Long id, String email, String password, List<RoleEntity> roles) {
}
