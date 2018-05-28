package table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SampleDataTable {

public final SimpleIntegerProperty id;
public final SimpleDoubleProperty weight;
public final SimpleDoubleProperty dailyGain;
public final SimpleDoubleProperty feedConsumption;
public final SimpleDoubleProperty waterConsumption;
public final SimpleIntegerProperty ageOfPig;

    public SampleDataTable(Integer id, Double weight, Double dailyGain, Double feedConsumption,
                           Double waterConsumption, Integer ageOfPig) {

        this.id = new SimpleIntegerProperty(id);
        this.weight = new SimpleDoubleProperty(weight);
        this.dailyGain = new SimpleDoubleProperty(dailyGain);
        this.feedConsumption = new SimpleDoubleProperty(feedConsumption);
        this.waterConsumption = new SimpleDoubleProperty(waterConsumption);
        this.ageOfPig = new SimpleIntegerProperty(ageOfPig);
    }

    public Integer getId() {
        return id.get();
    }

    public Double getWeight() {
        return weight.get();
    }

    public Double getDailyGain() {
        return dailyGain.get();
    }

    public Double getFeedConsumption() {
        return feedConsumption.get();
    }

    public Double getWaterConsumption() {
        return waterConsumption.get();
    }

    public Integer getAgeOfPig() {
        return ageOfPig.get();
    }
}
