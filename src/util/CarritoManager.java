package util;

import model.CarritoItem;
import model.Producto;

import java.util.ArrayList;
import java.util.List;

public class CarritoManager
{

    private static final List<CarritoItem> carrito = new ArrayList<>();

    public static synchronized void agregarProducto(Producto producto)
    {
        for (CarritoItem item : carrito)
        {
            if (item.getProducto().getIdProducto() == producto.getIdProducto())
            {
                item.setCantidad(item.getCantidad() + 1);
                return;
            }
        }
        carrito.add(new CarritoItem(producto, 1));
    }

    public static synchronized List<CarritoItem> obtenerCarrito()
    {
        return carrito;
    }

    public static synchronized void actualizarCantidad(int idProducto, int cantidad)
    {
        carrito.removeIf(item ->
        {
            if (item.getProducto().getIdProducto() == idProducto && cantidad <= 0)
            {
                return true;
            }
            return false;
        });
        for (CarritoItem item : carrito)
        {
            if (item.getProducto().getIdProducto() == idProducto)
            {
                item.setCantidad(cantidad);
                return;
            }
        }
    }

    public static synchronized void removerProducto(int idProducto)
    {
        carrito.removeIf(item -> item.getProducto().getIdProducto() == idProducto);
    }

    public static synchronized void limpiarCarrito()
    {
        carrito.clear();
    }

    public static synchronized double getTotal()
    {
        return carrito.stream().mapToDouble(CarritoItem::getSubtotal).sum();
    }
}
