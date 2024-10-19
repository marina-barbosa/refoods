package com.projeto.ReFood.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
      title = "ReFoods API", 
      description = "API para o projeto ReFoods", 
      version = "1.0.0", 
      contact = @Contact(
                          name = "", 
                          email = "", 
                          url = ""
                        ), 
      license = @License(
        name = "", 
        url = ""
      )
    ), 
    servers = {
      @Server(
        url = "http://localhost:8080", 
        description = "SERVIDOR DE DESENVOLVIMENTO"
      ),
      @Server(
        url = "", 
        description = "SERVIDOR DE PRODUÇÃO"
      )
    }
)

public class SwaggerConfig {}
