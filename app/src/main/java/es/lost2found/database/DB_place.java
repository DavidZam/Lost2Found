package es.lost2found.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import es.lost2found.entities.Place;

public class DB_place {
    private static String SERVER_PATH = "http://jcorreas-hp.fdi.ucm.es/lost2found/database/place/";

    public static Place insertPlace() {
        Place ret = null;
        try {
            JSONObject jsonObject = new JSONObject();

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "insertPlaceJSON.php";
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
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                if (response.toString().equals("correct"))
                    ret = new Place();
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Integer getId() {
        Integer id = 0;
        try {
            JSONObject jsonObject = new JSONObject();

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getPlaceIdJSON.php";
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
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                JSONObject object = new JSONObject(response.toString());
                if(object.getBoolean("correct")) {
                    try {
                        id = object.getInt("id");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getPlaceNameById(int id) {
        String name = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getPlaceNameByIdJSON.php";
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
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                JSONObject object = new JSONObject(response.toString());
                if(object.getBoolean("correct")) {
                    try {
                        String param1 = object.getString("param1");
                        if((object.toString().contains("param2"))) {
                            String param2 = object.getString("param2");
                            name = param1 + ", " + param2;
                            if((object.toString().contains("param3"))) {
                                String param3 = object.getString("param3");
                                name = param1 + ": " + param2 + ", " + param3;
                            }
                        } else {
                            String param3 = object.getString("param3");
                            name = param1 + ": " + param3;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getTypePlaceById(int id) {
        String typePlace = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getPlaceTypeByIdJSON.php";
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
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                JSONObject object = new JSONObject(response.toString());
                if(object.getBoolean("correct")) {
                    typePlace = object.getString("param1");
                }
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typePlace;
    }

    public static Double[] callGeocodingGoogleMapsApi(String address, String typePlace) {
        String[] addressArray;
        String[] addressArray2 = new String[2];
        if(typePlace.equals("concrete")) {
            if(address.contains(":")) {
                addressArray = address.split(":");
                if (addressArray[1].contains(",")) {
                    addressArray2 = addressArray[1].split(",");
                }
                address = addressArray[0] + addressArray2[0];
            }
        } else if(typePlace.equals("transport")) {
            if (address.contains(":")) {
                addressArray = address.split(":");
                if (addressArray[1].contains(",")) {
                    addressArray2 = addressArray[1].split(",");
                    address = addressArray2[1];
                } else {
                    address = addressArray[1];
                }
            }
        }
        if (address.contains(" ")) {
            address = address.replace(" ", "");
        }
        //final String API_KEY = "AIzaSyA6NVXllfUxwrhX0kqaeGD9usn12uY8fgQ";
        final String SERVER_KEY = "AIzaSyClw9c3Y2GmjjNqlBJqJAcuofhMWMLJ2sE";
        String API_URL = "https://maps.googleapis.com/maps/api/geocode/json?language=es";
        Double[] location = new Double[2];
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("address", address);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String parameters = "&address=" + address + "&key=" + SERVER_KEY;
            String urlStr = API_URL + parameters;
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
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                JSONObject object = new JSONObject(response.toString());
                if(response.toString().contains("status")) {
                    String statusResponse = object.getString("status");
                    if(statusResponse.equals("OK")) {
                        if(response.toString().contains("results") && response.toString().contains("geometry") ) {
                            try {
                                JSONObject jsonObject2 = new JSONObject(response.toString());
                                JSONArray results = jsonObject2.getJSONArray("results");
                                JSONObject jo = results.getJSONObject(0);
                                JSONObject geometry = jo.getJSONObject("geometry");
                                JSONObject location2 = geometry.getJSONObject("location");
                                String lat = location2.optString("lat");
                                String longit = location2.optString("lng");
                                location[0] = Double.valueOf(lat);
                                location[1] = Double.valueOf(longit);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
}
