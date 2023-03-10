


/*
 * File: GButton.java
 * ------------------
 * Provides a simple way to create a button with text.
 * The button will automatically set the size of the text
 * Based on the width and height of the button.
 * Users can also set the Color of the button's background
 * as well as the color of the text.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRoundRect;

public class GButton extends GCompound {
	private GRoundRect rect;
	private GLabel message;
	private Font font1;
	private Font font2;

	public static final int BUFFER = 5;

	public GButton(String label, double x, double y, double width, double height) {
		this(label, x, y, width, height, Color.white);
	}

	public GButton(String label, double x, double y, double width, double height, int r, int g, int b) {
		this(label, x, y, width, height, new Color(r, g, b));
	}

	public GButton(String label, double x, double y, double width, double height, Color col) {
		super();
		setLocation(x, y);
		rect = new GRoundRect(0, 0, width, height);
		rect.setFilled(true);
		rect.setFillColor(col);
		add(rect);
		message = new GLabel(label);
		sizeLabelFont(message, width - BUFFER, height - BUFFER);
		double centerX = width / 2 - message.getWidth() / 2;
		double centerY = height / 2 + message.getAscent() / 2;
		add(message, centerX, centerY);
	}

	private void sizeLabelFont(GLabel label, double width, double height) {
		int size, style;
		String name;
		Font f = label.getFont();
		name = f.getFontName();
		style = f.getStyle();
		size = f.getSize();
		while (label.getWidth() < width && label.getHeight() < height) {
			f = label.getFont();
			size = f.getSize();
			label.setFont(new Font(name, style, size + 1));
		}
		label.setFont(new Font(name, style, size - 1));
	}

	public void setFillColor(Color col) {
		rect.setFillColor(col);
	}

	public void setColor(Color col) {
		message.setColor(col);
	}
	
	public void setFont() {
		try {
		     font1 = Font.createFont(Font.TRUETYPE_FONT, new File("FONTS/square_block.ttf")).deriveFont(20f);
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(font1);
		} catch (IOException|FontFormatException e) {
		     e.printStackTrace();
		}
		message.setFont(font1);
	}
}
