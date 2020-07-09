package com.example.demo;

import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.service.CityGraphService;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoApplicationMockTest {
	@Mock
	private RestTemplate restTemplate;
	@LocalServerPort
	int randomServerPort;

	private static CityGraphService cityGraphService;

	@BeforeClass
	public static void onceExecutedBeforeAll() {
		cityGraphService = new CityGraphService();
		cityGraphService.init();
	}

	@Test
	public void testDirectConnectivitySuccess() throws URISyntaxException {
		String flag = "yes";
		Mockito.when(restTemplate.getForEntity(
				"http://localhost:" + randomServerPort + "/connected?origin=Boston&destination=Newark", String.class))
				.thenReturn(new ResponseEntity(flag, HttpStatus.OK));

		String response = cityGraphService.isReachable("Boston", "Newark") ? "yes" : "no";

		Assert.assertEquals(flag, response);
	}

	@Test
	public void testConnectivityFail() throws URISyntaxException {

		String flag = "no";
		Mockito.when(restTemplate.getForEntity(
				"http://localhost:" + randomServerPort + "/connected?origin=Philadelphia&destination=Albany",
				String.class)).thenReturn(new ResponseEntity(flag, HttpStatus.OK));

		String response = cityGraphService.isReachable("Philadelphia", "Albany") ? "yes" : "no";

		Assert.assertEquals(flag, response);

	}

}