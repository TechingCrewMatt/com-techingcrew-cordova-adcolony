//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.cordova.plugin.ad.adcolony;

import org.apache.cordova.CordovaPlugin;
//import org.apache.cordova.api.Plugin;
//import org.apache.cordova.api.PluginResult;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import android.annotation.TargetApi;
import android.app.Activity;
import android.util.Log;
//
import com.adcolony.sdk.*;
import org.apache.cordova.PluginResult.Status;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import java.util.Iterator;
//md5
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//Util
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Surface;
//
import java.util.*;//Random
//
import java.util.HashMap;//HashMap
import java.util.Map;//HashMap

class Util {

	//ex) Util.alert(cordova.getActivity(),"message");
	public static void alert(Activity activity, String message) {
		AlertDialog ad = new AlertDialog.Builder(activity).create();  
		ad.setCancelable(false); // This blocks the 'BACK' button  
		ad.setMessage(message);  
		ad.setButton("OK", new DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();                      
			}  
		});  
		ad.show(); 		
	}
	
	//https://gitshell.com/lvxudong/A530_packages_app_Camera/blob/master/src/com/android/camera/Util.java
	public static int getDisplayRotation(Activity activity) {
	    int rotation = activity.getWindowManager().getDefaultDisplay()
	            .getRotation();
	    switch (rotation) {
	        case Surface.ROTATION_0: return 0;
	        case Surface.ROTATION_90: return 90;
	        case Surface.ROTATION_180: return 180;
	        case Surface.ROTATION_270: return 270;
	    }
	    return 0;
	}

	public static final String md5(final String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }
}

public class AdColonyPlugin extends CordovaPlugin {
	private static final String LOG_TAG = "AdColonyPlugin";
	private CallbackContext callbackContextKeepCallback;
	//
	protected String email;
	protected String licenseKey;
	public boolean validLicenseKey;
	protected String TEST_APP_ID = "app873c30909d2a4f8983";
	protected String TEST_INTERSTITIAL_AD_ZONE_ID = "vz8838953078cf4f12aa";
	protected String TEST_REWARDED_VIDEO_AD_ZONE_ID = "vzc6760c29039a4f9fbf";
	//
	protected String appId;
	protected String interstitialAdZoneId;
	protected String rewardedVideoAdZoneId;
	
    @Override
	public void pluginInitialize() {
		super.pluginInitialize();
		//
    }
	
	//@Override
	//public void onCreate(Bundle savedInstanceState) {//build error
	//	super.onCreate(savedInstanceState);
	//	//
	//}
	
	//@Override
	//public void onStart() {//build error
	//	super.onStart();
	//	//
	//}
	
	@Override
	public void onPause(boolean multitasking) {
		super.onPause(multitasking);
	}
	
	@Override
	public void onResume(boolean multitasking) {
		super.onResume(multitasking);
	}
	
