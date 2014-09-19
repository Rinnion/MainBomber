package com.me.Players;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.logger.Log;


import java.util.Date;

public class FightInputProcessor implements InputProcessor, IListenerRegistration {
    public static final int DELTA_TAP = 150;
    public static final int DELTA_SWIPE_DISTANCE = 15;
    public static final int DELTA_DOUBLE_TAP = 150;
    private static final int DELTA_PAN_DISTANCE = 15; //px

    private IPlayerControls mListener;
    private boolean mDoublePress;
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
        public int curX;
        public int curY;
    }

    PointerInfo[] pi = new PointerInfo[]{new PointerInfo(), new PointerInfo()};

    public FightInputProcessor(Rectangle area)
    {
        mArea = area;
        mListener= null;
    }

    @Override
    public boolean keyDown(int keycode) {

        Vector2 v=new Vector2();
        switch (keycode) {

            case Input.Keys.SPACE:
                mListener.onTap();
                return true;
            case Input.Keys.TAB:
                mListener.onDoubleTap();
                return true;
            case Input.Keys.UP:
                v.y=-1;
                break;
            case Input.Keys.DOWN:
                v.y=1;
                break;


            case Input.Keys.LEFT:
                v.x=-1;
                break;
            case Input.Keys.RIGHT:
                v.x=1;
                break;
        }
        if((v.x!=0)||(v.y!=0))
        {
            mListener.onPan(v);
            return true;
        }return false;
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

        //check if this authorized controller
        if (!mArea.contains(screenX, screenY)) return false;

        if (!pi[0].onScreen){
            updatePointerInfoDown(pi[0], screenX, screenY, pointer);
            mDoublePress = pi[0].onScreen && pi[1].onScreen;
            return true;
        }

        if (!pi[1].onScreen){
            updatePointerInfoDown(pi[1], screenX, screenY, pointer);
            mDoublePress = pi[0].onScreen && pi[1].onScreen;
            return true;
        }

        return false;
    }

    private void updatePointerInfoDown(PointerInfo pointerInfo, int screenX, int screenY, int pointer) {
        pointerInfo.index = pointer;
        pointerInfo.downX = screenX;
        pointerInfo.downY = screenY;
        pointerInfo.downTime = new Date().getTime();
        pointerInfo.onScreen = true;
        updatePointerInfoDrag(pointerInfo, screenX, screenY);
    }

    private void updatePointerInfoUp(PointerInfo pointerInfo, int screenX, int screenY, int pointer) {
        pointerInfo.index = pointer;
        pointerInfo.upX = screenX;
        pointerInfo.upY = screenY;
        pointerInfo.upTime = new Date().getTime();
        pointerInfo.onScreen = false;
        updatePointerInfoDrag(pointerInfo, screenX, screenY);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        PointerInfo p1 = pi[0];
        PointerInfo p2 = pi[1];

        return (updatePointerInfo(screenX, screenY, pointer, p1, p2) ||
                updatePointerInfo(screenX, screenY, pointer, p2, p1));

    }

    private boolean updatePointerInfo(int screenX, int screenY, int pointer, PointerInfo p1, PointerInfo p2) {
        if (p1.onScreen && p1.index == pointer){
            updatePointerInfoUp(p1, screenX, screenY, pointer);
            if (mDoublePress){
                updatePointerInfoUp(p2, screenX, screenY, pointer);
                if (isDoubleTapTime(p1)) {
                    mListener.onDoubleTap();
                }else{
                    Vector2 middle = getMiddleVector();
                    Vector2 v = normalizeVector(middle);
                    mListener.onDoubleSwipe(v);
                }
                mDoublePress = false;
            }else{
                if (isTap(p1)) mListener.onTap();
                Vector2 v = normalizeVector(new Vector2(p1.upX - p2.downX, p1.upY - p2.downY));
                if (isSwipe(p1)) mListener.onSwipe(v);
            }

            return true;
        }
        return false;
    }

    private Vector2 getMiddleVector() {
        float downX = (pi[0].downX-pi[1].downX)/2f;
        float downY = (pi[0].downY-pi[1].downY)/2f;

        float curX = (pi[0].curX-pi[1].curX)/2f;
        float curY = (pi[0].curY-pi[1].curY)/2f;
        return new Vector2(curX - downX, curY - downY);
    }

    private boolean isSwipe(PointerInfo pointerInfo) {
        return (isSwipeDistance(pointerInfo) && isTapTime(pointerInfo));
    }

    private boolean isSwipeDistance(PointerInfo pointerInfo) {
        long dx = pointerInfo.upX - pointerInfo.downX;
        long dy = pointerInfo.upY - pointerInfo.downY;
        return dx*dx + dy*dy > DELTA_SWIPE_DISTANCE*DELTA_SWIPE_DISTANCE;
    }

    private boolean isTap(PointerInfo pointerInfo) {
        return (!isSwipeDistance(pointerInfo) && isTapTime(pointerInfo));
    }

    private boolean isTapTime(PointerInfo pointerInfo) {
        return (pointerInfo.upTime - pointerInfo.downTime) < DELTA_TAP;
    }

    private boolean isDoubleTapTime(PointerInfo pointerInfo) {
        return (pointerInfo.upTime - pointerInfo.downTime) < DELTA_DOUBLE_TAP;
    }

    private Vector2 normalizeVector(Vector2 nv) {
        float d = (float)Math.sqrt(nv.x*nv.x+nv.y*nv.y);
        Vector2 v;
        if (d != 0) {
         v = new Vector2(nv.x / d, nv.y / d);
        }
        else
        v = new Vector2(Vector2.Zero);
        return v;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pi[0].onScreen && pi[0].index == pointer && (!pi[1].onScreen || pi[0].downTime < pi[1].downTime)){
            if (updatePointerInfoDrag(pi[0], screenX, screenY)){
                Vector2 v = normalizeVector(new Vector2(screenX - pi[0].downX, screenY - pi[0].downY));
                if (mDoublePress) {
                    mListener.onDoublePan(v);
                }else{
                    mListener.onPan(v);
                }
            }
        }

        if (pi[1].onScreen && pi[1].index == pointer && (!pi[0].onScreen || pi[1].downTime < pi[0].downTime)){
            if (updatePointerInfoDrag(pi[1], screenX, screenY)) {
                Vector2 v = normalizeVector(new Vector2(screenX - pi[1].downX, screenY - pi[1].downY));
                if (mDoublePress) {
                    mListener.onDoublePan(v);
                } else {
                    mListener.onPan(v);
                }
            }
        }

        return false;
    }

    private boolean updatePointerInfoDrag(PointerInfo pointerInfo, int screenX, int screenY) {
        int dx = pointerInfo.curX - screenX;
        int dy = pointerInfo.curY - screenY;
        if (dx*dx + dy*dy < DELTA_PAN_DISTANCE) return false;
        pointerInfo.curX = screenX;        
        pointerInfo.curY = screenY;
        return true;
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
