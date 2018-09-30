package valueobjects;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Assignment implements Serializable{

	private static final long serialVersionUID = 7269940265505936933L;
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private boolean active;
	private Map<Team, TeamResult> teamResultVO;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Map<Team, TeamResult> getTeamResult() {
		if(teamResultVO==null)
		{
			teamResultVO=new HashMap<>();
		}
		return teamResultVO;
	}

	public void addTeamResult(Team team, TeamResult teamResult) {
		getTeamResult().put(team, teamResult);
	}

	public void setTeamResult(Map<Team, TeamResult> teamResultVO) {
		this.teamResultVO = teamResultVO;
	}

	
}
