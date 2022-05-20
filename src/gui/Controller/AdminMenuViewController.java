package gui.Controller;

import com.jfoenix.controls.JFXDrawer;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdminMenuViewController extends MenuController{
    @FXML
    private VBox btnBox,iconBox;
    private AnchorPane anchorPane;

    public AdminMenuViewController(AnchorPane mainPane) {
        super(mainPane);
        this.anchorPane = mainPane;
    }

    public VBox getBtnBox() {
        return btnBox;
    }

    public VBox getIconBox() {
        return iconBox;
    }

    public void handleStudentBtn(ActionEvent actionEvent) throws IOException {
        }

    public void handleTeacherBtn(ActionEvent actionEvent) throws IOException {
        /*try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(gridPane);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }*/

    }

    public void handleCitizenBtn(ActionEvent actionEvent) throws IOException {
        Tab duplicationTab = new Tab("Citizen templates");
        Tab assignTab = new Tab("student assigning");
        duplicationTab.setContent(FXMLLoader.load(getClass().getResource("/gui/View/TemplateView.fxml")));

        assignTab.setContent(FXMLLoader.load(getClass().getResource("/gui/View/CitizenAssignmentView.fxml")));

        TabPane tabPane = new TabPane(duplicationTab,assignTab);

        anchorPane.getChildren().setAll(tabPane);
        }

}
