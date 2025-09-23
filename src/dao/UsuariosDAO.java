/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import model.Usuarios;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author chemo
 */
public class UsuariosDAO
{

    public static boolean agregarUsuarios(Usuarios usuarios)
    {
        String sql = "INSERT INTO Usuarios (nombre, correo, contrasenia) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, usuarios.getNombre());
            pstmt.setString(2, usuarios.getCorre());
            pstmt.setString(3, usuarios.getContrasenia());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e)
        {
            System.err.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    public static boolean validarUsuario(String correo, String contrasenia)
    {
        String sql = "SELECT * FROM Usuarios WHERE correo = ? AND contrasenia = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setString(1, correo);
            ps.setString(2, contrasenia);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static Usuarios obtenerUsuario(String correo, String contrasenia)
    {
        String sql = "SELECT * FROM Usuarios WHERE correo = ? AND contrasenia = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setString(1, correo);
            ps.setString(2, contrasenia);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                int id = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String email = rs.getString("correo");
                String pass = rs.getString("contrasenia");

                return new Usuarios(id, nombre, email, pass);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String obtenerNombreUsuarioPorId(int idUsuario)
    {
        String sql = "SELECT nombre FROM Usuarios WHERE idUsuario = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getString("nombre");
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "Usuario Desconocido";
    }
}
