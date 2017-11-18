package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginController {
    Connection conn;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void onSubmit(ActionEvent event) throws SQLException, IOException {
        conn = DBConnection.Connect();
        Statement statement = conn.createStatement();
        ResultSet resultSet = null;
        try{
            resultSet = statement.executeQuery("select * from users where user_name = '"+usernameField.getText()+"'");
        }
        catch (SQLException sqex){
            System.out.println(sqex);
        }
        if (resultSet.next()) {
            if (passwordField.getText().equals(resultSet.getString("user_password"))) {
                Parent blah = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Scene scene = new Scene(blah);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Введён неверный логин или пароль.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Введён неверный логин или пароль.");
            alert.showAndWait();
        }

        conn.close();
    }

    @FXML
    void onRegister(ActionEvent event) throws IOException {
        Parent blah = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene scene = new Scene(blah);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }


}
