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
                return new ArrayList<>(obtenerCarrito());
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
                synchronized (carrito)
                {
                    return carrito.stream().mapToDouble(CarritoItem::getSubtotal).sum();
                }
            }
        };
    }

    public static synchronized double aplicarDescuento(double total)
    {
        if (total > 2000)
        {
            return total * 0.85;
        } else if (total > 1000)
        {
            return total * 0.90;
        }
        return total;
    }

    public static Task<Double> aplicarDescuentoTask(double total)
    {
        return new Task<>()
        {
            @Override
            protected Double call()
            {
                return aplicarDescuento(total);
            }
        };
    }

    public static synchronized double calcularDescuentoRate(double total, int totalItems)
    {
        double r1 = (total > 500) ? 0.30 : 0.0;    // 30% si total > 500
        double r2 = (totalItems >= 3) ? 0.10 : 0.0; // 10% si >=3 items
        double r3 = (total > 200) ? 0.20 : 0.0;    // 20% si total > 200
        return Math.max(r1, Math.max(r2, r3));     // toma la mayor
    }

    public static synchronized double aplicarDescuento(double total, int totalItems)
    {
        double rate = calcularDescuentoRate(total, totalItems);
        double totalConDescuento = total * (1.0 - rate);
        return totalConDescuento;
    }

    public static Task<Double> aplicarDescuentoTask(double total, int totalItems)
    {
        return new Task<>()
        {
            @Override
            protected Double call()
            {
                double rate = calcularDescuentoRate(total, totalItems);
                return total * (1.0 - rate);
            }
        };
    }

    public static synchronized int getTotalItems()
    {
        return carrito.stream().mapToInt(CarritoItem::getCantidad).sum();
    }
}
