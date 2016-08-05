/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.view;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class Home1Controller implements Initializable {
    @FXML
    private Pane title_pane;
    @FXML
    private AnchorPane main_container;
    @FXML
    private AnchorPane link;
    @FXML
    private Label animated_label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(40000), this.animated_label);
        translate.setFromX(this.link.getPrefWidth() + 500);
        System.out.println(this.animated_label.getWidth());
        translate.setToX(-420);
        translate.setCycleCount(Timeline.INDEFINITE);
        translate.play();
    }    
    @FXML
    private void onLinkClick(MouseEvent event) {
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI("http://www.unitech4u.com"));
            } catch (IOException ex) {
                Logger.getLogger(Home1Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(Home1Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
