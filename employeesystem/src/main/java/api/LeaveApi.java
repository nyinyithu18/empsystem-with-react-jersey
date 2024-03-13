package api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.LeaveRequest;
import dto.LeaveResponse;
import dto.update.EditLeaveDatas;
import services.LeaveServices;

@Path("/leave")
public class LeaveApi {

	@POST
	@Path("/addLeave")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LeaveResponse addLeave(LeaveRequest leaveRequest) {
		LeaveRequest addLeaveRequest = new LeaveRequest(leaveRequest.getEmp_id(), leaveRequest.getLeave_type(),
				leaveRequest.getFrom_date(), leaveRequest.getTo_date(), leaveRequest.getDays(),
				leaveRequest.getDeleted());
		return LeaveServices.addLeave(addLeaveRequest);
	}

	@GET
	@Path("/leaveList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LeaveResponse> leaveList() {
		return LeaveServices.leaveList();
	}

	@GET
	@Path("/leaveDetails/{leave_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LeaveResponse findByLeaveId(@PathParam("leave_id") int leave_id) {
		return LeaveServices.findByLeaveId(leave_id);
	}

	@DELETE
	@Path("/deleteLeaveId/{leave_id}")
	public void deleteLeaveId(@PathParam("leave_id") int leave_id) {
		LeaveServices.deleteLeaveId(leave_id);
	}

	@PUT
	@Path("/editLeaveData/{leave_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LeaveResponse editLeaveDatas(@PathParam("leave_id") int leave_id, LeaveRequest leaveRequest) {

		EditLeaveDatas editleavedatas = new EditLeaveDatas(leave_id, leaveRequest.getEmp_id(),
				leaveRequest.getLeave_type(), leaveRequest.getFrom_date(), leaveRequest.getTo_date(),
				leaveRequest.getDays(), leaveRequest.getDeleted());
		return LeaveServices.editLeaveData(leave_id, editleavedatas);
	}
}
