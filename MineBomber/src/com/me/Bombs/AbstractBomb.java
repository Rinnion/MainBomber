package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Map.AbstractGameObject;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.ObjectMaskHelper.MaskController;

import java.util.Calendar;

/**
 * Created by tretyakov on 09.04.2014.
 */
public abstract class AbstractBomb extends AbstractGameObject implements IBomb {
    public long ActivationTime;
    public Vector2I[] ExplodeMask;

    public AbstractBomb(BombProperty property, Vector2 pos, AnimatedSprite animatedSprite) {
        super(pos, 1, animatedSprite);
        ExplodeMask = MaskController.GetMask(property.range);
        //ee.damage = property.dmgMin + (int)Math.random()*(property.dmgMax - property.dmgMin);
        ActivationTime = Calendar.getInstance().getTimeInMillis() + property.activationTime;
        Position = new Vector2(pos.x, pos.y);
    }


    public void receiveDamage(int dmg, long time) {
        ActivationTime = time;
    }
}
