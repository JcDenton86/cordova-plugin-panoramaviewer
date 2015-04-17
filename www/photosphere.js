var Photosphere = function() {};
            
Photosphere.prototype.view = function(content, success, fail) {
    return cordova.exec( function(args) {
        success(args);
    }, function(args) {
        fail(args);
    }, 'Photosphere', '', [content]);
};

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.photosphere) {
    window.plugins.photosphere = new Photosphere();
}