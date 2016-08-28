package com.example.bond.simple;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public abstract class ManagedGameScene extends ManagedScene {
    // Create an easy to manage HUD that we can attach/detach when the game scene is shown or hidden.
    public HUD GameHud = new HUD();
    public ManagedGameScene thisManagedGameScene = this;

    public ManagedGameScene() {
        // Let the Scene Manager know that we want to show a Loading Scene for at least 2 seconds.
        this(0f);
    }

    ;

    public ManagedGameScene(float pLoadingScreenMinimumSecondsShown) {
        super(pLoadingScreenMinimumSecondsShown);
        // Setup the touch attributes for the Game Scenes.
        this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionMoveEnabled(true);
        // Scale the Game Scenes according to the Camera's scale factor.
        this.setScale(ResourceManager.getInstance().cameraScaleFactorX, ResourceManager.getInstance().cameraScaleFactorY);
        //this.setPosition(0, ResourceManager.getInstance().cameraHeight * .5f);
        GameHud.setScaleCenter(0f, 0f);
        GameHud.setScale(ResourceManager.getInstance().cameraScaleFactorX, ResourceManager.getInstance().cameraScaleFactorY);
    }

    // These objects will make up our loading scene.
    private Text LoadingText;
    private Scene LoadingScene;

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        // Setup and return the loading screen.
        LoadingScene = new Scene();
        LoadingScene.setBackgroundEnabled(true);
        LoadingText = new Text(0, 0, ResourceManager.fontDefault32Bold, "Loading...", ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        LoadingText.setPosition((ResourceManager.getInstance().cameraWidth - LoadingText.getWidth()) * 0.5f, (ResourceManager.getInstance().cameraHeight - LoadingText.getHeight()) * 0.5f);
        LoadingScene.attachChild(LoadingText);
        return LoadingScene;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {
        // detach the loading screen resources.
        LoadingText.detachSelf();
        LoadingText = null;
        LoadingScene = null;
    }

    @Override
    public void onLoadScene() {
        // Load the resources to be used in the Game Scenes.
        ResourceManager.loadGameResources();

        // Create a Sprite to use as the background.
        Sprite sprite = new Sprite(0, 0, ResourceManager.gameBackgroundTextureRegion, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        this.attachChild(sprite);
        this.getLastChild().setScaleCenter(0f, sprite.getHeight() * .5f);
        this.getLastChild().setScaleX(800f);

        // Setup the HUD Buttons and Button Texts.
        // Take note of what happens when the buttons are clicked.
        ButtonSprite MainMenuButton = new ButtonSprite(
                (ResourceManager.getInstance().cameraWidth * 0.5f - ResourceManager.buttonTiledTextureRegion.getTextureRegion(0).getWidth()),
                (ResourceManager.getInstance().cameraHeight - ResourceManager.buttonTiledTextureRegion.getTextureRegion(0).getHeight()) * (1f / 3f),
                ResourceManager.buttonTiledTextureRegion.getTextureRegion(0),
                ResourceManager.buttonTiledTextureRegion.getTextureRegion(1),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());

        MainMenuButton.setScale(1 / ResourceManager.getInstance().cameraScaleFactorX, 1 / ResourceManager.getInstance().cameraScaleFactorY);
//        MainMenuButton.setPosition((MainMenuButton.getWidth() * MainMenuButton.getScaleX()) * .5f, (MainMenuButton.getHeight() * MainMenuButton.getScaleY()) * .5f);
        MainMenuButton.setOnClickListener(new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite,
                                float pTouchAreaLocalX, float pTouchAreaLocalY) {
                // Play the click sound and show the Main Menu.
                ResourceManager.clickSound.play();
                SceneManager.getInstance().showMainMenu();
            }
        });

        Text MainMenuButtonText = new Text(MainMenuButton.getWidth() / 2, MainMenuButton.getHeight() / 2, ResourceManager.fontDefault32Bold, "MENU", ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        MainMenuButtonText.setPosition((MainMenuButton.getWidth() - MainMenuButtonText.getWidth()) / 2, (MainMenuButton.getHeight() - MainMenuButtonText.getHeight()) * .5f);
        MainMenuButton.attachChild(MainMenuButtonText);
        GameHud.attachChild(MainMenuButton);
        GameHud.registerTouchArea(MainMenuButton);

        ButtonSprite OptionsButton = new ButtonSprite(
                MainMenuButton.getX() + MainMenuButton.getWidth(),
                MainMenuButton.getY(),
                ResourceManager.buttonTiledTextureRegion.getTextureRegion(0),
                ResourceManager.buttonTiledTextureRegion.getTextureRegion(1),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        OptionsButton.setScale(1 / ResourceManager.getInstance().cameraScaleFactorX, 1 / ResourceManager.getInstance().cameraScaleFactorY);
//        OptionsButton.setPosition(800f - ((OptionsButton.getWidth() * OptionsButton.getScaleX()) * .5f), (OptionsButton.getHeight() * OptionsButton.getScaleY()) * .5f);
        OptionsButton.setOnClickListener(new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite,
                                float pTouchAreaLocalX, float pTouchAreaLocalY) {
                // Play the click sound and show the Options Layer.
                ResourceManager.clickSound.play();
                SceneManager.getInstance().showOptionsLayer(true);
            }
        });

        Text OptionsButtonText = new Text(0, 0, ResourceManager.fontDefault32Bold, "OPTIONS", ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        OptionsButtonText.setPosition((OptionsButton.getWidth() - OptionsButtonText.getWidth()) * .5f, (OptionsButton.getHeight() - OptionsButtonText.getHeight()) * .5f);
        //OptionsButtonText.setPosition((OptionsButton.getWidth()) *.5f, (OptionsButton.getHeight()) *.5f);
        OptionsButton.attachChild(OptionsButtonText);
        GameHud.attachChild(OptionsButton);
        GameHud.registerTouchArea(OptionsButton);
    }

    @Override
    public void onShowScene() {
        // We want to wait to set the HUD until the scene is shown because otherwise it will appear on top of the loading screen.
        ResourceManager.getInstance().engine.getCamera().setHUD(GameHud);
    }

    @Override
    public void onHideScene() {
        ResourceManager.getInstance().engine.getCamera().setHUD(null);
    }

    @Override
    public void onUnloadScene() {
        // detach and unload the scene.
        ResourceManager.getInstance().engine.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                thisManagedGameScene.detachChildren();
                thisManagedGameScene.clearEntityModifiers();
                thisManagedGameScene.clearTouchAreas();
                thisManagedGameScene.clearUpdateHandlers();
            }
        });
    }
}