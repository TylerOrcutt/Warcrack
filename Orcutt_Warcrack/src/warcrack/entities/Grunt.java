/*****************************************************************************
 * Class: Grunt
 * Author: Tyler Orcutt
 * Purpose: Grunt AI
 ****************************************************************************/

package warcrack.entities;

import java.util.ArrayList;

import helpfullThings.Point;
import helpfullThings.Position;
import warcrack.engine.Engine;
import warcrack.engine.SpriteSheet;

public class Grunt extends Entity {
	private Entity target;
	private boolean isAttacking;
	public Grunt(SpriteSheet spritesheet, Position position, Engine engine) {
		super(spritesheet, position, engine);
		// TODO Auto-generated constructor stub
		
		super.setBuildCost(100);
		this.isAttacking=false;
		
	}
	public Grunt(SpriteSheet spritesheet, Position position, Engine engine, boolean isEnemy) {
		super(spritesheet, position, engine);
		// TODO Auto-generated constructor stub
		
		super.setBuildCost(100);
		this.isAttacking=false;
		super.setisEnemy(isEnemy);
	}
	
	//Grunts have the ability to attack stuff so do that if in range
	public void Update(long dt, long td){
		super.Update(dt, td);
	   if(super.isdead()){
		   return;
	   }
	   
	   //try to find a player entity to attack
		if(super.getisEnemy()){
		for(Entity ent:super.getEngine().getEntities()){
			if(ent.isdead()==false){
			if(super.distanceTo(new Point(ent.getX(),ent.getY()))<100){
				ArrayList<Point> pt = new ArrayList<Point>();
				pt.add(new Point(ent.getX(),ent.getY()));
				target=ent;
				super.moveTo(pt);
				break;
			}
			}
			target=null;
		}
		
		
		if(target==null){
			//no player entites close enough try a building?
			for(Entity ent:super.getEngine().getBuildings()){
				if(ent.isdead()==false && !ent.isIndestructible()){
				if(super.distanceTo(new Point(ent.getX(),ent.getY()))<150){
					ArrayList<Point> pt = new ArrayList<Point>();
					pt.add(new Point(ent.getX(),ent.getY()));
					target=ent;
					super.moveTo(pt);
					//System.out.println("tarting building");
					break;
				}
				}
				target=null;
			}
		}
		
		
		
		}else{
			//aww its a friendly grunt find an enemy
			for(Entity ent:super.getEngine().getEnemies()){
				if(ent.isdead()==false){
				if(super.distanceTo(new Point(ent.getX(),ent.getY()))<100){
					ArrayList<Point> pt = new ArrayList<Point>();
					pt.add(new Point(ent.getX(),ent.getY()));
					target=ent;
					super.moveTo(pt);
					break;
				}
				}
				target=null;
			}
		}
		
		
		if(target!=null){
			//do we have a target? is it in range? attack!
			if(super.distanceTo(new Point(target.getX(),target.getY()))<60){
				if(target.isdead()==false && target.isVisible()){
				this.isAttacking=true;
					this.stopMoving();
				target.setHealth(target.getHealth()-1);
				if(target.isKindOf()=="TownHall"){
				System.out.println("TownHall Hit");
				}
				}
			}else{
				this.isAttacking=false;
			}
			
		}
		
		
	}
	
	
	//sprite stuff
	@Override
	public  int getNewSpriteXPos(String dir){
		if(dir=="up"){
			return 0;
		}
	if(dir=="down"){
		return 96;
		}
	if(dir=="left"){
		return 64;
	}
	if(dir=="right"){
		
		return 32;
		
	}
		
		return 0;
	}
	@Override
	public int getNewSpriteYPos(String dir) {
		// TODO Auto-generated method stub
		if(dir=="die"){
			return 6*32;
		}
		return 0;
	}

	public String isKindOf(){
		return "Grunt";
	}


}
