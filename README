### geocode-tool is a tool help us reverse accurate city by latitude and longitude.

##### there are four main features from tool
1. data crawling from amqp(高德)
2. GUI(swing) for user to reverse location
3. Java api for user to reverse location
4. Unit testing to verify if the function is correct. generate China's coordinate by a function and assert with amqp(高德) restapi.


##### Architecture diagram

```
sequenceDiagram
geocodetool->>amap: get data with http
amap->>geocodetool: save to disk json file
geocodetool->>gui: query gui
geocodetool->>api: java api
```


##### Development environment
JDK:1.8
Maven:3.5.2


##### Reference link:
amap: https://lbs.amap.com/api/webservice/summary/
