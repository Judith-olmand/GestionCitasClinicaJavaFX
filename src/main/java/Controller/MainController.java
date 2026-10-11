package Controller;

import Clases.Cita;
import Clases.Cliente;
import Clases.Especialidad;
import DAO.CitaDAO;
import DAO.ClienteDAO;
import DAO.Conexion;
import DAO.EspecialidadDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import util.AlertUtils;

import java.time.LocalDate;
import java.util.Date;

public class MainController {
    //variable interna para usar en todos los métodos
    private int idClienteActual;

    private Cita citaSelec;


    public void inicializarDatos(int idCliente) {
        /**
         * Almaceno el id pasado por parámetro desde el formulario de login
         * en la variable interna de esta clase para poderla usar en otros métodos
         */
        this.idClienteActual = idCliente;

        //Creo un objeto de clienteDAO, que es donde están las consultas en la BBDD
        ClienteDAO clienteDAO = new ClienteDAO();
        /**
         * Llamo al método que realiza la consulta de obtener datos del cliente que se logueó
         * pasando por parámetro la conexión y el id
         */
        Cliente datosCliente = clienteDAO.obtenerDatosCliente(Conexion.conectar(),idClienteActual);

        /**
         * Si el resultado no es nulo, carga los datos en sus correspondientes casillas
         */
        if (datosCliente != null) {
            tfDni.setText(datosCliente.getDni());
            tfNombrePaciente.setText(datosCliente.getNombre());
            tfDireccion.setText(datosCliente.getDireccion());
            tfTelefono.setText(String.valueOf(datosCliente.getTelefono()));
            cbEspecialidad.setItems(new EspecialidadDAO().obtenerTodas(Conexion.conectar()));
        }else {
            AlertUtils.mostrarError(
                    "No existe el cliente", "¡ERROR!", "No existe el cliente."
            );
        }




    }

    @FXML
    private Button btBorrarCita;

    @FXML
    private Button btModificarCita;

    @FXML
    private Button btNuevaCita;

    @FXML
    private Button btVerCitas;

    @FXML
    //indico que serán objetos de la clase Especialidad
    private ComboBox<Especialidad> cbEspecialidad;

    @FXML
    private DatePicker dpikFecha;

    @FXML
    private Label lbDireccion;

    @FXML
    private Label lbDni;

    @FXML
    private Label lbEspecialidad;

    @FXML
    private Label lbFecha;

    @FXML
    private Label lbNombrePaciente;

    @FXML
    private Label lbNumCita;

    @FXML
    private Label lbTelefono;

    //------------------------------------------------------
    @FXML
    //indico el objeto y el tipo que va a mostrar
    private TableColumn<Cita, String> tcEspecialidad;
    @FXML
    private TableColumn<Cita, Date> tcFecha;
    @FXML
    private TableColumn<Cita, Integer> tcNumCita;
    //------------------------------------------------------

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfNombrePaciente;

