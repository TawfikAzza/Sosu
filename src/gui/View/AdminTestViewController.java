package gui.View;

import be.Student;
import be.Teacher;
import bll.exceptions.UserException;
import gui.Model.TeacherModel;
import gui.Model.UserModel;
import gui.utils.DisplayMessage;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    }


