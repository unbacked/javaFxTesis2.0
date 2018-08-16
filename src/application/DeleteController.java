package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DeleteController implements Initializable {
	
	@FXML private Button regresar;
	@FXML private Button eliminar;
	@FXML private Label mensaje;
	@FXML private Label titulo;
	@FXML private TableView <Person> tabla;
	@FXML private TableColumn <Person, String> idColumn;
	@FXML private TableColumn <Person, String> nomColumn;
	@FXML private TableColumn <Person, String> apColumn;
	@FXML private TableColumn <Person, String> carColumn;
	@FXML private TableColumn <Person, String> userColumn;
	@FXML private TableColumn <Person, String> passColumn;
	
	private ObservableList<Person> people = FXCollections.observableArrayList();
	
	protected void llenarTabla() throws SQLException{
		ConexionesExternas con = new ConexionesExternas();
		
		con.conexionTabla(tabla, people);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		nomColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("nombre"));
		apColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("apellido"));
		carColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("cargo"));
		userColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("user"));
		passColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("pass"));
		
		tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		try {
			llenarTabla();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML protected void eliminar() throws SQLException {
		ObservableList<Person> selectedRows, allPeople;
		allPeople = tabla.getItems();
		
		selectedRows = tabla.getSelectionModel().getSelectedItems();
		ConexionesExternas con = new ConexionesExternas();
		con.eliminarUsuario(selectedRows);
		allPeople.removeAll(selectedRows);
	}
	
	@FXML protected void regresar(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("SecondScene.fxml"));
		Scene second = new Scene(loader);
		
		Stage newWindow;
		newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		newWindow.setScene(second);
		newWindow.show();
	}
}
