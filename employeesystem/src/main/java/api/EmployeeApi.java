package api;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;

import dto.EmployeeRequest;
import dto.EmployeeResponse;
import dto.update.UpdateEmpData;
import services.EmployeeServices;

@Path("/employee")
public class EmployeeApi {

	@POST
	@Path("/addEmp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public EmployeeResponse addEmployee(@FormDataParam("emp_id") int emp_id, @FormDataParam("emp_name") String emp_name,
			@FormDataParam("nrc") String nrc, @FormDataParam("phone") String phone,
			@FormDataParam("email") String email, @FormDataParam("dob") String dob, @FormDataParam("rank") String rank,
			@FormDataParam("dep") String dep, @FormDataParam("address") String address,
			@FormDataParam("checkdelete") int checkdelete, @FormDataParam("image") InputStream file) {

		EmployeeRequest request = new EmployeeRequest(emp_id, emp_name, nrc, phone, email, dob, rank, dep, address,
				checkdelete, file);
		return EmployeeServices.addEmployee(request);
	}

	@GET
	@Path("/empList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmployeeResponse> empList() {
		return EmployeeServices.empList();
	}

	@PUT
	@Path("/editEmpData/{emp_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EmployeeResponse editEmpDatas(@PathParam("emp_id") int emp_id, EmployeeRequest empRequest) {

		UpdateEmpData updateEmpData = new UpdateEmpData(emp_id, empRequest.getEmp_name(), empRequest.getNrc(),
				empRequest.getPhone(), empRequest.getEmail(), empRequest.getDob(), empRequest.getRank(),
				empRequest.getDep(), empRequest.getAddress(), empRequest.getCheckdelete());
		return EmployeeServices.editEmpDataWithoutImage(emp_id, updateEmpData);
	}

	@PUT
	@Path("/editEmpDataWithImage/{emp_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public EmployeeResponse editEmpDatasWithImage(@FormDataParam("emp_id") int emp_id,
			@FormDataParam("emp_name") String emp_name, @FormDataParam("nrc") String nrc,
			@FormDataParam("phone") String phone, @FormDataParam("email") String email,
			@FormDataParam("dob") String dob, @FormDataParam("rank") String rank, @FormDataParam("dep") String dep,
			@FormDataParam("address") String address, @FormDataParam("checkdelete") int checkdelete,
			@FormDataParam("image") InputStream file) {

		EmployeeRequest request = new EmployeeRequest(emp_id, emp_name, nrc, phone, email, dob, rank, dep, address,
				checkdelete, file);
		return EmployeeServices.editEmpDataImage(emp_id, request);
	}

	@GET
	@Path("/empDetails/{emp_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EmployeeResponse getByEmpID(@PathParam("emp_id") int emp_id) {
		return EmployeeServices.getByEmpID(emp_id);
	}

	@GET
	@Path("/image/{emp_id}")
	@Produces("image/jpeg")
	public Response getImage(@PathParam("emp_id") int emp_id) throws FileNotFoundException {
		return EmployeeServices.getProfileImage(emp_id);
	}

}
