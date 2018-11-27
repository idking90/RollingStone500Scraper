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
	
	private final String URL_TO_USE = "https://www.rollingstone.com/music/music-lists/500-greatest-songs-of-all-time-151127/smokey-robinson-and-the-miracles-the-tracks-of-my-tears-56465/";
	
	
	static Scraper scraper;
	
	public static void main (String[] args)  {
	       JFrame frame = new UserGUI();
	       frame.setTitle("Top 50 Songs Application");
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
		String[] forTesting = {""};
		lblSong=new JLabel("Song: ");
		cboSongs = new JComboBox(forTesting);//eliminate the arg once we get scraper up and running, this is just to test event handling
		txtInfo = new JTextArea(10, 100);
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
		if(cboSongs.getSelectedItem().toString()==null |
				cboSongs.getSelectedItem().toString().equals("") |
				cboSongs.getSelectedItem()==null) {
			return;
		}
		else {
			Song selectedSong = findSongByTitle(cboSongs.getSelectedItem().toString());
			txtInfo.setText(selectedSong.toString());
		}
	}
	private void btnScrape_Action() {
		System.out.println("scrape button pressed");
		scraper = new Scraper(URL_TO_USE, getOutputFileName());
		cboSongs.removeAll();
		cboSongs.insertItemAt("", 0);
		for(Song song: scraper.songs) {
			cboSongs.insertItemAt(song.getTitle(), song.getRank());
		}
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
		
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
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

	private static String getOutputFileName() {
		String outputFileName;
		Date now = new Date();
		String dateFormat = "yyyyMMddHHmm";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String nowAsString = format.format(now);
		outputFileName = "output" + nowAsString + ".txt";
		return outputFileName;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}// end class