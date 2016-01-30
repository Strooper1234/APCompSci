import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Manager extends JPanel implements ActionListener {
	
	private final static int WIDTH = 900;
	private final static int HEIGHT = 600;
	
	JButton songShuffle;
	JButton searchSong;
	JButton sortArtist;
	JButton searchAlbum;
	
	
	
	String menu = "all";
	
	int x=0, y=0, width=0, height=0, increment = 0;// helps to set up things
	
	ArrayList<Song> songList = new ArrayList<Song>();
	
	public Manager(){
		setLayout(null);
		
		songShuffle = new JButton("Shuffle All");
		songShuffle.setBounds(40,70,200,30);
		songShuffle.addActionListener(this);
        add(songShuffle);
        
        width = 200;
        height = 30;
        x = 40;
        y = 200;
        increment = 100;
        
        searchSong = new JButton("Sort by song name");
        searchSong.setBounds(x, y, width, height);
        searchSong.addActionListener(this);
        add(searchSong);
        y += increment;
        
        sortArtist = new JButton("Sort by Artist");
        sortArtist.setBounds(x, y, width, height);
        sortArtist.addActionListener(this);
        add(sortArtist);
        y += increment;
        
        searchAlbum = new JButton("Sort by album");
        searchAlbum.setBounds(x, y, width, height);
        searchAlbum.addActionListener(this);
        add(searchAlbum);
        y += increment;
        
        
        y = 170;
        
        
       
		
        //add Songs
		songList.add(new Song("Song1", "The Artist", "Refuse"));
		songList.add(new Song("Originals", "The Artist", "Ambience"));
		songList.add(new Song("Free liberty", "The Artist", "Continent"));
		songList.add(new Song("Freedom", "Free guys", "Cotery"));
		songList.add(new Song("Free opinion", "Free guys", "Original"));
		songList.add(new Song("Hehe", "Free guys", "Smile"));
		songList.add(new Song("Keke", "Six Famous", "Two Things"));
		songList.add(new Song("Jaga", "Six Famous", "Scare Of Life"));
		songList.add(new Song("The", "Six Famous", "11:35"));
		songList.add(new Song("The ones", "Six Famous", "Ferraswheel"));
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(WIDTH,HEIGHT);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if(menu == "all"){
			x = 450;
			y = 30;
			
			
			
			for(int i=0; i<songList.size();i++){
				int ran = (int) (Math.random()*songList.size());
				
				Song temp = songList.get(i);
				
				songList.set(i, songList.get(ran));
				songList.set(ran, temp);
				
			}
			for(Song each : songList){
				each.drawMe(g, x, y);
				y += 50;
			}
			
		}else if(menu.equals("songSort")){
			x = 450;
			y = 30;
			
			for(int i=0; i<songList.size();i++){
				for(int j=i+1; j<songList.size(); j++){
					
					if(songList.get(i).getSongName().compareTo(songList.get(j).getSongName()) > 0){
						Song temp = songList.get(i);
						songList.set(i, songList.get(j));
						songList.set(j, temp);
					
					}
				}
			}
			
			for(Song each : songList){
				each.drawMe(g, x, y);
				y += 50;
			}
		}else if(menu.equals("artistSort")){
			x = 450;
			y = 30;
			
			for(int i=0; i<songList.size();i++){
				for(int j=i+1; j<songList.size(); j++){
					
					if(songList.get(i).getArtist().compareTo(songList.get(j).getArtist()) > 0){
						Song temp = songList.get(i);
						songList.set(i, songList.get(j));
						songList.set(j, temp);
					
					}
				}
			}
			
			for(Song each : songList){
				each.drawMe(g, x, y);
				y += 50;
			}
		}else if(menu.equals("albumSort")){
			x = 450;
			y = 30;
			
			for(int i=0; i<songList.size();i++){
				for(int j=i+1; j<songList.size(); j++){
					
					if(songList.get(i).getAlbum().compareTo(songList.get(j).getAlbum()) > 0){
						Song temp = songList.get(i);
						songList.set(i, songList.get(j));
						songList.set(j, temp);
					
					}
				}
			}
			
			for(Song each : songList){
				each.drawMe(g, x, y);
				y += 50;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == songShuffle){
			menu = "all";
		}
		if(e.getSource() == searchSong){
			menu = "songSort";
		}
		if(e.getSource() == sortArtist){
			menu = "artistSort";
		}
		if(e.getSource() == searchAlbum){
			menu = "albumSort";
		}
		
		repaint();
	}

}
