package es.lost2found.database;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import es.lost2found.entities.User;

public class DB_user {

    // private static String SERVER_PATH = ""; // COMPLETAR
    private static String SERVER_PATH = "http://localhost/lost2found/database/user/";

    public static User findUserByEmail(String email, String contrasena) {
        User ret = null;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("password", contrasena);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getUserByEmailJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "your user agent");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "json=" + jsonString;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            JSONObject object = new JSONObject(response.toString());
            if(object.getBoolean("correct"))
                ret = new User(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  ret;
    }

    public static User insertUser(String nombre, String contrasena, String email) {
        User ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("contrasena", contrasena);
            jsonObject.put("nombre", nombre);

            List l = new LinkedList();
            l.addAll(Arrays.asList(jsonObject));
            String jsonString = l.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "insertUserJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "your user agent");
            //String language = Locale.getDefault().getLanguage();
            con.setRequestProperty("Accept-Language", "sp-SP,sp;q=0.5");

            String urlParameters = "json=" + jsonString;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader((new InputStreamReader(con.getInputStream())));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);

            if(response.toString().equals("correct"))
                ret = new User(nombre, email, contrasena);

            //wr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
