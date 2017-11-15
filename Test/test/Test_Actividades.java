
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Test_Actividades {
	
	public final String BASE_URL="http://localhost:8080/TPEMakeMyMeeting/api";

	public final HttpClient client = HttpClientBuilder.create().build();
	String token;
	
	@Test
	public void testUsuarioREST() throws ClientProtocolException, IOException {
		
		
		token = getToken();
		crearActividades();
		getActividad();
		getActividades();
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
		jsonObject.put("username", "laggis");
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
	
	public void crearActividades() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/actividades";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre","cumpleaños 30");
		jsonObject.put("duenio",1);
		jsonObject.put("fechaInicio","2017-11-19,20:00");
		jsonObject.put("fechaFin","2017-11-19,23:00");
		jsonObject.put("lugar","4");
		jsonObject.put("calendario","5");
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer-"+token+"");
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);


	}

	public void getActividad() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/actividades/"+9;

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	public void  getActividades() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/actividades";

		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer-"+token+"");

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

}

//package test;
//
//import java.io.IOException;
//import org.apache.http.client.ClientProtocolException;
//import org.junit.Assert;
//import org.junit.Test;
////import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//
//import entidades.Actividad;

//
//public class Test_Actividades {
//
//	public final String BASE_URL="http://localhost:8080/TPEMakeMyMeeting/api";
//
//	public Client client = Client.create();
//	
//	String token;
//
//	private int idActividad;
//	
//	@Test
//	public void testActividadesREST() throws ClientProtocolException, IOException {
//		
//		
//		token = getToken();
//		testCrearActividades();
//		testGetActividad();
//		testGetActividades();
//	}
//
//	public String getToken(){
//
//		String url = BASE_URL + "/autenticacion/";
//
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectNode jsonObject = mapper.createObjectNode();
//		jsonObject.put("username","laggis");
//		jsonObject.put("password","laggis1");
//		String jsonString = jsonObject.toString();
//
//		WebResource webResource = client.resource(url);
//		ClientResponse response = webResource.type("application/json").post(ClientResponse.class,jsonString);
//
//		System.out.println("\nPOST "+url);
//		System.out.println("Response Code : " + response.getStatus());
//		String responseContent= response.getEntity(String.class);
//		System.out.println("Response Content : " + responseContent);	
//		return responseContent;
//
//	}
//	
////	public Obj_Actividad(@JsonProperty("nombre")String nombre, @JsonProperty("duenio") int id_duenio,
////			 @JsonProperty("fechaInicio")String fechai, @JsonProperty("fechaFin") String fechaf,
////			 @JsonProperty("lugar")int sala, @JsonProperty("calendario") int calendario)
//
//
//	public void testCrearActividades() {
//
//		String url = BASE_URL + "/actividades";
//
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectNode jsonObject = mapper.createObjectNode();
//		System.out.println("crear");
//		jsonObject.put("nombre","cumpleaños 30");
//		jsonObject.put("duenio","laggis");
//		jsonObject.put("fechaInicio","2017-11-19,20:00");
//		jsonObject.put("fechaFin","2017-11-19,23:00");
//		jsonObject.put("lugar","4");
//		jsonObject.put("calendario","5");
//		String jsonString = jsonObject.toString();
//		
//		WebResource webResource = client.resource(url);
//		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).type("application/json").post(ClientResponse.class, jsonString);
////		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, jsonString);
//		System.out.println("\nPOST "+url);
//		System.out.println("Response Code : " + response.getStatus());
//		Assert.assertEquals(response.getStatus(), 201);
//
////		Actividad actividad = response.getEntity(Actividad.class);
////		System.out.println("crear"+actividad.getNombre());
////		
////		this.idActividad=actividad.getId();
////		System.out.println("Response Content : " +actividad );
////		Assert.assertEquals(response.getStatus(), 201);
//		System.out.println("crearffff");
//		
//		
//
////		jsonObject = mapper.createObjectNode();
////		jsonObject.put("nombre","asado");
////		jsonObject.put("duenio","laggis");
////		jsonObject.put("fechaInicio","2017-11-20,12:00");
////		jsonObject.put("fechaFin","2017-11-20,15:00");
////		jsonObject.put("lugar","21");
////		jsonObject.put("calendario","5");
////		jsonString = jsonObject.toString();
////		webResource = client.resource(url);
////		response = webResource.header("Authorization", "Bearer-"+token).type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
////
////		jsonObject = mapper.createObjectNode();
////		jsonObject.put("nombre","viaje");
////		jsonObject.put("duenio","laggis");
////		jsonObject.put("fechaInicio","2017-12-22,10:00");
////		jsonObject.put("fechaFin","2017-11-26,17:00");
////		jsonObject.put("lugar","21");
////		jsonObject.put("calendario","5");
////		jsonString = jsonObject.toString();
////
////		webResource = client.resource(url);
////		response = webResource.header("Authorization", "Bearer-"+token).type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
////
////		jsonObject = mapper.createObjectNode();
////		jsonObject.put("nombre","cena");
////		jsonObject.put("duenio","laggis");
////		jsonObject.put("fechaInicio","2017-11-22,21:00");
////		jsonObject.put("fechaFin","2017-11-22,23:00");
////		jsonObject.put("lugar","21");
////		jsonObject.put("calendario","1");
////		jsonString = jsonObject.toString();
////
////		webResource = client.resource(url);
////		response = webResource.header("Authorization", "Bearer-"+token).type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
//
////		jsonObject = mapper.createObjectNode();
////		jsonObject.put("nombre","cena");
////		jsonObject.put("duenio","laggis");
////		jsonObject.put("fechaInicio","2017-11-22,21:00");
////		jsonObject.put("fechaFin","2017-11-22,23:00");
////		jsonObject.put("lugar","21");
////		jsonObject.put("calendario","1");
////		jsonObject.put("remember","0");
////		jsonString = jsonObject.toString();
////
////		webResource = client.resource(url);
////		response = webResource.type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
////
////		jsonObject = mapper.createObjectNode();
////		jsonObject.put("nombre","cena");
////		jsonObject.put("duenio","laggis");
////		jsonObject.put("fechaInicio","2017-11-22,21:00");
////		jsonObject.put("fechaFin","2017-11-22,23:00");
////		jsonObject.put("lugar","21");
////		jsonObject.put("calendario","1");
////		jsonString = jsonObject.toString();
////
////		webResource = client.resource(url);
////		response = webResource.type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
////
////		jsonObject = mapper.createObjectNode();
////		jsonObject.put("nombre","cena");
////		jsonObject.put("duenio","laggis");
////		jsonObject.put("fechaInicio","2017-11-22,21:00");
////		jsonObject.put("fechaFin","2017-11-22,23:00");
////		jsonObject.put("lugar","21");
////		jsonObject.put("calendario","1");
////		jsonString = jsonObject.toString();
////
////		webResource = client.resource(url);
////		response = webResource.type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
////
////		jsonObject = mapper.createObjectNode();
//		//jsonObject.put("nombre","cena");
//		//jsonObject.put("duenio","laggis");
//		//jsonObject.put("fechaInicio","2017-11-22,21:00");
//		//jsonObject.put("fechaFin","2017-11-22,23:00");
//		//jsonObject.put("lugar","21");
//		//jsonObject.put("calendario","1");
////		jsonString = jsonObject.toString();
////
////		webResource = client.resource(url);
////		response = webResource.type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
////
////		jsonObject = mapper.createObjectNode();
////		jsonObject.put("nombre","cena");
////		jsonObject.put("duenio","laggis");
////		jsonObject.put("fechaInicio","2017-11-22,21:00");
////		jsonObject.put("fechaFin","2017-11-22,23:00");
////		jsonObject.put("lugar","21");
////		jsonObject.put("calendario","1");
////		jsonString = jsonObject.toString();
////
////		webResource = client.resource(url);
////		response = webResource.type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
////
////		jsonObject = mapper.createObjectNode();
////		jsonObject.put("nombre","cena");
////		jsonObject.put("duenio","laggis");
////		jsonObject.put("fechaInicio","2017-11-22,21:00");
////		jsonObject.put("fechaFin","2017-11-22,23:00");
////		jsonObject.put("lugar","21");
////		jsonObject.put("calendario","1");
////		jsonString = jsonObject.toString();
////
////		webResource = client.resource(url);
////		response = webResource.type("application/json").post(ClientResponse.class, jsonString);
////
////		System.out.println("\nPOST "+url);
////		System.out.println("Response Code : " + response.getStatus());
////		System.out.println("Response Content : " + response.getEntity(String.class));
////		Assert.assertEquals(response.getStatus(), 201);
//	}
//
//
//	public void testGetActividad() {
//
//		String token =getToken();
//		String url = BASE_URL + "/actividades/"+idActividad;
//		WebResource webResource = client.resource(url);
//		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).accept("application/json").get(ClientResponse.class);
//
//		System.out.println("\nGET "+url);
//		System.out.println("Response Code : " + response.getStatus());
//		System.out.println("Response Content : " + response.getEntity(String.class));
//		Assert.assertEquals(response.getStatus(), 200);
//		System.out.println("getactivdad");
//
//	}
//
//	public void testGetActividades() {
//		System.out.println("test act");
//
//		String token =getToken();
//		String url = BASE_URL + "/actividades";
//		WebResource webResource = client.resource(url);
//		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).accept("application/json").get(ClientResponse.class);
//
//		System.out.println("\nGET "+url);
//		System.out.println("Response Code : " + response.getStatus());
//		System.out.println("Response Content : " + response.getEntity(String.class));
//		Assert.assertEquals(response.getStatus(), 200);
//
//	}
//
//}
