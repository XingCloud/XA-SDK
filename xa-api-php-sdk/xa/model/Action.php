<?php
include_once '../../model/Operation.php' ;

class Action implements \Operation {
	
	var $eventName;
	var $value;
	var $timestamp;
	
	
	public function __construct($eventName,$value = null,$timestamp = null){
		$this->eventName = $eventName ;
		$this->value = $value;
		$this->timestamp = $timestamp;
	}
	
	public function getOperationValue() {
		return $this->eventName . ',' . $this->value . ',' . $this->timestamp ;
	}
	
	public function getOperationName($index) {
		return 'action' . $index ;
	}
}

?>