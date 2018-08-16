package application;

/*import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;*/

import java.sql.*;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

//import org.apache.commons.net.ftp.*;

public class ConexionesExternas {
	
	protected void conexionDBnormal(String nombre, String apellido, String cargo, String user, String pass, int perfil) throws SQLException {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			/*
			 * Conexion con la DB
			 */
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tesis","root","");
			/*
			 * Creamos el estado de la conexion
			 */
			myStmt = myConn.createStatement();
			
			myStmt.executeUpdate("INSERT INTO empleados ("
					+"nombre, "
					+"apellido, "
					+"cargo, "
					+"user, "
					+"clave, "
					+"perfil)"
					+"VALUES ("
					+ "'"+nombre+"','"+apellido+"','"+cargo+"','"+user+"','"+pass+"','"+perfil+"')");
			/*
			 * Ejecuto el query
			 */
			myRs = myStmt.executeQuery("select * from empleados where id="+1);
			/*
			 * Proceso el resultado
			 */
			while(myRs.next()) {
				System.out.println(myRs.getString("nombre")+ ", "+myRs.getString("apellido")+", "+myRs.getString("cargo"));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		}
	}
	
	protected boolean conexionAdmin(String user, String pass) throws SQLException {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;	
		boolean autorizacion = false;
		//String clave[];
		//String usuario[];
		try {
			/*
			 * Conexion con la DB
			 */
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tesis", "root", "");
			/*
			 * Creamos el estado de la conexion
			 */
			myStmt = myConn.createStatement();
			
			myStmt.executeQuery("SELECT "
					+ "user, "
					+ "clave "
					+ "FROM empleados WHERE perfil="+1);
			/*
			 * Ejecuto el query
			 */
			myRs = myStmt.executeQuery("select * from empleados where perfil="+1+"&& user="+" '"+user+"' && clave="+" '"+pass+"'");
			/*
			 * Proceso el resultado
			 */
			while(myRs.next()) {
				String clave = myRs.getString("clave");
				String usuario = myRs.getString("user");
				if(pass.contains(clave) && user.contains(usuario)) {
					System.out.println("Acceso autorizado");
					autorizacion = true;
				}
				else {
					autorizacion = false;
				}
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		}
		return autorizacion;
	}
	
	protected void conexionTabla(TableView<Person> list,  ObservableList<Person> items) throws SQLException {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tesis", "root", "");
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM empleados");
			while (myRs.next()) {
				items.add(new Person(myRs.getString("id"), myRs.getString("nombre"), myRs.getString("apellido"), myRs.getString("cargo"), myRs.getString("user"), myRs.getString("clave")));
			}
		}
		catch (SQLException e) {
	
		}
		list.setItems(items);
	}
	
	protected void eliminarUsuario(ObservableList<Person> items) throws SQLException {
		Connection myConn = null;
		String id = items.get(0).getNombre();
		String query = "DELETE FROM empleados WHERE id=?";
		
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tesis","root","");
			
			PreparedStatement stmt = myConn.prepareStatement(query);
			stmt.setInt(1, Integer.parseInt(id));
			int result = stmt.executeUpdate();
			if (result >0) {
				System.out.println("deleted");
			}
			else {
				System.out.println("Can´t delete");
			}
		}
		catch (Exception ex) {
			System.out.print("ERROR");
		}
		finally {
			try {
				myConn.close();
			}
			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
