package api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.RankResponse;
import services.RankServices;

@Path("/rank")
public class RankApi {

	@GET
	@Path("/rankList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RankResponse> rankList() {
		return RankServices.rankList();
	}
}
