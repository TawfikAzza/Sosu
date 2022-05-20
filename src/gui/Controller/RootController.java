package gui.Controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import gui.utils.DisplayMessage;
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

    private int index=0;

    private LoginLogoutUtil.UserType userType;

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
        initUser();
        List<JFXHamburger>allHamBurgers= List.of(iconHamburgerDGP,iconHamburgerMGP);
        for (JFXHamburger iconHamburger : allHamBurgers)
        iconHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            if(drawer.isOpened()) {
                /**
                 * This part is the most important as it illustrates how we are going to use the stack pane.
                 * When the drawer is open, it means our menu should be on top of the stack pane and the main pane is in the background.
                 * When a user decides to close it, we need them to switch positions.To achieve this, we create an intermediate grid pane.
                 */
                GridPane gridPane = new GridPane();
                gridPane.getChildren().add(mainGPane);
                stackPane.getChildren().add(gridPane);
                if (index > 1)
                    stackPane.getChildren().remove(0);
                drawer.close();
                index++;
            }
                /**
                 * Important:
                 * When we first load the fxml file associated to this controller, we have the main grid pane on top of the stack pane and the menu is hidden,
                 * If the user decides to use the side menu bar, then we will need to take out the menu grid pane from the bottom and put it on top,
                 * all we have to do is create a copy of that pane add it to the list of children nodes of the stack pane and delete the old grid pane menu
                 * in order to clean that list, so we always have only  2 items.
                 */
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
            MenuController menuController = new TeacherMenuController(mainPane);
            loader.setController(menuController);
            GridPane gridPane = FXMLLoader.load(getClass().getResource("/gui/View/CitizenAssignmentView.fxml"));
            setInitialScene(loader, gridPane);
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
            TabPane tabPane = FXMLLoader.load(getClass().getResource("../View/StudentView.fxml"));
            setInitialScene(loader,tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAdmin()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminMenuView.fxml"));
            MenuController menuController = new AdminMenuViewController(mainPane);
            loader.setController(menuController);
            TabPane tabPane = FXMLLoader.load(getClass().getResource("/gui/View/AdminView.fxml"));
            setInitialScene(loader, tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInitialScene(FXMLLoader menu, Node scene)
    {
        try {
            menu.load();
            MenuController menuController = menu.getController();

            drawer.setSidePane(menuController.getBtnBox());
            iconsBox.getChildren().add(menuController.getIconBox());

            mainPane.getChildren().clear();
            mainPane.getChildren().add(scene);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }
}
