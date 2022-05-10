package be;

import java.time.LocalDate;

public class BloodPressure extends Measurement{
    public BloodPressure(int citizenId, float floatMeasurement, LocalDate date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
