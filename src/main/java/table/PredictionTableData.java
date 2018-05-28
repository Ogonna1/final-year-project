package table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PredictionTableData {

    public final SimpleIntegerProperty serial;
    public final SimpleDoubleProperty prediction;
    public final SimpleIntegerProperty days;
    public final SimpleStringProperty dateAdded;
    public final SimpleStringProperty dateModified;

    public PredictionTableData(Integer serial, Double prediction, Integer days, String dateAdded, String dateModified) {
        this.serial = new SimpleIntegerProperty(serial);
        this.prediction = new SimpleDoubleProperty(prediction);
        this.days = new SimpleIntegerProperty(days);
        this.dateAdded = new SimpleStringProperty(dateAdded);
        this.dateModified = new SimpleStringProperty(dateModified);
    }

    public Integer getSerial() {
        return serial.get();
    }

    public Double getPrediction() {
        return prediction.get();
    }

    public Integer getDays() {
        return days.get();
    }

    public String getDateAdded() {
        return dateAdded.get();
    }

    public String getDateModified() {
        return dateModified.get();
    }
}