package com.projeto_integrador.projeto_integrador.modules.student.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {
    
    @Id
    private Long id_student;

    private String student_name;

    private String institutionalEmail;

    private String student_password;
}
