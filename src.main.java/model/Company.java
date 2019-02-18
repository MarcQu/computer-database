package model;

public class Company {
	private int id;
	private String name;
	
	/**
	 * Modèle Company.
	 * @param id l'id de la companie
	 * @param name le nom de la companie
	 */
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
