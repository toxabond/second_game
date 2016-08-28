package com.example.bond.simple;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

        public static final int CAMERA_WIDTH = 1920;
        public static final int CAMERA_HEIGHT = 1080;
//    public static final int CAMERA_WIDTH = 800;
//    public static final int CAMERA_HEIGHT = 480;

    /* Min/Max distances the camera can automatically pan to on the X axis */
    private static final float CAMERA_MIN_CENTER_X = 0;
    private static final float CAMERA_MAX_CENTER_X = CAMERA_WIDTH;

    /* Camera scroll speed factor */
    private static final float SCROLL_FACTOR = 5;

    private Scene mScene;
    private Camera mCamera;

    private ITextureRegion mHillTextureRegion;

    @Override
    public EngineOptions onCreateEngineOptions() {

        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT) {

            /* Boolean value which will determine whether
             * to increase or decrease x coordinate */
            boolean incrementX = true;

            /* On camera update... */
            @Override
            public void onUpdate(float pSecondsElapsed) {

				/* Obtain the current camera X coordinate */
                final float currentCenterX = this.getCenterX();

				/* Value which will be used to offset the camera */
                float newCenterX = 0;

				/* If incrementX is true... */
                if (incrementX) {

					/* offset the camera's x coordinate according to time passed */
                    newCenterX = currentCenterX + pSecondsElapsed * SCROLL_FACTOR;

					/* If the new offset coordinate is greater than the max X limit */
                    if (newCenterX >= CAMERA_MAX_CENTER_X) {

						/* Set to decrement the camera's X coordinate next */
                        incrementX = false;
                    }
                } else {
                    /* If increment is equal to false, decrement X coordinate */
                    newCenterX = currentCenterX - pSecondsElapsed * SCROLL_FACTOR;

					/* If the new offset coordinate is less than the min X limit */
                    if (newCenterX <= CAMERA_MIN_CENTER_X) {

						/* Set to increment the camera's X coordinate next */
                        incrementX = true;
                    }
                }

				/* Apply the offset position to the camera */
                this.setCenter(newCenterX, this.getCenterY());

                super.onUpdate(pSecondsElapsed);
            }
        };

        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(),
                mCamera);

        return engineOptions;
    }

    @Override
    public void onCreateResources(IGameInterface.OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		/* Create a texture atlas suitable for the hill.png.
		 * The hill image used for this recipe is equal to 800x75
		 * pixels in dimension*/
        BuildableBitmapTextureAtlas bitmapTextureAtlas = new BuildableBitmapTextureAtlas(
                mEngine.getTextureManager(), 800, 150);

		/* Create the mHillTextureRegion, passing in the hill.png image */
        mHillTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, getAssets(), "hill.png");

		/* Build the texture atlas. Since we're loading only a
		 * single image into the texture atlas, we need not
		 * worry about padding or spacing values */
        bitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));

		/* Load the texture atlas */
        bitmapTextureAtlas.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        mScene = new Scene();

		/* Obtain the mHillTextureRegion image height for the purpose of
		 * placing the Sprites at different heights on the Scene */
        final float textureHeight = mHillTextureRegion.getHeight();

		/* Create the hill which will appear to be the furthest
		 * into the distance. This Sprite will be placed higher than the
		 * rest in order to retain visibility of it */
        Sprite hillFurthest = new Sprite(CAMERA_WIDTH * 0.5f*0, CAMERA_HEIGHT - textureHeight  - 50, mHillTextureRegion,
                mEngine.getVertexBufferObjectManager());

		/* Create the hill which will appear between the furthest and closest
		 * hills. This Sprite will be placed higher than the closest hill, but
		 * lower than the furthest hill in order to retain visibility */
        Sprite hillMid = new Sprite(CAMERA_WIDTH * 0.5f*0, CAMERA_HEIGHT - textureHeight  - 25, mHillTextureRegion,
                mEngine.getVertexBufferObjectManager());

		/* Create the closest hill which will not be obstructed by any other hill
		 * Sprites. This Sprite will be placed at the bottom of the Scene since
		 * nothing will be covering its view */
        Sprite hillClosest = new Sprite(CAMERA_WIDTH * 0.5f*0, CAMERA_HEIGHT - textureHeight , mHillTextureRegion,
                mEngine.getVertexBufferObjectManager());

		/* Create the ParallaxBackground, setting the color values to represent
		 * a blue sky */
        ParallaxBackground background = new ParallaxBackground(0.3f, 0.3f, 0.9f) {

            /* We'll use these values to calculate the
             * parallax value of the background */
            float cameraPreviousX = 0;
            float parallaxValueOffset = 0;

            /* onUpdates to the background, we need to calculate new
             * parallax values in order to apply movement to the background
             * objects (the hills in this case) */
            @Override
            public void onUpdate(float pSecondsElapsed) {

				/* Obtain the camera's current center X value */
                final float cameraCurrentX = mCamera.getCenterX();

				/* If the camera's position has changed since last
				 * update... */
                if (cameraPreviousX != cameraCurrentX) {

					/* Calculate the new parallax value offset by
					 * subtracting the previous update's camera x coordinate
					 * from the current update's camera x coordinate */
                    parallaxValueOffset += cameraCurrentX - cameraPreviousX;

					/* Apply the parallax value offset to the background, which
					 * will in-turn offset the positions of entities attached
					 * to the background */
                    this.setParallaxValue(parallaxValueOffset);

					/* Update the previous camera X since we're finished with this
					 * update */
                    cameraPreviousX = cameraCurrentX;
                }

                super.onUpdate(pSecondsElapsed);
            }
        };

		/* Rather than attaching Sprite's to the ParallaxBackground, we must
		 * attach ParallaxEntity objects. We create a new ParallaxEntity for
		 * each Sprite we'd like to attach to the background, specifying a
		 * parallax factor (speed in which to move). Further objects should move
		 * slower than closer objects */
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(5, hillFurthest));
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(11, hillMid));
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(17, hillClosest));

		/* Set & Enabled the background */
        mScene.setBackground(background);
        mScene.attachChild(new Sprite(0,0,mHillTextureRegion,mEngine.getVertexBufferObjectManager()));
//        mScene.setBackground(new EntityBackground(new Sprite(0,300,mHillTextureRegion,mEngine.getVertexBufferObjectManager())));
        mScene.setBackgroundEnabled(true);

        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, IGameInterface.OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }


}
