package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String URL;
    private static final String USUARIO;
    private static final String SENHA;

    static {
        Properties props = new Properties();
        try (InputStream input = ConnectionFactory.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo db.properties não encontrado. Copie db.properties.example e preencha com suas credenciais.");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar db.properties", e);
        }

        URL = props.getProperty("db.url");
        USUARIO = props.getProperty("db.usuario");
        SENHA = props.getProperty("db.senha");
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}