package com.projeto_integrador.projeto_integrador.modules.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthAdminDTO {
    
    private String adminPassword;
    private String adminEmail;
}
