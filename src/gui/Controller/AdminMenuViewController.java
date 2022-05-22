package gui.Controller;

import bll.exceptions.UserException;
import com.jfoenix.controls.JFXDrawer;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdminMenuViewController extends MenuController{
    @FXML
    private VBox btnBox,iconBox;
    private AnchorPane anchorPane;
    private RootController rootController;
    public AdminMenuViewController(AnchorPane mainPane) {
        super(mainPane);
        this.anchorPane = mainPane;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    public VBox getBtnBox() {
        return btnBox;
    }

    public VBox getIconBox() {
        return iconBox;
    }

    public void handleSchoolsBtn(ActionEvent actionEvent){}

    public void handleLogOutBtn(ActionEvent actionEvent){}

    public void handleAdminBtn(ActionEvent actionEvent) throws IOException, UserException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
        loader.setController(new ManageUsersController(LoginLogoutUtil.UserType.ADMIN));
        anchorPane.getChildren().setAll((Node) loader.load());
        rootController.closeDrawer();
    }

    public void handleStudentBtn(ActionEvent actionEvent) throws IOException, UserException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
        loader.setController(new ManageUsersController(LoginLogoutUtil.UserType.STUDENT));
        anchorPane.getChildren().setAll((Node) loader.load());
        rootController.closeDrawer();
    }

    public void handleTeacherBtn(ActionEvent actionEvent) throws IOException, UserException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
        loader.setController(new ManageUsersController(LoginLogoutUtil.UserType.TEACHER));
        anchorPane.getChildren().setAll((Node) loader.load());
        rootController.closeDrawer();
    }

    public void handleCitizenBtn(ActionEvent actionEvent) throws IOException {
        Tab duplicationTab = new Tab("Citizen templates");
        Tab assignTab = new Tab("student assigning");
        duplicationTab.setContent(FXMLLoader.load(getClass().getResource("/gui/View/TemplateView.fxml")));

        assignTab.setContent(FXMLLoader.load(getClass().getResource("/gui/View/CitizenAssignmentView.fxml")));

        TabPane tabPane = new TabPane(duplicationTab,assignTab);
        System.out.println("here");
        anchorPane.getChildren().setAll(tabPane);
        rootController.closeDrawer();
        }

}
