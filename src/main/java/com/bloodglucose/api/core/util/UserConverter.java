package com.bloodglucose.api.core.util;

import com.bloodglucose.api.core.dto.RecoveryUserRecord;
import com.bloodglucose.api.core.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserConverter {

    public RecoveryUserRecord convertToRecord(UserEntity entity) {
        return new RecoveryUserRecord(entity.getId(), entity.getEmail(), entity.getPassword(), entity.getRoles());
    }

}
