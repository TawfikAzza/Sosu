package gui.Controller;

import be.*;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import bll.util.GlobalVariables;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import gui.Model.SchoolModel;
import gui.Model.StudentModel;
import gui.Model.UserModel;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewEditUserController implements Initializable {
    @FXML
    private RowConstraints schoolChoiceRow;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button cnfrmButton;
    @FXML
    private Text mainLabel;
    @FXML
    private TextField firstName, lastName, userName, passWord, email, phoneNumberField;
    @FXML
    private JFXComboBox<School> schoolComboBox;
    @FXML
    private HBox iconUNBox,iconPWBox,iconEmailBox,iconPNBox;

    private Boolean newUser = true;

    private SchoolModel schoolModel;
    private UserModel userModel;
    private StudentModel studentModel;

    private Teacher teacher;
    private Student student;
    private Admin admin;
    private LoginLogoutUtil.UserType userType;


    public NewEditUserController(LoginLogoutUtil.UserType userType) {
        this.userType = userType;
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage;
        if (!(firstName.getText().isEmpty() && lastName.getText().isEmpty() && userName.getText().isEmpty() && passWord.getText().isEmpty() && email.getText().isEmpty() && phoneNumberField.getText().isEmpty() && schoolComboBox.getSelectionModel().getSelectedItem() == null)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Are you sure you want to close this window ?");
            alert.setContentText("All your infos will be lost in this case.");
            alert.showAndWait();
            if (alert.showAndWait().get() == ButtonType.OK) {
                stage = (Stage) cnfrmButton.getScene().getWindow();
                stage.close();
            }
        } else {
            stage = (Stage) cnfrmButton.getScene().getWindow();
            stage.close();
        }
    }

    public void createNewUser(ActionEvent actionEvent) throws SQLException, UserException {
        if (userType == LoginLogoutUtil.UserType.TEACHER) {
            if (newUser) {
                try {
                    userModel.newTeacher(schoolComboBox.getSelectionModel().getSelectedItem(),
                            firstName.getText(), lastName.getText(), userName.getText(), passWord.getText(), email.getText(), phoneNumberField.getText());
                    Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                    stage.close();
                } catch (UserException e) {
                    DisplayMessage.displayError(e);
                    DisplayMessage.displayMessage(e.getExceptionMessage());
                }
            } else {
                teacher.setFirstName(firstName.getText());
                teacher.setLastName(lastName.getText());
                teacher.setUserName(userName.getText());
                teacher.setPassWord(passWord.getText());
                teacher.setEmail(email.getText());
                teacher.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
                try {
                    userModel.editTeacher(teacher, schoolComboBox.getSelectionModel().getSelectedItem());

                    Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                    stage.close();
                } catch (UserException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(e.getExceptionMessage());
                    alert.setContentText(e.getInstructions());
                    alert.showAndWait();
                }
            }
        } else if (userType == LoginLogoutUtil.UserType.STUDENT) {
            if (newUser) {
                try {
                    Student student = userModel.newStudent(schoolComboBox.getSelectionModel().getSelectedItem(),
                            firstName.getText(), lastName.getText(), userName.getText(), passWord.getText(), email.getText(), phoneNumberField.getText());

                    ObservableList<Student> underlyingList = (ObservableList<Student>) studentModel.getObsStudents().getSource();
                    underlyingList.add(student);

                    Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                    stage.close();
                } catch (UserException userException) {
                    DisplayMessage.displayMessage(userException.getExceptionMessage());
                }
            } else {
                student.setFirstName(firstName.getText());
                student.setLastName(lastName.getText());
                student.setUserName(userName.getText());
                student.setPassWord(passWord.getText());
                student.setEmail(email.getText());
                student.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
                try {
                    userModel.editStudent(schoolComboBox.getSelectionModel().getSelectedItem(), student);
                    Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                    stage.close();
                } catch (UserException e) {
                    DisplayMessage.displayError(e);
                    DisplayMessage.displayMessage(e.getExceptionMessage());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (newUser) {
                try {
                    userModel.newAdmin(schoolComboBox.getSelectionModel().getSelectedItem(),
                            firstName.getText(), lastName.getText(), userName.getText(), passWord.getText(), email.getText(), phoneNumberField.getText());
                    Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                    stage.close();
                }catch (UserException ue){
                    DisplayMessage.displayError(ue);
                    DisplayMessage.displayMessage(ue.getExceptionMessage());
                }
            }
            else {
                admin.setFirstName(firstName.getText());
                admin.setLastName(lastName.getText());
                admin.setUserName(userName.getText());
                admin.setPassWord(passWord.getText());
                admin.setEmail(email.getText());
                admin.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
                userModel.editAdmin(schoolComboBox.getSelectionModel().getSelectedItem(),
                        admin);
                Stage stage = (Stage) cnfrmButton.getScene().getWindow();
                stage.close();
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userModel = UserModel.getInstance();
            studentModel = StudentModel.getInstance();
            schoolModel = SchoolModel.getInstance();

            schoolComboBox.setItems(schoolModel.getAllSchoolsFL());
        } catch (IOException | UserException | SchoolException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setAdminMode();
        setUpMainLabel();
        setUpIcons();

        phoneNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (phoneNumberField.getText().length() > 8) {
                    String s = phoneNumberField.getText().substring(0, 8);
                    phoneNumberField.setText(s);
                }
            }
        });

        gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    try {
                        createNewUser(new ActionEvent());
                    } catch (SQLException | UserException e) {
                        e.printStackTrace();
                    }
                } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                    handleCancel(new ActionEvent());
                }
            }
        });
    }

    private void setUpIcons() {
        Text unIcon = GlyphsDude.createIcon(FontAwesomeIcon.USER);
        iconUNBox.getChildren().add(unIcon);

        iconPWBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcon.KEY));
        iconEmailBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcon.ENVELOPE));
        iconPNBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcon.PHONE));
    }

    private void setUpMainLabel() {
        if (userType == LoginLogoutUtil.UserType.STUDENT)
            mainLabel.setText("New student");
        else if (userType == LoginLogoutUtil.UserType.ADMIN)
            mainLabel.setText("New admin");
    }

    //check if admin has logged in, otherwise disable choice of school
    private void setAdminMode() {
        if (GlobalVariables.getCurrentAdmin() == null)
            disableSchoolChoice();
    }

    private void disableSchoolChoice() {
        //If we want to keep it but remove the choice of selection
        schoolComboBox.setDisable(true);
        schoolComboBox.getSelectionModel().select(GlobalVariables.getCurrentSchool());
        schoolComboBox.setOpacity(0);
    }


    public void isNewUser(Boolean newUser, User selectedItem) {
        this.newUser = newUser;
        if (userType == LoginLogoutUtil.UserType.TEACHER){
            mainLabel.setText("Edit teacher");
            teacher = (Teacher) selectedItem;
        }
        else if (userType == LoginLogoutUtil.UserType.STUDENT){
            mainLabel.setText("Edit student");
            student= (Student) selectedItem;
        }
        else {
            mainLabel.setText("Edit Admin");
            admin = (Admin) selectedItem;
        }

        firstName.setText(selectedItem.getFirstName());
        lastName.setText(selectedItem.getLastName());
        userName.setText(selectedItem.getUserName());
        passWord.setText(selectedItem.getPassWord());
        email.setText(selectedItem.getEmail());
        phoneNumberField.setText(String.valueOf(selectedItem.getPhoneNumber()));
        for (School school : schoolComboBox.getItems()) {
            if (school.getId()==selectedItem.getSchoolId()) {
                int index = schoolComboBox.getItems().indexOf(school);
                schoolComboBox.getSelectionModel().select(index);
            }
        }
    }

}
