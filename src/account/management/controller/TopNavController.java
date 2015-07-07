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
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "AddLocation.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add new location");
            stage.setScene(scene);
            stage.showAndWait();
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
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateAccount2.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Create New Account");
            stage.setScene(scene);
            stage.showAndWait();
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
        Parent root;
        root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "depositVoucher.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setRoot(root);
        stage.setResizable(false);
        stage.setTitle("জমা ভাউচার");
        stage.setScene(scene);
        stage.showAndWait();
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
            stage.setTitle("Show Financial Statement");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onTrialBalanceReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ReportTrialBalance.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Trial Balance");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCreateBank(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "BankAccount.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Create Bank Account");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onDisplaySettingClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "DisplaySettings.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Display Settings");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCreatePartyClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CreateParty.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Create Party");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onAddItemClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "AddNewItem.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Add New Item");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onInsertStockClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "InsertStock.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Insert into Stock");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onStockReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "StockReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Stock Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onProductWiseInventoryReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "ProductWiseInventoryReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Stock Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onAllProductsReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "AllProductWiseReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Stock Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onPurchaseReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "PurchaseReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Purchase Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onSellReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.inventoryViewPath + "SellReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Sell Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onLCReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "LCReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("LC Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onProjectReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "ProjectReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Project Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onCNFReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "CNFReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("C&F Report");
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    private void onPartyDetailsReportClick(ActionEvent event) throws IOException {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "PartyDetailsReport.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setRoot(root);
            stage.setResizable(false);
            stage.setTitle("Party Wise Details Report");
            stage.setScene(scene);
            stage.showAndWait();
    }
    
}
