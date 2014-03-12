package com.nickan.epiphany.view;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.Character;
import com.nickan.epiphany.tweenaccessories.SpriteTween;
import com.nickan.framework1_0.Animation;
import com.nickan.framework1_0.Animation.PlayMode;

/**
 * This class will work independently for the animation, I still don't know if this is the best way
 * but I am still trying to figure all out.
 * (Should be put in one file and just separate by coding, will do later if needed for optimization)
 * @author Nickan
 */
public class AllAnimations extends BaseAllAnimations{
	int moveX = 0;
	int moveY = 0;

	Animation attackE;
	Animation attackN;
	Animation attackNe;
	Animation attackNw;
	Animation attackW;
	Animation attackS;
	Animation attackSe;
	Animation attackSw;
	Animation runN;
	Animation runE;
	Animation runNe;
	Animation runNw;
	Animation runS;
	Animation runW;
	Animation runSe;
	Animation runSw;
	Animation tipOverE;
	Animation tipOverN;
	Animation tipOverNe;
	Animation tipOverNw;
	Animation tipOverW;
	Animation tipOverS;
	Animation tipOverSe;
	Animation tipOverSw;

	// Thinking if I will still use this (flinching)
	Animation hitE;
	Animation hitN;
	Animation hitNe;
	Animation hitNw;
	Animation hitW;
	Animation hitS;
	Animation hitSe;
	Animation hitSw;

	private Texture attackETexture;
	private Texture attackNTexture;
	private Texture attackNeTexture;
	private Texture attackNwTexture;
	private Texture attackWTexture;
	private Texture attackSTexture;
	private Texture attackSeTexture;
	private Texture attackSwTexture;
	private Texture runNTexture;
	private Texture runETexture;
	private Texture runNeTexture;
	private Texture runNwTexture;
	private Texture runSTexture;
	private Texture runWTexture;
	private Texture runSeTexture;
	private Texture runSwTexture;
	private Texture tipOverETexture;
	private Texture tipOverNTexture;
	private Texture tipOverNeTexture;
	private Texture tipOverNwTexture;
	private Texture tipOverWTexture;
	private Texture tipOverSTexture;
	private Texture tipOverSeTexture;
	private Texture tipOverSwTexture;


	boolean updateTipOverSprite = true;
	private Sprite tipOverSprite;
	private TweenManager tweenManager;

	private Texture standing;

	float stateTime = 0;
	float tipOverStateTime = 0;

//	public AllAnimations() { }

	public AllAnimations(Character character, String mainFilePath, int width, int height, int columns, int totalFrames,
			float animationDelay) {
		super(character, width, height, columns, totalFrames, animationDelay);

		standing = new Texture(mainFilePath + "/standing.png");

		initializeAttackAnimation(mainFilePath);
		initializeRunningAnimation(mainFilePath, animationDelay);
		initializeTipOverAnimation(mainFilePath, animationDelay);
	}

	public AllAnimations(Character character, AllAnimations allAnimations) {
		super(character, allAnimations.width, allAnimations.height, allAnimations.columns,
				allAnimations.totalFrames, allAnimations.animationDelay);

		standing = allAnimations.standing;

		attackN = new Animation(allAnimations.attackN.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.NORMAL);
		attackE = new Animation(allAnimations.attackE.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.NORMAL);
		attackNe = new Animation(allAnimations.attackNe.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.NORMAL);
		attackNw = new Animation(allAnimations.attackNw.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.NORMAL);

		attackS = new Animation(allAnimations.attackS.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.NORMAL);
		attackW = new Animation(allAnimations.attackW.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.NORMAL);
		attackSe = new Animation(allAnimations.attackSe.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.NORMAL);
		attackSw = new Animation(allAnimations.attackSw.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.NORMAL);

		runE = new Animation(allAnimations.runE.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.LOOP);
		runN = new Animation(allAnimations.runN.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.LOOP);
		runNe = new Animation(allAnimations.runNe.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.LOOP);
		runNw = new Animation(allAnimations.runNw.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.LOOP);
		runW = new Animation(allAnimations.runW.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.LOOP);
		runS = new Animation(allAnimations.runS.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.LOOP);
		runSe = new Animation(allAnimations.runSe.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.LOOP);
		runSw = new Animation(allAnimations.runSw.getRegions(), totalFrames, character.statsHandler.attackDelay, PlayMode.LOOP);


		tipOverE = new Animation(allAnimations.tipOverE.getRegions(), totalFrames, animationDelay);
		tipOverN = new Animation(allAnimations.tipOverN.getRegions(), totalFrames, animationDelay);
		tipOverNe = new Animation(allAnimations.tipOverNe.getRegions(), totalFrames, animationDelay);
		tipOverNw = new Animation(allAnimations.tipOverNw.getRegions(), totalFrames, animationDelay);
		tipOverW = new Animation(allAnimations.tipOverW.getRegions(), totalFrames, animationDelay);
		tipOverS = new Animation(allAnimations.tipOverS.getRegions(), totalFrames, animationDelay);
		tipOverSe = new Animation(allAnimations.tipOverSe.getRegions(), totalFrames, animationDelay);
		tipOverSw = new Animation(allAnimations.tipOverSw.getRegions(), totalFrames, animationDelay);
	}

