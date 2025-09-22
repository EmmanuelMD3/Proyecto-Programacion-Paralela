/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import model.Ventas;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;
import model.CarritoItem;

/**
 *
 * @author chemo
 */
public class VentasDAO
{

    public static int registrarVenta(int idUsuario, double subtotal, double descuento, double total, List<CarritoItem> items)
    {
        int idVenta = -1;

        String sqlVenta = "INSERT INTO Ventas (idUsuario, subtotal, descuento, total) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO Detalle_Ventas (idVenta, idProducto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar(); PreparedStatement psVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS); PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle))
        {
            conn.setAutoCommit(false); 

            psVenta.setInt(1, idUsuario);
            psVenta.setDouble(2, subtotal);
            psVenta.setDouble(3, descuento);
            psVenta.setDouble(4, total);
            psVenta.executeUpdate();

            ResultSet rs = psVenta.getGeneratedKeys();
            if (rs.next())
            {
                idVenta = rs.getInt(1);
            }

            for (CarritoItem item : items)
            {
                psDetalle.setInt(1, idVenta);
                psDetalle.setInt(2, item.getProducto().getIdProducto());
                psDetalle.setInt(3, item.getCantidad());
                psDetalle.setDouble(4, item.getProducto().getPrecio());
                psDetalle.addBatch();
            }

            psDetalle.executeBatch();

            conn.commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return idVenta;
    }
}
