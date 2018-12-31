package com.nanoxic.nanorpc4j;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanoxic.nanorpc4j.exceptions.ResponseException;

abstract class HttpClient {

	private static String url;
	private static HttpPost request;
	private static ObjectMapper mapper = new ObjectMapper();
	private static CloseableHttpClient client = HttpClients.createDefault();

	static {
		url = "http://" + Node.getHostname() + ":" + Node.getPort();
		request = new HttpPost(url);
		request.addHeader(HttpHeaders.ACCEPT, "application/json");
		request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
	}

	public static <T extends RequestMessage, S extends ResponseMessage> ResponseMessage getResponse(T requestObject,
			Class<S> responseClass) {
		S responeMessage = null;
		try {
			request.setEntity(new StringEntity(mapper.writeValueAsString(requestObject)));
			HttpEntity response = client.execute(request).getEntity();
			responeMessage = mapper.readValue(EntityUtils.toString(response, "UTF-8"), responseClass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (responeMessage.getError() != null)
			throw new ResponseException(responeMessage.getError());
		return responeMessage;
	}

}
