<?php
include_once dirname(__FILE__) . '/../XAService.php';
include_once dirname(__FILE__) . '/../model/Action.php';
include_once dirname(__FILE__) . '/../model/Update.php';

$visit = new Action ( 'visit' );
$buyFruit = new Action ( 'buy.fruit', 1 );
$pay = new Action ( 'pay.*', 30, time () );
$heartbeat = new Action ( 'heartbeat.*.*', 1, time () );

$nation = new Update ( 'nation', 'USA' );
$platform = new Update ( 'platform', 'XA' );

$appid = 'xademo';
$uid = '123a';
$default_timeout = 10;
$service = new XAService ( $appid, $uid );

// action
$response = $service->action ( $visit );
responseHandle ( $response );

/*
 * action with current time on the client side see batchWithTimestamp
 */

$response = $service->actionWithTimestamp ( $pay, time () );
responseHandle ( $response );

// update
$response = $service->update ( $nation, $default_timeout );
responseHandle ( $response );

// batch
$response = $service->batch ( array (
		$pay,
		$nation 
) );
responseHandle ( $response );

/**
 * batch with current time on the client side
 * all actions' accurate happen time will be corrected on the server side
 * by the current time on the client side and time recorded in actions
 * often used in condition the the clock is not accurate on the client side
 */
$response = $service->batchWithTimestamp ( array (
		$pay,
		$heartbeat,
		$nation 
), time () );
responseHandle ( $response );

/**
 * batch with absolute timestamp
 * all timestamp recorded in actions will be ignored and replaced by the absoluteTs
 * rarely used
 */
$response = $service->batchWithAbsoluteTime ( array (
		$pay 
), time () - 86400 );
responseHandle ( $response );
function responseHandle($response) {
	// TODO
	// handle response here
	echo $response . "\n";
}