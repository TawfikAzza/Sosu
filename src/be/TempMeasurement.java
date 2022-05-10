package be;

import java.sql.Date;

public class TempMeasurement extends Measurement{
    public TempMeasurement(int citizenId, float floatMeasurement, Date date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
