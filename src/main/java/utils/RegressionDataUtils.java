package utils;

import models.RegressionDataDTO;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class RegressionDataUtils {
    public static List<RegressionDataDTO> defaultData(){

        return Arrays.asList(
                new RegressionDataDTO(UUID.randomUUID().toString(), 7.0, 210, 350,0.9,	4) ,
                new RegressionDataDTO(UUID.randomUUID().toString(), 12.0, 400, 750, 1.9,6),
                new RegressionDataDTO(UUID.randomUUID().toString(), 21.0, 625, 1000, 2.5, 8),
                new RegressionDataDTO(UUID.randomUUID().toString(), 30.0, 655, 1200, 3, 10),
                new RegressionDataDTO(UUID.randomUUID().toString(), 40.0, 710, 1400, 3.6, 12),
                new RegressionDataDTO(UUID.randomUUID().toString(), 51.0, 805, 1700, 4.2, 14),
                new RegressionDataDTO(UUID.randomUUID().toString(), 65.0, 970, 1900, 4.8, 16),
                new RegressionDataDTO(UUID.randomUUID().toString(), 80.0, 1020, 2200, 5.5, 18),
                new RegressionDataDTO(UUID.randomUUID().toString(), 95.0, 1120, 2400, 6, 20),
                new RegressionDataDTO(UUID.randomUUID().toString(), 110.0,1100, 2650, 6.6, 22)
        );
    }
}
