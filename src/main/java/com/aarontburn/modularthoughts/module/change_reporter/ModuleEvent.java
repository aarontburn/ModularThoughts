package com.aarontburn.modularthoughts.module.change_reporter;


public class ModuleEvent {

    private final String eventName;
    private final Object data;

    public ModuleEvent(final String theEventName,
                       final Object theData) {

        this.eventName = theEventName;
        this.data = theData;

    }

    public String getEventName() {
        return this.eventName;
    }

    public Object getData() {
        return this.data;

    }





}
