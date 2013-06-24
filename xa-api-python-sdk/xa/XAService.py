from xa.http.RequestParams import *
from xa.http.HttpToolKit import doGet


class XAService:

    def __init__(self, appid, uid):
        self.appid = appid
        self.uid = uid
        self.path = '/v4/' + appid + '/' + uid
        self.host = 'xa.xingcloud.com'

    def action(self, action, timeout=10):
        return self.batch([action],timeout)

    def update(self, update, timeout=10):
        return self.batch([update],timeout)

    def batch(self, operations, timeout=10):
        requestParams = RequestParams(operations)
        url = self.getRequestUrl(requestParams)
        return doGet(self.host,url,timeout)


    def batchWithTimestamp(self,operations, timestamp, timeout=10):
        requestParams = RequestParams(operations)
        requestParams.addParam('timestamp',str(timestamp))
        url = self.getRequestUrl(requestParams)
        return doGet(self.host,url,timeout)

    def batchWithAbsoluteTime(self, operations,absoluteTimestamp, timeout = 10):
        requestParams = RequestParams(operations)
        requestParams.addParam('abs_ts',str(absoluteTimestamp))
        url = self.getRequestUrl(requestParams)
        return doGet(self.host,url,timeout)

    def getRequestUrl(self,requestParams):
        return self.path + '?' + requestParams.toQueryString()
