package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CityGraphService;
import com.sun.org.slf4j.internal.LoggerFactory;

@RestController
public class DistanceController {
	private static Log logger = LogFactory.getLog(DistanceController.class);
	
	@Autowired
	CityGraphService cityGraphService;
	
	@GetMapping(path = "/connected")	
	public ResponseEntity<String> getConnectivity(@RequestParam(required=true) String origin,@RequestParam(required=true) String destination ) {
		
		logger.info("getConnectivity method - begin");
		
		boolean flag = cityGraphService.isReachable(origin,destination);
		String response ="no";
		if(flag) {
			response ="yes";
		}
		logger.info("getConnectivity method - end");
		return ResponseEntity.ok(response);
	}

}