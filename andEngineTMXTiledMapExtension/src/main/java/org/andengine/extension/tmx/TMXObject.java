package org.andengine.extension.tmx;

import org.andengine.entity.primitive.Vector2;
import org.andengine.extension.tmx.util.constants.TMXConstants;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.util.Log;

/**
 * (c) 2010 Nicolas Gramlich (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 11:21:01 - 29.07.2010
 */
public class TMXObject implements TMXConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final String mName;
	private final String mType;
	private final int mX;
	private final int mY;
	private final int mWidth;
	private final int mHeight;
	private Vector2[] mPolyLines;
	private final TMXProperties<TMXObjectProperty> mTMXObjectProperties = new TMXProperties<TMXObjectProperty>();

	// ===========================================================
	// Constructors
	// ===========================================================

	public TMXObject(final Attributes pAttributes) {
		this.mName = pAttributes.getValue("",
				TMXConstants.TAG_OBJECT_ATTRIBUTE_NAME);
		this.mType = pAttributes.getValue("",
				TMXConstants.TAG_OBJECT_ATTRIBUTE_TYPE);
		this.mX = SAXUtils.getIntAttributeOrThrow(pAttributes,
				TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
		this.mY = SAXUtils.getIntAttributeOrThrow(pAttributes,
				TMXConstants.TAG_OBJECT_ATTRIBUTE_Y);
		this.mWidth = SAXUtils.getIntAttribute(pAttributes,
				TMXConstants.TAG_OBJECT_ATTRIBUTE_WIDTH, 0);
		this.mHeight = SAXUtils.getIntAttribute(pAttributes,
				TMXConstants.TAG_OBJECT_ATTRIBUTE_HEIGHT, 0);
		this.mPolyLines = new Vector2[]{};
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public Vector2[] getmPolyLines() {
		return mPolyLines;
	}

	public int[][] getmPolyLines(float pixelToMeterRatio) {
		Log.v("FEBI2",String.valueOf(this.getmPolyLines().length));
		int[][] polyLines = new int[this.getmPolyLines().length][2];
		for (int i = 0; i < this.getmPolyLines().length; i++) {

			polyLines[i][0] = (int) (this.getmPolyLines()[i].x
					/ pixelToMeterRatio);
			polyLines[i][1] = (int) (this.getmPolyLines()[i].y
					/ pixelToMeterRatio);

		}
		return polyLines;
	}

	public void setmPolyLines(Vector2[] mPolyLines) {
		this.mPolyLines = mPolyLines;
	}

	public String getName() {
		return this.mName;
	}

	public String getType() {
		return this.mType;
	}

	public int getX() {
		return this.mX;
	}

	public int getY() {
		return this.mY;
	}

	public int getWidth() {
		return this.mWidth;
	}

	public int getHeight() {
		return this.mHeight;
	}

	public void addTMXObjectProperty(final TMXObjectProperty pTMXObjectProperty) {
		this.mTMXObjectProperties.add(pTMXObjectProperty);
	}

	public TMXProperties<TMXObjectProperty> getTMXObjectProperties() {
		return this.mTMXObjectProperties;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
