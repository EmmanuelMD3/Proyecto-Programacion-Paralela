package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

public class DashboardController
{

    @FXML
    private VBox sideMenu;

    // Mostrar/Ocultar menú lateral
    @FXML
    private void toggleMenu(ActionEvent event)
    {
        boolean visible = sideMenu.isVisible();
        sideMenu.setVisible(!visible);
        sideMenu.setManaged(!visible); 
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());

            Stage stage = (Stage) sideMenu.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
