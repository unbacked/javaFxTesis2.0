package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DataController {
	@FXML private Label titulo;
	@FXML private Label nombre;
	@FXML private Label apellido;
	@FXML private Label cargo;
	@FXML private Label user;
	@FXML private Label pass;
	@FXML private Label mensaje;
	@FXML private TextField textNombre;
	@FXML private TextField textApellido;
	@FXML private TextField textCargo;
	@FXML private TextField textUser;
	@FXML private TextField textPass;
	@FXML private Button inDatos;
	@FXML private Button video;
	@FXML private Button regresar;
	
	ConexionesExternas con = new ConexionesExternas();
	
	@FXML protected void perfiles(ActionEvent event) {
		String cargo = textCargo.getText().toLowerCase().trim();
		if (cargo.contains("seguridad") || cargo.contains("admin")) {
			textUser.setDisable(false);
			textPass.setDisable(false);
		}
		else {
			textUser.setDisable(true);
			textPass.setDisable(true);
		}
	}
	
	@FXML protected void ingresoDatos() throws SQLException {
		String nombre = textNombre.getText();
		String apellido = textApellido.getText();
		String cargo = textCargo.getText().toLowerCase();
		String usuario = textUser.getText();
		String clave = textPass.getText();
		
		int perfil = 0;
		
		if(nombre.length()==0|| apellido.length()==0 || cargo.length()==0) {
			mensaje.setText("Error: faltan datos");
		}
		if(cargo.contains("seguridad") || cargo.contains("admin")) {
			if (usuario.length()==0 || clave.length()==0) {
				mensaje.setText("Error: falta usuario o contraseña");
			}
			if(cargo.contains("seguridad")) {
				perfil = 2;
			}
			else if (cargo.contains("admin")) {
				perfil = 1;
			}
			else {
				perfil = 0;
			}
			if(nombre.length()!=0 && apellido.length()!=0 && cargo.length()!=0 && usuario.length()!=0 && clave.length()!=0) {
				mensaje.setText("Datos Ingresados");
				con.conexionDBnormal(nombre, apellido, cargo, usuario, clave, perfil);
			}
		}
		if (nombre.length()!=0 && apellido.length()!=0 && cargo.length()!=0 && perfil == 0) {
			mensaje.setText("Datos Ingresados");
			con.conexionDBnormal(nombre, apellido, cargo, usuario, clave, perfil);
			video.setDisable(false);
		}
	}
	
	@FXML protected void tomaVideo(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("VideoCapture.fxml"));
		Scene captura = new Scene(loader);
		
		Stage newWindow;
		newWindow =(Stage)((Node)event.getSource()).getScene().getWindow();
		
		newWindow.setWidth(700);
		newWindow.setHeight(600);
		newWindow.setScene(captura);
		newWindow.show();		
	}
	
	@FXML protected void inicio(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("SecondScene.fxml"));
		Scene inicio = new Scene(loader);
		
		Stage window;
		window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setWidth(310);
		window.setHeight(170);
		window.setScene(inicio);
		window.show();
	}
	
	
}
