package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController
{

    @FXML
    private VBox rootContainer;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtContraseniaVisible;

    @FXML
    private CheckBox chkMostrarContrasenia;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegistro;

    @FXML
    private Hyperlink linkOlvido;

    @FXML
    private void initialize()
    {
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), rootContainer);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        btnLogin.setOnMouseEntered(e ->
        {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), btnLogin);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        btnLogin.setOnMouseExited(e ->
        {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), btnLogin);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });

        chkMostrarContrasenia.selectedProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal)
            {
                txtContraseniaVisible.setText(txtContrasenia.getText());
                txtContraseniaVisible.setVisible(true);
                txtContraseniaVisible.setManaged(true);
                txtContrasenia.setVisible(false);
                txtContrasenia.setManaged(false);
            } else
            {
                txtContrasenia.setText(txtContraseniaVisible.getText());
                txtContrasenia.setVisible(true);
                txtContrasenia.setManaged(true);
                txtContraseniaVisible.setVisible(false);
                txtContraseniaVisible.setManaged(false);
            }
        });
    }

    @FXML
    private void handleLogin(ActionEvent event)
    {
        String correo = txtCorreo.getText();
        String contrasenia = chkMostrarContrasenia.isSelected()
                ? txtContraseniaVisible.getText()
                : txtContrasenia.getText();

        if (correo.isEmpty() || contrasenia.isEmpty())
        {
            mostrarAlerta("Error", "Debe ingresar usuario y contraseña");
            return;
        }

        if (correo.equals("admin@uaemex.mx") && contrasenia.equals("1234"))
        {
            mostrarAlerta("Bienvenido", "Inicio de sesión correcto");
        } else
        {
            mostrarAlerta("Error", "Credenciales inválidas");
        }
    }

    @FXML
    private void handleRegistro(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registro.fxml"));
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());
            
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setTitle("Crear Cuenta");
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOlvidoContrasenia(ActionEvent event)
    {
        mostrarAlerta("Recuperar contraseña", "Se ha enviado un enlace de recuperación a su correo.");
    }

    private void mostrarAlerta(String titulo, String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
