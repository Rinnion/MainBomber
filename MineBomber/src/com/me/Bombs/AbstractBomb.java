package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Map.AbstractGameObject;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.ObjectMaskHelper.MaskController;
import com.me.Players.IPlayer;

import java.util.Calendar;

/**
 * Created by tretyakov on 09.04.2014.
 */
public abstract class AbstractBomb extends AbstractGameObject {
    public final BombProperty Property;
    public long ActivationTime;
    public Vector2IDamage[] ExplodeMask;

    public AbstractBomb(IPlayer player, BombProperty property, Vector2 pos, AnimatedSprite animatedSprite) {
        //FIXME life from bomb
        super(player, pos, 1, animatedSprite);
        Vector2I[] vector2Is = MaskController.GetMask(property.range);
        ExplodeMask = new Vector2IDamage[vector2Is.length];
        for (int i = 0; i < vector2Is.length; i++) {
            int damage = property.dmgMin + (int)Math.random()*(property.dmgMax - property.dmgMin);
            ExplodeMask[i] = new Vector2IDamage(vector2Is[i], damage);
        }
        ActivationTime = Calendar.getInstance().getTimeInMillis() + property.activationTime;
        Property = property;
    }

    public void receiveDamage(int dmg, long time) { detonate(time); }

    public void detonate(long time) {ActivationTime = time;}
}
