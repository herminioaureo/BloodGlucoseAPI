package com.bloodglucose.api.port.in;

import com.bloodglucose.api.core.dto.RecoveryUserRecord;
import com.bloodglucose.api.core.util.UserConverter;
import com.bloodglucose.api.port.out.UserPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPortIn {

    @Autowired
    private UserPortOut portOut;

    @Autowired
    private UserConverter userConverter;

    public RecoveryUserRecord findByEmail(String email) throws Exception {
        return userConverter.convertToRecord(portOut.findByEmail(email));
    }

}