	private void initializeAttackAnimation(String mainFilePath) {
		attackNTexture = new Texture(mainFilePath + "/attack n.png");
		attackNTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		attackN = new Animation(attackNTexture, 0, 0, width, height, columns, totalFrames, character.statsHandler.attackDelay);

		attackETexture = new Texture(mainFilePath + "/attack e.png");
		attackETexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		attackE = new Animation(attackETexture, 0, 0, width, height, columns, totalFrames, character.statsHandler.attackDelay);

		attackNeTexture = new Texture(mainFilePath + "/attack ne.png");
		attackNeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		attackNe = new Animation(attackNeTexture, 0, 0, width, height, columns, totalFrames, character.statsHandler.attackDelay);

		attackNwTexture = new Texture(mainFilePath + "/attack nw.png");
		attackNwTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		attackNw = new Animation(attackNwTexture, 0, 0, width, height, columns, totalFrames, character.statsHandler.attackDelay);

		attackSTexture = new Texture(mainFilePath + "/attack s.png");
		attackSTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		attackS = new Animation(attackSTexture, 0, 0, width, height, columns, totalFrames, character.statsHandler.attackDelay);

		attackWTexture = new Texture(mainFilePath + "/attack w.png");
		attackWTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		attackW = new Animation(attackWTexture, 0, 0, width, height, columns, totalFrames, character.statsHandler.attackDelay);

		attackSeTexture = new Texture(mainFilePath + "/attack se.png");
		attackSeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		attackSe = new Animation(attackSeTexture, 0, 0, width, height, columns, totalFrames, character.statsHandler.attackDelay);

		attackSwTexture = new Texture(mainFilePath + "/attack sw.png");
		attackSwTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		attackSw = new Animation(attackSwTexture, 0, 0, width, height, columns, totalFrames, character.statsHandler.attackDelay);
	}

	private void initializeRunningAnimation(String mainFilePath, float animationDelay) {
		runNTexture = new Texture(mainFilePath + "/run n.png");
		runNTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		runN = new Animation(runNTexture, 0, 0, width, height, columns, totalFrames, animationDelay, PlayMode.LOOP);

		runETexture = new Texture(mainFilePath + "/run e.png");
		runETexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		runE = new Animation(runETexture, 0, 0, width, height, columns, totalFrames, animationDelay, PlayMode.LOOP);

		runNeTexture = new Texture(mainFilePath + "/run ne.png");
		runNeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		runNe = new Animation(runNeTexture, 0, 0, width, height, columns, totalFrames,
				animationDelay, PlayMode.LOOP);

		runNwTexture = new Texture(mainFilePath + "/run nw.png");
		runNwTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		runNw = new Animation(runNwTexture, 0, 0, width, height, columns, totalFrames,
				animationDelay, PlayMode.LOOP);

		runSTexture = new Texture(mainFilePath + "/run s.png");
		runSTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		runS = new Animation(runSTexture, 0, 0, width, height, columns, totalFrames,
				animationDelay, PlayMode.LOOP);

		runWTexture = new Texture(mainFilePath + "/run w.png");
		runWTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		runW = new Animation(runWTexture, 0, 0, width, height, columns, totalFrames,
				animationDelay, PlayMode.LOOP);

		runSeTexture = new Texture(mainFilePath + "/run se.png");
		runSeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		runSe = new Animation(runSeTexture, 0, 0, width, height, columns, totalFrames,
				animationDelay, PlayMode.LOOP);

		runSwTexture = new Texture(mainFilePath + "/run sw.png");
		runSwTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		runSw = new Animation(runSwTexture, 0, 0, width, height, columns, totalFrames,
				animationDelay, PlayMode.LOOP);
	}

