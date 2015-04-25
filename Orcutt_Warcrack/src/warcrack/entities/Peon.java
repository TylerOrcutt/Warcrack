/*****************************************************************************
 * Class:Peon
 * Author: Tyler Orcutt
 * Purpose:Peon AI
 ****************************************************************************/
package warcrack.entities;


import java.util.ArrayList;

import helpfullThings.Point;
import helpfullThings.Position;
import warcrack.engine.Engine;
import warcrack.engine.SpriteSheet;

public class Peon extends Entity {
private boolean isMining;
private TownHall cmdCenter;
private GoldMine gm;
private Entity inBuilding;
private Entity travilingToBuilding;
private int TimeInBuilding;
private int LastUpdate;
public static int MiningTime=200;
	public Peon(SpriteSheet spritesheet, Position position, Engine engine) {
		super(spritesheet, position, engine);
		// TODO Auto-generated constructor stub
		this.isMining=false;
		TimeInBuilding=0;
		super.setBuildCost(50);
	}

	//move and decide where togo next
	public void Update(long dt, long td){
		super.Update(dt,td);
		if(gm!= null && cmdCenter!=null && isMining && !super.IsMoving()){
				if(inBuilding!=null){
					TimeInBuilding+=1*td;
					if(TimeInBuilding>=MiningTime){
						TimeInBuilding=0;
						if(inBuilding==gm)
						{
							travilingToBuilding=cmdCenter;
						}else{
							travilingToBuilding=gm;
						}
						
						inBuilding=null;
						this.setVisible(true);
						ArrayList<Point> pts=new ArrayList<Point>();
								pts.add(new Point(travilingToBuilding.getX()+16,travilingToBuilding.getY()+32));
						super.moveTo(pts);
					}
						
				
				}
		
		}
		
	}
	
	//special collide building
	//if we hit a gold mine peons are now have the status of Mining untill they are told to do something else
	//when they are mining they cannot collide with other entities
	//but this function also needs to locate the nearest townhall to dump the gold off
	//ALso needs to check if it hit the townhall so the peon can go back to the goldmine to 
	//pick up some more gold
	  public boolean collideBuilding( ArrayList<Entity>entities){
		  Entity ent=this;
		  for(Entity b : entities){
			  	if(b!=this){
			   if(ent.getX()+20>=b.getX() && ent.getX()<=b.getX()+64 && ent.getY()+32>=b.getY() && ent.getY()<=b.getPosition().getY()+60){
				   			if(b.isKindOf()=="GoldMine"){
				   					TownHall cmd;
				   				if((cmd=getClosestCommanderCenter())!=null){
				   				super.setVisible(false);
				   				isMining = true;
				   					cmdCenter=cmd;
				   					gm=(GoldMine) b;
				   					inBuilding=b;
				   				//	this.deselect();
				   				}else{
				   					return true;
				   				}
				   			}
				   			if(b.isKindOf()=="TownHall"){
				   				if(isMining){
				   				super.setVisible(false);
				   				inBuilding=b;
				   				super.getEngine().UpdateGold();
				   			}}
				   	
				   return true;
			   }
			  	}
			  
		  }
		  return false;
	  }
	  
	  
	  //collision for other entities 
	  public boolean collide( ArrayList<Entity>entities){
		  Entity ent=this;
		  for(Entity b : entities){
			  	if(b!=this && b.isVisible() && !this.isMining && !super.isdead()){
			   if(ent.getX()+32>=b.getPosition().getX() && ent.getX()<=b.getPosition().getX()+32 && ent.getY()+32>=b.getPosition().getY() && ent.getY()<=b.getPosition().getY()+28){
				   return true;
			   }
			  	}
			  
		  }
		  return false;
	  }
	  
	  //get the closest townhall
	  public TownHall getClosestCommanderCenter(){
		  TownHall cmd=null;
		 Integer distance=null;
		  for(Entity ent:super.getEngine().getBuildings()){
			  if(ent.isKindOf()=="TownHall"){	 
			  if(distance!=null){
				  int dist=super.distanceTo(new Point(ent.getX(),ent.getY()));
			  		 if(distance.intValue()>dist){
			  			 distance = dist;
			  			 cmd=(TownHall) ent;
			  		 }
			  	 }else{
			  		  int dist=super.distanceTo(new Point(ent.getX(),ent.getY()));
			  		 distance = dist;
		  			 cmd=(TownHall) ent;
			  	 }
		  }
		  }
		  
		  return cmd;
	  }
	  
	  //gett
	public boolean isInBuilding(){
		if(inBuilding==null){
			return false;
		}
		return true;
		
	}
	public void stopMining(){
		gm=null;
		cmdCenter=null;
				isMining=false;
	}
	
	//sprite stuff
	public int getNewSpriteYPos(String dir){
		if(dir=="die"){
			return 6*32;
		}
		return 0;
	}
	
	public int getNewSpriteXPos(String dir){
		if(dir=="up"){
			return 0;
		}
	if(dir=="down"){
		return 4*32;
		}
	if(dir=="left"){
		return 6*32;
	}
	if(dir=="right"){
		return 2*32;
	}
	
		return 0;
	}
	public String isKindOf(){
		return "Peon";
	}
}
