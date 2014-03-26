package com.me.Players;

/**
 * Created by alekseev on 26.03.2014.
 */
public interface IListenerRegistrator {
    void setListener(IPlayerControlls listener);
    void removeListener(IPlayerControlls listener);
}
