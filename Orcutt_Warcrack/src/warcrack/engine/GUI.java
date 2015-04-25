/*****************************************************************************
 * Class:GUI
 * Author: Tyler Orcutt
 * Purpose: Handles GUI components and draw GUI
 ****************************************************************************/
package warcrack.engine;

import helpfullThings.Point;
import helpfullThings.Position;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import warcrack.entities.Building;
import warcrack.entities.Entity;

public class GUI {
	private Image botImage;
	private boolean isTyping;
	private String typedText;
	private Engine engine;
	private SpriteSheet  buildicons;
	private ArrayList<GuiButton> guibuttons;
	
public GUI(Engine engine){
	try{
		botImage=ImageIO.read(new File("res/gui_bottom.png"));
		buildicons= new SpriteSheet("UI_Icons",96);
	}catch(Exception e){
		
	}
	this.typedText="";
	 isTyping=false;
	 this.engine=engine;
	 guibuttons= new ArrayList<GuiButton>();
}
//draw the GUI
public void Draw(Graphics g){
	if(botImage!=null){
		g.drawImage(botImage, 0, 425,800, 175, null);
	}
	
	if(isTyping){
	
		g.drawString(typedText, 350, 300);
	
	
	}
	g.drawString("Gold:" + engine.getGold(), 0, 10);
	Entity ent;
	//if there is a building selected the Interface for that object needs to be drawn
	if((ent=engine.getSelectedBuilding())!=null){
		  g.drawImage(ent.getSprite(),50,450,96,96,null);
			g.drawString(ent.isKindOf(),150,500);
			//Object Health
			if(ent.isIndestructible()){
				g.drawString("Indestructible",150,520);
			}else{
			g.drawString("Health: "+ent.getHealth()+"/"+ent.getMaxHealth(),150,520);
			}
			
			//is the object building something? 
		  if(((Building)engine.getSelectedBuilding()).isBuilding()){
			 double pro= (((Building)engine.getSelectedBuilding()).getcBuildTime());
			  double total=(((Building)engine.getSelectedBuilding()).getBuildTime());
			 double percent=(pro/total)*100;
			 NumberFormat formatter = new DecimalFormat("#0.00");     
			 g.drawString("Building " + ((Building)engine.getSelectedBuilding()).getToBuild().isKindOf(),380,480);
			  g.drawString(formatter.format(percent) + "%",400,500);
		  }
		  //draw GUI buttons
			for(GuiButton btn:guibuttons){
				btn.Draw(g);
			}
			
			
	}
}

//Test if GUI button was CLicked
public void GUI_Click(Point p){
	for(GuiButton btn:guibuttons){
	if(btn.Clicked(p)){
		//System.out.println("GUI clicked:" + btn.getFunction());
		((Building)engine.getSelectedBuilding()).Build(btn.getFunction());
		return;
	}
	
	}
}

//generate GUI buttons when something was selected
public void generateGUIButtons(Entity ent){
	guibuttons.clear();
	int baseX=600;
	int startx=baseX;
	int starty=430;
	int ccols=0;
	int cols=3;
	for(String func :((Building)ent).getBuildFunctions()){
		if(func=="Peon"){
			guibuttons.add(new GuiButton(new Position(startx,starty),buildicons.getSprite(0, 0),func));
			
		}
		if(func=="Grunt"){
			guibuttons.add(new GuiButton(new Position(startx,starty),buildicons.getSprite(96, 0),func));
			
		}
		
		startx+=40;
		ccols++;
		if(ccols==cols){
			starty+=40;
			startx=baseX;
			ccols=0;
		}
	}
}

//delete GUI buttons
public void ClearGuiButtons(){
	guibuttons.clear();
}

//begin/end typing
public void setTyping(boolean t){
	this.isTyping=t;
}
public boolean Typing(){
	return this.isTyping;
}

public void updateTypedText(char c){
if(c=='' && typedText.length()>0){
	typedText=typedText.substring(0, typedText.length()-1);
}else{
	typedText += c;
}
}

//clear the text also check if a cheet was entered
public void ClearText(){
	this.typedText=this.typedText.substring(1, typedText.length());
	
	if(this.typedText.equals("showmethemoney")){
		engine.setGold(engine.getGold()+1000);
	}
	if(this.typedText.equals("operationcwall")){
		engine.setFatBuildMode(!engine.isFastBuildMode());
	}
	this.typedText="";
}

public String getText(){
	return typedText;
}
}
