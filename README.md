# Disaster Response Project

Disaster Response and Monitoring built on AWS, Bluemix and Azure Mashup

- (1) Getting internet access via SMS can help you out of disaster.
- (2) Real-time Weather Monitoring and Predict Map
- (3) Twitter sentiment Anaylsis and #hashtag heat Monitoring

### Tech and APIs
* IBM Bluemix
* Microsoft Azure
* Twilio
* Twitter API 
* Apache Spark
* Twitter Bootstrap - great UI boilerplate for modern web apps
* node.js - evented I/O for the backend
* Express - fast node.js network app framework [@tjholowaychuk]
* weather_api_url - http://www.wunderground.com/weather/api/
* leaflet_url - http://leafletjs.com/
* esri_leaflet_url - http://esri.github.io/esri-leaflet/
* geocoding_api_url - https://developers.google.com/maps/documentation/geocoding/intro




### Installation: Twilio (get internet access via SMS)

The easiest way to install twilio-python is from PyPi using pip, a package manager for Python. Simply run this in the terminal:

```sh
$ git clone https://github.com/ohyeslk/DisasterResponse.git
$ cd DisasterResponse
```

```sh
$ pip install twilio
$ easy_install twilio
$ python setup.py install
$ npm i -d
```

### Installation: Real-time Weather Monitoring and Predict Map 

It utilizes the Weather Channel service, Leaflet and Esri Leaflet mapping APIs, and the Google Maps Geocoding API. It allows users to visualize the current weather in all the world capitals, as well as obtain historical weather data and future weather projections. 
##### Run the app locally
1. Create a Bluemix Account. You will need this to create a Weather Channel service and grab the credentials later on.

[Sign up][bluemix_signup_url] in Bluemix, or use an existing account.

2. If you have not already, [download node.js][download_node_url] and install it on your local machine.

3. Clone the app to your local environment from your terminal using the following command:

```
git clone https://github.com/ohyeslk/DisasterResponse.git
```

4. `cd` into this newly created directory

5. Install the required npm and bower packages using the following command

```
npm install
```

6. Create a Weather Channel service using your Bluemix account and replace the corresponding credentials in your `vcap-local.json` file.

7. Start your app locally with the following command.

```
npm run watch
```

This command will trigger [`cake`][cake_url] to build and start your application. When your app has started, your console will print that your `server started on: http://localhost:0926`.

Since we are using `cake`, the app is rebuilt continuously as changes are made to the local file system. Therefore, you do not have to constantly stop and restart your app as you develop locally. Run `npm run cake` to see the other commands available in the `Cakefile`.

### Installation: Twitter sentiment Anaylsis and #hashtag heat 
https://azure.microsoft.com/en-us/documentation/articles/stream-analytics-twitter-sentiment-analysis-trends/

##### Social media analysis: Real-time Twitter sentiment analysis in Azure Stream Analytics

- Create an Event Hub input and a Consumer Group
- Configure and start the Twitter client application
- Create Stream Analytics job
- Create output sink
- Specify job output
- Start job
- View output for sentiment analysis


```sh
$ git clone https://github.com/ohyeslk/DisasterResponse.git
$ cd DisasterResponse/TwitterAssist
```

```sh
$ npm install
$ npm test
```

### Future Work

- Scale up test
- UI beatutify

License
----

MIT

Contact
----
Lan Luo, Kai Lu, Weining Bao, Zhixian Huang, Sichao Wang

