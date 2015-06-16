var Photosphere = function() {};
            
Photosphere.prototype.view = function(content, success, fail) {
    return cordova.exec( function(args) {
        success(args);
    }, function(args) {
        fail(args);
    }, 'Photosphere', '', [content]);
};

Photosphere.prototype.IMAGE_SOURCE = {
  LOAD_REMOTE_IMAGE         : 1, // default
  LOAD_LOCAL_IMAGE            : 2
};

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.photosphere) {
    window.plugins.photosphere = new Photosphere();
}