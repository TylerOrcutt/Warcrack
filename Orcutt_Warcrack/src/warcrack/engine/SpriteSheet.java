/*****************************************************************************
 * Class:SpriteSheet
 * Author: Tyler Orcutt
 * Purpose:Manages SpriteSheets
 ****************************************************************************/
package warcrack.engine;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private BufferedImage image;
	private int size;
	public SpriteSheet (String file, int size){
		try {
			image=ImageIO.read(new File("res/"+file+".png"));
			this.size=size;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				 System.out.println("faild to load image");
				 
			
		}
		
	}
	//get a image and location
	public BufferedImage getSprite(int x, int y){
		return image.getSubimage(x,y,size,size);
		
	}
	

	
}
