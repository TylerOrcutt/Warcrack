/*****************************************************************************
 * Class: Display
 * Author: Tyler Orcutt
 * Purpose: Handles mouse input/base repait
 ****************************************************************************/


package warcrack;

import helpfullThings.Point;
import helpfullThings.Position;
import helpfullThings.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import warcrack.engine.Camera;
import warcrack.engine.Engine;

public class Display extends JPanel{
	private FinalProject fp;
	private boolean isRunning;
	private Rectangle rect;
	private Position cursorPosition;
	private boolean MainMenu;
    public Display(FinalProject fp){
    	//this.engine=engine;
    	this.fp=fp;
    	this.setBackground(Color.BLACK);
    //	this.addKeyListener(new kbListener());
    	//this.add
    
    	MainMenu=false;
    	isRunning=true;
    	cursorPosition= new Position();
    	MouseListener ml = new MouseListener();
    	this.addMouseListener(ml);
    	    	this.addMouseMotionListener(ml);
    	    	
    }
    
    //base draw method
    public void paint(Graphics g){
  //  	
    	super.paintComponent(g);
    	g.setColor(Color.BLACK);
    	g.clearRect(0, 0, fp.getWidth(), fp.getHeight());
    	g.fillRect(0, 0, fp.getWidth(), fp.getHeight());
    	g.setColor(Color.WHITE);
    	if(MainMenu)
    	{
    	//
    		
    		
    		return;
    	}
    	
    	if(fp.getEngine()!=null){
    	fp.getEngine().update();
    	fp.getEngine().renderAll(g);
    	
    	if(rect!=null){
    	   g.setColor(Color.GREEN);
    		g.drawRect(rect.getX()-fp.getEngine().getCamera().getX(),rect.getY()-fp.getEngine().getCamera().getY(),rect.getWidth(),rect.getHeight());
    	  
    	   g.setColor(Color.WHITE);
    	   
    	}
    	}
    	g.drawString("X:"+ cursorPosition.getX() + "   Y:"+cursorPosition.getY(), 100, 200);
    	
    }


	//mouse events
	private class MouseListener extends MouseAdapter implements MouseMotionListener{
		public void mousePressed(MouseEvent e){
			if(fp.getEngine()!=null){
			if(e.getButton()==1){
				if(e.getY()<fp.getEngine().getCamera().getHeight()+26){
				rect=new Rectangle(e.getX()+fp.getEngine().getCamera().getX(),e.getY()+fp.getEngine().getCamera().getY(),e.getX()+fp.getEngine().getCamera().getX(),e.getY()+fp.getEngine().getCamera().getY());
				}else{
					//clicked a GUI button?
					fp.getEngine().getGUI().GUI_Click(new Point(e.getX(),e.getY()));
				}
			}
			if(e.getButton()==3){
				fp.getEngine().moveSelected(new Point(e.getX()+fp.getEngine().getCamera().getX()-16,e.getY()+fp.getEngine().getCamera().getY()-16) );
			}
			}
			}
		public void mouseReleased(MouseEvent e){
			if(fp.getEngine()!=null){
			if(rect!=null){
				fp.getEngine().selectEntites(rect);
			}
			rect=null;
			}
		}
	//mouse being dragged
    	public void mouseDragged(MouseEvent e){
    		if(fp.getEngine()!=null){
	     	if(rect!=null){
	     		if(e.getY()>fp.getEngine().getCamera().getHeight()+26){
	     			//rect.setX2(e.getX()+engine.getCamera().getX());
				//	rect.setY2(e.getY()+engine.getCamera().getY());
	     	}else{
				rect.setX2(e.getX()+fp.getEngine().getCamera().getX());
				rect.setY2(e.getY()+fp.getEngine().getCamera().getY());
	     	}
		   }
    		}
	  }
    	//record the mouse position
    	public void mouseMoved(MouseEvent e){
    		if(fp.getEngine()!=null){
    		cursorPosition.setX(e.getX()+fp.getEngine().getCamera().getX());
    		cursorPosition.setY(e.getY()+fp.getEngine().getCamera().getY());
    		}
    	}
    	
	}
    

}
