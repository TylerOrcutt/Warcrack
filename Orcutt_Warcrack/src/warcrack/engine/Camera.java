/*****************************************************************************
 * Class: Camera
 * Author: Tyler Orcutt
 * Purpose: handles camera movement/position
 ****************************************************************************/
package warcrack.engine;

import helpfullThings.Position;

public class Camera {
private Position position;
public static final int CAM_SPEED=16;
private boolean isMoving;
private String Direction;
private int Height,Width;
public Camera(){
	position= new Position();
	isMoving=false;
	Direction="";
	Height=400;
	Width=800;
}

public Camera(Position position){
	this.position=position;
}

//update the camera position if need be
public void Update(long dt){
	switch(Direction){
	case "North": position.setY((int) (position.getY()-(CAM_SPEED*dt)));
				  	break;
	case "South":  position.setY((int) (position.getY()+(CAM_SPEED*dt)));
	   break;
	case "East":   position.setX((int) (position.getX()+(CAM_SPEED*dt)));
		  	break;
    case "West":   position.setX((int) (position.getX()-(CAM_SPEED*dt)));
			  	break;
	default:isMoving=false;
			Direction="";
			
	}
}
//set the camera being moves -> a key must have been pressed or releases
public void isMoving(String direction){
		switch(direction){
		case "North": isMoving=true;
					  Direction="North";
					  	break;
		case "South": isMoving=true;
		  Direction="South";
		   break;
		case "East": isMoving=true;
			  Direction="East";
			  	break;
	    case "West": isMoving=true;
				  Direction="West";
				  	break;
		default:isMoving=false;
				Direction="";
				
		}
}


public void resize(){
	
}
//getters and setter
public int getWidth(){
	return Width;
}
public int getHeight(){
	return Height;
}

public Position getPosition() {
	return position;
}

public void setPosition(Position position) {
	this.position = position;
}

public void setX(int x){
  position.setX(x);	
}
public void setY(int y){
	position.setY(y);
}
public int getY(){
	return position.getY();
}
public int getX(){
	return position.getX();
}



}
