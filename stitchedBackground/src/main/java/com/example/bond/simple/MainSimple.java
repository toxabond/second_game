package com.example.bond.simple;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

public class MainSimple extends BaseGameActivity implements IOnSceneTouchListener, PinchZoomDetector.IPinchZoomDetectorListener {


    //    public static final int CAMERA_WIDTH = 1920;
//    public static final int CAMERA_HEIGHT = 1080;
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;
    private Scene mScene;
    private ZoomCamera mCamera;

    // Background texture regions
    private ITextureRegion mBackgroundLeftTextureRegion;
    private ITextureRegion mBackgroundRightTextureRegion;

    // Background sprites
    private Sprite mBackgroundLeftSprite;
    private Sprite mBackgroundRightSprite;

    // Zoom detector
    private PinchZoomDetector mPinchZoomDetector;

    private float mInitialTouchZoomFactor;

    // Initial scene touch coordinates on ACTION_DOWN
    private float mInitialTouchX;
    private float mInitialTouchY;

    @Override
    public EngineOptions onCreateEngineOptions() {

        // Create the zoom camera
        mCamera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        // Enable our level bounds so that we cant scroll too far
        mCamera.setBounds(0, 0, 1600, 480);
        mCamera.setBoundsEnabled(true);

        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), mCamera);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws ITextureAtlasBuilder.TextureAtlasBuilderException {

        // Set the base path
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		/* Create the background left texture atlas */
        BuildableBitmapTextureAtlas backgroundTextureLeft = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 800, 480, TextureOptions.BILINEAR);

		/* Create the background left texture region */
        mBackgroundLeftTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureLeft, getAssets(), "background_left.png");

		/* Build and load the background left texture atlas */
        backgroundTextureLeft.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0)).load();

		/* Create the background right texture atlas */
        BuildableBitmapTextureAtlas backgroundTextureRight = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 800, 480, TextureOptions.BILINEAR);

		/* Create the background right texture region */
        mBackgroundRightTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureRight, getAssets(), "background_right.png");

		/* Build and load the background right texture atlas */
        backgroundTextureRight.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0)).load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {

        mScene = new Scene();

        // Register this activity as our touch listener
        mScene.setOnSceneTouchListener(this);

        // Register this activity as our zoom detector listener and enable it
        mPinchZoomDetector = new PinchZoomDetector(this);
        mPinchZoomDetector.setEnabled(true);

        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {

        // Create left background sprite
//        mBackgroundLeftSprite = new Sprite(halfTextureWidth, halfTextureHeight, mBackgroundLeftTextureRegion, mEngine.getVertexBufferObjectManager());
        mBackgroundLeftSprite = new Sprite(0, 0, mBackgroundLeftTextureRegion, mEngine.getVertexBufferObjectManager());
        // Attach left background sprite to the background scene
        mScene.attachChild(mBackgroundLeftSprite);

        // Create the right background sprite, positioned directly to the right of the first segment
        mBackgroundRightSprite = new Sprite(mBackgroundLeftSprite.getX() + mBackgroundLeftTextureRegion.getWidth(), 0, mBackgroundRightTextureRegion, mEngine.getVertexBufferObjectManager());

        // Attach right background sprite to the background scene
        mScene.attachChild(mBackgroundRightSprite);

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public void onPinchZoomStarted(PinchZoomDetector pPinchZoomDetector, TouchEvent pSceneTouchEvent) {
        // Obtain the initial zoom factor on pinch detection
        mInitialTouchZoomFactor = mCamera.getZoomFactor();
    }

    @Override
    public void onPinchZoom(PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor) {
        // Calculate the zoom offset
        final float newZoomFactor = mInitialTouchZoomFactor * pZoomFactor;

        // Apply the zoom offset to the camera, allowing zooming of between (default) 1x and 2x
        if (newZoomFactor < 2f && newZoomFactor > 1f)
            mCamera.setZoomFactor(newZoomFactor);
    }

    @Override
    public void onPinchZoomFinished(PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor) {
        // Calculate the zoom offset
        final float newZoomFactor = mInitialTouchZoomFactor * pZoomFactor;

        // Apply the zoom offset to the camera, allowing zooming of between (default) 1x and 2x
        if (newZoomFactor < 2f && newZoomFactor > 1f)
            mCamera.setZoomFactor(newZoomFactor);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        // Pass the touch events to the zoom detector
        mPinchZoomDetector.onTouchEvent(pSceneTouchEvent);

        if (pSceneTouchEvent.isActionDown()) {
            // Obtain the initial touch coordinates when the scene is first pressed
            mInitialTouchX = pSceneTouchEvent.getX();
            mInitialTouchY = pSceneTouchEvent.getY();
        }

        if (pSceneTouchEvent.isActionMove()) {
            // Calculate the offset touch coordinates
            final float touchOffsetX = mInitialTouchX - pSceneTouchEvent.getX();
            final float touchOffsetY = mInitialTouchY - pSceneTouchEvent.getY();
            // Apply the offset touch coordinates to the current camera coordinates
            mCamera.setCenter(mCamera.getCenterX() + touchOffsetX, mCamera.getCenterY() + touchOffsetY);
        }
        return true;
    }
}