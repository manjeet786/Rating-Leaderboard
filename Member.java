package valueobjects;

import java.io.Serializable;

public class Member implements Serializable {

	private static final long serialVersionUID = 4906733265255330905L;
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
