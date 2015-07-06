/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import com.mashape.unirest.http.Unirest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CreatePartyController implements Initializable {
    @FXML
    private Button choose_file;
    @FXML
    private ImageView preview;
    @FXML
    private Button submit;
    private File file;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        file = new File("");
    }    

    @FXML
    private void onChooseFileClick(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        file = chooser.showOpenDialog(null);
        try {
            BufferedImage bf = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bf, null);
            preview.setImage(image);
        } catch (Exception e) {
        }
    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        try {
            Unirest.post("http://localhost/acc/public/test")
                    .field("photo", new File("/resources/error.jpg")).asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
