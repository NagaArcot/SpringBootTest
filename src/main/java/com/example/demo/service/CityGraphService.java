package com.example.demo.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CityGraphService {

	private Map<String, LinkedHashSet<String>> map = new HashMap<String, LinkedHashSet<String>>();

	public void addEdge(String node1, String node2) {
		LinkedHashSet<String> adjacent = map.get(node1);
		if (adjacent == null) {
			adjacent = new LinkedHashSet<String>();
			map.put(node1, adjacent);
		}
		adjacent.add(node2);
	}

	public LinkedList<String> adjacentNodes(String last) {
		LinkedHashSet<String> adjacent = map.get(last);
		if (adjacent == null) {
			return new LinkedList();
		}
		return new LinkedList<String>(adjacent);
	}

	@Bean(initMethod = "init")
	public void init() {

		String fileName = "cities.txt";

		// read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.forEach(line -> {
				String[] cities = line.split(",");
				addEdge(cities[0].trim().toUpperCase(), cities[1].trim().toUpperCase());
				addEdge(cities[1].trim().toUpperCase(), cities[0].trim().toUpperCase());
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Boolean isReachable(String s, String d) {
		LinkedList<String> visited = new LinkedList<String>();
		LinkedList<String> queue = new LinkedList<String>();
		s = s.toUpperCase();
		d = d.toUpperCase();
		visited.add(s);
		queue.add(s);
		while (queue.size() != 0) {
			s = queue.poll();
			LinkedList<String> nodes = adjacentNodes(s);
			if (nodes.contains(d)) {
				return true;
			} else {
				nodes.stream().forEach(node -> {
					if (!visited.contains(node)) {
						visited.add(node);
						queue.add(node);
					}
				});
			}			
		}
		return false;
	}
}