package es.lost2found.entities;

import org.json.JSONObject;

public class TransportTerminalPlace  { //  extends Place

    private Integer id;
    private String nombre;
    private String terminal;

    public TransportTerminalPlace(Integer id, String nombre, String terminal) {
        //super(id, nombre);
        this.terminal = terminal;
    }

    public TransportTerminalPlace(String json) {
        //super(json);
        try {
            JSONObject jObject = new JSONObject(json);
            this.terminal = jObject.getString("terminal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTerminal() {
        return this.terminal;
    }
}
