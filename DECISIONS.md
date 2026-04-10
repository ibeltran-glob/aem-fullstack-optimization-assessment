en weather parece que el modelo no es necesario, se ha sustituido por objetos globales de HTL



---------------------------- END NOTES AREA -------------------------------------------

Here I will explain all modifications I have made.

All the API Calls were placed at WeatherServiceImpl.java. This allows to create a configuration file and remove all api keys references from the rest of the project.

Since the WeatherServiceImpl.java already has a method to make the API call, I have removed the API call from WeatherModel.java. I used the model to include all the logic and all the files of weather component including the temperature and the description.

I did not implement any JavaScript code and AEM servlets for get the information from the API because I realized that the city is a content introduced in the component's dialog, and it is not a content that a webpage user introduces. So, it is not neccesary to implement any JavaScript behaviour. 

With those decisions I pretend to obfuscate the API call and the API Key at client side. Also, since the city is a content introduced by AEM authors, the API is called in server side and catched by the dispatcher.

In the dispatcher I have followed a Deny-allow strategy on which it is recommended to deny all and allow those things needed in your website.