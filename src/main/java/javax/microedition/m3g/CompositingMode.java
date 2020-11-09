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

public class CompositingMode extends Object3D {
	//------------------------------------------------------------------
	// Static data
	//------------------------------------------------------------------

	public static final int ALPHA = 64;
	public static final int ALPHA_ADD = 65;
	public static final int MODULATE = 66;
	public static final int MODULATE_X2 = 67;
	public static final int REPLACE = 68;

	//------------------------------------------------------------------
	// Constructors
	//------------------------------------------------------------------

	public CompositingMode() {
		super(0);
	}

	/**
	 */
	CompositingMode(long handle) {
		super(handle);
	}

	//------------------------------------------------------------------
	// Public methods
	//------------------------------------------------------------------

	public void setBlending(int mode) {

	}

	public int getBlending() {
		return 0;
	}

	public void setAlphaThreshold(float threshold) {

	}

	public float getAlphaThreshold() {
		return 0f;
	}

	public void setAlphaWriteEnable(boolean enable) {

	}

	public boolean isAlphaWriteEnabled() {
		return false;
	}

	public void setColorWriteEnable(boolean enable) {

	}

	public boolean isColorWriteEnabled() {
		return false;
	}

	public void setDepthWriteEnable(boolean enable) {

	}

	public boolean isDepthWriteEnabled() {
		return false;
	}

	public void setDepthTestEnable(boolean enable) {

	}

	public boolean isDepthTestEnabled() {
		return false;
	}

	public void setDepthOffset(float factor, float units) {

	}

	public float getDepthOffsetFactor() {
		return 0f;
	}

	public float getDepthOffsetUnits() {
		return 0f;
	}

}
