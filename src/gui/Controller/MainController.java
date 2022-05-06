package gui.Controller;

import be.*;
import bll.UserManager;
import gui.Main;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
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

    private Main main;

    public UserManager userManager = new UserManager();

    public MainController() throws IOException {
    }


    public void closeWindow() throws IOException{
        Stage window = (Stage) this.passwordField.getScene().getWindow();
        window.close();
    }


    public void submitLogin(ActionEvent actionEvent) throws Exception {

        User user = userManager.submitLogin(userField.getText(), passwordField.getText());
        main.setUser(user);
        //System.out.println(user.getRoleID());

        if (user != null){
            if (user.getRoleID()==1){
                main.setLayoutChosen("admin");
                try {
                    main.initRootLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                WrongLoginLabel.setVisible(false);

            }
            if (user.getRoleID()==2){
                main.setLayoutChosen("teacher");
                try {
                    main.initRootLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                WrongLoginLabel.setVisible(false);
            }
            if (user.getRoleID()==3){
                main.setLayoutChosen("student");
                try {
                    main.initRootLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        Citizen citizen = new Citizen(138,"Jeppe", "moritz","1254789636587");
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
        Citizen citizen = new Citizen(138,"Jeppe", "moritz","1254789636587");
        healthConditionReportViewController.setCurrentCitizen(citizen);
        // functionalReportViewController.displayCitizenReport();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }

    public void openTeacher(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/TeacherWindow.fxml"));
        Parent root = loader.load();

        TeacherWindowController teacherWindowController = loader.getController();
        teacherWindowController.setCurrentTeacher((Teacher) user);
        teacherWindowController.loadData();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }

    public void openStudent(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/StudentMenuView.fxml"));
        Parent root = loader.load();

        StudentMenuViewController studentMenuViewController = loader.getController();
        studentMenuViewController.setCurrentStudent((Student) user);
        studentMenuViewController.upadateTableCitizen();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }

    public void setMainApp(Main main) {
        this.main= main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        submitLogin(new ActionEvent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
