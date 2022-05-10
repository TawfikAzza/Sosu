package be;

import java.sql.Date;

public class BloodPressureMeasurement extends Measurement{
    public BloodPressureMeasurement(int citizenId, float floatMeasurement, Date date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
