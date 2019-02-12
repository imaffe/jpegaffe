package com.affe.view;

import com.affe.controller.FXMLController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;


public class MainApp extends Application {
    Scene scene;

    public final static Logger logger = Logger.getLogger("jpegaffe");

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
            root = (Parent)loader.load();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        // get logger

        // set up controller context
        FXMLController controller = (FXMLController) loader.getController();
        controller.setStage(stage);
        controller.setLogger(logger);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);

        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
