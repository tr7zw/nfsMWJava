/*
 * Copyright 2012 Kulikov Dmitriy
 * Copyright 2017-2018 Nikita Shakarun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.microedition.lcdui;

import java.io.IOException;
import java.io.InputStream;

public class Image {

	public static Image createTransparentImage(int width, int height) {
		return null;
	}

	public Canvas getCanvas() {
		return null;
	}

	public static Image createImage(int width, int height) {
		return null;
	}

	public static Image createImage(String resname) throws IOException {
		return null;
	}

	public static Image createImage(InputStream stream) throws IOException {
		return null;
	}

	public static Image createImage(byte[] imageData, int imageOffset, int imageLength) {
		return null;
	}

	public static Image createImage(Image image, int x, int y, int width, int height, int transform) {
		return null;
	}

	public static Image createImage(Image image) {
		return null;
	}

	public static Image createRGBImage(int[] rgb, int width, int height, boolean processAlpha) {
		return null;
	}

	public Graphics getGraphics() {
		return null;
	}

	public boolean isMutable() {
		return false;
	}

	public int getWidth() {
		return 0;
	}

	public int getHeight() {
		return 0;
	}

	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {

	}

	void copyTo(Image dst) {

	}

	void copyTo(Image dst, int x, int y) {

	}

	public Graphics getSingleGraphics() {
		return null;
	}

	void setSize(int width, int height) {

	}

	public boolean isBlackWhiteAlpha() {
		return false;
	}

	public void setBlackWhiteAlpha(boolean blackWhiteAlpha) {

	}
}
