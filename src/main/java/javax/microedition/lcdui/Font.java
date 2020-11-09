/*
 * Copyright 2012 Kulikov Dmitriy
 * Copyright 2017 Nikita Shakarun
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

public class Font {
	public static final int FACE_MONOSPACE = 32;
	public static final int FACE_PROPORTIONAL = 64;
	public static final int FACE_SYSTEM = 0;

	public static final int SIZE_LARGE = 16;
	public static final int SIZE_MEDIUM = 0;
	public static final int SIZE_SMALL = 8;

	public static final int STYLE_BOLD = 1;
	public static final int STYLE_ITALIC = 2;
	public static final int STYLE_PLAIN = 0;
	public static final int STYLE_UNDERLINED = 4;


	public static void setApplyDimensions(boolean flag) {

	}

	public static void setSize(int size, float value) {

	}

	public static Font getFont(int fontSpecifier) {
		return null;
	}

	public static Font getFont(int face, int style, int size) {
		return null;
	}

	public static Font getDefaultFont() {
		return null;
	}

	public boolean isSmall() {
		return false;
	}


	public int getFace() {
		return 0;
	}

	public int getStyle() {
		return 0;
	}

	public int getSize() {
		return 0;
	}

	public boolean isUnderlined() {
		return false;
	}

	public int getHeight() {
		return 0;
	}

	public int getBaselinePosition() {
		return 0;
	}

	public int charWidth(char c) {
		return 0;
	}

	public int charsWidth(char[] ch, int offset, int length) {
		return 0;
	}

	public int stringWidth(String text) {
		return 0;
	}

	public int substringWidth(String str, int i, int i2) {
		return 0;
	}

	private static int getFontIndex(int face, int style, int size) {
		return 0;
	}

	public boolean isBold() {
		return false;
	}

	public boolean isPlain() {
		return false;
	}

	public boolean isItalic() {
		return false;
	}
}
