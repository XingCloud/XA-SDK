package com.xingcloud;

import com.xingcloud.http.HttpToolkit;
import com.xingcloud.http.RequestParams;
import com.xingcloud.model.Action;
import com.xingcloud.model.Operation;
import com.xingcloud.model.Update;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: witwolf
 * Date: 6/20/13
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class XAService {
    private String appid;
    private String uid;
    /**
     *
     * @param appid
     */
    public XAService(String appid) {
        this.appid = appid;
    }
    /**
     * @param appid
     * @param uid
     */
    public XAService(String appid, String uid) {
        this.appid = appid;
        this.uid = uid;
    }
    /**
     *
     * @param uid
     * @param action
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String action(String uid,Action action, int timeout) throws IOException {
        return batch(uid,Arrays.asList(action), timeout);
    }

    /**
     *
     * @param action
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String action(Action action, int timeout) throws IOException {
        return action(uid, action, timeout);
    }

    /**
     * @param uid
     * @param action
     * @param timestamp
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String action(String uid,Action action, long timestamp, int timeout) throws IOException {
        return batch(uid, Arrays.asList(action), timestamp, timeout);
    }

    /**
     * @see XAService#action(String, com.xingcloud.model.Action, long, int)
     * @param action
     * @param timestamp
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String action(Action action, long timestamp, int timeout) throws IOException {
        return action(uid, action, timestamp, timeout);
    }

    /**
     * @param uid
     * @param update
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String update(String uid,Update update, int timeout) throws IOException {
        return batch(uid, Arrays.asList(update), timeout);
    }

    /**
     * @see XAService#update(String, com.xingcloud.model.Update, int)
     * @param update
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String update(Update update, int timeout) throws IOException {
        return update(uid, update, timeout);
    }

    /**
     * @param uid
     * @param operations
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String batch(String uid,List<? extends Operation> operations, int timeout) throws IOException {
        RequestParams requestParams = new RequestParams(operations);
        String requestUrl = getUrlPath(uid) + '?' + requestParams.toQueryString();
        return HttpToolkit.doGet(requestUrl, timeout);
    }

    /**
     * @see XAService#batch(String, java.util.List, int)
     * @param operations
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String batch(List<? extends Operation> operations, int timeout) throws IOException {
          return batch(uid,operations,timeout);
    }

    /**
     * batch  with current known timestamp  on the client side
     * all actions' accurate happen time will be corrected  on the server side
     * by the current time on the client side and time recorded in actions
     * often used in condition that the clock is not accurate on the client side
     *
     * @param uid
     * @param operations
     * @param timestamp  current time on the client side
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String batch(String uid,List<? extends Operation> operations, long timestamp, int timeout) throws IOException {
        RequestParams requestParams = new RequestParams(operations);
        requestParams.addParam("timestamp", String.valueOf(timestamp));
        String requestUrl = getUrlPath(uid) + '?' + requestParams.toQueryString();
        return HttpToolkit.doGet(requestUrl, timeout);
    }

    /**
     * @see XAService#batch(String, java.util.List, long, int)
     * @param operations
     * @param timestamp
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String batch(List<? extends Operation> operations, long timestamp, int timeout) throws IOException {
        return batch(uid,operations,timestamp,timeout);
    }

    /**
     * batch  with absolute timestamp
     * all timestamp recorded in actions will be ignored and  replaced by the absoluteTs
     * rarely used
     *
     * @param uid
     * @param absoluteTimestamp
     * @param operations
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String batch(String uid,long absoluteTimestamp, List<? extends Operation> operations, int timeout) throws IOException {
        RequestParams requestParams = new RequestParams(operations);
        requestParams.addParam("abs_ts", String.valueOf(absoluteTimestamp));
        String requestUrl = getUrlPath(uid) + '?' + requestParams.toQueryString();
        return HttpToolkit.doGet(requestUrl, timeout);
    }

    /**
     * @see XAService#batch(String, long, java.util.List, int)
     * @param absoluteTimestamp
     * @param operations
     * @param timeout
     * @return
     * @throws java.io.IOException
     */
    public String batch(long absoluteTimestamp, List<? extends Operation> operations, int timeout) throws IOException {
        return batch(uid,absoluteTimestamp,operations,timeout);
    }



    private String getUrlPath(String uid) {
        return "http://xa.xingcloud.com/v4/" + appid + "/" + uid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
