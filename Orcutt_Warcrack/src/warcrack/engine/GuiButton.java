/*****************************************************************************
 * Class:GUI Button
 * Author: Tyler Orcutt
 * Purpose:Clickable Buttons for GUI
 ****************************************************************************/

package warcrack.engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpfullThings.Point;
import helpfullThings.Position;

public class GuiButton {
	private Position position;
	private BufferedImage img;
	private String function;
public GuiButton(Position pos, BufferedImage img,String function){
	this.img=img;
	this.position=pos;
	this.function=function;
}
	
	//get the function of the button
public String getFunction(){
	return function;
}
	

//draw self
	public void Draw(Graphics g){
		g.drawImage(img, position.getX(), position.getY(),32,32, null);
	}
	
	
	//was i clicked on?
	public boolean Clicked(Point cp){
		if(cp.getX()>=position.getX() && cp.getX()<=position.getX()+32 &&cp.getY()>=position.getY() && cp.getY()<=position.getY()+32){
			return true;
		}
		return false;
	}
}
