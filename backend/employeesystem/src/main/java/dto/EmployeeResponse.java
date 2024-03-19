package dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employeeResponse")
public class EmployeeResponse {
	private int emp_id;
	private String emp_name;
	private String nrc;
	private String phone;
	private String email;
	private String dob;
	private String rank;
	private String dep;
	private String address;
	private int checkdelete;
	// private String filePath;

	public EmployeeResponse() {
		super();
	}

	public EmployeeResponse(int emp_id, String emp_name, String nrc, String phone, String email, String dob,
			String rank, String dep, String address, int checkdelete) {
		super();
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.nrc = nrc;
		this.phone = phone;
		this.email = email;
		this.dob = dob;
		this.rank = rank;
		this.dep = dep;
		this.address = address;
		this.checkdelete = checkdelete;
		// this.filePath = filePath;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCheckdelete() {
		return checkdelete;
	}

	public void setCheckdelete(int checkdelete) {
		this.checkdelete = checkdelete;
	}
	/*
	 * public String getFilePath() { return filePath; }
	 * 
	 * public void setFilePath(String filePath) { this.filePath = filePath; }
	 */
}
