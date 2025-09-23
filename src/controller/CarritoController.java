package controller;

import dao.VentasDAO;
import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CarritoItem;
import model.Usuarios;
import util.CarritoManager;

public class CarritoController
{

    private static CarritoController instance;

    public CarritoController()
    {
        instance = this;
    }

    public static CarritoController getInstance()
    {
        return instance;
    }

    @FXML
    private TableView<CarritoItem> tablaCarrito;
    @FXML
    private TableColumn<CarritoItem, String> colNombre;
    @FXML
    private TableColumn<CarritoItem, Integer> colCantidad;
    @FXML
    private TableColumn<CarritoItem, Double> colPrecio;
    @FXML
    private TableColumn<CarritoItem, Double> colSubtotal;
    @FXML
    private Label lblDescuento;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnVaciar, btnFinalizar;

    private ObservableList<CarritoItem> carritoObservable;

    @FXML
    public void initialize()
    {
        System.out.println("lblTotal es: " + lblTotal);

        tablaCarrito.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        lblTotal.setText("$0.00");
        lblDescuento.setText("$0.00");
        carritoObservable = FXCollections.observableArrayList(CarritoManager.obtenerCarrito());

        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getProducto().getNombre()));

        colCantidad.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(
                data.getValue().getCantidad()).asObject());

        colPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(
                data.getValue().getProducto().getPrecio()).asObject());

        colSubtotal.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(
                data.getValue().getSubtotal()).asObject());

        tablaCarrito.setItems(carritoObservable);

        actualizarTotalConDescuentoAuto();

        btnVaciar.setOnAction(e ->
        {
            CarritoManager.limpiarCarrito();
            carritoObservable.clear();
            actualizarTotal();
        });

