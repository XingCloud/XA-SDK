from xa.model.Action import *
from xa.model.Update import *
from xa.XAService import XAService
import time


def responseHandle(response):
     # TODO reponse handle
    print response
    '''
    {"stats":"error","time":"0.06 ms","message":"appid is not set"}
    {"stats":"error","time":"0.05 ms","message":"uid is not set"}
    {"stats":"ok","time":"0.37 ms","message":"store 2 action and 1 update "}
    '''


if __name__ == '__main__':
    appid = 'xademo'
    uid = '123a'
    xaService = XAService(appid,uid)

    visit = Action('visit');
    buy = Action('buy.fruit',5,long(time.time()))

    updateNation = Update('nation','USA')
    updateLevel = Update('level',1)

    try:
        # action
        response = xaService.action(visit,10)
        responseHandle(response)

        # update
        response = xaService.update(updateNation);
        responseHandle(response)

        # batch , one action & one update
        response = xaService.batch([visit,updateNation]);
        responseHandle(response)

        # batch with timestamp,two actions & one update
        # all timestamp in actions will be fixed by
        # used often when the client time is not accurate
        response = xaService.batchWithAbsoluteTime([buy,updateNation],long(time.time()));
        responseHandle(response)

        # batch with absolute timestamp,one action
        # all timestamp in actions will be ignored
        # and replaced by absoluteTimestamp
        # rarely used
        response = xaService.batchWithTimestamp([buy],1831339293);
        responseHandle(response)


    except Exception as e:
        print e
        # TODO exception handle
        # Network related
        raise



