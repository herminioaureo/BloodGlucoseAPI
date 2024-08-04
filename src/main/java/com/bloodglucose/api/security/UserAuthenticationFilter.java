package com.bloodglucose.api.security;

import com.bloodglucose.api.adapter.out.UserRepositoryAdapterOut;
import com.bloodglucose.api.core.dto.RecoveryUserRecord;
import com.bloodglucose.api.core.entity.UserEntity;
import com.bloodglucose.api.port.in.UserPortIn;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService; // Service que definimos anteriormente
    @Autowired
    private UserPortIn userPortIn;

    static final Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Verifica se o endpoint requer autenticação antes de processar a requisição
       logger.info("vai verificaqr se o endpoint requeer autenticacao antes de processar a requisicao");
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request); // Recupera o token do cabeçalho Authorization da requisição
            logger.info("token recuperado do header da requisicao. token: ".concat(token));
            if (!token.isBlank() || !token.isEmpty()) {
                String subject = jwtTokenService.getSubjectFromToken(token); // Obtém o assunto (neste caso, o nome de usuário) do token
                logger.info("usuario recuperado do token... usuario: ".concat(subject));
                try {
                    RecoveryUserRecord recoveryUser = userPortIn.findByEmail(subject); // Busca o usuário pelo email (que é o assunto do token)
                    UserDetailsImpl userDetails = new UserDetailsImpl(recoveryUser); // Cria um UserDetails com o usuário encontrado
                    // Cria um objeto de autenticação do Spring Security
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                    // Define o objeto de autenticação no contexto de segurança do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("fluxo de autenticacao realizado com sucesso!");
                } catch (Exception ex) {
                    logger.debug("Erro ao obter dados do usuario e fazer autenticacao. " + ex.getMessage());
                    throw new RuntimeException("Erro ao obter dados do usuario e fazer autenticacao. " + ex.getMessage());
                }
            } else {
                logger.debug("o token esta ausente ou eh invalido");
                throw new RuntimeException("O token está ausente.");
            }
        }
        filterChain.doFilter(request, response); // Continua o processamento da requisição
    }

    // Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    // Verifica se o endpoint requer autenticação antes de processar a requisição
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        boolean result = !Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);

        return result;
    }

}

