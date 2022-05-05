package gui.Controller;

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

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class MedicineListController implements Initializable {



    @FXML
    private Label lblCPR;
    @FXML
    private Button btnSave;

    @FXML
    private Label lblLastname;

    @FXML
    private Label lblfirstname;

    @FXML
    private TextArea textMedicineList;
    private MedicineListModel medicineListModel;

    public  MedicineListController () {


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            medicineListModel = new MedicineListModel();

        } catch (MedicineListException e) {
            DisplayMessage.displayError(e);
        }

    }




    @FXML
    private void saveMedicineList() {
        MedicineList medicineList = null;
        try {
            medicineList = medicineListModel.getMedicineList(GlobalVariables.getSelectedCitizen());
        } catch (MedicineListException e) {
            DisplayMessage.displayError(e);
        }
        medicineList = new MedicineList(1, GlobalVariables.getSelectedCitizen().getId(), textMedicineList.getText());

        if (medicineList != null ){
            try {
                medicineListModel.updateMedicineList(medicineList);
            } catch (MedicineListException e) {
                e.printStackTrace();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        else{


        }



    }
}
