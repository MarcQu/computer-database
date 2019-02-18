package model;

public class Company {
	private Integer id;
	private String name;
	
	/**
	 * Contructeur Company vide.
	 */
	public Company() {
	}
	
	/**
	 * Constructeur Company.
	 * @param id l'id de la companie
	 * @param name le nom de la companie
	 */
	public Company(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		String chaine = "Companie : ";
		if (this.id != null) {
			chaine += this.id + " ";
		}
		if (this.name != null) {
			chaine += this.name;
		}
		return chaine;
	}
}
