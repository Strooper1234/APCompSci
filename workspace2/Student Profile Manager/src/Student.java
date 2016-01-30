import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Student {
	
	String firstName;
	String lastName;
	String subject;
	
	int id;
	Image image;
	
	List<Period> periods = new ArrayList<Period>();
	
	public Student(String firstName, String lastName, int id){
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		
		try {                
	        	image = ImageIO.read(new File("res/" + firstName + ".png"));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	}
	
	public void drawMe(Graphics g, int x, int y){
		
		Font title = new Font("Arial", Font.BOLD, 15);
		Font normal = new Font("Arial", Font.PLAIN, 15);
		
		g.setFont(title);
		g.setColor(Color.black);
		
		int increment = 15;
		
		
		g.drawImage(image, x, y-10, 60, 60, null);
		
		x += 100;
		
		g.drawString(firstName + " " + lastName + ", id=" + id, x, y);
		g.setFont(normal);
		x += 20;
		for(int i=0; i<periods.size();i++){
			y += increment;
			g.drawString(getFullPeriod(i), x, y);
			
		}
		
	}
	
	public void setPeriod(int period, String subject, char grade){
		periods.add(new Period(period, subject, grade));
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSubject(int index) {
		return periods.get(index).getSubject();
	}

	public String getId() {
		return Integer.toString(id);
	}
	
	public String getFullPeriod(int index){
		return periods.get(index).getPeriod() + ", " + periods.get(index).getSubject()
				+ ", " + periods.get(index).getGrade();
	}

	
	
	
	
}
