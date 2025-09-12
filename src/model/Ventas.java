/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author chemo
 */
public class Ventas
{
    private int idVenta;
    private int idUsuario;
    private Date fecha;
    private double subtotal;
    private double descuento;
    private double total;

    public Ventas()
    {
    }

    public Ventas(int idVenta, int idUsuario, Date fecha, double subtotal, double descuento, double total)
    {
        this.idVenta = idVenta;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
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
     * @return the idUsuario
     */
    public int getIdUsuario()
    {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario)
    {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the fecha
     */
    public Date getFecha()
    {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal()
    {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal)
    {
        this.subtotal = subtotal;
    }

    /**
     * @return the descuento
     */
    public double getDescuento()
    {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(double descuento)
    {
        this.descuento = descuento;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }
    
}
