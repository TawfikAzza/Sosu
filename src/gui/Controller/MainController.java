package gui.Controller;

import be.*;
import bll.UserManager;
import bll.exceptions.UserException;
import bll.util.GlobalVariables;
import gui.Main;
import gui.Model.LogInModel;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    private LogInModel logInModel;


    public void closeWindow() throws IOException{
        Stage window = (Stage) this.passwordField.getScene().getWindow();
        window.close();
    }


    public void submitLogin(Event actionEvent) throws Exception {

        User user = logInModel.submitLogin(userField.getText(), passwordField.getText());

        if (user != null)
            LoginLogoutUtil.login(actionEvent,user.getRoleID());
        else
            WrongLoginLabel.setVisible(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            logInModel = new LogInModel();
        } catch (SQLException | IOException | UserException e) {
            DisplayMessage.displayError(e);
        }
        mainPane.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                try {
                    submitLogin(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}