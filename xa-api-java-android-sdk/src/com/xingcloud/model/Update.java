package com.xingcloud.model;

/**
 * Created with IntelliJ IDEA.
 * User: witwolf
 * Date: 6/20/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class Update implements Operation {
    public String propertyName;
    public Object value;

    public Update(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String getOperationName(int index) {
        return "update" + index ;
    }

    @Override
    public String getOperationValue() {
        return propertyName + "," + value ;
    }

}
