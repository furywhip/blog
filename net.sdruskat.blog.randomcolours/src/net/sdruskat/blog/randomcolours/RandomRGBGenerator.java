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
package net.sdruskat.blog.randomcolours;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.graphics.RGB;

/**
 * 
 * This class can be used to create (pseudo-)randomly generated {@link RGB} objects.
 * 
 * Note that this class is stateful, i.e., every call to {@link #calculateGoldenRatioHue(float)} 
 * (publicly accessible only via {@link #goldenRatioHsvToRgb(float, float, float)})
 * changes {@link #goldenRatioFloatForH}. Therefore, if a returned {@link RGB} object n is not
 * used, n+1 is not golden ration-spaced from n-1.
 * 
 * The implementation of the golden ratio-spaced hue value calculation is based on the
 * blog post <a href="http://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/">&quot;How to Generate Random Colors Programmatically&quot;</a> by <i>Martin Ankerl</i>
 * (http://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/), last accessed on 01 April 2014 (no joke!).
 *  
 * @author Stephan Druskat
 * 
 */
public class RandomRGBGenerator {
	
	/**
	 * Default float for the hue value, tested against in {@link net.sdruskat.blog.randomcolours.RandomRGBGenerator#calculateGoldenRatioHue(float)}.
	 */
	private static float goldenRatioFloatForH = -1;
	
	/**
	 * Calculates a golden ratio-spaced float value for hue to be used in an HSV to RGB calculation.
	 * 
	 * @param h The hue float that is tested against the default float and used for hue randomization. This should be a {@link Random#nextFloat()}. 
	 * @return A float that is golden ratio-spaced to the last calculated float.
	 */
	protected float calculateGoldenRatioHue(float h) {
		double goldenRatioConjugate = 0.618033988749895;
		if (goldenRatioFloatForH  == -1) {
			goldenRatioFloatForH = h;
		}
		else {
			goldenRatioFloatForH += goldenRatioConjugate;
			float moduloGoldenRatioFloat = (goldenRatioFloatForH % 1);
			goldenRatioFloatForH = moduloGoldenRatioFloat;
		}
		return goldenRatioFloatForH;
	}
	
	/**
	 * Creates an {@link RGB} object from floats for hue, saturation and perceived luminance. The hue value is golden ratio-spaced from the last calculated hue value in the life cycle of the {@link RandomRGBGenerator} instance. It calls {@link #calculateGoldenRatioHue(float)} for the hue calculation.
	 * 
	 * @param h A float representing hue
	 * @param s A float representing saturation
	 * @param v A float representing perceived luminance
	 * @return An {@link RGB} object, golden ratio-space randomized on hue.
	 */
	public RGB goldenRatioHsvToRgb(float h, float s, float v) {
		float goldenRatioH = calculateGoldenRatioHue(h);
		return hsvToRgb(goldenRatioH, s, v);
	}

	/**
	 * Creates an {@link RGB} object, simply randomized by passing Random().nextInt(256) to its constructor thrice.
	 * @return An {@link RGB} object based on simply (pseudo-) randomized ints.
	 */
	public RGB simpleRandomRgb() {
		return new RGB(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
	}

	/**
	 * Converts an <a href="http://en.wikipedia.org/wiki/HSL_and_HSV">HSV</a> value set to the visually closest {@link RGB} value set.
	 * 
	 * @param h A float representing hue
	 * @param s A float representing saturation
	 * @param v A float representing perceived luminance
	 * @return An {@link RGB} object which is visually as close as possible to the passed-in HSV values.
	 */
	public RGB hsvToRgb(float h, float s, float v) {
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
		// Uses simple casting, could use rounding (Math.round()) as well before casting.
		return new RGB((int)(r * 256), (int)(g * 256), (int)(b * 256));
	}
	
	/**
	 * Creates a list of n golden ratio-spaced random {@link RGB} objects.
	 * 
	 * @param numberOfRgbObjects
	 * @param saturation
	 * @param value
	 * @return randomGoldenRatioRgbList An ArrayList<RGB> containing numberOfRgbObjects {@link RGB} objects
	 */
	public List<RGB> createRandomGoldenRationRgbList(int numberOfRgbObjects, float saturation, float value) {
		List<RGB> randomGoldenRatioRgbList = new ArrayList<RGB>();
		for (int i = 0; i < numberOfRgbObjects; i++) {
			randomGoldenRatioRgbList.add(goldenRatioHsvToRgb(new Random().nextFloat(), saturation, value));
		}
		return randomGoldenRatioRgbList;
	}

}
