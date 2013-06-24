<?php
include_once '../../model/Operation.php' ;

class Update implements \Operation {
	
	var $propertyName;
	var $value ;
	
	public function __construct($propertyName,$value){
		$this->propertyName = $propertyName;
		$this->value = $value;
	}
	
	public function getOperationValue() {
		return $this->propertyName . ',' . $this->value;
	}
	
	public function getOperationName($index) {
		return 'update' . $index ;
	}
}

?>