//        btnFinalizar.setOnAction(e ->
//        {
//            mostrarAlerta("Compra realizada", "¡Gracias por tu compra!");
//            CarritoManager.limpiarCarrito();
//            carritoObservable.clear();
//            actualizarTotal();
//        });
    }

    private void actualizarTotal()
    {
        Task<Double> task = CarritoManager.getTotalTask();

        task.setOnSucceeded(e ->
        {
            Double total = task.getValue();
            lblTotal.setText("$" + String.format("%.2f", total));
        });

        task.setOnFailed(e ->
        {
            lblTotal.setText("Error al calcular total");
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }


    public void actualizarTablaDesdeCarrito()
    {
        Task<List<CarritoItem>> t = CarritoManager.obtenerCarritoTask();
        t.setOnSucceeded(ev ->
        {
            List<CarritoItem> lista = t.getValue();
            Platform.runLater(() ->
            {
                tablaCarrito.getItems().setAll(lista);
            });
        });
        t.setOnFailed(ev -> t.getException().printStackTrace());
        new Thread(t).start();
    }

    public void actualizarTotalConDescuentoAuto()
    {
        Task<Double> totalTask = CarritoManager.getTotalTask();

        totalTask.setOnSucceeded(e ->
        {
            double total = totalTask.getValue();
            int totalItems = CarritoManager.getTotalItems();

            double rate = CarritoManager.calcularDescuentoRate(total, totalItems);
            double discountPercent = rate * 100.0;

            Task<Double> descuentoTask = CarritoManager.aplicarDescuentoTask(total, totalItems);

            descuentoTask.setOnSucceeded(ev ->
            {
                double totalConDescuento = descuentoTask.getValue();
                double descuentoImporte = total - totalConDescuento;

                javafx.application.Platform.runLater(() ->
                {
                    if (rate > 0.0)
                    {
                        lblDescuento.setText(String.format("Descuento: %.0f%%   (-$%.2f)", discountPercent, descuentoImporte));
                        lblDescuento.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");

                    } else
                    {
                        lblDescuento.setText("");
                    }

                    lblTotal.setText("$" + String.format("%.2f", totalConDescuento));
                });

                actualizarTablaDesdeCarrito();
                System.out.println("[H4] total=" + total + " [H5] totalConDescuento=" + totalConDescuento + " items=" + totalItems);
            });

            descuentoTask.setOnFailed(ev ->
            {
                descuentoTask.getException().printStackTrace();
            });

            new Thread(descuentoTask).start();
        });

        totalTask.setOnFailed(e ->
        {
            totalTask.getException().printStackTrace();
        });

        new Thread(totalTask).start();
    }

    @FXML
    private void handleVaciar()
    {
        Task<Void> t = CarritoManager.limpiarCarritoTask();
        t.setOnSucceeded(e ->
        {
            actualizarTablaDesdeCarrito();
            actualizarTotalConDescuentoAuto();
        });
        new Thread(t).start();
    }

    @FXML
    private void mostrarAlerta(String titulo, String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/style/alert.css").toExternalForm());

        alert.showAndWait();
    }

    @FXML
    private void handleFinalizar(ActionEvent event)
    {
        Usuarios usuario = util.SessionManager.getUsuarioActual();

        if (usuario == null)
        {
            mostrarAlerta("Error", "No hay usuario en sesión.");
            return;
        }

        Task<Integer> finalizarCompraTask = CarritoManager.finalizarCompraTask(usuario.getIdUsuario());

        finalizarCompraTask.setOnSucceeded(e ->
        {
            int idVentaGenerada = finalizarCompraTask.getValue();

            if (idVentaGenerada > 0)
            {
                Platform.runLater(() ->
                {
//                    Alert a = new Alert(Alert.AlertType.INFORMATION);
//                    a.setTitle("Compra finalizada");
//                    a.setHeaderText("Pago exitoso");
//                    a.setContentText("Tu compra se registró en el sistema.");
//                    a.showAndWait();
                    mostrarAlerta("Compra finalizada", "Pago exitoso\nTu compra se registró en el sistema.");
                });

                List<CarritoItem> itemsVenta = dao.VentasDAO.obtenerDetallesPorVenta(idVentaGenerada);
                double subtotal = itemsVenta.stream().mapToDouble(CarritoItem::getSubtotal).sum();
                int totalItems = itemsVenta.stream().mapToInt(CarritoItem::getCantidad).sum();
                double totalConDesc = CarritoManager.aplicarDescuento(subtotal, totalItems);
                double descuento = subtotal - totalConDesc;
                String nombre = dao.UsuariosDAO.obtenerNombreUsuarioPorId(usuario.getIdUsuario());

                String userHome = System.getProperty("user.home");
                File downloadsDir = new File(userHome, "Downloads");
                if (!downloadsDir.exists())
                {
                    downloadsDir.mkdirs();
                }
                File ticketFile = new File(downloadsDir, "Ticket_Venta_" + idVentaGenerada + ".pdf");

                Task<File> generarTicketTask = CarritoManager.generarTicketTask(
                        ticketFile,
                        nombre,
                        idVentaGenerada,
                        subtotal,
                        descuento,
                        totalConDesc,
                        itemsVenta
                );

                generarTicketTask.setOnSucceeded(ev ->
                {
                    File ticket = generarTicketTask.getValue();
                    if (ticket != null && ticket.exists())
                    {
                        Platform.runLater(() ->
                        {
                            try
                            {
                                if (Desktop.isDesktopSupported())
                                {
                                    Desktop.getDesktop().open(ticket);
                                }
                            } catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        });
                    }
                });

                generarTicketTask.setOnFailed(ev ->
                {
                    generarTicketTask.getException().printStackTrace();
                    Platform.runLater(() ->
                    {
                        mostrarAlerta("Error al generar PDF", "No se pudo crear el archivo del ticket.");
                    });
                });

                new Thread(generarTicketTask).start();

                Platform.runLater(() ->
                {
                    actualizarTablaDesdeCarrito();
                    actualizarTotalConDescuentoAuto();
                });
            } else
            {
                Platform.runLater(() ->
                {
                    mostrarAlerta("Error", "No se pudo registrar la compra.");
                });
            }
        });

        finalizarCompraTask.setOnFailed(e ->
        {
            finalizarCompraTask.getException().printStackTrace();
            Platform.runLater(() ->
            {
                mostrarAlerta("Error de conexión", "No se pudo completar la compra. Revisa tu conexión.");
            });
        });

        new Thread(finalizarCompraTask).start();
    }
}
