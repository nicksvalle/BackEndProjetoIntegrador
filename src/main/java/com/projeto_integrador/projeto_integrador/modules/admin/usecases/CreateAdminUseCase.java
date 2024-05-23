package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;
import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

@Service
public class CreateAdminUseCase {
    @Autowired
    AdminRepository adminRepository;

    public AdminEntity execute(AdminEntity adminEntity){
        this.adminRepository.findByEmail(adminEntity.getEmail())
                                .ifPresent(user -> {
                                    throw new UserFoundException();
        });

        return this.adminRepository.save(adminEntity);
    }
}
