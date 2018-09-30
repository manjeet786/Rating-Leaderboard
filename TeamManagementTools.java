package teammanagementhelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import comparator.compareByScore;
import exceptionhandling.ManagementToolsException;
import valueobjects.Assignment;
import valueobjects.Member;
import valueobjects.Team;
import valueobjects.TeamResult;

public class TeamManagementTools {

	private Map<Integer, Member> memberVO;
	private Map<Integer, Team> teamVO;
	private Map<Integer, Assignment> assignmentVO;

	public Map<Integer, Member> getMemberVO() {
		if (memberVO == null) {
			memberVO = new HashMap<>();
		}
		return memberVO;
	}

	public Map<Integer, Team> getTeamVO() {
		if (teamVO == null) {
			teamVO = new HashMap<>();
		}
		return teamVO;
	}

	public Map<Integer, Assignment> getAssignmentVO() {
		if (assignmentVO == null) {
			assignmentVO = new HashMap<>();
		}
		return assignmentVO;
	}

	@SuppressWarnings("unchecked")
	public void loadObjects(String fileName) throws ManagementToolsException {
		File inputFile = new File(fileName);
		if (!inputFile.exists()) {
			throw new ManagementToolsException("Invalid file path");
		}
		try {
			FileInputStream fis = new FileInputStream(inputFile);
			if (fis.available() == 0) {
				System.out.println("No objects Available");
				fis.close();
				return;
			}
			ObjectInputStream ois = new ObjectInputStream(fis);
			memberVO = (Map<Integer, Member>) ois.readObject();
			teamVO = (Map<Integer, Team>) ois.readObject();
			assignmentVO = (Map<Integer, Assignment>) ois.readObject();
			ois.close();
			System.out.println("Objects loaded successfully");
		} catch (Exception e) {
			System.out.println("Failed to load objects");
			throw new ManagementToolsException(e.getMessage());
		}
	}

	public void updateDetails(String fileName) throws IOException {
		if (memberVO == null || teamVO == null || assignmentVO == null) {
			System.out.println("Nothing to save");
			return;
		}
		File outputFile = new File(fileName);
		if (outputFile.exists()) {
			outputFile.delete();
		}
		try {
			outputFile.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
			oos.writeObject(getMemberVO());
			oos.writeObject(getTeamVO());
			oos.writeObject(getAssignmentVO());
			oos.close();
			System.out.println("SESSION SAVED SUCCESSFULLY");
		} catch (Exception e) {
			System.out.println("Object storing failed");
		}

	}

	public void createTeam(BufferedReader br) throws Exception {

		System.out.println("Enter the team ID :");
		int teamId = getNumericalInput(br);
		if (getTeamVO().containsKey(teamId)) {
			System.out.println("Team Already exists");
			return;
		}
		Team team = new Team();
		team.setId(teamId);
		System.out.println("Enter the team name : (max. 25 characters)");
		String name;
		while ((name = br.readLine()).length() > 25) {
			System.out.println("Name exceeds 25 characters. Please enter again.");
		}
		team.setName(name);
		System.out.println("Enter the number of team members :");
		int num = getNumericalInput(br);
		for (int i = 1; i <= num; i++) {
			addMemberHelper(br, team, i + " ");
		}
		getTeamVO().put(team.getId(), team);

		System.out.println("TEAM CREATION SUCCESSFUL");
	}

	public void removeTeam(BufferedReader br) throws Exception {

		if (getTeamVO().size() == 0) {
			System.out.println("No Team Exists");
			return;
		}
		printTeams();
		System.out.println("Enter the teamId to be deleted :");
		int id = getNumericalInput(br);
		if (getTeamVO().containsKey(id)) {
			getTeamVO().remove(id);
			System.out.println("TEAM REMOVAL SUCCESSFUL");
		} else {
			System.out.println("Team Does not Exist");
		}
	}

	public void changeTeamName(BufferedReader br) throws Exception {
		if (getTeamVO().size() == 0) {
			System.out.println("No Team Exists");
			return;
		}
		printTeams();
		System.out.println("Enter the teamId to be change team name :");
		int id = getNumericalInput(br);
		if (getTeamVO().containsKey(id)) {
			System.out.println("Enter new Team name : (max. 25 characters)");
			String name;
			while ((name = br.readLine()).length() > 25) {
				System.out.println("Name exceeds 25 characters. Please enter again.");
			}
			getTeamVO().get(id).setName(name);
		} else {
			System.out.println("Team Does not Exist");
		}

		System.out.println("TEAM NAME CHANGED SUCCESSFULLY");
	}

