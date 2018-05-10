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

import es.lost2found.entities.Chat;

public class DB_chat {

    private static String SERVER_PATH = "http://jcorreas-hp.fdi.ucm.es/lost2found/database/chat/";

    public static Chat createNewChat(String chatTitle, Integer idUser1, Integer idUser2) { // , String lastMsg
        Chat chat = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chatTitle", chatTitle);
            jsonObject.put("idUser1", idUser1);
            jsonObject.put("idUser2", idUser2);
            //jsonObject.put("lastMsg", lastMsg);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "insertNewChatJSON.php";
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
                    chat = new Chat(chatTitle, idUser1, idUser2); // , lastMsg
            } finally {
                con.disconnect();
            }
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chat;
    }

    public static boolean checkIfChatAlreadyExists(Integer idUser1, Integer idUser2) {
        boolean chatExists = false;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idUser1", idUser1);
            jsonObject.put("idUser2", idUser2);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "checkIfChatExistsJSON.php";
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

            if(response.toString().equals("exists")) {
                chatExists = true;
            } else if(response.toString().equals("no exists")) {
                chatExists = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatExists;
    }

    public static Chat getChat(Integer idUser1, Integer idUser2) {
        Chat chat = new Chat();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idUser1", idUser1);
            jsonObject.put("idUser2", idUser2);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getChatJSON.php";
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
                        chat = new Chat(response.toString());
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
        return chat;
    }

    public static Integer getNumberChats(Integer userId) { // Falta crear en el servidor y comprobar que funciona
        Integer ret = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userId);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getNumberUserChatsJSON.php";
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

    public static Chat[] getChats(Integer userId, Integer numberChats) {
        Chat[] chatsArray = new Chat[numberChats];
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userId);

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            String urlStr = SERVER_PATH + "getChatsJSON.php";
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
            String[] chats = res.split("\\.");
            chats[0] = chats[0].replace("[", "");
            chats[chats.length-1] = chats[chats.length-1].replace("]", "");

            for(int i = 0; i < numberChats; i++) {
                char firstChar = chats[i].charAt(1);
                if(firstChar == ',') {
                    chats[i] = chats[i].substring(2, chats[i].length());
                }
                JSONObject object = new JSONObject(chats[i]);
                String chatTitle =  object.getString("nombreChat");
                Integer idUsuario1 =  object.getInt("idUsuario1");
                Integer idUsuario2 =  object.getInt("idUsuario2");

                Chat chat = new Chat(chatTitle, idUsuario1, idUsuario2);

                chatsArray[i] = chat;
            }

            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatsArray;
    }

    public static Integer getChatId(Chat chat) {
        Integer idChat = 0;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombreChat", chat.getChattitle());
            jsonObject.put("idUser1", chat.getIdUsuario1());
            jsonObject.put("idUser2", chat.getIdUsuario2());

            List list = new LinkedList();
            list.addAll(Arrays.asList(jsonObject));
            String jsonString = list.toString();

            jsonString = URLEncoder.encode(jsonString, "UTF-8");

            String urlStr = SERVER_PATH + "getChatIdJSON.php";
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
                        idChat = object.getInt("id");
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
        return idChat;
    }
}
