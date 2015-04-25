/*****************************************************************************
 * Class:Entity
 * Author: Tyler Orcutt
 * Purpose: Base functions for all game objects
 ****************************************************************************/


package warcrack.entities;



import helpfullThings.Point;
import helpfullThings.Position;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.util.ArrayList;

import warcrack.engine.Camera;
import warcrack.engine.Engine;
import warcrack.engine.Map;
import warcrack.engine.SpriteSheet;
import warcrack.engine.Map.Block;





public abstract class Entity {
	private int BASE_SPEED=2;
	private int speed;
	private Position position;
	public static enum Direction{ NORTH,EAST,SOUTH,WEST, NULL}
	private Direction direction;
	private SpriteSheet spritesheet;
	private BufferedImage img;
	private boolean selected;
	private ArrayList<Point> movepoint;
	private boolean isMoving;
	private Position SpritePosition;
	private long lastUpdate;
	private boolean visible;
	private Engine engine;
	private int buildTime;
	private int cBuildTime;
	private boolean isbuilding;
	private int buildCost;
	private int MaxHealth;
	private int Health;
	private boolean isDead;
	private boolean isEnemy;
	private boolean isindestructible;
//	public Entity(){
		//this(new Spritenew Position(0,0));
	//}
	public Entity(SpriteSheet spritesheet, Position position, Engine engine){
			this(spritesheet,position, new Position(),engine);
			
	}
	
	public Entity(SpriteSheet spritesheet, Position position, Position Spritepos,Engine engine){
		this.position=position;
		this.spritesheet=spritesheet;
		selected=false;
		SpritePosition=Spritepos;
		img=spritesheet.getSprite(Spritepos.getX(),Spritepos.getY());
		isMoving=false;
		speed=BASE_SPEED;
		lastUpdate=0;
		visible=true;
		this.engine=engine;
		buildTime=1000;
		
		isbuilding=false;
		cBuildTime=0;
		buildCost=0;
		MaxHealth=100;
		Health=100;
		isDead=false;
		isEnemy=false;
		isindestructible=false;
	}
	
	//this mostly handles the object moving and spriteupdates checks for collisions and what not
	public void Update(long dt,long td){
	/*	switch(direction){
		case NORTH:
				position.setX(position.getX()+speed);
			break;
			
		}*/
		if(isDead){
			
			return;
		}
		if(Health<=0){
			SpritePosition.setX(0);
			SpritePosition.setY(getNewSpriteYPos("die"));
			isDead=true;
			this.deselect();
			this.img=spritesheet.getSprite(SpritePosition.getX(), SpritePosition.getY());
			getEngine().getDeadEntities().add(this);
			if(this.isEnemy){
			
			getEngine().getEnemies().remove(this);
			}else{
				getEngine().getEntities().remove(this);
			}
			//	System.out.println("dead");
		
			return;
		}
	
		if(isMoving ){
			if(movepoint.size()==0){
				isMoving=false;
				movepoint=null;
				
			}
				if(movepoint!=null){
					
						if(position.getX()==movepoint.get(0).getX() && position.getY()==movepoint.get(0).getY()  ){
							//System.out.println("move complete");
							movepoint.remove(0);
							if(movepoint.size()==0){
								movepoint=null;
								
								isMoving=false;
							}
						}else{
						//	System.out.println("moving");
						//	System.out.println(td);
								double angle= Math.atan2(movepoint.get(0).getY()- position.getY(),movepoint.get(0).getX()-position.getX());
								int nx=(int) ((Math.cos(angle)*speed)*td);
								int ny=(int) ((Math.sin(angle)*speed)*td);
								position.setX(position.getX()+nx);
								position.setY(position.getY()+ny);
								if(engine.getMap().collide(this)){
					    			this.stopMoving();
					    			this.undoMove(nx, ny);
								     return;
					    		}
								if(this.collide(engine.getEntities())){
									this.stopMoving();
					    			this.undoMove(nx, ny);
								     return;
					    		}
								if(this.collideBuilding(engine.getBuildings())){
									this.stopMoving();
					    			this.undoMove(nx, ny);
								     return;
					    		}	
								
								
								angle=-angle;
								//System.out.println(ny);
								
						
								if(ny==-1  && nx==0){
									 //up
									//  this.SpritePosition.setX(0);
									this.SpritePosition.setX(this.getNewSpriteXPos("up"));
									}
								if(ny==1 && nx==0){
									//  this.SpritePosition.setX(6*32);
									this.SpritePosition.setX(this.getNewSpriteXPos("down"));
									  //down
								}
							
								
								if(nx==-1  && ny==0){
									//  this.SpritePosition.setX(4*32);
										this.SpritePosition.setX(this.getNewSpriteXPos("left"));
									 //left
									}
									
								
									
									
								
									
									if(nx==1 &&ny==0){
										//  this.SpritePosition.setX(2*32);
										  //right
										this.SpritePosition.setX(this.getNewSpriteXPos("right"));
									}
								
								
								if(dt-lastUpdate>100){
								  this.SpritePosition.setY(this.SpritePosition.getY()+32);
								  	lastUpdate=dt;
								  
								}
								  if(this.SpritePosition.getY()>64){
									  this.SpritePosition.setY(0);
								  }
								this.img=spritesheet.getSprite(SpritePosition.getX(), SpritePosition.getY());
							
								
								
								//isMoving=false;
		//		position.setX(movepoint.getX());
			//position.setY(movepoint.getY());
							}
			
			    //movepoint=null;
				//isMoving=false;
				}else{
					isMoving=false;
				}
		}
		
			//img=spritesheet.getSprite(0,0);
		
	}
	
	

//collide function for other entites	
	
