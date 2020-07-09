package com.example.demo;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class DemoApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Test
	public void testDirectConnectivitySuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/connected?origin=Boston&destination=Newark";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		System.out.print(result.getBody());
		Assert.assertEquals(true, result.getBody().contains("yes"));
	}

	@Test
	public void testTransitiveConnectivitySuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/connected?origin=Newark&destination=NewYork";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		System.out.print(result.getBody());
		Assert.assertEquals(true, result.getBody().contains("yes"));
	}
	
	@Test
	public void testConnectivityFail() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/connected?origin=Philadelphia&destination=Albany";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		System.out.print(result.getBody());
		Assert.assertEquals(true, result.getBody().contains("no"));
	}
	
	@Test
	public void onCompleteFailure() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/connected?origin=Philadelphia";
		URI uri = new URI(baseUrl);
		try {
			ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		}catch(Exception e) {
			 Assert.assertTrue(e instanceof BadRequest );
		}
		
	}	
}
