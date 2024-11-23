package com.bloodglucose.api.port.out;

import com.bloodglucose.api.adapter.out.UserRepositoryAdapterOut;
import com.bloodglucose.api.core.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPortOut {

    @Autowired
    private UserRepositoryAdapterOut userRepository; // Repository que definimos anteriormente

    public UserEntity findByUsername(String email) throws Exception {
        return userRepository.findByUsername(email);
    }
}
