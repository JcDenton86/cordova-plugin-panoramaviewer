# cordova-plugin-panoramaviewer

I created this plugin for the purposes of my project and decided to make it public. Photosphere plugin uses the <a href="https://developers.google.com/photo-sphere/android/">Panorama API</a> and displays the image in the Photosphere viewer.

# How it works

The plugin accepts two parameters for now, an image url and a title. It downloads the image on the background showing a progress dialog and saves it to a local file. Then it loads the photosphere viewer and displays the image natively.
Tested on:
<ul><li>Android 5.0+</li><li>Android 4.0+</li></ul>

<img src="https://dl.dropboxusercontent.com/u/6816009/photospheredemo.gif"/>

A working example can be found on <a href="https://github.com/JcDenton86/photospherePlugin_example.git"> Photosphere example</a>

# Install

You can install this plugin using common cordova CLI commands:

```
cordova plugin add https://github.com/JcDenton86/gr.denton.photosphere.git
//OR
cordova plugin add cordova-plugin-panoramaviewer
```
# Dependencies

Panorama API is coming through Google Play Services, so make sure you have added `google play services` plugin on your project.

# Usage

Using this plugin is simple:

```
//depending on your object you might have to encode the url before you send it over:
var panomobile = object.pano.url;	
var encodedURL = encodeURI(panomobile);
      
window.plugins.photosphere.view({	  
      imageurl: encodedURL, // = http://photosphereviewer.net/img/demo/demo1.jpg
	  title:"Image Title"},
      function(msg) {console.log(msg)},
      function(error) {console.log(error)}
  )
```

# If you like
You can propose additions and/or contribute or open an issue.



