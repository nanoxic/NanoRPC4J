package com.nanoxic.nanorpc4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

abstract class HttpClient {

	private static String url;
	private static RestTemplate restTemplate = new RestTemplate();
	private static HttpHeaders headers = new HttpHeaders();

	static {
		headers.setContentType(MediaType.APPLICATION_JSON);
		url = "http://" + Node.getHostname() + ":" + Node.getPort();
	}

	public static <T extends RequestMessage, S extends ResponseMessage> ResponseMessage getResponse(T request,
			Class<S> response) {
		HttpEntity<T> requestMessage = new HttpEntity<T>(request, headers);
		S responeMessage = restTemplate.postForObject(url, requestMessage, response);
		return responeMessage;

	}

}
