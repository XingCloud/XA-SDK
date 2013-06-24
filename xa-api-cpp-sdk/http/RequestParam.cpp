//
//  RequestParam.cpp
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/21/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#include "HttpToolKit.h"

XA_API_BEGIN
RequestParam::RequestParam(){
    
}

RequestParam::RequestParam(const Operation* operation){
    params[operation->getOperationName(0)] = operation->getOperationValue();
}

RequestParam::RequestParam(const std::vector<Operation*> &operations){
    std::vector<Operation*>::const_iterator it = operations.begin();
    int count = 0 ;
    for(;it!= operations.end();it++){
        params[(*it)->getOperationName(count++)] = (*it)->getOperationValue();
    }
}

RequestParam& RequestParam::addParam(const std::string& key,const std::string& value){
    params[key] = value;
    return *this ;
}

RequestParam& RequestParam::removeParam(const std::string &key){
    params.erase(params.find(key));
    return *this;
}

void RequestParam::setValue(const std::string &key,const std::string &value){
    params[key] = value;
}

std::string RequestParam::toQueryString()const{
    std::string rslt;
    std::map<std::string,std::string>::const_iterator it = params.begin();
    int count = 0 ;
    for(;it != params.end();it++){
        if(count != 0)
            rslt += "&";
        rslt += it->first + "=" + it->second ;
        count ++ ;
    }
    return rslt;
}
XA_API_END