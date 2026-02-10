package modelo;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona {
	
	private final StringProperty nombre = new SimpleStringProperty();
	private final StringProperty apellidos = new SimpleStringProperty();
        private String imagen = new String();
		
	public Persona(String nombre, String apellidos, String i)
	{
		this.nombre.setValue(nombre);
		this.apellidos.setValue(apellidos);
                this.imagen = i;
	}
	
	public  StringProperty NombreProperty() {
		return this.nombre;
	}
	public String getNombre() {
		return this.NombreProperty().get();
	}
	public final void setNombre(String Nombre) {
		this.NombreProperty().set(Nombre);
	}
	public  StringProperty ApellidosProperty() {
		return this.apellidos;
	}
	public String getApellidos() {
		return this.ApellidosProperty().get();
	}
	public  void setApellidos(String Apellidos) {
		this.ApellidosProperty().set(Apellidos);
	}
        public String getImagen() {
		return this.imagen;
	}
	public final void setImagen(String path) {
		this.imagen = path;
        }

}