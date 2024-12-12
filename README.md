# API de Grade de Horário - Sistema de Gestão de Laboratórios da Fatec Itu

Este projeto é uma solução para a gestão de reservas de laboratórios na Fatec Itu. A aplicação foi projetada para otimizar o uso dos espaços educacionais, oferecendo funcionalidades que atendem às necessidades de diferentes usuários: alunos, professores e administradores.

## Funcionalidades

- **Cadastro de usuários**: Permite registrar alunos, professores e administradores com perfis distintos.
- **Reserva de horários**: Gerencia o agendamento de laboratórios de forma eficiente.
- **Visualização de reservas**: Exibe as reservas ativas e horários disponíveis.
- **Documentação interativa**: Inclui integração com Swagger para facilitar o uso e testes da API.

## Tecnologias Utilizadas

- **Linguagem**: Java (Spring Boot)
- **Banco de Dados**: PostgreSQL
- **Containerização**: Docker com Docker Compose
- **Documentação**: Swagger UI

## Requisitos para Deploy

- **Docker Desktop** instalado e configurado.
- Ambiente de desenvolvimento recomendado: **Visual Studio Code** ou **Eclipse**.

## Passo a Passo para Deploy

1. **Clone o repositório**:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd <PASTA_DO_PROJETO>
   ```
2. **Suba os containers com Docker Compose**:

  ```bash
  docker-compose up -d
  ```

## Estrutura do Projeto

- **src/main**: Contém o código principal do projeto, incluindo os controladores, serviços e modelos.
- **docker-compose.yml**: Arquivo de configuração para subir os serviços necessários (banco de dados e API).
- **application.properties**: Configurações do Spring Boot.

### Contato

Se tiver dúvidas ou sugestões, entre em contato:

- **Email**: [nicolasdeoliveiravalle@gmail.com](mailto:nicolasdeoliveiravalle@gmail.com)  
- **LinkedIn**: [Nicolas Valle](www.linkedin.com/in/nicolas-valle-620b29219)
