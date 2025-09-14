package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MenuController
{

    @FXML
    private void handleCerrarSesion(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
