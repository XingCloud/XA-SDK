//
//  Sample.cpp
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/20/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#ifndef xa_api_cpp_sdk_Sample_cpp
#define xa_api_cpp_sdk_Sample_cpp

#include <iostream>
#include <string>
#include <vector>
#include <time.h>
#include "XAService.h"
#include "model/Operation.h"

using namespace std;
using namespace xa;
void responseHandle(Response * response);
int main(int argc,char **argv){
    string appid = "xademo" ;
    string uid = "123a";
    
    XAService xaService(appid,uid);
    
    Action *visit = new Action("visit");
    Action *buyFruit = new Action("buy.fruit",5,time(NULL));
    Action *pay = new Action("pay.item",10,time(NULL));
    
    Update *updateNation = new Update("nation","USA");
    Update *updateLevel = new Update("level","10");
    
    vector<Operation*> operations;
    operations.push_back(buyFruit);
    operations.push_back(pay);
    operations.push_back(updateNation);
    
    
    Response response;
    
    // one action
    xaService.action(visit, &response);
    responseHandle(&response);
    response.clearData();
    
    // one update
    xaService.update(updateLevel, &response);
    responseHandle(&response);
    response.clearData();
    
    // batch action & update
    xaService.batch(operations,&response);
    responseHandle(&response);
    response.clearData();
    
    // batch acion & update with timestamp
    xaService.batch(operations,time(NULL),&response);
    responseHandle(&response);
    response.clearData();
    
    // batch action & update with absolute timestamp
    xaService.batch(time(NULL),operations,&response);
    responseHandle(&response);
    response.clearData();
    
    delete visit;
    delete buyFruit;
    delete pay;
    delete updateNation;
    delete updateLevel;
}

void responseHandle(Response * response){
    // TODO
    cout << response->getData() << endl;
}


#endif
