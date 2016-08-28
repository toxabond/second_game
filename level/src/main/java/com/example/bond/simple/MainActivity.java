package com.example.bond.simple;

import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class MainActivity extends BaseGameActivity {


    //    public static final int CAMERA_WIDTH = 1920;
//    public static final int CAMERA_HEIGHT = 1080;
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private BitmapTextureAtlas bitmapTextureAtlas;
    private TextureRegion textureRegion;

    private Camera camera;
    private Scene scene;

    /* Define the level selector properties */
    final int maxUnlockedLevel = 7;
    final int levelSelectorChapter = 1;
    private Font fontDefault32Bold;
    private TextureRegion menuLevelIconTR;

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

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        bitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "b.png", 0, 0);
        bitmapTextureAtlas.load();

        BitmapTextureAtlas LevelIconT = new BitmapTextureAtlas(mEngine.getTextureManager(), 64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuLevelIconTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(LevelIconT, this, "LevelIcon.png", 0, 0);
        LevelIconT.load();

        fontDefault32Bold = FontFactory.create(mEngine.getFontManager(), mEngine.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 14f, true, Color.WHITE_ARGB_PACKED_INT);
        fontDefault32Bold.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        mEngine.registerUpdateHandler(new FPSLogger());
        scene = new Scene();
        scene.setBackground(new SpriteBackground(new Sprite(0, 0, textureRegion, getVertexBufferObjectManager())));

        /* Create a new level selector */
        LevelSelector levelSelector = new LevelSelector(maxUnlockedLevel, levelSelectorChapter, CAMERA_WIDTH, CAMERA_HEIGHT, scene, mEngine);
        /* Generate the level tiles for the levelSelector object */
        levelSelector.createTiles(menuLevelIconTR, fontDefault32Bold);
        /* Display the levelSelector object on the scene */
        levelSelector.show();


        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, IGameInterface.OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

}
