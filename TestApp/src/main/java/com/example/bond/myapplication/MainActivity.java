package com.example.bond.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private BitmapTextureAtlas bitmapTextureAtlas;
    private ITextureRegion mRectangleOneTextureRegion;
    private TextureRegion textureRegion;

    private Camera camera;
    private Scene scene;

    @Override
    public EngineOptions onCreateEngineOptions() {

        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        final Display display = getWindowManager().getDefaultDisplay();
        final int displayWidth = display.getWidth();
        final int displayHeight = display.getHeight();

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(displayWidth, displayHeight), this.camera);
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        bitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "b.png", 0, 0);
        bitmapTextureAtlas.load();


//        BuildableBitmapTextureAtlas buildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 512);
//        mRectangleOneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, this, "b.png");
//        BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas> blackPawnTextureAtlasBuilder = new BlackPawnTextureAtlasBuilder<>(0, 0, 0);
//        buildableBitmapTextureAtlas.build(blackPawnTextureAtlasBuilder);
//        buildableBitmapTextureAtlas.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();


    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        mEngine.registerUpdateHandler(new FPSLogger());
        scene = new Scene();
        scene.setBackground(new SpriteBackground(new Sprite(0, 0, textureRegion, getVertexBufferObjectManager())));
//        scene.setBackground(new SpriteBackground(new Sprite(0, 0, mRectangleOneTextureRegion, getVertexBufferObjectManager())));
//        scene.setBackground(new RectangularShape(0,0,200,200,new ShaderProgram()))
//        scene.setBackground(new Background(0.9804f, 0.8f, 0.0f));


        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {

    }


    protected void onCreate1(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
