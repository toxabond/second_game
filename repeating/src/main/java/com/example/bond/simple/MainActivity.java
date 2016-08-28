package com.example.bond.simple;

import android.graphics.Point;
import android.view.Display;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

    //    public static final int CAMERA_WIDTH = 1920;
    //    public static final int CAMERA_HEIGHT = 1080;
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private Camera camera;
    private Scene scene;

    private IBitmapTextureAtlasSource bitmapTextureAtlasSource;

    @Override
    public EngineOptions onCreateEngineOptions() {

        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        final Display display = getWindowManager().getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);
        final int displayWidth = outSize.x;
        final int displayHeight = outSize.y;

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(displayWidth, displayHeight), this.camera);
    }

    @Override
    public void onCreateResources(IGameInterface.OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {

        bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource.create(getAssets(), "gfx/grass.png");
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        mEngine.registerUpdateHandler(new FPSLogger());
        scene = new Scene();
        final float repeatingScale = 1.2f;
/* Create the RepeatingSpriteBackground */
        RepeatingSpriteBackground background = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT,
                getTextureManager(), bitmapTextureAtlasSource, repeatingScale,
                mEngine.getVertexBufferObjectManager());

/* Set & Enable the background */
        scene.setBackground(background);
        scene.setBackgroundEnabled(true);

        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, IGameInterface.OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

}
