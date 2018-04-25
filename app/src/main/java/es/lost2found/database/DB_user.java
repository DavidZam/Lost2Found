package es.lost2found.database;

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

import es.lost2found.entities.User;

public class DB_user {


    //private static String SERVER_PATH = "http://192.168.1.42/lost2found/database/user/";
    // Server: jcorreas-hp.fdi.ucm.es
    private static String SERVER_PATH = "http://jcorreas-hp.fdi.ucm.es/lost2found/database/user/";

    public static User findUserByEmail(String email, String contrasena) {
        User ret = null;
        Integer idLUser = getId(email); // Obtenemos el id de ese lugar
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("contrasena", contrasena);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getUserByEmailJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "your user agent");
            con.setRequestProperty("Accept-Language", "sp,SP;q=0.5");

            String urlParameters = "json=" + jsonString;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            JSONObject object = new JSONObject(response.toString());
            if(object.getBoolean("correct"))
                ret = new User(response.toString());
                ret.setId(idLUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ret;
    }

    public static User insertUser(String nombre, String email, String contrasena) {
        User ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", nombre);
            jsonObject.put("email", email);
            jsonObject.put("contrasena", contrasena);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "insertUserJSON.php";
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
                    ret = new User(getId(email), email, nombre, contrasena);

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

    public static boolean checkIfUserAlreadyExists(String nombre, String email) {
        boolean userExists = false;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("nombre", nombre);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "checkIfUserExists.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "your user agent");
            con.setRequestProperty("Accept-Language", "sp,SP;q=0.5");

            String urlParameters = "json=" + jsonString;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            if(response.toString().equals("exists"))
                userExists = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userExists;
    }

    public static boolean checkIfEmailAlreadyExists(String email) {
        boolean userExists = false;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "checkIfUserExists.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "your user agent");
            con.setRequestProperty("Accept-Language", "sp,SP;q=0.5");

            String urlParameters = "json=" + jsonString;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            if(response.toString().equals("exists"))
                userExists = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userExists;
    }

    public static Integer getId(String email) {
        Integer id = 0;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getUserByIdJSON.php";
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

    public static String getNameById(int id) {
        String name = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getUserNameByIdJSON.php";
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
                        name = object.getString("name");
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

    public static User updateNameUser(String nombre, String email) {
        User ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", nombre);
            jsonObject.put("email", email);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "updateNameUserJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "your user agent");
            con.setRequestProperty("Accept-Language", "sp,SP;q=0.5");

            String urlParameters = "json=" + jsonString;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);

            if (response.toString().equals("correct"))
                ret = new User(getId(email), email, nombre, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ret;
    }

    public static User updatePasswordUser(String pass, String email, String nombre){
        User ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", nombre);
            jsonObject.put("email", email);
            jsonObject.put("contrasena", pass);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "updatePasswordUserJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "your user agent");
            con.setRequestProperty("Accept-Language", "sp,SP;q=0.5");

            String urlParameters = "json=" + jsonString;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);

            if (response.toString().equals("correct"))
                ret = new User(getId(email), email, nombre, pass);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static User updateEmailUser(String nombre, String email, String emailAntiguo) {
        User ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("id", getId(emailAntiguo));

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "updateEmailUserJSON.php";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "your user agent");
            con.setRequestProperty("Accept-Language", "sp,SP;q=0.5");

            String urlParameters = "json=" + jsonString;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);

            if (response.toString().equals("correct"))
                ret = new User(getId(email), email, nombre, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ret;
    }

}
