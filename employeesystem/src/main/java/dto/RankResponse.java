package dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rankResponse")
public class RankResponse {

	private int rank_id;
	private String rank_name;

	public RankResponse() {
		super();
	}

	public RankResponse(int rank_id, String rank_name) {
		this.rank_id = rank_id;
		this.rank_name = rank_name;
	}

	public int getRank_id() {
		return rank_id;
	}

	public void setRank_id(int rank_id) {
		this.rank_id = rank_id;
	}

	public String getRank_name() {
		return rank_name;
	}

	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}

}
