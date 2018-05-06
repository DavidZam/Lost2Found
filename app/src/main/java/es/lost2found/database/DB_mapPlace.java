package es.lost2found.database;

import com.google.android.gms.maps.model.LatLng;

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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.lost2found.entities.MapPlace;
import es.lost2found.entities.Place;

public class DB_mapPlace {

    // Server: jcorreas-hp.fdi.ucm.es
    private static String SERVER_PATH = "http://jcorreas-hp.fdi.ucm.es/lost2found/database/place/map/";

    public static MapPlace insertMapPlace(Double latitud, Double longitud) {
        Place lugar = DB_place.insertPlace(); // Primero insertamos un lugar
        Integer idLugar = lugar.getId(); // Obtenemos el id de ese lugar
        MapPlace ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", idLugar);
            jsonObject.put("latitud", latitud);
            jsonObject.put("longitud", longitud);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "insertMapPlaceJSON.php";
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
                    ret = new MapPlace(idLugar, latitud, longitud);
                    ret.setId(idLugar);
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
}
