/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author chemo
 */
public class Detalle_Ventas
{
    private int idDetalle;
    private int idVenta;
    private int idProducto;
    private int cantidad;
    private double precio_unitario;

    public Detalle_Ventas(int idDetalle, int idVenta, int idProducto, int cantidad, double precio_unitario)
    {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public Detalle_Ventas()
    {
    }

    /**
     * @return the idDetalle
     */
    public int getIdDetalle()
    {
        return idDetalle;
    }

    /**
     * @param idDetalle the idDetalle to set
     */
    public void setIdDetalle(int idDetalle)
    {
        this.idDetalle = idDetalle;
    }

    /**
     * @return the idVenta
     */
    public int getIdVenta()
    {
        return idVenta;
    }

    /**
     * @param idVenta the idVenta to set
     */
    public void setIdVenta(int idVenta)
    {
        this.idVenta = idVenta;
    }

    /**
     * @return the idProducto
     */
    public int getIdProducto()
    {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(int idProducto)
    {
        this.idProducto = idProducto;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad()
    {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }

    /**
     * @return the precio_unitario
     */
    public double getPrecio_unitario()
    {
        return precio_unitario;
    }

    /**
     * @param precio_unitario the precio_unitario to set
     */
    public void setPrecio_unitario(double precio_unitario)
    {
        this.precio_unitario = precio_unitario;
    }
            
}
