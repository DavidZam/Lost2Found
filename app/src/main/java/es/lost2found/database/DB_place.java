package es.lost2found.database;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
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
import es.lost2found.entities.User;

public class DB_place {
    // Server: jcorreas-hp.fdi.ucm.es
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
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                if (response.toString().equals("correct"))
                    ret = new Place();
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
                StringBuffer response = new StringBuffer();

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
                StringBuffer response = new StringBuffer();

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
                StringBuffer response = new StringBuffer();

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

    public static Integer[] callGeocodingGoogleMapsApi(String address) {
        //List<String> addressArray = new ArrayList<String>();
        String[] addressArray = new String[2];
        String[] addressArray2 = new String[2];
        if(address.contains(":")) {
            addressArray = address.split(":");
                if(addressArray[1].contains(",")) {
                    addressArray2 = addressArray[1].split(",");
                    address = addressArray2[1];
                } else {
                    address = addressArray[1];
                }
        }
        if(address.contains(" ")) {
            address = address.replace(" ", "");
        }
        //final String API_KEY = "AIzaSyA6NVXllfUxwrhX0kqaeGD9usn12uY8fgQ";
        String API_URL = "https://maps.googleapis.com/maps/api/geocode/json?language=es";
        String typePlace = "";
        Integer[] location = new Integer[2]; // = new ArrayList<Integer>()
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("address", address);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String parameters = "&address=" + address; // + "&key=" + API_KEY // + "&region=fr"
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
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                JSONObject object = new JSONObject(response.toString());
                if(response.toString().contains("status")) {
                    String statusResponse = object.getString("status");
                    if(statusResponse.equals("OK")) {
                        if(response.toString().contains("results") && response.toString().contains("geometry") ) {
                            //Object locationObject = object.getJSONObject("location");
                            JSONArray results = object.getJSONArray("results");
                            JSONArray formattedAddress = results.getJSONArray(3);
                            //JSONObject locationArr = results.getJSONObject("location");
                            //String latitud = results.getStr
                            //Integer longitud = object.getString("lng");
                            //Integer latitudInt = Integer.valueOf(latitud);
                            //Integer longitudInt = Integer.valueOf(longitud);
                            //location[0] = latitudInt;
                            //location[1] = longitudInt;
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