	//@Override
	//public void onStop() {//build error
	//	super.onStop();
	//	//
	//}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//
	}
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		if (action.equals("setUp")) {
			return true;
			//setUp(action, args, callbackContext);
		}			
		else if (action.equals("showInterstitialAd")) {
			showInterstitialAd(action, args, callbackContext);
						
			return true;
		}
		else if (action.equals("showRewardedVideoAd")) {
			showRewardedVideoAd(action, args, callbackContext);
						
			return true;
		}
		
		return false; // Returning false results in a "MethodNotFound" error.
	}


	private void setUp(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		//Activity activity=cordova.getActivity();
		//webView
		//args.length()
		//args.getString(0)
		//args.getString(1)
		//args.getInt(0)
		//args.getInt(1)
		//args.getBoolean(0)
		//args.getBoolean(1)
		//JSONObject json = args.optJSONObject(0);
		//json.optString("adUnitBanner")
		//json.optString("adUnitInterstitial")
		//JSONObject inJson = json.optJSONObject("inJson");
		//final String adUnitBanner = args.getString(0);
		//final String adUnitInterstitial = args.getString(1);				
		//final boolean isOverlap = args.getBoolean(2);				
		//final boolean isTest = args.getBoolean(3);
		//final String[] zoneIds = new String[args.getJSONArray(4).length()];
		//for (int i = 0; i < args.getJSONArray(4).length(); i++) {
		//	zoneIds[i] = args.getJSONArray(4).getString(i);
		//}			
		//Log.d(LOG_TAG, String.format("%s", adUnitBanner));			
		//Log.d(LOG_TAG, String.format("%s", adUnitInterstitial));
		//Log.d(LOG_TAG, String.format("%b", isOverlap));
		//Log.d(LOG_TAG, String.format("%b", isTest));	
		final String appId = args.getString(0);
		final String interstitialAdZoneId = args.getString(1);
		final String rewardedVideoAdZoneId = args.getString(2);
		Log.d(LOG_TAG, String.format("%s", appId));			
		Log.d(LOG_TAG, String.format("%s", interstitialAdZoneId));			
		Log.d(LOG_TAG, String.format("%s", rewardedVideoAdZoneId));			
		
		callbackContextKeepCallback = callbackContext;
			
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setUp(appId, interstitialAdZoneId, rewardedVideoAdZoneId);
			}
		});
	}
	
	private void showInterstitialAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showInterstitialAd();
			}
		});
	}

	private void showRewardedVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showRewardedVideoAd();
			}
		});
	}
		
	private void _setUp(String appId, String interstitialAdZoneId, String rewardedVideoAdZoneId) {
		this.appId = appId;
		this.interstitialAdZoneId = interstitialAdZoneId;
		this.rewardedVideoAdZoneId = rewardedVideoAdZoneId;
		String optionString = "";
		//version - arbitrary application version
		//store   - google or amazon
		//String optionString = "version:1.0,store:google";
 
	    try{
			String[] zoneIds = new String[2];
			zoneIds[0] = this.interstitialAdZoneId;
			zoneIds[1] = this.rewardedVideoAdZoneId;

			//AdColony.configure(cordova.getActivity(), optionString, this.appId, zoneIds);
			//AdColony.addAdAvailabilityListener(new MyAdColonyAdAvailabilityListener());
			//AdColony.addV4VCListener(new MyAdColonyV4VCListener());
			AdColonyAppOptions app_options = new AdColonyAppOptions().setUserID( "unique_user_id" );
			AdColony.configure(cordova.getActivity(),app_options, this.appId, zoneIds);
	//		AdColony.addAdAvailabilityListener(new MyAdColonyAdAvailabilityListener());	
			AdColony.setRewardListener(new MyAdColonyRewardListener());
			}
		catch(Exception e){
			PluginResult pr4 = new PluginResult(PluginResult.Status.ERROR, "setup failed in Java");
			pr4.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr4);
		}
	}

	private void _showInterstitialAd() {
	
//		AdColonyVideoAd ad = new AdColonyVideoAd(interstitialAdZoneId);
//		ad.withListener(new AdColonyAdListenerInterstitialAd());
//		ad.show();
		AdColonyAdOptions ad_options;
        AdColonyUserMetadata metadata = new AdColonyUserMetadata()
                .setUserAge( 26 )
                .setUserEducation( AdColonyUserMetadata.USER_EDUCATION_BACHELORS_DEGREE )
                .setUserGender( AdColonyUserMetadata.USER_MALE );
        ad_options = new AdColonyAdOptions()
				.setUserMetadata(metadata);		
		AdColony.requestInterstitial(interstitialAdZoneId, new MyAdColonyInterstitialListenerInterstitialAd(), ad_options );
	}

	private void _showRewardedVideoAd() {
		
//		AdColonyV4VCAd ad = new AdColonyV4VCAd(rewardedVideoAdZoneId);
//		ad.withListener(new AdColonyAdListenerRewardedVideoAd());
//		//ad.withConfirmationDialog().withResultsDialog();
//		ad.show();
//		//ad.getRewardName()
//		//ad.getAvailableViews()
		AdColonyAdOptions ad_options;
        AdColonyUserMetadata metadata = new AdColonyUserMetadata()
                .setUserAge( 26 )
                .setUserEducation( AdColonyUserMetadata.USER_EDUCATION_BACHELORS_DEGREE )
                .setUserGender( AdColonyUserMetadata.USER_MALE );
        ad_options = new AdColonyAdOptions()
                .enableConfirmationDialog( true )
                .enableResultsDialog( true )
                .setUserMetadata( metadata );        			
		AdColony.requestInterstitial(rewardedVideoAdZoneId, new MyAdColonyInterstitialListenerRewardedVideoAd(), ad_options );
	}
	
