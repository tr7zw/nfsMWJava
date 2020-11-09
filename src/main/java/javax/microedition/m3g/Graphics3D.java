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

import java.util.Hashtable;

public class Graphics3D {
	// ------------------------------------------------------------------
	// Static data
	// ------------------------------------------------------------------

	public static final int ANTIALIAS = 2;
	public static final int DITHER = 4;
	public static final int TRUE_COLOR = 8;

	// M3G 1.1
	public static final int OVERWRITE = 16;

	// ------------------------------------------------------------------
	// Constructor(s)
	// ------------------------------------------------------------------
	public static final Graphics3D getInstance() {

		return null;
	}

	public static void initGraphics3D() {

	}

	private Graphics3D() {

	}

	// ------------------------------------------------------------------
	// Public methods
	// ------------------------------------------------------------------

	/**
	 */
	public void bindTarget(java.lang.Object target) {

	}

	/**
	 *
	 */
	public void bindTarget(java.lang.Object target, boolean depth, int flags) {

	}

	/**
	 *
	 */
	public void releaseTarget() {

	}

	/**
	 *
	 */
	public void setViewport(int x, int y, int width, int height) {

	}

	/**
	 *
	 */
	public void clear(Background background) {

	}

	/**
	 *
	 */
	public void render(World world) {

	}

	/**
	 *
	 */
	public void render(VertexBuffer vertices, IndexBuffer primitives, Appearance appearance, Transform transform) {

	}

	/**
	 *
	 */
	public void render(VertexBuffer vertices, IndexBuffer primitives, Appearance appearance, Transform transform,
			int scope) {

	}

	/**
	 *
	 */
	public void render(Node node, Transform transform) {

	}

	public void setCamera(Camera camera, Transform transform) {

	}

	/**
	 */
	public int addLight(Light light, Transform transform) {
		return 0;
	}

	/**
	 *
	 */
	public void setLight(int index, Light light, Transform transform) {

	}

	/**
	 */
	public void resetLights() {

	}

	/**
	 *
	 */
	public static final Hashtable getProperties() {
		return null;
	}

	/**
	 *
	 */
	public void setDepthRange(float near, float far) {

	}

	// M3G 1.1

	public Camera getCamera(Transform transform) {
		return null;
	}

	public float getDepthRangeFar() {
		return 0f;
	}

	public float getDepthRangeNear() {
		return 0f;
	}

	public Light getLight(int index, Transform transform) {
		return null;
	}

	public int getLightCount() {
		return 0;
	}

	public java.lang.Object getTarget() {
		return null;
	}

	public int getViewportHeight() {
		return 0;
	}

	public int getViewportWidth() {
		return 0;
	}

	public int getViewportX() {
		return 0;
	}

	public int getViewportY() {
		return 0;
	}

	public int getHints() {
		return 0;
	}

	public boolean isDepthBufferEnabled() {
		return false;
	}

}
