package com.catalanrenegado.tinkdatabase.testing;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Displayer {
	// Ambos metodos hacen lo mismo
	public static void display(BufferedImage bi) {
		ImageIcon icon = new ImageIcon(new ImageIcon(bi).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
		JFrame frame = new JFrame();
		JLabel label = new JLabel(icon);
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	public static void display(InputStream png) {
		Image image = null;
		try {
			// Read from a file
			/*
			 * File sourceimage = new File("source.gif"); image = ImageIO.read(sourceimage);
			 */

			// Read from an input stream
			InputStream is = new BufferedInputStream(png);
			image = ImageIO.read(is);

			// Read from a URL
			/*
			 * URL url = new URL("http://java-tips.org/source.gif"); image =
			 * ImageIO.read(url);
			 */
		} catch (IOException e) {

		}

		// Use a label to display the image
		JFrame frame = new JFrame();
		JLabel label = new JLabel(new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
/*
	public static void display(TextureAtlasSprite tas) {
		if (tas.getFrameCount() == 0) {
			return;
		} else {
			BufferedImage bf = new BufferedImage(tas.getIconWidth(), tas.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			int[][] array = tas.getFrameTextureData(0);

			for (int i = 0; i < array.length; i++) {
				int[] array2 = array[i];
				for (int j = 0; j < array2.length; j++) {
					int currentPixel = array2[j];
					bf.setRGB(i, j, currentPixel);
				}
			}

			display(bf);
		}
	}

	public static void displayTest(TextureAtlasSprite tas) {
		if (tas.getFrameCount() == 0) {
			return;
		} else {
			BufferedImage bf = new BufferedImage(tas.getIconWidth(), tas.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			int[][] array = tas.getFrameTextureData(0);
			for (int i = 0; i < array[0].length; i++) {
				int currentCol = i % tas.getIconWidth();
				int currentRow = i / tas.getIconWidth();
				bf.setRGB(currentCol, currentRow, array[0][i]);
			}
			display(bf);
		}
	}

	public static void displayTest(TextureAtlasSprite tas, int arr) {
		if (tas.getFrameCount() == 0) {
			return;
		} else {
			BufferedImage bf = new BufferedImage(tas.getIconWidth(), tas.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			int[][] array = tas.getFrameTextureData(0);
			for (int i = 0; i < array[arr].length; i++) {
				int currentCol = i % tas.getIconWidth();
				int currentRow = i / tas.getIconWidth();
				bf.setRGB(currentCol, currentRow, array[0][i]);
			}
			display(bf);
		}
	}*/
}
