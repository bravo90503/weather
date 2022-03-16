package com.mlb.homework.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HttpUtils {
	public static CloseableHttpClient client;

	@PostConstruct
	public void configureHttpClient() throws InterruptedException {
		client = createHttpClient();
	}

	@PreDestroy
	public void clean() {
		client.close(CloseMode.GRACEFUL);
	}

	/* httpclient start */
	final PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
			.setDefaultSocketConfig(SocketConfig.custom()//
					.setSoTimeout(Timeout.ofSeconds(5))//
					.build())//
			.setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)//
			.setConnPoolPolicy(PoolReusePolicy.LIFO)//
			.setConnectionTimeToLive(TimeValue.ofMinutes(1L))//
			.build();

	protected CloseableHttpClient createHttpClient() {
		return HttpClients.custom()//
				.setConnectionManager(connectionManager)//
				.setDefaultRequestConfig(RequestConfig.custom()//
						.setConnectTimeout(Timeout.ofSeconds(5))//
						.setResponseTimeout(Timeout.ofSeconds(5))//
						.setCookieSpec(StandardCookieSpec.STRICT)//
						.build())//
				.build();
	}

	public static ResponseEntity<String> getResponseEntity(CloseableHttpResponse response) throws IOException {
		InputStream is = response.getEntity().getContent();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		byte[] byteArray = buffer.toByteArray();
		String reply = new String(byteArray);
		int statusCode = response.getCode();

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(reply, HttpStatus.valueOf(statusCode));

		EntityUtils.consume(response.getEntity());
		response.close();
		return responseEntity;
	}


	public static boolean isSuccessful(ResponseEntity<String> responseEntity) {
		return (responseEntity.getStatusCode().value() >= 200 && responseEntity.getStatusCode().value() <= 299);
	}
}
