import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class RenamerMain {
	
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter the directory of the mp3 files to rename: ");
		String path = in.nextLine();
		System.out.println();
		
		Files.walk(Paths.get(path)).forEach(filePath -> {
			if (Files.isRegularFile(filePath)){
				//System.out.println(filePath);
				try {
					Rename(filePath.toString());
				} catch (Exception e) {
					System.out.println(filePath + " failed to rename");
					//e.printStackTrace();
				}
			}
		}); 
		

	}
	private static void Rename(String filePath) throws UnsupportedTagException, InvalidDataException, IOException{
		Mp3File mp3file = new Mp3File(filePath);
		 ID3v1 id3v1Tag;
		if (mp3file.hasId3v1Tag()){
			
			 id3v1Tag = mp3file.getId3v1Tag();
			 // TODO Sanitize incoming meta data
			 String artist = id3v1Tag.getArtist();
			 String title = id3v1Tag.getTitle();
			    
		    
		    File oldFile = new File(filePath);
		    String newName = artist + " - " + title +".mp3";
		   
		    File newFile = new File(oldFile.getParent(),newName);
		    Files.move(oldFile.toPath(), newFile.toPath());
		    System.out.println(filePath + " was renamed successfully");
		}else{
			System.out.println("error: no information obtained.");
		}
	   
		
	   
	    
	}
}
