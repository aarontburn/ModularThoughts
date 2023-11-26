package com.aarontburn.modularthoughts.module_builder.change_reporter;


import java.util.ArrayList;
import java.util.List;

public class ModuleChangeReporter {

    private final List<ModuleListener> listenerList = new ArrayList<>();

    public void addListener(final ModuleListener theListener) {
        listenerList.add(theListener);
    }

    public void notifyListeners(final String theEvent, final Object theData) {
        for (final ModuleListener listener : listenerList) {
            listener.eventFired(new ModuleEvent(theEvent, theData));
        }
    }





}
