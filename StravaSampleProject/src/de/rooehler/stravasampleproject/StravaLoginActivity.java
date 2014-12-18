package de.rooehler.stravasampleproject;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

import de.rooehler.strava4android.StravaUtil;
import de.rooehler.strava4android.StravaUtil.StravaAuthCallback;
import de.rooehler.strava4android.model.Strava;
import de.rooehler.strava4android.model.Strava.Athlete;
import de.rooehler.strava4android.model.Strava.Bike;



public class StravaLoginActivity extends Activity{
	
	private final static String TAG = StravaLoginActivity.class.getSimpleName();
	
	public final static float METER_TO_MILES = 0.000621371192f;
	
	private View mNotLoggedFormView;
	private View mLoggedFormView;
	private View mLoginStatusView;
	
	//TODO set your credentials here
	private final static int STRAVA_ID = 0;
	private final static String STRAVA_SECRET = null;
	
	private StravaAuthCallback mStravaAuthCallback = new StravaAuthCallback() {
		
		@Override
		public void gotStrava(final Strava strava) {
			
			StravaLoginActivity.this.showProgress(false);

			StravaUtil.saveStrava(getBaseContext(), strava);

				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						fillProps(strava);
						switchBetweenLoggedInAndLoggedOut(true);
					}
				});

		}
		
		@Override
		public void error() {
			
			StravaLoginActivity.this.showProgress(false);
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					Toast.makeText(getBaseContext(), R.string.login_error,Toast.LENGTH_SHORT).show();

				}
			});
		}

		@Override
		public void showProgress() {
			
			StravaLoginActivity.this.showProgress(true);
			
		}

		@Override
		public void denied() {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					Toast.makeText(getBaseContext(), R.string.login_denied,Toast.LENGTH_SHORT).show();
				}
			});
		}
	};

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		
		setContentView(R.layout.strava);

		mLoggedFormView = findViewById(R.id.logged_in_form);
		mNotLoggedFormView = findViewById(R.id.not_logged_in_form);
		mLoginStatusView = findViewById(R.id.login_status);

		//if we are logged in, a serialized strava object will be here
		Strava strava = StravaUtil.loadStrava(getBaseContext());
		
		if(strava != null){
			//use it
			fillProps(strava);
			
		}else{
			//switch to login screen
			switchBetweenLoggedInAndLoggedOut(false);
		}
		
		final StravaCredentials credentials = new StravaCredentials(STRAVA_ID, STRAVA_SECRET);
		
		final ImageView login = (ImageView) findViewById(R.id.loginWithStrava);
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				StravaUtil.authorize(StravaLoginActivity.this, credentials.getStravaSecret(), credentials.getAppId(), mStravaAuthCallback);
				
			}
		});
		
		final Button logoutButton = (Button) findViewById(R.id.logout_button);
		
		logoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//delete the serialized object
				StravaUtil.deleteStrava(getBaseContext());
				
				switchBetweenLoggedInAndLoggedOut(false);
				
			}
		});
		
		if(!credentials.hasValidCredentials()){

			final AlertDialog ad = new AlertDialog.Builder(this)
			.setMessage(R.string.invalid_credentials)
			.setIcon(R.drawable.strava) 
			.setTitle(R.string.app_name)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();    
					finish();
				}
			})
			.create();
			ad.show();
		}
	}
	/**
	 * fills the properties of this Strava athlete
	 * @param strava
	 */
	public void fillProps(Strava strava){
		final ImageView athleteIcon = (ImageView) findViewById(R.id.athlete_icon);
		
		Log.d(TAG, "token : "+ strava.getAccessToken());
		
		Athlete ath = strava.getAthlete();
		
		String url = ath.getProfile();
		
		AQuery aq = new AQuery(getBaseContext());
		
		ImageOptions options = new ImageOptions();
		options.round = 90;
		
		aq.id(athleteIcon).image(url, options);
		
		final TextView name =  (TextView) findViewById(R.id.athlete_name);
		final TextView place = (TextView) findViewById(R.id.athlete_place);
		final TextView dist =  (TextView) findViewById(R.id.athlete_distance);
		
		name.setText(ath.getFirstname()+" "+ath.getLastname());
		
		place.setText(ath.getCity()+", "+ath.getState()+", "+ath.getCountry());
		
		double d = 0.0d;
		
		for(Bike bike : ath.getBikes()){
			
			d += bike.getDistance();
			
		}
		
		String dist_ = ath.getMeasurementPreference().equals("feet") ? String.format("%.2f mi", d * METER_TO_MILES) : String.format("%.2f km", d / 1000f);
		dist.setText(dist_);
	}
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {

				// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
				// for very easy animations. If available, use these APIs to fade-in
				// the progress spinner.
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
					int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
					
					mLoginStatusView.setVisibility(View.VISIBLE);
					mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE: View.GONE);
						}
					});
					
					mLoggedFormView.setVisibility(View.VISIBLE);
					mLoggedFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoggedFormView.setVisibility(show ? View.GONE : View.VISIBLE);
						}
					});
					
					mNotLoggedFormView.setVisibility(View.VISIBLE);
					mNotLoggedFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mNotLoggedFormView.setVisibility(show ? View.GONE : View.VISIBLE);
						}
					});
					
				} else {
					// The ViewPropertyAnimator APIs are not available, so simply show
					// and hide the relevant UI components.
					mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
					mLoggedFormView.setVisibility(show ? View.GONE : View.VISIBLE);
					mNotLoggedFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			}
		});
	}
	
	public void switchBetweenLoggedInAndLoggedOut(final boolean loggedIn){
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
			
			mLoggedFormView.setVisibility(View.VISIBLE);
			mLoggedFormView.animate().setDuration(shortAnimTime)
			.alpha(loggedIn ? 1 : 0)
			.setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mLoggedFormView.setVisibility(loggedIn ? View.VISIBLE : View.GONE);
				}
			});
			
			mNotLoggedFormView.setVisibility(View.VISIBLE);
			mNotLoggedFormView.animate().setDuration(shortAnimTime)
			.alpha(loggedIn ? 0 : 1)
			.setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mNotLoggedFormView.setVisibility(loggedIn ? View.GONE : View.VISIBLE);
				}
			});
		}else{
			mLoggedFormView.setVisibility(loggedIn ? View.VISIBLE : View.GONE);
			mNotLoggedFormView.setVisibility(loggedIn ? View.GONE : View.VISIBLE);
		}
	}
}
