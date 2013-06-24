//
//  Operaction.h
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/20/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#ifndef xa_api_cpp_sdk_Operaction_h
#define xa_api_cpp_sdk_Operaction_h


#include <string>
#include "../XA.h"

XA_API_BEGIN

class Operation{
public:
    virtual std::string getOperationName(int index)const=0;
    virtual std::string getOperationValue()const= 0;
};

// Action
class Action:public Operation{
public:
    Action(std::string eventName,long value=0,long timestamp = -1);
    std::string getOperationName(int index)const;
    std::string getOperationValue()const;
    
private:
    std::string sEventName ;
    long lValue ;
    long lTimestamp ;
    
};

// Update
class Update : public Operation{
public:
    Update(std::string propertyName,std::string value);
    std::string getOperationName(int index)const;
    std::string getOperationValue()const;
    
private:
    std::string sPropertyName;
    std::string sValue;
};
XA_API_END

#endif
