package be;

import java.sql.Date;

public class BPMeasurement extends Measurement{
    public BPMeasurement(int citizenId, float floatMeasurement, Date date_observation) {
        super(citizenId, floatMeasurement, date_observation);
    }
}
