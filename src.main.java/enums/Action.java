package enums;

public enum Action {
	LIST ("List"),
	SHOW ("Show"),
	CREATE ("Create"),
	UPDATE ("Update"),
	DELETE ("Delete");
   
	private String name;
   
	Action(String name){
		this.name = name;
	}
   
	public String toString(){
		return name;
	}
}
