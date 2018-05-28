package features.sample_data;

import com.jfoenix.controls.JFXTextField;
import database.PredictionDAO;
import database.RegressionDataDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.PredictionDTO;
import models.RegressionDataDTO;
import models.UserDTO;
import table.PredictionTableData;
import table.SampleDataTable;
import utils.DialogUtils;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SampleData implements Initializable {

    @FXML
    private TableView<SampleDataTable> entryTableView;

    @FXML
    private TableColumn<SampleDataTable, Integer> serialColumn;
    @FXML
    private TableColumn<SampleDataTable, Double> weightColumn;

    @FXML
    private TableColumn<SampleDataTable, Double> dailyGainColumn;

    @FXML
    private TableColumn<SampleDataTable, Double> feedConsumptionColumn;

    @FXML
    private TableColumn<SampleDataTable, Double> waterConsumptionColum;

    @FXML
    private TableColumn<SampleDataTable, Double> ageColumn;

    @FXML
    private GridPane newEntryGridPane;

    @FXML
    private JFXTextField weightTextField;

    @FXML
    private JFXTextField dailyGainTextField;

    @FXML
    private JFXTextField feedTextField;

    @FXML
    private JFXTextField waterTextField;

    @FXML
    private JFXTextField ageTextField;
    private UserDTO user;

    ObservableList<SampleDataTable> sampleDataTables;
    private List<RegressionDataDTO> datas;
    /*ObservableList<SampleDataTable> sampleDataTables =
            FXCollections.observableArrayList(
                    new SampleDataTable(1, 6.0, 4.0, 3.0, 4.0, 5.0),
                    new SampleDataTable(2, 6.0, 4.0, 3.0, 4.0, 5.0),
                    new SampleDataTable(3, 6.0, 4.0, 3.0, 4.0, 5.0),
                    new SampleDataTable(4, 6.0, 4.0, 3.0, 4.0, 5.0),
                    new SampleDataTable(5, 6.0, 4.0, 3.0, 4.0, 5.0),
                    new SampleDataTable(6, 6.0, 4.0, 3.0, 4.0, 5.0),
                    new SampleDataTable(7, 6.0, 4.0, 3.0, 4.0, 5.0),
                    new SampleDataTable(8, 6.0, 4.0, 3.0, 4.0, 5.0)
            );*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serialColumn.setCellValueFactory(new PropertyValueFactory<SampleDataTable, Integer>("id"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<SampleDataTable, Double>("weight"));
        dailyGainColumn.setCellValueFactory(new PropertyValueFactory<SampleDataTable, Double>("dailyGain"));
        feedConsumptionColumn.setCellValueFactory(new PropertyValueFactory<SampleDataTable, Double>("feedConsumption"));
        waterConsumptionColum.setCellValueFactory(new PropertyValueFactory<SampleDataTable, Double>("waterConsumption"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<SampleDataTable, Double>("ageOfPig"));

        sampleDataTables = FXCollections.observableArrayList();
//        entryTableView.setItems(sampleDataTables);

    }


    @FXML
    void addNewEntryToDatabase(ActionEvent event) {

        if(weightTextField.getText().isEmpty() || dailyGainTextField.getText().isEmpty() ||
                feedTextField.getText().isEmpty() || waterTextField.getText().isEmpty() ||
                ageTextField.getText().isEmpty()) {

            DialogUtils.showErrorDialog("ERROR", "Error adding data!!! some fields are empty");
        }else{

            try {
                double weight = Double.parseDouble(weightTextField.getText());
                double dailyGain = Double.parseDouble(dailyGainTextField.getText());
                double feed = Double.parseDouble(feedTextField.getText());
                double water = Double.parseDouble(waterTextField.getText());
                int age = Integer.parseInt(ageTextField.getText());

                RegressionDataDTO data = new RegressionDataDTO();
                data.setWeight(weight);
                data.setDailyGain(dailyGain);
                data.setDailyFeed(feed);
                data.setWater(water);
                data.setAge(age);

                RegressionDataDAO.getInstance().addData(user.getId(), data);
                refreshTableData();

                newEntryGridPane.setVisible(false);
                weightTextField.setText("");
                dailyGainTextField.setText("");
                feedTextField.setText("");
                waterTextField.setText("");
                ageTextField.setText("");

            }catch (Exception e){
                System.out.println(e.getMessage());

                DialogUtils.showErrorDialog("ERROR", "Error invalid value detected!!! \n" +
                        "Some fields contains invalid characters. only numeric characters (0-9) and '.' are allowed");
            }
        }
    }

    @FXML
    void cancelNewEntry(ActionEvent event) {
        newEntryGridPane.setVisible(false);
    }

    @FXML
    void deleteSelectedEntry(ActionEvent event) {
        TableView.TableViewSelectionModel selectionModel = entryTableView.getSelectionModel();
        ObservableList selectedCells = selectionModel.getSelectedCells();

        if(selectedCells.size() > 0) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete data");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this entry? \n(Note: this action cannot be reversed)");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
//                    Object val = tablePosition.getTableColumn().getCellData(newValue);
                RegressionDataDTO data = datas.get(tablePosition.getRow());

                RegressionDataDAO.getInstance().deleteData(user.getId(), data.getId());
                refreshTableData();

            } else {
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
        }else{
            DialogUtils.showErrorDialog("ERROR", "No table data selected");
        }

    }

    @FXML
    void showNewEntryDialog(ActionEvent event) {
        newEntryGridPane.setVisible(true);
    }

    public void setUser(UserDTO user){
        this.user = user;

        refreshTableData();
    }

    private void refreshTableData() {
        this.datas = RegressionDataDAO.getInstance().getAllData(user.getId());
        sampleDataTables.clear();

        for(int i = 0; i < datas.size(); i++){
            RegressionDataDTO data = datas.get(i);
            sampleDataTables.add(new SampleDataTable(i+1, data.getWeight(), data.getDailyGain(),
                    data.getDailyFeed(), data.getWater(), data.getAge()));
        }

        entryTableView.setItems(sampleDataTables);

    }

}
