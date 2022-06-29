package com.xyrus.api;

import com.xyrus.api.services.PeerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	@Autowired
	PeerGroupService peerGroupService;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
