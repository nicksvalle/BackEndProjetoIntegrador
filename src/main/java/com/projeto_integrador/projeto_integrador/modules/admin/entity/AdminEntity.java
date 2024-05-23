package com.projeto_integrador.projeto_integrador.modules.admin.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = "idAdmin")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "admin")
public class AdminEntity {
    
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdmin;

    @NotBlank
    @Column(name = "admin_name")
    private String adminName;
   
    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Length(min = 8, max = 100, message = "must have between 8 to 100 characters")
    @Column(name = "admin_password")
    private String adminPassword;
}
