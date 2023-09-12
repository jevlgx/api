package com.stage.onlinecourse.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public interface OperationsRepository {
	
	public static void getRequest(String url, String username, String password) {
        try {
            // Création de l'URL de la requête GET
            URL urlObj = new URL(url);
            
            // Ouverture de la connexion
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            
            // Configuration de la méthode de requête
            connection.setRequestMethod("GET");
            
            // Ajout des informations d'authentification si nécessaire
            if (username != null && password != null) {
                String credentials = username + ":" + password;
                String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
                connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            }
            
            // Lecture de la réponse
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            reader.close();
            
            System.out.println(response.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void postRequest(String url, String userName, String password,String acceptedFormat, String contentType, String requestBody) {
		try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // Configuration de la requête
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", acceptedFormat);
            connection.setRequestProperty("Content-Type", contentType);

            // Authentification de base
            String authString = userName + ":" + password;
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(authString.getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

            // Envoi du corps de la requête
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();

            // Lecture de la réponse
            int responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Affichage de la réponse
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + response.toString());

            // Fermeture de la connexion
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static void deleteRequest(String url, String userName, String password, String acceptedFormat ) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("DELETE");
			con.setRequestProperty("accept", acceptedFormat);
			
			//ajout des parametres d'authentification
            String auth = userName + ":" + password;
            byte[] authBytes = auth.getBytes(StandardCharsets.UTF_8);
            String encodedAuth = Base64.getEncoder().encodeToString(authBytes);
            String authHeader = "Basic " + encodedAuth;
            con.setRequestProperty("Authorization", authHeader);
			
			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			System.out.println("Response: " + response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void putRequest(String url, String userName, String password, String acceptedFormat) {
		try {
            
            String authString = userName + ":" + password;
            String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("accept", acceptedFormat);
            conn.setRequestProperty("Authorization", "Basic " + encodedAuthString);

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Response: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
