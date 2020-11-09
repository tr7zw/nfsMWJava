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

public class Graphics {
	public static final int HCENTER = 1;
	public static final int VCENTER = 2;
	public static final int LEFT = 4;
	public static final int RIGHT = 8;
	public static final int TOP = 16;
	public static final int BOTTOM = 32;
	public static final int BASELINE = 64;

	public static final int SOLID = 0;
	public static final int DOTTED = 1;


	public void reset() {

	}

	public Canvas getCanvas() {
		return null;
	}

	public void fillPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints) {

	}

	public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints) {

	}

	public void setColor(int color) {
	}

	public void setColorAlpha(int color) {

	}

	public void setColor(int r, int g, int b) {

	}

	public void setGrayScale(int value) {

	}

	public int getGrayScale() {
		return 0;
	}

	public int getRedComponent() {
		return 0;
	}

	public int getGreenComponent() {
		return 0;
	}

	public int getBlueComponent() {
		return 0;
	}

	public int getColor() {
		return 0;
	}

	public int getDisplayColor(int color) {
		return 0;
	}

	public void setStrokeStyle(int stroke) {

	}

	public int getStrokeStyle() {
		return 0;
	}

	public void setFont(Font font) {

	}

	public Font getFont() {
		return null;
	}

	public void setClip(int x, int y, int width, int height) {

	}

	public void clipRect(int x, int y, int width, int height) {

	}

	public int getClipX() {
		return 0;
	}

	public int getClipY() {
		return 0;
	}

	public int getClipWidth() {
		return 0;
	}

	public int getClipHeight() {
		return 0;
	}

	public void translate(int dx, int dy) {

	}

	public int getTranslateX() {
		return 0;
	}

	public int getTranslateY() {
		return 0;
	}

	public void drawLine(int x1, int y1, int x2, int y2) {

	}

	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

	}

	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

	}

	public void drawRect(int x, int y, int width, int height) {

	}

	public void fillRect(int x, int y, int width, int height) {

	}

	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

	}

	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

	}

	public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {

	}

	public void drawChar(char character, int x, int y, int anchor) {

	}

	public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) {
	
	}

	public void drawString(String text, int x, int y, int anchor) {

	}

	public void drawImage(Image image, int x, int y, int anchor) {

	}

	public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {

	}

	public void drawRegion(Image image, int srcx, int srcy, int width, int height,
						   int transform, int dstx, int dsty, int anchor) {

	}

	public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) {

	}

	public void copyArea(int x_src, int y_src, int width, int height,
						 int x_dest, int y_dest, int anchor) {

	}

	public void getPixels(int[] pixels, int offset, int stride,
						  int x, int y, int width, int height) {

	}

	void flush(Image image, int x, int y, int width, int height) {

	}

}
