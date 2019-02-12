package com.affe.model;

import javafx.beans.InvalidationListener;

public class JpegModelObservable extends BaseObservable{

    // factory using a static method
    JpegModel jpeg;
    public static BaseObservable create() {
        return null;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener listener){

    }

    @Override
    public void notifyListeners(){

    }

    // getters and setter
    // TODO : how to implement onChange() ?
    // TODO : how to add modifiers ?
}
