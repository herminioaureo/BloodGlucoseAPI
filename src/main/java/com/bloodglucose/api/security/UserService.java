package com.bloodglucose.api.security;

import com.bloodglucose.api.adapter.out.UserRepositoryPortOut;
import com.bloodglucose.api.core.dto.CreateUserRecord;
import com.bloodglucose.api.core.dto.LoginUserRecord;
import com.bloodglucose.api.core.dto.RecoveryJwtTokenRecord;
import com.bloodglucose.api.core.entity.RoleEntity;
import com.bloodglucose.api.core.entity.UserEntity;
import com.bloodglucose.api.core.util.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepositoryPortOut userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenRecord authenticateUser(LoginUserRecord loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenRecord(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserRecord createUser) {

        // Cria um novo usuário com os dados fornecidos
        UserEntity newUser = UserEntity.builder()
                .email(createUser.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUser.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(RoleEntity.builder().name(createUser.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }
}
