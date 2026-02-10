/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import static java.lang.Double.max;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import static javafxmlapplication.Utils.*;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    Circle ball;
    @FXML
    private GridPane grid;
    @FXML
    private ToggleButton myButton;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider radius;
    
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // One way
        /*
        radius.valueProperty().addListener((observable, oldVal, newVal) -> {
            double newRadius = max((double) newVal, 10.0);
            ball.setRadius(newRadius);
        });
        */
        //Other option
        //ball.radiusProperty().bind(radius.valueProperty()); // It is fine, but not complete
        ball.radiusProperty().bind(Bindings.min(radius.valueProperty(),   grid.heightProperty().divide(grid.getRowCount()*2.5) ) );
        
    }    
    
    @FXML
    public void handleKeyPressed(KeyEvent event){
        int row = GridPane.getRowIndex(ball);
        int column = GridPane.getColumnIndex(ball);
        if(null != event.getCode())switch (event.getCode()) {
            case UP:
                row--;
                row = rowNorm(grid, row);
                GridPane.setRowIndex(ball, row);
                break;
            case DOWN:
                row++;
                row = rowNorm(grid, row);
                GridPane.setRowIndex(ball, row);
                break;
            case LEFT:
                column--;
                column = columnNorm(grid, column);
                GridPane.setColumnIndex(ball, column);
                break;
            case RIGHT:
                column++;
                column = columnNorm(grid, column);
                GridPane.setColumnIndex(ball, column);
                break;
            default:
                break;
        }
    }
    
    //normalize any row
    public int rowNorm(GridPane grid, int row){
        int rowCount  = grid.getRowCount();
        return(row + rowCount)%rowCount;
    }
    
    
    //normalize any column
    public int columnNorm(GridPane grid, int column){
        int columnCount  = grid.getColumnCount();
        return(column + columnCount)%columnCount;
    }
    
    /*
     * EXCERCISE 2: Move by clicking
     * */
    @FXML
    public void handlePanePressed(MouseEvent event){
        double x = event.getSceneX();
        double y = event.getSceneY();
        
        int newX = columnCalc(grid, (int)x);
        int newY = rowCalc(grid, (int)y);
        System.out.printf("(%d, %d)\n", newX, newY);
 
        GridPane.setColumnIndex(ball, newX);
        GridPane.setRowIndex(ball, newY);
        
    }
    
    
    double X_ini, Y_ini;
    
    
    @FXML
    public void handleMousePressed(MouseEvent event){
        X_ini = event.getSceneX();
        Y_ini = event.getSceneY();
        System.out.printf("(%f, %f)\n", X_ini, Y_ini);
    }
    
    @FXML
    public void handleMouseDragged(MouseEvent event){
        ball.setTranslateX(event.getSceneX() - X_ini);
        ball.setTranslateY(event.getSceneY() - Y_ini);
    }
    
    @FXML
    public void handleMouseReleased(MouseEvent event){
        double x = event.getSceneX();
        double y = event.getSceneY();
        int next_X = columnCalc(grid, (int)x);
        int next_Y = rowCalc(grid, (int)y);
        System.out.printf("(%d, %d)\n", next_X, next_Y);
        
        GridPane.setConstraints(ball, next_X, next_Y);
       
        
        ball.setTranslateX(next_X);
        ball.setTranslateY(next_Y);
        event.consume();
    }
    
    
    public int columnCalc(GridPane grid, int x){
        int celdaWidth = (int)grid.getWidth() / grid.getColumnCount();
        return (int)(x/celdaWidth);
    }
    
    public int rowCalc(GridPane grid, int y){
        int celdaHeight = (int)grid.getHeight() / grid.getRowCount();
        return (int)(y/celdaHeight);
    }


    @FXML
   public void handleOnActionButton(MouseEvent event){  //We use the colorPicker just for flavour:  
       if(myButton.isSelected()){
           ball.setFill(Color.TRANSPARENT);          //setFill sets the propierty Fill(interior of the circle) to a value  
           ball.setStroke(colorPicker.getValue());   //setStroke sets the propierty Stroke(outline of circle) to a value
       }else{
           ball.setFill(colorPicker.getValue());
           ball.setStroke(Color.TRANSPARENT);
       }
   }

    
    
}
