/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class VistaTablaController implements Initializable {

    private ObservableList<Persona> datos = null; // Colecci�n vinculada a la vista.

    @FXML
    private Button addButton;
    @FXML
    private Button modificarButton;
    @FXML
    private Button borrarButton;
    @FXML
    private TableColumn<Persona, String> nombreColumn;
    @FXML
    private TableColumn<Persona, String> apellidosColumn;
    @FXML
    private TableView<Persona> personasTableV;
    @FXML
    private TableColumn<Persona, String> imagenColumn;

    private void inicializaModelo() {
        ArrayList<Persona> misdatos = new ArrayList<Persona>();
        misdatos.add(new Persona("Pepe", "García", "/resources/images/Lloroso.png"));
        misdatos.add(new Persona("María", "Pérez","/resources/images/Sonriente.png"));
        //==================================================
        // create ObservableList, FXCollections
        datos = FXCollections.observableArrayList(misdatos);
    }

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializaModelo();
        //=================================================
        // connect model with tableView setItems()
        personasTableV.setItems(datos);
        //=================================================
        // initialize tableColumn   setCellValueFactory()
        nombreColumn.setCellValueFactory(
            (personaFila) -> {
                return personaFila.getValue().NombreProperty();
            });
        apellidosColumn.setCellValueFactory(
            (personaFila) -> {
                return personaFila.getValue().ApellidosProperty();
            });
        imagenColumn.setCellValueFactory(
             personaFila ->
                 new SimpleStringProperty(personaFila.getValue().getImagen()));
        imagenColumn.setCellFactory( c -> new ImagenTabCell());
        
        //===============================================
        // add bindings for diable buttons
        modificarButton.disableProperty().bind(personasTableV.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        borrarButton.disableProperty().bind(personasTableV.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        
        
        
    }

    @FXML
    private void addAction(ActionEvent event) throws IOException {
          //The add button should be always enabled
        FXMLLoader miCargador = new
            FXMLLoader(getClass().getResource("/vista/VistaPersona.fxml"));
        Parent root = miCargador.load();
        
        VistaPersonaController controlador2 = miCargador.getController();
        
        
        Scene scene = new Scene(root, 500, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        
        stage.showAndWait();
        
        
        if(controlador2.isOKPressed()){
            Persona p = controlador2.getPersona();
            datos.add(p);
        
        }
        
    }

    @FXML
    private void updateAction(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new 
                    FXMLLoader(getClass().getResource("/vista/VistaPersona.fxml"));
        
        //Parent: base class for all nodes that have childern in the scene graph. It handels all hierachichal scene graph operations: adding, removing...
        Parent root = miCargador.load();
        
        //acceso al controlador de datos persona
        VistaPersonaController controlador2 = miCargador.getController();
        controlador2.initPersona(personasTableV.getSelectionModel().getSelectedItem());
        
        //Scene: container for all content in a scene graph
        Scene scene = new Scene(root, 500, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        
   
        stage.showAndWait(); // inicia un segundo hilo de eventos anidado con el primero
        // para obtener el valor modificado en la ventana emergente...
        if(controlador2.isOKPressed()){
            //nuevo valor
            Persona p = controlador2.getPersona();
            //actualiza la persona
            datos.set(personasTableV.getSelectionModel().getSelectedIndex(), p);
            
        }
        
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        //============================================
        // borramos de la lista
        datos.remove(personasTableV.getSelectionModel().getSelectedIndex());

        //================================================
    }

    // Clase local al controlador
class ImagenTabCell extends TableCell<Persona, String> {
    private ImageView view = new ImageView();
    private Image imagen;
    @Override
    protected void updateItem(String t, boolean bln) {
        super.updateItem(t, bln);
        if (t == null || bln) {
            setText(null);
            setGraphic(null);
        } else {
            imagen = new Image(t, 25, 25, true, true);
            view.setImage(imagen);
            setGraphic(view);
            }
        }
    }

}


