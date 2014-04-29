package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Map.MapManager;
import com.me.Particles.ParticleManager;
import com.me.Players.IPlayer;
import com.me.Utility.DelayTimer;


/**
 * Created by alekseev on 27.03.2014.
 */
public class DestBomb extends AbstractBomb {

    float dmgMax;
    float dmgMin;
    int detonateTime;
    boolean detonate;
    boolean active;
    BombProperty property;

    public DestBomb(IPlayer player, BombProperty property, Vector2 pos)
    {
        super(player, property, new Vector2(pos.x/MapManager.rowW, pos.y/MapManager.rowH), AnimatedSprite.Factory.Create("dst_bomb"));

        this.property=new BombProperty(property);
        property.active=false;
        detonate=false;
        detonateTime=property.activationTime;
        this.active=property.active;
        dmgMax=property.dmgMax;
        dmgMin=property.dmgMin;

        sprite.setPosition(pos.x-(sprite.getWidth()/2) ,pos.y-(sprite.getHeight ()/2));

    }

}
