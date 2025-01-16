package com.stefanini;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Lista de Tarefas", version = "2.0.2", description = "API para criar um registro, obter todos os registros, obter um registro específico, deletar um registro e atualizar um registro."))
public class StefaniniApplication {

	public static void main(String[] args) {
		SpringApplication.run(StefaniniApplication.class, args);
	}

}
