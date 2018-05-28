import database.PredictionDAO;
import database.RegressionDataDAO;
import database.UserDAO;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.PredictionDTO;
import models.RegressionDataDTO;
import models.UserDTO;
import utils.NavigationUtils;
import utils.PredictWeightGain;
import utils.RegressionDataUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource(NavigationUtils.USER_AUTH));
        Parent root = loader.load();
//        root.getStylesheets().addAll(StylesUtils.COMPONENTS, StylesUtils.TABSTYLES);

//
//        try {
//            Image icon = new Image(Main.class.getResourceAsStream("resoursces\\icons\\appIcon.png"));
//            primaryStage.getIcons().add(icon);
//        } catch (Exception e) {
//
//        }


        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("LIFESTOCK WEIGHT PREDICTION SYSTEM");
        primaryStage.setMaximized(true);


        primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            }
        }));

        primaryStage.show();
    }


    public static void main(String[] args) {

//        SQLiteDB.connect();

//        UserDAO userDB = UserDAO.getInstance();
/*        UserDTO user = new UserDTO();
        user.setFullName("Ani Emmanule");
        user.setLoginName("Mr 7");
        user.setPhoneNumber("123456");

        userDB.addUser(user, "1234");*/
/*        UserDTO user = userDB.getUser("Mr 7", "1234");

        if(user != null){
            System.out.println(user.toString());
        }else{
            System.out.println("User not registered");
        }

        PredictionDAO predictionDB = PredictionDAO.getInstance();*/

      /*  for(int i = 1; i <= 5; i++){
            PredictionDTO prediciton = new PredictionDTO();
            prediciton.setAge(2 * i);
            prediciton.setHealth("Healthy");
            prediciton.setCarbo(3.9 * i);
            prediciton.setProtein(4 * i);
            prediciton.setFat(4 * i);
            prediciton.setVitamin(3 * i);
            prediciton.setWater(10 * i);

            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy hh:mm:ss");
            prediciton.setDateAdded(formatter.format(dateTime));
            prediciton.setDateModified(formatter.format(dateTime));

            prediciton.setPrediction(PredictWeightGain.predict(
                    (prediciton.getCarbo() + prediciton.getProtein() + prediciton.getFat() + prediciton.getVitamin()),
                    prediciton.getWater(), prediciton.getAge()));

            predictionDB.addPrediction(user.getId(), prediciton);

        }*/


 /*       for(PredictionDTO pred : predictionDB.getAllPredictions(user.getId())){
            System.out.println(pred.toString());
        }*/

//        RegressionDataDAO instance = RegressionDataDAO.getInstance();
/*        for(RegressionDataDTO data : RegressionDataUtils.defaultData()){
            instance.addData(user.getId(), data);
        }*/
/*        for(RegressionDataDTO data : instance.getAllData(user.getId())){
            System.out.println(data.toString());
        }*/


//        System.exit(0);

        launch(args);
    }

    /*

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }*/
}
