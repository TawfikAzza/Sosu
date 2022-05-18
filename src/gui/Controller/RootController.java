package gui.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import gui.utils.LoginLogoutUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RootController implements Initializable {


    @FXML
    private GridPane mainGPane;
    @FXML
    private JFXHamburger iconHamburgerMGP;
    @FXML
    private GridPane drawerGPane;
    @FXML
    private JFXHamburger iconHamburgerDGP;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView exitDGP;
    @FXML
    private ImageView exitMGP;
    @FXML
    private VBox iconsBox;
    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXDrawer drawer;

    private int index=0;

    private LoginLogoutUtil.UserType userType;

    public RootController(LoginLogoutUtil.UserType userType)
    {
        this.userType = userType;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUser();
        List<JFXHamburger>allHamBurgers= List.of(iconHamburgerDGP,iconHamburgerMGP);
        for (JFXHamburger iconHamburger : allHamBurgers)
        iconHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            if(drawer.isOpened()) {
                GridPane gridPane = new GridPane();
                gridPane.getChildren().add(mainGPane);
                stackPane.getChildren().add(gridPane);
                if (index>1)
                    stackPane.getChildren().remove(0);
                drawer.close();
                index++;

            }
            else {
                GridPane gridPane = new GridPane();
                gridPane.getChildren().add(drawerGPane);
                stackPane.getChildren().add(gridPane);
                if (index>0)
                    stackPane.getChildren().remove(0);
                drawer.open();
                index++;
            }
            /*int counter = 0;
            for (Node node : stackPane.getChildren()){
                counter++;
                System.out.println(node+" -----"+counter);
            }*/
        });

        List<ImageView>allExitIV= List.of(exitDGP,exitMGP);
        for (ImageView imageView :allExitIV){
            imageView.setOnMouseClicked(e->{
                System.exit(1);
            });
        }
    }

    private void initUser()
    {
        if(userType== LoginLogoutUtil.UserType.TEACHER)
        {
            initTeacher();
        }
        if(userType == LoginLogoutUtil.UserType.STUDENT)
        {
            initStudent();
        }
        if(userType== LoginLogoutUtil.UserType.ADMIN)
        {
            initAdmin();
        }
    }

    private void initTeacher()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/TeacherMenu.fxml"));
            loader.load();
            TeacherMenuController teacherMenuController = loader.getController();

            drawer.setSidePane(teacherMenuController.getBtnBox());
            iconsBox.getChildren().add(teacherMenuController.getIconBox());

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

    private void initAdmin()
    {
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("/gui/View/TeacherMenu.fxml"));
            drawer.setSidePane(vbox);
            TabPane tabPane = FXMLLoader.load(getClass().getResource("/gui/View/AdminView.fxml"));
            mainPane.getChildren().add(tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
