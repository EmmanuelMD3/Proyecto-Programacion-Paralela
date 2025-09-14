package controller;

import dao.UsuariosDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import model.Usuarios;

public class RegistroController
{

    @FXML
    private TextField txtNombre, txtCorreo;
    @FXML
    private PasswordField txtContrasenia, txtConfirmar;

    @FXML
    private void handleRegistro(ActionEvent event)
    {
        //Thread hilo = new Thread(() ->
        //{
            System.out.println("Nombre: " + txtNombre.getText());
            System.out.println("Correo: " + txtCorreo.getText());
            System.out.println("Contraseña: " + txtContrasenia.getText());

            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String contrasenia = txtContrasenia.getText();

            Usuarios usuario = new Usuarios(nombre, correo, contrasenia);

            UsuariosDAO.agregarUsuarios(usuario);

            mostrarAlerta("Usuario Registrado", "Usuario Registrado Exitosamente");
        //});
    }

    @FXML
    private void handleVolverLogin(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());

            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
