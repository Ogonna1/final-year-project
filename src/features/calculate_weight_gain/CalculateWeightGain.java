package features.calculate_weight_gain;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.PredictionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.PredictionDTO;
import models.UserDTO;
import utils.DialogUtils;
import utils.PredictWeightGain;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CalculateWeightGain implements Initializable{

    @FXML
    private JFXTextField ageOfPig;

    @FXML
    private JFXComboBox<String> healthLevel;

    @FXML
    private JFXTextField qtyOfCarbohydrate;

    @FXML
    private JFXTextField qtyOfProtein;

    @FXML
    private JFXTextField qtyOfFat;

    @FXML
    private JFXTextField qtyOfMinerals;

    @FXML
    private JFXTextField qtyOfWater;

    @FXML
    private JFXTextField numberOfDaysTextField;

    @FXML
    private Text weightGainTextView;

    private UserDTO user;
    private double prediction = 0.0;
    private DataSetChangeListener dataAddedListener;

    int days;
    int age;
    String health;
    double carbo;
    double protein;
    double fat;
    double mineral;
    double water;
    private PredictionDTO data;

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        healthLevel.getItems().addAll("Healthy", "Not healthy");
        healthLevel.getSelectionModel().selectFirst();
    }

    @FXML
    void calculateWeightGain(ActionEvent event) {

        if(ageOfPig.getText().isEmpty() || qtyOfCarbohydrate.getText().isEmpty() || qtyOfProtein.getText().isEmpty() ||
                qtyOfFat.getText().isEmpty() || qtyOfMinerals.getText().isEmpty() || qtyOfWater.getText().isEmpty()
                || numberOfDaysTextField.getText().isEmpty()){
            //some field are empty
            DialogUtils.showErrorDialog("Error", "Error!!! Some fields are empty");
        }else {

            try {
                days = Integer.parseInt(numberOfDaysTextField.getText());
                age = Integer.parseInt(ageOfPig.getText());
                health = healthLevel.getSelectionModel().getSelectedItem();
                carbo = Double.parseDouble(qtyOfCarbohydrate.getText());
                protein = Double.parseDouble(qtyOfProtein.getText());
                fat = Double.parseDouble(qtyOfFat.getText());
                mineral = Double.parseDouble(qtyOfMinerals.getText());
                water = Double.parseDouble(qtyOfWater.getText());

                this.prediction = PredictWeightGain.predict((carbo + protein + fat + mineral), water, age);

                weightGainTextView.setText(String.format("Weight gain after %d days is %.2f", days, prediction));
                weightGainTextView.setText(String.valueOf(prediction));

            } catch (Exception e) {
                e.getMessage();

                DialogUtils.showErrorDialog("Calculation error", "Error performin calculation!!! Fields can only contain Numeric(0 - 9) characters");
            }
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void save(ActionEvent event) {
        if(prediction > 0){

            PredictionDTO pred = new PredictionDTO();;

            if(data != null)
                pred = data;

            pred.setAge(age);
            pred.setDays(days);
            pred.setHealth(health);
            pred.setCarbo(carbo);
            pred.setProtein(protein);
            pred.setPrediction(prediction);
            pred.setFat(fat);
            pred.setVitamin(mineral);
            pred.setWater(water);


            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy hh:mm:ss");

            pred.setDateModified(formatter.format(dateTime));

            if(data == null){
                pred.setDateAdded(formatter.format(dateTime));
                PredictionDAO.getInstance().addPrediction(user.getId(), pred);
            } else{
                PredictionDAO.getInstance().updateCalculation(user.getId(), pred);
            }

            dataAddedListener.onDataSetChanged();

            DialogUtils.showInfoDialog("SAVE", "Data saved successfully");
        }else{
            DialogUtils.showErrorDialog("Save Error", "Error!!! you have not performed any calculation, " +
                    "perform a calculation to save");

        }
    }

    public void setDataAddedListener(DataSetChangeListener dataAddedListener) {
        this.dataAddedListener = dataAddedListener;
    }

    public void setPredictionData(PredictionDTO predictionData) {
        this.data = predictionData;

        ageOfPig.setText(String.valueOf(data.getAge()));
        numberOfDaysTextField.setText(String.valueOf(data.getDays()));
        healthLevel.getSelectionModel().select(data.getHealth());
        qtyOfCarbohydrate.setText(String.valueOf(data.getCarbo()));
        qtyOfProtein.setText(String.valueOf(data.getProtein()));
        qtyOfFat.setText(String.valueOf(data.getFat()));
        qtyOfMinerals.setText(String.valueOf(data.getVitamin()));
        qtyOfWater.setText(String.valueOf(data.getWater()));

    }

    public interface DataSetChangeListener {
        void onDataSetChanged();
    }
}
