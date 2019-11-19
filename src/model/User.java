package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Table(name = "USER")
@Entity
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // nicht alle DB unterstutzen id autoincrement
	private int id;
	
	@Column(unique = true)
	private String username;
	private String passwort;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String username, String passwort) {
		super();
		this.username = username;
		this.passwort = passwort;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwort=" + passwort + "]";
	}
	
	
}
