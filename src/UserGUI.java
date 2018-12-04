//***********************************************************************
//*																		*
//* CIS611					Fall 2018									*
//*																		*
//*						Program Assignment PP04							*
//*																		*
//*						song scraper, GUI class				*
//*																		*
//*						Created 21 Nov 2018								*
//*																		*
//*						Saved in UserGUI.java						*
//*																		*
//***********************************************************************


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserGUI extends JFrame implements ActionListener{
	private JComboBox cboSongs;
	private JTextArea txtInfo;
	private JButton btnScrape;
	private JButton btnClose;
	private JScrollPane scroll;
	private JLabel lblSong;
	
	private final String URL0 =  "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/smokey-robinson-and-the-miracles-shop-around-71184/" ;
	private final String URL1 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/bob-marley-and-the-wailers-i-shot-the-sheriff-161581/" ;
	private final String URL2 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/the-four-tops-baby-i-need-your-loving-170636/" ;
	private final String URL3 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/jimmy-cliff-the-harder-they-come-35805/" ;
	private final String URL4 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/led-zeppelin-black-dog-50226/" ;
	private final String URL5 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/sly-and-the-family-stone-hot-fun-in-the-summertime-56860/" ;
	private final String URL6 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/elvis-presley-dont-be-cruel-55974/" ;
	private final String URL7 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/the-everly-brothers-cathys-clown-63263/" ;
	private final String URL8 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/gnarls-barkley-crazy-40673/" ;
	private final String URL9 = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/smokey-robinson-and-the-miracles-the-tracks-of-my-tears-56465/";
   
	private final String FILENAME = "RollingStoneTop500_Output.txt";
   //test incorrect link to see if program will crash 
   //private final String URL_TO_USE = "http://NFL.com"; 

	static Scraper scraper;
	
	public static void main (String[] args)  {
	   JFrame frame = new UserGUI();
	   frame.setTitle("Top 500 Songs Application");
	   frame.pack();
	   frame.setLocationRelativeTo(null);
	 	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 	frame.setVisible(true);
	} // end main 
   
	public UserGUI() {
		initComponent();
		doTheLayout();
	}
   
	private void initComponent() {
		String[] forTesting = {"Press the 'Scrape Songs' button to scrape and populate this list."};
		lblSong = new JLabel("Song: ");
		cboSongs = new JComboBox(forTesting);
		   DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
         dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 
         cboSongs.setRenderer(dlcr); 
      txtInfo = new JTextArea(8, 75);
		btnScrape = new JButton("Scrape Songs");
		btnClose = new JButton("Close");
		scroll = new JScrollPane(txtInfo,
		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		   JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		txtInfo.setEditable(false);
		txtInfo.setWrapStyleWord(true);
		txtInfo.setLineWrap(true);
		scroll.setAutoscrolls(true);
		
		cboSongs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cboSongs_Action();
			}
		});
		btnScrape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnScrape_Action();
			}
		});
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_Action();
			}
		});
	}
   
	private void cboSongs_Action() {
		if(cboSongs.getSelectedIndex()<1) {
			return;
		}
		else {
			Song selectedSong = findSongByTitle(cboSongs.getSelectedItem().toString());
			txtInfo.setText(selectedSong.toString());
		}
	}
   
	private void btnScrape_Action() {
		Date start = new Date();
     
      
		if(scraper != null) {
			JOptionPane.showMessageDialog(null, "You've already scraped the website. Close the program and start over if you want to do it again.");
			return;
		}
		
      
		String[] URLs = {URL0, URL1, URL2, URL3, URL4, URL5, URL6, URL7, URL8, URL9};

      
		scraper = new Scraper(URLs, FILENAME);
		for(Song song: scraper.songs) {
			cboSongs.insertItemAt(song.getTitle(), song.getRank());
		}
      txtInfo.setText("Song list populated! Select a song from the drop down menu to see details."); 
		
      
      
      Date finish = new Date();
      System.out.println("Started at: " + start);
      System.out.println("Finished at: " + finish);
	}
   
	private Song findSongByTitle(String title) {
		for(Song song: scraper.songs) {
			if(song.getTitle().equals(title)) {
				return song;
			}
		}
		return null;
	}
   
	private void close() {
		System.exit(0);
	}
   
	private void btnClose_Action() {
		close();
	}
   
	private void doTheLayout() {
		JPanel top = new JPanel();
		JPanel middle = new JPanel();
		JPanel buttons = new JPanel();
		
		top.setLayout(new FlowLayout(FlowLayout.CENTER));
		top.add(lblSong);
		top.add(cboSongs);
		middle.add(scroll);
		
		buttons.setLayout(new FlowLayout());
		buttons.add(btnScrape);
		buttons.add(btnClose);
		
		this.setLayout(new BorderLayout());
		this.add(top, "North");
		this.add(middle, "Center");
		this.add(buttons, "South");
	}


  
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
   }
}// end class