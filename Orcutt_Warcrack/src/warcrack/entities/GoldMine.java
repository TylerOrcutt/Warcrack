/*****************************************************************************
 * Class:GoldMine
 * Author: Tyler Orcutt
 * Purpose: GoldMine GameObject
 ****************************************************************************/
package warcrack.entities;

import java.util.ArrayList;

import helpfullThings.Position;
import warcrack.engine.Engine;
import warcrack.engine.SpriteSheet;

public class GoldMine extends Building {
		public GoldMine(SpriteSheet spritesheet, Position position, Engine engine) {
		super(spritesheet, position, new Position(0,0), engine);
		// TODO Auto-generated constructor stub
		super.setisIndestructible(true);
	}


	
	//yea not a whole lot here
	//get what i am
	public String isKindOf(){
		return "GoldMine";
	}
	
}
