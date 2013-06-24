<?php
function doGet($requestUrl,$timeout){
	//echo "Request:" . $requestUrl . "\n" ;
	$ch = curl_init();
	curl_setopt($ch,CURLOPT_SSL_VERIFYHOST, 0);
	curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,0);
	curl_setopt($ch, CURLOPT_URL, $requestUrl);
	curl_setopt($ch,CURLOPT_TIMEOUT,$timeout);
	curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
	$result = curl_exec($ch);
	curl_close($ch);
	return $result;
}