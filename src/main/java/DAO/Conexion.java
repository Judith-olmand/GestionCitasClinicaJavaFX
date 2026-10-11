package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import Controller.LoginController;
import util.R;


public class Conexion {

    public static Connection conectar() {
//        String url = "jdbc:mysql://" + DBConfig.getHost()
//                + ":" + DBConfig.getPort() + "/"
//                + DBConfig.getDatabase()
//                + "?serverTimezone=UTC";
        try {
            return DriverManager.getConnection("jdbc:mysql://" + DBConfig.getHost()
                            + ":" + DBConfig.getPort() + "/" + DBConfig.getDatabase()
                            + "?serverTimezone=UTC", DBConfig.getUser(), DBConfig.getPassword());
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}


//    private Connection conexion;
//
//    public void conectar() throws ClassNotFoundException, SQLException, IOException {
//        Properties configuration = new Properties();
//        configuration.load(R.getProperties("database.properties"));
//        String host = configuration.getProperty("host");
//        String port = configuration.getProperty("port");
//        String name = configuration.getProperty("name");
//        String username = configuration.getProperty("username");
//        String password = configuration.getProperty("password");
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
//                username, password);
//    }
//
//    private Connection getConexion() throws ClassNotFoundException, SQLException, IOException {
//        return conexion;
//    }
//
//
//
//    public void desconectar() throws SQLException {
//        conexion.close();
//    }
//}
//