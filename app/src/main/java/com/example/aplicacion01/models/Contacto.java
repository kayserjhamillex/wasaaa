package com.example.aplicacion01.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.aplicacion01.MainActivity;
import com.example.aplicacion01.helpers.DatabaseHelper;
import com.example.aplicacion01.helpers.QueueUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contacto {
    public String phone;
    public String nickname;
    public String image;
    public  int id;
    public  static  DatabaseHelper dbInstance;

    public  String getSmallImage(){
        return  this.image;
    }

    public Contacto(String _phone, String _nickname, String _image) {
        this.phone = _phone;
        this.nickname = _nickname;
        this.image = _image;
    }


    public DatabaseHelper getDbInstance(Context _context) {
        if ( Contacto.dbInstance == null ) {
            Contacto.dbInstance = new DatabaseHelper(_context);
            return Contacto.dbInstance;
        }
        return Contacto.dbInstance;
    }


    public static ArrayList getCollection() {
        ArrayList<Contacto> collection = new ArrayList<>();
        collection.add(new Contacto("981999923", "Dia","https://mymodernmet.com/wp/wp-content/uploads/2019/05/even-tryggstrand-norway-photos-4.jpeg"));
        collection.add(new Contacto("9859913923", "Noche","https://mymodernmet.com/wp/wp-content/uploads/2019/05/even-tryggstrand-norway-photos-19.jpg"));
        collection.add(new Contacto("981914213", "Aurora","https://mymodernmet.com/wp/wp-content/uploads/2019/05/even-tryggstrand-norway-photos-22.jpg"));
        collection.add(new Contacto("981914213", "paisaje","https://mymodernmet.com/wp/wp-content/uploads/2019/05/even-tryggstrand-norway-photos-3.jpg\n"));
        return collection;
    }

    public static void sendRequestPOST(QueueUtils.QueueObject o, final MainActivity _interface) {
        String url = "http://rrojasen.alwaysdata.net/purchaseorders.json";
        url = "http://fipo.equisd.com/api/users/new.json";
        url = "https://sugared-stick.glitch.me/products/new.json";
        /*url = "http://192.168.58.3:8056/api/users/new.json";*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Do it with this it will work
                            JSONObject _response = new JSONObject(response);
                            if (_response.has("object")) {
                                JSONObject object_response = null;
                                try {
                                    object_response = _response.getJSONObject("object");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if ( object_response != null ) {
                                    try {
                                        System.out.println(object_response.getInt("id"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("first_name","Daisy Yolisa");
                params.put("last_name","Muñoz");
                params.put("avatar","Futbol");

                return params;
            }
        };
        o.addToRequestQueue(stringRequest);
    }


    public static void injectContactsFromCloud(final QueueUtils.QueueObject o,
                                               final ArrayList<Contacto> contactos,
                                               final MainActivity _interface,
                                               final String tipo) {
        String url = "http://fipo.equisd.com/api/users.json";
        url = "https://sugared-stick.glitch.me/products.json?tipo=" + tipo;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("objects")) {

                            try {
                                JSONArray list = response.getJSONArray("objects");
                                for (int i=0; i < list.length(); i++) {
                                    JSONObject o = list.getJSONObject(i);
                                    contactos.add(new Contacto(o.getString("name"),
                                            o.getString("description"),""));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            _interface.refreshList(); // Esta función debemos implementarla
                            // en nuestro activity
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        o.addToRequestQueue(jsonObjectRequest);
    }



    public ArrayList<Contacto> getAll(Context _context) {
        ArrayList<Contacto> rows = new ArrayList<>();
        SQLiteDatabase db = this.getDbInstance(_context).getReadableDatabase();

        String[] fields = new String[] {DatabaseHelper.Columns._ID,
                DatabaseHelper.Columns.COLUMN_NAME_NAME,
                DatabaseHelper.Columns.COLUMN_NAME_DESCRIPTION};
        String[] args = new String[] {this.id + ""};

        Cursor c = db.query(DatabaseHelper.CONTACT_TABLE_NAME, fields,
                null, null,  null, null,
                DatabaseHelper.Columns._ID+" DESC");

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            do {
                Contacto n = new Contacto(c.getString(1),c.getString(2), "");
                n.id = c.getInt(0);
                //n.setId(c.getInt(0));
                //n.setName(c.getString(1));
               // n.setIsActive(c.getString(2));
                rows.add(n);
            } while(c.moveToNext());
        }
        return rows;
    }



    public void setLocal(Context _context) {
        SQLiteDatabase db = this.getDbInstance(_context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Columns.COLUMN_NAME_NAME, this.phone);
        values.put(DatabaseHelper.Columns.COLUMN_NAME_DESCRIPTION, this.nickname);

        String[] args = new String[] {this.id + ""};

        if (this.id == 0) {
            this.id = this.getAll(_context).size() + 1;
        }
        long newRowId = db.insert(DatabaseHelper.CONTACT_TABLE_NAME, null, values);
    }
}
