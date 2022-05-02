package gui.Controller;

import be.Citizen;
import bll.exceptions.CitizenException;
import gui.Model.TeacherModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherWindowController implements Initializable {

    private final TeacherModel model;
    @FXML
    private TableView<Citizen> tableViewTemplates;
    @FXML
    private TableColumn<Citizen, String> fNameTableColumn;
    @FXML
    private TableColumn<Citizen, String> lNameTableColumn;

    public TeacherWindowController() throws IOException {
        this.model = new TeacherModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<Citizen> cits = model.getTemplates();
            this.tableViewTemplates.setItems(cits);
            for(Citizen citizen : cits)
            {
                System.out.println(citizen.getFName());
                System.out.println(citizen.getLName());
            }
            this.initTables();
        } catch (CitizenException e) {
            e.printStackTrace();
        }
    }

    private void initTables() {
        //right side
        this.fNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.lNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));
    }
}
