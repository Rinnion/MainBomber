package com.me.Players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.MaskController;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.TextManager.TextManager;
import com.me.bomb.AbstractBomb;
import com.me.bomb.AnimatedSpriteAnimator;
import com.me.bomb.activator.DestinationActivator;

import java.util.ArrayList;

/**
 * Created by tretyakov on 06.05.2014.
 */
public abstract class AbstractPlayer implements IPlayer {

    public static final int MAX_ACTIVATORS = 2500;
    private static final boolean playerDebug = false;
    private static final int DEF_BOMB_COUNT = 20;
    static ArrayList<DestinationActivator> activators = new ArrayList<>(MAX_ACTIVATORS);
    public final Vector2I[] mask_view;
    protected final ArrayList<AbstractBomb> bombList;
    private final Vector2I[] mask_go;
    private final Vector2I[] mask_dmg;
    public float radiusView = 15;
    public AnimatedSpriteAnimator sprite;
    public float X;
    public float Y;
    public float oX;
    public float oY;
    protected LifeProgressBar mLifeProgressBar;
    protected String mName;
    Arsenal arsenal;
    ArsenalInfo arsenalInfo;
    float radiusDig=4.5f;
    float radiusGo=1.7f ;
    int digDmg=5;
    float playerSpd=0.040f;
    float playerSpeedPerFrame = 2f;
    float maxLife=10000;
    float curLife=10000;
    Vector2 v = new Vector2(0.7f,0.7f);
    boolean mDie=false;
    ShapeRenderer shapeRenderer;
    private float newX;
    private float newY;

    public AbstractPlayer(String mName) {
        this.mName = mName;
        arsenal=new Arsenal();
        mLifeProgressBar=new LifeProgressBar(this);
        mask_go = MaskController.GetMask(radiusGo);
        mask_dmg = MaskController.GetMask(radiusDig);
        mask_view = MaskController.GetMask(radiusView);
        bombList=new ArrayList<>(DEF_BOMB_COUNT);

        if(playerDebug)
        shapeRenderer=new ShapeRenderer();
        arsenalInfo=new ArsenalInfo(this);

    }

    @Override
    public void Render(Batch batch) {
        mLifeProgressBar.Draw();
        if (mDie) {
            return;
        }

        /*
        if(playerDebug) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(new Color(255, 255, 255, 100));
            shapeRenderer.circle(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY(), radiusDig * 2);
            shapeRenderer.circle(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY(), radiusGo * 2);

            shapeRenderer.end();
            batch.enableBlending();


            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(255, 0, 0, 100));
            for (Vector2I tmpI : mask_dmg) {
                shapeRenderer.rect((tmpI.x * MapManager.rowW) + sprite.getX() + sprite.getOriginX(), (tmpI.y * MapManager.rowH) + sprite.getY() + sprite.getOriginY(), 2, 2);
            }
            shapeRenderer.setColor(new Color(0, 255, 0, 100));
            for (Vector2I tmpI : mask_go) {
                shapeRenderer.rect((tmpI.x * MapManager.rowW) + sprite.getX() + sprite.getOriginX(), (tmpI.y * MapManager.rowH) + sprite.getY() + sprite.getOriginY(), 2, 2);
            }

            shapeRenderer.end();
        }
        */

        //TextureRegion region= animationSprite.getKeyFrame(Gdx.graphics.getDeltaTime());
        //region.flip(false,true);

        //sprite.setRegion(region);

        sprite.draw(batch, X, Y);
        if (arsenalInfo.isVisible)
            arsenalInfo.Render(batch);
    }

    @Override
    public float GetMaxLife() {
        return maxLife;
    }

    @Override
    public float GetLife() {
        return curLife;
    }

    @Override
    public LifeProgressBar GetLifeBar() {
        return mLifeProgressBar;
    }

    @Override
    public void DealDamage(int dmg) {

        if(mDie)
            return;

        if(curLife-dmg<0) {
            curLife = 0;
            mDie=true;
        }
        else
            curLife-=dmg;
        if(dmg!=0) {

            TextManager.Add("-"+ dmg, Color.PINK, X, Y);
            mLifeProgressBar.DoItVisible();
        }

    }

    @Override
    public void activateBombs(long time) {
        for (DestinationActivator act : activators) {
            act.Activate(time);
        }
    }

    @Override
    public void addActivator(DestinationActivator activator) {
        activators.add(activator);
    }

    public Arsenal GetArsenal() {
        return arsenal;
    }

    @Override
    public void addMoney(long value) {

    }

    @Override
    public Arsenal getArsenal() {
        return arsenal;
    }

    void calculate(long time) {
        if(mDie) return;

        float xStep = (playerSpeedPerFrame * v.x);
        float yStep = (playerSpeedPerFrame * v.y);

        newX = X;
        newY = Y;

        MapManager.beginRedrawViewMask(mask_view, newX / MapManager.rowW, newY / MapManager.rowH);

        if (MapManager.isEmptyField((newX + xStep) / MapManager.rowW, (newY + yStep) / MapManager.rowH, mask_go)) {
            newX += xStep;
            newY += yStep;
            X += xStep;
            Y += yStep;
            MapManager.collect(this, mask_go, newX / MapManager.rowW, newY / MapManager.rowH, time);
        }

        MapManager.addDigDamageToField(mask_dmg, digDmg, newX / MapManager.rowW, newY / MapManager.rowH);



    }
}
