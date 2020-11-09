/*
 * Copyright (c) 2003 Nokia Corporation and/or its subsidiary(-ies).
 * All rights reserved.
 * This component and the accompanying materials are made available
 * under the terms of "Eclipse Public License v1.0"
 * which accompanies this distribution, and is available
 * at the URL "http://www.eclipse.org/legal/epl-v10.html".
 *
 * Initial Contributors:
 * Nokia Corporation - initial contribution.
 *
 * Contributors:
 *
 * Description:
 *
 */

package javax.microedition.m3g;

public class Background extends Object3D {

	public static final int BORDER = 32;
	public static final int REPEAT = 33;

	public Background() {
		super(0);
	}

	
	public void setColor(int ARGB) {

	}

	public int getColor() {
		return 0;
	}

	public void setImage(Image2D image) {

	}

	public Image2D getImage() {
		return null;
	}

	public void setImageMode(int modeX, int modeY) {

	}

	public int getImageModeX() {
		return 0;
	}

	public int getImageModeY() {
		return 0;
	}

	public void setColorClearEnable(boolean enable) {

	}

	public void setDepthClearEnable(boolean enable) {

	}

	public boolean isColorClearEnabled() {
		return false;
	}

	public boolean isDepthClearEnabled() {
		return false;
	}

	public void setCrop(int cropX, int cropY, int width, int height) {

	}

	public int getCropX() {
		return 0;
	}

	public int getCropY() {
		return 0;
	}

	public int getCropWidth() {
		return 0;
	}

	public int getCropHeight() {
		return 0;
	}

}
