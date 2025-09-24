package controller;

import dao.UsuariosDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.Usuarios;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private ImageView imgPerfil;

    private File selectedFile;

    @FXML
    public void initialize()
    {
        File defaultFile = new File("imagenes/default_profile.png");
        if (defaultFile.exists())
        {
            imgPerfil.setImage(new Image(defaultFile.toURI().toString()));
        }

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
    private void handleSeleccionarFoto(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto de Perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null)
        {
            try
            {
                Image image = new Image(selectedFile.toURI().toString(), 100, 100, true, true);
                imgPerfil.setImage(image);
            } catch (Exception e)
            {
                mostrarAlerta("Error", "No se pudo cargar la imagen.");
                e.printStackTrace();
            }
        }
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

        String fotoPerfilPath = "imagenes/default_profile.png";

        if (selectedFile != null)
        {
            try
            {
                Path projectRoot = Paths.get("").toAbsolutePath();
                Path destDir = projectRoot.resolve("src/imagenes/");

                if (!Files.exists(destDir))
                {
                    Files.createDirectories(destDir);
                }

                String fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                Path destFile = destDir.resolve(fileName);
                Files.copy(selectedFile.toPath(), destFile, StandardCopyOption.REPLACE_EXISTING);

                fotoPerfilPath = "imagenes/" + fileName;
            } catch (Exception e)
            {
                mostrarAlerta("Error", "No se pudo guardar la imagen de perfil.");
                e.printStackTrace();
                return;
            }
        }

        Usuarios usuario = new Usuarios(nombre, correo, contrasenia, fotoPerfilPath);
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
        alert.showAndWait();
    }
}
