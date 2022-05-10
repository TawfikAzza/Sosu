package be;

import java.sql.Date;

public class BSMeasurement extends Measurement{
    public BSMeasurement(int citizenId, float floatMeasurement, Date date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
