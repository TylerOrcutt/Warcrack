/*****************************************************************************
 * Class: Map
 * Author: Tyler Orcutt
 * Purpose: handles the map(load, draw), entity collision with map objects
 ****************************************************************************/

package warcrack.engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import warcrack.FinalProject;
import warcrack.entities.Entity;
import helpfullThings.Point;
import helpfullThings.Position;

public class Map {
	
private ArrayList<Block> blocks;
private SpriteSheet spriteSheet;
private Block blks[][];

private  int minX, maxX,minY,maxY;
private int offsetX,offsetY;
public Map(String map,SpriteSheet sprite){
	minX=0; maxX=0;minY=0;maxY=0;
	offsetX=0;
	offsetY=0;
	this.spriteSheet=sprite;
	loadMap(map);
	
	//sort it
	sort();
	
	
}

//entity collide with a blocked map object?
  public boolean collide(Entity ent){
	  for(Block b : blocks){
		  if(b.isBlocked){
		   if(ent.getX()+32>=b.position.getX() && ent.getX()<=b.position.getX()+32 && ent.getY()+32>=b.position.getY() && ent.getY()<=b.position.getY()+28){
			   return true;
		   }
		  }
	  }
	  return false;
  }

	public void Update(){
		
	}
	
	
	//.... cant figure this
	public ArrayList<Point> findPath(Entity ent, Point point){
		ArrayList<Point> points= new ArrayList<Point>();
		
		//Translate points
		 Point startPoint=new Point((ent.getX()/32)+offsetX, (ent.getY()/32)+offsetY);
		 Point endPoint=new Point((point.getX()/32)+offsetX, (point.getY()/32)+offsetY);
		 int maxDistance=blocks.size();
		//System.out.println("StartX: " + startPoint.getX() + "  Y:"+ startPoint.getY());
		
		//	System.out.println("X: " + 	blks[startPoint.getX()][startPoint.getY()].position.getX() + "  Y:"+ 	blks[startPoint.getX()][startPoint.getY()].position.getY());
	//	 System.out.println("X: " + 	blks[endPoint.getX()][endPoint.getY()].position.getX() + "  Y:"+ 	blks[endPoint.getX()][endPoint.getY()].position.getY());
		
	//	 for(int x=startPoint.getX();x
		points.add(point) ;

		 
		 
		 return points;
		
	
	}
	//redraw map but only visible stuff
	public void Draw(Graphics g, Camera cam){
		//System.out.println("Total Blocks:" + blocks.size());
		
		for(Block block : blocks){
			if(block.position.getX()+32>=cam.getX() && block.position.getX()<=cam.getX()+cam.getWidth()
				&& block.position.getY()+32>=cam.getY() && block.position.getY()-32<cam.getY()+cam.getHeight())
			g.drawImage(block.sprite, (int)(block.position.getX()-cam.getX()),(int)(block.position.getY()-cam.getY()),null);
			
		}
		
	
		/* broken
	
         for(int x=cam.getX()-32;x<=cam.getWidth();x+=32){
        	 for(int y=cam.getY()-32;y<=cam.getHeight();y+=32){
        		 int bx=(x/32)+offsetX;
        		 int by=(y/32)+offsetY;
        		 
        		 if(bx>=0 && bx<blks.length){
        			 if(by>=0 && by<blks[bx].length){
        		
        		 
        		 if(blks[bx][by]!=null){
        			g.drawImage(blks[bx][by].sprite,blks[bx][by].position.getX()-cam.getX(),blks[bx][by].position.getY()-cam.getY(),null);
        		 }
        		 
        	 }
        	 }
         }}
         */
	}

	
	///sorting for path finding - pathfinding is not implemented so this is worthless
	public void sort(){
		offsetX=Math.abs(minX)/32;
		offsetY=Math.abs(minY)/32;
		int width=(Math.abs(minX)+Math.abs(maxX))/32 + offsetX;
		int height=(Math.abs(minY)+Math.abs(maxY))/32 + offsetY;
		System.out.println("MinX: " + minX + "   MaxX:" + maxX);
		System.out.println("MinY: " + minY + "   MaxY:" + maxY);
		System.out.println("offsetX: " + offsetX + "   offsetY:" + offsetY);
		System.out.println("Width: " + width + "   Height:" + height);
		
		
		blks= new Block[width][height];
		
		for(Block block:blocks){
			blks[(block.position.getX()/32)+offsetX][(block.position.getY()/32)+offsetY]=block;
			
		}
	}
	
//load in the map from text file
	public void loadMap(String map){
	
		blocks= new ArrayList<Block>();
		try{
			BufferedReader br= new BufferedReader(new FileReader("res/maps/"+map+".txt"));
			 String line;
			 while((line=br.readLine())!=null){
				 Block blk= new Block();
				 for(int i=0;i<line.length();i++){
					 if(i+5<line.length()){
						 if(line.substring(i, i+5).equalsIgnoreCase("posx=")){
							  blk.position.setX(StringToInt(getValue(line,i+5)));
							  if(blk.position.getX()<minX){
								  minX=blk.position.getX();
							  }
							  if(blk.position.getX()>maxX){
								  maxX=blk.position.getX();
							  }
						 }
						 if(line.substring(i, i+5).equalsIgnoreCase("posy=")){
							  blk.position.setY(StringToInt(getValue(line,i+5)));
							  if(blk.position.getY()<minY){
								  minY=blk.position.getY();
							  }
							  if(blk.position.getY()>maxY){
								  maxY=blk.position.getY();
							  }
						 }
						 
						 if(line.substring(i, i+5).equalsIgnoreCase("imgx=")){
							  blk.SpritePosition.setX(StringToInt(getValue(line,i+5)));
						 } 
						 if(line.substring(i, i+5).equalsIgnoreCase("imgy=")){
							  blk.SpritePosition.setY(StringToInt(getValue(line,i+5)));
						 }
						 
						}
					 
					 
					 
					 if(i+8<line.length()){
						 if(line.substring(i, i+8).equalsIgnoreCase("blocked=")){
							String blocked=getValue(line,i+8);
							   if(blocked.equalsIgnoreCase("true")){
								   blk.isBlocked=true;
							   }else{
								   blk.isBlocked=false;
							   }
						 }
					 }
					 
					
					 
				 }
				 blk.sprite = spriteSheet.getSprite(blk.SpritePosition.getX(),blk.SpritePosition.getY());
				 
				 blocks.add(blk);
			 }
				System.out.println("Total Blocks:" + blocks.size());
		}catch(Exception e){
			System.out.println("Faild to load map");
		}
		//System.out.println("MinX: " + minX + "   MaxX:" + maxX);
		//System.out.println("MinY: " + minY + "   MaxY:" + maxY);
	}
	
	
	//convert seting to int
	private int StringToInt(String string){
		int value=0;
		try{
			value=Integer.parseInt(string);
		}catch(Exception e){
			
		}
		return value;
	}
	//get the value of a blocks field
	private String getValue(String line,int pos){
		String value="";
		for(int i=pos;i<line.length() && !(line.substring(i, i+1).equalsIgnoreCase(";"));i++){
			value+=line.substring(i, i+1);
		}
		
		return value;
	}
	//block definition
	public class Block{
		public Position position,SpritePosition;
		public BufferedImage sprite;
		public boolean isBlocked=false;
		public Block(){
			position=new Position();
			SpritePosition = new Position();
			
		}
		
		
	}
	//for pathfinding -> not used so everything below here is worthless
	private class Node{
		private Position position;
		private int  depth;
		private int cost;
		private boolean isBlocked=false;
		private Node parent;
		public Node(int x, int y){
			position=new Position(x,y);
				depth=0;
				cost=0;
			
		}
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			
			return depth;
		}
		
		
	}
}
