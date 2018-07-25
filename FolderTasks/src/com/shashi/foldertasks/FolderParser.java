package com.shashi.foldertasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class FolderParser {
	private static final String DELETEWORDSLIST = "C:\\AppsData\\WebAll\\deletewordList.txt";
	public static String  INITIALPATH= "";	
	 static int preserveRootFolders =0;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub


		
		List<Path> folders = new ArrayList<Path>();
		List<Path> files =  new ArrayList<Path>();
	
		List<String> DeleteWordsList = new ArrayList<String>();
		
		fetchDeleteWords(DeleteWordsList);
		System.out.println("******"+INITIALPATH);
		Path path = Paths.get(INITIALPATH);
		listFiles(path, folders, files);
		processPath(files,DeleteWordsList );
		
	
	}
	
	private static void processPath ( List<Path>  files, List<String>  DeleteWordsList ) throws IOException{
		for(Path file:files){
			String parentf= file.getParent().toString();
			if(parentf.length()>INITIALPATH.length()){
				String midFolders= parentf.substring(INITIALPATH.length());
				//System.out.println(midFolders);
				String newfolderPath= removeJunk(midFolders, DeleteWordsList);
				
				if(!newfolderPath.equals(midFolders+File.separator)){
					movefileToNewPath(INITIALPATH+newfolderPath, file);
					
				}
			}
		}
	}

	private static void movefileToNewPath(String trimmedPath, Path file) throws IOException {
		
		Path target = Paths.get(trimmedPath+file.getFileName());
		System.out.println(file.toAbsolutePath()+"==>" +target.toAbsolutePath());
			
		FileUtils.moveFile(file.toFile(), target.toFile());
		
	//	Files.move(file, target, StandardCopyOption.ATOMIC_MOVE);
		
	}

	private static void fetchDeleteWords(List<String> DeleteWordsList) {

		try (BufferedReader br = new BufferedReader(new FileReader(DELETEWORDSLIST))) {

			String line;
			while ((line = br.readLine()) != null) {
				DeleteWordsList.add(line);
				// System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (DeleteWordsList.get(0).endsWith(File.separator)) {
			INITIALPATH = DeleteWordsList.get(0);
		} else {
			INITIALPATH = DeleteWordsList.get(0) + File.separator;
		}
		DeleteWordsList.remove(0);

	}

	private static String removeJunk(String midFolders, List<String> deleteWordsList) {
	
		
		StringBuilder sb= new StringBuilder();
		String[] midfolderArray = midFolders.split(File.separator+File.separator);
		List<String> midfolderList = new LinkedList<String>(Arrays.asList(midfolderArray));
		 deleteWordsList.remove(null);
		 deleteWordsList.remove("");
		 midfolderList.remove(null);
		 midfolderList.remove("");
		
		 
	// remove delete words	 
		 for(String dword:deleteWordsList){
			 midfolderList.remove(dword) ;
		 }
		 
		 ArrayList<String>rmList =new ArrayList<String>();
		// check for crap folders
		 for(String mf: midfolderList){
			 if(mf.length()<=2){
				 rmList.add(mf);
				 //midfolderList.remove(mf);
			 }
			 if(mf.length()>14 && mf.length()<17){
				 rmList.add(mf);
				// midfolderList.remove(mf);
			 }
			 //remove .. cms.static.hw.famedownload.com folders 
			 if((mf.contains(".com") )||(mf.contains(".net"))){
				 rmList.add(mf);
				// midfolderList.remove(mf);
			 }
		 }
		 midfolderList.removeAll(rmList);
	
		 for(String mf:midfolderList){
			 sb.append(mf);
			 sb.append(File.separator);
		 }
		
		// sb.replace( sb.lastIndexOf(File.separator), sb.length(),"");
		 //System.out.println(midFolders+ "==>"+ sb.toString()+"==>"+midfolderList.toString());
		return sb.toString();

		
	}


	
	private static void listFiles(Path path, List<Path> folders, List<Path> files) throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path entry : stream) {
				if (Files.isDirectory(entry)) {
					folders.add(entry);
					listFiles(entry, folders, files);
				}else{
				files.add(entry);
				}
			}
		}
	}
}
