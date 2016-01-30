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

public class Screen extends JPanel implements ActionListener {
	
	public final static int WIDTH = 900;
	public final static int HEIGHT = 600;
	
	JButton allStudents;
	JButton searchName;
	JButton searchId;
	JButton searchSubject;
	
	JTextField nameTX;
	JTextField idTX;
	JTextField subjectTX;
	
	String menu = "all";
	
	List<Student> studentList = new ArrayList<Student>();
	
	int x=0, y=0, width=0, height=0, increment = 0;
	
	public Screen(){
		
		setLayout(null);
		
		allStudents = new JButton("Display All");
		allStudents.setBounds(40,70,200,30);
		allStudents.addActionListener(this);
        add(allStudents);
        
        width = 200;
        height = 30;
        x = 40;
        y = 200;
        increment = 100;
        
        searchName = new JButton("Search by name");
        searchName.setBounds(x, y, width, height);
        searchName.addActionListener(this);
        add(searchName);
        y += increment;
        
        searchId = new JButton("Search by id");
        searchId.setBounds(x, y, width, height);
        searchId.addActionListener(this);
        add(searchId);
        y += increment;
        
        searchSubject = new JButton("Search by subject");
        searchSubject.setBounds(x, y, width, height);
        searchSubject.addActionListener(this);
        add(searchSubject);
        y += increment;
        
        
        y = 170;
        
        
        nameTX = new JTextField(50);
        nameTX.setBounds(x, y, width, height);
        add(nameTX);
        y += increment;
        
        idTX = new JTextField(50);
        idTX.setBounds(x, y, width, height);
        add(idTX);
        y += increment;
        
        subjectTX = new JTextField(50);
        subjectTX.setBounds(x, y, width, height);
        add(subjectTX);
        
        
        studentList.add(new Student("Aaron", "Smith", 1234));
        studentList.add(new Student("Jane", "Doe", 4321));
        studentList.add(new Student("Ben", "Washington", 3021));
        studentList.add(new Student("Carly", "Jones", 1001));
        
        studentList.get(0).setPeriod(1, "US History", 'A');
        studentList.get(0).setPeriod(2, "Geometry", 'B');
        studentList.get(0).setPeriod(3, "Physics", 'A');
        
        studentList.get(1).setPeriod(2, "Geometry", 'A');
        studentList.get(1).setPeriod(3, "Physics", 'C');
        studentList.get(1).setPeriod(4, "Computer Science", 'A');
        
        studentList.get(2).setPeriod(1, "US History", 'B');
        studentList.get(2).setPeriod(3, "Physics", 'B');
        studentList.get(2).setPeriod(4, "Computer Science", 'A');
        
        studentList.get(3).setPeriod(4, "Geometry", 'B');
        studentList.get(3).setPeriod(5, "Physics", 'B');
        studentList.get(3).setPeriod(6, "English", 'A');
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(WIDTH,HEIGHT);
	}
	
	public void paintComponent(Graphics g){
		
		
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.black);
		x = 400;
		y = 100;
		increment = 140;
		
		if(menu.equals("all")){
			for(Student each : studentList){
				each.drawMe(g, x, y);
				y += increment;
			}
		}else if(menu.equals("searchName")){
			for(int i=0; i<studentList.size(); i++){
				
				
				if(studentList.get(i).getFirstName().equals(nameTX.getText())){
					studentList.get(i).drawMe(g, x, y);
					y += increment;
				}
			}
		}else if(menu.equals("searchSubject")){
			for(int i=0; i<studentList.size(); i++){
				for(int a=0; a<3; a++){
					if(studentList.get(i).getSubject(a).equals(subjectTX.getText())){
						studentList.get(i).drawMe(g, x, y);
						y += increment;
						break;
					}
				}
			}
			
		}else if(menu.equals("searchId")){
			for(int i=0; i<studentList.size(); i++){
				if(studentList.get(i).getId().equals(idTX.getText())){
					studentList.get(i).drawMe(g, x, y);
					y += increment;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == allStudents){
			menu = "all";
		}
		
		if(e.getSource() == searchName){
			menu = "searchName";
		}
		
		if(e.getSource() == searchSubject){
			menu = "searchSubject";
		}
		
		if(e.getSource() == searchId){
			menu = "searchId";
		}
		
		
		repaint();
		
	}
}
