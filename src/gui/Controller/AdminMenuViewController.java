package gui.Controller;

import com.jfoenix.controls.JFXDrawer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdminMenuViewController {
    @FXML
    private VBox btnBox,iconBox;
    private AnchorPane anchorPane;
    private Node node;
    private JFXDrawer jfxDrawer;

    public VBox getBtnBox() {
        return btnBox;
    }

    public VBox getIconBox() {
        return iconBox;
    }

    public void handleStudentBtn(ActionEvent actionEvent) throws IOException {
        ManageStudentsController manageStudentsController = new ManageStudentsController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
        loader.setController(manageStudentsController);
        loader.load();

        anchorPane.getChildren().setAll((Node) loader.getRoot());
        }

    public void handleTeacherBtn(ActionEvent actionEvent) throws IOException {
        ManageTeachersController manageTeachersController = new ManageTeachersController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ManageUsersView.fxml"));
        loader.setController(manageTeachersController);

        loader.load();

        Node root = loader.getRoot();
        anchorPane.getChildren().setAll(root);
        root.setLayoutX(66);
        root.setLayoutY(30);

    }

    public void handleCitizenBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/TestViewTeacher.fxml"));
        loader.load();
        TeacherViewController teacherViewController = loader.getController();
        GridPane gridPane = teacherViewController.getMainGridPane();

        anchorPane.getChildren().setAll(gridPane);
        gridPane.setLayoutY(30);
        }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void setDrawer(JFXDrawer drawer) {
        this.jfxDrawer=drawer;
    }
}
