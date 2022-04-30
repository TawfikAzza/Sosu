package gui.Controller;

import be.User;
import bll.UserManger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button submitButton;

    @FXML
    private Label loginLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userField;

    public UserManger userManger = new UserManger();



    public void openHpMgr(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/HealthSectionDisplay.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();
    }

    public void openFaMgr(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/FunctionalSectionDisplay.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();
    }

    public void openCitizenForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/CitizenFormView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();
    }

    public void closeWindow() throws IOException{
        Stage window = (Stage) this.passwordField.getScene().getWindow();
        window.close();
    }


    public void submitLogin(ActionEvent actionEvent) throws Exception {

        User user = userManger.submitLogin(userField.getText(), passwordField.getText());

        if (user != null){
            if (user.getRoleID() == 1){
                openHpMgr(actionEvent);
            }
            else if (user.getRoleID() ==2){
                openFaMgr( actionEvent);
            }
        }
    }
}
