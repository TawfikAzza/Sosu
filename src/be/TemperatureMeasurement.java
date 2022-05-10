package be;

import java.sql.Date;

public class TemperatureMeasurement extends Measurement{
    public TemperatureMeasurement(int citizenId, float floatMeasurement, Date date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
