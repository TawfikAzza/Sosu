package gui.Controller;

import gui.Model.CategoryModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class BranchDisplayController implements Initializable {
    @FXML
    private ListView hConditionList;
    @FXML
    private ListView fStateList;

    private CategoryModel categoryModel;

    public BranchDisplayController() {
        categoryModel = new CategoryModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fStateList.setItems(categoryModel.getAbilityCategories());
    }


    @FXML
    private void selectState(MouseEvent mouseEvent) {

    }

    @FXML
    private void selectCondition(MouseEvent mouseEvent) {
    }
}
