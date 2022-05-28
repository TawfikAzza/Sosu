package gui.Controller;

import be.InfoCategory;
import bll.exceptions.GeneralInfoException;
import bll.util.GlobalVariables;
import gui.Model.GInfoModel;
import gui.utils.DisplayMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GeneralInfoReportController implements Initializable {

    @FXML
    private ListView<InfoCategory> infoCategoryList;

    private GInfoModel generalInfoModel;

    public GeneralInfoReportController() {
        try {
            generalInfoModel = new GInfoModel();
        } catch (GeneralInfoException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectGeneralInfo(MouseEvent mouseEvent) throws IOException {
        InfoCategory selectedInfoCategory = infoCategoryList.getSelectionModel().getSelectedItem();
        if (selectedInfoCategory == null)
            return;
        openGeneralInfoForm(selectedInfoCategory);
    }

    private void openGeneralInfoForm(InfoCategory selectedInfoCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/GeneralInfoFormView.fxml"));
        Parent root = loader.load();

        GeneralInfoFormController infoFormController = loader.getController();

        infoFormController.setSelectedInfoCategory(selectedInfoCategory);

        Stage newWindow = new Stage();
        newWindow.setScene(new Scene(root));
        newWindow.setTitle(selectedInfoCategory.getName());
        newWindow.getIcons().add(new Image("Images/sosu.png"));
        newWindow.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategories();
    }

    private void loadCategories() {
        try {
            List<InfoCategory> categoryList = generalInfoModel.getGInfoCategories();
            infoCategoryList.setItems(FXCollections.observableArrayList(categoryList));
        } catch (GeneralInfoException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    public void openGeneralReport(ActionEvent actionEvent) {
        if(GlobalVariables.getSelectedCitizen()==null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/DisplayGRIView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

}
