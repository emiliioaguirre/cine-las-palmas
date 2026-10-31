package com.cine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class CineApplication {
    public static void main(String[] args) {
        // Inicia el servidor HTTP y conserva los argumentos de ejecución.
        SpringApplication.run(CineApplication.class, args);
    }
    // Un atributo entero no debe aceptar decimales truncados al leer JSON.
    @org.springframework.context.annotation.Bean
    public org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer enterosSinDecimales() {
        return builder -> builder.disable(tools.jackson.databind.DeserializationFeature.ACCEPT_FLOAT_AS_INT);
    }
}
