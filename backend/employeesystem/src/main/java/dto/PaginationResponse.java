package dto;

import java.util.List;

public class PaginationResponse {

	private List<EmployeeResponse> emp;
	private int totalCount;
	private int totalPages;

	public PaginationResponse(List<EmployeeResponse> emp, int totalCount, int totalPages) {
		this.emp = emp;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
	}

	public PaginationResponse() {
		super();
	}

	public List<EmployeeResponse> getEmp() {
		return emp;
	}

	public void setEmp(List<EmployeeResponse> emp) {
		this.emp = emp;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
