package com.projeto_integrador.projeto_integrador.modules.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.CreateAdminUseCase;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.DeleteAdminUseCase;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.GetAllAdmins;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.GetAdminById;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.PutAdminById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin") 
public class AdminController {
    
    @Autowired
    AdminRepository repository;

    @Autowired
    CreateAdminUseCase createAdmin;

    @Autowired
    GetAllAdmins getAllAdmins;

    @Autowired
    GetAdminById getAdminById;

    @Autowired
    PutAdminById putAdminById;

    @Autowired
    DeleteAdminUseCase deleteAdminById;

    

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody AdminEntity adminEntity) {
        try {
            var result = this.createAdmin.execute(adminEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<AdminEntity>> getAllAdmins() {
       try {
            var result = this.getAllAdmins.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("Admin not Register");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminEntity> getById(@Valid @PathVariable Long id){
       try {
        var admin = this.getAdminById.execute(id);
        return ResponseEntity.ok().body(admin);
       } catch (Exception e) {
            throw new EntityNotFoundException("Admin not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminEntity> putAdmin(@Valid @RequestBody AdminEntity adminEntity, @PathVariable Long id) {
        try {
            var updatedAdmin = this.putAdminById.execute(id, adminEntity);
            return ResponseEntity.ok().body(updatedAdmin);
        } catch (Exception e) {
            throw new EntityNotFoundException("Admin not found");
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@Valid @PathVariable Long id) {
        this.deleteAdminById.execute(id);
        return ResponseEntity.ok().build();
    }

    
}
