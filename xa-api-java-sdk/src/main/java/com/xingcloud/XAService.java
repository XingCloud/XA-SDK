package com.xingcloud;

import com.xingcloud.http.RequestParams;
import com.xingcloud.model.Action;
import com.xingcloud.model.Operation;
import com.xingcloud.model.Update;
import com.xingcloud.http.HttpToolkit;

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
     * @param appid
     * @param uid
     */
    public XAService(String appid, String uid) {
        this.appid = appid;
        this.uid = uid;
    }

    /**
     * @param action
     * @param timeout
     * @return
     * @throws IOException
     */
    public String action(Action action, int timeout) throws IOException {
        return batch(Arrays.asList(action), timeout);
    }

    /**
     * @param action
     * @param timestamp
     * @param timeout
     * @return
     * @throws IOException
     */
    public String action(Action action, long timestamp, int timeout) throws IOException {
        return batch(Arrays.asList(action), timestamp, timeout);
    }

    /**
     * @param update
     * @param timeout
     * @return
     * @throws IOException
     */

    public String update(Update update, int timeout) throws IOException {
        return batch(Arrays.asList(update), timeout);
    }

    /**
     * @param operations
     * @param timeout
     * @return
     * @throws IOException
     */
    public String batch(List<? extends Operation> operations, int timeout) throws IOException {
        RequestParams requestParams = new RequestParams(operations);
        String requestUrl = getUrlPath() + '?' + requestParams.toQueryString();
        return HttpToolkit.doGet(requestUrl, timeout);
    }

    /**
     * batch  with current known timestamp  on the client side
     * all actions' accurate happen time will be corrected  on the server side
     * by the current time on the client side and time recorded in actions
     * often used in condition that the clock is not accurate on the client side
     *
     * @param operations
     * @param timestamp  current time on the client side
     * @param timeout
     * @return
     * @throws IOException
     */
    public String batch(List<? extends Operation> operations, long timestamp, int timeout) throws IOException {
        RequestParams requestParams = new RequestParams(operations);
        requestParams.addParam("timestamp", String.valueOf(timestamp));
        String requestUrl = getUrlPath() + '?' + requestParams.toQueryString();
        return HttpToolkit.doGet(requestUrl, timeout);
    }

    /**
     * batch  with absolute timestamp
     * all timestamp recorded in actions will be ignored and  replaced by the absoluteTs
     * rarely used
     *
     * @param absoluteTimestamp
     * @param operations
     * @param timeout
     * @return
     * @throws IOException
     */
    public String batch(long absoluteTimestamp, List<? extends Operation> operations, int timeout) throws IOException {
        RequestParams requestParams = new RequestParams(operations);
        requestParams.addParam("abs_ts", String.valueOf(absoluteTimestamp));
        String requestUrl = getUrlPath() + '?' + requestParams.toQueryString();
        return HttpToolkit.doGet(requestUrl, timeout);
    }

    private String getUrlPath() {
        return "http://xa.xingcloud.com/v4/" + appid + "/" + uid;
    }
}