/*
	class MyAdColonyAdAvailabilityListener extends AdColonyAdAvailabilityListener {
		// Ad Availability Change Callback - update button text
		public void onAdColonyAdAvailabilityChange(boolean available, String zone_id) {
			Log.d(LOG_TAG, String.format("%s: %b", "onAdColonyAdAvailabilityChange", available));
			PluginResult pr = new PluginResult(PluginResult.Status.OK, zone_id + ", " + rewardedVideoAdZoneId);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);		
			if (available) {
				if(zone_id.equals(interstitialAdZoneId)) {
					PluginResult pr = new PluginResult(PluginResult.Status.OK, "onInterstitialAdLoaded");
					pr.setKeepCallback(true);
					callbackContextKeepCallback.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//callbackContextKeepCallback.sendPluginResult(pr);			
				}
				else if(zone_id.equals(rewardedVideoAdZoneId)) {
					PluginResult pr = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdLoaded");
					pr.setKeepCallback(true);
					callbackContextKeepCallback.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//callbackContextKeepCallback.sendPluginResult(pr);

				}
			}
		}
	}
*/

	class MyAdColonyRewardListener implements AdColonyRewardListener {

		@Override
		public void onReward( AdColonyReward reward )
		{
			Log.d(LOG_TAG, String.format("%s: %b", "onReward", reward.success()));
			PluginResult pr = new PluginResult(PluginResult.Status.OK, reward.success());
				pr.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr);
			if (reward.success()) {				
				//reward.name();
				//reward.amount();
								
				PluginResult pr1 = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdCompleted");
				pr1.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr1);;				
			}
			else{
				PluginResult pr2 = new PluginResult(PluginResult.Status.ERROR, "onReward Failed");
				pr2.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr2);		
			}
		}		
	}
	
	class MyAdColonyInterstitialListenerInterstitialAd extends AdColonyInterstitialListener {
		
		/** Ad passed back in request filled callback, ad can now be shown */
		@Override
		public void onRequestFilled( AdColonyInterstitial ad )
		{
			Log.d(LOG_TAG, String.format("%s", "onRequestFilled"));
			ad.show();
		}

		/** Ad request was not filled */
		@Override
		public void onRequestNotFilled( AdColonyZone zone )
		{
			Log.d(LOG_TAG, String.format("%s", "onRequestNotFilled"));
		}

		/** Ad opened, reset UI to reflect state change */
		@Override
		public void onOpened( AdColonyInterstitial ad )
		{
			Log.d(LOG_TAG, String.format("%s", "onOpened"));
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onInterstitialAdShown");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//callbackContextKeepCallback.sendPluginResult(pr);		
		}

		/** Request a new ad if ad is expiring */
		@Override
		public void onExpiring( AdColonyInterstitial ad )
		{
//			AdColony.requestInterstitial( ZONE_ID, this, ad_options );//

			Log.d(LOG_TAG, String.format("%s", "onExpiring"));
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onInterstitialAdHidden");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//callbackContextKeepCallback.sendPluginResult(pr);	
		}
	}

	class MyAdColonyInterstitialListenerRewardedVideoAd extends AdColonyInterstitialListener {
		
		/** Ad passed back in request filled callback, ad can now be shown */
		@Override
		public void onRequestFilled( AdColonyInterstitial ad )
		{
			Log.d(LOG_TAG, String.format("%s", "onRequestFilled"));
			ad.show();
		}

		/** Ad request was not filled */
		@Override
		public void onRequestNotFilled( AdColonyZone zone )
		{
			Log.d(LOG_TAG, String.format("%s", "onRequestNotFilled"));
		}

		/** Ad opened, reset UI to reflect state change */
		@Override
		public void onOpened( AdColonyInterstitial ad )
		{
			Log.d(LOG_TAG, String.format("%s", "onOpened"));
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdShown");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//callbackContextKeepCallback.sendPluginResult(pr);	
		}

		/** Request a new ad if ad is expiring */
		@Override
		public void onExpiring( AdColonyInterstitial ad )
		{
//			AdColony.requestInterstitial( ZONE_ID, this, ad_options );//
	
			Log.d(LOG_TAG, String.format("%s", "onExpiring"));
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdHidden");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//callbackContextKeepCallback.sendPluginResult(pr);	
		}		
	}
}
