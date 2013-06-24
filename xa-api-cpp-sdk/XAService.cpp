//
//  XAService.cpp
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/20/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#include "XAService.h"
#include "model/Operation.h"
XA_API_BEGIN

XAService::XAService(std::string appid,std::string uid):sAppid(appid),sUid(uid){
    pRequest = new HttpRequst;
}

XAService::~XAService(){
    delete pRequest ;
}

void XAService::action(const Action *action,
                       Response* response){
    RequestParam requestParam(action);
    pRequest->syncRequest(getRequestUrl(requestParam),response);
    
}

void XAService::action(const Action *action ,long timestamp,Response *response){
    RequestParam requestParam(action);
    char sTimestamp[20];
    sprintf(sTimestamp,"%ld",timestamp);
    requestParam.addParam("timestamp",sTimestamp);
    pRequest->syncRequest(getRequestUrl(requestParam), response);
}

void XAService::update(const Update * update,
                       Response * response){
    
    RequestParam requestParam(update);
    pRequest->syncRequest(getRequestUrl(requestParam), response);
}

void XAService::batch(const std::vector<Operation*> & operations,
                      Response* response){
    RequestParam requestParam(operations);
    pRequest->syncRequest(getRequestUrl(requestParam), response);
    
}

void XAService::batch(const std::vector<Operation*> & operations
           ,long timestamp,
                      Response* response) {
    RequestParam requestParam(operations);
    char sTimestamp[20];
    sprintf(sTimestamp,"%ld",timestamp);
    requestParam.addParam("timestamp", sTimestamp);
    pRequest->syncRequest(getRequestUrl(requestParam), response);
    
}

void XAService::batch(long absoluteTimestamp,
           const std::vector<Operation*> & operations,
                      Response *response){
    RequestParam requestParam(operations);
    char sAbsoluteTimestamp[20] ;
    sprintf(sAbsoluteTimestamp, "%ld",absoluteTimestamp);
    requestParam.addParam("abs_ts", sAbsoluteTimestamp);
    pRequest->syncRequest(getRequestUrl(requestParam), response);
    
}

std::string XAService::getRequestUrl(const RequestParam& requestParam){
    return "http://xa.xingcloud.com/v4/" + sAppid + "/" + sUid + "?" + requestParam.toQueryString();
    
}
XA_API_END
