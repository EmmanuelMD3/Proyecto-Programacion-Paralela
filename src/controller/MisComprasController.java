package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.beans.property.*;
import model.CarritoItem;
import model.Ventas;
import util.SessionManager;
import dao.VentasDAO;

import java.util.List;

public class MisComprasController
{

    @FXML
    private TableView<Ventas> tablaCompras;
    @FXML
    private TableColumn<Ventas, Integer> colIdVenta;
    @FXML
    private TableColumn<Ventas, String> colFecha;
    @FXML
    private TableColumn<Ventas, Double> colVentaSubtotal;
    @FXML
    private TableColumn<Ventas, Double> colVentaDescuento;
    @FXML
    private TableColumn<Ventas, Double> colVentaTotal;

    @FXML
    private TableView<CarritoItem> tablaDetalles;
    @FXML
    private TableColumn<CarritoItem, String> colDetalleProducto;
    @FXML
    private TableColumn<CarritoItem, Integer> colDetalleCantidad;
    @FXML
    private TableColumn<CarritoItem, Double> colDetallePrecio;
    @FXML
    private TableColumn<CarritoItem, Double> colDetalleSubtotal;

    @FXML
    public void initialize()
    {
        tablaCompras.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaDetalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colIdVenta.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getIdVenta()).asObject());
        colFecha.setCellValueFactory(d ->
        {
            return new SimpleStringProperty(d.getValue().getFecha() == null ? "" : d.getValue().getFecha().toString());
        });
        colVentaSubtotal.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getSubtotal()).asObject());
        colVentaDescuento.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getDescuento()).asObject());
        colVentaTotal.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getTotal()).asObject());

        colDetalleProducto.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getProducto().getNombre()));
        colDetalleCantidad.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getCantidad()).asObject());
        colDetallePrecio.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getProducto().getPrecio()).asObject());
        colDetalleSubtotal.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getSubtotal()).asObject());

        if (SessionManager.getUsuarioActual() == null)
        {
            tablaCompras.setItems(FXCollections.observableArrayList());
            tablaDetalles.setItems(FXCollections.observableArrayList());
            System.err.println("MisComprasController: no hay usuario en session.");
            return;
        }

        int idUsuario = SessionManager.getUsuarioActual().getIdUsuario();
        List<Ventas> ventas = VentasDAO.obtenerComprasPorUsuario(idUsuario);
        ObservableList<Ventas> compras = FXCollections.observableArrayList(ventas);
        tablaCompras.setItems(compras);

        tablaCompras.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, nuevaVenta) ->
        {
            if (nuevaVenta != null)
            {
                List<CarritoItem> detalles = VentasDAO.obtenerDetallesPorVenta(nuevaVenta.getIdVenta());
                tablaDetalles.setItems(FXCollections.observableArrayList(detalles));
            } else
            {
                tablaDetalles.getItems().clear();
            }
        });
    }
}
