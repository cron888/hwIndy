package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главное приложение Spring Boot.
 * 
 * Точка входа в приложение. Запускает встроенный Tomcat и инициализирует Spring-контекст.
 * Автоматически сканирует пакеты org.example.recommendation** для регистрации компонентов.
 * 
 * Порт сервера настраивается в application.yml (по умолчанию 8085).
 */
@SpringBootApplication
public class StarLTDApplication {
    /**
     * Точка входа в приложение.
     * Запускает Spring Boot приложение с встроенным сервером Tomcat.
     * 
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(StarLTDApplication.class, args);
    }
}
