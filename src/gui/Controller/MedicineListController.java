package gui.Controller;

import be.MedicineList;
import bll.util.GlobalVariables;
import gui.Model.MedicineListModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
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
     medicineListModel = new MedicineListModel();

    }


    @FXML
    private void saveMedicineList() {
        MedicineList medicineList = null;
        medicineList = new MedicineList(1, GlobalVariables.getSelectedCitizen().getId(), textMedicineList.getText());

    }
}
