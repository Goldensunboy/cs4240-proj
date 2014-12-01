package com.tiger.tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.exception.BadDeveloperException;

public class TigerTestRunner {
	public static void main(String[] args) {
		
		String mainDeveloperName = System.getProperty("user.name");
		
		/******** Instructor Specific ***********/
		//mainDeveloperName = "vincent"; //TODO, added for Vincent to test our code
//		mainDeveloperName = "andrew";
		boolean irCodeOn = false;
		boolean symbolTableOn = false;
		/****************************************/
		
		checkForThrows(mainDeveloperName);
		
		System.out.println(System.getProperty("user.dir"));
		System.out.println();
		
		new TigerTest(mainDeveloperName, irCodeOn, symbolTableOn).runTestCases();
	}
	
	private static void checkForThrows(String developerName) {
		File file = new File("src/com/tiger/Tiger.g");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
			   if(line.contains("throw new"))
				   throw new BadDeveloperException("Bad " + developerName + "!!! Don't use throw!!! PLEASE " + developerName + " PLEASE");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
