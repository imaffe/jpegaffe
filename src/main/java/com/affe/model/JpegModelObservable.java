package com.affe.model;

import com.affe.interfaces.Observable;
import com.affe.interfaces.Observer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JpegModelObservable implements Observable {

    // factory using a static method
    private JpegModel jpeg;
    private List<Observer> observerList;

    public JpegModelObservable(File file){
        observerList = new ArrayList<Observer>();
        // TODO a factory here is more appropriate
        jpeg = new JpegModel(file);
    }

    // TODO do we need to pass a File to create JpegModel
    // TODO or let JpegModel control the file opening ?

    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    public void removeObserver(Observer observer){
        int i = observerList.indexOf(observer);
        if (i >= 0) {
            observerList.remove(i);
        }
    }

    public void notifyObservers(){
        for (int i = 0; i < observerList.size(); i++) {
            Observer observer = (Observer)observerList.get(i);
            observer.update(jpeg.getImage());
        }
    }


    // getters and setter
    public JpegModel getJpeg(){
        return jpeg;
    }

    public List<Observer> getObserverList(){
        return observerList;
    }

    public void setJpeg(JpegModel jpeg){
        this.jpeg = jpeg;
    }

    // TODO : how to implement onChange() ?
    // TODO : how to add modifiers ?
}
