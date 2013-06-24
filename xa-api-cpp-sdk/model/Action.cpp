//
//  Action.cpp
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/20/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#ifndef xa_api_cpp_sdk_Action_cpp
#define xa_api_cpp_sdk_Action_cpp

#include "Operation.h"
XA_API_BEGIN
Action::Action(std::string eventName,long value,long timestamp):sEventName(eventName),lValue(value),lTimestamp(timestamp){}
    
std::string Action::getOperationName(int index)const{
    char operationName[10];
    sprintf(operationName,"action%d",index);
    return operationName;
    
}

std::string Action::getOperationValue()const{
    char operationValue[sEventName.size() + 40];
    if(lTimestamp>0)
        sprintf(operationValue,"%s,%ld,%ld",sEventName.c_str(),lValue,lTimestamp);
    else
        sprintf(operationValue, "%s,%ld",sEventName.c_str(),lValue);
    
    
    return operationValue;
}
XA_API_END

#endif
