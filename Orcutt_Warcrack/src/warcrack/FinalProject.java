/*****************************************************************************
 * Class: Final Project
 * Author: Tyler Orcutt
 * Purpose: entry point / keyboard listener
 ****************************************************************************/

package warcrack;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import warcrack.engine.Engine;

public class FinalProject extends JFrame {
	private int WIDTH =800;
	private int HEIGHT=600;

		private Display display;
		private Engine engine;
		Thread displayThread;
	public FinalProject(){
		
		engine=new Engine(this);
		 	display=new Display(this);
		 	
		 
		 	 startGame();
		 	this.add(display);
		 	this.setBounds(100,100,WIDTH,HEIGHT);
		  this.setVisible(true);
		  this.addKeyListener(new kbListener());

	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	  
	}
	public void startGame(){
	
		  displayThread= new Thread(engine);
		  displayThread.start();
	}
	
	//getters
	public int getHeight(){
		return HEIGHT;
	}
	public int getWidth(){
		return WIDTH;
	}
	public Display getDisplay(){
		return display;
	}	
	public Engine getEngine(){
		return engine;
	}
//keyboard listener
    private class kbListener implements KeyListener{

    	
		public void keyPressed(KeyEvent e) {
			//w=87
			//a=65
			//s=83
			//d=68
			if(e.getKeyCode()==10){
				if(engine.Typing()){
					engine.setTyping(false);
					engine.guiClearText();
				}else{
					engine.setTyping(true);
				}
			}
			if(!engine.Typing()){
			if(e.getKeyCode()==87){
				engine.setCameraMoving("North");
			}
			if(e.getKeyCode()==65){
				engine.setCameraMoving("West");
			}
			if(e.getKeyCode()==83){
				engine.setCameraMoving("South");
			}
			if(e.getKeyCode()==68){
				engine.setCameraMoving("East");
			}
			// TODO Auto-generated method stub
			}else{
				engine.updateGuiTypeString(e.getKeyChar());
			}
//		/System.out.println(e.getKeyCode());
		}
		
		
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("Key:"+e);
			if(e.getKeyCode()==87){
				engine.setCameraMoving("");
			}
			if(e.getKeyCode()==65){
				engine.setCameraMoving("");
			}
			if(e.getKeyCode()==83){
				engine.setCameraMoving("");
			}
			if(e.getKeyCode()==68){
				engine.setCameraMoving("");
			}
		}

		
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			FinalProject fp= new FinalProject();
	}

}
