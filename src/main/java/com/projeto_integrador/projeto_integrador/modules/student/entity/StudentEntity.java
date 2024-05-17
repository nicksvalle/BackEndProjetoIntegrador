package com.projeto_integrador.projeto_integrador.modules.student.entity;

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
@EqualsAndHashCode(of = "id_student")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "students")
public class StudentEntity {
    
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_student;

    @NotBlank
    @Column(name = "student_name")
    private String student_name;
   
    @NotBlank
    @Email
    @Column(name = "institutional_email")
    private String institutionalEmail;

    @NotBlank
    @Length(min = 8, max = 100, message = "must have between 8 to 100 characters")
    @Column(name = "student_password")
    private String studentPassword;
}
