package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SecondSceneController {
	/*
	 * Llamo todos los elementos FXML
	 */
	@FXML private Button ingreso;
	@FXML private Button eliminar;
	@FXML private Button salir;
	@FXML private Label mensaje;
	
	
	@FXML protected void ingUser(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("Ingreso.fxml"));
		Scene datos = new Scene(loader);
		
		Stage newWindow;
		newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		newWindow.setWidth(290);
		newWindow.setHeight(320);
		newWindow.setScene(datos);
		newWindow.show();
	}
	
	@FXML protected void eliUser(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("Delete.fxml"));
		Scene borrar = new Scene(loader);
		
		Stage newWindow;
		newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		newWindow.setWidth(490);
		newWindow.setHeight(590);
		newWindow.setScene(borrar);
		newWindow.show();
	}
	
	@FXML protected void salirSistema(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("FirstScene.fxml"));
		Scene inicio = new Scene(loader);
		
		Stage newWindow;
		newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		newWindow.setWidth(290);
		newWindow.setHeight(215);
		newWindow.setScene(inicio);
		newWindow.show();
	}
	
}
