package DAO;

import Clases.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ClienteDAO {

    //METODO PARA OBTENER LA CONTRASEÑA DEL CLIENTE CON ID PASADO POR PARÁMETRO
    public String obtenerPasswordHash(Connection conexion, int idCliente) {
        String sql = "SELECT CONTRASENIA FROM CLIENTE WHERE ID = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("CONTRASENIA");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el hash de la contraseña");
        }
        return null;
    }

    public Cliente obtenerDatosCliente(Connection conexion, int idCliente){
        String sql = "SELECT DNI, NOMBRE, DIRECCION, TELEFONO FROM CLIENTE WHERE ID = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String dni = rs.getString("DNI");
                String nombre = rs.getString("NOMBRE");
                String direccion = rs.getString("DIRECCION");
                int telefono = rs.getInt("TELEFONO");
                Cliente datosCliente = new Cliente(idCliente,dni,nombre,direccion,telefono);
                return datosCliente;
            } else {
                System.out.println("No se encontró el cliente");
            }


        }catch (SQLException e){
            System.out.println("Error al obtener los datos del cliente. " +e.getMessage());
        }
        return null;
    }

    public int obtenerIdCliente(Connection conexion, String dni) {
        String sql = "SELECT ID FROM CLIENTE WHERE DNI = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("ID");
        }catch (SQLException e){
            System.out.println("Error al obtener el id del cliente. " +e.getMessage());
        }
        return 0;
    }
}
