/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.List;                  
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateStringConverter;

public class FXMLRegisterController implements Initializable {

    //===================================EMAIL
    @FXML
    private Label emailError;
    @FXML
    private TextField emailField;
 
    //===================================PASSWORD 
    @FXML
    private Label passwordError;
    @FXML
    private TextField passwordField;
    
    //====================================PASSWORD2
    @FXML
    private Label repeatError;
    @FXML
    private TextField repeatField;
    
    //====================================DATE
    @FXML
    private Label dateError;
    @FXML
    private DatePicker dateField;
    
    //====================================Button
    @FXML
    private Button bAccept;
    @FXML
    private Button bCancel;
    
    
    
    
    
    //properties to control valid fields values. 
    private BooleanProperty validEmail;
    private BooleanProperty validPassword;
    private BooleanProperty validRepeat;
    private BooleanProperty validDate;
 
    // listener to register on textProperty() or valueProperty()
    private ChangeListener<String> listenerEmail;
    private ChangeListener<String> listenerPassword;
    private ChangeListener<String> listenerRepeat;
    private ChangeListener<LocalDate> listenerDate;

    
    

    
    private void checkEmail() {
        String email = emailField.getText();
//        boolean isValid = email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
        boolean isValid = email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        validEmail.set(isValid); //actualiza la property asociada
        showError(isValid, emailField, emailError); //muestra o esconde el mensaje de error
    }

    private void checkPassword() {
        String password = passwordField.getText();
        boolean isValid = password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}$");
        validPassword.set(isValid); //actualiza la property asociada
        showError(isValid, passwordField, passwordError); //muestra o esconde el mensaje de error
    }
    
    private void checkPasswordsMatch() {
        boolean match = passwordField.getText().equals(repeatField.getText());
        validRepeat.set(match);
        showError(match, repeatField, repeatError);
    }

    private void checkDate(){
        LocalDate value = dateField.getValue();
        boolean isValid = value.isBefore(LocalDate.now().minus(16, YEARS));
        validDate.set(isValid);
        showError(isValid, dateField, dateError);
    }
    
    
    private void showError(boolean isValid, Node field, Node errorMessage){
        errorMessage.setVisible(!isValid);
        field.setStyle(((isValid) ? "" : "-fx-background-color: #FCE5E0"));
    }

    
    @FXML
    private void handleBAcceptOnAction(ActionEvent event) {
        emailField.clear();
        passwordField.clear();
        repeatField.clear();
        dateField.setValue(null);
        validEmail.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        validRepeat.setValue(Boolean.FALSE);
        validDate.setValue(Boolean.FALSE);
    }   
    
    
    @FXML
    private void handleBRejectOnAction(ActionEvent event) {
        bCancel.getScene().getWindow().hide();
    }   
    
    
    
    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {        //Initialize == main (more or less)

        validEmail = new SimpleBooleanProperty(false);
        validPassword = new SimpleBooleanProperty(false);
        validRepeat = new SimpleBooleanProperty(false);
        validDate = new SimpleBooleanProperty(false);
        
        
        //ACCEPT BUTTON
        BooleanBinding validFields = Bindings.and(validEmail, validPassword)
                                             .and(validRepeat)
                                             .and(validDate);
        bAccept.disableProperty().bind(
                    Bindings.not(validFields)
                );
        
        
        
        
        
        
        //EMAIL
        
        //When the field loses focus, the field is validated. 
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                checkEmail();
                if (!validEmail.get()) {
                    //If it is not correct, a listener is added to the text or value 
                    //so that the field is validated while it is being edited.
                    if (listenerEmail == null) {
                        listenerEmail = (a, b, c) -> checkEmail();
                        emailField.textProperty().addListener(listenerEmail);
                    }
                }
            }
        });
        
        //PASSWORD
        
        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if(!newVal){
                System.out.println("Before checking the password");
                
                checkPassword();      //Updates the value of validPassword
                System.out.println("I have checked the password");
                if(!validPassword.get()){
                    
                    if(listenerPassword == null){
                        System.out.println("ListenerPassword == null");
                        listenerPassword = (a, b, c) -> checkPassword();
                        System.out.println("The listener was created...");
                        passwordField.textProperty().addListener(listenerPassword);
                        System.out.println("...and now added to the textProperty of the password");
                        System.out.println();
                    }
                
                }
            }
        });
        
        //PASSWORD2
        
        
        repeatField.focusedProperty().addListener((obs, oldVal, newVal)->{
            
            
            if(!newVal){
                System.out.println("Before checking the password");
                checkPasswordsMatch();
                 System.out.println("I have checked the password");
                if(!validRepeat.get()){
                    System.out.println("Ups, not okay. Password2 is not correct");
                    //creates a new Listener

                    if(listenerRepeat == null){
                           System.out.println("ListenerRepeat == null");
                        listenerRepeat = ((a,b,c) -> checkPasswordsMatch());
                         System.out.println("The listener was created...");
                        repeatField.textProperty().addListener(listenerRepeat);
                        System.out.println("...and now added to the textProperty of the password");
                        System.out.println();
                    }
                    System.out.println("Now I should clear the password and repeat fields");
                        System.out.println();

                }
            }
        
        });
        
        
        
         
         
       //DATE
        LocalDateStringConverter localDateStringConverter = new LocalDateStringConverter() {
        @Override
            public LocalDate fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (Exception e) {
                    System.out.println("Exception in fromString");
                    return LocalDate.now();
                }
            }
        };
        dateField.setConverter(localDateStringConverter);
        
        dateField.focusedProperty().addListener((obs, oldVal, newVal) ->{
            if(!newVal){
                checkDate();
                
                if(!validDate.get()){
                    if(listenerDate == null){
                        listenerDate = (a,b,c)->checkDate();
                        dateField.valueProperty().addListener(listenerDate);
                    }
                
                }
                
            }
        });
        
        
        

       
        

        
    }
    


}