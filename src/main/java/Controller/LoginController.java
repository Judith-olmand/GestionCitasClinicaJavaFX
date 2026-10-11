package Controller;

import DAO.ClienteDAO;
import DAO.Conexion;
import Seguridad.PasswordService;
import com.example.gestioncitasclinica.Launcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.AlertUtils;

import java.io.IOException;

public class LoginController {
    private final PasswordService passwordService = new PasswordService();


    @FXML
    private Button btLogin;

    @FXML
    private Label lbID;

    @FXML
    private Label lbPW;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private Text textTitulo;

    @FXML
    private TextField tfIdUsuario;

    @FXML
    void onLoginbt(ActionEvent event) {
        String id = tfIdUsuario.getText();
        String password = pfContrasenia.getText();
        ClienteDAO clienteDAO = new ClienteDAO();

        if(id.isEmpty() || password.isEmpty()){
            AlertUtils.mostrarError( "Campos vacíos.", "¡ERROR!", "Por favor, rellene todos los datos antes de continuar.");
        } else {
            try {
                int idUsuario = Integer.parseInt(id);
                String hash = clienteDAO.obtenerPasswordHash(Conexion.conectar(), idUsuario);

                if (hash != null && passwordService.verifyPassword(password, hash)) {
                    try {
                        //PARA ABRIR LA VENTANA DEL FORMULARIO
                        /**
                         * ORDEN PARA PASAR EL ID INTRODUCIDO AL SIGUIENTE FORMULARIO
                         * 1º PREPARO EL FXM
                         */
                        FXMLLoader fxmlLoader = new FXMLLoader(com.example.gestioncitasclinica.HelloApplication.class.getResource("main-view.fxml"));
                        /**
                         * 2º CARGAR FXM Y GUARDAR EN PARENT ROOT
                         */
                        javafx.scene.Parent root = fxmlLoader.load();
                        /**
                         * 3º OBTENER EL CONTROLADOR DE LA NUEVA VENTANA
                         */
                        MainController mainController = fxmlLoader.getController();
                        /**
                         * 4º PASAR EL ID
                         * Llamar al método pasando el id que hemos guardado
                         * para que se ejecute al abrirse el nuevo formulario
                         */
                        mainController.inicializarDatos(idUsuario);
                        /**
                         * 5º CREAR Y CARGAR LA ESCENA CARGADA EN EL PASO 2
                         */
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Hello!");
                        stage.setScene(scene);
                        stage.show();

                        //PARA CERRAR LA VENTANA LOGIN
                        Stage ventanaLogin = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                        ventanaLogin.close();


                    } catch (IOException e) {
                        System.out.println("Error al cargar la nueva ventana" + e.getMessage());
                    }
                } else {
                    AlertUtils.mostrarError( "Login incorrecto", "¡ERROR!", "Usuario o contraseña incorrectos.");
                }
            } catch (NumberFormatException e) {
                AlertUtils.mostrarError( "Formato de id incorrecto", "¡ERROR!", "El id ha de ser numérico.");
            }
        }

    }
}
