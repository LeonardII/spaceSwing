package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Hole {
    private Vector2 position;
    private float attractionRadius;
    private boolean playerPassed;

    public Hole(float y, float minAttractionRadius, float maxAttractionRadius, float worldTop){
        playerPassed = false;
        attractionRadius = MathUtils.random(minAttractionRadius,maxAttractionRadius);
        position = new Vector2(worldTop+attractionRadius,y);
    }

    public Hole (Vector2 position, float minAttractionRadius, float maxAttractionRadius){
        this.position = position;
        attractionRadius = MathUtils.random(minAttractionRadius,maxAttractionRadius);
        playerPassed = false;
    }

    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(0.6f,0.6f,0.9f,0.6f);
        shapeRenderer.circle(position.x, position.y, attractionRadius);
        shapeRenderer.setColor(0.2f,0.4f,0.9f,1f);
        shapeRenderer.circle(position.x,position.y,30);
    }


    public void forceOnPlayer(Player player,ShapeRenderer sr){
        Vector2 playerPosition = player.getPosition();
        Vector2 pos = new Vector2(position);
        if(pos.dst(playerPosition) <= attractionRadius){
            Vector2 dif = pos.sub(player.getPosition());
            playerPassed = true;
            if(Math.abs(dif.angle(player.getVelocity())) >= 90 || playerPassed){
                dif.setLength((attractionRadius*2-dif.len())* GameScreen.ATTRACTION_MULTIPLIER/attractionRadius);
                player.applyAccelerationVector(sr,dif);
            }
            Gdx.app.log("angle",Math.abs(dif.angle(player.getVelocity()))+"");

        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y){
        position = new Vector2(x,y);

    }
    public float getAttractionRadius() {
        return attractionRadius;
    }
}
