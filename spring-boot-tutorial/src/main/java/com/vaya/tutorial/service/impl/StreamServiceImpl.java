package com.vaya.tutorial.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vaya.tutorial.service.core.StreamService;

@Service
public class StreamServiceImpl implements StreamService {

	@Override
	public boolean streamMatch(List<Integer> input) {
		// TODO Auto-generated method stub

		boolean answer = input.stream().anyMatch(n -> (n * (n + 1)) / 4 == 5);
		System.out.println(answer);
		
		return answer;

	}

}
