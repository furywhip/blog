/**
 * Copyright 2014 Stephan Druskat
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sdruskat.blog.randomcolours.example;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.sdruskat.blog.randomcolours.RandomRGBGenerator;

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
		shell.setText("\"Ran\" = Simple random, \"HSV\" = HSV Random Hue, \"Gol\" = golden ratio-spaced random hue");
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 27;
		shell.setLayout(layout);
		
		GridData gridData = new GridData();
		
		produceLabels(RandomColoursTest.SIMPLE_RANDOM, gridData);
		produceLabels(RandomColoursTest.RANDOM_HSV, gridData);
		produceLabels(RandomColoursTest.GOLDEN_RATIO_HSV, gridData);
		produceLabelsFromList(gridData);
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void produceLabelsFromList(GridData gridData) {
		RandomRGBGenerator gen = new RandomRGBGenerator();
		List<RGB> list = gen.createRandomGoldenRationRgbList(44, 0.5f, 0.95f);
		Label mainLabel = new Label(shell, SWT.NONE);
		mainLabel.setLayoutData(gridData);
		mainLabel.setText(list.size() + "x");
		for (Iterator<RGB> iterator = list.iterator(); iterator.hasNext();) {
			RGB rgb = (RGB) iterator.next();
			Label label = new Label(shell, SWT.CENTER);
			label.setBackground(new Color(display, rgb));
			gridData.heightHint = 30;
			gridData.widthHint = 30;
			label.setLayoutData(gridData);
			label.setText(String.valueOf(list.indexOf(rgb) + 1));
		}

		
	}

	private static void produceLabels(String randomType, GridData gridData) {
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
