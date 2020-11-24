package cl.ubb.scrumitos.model;

import javax.persistence.Entity;


@Entity
public class Funcionario {
 
	private String run;
	private String nombre;
	private String apellido;
	private String cargo;
	private String telefono;
	private String email;
	private int idFuncionario;
	
	public Funcionario() {}
	
	public Funcionario(String run, String nombre, String apellido, String cargo, String telefono, String email,int idFuncionario) {
		this.run = run;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cargo = cargo;
		this.telefono = telefono;
		this.email = email;
		this.idFuncionario = idFuncionario;
	}


	public void setRun(String run) {
		this.run = run;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}


	public String getRun() {
		// TODO Auto-generated method stub
		return run;
	}
	
	
	
	
	
}
