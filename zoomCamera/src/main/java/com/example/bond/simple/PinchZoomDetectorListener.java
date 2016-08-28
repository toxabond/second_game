package com.example.bond.simple;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;

public class PinchZoomDetectorListener implements PinchZoomDetector.IPinchZoomDetectorListener {

    // It is a good idea to place limits on zoom functionality
    private static final float MIN_ZOOM_FACTOR = 0.5f;
    private static final float MAX_ZOOM_FACTOR = 1.5f;

    private float mInitialTouchZoomFactor;
    private ZoomCamera mCamera;

    public PinchZoomDetectorListener(ZoomCamera mCamera) {
        this.mCamera = mCamera;
    }

    /* This method is fired when two fingers press down
          * on the display */
    @Override
    public void onPinchZoomStarted(PinchZoomDetector pPinchZoomDetector,
                                   TouchEvent pSceneTouchEvent) {

        // On first detection of pinch zooming, obtain the initial zoom factor
        mInitialTouchZoomFactor = mCamera.getZoomFactor();
    }

    /* This method is fired when two fingers are being moved
     * around on the display, ie. in a pinching motion */
    @Override
    public void onPinchZoom(PinchZoomDetector pPinchZoomDetector,
                            TouchEvent pTouchEvent, float pZoomFactor) {

		/* On every sub-sequent touch event (after the initial touch) we offset
		 * the initial camera zoom factor by the zoom factor calculated by
		 * pinch-zooming */
        final float newZoomFactor = mInitialTouchZoomFactor * pZoomFactor;

        // If the camera is within zooming bounds
        if (newZoomFactor < MAX_ZOOM_FACTOR && newZoomFactor > MIN_ZOOM_FACTOR) {
            // Set the new zoom factor
            mCamera.setZoomFactor(newZoomFactor);
        }
    }

    /* This method is fired when fingers are lifted from the screen */
    @Override
    public void onPinchZoomFinished(PinchZoomDetector pPinchZoomDetector,
                                    TouchEvent pTouchEvent, float pZoomFactor) {

        // Set the zoom factor one last time upon ending the pinch-to-zoom functionality
        final float newZoomFactor = mInitialTouchZoomFactor * pZoomFactor;

        // If the camera is within zooming bounds
        if (newZoomFactor < MAX_ZOOM_FACTOR && newZoomFactor > MIN_ZOOM_FACTOR) {
            // Set the new zoom factor
            mCamera.setZoomFactor(newZoomFactor);
        }
    }
}
