package gui.Controller;

import bll.exceptions.UserException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.EventHandler;
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
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class RootController implements Initializable {


    @FXML
    private GridPane mainGPane,drawerGPane;

    @FXML
    private JFXHamburger iconHamburgerDGP,iconHamburgerMGP;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView exitDGP,exitMGP;

    @FXML
    private VBox iconsBox;
    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane hidePane,closePane;

    private LoginLogoutUtil.UserType userType;
    private  List<Node>menuButtons;
    public RootController(LoginLogoutUtil.UserType userType)
    {
        this.userType = userType;
    }

    /**
     * Stack pane allows us to use panes on top of each others.
     * If you already have a pane on a stack pane, and you add another one, then the first one will serve as a background to the second one.
     * We use this property for our side menu bar and apply it to our root layout.
     * In this way, we can have our main grid  pane as in the background when the user is using the menu grid pane, and we can also hide the menu grid pane while the user is using the main pane.
     * @param location
     * @param resources
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuButtons = drawer.getSidePane();
        initUser();
        setDrawer();
        hidePane.setOpacity(0.23);
        hidePane.setVisible(false);

        closePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeDrawer();
            }
        });
    }
    public void closeDrawer()
    {
        stackPane.getChildren().get(0).toFront();
        drawer.close();

        for (Node node : menuButtons){
            node.setVisible(false);
            node.setDisable(true);
            hidePane.setVisible(false);
        }
    }
    public void setDrawer() {
        List<JFXHamburger>allHamBurgers= List.of(iconHamburgerDGP,iconHamburgerMGP);

        for (Node node : menuButtons){
            node.setVisible(false);
            node.setDisable(true);
        }

        for (JFXHamburger iconHamburger : allHamBurgers)
            iconHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                stackPane.getChildren().get(0).toFront();
                if(drawer.isOpened()) {
                    drawer.close();
                    for (Node node : menuButtons){
                        node.setVisible(false);
                        node.setDisable(true);
                        hidePane.setVisible(false);
                    }
                }
                else {
                    drawer.open();
                    for (Node node : menuButtons){
                        node.setVisible(true);
                        node.setDisable(false);
                        hidePane.setVisible(true);
                    }
                }
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/TeacherMenu.fxml"));
            MenuController menuController = new TeacherMenuController(mainPane,hidePane);
            loader.setController(menuController);
            TeacherMenuController teacherMenuController = loader.getController();
            teacherMenuController.setRootController(this);
            GridPane gridPane = FXMLLoader.load(getClass().getResource("/gui/View/CitizenAssignmentView.fxml"));
            setInitialScene(loader, gridPane);
            gridPane.setLayoutX(40);
            gridPane.setLayoutY(26);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initStudent()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/StudentMenuView.fxml"));
            MenuController menuController = new StudentMenuController(mainPane);
            loader.setController(menuController);
            GridPane gridPane = FXMLLoader.load(getClass().getResource("../View/StudentView.fxml"));
            setInitialScene(loader,gridPane);
            gridPane.setLayoutX(80);
            gridPane.setLayoutY(26);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAdmin()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/AdminMenuView.fxml"));
            MenuController menuController = new AdminMenuViewController(mainPane);
            loader.setController(menuController);
            AdminMenuViewController adminMenuViewController = loader.getController();
            adminMenuViewController.setRootController(this);
            adminMenuViewController.setHidePane(hidePane);
            FXMLLoader teacherLoader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
            ManageUsersController manageUsersController = new ManageUsersController(LoginLogoutUtil.UserType.ADMIN);
            teacherLoader.setController(manageUsersController);
            GridPane gridPane = teacherLoader.load();
            gridPane.setLayoutX(60);
            gridPane.setLayoutY(26);

            setInitialScene(loader,gridPane);
        } catch (IOException | UserException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setInitialScene(FXMLLoader menu, Node scene)
    {
        try {
            menu.load();
            MenuController menuController = menu.getController();
            menuController.setHidePane(hidePane);

            drawer.setSidePane(menuController.getBtnBox());
            iconsBox.getChildren().add(menuController.getIconBox());
            mainPane.getChildren().clear();
            mainPane.getChildren().add(scene);
            mainPane.getChildren().add(hidePane);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }
}
