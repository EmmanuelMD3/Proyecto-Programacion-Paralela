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
import java.util.LinkedHashMap;
import java.util.Map;

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

    public static Map<String, List<Producto>> obtenerProductosAgrupadosPorCategoria()
    {
        Map<String, List<Producto>> data = new LinkedHashMap<>();

        String sql = "SELECT p.idProducto, p.idCategoria, p.nombre, p.presentacion, p.precio, "
                + "c.nombre AS categoria "
                + "FROM Productos p "
                + "INNER JOIN Categorias c ON p.idCategoria = c.idCategoria "
                + "ORDER BY c.nombre, p.nombre";

        try (Connection conn = Conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {

            while (rs.next())
            {
                Producto p = new Producto(
                        rs.getInt("idProducto"),
                        rs.getInt("idCategoria"),
                        rs.getString("nombre"),
                        rs.getString("presentacion"),
                        rs.getDouble("precio"),
                        rs.getString("categoria")
                );

                String categoria = p.getNombreCategoria();

                data.putIfAbsent(categoria, new ArrayList<>());

                data.get(categoria).add(p);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return data;
    }

}
