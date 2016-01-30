import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class Screen extends JPanel implements ActionListener
{
    JButton storyButton;
    JButton resumeButton;
    JButton createResumeButton;
    JButton back;
    JButton createFile;
    
    JTextField resumeNameTF;
    
    JTextField fullName;
    JTextField email;
    JTextField mobile;
    JTextField objective;
    JTextField education;
    JTextField experience;
    JTextField skills;
    JTextField employment;
    JTextField teamwork;
    JTextField fileName;
    
    String sFullName = "";
    String sEmail = "";
    String sMobile = "";
    String sObjective = "";
    String sEducation = "";		//2
    String sExperience = "";	//2
    String sSkills = "";		//3
    String sEmployment = "";	//2
    String sTeamwork = "";		//2
    String sFileName = "";
    
    //substring of the above Strings
    String educationSub1 = "";
    String educationSub2 = "";
    String experienceSub1 = "";
    String experienceSub2 = "";
    String skillsSub1 = "";
    String skillsSub2 = "";
    String skillsSub3 = "";
    String employmentSub1 = "";
    String employmentSub2 = "";
    String teamworkSub1 = "";
    String teamworkSub2 = "";
    
    
    
    
    JTextField nameStory;
    JTextField noun;
    JTextField verb1;
    JTextField verb2;
    JTextField verb3;
    JTextField adjetive;
    
    JButton createStoryButton;
    
    String menu;
    String resumeName = "";
    
    String storyName = "";
    String storyName1 = "";
    String storyName2 = "";
    String storyName3 = "";
    String storyName4 = "";
    String storyName5 = "";
    
    int x = 0, y = 0, width = 0, height = 0;//help to set up
    
    private final static int WIDTH = 900;
    private final static int HEIGHT = 600;
    
    public Screen()
    {
        setLayout(null);
        
        storyButton = new JButton("Story");  //instantiate the button
        storyButton.setBounds(100,100,200,30); //set the location and size
        storyButton.addActionListener(this); //add the actionlistener to the button
        add(storyButton); //add the button the JPanel
        
        resumeButton = new JButton("Resume");
        resumeButton.setBounds(400,100,200,30);
        resumeButton.addActionListener(this);
        add(resumeButton);
        
        back = new JButton("BACK");
        back.setBounds(10,10,70,30);
        back.addActionListener(this);
        
        createFile = new JButton("create File");
        createFile.setBounds(640,60,120,30);
        createFile.addActionListener(this);
        
        fileName = new JTextField(50);
        fileName.setBounds(600, 10, 200, 30);
        
        resumeNameTF = new JTextField(50);
        resumeNameTF.setBounds(120,50,200,30);
        
        nameStory = new JTextField(50);
        nameStory.setBounds(160,50,200,30);
        noun = new JTextField(50);
        noun.setBounds(160,100,200,30);
        verb1 = new JTextField(50);
        verb1.setBounds(160,150,200,30);
        verb2 = new JTextField(50);
        verb2.setBounds(160,200,200,30);
        verb3 = new JTextField(50);
        verb3.setBounds(160,250,200,30);
        adjetive = new JTextField(50);
        adjetive.setBounds(160,300,200,30);
        
        x = 240;
        y = 50;
        width = 200;
        height = 30;
        int increment = 50;
        
        fullName = new JTextField(50);
        fullName.setBounds(x, y, width, height);
        y += increment;
        email = new JTextField(50);
        email.setBounds(x, y, width, height);
        y += increment;
        mobile = new JTextField(50);
        mobile.setBounds(x, y, width, height);
        y += increment;
        objective = new JTextField(50);
        objective.setBounds(x, y, width, height);
        y += increment;
        education = new JTextField(50);
        education.setBounds(x, y, width, height);
        y += increment;
        experience = new JTextField(50);
        experience.setBounds(x, y, width, height);
        y += increment;
        skills = new JTextField(50);
        skills.setBounds(x, y, width, height);
        y += increment;
        employment = new JTextField(50);
        employment.setBounds(x, y, width, height);
        y += increment;
        teamwork = new JTextField(50);
        teamwork.setBounds(x, y, width, height);
        y += increment;
        
       
        
        
        createResumeButton = new JButton("Create Resume");
        createResumeButton.setBounds(100,550,200,30);
        createResumeButton.addActionListener(this);
        
        createStoryButton = new JButton("Create Story");
        createStoryButton.setBounds(500,100,200,30);
        createStoryButton.addActionListener(this);
        
        menu = "mainMenu";
    }
    
    public Dimension getPreferredSize()
    {
        //Sets the size of the panel
        return new Dimension(WIDTH,HEIGHT);
    }
    
    public void paintComponent(Graphics g)
    {
        //draw background
        g.setColor(Color.white);
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        Font font = new Font("Arial", Font.PLAIN, 20);
        Font titleFont = new Font("Arial", Font.BOLD, 15);
        Font normalFont = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);
        g.setColor(Color.blue);
        
        if( menu.equals("resume") || menu.equals("createdResume")){
            
        	x = 15;
        	y = 75;
        	int increment = 50;
        	g.drawString("Name: ", x, y);
            y += increment;
            g.drawString("Email: ", x, y);
            y += increment;
            g.drawString("Mobile: ", x, y);
            y += increment;
            g.drawString("Field of work: ", x, y);
            y += increment;
            g.drawString("(2)Education: ", x, y);
            y += increment;
            g.drawString("(2)Relevant Experiences: ", x, y);
            y += increment;
            g.drawString("(3)Skills: ", x, y);
            y += increment;
            g.drawString("(2)Employments: ", x, y);
            y += increment;
            g.drawString("(2)Teamworks: ", x, y);
            y += increment;
            
            
            x = 500;
            y = 150;
            
            increment = 15;
            
            if( menu.equals("createdResume")){
            	
            	g.drawString("File Name: ", 500, 30);
            	
		        g.setColor(Color.black);
		        g.setFont(titleFont);
		        g.drawString(sFullName, x + 100, y);
		        y += increment;
		        g.setFont(normalFont);
		        g.drawString(sEmail, x + 100, y);
		        y += increment;
		        g.drawString("Mobile: " + sMobile, x + 100, y);
		        y += increment * 2;
		        
		        g.setFont(titleFont);
		        g.drawString("OBJECTIVE", x, y);
		        y += increment;
		        g.setFont(normalFont);
		        g.drawString("An employment opportunity in a " + sObjective + " related field", x, y);
		        y += increment * 2;
		        
		        g.setFont(titleFont);
		        g.drawString("EDUCATION", x, y);
		        y += increment;
		        g.setFont(normalFont);
		        g.drawString(educationSub1, x, y);
		        y += increment;
		        g.drawString(educationSub2, x, y);
		        y += increment * 2;
		        
		        g.setFont(titleFont);
		        g.drawString("RELEVANT EXPERIENCE", x, y);
		        y += increment;
		        g.setFont(normalFont);
		        g.drawString(experienceSub1, x, y);
		        y += increment;
		        g.drawString(experienceSub2, x, y);
		        y += increment * 2;
		        
		        g.setFont(titleFont);
		        g.drawString("SKILL", x, y);
		        y += increment;
		        g.setFont(normalFont);
		        g.drawString(skillsSub1, x, y);
		        y += increment;
		        g.drawString(skillsSub2, x, y);
		        y += increment;
		        g.drawString(skillsSub3, x, y);
		        y += increment * 2;
		        
		        g.setFont(titleFont);
		        g.drawString("EMPLOYMENT", x, y);
		        y += increment;
		        g.setFont(normalFont);
		        g.drawString(employmentSub1, x, y);
		        y += increment;
		        g.drawString(employmentSub2, x, y);
		        y += increment * 2;
		        
		        g.setFont(titleFont);
		        g.drawString("TEAMWORK", x, y);
		        y += increment;
		        g.setFont(normalFont);
		        g.drawString(teamworkSub1, x, y);
		        y += increment;
		        g.drawString(teamworkSub2, x, y);
		        //y += increment;
        	}
            
            
        }else if( menu.equals("Story")){
            g.drawString("Name: ", 35, 75);
            g.drawString("noun: ", 35, 125);
            g.drawString("verb : ", 35, 175);
            g.drawString("verb: ", 35, 225);
            g.drawString("verb: ", 35, 275);
            g.drawString("adjective: ", 35, 325);
            
            g.drawString(storyName , 450, 250);
            g.drawString(storyName1 , 450, 280);
            g.drawString(storyName2 , 450, 310);
            g.drawString(storyName3 , 450, 340);
            g.drawString(storyName4 , 450, 370);
        }
        
        
    }
    
    public void createFile() throws IOException {
    	File fout = new File(sFileName + ".txt");
    	FileOutputStream fos = new FileOutputStream(fout);
     
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
     
    	if(menu.equals("createdResume")){
    		
    		bw.write("             " + sFullName);
    		bw.newLine();
    		bw.write("             " + sEmail);
    		bw.newLine();
    		bw.write("             Mobile: " + sMobile);
    		bw.newLine();
    		bw.newLine();
    		bw.write("OBJECTIVE-------------------------------");
    		bw.newLine();
    		bw.write("An employment opportunity in a " + sObjective + " related field");
    		bw.newLine();
    		bw.newLine();
    		bw.write("EDUCATION");
    		bw.newLine();
    		bw.write(educationSub1);
    		bw.newLine();
    		bw.write(educationSub2);
    		bw.newLine();
    		bw.newLine();
    		bw.write("RELEVANT EXPERIENCE---------------------");
    		bw.newLine();
    		bw.write(experienceSub1);
    		bw.newLine();
    		bw.write(experienceSub2);
    		bw.newLine();
    		bw.newLine();
    		bw.write("SKILL-----------------------------------");
    		bw.newLine();
    		bw.write(skillsSub1);
    		bw.newLine();
    		bw.write(skillsSub2);
    		bw.newLine();
    		bw.write(skillsSub3);
    		bw.newLine();
    		bw.newLine();
    		bw.write("EMPLOYMENT------------------------------");
    		bw.newLine();
    		bw.write(employmentSub1);
    		bw.newLine();
    		bw.write(employmentSub2);
    		bw.newLine();
    		bw.newLine();
    		bw.write("TEAMWORK--------------------------------");
    		bw.newLine();
    		bw.write(teamworkSub1);
    		bw.newLine();
    		bw.write(teamworkSub2);
    		bw.newLine();
    		
    		
    	}else if(menu.equals("Story")){
    		bw.write(storyName);
    		bw.newLine();
    		bw.write(storyName1);
    		bw.newLine();
    		bw.write(storyName2);
    		bw.newLine();
    		bw.write(storyName3);
    		bw.newLine();
    		bw.write(storyName4);
    		bw.newLine();
    	}
     
    	bw.close();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource() == resumeButton )
        {
            //System.out.println("Resume Button");
            menu = "resume";
            
            removeAll();
            
            add(fullName);
            add(email);
            add(mobile);
            add(objective);
            add(education);
            add(experience);
            add(skills);
            add(employment);
            add(teamwork);
            
            
            add(createResumeButton);
            
            add(back);
        }else if( e.getSource() == createResumeButton){
            
        	add(createFile);
        	add(fileName);
        	
        	
        	
        	menu = "createdResume";
        	
        	sFullName = fullName.getText();
            sEmail = email.getText();
            sMobile = mobile.getText();
            sObjective = objective.getText();
            sEducation = education.getText();	//2
            sExperience = experience.getText();	//2
            sSkills = skills.getText();			//3
            sEmployment = employment.getText();	//2
            sTeamwork = teamwork.getText();		//2
            
            if(!sEducation.equals("")){
            	educationSub1 = sEducation.substring(0,sEducation.indexOf(';'));
                educationSub2 = sEducation.substring(sEducation.indexOf(';') + 1, sEducation.length());
            }
            
            if(!sExperience.equals("")){
            	experienceSub1 = sExperience.substring(0,sExperience.indexOf(';'));
                experienceSub2 = sExperience.substring(sExperience.indexOf(';') + 1, sExperience.length());
            }
            
            if(!sSkills.equals("")){
            	skillsSub1 = sSkills.substring(0,sSkills.indexOf(';'));
                skillsSub2 = sSkills.substring(sSkills.indexOf(';') + 1, sSkills.lastIndexOf(';'));
                skillsSub3 = sSkills.substring(sSkills.lastIndexOf(';') + 1, sSkills.length());
            }
            
            if(!sEmployment.equals("")){
            	employmentSub1 = sEmployment.substring(0, sEmployment.indexOf(';'));
                employmentSub2 = sEmployment.substring(sEmployment.indexOf(';') + 1, sEmployment.length());
            }
            
            if(!sTeamwork.equals("")){
            	 teamworkSub1 = sTeamwork.substring(0, sTeamwork.indexOf(';'));
                 teamworkSub2 = sTeamwork.substring(sTeamwork.indexOf(';') + 1, sTeamwork.length());
            }
            
            
            
            
           
            
            
        }
        
        if( e.getSource() == storyButton){
            menu = "Story";
            
            removeAll();
            
            add(nameStory);
            add(noun);
            add(verb1);
            add(verb2);
            add(verb3);
            add(adjetive);
            
            add(createStoryButton);
            
            add(back);
            
        }else if( e.getSource() == createStoryButton){
        	add(createFile);
        	add(fileName);
        	
            storyName = "Yesterday " + nameStory.getText() + " walk the " + noun.getText() + ".";
            storyName1 = "birds were " + verb1.getText() + " at me.";
            storyName2 = "I didn't know what to do, so I " + verb2.getText() + ".";
            storyName3 = "it was fun to " + verb3.getText() + " with friends";
            storyName4 = nameStory.getText() + " is " + adjetive.getText() + ".";

            
        }
        
        if(e.getSource() == back){
        	menu = "mainMenu";
        	
        	removeAll();
        	
        	
        	add(resumeButton);
        	add(storyButton);
        }
        
        if(e.getSource() == createFile){
        	
        	sFileName = fileName.getText();
        	
        	try {
				createFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        repaint();
    }
    
    
}