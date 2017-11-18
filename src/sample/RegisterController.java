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
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {
    Connection conn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repPassField;

    @FXML
    void onBack(ActionEvent event) throws IOException, SQLException {
        if (conn!=null){
            conn.close();
        }


        Parent blah = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(blah);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    void onSubmit(ActionEvent event) throws SQLException, IOException {
        if (passwordField.getText().equals(repPassField.getText())){
            conn = DBConnection.Connect();
            Statement statement = conn.createStatement();
            try{
                statement.execute("INSERT INTO `business`.`users` (`user_name`, `user_password`, `user_lvl`) VALUES ('"+usernameField.getText()+"', '"+passwordField.getText()+"', 'simple');");
            }
            catch (SQLException sqex){
                System.out.println(sqex);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Успех!");
            alert.setHeaderText(null);
            alert.setContentText("Аккаунт успешно зарегистрирован!");
            alert.showAndWait();

            conn.close();

            Parent blah = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Пароли не совпадают!");

            alert.showAndWait();
        }
    }

}
