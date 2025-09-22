package util;

import model.Usuarios;

public class SessionManager
{

    private static Usuarios usuarioActual;

    public static void setUsuarioActual(Usuarios usuario)
    {
        usuarioActual = usuario;
    }

    public static Usuarios getUsuarioActual()
    {
        return usuarioActual;
    }

    public static void cerrarSesion()
    {
        usuarioActual = null;
    }
}
