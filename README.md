# com-techingcrew-cordova-adcolony
Free and open source Cordova plugin for AdColony rewarded videos

1. Install the plugin
2. Add the following JavaScript functions to your project.
3. Replace the arguments in initColony with your development App Id and Zone Id. In production, requests fro video ads are not filled as      often as you'd like. Test your implementation with development credentials.
4. On deviceready call initColony().
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
                adcolony._SUCCESS = true;
            }
            var error = function (e) {
                console.log(e);
            }

            function showVideo() {
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
