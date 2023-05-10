package com.agilecompile.recursivetreepackage;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RecursiveTree {

	public static void main(String[] args) throws Exception {

		//Initializes scanner to receive input from command line or console.
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter a directory path:");
		String directoryPath = scanner.nextLine();

		//Outputs directory with file size and file count
		try {


			Path currentPath = Paths.get(directoryPath);

			System.out.println("Listing files in directory: " + currentPath);

			listDirectory(currentPath, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Recursive method to list Directory
	public static void listDirectory(Path path, int depth) throws Exception {

		//Retrieval of file attributes into attr
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);


		if(attr.isDirectory()) {


			DirectoryStream<Path> paths = Files.newDirectoryStream(path);
			AtomicInteger fileCount = new AtomicInteger(0);
			AtomicLong totalSize = new AtomicLong(0);


			System.out.println(spacesForDepth(depth) + " > " + path.getFileName());

			//Recursion of listDirectory
			for(Path tempPath: paths) {
				listDirectory(tempPath, depth + 1);
				if(!Files.isDirectory(tempPath)) {
					fileCount.incrementAndGet();
					totalSize.addAndGet(Files.size(tempPath));
				}
			}

			System.out.println(spacesForDepth(depth) + " > " + path.getFileName() + " - Files: " + fileCount.get() + ", Size: " + totalSize.get() + " bytes");

		} else {

			//Once there is no directory, print all files within the parent directory.
			System.out.println(spacesForDepth(depth) + " -- " + path.getFileName());
		}
	}

	//Method used to make spaces indicating parent-child relative tree depth
	public static String spacesForDepth(int depth) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < depth; i++) {
			builder.append("   ");
		}
		return builder.toString();
	}
}
