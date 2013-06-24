//
//  Response.cpp
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/21/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#include "HttpToolKit.h"
#include <memory>

XA_API_BEGIN
Response::Response(){
    m_data = 0 ;
    m_dataSize = 0;
}
Response::~Response(){
    clearData();
}
void Response::clearData(){
    delete [] m_data;
    m_data = 0;
    m_dataSize = 0;
}
void Response::addData(const char *data,size_t size){
    if(!m_data || !m_dataSize){
        m_data = new char[size];
        memcpy(m_data,data,size);
        m_dataSize = size ;
    }else{
        char *ptr = (char*)realloc(m_data,m_dataSize + size);
        m_data = ptr ;
        ::memcpy(m_data + m_dataSize, data, size);
        m_dataSize += size ;
    }
}
void Response::setData(const char *data,size_t size){
    if(!size)
        return ;
    if(m_dataSize){
        clearData();
    }
    m_data = new char[size];
    ::memcpy(m_data, data, size);
    m_dataSize = size;
    
}
const char* Response::getData(){
    return m_data;
}
XA_API_END