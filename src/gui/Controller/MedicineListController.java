package gui.Controller;

import be.Citizen;
import be.Condition;
import be.MedicineList;
import bll.exceptions.MedicineListException;
import bll.util.GlobalVariables;
import gui.Model.MedicineListModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class MedicineListController implements Initializable {


    @FXML
    private Button btnSave;

    @FXML
    private Label lblLastname;

    @FXML
    private Label lblfirstname;

    @FXML
    private Button btnclose;

    @FXML
    private TextArea textMedicineList;
    private String operationType;

    private MedicineListModel medicineListModel;
    //private Citizen currentCitizen;
   // private StudentMenuViewController studentMenuViewController;

    public MedicineListController() {


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            medicineListModel = new MedicineListModel();

        } catch (MedicineListException e) {
            DisplayMessage.displayError(e);
        }

        setFields();


    }


    public void setFields() {
        try {

            MedicineList medicineList = medicineListModel.getMedicineList(GlobalVariables.getSelectedCitizen());
            if (medicineList == null) {
                operationType = "Insert";
                btnSave.setText("Add Medicine");
                return;
            }

            operationType = "Update";
            btnSave.setText(" Update Medicine");

            lblfirstname.setText(GlobalVariables.getSelectedCitizen().getFName());
            lblLastname.setText(GlobalVariables.getSelectedCitizen().getLName());
            textMedicineList.setText(medicineList.getMedicineList());

        } catch (MedicineListException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void saveMedicineList() throws MedicineListException {
       MedicineList medicineList;
       medicineList = new MedicineList(1, GlobalVariables.getSelectedCitizen().getId(), textMedicineList.getText());

       if (operationType.equals("Insert")) {
           medicineListModel.addMedicineList(medicineList);
       }

       if (operationType.equals("Update")){
           medicineListModel.updateMedicineList(medicineList);
       }


        clickclose();

    }


    public void clickclose() {
        Stage window = (Stage) this.btnclose.getScene().getWindow();
        window.close();
    }
}
