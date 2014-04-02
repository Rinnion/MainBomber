package com.me.Bombs;

import com.me.Players.IPlayer;

/**
 * Created by alekseev on 31.03.2014.
 */
public class BombProperty {

    public int activationTime;
    public boolean active;
    public boolean dstActivation;
    public boolean visible;
    public float dmgMin;
    public float dmgMax;
    public float range;
    public int type;

    IPlayer owner;


    public  BombProperty(IPlayer owner,int type, int activationTime,float dmgMin,float dmgMax,float range)
    {
        this(owner,type, activationTime, dmgMin, dmgMax, range, true, false, true);


    }

    public  BombProperty(IPlayer owner,int type, int activationTime,float dmgMin,float dmgMax,float range,boolean active,boolean dstActivation,boolean visible)
    {
        this.activationTime=activationTime;
        this.active=active;
        this.dstActivation=dstActivation;
        this.visible=visible;
        this.dmgMax=dmgMax;
        this.dmgMin=dmgMin;
        this.type=type;
        this.range=range;
        this.owner=owner;
    }

    public BombProperty(BombProperty property)
    {
        this(property.owner,property.type,property.activationTime,property.dmgMin,property.dmgMax,property.range,property.active,property.dstActivation,property.visible);
    }


}
