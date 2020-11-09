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

public abstract class Canvas extends Displayable {

	public static final int KEY_POUND = 35;
	public static final int KEY_STAR = 42;
	public static final int KEY_NUM0 = 48;
	public static final int KEY_NUM1 = 49;
	public static final int KEY_NUM2 = 50;
	public static final int KEY_NUM3 = 51;
	public static final int KEY_NUM4 = 52;
	public static final int KEY_NUM5 = 53;
	public static final int KEY_NUM6 = 54;
	public static final int KEY_NUM7 = 55;
	public static final int KEY_NUM8 = 56;
	public static final int KEY_NUM9 = 57;

	public static final int KEY_UP = -1;
	public static final int KEY_DOWN = -2;
	public static final int KEY_LEFT = -3;
	public static final int KEY_RIGHT = -4;
	public static final int KEY_FIRE = -5;
	public static final int KEY_SOFT_LEFT = -6;
	public static final int KEY_SOFT_RIGHT = -7;
	public static final int KEY_CLEAR = -8;
	public static final int KEY_SEND = -10;
	public static final int KEY_END = -11;

	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 5;
	public static final int DOWN = 6;
	public static final int FIRE = 8;
	public static final int GAME_A = 9;
	public static final int GAME_B = 10;
	public static final int GAME_C = 11;
	public static final int GAME_D = 12;


	private static void remapKeys() {

	}

	private static void mapKeyCode(int midpKeyCode, int gameAction, String keyName) {

	}

	private static void mapGameAction(int gameAction, int keyCode) {

	}

	private static int convertAndroidKeyCode(int keyCode) {
		return 0;
	}

	public static int convertKeyCode(int keyCode) {
		return 0;
	}

	public static void setScale(boolean scaleToFit, boolean keepAspectRatio, int scaleRatio) {

	}

	public static void setBackgroundColor(int color) {

	}

	public static void setFilterBitmap(boolean filter) {

	}


	public static void setHasTouchInput(boolean touchInput) {

	}

	public static void setGraphicsMode(int mode, boolean parallel) {

	}

	public static void setForceFullscreen(boolean forceFullscreen) {

	}

	public static void setShowFps(boolean showFps) {

	}

	public static void setLimitFps(int fpsLimit) {

	}

	public int getKeyCode(int gameAction) {
		return 0;
	}

	public int getGameAction(int keyCode) {
		return 0;
	}

	public String getKeyName(int keyCode) {
		return null;
	}

	public void postKeyPressed(int keyCode) {
	}

	public void postKeyReleased(int keyCode) {

	}

	public void postKeyRepeated(int keyCode) {

	}

	public void callShowNotify() {

	}

	public void callHideNotify() {

	}


	@Override
	public void clearDisplayableView() {

	}

	public void setFullScreenMode(boolean flag) {

	}

	public boolean hasPointerEvents() {
		return false;
	}

	public boolean hasPointerMotionEvents() {
		return false;
	}

	public boolean hasRepeatEvents() {
		return true;
	}

	public boolean isDoubleBuffered() {
		return true;
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	public final void repaint() {

	}

	public final void repaint(int x, int y, int width, int height) {

	}

	@Override
	public boolean isShown() {
		return false;
	}

	// GameCanvas
	public void flushBuffer(Image image, int x, int y, int width, int height) {

	}

	// ExtendedImage
	public void flushBuffer(Image image, int x, int y) {

	}

	private void limitFps() {

	}

	public void setVisible(boolean visible) {

	}

	private boolean repaintScreen() {
		return false;
	}

	/**
	 * After calling this method, an immediate redraw is guaranteed to occur,
	 * and the calling thread is blocked until it is completed.
	 */
	public final void serviceRepaints() {

	}

	protected void showNotify() {
	}

	protected void hideNotify() {
	}

	public void keyPressed(int keyCode) {
	}

	public void keyRepeated(int keyCode) {
	}

	public void keyReleased(int keyCode) {
	}

	public void pointerPressed(int pointer, float x, float y) {

	}

	public void pointerDragged(int pointer, float x, float y) {

	}

	public void pointerReleased(int pointer, float x, float y) {

	}

	public void pointerPressed(int x, int y) {
	}

	public void pointerDragged(int x, int y) {
	}

	public void pointerReleased(int x, int y) {
	}

}
