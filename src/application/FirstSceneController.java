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

public class FirstSceneController {
	
	/*
	 * LLamando a todos los elementos FXML
	 */
	//Primera Scene
	@FXML private Button ingresar;
	@FXML private Button close;
	@FXML private Label contrasena;
	@FXML private Label usuario;
	@FXML private Label titulo;
	@FXML private Label mensaje;
	@FXML private TextField textUser;
	@FXML private TextField textPass;
	
	ConexionesExternas con = new ConexionesExternas();
	
	
	@FXML protected void ingresoDatos(ActionEvent event) throws SQLException, IOException {
		/*
		 * Inicializo todo lo necesario
		 */
		String usuario = textUser.getText().toString().trim();
		String clave = textPass.getText().toString().trim();
		boolean a = false;
		
		if(usuario.length()==0 && clave.length()==0) {
			this.mensaje.setText("Debe colocar Usuario y Contraseña");
			this.textUser.clear();
			this.textPass.clear();
		}
		else if (usuario.length()==0) {
			this.mensaje.setText("Debe colocar Usuario");
			this.textPass.clear();
		}
		else if (clave.length()==0) {
			this.mensaje.setText("Debe colocar Contraseña");
			this.textUser.clear();
		}
		else {
			this.mensaje.setText("");
			a = con.conexionAdmin(usuario, clave);
			if(a==true) {
				Parent loader = FXMLLoader.load(getClass().getResource("SecondScene.fxml"));
				Scene elegir = new Scene(loader);
				
				Stage newWindow;
				newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
				
				newWindow.setScene(elegir);
				newWindow.show();
			}
			else {
				this.mensaje.setText("No es un administrador");
				this.textUser.clear();
				this.textPass.clear();
			}
			
		}	
	}

	@FXML protected void cerrar() {
		Stage stage = (Stage)close.getScene().getWindow();
		stage.close();
	}
}
