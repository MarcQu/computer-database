package enums;

public enum Table {
	COMPANY ("company"),
	COMPUTER ("computer");
	
	private String name;
   
	Table(String name){
		this.name = name;
	}
   
	public String toString(){
		return name;
	}
}
