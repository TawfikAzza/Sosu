package be;

import java.util.Date;

public class BMIMeasurement extends Measurement{
    public BMIMeasurement(int citizenId, float weight, int height, Date date_observation) {
        super(citizenId, weight, height, date_observation);
    }
}
