package com.example.bond.simple;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.ui.activity.BaseGameActivity;

public class MainZoomCamera extends BaseGameActivity {

    //    public static final int CAMERA_WIDTH = 1920;
//    public static final int CAMERA_HEIGHT = 1080;
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private static final int RECTANGLE_DIMENSIONS = 200;


    private Scene mScene;
    private ZoomCamera mCamera;


    @Override
    public EngineOptions onCreateEngineOptions() {

        // We need to use zoom or smooth camera
        mCamera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), mCamera);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        mScene = new Scene();

		/* Create and set the zoom detector to listen for
         * touch events using this activity's listener */
        PinchZoomDetector mPinchZoomDetector = new PinchZoomDetector(new PinchZoomDetectorListener(mCamera));
        // Enable the zoom detector
        mPinchZoomDetector.setEnabled(true);

        		/* Set the scene to listen for touch events using
         * this activity's listener */
        mScene.setOnSceneTouchListener(mPinchZoomDetector);

        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {

		/* A rectangle is set on the scene in order to help us visualize
         * the zoom factor changes upon pinching the display */
        final float x = CAMERA_WIDTH * 0.5f - RECTANGLE_DIMENSIONS * 0.5f;
        final float y = CAMERA_HEIGHT * 0.5f - RECTANGLE_DIMENSIONS * 0.5f;

        Rectangle rectangle = new Rectangle(x, y, RECTANGLE_DIMENSIONS, RECTANGLE_DIMENSIONS,
                mEngine.getVertexBufferObjectManager());
        mScene.attachChild(rectangle);

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }


}
