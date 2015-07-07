/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.FileUpload;
import account.management.model.MetaData;
import com.mashape.unirest.http.Unirest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CreatePartyController implements Initializable {
    private ImageView preview;
    private File file;
    @FXML
    private WebView webView;
    @FXML
    private Button close;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        file = new File("");
        webView.getEngine().load(MetaData.baseUrl + "create/party");
    }    


    private void onSubmitButtonClick(ActionEvent event) {
        
        try {
            FileUpload.uploadFile("http://localhost/acc/public/testaa", "/profile.jpg");
        } catch (Exception ex) {
            Logger.getLogger(CreatePartyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onCloseButtonClick(ActionEvent event) {
        this.close.getScene().getWindow().hide();
    }
    
}
