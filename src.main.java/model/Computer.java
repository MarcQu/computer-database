package model;

import java.sql.Timestamp;

public class Computer {
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private int companyId;
	
	/**
	 * Modèle Computer.
	 * @param id l'id de l'ordinateur
	 * @param name le nom de l'ordinateur
	 * @param introduced la date d'introduction de l'ordinateur
	 * @param discontinued la date d'interruption de l'ordinateur
	 * @param companyId l'id de la companie de l'ordinateur
	 */
	public Computer(int id, String name, Timestamp introduced, Timestamp discontinued, int companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;		
		this.companyId = companyId;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Timestamp getIntroduced() {
		return this.introduced;
	}
	
	public Timestamp getDiscontinued() {
		return this.discontinued;
	}
	
	public int getCompanyId() {
		return this.companyId;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
