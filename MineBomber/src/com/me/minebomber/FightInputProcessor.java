package com.me.minebomber;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.Date;

/**
 * Created by tretyakov on 21.03.2014.
 */
public class FightInputProcessor implements InputProcessor {
    public static final long DELTA_TIME = 10;
    private static final long MULTIPLE_PRESS_DELTA_TIME = 20;
    private IFightInputListener mListener;
    private boolean mDoublePress;

    public void setListener(IFightInputListener mListener) {
        this.mListener = mListener;
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

    public FightInputProcessor(IFightInputListener listener){
        setListener(listener);
    }

    @Override
    public boolean keyDown(int keycode) {
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
            processPointerInfoUp(pi[0], screenX, screenY, time);
        }

        if (pi[1].onScreen && pi[1].index == pointer){
            processPointerInfoUp(pi[1], screenX, screenY, time);
        }

        return true;
    }

    private void processPointerInfoUp(PointerInfo pointerInfo, int screenX, int screenY, long time) {
        if (mDoublePress){
            //TODO: send two button info
        }
        if (time - pointerInfo.downTime > DELTA_TIME) {
            if (mListener != null) {
                sendFixEvent(pointerInfo, screenX, screenY);
            }
        }
        pointerInfo.onScreen = false;
        mDoublePress = false;
    }

    private void sendFixEvent(PointerInfo pointerInfo, int screenX, int screenY) {
        Vector2 nv = new Vector2(screenX - pointerInfo.downX, screenY - pointerInfo.downY);
        float d = (float)Math.sqrt(nv.x*nv.x+nv.y*nv.y);
        if (d != 0) {
            Vector2 v = new Vector2(nv.x / d, nv.y / d);
            mListener.onFix(v);
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
