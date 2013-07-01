package com.xingcloud.http;

import com.xingcloud.model.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: witwolf
 * Date: 6/20/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestParams {

    private Map<String,String> params = new HashMap<String,String>() ;

    public RequestParams(Operation operation){
        params.put(operation.getOperationName(0),operation.getOperationValue());
    }

    public RequestParams(List<? extends Operation> operations){
        int count = 0;
        for(Operation operation:operations){
              params.put(operation.getOperationName(count++),operation.getOperationValue());
        }
    }

    public RequestParams(Map<String,Object> params){
        addParam(params);
    }

    public void addParam(Map<String,Object> params){
        for(Map.Entry<String,Object> entry:params.entrySet()){
            this.params.put(entry.getKey(),String.valueOf(entry.getValue()));
        }
    }

    public void addParam(String key,String value){
        params.put(key,value);
    }

    public void removeParam(String key){
        params.remove(key);
    }


    public String toQueryString(){
        boolean firstParam = true ;
        String queryString = "" ;
        for(Map.Entry<String,String> param : params.entrySet()){
            if(!firstParam){
                queryString += '&' ;
            }
            queryString += param.getKey() + '=' + param.getValue() ;
            firstParam = false;
        }
        return  queryString ;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
