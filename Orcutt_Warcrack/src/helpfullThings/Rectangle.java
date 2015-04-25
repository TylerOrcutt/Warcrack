
/*****************************************************************************
 * Class:Rectangle
 * Author: Tyler Orcutt
 * Purpose: Drawing rectangles
 ****************************************************************************/
package helpfullThings;

public class Rectangle {
private int x1,x2,y1,y2;

//constructors
	public Rectangle(){
		this(0,0,0,0);
	}
	
	public Rectangle(int x1,int y1,int x2,int y2){
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
	}

//get Min x value	
	public int getX(){
		return Math.min(x1, x2);
	}
	//get min Y value
	public int getY(){
		return Math.min(y1, y2);
	}
	
	//get MaxY
	public int getMaxY(){
		return Math.max(y1, y2);
	}
	//get Max X
	public int getMaxX(){
		return Math.max(x1, x2);
	}
	
	//get the height of the rectangle
	public int getHeight(){
		return Math.abs(y2-y1);
	}
	//get the width of the rectangle
	public int getWidth(){
		return Math.abs(x2-x1);
	}
	//getters and setters
	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	
	
}
