/*****************************************************************************
 * Class: TownHall
 * Author: Tyler Orcutt
 * Purpose:TownHall object
 ****************************************************************************/
package warcrack.entities;

import helpfullThings.Position;
import warcrack.engine.Engine;
import warcrack.engine.SpriteSheet;

public class TownHall extends Building{

	public TownHall(SpriteSheet spritesheet, Position position, Engine engine) {
		super(spritesheet, position,new Position(96,0), engine);
		// TODO Auto-generated constructor stub
		//functions that this building can perform 
		super.addBuildFunc("Peon");
		super.addBuildFunc("Grunt");
	
	}

	
	public String isKindOf(){
		return "TownHall";
	}
	
}