    @FXML
    private TextField tfNumCita;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TableView<Cita> tvTabla;

//------------------------------------------------------------------------------------------------------------
//                                  CUANDO CLICO EN EL BOTÓN BORRAR CITA
//------------------------------------------------------------------------------------------------------------
    @FXML
    void onBorrarCitabt(ActionEvent event) {
        /**
         * "almaceno" en la variable los datos del objeto seleccionado en la tabla
         */
        citaSelec = tvTabla.getSelectionModel().getSelectedItem();

        //si fuera nulo porque no hay ninguna seleccionada, muestro un Alert con error.
        if (citaSelec == null) {
            AlertUtils.mostrarError(
                     "No hay ninguna cita seleccionada", "¡ERROR!", "Seleccione una cita de la lista antes de borrarla."
            );
        } else {
            /**
             * Si existe muestro un Alert de confirmación que me devuelve un boolean para capturar si el usuario
             * ha pulsado el botón de Aceptar o el de Cancelar
             */
            boolean confirmado = AlertUtils.mostrarAlerta( "Confirmación de borrado de cita",
                    "¿Desea borrar la cita seleccionada?", "");

            /**
             * Si el usuario ha clicado el de aceptar creo un nuevo objeto de la Clase cita
             * y llamo al método borrarCita de su clase, pasando por parámetro la conexión y el num de la cita
             */
            if (confirmado) {
                CitaDAO citaDAO = new CitaDAO();
                citaDAO.borrarCita(Conexion.conectar(), citaSelec.getNumeroCita());

                //borro los campos correspondientes a la cita
                tfNumCita.clear();
                dpikFecha.setValue(null);
                cbEspecialidad.getSelectionModel().clearSelection();
                /**
                 * Muestro un Alert de confirmación de que ha sido borrada.
                 */
                AlertUtils.mostrarConfirmacion("Cita borrada", "CONFIRMACIÓN", "Cita borrada con éxito.");
            }
        }
        //actualizo la tabla para que desaparezca la cita borrada
        onVerCitasbt(event);
    }


//------------------------------------------------------------------------------------------------------------
//                                  CUANDO CLICO EN EL BOTÓN MODIFICAR CITA
//------------------------------------------------------------------------------------------------------------
    @FXML
    void onModificarCitabt(ActionEvent event) {
        /**
         * "almaceno" en la variable los datos del objeto seleccionado en la tabla
         */
        citaSelec = tvTabla.getSelectionModel().getSelectedItem();

        //si fuera nulo porque no hay ninguna seleccionada, muestro un Alert con error.
        if (citaSelec == null) {
            AlertUtils.mostrarError(
                     "No hay ninguna cita seleccionada", "¡ERROR!", "Seleccione una cita de la lista antes de modificarla."
            );
        } else {
            /**
             * Si existe muestro un Alert de confirmación que me devuelve un boolean para capturar si el usuario
             * ha pulsado el botón de Aceptar o el de Cancelar
             */
            boolean confirmado = AlertUtils.mostrarAlerta( "Confirmación de modificado de cita",
                    "¿Desea modificar la cita seleccionada?", "");

            /**
             * Si el usuario ha clicado el de aceptar almaceno en variables los datos que hay en los campos
             * correspondientes a la cita
             */
            if (confirmado) {
                int numCita = Integer.parseInt(tfNumCita.getText());
                //extraigo la fecha del datePiker
                LocalDate fechaCita = dpikFecha.getValue();
                /**
                 * Compruebo que la fecha indicada no sea anterior a la fecha actual, si es así lanzo
                 * un Alert con un error
                 */
                if (fechaCita.isBefore(LocalDate.now())) {
                    AlertUtils.mostrarError( "Fecha pasada", "¡ERROR!", "Seleccione una fecha próxima.");
                } else {
                    /**
                     * Si la fecha es posterior
                     * la convierto en el tipo de fecha que pueda usar la base de datos
                     */
                    java.sql.Date fecha = java.sql.Date.valueOf(fechaCita);

                    /**
                     * creo un objeto de la Clase EspecialidadDAO para llamar al método que me dará el id
                     * de dicha especialidad, pasando por parámetro la conexión y el nombre de ésta, que será
                     * obtenido del campo combobox
                     * Lo almaceno en una variable.
                     */
                    EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                    int idespecialidad = especialidadDAO.obtenerIdEspecialidad(Conexion.conectar(), cbEspecialidad.getValue().toString());
                    /**
                     * Si no se puede obtener el id por algún error y me devuelve 0, lanzo un Alert con error
                     */
                    if (idespecialidad == 0) {
                        AlertUtils.mostrarError(
                                 "Error al obtener el id", "¡ERROR!", "Error al obtener el id de la especialidad");
                    } else {
                        /**
                         * Si me devuelve el id, creo un objeto de la clase CitaDAO y llamo al método para modificar
                         * la cita, pasando por parámetro la conexión, el numero de cita, la fecha y el id de la especialidad
                         */
                        CitaDAO citaDAO = new CitaDAO();
                        citaDAO.modificarCita(Conexion.conectar(), numCita, fecha , idespecialidad);
                        /**
                         * Muestro un Alert de confirmación de que ha sido modificada.
                         */
                        AlertUtils.mostrarConfirmacion( "Cita modificada", "CONFIRMACIÓN", "Cita modificada con éxito.");
                    }
                }
            }
        }
        //actualizo la tabla para que se actualicen los datos de la cita modificada
        onVerCitasbt(event);
    }


//------------------------------------------------------------------------------------------------------------
//                                  CUANDO CLICO EN EL BOTÓN NUEVA CITA
//------------------------------------------------------------------------------------------------------------
    @FXML
    void onNuevaCitabt(ActionEvent event) {
        /**
         * Al pulsar el botón muestro un Alert de confirmación que me devuelve un boolean para capturar si el usuario
         * ha pulsado el botón de Aceptar o el de Cancelar
         */
        boolean confirmado = AlertUtils.mostrarAlerta("Confirmación de nueva de cita",
                "¿Desea solicitar la nueva cita?", "");

        /**
         * Si el usuario ha clicado el de aceptar almaceno en variables los datos que hay en los campos
         * correspondientes a la cita
         */
        if (confirmado) {
            //extraigo la fecha del datePiker
            LocalDate fechaCita = dpikFecha.getValue();
            /**
             * Compruebo que la fecha indicada no sea anterior a la fecha actual, si es así lanzo
             * un Alert con un error
             */
            if (fechaCita.isBefore(LocalDate.now())) {
                AlertUtils.mostrarError( "Fecha pasada", "¡ERROR!", "Seleccione una fecha próxima.");
            } else {
                /**
                 * Si la fecha es posterior
                 * la convierto en el tipo de fecha que pueda usar la base de datos
                 */
                java.sql.Date fecha = java.sql.Date.valueOf(fechaCita);

                /**
                 * creo un objeto de la Clase EspecialidadDAO para llamar al método que me dará el id
                 * de dicha especialidad, pasando por parámetro la conexión y el nombre de ésta, que será
                 * obtenido del campo combobox
                 * Lo almaceno en una variable.
                 */
                EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                int idespecialidad = especialidadDAO.obtenerIdEspecialidad(Conexion.conectar(), cbEspecialidad.getValue().toString());

                /**
                 * Si no se puede obtener el id por algún error y me devuelve 0, lanzo un Alert con error
                 */
                if (idespecialidad == 0) {
                    AlertUtils.mostrarError(
                             "Error al obtener el id", "¡ERROR!", "Error al obtener el id de la especialidad");
                } else {
                    /**
                     * Si me devuelve el id, creo un objeto de la clase CitaDAO y llamo al método para modificar
                     * la cita, pasando por parámetro la conexión, el numero de cita, la fecha y el id de la especialidad
                     */
                    CitaDAO citaDAO = new CitaDAO();
                    citaDAO.aniadirCita(Conexion.conectar(),idClienteActual,fecha, idespecialidad);
                    /**
                     * Muestro un Alert de confirmación de que ha sido añadida.
                     */
                    AlertUtils.mostrarConfirmacion("Cita añadida", "CONFIRMACIÓN", "Cita añadida con éxito.");
                }
            }
        }
        //actualizo la tabla para que se actualicen los datos de la cita modificada
        onVerCitasbt(event);
    }

//------------------------------------------------------------------------------------------------------------
//                                  CUANDO CLICO EN EL BOTÓN VER CITAS
//------------------------------------------------------------------------------------------------------------
    @FXML
    void onVerCitasbt(ActionEvent event) {
        tcNumCita.setCellValueFactory(new PropertyValueFactory<>("numeroCita")); //--> ha de llamarse igual que el atributo de la clase Cita
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tcEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));

        //--------------------------------------------------------------------------------------------------------------------
        //              TROZO CREADO POR GEMINI PARA MOSTRAR LA FECHA FORMATEADA
        //--------------------------------------------------------------------------------------------------------------------
        tcFecha.setCellFactory(columna -> new javafx.scene.control.TableCell<Cita, java.util.Date>() {
            private final java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");

            @Override
            protected void updateItem(java.util.Date fecha, boolean vacio) {
                super.updateItem(fecha, vacio);
                if (vacio || fecha == null) {
                    setText(null);
                } else {
                    // Aplica el formato DD-MM-YYYY solo para mostrarlo en la celda
                    setText(formato.format(fecha));
                }
            }
        });
        //--------------------------------------------------------------------------------------------------------------------

        CitaDAO citaDAO = new CitaDAO();
        ObservableList<Cita> listaCitas = citaDAO.obtenerCitasCliente(Conexion.conectar(),idClienteActual);

        tvTabla.setItems(listaCitas);
    }


    private void cargarCita(Cita cita) {
        if (cita != null) {
            tfNumCita.setText(String.valueOf(cita.getNumeroCita()));
            dpikFecha.setValue(((java.sql.Date) cita.getFecha()).toLocalDate());
            for (Especialidad especialidad : cbEspecialidad.getItems()) {
                if (especialidad.getIdE() == cita.getIdEspecial()){
                    cbEspecialidad.setValue(especialidad);
                }
            }
        }
    }

    @FXML
    void citaSeleccionada(MouseEvent event) {
        System.out.println("1. ¡Clic detectado en la tabla!");
        citaSelec = tvTabla.getSelectionModel().getSelectedItem();
        cargarCita(citaSelec);
    }
}
