package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;


@Service
public class AuthenticateAdminUseCase {

    @Autowired
    private AdminRepository adminRepository;

    public AdminEntity execute(String email, String password) throws Exception {
        Optional<AdminEntity> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            AdminEntity admin = adminOptional.get();
            if (admin.getAdminPassword().equals(password)) {
                return admin;
            } else {
                throw new Exception("Invalid password");
            }
        } else {
            throw new Exception("Invalid Email");
        }
    }
}