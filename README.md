# com-techingcrew-cordova-adcolony
Free and open source Cordova plugin for AdColony rewarded videos

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
