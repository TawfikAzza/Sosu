package gui.Controller;

import gui.Controller.MenuController;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class StudentMenuController extends MenuController {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox iconBox;
    @FXML
    private VBox btnBox;

    public StudentMenuController(AnchorPane mainPane) {
        super(mainPane);
        this.mainPane=mainPane;
    }

    @FXML
    private void handleCitizensClick(ActionEvent actionEvent) {

    }

    @FXML
    private void handleLogoutClick(ActionEvent actionEvent) {
        try {
            LoginLogoutUtil.logout(actionEvent);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    public VBox getIconBox() {
        return iconBox;
    }

    public VBox getBtnBox() {
        return btnBox;
    }
}
