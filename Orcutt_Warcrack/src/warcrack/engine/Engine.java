/*****************************************************************************
 * Class: Engine
 * Author: Tyler Orcutt
 * Purpose: Manages all game objects/ main draw function
 ****************************************************************************/


package warcrack.engine;

import helpfullThings.Point;
import helpfullThings.Position;
import helpfullThings.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.Timestamp;
import java.util.ArrayList;

import warcrack.Display;
import warcrack.FinalProject;
import warcrack.entities.Grunt;
import warcrack.entities.TownHall;
import warcrack.entities.Entity;
import warcrack.entities.GoldMine;
import warcrack.entities.Peon;


public class Engine implements Runnable {
	private Display display;
	private Map map;
	private Camera camera;
	private GUI gui;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> Buildings;
	private ArrayList<Entity> enemies;
	private ArrayList<Entity> deadEntities;
	private long lastUpdate,currentTime;
	private boolean isRunning;
	private int Gold;
	private boolean fastBuildMode;
	SpriteSheet  SSpeon;
	SpriteSheet  SSgrunt;
	private boolean MainMenu;
	
	private Entity selectedBuilding;
	private boolean GameOver;
public static int GoldInc=10;
	FinalProject fp;
	public Engine(FinalProject fp){
		//this.display=display;
	
		Gold=0;
		this.fp=fp;
		SpriteSheet sp= new SpriteSheet("orcbuilder",32);
		SpriteSheet mapsheet = new SpriteSheet("sp2",32);
		map = new Map("map001",mapsheet);
		 SSpeon= new SpriteSheet("peon",32);
		 SSgrunt= new SpriteSheet("orcgrunt",32);
		entities= new ArrayList<Entity>();
		entities.add(new Peon(SSpeon,new Position(407,160),this));
		entities.add(new Peon(SSpeon,new Position(407,120),this));
		entities.add(new Peon(SSpeon,new Position(360,160),this));
	//	player=new Player(sp,new Position());
		Buildings = new ArrayList<Entity>();
		SpriteSheet  buildings= new SpriteSheet("buildings",96);
		Buildings.add((new GoldMine(buildings, new Position(200,100),this)));
		Buildings.add((new TownHall(buildings, new Position(477,-6),this)));
		enemies = new ArrayList<Entity>();
		enemies.add(new Grunt(SSgrunt,new Position(1245,543),this,true));
		enemies.add(new Grunt(SSgrunt,new Position(1245,590),this,true));
		enemies.add(new Grunt(SSgrunt,new Position(1145,543),this,true));
		
		deadEntities = new ArrayList<Entity>();
		camera= new Camera();
		gui=new GUI(this);
		isRunning=true;
		fastBuildMode=false;
		this.GameOver=false;
	}
	
