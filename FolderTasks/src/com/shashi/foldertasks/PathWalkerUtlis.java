package com.shashi.foldertasks;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathWalkerUtlis {

	
	

	public static List<Path> listSourceFiles(Path dir) throws IOException {
		List<Path> result = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*")) {
			for (Path entry : stream) {
				result.add(entry);
			}
		} catch (DirectoryIteratorException ex) {
			// I/O error encounter during the iteration, the cause is an
			// IOException
			throw ex.getCause();
		}
		return result;
	}

	public static void pathwalker(Path start) throws IOException {
	//	Path start = Paths.get("");
		int maxDepth = 5;
		try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) -> String.valueOf(path).endsWith(".jpg"))) {
			String joined = stream.sorted().map(String::valueOf).collect(Collectors.joining("; "));
			System.out.println("Found: " + joined);
		}
	}

	//	List<Path> result =PathWalkerUtlis.listSourceFiles(path);
	//	System.out.println(result.toString() + "\n" + result.size());
	//	PathWalkerUtlis.pathwalker(path);
//		for(Path file:files){
//			String parentf= file.getParent().toString();
//			String midFolders= parentf.substring(INITIALPATH.length());
//			System.out.println(midFolders);
//			
//		}
		
}
