package dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "leaveRequest")
public class LeaveRequest {

	private int leave_id;
	private int emp_id;
	private String leave_type;
	private String from_date;
	private String to_date;
	private int days;
	private int deleted;

	public LeaveRequest() {
		super();
	}

	public LeaveRequest(int emp_id, String leave_type, String from_date, String to_date, int days, int deleted) {
		super();
		this.emp_id = emp_id;
		this.leave_type = leave_type;
		this.from_date = from_date;
		this.to_date = to_date;
		this.days = days;
		this.deleted = deleted;
	}

	public int getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(int leave_id) {
		this.leave_id = leave_id;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getLeave_type() {
		return leave_type;
	}

	public void setLeave_type(String leave_type) {
		this.leave_type = leave_type;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

}
