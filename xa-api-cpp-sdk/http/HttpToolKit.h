//
//  HttpToolKit.h
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/21/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#ifndef __xa_api_cpp_sdk__HttpToolKit__
#define __xa_api_cpp_sdk__HttpToolKit__

#include <iostream>
#include <string>
#include <map>
#include <vector>
#include "../curl/curl.h"
#include "../curl/easy.h"
#include "../model/Operation.h"
#include "../XA.h"

XA_API_BEGIN
// Requst parameters
class RequestParam{
public:
    RequestParam();
    RequestParam(const Operation*);
    RequestParam(const std::vector<Operation*> &operations);
    RequestParam& addParam(const std::string& key,const std::string& value);
    RequestParam& removeParam(const std::string &key);
    void setValue(const std::string &key,const std::string &value);

    std::string toQueryString() const;

private:
    std::map<std::string,std::string> params;
};

// Request response
class Response{
public:
    Response();
    ~Response();
    void clearData();
    void addData(const char *data,size_t size);
    void setData(const char *data,size_t size);
    const char *getData();
    
protected:
    char *m_data;
    size_t m_dataSize ;
    
};

// http get method request
class HttpRequst{
public:
    HttpRequst();
    ~HttpRequst();
    void syncRequest(const std::string &url,
                    Response *response);
protected:
    void * m_curl ;
    static size_t _receive(void *ptr, size_t size, size_t nmemb, void *userdata);
};
XA_API_END

#endif /* defined(__xa_api_cpp_sdk__HttpToolKit__) */
