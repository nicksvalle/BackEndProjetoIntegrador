package com.projeto_integrador.projeto_integrador.modules.teacher.entity;

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
@Entity(name = "teachers")
public class TeacherEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @NotBlank
    @Column(name = "teacher_name")
    private String teacher_name;

    @NotBlank
    @Email(message = "The field (institutional) must have a valid institutional email")
    @Column(name = "institutional_email")
    private String institutionalEmail;

    @NotBlank
    @Email(message = "The field (personal_email) must have a valid personal email")
    @Column(name = "personal_email")
    private String personal_email;

    @NotBlank
    @Length(min = 8, max = 100, message = "must have between 8 to 100 characters")
    @Column(name = "teacher_password")
    private String teacher_password;

    @NotBlank
    @Column(name = "personal_phone")
    private String personal_phone;

    @NotBlank
    @Column(name = "business_phone")
    private String business_phone;

    @NotBlank
    @Column(name = "research_line")
    private String research_line;

    @NotBlank
    @Column(name = "teacher_area")
    private String teacher_area;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
    

}
