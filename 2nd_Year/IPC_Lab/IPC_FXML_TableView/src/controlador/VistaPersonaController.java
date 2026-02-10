/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button aceptarButton;
    @FXML
    private ComboBox<String> imagesCombo;
    
    private Persona persona;
    private boolean pulsadoOK;

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
        imagesCombo.setValue(p.getImagen());
        
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //=================================================================
        // add 3 path images to ComboBox
        imagesCombo.getItems().addAll("/resources/images/Lloroso.png", "/resources/images/Sonriente.png", "/resources/images/Pregunta.png");
        
        //=================================================================
        // change the way the ComboBox cell is visualized, setCellFactory
        imagesCombo.setCellFactory( c -> new ImageComboCell());
        //=================================================================
        // change the way the ComboBox selecction is visualized, setButtonCell
        imagesCombo.setButtonCell(new ImageComboCell());
        
        //==================================================================
        // add binding with the disabled property on aceptarButton
        aceptarButton.disableProperty().bind(Bindings.or(nombreTextField.textProperty().isEmpty(), apellidosTextField.textProperty().isEmpty()).or(imagesCombo.valueProperty().isNull()));
        
    }

    @FXML
    private void aceptar(ActionEvent event) {
          if((!nombreTextField.getText().isEmpty())
                && (nombreTextField.getText().trim().length() != 0)
                && (!apellidosTextField.getText().isEmpty())
                && (apellidosTextField.getText().trim().length() != 0)) {
            //============================================
            // añadimos a la lista
            persona = new Persona(nombreTextField.getText(), apellidosTextField.getText(), imagesCombo.getValue());
            pulsadoOK = true; 
            nombreTextField.getScene().getWindow().hide();
        }

       
    }

    @FXML
    private void cancelar(ActionEvent event) {
        nombreTextField.getScene().getWindow().hide();
    }

    //=======================================================
    // class to display images in the combobox
    // to us with setCellFactory and setButtonCell
    class ImageComboCell extends ComboBoxListCell<String> {

        private ImageView view = new ImageView();
        private Image imagen;

        @Override
        public void updateItem(String t, boolean bln) {
            super.updateItem(t, bln);
            if (t == null || bln) {
                setText(null);
                setGraphic(null);
            } else {
                imagen = new Image(t, 25, 25, true, true);
                view.setImage(imagen);
                setGraphic(view);
                setText(null);
            }
        }
    }

}
