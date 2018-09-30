package com.cts.ally.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoginValidation {

	static File file = new File("D:\\Password3.txt");
	//Username = 99
	// Password is Arvind@123 
	public static void main(String[] args) throws IOException {

		
		FileReader fRead = new FileReader(file);

		BufferedReader brFile = new BufferedReader(fRead);
		String line;
		List<String> list = new ArrayList<String>();

		while ((line = brFile.readLine()) != null) {
			list.add(line);
		}
		brFile.close();
		

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 1: For Login Into The Application");
		System.out.println("Enter 2: For Changing The Password ");
		while (true) {

			System.out.println("Enter Your Choice");

			int ch = Integer.parseInt(br.readLine());

			switch (ch) {

			case 1:

				login(list);
				break;

			case 2:

				changePassword(list);
				System.exit(0);
				break;

			default:

				System.out.println("You entered wrong option");

				System.exit(0);

			}

		}
		

	}

	public static void login(List<String> list) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Your Username");
		String username = br.readLine();
		if (list.get(0).equals(username)) 
		{
		System.out.println("Enter Your Password");
		String password = br.readLine();

		String cipher = encryption(password);

		if (username.equals(list.get(0)) && cipher.equals(list.get(1))) {
			System.out.println("Login Successfull");
			System.exit(0);
			
		}
		else {
			System.out.println("Enter Your Correct Password");
		}
		}
		else {
			System.out.println("Enter Your Correct Username");
		}

	}

	public static void changePassword(List<String> list) throws IOException

	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter brWrite = new BufferedWriter(new FileWriter(file));
		FileOutputStream fos = new FileOutputStream(file, false);

		System.out.println("Enter Your Username");
		String username = br.readLine();
		System.out.println("Enter Your Old Password");
		String oldPassword = br.readLine();
		String cipher = encryption(oldPassword);

		if (cipher.equals(list.get(1)) && list.get(0).equals(username)) {
			System.out.println("Enter Your New Password");
			String newPassword = br.readLine();
			String cipher1 = encryption(newPassword);
			
			fos.write((list.get(0) + System.lineSeparator()).getBytes());
			fos.write(cipher1.toString().getBytes());
			fos.close();

		} else {
			System.out.println("Invalid Credentials");
		}
		brWrite.close();
	}

	public static String encryption(String plainText) {
		int key = 101;

		StringBuilder cipher = new StringBuilder();

		for (int i = 0; i < plainText.length(); i++) {
			cipher.append((char) (plainText.charAt(i) ^ key));
		}
		return cipher.toString();
	}

}
