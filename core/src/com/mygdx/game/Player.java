package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    //private Vector2 acceleration;
    private float radius = 30;
    public Player(float xStart, float yStart){
        resetPlayer(xStart,yStart);
    }

    void resetPlayer(float xStart, float yStart){
        position = new Vector2(xStart, yStart);
        velocity = new Vector2(700,200);
        //acceleration = new Vector2(0,0);
    }
    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.circle(position.x,position.y,radius);
    }
    public void update(float dt){
        velocity.add(GameScreen.GRAVITY);
        position.mulAdd(velocity,dt);
        //position.mulAdd(new Vector2(100,0),dt);
        //acceleration.scl(0.9f);
        //velocity.scl(0.996f);
    }
    public Vector2 getPosition() {
        return new Vector2(position);
    }
    public void applyAccelerationVector(ShapeRenderer sr, Vector2 vec){
        Vector2 pos = new Vector2(position);
        sr.setColor(Color.RED);
        sr.rectLine(pos.x,pos.y,pos.x+vec.x*50,pos.y+vec.y*100,10);
        float v = velocity.len();
        velocity.add(vec);
        velocity.scl(v/velocity.len());
        //Gdx.app.log("accelerationG",""+acceleration);
    }
    public float getRadius(){
        return radius;
    }
    public Vector2 getVelocity(){
        return velocity;
    }
}
