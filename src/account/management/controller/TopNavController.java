/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.BS;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import report.Report;

/**
 *
 * @author Raju
 */
public class TopNavController implements Initializable{
    @FXML
    private MenuItem logout;
    @FXML
    private MenuBar top_navbar;
    @FXML
    private MenuItem depositVoucher;
    @FXML
    private MenuItem expense_voucher;
    @FXML
    private MenuItem depositVoucher1;

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

    @FXML
    private void onDepositVoucherClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "depositVoucher.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
    }

    @FXML
    private void onExpenseVoucherClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "expenseVoucher.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
    }

    @FXML
    private void onCreateAccountTypeClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "createAccountType.fxml"));
        Scene scene = top_navbar.getScene();
        Stage stage = (Stage)top_navbar.getScene().getWindow();
        scene.setRoot(root);
        stage.setScene(scene);
    }

    @FXML
    private void onCreateProjectMenuClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateProject.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add New Project");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCreateLCMenuClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateLC.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add New LC");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCreateCNFMenuClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateCNF.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add New C&F");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onLedgerReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ReportLedger.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Show Ledger");
            stage.setScene(scene);
            stage.showAndWait();

        
    }

    @FXML
    private void onCreateVoucherType(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "VoucherType.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Create Voucher Type");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onBsReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ReportBs.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Show Balance Sheet");
            stage.setScene(scene);
            stage.showAndWait();
    }
    
}
