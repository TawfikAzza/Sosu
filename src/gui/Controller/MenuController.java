package gui.Controller;

import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public abstract class MenuController {

    private VBox iconBox;
    private VBox btnBox;
    private AnchorPane mainPane,hidePane;

    public MenuController(AnchorPane mainPane)
    {
        this.mainPane = mainPane;
    }

    public VBox getIconBox() {
        return iconBox;
    }

    public VBox getBtnBox() {
        return btnBox;
    }

    public void setHidePane(AnchorPane hidePane) {
        this.hidePane=hidePane;
    }
}
