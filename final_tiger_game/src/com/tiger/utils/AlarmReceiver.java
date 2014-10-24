package com.tiger.utils;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{
	
	
	public static boolean houseLight = false;
	public static boolean sureBone = false;
	public static String houseLightMsg = "";
	public static int hourseCredit = 0;
	
	private static String TAG = "AlarmReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "AlarmReceiver onReceive");
		
		//请求跑马灯
		executeRequest(new StringRequest(Method.POST, "http://wondergame.sinaapp.com/interface/getBonus", houseLightresponseListener(),
				errorListener()) {
			protected Map<String, String> getParams() {
				return new ApiParams().with("equipment_id", "sb03");
			}
		});
		
		//请求强制中奖
		executeRequest(new StringRequest(Method.POST, "http://wondergame.sinaapp.com/interface/getLimit", sureBoneresponseListener(),
				errorListener()) {
			protected Map<String, String> getParams() {
				return new ApiParams().with("equipment_id", "sb03");
			}
		});
	}
	
	
	protected void executeRequest(Request<?> request) {
		RequestManager.addRequest(request, this);
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
//				Toast.makeText(AlarmReceiver.this, error.getMessage(), Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	private Response.Listener<String> houseLightresponseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, "houseLight return :" + response);
				
				JSONObject object;
				try {
					object = new JSONObject(response.toString());
					
					int resultCode = object.getInt("ret");
					if (0 == resultCode) {
						int status = object.getInt("status");
						if (status == 1) {
							houseLight = true;
							houseLightMsg =  object.getString("msg");
							hourseCredit = object.getInt("credits");
						} else {
							houseLight = false;
							houseLightMsg =  "";
						}
					} else {
						//提示错误
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
	
	private Response.Listener<String> sureBoneresponseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, "sureBone return :" + response);
				
				JSONObject object;
				try {
					object = new JSONObject(response.toString());
					
					int resultCode = object.getInt("ret");
					if (0 == resultCode) {
						int status = object.getInt("status");
						if (status == 1) {
							sureBone = true;
						}
					} else {
						//提示错误
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
}
