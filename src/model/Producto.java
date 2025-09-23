/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author chemo
 */
public class Producto
{

    private int idProducto;
    private int idCategoria;
    private String nombre;
    private String presentacion;
    private double precio;
    private String nombreCategoria;

    public Producto()
    {
    }

    public Producto(int idProducto, String nombre, double precio)
    {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
    }
    

    public Producto(int idProducto, int idCategoria, String nombre, String presentacion, double precio)
    {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.precio = precio;
    }

    public Producto(int idProducto, int idCategoria, String nombre, String presentacion, double precio, String nombreCategoria)
    {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.precio = precio;
        this.nombreCategoria = nombreCategoria;
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

    /**
     * @return the nombreCategoria
     */
    public String getNombreCategoria()
    {
        return nombreCategoria;
    }

    /**
     * @param nombreCategoria the nombreCategoria to set
     */
    public void setNombreCategoria(String nombreCategoria)
    {
        this.nombreCategoria = nombreCategoria;
    }
}
