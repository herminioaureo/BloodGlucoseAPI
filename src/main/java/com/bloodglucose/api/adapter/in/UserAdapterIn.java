package com.bloodglucose.api.adapter.in;

import com.bloodglucose.api.core.dto.CreateUserRecord;
import com.bloodglucose.api.core.dto.LoginUserRecord;
import com.bloodglucose.api.core.dto.RecoveryJwtTokenRecord;
import com.bloodglucose.api.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserAdapterIn {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://http://localhost:4200/")
    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenRecord> authenticateUser(@RequestBody LoginUserRecord loginUser) {
        RecoveryJwtTokenRecord token = userService.authenticateUser(loginUser);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRecord createUser) {
        userService.createUser(createUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }
}
