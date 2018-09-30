package comparator;

import java.util.Comparator;
import java.util.Map;

import valueobjects.Team;
import valueobjects.TeamResult;

public class compareByScore implements Comparator<Object> {
	Map<Integer,Team> map;
	
	public compareByScore(Map<Integer,Team> map)
	{
		this.map=map;
	}
	
	@Override
	public int compare(Object arg0, Object arg1) {
		TeamResult teamResult1=map.get((Integer)arg0).getTeamResult();
		TeamResult teamResult2=map.get((Integer)arg1).getTeamResult();
		Integer score1=(teamResult1 == null)?0:teamResult1.getTotalScore();
		Integer score2=(teamResult2 == null)?0:teamResult2.getTotalScore();
		return -score1.compareTo(score2);
	}

}
