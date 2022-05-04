package gui.Controller;

import be.*;
import bll.UserManager;
import bll.exceptions.HealthCategoryException;
import gui.Model.CategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {


    @FXML
    private Button submitButton;

    @FXML
    private Label loginLabel;
    @FXML
    private Label WrongLoginLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userField;

    public UserManager userManager = new UserManager();

    public MainController() throws IOException {
    }




    public void openAdminMgr(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root,755,400);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }

    public void closeWindow() throws IOException{
        Stage window = (Stage) this.passwordField.getScene().getWindow();
        window.close();
    }


    public void submitLogin(ActionEvent actionEvent) throws Exception {

        User user = userManager.submitLogin(userField.getText(), passwordField.getText());
        //System.out.println(user.getRoleID());

        if (user != null){
            if (user.getRoleID()==1){
                openAdminMgr(new ActionEvent());
                WrongLoginLabel.setVisible(false);

            }
            if (user.getRoleID()==2){
                openTeacher(new ActionEvent());
                WrongLoginLabel.setVisible(false);
            }
            if (user.getRoleID()==3){
                openStudent();
                WrongLoginLabel.setVisible(false);
            }
        }
        else
        {
            WrongLoginLabel.setVisible(true);
        }
    }


    public void openFAReportMgr(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/FunctionalReportView.fxml"));
        Parent root = loader.load();
        FunctionalReportViewController functionalReportViewController = loader.getController();
        Citizen citizen = new Citizen(1,"Jeppe", "moritz","1254789636587");
        functionalReportViewController.setCurrentCitizen(citizen);
        // functionalReportViewController.displayCitizenReport();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }

    public void openHealthReportMgr(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/HealthConditionReportView.fxml"));
        Parent root = loader.load();
        HealthConditionReportViewController healthConditionReportViewController = loader.getController();
        Citizen citizen = new Citizen(1,"Jeppe", "moritz","1254789636587");
        healthConditionReportViewController.setCurrentCitizen(citizen);
        // functionalReportViewController.displayCitizenReport();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }

    public void openTeacher(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/TeacherWindow.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }

    public void openStudent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/StudentMenuView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }
}
