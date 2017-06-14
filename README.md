# com-techingcrew-cordova-adcolony
Free and open source Cordova plugin for AdColony rewarded videos.

-Tested on Samsung Galaxy S6 using Android 7.0 and project built using CLI 6.1.1
-iOS version is in development.

1. Install the plugin
<pre><code>cordova plugin add com-techingcrew-cordova-adcolony</code></pre>
2. Add the following JavaScript functions to your project.
<pre>
  <code>
            var success = function (result) {
                console.log(result);
                if (result === 'onRequestFilled') {
                      console.log('Video Request Filled');
                }
                if (result === 'onRewardVideoAdCompleted') {
                    console.log('Reward Video Completed. Reward the user and request another video.');
                    requestVideo();
                }
                if (result === 'onReward Failed'){
                    console.log('Reward video did not complete successfully. The user may have exited the video.');
                }
                onReward Failed
                adcolony._SUCCESS = true;
            }
            
            var error = function (e) {
                console.log(e);
            }

            function showVideo([], success, error) {
                window.adcolony.showVideo();
            }

            function requestVideo() {
                window.adcolony.requestVideo([], success, error);
            }

            function initColony() {
                window.adcolony.setup(['xxxxAppIdxxxx', 'xxxxZoneIdxxxx'], success, error);
            }
            
  </code>
</pre>
3. Replace the arguments in initColony with your development App Id and Zone Id. In production, requests for video ads are not filled as      often as you'd like. Test your implementation with development credentials.
4. On deviceready call initColony. This initializes AdColony and requests a video to be played by calling showVideo.
5. Create a link or button that calls showVideo onclick.