	public void addMember(BufferedReader br) throws NumberFormatException, IOException {
		if (getTeamVO().size() == 0) {
			System.out.println("No team Exists");
			return;
		}
		printTeams();
		System.out.println("Enter Team ID");
		int teamId = getNumericalInput(br);
		if (!getTeamVO().containsKey(teamId)) {
			System.out.println("Team Not Found");
			return;
		}
		Team team = getTeamVO().get(teamId);
		addMemberHelper(br, team, "");
		System.out.println("MEMBER ADDITION SUCCESSFUL");
		/*
		 * System.out.println("Enter Member ID"); int id =
		 * getNumericalInput(br); if (!members.containsKey(id)) {
		 * System.out.println("Member Not Present"); } else { Set<Member> set =
		 * team.getSet(); if (set.isEmpty()) { set.add(members.get(id)); } else
		 * { if (set.contains(members.get(id))) {
		 * System.out.println("Member Already Present in team"); } else {
		 * System.out.println(members.get(id).toString());
		 * set.add(members.get(id)); } }
		 * 
		 * }
		 */ }

	public void removeMember(BufferedReader br) throws NumberFormatException, IOException {
		printTeams();
		System.out.println("Enter Team ID");
		int teamId = getNumericalInput(br);
		if (!getTeamVO().containsKey(teamId)) {
			System.out.println("Team Not Found");
			return;
		}
		Team team = getTeamVO().get(teamId);
		// Set<Member> set = team.getMembers();
		System.out.println("Enter Member ID you want to remove");
		int id = getNumericalInput(br);
		if (!getMemberVO().containsKey(id)) {
			System.out.println("Member does not exist");
			return;
		}
		if (!team.getMembers().contains(getMemberVO().get(id))) {
			System.out.println("Member does not exist");
			return;
		}
		team.getMembers().remove(getMemberVO().get(id));
		System.out.println("MEMBER REMOVAL SUCCESSFUL");
		/*
		 * boolean found = false; for (Member member : set) { if
		 * (member.getMemberID() == id) {
		 * System.out.println(members.get(id).toString()); set.remove(member);
		 * found = true; break; } else {
		 * System.out.println("This team does not contains the given member"); }
		 * } if (found) { System.out.println("Member Removed from " +
		 * teamMap.get(teamId).getTeamName()); }
		 */

	}

	public void findMembers(BufferedReader br) throws NumberFormatException, IOException {
		if (getTeamVO().size() == 0) {
			System.out.println("No Team Exists");
			return;
		}
		printTeams();
		System.out.println("Enter Team ID");
		int teamId = getNumericalInput(br);
		if (!getTeamVO().containsKey(teamId)) {
			System.out.println("Team Not Found");
			return;
		}
		Team team = getTeamVO().get(teamId);
		System.out.println("MEMBER DETAILS :");
		printRow(true, "MEMBER ID", "MEMBER NAME");
		//Set<Member> set = team.getMembers();
		for (Member member : team.getMembers()) {
			printRow(false,String.valueOf(member.getId()),member.getName());
		}
		System.out.println("Press \"ENTER\" to go back");
		br.readLine();

	}

	public void findTeams(BufferedReader br) throws IOException {
		if (getMemberVO().size() == 0) {
			System.out.println("No Member Exists");
			return;
		}
		printMember();
		System.out.println("Enter the member Id to find team :");
		int memberID = getNumericalInput(br);
		if (!getMemberVO().containsKey(memberID)) {
			System.out.println("Member does not exists");
			return;
		}
		Member member = getMemberVO().get(memberID);
		System.out.println("TEAM DETAILS :");
		printRow(true, "TEAM ID", "TEAM NAME");
		for (Map.Entry<Integer, Team> map : getTeamVO().entrySet()) {
			if (map.getValue().getMembers().contains(member)) {
				printRow(false, map.getKey().toString(), map.getValue().getName());
			}
		}
		System.out.println("Press \"ENTER\" to go back");
		br.readLine();

	}

