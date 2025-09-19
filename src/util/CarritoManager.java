package util;

import javafx.concurrent.Task;
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
        carrito.removeIf(item
                -> item.getProducto().getIdProducto() == idProducto && cantidad <= 0
        );
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

    public static Task<Void> agregarProductoTask(Producto producto)
    {
        return new Task<>()
        {
            @Override
            protected Void call()
            {
                agregarProducto(producto);
                return null;
            }
        };
    }

    public static Task<List<CarritoItem>> obtenerCarritoTask()
    {
        return new Task<>()
        {
            @Override
            protected List<CarritoItem> call()
            {
                return new ArrayList<>(obtenerCarrito()); // copia segura
            }
        };
    }

    public static Task<Void> actualizarCantidadTask(int idProducto, int cantidad)
    {
        return new Task<>()
        {
            @Override
            protected Void call()
            {
                actualizarCantidad(idProducto, cantidad);
                return null;
            }
        };
    }

    public static Task<Void> removerProductoTask(int idProducto)
    {
        return new Task<>()
        {
            @Override
            protected Void call()
            {
                removerProducto(idProducto);
                return null;
            }
        };
    }

    public static Task<Void> limpiarCarritoTask()
    {
        return new Task<>()
        {
            @Override
            protected Void call()
            {
                limpiarCarrito();
                return null;
            }
        };
    }

    public static Task<Double> getTotalTask()
    {
        return new Task<>()
        {
            @Override
            protected Double call()
            {
                return getTotal();
            }
        };
    }
}
