var exec = require('cordova/exec');
module.exports = {
    _SUCCESS: false,
    setup: function (arg0, success, error) {
        exec(success, error, "AdColonyPlugin", "setup", arg0)
    },
    showVideo: function (arg0, success, error) {
        exec(success, error, "AdColonyPlugin", "showVideo", [arg0])
    },
    requestVideo: function(arg0, success, error){
        exec(success, error, "AdColonyPlugin", "requestVideo", [arg0])
    }
};
