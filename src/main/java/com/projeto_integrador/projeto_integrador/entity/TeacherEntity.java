package com.projeto_integrador.projeto_integrador.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_teachers")
public class TeacherEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Teacher")
    private Long id;

    @NotBlank
    @Column(name = "Teacher_Name")
    private String teacher_name;

    @NotBlank
    @Email(message = "O campo (institutional_email) deve conter um email válido")
    @Column(name = "Institutional_Email")
    private String institutional_email;

    @NotBlank
    @Email(message = "O campo (personal_email) deve conter um email válido")
    @Column(name = "Personal_Email")
    private String personal_email;

    @NotBlank
    @Length(min = 8, max = 100)
    @Column(name = "Teacher_Password")
    private String teacher_password;

    @NotBlank
    @Column(name = "Personal_Phone")
    private String personal_phone;

    @NotBlank
    @Column(name = "Business_Phone")
    private String business_phone;

    @NotBlank
    @Column(name = "Research_Line")
    private String research_line;

    @NotBlank
    @Column(name = "Teacher_Area")
    private String teacher_area;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
    

}
