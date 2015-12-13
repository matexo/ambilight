package ambilight;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class Sender {

	public static final int DATA_RATE = 9600;
	public static final int TIMEOUT = 2000;
	
	private SerialPort serial;
	private OutputStream output;
	
	private Color[] ledColor;
	public void setLedColor(Color[] ledColor) { this.ledColor = ledColor; }
	
	public Sender(int size , Color[] ledsColor)
	{
		initSerial();
		initOutputStream();
		this.ledColor = ledsColor;
	}
	
	public void sendColors() {
		try {
			output.write(0xff);
			for (int i = 0; i < ledColor.length; i++) {
				output.write(ledColor[i].getBlue());
				output.write(ledColor[i].getGreen());
				output.write(ledColor[i].getRed());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initSerial() {
		// znajdujemy port, do którego podpiête jest Arduino
		CommPortIdentifier serialPortId = null;
		Enumeration enumComm = CommPortIdentifier.getPortIdentifiers();
		while (enumComm.hasMoreElements() && serialPortId == null) {
			serialPortId = (CommPortIdentifier) enumComm.nextElement();
			serialPortId = (CommPortIdentifier) enumComm.nextElement();
			try {
			Thread.sleep(1000);
			}
			catch (InterruptedException e )
			{}
			}
		try {
			serial = (SerialPort) serialPortId.open(this.getClass().getName(),
					TIMEOUT);
			serial.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (PortInUseException | UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
	}
	
	private void initOutputStream() {
		try {
			output = serial.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void clean() {
		if(output != null)
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if(serial != null)
			serial.close();
	}

	
	
	
}
