package com.tiger.tester;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.runtime.RecognitionException;

import com.compiler.TigerCompiler;


public class TigerTest {
	public static void main(String[] args) throws RecognitionException {
		
		System.out.println(System.getProperty("user.dir"));
			
		File folder = new File("test/com/tiger/test_case");
		for (File file : folder.listFiles()) {
			System.out.println("Compiling: " + file.getName());
			System.out.println("******************************");
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();

				TigerCompiler compiler = new TigerCompiler();
				String input = new String(data, "UTF-8");

				compiler.compile(input);
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
			}
			System.out.println("******************************");
			System.out.println();
		}
	}
}
