//
//  XAService.h
//  xa-api-cpp-sdk
//
//  Created by witwolf on 6/20/13.
//  Copyright (c) 2013 witwolf. All rights reserved.
//

#ifndef __xa_api_cpp_sdk__XAService__
#define __xa_api_cpp_sdk__XAService__

#include <iostream>
#include <string>
#include <vector>
#include "http/HttpToolKit.h"

XA_API_BEGIN
#define DEFAULT_TIMEOUT 30
class Action;
class Update;
class Response;

class XAService{
public:
    XAService(std::string appid,std::string uid) ;
    ~XAService();
    
    /**
	 * see @ void batch(const std::vector<Operation*> & operations,Response* response);
	 * @param Action *action
	 * @param Response* response
	 */
    void action(const Action *action,
                Response* response);
    /**
	 * 
	 * @param Action *action
     * @param long timestamp
	 * @param Response* response
	 */
    void action(const Action *action ,long timestamp,Response *response);
    /**
	 *
	 * @param Update *update
	 * @param Response *response
	 */
    void update(const Update * update,
                Response * response);
    /**
	 *
	 * @param vector<Operation*>& operations
	 * @param Response* response
	 */
    void batch(const std::vector<Operation*> & operations,
               Response* response);
    /**
	 * batch with current known timestamp on the client side
	 * all actions' accurate happen time will be corrected on the server side
	 * by the current time on the client side and time recorded in actions
	 * often used in condition the the clock is not accurate on the client side
	 *
	 * @param vector<Operation*>& operations
	 * @param long timestamp
	 * @param Response *response
	 */
    void batch(const std::vector<Operation*> & operations
               ,long timestamp,
               Response* response) ;
    
    /**
	 * batch with absolute timestamp
	 * all timestamp recorded in actions will be ignored and replaced by the absoluteTs
	 * rarely used
	 *
	 * @param vector<Opeartion*> &operations
	 * @param long absoluteTimestamp
	 * @param Response *response
	 */
    void batch(long absoluteTimestamp,
               const std::vector<Operation*> &,
               Response *response);

private:
    std::string getRequestUrl(const RequestParam&);
    std::string sAppid ;
    std::string sUid ;
    HttpRequst * pRequest ;
};
XA_API_END
#endif /* defined(__xa_api_cpp_sdk__XAService__) */
