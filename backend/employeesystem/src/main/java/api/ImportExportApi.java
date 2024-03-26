package api;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;

import dto.EmployeeResponse;
import dto.PaginationResponse;
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
	@Path("/export")
	@Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public Response empLeaveList(@QueryParam("search") String searchValue) {
		System.out.println(searchValue);
		return EmpLeaveServices.exportToExcel(searchValue);
	}

	@GET
	@Path("/findWithPager")
	@Produces(MediaType.APPLICATION_JSON)
	public PaginationResponse findWithPager(@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("limit") @DefaultValue("10") int limit, @QueryParam("search") String searchValue) {

		int startIndex = page * limit;
		List<EmployeeResponse> emp = EmpLeaveServices.findWithPager(startIndex, limit, searchValue);
		int totalCount = EmpLeaveServices.getTotalEmpCount(searchValue);
		int totalPages = EmpLeaveServices.calculateTotalPages(totalCount, limit);
		PaginationResponse response = new PaginationResponse(emp, totalCount, totalPages);

		return response;
	}

}
