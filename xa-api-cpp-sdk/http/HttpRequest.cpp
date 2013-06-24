//
//  HttpRequest.cpp
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/21/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#include "HttpToolKit.h"
#include <assert.h>

XA_API_BEGIN
HttpRequst::HttpRequst(){
    m_curl = ::curl_easy_init();
    assert(m_curl);
}

HttpRequst::~HttpRequst(){
    ::curl_easy_cleanup(m_curl);
}

void HttpRequst::syncRequest(const std::string &url,
                                    Response *response){
    
    CURLcode res = ::curl_easy_setopt(m_curl, CURLOPT_URL, url.c_str());
	if(url.npos != url.find("https"))
	{
		// https
		::curl_easy_setopt(m_curl, CURLOPT_SSL_VERIFYPEER, 0L);
		::curl_easy_setopt(m_curl, CURLOPT_SSL_VERIFYHOST, 0L);
	}
    
	::curl_easy_setopt(m_curl, CURLOPT_WRITEFUNCTION, HttpRequst::_receive);
	::curl_easy_setopt(m_curl, CURLOPT_WRITEDATA, response);
    
	::curl_easy_setopt(m_curl, CURLOPT_FOLLOWLOCATION, 1L);
    
    res = ::curl_easy_perform(m_curl);
}

size_t HttpRequst::_receive(void *ptr, size_t size, size_t nmemb, void *userdata)
{
	Response *pResponse = (Response *)userdata;
	pResponse->addData((char *)ptr, nmemb);
    
	return nmemb * size;
}
XA_API_END