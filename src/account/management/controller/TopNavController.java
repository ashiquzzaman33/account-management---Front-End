/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author Raju
 */
public class TopNavController implements Initializable{
    @FXML
    private MenuItem logout;
    @FXML
    private MenuBar top_navbar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void onLogoutMenuClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "login.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
    }

    @FXML
    private void onAddLocationMenuClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "AddLocation.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.setTitle("Add Location- Account Management (UniTech4U)");
    }

    @FXML
    private void onEditLocationMenuClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "editLocation.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
    }

    @FXML
    private void onCreateAccountMenuClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "createAccount.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
    }

    @FXML
    private void onEditAccountMenuClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "editAccount.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
    }


    @FXML
    private void onNewVoucherMenuClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "newVoucher.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
    }
    
}
