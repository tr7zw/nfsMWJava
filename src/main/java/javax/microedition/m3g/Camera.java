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

public class Camera extends Node {
	//------------------------------------------------------------------
	// Static data
	//------------------------------------------------------------------

	public static final int GENERIC = 48;
	public static final int PARALLEL = 49;
	public static final int PERSPECTIVE = 50;

	//------------------------------------------------------------------
	// Constructors
	//------------------------------------------------------------------

	public Camera() {
		super(0);
	}

	/**
	 */
	Camera(long handle) {
		super(handle);
	}

	//------------------------------------------------------------------
	// Public methods
	//------------------------------------------------------------------

	public void setParallel(float height, float aspectRatio, float near, float far) {
		
	}

	public void setPerspective(float fovy, float aspectRatio, float near, float far) {
		
	}

	public void setGeneric(Transform transform) {
		
	}

	public int getProjection(Transform transform) {
		return 0;
	}

	public int getProjection(float[] params) {
		return 0;
	}

}
