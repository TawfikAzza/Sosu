package gui.Controller;

import be.School;
import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import gui.Model.SchoolModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewEditSchoolController implements Initializable {
    @FXML
    private GridPane mainPane;
    private SchoolModel schoolModel;
    private School school;

    public NewEditSchoolController() throws SchoolException, IOException, UserException {
        schoolModel= SchoolModel.getInstance();
    }

    @FXML
    private TextField schoolName, schoolAddress, pCode, regionSchool, schoolNumber;
    private boolean onAdd=true;

    public void setEdit(School selectedItem) {
        onAdd=false;
        school=selectedItem;
        schoolName.setText(selectedItem.getName());
    }


    @FXML
    private void handleCancelBtn(ActionEvent actionEvent) {
        if (!schoolName.getText().isEmpty()){
            DisplayMessage.displayConfirmation("Your imput will be lost","Are you ssure you want to close this window");
        }
        else {
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
            }
        }

    @FXML
    private void handleConfirmBtn(ActionEvent actionEvent) throws SchoolException, SQLException {
        if (onAdd)
            try {
                schoolModel.newSchool(schoolName.getText());
                Stage stage;
                stage = (Stage) schoolName.getScene().getWindow();
                stage.close();
            }catch (SchoolException schoolException){
                DisplayMessage.displaySchoolErrorMessage(schoolException);
            }
        else {
            school.setName(schoolName.getText());
            schoolModel.editSchool(school,schoolName.getText());
            Stage stage;
            stage = (Stage) schoolName.getScene().getWindow();
            stage.close();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    try {
                        handleConfirmBtn(new ActionEvent());
                    } catch (SchoolException e) {
                        DisplayMessage.displayError(e);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if (event.getCode().equals(KeyCode.ESCAPE))
                    handleCancelBtn(new ActionEvent());
            }
        });
    }
}
