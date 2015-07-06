/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller.inventory;

import account.management.model.MetaData;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class AddNewItemController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField p_rate;
    @FXML
    private TextField s_rate;
    @FXML
    private Button save;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        this.save.setDisable(true);
        try {
            String name = this.name.getText();
            float p_rate = Float.parseFloat(this.p_rate.getText());
            float s_rate = Float.parseFloat(this.s_rate.getText());
            Unirest.post(MetaData.baseUrl + "add/products")
                    .field("name", name)
                    .field("p_rate", p_rate)
                    .field("s_rate", s_rate)
                    .asJson();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("New Item has been added successfully!");
                alert.setGraphic(new ImageView(new Image("resources/success.jpg")));
                alert.showAndWait();
                this.save.setDisable(false);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
