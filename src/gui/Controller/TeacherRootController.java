package gui.Controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherRootController implements Initializable {


    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXHamburger iconHamburger;
    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("/gui/View/TeacherMenu.fxml"));
            drawer.setSidePane(vbox);
            GridPane gridPane = FXMLLoader.load(getClass().getResource("/gui/View/TeacherView.fxml"));
            mainPane.getChildren().add(gridPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        iconHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            if(drawer.isOpened())
            {
                drawer.close();
            }
            else
            {
                drawer.open();
            }
        });
    }
}
