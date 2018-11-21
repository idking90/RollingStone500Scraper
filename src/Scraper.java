//***********************************************************************
//*																		*
//* CIS611					Fall 2018									*
//*																		*
//*						Program Assignment PP04							*
//*																		*
//*						song scraper, Scraper object class				*
//*																		*
//*						Created 21 Nov 2018								*
//*																		*
//*						Saved in Scraper.java						*
//*																		*
//***********************************************************************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Scraper{
	
	private String url; 
	private String fileName;
	Song[] songs = new Song[50];
	
	Scraper(String url, String fileName){
		this.url = url;
		this.fileName=fileName;
		try {
			parseData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// this method will parse a song data (starting from the first URL), and it creates song objects for each song URL
	// it adds the new Song object to the array of Song objects, songs 
	private void parseData() throws FileNotFoundException {
		//all of the hardest work is gonna go between here and what I have written below
		
		
		
		
		
		
		
		
		Song theSong=null;//here is where we'll create the song
		//here and below should be ok, just need to figure everything else out first to test any of it
		theSong.setArtist("artistPlaceholder");
		theSong.setTitle("titlePlaceholder");
		int openSpot=-1;
		for(int i=0; i<songs.length; i++) {
			if(songs[i]==null) {
				openSpot=i;
				break;
			}
		}
		songs[openSpot]=theSong;
	
		writeToFile();
	}
	
	// will read the data from songs array (one by one) and return a String of the all Song object strings in multiple lines, each line has a Song object String
	@Override
	public String toString() {	
		String toReturn="";
		for(Song song: songs) {
			if(song == null) {
				break;
			}
			else {
				toReturn += song.toString() + "\n";
			}
		}
		return toReturn;	//returns as one big string
	}
	
	// store the program out in a text file, output.txt 
	public void writeToFile() throws FileNotFoundException {
			
		File outputFile = new File(fileName);
		PrintWriter write = new PrintWriter(outputFile);
		write.print(toString());
		write.close();
	}
	
    }//end Class
	

