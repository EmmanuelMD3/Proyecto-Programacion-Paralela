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
}
