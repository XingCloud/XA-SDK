//
//  Update.cpp
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/20/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#ifndef xa_api_cpp_sdk_Update_cpp
#define xa_api_cpp_sdk_Update_cpp

#include "Operation.h"
XA_API_BEGIN
Update::Update(std::string propertyName,std::string value):sPropertyName(propertyName),sValue(value){}

std::string Update::getOperationName(int index)const{
    char operationName[10];
    sprintf(operationName,"update%d",index);
    return operationName;
}
std::string Update::getOperationValue()const{
    return sPropertyName + "," + sValue;
}
XA_API_END


#endif
