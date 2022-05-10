package be;

import java.time.LocalDate;

public class Oxygen extends Measurement{
    public Oxygen(int citizenId, float weight, int height, LocalDate date_observation) {
        super(citizenId, weight, height, date_observation);
    }
}