	  public boolean collide( ArrayList<Entity>entities){
		  Entity ent=this;
		  for(Entity b : entities){
			  	if(b!=this && b.isVisible() && !b.isdead()){
			   if(ent.getX()+32>=b.position.getX() && ent.getX()<=b.position.getX()+32 && ent.getY()+32>=b.position.getY() && ent.getY()<=b.position.getY()+28){
				   return true;
			   }
			  	}
			  
		  }
		  return false;
	  }
	
	
//collide function for building entities	
	  public boolean collideBuilding( ArrayList<Entity>entities){
		  Entity ent=this;
		  for(Entity b : entities){
			  	if(b!=this ){
			   if(ent.getX()+32>=b.getPosition().getX() && ent.getX()<=b.getPosition().getX()+32 && ent.getY()+64>=b.getPosition().getY() && ent.getY()<=b.getPosition().getY()+60){
				   		
				   return true;
			   }
			  	}
			  
		  }
		  return false;
	  }
	
	
	
	  
//draw the object
	public void Draw(Graphics g,Camera cam){
		/*if(selected){
			g.setColor(Color.GREEN);
		   
			g.drawOval(position.getX()-cam.getX()-1, position.getY()-cam.getY()+20, 32, 16);
			g.setColor(Color.WHITE);
		}*/
		
		if(visible){
		g.drawImage(img,(int)( position.getX()-cam.getX()), (int)(position.getY()-cam.getY()), null);
		}
		
	}
	
	//draw the selected indicator
	public void DrawSelectd(Graphics g,Camera cam){
		if(visible){
		if(selected){
			g.setColor(Color.GREEN);
		   
			g.drawOval((int)(position.getX()-cam.getX()-1), (int)(position.getY()-cam.getY()+20), 32, 16);
			g.setColor(Color.WHITE);
		}
		}
	//	g.drawImage(img, position.getX()-cam.getX(), position.getY()-cam.getY(), null);
		
		
	}
	
//undo the last move - probably collided with something
	public void undoMove(int pchangex, int pchangey){
		this.position.setX(this.position.getX()-pchangex);
		this.position.setY(this.position.getY()-pchangey);
	}
	
	//add points to move for -> ArrayList for pathfinding(not completely implemented)
	public void moveTo(ArrayList<Point> point){
		isMoving=true;
		movepoint=point;
	}
	//get the distance to a point
	public int distanceTo(Point point){
		int x=Math.abs(this.getX()-point.getX());
		int y=Math.abs(this.getY()-point.getY());
		
		return (int)Math.sqrt((x*x)+(y*y));
	}
	
	//sett sprite stuff
	public abstract int getNewSpriteXPos(String dir);
	public abstract int getNewSpriteYPos(String dir);
	
	//getters and setters	
	
	public boolean isVisible(){
		return visible;
	}
	public void setVisible(boolean vis){
		this.visible=vis;
	}
	public void stopMoving(){
		isMoving=false;
		movepoint=null;
	}
	
	public boolean IsMoving(){
		return isMoving;
	}
	
	public boolean isSelected(){
		return selected;
	}
	public void select(){
		selected=true;
	}
	public void deselect(){
		selected=false;
	}
public int getBuildCost(){
	return buildCost;
}
public void setBuildCost(int cost){
	this.buildCost=cost;
}
	public int getBuildTime(){
		 return this.buildTime;
	}
	public void setBuildTime(int time){
		this.buildTime=time;
	}
	
	public int getcBuildTime(){
		 return this.cBuildTime;
	}
	public void setcBuildTime(int time){
		this.cBuildTime=time;
	}
	public void incCBuildTime(int td){
		this.cBuildTime+=1*td;
	}
	public boolean isBuilding(){
		return isbuilding;
	}
	public void setBuilding(boolean ib){
		isbuilding=ib;
	}
	
	public int getHealth(){
		return Health;
	}
	public void setHealth(int hp){
		Health=hp;
	}
	public int getMaxHealth(){
		return MaxHealth;
	}
	public boolean isdead(){
		return isDead;
	}
	public void setDead(boolean id){
		isDead=id;
	}
	public void setisIndestructible(boolean is){
		this.isindestructible=is;
	}
	public boolean isIndestructible(){
		return this.isindestructible;
	}
	public void setisEnemy(boolean is){
		this.isEnemy=is;
	}
	public boolean getisEnemy(){
		return this.isEnemy;
	}
	public Position getPosition(){
		return position;
	}
	public void setPosition(Position position){
		this.position=position;
	}
	
	
	public Position getSpritePosition(){
		return SpritePosition;
	}
	public void setSpritePosition(Position position){
		this.SpritePosition=position;
	}
	
	
	public int getX(){
		return position.getX();
	}
	public int getY(){
		return position.getY();
	}
	
	public void setX(int x){
		this.position.setX(x);
	}
	public void setY(int y){
		this.position.setY(y);
		
	}
	
	public String isKindOf(){
		return "Entity";
	}
	public Engine getEngine(){
		return engine;
	}
	public BufferedImage getSprite(){
		return img;
	}
	
}
