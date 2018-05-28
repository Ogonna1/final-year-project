package features.login;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import database.RegressionDataDAO;
import database.UserDAO;
import features.home.HomePage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import models.RegressionDataDTO;
import models.UserDTO;
import utils.DialogUtils;
import utils.NavigationUtils;
import utils.RegressionDataUtils;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;

public class LoginSignup implements Initializable{

    public static final String REMEMBER_ME_KEY = "remeber_me";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String CONFIG = "config.prop";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab tabSignin;

    @FXML
    private JFXTextField signin_loginName;

    @FXML
    private JFXPasswordField signin_password;

    @FXML
    private Tab tabSignup;

    @FXML
    private JFXTextField signup_fullName;

    @FXML
    private JFXCheckBox rememberMeCheckbox;

    @FXML
    private JFXTextField signup_loginName;

    @FXML
    private JFXTextField signup_phoneNumber;

    @FXML
    private JFXPasswordField signup_newPassword;

    @FXML
    private JFXPasswordField signup_confirmPassword;

    Properties prop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prop = new Properties();

        try {
            InputStream is = new FileInputStream(CONFIG);
            prop.load(is);
            rememberMeCheckbox.setSelected(prop.getProperty(REMEMBER_ME_KEY).equals(TRUE));
            signin_loginName.setText(prop.getProperty(USER_NAME));
            signin_password.setText(prop.getProperty(PASSWORD));
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void createAccount(ActionEvent event) {
        if(!signup_newPassword.getText().isEmpty() &&
                signup_newPassword.getText().equals(signup_confirmPassword.getText())){


            String name = signup_fullName.getText();
            String loginName = signup_loginName.getText();
            String phone = signup_phoneNumber.getText();
            String password = signup_newPassword.getText();

            if(name.isEmpty() || loginName.isEmpty() || phone.isEmpty()){
                //somefields are empty
                DialogUtils.showErrorDialog("Error creating account",
                        "Error!! some fields are empty, please ensure you filled all fields correctly");
            }else{

                UserDTO user = new UserDTO(UUID.randomUUID().toString(), name, loginName, phone);
                UserDAO.getInstance()
                        .addUser(user, password);

                tabPane.getSelectionModel().select(tabSignin);
                signin_loginName.setText(loginName);
                signin_password.setText(password);

                for(RegressionDataDTO data : RegressionDataUtils.defaultData())
                    RegressionDataDAO.getInstance().addData(user.getId(), data);

            }
        }else{
            //show password not match error
            DialogUtils.showErrorDialog("Error creating account",
                    "Error!! password do not match");
        }
    }

    @FXML
    void signin(ActionEvent event) {

        if(signin_loginName.getText().isEmpty() || signin_password.getText().isEmpty()){
            DialogUtils.showWarningDialog("Login failed", "Incorrect username or password");
        }else{
            UserDTO user = UserDAO.getInstance()
                    .getUser(signin_loginName.getText().trim(), signin_password.getText().trim());

            if(user == null){
                DialogUtils.showWarningDialog("Login failed", "Incorrect username or password");
            }else{
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(NavigationUtils.HOME));
                    Parent root = loader.load();

                    HomePage controller = loader.getController();
                    controller.setUser(user);

//                    Scene homeScene = new Scene(root);
                    Scene homeScene = ((Node)event.getSource()).getScene();
                    homeScene.setRoot(root);

                    if(rememberMeCheckbox.isSelected()) {
                        prop.setProperty(REMEMBER_ME_KEY, TRUE);
                        prop.setProperty(USER_NAME, signin_loginName.getText());
                        prop.setProperty(PASSWORD, signin_password.getText());
                    }else{
                        prop.setProperty(REMEMBER_ME_KEY, FALSE);
                        prop.setProperty(USER_NAME, "");
                        prop.setProperty(PASSWORD, "");
                    }
                    OutputStream os = new FileOutputStream(CONFIG);
                    prop.store(os, null);
//                    Stage homeWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
                   /* homeWindow.setMaximized(true);

                    homeWindow.setScene(homeScene);
                    homeWindow.show();*/

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void switchToSigninTab(ActionEvent event) {
        tabPane.getSelectionModel().select(tabSignin);
    }

    @FXML
    void switchToSignupTab(ActionEvent event) {
        tabPane.getSelectionModel().select(tabSignup);
    }
}
