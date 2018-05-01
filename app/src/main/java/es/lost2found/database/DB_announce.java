package es.lost2found.database;

import android.content.Intent;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import es.lost2found.entities.Announce;
import es.lost2found.entities.User;
import es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI.MatchAnnounceViewAdapter;

public class DB_announce {

    // Server: jcorreas-hp.fdi.ucm.es
    private static String SERVER_PATH = "http://jcorreas-hp.fdi.ucm.es/lost2found/database/announce/";

    public static Integer getNumberAnnounces(String email) {
        Integer userId = DB_user.getId(email); // Obtenemos el id del usuario en cuestion
        Integer ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userId);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getNumberUserAnnouncesJSON.php";
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

            if(Integer.valueOf(response.toString()) >= 0) {
                ret = Integer.valueOf(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ret;
    }

    public static Announce[] getAnnounces(String email, String numberAnnounces, String place) {
        Integer userId = DB_user.getId(email); // Obtenemos el id del usuario en cuestion
        Integer numAnnounces = Integer.valueOf(numberAnnounces);
        Announce[] announcesArray = new Announce[numAnnounces];
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userId);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getAnnouncesJSON.php";
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

            String res = response.toString();
            String[] announces = res.split("\\.");
            String text = announces[0].replace("[", "");
            announces[0] = text;
            String text2 = announces[announces.length-1].replace("]", "");
            announces[announces.length-1] = text2;

            for(int i = 0; i < numAnnounces; i++) {
                char firstChar = announces[i].charAt(1);
                if(firstChar == ',') {
                    announces[i] = announces[i].substring(2, announces[i].length());
                }
                JSONObject object = new JSONObject(announces[i]);
                String idUser =  object.getString("idUsuario");
                String idAnuncio = object.getString("id");
                Integer intIdAnuncio = Integer.valueOf(idAnuncio);

                //////HAY QUE CREAR UNA LISTA DE IDs PARA, CUANDO EL USUARIO SELECCIONA UNO, SACAR ESE
                DB_typeObject.listaIdsAnuncios.add(intIdAnuncio);

                Integer intIdUser = Integer.valueOf(idUser);
                String userOwner = DB_user.getNameById(intIdUser);
                Announce announce = new Announce(announces[i], userOwner, place, intIdAnuncio);

                announcesArray[i] = announce;
            }

            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return announcesArray;
    }

    // Id, TipoAnuncio, HoraActual, DiaAnuncio, HoraPerdidaoHallazgo, Modelo, Marca, Color, idUsuario e idLugar, Categoria (NombreTabla)
    public static Announce insertAnnounce(String announceType, String currentTime, String announceDayText, String announceHourText, String color, String idUser, String idPlace, String announceCategorie, String place, String userName) {
        Announce ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("announceType", announceType);
            jsonObject.put("currentTime", currentTime);
            jsonObject.put("announceDateText", announceDayText);
            jsonObject.put("announceHourText", announceHourText);
            jsonObject.put("color", color);
            jsonObject.put("idUser", idUser);
            jsonObject.put("idPlace", idPlace);
            jsonObject.put("announceCategorie", announceCategorie);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "insertAnnounceJSON.php";
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
                Integer colorInt = Integer.valueOf(color);
                if (response.toString().equals("correct"))
                    ret = new Announce(announceType, currentTime, announceDayText, announceHourText, colorInt, announceCategorie, place, userName);
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


    public static Integer getNumberSeekerAnnounces(String categoria, String tipo) {

        Integer ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombreTabla", categoria);
            jsonObject.put("tipoAnuncio", tipo);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getNumberSeekerAnnouncesJSON.php";
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

            if(Integer.valueOf(response.toString()) >= 0) {
                ret = Integer.valueOf(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ret;
    }

    // id, tipoAnuncio, horaActual, diaAnuncio, horaPerdidaoHallazgo, color, idUsuario, idLugar, nombreTabla (categoria)
    public static Announce[] getAnnouncesSeeker(String categoria, String tipo, String numberAnnounces, String place) {
        Integer numAnnounces = Integer.valueOf(numberAnnounces);
        Announce[] announcesArray = new Announce[numAnnounces];
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombreTabla", categoria);
            jsonObject.put("tipoAnuncio", tipo);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getAnnouncesSeekerJSON.php";
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

            String res = response.toString();
            String[] announces = res.split("\\.");
            String text = announces[0].replace("[", "");
            announces[0] = text;
            String text2 = announces[announces.length-1].replace("]", "");
            announces[announces.length-1] = text2;

            for(int i = 0; i < numAnnounces; i++) {
                char firstChar = announces[i].charAt(1);
                if(firstChar == ',') {
                    announces[i] = announces[i].substring(2, announces[i].length());
                }
                JSONObject object = new JSONObject(announces[i]);
                String idUser =  object.getString("idUsuario");
                Integer intIdUser = Integer.valueOf(idUser);
                String userOwner = DB_user.getNameById(intIdUser);
                String idAnuncio = object.getString("id");
                Integer intIdAnuncio = Integer.valueOf(idAnuncio);
                Announce announce = new Announce(announces[i], userOwner, place, intIdAnuncio);
                announcesArray[i] = announce;
            }

            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return announcesArray;
    }



    ///////////////////////////////MATCH///////////////////////////////////////
    public static Integer getNumberMatchAnnounces(String email, String categoria, String tipo, String dia, String idAnuncio) {
        Integer userId = DB_user.getId(email);
        Integer idObjeto = Integer.valueOf(idAnuncio);
        Integer ret = null;
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", userId);
            jsonObject.put("nombreTabla", categoria);
            jsonObject.put("tipoAnuncio", tipo);
            jsonObject.put("diaAnuncio", dia);
            jsonObject.put("idObjeto", idObjeto);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getNumberMatchAnnouncesJSON.php";
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

            if(Integer.valueOf(response.toString()) >= 0) {
                ret = Integer.valueOf(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ret;
    }

    public static Announce[] getAnnouncesMatch(String email, String categoria, String tipo, String numberAnnounces, String place, String dia) {
        Integer numAnnounces = Integer.valueOf(numberAnnounces);
        Integer userId = DB_user.getId(email);
        Announce[] announcesArray = new Announce[numAnnounces];
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userId);
            jsonObject.put("nombreTabla", categoria);
            jsonObject.put("tipoAnuncio", tipo);
            jsonObject.put("diaAnuncio", dia);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getAnnouncesMatchJSON.php";
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

            String res = response.toString();
            String[] announces = res.split("\\.");
            String text = announces[0].replace("[", "");
            announces[0] = text;
            String text2 = announces[announces.length-1].replace("]", "");
            announces[announces.length-1] = text2;

            for(int i = 0; i < numAnnounces; i++) {
                char firstChar = announces[i].charAt(1);
                if(firstChar == ',') {
                    announces[i] = announces[i].substring(2, announces[i].length());
                }
                JSONObject object = new JSONObject(announces[i]);
                String idUser =  object.getString("idUsuario");
                Integer intIdUser = Integer.valueOf(idUser);
                String userOwner = DB_user.getNameById(intIdUser);
                String idAnuncio = object.getString("id");
                Integer intIdAnuncio = Integer.valueOf(idAnuncio);
                Announce announce = new Announce(announces[i], userOwner, place, intIdAnuncio);
                announcesArray[i] = announce;
            }

            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return announcesArray;
    }
}
