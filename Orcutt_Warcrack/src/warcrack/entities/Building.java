/*****************************************************************************
 * Class: Building
 * Author: Tyler Orcutt
 * Purpose: Handles Building functions for game Buildings
 ****************************************************************************/

package warcrack.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import helpfullThings.Position;
import warcrack.engine.Camera;
import warcrack.engine.Engine;
import warcrack.engine.SpriteSheet;

public class Building  extends Entity{
private ArrayList<String> buildfuncs;
private Entity toBuild;


	public Building(SpriteSheet spritesheet, Position position,
			Position Spritepos, Engine engine) {
		super(spritesheet, position, Spritepos, engine);
		// TODO Auto-generated constructor stub
		buildfuncs=new ArrayList<String>();
	
	}
	
	//draw the selected rectangle thingy
	public void DrawSelectd(Graphics g,Camera cam){
		if(super.isVisible()){
		if(super.isSelected()){
			g.setColor(Color.GREEN);
		   
			g.drawRect((int)(getPosition().getX()-cam.getX()), (int)(getPosition().getY()-cam.getY()), 96, 96);
			g.setColor(Color.WHITE);
		}
		}
	}
	
	
	//update the building - mostly for when its building stuff
	public void Update(long dt, long td){
		if(super.isdead()){
			return;
		}
		if(super.getHealth()<=0){
			super.setDead(true);
			
		}
				if(super.isBuilding() && toBuild!=null){
					super.incCBuildTime((int) td);
					//System.out.println(super.getcBuildTime());
					if(super.getcBuildTime()>=toBuild.getBuildTime()){
						//build complete
						if(toBuild.collide(super.getEngine().getEntities())){
							
						}
						
						
						toBuild.setVisible(true);
						super.getEngine().addEntity(toBuild);
						toBuild=null;
						//System.out.println("Build Complete");
						super.setcBuildTime(0);
						super.setBuilding(false);
						
					}
					
				}
	}
	
	
	//get the functions the building can perform
	public ArrayList<String> getBuildFunctions(){
		return buildfuncs;
	}
//add a function the building can perform 
	public void addBuildFunc(String func){
		buildfuncs.add(func);
	}
	
	//Build something!
	public void Build(String type){
		if(super.isBuilding() || type=="" || super.isdead()){
			return;
		}
		if(type=="Peon"){
			toBuild=new Peon(super.getEngine().getSpriteSheet("Peon"),new Position(this.getX()+128,this.getY()),this.getEngine());
			super.setBuilding(true);
		}
		if(type=="Grunt"){
			toBuild=new Grunt(super.getEngine().getSpriteSheet("Grunt"),new Position(this.getX()+128,this.getY()),this.getEngine());
			super.setBuilding(true);
		}
		if(toBuild!=null){
		
		if(super.getEngine().getGold()<toBuild.getBuildCost()){
			super.setBuilding(false);
			toBuild=null;
		}else{
			super.setcBuildTime(0);
			super.getEngine().setGold(super.getEngine().getGold()-toBuild.getBuildCost());
			if(super.getEngine().isFastBuildMode()){
				super.setcBuildTime(toBuild.getBuildTime());
			}
		}
	}
	}
	
	//get the object that it is building
	public Entity getToBuild(){
		return toBuild;
	}
	//for when the building dies?
	@Override
	public int getNewSpriteXPos(String dir) {
		// TODO Auto-generated method stub
		return super.getSpritePosition().getX();
	}
	@Override
	public int getNewSpriteYPos(String dir) {
		// TODO Auto-generated method stub
		return 0;
	}

}
