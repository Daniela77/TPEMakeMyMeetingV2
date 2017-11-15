package test;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import entidades.Sala;

public class Test_Sala {

	public final String BASE_URL="http://localhost:8080/TPEMakeMyMeeting/api";

	public Client client = Client.create();

	@Test
	public void test() {
		testCrearSalas();
		testGetSala();
		testGetSalas();
	}
	
	public String getToken(){

		String url = BASE_URL + "/autenticacion/";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("username","laggis");
		jsonObject.put("password","laggis1");
		String jsonString = jsonObject.toString();

		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class,jsonString);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		String responseContent= response.getEntity(String.class);
		System.out.println("Response Content : " + responseContent);	
		return responseContent;

	}

	private int idDeSala;
	
	public void testCrearSalas() {
		String token =getToken();

		String url = BASE_URL + "/salas";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre","Sala A");
		jsonObject.put("direccion","Montiel 777");
		String jsonString = jsonObject.toString();

		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).type("application/json").post(ClientResponse.class, jsonString);
		

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		Sala respuesta = response.getEntity(Sala.class);
		this.idDeSala=respuesta.getId();
		System.out.println("Response Content : " + respuesta);
		Assert.assertEquals(response.getStatus(), 201);

//		jsonObject = mapper.createObjectNode();
//		jsonObject.put("nombre","Sala A");
//		jsonObject.put("direccion","Montiel 777");
//		jsonString = jsonObject.toString();
//
//		webResource = client.resource(url);
//		response = webResource.type("application/json").post(ClientResponse.class, jsonString);
//
//		System.out.println("\nPOST "+url);
//		System.out.println("Response Code : " + response.getStatus());
//		System.out.println("Response Content : " + response.getEntity(String.class));
//		Assert.assertEquals(response.getStatus(), 201);
//
//		jsonObject = mapper.createObjectNode();
//		jsonObject.put("nombre","Sala A");
//		jsonObject.put("direccion","Montiel 777");
//		jsonString = jsonObject.toString();
//
//		webResource = client.resource(url);
//		response = webResource.type("application/json").post(ClientResponse.class, jsonString);
//
//		System.out.println("\nPOST "+url);
//		System.out.println("Response Code : " + response.getStatus());
//		System.out.println("Response Content : " + response.getEntity(String.class));
//		Assert.assertEquals(response.getStatus(), 201);

	}

//	@Test(dependsOnMethods= {"testCrearSalas"})
	public void testGetSala() {

		String token =getToken();
		String url = BASE_URL + "/salas/"+idDeSala;
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).accept("application/json").get(ClientResponse.class);

		System.out.println("\nGET "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 200);

	}

//	@Test(dependsOnMethods= {"testCrearSalas"})
	public void testGetSalas() {

		String token =getToken();
		String url = BASE_URL + "/salas";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).accept("application/json").get(ClientResponse.class);

		System.out.println("\nGET "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 200);

	}

//	@Test(dependsOnMethods= {"testCrearSalas"})
//	public void testUpdateSala() {
//
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectNode jsonObject = mapper.createObjectNode();
//		jsonObject.put("nombre","Sala A");
//	    jsonObject.put("direccion","Montiel 777");
//		String jsonString = jsonObject.toString();
//
//		String token =getToken();
//		String url = BASE_URL + "/salas/4";
//		WebResource webResource = client.resource(url);
//		ClientResponse response = webResource.header("Authorization", "Bearer-"+token+"").type("application/json").put(ClientResponse.class,jsonString);
//
//		System.out.println("\nPUT "+url);
//		System.out.println("Response Code : " + response.getStatus());
//		System.out.println("Response Content : " + response.getEntity(String.class));
//		Assert.assertEquals(response.getStatus(), 201);
//
//	}
//
//	@Test(dependsOnMethods= {"testCrearSalas"})
//	public void testDeleteSala() {
//
//		String token =getToken();
//		String url = BASE_URL + "/salas/21";
//		WebResource webResource = client.resource(url);
//		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).delete(ClientResponse.class);
//
//		System.out.println("\nDELETE "+url);
//		System.out.println("Response Code : " + response.getStatus());
//		if(response.hasEntity())
//			System.out.println("Response Content : " + response.getEntity(String.class));
//		else
//			System.out.println("Response Content : NO CONTENT");
//		Assert.assertTrue(response.getStatus() == 200);
//
//	}
}

