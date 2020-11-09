/*
 * Copyright 2012 Kulikov Dmitriy
 * Copyright 2015-2016 Nickolay Savchenko
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

public abstract class Displayable {

	public Displayable() {

	}

	public static void setVirtualSize(int virtualWidth, int virtualHeight) {

	}

	public static int getVirtualWidth() {
		return 0;
	}

	public static int getVirtualHeight() {
		return 0;
	}

	public void setTitle(String title) {
		
	}

	public String getTitle() {
		return null;
	}

	public boolean isShown() {
		return false;
	}

	public void clearDisplayableView() {

	}

	public int countCommands() {
		return 0;
	}

	public int getWidth() {
		return 0;
	}

	public int getHeight() {
		return 0;
	}

	public void setTicker(Ticker newticker) {

	}

	public Ticker getTicker() {
		return null;
	}

	public void sizeChanged(int w, int h) {
	}

	public boolean menuItemSelected(int id) {
		return true;
	}
}
