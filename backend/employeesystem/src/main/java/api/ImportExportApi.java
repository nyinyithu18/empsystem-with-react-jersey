package api;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;

import dto.EmpLeaveResponse;
import services.EmpLeaveServices;

@Path("/empLeave")
public class ImportExportApi {

	@POST
	@Path("/uploadExcel")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void importExcel(@FormDataParam("file") InputStream fileInput) {

		try {

			EmpLeaveServices.importExcel(fileInput);
			// return Response.status(Response.Status.OK).entity("Excel file imported
			// successfully").build();
		} catch (Exception e) {
			e.printStackTrace();
			// return null;
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed
			// to import Excel file").build();
		}
	}

	@GET
	@Path("/empLeaveList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmpLeaveResponse> empLeaveList() {
		return EmpLeaveServices.empLeaveList();
	}

}
