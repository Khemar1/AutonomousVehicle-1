package com.autonomousvehicle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khemar on 2017-04-03.
 */

public class MapRequest extends StringRequest {
    public static final String  REGISTER_REQUEST_URL = "http://myloginappsite.site88.net/setmap.php";

    private Map<String, String> params;

    public MapRequest( String x,String y, Response.Listener<String> listener){

        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("x", x);
        params.put("y",y);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
