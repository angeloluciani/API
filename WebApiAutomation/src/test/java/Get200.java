import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Get200 {

	public static final String BASE_ENDPOINT = "https://api.github.com";
	// HttpClient client = HttpClientBuilder.create().build();

	CloseableHttpClient client;
	CloseableHttpResponse response;

	@BeforeEach
	public void setup() {
		client = HttpClientBuilder.create().build();
	}

	@Test
	public void baseUrlReturns200() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(BASE_ENDPOINT);
		response = client.execute(get);

		int actualStatus = response.getStatusLine().getStatusCode();
		System.out.println(actualStatus);
		Assert.assertEquals(actualStatus, 200);
	}

	@Test
	public void rateLimitReturn200() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(BASE_ENDPOINT + "/search/repositories?q=java");
		response = client.execute(get);

		int actualStatus = response.getStatusLine().getStatusCode();
		System.out.println(actualStatus);
		Assert.assertEquals(actualStatus, 200);
	}

	@Test
	public void nonExistingEndPoint404() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(BASE_ENDPOINT + "/plutarco");
		response = client.execute(get);

		int actualStatus = response.getStatusLine().getStatusCode();
		System.out.println(actualStatus);
		Assert.assertEquals(actualStatus, 404);
	}

	@Test
	public void userReturns401() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(BASE_ENDPOINT + "/user");
		response = client.execute(get);

		int actualStatus = response.getStatusLine().getStatusCode();
		System.out.println(actualStatus);
		Assert.assertEquals(actualStatus, 401);
	}

	@Test
	public void userFollowers401() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(BASE_ENDPOINT + "/user/followers");
		response = client.execute(get);

		int actualStatus = response.getStatusLine().getStatusCode();
		System.out.println(actualStatus);
		Assert.assertEquals(actualStatus, 401);
	}

	@Test
	public void usernotification401() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(BASE_ENDPOINT + "/notifications");
		response = client.execute(get);

		int actualStatus = response.getStatusLine().getStatusCode();
		System.out.println(actualStatus);
		Assert.assertEquals(actualStatus, 401);
	}

	@Test
	public void contentTypeIsJson() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(BASE_ENDPOINT);
		response = client.execute(get);

		int actualStatus = response.getStatusLine().getStatusCode();
		System.out.println(actualStatus);
		Assert.assertEquals(actualStatus, 200);

		Header contentType = response.getEntity().getContentType();
		Assert.assertEquals(contentType.getValue(), "application/json; charset=utf-8");

		ContentType ct = ContentType.getOrDefault(response.getEntity());
		Assert.assertEquals(ct.getMimeType(), "application/json");

	}

	@Test
	public void serverIsGithub() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(BASE_ENDPOINT);
		response = client.execute(get);

		int actualStatus = response.getStatusLine().getStatusCode();
		System.out.println(actualStatus);
		Assert.assertEquals(actualStatus, 200);

		String headerValue = getHeader(response, "Server");
	}

	private String getHeader(CloseableHttpResponse response, String headerName) {
		Header[] headers = response.getAllHeaders();
		return headerName;

	}

	@AfterEach
	public void closeresources() throws IOException {
		client.close();
		response.close();

	}
}
