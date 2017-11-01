package login;

import java.util.Hashtable;
import java.util.UUID;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


public class TokenHelper {
	static Hashtable<String,String> tokens = new Hashtable<String,String>();
	
	/**
	 * Genera un token al usuario que corresponde a un cierto userName
	 * @param userName del usuario
	 * @return token de autentificacion
	 */
	public static String generarToken(String userName){
		long minutes = System.currentTimeMillis() / 1000 / 60;
		String key = UUID.randomUUID().toString().toUpperCase() +
				"|" + userName +
				"|" + minutes;
	StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
		jasypt.setPassword(userName);
		String authenticationToken = jasypt.encrypt(key);
		return authenticationToken;
	}

	/**
	 * Setea el nuevo token en la hash que contiene los tokens de usuarios
	 * @param token Token a setear
	 * @param userName del usuario dueño del token
	 */
	public static void setToken(String token, String userName){
		tokens.put(token,userName);
	}

	/**
	 * Retorna si un token existe en la lista de tokens asignados a usuarios
	 * @param token Token a verificar existencia
	 * @return true en caso de existir token en la lista, false en caso contrario
	 */
	public static boolean isValidoToken(String token) {
		return tokens.containsKey(token);
	}

	/**
	 * Elimina el token de la lista de tokens asignados a usuarios
	 * @param token Token a eliminar
	 */
	public static void eliminarToken(String token){
			tokens.remove(token);
	}
	 
	 
	 
	}

