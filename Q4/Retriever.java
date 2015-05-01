/**
 * Author: Drake Lee
 * Date: May 1, 2015
 * Pulls from the given API and displays desired videos or articles given 
 * specified parameters. Utilizes the JSON formatting. 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Retriever {


  public static void main(String[] args) throws IOException, JSONException {
    Scanner scan = new Scanner(System.in);
    String type="";
    String url = "http://ign-apis.herokuapp.com";
    InputStream input = new URL("http://ign-apis.herokuapp.com/").openStream();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
      StringBuilder builder = new StringBuilder();
      int i;
      while ((i = reader.read()) != -1) {
        builder.append((char) i);
      }
      String result = builder.toString();
      JSONObject json = new JSONObject(result);
      System.out.println(json.getString("welcome_message"));
      System.out.println("Type in the exact endpoint you want (i.e. \"/videos\"):");
    
      for(int g = 0; g < json.getJSONArray("endpoints").length(); ++g){
        System.out.println(json.getJSONArray("endpoints").get(g));
      }
      System.out.print("Enter:");
      boolean valid = false;
      String response="";
      while(!valid){
        response = scan.nextLine();
        for(int g = 0; g < json.getJSONArray("endpoints").length(); ++g){
          if(json.getJSONArray("endpoints").getString(g).equals(response)){
            valid=true;
            break;
          }
        }
        if(!valid){
          System.out.print("Not a valid endpoint. Please try again:");
        }
      }
      type=response.substring(1);
      url+=response+"?";
      JSONObject params=json.getJSONObject("supportedParameters");
      Iterator<String> iter=params.keys();
      String sParam=iter.next();
      while(sParam!=null){
        url+=sParam+"=";
        System.out.println("Please enter the ");
        System.out.print(params.getString(sParam)+":");  
        if(!iter.hasNext()){
          url+=scan.nextLine();
          break;
        }else{
          url+=scan.nextLine()+"&";
          sParam=iter.next();
        }
      }
      System.out.println("Processing desired request...\n\n");
      
      input = new URL(url).openStream();
      try {
        reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
        builder = new StringBuilder();
        int k;
        while ((k = reader.read()) != -1) {
          builder.append((char) k);
        }
        result = builder.toString();
        json = new JSONObject(result);     
        System.out.println(type.toUpperCase() + ":");
        
        JSONArray query = json.getJSONArray("data");
        for(int r=0; r<query.length(); ++r){
          if(type.equals("articles")){
            System.out.println((r+1)+". "+query.getJSONObject(r).getJSONObject("metadata").getString("headline")+":");
            try{
              System.out.print("\t"+query.getJSONObject(r).getJSONObject("metadata").getString("subHeadline")+"\n");
            } catch (JSONException e) {
              
            }
          }
          else if(type.equals("videos")){
            System.out.println((r+1)+". "+query.getJSONObject(r).getJSONObject("metadata").getString("name")+":");
            try{
              System.out.print("\t"+query.getJSONObject(r).getJSONObject("metadata").getString("description")+"\n");
            } catch (JSONException e) {
              
            }          
          }
        }
        
      } finally {
        input.close();
      }
    } finally {
      input.close();
    }

  }
}