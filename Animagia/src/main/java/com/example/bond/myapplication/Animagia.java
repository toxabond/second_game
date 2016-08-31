package com.example.bond.myapplication;

import android.view.Display;

import com.example.bond.myapplication.sceneTouch.MapRengionTexture;
import com.example.bond.myapplication.sceneTouch.MapScene;
import com.example.bond.myapplication.sceneTouch.SceneTouchListener;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.emitter.BaseParticleEmitter;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class Animagia extends BaseGameActivity {

    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    //    private BitmapTextureAtlas bitmapTextureAtlas;
//    private ITextureRegion mRectangleOneTextureRegion;
//    private TextureRegion mStarTextureRegion;
//    private TiledTextureRegion mTiledTextureRegion;
//    private TextureRegion textureRegion;
    private ITextureRegion mRectangleOneTextureRegion;


    private MapRengionTexture mapRengionTexture;
    private MapScene mapScene;
    private Camera camera;
    private Scene scene;
    private ITextureRegion mRectangleOneTextureRegion2;
    private ITextureRegion mRectangleOneTextureRegion3;


    @Override
    public EngineOptions onCreateEngineOptions() {

        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        final Display display = getWindowManager().getDefaultDisplay();
        final int displayWidth = display.getWidth();
        final int displayHeight = display.getHeight();


        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), this.camera);
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        mapRengionTexture = new MapRengionTexture();
        mapScene = new MapScene();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");


        BuildableBitmapTextureAtlas buildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 512);
        addTexture(0, "ground.png", buildableBitmapTextureAtlas);
        addTexture(1, "grass.png", buildableBitmapTextureAtlas);
        addTexture(2, "water.png", buildableBitmapTextureAtlas);
        addTexture(3, "stoone.png", buildableBitmapTextureAtlas);
        addTexture(4, "freep.png", buildableBitmapTextureAtlas);


        mRectangleOneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, this, "book.png");
        mRectangleOneTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, this, "animit.png");
        mRectangleOneTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, this, "bag.png");

        BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas> blackPawnTextureAtlasBuilder = new BlackPawnTextureAtlasBuilder<>(0, 0, 0);
        buildableBitmapTextureAtlas.build(blackPawnTextureAtlasBuilder);
        buildableBitmapTextureAtlas.load();



        pOnCreateResourcesCallback.onCreateResourcesFinished();


    }

    private void addTexture(Integer key, String textureName, BuildableBitmapTextureAtlas buildableBitmapTextureAtlas) {
        mapRengionTexture.hashMap.put(key, BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, this, textureName));
    }


    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        mEngine.registerUpdateHandler(new FPSLogger());
        scene = new Scene();
//        scene.setBackground(new SpriteBackground(new Sprite(0, 0, textureRegion, getVertexBufferObjectManager())));


        Sprite pSprite = null;
        for (int i = 0; i < mapScene.map.size(); i++) {
          for (int j=0; j<mapScene.map.get(i).size(); j++){
              if (i==0 && j==0){
                  pSprite = new Sprite(j*100, i*80, mapRengionTexture.hashMap.get(mapScene.map.get(i).get(j)), getVertexBufferObjectManager());
              }else{
                  pSprite.attachChild(new Sprite(j*100, i*80, mapRengionTexture.hashMap.get(mapScene.map.get(i).get(j)), getVertexBufferObjectManager()));
              }

          }
        }

        scene.setBackground(new SpriteBackground(pSprite));

        Sprite bookSprite = new Sprite(0,0,mRectangleOneTextureRegion,getVertexBufferObjectManager());
        scene.attachChild(bookSprite);
        Sprite animitSprite = new Sprite(50,50,mRectangleOneTextureRegion2,getVertexBufferObjectManager());
        scene.attachChild(animitSprite);
        Sprite bagSprite = new Sprite(200,200,mRectangleOneTextureRegion3,getVertexBufferObjectManager());
        scene.attachChild(bagSprite);
//        scene.setBackground(new RectangularShape(0,0,200,200,new ShaderProgram()))
//        scene.setBackground(new Background(0.9804f, 0.8f, 0.0f));


//        AnimatedSprite animatedSprite = createAnimatedSprite(mTiledTextureRegion);
//        scene.attachChild(animatedSprite);
///* Attach the particle system to the Scene */
//        BatchedSpriteParticleSystem particleSystem = createParticle(mStarTextureRegion);
//        scene.attachChild(particleSystem);
//        scene.setOnSceneTouchListener(new SceneTouchListener((BaseParticleEmitter)particleSystem.getParticleEmitter()));
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

}
