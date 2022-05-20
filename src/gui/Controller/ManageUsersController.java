package gui.Controller;

import be.Student;
import be.Teacher;
import be.User;
import bll.exceptions.UserException;
import gui.Model.UserModel;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageUsersController implements Initializable {
    @FXML
    private TextField searchUsersField;
    @FXML
    private TableView usersTV;
    @FXML
    private TableColumn<?,String> firstNameTC,lastNameTC,userNameTC,passwordTC,emailTC;
    @FXML
    private TableColumn<?,Integer> phoneNumberTC;

    private LoginLogoutUtil.UserType userType;
    private UserModel userModel;

    public ManageUsersController(LoginLogoutUtil.UserType userType) throws IOException, UserException {
        this.userType=userType;
        userModel = UserModel.getInstance();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUsersTV();
        searchUsersField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    try {
                        if (userType== LoginLogoutUtil.UserType.TEACHER)
                            usersTV.setItems(userModel.getAllTeachers(searchUsersField.getText()));
                        else
                            usersTV.setItems(userModel.getAllStudents(searchUsersField.getText()));
                    } catch (SQLException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
            }
        }
    });
    }

    private void initializeUsersTV() {
        firstNameTC.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTC.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userNameTC.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordTC.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        emailTC.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberTC.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    public void deleteUser(ActionEvent actionEvent) {
    }

    public void editUser(ActionEvent actionEvent) throws IOException {
        if (usersTV.getSelectionModel().getSelectedItem()!=null){
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
            root = loader.load();

            NewEditUserController newEditUserController = loader.getController();
            if (userType== LoginLogoutUtil.UserType.STUDENT)
                newEditUserController.editStudent((Student) usersTV.getSelectionModel().getSelectedItem());
            else
                newEditUserController.editTeacher((Teacher) usersTV.getSelectionModel().getSelectedItem());


            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        NewEditUserController newEditUserController = loader.getController();
        if (userType== LoginLogoutUtil.UserType.STUDENT)
        newEditUserController.newStudent();

        Stage stage = new Stage();
        stage.setTitle("New Student");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
