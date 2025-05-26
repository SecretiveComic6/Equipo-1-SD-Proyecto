package com.ejemplo.tareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal para iniciar la aplicación Spring Boot del gestor de tareas.
 */
@SpringBootApplication
public class TareasApplication {

    /**
     * Método principal que lanza la aplicación.
     * 
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(TareasApplication.class, args);
    }
}
