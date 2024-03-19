package api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.DepartmentResponse;
import services.DepartmentServices;

@Path("/dep")
public class DepartmentApi {

	@GET
	@Path("/depList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DepartmentResponse> depList() {
		return DepartmentServices.depList();
	}
}