	public void createAssignment(BufferedReader br) throws Exception {

		Assignment assignment = new Assignment();
		System.out.println("Enter Assignment ID :");
		int id = getNumericalInput(br);
		assignment.setId(id);
		System.out.println("Enter Assignment name :");
		String name = br.readLine();
		assignment.setName(name);
		Date startDate = new Date();
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		dateformat.setLenient(false);
		System.out.println("Enter Assignment End Date in format dd/MM/yyyy:");
		String lastDate;
		while (!(lastDate = br.readLine()).matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
			System.out.println("Invalid date format");
		}
		Date endDate = dateformat.parse(lastDate);
		if (startDate.after(endDate) && !dateformat.format(startDate).equals(lastDate)) {
			System.out.println("invalid date");
		} else {
			assignment.setStartDate(startDate);
			assignment.setEndDate(endDate);
			assignment.setActive(true);
			getAssignmentVO().put(id, assignment);

			System.out.println("ASSIGNMENT CREATION SUCCESSFUL");
		}
	}
	
	public void updateAssignmentScore(BufferedReader br) throws Exception {

		if (getAssignmentVO().size() == 0) {
			System.out.println("No assignment Exists");
			return;
		}
		printAssignments();
		System.out.println("Enter assignment id to update score ");
		int assignmentID = getNumericalInput(br);
		if (!getAssignmentVO().containsKey(assignmentID)) {
			System.out.println("Invalid assignment id");
			return;
		}
		if (getTeamVO().size() == 0) {
			System.out.println("no team exists");
			return;
		}
		Assignment assignment = getAssignmentVO().get(assignmentID);
		System.out.println("Do you want to enter score in sequential manner? (Y/N)");
		String choice = getYesOrNo(br);
		if (choice.equalsIgnoreCase("Y")) {
			for (Map.Entry<Integer, Team> map : getTeamVO().entrySet()) {
				Team team = map.getValue();
				updateScoreHelper(br, assignment, team);
			}
		} else {
			printTeams();
			System.out.println("Enter the team id to update score:");
			int teamID = getNumericalInput(br);
			if (!getTeamVO().containsKey(teamID)) {
				System.out.println("Team Does Not Exist");
				return;
			} else {
				Team team = getTeamVO().get(teamID);
				updateScoreHelper(br, assignment, team);
			}

		}
		System.out.println("SCORE UPDATION SUCCESSFUL");
	}

	public void viewAssignmentHistory(BufferedReader br) throws IOException {
		if (getAssignmentVO().size() == 0) {
			System.out.println("No assignment Exists");
			return;
		}
		printAssignments();
		System.out.println("Enter assignment id to view team scores :");
		int assignmentID = getNumericalInput(br);
		if (!getAssignmentVO().containsKey(assignmentID)) {
			System.out.println("Invalid assignment id");
			return;
		}
		if (getTeamVO().size() == 0) {
			System.out.println("no team exists");
			return;
		}
		Assignment assignment = getAssignmentVO().get(assignmentID);
		Map<Team, TeamResult> teamResultVO = assignment.getTeamResult();
		System.out.println("TEAM DETAILS :");
		printRow(true, "TEAM NAME", "TEAM SCORE");
		for (Map.Entry<Integer, Team> map : getTeamVO().entrySet()) {
			String score = String.valueOf(0);
			if (teamResultVO.containsKey(map.getValue())) {
				score = String.valueOf(teamResultVO.get(map.getValue()).getScore());
			}
			printRow(false, map.getValue().getName(), score);
		}
		System.out.println("Press \"ENTER\" to go back");
		br.readLine();
	}

	public void viewScoreBoard(BufferedReader br) throws IOException {

		Map<Integer, Team> sortedTeamVO = new TreeMap<>(new compareByScore(getTeamVO()));
		sortedTeamVO.putAll(getTeamVO());
		int rank = 1;
		System.out.println("SCOREBOARD :");
		printRow(true, "TEAM NAME", "TEAM SCORE", "RANK");
		for (Map.Entry<Integer, Team> teamMap : sortedTeamVO.entrySet()) {
			String teamName = teamMap.getValue().getName();
			int score;
			if (teamMap.getValue().getTeamResult() == null) {
				score = 0;
			} else {
				score = teamMap.getValue().getTeamResult().getTotalScore();
			}
			printRow(false, teamName, String.valueOf(score), String.valueOf(rank++));
		}
		System.out.println("Press \"ENTER\" to go back");
		br.readLine();

	}

