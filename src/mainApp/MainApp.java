/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mainApp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author chemo
 */
public class MainApp extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());
            stage.setTitle("Tienda Online");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
