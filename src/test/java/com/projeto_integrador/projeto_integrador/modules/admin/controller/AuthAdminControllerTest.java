package com.projeto_integrador.projeto_integrador.modules.admin.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.extension.ExtendWith;

import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.AuthAdminUseCase;

import javax.naming.AuthenticationException;

@ExtendWith(MockitoExtension.class)
public class AuthAdminControllerTest {

    @Mock
    private AuthAdminUseCase authAdminUseCase;

    @InjectMocks
    private AuthAdminController authAdminController;

    @BeforeEach
    public void setUp() {
        // Configurações antes de cada teste, se necessário
    }

    @Test
    @DisplayName("Should return token when authentication is successful")
    public void should_return_token_when_authentication_successful() throws Exception {
        // Arrange
        String email = "admin@example.com";
        String password = "password123";
        AuthAdminDTO authAdminDTO = new AuthAdminDTO(password, email);

        // Simulando um retorno de AuthAdminResponseDTO com um token
        AuthAdminResponseDTO responseDTO = new AuthAdminResponseDTO();
        responseDTO.setAccess_token("mockedToken"); // Simulando um token

        // Mockando o comportamento do use case
        when(authAdminUseCase.execute(authAdminDTO)).thenReturn(responseDTO);

        // Act
        ResponseEntity<Object> result = authAdminController.create(authAdminDTO);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK); // Verificando status 200
        assertThat(result.getBody()).isNotNull(); // Verificando que o corpo não é nulo
        assertThat(((AuthAdminResponseDTO) result.getBody()).getAccess_token()).isEqualTo("mockedToken"); // Verificando o token
    }

    @Test
    @DisplayName("Should return unauthorized when authentication fails")
    public void should_return_unauthorized_when_authentication_fails() throws AuthenticationException {
        // Arrange
        String email = "invalid@example.com";
        String password = "wrongPassword";
        AuthAdminDTO authAdminDTO = new AuthAdminDTO(password, email);

        // Simulando que o use case lançará uma exceção do tipo AuthenticationException
        when(authAdminUseCase.execute(authAdminDTO)).thenThrow(new AuthenticationException("Authentication failed"));

        // Act & Assert (capturando a exceção diretamente)
        ResponseEntity<Object> result = authAdminController.create(authAdminDTO);
        
        // Verificando se o status retornado é 401 e a mensagem correta
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(result.getBody()).isEqualTo("Authentication failed");
    }
}
