package ambilight;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;


public class ColorPicker {
	
	private Robot robot;
	private Color[] ledColors;
	
	//Configuration
	public static final int SCREEN_WIDTH = 1680;
	public static final int SCREEN_HEIGHT = 1060;
	public static final int OFFSET_WIDTH = 100;
	public static final int OFFSET_HEIGHT = 100;
	public static final int ROWS = 2;
	public static final int COLS = 16;
	public static final int PIXEL_GAP = 10;
	
	public ColorPicker(Color[] ledColors)
	{
		initRobot();
		ledColors = new Color[ROWS * COLS];
		this.ledColors = ledColors;
	}
	
	public Color[] getColors()
	{
		fillTable();
		return ledColors;
	}
	
	
	private void initRobot()
	{
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	private BufferedImage getScreen()
	{
		return robot.createScreenCapture(new Rectangle(OFFSET_WIDTH , OFFSET_HEIGHT , SCREEN_WIDTH - 2 * OFFSET_WIDTH ,  SCREEN_HEIGHT - 2 * OFFSET_HEIGHT));
	}
	
	private void fillTable()  
	{		
		BufferedImage screen = getScreen();

		for( int i = 0 ; i < ROWS ; i++)
			{
				for( int j = 0 ; j < COLS ; j++)
					{
						ledColors[i*COLS + j] = getAvgColor(screen , i , j);
					}
			}
	}
	
	private Color getAvgColor(BufferedImage screen , int row , int col)
	{
		int blockHeight = screen.getHeight() / ROWS ;
		int blockWidth = screen.getWidth() / COLS ;
		
		int red = 0;
		int green = 0;
		int blue = 0;
		
		int pixelCounter = 0;
		for (int x = blockWidth * col; x < blockWidth * col + blockWidth; x += PIXEL_GAP) 
			{
				for (int y = blockHeight * row; y < blockHeight * row + blockHeight; y += PIXEL_GAP) 
				{
					int rgb = screen.getRGB(x, y);
					Color color = new Color(rgb);
					red += color.getRed();
					green += color.getGreen();
					blue += color.getBlue();
					pixelCounter++;
				}
			}
		red /= pixelCounter;
		green /= pixelCounter;
		blue /= pixelCounter;
		
		return new Color(red,green,blue);
	}
		

}
