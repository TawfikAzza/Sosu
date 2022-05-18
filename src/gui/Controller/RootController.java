package gui.Controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import gui.utils.LoginLogoutUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {


    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXHamburger iconHamburger;
    @FXML
    private JFXDrawer drawer;

    private LoginLogoutUtil.UserType userType;

    public RootController(LoginLogoutUtil.UserType userType)
    {
        this.userType = userType;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(userType== LoginLogoutUtil.UserType.TEACHER)
        {
            initTeacher();
        }
        if(userType == LoginLogoutUtil.UserType.STUDENT)
        {
            initStudent();
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

    private void initTeacher()
    {
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("/gui/View/TeacherMenu.fxml"));
            drawer.setSidePane(vbox);
            GridPane gridPane = FXMLLoader.load(getClass().getResource("/gui/View/TeacherView.fxml"));
            mainPane.getChildren().add(gridPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initStudent()
    {
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("/gui/View/TeacherMenu.fxml"));
            drawer.setSidePane(vbox);
            TabPane tabPane = FXMLLoader.load(getClass().getResource("/gui/View/StudentMenuView.fxml"));
            mainPane.getChildren().add(tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
