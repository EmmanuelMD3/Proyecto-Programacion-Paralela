package util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import javafx.concurrent.Task;
import model.CarritoItem;
import model.Producto;

import java.util.ArrayList;
import java.util.Date;
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
        double r1 = (total > 500) ? 0.30 : 0.0;
        double r2 = (totalItems >= 3) ? 0.10 : 0.0;
        double r3 = (total > 200) ? 0.20 : 0.0;
        return Math.max(r1, Math.max(r2, r3));
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

    public static Task<Integer> finalizarCompraTask(int idUsuario)
    {
        return new Task<>()
        {
            @Override
            protected Integer call() throws Exception
            {
                Thread.sleep(2000);

                List<CarritoItem> items = new ArrayList<>(carrito);

                double subtotal = getTotal();
                int totalItems = getTotalItems();
                double rate = calcularDescuentoRate(subtotal, totalItems);
                double total = subtotal * (1 - rate);
                double descuento = subtotal - total;

                int idVenta = dao.VentasDAO.registrarVenta(idUsuario, subtotal, descuento, total, items);

                if (idVenta > 0)
                {
                    limpiarCarrito();
                    return idVenta;
                }
                return -1;
            }
        };
    }

    public static Task<File> generarTicketTask(File file, String nombreUsuario, int idVenta, double subtotal, double descuento, double total, List<CarritoItem> items)
    {
        return new Task<File>()
        {
            @Override
            protected File call() throws Exception
            {
                // Si el archivo es nulo, el usuario canceló la operación
                if (file == null)
                {
                    return null;
                }

                try (FileOutputStream fos = new FileOutputStream(file))
                {
                    Document document = new Document(PageSize.A4, 30, 30, 30, 30);
                    PdfWriter writer = PdfWriter.getInstance(document, fos);
                    document.open();

                    // --- Estilos de Fuentes ---
                    Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
                    Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
                    Font fontHeaderTabla = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
                    Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
                    Font fontTotales = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
                    Font fontTotalFinal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new BaseColor(46, 139, 87));

                    // --- Encabezado ---
                    Paragraph pTitulo = new Paragraph("TIENDA ONLINE", fontTitulo);
                    pTitulo.setAlignment(Element.ALIGN_CENTER);
                    document.add(pTitulo);

                    Paragraph pSubtitulo = new Paragraph("TICKET DE COMPRA", fontSubtitulo);
                    pSubtitulo.setAlignment(Element.ALIGN_CENTER);
                    document.add(pSubtitulo);

                    document.add(new Paragraph(" "));

                    // --- Detalles de la venta ---
                    PdfPTable infoTable = new PdfPTable(2);
                    infoTable.setWidthPercentage(100);
                    infoTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    infoTable.addCell(new Paragraph("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), fontSubtitulo));
                    infoTable.addCell(new Paragraph("Usuario: " + nombreUsuario, fontSubtitulo));
                    infoTable.addCell(new Paragraph("ID Venta: " + idVenta, fontSubtitulo));
                    infoTable.addCell("");
                    document.add(infoTable);

                    document.add(new Paragraph(" "));

                    // --- Tabla de Productos ---
                    PdfPTable tabla = new PdfPTable(new float[]
                    {
                        3, 1, 1.5f, 1.5f
                    });
                    tabla.setWidthPercentage(100);
                    tabla.setSpacingBefore(10f);
                    tabla.setSpacingAfter(10f);

                    PdfPCell cell;
                    cell = new PdfPCell(new Paragraph("Producto", fontHeaderTabla));
                    cell.setBackgroundColor(new BaseColor(64, 64, 64));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8f);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph("Cantidad", fontHeaderTabla));
                    cell.setBackgroundColor(new BaseColor(64, 64, 64));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8f);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph("Precio Unitario", fontHeaderTabla));
                    cell.setBackgroundColor(new BaseColor(64, 64, 64));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8f);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph("Subtotal", fontHeaderTabla));
                    cell.setBackgroundColor(new BaseColor(64, 64, 64));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8f);
                    tabla.addCell(cell);

                    for (CarritoItem item : items)
                    {
                        cell = new PdfPCell(new Paragraph(item.getProducto().getNombre(), fontCelda));
                        cell.setPadding(5f);
                        cell.setBorder(Rectangle.BOTTOM);
                        tabla.addCell(cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(item.getCantidad()), fontCelda));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setPadding(5f);
                        cell.setBorder(Rectangle.BOTTOM);
                        tabla.addCell(cell);

                        cell = new PdfPCell(new Paragraph("$" + String.format("%.2f", item.getProducto().getPrecio()), fontCelda));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setPadding(5f);
                        cell.setBorder(Rectangle.BOTTOM);
                        tabla.addCell(cell);

                        cell = new PdfPCell(new Paragraph("$" + String.format("%.2f", item.getSubtotal()), fontCelda));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setPadding(5f);
                        cell.setBorder(Rectangle.BOTTOM);
                        tabla.addCell(cell);
                    }
                    document.add(tabla);

                    // --- Sección de Totales ---
                    PdfPTable totalTable = new PdfPTable(2);
                    totalTable.setWidthPercentage(100);
                    totalTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

                    totalTable.addCell(new Paragraph("Subtotal:", fontTotales));
                    PdfPCell subtotalCell = new PdfPCell(new Paragraph("$" + String.format("%.2f", subtotal), fontTotales));
                    subtotalCell.setBorder(Rectangle.NO_BORDER);
                    subtotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    totalTable.addCell(subtotalCell);

                    totalTable.addCell(new Paragraph("Descuento:", fontTotales));
                    PdfPCell descuentoCell = new PdfPCell(new Paragraph("-$" + String.format("%.2f", descuento), fontTotales));
                    descuentoCell.setBorder(Rectangle.NO_BORDER);
                    descuentoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    totalTable.addCell(descuentoCell);

                    totalTable.addCell(new Paragraph("TOTAL:", fontTotalFinal));
                    PdfPCell totalCell = new PdfPCell(new Paragraph("$" + String.format("%.2f", total), fontTotalFinal));
                    totalCell.setBorder(Rectangle.NO_BORDER);
                    totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    totalTable.addCell(totalCell);

                    document.add(totalTable);

                    // --- Pie de página ---
                    document.add(new Paragraph(" "));
                    Paragraph pFooter = new Paragraph("¡Gracias por tu compra!", fontSubtitulo);
                    pFooter.setAlignment(Element.ALIGN_CENTER);
                    document.add(pFooter);

                    document.close();
                    writer.close();
                    return file;

                } catch (Exception e)
                {
                    e.printStackTrace();
                    throw e;
                }
            }
        };
    }
}
