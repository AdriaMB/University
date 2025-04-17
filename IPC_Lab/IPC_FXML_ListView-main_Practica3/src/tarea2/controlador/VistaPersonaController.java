/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class VistaPersonaController implements Initializable {

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private Button buttonAceptar;
    private Persona persona;
    private boolean pulsadoOK = false;
    
    public boolean isOKPressed(){
        return pulsadoOK;
    }
    
    public Persona getPersona(){
        return persona;
    }
    
    //Metodo public controlador de la ventana secundaria con los parámetros necesarios
    //En el método se puede añadir el código para guardar los datos o inicializar elementos
    //de la interfaz
    
    public void initPersona(Persona p){
        persona = p; // creamos una persona
        nombreTextField.setText(p.getNombre());
        apellidosTextField.setText(p.getApellidos());
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buttonAceptar.disableProperty().bind(Bindings.or(nombreTextField.textProperty().isEmpty(), apellidosTextField.textProperty().isEmpty()));
        
        
        
        
        
    }    

    @FXML
    private void aceptar(ActionEvent event) {
        if((!nombreTextField.getText().isEmpty())
                && (nombreTextField.getText().trim().length() != 0)
                && (!apellidosTextField.getText().isEmpty())
                && (apellidosTextField.getText().trim().length() != 0)) {
            //============================================
            // añadimos a la lista
            persona = new Persona(nombreTextField.getText(), apellidosTextField.getText());
            pulsadoOK = true; 
            nombreTextField.getScene().getWindow().hide();
        }

       
    }

    @FXML
    private void cancelar(ActionEvent event) {
        nombreTextField.getScene().getWindow().hide();
    }


    
}
