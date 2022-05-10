package be;

import java.sql.Date;

public class BloodSugarMeasurement extends Measurement{
    public BloodSugarMeasurement(int citizenId, float floatMeasurement, Date date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
