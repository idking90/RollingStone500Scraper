//***********************************************************************
//*																		*
//* CIS611					Fall 2018									*
//*																		*
//*						Program Assignment PP04							*
//*																		*
//*						song scraper, song object class				*
//*																		*
//*						Created 21 Nov 2018								*
//*																		*
//*						Saved in Song.java						*
//*																		*
//***********************************************************************
public class Song {

	private int rank;
	private String writer;
	private String producer;
	private String releaseDate;
	private String artist;
	private String title;
	
	private String url;
	private String description;

	
	public Song(int rank, String releaseDate, String artist, String title) {
		this.rank = rank;

		this.releaseDate = releaseDate;

		this.title=title;
		this.artist=artist;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String pDescription) {
		description=pDescription;
	}
	
	
	public int getRank() {
		return rank;
	}
	public void setRank(int pRank) {
		rank = pRank;
	}
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String pWriter) {
		writer = pWriter;
	}
	
	public String getProducer() {
		return producer;
	}
	public void setProducer(String pProducer) {
		producer = pProducer;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String pReleaseDate) {
		releaseDate = pReleaseDate;
	}
	
	public String getURL() {
		return url;
	}
	public void setURL(String pURL) {
		url = pURL;
	}
	
	public String getArtist() {
		return artist;

	}
	public void setArtist(String pArtist) {
		artist = pArtist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String pTitle) {
		title = pTitle;
	}
	@Override
	public String toString() {
		String s = " | ";
		return rank + s + artist + s + title + s + releaseDate;
	}
}