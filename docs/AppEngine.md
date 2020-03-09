# App Engine

## Prerequisites

We assume java in this tutorial, so we install:

```
gcloud components install app-engine-java
```

## Clone sample app

```
git clone https://github.com/GoogleCloudPlatform/appengine-try-java
cd appengine-try-java
```

## Test app

1. Setup google cloud sdk path and test app:

```
set GOOGLE_CLOUD_SDK=PATH_TO/google-cloud-sdk
mvn appengine:run 

# or
mvn appengine:run -DcloudSdkPath=GOOGLE_CLOUD_SDK
```

Notice, path must be to root dir, not to bin dir!!!

If `mvn appengine:run` has error: 

```
com.google.apphosting.utils.config.AppEngineConfigException: Received IOException parsing the input stream.
	at com.google.apphosting.utils.config.XmlUtils.parseXml(XmlUtils.java:52)
	at com.google.apphosting.utils.config.XmlUtils.parseXml(XmlUtils.java:40)
	at com.google.apphosting.utils.config.WebXmlReader.processXml(WebXmlReader.java:119)
	at com.google.apphosting.utils.config.WebXmlReader.processXml(WebXmlReader.java:20)
	at com.google.apphosting.utils.config.AbstractConfigXmlReader.readConfigXml(AbstractConfigXmlReader.java:89)
	at com.google.apphosting.utils.config.WebXmlReader.readWebXml(WebXmlReader.java:96)
	at com.google.appengine.tools.admin.Application.<init>(Application.java:288)
	at com.google.appengine.tools.admin.Application.readApplication(Application.java:532)
	at com.google.appengine.tools.admin.AppCfg.readWar(AppCfg.java:303)
	at com.google.appengine.tools.admin.AppCfg.readApplication(AppCfg.java:281)
	at com.google.appengine.tools.admin.AppCfg.<init>(AppCfg.java:209)
	at com.google.appengine.tools.admin.AppCfg.<init>(AppCfg.java:118)
	at com.google.appengine.tools.admin.AppCfg.main(AppCfg.java:114)
Caused by: java.net.UnknownHostException: java.sun.com
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:184)
	at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:172)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
	at java.net.Socket.connect(Socket.java:589)
	at java.net.Socket.connect(Socket.java:538)
```

Download dtd and xml files from `web.xml` manually and set absolute path to it. 

2. Type in browser`localhost:8080`

## Create app

1. Create application:

```
gcloud app create
```

2. Deploy app

```
mvn appengine:deploy
```

3. Open browser and go to `https://PROJECT_ID.appspot.com/`

