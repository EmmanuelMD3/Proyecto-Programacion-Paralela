package controller;

import dao.ProductoDAO;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.CarritoItem;
import model.Producto;
import util.CarritoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import model.CarritoItem;
import model.Producto;
import util.CarritoManager;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Node;

public class DashboardController
{

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblCorreo;

    @FXML
    private StackPane contentArea;

    private Label lblTotalLocal;

    public void setUserData(String nombre, String correo)
    {
        lblNombre.setText(nombre);
        lblCorreo.setText(correo);
    }

    private void cargarProductos()
    {
        Map<String, List<Producto>> data = ProductoDAO.obtenerProductosAgrupadosPorCategoria();
        mostrarProductosPorCategoria(data);
    }

    @FXML
    private void handleProductos()
    {
        Task<Map<String, List<Producto>>> task = new Task<>()
        {
            @Override
            protected Map<String, List<Producto>> call()
            {
                return ProductoDAO.obtenerProductosAgrupadosPorCategoria();
            }
        };

        task.setOnSucceeded(e ->
        {
            Map<String, List<Producto>> data = task.getValue();
            mostrarProductosPorCategoria(data);
        });

        task.setOnFailed(e ->
        {
            mostrarMensaje("Error al cargar productos.");
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    private void mostrarProductosPorCategoria(Map<String, List<Producto>> data)
    {
        VBox root = new VBox(25);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f4f6f9;");

        for (Map.Entry<String, List<Producto>> entry : data.entrySet())
        {
            String categoria = entry.getKey();
            List<Producto> productos = entry.getValue();

            Label lblCat = new Label(categoria);
            lblCat.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            FlowPane flow = new FlowPane();
            flow.setHgap(20);
            flow.setVgap(20);
            flow.setPrefWrapLength(800);

            for (Producto p : productos)
            {
                VBox card = new VBox(10);
                card.setStyle(
                        "-fx-background-color: white;"
                        + "-fx-padding: 15;"
                        + "-fx-background-radius: 12;"
                        + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 6,0,0,3);"
                );
                card.setPrefWidth(200);

                Label nombre = new Label(p.getNombre());
                nombre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");

                Label desc = new Label(p.getPresentacion() != null ? p.getPresentacion() : "Sin descripción");
                desc.setWrapText(true);
                desc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");

                Label precio = new Label("$" + String.format("%.2f", p.getPrecio()));
                precio.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 14px;");

                Button btnAgregar = new Button("Agregar al carrito");
                btnAgregar.setStyle(
                        "-fx-background-color: #2c3e50;"
                        + "-fx-text-fill: white;"
                        + "-fx-background-radius: 6;"
                        + "-fx-cursor: hand;"
                );

                btnAgregar.setOnAction(e ->
                {
                    Task<Void> t = CarritoManager.agregarProductoTask(p);

                    t.setOnSucceeded(ev ->
                    {
                        Platform.runLater(() -> mostrarMensaje("Producto agregado: " + p.getNombre()));

                        if (CarritoController.getInstance() != null)
                        {
                            CarritoController.getInstance().actualizarTotalConDescuentoAuto();
                        } else
                        {

                            System.out.println("CarritoController no disponible aún. Actualiza total en próxima apertura.");
                        }
                    });

                    t.setOnFailed(ev ->
                    {
                        t.getException().printStackTrace();
                        Platform.runLater(() -> mostrarMensaje("Error al agregar el producto"));
                    });

                    new Thread(t).start();
                });

                card.getChildren().addAll(nombre, desc, precio, btnAgregar);
                flow.getChildren().add(card);
            }

            root.getChildren().addAll(lblCat, flow);
        }

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent;");

        contentArea.getChildren().setAll(scrollPane);

        contentArea.getChildren().setAll(root);
    }

    @FXML
    private void handleCarrito()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/carrito.fxml"));
            Parent root = loader.load();
            contentArea.getChildren().setAll(root);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCompras()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/compras.fxml"));
            Parent root = loader.load();

            MisComprasController controller = loader.getController();

            contentArea.getChildren().setAll(root); 
        } catch (IOException e)
        {
            e.printStackTrace();
            mostrarMensaje("Error al cargar la sección de compras.");
        }
    }

    @FXML
    private void handleCerrarSesion()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());

            Stage stage = (Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dashboard");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarCarrito()
    {
        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 20;");

        Label titulo = new Label("Mi carrito");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        for (CarritoItem item : CarritoManager.obtenerCarrito())
        {
            HBox fila = new HBox(20);

            Label nombre = new Label(item.getProducto().getNombre());
            Label cantidad = new Label("Cantidad: " + item.getCantidad());
            Label subtotal = new Label("Subtotal: $" + String.format("%.2f", item.getSubtotal()));

            fila.getChildren().addAll(nombre, cantidad, subtotal);
            root.getChildren().add(fila);
        }

        Label total = new Label("Total: $" + String.format("%.2f", CarritoManager.getTotal()));
        total.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #27ae60;");

        root.getChildren().add(total);

        contentArea.getChildren().setAll(root);
    }

}
