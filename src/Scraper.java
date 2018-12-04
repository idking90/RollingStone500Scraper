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

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Scraper{
	
	private String[] URLs = new String[10]; 
	private String fileName;
	Song[] songs = new Song[500];
	private Pattern eachSongPattern = Pattern.compile("<article class=\\\"c-list__item\\\"[\\s\\S]*?<!-- .c-list__item -->");
	private Matcher eachSongMatcher;
	private String[] eachSongHTML = new String[50];
	
	private Pattern rankPattern=Pattern.compile("data-list-item=\"[\\d]{1,3}");
	private Pattern releaseDatePattern=Pattern.compile("Released:[\\s\\S]*?[\\d]{2}");
	private Pattern artistPattern=Pattern.compile("data-list-title=\"[\\s\\S]*?,");
	private Pattern titlePattern=Pattern.compile("&#8216;[\\s\\S]*?(&#8217;\")|&#8216;[\\s\\S]*?(&#8221;\")");
	

	private Matcher rankMatcher ;

	private Matcher releaseDateMatcher ;
	private Matcher artistMatcher ;
	private Matcher titleMatcher ;

	Scraper(String[] URLs, String fileName){
		this.URLs=URLs;
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
	private void parseData() throws IOException {
		
		URL[] allURLs = new URL[10];
		
		try {
			for(int i=0; i<URLs.length; i++) {

				allURLs[i]=new URL(URLs[i]); //opens each as a URL
			}
		}
		catch(Exception e) {//issue with url
			System.out.println("issue with URL");
			e.printStackTrace();
			return;
		}
		for(int i=0; i<allURLs.length;i++){//adjust start of loop to reduce testing time to next URL
			System.out.println("reading URL " + i);
		InputStream input = allURLs[i].openStream();
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(input));
		String line;
		String htmlSource="";
		while((line=buffRead.readLine()) != null) {
			htmlSource += line;
		}
	
		int openSpot = 0;
		eachSongMatcher = eachSongPattern.matcher(htmlSource);
		while(eachSongMatcher.find()) {
		eachSongHTML[openSpot]=eachSongMatcher.group();
		openSpot++;
		}
		//System.out.println("last song's html*****\n" + eachSongHTML[49]); 
		
		for(String songCode: eachSongHTML){
			rankMatcher = rankPattern.matcher(songCode);
			releaseDateMatcher = releaseDatePattern.matcher(songCode);
			artistMatcher = artistPattern.matcher(songCode);
			titleMatcher = titlePattern.matcher(songCode);
			
			int rank = -1;//initalized as -1 so that it throws an error if messed up somehow
			String rankAsString = "";
			
			String releaseDate = "";
			String artist ="";
			String title="";

			while(rankMatcher.find()) {
				rankAsString=rankMatcher.group();
			}
			
			while(releaseDateMatcher.find()) {
				releaseDate=releaseDateMatcher.group();
			}
			while(artistMatcher.find()) {
				artist=artistMatcher.group();
			}
			while(titleMatcher.find()) {
				title=titleMatcher.group();
			}
			
			rankAsString = rankAsString.replace("data-list-item=\"", "");
			try {
				rank = Integer.parseInt(rankAsString);
				if(rank < 1 | rank >500) {
					throw new Exception();
				}
			}
			catch(Exception e) {
				System.out.println("issue converting rank to int");
				e.printStackTrace();
			}

			
			releaseDate = releaseDate.replaceAll("Released:", "");			
			releaseDate = releaseDate.replaceAll("&#xA0;", "");
			releaseDate = releaseDate.replaceAll("&apos;", "'");
			releaseDate=releaseDate.replaceAll("<\\/strong>", "");			
			releaseDate=releaseDate.trim();
			if(releaseDate.length() > 9) {
				releaseDate=releaseDate.substring(0, 9);
			}
			releaseDate=releaseDate.replaceAll("[<]", "");
			
			
			artist = artist.replaceAll("data-list-title=\"", "");
			artist=artist.replaceAll(",", "");
			artist=artist.trim();
			

			//these are individual exceptions to the regex that are easier to just manually do...
			if(rank==281) {
				title = "I'll Take You There\'";
			}
			if(rank==213) {
				title="96 Tears'";
			}
			if(rank==172) {
				title="99 Problems'";
			}
			title = title.replaceAll("&#8216;", "");
			title=title.replaceAll("&#8217;", "'");
			title=title.replaceAll("&#8211;", "-");
			title=title.replaceAll("\"", "");
			title=title.replaceAll("&#8221;", "'");
			title=title.substring(0, title.length()-1);
			title=title.trim();
			


			Song theSong = new Song(rank,  releaseDate, artist, title);

			songs[rank-1]=theSong;	
			System.out.println("Created Song " + theSong.toString());

		}//end for each songHTML loop
		}
		writeToFile();
		//System.out.println("success writing file at "+ new Date());
	}
	
	// will read the data from songs array (one by one) and return a String of the all Song object strings in multiple lines, each line has a Song object String
	@Override
	public String toString() {	
		String toReturn="Rank | Artist | Song | Release Date" + System.getProperty("line.separator");
		for(Song song: songs) {
			if(song == null) {
				break;
			}
			else {
				toReturn += song.toString() + System.getProperty("line.separator"); 
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
	
