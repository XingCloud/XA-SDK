import httplib


def doGet(host, url, timeout):
    conn = httplib.HTTPConnection(host,timeout=timeout)
    conn.request('GET', url)
    response = conn.getresponse()
    data = response.read()
    conn.close()
    return data
