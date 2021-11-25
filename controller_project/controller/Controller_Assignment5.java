package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXB;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller_Assignment5 {
    private enum PenSize {

        SMALL(2),
        MEDIUM(6),
        LARGE(10);

        final int radius;

        PenSize(int radius) {
            this.radius = radius;
        }

    }

    private enum DrawColor {

        BLACK(Color.BLACK),
        RED(Color.RED),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE);

        final Color color;

        DrawColor(Color color) {
            this.color = color;
        }
    }

    private PenSize penSize = PenSize.MEDIUM;
    private DrawColor drawColor = DrawColor.BLACK;

    @FXML
    private RadioButton rbBlack;

    @FXML
    private ToggleGroup tgColor;

    @FXML
    private RadioButton rbGreen;

    @FXML
    private RadioButton rbRed;

    @FXML
    private RadioButton rbBlue;

    @FXML
    private RadioButton rbSmall;

    @FXML
    private ToggleGroup tgPenSize;

    @FXML
    private RadioButton rbMed;

    @FXML
    private RadioButton rbLarge;

    @FXML
    private Button btnUndo;

    @FXML
    private Button btnClear;

    @FXML
    private Pane panelDraw;

    @FXML
    void drawingAreaMouseDragged(MouseEvent event) {

        panelDraw.getChildren().add(new Circle(event.getX(), event.getY(), penSize.radius, drawColor.color));
    }

    @FXML
    void colorChangeEventHandler(ActionEvent event) {
        if (rbBlack.isSelected())
            drawColor = DrawColor.BLACK;
        else if (rbGreen.isSelected())
            drawColor = DrawColor.GREEN;
        else if (rbRed.isSelected())
            drawColor = DrawColor.RED;
        else
            drawColor = DrawColor.BLUE;
    }

    @FXML
    void penSizeChangeEventHandler(ActionEvent event) {

        if (rbSmall.isSelected())
            penSize = PenSize.SMALL;
        else if (rbMed.isSelected())
            penSize = PenSize.MEDIUM;
        else
            penSize = PenSize.LARGE;
    }

    @FXML
    void undoButtonEventHandler(ActionEvent event) {

        panelDraw.getChildren().remove(panelDraw.getChildren().size() - 1);
    }

    @FXML
    void clearButtonEventHandler(ActionEvent event) {

        panelDraw.getChildren().clear();
    }

    @FXML
    void initialize() {
        assert rbBlack != null : "fx:id=\"rbBlack\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert tgColor != null : "fx:id=\"tgColor\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert rbGreen != null : "fx:id=\"rbGreen\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert rbRed != null : "fx:id=\"rbRed\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert rbBlue != null : "fx:id=\"rbBlue\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert rbSmall != null : "fx:id=\"rbSmall\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert tgPenSize != null : "fx:id=\"tgPenSize\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert rbMed != null : "fx:id=\"rbMed\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert rbLarge != null : "fx:id=\"rbLarge\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert btnUndo != null : "fx:id=\"btnUndo\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Assignment5.fxml'.";
        assert panelDraw != null : "fx:id=\"panelDraw\" was not injected: check your FXML file 'Assignment5.fxml'.";
    }
}
