package com.me.Players;

import com.badlogic.gdx.graphics.Color;
import com.me.Graphics.ILifeProgressBar;
import com.me.Utility.DelayTimer;

/**
 * Created by alekseev on 02.04.2014.
 */
public class LifeProgressBar implements ILifeProgressBar {

    float mAddIndex = 0.1f;
    private IPlayer player;
    private Color mColorProgressBack;
    private Color mColorProgressFront;
    private boolean mVisible = true;

    private DelayTimer mFlashTimer = new DelayTimer(100);
    private DelayTimer mVisibleTimer = new DelayTimer(2000, false);

    private boolean mBeginDisableVisible = false;


    public LifeProgressBar(IPlayer player) {
        this.player = player;
        setDefaultColor();
    }

    private void setDefaultColor() {

        mColorProgressBack = new Color(Color.RED);
        mColorProgressFront = new Color(Color.GREEN);
    }

    @Override
    public Color colorBack() {
        return mColorProgressBack;
    }

    @Override
    public Color colorFront() {
        return mColorProgressFront;
    }

    @Override
    public boolean visibleLifeBar() {
        return mVisible;
    }


    @Override
    public void Draw() {
        if (!mVisible)
            return;

        if (mBeginDisableVisible) {
            if (mVisibleTimer.CheckTimeOut())
                mBeginDisableVisible = false;

            return;
        }


        if (mFlashTimer.CheckTimeOut()) {

            if (mColorProgressBack.a + mAddIndex <= 0) {
                //if(mVisibleTimer.CheckTimeOut())
                mVisible = false;

                return;
            }

            if (mColorProgressBack.a + mAddIndex >= 1) {
                //mVisible=false;
                mAddIndex = -0.1f;
                mBeginDisableVisible = true;
                mVisibleTimer.Restart();
                return;
            }

            mColorProgressBack.a += mAddIndex;
            mColorProgressFront.a += mAddIndex;


            mFlashTimer.Reset();
        }
    }

    @Override
    public void DoItVisible() {
        mAddIndex = 0.1f;
        mBeginDisableVisible = false;
        mFlashTimer.Reset();

        mVisible = true;


    }
}
