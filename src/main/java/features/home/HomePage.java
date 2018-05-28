package features.home;

import database.PredictionDAO;
import features.calculate_weight_gain.CalculateWeightGain;
import features.sample_data.SampleData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.PredictionDTO;
import table.PredictionTableData;
import models.UserDTO;
import utils.DialogUtils;
import utils.NavigationUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomePage implements Initializable, CalculateWeightGain.DataSetChangeListener {

    @FXML
    private TableView<PredictionTableData> predictedTableView;

    @FXML
    private TableColumn<PredictionTableData, Integer> serialColumn;

    @FXML
    private TableColumn<PredictionTableData, Double> predictionColumn;

    @FXML
    private TableColumn<PredictionTableData, Integer> daysColumn;

    @FXML
    private TableColumn<PredictionTableData, String> dateAddedColumn;

    @FXML
    private TableColumn<PredictionTableData, String> lastModifiedColumn;

    @FXML private Text dateCreatedText;

    @FXML private Text lastModifiedText;

    @FXML private Text ageText;

    @FXML private Text healthText;

    @FXML private Text qtyCarbonhydrateText;

    @FXML private Text qtyProteinText;

    @FXML private Text qtyFatText;

    @FXML private Text qtyVitaminText;

    @FXML private Text qtyWaterText;

    @FXML private VBox totalCalculationLayout;

    @FXML private Text totalCalcText;

    @FXML private Button profileButton;


    private List<PredictionDTO> allPredictions;
    private UserDTO user;

    Parent root = null;
    Stage primaryStage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serialColumn.setCellValueFactory(new PropertyValueFactory<PredictionTableData, Integer>("serial"));
        predictionColumn.setCellValueFactory(new PropertyValueFactory<PredictionTableData, Double>("prediction"));
        daysColumn.setCellValueFactory(new PropertyValueFactory<PredictionTableData, Integer>("days"));
        dateAddedColumn.setCellValueFactory(new PropertyValueFactory<PredictionTableData, String>("dateAdded"));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<PredictionTableData, String>("dateModified"));

        totalCalculationLayout.setVisible(false);

        predictedTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(predictedTableView.getSelectionModel().getSelectedItem() != null)
                {
                    TableView.TableViewSelectionModel selectionModel = predictedTableView.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
//                    Object val = tablePosition.getTableColumn().getCellData(newValue);
                    PredictionDTO data = allPredictions.get(tablePosition.getRow());

                    dateCreatedText.setText(data.getDateAdded());
                    lastModifiedText.setText(data.getDateModified());
                    ageText.setText(String.valueOf(data.getAge()));
                    healthText.setText(data.getHealth());
                    qtyCarbonhydrateText.setText(String.valueOf(data.getCarbo()));
                    qtyProteinText.setText(String.valueOf(data.getProtein()));
                    qtyFatText.setText(String.valueOf(data.getFat()));
                    qtyVitaminText.setText(String.valueOf(data.getVitamin()));
                    qtyWaterText.setText(String.valueOf(data.getWater()));

                    totalCalcText.setText(String.format("WEIGHT GAIN AFTER %d DAYS IS %.2f", data.getDays(), data.getPrediction()));
                    totalCalculationLayout.setVisible(true);

                }
            }
        });
    }

    @FXML
    void deletePrediction(ActionEvent event) {

        TableView.TableViewSelectionModel selectionModel = predictedTableView.getSelectionModel();
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
                PredictionDTO data = allPredictions.get(tablePosition.getRow());

                PredictionDAO.getInstance().removeCalculation(user.getId(), data);
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
    void editData(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(NavigationUtils.DATA));

        try {
            root = loader.load();
            SampleData controller = loader.getController();
            controller.setUser(this.user);

            primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));

//            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setResizable(false);
            primaryStage.setTitle("REGRESSION DATA");
            primaryStage.initModality(Modality.APPLICATION_MODAL);

//            primaryStage.initOwner(btn1.getScene().getWindow());
            primaryStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editPrediction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(NavigationUtils.CALCULATION));

        TableView.TableViewSelectionModel selectionModel = predictedTableView.getSelectionModel();
        ObservableList selectedCells = selectionModel.getSelectedCells();

        if(selectedCells.size() > 0) {

            try {
                root = loader.load();
                CalculateWeightGain controller = loader.getController();
                controller.setUser(this.user);
                controller.setDataAddedListener(this);

//                    Object val = tablePosition.getTableColumn().getCellData(newValue);
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                PredictionDTO data = allPredictions.get(tablePosition.getRow());
                controller.setPredictionData(data);

                primaryStage = new Stage();
                primaryStage.setScene(new Scene(root));

//            primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.setResizable(false);
                primaryStage.setTitle("PREDICTION");
                primaryStage.initModality(Modality.APPLICATION_MODAL);


//            primaryStage.initOwner(event.getScene().getWindow());
                primaryStage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            DialogUtils.showErrorDialog("ERROR", "No table data selected");
        }
    }

    @FXML
    void newCalculation(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(NavigationUtils.CALCULATION));

        try {
            root = loader.load();
            CalculateWeightGain controller = loader.getController();
            controller.setUser(this.user);
            controller.setDataAddedListener(this);

            primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));

//            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setResizable(false);
            primaryStage.setTitle("PREDICTION");
            primaryStage.initModality(Modality.APPLICATION_MODAL);


//            primaryStage.initOwner(event.getScene().getWindow());
            primaryStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showProfile(ActionEvent event) {

    }

    @FXML
    void signout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(NavigationUtils.USER_AUTH));
            Parent root = loader.load();

//                    Scene homeScene = new Scene(root);
            Scene homeScene = ((Node)event.getSource()).getScene();
            homeScene.setRoot(root);
//                    Stage homeWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
                   /* homeWindow.setMaximized(true);

                    homeWindow.setScene(homeScene);
                    homeWindow.show();*/

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUser(UserDTO user) {
        this.user = user;

        if(this.user != null){
            profileButton.setText(user.getFullName().toUpperCase());

            refreshTableData();
        }
    }

    private void refreshTableData() {
        ObservableList<PredictionTableData> predictionList = FXCollections.observableArrayList();
        allPredictions = PredictionDAO.getInstance().getAllPredictions(user.getId());
        PredictionDTO pred = null;

        for(int i = 0; i < allPredictions.size(); i++){
            pred = allPredictions.get(i);
            predictionList.addAll(new PredictionTableData(i + 1, pred.getPrediction(), pred.getDays(),
                    pred.getDateAdded(), pred.getDateModified()));
        }

        predictedTableView.setItems(predictionList);

        dateCreatedText.setText("");
        lastModifiedText.setText("");
        ageText.setText("");
        healthText.setText("");
        qtyCarbonhydrateText.setText("");
        qtyProteinText.setText("");
        qtyFatText.setText("");
        qtyVitaminText.setText("");
        qtyWaterText.setText("");

        totalCalculationLayout.setVisible(false);
    }

    @Override
    public void onDataSetChanged() {
        refreshTableData();
    }
}
