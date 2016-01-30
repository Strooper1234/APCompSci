import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Song {
	
	String songName = "";
	String artist = "";
	String album = "";
	
	public Song(String songName, String artist, String album){
		this.songName = songName;
		this.artist = artist;
		this.album = album;
	}
	
	public void drawMe(Graphics g, int x, int y){
		Font font = new Font("Arial", Font.PLAIN,15);
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(songName, x, y);
		y+=15;
		g.drawString(artist, x, y);
		y+=15;
		g.drawString(album, x, y);
	}

	public String getSongName() {
		return songName;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	
	
}
