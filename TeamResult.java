package valueobjects;

import java.io.Serializable;

public class TeamResult implements Serializable {

	private static final long serialVersionUID = 8243116116352987975L;
	private Integer score;
	private TeamResult teamResult;
	
	public TeamResult(TeamResult teamResult)
	{
		this.teamResult=teamResult;
	}

	public int getScore() {
		return score==null?0:score;
	}

	public void setScore(int score) {
		this.score=score;
	}
	
	public int getTotalScore()
	{
		if(teamResult==null)
		{
			return getScore();
		}
		return teamResult.getTotalScore()+getScore();
	}

}
