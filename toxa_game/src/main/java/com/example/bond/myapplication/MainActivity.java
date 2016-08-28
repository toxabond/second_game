package com.example.bond.myapplication;

import android.view.Display;

import com.example.bond.myapplication.sceneTouch.SceneTouchListener;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
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
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private BitmapTextureAtlas bitmapTextureAtlas;
    private ITextureRegion mRectangleOneTextureRegion;
    private TextureRegion mStarTextureRegion;
    private TiledTextureRegion mTiledTextureRegion;
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
        bitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 32 + (636 - 32), 32 + 104+1, TextureOptions.BILINEAR);
        mStarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "star.png", 0, 0);
        mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bitmapTextureAtlas, this, "ball.png", 0, 33, 12, 1);


        BuildableBitmapTextureAtlas buildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 512);
        mRectangleOneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, this, "b.png");

        BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas> blackPawnTextureAtlasBuilder = new BlackPawnTextureAtlasBuilder<>(0, 0, 0);
        buildableBitmapTextureAtlas.build(blackPawnTextureAtlasBuilder);
        buildableBitmapTextureAtlas.load();

        bitmapTextureAtlas.load();
        pOnCreateResourcesCallback.onCreateResourcesFinished();


    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        mEngine.registerUpdateHandler(new FPSLogger());
        scene = new Scene();
//        scene.setBackground(new SpriteBackground(new Sprite(0, 0, textureRegion, getVertexBufferObjectManager())));
        scene.setBackground(new SpriteBackground(new Sprite(250, 250, mRectangleOneTextureRegion, getVertexBufferObjectManager())));
//        scene.setBackground(new RectangularShape(0,0,200,200,new ShaderProgram()))
//        scene.setBackground(new Background(0.9804f, 0.8f, 0.0f));


        AnimatedSprite animatedSprite = createAnimatedSprite(mTiledTextureRegion);
        scene.attachChild(animatedSprite);
/* Attach the particle system to the Scene */
        BatchedSpriteParticleSystem particleSystem = createParticle(mStarTextureRegion);
        scene.attachChild(particleSystem);
        scene.setOnSceneTouchListener(new SceneTouchListener((BaseParticleEmitter)particleSystem.getParticleEmitter()));
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    private AnimatedSprite createAnimatedSprite(TiledTextureRegion mTiledTextureRegion) {

/* Create a new animated sprite in the center of the scene */
        AnimatedSprite animatedSprite = new AnimatedSprite(50, CAMERA_HEIGHT * 0.5f, mTiledTextureRegion, mEngine.getVertexBufferObjectManager());
/* Length to play each frame before moving to the next */
        long frameDuration[] = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200};
        for (int i = 0; i <frameDuration.length ; i++) {
            frameDuration[i] *= 0.2f;
        }
/* We can define the indices of the animation to play between */
        int firstTileIndex = 0;
        int lastTileIndex = mTiledTextureRegion.getTileCount() - 1;
/* Allow the animation to continuously loop? */
        boolean loopAnimation = true;
        /* Animate the sprite with the data as set defined above */
        animatedSprite.animate(frameDuration, firstTileIndex,
                lastTileIndex, loopAnimation, new AnimatedSprite.IAnimationListener() {
                    @Override
                    public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
                                                   int pInitialLoopCount) {
/* Fired when the animation first begins to run*/
                    }

                    @Override
                    public void onAnimationFrameChanged(AnimatedSprite
                                                                pAnimatedSprite,
                                                        int pOldFrameIndex, int pNewFrameIndex) {
/* Fired every time a new frame is selected to display*/
                    }

                    @Override
                    public void onAnimationLoopFinished(AnimatedSprite
                                                                pAnimatedSprite,
                                                        int pRemainingLoopCount, int pInitialLoopCount) {
/* Fired when an animation loop ends (from first to last
frame) */
                    }

                    @Override
                    public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
/* Fired when an animation sequence ends */
                    }
                }
        );
        return animatedSprite;

    }

    private BatchedSpriteParticleSystem createParticle(ITextureRegion mTextureRegion) {

/* Define the center point of the particle system spawn location */
        final int particleSpawnCenterX = (int) (CAMERA_WIDTH * 0.5f);
        final int particleSpawnCenterY = (int) (CAMERA_HEIGHT * 0.5f);
/* Create the particle emitter */
        PointParticleEmitter particleEmitter = new PointParticleEmitter(particleSpawnCenterX, particleSpawnCenterY);

/* Define the particle system properties */
        final float minSpawnRate = 2;
        final float maxSpawnRate = 5;
        final int maxParticleCount = 150;
/* Create the particle system */
        BatchedSpriteParticleSystem particleSystem = new BatchedSpriteParticleSystem(
                particleEmitter, minSpawnRate, maxSpawnRate,
                maxParticleCount,
                mTextureRegion,
                mEngine.getVertexBufferObjectManager());

        /* Add an acceleration initializer to the particle system */
        particleSystem.addParticleInitializer(new AccelerationParticleInitializer<UncoloredSprite>(50f, -50f, -50f, 50f));
/* Add an expire initializer to the particle system */
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(4));
/* Add a particle modifier to the particle system */
        particleSystem.addParticleModifier(new ScaleParticleModifier<UncoloredSprite>(0f, 3f, 0f, 3f));

        particleSystem.addParticleModifier(new RotationParticleModifier<UncoloredSprite>(2f, 3f, 0f, 90f));
        particleSystem.addParticleModifier(new AlphaParticleModifier<UncoloredSprite>(3f, 4f, 1f, 0f));


        return particleSystem;
    }
//
//
//    protected void onCreate1(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
}
