package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtils {

    public static void mostrarError (String tituloAlert, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(tituloAlert);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static boolean mostrarAlerta(String tituloAlert, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(tituloAlert);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> resultado = alert.showAndWait(); //pausa el programa y recoge el botón pulsado
        // devuelve true si el botón pulsado es aceptar y false si pulso cancelar
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    public static void mostrarConfirmacion(String tituloAlert, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tituloAlert);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
