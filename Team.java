package valueobjects;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Team implements Serializable {

	private static final long serialVersionUID = 4393342983697977773L;
	private int id;
	private String name;
	private Set<Member> members;
	private TeamResult teamResult;

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

	public Set<Member> getMembers() {
		if (members == null) {
			members = new HashSet<Member>();
		}
		return members;
	}

	public void addMember(Member member) {
		getMembers().add(member);
	}

	public void removeMember(Member member) {
		getMembers().remove(member);
	}

	public TeamResult getTeamResult() {
		return teamResult;
	}

	public void setTeamResult(TeamResult teamResult) {
		this.teamResult = teamResult;
	}
}
