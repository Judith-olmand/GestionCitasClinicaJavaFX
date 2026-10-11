package DAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DBConfig.class
                .getClassLoader()
                .getResourceAsStream("configuration/db.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró db.properties");
            }
            props.load(input);

        } catch (IOException e){
            throw new RuntimeException("Error al cargar db.properties", e);
        }
    }

    public static String getHost() {
        return props.getProperty("host");
    }
    public static int getPort() {
        return Integer.parseInt(props.getProperty("port"));
    }
    public static String getDatabase() {
        return props.getProperty("name");
    }
    public static String getUser() {
        return props.getProperty("username");
    }
    public static String getPassword() {
        return props.getProperty("password");
    }
}
