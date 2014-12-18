package de.rooehler.strava4android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import de.rooehler.strava4android.StravaDialog.StravaDialogListener;
import de.rooehler.strava4android.model.Strava;
import de.rooehler.strava4android.model.StravaUploadResponse;

public class StravaUtil {

	private final static String TAG = StravaUtil.class.getSimpleName();

	private static final String SAVED_STRAVA = "de.rooehler.strava4android.saved_strava";

	private final static String DEFAULT_REDIRECT_URI = "http://localhost/token_exchange";
	
	
	/**
	 * authorizes : 2 steps
	 * 
	 * 1.Show a webview, to let the user login and grant access to the app resulting in a "code"
	 * 
	 * 2.Retrieve the Strava object including the access token, using the code and the Strava secret
	 * 
	 * result is communicated using the callback
	 * 
	 * @param context
	 * @param callback
	 */
	public static void authorize(final Context context,final String strava_secret, final int strava_id,final String redirect_Uri, final StravaAuthCallback callback){


		final String url = String.format(
				"https://www.strava.com/oauth/authorize?client_id=%d&response_type=code&redirect_uri=%s&scope=write&state=mystate&approval_prompt=force",
				strava_id,redirect_Uri);


		final StravaDialogListener listener = new StravaDialogListener() {
			@Override
			public void onComplete(String value) {

				final int codeStart = value.lastIndexOf("=") + 1;

				final String code   = value.substring(codeStart); 
				
				Log.d(TAG, "code "+ code);
				
				if(code.equals("access_denied")){
					//user decided not to give access
					callback.denied();
				}else{
					
					callback.showProgress();
					getToken(code,strava_secret, strava_id, callback);
				}

			}

			@Override
			public void onError(String value) {
				Log.e(TAG,"Failed opening authorization page");
				callback.error();
			}
		};

		new StravaDialog(context, url, redirect_Uri, listener).show();

	}
	
	public static void authorize(final Context context,final String strava_secret, final int strava_id, final StravaAuthCallback callback){
			authorize(context,strava_secret, strava_id, DEFAULT_REDIRECT_URI, callback);
	}

	private static void getToken(final String code,final String strava_secret, final int strava_id, final StravaAuthCallback callback){

		String encodedCode = null, encodedSecret = null;
		try{
			encodedCode   = URLEncoder.encode(code, "UTF-8");
			encodedSecret = URLEncoder.encode(strava_secret  , "UTF-8");
		}catch(UnsupportedEncodingException e){
			Log.e(TAG, "error encoding",e);
		}


		final String url = String.format("https://www.strava.com/oauth/token?client_id=%d&client_secret=%s&code=%s",
				strava_id,encodedSecret,encodedCode);

		new Thread(new Runnable() {

			@Override
			public void run() {
				final String json = postRequest(url);
				Log.d(TAG, "json : \n"+json);
				try{

					Gson gson = new Gson();

					Strava strava = gson.fromJson(json, Strava.class);

					callback.gotStrava(strava);

				} catch(Exception e){
					Log.e(TAG,"Error parsing JSON using Gson",e);
					callback.error();
				}
			}
		}).start();

	}

	private static String postRequest(String address){
		
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpUriRequest http = new HttpPost(address);


		try{
			org.apache.http.HttpResponse response = client.execute(http);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(statusCode == 200){
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while((line = reader.readLine()) != null){
					builder.append(line);
				}
			} else {
				Log.e(TAG,"http statuscode "+ statusCode);
			}
		}catch(ClientProtocolException e){
			Log.e(TAG,"ClientProtocolException reading JSON object");
		} catch (IOException e){
			Log.e(TAG,"IOException reading JSON object");
		}
		return builder.toString();
	}
	
	/**
	 * uploads a gpx to strava
	 * @param gpxFile
	 * @param token
	 * @param callback
	 */
	public static void uploadGPX(final File gpxFile, final String token, final StravaUploadResponseCallback callback){
	
		
		new Thread(new Runnable() {
			@Override
			public void run() {

				try { 
					String upLoadServerUri = "https://www.strava.com/api/v3/uploads?data_type=gpx&activity_type=ride";

					if(!gpxFile.isFile()){
						throw new IllegalArgumentException("file provided not valid");
					}

					MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
					multipartEntity.addBinaryBody("file", gpxFile, ContentType.create("application/octet-stream"), gpxFile.getName());

					HttpClient client = new DefaultHttpClient();
					
					HttpPost post = new HttpPost(upLoadServerUri); 
					post.addHeader(new BasicHeader("Authorization", String.format("Bearer %s", token)));
					post.setEntity(multipartEntity.build());
					HttpResponse response = client.execute(post);
					
					int statusCode = response.getStatusLine().getStatusCode();

					if(statusCode == 200 || statusCode == 201){
						StringBuilder builder = new StringBuilder();
						HttpEntity entity = response.getEntity();
						InputStream content = entity.getContent();
						BufferedReader reader = new BufferedReader(new InputStreamReader(content));
						String line;
						while((line = reader.readLine()) != null){
							builder.append(line);
						}
						final String json = builder.toString();
						
						Log.d(TAG, "json : "+json);
						
						Gson gson = new Gson();

						StravaUploadResponse sur = gson.fromJson(json, StravaUploadResponse.class);

						callback.uploaded(sur);     
					}else{
						Log.d(TAG, "responsecode : "+statusCode);
						callback.error("error posting this session "+response);
					}

				}catch (Exception e) {

					Log.e("Upload file to server Exception", "Exception : "	+ e.getMessage(), e);
					callback.error("error posting this session "+e.getMessage());
				}
			}
		}).start();
	}

	public static void saveStrava(final Context context,final Strava strava){

		try {
			if(strava != null){

				FileOutputStream fo = context.openFileOutput(SAVED_STRAVA, Context.MODE_PRIVATE);
				ObjectOutputStream out = new ObjectOutputStream(fo);
				out.writeObject(strava);
				out.flush();
				out.close();
				fo.close();
			}
		} catch (IOException e) {
			Log.e(TAG, "Strava save failed",e);
		}
	}
	
	public static Strava loadStrava(final Context context){

		try {

			FileInputStream fi = context.openFileInput(SAVED_STRAVA);
			ObjectInputStream in = new ObjectInputStream(fi);
			Strava strava = (Strava) in.readObject();
			Log.d(StravaUtil.class.getSimpleName(), "Saved Strava load succeeded");
			in.close();
			fi.close();

			return strava;

		} catch (FileNotFoundException e) {
			Log.d(TAG, "No Strava saved yet");
		} catch (StreamCorruptedException e) {
			Log.e(TAG, "Saved Strava load failed",e);
		} catch (IOException e) {
			Log.e(TAG, "Saved Strava load failed",e);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "Saved Strava load failed",e);
		}
		return null;

	}
	public static boolean deleteStrava(final Context context){
		
		return context.deleteFile(SAVED_STRAVA);
	}
	
	public interface StravaUploadResponseCallback
	{
		public void uploaded(StravaUploadResponse respomse);
		
		public void error(final String error);
	}
	
	public interface StravaAuthCallback
	{
		public void showProgress();

		public void gotStrava(final Strava strava);
		
		public void denied();

		public void error();
	}
}
