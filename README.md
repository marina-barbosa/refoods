## Refood - Back-End

## Requisitos

- JDK: Versão 17
- Maven: Para gerenciamento de dependências
- Banco de Dados: PostgreSQL (runtime)
- Swagger: Para documentação da API
- JWT: Para autenticação de usuários
    
## Dependências
As dependências principais já estão especificadas no pom.xml e incluem:

- Spring Boot (Web, JPA, Security, OAuth2)
- H2 Database (para testes)
- PostgreSQL (para runtime)
- Lombok (opcional)
- Swagger (para UI do Swagger)
- JWT (para autenticação)

## Estrutura de pastas

```plaintext
src/
├── main/                       
│   ├── java/                     
│   │   └── com.projeto.ReFood/           
│   │       ├── controller/  
│   │       ├── dto/
│   │       ├── exception/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── security/
│   │       ├── service/  
│   │       └── ReFoodsApplication.java                
│   └── resources/                 
│       └── ...
└──...
```

# Descrição das pastas
src/main/java/com.projeto.ReFood/: Contém o código-fonte Java principal do projeto.

- controller/: Onde ficam os controllers, responsáveis por lidar com as requisições HTTP e definir os endpoints da API. Os controllers traduzem as requisições dos usuários em chamadas para os serviços da aplicação.

- dto/: Armazena as classes DTO (Data Transfer Object). Essas classes são usadas para transportar dados entre as camadas da aplicação e podem ser usadas para mapear objetos de entrada e saída da API.

- exception/: Contém as classes relacionadas ao tratamento de exceções. Aqui você pode criar exceções personalizadas e lidar com erros globais, como respostas personalizadas para códigos de erro HTTP.

- model/: Armazena as classes que representam os modelos de dados (entidades). Essas classes geralmente são mapeadas para tabelas de banco de dados e são usadas para persistência de dados com o JPA (Java Persistence API).

- repository/: Contém as interfaces repository, que se comunicam diretamente com o banco de dados. Usando JPA, você pode definir métodos para realizar operações como salvar, atualizar, excluir ou consultar entidades no banco de dados.

- security/: Contém as configurações e classes relacionadas à segurança da aplicação, como autenticação e autorização, usando OAuth2, JWT e outras estratégias de segurança.

- service/: Contém as classes de service, responsáveis pela lógica de negócios. Essa camada realiza o processamento de dados e coordena as operações entre os repositórios e os controllers.

- ReFoodsApplication.java: Classe principal da aplicação Spring Boot, contendo o método main. Esta é a classe que inicializa e configura o contexto da aplicação.

- src/main/resources/: Armazena recursos não Java, como arquivos de configuração (application.properties ou application.yml), templates de e-mail, arquivos de mapeamento SQL e outros recursos que a aplicação pode precisar.