	private void initializeTipOverAnimation(String mainFilePath, float animationDelay) {
		tipOverNTexture = new Texture(mainFilePath + "/tip over n.png");
		tipOverNTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tipOverN = new Animation(tipOverNTexture, 0, 0, width, height, columns, totalFrames, animationDelay);

		tipOverETexture = new Texture(mainFilePath + "/tip over e.png");
		tipOverETexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tipOverE = new Animation(tipOverETexture, 0, 0, width, height, columns, totalFrames, animationDelay);

		tipOverNeTexture = new Texture(mainFilePath + "/tip over ne.png");
		tipOverNeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tipOverNe = new Animation(tipOverNeTexture, 0, 0, width, height, columns, totalFrames, animationDelay);

		tipOverNwTexture = new Texture(mainFilePath + "/tip over nw.png");
		tipOverNwTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tipOverNw = new Animation(tipOverNwTexture, 0, 0, width, height, columns, totalFrames, animationDelay);

		tipOverSTexture = new Texture(mainFilePath + "/tip over s.png");
		tipOverSTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tipOverS = new Animation(tipOverSTexture, 0, 0, width, height, columns, totalFrames, animationDelay);

		tipOverWTexture = new Texture(mainFilePath + "/tip over w.png");
		tipOverWTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tipOverW = new Animation(tipOverWTexture, 0, 0, width, height, columns, totalFrames, animationDelay);

		tipOverSwTexture = new Texture(mainFilePath + "/tip over sw.png");
		tipOverSwTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tipOverSw = new Animation(tipOverSwTexture, 0, 0, width, height, columns, totalFrames, animationDelay);

		tipOverSeTexture = new Texture(mainFilePath + "/tip over se.png");
		tipOverSeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tipOverSe = new Animation(tipOverSeTexture, 0, 0, width, height, columns, totalFrames, animationDelay);
	}

	private void handleAttackAnimationDelay() {
		if (character.statsHandler.attackDelay > 0.6f) {
			setAttackAnimationDuration(0.5f);
		} else {
			setAttackAnimationDuration(character.statsHandler.attackDelay);
		}
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		if (character.alive) {
			// A quick fix for the attack animation
			if (character.atkSpdUpdated) {
				character.atkSpdUpdated = false;
				handleAttackAnimationDelay();
			}

			switch (character.currentAction) {
			case STANDING:
				if (character.attackHandler.attacking) {
					drawAttacking(spriteBatch);
				} else {
					drawStanding(spriteBatch);
				}
				break;
			default:
				drawRunning(spriteBatch);
				break;
			}

		// If it is not alive
		} else {

			handleTipOverDrawing(spriteBatch);
		}

	}

	private void handleTipOverDrawing(SpriteBatch batch) {
		// Update once for playing tipping over
		if (updateTipOverSprite) {
			updateTipOverSprite = false;
			setPlayTippingOver();
		}

		// Oh no, not a uniform coding, will figure out the better coding style later
		if (tipOverStateTime > tipOverE.animationDuration) {
			tweenManager.update(Gdx.graphics.getDeltaTime());
			tipOverSprite.draw(batch);
		} else {
			drawTippingOver(batch);
		}
	}

