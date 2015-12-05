package co.in.db;

public class StudentDao {

	private String name;
	private Integer rollNo;
	private String location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRollNo() {
		return rollNo;
	}

	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "[Name: " + name + " , " + "RollNo: " + rollNo + " , " + "Location: " + location + "]";
	}

}
