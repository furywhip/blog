package net.sdruskat.randomcolours.example;

import java.util.Random;

import net.sdruskat.randomcolours.RandomRGBGenerator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Demonstrates the usage of {@link RandomRGBGenerator} by creating
 * a number of {@link Label}s with randomly generated {@link RGB}-based {@link Color}s.
 * 
 * @author Stephan Druskat
 *
 */
public class RandomColoursTest {
	
	private static final String SIMPLE_RANDOM = "Random float";
	private static final String RANDOM_HSV = "HSV Hue float (random)";
	private static final String GOLDEN_RATIO_HSV = "Golden ratio-spaced HSV Hue float";
	static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static Shell shell;
	private static Display display;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		display = new Display();
		shell = new Shell(display);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 27;
		shell.setLayout(layout);
		
		produceLabels(RandomColoursTest.SIMPLE_RANDOM);
		produceLabels(RandomColoursTest.RANDOM_HSV);
		produceLabels(RandomColoursTest.GOLDEN_RATIO_HSV);
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void produceLabels(String randomType) {
		GridData gridData = new GridData();
		Label mainLabel = new Label(shell, SWT.NONE);
		mainLabel.setLayoutData(gridData);
		mainLabel.setText(randomType.substring(0, 3));
		RandomRGBGenerator gen = new RandomRGBGenerator();
		for (int i = 0; i < alphabet.length; i++) {
			Label label = new Label(shell, SWT.CENTER);
			float h = new Random().nextFloat();
			if (randomType == RandomColoursTest.SIMPLE_RANDOM) {
				label.setBackground(new Color(display, gen.simpleRandomRgb()));
			}
			else if (randomType == RandomColoursTest.RANDOM_HSV) {
				label.setBackground(new Color(display, gen.hsvToRgb(h, 0.99f, 0.95f)));
			}
			else if (randomType == RandomColoursTest.GOLDEN_RATIO_HSV) {
				label.setBackground(new Color(display, gen.goldenRatioHsvToRgb(h, 0.99f, 0.99f)));
			}
			gridData.heightHint = 30;
			gridData.widthHint = 30;
			label.setLayoutData(gridData);
			label.setText(String.valueOf(alphabet[i]));
		}
		
	}

}
