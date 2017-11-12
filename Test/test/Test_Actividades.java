package test;

import org.testng.Assert;
import org.testng.annotations.Test;

//import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Test_Actividades {

	public final String BASE_URL="http://localhost:8080/TPEMakeMyMeeting/api";

	public Client client = Client.create();

	public String getToken(){

		String url = BASE_URL + "/autenticacion/";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("userName","laggis");
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
	
//	public Obj_Actividad(@JsonProperty("nombre")String nombre, @JsonProperty("duenio") int id_duenio,
//			 @JsonProperty("fechaInicio")String fechai, @JsonProperty("fechaFin") String fechaf,
//			 @JsonProperty("lugar")int sala, @JsonProperty("calendario") int calendario)

	@Test
	public void testCrearActividades() {

		String url = BASE_URL + "/actividades";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre","cumpleaños 30");
		jsonObject.put("duenio","laggis");
		jsonObject.put("fechaInicio","2017-11-19,20:00");
		jsonObject.put("fechaFin","2017-11-19,23:00");
		jsonObject.put("lugar","21");
		jsonObject.put("calendario","1");
		String jsonString = jsonObject.toString();

		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, jsonString);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 201);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre","asado");
		jsonObject.put("duenio","laggis");
		jsonObject.put("fechaInicio","2017-11-20,12:00");
		jsonObject.put("fechaFin","2017-11-20,15:00");
		jsonObject.put("lugar","21");
		jsonObject.put("calendario","1");
		jsonString = jsonObject.toString();
		webResource = client.resource(url);
		response = webResource.type("application/json").post(ClientResponse.class, jsonString);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 201);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre","viaje");
		jsonObject.put("duenio","laggis");
		jsonObject.put("fechaInicio","2017-12-22,10:00");
		jsonObject.put("fechaFin","2017-11-26,17:00");
		jsonObject.put("lugar","21");
		jsonObject.put("calendario","1");
		jsonString = jsonObject.toString();

		webResource = client.resource(url);
		response = webResource.type("application/json").post(ClientResponse.class, jsonString);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 201);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre","cena");
		jsonObject.put("duenio","laggis");
		jsonObject.put("fechaInicio","2017-11-22,21:00");
		jsonObject.put("fechaFin","2017-11-22,23:00");
		jsonObject.put("lugar","21");
		jsonObject.put("calendario","1");
		jsonString = jsonObject.toString();

		webResource = client.resource(url);
		response = webResource.type("application/json").post(ClientResponse.class, jsonString);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 201);

//		jsonObject = mapper.createObjectNode();
//		jsonObject.put("nombre","cena");
//		jsonObject.put("duenio","laggis");
//		jsonObject.put("fechaInicio","2017-11-22,21:00");
//		jsonObject.put("fechaFin","2017-11-22,23:00");
//		jsonObject.put("lugar","21");
//		jsonObject.put("calendario","1");
//		jsonObject.put("remember","0");
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
//		jsonObject.put("nombre","cena");
//		jsonObject.put("duenio","laggis");
//		jsonObject.put("fechaInicio","2017-11-22,21:00");
//		jsonObject.put("fechaFin","2017-11-22,23:00");
//		jsonObject.put("lugar","21");
//		jsonObject.put("calendario","1");
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
//		jsonObject.put("nombre","cena");
//		jsonObject.put("duenio","laggis");
//		jsonObject.put("fechaInicio","2017-11-22,21:00");
//		jsonObject.put("fechaFin","2017-11-22,23:00");
//		jsonObject.put("lugar","21");
//		jsonObject.put("calendario","1");
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
		//jsonObject.put("nombre","cena");
		//jsonObject.put("duenio","laggis");
		//jsonObject.put("fechaInicio","2017-11-22,21:00");
		//jsonObject.put("fechaFin","2017-11-22,23:00");
		//jsonObject.put("lugar","21");
		//jsonObject.put("calendario","1");
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
//		jsonObject.put("nombre","cena");
//		jsonObject.put("duenio","laggis");
//		jsonObject.put("fechaInicio","2017-11-22,21:00");
//		jsonObject.put("fechaFin","2017-11-22,23:00");
//		jsonObject.put("lugar","21");
//		jsonObject.put("calendario","1");
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
//		jsonObject.put("nombre","cena");
//		jsonObject.put("duenio","laggis");
//		jsonObject.put("fechaInicio","2017-11-22,21:00");
//		jsonObject.put("fechaFin","2017-11-22,23:00");
//		jsonObject.put("lugar","21");
//		jsonObject.put("calendario","1");
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

	@Test(dependsOnMethods= {"testCrearAct"})
	public void testGetActividad() {

		String token =getToken();
		String url = BASE_URL + "/actividades/1";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).accept("application/json").get(ClientResponse.class);

		System.out.println("\nGET "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 200);

	}

	@Test(dependsOnMethods= {"testCrearAct"})
	public void testGetActividades() {

		String token =getToken();
		String url = BASE_URL + "/actividades";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Bearer-"+token).accept("application/json").get(ClientResponse.class);

		System.out.println("\nGET "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 200);

	}

}
