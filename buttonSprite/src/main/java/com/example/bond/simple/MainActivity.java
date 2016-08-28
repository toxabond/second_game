package com.example.bond.simple;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

//    public static final int CAMERA_WIDTH = 1920;
//    public static final int CAMERA_HEIGHT = 1080;
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private Scene mScene;
    private Camera mCamera;
    /* The ButtonSprite class requires us to use an ITiledTextureRegion object */
    private ITiledTextureRegion mButtonTextureRegion;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public EngineOptions onCreateEngineOptions() {

        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(),
                mCamera);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws ITextureAtlasBuilder.TextureAtlasBuilderException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		/* Create the bitmap texture atlas.
		 * The bitmap texture atlas is created to fit a texture region of 300x50 pixels*/
        BuildableBitmapTextureAtlas bitmapTextureAtlas = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 300, 50);

		/* Create the buttons texture region with 2 columns, 1 row */
        mButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bitmapTextureAtlas, getAssets(), "button_tiles.png", 2, 1);

		/* Build the bitmap texture atlas */
        bitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0)).load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        mScene = new Scene();

		/* Enable touch area binding on the mScene object */
        mScene.setTouchAreaBindingOnActionDownEnabled(true);

        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {

		/* Create the buttonSprite object in the center of the Scene */
        ButtonSprite buttonSprite = new ButtonSprite(CAMERA_WIDTH * 0.5f,
                CAMERA_HEIGHT * 0.5f, mButtonTextureRegion,
                mEngine.getVertexBufferObjectManager()) {

            /* Override the onAreaTouched() event method */
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                         float pTouchAreaLocalX, float pTouchAreaLocalY) {

				/* If buttonSprite is touched with the finger */
                if (pSceneTouchEvent.isActionDown()) {

					/* When the button is pressed, we can create an event
                     * In this case, we're simply displaying a quick toast */
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Button Pressed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

				/* In order to allow the ButtonSprite to swap tiled texture region
				 * index on our buttonSprite object, we must return the super method */
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
                        pTouchAreaLocalY);
            }
        };

		/* Register the buttonSprite as a 'touchable' Entity */
        mScene.registerTouchArea(buttonSprite);
		/* Attach the buttonSprite to the Scene */
        mScene.attachChild(buttonSprite);

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

}
