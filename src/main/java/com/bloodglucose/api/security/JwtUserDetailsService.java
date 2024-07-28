package com.bloodglucose.api.security;

import com.bloodglucose.api.core.dto.RecoveryUserRecord;
import com.bloodglucose.api.core.entity.UserEntity;
import com.bloodglucose.api.core.util.UserConverter;
import com.bloodglucose.api.port.out.UserPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe usada pelo Spring Security, nao remover
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserPortOut repository;

    @Autowired
    private UserConverter converter;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        try {
            List<RecoveryUserRecord> userRecord = converter.convertToRecord(repository.findByEmail(username));
            return new UserDetailsImpl(userRecord.get(0));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
