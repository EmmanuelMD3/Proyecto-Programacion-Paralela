/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author chemo
 */
public class Categorias
{
    private int idCategoria;
    private String nombre;

    public Categorias()
    {
    }

    public Categorias(int idCategoria, String nombre)
    {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
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
}
