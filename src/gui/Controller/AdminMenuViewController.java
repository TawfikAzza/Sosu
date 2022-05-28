package gui.Controller;

import bll.exceptions.UserException;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class AdminMenuViewController extends MenuController{
    @FXML
    private VBox btnBox,iconBox;
    private AnchorPane anchorPane,hidePane;
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

    @FXML
    private void handleSchoolsBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageSchoolsViews.fxml"));
        Node node = loader.load();
        node.setLayoutX(320);
        node.setLayoutY(26);
        anchorPane.getChildren().setAll(node);
        anchorPane.getChildren().add(hidePane);

        rootController.closeDrawer();
    }

    //public void handleLogOutBtn(ActionEvent actionEvent){}

    @FXML
    private void handleAdminBtn(ActionEvent actionEvent) throws IOException, UserException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
        loader.setController(new ManageUsersController(LoginLogoutUtil.UserType.ADMIN));
        Node node = loader.load();
        node.setLayoutX(60);
        node.setLayoutY(26);
        anchorPane.getChildren().setAll(node);
        anchorPane.getChildren().add(hidePane);

        rootController.closeDrawer();
    }

    @FXML
    private void handleStudentBtn(ActionEvent actionEvent) throws IOException, UserException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
        loader.setController(new ManageUsersController(LoginLogoutUtil.UserType.STUDENT));
        Node node = loader.load();
        node.setLayoutX(100);
        node.setLayoutY(26);
        anchorPane.getChildren().setAll(node);
        anchorPane.getChildren().add(hidePane);

        rootController.closeDrawer();
    }

    @FXML
    private void handleTeacherBtn(ActionEvent actionEvent) throws IOException, UserException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
        loader.setController(new ManageUsersController(LoginLogoutUtil.UserType.TEACHER));
        Node node = loader.load();
        node.setLayoutX(100);
        node.setLayoutY(26);
        anchorPane.getChildren().setAll(node);
        anchorPane.getChildren().add(hidePane);


        rootController.closeDrawer();
    }

    @FXML
    private void handleCitizenBtn(ActionEvent actionEvent) throws IOException {
        Tab duplicationTab = new Tab("Citizen templates");
        Tab assignTab = new Tab("student assigning");
        duplicationTab.setContent(FXMLLoader.load(getClass().getResource("/gui/View/TemplateView.fxml")));

        assignTab.setContent(FXMLLoader.load(getClass().getResource("/gui/View/CitizenAssignmentView.fxml")));


        TabPane tabPane = new TabPane(duplicationTab,assignTab);

        tabPane.setLayoutX(40);
        anchorPane.getChildren().setAll(tabPane);
        anchorPane.getChildren().add(hidePane);

        rootController.closeDrawer();
        }

    public void setHidePane(AnchorPane hidePane) {
        this.hidePane=hidePane;
    }

    @FXML
    private void handleLogoutClick(ActionEvent actionEvent) {
        try {
            LoginLogoutUtil.logout(actionEvent);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }
}
