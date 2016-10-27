package com.shashi.foldertasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Listprocess {
	private static final String DELETEWORDSLIST = "C:\\AppsData\\ACDSee9Pro\\Neo Done\\Thesupermodelsgallery.Com\\wp-content\\2008_01\\abc.txt";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> DeleteWordsList = new ArrayList<String>();
		fetchDeleteWords(DeleteWordsList);
		
		for(String s:DeleteWordsList){
			String[] x = s.split(".jpg");
		//	for(int i=0;i<x.length;i++){
		//		System.out.println("mkdir "+x[0]);
		//	}

			
			if(x.length>0)
			System.out.println("move "+s +" "+ x[0]);
		}
		
	}private static void fetchDeleteWords(List<String> DeleteWordsList) {

		try (BufferedReader br = new BufferedReader(new FileReader(DELETEWORDSLIST))) {

			String line;
			while ((line = br.readLine()) != null) {
				DeleteWordsList.add(line);
				// System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		

	}

}
