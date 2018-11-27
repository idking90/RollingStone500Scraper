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
	
	private String url; 
	private String fileName;
	Song[] songs = new Song[50];
	private Pattern eachSongPattern = Pattern.compile("<article class=\\\"c-list__item\\\"[\\s\\S]*?<!-- .c-list__item -->");
	private Matcher eachSongMatcher;
	private String[] eachSongHTML = new String[50];
	
	private Pattern rankPattern=Pattern.compile("data-list-item=\"[\\d]{1,2}");
	private Pattern writerPattern=Pattern.compile("Writers?:\\s?<\\/strong>[\\s\\S]*?<br \\/>");
	private Pattern producerPattern=Pattern.compile("Producers?:\\s?<\\/strong>[\\s\\S]*?<br \\/>");
	private Pattern releaseDatePattern=Pattern.compile("Released:[\\s\\S]*?,");
	private Pattern artistPattern=Pattern.compile("data-list-title=\"[\\s\\S]*?,");
	private Pattern titlePattern=Pattern.compile("&#8216;[\\s\\S]*?&#8217;");
	private Pattern urlPattern=Pattern.compile("data-list-permalink=\"http.*\"");
	private Pattern descriptionPattern=Pattern.compile("<\\/p>[\\s]*<p>.{15}");

	private Matcher rankMatcher ;
	private Matcher writerMatcher ;
	private Matcher producerMatcher ;
	private Matcher releaseDateMatcher ;
	private Matcher artistMatcher ;
	private Matcher titleMatcher ;
	private Matcher urlMatcher ;
	private Matcher descriptionMatcher ;

	
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
	private void parseData() throws IOException {
		//all of the hardest work is gonna go between here and what I have written below
		
		//this section needs to be looped for each song. For now just trying to learn how to read the URL
		URL urlAsURL;
		try {
			urlAsURL = new URL(url);
		}
		catch(Exception e) {//issue with url
			System.out.println("issue with URL");
			e.printStackTrace();
			return;
		}
		InputStream input = urlAsURL.openStream();
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
		//System.out.println("last song's html*****\n" + eachSongHTML[49]); //ok, this works, proving that each chunk of HTML gets added to this array
		
		for(String songCode: eachSongHTML){
			rankMatcher = rankPattern.matcher(songCode);
			writerMatcher = writerPattern.matcher(songCode);
			producerMatcher = producerPattern.matcher(songCode);
			releaseDateMatcher = releaseDatePattern.matcher(songCode);
			artistMatcher = artistPattern.matcher(songCode);
			titleMatcher = titlePattern.matcher(songCode);
			urlMatcher = urlPattern.matcher(songCode);
			descriptionMatcher = descriptionPattern.matcher(songCode);
			
			int rank = -1;//initalized as -1 so that it throws an error if messed up somehow
			String rankAsString = "";
			String writer = "";
			String producer = "";
			String releaseDate = "";
			String artist ="";
			String title="";
			String url ="";
			String description="";
			while(rankMatcher.find()) {
				rankAsString=rankMatcher.group();
			}
			while(writerMatcher.find()) {
				writer=writerMatcher.group();
			}
			while(producerMatcher.find()) {
				producer=producerMatcher.group();
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
			while(urlMatcher.find()) {
				url=urlMatcher.group();
			}
			while(descriptionMatcher.find()) {
				description=descriptionMatcher.group();
				break;
			}
			//right now, the variables all appear as they're supposed to, minus "rankAsString" 
			//except each one has a bunch of garbage attached to it from the regex to find it within the HTML
			//next step is trying to get rid of that garbage to turn it into something meaningful
			//and consider replacing the HTML code for some of the special characters, but that's low priority
			System.out.println("rank******" + rankAsString);
			System.out.println("writer******" +writer);
			System.out.println("producer******" +producer);
			System.out.println("releaseDate******" +releaseDate);
			System.out.println("artist******" +artist);
			System.out.println("title******" +title);
			System.out.println("url******" +url);
			System.out.println("description******" +description);
			
			
			

			
			Song theSong = new Song(rank, writer, producer, releaseDate, url, description);
			theSong.setArtist(artist);
			theSong.setTitle(title);
			songs[rank-1]=theSong;
			

			
		}//end for each songHTML loop

		
/*
		
	almost ready to uncomment this once it works...
//		writeToFile();*/
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
	

