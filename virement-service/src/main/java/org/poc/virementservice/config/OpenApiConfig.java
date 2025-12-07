package org.poc.virementservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI virementServiceAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8082");
        devServer.setDescription("Serveur de développement");

        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080/virement-service");
        gatewayServer.setDescription("Serveur via Gateway");

        Contact contact = new Contact();
        contact.setEmail("contact@enset.ma");
        contact.setName("ENSET Support");
        contact.setUrl("https://www.enset.ma");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("API de Gestion des Virements Bancaires")
                .version("1.0.0")
                .contact(contact)
                .description("Cette API expose les endpoints pour gérer les virements bancaires dans le système e-banking")
                .termsOfService("https://www.enset.ma/terms")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, gatewayServer));
    }
}