	private void drawRunning(SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		Rectangle bounds = character.getBounds();
		// Just update when the character is moving to prevent sudden change of animation
		if (character.isMoving()) {
			moveX = character.moveIndicatorX;
			moveY = character.moveIndicatorY;
		}

		// character is moving east
		if (moveX > 0) {

			// Moving north east
			if (moveY > 0) {
				batch.draw(runNe.getKeyFrame(stateTime), bounds.x - 1.50f, bounds.y - 1, 4, 4);
			} else if (moveY < 0) {
				batch.draw(runSe.getKeyFrame(stateTime), bounds.x - 1.50f, bounds.y - 1, 4, 4);
			} else {
				batch.draw(runE.getKeyFrame(stateTime), bounds.x - 1.50f, bounds.y - 1, 4, 4);
			}
			// Moving west
		} else if (moveX < 0) {
			// Moving north
			if (moveY > 0) {
				batch.draw(runNw.getKeyFrame(stateTime), bounds.x - 1.50f, bounds.y - 1, 4, 4);
			} else if (moveY < 0) {
				batch.draw(runSw.getKeyFrame(stateTime), bounds.x - 1.50f, bounds.y - 1, 4, 4);
			} else {
				batch.draw(runW.getKeyFrame(stateTime), bounds.x - 1.50f, bounds.y - 1, 4, 4);
			}
		} else {
			// Moving north
			if (moveY > 0) {
				batch.draw(runN.getKeyFrame(stateTime), bounds.x - 1.50f, bounds.y - 1, 4, 4);
			} else if (moveY < 0) {
				batch.draw(runS.getKeyFrame(stateTime), bounds.x - 1.50f, bounds.y - 1, 4, 4);
			}
		}
	}

