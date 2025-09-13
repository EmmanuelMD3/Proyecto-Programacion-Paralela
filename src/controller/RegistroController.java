package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class RegistroController
{

    @FXML
    private TextField txtNombre, txtCorreo;
    @FXML
    private PasswordField txtContrasenia, txtConfirmar;

    @FXML
    private void handleRegistro(ActionEvent event)
    {
        // Aquí irá la lógica para guardar en la base de datos (MySQL)
        System.out.println("Nombre: " + txtNombre.getText());
        System.out.println("Correo: " + txtCorreo.getText());
        System.out.println("Contraseña: " + txtContrasenia.getText());
    }

    @FXML
    private void handleVolverLogin(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
