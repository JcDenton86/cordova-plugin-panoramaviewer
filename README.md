# gr.denton.photosphere

I created this plugin for the purposes of my project and decided to make it public. Photosphere plugin uses the <a href="https://developers.google.com/photo-sphere/android/">Panorama API</a> and displays the image in the Photosphere viewer.
Photospere plugin downloads an image on the background from a user specified URL and saves it to a local file. Then it loads the photosphere viewer and displays the image natively

An working example can be found on <a href="https://github.com/JcDenton86/photospherePlugin_example.git"> Photosphere example</a>

# Install

You can install this plugin using common cordova CLI commands:

`cordova plugin add https://github.com/JcDenton86/gr.denton.photosphere.git`

#Dependencies

Panorama API is coming through Google Play Services, so make sure you have added `google play services` plugin on your project.

#Usage

Using this plugin is simple:

```
window.plugins.photosphere.view({
      imageurl:"http://photosphereviewer.net/img/demo/demo1.jpg"},
      function() {},
      function() {}
  )
```

#To Do
1. add option to load a panorama image from a local folder


