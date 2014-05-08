package com.me.Players;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.MaskController;
import com.me.ObjectMaskHelper.Vector2I;

/**
 * Created by tretyakov on 06.05.2014.
 */
public class AbstractPlayer implements IPlayer {
    private final Vector2I[] mask_go;
    private final Vector2I[] mask_dmg;
    protected LifeProgressBar mLifeProgressBar;
    protected String mName;
    float radiusDig=4.5f;
    float radiusGo=1.7f ;
    int digDmg=1;
    float playerSpd=0.040f;
    float playerSpeedPerFrame = 2f;
    float maxLife=100;
    float curLife=100;
    Vector2 v = new Vector2(0.7f,0.7f);
    boolean mDie=false;Sprite sprite;
    private float newX;
    private float newY;

    public AbstractPlayer(String mName) {
        this.mName = mName;
        mLifeProgressBar=new LifeProgressBar(this);
        mask_go = MaskController.GetMask(radiusGo);
        mask_dmg = MaskController.GetMask(radiusDig);
    }

    @Override
    public void Render(Batch batch) {
        mLifeProgressBar.Draw();
        if(mDie) { return; }

        sprite.draw(batch);
    }

    @Override
    public float GetDmgRadius() {
        return radiusDig;
    }

    @Override
    public float GetGoRadius() {
        return radiusGo;
    }

    @Override
    public int GetDigDmg() {
        return digDmg;
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
    public String getName() {
        return mName;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public LifeProgressBar GetLifeBar() {
        return mLifeProgressBar;
    }

    @Override
    public void DealDamage(int dmg) {

        //float dmg=bomb.GetProperty().dmgMin + (float)Math.random()*bomb.GetProperty().dmgMax;

        if(curLife-dmg<0) {
            curLife = 0;
            mDie=true;
        }
        else
            curLife-=dmg;
        mLifeProgressBar.DoItVisible();

    }

    @Override
    public float getX() {
        return sprite.getX();
    }

    @Override
    public float getY() {
        return sprite.getY();
    }

    @Override
    public float getH() {

        return sprite.getHeight();
    }

    @Override
    public float getW() {
        return sprite.getWidth() ;
    }

    void calculate(long time) {

        float xStep = (playerSpeedPerFrame * v.x);
        float yStep = (playerSpeedPerFrame * v.y);

        newX = (int) (sprite.getX() + sprite.getOriginX());
        newY = (int) (sprite.getY() + sprite.getOriginY());

        if (MapManager.isEmptyField((newX + xStep) / MapManager.rowW, (newY + yStep) / MapManager.rowH, mask_go)) {
            newX += xStep;
            newY += yStep;
            sprite.translate(xStep, yStep);

        }
        MapManager.addDigDamageToField(mask_dmg, digDmg, newX / MapManager.rowW, newY / MapManager.rowH);
    }
}
