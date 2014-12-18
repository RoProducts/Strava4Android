package de.rooehler.strava4android;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class StravaDialog  extends Dialog  {

	private static final String TAG = StravaDialog.class.getSimpleName();

	static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

	private String mUrl;
	private StravaDialogListener mListener;
	private ProgressDialog mSpinner;
	private WebView mWebView;
	private LinearLayout mContent;
	private String mCallbackUri;

	public StravaDialog(Context context, String url, String callbackUri, StravaDialogListener listener) {
		super(context);

		mUrl 			= url;
		mListener 		= listener;
		mCallbackUri 	= callbackUri;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSpinner = new ProgressDialog(getContext());

		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSpinner.setMessage(getContext().getString(R.string.loading));

		mContent = new LinearLayout(getContext());   
		mContent.setOrientation(LinearLayout.VERTICAL);

		setUpWebView();

		float[] dimensions = {getContext().getResources().getDisplayMetrics().widthPixels  * 9 / 10.0f,
				getContext().getResources().getDisplayMetrics().heightPixels * 9 / 10.0f};


		addContentView(mContent, new FrameLayout.LayoutParams((int) (dimensions[0] + 0.5f ),(int) (dimensions[1] + 0.5f )));

	}


	private void setUpWebView() {
		mWebView = new WebView(getContext());

		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);

		mWebView.setWebViewClient(new StravaWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(mUrl);
		mWebView.setLayoutParams(FILL);

		mContent.addView(mWebView);
	}

	private class StravaWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d(TAG, "Redirecting URL " + url);

			if (url.startsWith(mCallbackUri)) {
				mListener.onComplete(url);

				StravaDialog.this.dismiss();

				return true;
			}  else if (url.startsWith("authorize")) {
				return false;
			}

			return false;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);

			mListener.onError(description);

			StravaDialog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);

			if(mSpinner != null){
				mSpinner.show();
			}

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);

			if(mSpinner != null && mSpinner.isShowing()){
				mSpinner.dismiss();
			}

		}

	}

	public interface StravaDialogListener {

		public void onComplete(String value);		

		public void onError(String value);
	}
}
