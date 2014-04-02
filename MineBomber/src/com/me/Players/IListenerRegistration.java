package com.me.Players;

/**
 * Created by alekseev on 26.03.2014.
 */
public interface IListenerRegistration {

    void setListener(IPlayerControls listener);
    void removeListener(IPlayerControls listener);
}