	public int getNumericalInput(BufferedReader br, int... range) {
		int input;
		while (true) {
			try {
				input = Integer.parseInt(br.readLine());
				if (range.length != 0 && (input < range[0] || input > range[1])) {
					throw new Exception();
				}
				break;
			} catch (Exception e) {
				System.out.println("Enter a valid input :");
				continue;
			}
		}
		return input;
	}

	private void printRow(boolean head, String... elements) {
		String columnSpace = "                         ";
		if (head) {
			printSeperator(true, elements.length);
		}
		for (String element : elements) {
			System.out.print(" " + element + columnSpace.substring(element.length()) + "|");
		}
		System.out.println();
		if (head) {
			printSeperator(true, elements.length);
			return;
		}
		printSeperator(false, elements.length);
	}

	public void printSeperator(boolean head, int column) {
		String seperator = head ? "===========================" : "---------------------------";
		for (int i = 0; i < column; i++) {
			System.out.print(seperator);
		}
		System.out.println();
	}

	private void printTeams() {
		System.out.println("TEAM DETAILS :");
		printRow(true, "TEAM ID", "TEAM NAME");
		for (Map.Entry<Integer, Team> map : getTeamVO().entrySet()) {
			printRow(false, map.getKey().toString(), map.getValue().getName());
		}
	}

	private void printMember() {
		System.out.println("MEMBER DETAILS :");
		printRow(true, "MEMBER ID", "MEMBER NAME");
		for (Map.Entry<Integer, Member> map : getMemberVO().entrySet()) {
			printRow(false, map.getKey().toString(), map.getValue().getName());
		}

	}

	private void printMembers(int teamID) {
		System.out.println("MEMBER DETAILS :");
		printRow(true, "MEMBER ID", "MEMBER NAME");
		for (Member member : getTeamVO().get(teamID).getMembers()) {
			printRow(false, String.valueOf(member.getId()), member.getName());
		}
	}

	private void printAssignments() {
		System.out.println("ASSIGNMENT DETAILS :");
		printRow(true, "ASSIGNMENT ID", "ASSIGNMENT NAME", "ASSIGNMENT STATUS", "START DATE", "END DATE");
		for (Map.Entry<Integer, Assignment> map : getAssignmentVO().entrySet()) {
			Assignment assignment = map.getValue();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			df.setLenient(false);
			String startDate = df.format(assignment.getStartDate());
			String endDate = df.format(assignment.getEndDate());
			printRow(false, map.getKey().toString(), assignment.getName(),
					(assignment.isActive() ? "Active" : "Inactive"), startDate, endDate);
		}
	}

	private void addMemberHelper(BufferedReader br, Team team, String index) throws IOException {
		System.out.println("Enter member " + index + "ID :");
		int memberID = getNumericalInput(br);
		if (getMemberVO().containsKey(memberID)) {
			team.addMember(getMemberVO().get(memberID));
		} else {
			Member member = new Member();
			member.setId(memberID);
			System.out.println("Enter the member name : (max. 25 characters)");
			String name;
			while ((name = br.readLine()).length() > 25) {
				System.out.println("Name exceeds 25 characters. Please enter again.");
			}
			member.setName(name);
			team.addMember(member);
			getMemberVO().put(memberID, member);
		}
	}

	private void updateScoreHelper(BufferedReader br, Assignment assignment, Team team) {
		System.out.println("Enter the score for team \"" + team.getName() + "\" :");
		int score = getNumericalInput(br);
		TeamResult teamResult;
		if (assignment.getTeamResult().containsKey(team)) {
			teamResult = assignment.getTeamResult().get(team);
		} else {
			if (team.getTeamResult() == null) {
				teamResult = new TeamResult(null);
			} else {
				teamResult = new TeamResult(team.getTeamResult());
			}
			team.setTeamResult(teamResult);
		}
		teamResult.setScore(score);
		assignment.addTeamResult(team, teamResult);
	}

	private String getYesOrNo(BufferedReader br) throws IOException {
		String choice;
		if ((choice = br.readLine()).length() == 1 && choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")) {
			return choice;
		}
		System.out.println("Enter a valid input :");
		return getYesOrNo(br);
	}

}
