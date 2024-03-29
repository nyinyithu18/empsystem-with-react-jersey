package dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "departmentResponse")
public class DepartmentResponse {

	private int dep_id;
	private String dep_name;

	public DepartmentResponse() {
		super();
	}

	public DepartmentResponse(int dep_id, String dep_name) {
		this.dep_id = dep_id;
		this.dep_name = dep_name;
	}

	public int getDep_id() {
		return dep_id;
	}

	public void setDep_id(int dep_id) {
		this.dep_id = dep_id;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

}
