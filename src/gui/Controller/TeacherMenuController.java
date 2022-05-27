package gui.Controller;

import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TeacherMenuController extends MenuController{

    private AnchorPane mainPane,hidePane;
    @FXML
    private VBox iconBox;
    @FXML
    private VBox btnBox;

    public TeacherMenuController(AnchorPane mainPane,AnchorPane hidePane) {
        super(mainPane);
        this.mainPane=mainPane;
        this.hidePane = hidePane;
    }

    public VBox getIconBox() {
        return iconBox;
    }

    public VBox getBtnBox() {
        return btnBox;
    }


    public void handleStudentsAssignmentsClick(ActionEvent actionEvent) {
        try {
            GridPane gridPane = FXMLLoader.load(getClass().getResource("/gui/View/CitizenAssignmentView.fxml"));
            gridPane.setLayoutX(40);
            gridPane.setLayoutY(26);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(gridPane);
            mainPane.getChildren().add(hidePane);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void handleTemplatesCitizensClick(ActionEvent actionEvent) {
        try {
            GridPane gridPane = FXMLLoader.load(getClass().getResource("/gui/View/TemplateView.fxml"));
            gridPane.setLayoutX(40);
            gridPane.setLayoutY(26);

            mainPane.getChildren().clear();
            mainPane.getChildren().add(gridPane);
            mainPane.getChildren().add(hidePane);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void handleLogoutClick(ActionEvent actionEvent) {
        try {
            LoginLogoutUtil.logout(actionEvent);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

}
