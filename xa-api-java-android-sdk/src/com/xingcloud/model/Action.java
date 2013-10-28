package com.xingcloud.model;

/**
 * Created with IntelliJ IDEA.
 * User: witwolf
 * Date: 6/20/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class Action implements Operation {

    // event eventName
    private String eventName;
    // event value , default 0
    private long value = 0;
    // the time the event happend
    private long timestamp = -1;

    public Action(String eventName) {
        this.eventName = eventName;
    }

    public Action(String eventName, long value) {
        this.eventName = eventName;
        this.value = value;
    }

    public Action(String eventName, long value, long timestamp) {
        this.eventName = eventName;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private boolean checkNameVaild(String name) {

        return true;
    }

    @Override
    public String getOperationName(int index) {
        return "action" + index ;
    }

    @Override
    public String getOperationValue() {
        String operatorValue = eventName + "," + value ;
        if(timestamp > 0)
            operatorValue += "," + timestamp ;
        return operatorValue ;
    }
}
