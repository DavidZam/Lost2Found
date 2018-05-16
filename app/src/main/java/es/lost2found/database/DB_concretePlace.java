package es.lost2found.database;

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

import es.lost2found.entities.ConcretePlace;
import es.lost2found.entities.Place;

public class DB_concretePlace {

     public static ConcretePlace insertConcretePlace(String street, String number, String postalcode) {
        String SERVER_PATH = "http://jcorreas-hp.fdi.ucm.es/lost2found/database/place/concrete/";
        Place lugar = DB_place.insertPlace(); // Primero insertamos un lugar
        Integer idLugar = lugar.getId(); // Obtenemos el id de ese lugar
        ConcretePlace ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", idLugar);
            jsonObject.put("street", street);
            jsonObject.put("number", number);
            jsonObject.put("postalcode", postalcode);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "insertConcretePlaceJSON.php";
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
                    ret = new ConcretePlace(idLugar, street, number, postalcode);
                    ret.setId(idLugar);
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
