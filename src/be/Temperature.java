package be;

import java.time.LocalDate;

public class Temperature extends Measurement{
    public Temperature(int citizenId, float floatMeasurement, LocalDate date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
