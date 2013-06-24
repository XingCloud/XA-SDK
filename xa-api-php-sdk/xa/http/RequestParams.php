<?php
include_once '../model/Action.php';
include_once '../model/Update.php';
include_once '../model/Operation.php';

class RequestParams {
	
	var $requestParams;
	
	public function __construct($params) {
		$this->requestParams = array ();
		if (is_array ( $params )) {
			$operations = $params;
		} else {
			$operations = array (
					$params 
			);
		}
		$count = 0;
		foreach ( $operations as $operation ) {
			if (($operation instanceof Action) || ($operation instanceof Update)) {
				$this->requestParams [$operation->getOperationName ( $count ++ )] = $operation->getOperationValue ();
			}
		}
	}
	
	public function addParam($key, $value) {
		$this->requestParams [$key] = $value;
	}
	
	public function removeParam($key) {
		unset ( $this->requestParams [$key] );
	}
	
	public function toQueryString() {
		$first = true;
		foreach ( $this->requestParams as $key => $value ) {
			if (! $first) {
				$queryString .= '&';
			}
			$queryString .= $key . '=' . $value;
			$first = false;
		}
		return $queryString;
	}
}

?>