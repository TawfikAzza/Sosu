package gui.Controller;

import be.*;
import bll.UserManager;
import gui.utils.LoginLogoutUtil;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label WrongLoginLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userField;

    private UserManager userManager = new UserManager();

    public MainController() throws IOException {
    }


    public void closeWindow() throws IOException{
        Stage window = (Stage) this.passwordField.getScene().getWindow();
        window.close();
    }


    @FXML
    private void submitLogin(Event actionEvent) throws Exception {

        User user = userManager.submitLogin(userField.getText(), passwordField.getText());

        if (user != null)
            LoginLogoutUtil.login(actionEvent,user.getRoleID());
        else
            WrongLoginLabel.setVisible(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        submitLogin(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
