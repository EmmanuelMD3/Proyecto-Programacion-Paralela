package controller;

import dao.UsuariosDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.Usuarios;

public class RegistroController
{

    @FXML
    private TextField txtNombre, txtCorreo;

    @FXML
    private PasswordField txtContrasenia, txtConfirmar;

    @FXML
    private TextField txtContraseniaVisible, txtConfirmarVisible;

    @FXML
    private CheckBox chkMostrarContrasenia, chkMostrarConfirmar;

    @FXML
    public void initialize()
    {
        txtContraseniaVisible.textProperty().bindBidirectional(txtContrasenia.textProperty());

        chkMostrarContrasenia.selectedProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal)
            {
                txtContraseniaVisible.setVisible(true);
                txtContraseniaVisible.setManaged(true);
                txtContrasenia.setVisible(false);
                txtContrasenia.setManaged(false);
            } else
            {
                txtContrasenia.setVisible(true);
                txtContrasenia.setManaged(true);
                txtContraseniaVisible.setVisible(false);
                txtContraseniaVisible.setManaged(false);
            }
        });

        txtConfirmarVisible.textProperty().bindBidirectional(txtConfirmar.textProperty());

        chkMostrarConfirmar.selectedProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal)
            {
                txtConfirmarVisible.setVisible(true);
                txtConfirmarVisible.setManaged(true);
                txtConfirmar.setVisible(false);
                txtConfirmar.setManaged(false);
            } else
            {
                txtConfirmar.setVisible(true);
                txtConfirmar.setManaged(true);
                txtConfirmarVisible.setVisible(false);
                txtConfirmarVisible.setManaged(false);
            }
        });
    }

    @FXML
    private void handleRegistro(ActionEvent event)
    {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String contrasenia = txtContrasenia.getText();
        String confirmar = txtConfirmar.getText();

        if (!contrasenia.equals(confirmar))
        {
            mostrarAlerta("Error", "Las contraseñas no coinciden.");
            return;
        }

        Usuarios usuario = new Usuarios(nombre, correo, contrasenia);
        UsuariosDAO.agregarUsuarios(usuario);

        mostrarAlerta("Usuario Registrado", "Usuario registrado exitosamente");
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
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/style/alert.css").toExternalForm());

        alert.showAndWait();
    }

}
