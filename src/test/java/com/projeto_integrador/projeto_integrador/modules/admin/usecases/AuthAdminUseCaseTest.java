package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;
import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminResponseDTO; 

@ExtendWith(MockitoExtension.class)  // Garante que o MockitoExtension seja utilizado para mocks
@TestPropertySource(properties = "security.token.secret=INTEGRADOR_@123#")
public class AuthAdminUseCaseTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private String secretKey = "INTEGRADOR_@123#"; 

    @InjectMocks  // Garante a injeção dos mocks no use case
    private AuthAdminUseCase authAdminUseCase;

    @BeforeEach
    public void setUp() {
        // O @InjectMocks vai garantir que o authAdminUseCase seja corretamente inicializado com os mocks
    }

    @Test
    @DisplayName("Should throw BadCredentialsException if email or password is null")
    public void should_throw_exception_if_email_or_password_is_null() {
        var authAdminDTO = new AuthAdminDTO(null, null);
        
        assertThatThrownBy(() -> authAdminUseCase.execute(authAdminDTO))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("Email and password must not be null");
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException if admin email not found")
    public void should_throw_exception_if_admin_email_not_found() {
        var authAdminDTO = new AuthAdminDTO("password123", "admin@example.com");
        
        // Mockando o comportamento do repositório
        when(adminRepository.findByAdminEmail("admin@example.com"))
            .thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> authAdminUseCase.execute(authAdminDTO))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("Email not found");
    }

    @Test
    @DisplayName("Should throw AuthenticationException if password does not match")
    public void should_throw_exception_if_password_does_not_match() throws Exception {
        var authAdminDTO = new AuthAdminDTO("password123", "admin@example.com");
        var admin = new AdminEntity();
        admin.setAdminPassword("encodedPassword");

        // Mockando o comportamento do repositório e a comparação da senha
        when(adminRepository.findByAdminEmail("admin@example.com"))
            .thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("password123", "encodedPassword"))
            .thenReturn(false);
        
        assertThatThrownBy(() -> authAdminUseCase.execute(authAdminDTO))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("Invalid password");
    }

    @Test
    @DisplayName("Should load the secret key from properties")
    public void should_load_secret_key_from_properties() {
        assertThat(secretKey).isEqualTo("INTEGRADOR_@123#");
    }

    @Test
    @DisplayName("Should return token when authentication is successful")
    public void should_return_token_when_authentication_successful() throws Exception {
        var authAdminDTO = new AuthAdminDTO("password123", "admin@example.com");
        var admin = new AdminEntity();
        admin.setAdminId(1L);
        admin.setAdminPassword("encodedPassword");

        // Mockando o comportamento de retorno do repositório e a comparação da senha
        when(adminRepository.findByAdminEmail("admin@example.com"))
            .thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("password123", "encodedPassword"))
            .thenReturn(true);

        // Criando um DTO de resposta com o token
        AuthAdminResponseDTO responseDTO = new AuthAdminResponseDTO();
        responseDTO.setAccess_token("INTEGRADOR_@123#");

        // Simulando o retorno do DTO com o token
        when(authAdminUseCase.execute(authAdminDTO)).thenReturn(responseDTO);

        // Act
        AuthAdminResponseDTO result = authAdminUseCase.execute(authAdminDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccess_token()).isEqualTo("mockedToken");  // Verificando o token
    }
}
