package ambilight;

import java.awt.Color;

public class Controller implements Runnable{
	
	private ColorPicker colorPicker;
	private Sender sender;
	
	public static final int LED_NUMBER = 32;
	
	public Controller(ColorPicker colorPicker , Sender sender)
	{
		this.colorPicker = colorPicker;
		this.sender = sender;
	}
	
	
	@Override
	public void run() {
		while(true){
		sender.setLedColor(colorPicker.getColors());
		sender.sendColors();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Color[] colorsTable = new Color[32];
		ColorPicker cp = new ColorPicker(colorsTable);
		
		Sender se = new Sender(LED_NUMBER, colorsTable);
		
		Controller controller = new Controller(cp, se);
		Thread t = new Thread(controller);
		t.start();
		
	}


}
