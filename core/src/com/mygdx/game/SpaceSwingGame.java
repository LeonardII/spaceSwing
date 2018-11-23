package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SpaceSwingGame extends Game {

	private final float GAME_WORLD_WIDTH = 1000;

	private final Viewport VIEWPORT_GAME = new ExtendViewport(GAME_WORLD_WIDTH,GAME_WORLD_WIDTH);;
	private final Viewport VIEWPORT_UI = new ExtendViewport(GAME_WORLD_WIDTH,GAME_WORLD_WIDTH);
	@Override
	public void create () {
		VIEWPORT_GAME.setWorldWidth(GAME_WORLD_WIDTH);
		this.setScreen(new GameScreen(this,VIEWPORT_GAME,VIEWPORT_UI));
	}
}
