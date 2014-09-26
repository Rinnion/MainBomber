package com.me.Bombs.Activator;

import com.me.Bombs.AbstractBomb;

/**
 * Created by alekseev on 18.09.2014.
 */
public interface IActivator {
    void Register(long time);
    void Calculate(long time);
}