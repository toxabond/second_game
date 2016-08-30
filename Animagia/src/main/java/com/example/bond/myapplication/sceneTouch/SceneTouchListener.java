package com.example.bond.myapplication.sceneTouch;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.emitter.BaseParticleEmitter;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

/**
 * Created by Zver on 06.08.2016.
 */
public class SceneTouchListener implements IOnSceneTouchListener {
    private BaseParticleEmitter emitter;

    public SceneTouchListener(BaseParticleEmitter emitter) {
        this.emitter = emitter;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

        emitter.setCenter(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
        return false;
    }
}

