/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author chemo
 */
public class Productos
{
    private int idProducto;
    private int idCategoria;
    private String nombre;
    private String presentacion;
    private double precio;

    public Productos()
    {
    }

    public Productos(int idProducto, int idCategoria, String nombre, String presentacion, double precio)
    {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.precio = precio;
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
     * @return the idCategoria
     */
    public int getIdCategoria()
    {
        return idCategoria;
    }

    /**
     * @param idCategoria the idCategoria to set
     */
    public void setIdCategoria(int idCategoria)
    {
        this.idCategoria = idCategoria;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the presentacion
     */
    public String getPresentacion()
    {
        return presentacion;
    }

    /**
     * @param presentacion the presentacion to set
     */
    public void setPresentacion(String presentacion)
    {
        this.presentacion = presentacion;
    }

    /**
     * @return the precio
     */
    public double getPrecio()
    {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio)
    {
        this.precio = precio;
    }
}
