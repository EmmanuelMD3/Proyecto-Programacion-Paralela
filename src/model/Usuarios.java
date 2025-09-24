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
public class Usuarios
{

    private int idUsuario;
    private String nombre;
    private String corre;
    private String contrasenia;
    private Date creado_en;
    private String fotoPerfil;

    public Usuarios(int idUsuario, String nombre, String corre, String contrasenia, String fotoPerfil)
    {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.corre = corre;
        this.contrasenia = contrasenia;
        this.fotoPerfil = fotoPerfil;
    }

    public Usuarios(int idUsuario, String nombre, String corre, String contrasenia, Date creado_en, String fotoPerfil)
    {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.corre = corre;
        this.contrasenia = contrasenia;
        this.creado_en = creado_en;
        this.fotoPerfil = fotoPerfil;
    }

    public Usuarios(String nombre, String corre, String contrasenia, String fotoPerfil)
    {
        this.nombre = nombre;
        this.corre = corre;
        this.contrasenia = contrasenia;
        this.fotoPerfil = fotoPerfil;
    }

    public Usuarios(int idUsuario, String nombre, String corre, String contrasenia)
    {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.corre = corre;
        this.contrasenia = contrasenia;
    }

    public Usuarios(String nombre, String corre, String contrasenia)
    {
        this.nombre = nombre;
        this.corre = corre;
        this.contrasenia = contrasenia;
    }

    public String getFotoPerfil()
    {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil)
    {
        this.fotoPerfil = fotoPerfil;
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
     * @return the corre
     */
    public String getCorre()
    {
        return corre;
    }

    /**
     * @param corre the corre to set
     */
    public void setCorre(String corre)
    {
        this.corre = corre;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia()
    {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the creado_en
     */
    public Date getCreado_en()
    {
        return creado_en;
    }

    /**
     * @param creado_en the creado_en to set
     */
    public void setCreado_en(Date creado_en)
    {
        this.creado_en = creado_en;
    }

}
