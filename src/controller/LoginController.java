package controller;

import dao.UsuariosDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Usuarios;

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
        String contrasenia = txtContrasenia.getText();

        Task<Usuarios> loginTask = new Task<>()
        {
            @Override
            protected Usuarios call()
            {
                return UsuariosDAO.obtenerUsuario(correo, contrasenia);
            }
        };

        loginTask.setOnSucceeded(e ->
        {
            Usuarios usuario = loginTask.getValue();
            if (usuario != null)
            {
                abrirDashboard(usuario);
            } else
            {
                mostrarAlerta("Error", "Credenciales inválidas.");
            }
        });

        new Thread(loginTask).start();
    }

    private void abrirDashboard(Usuarios usuario)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/style/dashboard.css").toExternalForm());

            DashboardController controller = loader.getController();
            controller.setUserData(usuario.getNombre(), usuario.getCorre());

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegistro(ActionEvent event
    )
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

    private void mostrarAlerta(String titulo, String mensaje
    )
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
    private void handleOlvidoContrasenia()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recuperar contraseña");
        alert.setHeaderText("Función no implementada aún");
        alert.setContentText("Aquí iría la lógica para recuperar la contraseña, como enviar un correo.");
        alert.showAndWait();
    }

}
