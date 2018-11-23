package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private SpaceSwingGame game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Viewport viewportGame;

    private final Vector2 cameraOffset  = new Vector2(1f/3f,1f/2f);
    private final float maxAttractionRadius = 500;
    private final float minAttractionRadius = 200;
    public static final float ATTRACTION_MULTIPLIER = 50f;
    public static final Vector2 GRAVITY = new Vector2(0,-13);
    private Player player;
    private ArrayList<Hole> holes;


    public GameScreen(SpaceSwingGame game, Viewport viewportGame, Viewport viewportUi){
        this.game = game;
        this.viewportGame = viewportGame;
        //ghjgjhgj
    }

    @Override
    public void show() {
        viewportGame.apply(true);


        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        player = new Player(0,viewportGame.getWorldHeight());
        holes = new ArrayList<Hole>();

        Hole h = new Hole(new Vector2(viewportGame.getWorldWidth()/2.4f,150),minAttractionRadius,maxAttractionRadius);
        holes.add(h);
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0.3f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.app.log("debug: ",""+player.getPosition());
        Vector2 offset = new Vector2(viewportGame.getWorldWidth()-cameraOffset.x*viewportGame.getWorldWidth(),
                                    viewportGame.getWorldHeight()-cameraOffset.y*viewportGame.getWorldHeight());

        viewportGame.getCamera().position.set(offset.x + player.getPosition().x,offset.y,0);
        viewportGame.getCamera().update();
        shapeRenderer.setProjectionMatrix(viewportGame.getCamera().combined);
        batch.setProjectionMatrix(viewportGame.getCamera().combined);

        if(viewportGame.getWorldWidth()/2 + viewportGame.getCamera().position.x -
                (holes.get(holes.size()-1).getPosition().x) > maxAttractionRadius){

            holes.add(new Hole(MathUtils.random(minAttractionRadius,viewportGame.getWorldHeight()-minAttractionRadius),
                    minAttractionRadius,maxAttractionRadius, viewportGame.getWorldWidth()/2+viewportGame.getCamera().position.x));
        }

        player.update(delta);
        if(player.getPosition().y<-player.getRadius()-100){
            player.resetPlayer(0,viewportGame.getWorldHeight());
            viewportGame.getCamera().position.set(player.getPosition().x+offset.x,player.getPosition().y+offset.y,0);
            resetHoles();
        }

        for(int i = holes.size()-1; i >= 0 ; i--) {
            Hole h = holes.get(i);
            if(h.getPosition().x - viewportGame.getCamera().position.x + viewportGame.getWorldWidth()/2 < -h.getAttractionRadius()){
                holes.remove(i);
            }
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i < holes.size(); i++) {
            holes.get(i).render(shapeRenderer);
        }
        player.render(shapeRenderer);
        checkForInput(shapeRenderer);
        shapeRenderer.end();
        batch.begin();
        batch.end();
    }

    public void checkForInput(ShapeRenderer sr){
        if(Gdx.input.isTouched()){
            Gdx.app.log("Touched","isTouched");
            for(Hole h : holes){
                h.forceOnPlayer(player,sr);
            }
        }
    }

    public void resetHoles(){
        //reset Camera first
        for(int i = holes.size()-1; i >= 0 ; i--) {
            holes.remove(i);
        }holes.add(new Hole(MathUtils.random(minAttractionRadius,viewportGame.getWorldWidth()-minAttractionRadius),
                minAttractionRadius,maxAttractionRadius, viewportGame.getWorldHeight()/2+viewportGame.getCamera().position.y));
        Gdx.app.log("RESET","resetHOLES");
    }

    @Override
    public void resize(int width, int height) {
        viewportGame.update(width,height);
        viewportGame.apply(true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        batch.dispose();
        shapeRenderer.dispose();
    }
}
