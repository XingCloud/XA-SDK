<?php
include_once 'http/RequestParams.php';
include_once 'http/HttpToolKit.php';
class XAService {
	var $appid;
	var $uid;
	
	/**
	 * 
	 * @param string $appid
	 * @param string $uid
	 */
	public function __construct($appid, $uid) {
		$this->appid = $appid;
		$this->uid = $uid;
	}
	
	/**
	 *
	 * @param Action $action        	
	 * @param number $timeout        	
	 */
	public function action(Action $action, $timeout = 10) {
		return $this->batch ( array (
				$action 
		), $timeout );
	}
	
	/**
	 * see batchWithTimestamp
	 * 
	 * @param Action $action        	
	 * @param number $timestamp        	
	 * @param number $timeout        	
	 */
	public function actionWithTimestamp(Action $action, $timestamp, $timeout = 10) {
		return $this->batchWithTimestamp ( array (
				$action 
		), $timestamp, $timeout );
	}
	
	/**
	 *
	 * @param Update $update        	
	 * @param number $timeout        	
	 * @return mixed
	 */
	public function update(Update $update, $timeout = 10) {
		return $this->batch ( array (
				$update 
		), $timeout );
	}
	
	/**
	 *
	 * @param array $operations        	
	 * @param number $timeout        	
	 * @return mixed, false or string
	 */
	public function batch(array $operations, $timeout = 10) {
		$requestParams = new RequestParams ( $operations );
		$requestUrl = $this->getUrlPath () . '?' . $requestParams->toQueryString ();
		return doGet ( $requestUrl, $timeout );
	}
	
	/**
	 * batch with current known timestamp on the client side
	 * all actions' accurate happen time will be corrected on the server side
	 * by the current time on the client side and time recorded in actions
	 * often used in condition the the clock is not accurate on the client side
	 * 
	 * @param array $operations        	
	 * @param number $timestamp        	
	 * @param number $timeout        	
	 * @return mixed,false or string
	 */
	public function batchWithTimestamp(array $operations, $timestamp, $timeout = 10) {
		$requestParams = new RequestParams ( $operations );
		$requestParams->addParam ( 'timestamp', $timestamp );
		$requestUrl = $this->getUrlPath () . '?' . $requestParams->toQueryString ();
		return doGet ( $requestUrl, $timeout );
	}
	
	/**
	 * batch with absolute timestamp
	 * all timestamp recorded in actions will be ignored and replaced by the absoluteTs
	 * rarely used
	 * 
	 * @param array $operations        	
	 * @param unknown $absoluteTimestamp        	
	 * @param number $timeout        	
	 * @return mixed,false or string
	 */
	public function batchWithAbsoluteTime(array $operations, $absoluteTimestamp, $timeout = 10) {
		$requestParams = new RequestParams ( $operations );
		$requestParams->addParam ( 'abs_ts', $absoluteTimestamp );
		$requestUrl = $this->getUrlPath () . '?' . $requestParams->toQueryString ();
		return doGet ( $requestUrl, $timeout );
	}
	private function getUrlPath() {
		return 'http://xa.xingcloud.com/v4/' . $this->appid . '/' . $this->uid;
	}
}

?>