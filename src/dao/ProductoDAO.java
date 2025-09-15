/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import model.Producto;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author chemo
 */
public class ProductoDAO
{

    public static List<Producto> obtenerProductos()
    {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos";

        try (Connection conn = Conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {

            while (rs.next())
            {
                Producto p = new Producto(
                        rs.getInt("idProducto"),
                        rs.getInt("idCategoria"),
                        rs.getString("nombre"),
                        rs.getString("presentacion"),
                        rs.getDouble("precio")

                );
                lista.add(p);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return lista;
    }
}
