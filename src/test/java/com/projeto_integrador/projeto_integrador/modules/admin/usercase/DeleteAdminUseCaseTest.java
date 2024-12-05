package com.projeto_integrador.projeto_integrador.modules.admin.usercase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.DeleteAdminUseCase;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class DeleteAdminUseCaseTest {

    @Mock
    private AdminRepository repository;

    @InjectMocks
    private DeleteAdminUseCase deleteAdminUseCase;

    @BeforeEach
    public void setUp() {
        // Não precisamos mais acessar diretamente o campo repository
        // O Mockito cuidará da injeção de dependências.
    }

    @Test
    @DisplayName("Should delete admin when ID exists")
    public void shouldDeleteAdminWhenIdExists() {
        // Arrange
        Long adminId = 1L;
        when(repository.existsById(adminId)).thenReturn(true);

        // Act
        deleteAdminUseCase.execute(adminId);

        // Assert
        verify(repository, times(1)).deleteById(adminId);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when ID does not exist")
    public void shouldThrowExceptionWhenIdDoesNotExist() {
        // Arrange
        Long adminId = 1L;
        when(repository.existsById(adminId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> deleteAdminUseCase.execute(adminId))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Admin not found");
    }
}
