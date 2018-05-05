package es.lost2found.database;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.lost2found.entities.Place;
import es.lost2found.entities.TransportPlace;

public class DB_transportPlace {

    // Server: jcorreas-hp.fdi.ucm.es
    private static String SERVER_PATH = "http://jcorreas-hp.fdi.ucm.es/lost2found/database/place/transport/";

    public static TransportPlace getTransportPlace(String lineText, String stationText) {
        TransportPlace ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lineText", lineText);
            jsonObject.put("stationText", stationText);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getTransportPlaceIdByLineStationJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try {
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "your user agent");
                con.setRequestProperty("Accept-Language", "sp-SP,sp;q=0.5");

                String urlParameters = "json=" + jsonString;

                con.setDoOutput(true);
                OutputStream outstream = con.getOutputStream();
                DataOutputStream wr = new DataOutputStream(outstream);
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                InputStream instream;

                int status = con.getResponseCode();

                if (status != HttpURLConnection.HTTP_OK)
                    instream = con.getErrorStream();
                else
                    instream = con.getInputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                in.close();

                JSONObject object = new JSONObject(response.toString());

                ret = new TransportPlace(response.toString());
            } finally {
                con.disconnect();
            }
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String[] getLines(String transport) {
        String[] lines = new String[13];
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tipoTte", transport);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getLinesByTypeTteJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try {
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "your user agent");
                con.setRequestProperty("Accept-Language", "sp-SP,sp;q=0.5");

                String urlParameters = "json=" + jsonString;

                con.setDoOutput(true);
                OutputStream outstream = con.getOutputStream();
                DataOutputStream wr = new DataOutputStream(outstream);
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                InputStream instream;

                int status = con.getResponseCode();

                if (status != HttpURLConnection.HTTP_OK)
                    instream = con.getErrorStream();
                else
                    instream = con.getInputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                lines = response.toString().split(",");
                for(int i = 0; i < lines.length; i++) {
                    if (lines[i].contains("\"")) {
                        String text = lines[i].replace("\"", "");
                        lines[i] = text;
                    }
                }
                String text = lines[0].replace("[", "");
                lines[0] = text;
                String text2 = lines[lines.length-1].replace("]", "");
                lines[lines.length-1] = text2;
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String[] getStations(String linea) {
        String[] stations = new String[13];
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("linea", linea);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getStationsByLineJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try {
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "your user agent");
                con.setRequestProperty("Accept-Language", "sp-SP,sp;q=0.5");

                String urlParameters = "json=" + jsonString;

                con.setDoOutput(true);
                OutputStream outstream = con.getOutputStream();
                DataOutputStream wr = new DataOutputStream(outstream);
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                InputStream instream;

                int status = con.getResponseCode();

                if (status != HttpURLConnection.HTTP_OK)
                    instream = con.getErrorStream();
                else
                    instream = con.getInputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                stations = response.toString().split(",");
                for(int i = 0; i < stations.length; i++) {
                    if (stations[i].contains("\"")) {
                        String text = stations[i].replace("\"", "");
                        stations[i] = text;
                    }
                }
                String text = stations[0].replace("[", "");
                stations[0] = text;
                String text2 = stations[stations.length-1].replace("]", "");
                stations[stations.length-1] = text2;
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }

    public static String[] getTrainStations() {
        String OPEN_DATA_URL = "https://data.sncf.com/api/records/1.0/search//?dataset=objets-trouves-gares&rows=0&facet=gc_obo_gare_origine_r_name";
        String[] stations = new String[12];
        String[] realStations = new String[13];

        int timeout = 5000;
        URL url = null;
        HttpURLConnection connection = null;
        JSONObject object = null;
        InputStream instream = null;
        try {
            url = new URL(OPEN_DATA_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.connect();

            int status = connection.getResponseCode();

            if (status != HttpURLConnection.HTTP_OK)
                instream = connection.getErrorStream();
            else
                instream = connection.getInputStream();

            instream = connection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(instream));
            String temp, response = "";
            while((temp = bReader.readLine()) != null) {
                response += temp;
            }
            stations = response.split("path"); //  + "\"" +  "\\:" + "\""
            for(int i = 1; i < 13; i++) {
                if (stations[i].contains("\"")) {
                    String text = stations[i].replace("\"", "");
                    stations[i] = text;
                }
                if (stations[i].contains(":")) {
                    String text = stations[i].replace(":", "");
                    stations[i] = text;
                }
            }
            for(int i = 0; i < 12; i++) {
                realStations[i] = stations[i + 1].substring(0, stations[i + 1].indexOf(","));
                realStations[i] = realStations[i].substring(1, realStations[i].length());
            }
            realStations[12] = "Otra"; // Comprobar
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(instream != null) {
                try {
                    instream.close();
                } catch(IOException ignored) {
                }
            }
            if(connection != null) {
                connection.disconnect();
            }
        }
        return realStations;
    }

    public static TransportPlace insertTransportTrainPlace(String estacion) {
        Place newPlace = DB_place.insertPlace();
        String placeId = String.valueOf(newPlace.getId());
        String tipoTte = "tren";
        String linea = null;
        TransportPlace trainPlace = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("placeId", placeId);
            jsonObject.put("tipoTte", tipoTte);
            jsonObject.put("linea", linea);
            jsonObject.put("estacion", estacion);


            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "insertTrainStationJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try {
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "your user agent");
                con.setRequestProperty("Accept-Language", "sp-SP,sp;q=0.5");

                String urlParameters = "json=" + jsonString;

                con.setDoOutput(true);
                OutputStream outstream = con.getOutputStream();
                DataOutputStream wr = new DataOutputStream(outstream);
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                InputStream instream;

                int status = con.getResponseCode();

                if (status != HttpURLConnection.HTTP_OK)
                    instream = con.getErrorStream();
                else
                    instream = con.getInputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Integer id = Integer.valueOf(placeId);
                if (response.toString().equals("correct"))
                   trainPlace = new TransportPlace(id, "tren", null, estacion);
                    trainPlace.setId(id);
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainPlace;
    }
}
