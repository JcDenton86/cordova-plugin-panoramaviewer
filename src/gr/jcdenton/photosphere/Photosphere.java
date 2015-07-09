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
    public static CallbackContext cbcxt;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        cbcxt = callbackContext;
        
        JSONObject jobject = args.getJSONObject(0);
        String imgurl = jobject.optString("imageurl");
        String title = jobject.optString("title");
        String message = jobject.optString("message");
        int imgsource = jobject.optInt("imageSource",1);
        
        loadImg(title, message, imgurl,imgsource);
        
        PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult.setKeepCallback(true);
        
        return true;
    }
    
    private void loadImg(String title, String msg, String imgurl, int imgsrc){
        if(imgsrc==1)
        new LoadImage(imgurl, title, msg).execute();
    }
    
    private class LoadImage extends AsyncTask<String, String, String> {
        String urlimg, mTitle, mMessage, storagepath;
        
        public LoadImage(String imgURL, String title, String msg){
            urlimg = imgURL;
            mTitle = title;
            mMessage = msg;
            storagepath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator  + "Android" + File.separator + "data" + File.separator + cordova.getActivity().getPackageName() + File.separator + "files";
            File f = new File(storagepath);
            if(!f.isDirectory())
            f.mkdirs();
            
        }
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(cordova.getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setTitle(mTitle);
            pDialog.setMessage(mMessage);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            try{
                URL url = new URL (urlimg);
                InputStream input = url.openStream();
                try {
                    OutputStream output = new FileOutputStream (storagepath + "/photo360.jpg");
                    try {
                        byte[] buffer = new byte[2048];
                        int bytesRead = 0;
                        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                            output.write(buffer, 0, bytesRead);
                        }
                    } finally {output.close();}
                } finally {input.close();}
            }catch(Exception e){cbcxt.error(e.getMessage());return null;}
            
            return storagepath+"/photo360.jpg";
        }
        protected void onPostExecute(String imgpath) {
            if(imgpath != null){
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
                pluginResult.setKeepCallback(false);
                cbcxt.success("Local file: "+imgpath+" created successfully");
                Intent intent = new Intent(cordova.getActivity().getApplicationContext(), PanoramaViewer.class);
                intent.putExtra("filepath", imgpath);
                cordova.getActivity().startActivity(intent);
                pDialog.dismiss();
            }else{
                pDialog.dismiss();
                cbcxt.error("Local path: "+imgpath+" could not be created. Check the image URL");
            }
        }
    }
}