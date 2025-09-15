package controller;

import dao.ProductoDAO;
import java.util.List;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Producto;

public class DashboardController
{

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblCorreo;

    @FXML
    private StackPane contentArea;

    public void setUserData(String nombre, String correo)
    {
        lblNombre.setText(nombre);
        lblCorreo.setText(correo);
    }

    @FXML
    private void handleProductos()
    {
        Task<List<Producto>> task = new Task<>()
        {
            @Override
            protected List<Producto> call()
            {
                return ProductoDAO.obtenerProductos();
            }
        };

        task.setOnSucceeded(e ->
        {
            List<Producto> productos = task.getValue();
            mostrarProductos(productos);
        });

        task.setOnFailed(e ->
        {
            mostrarMensaje("Error al cargar productos.");
        });

        new Thread(task).start();
    }

    private void mostrarProductos(List<Producto> productos)
    {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        for (Producto p : productos)
        {
            HBox item = new HBox(15);
            Label nombre = new Label(p.getNombre());
            nombre.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            Label precio = new Label("$" + p.getPrecio());
            Button btnAgregar = new Button("Agregar al carrito");

            item.getChildren().addAll(nombre, precio, btnAgregar);
            vbox.getChildren().add(item);
        }
        contentArea.getChildren().setAll(vbox);
    }

    @FXML
    private void handleCarrito()
    {
        mostrarMensaje("Sección del carrito");
    }

    @FXML
    private void handleCompras()
    {
        mostrarMensaje("Sección de mis compras");
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
}
