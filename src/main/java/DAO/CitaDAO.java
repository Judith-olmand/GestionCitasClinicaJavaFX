package DAO;

import Clases.Cita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

public class CitaDAO {
    public ObservableList<Cita> obtenerCitasCliente(Connection conexion, int idCliente){
        ObservableList<Cita> listaCitas = FXCollections.observableArrayList();

        String sql = "SELECT c.NUMEROCITA, c.FECHA, c.IDESPECIAL, e.NOMBRE FROM CITA C JOIN ESPECIALIDAD E ON c.IDESPECIAL = e.IDE WHERE IDP = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int numeroCita = rs.getInt("NUMEROCITA");
                Date fecha = rs.getDate("FECHA");
                String nombreEsp = rs.getString("NOMBRE");
                int idEspecialidad = rs.getInt("IDESPECIAL");
                listaCitas.add(new Cita(idCliente, numeroCita, fecha, nombreEsp, idEspecialidad));
            }

        }catch (SQLException e){
            System.out.println("Error al obtener la cita del cliente. " +e.getMessage());
        }
        return listaCitas;
    }

    public void borrarCita(Connection conexion,int numeroCita){
        String sql = "DELETE FROM CITA WHERE NUMEROCITA = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, numeroCita);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Error al borrar la cita del cliente. " +e.getMessage());
        }
    }

    public void modificarCita(Connection conexion,int numeroCita, java.sql.Date fecha, int idespecialidad){
        String sql = "UPDATE CITA SET FECHA = ?, IDESPECIAL = ? WHERE NUMEROCITA = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setDate(1, fecha);
            ps.setInt(2, idespecialidad);
            ps.setInt(3, numeroCita);
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error al modificar la cita del cliente. " +e.getMessage());
        }
    }

    public int idNuevaCita(Connection conexion){
        String sql = "SELECT MAX(NUMEROCITA) FROM CITA";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)){
            rs.next();
            return rs.getInt("MAX(NUMEROCITA)");
        }catch (SQLException e){
            System.out.println("Error al obtener el id. " +e.getMessage());
        }
        return -1;
    }

    public void aniadirCita(Connection conexion,int idCliente, java.sql.Date fecha, int idEspecialidad){
        int idNuevo = idNuevaCita(conexion);
        if(idNuevo == -1){
            System.out.println("Error al obtener el id");
        } else {
            idNuevo += 1;
            String sql = "INSERT INTO CITA VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conexion.prepareStatement(sql)){
                ps.setInt(1, idCliente);
                ps.setInt(2, idNuevo);
                ps.setDate(3, fecha);
                ps.setInt(4, idEspecialidad);
                ps.executeUpdate();

            }catch (SQLException e){
                System.out.println("Error al añadir la cita del cliente. " +e.getMessage());
            }
        }
    }
}