	public void update(){
		
	}
	
	
	//Draw Everything!
	public void renderAll(Graphics g){

		
	//	System.out.println("redrawing");
		currentTime=new Timestamp(System.currentTimeMillis()).getTime();
	
    	
 
    	//g.drawRect(10, 10, 100, 100);
    	camera.Update((currentTime-lastUpdate)/20);
    	map.Draw(g, camera);
    	
    	for(Entity ent:deadEntities){
    		ent.Draw(g, camera);
    	}
    	
    	for(Entity ent: Buildings){
    		if(!GameOver){
    		ent.Update(currentTime,(currentTime-lastUpdate)/20);
    		}
    		ent.Draw(g, camera);
    		
    	}
    	
    	for(Entity ent:Buildings){
    		ent.DrawSelectd(g, camera);
    	}
    	
    
    	
     for(int i=0;i<entities.size();i++){
    		
    	 entities.get(i).Update(currentTime,(currentTime-lastUpdate)/20);
    		if(i<entities.size() && entities.get(i)!=null){//may have been deleted in the update
    			
    			entities.get(i).DrawSelectd(g, camera);
    		}else{
    			i--;
    		}
    	}
    	
    	
     for(int i=0;i<enemies.size();i++){
    	 if(!GameOver){
    	 enemies.get(i).Update(currentTime,(currentTime-lastUpdate)/20);
    	 }
   		if(i<enemies.size() && enemies.get(i)!=null){//may have been deleted in the update
   			enemies.get(i).Draw(g, camera);
		}else{
			i--;
		}
    	}
    	
    	for(Entity ent:entities){
    		ent.Draw(g,camera);
    	}
    
    gui.Draw(g);
    lastUpdate=currentTime;
    
    
    
	if(this.Gold<50 && entities.size()==0){
		//lost
		g.drawString("Defeat", fp.getWidth()/2,fp.getHeight()/2);
		GameOver=true;
	}
	
	if(enemies.size()==0){
		//win
		
		g.drawString("Victory", fp.getWidth()/2,fp.getHeight()/2);
		GameOver=true;
	
	}
		
	}

	
//set the entites within the rectangle to be selected
	public void selectEntites(Rectangle rect){
		if(GameOver){
			return;
		}
		/*int x=rect.getMaxX();
		int y =rect.getMaxY();
		System.out.println("rec:: X:" + x+ "  Y:"+y);
		*/
		boolean bs=false;
		for(Entity ent:Buildings){
			if(!bs){
			if(ent.getX()+96>=rect.getX()&& ent.getX()<=rect.getMaxX() &&
					ent.getY()+96>=rect.getY() && ent.getY()<=rect.getMaxY()){
				ent.select();
				bs=true;
				selectedBuilding=ent;
				gui.generateGUIButtons(ent);
			}else{
				ent.deselect();
			}
			}else{
				ent.deselect();
			}
			
		}
		if(!bs){
			selectedBuilding=null;
			gui.ClearGuiButtons();
		}
		
		
		for(Entity ent:entities){
			if(!bs){
				if(ent.getX()+32>=rect.getX()&& ent.getX()<=rect.getMaxX() &&
						ent.getY()+32>=rect.getY() && ent.getY()<=rect.getMaxY()){
					ent.select();
				}else{
					ent.deselect();
				}
			}else{
				ent.deselect();
			}
		
		}
		
	}
	
//move the selected entites	
	public void moveSelected(Point point){
		if(GameOver){
			return;
		}
		for(Entity ent:entities){
			if(ent.isSelected()){
			if(ent.isKindOf()=="Peon"){
					if(!((Peon)ent).isInBuilding()){
						ent.moveTo(map.findPath(ent,point));
						((Peon)ent).stopMining();
						ent.setVisible(true);
					}
			}else{
				ent.moveTo(map.findPath(ent,point));
			}
		}
			}
	}

//thread loop
	@Override
	public void run() {
		while(isRunning){
			fp.getDisplay().repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	}
	
//getters and setters	
	public void addEntity(Entity ent){
		entities.add(ent);
	}
	
	
	public void setCameraMoving(String Direction){
		camera.isMoving(Direction);
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public Entity getSelectedBuilding(){
		return selectedBuilding;
	}
	
	public void setTyping(boolean t){
		gui.setTyping(t);
	}
	public boolean Typing(){
		return gui.Typing();
	}
	public void updateGuiTypeString(char c){
		gui.updateTypedText(c);
	}
	public void guiClearText(){
		gui.ClearText();
	}
	

	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	public ArrayList<Entity> getBuildings(){
		return Buildings;
	}
	public ArrayList<Entity> getEnemies(){
		return enemies;
	}
	public ArrayList<Entity> getDeadEntities(){
		return deadEntities;
	}
	public Map getMap(){
		return map;
	}
	
	public GUI getGUI(){
		return gui;
	}
	public void UpdateGold(){
		Gold+=GoldInc;
	}
	public int getGold(){
		return Gold;
	}
	public void setGold(int g){
		this.Gold=g;
	}
	
	public boolean isFastBuildMode(){
		return fastBuildMode;
	}
	public void setFatBuildMode(boolean fast){
		this.fastBuildMode=fast;
	}
	public SpriteSheet getSpriteSheet(String sheet){
		if(sheet=="Peon"){
			return SSpeon;
		}
		if(sheet=="Grunt"){
			return SSgrunt;
		}
		return null;
	}

	
}
