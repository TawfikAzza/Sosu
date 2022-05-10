package be;

import java.time.LocalDate;

public class BloodSugar extends Measurement{
    public BloodSugar(int citizenId, float floatMeasurement, LocalDate date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
