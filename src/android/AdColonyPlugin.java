package com.TechingCrew.Cordova.AdColonyPlugin;

import org.apache.cordova.CordovaPlugin; 
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.adcolony.sdk.*;


public class AdColonyPlugin extends CordovaPlugin {
    protected String appID;
	protected String interstitialAdZoneId;
	protected String rewardedVideoAdZoneId;
	private CallbackContext callbackContext;
	private AdColonyInterstitial ad;
	private AdColonyInterstitialListener listener;
	private AdColonyAppOptions app_options;
	//private AdColonyUserMetadata metadata = new AdColonyUserMetadata()
	//	.setUserAge( 26 )
	//	.setUserEducation( AdColonyUserMetadata.USER_EDUCATION_BACHELORS_DEGREE )
	//	.setUserGender( AdColonyUserMetadata.USER_MALE );
	private AdColonyAdOptions ad_options = new AdColonyAdOptions()
		.enableConfirmationDialog( false )
		.enableResultsDialog( false );
		//.setUserMetadata( metadata );
	private AdColonyRewardListener rewardListener;

	@Override
	public void onPause(boolean multitasking) {
		super.onPause(multitasking);
	}
	
	@Override
	public void onResume(boolean multitasking) {
		super.onResume(multitasking);
		if (ad == null || ad.isExpired())
        {
            /**
             * Optionally update location info in the ad options for each request:
             * LocationManager location_manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
             * Location location = location_manager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
             * ad_options.setUserMetadata( ad_options.getUserMetadata().setUserLocation( location ) );
             */
            AdColony.requestInterstitial( rewardedVideoAdZoneId, listener, ad_options );
        }
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//
	}

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext2) throws JSONException {
		if (action.equals("setup")) {
			callbackContext = callbackContext2;
			
			appID = args.getString(0);
			rewardedVideoAdZoneId = args.getString(1);
			setup();
            return true;
        }
		else if (action.equals("showVideo")) {
			showRewardedVideoAd();
            return true;
        }
		else if(action.equals("requestVideo")){
			requestVideo();
			return true;
		}
        return false;
    }

	@Override
	public void pluginInitialize() {
		super.pluginInitialize();
		//
    }

	private void requestVideo() throws JSONException{
		PluginResult pr8 = new PluginResult(PluginResult.Status.OK, "Request Video");
		pr8.setKeepCallback(true);
		callbackContext.sendPluginResult(pr8);	
		AdColony.requestInterstitial(rewardedVideoAdZoneId, listener, ad_options);
	}

	private void setup() throws JSONException {
	/** Construct optional app options object to be sent with configure */
		cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
				
					app_options = new AdColonyAppOptions()
							.setUserID( "123456789" );
					AdColony.configure(cordova.getActivity(), app_options, appID, rewardedVideoAdZoneId); 
					rewardListener = new AdColonyRewardListener() {
						@Override
						public void onReward(AdColonyReward reward) {
							/** Query the reward object for information here */
							if (reward.success()) {							
								//callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdCompleted"));
								PluginResult pr5 = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdCompleted");
								pr5.setKeepCallback(true);
								callbackContext.sendPluginResult(pr5);		
							}
							else{
								//callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "onReward Failed"));
								PluginResult pr6 = new PluginResult(PluginResult.Status.OK, "onReward Failed");
								pr6.setKeepCallback(true);
								callbackContext.sendPluginResult(pr6);				
							}
							//AdColony.requestInterstitial(rewardedVideoAdZoneId, listener, ad_options);
						}
					};

					/** Set reward listener for your app to be alerted of reward events */
					AdColony.setRewardListener(rewardListener);
					listener = new AdColonyInterstitialListener(){
						/** Ad passed back in request filled callback, ad can now be shown */
						@Override
						public void onRequestFilled( AdColonyInterstitial ad2 )
						{
							ad = ad2;
							PluginResult pr = new PluginResult(PluginResult.Status.OK, "onRequestFilled");
							pr.setKeepCallback(true);
							callbackContext.sendPluginResult(pr);
						}

						/** Ad request was not filled */
						@Override
						public void onRequestNotFilled( AdColonyZone zone )
						{
							PluginResult pr2 = new PluginResult(PluginResult.Status.OK, "onRequestNotFilled");
							pr2.setKeepCallback(true);
							callbackContext.sendPluginResult(pr2);
							//Log.d(LOG_TAG, String.format("%s", "onRequestNotFilled"));
							//AdColony.requestInterstitial(rewardedVideoAdZoneId, listener, ad_options );
						}

						/** Ad opened, reset UI to reflect state change */
						@Override
						public void onOpened( AdColonyInterstitial ad )
						{

						}

						/** Request a new ad if ad is expiring */
						@Override
						public void onExpiring( AdColonyInterstitial ad )
						{

						}
					};		
					AdColony.requestInterstitial(rewardedVideoAdZoneId, listener, ad_options);
				}
		});
		
	}

	private void showRewardedVideoAd() throws JSONException {

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				if(ad != null){
					ad.show();
					AdColony.requestInterstitial(rewardedVideoAdZoneId, listener, ad_options );
					//callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "New Video Requested Java"));
				}
				else{
					//callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "ad NULL"));
					PluginResult pr7 = new PluginResult(PluginResult.Status.OK, "ad NULL");
					pr7.setKeepCallback(true);
					callbackContext.sendPluginResult(pr7);
				}
			}
		});
	}

	class MyAdColonyInterstitialListenerRewardedVideoAd extends AdColonyInterstitialListener {
		
		/** Ad passed back in request filled callback, ad can now be shown */
		@Override
		public void onRequestFilled( AdColonyInterstitial ad2 )
		{
			ad = ad2;
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onRequestFilled");
			pr.setKeepCallback(true);
			callbackContext.sendPluginResult(pr);
		}

		/** Ad request was not filled */
		@Override
		public void onRequestNotFilled( AdColonyZone zone )
		{
			PluginResult pr2 = new PluginResult(PluginResult.Status.OK, "onRequestNotFilled");
			pr2.setKeepCallback(true);
			callbackContext.sendPluginResult(pr2);
			//Log.d(LOG_TAG, String.format("%s", "onRequestNotFilled"));
		}

		/** Ad opened, reset UI to reflect state change */
		@Override
		public void onOpened( AdColonyInterstitial ad )
		{
			PluginResult pr9 = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdShown");
			pr9.setKeepCallback(true);
			callbackContext.sendPluginResult(pr9);
		}

		/** Request a new ad if ad is expiring */
		@Override
		public void onExpiring( AdColonyInterstitial ad )
		{
			PluginResult pr8 = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdHidden");
			pr8.setKeepCallback(true);
			callbackContext.sendPluginResult(pr8);
		}		
	}
}

	

	
