/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author sovacu
 */
public class MainViewController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private CheckMenuItem menuSeguro;
    @FXML
    private MenuItem menuImprimir;
    @FXML
    private MenuItem menuDelete;
    @FXML
    private MenuItem menuSalir;
    @FXML
    private ToggleGroup ejesToggleGroup;
    @FXML
    private MenuItem launchAmazon;
    @FXML
    private MenuItem launchAmazon1;
    @FXML
    private MenuItem launchAmazon2;
    @FXML
    private MenuItem launchAmazon3;
    @FXML
    private MenuItem launchAmazon4;
    @FXML
    private RadioMenuItem buyAmazon;
    @FXML
    private ToggleGroup buyersGroup;
    @FXML
    private RadioMenuItem buyEbay;
    @FXML
    private Button amazonButton;
    @FXML
    private Button ebayButton;
    @FXML
    private Button facebookButton;
    @FXML
    private Button bloggerButton;
    @FXML
    private Label labelBlog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void imprimir(ActionEvent e) {
        System.out.println("Print");
    }

    @FXML
    private void seguro(ActionEvent event) {
        System.out.println( "Are you sure: " + (menuSeguro.isSelected() ? "YES" : "NO"));
    }

    @FXML
    private void delete(ActionEvent event) {
        System.out.println("Delete");
    }

    @FXML
    private void salir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to leave the program");
        alert.setContentText("Do you want to exit?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("OK");
        }
        else{
            System.out.println("CANCEL");
        }
        
        
    }

    @FXML
    private void buyAmazonAction(ActionEvent event) {
        if(buyAmazon.isSelected()){
            WebView webView = new WebView();
            webView.getEngine().load("http://www.amazon.es"); // loads the web page...
            borderPane.setCenter(webView);// ... in the middle of the borderPane
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Selection error");
            alert.setHeaderText("You cannot buy in Amazon");
            alert.setContentText("Please, change the current selection in the Options menu");
            
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                System.out.println("OK");
            }
        }
    }

    @FXML
    private void buyEbayAction(ActionEvent event) {
        if(buyEbay.isSelected()){
            WebView webView = new WebView();
            webView.getEngine().load("http://www.ebay.es"); // loads the web page...
            borderPane.setCenter(webView);// ... in the middle of the borderPane
            
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Selection error");
            alert.setHeaderText("You cannot buy in Ebay");
            alert.setContentText("Please, change the current selection in the Options menu");
            
            //NECESSARY FOR THE SCREEN TO APPEAR
            Optional<ButtonType> result = alert.showAndWait();
            
            //NOT NECESSARY
            /**
            if(result.isPresent() && result.get() == ButtonType.OK){
                System.out.println("OK");
            }
            * */
        }
    }

    @FXML
    private void openFacebookAction(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Introduce your name");
        dialog.setHeaderText("Which user do you want to use to write in Facebook");
        dialog.setContentText("Enter your name: ");
        
        Optional<String> result = dialog.showAndWait();
        if(!result.get().equals("")){
            labelBlog.setText("Message sent to: " + result.get());
        }
        else{
            labelBlog.setText("No message was sent");
        }
        
    }

    @FXML
    private void openBloggerAction(ActionEvent event) {
        
        //Create a list of the possible choices
        List<String> choices = new ArrayList<>();
        choices.add("Athos' blog");
        choices.add("Porthos' blog");
        choices.add("Aramis' blog");
        
        //Create now the screen
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Athos' blog", choices);
        dialog.setTitle("Select a blog");
        dialog.setHeaderText("Which blog do you want to visit?");
        dialog.setContentText("Choose: ");
        
        Optional<String> result = dialog.showAndWait();
        labelBlog.setText("Visiting " + result.get());
        
    }
    
    
    
    
}
