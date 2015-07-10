/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.view.layouts;

import account.management.model.MetaData;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amimul Ahsan
 */
public class Layout_sidenavController implements Initializable {
    @FXML
    private Accordion sidenav_container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void newBankAccount(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "BankAccount.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(false);
        stage.setTitle("Party Wise Details Report");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void editBankAccount(ActionEvent event) {
    }

    @FXML
    private void viewBankAccount(ActionEvent event) {
    }
    
}
