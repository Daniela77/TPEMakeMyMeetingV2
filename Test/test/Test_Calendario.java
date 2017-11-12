package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Test_Calendario {
	
	public final String BASE_URL="http://localhost:8080/TPEMakeMyMeeting/api";

	public final HttpClient client = HttpClientBuilder.create().build();
	String token;
	@Test
	public void testUsuarioREST() throws ClientProtocolException, IOException {
		token = getToken();
		crearCalendarios();
		getCalendario();
		getCalendarios();
//		updateCalendario();
//		deleteCalendario();
	}
	private String getResultContent(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if(entity!=null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		}else {
			return "";
		}
	}

	public String getToken() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/autenticacion";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("userName", "laggis");
		jsonObject.put("password", "laggis1");
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		return resultContent;

	}
	public void crearCalendarios() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/calendarios";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Deportes");
		jsonObject.put("usuario", 1);
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Reuniones Laborales");
		jsonObject.put("usuario", 1);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Showroom");
		jsonObject.put("usuario", 1);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

	}

	public void getCalendario() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/calendarios/1";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void  getCalendarios() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/calendarios";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

//	public void updateCalendario() throws ClientProtocolException, IOException {
//
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectNode jsonObject = mapper.createObjectNode();
//		jsonObject.put("nombre", "TestJUnit Update");
//		jsonObject.put("duenio", 1);
//		String jsonString = jsonObject.toString();
//
//		String url = BASE_URL + "/calendarios/2";
//		HttpPut request = new HttpPut(url);
//		request.addHeader("Authorization", "Bearer-"+token+"");
//		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
//		HttpResponse response = client.execute(request);
//
//		System.out.println("\nPUT "+url);
//
//		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//
//		String resultContent = getResultContent(response);
//
//		System.out.println("Response Content : " + resultContent);
//
//	}

//	public void deleteCalendario() throws ClientProtocolException, IOException {
//
//		String url = BASE_URL + "/calendarios/1";
//
//		HttpDelete request = new HttpDelete(url);
//		request.addHeader("Authorization", "Bearer-"+token+"");
//
//		HttpResponse response = client.execute(request);
//
//		System.out.println("\nDELETE "+url);
//
//		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//
//		String resultContent = getResultContent(response);
//
//		System.out.println("Response Content : " + resultContent);
//
//	}

}
