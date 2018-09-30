package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import teammanagementhelper.TeamManagementTools;

public class Menu {

	private String storageFileName = "C:\\Users\\713913\\workspace\\GameOfTeams\\storage.txt";

	public static void main(String[] args) throws Exception {
		Menu menu = new Menu();
		TeamManagementTools teamManagementTools = new TeamManagementTools();

		try {
			teamManagementTools.loadObjects(menu.storageFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		menu.showMainMenu(teamManagementTools);
	}

	public void showMainMenu(TeamManagementTools teamManagementTools) throws Exception {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			teamManagementTools.printSeperator(false, 4);
			System.out.println("***** MENU - MAIN ******");
			System.out.println("1. View");
			System.out.println("2. Edit");
			System.out.println("3. Exit");
			System.out.println("Choose an option [1 - 3]:");
			int choice = teamManagementTools.getNumericalInput(br,1,3);
			switch (choice) {
			case 1:
				showViewMenu(br, teamManagementTools);
				break;
			case 2:
				showEditMenu(br, teamManagementTools);
				break;
			case 3:
				System.out.println("Terminating the application.");
				System.exit(0);
			default:
			}
		}

	}

	public void showViewMenu(BufferedReader br, TeamManagementTools teamManagementTools) throws Exception {
		boolean goToMain = false;
		while (!goToMain) {
			teamManagementTools.printSeperator(false, 4);
			System.out.println("***** MENU - VIEW *****");
			System.out.println("1. View Score Board");
			System.out.println("2. Find Members by team");
			System.out.println("3. Find Teams by member");
			System.out.println("4. View Assignments and score History");
			System.out.println("5. Go to Main Menu");
			System.out.println("Choose an option [1 - 5]:");
			int choice = teamManagementTools.getNumericalInput(br,1,5);
			switch (choice) {
			case 1:
				teamManagementTools.viewScoreBoard(br);
				break;
			case 2:
				teamManagementTools.findMembers(br);
				break;
			case 3:
				teamManagementTools.findTeams(br);
				break;
			case 4:
				teamManagementTools.viewAssignmentHistory(br);
				break;
			case 5:
				goToMain=true;
				break;
			default:
			}
		}
	}
	
	public void showEditMenu(BufferedReader br, TeamManagementTools teamManagementTools) throws Exception
	{
		boolean goToMain = false;
		while (!goToMain) {
			teamManagementTools.printSeperator(false, 4);
			System.out.println("***** MENU - EDIT *****");
			System.out.println("1. Create a new Team ");
			System.out.println("2. Remove existing team");
			System.out.println("3. Change Team Name");
			System.out.println("4. Add Member");
			System.out.println("5. Remove Member ");
			System.out.println("6. Create Assignment");
			System.out.println("7. Update Assignment Score");
			System.out.println("8. Save Session");
			System.out.println("9. Go to Main Menu");
			System.out.println("Choose an option [1 - 9]:");
			int choice = teamManagementTools.getNumericalInput(br,1,9);
			switch (choice) {
			case 1:
				teamManagementTools.createTeam(br);
				break;
			case 2:
				teamManagementTools.removeTeam(br);
				break;
			case 3:
				teamManagementTools.changeTeamName(br);
				break;
			case 4:
				teamManagementTools.addMember(br);
				break;
			case 5:
				teamManagementTools.removeMember(br);
				break;
			case 6:
				teamManagementTools.createAssignment(br);
				break;
			case 7:
				teamManagementTools.updateAssignmentScore(br);
				break;
			case 8:
				teamManagementTools.updateDetails(this.storageFileName);
				break;
			case 9:
				goToMain=true;
				break;
			default:
			}
		}
	}

}