	private void drawTippingOver(SpriteBatch batch) {
		Vector2 pos = character.getPosition();
		tipOverStateTime += Gdx.graphics.getDeltaTime();
		switch ((int) character.rotation) {
		case 0:
			batch.draw(tipOverE.getKeyFrame(tipOverStateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 45:
			batch.draw(tipOverNe.getKeyFrame(tipOverStateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 90:
			batch.draw(tipOverN.getKeyFrame(tipOverStateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 135:
			batch.draw(tipOverNw.getKeyFrame(tipOverStateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 180:
			batch.draw(tipOverW.getKeyFrame(tipOverStateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 225:
			batch.draw(tipOverSw.getKeyFrame(tipOverStateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 270:
			batch.draw(tipOverS.getKeyFrame(tipOverStateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 315:
			batch.draw(tipOverSe.getKeyFrame(tipOverStateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		}
	}

	private void drawAttacking(SpriteBatch batch) {
		Vector2 pos = character.getPosition();
		float stateTime = character.attackAnimationHandler.stateTime;

		switch ((int) character.rotation) {
		case 0:
			batch.draw(attackE.getKeyFrame(stateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 45:
			batch.draw(attackNe.getKeyFrame(stateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 90:
			batch.draw(attackN.getKeyFrame(stateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 135:
			batch.draw(attackNw.getKeyFrame(stateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 180:
			batch.draw(attackW.getKeyFrame(stateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 225:
			batch.draw(attackSw.getKeyFrame(stateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 270:
			batch.draw(attackS.getKeyFrame(stateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		case 315:
			batch.draw(attackSe.getKeyFrame(stateTime), pos.x - 1.50f, pos.y - 1, 4, 4);
			break;
		}

	}

	private void drawStanding(SpriteBatch batch) {
		Vector2 pos = character.getPosition();
		switch ((int) character.rotation) {
		case 0:
			batch.draw(standing, pos.x - 1.50f, pos.y - 1, 4, 4, 0, 0, width, height, false, false);
			break;
		case 45:
			batch.draw(standing, pos.x - 1.50f, pos.y - 1, 4, 4, width * 2, 0, width, height, false, false);
			break;
		case 90:
			batch.draw(standing, pos.x - 1.50f, pos.y - 1, 4, 4, width, 0, width, height, false, false);
			break;
		case 135:
			batch.draw(standing, pos.x - 1.50f, pos.y - 1, 4, 4, width * 3, 0, width, height, false, false);
			break;
		case 180:
			batch.draw(standing, pos.x - 1.50f, pos.y - 1, 4, 4, width * 3, height, width, height, false, false);
			break;
		case 225:
			batch.draw(standing, pos.x - 1.50f, pos.y - 1, 4, 4, width * 2, height, width, height, false, false);
			break;
		case 270:
			batch.draw(standing, pos.x - 1.50f, pos.y - 1, 4, 4, 0, height, width, height, false, false);
			break;
		case 315:
			batch.draw(standing, pos.x - 1.50f, pos.y - 1, 4, 4, width, height, width, height, false, false);
			break;
		default:
				break;
		}
	}

	public void setAttackAnimationDuration(float animationDuration) {
		attackN.setAnimationDuration(animationDuration);
		attackE.setAnimationDuration(animationDuration);
		attackNe.setAnimationDuration(animationDuration);
		attackNw.setAnimationDuration(animationDuration);
		attackW.setAnimationDuration(animationDuration);
		attackS.setAnimationDuration(animationDuration);
		attackSe.setAnimationDuration(animationDuration);
		attackSw.setAnimationDuration(animationDuration);
	}

	public void setHitanimationDelay(float animationDuration) {
		hitE.setAnimationDuration(animationDuration);
		hitN.setAnimationDuration(animationDuration);
		hitNe.setAnimationDuration(animationDuration);
		hitNw.setAnimationDuration(animationDuration);
		hitW.setAnimationDuration(animationDuration);
		hitS.setAnimationDuration(animationDuration);
		hitSe.setAnimationDuration(animationDuration);
		hitSw.setAnimationDuration(animationDuration);
	}

	public void setRunninganimationDelay(float animationDuration) {
		runE.setAnimationDuration(animationDuration);
		runN.setAnimationDuration(animationDuration);
		runNe.setAnimationDuration(animationDuration);
		runNw.setAnimationDuration(animationDuration);
		runW.setAnimationDuration(animationDuration);
		runS.setAnimationDuration(animationDuration);
		runSe.setAnimationDuration(animationDuration);
		runSw.setAnimationDuration(animationDuration);
	}

	private void setPlayTippingOver() {
		switch ((int) character.rotation) {
		case 0:
			tipOverSprite = new Sprite(tipOverE.getRegions()[11]);
			break;
		case 45:
			tipOverSprite = new Sprite(tipOverNe.getRegions()[11]);
			break;
		case 90:
			tipOverSprite = new Sprite(tipOverN.getRegions()[11]);
			break;
		case 135:
			tipOverSprite = new Sprite(tipOverNw.getRegions()[11]);
			break;
		case 180:
			tipOverSprite = new Sprite(tipOverW.getRegions()[11]);
			break;
		case 225:
			tipOverSprite = new Sprite(tipOverSw.getRegions()[11]);
			break;
		case 270:
			tipOverSprite = new Sprite(tipOverS.getRegions()[11]);
			break;
		case 315:
			tipOverSprite = new Sprite(tipOverSe.getRegions()[11]);
			break;
		}

		tipOverSprite.setBounds(character.getPosition().x - 1.5f, character.getPosition().y - 1, 4, 4);

		// For fading of killed animation
		Tween.registerAccessor(Sprite.class, new SpriteTween());

		tweenManager = new TweenManager();

		TweenCallback cb = new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenCompleted();
			}

		};

		Tween.to(tipOverSprite, SpriteTween.ALPHA, 2.5f).target(0).ease(TweenEquations.easeInQuad).setCallback(cb).
		setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);

	}

	private void tweenCompleted() {
		// Nothing to do for now...

	}

	public void dispose() {
		attackETexture.dispose();
		attackNTexture.dispose();
		attackNeTexture.dispose();
		attackNwTexture.dispose();
		attackWTexture.dispose();
		attackSTexture.dispose();
		attackSeTexture.dispose();
		attackSwTexture.dispose();
		runNTexture.dispose();
		runETexture.dispose();
		runNeTexture.dispose();
		runNwTexture.dispose();
		runSTexture.dispose();
		runWTexture.dispose();
		runSeTexture.dispose();
		runSwTexture.dispose();
		tipOverETexture.dispose();
		tipOverNTexture.dispose();
		tipOverNeTexture.dispose();
		tipOverNwTexture.dispose();
		tipOverWTexture.dispose();
		tipOverSTexture.dispose();
		tipOverSeTexture.dispose();
		tipOverSwTexture.dispose();
	}

}
