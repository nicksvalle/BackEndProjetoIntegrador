package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;


@Service
public class AuthAdminUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthAdminDTO authAdminDTO) throws AuthenticationException {
        
        var admin = this.adminRepository.findByAdminEmail(authAdminDTO.getAdminEmail()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Email/Password incorrect");
        });

        var passwordMatches = this.passwordEncoder.matches(authAdminDTO.getAdminPassword(), admin.getAdminPassword());

        System.out.println("Senha codificada: " + admin.getAdminPassword());
        System.out.println("Senha informada combina: " + passwordMatches);


        if(!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var token = JWT.create().withIssuer("cps")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(7)))
            .withSubject(admin.getIdAdmin().toString()).sign(algorithm);
        
        return token;
    }
}