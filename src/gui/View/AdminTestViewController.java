package gui.View;

import be.Student;
import be.Teacher;
import bll.exceptions.UserException;
import gui.Controller.MainController;
import gui.Controller.NewEditUserController;
import gui.Model.UserModel;
import gui.utils.DisplayMessage;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminTestViewController implements Initializable {
    public TableView<Teacher> teachersTV;
    public TableColumn<Teacher,String> firstNameTTC,lastNameTTC,userNameTTC,passWordTTC,emailTTC;
    public TableColumn<Teacher,Integer> phoneNumberTTC;

    public TableView<Student> studentsTV;
    public TableColumn<Student,String> firstNaneSTC,lastNameSTC,userNameSTC,passWordSTC,emailSTC;
    public TableColumn<Student,Integer> phoneNumberSTC;

    @FXML
    private TextField searchStudentsFilter,searchCitizensFilter,searchTeachersFilter;

    @FXML
    private AnchorPane pane1,pane2;

    @FXML
    private ImageView exit,menu;

    private UserModel userModel;

    //private String tabName;

    private FXMLLoader newUserWindowLoader = new FXMLLoader();
    private Parent root;
    private final Stage stage = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newUserWindowLoader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        try {
            root = newUserWindowLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        initStudentsTV();

        try {
            userModel=UserModel.getInstance();
        } catch (IOException | UserException e) {
            e.printStackTrace();
        }

        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        pane1.setVisible(false);


        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();



        menu.setOnMouseClicked(event -> {

            pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        pane1.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });


            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

        searchTeachersFilter.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        teachersTV.setItems(userModel.getAllTeachers(searchTeachersFilter.getText()));
                    } catch (SQLException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
                }
            }
        });
        searchStudentsFilter.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        studentsTV.setItems(userModel.getAllStudents(searchTeachersFilter.getText()));
                    } catch (SQLException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void initStudentsTV(){
        firstNaneSTC.setCellValueFactory(new PropertyValueFactory<>("fNameProperty"));
        lastNameSTC.setCellValueFactory(new PropertyValueFactory<>("lNameProperty"));
        userNameSTC.setCellValueFactory(new PropertyValueFactory<>("userNameProperty"));
        passWordSTC.setCellValueFactory(new PropertyValueFactory<>("passwordProperty"));
        emailSTC.setCellValueFactory(new PropertyValueFactory<>("emailProperty"));
        phoneNumberSTC.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    public void handleManageStudentsTab(Event event) {
        NewEditUserController newEditUserController = newUserWindowLoader.getController();
        newEditUserController.newStudent();
    }

    public void handleManageCitizensTab(Event event) {
    }

    public void handleLogOutBtn(ActionEvent actionEvent) {
    }

    public void handleDuplicateCitizenBtn(ActionEvent actionEvent) {
    }

    public void handleAssignCitizenBtn(ActionEvent actionEvent) {
    }

    public void handleDeleteUserBtn(ActionEvent actionEvent) {
    }

    public void handleEditUserBtn(ActionEvent actionEvent) {
    }

    public void handleAddUserBtn(ActionEvent actionEvent) throws IOException {

        stage.setScene(new Scene(root));
        stage.show();


        /*if (tabName.equals("student")){
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        NewEditUserController newEditUserController = loader.getController();
        newEditUserController.newStudent();

        Stage stage = new Stage();
        stage.setTitle("New Student");
        stage.setScene(new Scene(root));
        stage.show();
        }
        else
        {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("New Teacher");
            stage.setScene(new Scene(root));
            stage.show();
        }*/
    }
}


