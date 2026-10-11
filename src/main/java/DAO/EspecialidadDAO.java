package DAO;

import Clases.Especialidad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EspecialidadDAO {

    public ObservableList<Especialidad> obtenerTodas(Connection conexion) {
        ObservableList<Especialidad> listaNombresEspec = FXCollections.observableArrayList();

        String sql = "SELECT  IDE, NOMBRE FROM ESPECIALIDAD";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("IDE");
                String nombreEsp = rs.getString("NOMBRE");
                listaNombresEspec.add(new Especialidad(id, nombreEsp));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la cita del cliente. " + e.getMessage());
        }
        return listaNombresEspec;
    }

    public int obtenerIdEspecialidad(Connection conexion, String nombre) {
        String sql = "SELECT IDE FROM ESPECIALIDAD WHERE NOMBRE = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            int idEspecialidad = rs.getInt("IDE");
            return idEspecialidad;
        } catch (SQLException e){
            System.out.println("Error al obtener el ID de la especialidad. " + e.getMessage());
        }
        return 0;
    }
}
