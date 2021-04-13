/*package com.example.project_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;

public class AzureMLClient
{

    private endPointURL; //Azure ML Endpoint
    private key; //API KEY


    public AzureMLClient(String endPointURL,String key)
    {
        this.endPointURL= "https://ussouthcentral.services.azureml.net/workspaces/ab8e1cecc5f94c0bba414b2d9ea77445/services/199028c672844aacaa2faa5144e86f85/execute?api-version=2.0&details=true";
        this.key= "sX0v9tXPA1U9dz8w5tJx7mZGpHKH2KGi22ipejLnIxB28BInhO6ro3dKClZ1+dKWf/o+OWxJrUuc+TVby/CiLw==";
    }

     //Takes an Azure ML Request Body then Returns the Response String Which Contains Scored Lables etc

    public static String requestResponse( String requestBody ) throws Exception
    {
        URL u = new URL(this.endPointURL);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        conn.setRequestProperty("Authorization","Bearer "+ this.key);
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("POST");

        String body= new String(requestBody);

        conn.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());

        wr.write(body);
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String decodedString;
        String responseString="";

        while ((decodedString = in.readLine()) != null)
        {
            response+=decodedString;
        }
        return responseString;
    }

}azureMlclient {
}*/
