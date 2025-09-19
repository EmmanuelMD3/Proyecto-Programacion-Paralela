package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CarritoItem;
import util.CarritoManager;

public class CarritoController
{

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
    private Label lblTotal;
    @FXML
    private Button btnVaciar, btnFinalizar;

    private ObservableList<CarritoItem> carritoObservable;

    @FXML
    public void initialize()
    {
        tablaCarrito.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        lblTotal.setText("$0.00");
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

        actualizarTotal();

        btnVaciar.setOnAction(e ->
        {
            CarritoManager.limpiarCarrito();
            carritoObservable.clear();
            actualizarTotal();
        });

        btnFinalizar.setOnAction(e ->
        {
            mostrarAlerta("Compra realizada", "¡Gracias por tu compra!");
            CarritoManager.limpiarCarrito();
            carritoObservable.clear();
            actualizarTotal();
        });
    }

    private void actualizarTotal()
    {
        lblTotal.setText("$" + String.format("%.2f", CarritoManager.getTotal()));
    }

    private void mostrarAlerta(String titulo, String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
