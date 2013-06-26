from xa.http.RequestParams import *
from xa.http.HttpToolKit import doGet


class XAService:

    def __init__(self, appid, uid = None):
        self.appid = appid
        self.uid = uid
        self.host = 'xa.xingcloud.com'

    def action(self, action, uid = None, timeout=10):
        return self.batch([action],uid,timeout)

    def update(self, update, uid = None, timeout=10):
        return self.batch([update],uid,timeout)

    def batch(self, operations,uid = None, timeout=10):
        requestParams = RequestParams(operations)
        url = self.getRequestUrl(requestParams,uid)
        return doGet(self.host,url,timeout)


    def batchWithTimestamp(self,operations, timestamp,uid = None, timeout=10):
        requestParams = RequestParams(operations)
        requestParams.addParam('timestamp',str(timestamp))
        url = self.getRequestUrl(requestParams,uid)
        return doGet(self.host,url,timeout)

    def batchWithAbsoluteTime(self, operations,absoluteTimestamp, uid=None, timeout = 10):
        requestParams = RequestParams(operations)
        requestParams.addParam('abs_ts',str(absoluteTimestamp))
        url = self.getRequestUrl(requestParams,uid)
        return doGet(self.host,url,timeout)

    def getRequestUrl(self,requestParams,uid):
        path = '/v4/' + self.appid + '/'
        if None == uid:
            path = path +  str(self.uid)
        else:
            path = path +  str(uid)
        return path + '?' + requestParams.toQueryString()
