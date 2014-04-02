package com.me.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.BombPlaser;
import com.me.Bombs.BombProperty;
import com.me.Bombs.BombType;
import com.me.logger.Log;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tretyakov on 21.03.2014.
 */
public class FightInputProcessor implements InputProcessor, IListenerRegistration {
    public static final long DELTA_TIME = 10;
    private static final long MULTIPLE_PRESS_DELTA_TIME = 50;
    private static final long ONE_PRESS_DELTA_TIME = 70;

    private IPlayerControls mListener;
    private boolean mDoublePress;
    private boolean mOnePress;
    private Rectangle mArea;

    @Override
    public void setListener(IPlayerControls listener) {
        this.mListener = listener;
    }

    @Override
    public void removeListener(IPlayerControls listener)
    {
        this.mListener = null;
    }


    class PointerInfo{
        public int downX;
        public int downY;
        public int upX;
        public int upY;
        public int index;
        public long downTime;
        public long upTime;
        public boolean onScreen;
    }

    PointerInfo[] pi = new PointerInfo[]{new PointerInfo(), new PointerInfo()};

   // public FightInputProcessor(IFightInputListener listener){
     //   setListener(listener);
    //}
    public FightInputProcessor(Rectangle area)
    {
        mArea = area;
        mListener= null;
        //Gdx.input.setInputProcessor(this);

    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {

            case Input.Keys.SPACE:
                    mListener.PlaceBomb();
                return true;
            case Input.Keys.TAB:
                mListener.DetonateBomb();
                return true;
        }
            return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Log.d(mArea.toString());
        Log.d(screenX + ", " + screenY);

        if (!mArea.contains(screenX, screenY)) return false;

        if (pi[0].onScreen == false ){
            updatePointerInfoDown(pi[0], screenX, screenY, pointer);
            setDoublePress();

            return true;
        }

        if (pi[1].onScreen == false ){
            updatePointerInfoDown(pi[1], screenX, screenY, pointer);
            setDoublePress();

            return true;
        }

        return false;
    }

    private void setDoublePress() {
        mDoublePress = (pi[0].onScreen == true && (pi[0].downTime - pi[1].downTime < MULTIPLE_PRESS_DELTA_TIME));
    }

    private void setOnePress(int x,int y) {
        mOnePress = ((pi[0].onScreen == true && pi[0].downX==x && pi[0].downY==y) &&( (pi[0].downTime - (new Date()).getTime())< ONE_PRESS_DELTA_TIME));
    }

    private void updatePointerInfoDown(PointerInfo pointerInfo, int screenX, int screenY, int pointer) {
        pointerInfo.index = pointer;
        pointerInfo.downX = screenX;
        pointerInfo.downY = screenY;
        pointerInfo.downTime = new Date().getTime();
        pointerInfo.onScreen = true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        long time = new Date().getTime();

        if (pi[0].onScreen && pi[0].index == pointer){
            setOnePress(screenX,screenY);
            processPointerInfoUp(pi[0], screenX, screenY, time);

            return true;
        }

        if (pi[1].onScreen && pi[1].index == pointer){
            processPointerInfoUp(pi[1], screenX, screenY, time);
            return true;
        }

        return false;
    }

    private void processPointerInfoUp(PointerInfo pointerInfo, int screenX, int screenY, long time) {
        if (mDoublePress){
            //TODO: send two button info
            mDoublePress = false;
            mListener.DetonateBomb();
            pointerInfo.onScreen = false;
            mOnePress=false;
            return;
        }

        if(mOnePress)
        {
            mOnePress=false;
            mListener.PlaceBomb();
            pointerInfo.onScreen = false;
            mDoublePress=false;
            return;
        }


        if (time - pointerInfo.downTime > DELTA_TIME) {
            if (mListener != null) {
                sendFixEvent(pointerInfo, screenX, screenY);
            }
        }
        pointerInfo.onScreen = false;
    }

    private void sendFixEvent(PointerInfo pointerInfo, int screenX, int screenY) {
        Vector2 nv = new Vector2(screenX - pointerInfo.downX, screenY - pointerInfo.downY);
        float d = (float)Math.sqrt(nv.x*nv.x+nv.y*nv.y);
        if (d != 0) {
            Vector2 v = new Vector2(nv.x / d, nv.y / d);
            if (mListener != null)
                mListener.ChangeMoveDirection(v);
            //for(int i=0;i<mListener.size();i++)
            //    mListener.get(i).ChangeMoveDeriction(v);


        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
