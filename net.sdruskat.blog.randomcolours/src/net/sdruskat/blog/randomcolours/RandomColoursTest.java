package net.sdruskat.blog.randomcolours;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */

/**
 * @author Stephan Druskat
 *
 */
public class RandomColoursTest {
	
	private static final String SIMPLE_RANDOM = "Simple random float";
	private static final String RANDOM_HSV = "Random Hue float (HSV)";
	private static final String GOLDEN_RATIO_HSV = "Golden ratio-spaced Hue float (HSV)";
	static float goldenRatioH = -1;
	static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static Shell shell;
	private static Display display;
	private static GridData gridData;
	private static float goldenRatioFloatForH = -1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		display = new Display();
		shell = new Shell(display);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 27;
		layout.makeColumnsEqualWidth = false;
		shell.setLayout(layout);
		
		gridData = new GridData();
		
		produceLabels(gridData, RandomColoursTest.SIMPLE_RANDOM);
		produceLabels(gridData, RandomColoursTest.RANDOM_HSV);
		produceLabels(gridData, RandomColoursTest.GOLDEN_RATIO_HSV);
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void produceLabels(GridData gridData, String randomType) {
		Label mainLabel = new Label(shell, SWT.NONE);
		mainLabel.setLayoutData(gridData);
		mainLabel.setText(randomType);
		float originalRandomFloat = new Random().nextFloat();
		float h = -1, s = 0.99f, v = 0.95f;
		for (int i = 0; i < alphabet.length; i++) {
			Label label = new Label(shell, SWT.CENTER);
			if (randomType == RandomColoursTest.SIMPLE_RANDOM) {
				label.setBackground(new Color(display, simpleRandomRgb()));
			}
			else if (randomType == RandomColoursTest.RANDOM_HSV) {
				h = new Random().nextFloat();
				label.setBackground(new Color(display, hsvToRgb(h, s, v)));
			}
			else if (randomType == RandomColoursTest.GOLDEN_RATIO_HSV) {
				h = calculateGoldenRatioHue(originalRandomFloat);
				label.setBackground(new Color(display, hsvToRgb(h, s, v)));
			}
			gridData.heightHint = 30;
			gridData.widthHint = 30;
			label.setLayoutData(gridData);
			label.setText(String.valueOf(alphabet[i]));
		}
		
	}

	private static float calculateGoldenRatioHue(float originalRandomFloat) {
		double goldenRatioConjugate = 0.618033988749895;
		if (goldenRatioFloatForH == -1) {
			goldenRatioFloatForH = originalRandomFloat;
		}
		else {
			goldenRatioFloatForH += goldenRatioConjugate;
			float moduloGoldenRatioFloat = (goldenRatioFloatForH % 1);
			goldenRatioFloatForH = moduloGoldenRatioFloat;
		}
		return goldenRatioFloatForH;
	}

	private static RGB simpleRandomRgb() {
		return new RGB(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
	}

	// Using float just like in the original example
	private static RGB hsvToRgb(float h, float s, float v) {
		int h_i = (int)(h * 6);
		float f = (h * 6) - h_i;
		float p = v * (1 - s);
		float q = v * (1 - f * s);
		float t = v * (1 - (1 - f) * s);
		float r = 0,g = 0,b = 0;
		switch (h_i) {
		case 0:
			r = v;
			g = t;
			b = p;
			break;
		case 1:
			r = q;
			g = v;
			b = p;
			break;
		case 2:
			r = p;
			g = v;
			b = t;
			break;
		case 3:
			r = p;
			g = q;
			b = v;
			break;
		case 4:
			r = t;
			g = p;
			b = v;
			break;
		case 5:
			r = v;
			g = p;
			b = q;
			break;

		default:
			break;
		}
		// TODO: Simple casting versus rounding
		return new RGB((int)(r * 256), (int)(g * 256), (int)(b * 256));
	}

}
