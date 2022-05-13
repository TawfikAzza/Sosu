package gui.Controller;

import be.Citizen;
import be.ObservationType;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;

public class ChartController {
    @FXML
    private AreaChart areaChart;

    private Citizen currentCitizen;

    private ObservationType observationType;

    public void setObservationType(ObservationType bpMeasurement) {
        this.observationType =observationType;
    }

    public void setCitizen(Citizen selectedCitizen) {
        currentCitizen= selectedCitizen;
    }
}
