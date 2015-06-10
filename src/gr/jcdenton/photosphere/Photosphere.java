package gr.jcdenton.photosphere;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import android.os.Environment;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

public class Photosphere extends CordovaPlugin {
    
    ProgressDialog pDialog;
    
    @Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
		PluginResult.Status status = PluginResult.Status.OK;
		JSONArray result = new JSONArray();
		
		try {
			JSONObject jobject = args.getJSONObject(0);
			String imgOrVid = jobject.getString("imageurl");
			String title = jobject.getString("title");
            showImg(title, imgOrVid);//jobject.getString("imageurl")
			callbackContext.sendPluginResult(new PluginResult(status, result));
		} catch (JSONException e) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
        }
        return true;
	}
    
    private void showImg(String title, String imgurl){
        new LoadImage(imgurl,title).execute();
	}
    
     private class LoadImage extends AsyncTask<String, String, String> {
		 
		 String urlimg, mTitle;
		 
		 public LoadImage(String imgURL, String title){
			 urlimg = imgURL;
			 mTitle = title;
		 }
        @Override
        protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(cordova.getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
				pDialog.setTitle(mTitle);
                pDialog.setMessage("Loading....");
                pDialog.show();
        }
        protected String doInBackground(String... args) {
            File storagePath = Environment.getExternalStorageDirectory();
            try{
                URL url = new URL (urlimg);//args[0]
                InputStream input = url.openStream();
                try {
                    OutputStream output = new FileOutputStream (storagePath + "/photo360.jpg");
                try {
                        byte[] buffer = new byte[2048];
                        int bytesRead = 0;
                        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                            output.write(buffer, 0, bytesRead);
                    }
                    } finally {output.close();}
                } finally {input.close();}
            }catch(Exception e){}
            return storagePath+"/photo360.jpg";
        }
        protected void onPostExecute(String path) {
            if(path != null){
                Intent intent = new Intent(cordova.getActivity().getApplicationContext(), PanoramaViewer.class);
                intent.putExtra("filepath", path);
                cordova.getActivity().startActivity(intent);
                pDialog.dismiss();
            }else{
               pDialog.dismiss();
             }
        }
    }
}