package com.affe.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public abstract class BaseObservable implements Observable{

    // public abstract BaseObservable create();
    public abstract  void addListener(InvalidationListener invalidationListener);
    public abstract void removeListener(InvalidationListener listener);
    public abstract void notifyListeners();
}

