package com.projeto_integrador.projeto_integrador.exceptions;

public class TeacherFoundException extends RuntimeException{
    public TeacherFoundException() {
        super("Teacher already exists");
    }
}
