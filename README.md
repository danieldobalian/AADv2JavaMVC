# Azure AD Java MVC using the v2 Endpoint

This is a Java MVC app built in Spring that uses the Azure AD v2 endpoint to get authorization for the Microsoft Graph.  

## Authors

Daniel Dobalian ([dadobali@microsoft.com](mailto:dadobali@microsoft.com))

## Steps to Run

If you don't have a functioning environment that allows you to run Spring MVC apps, I highly recommend using the [Crunchify Hello World](http://crunchify.com/simplest-spring-mvc-hello-world-example-tutorial-spring-model-view-controller-tips/) sample to get up and running.

Once you have the environment configured, all you should go to the [Azure AD v2 Portal](https://identity.microsoft.com/#/appList) and register an app with whatever name you would like, and add the web platform.  You'll want to add the redirect URI that your Tomcat server is hosted on, my code uses ```http://localhost:8080/AADv2JavaWebApp/```. Finally, you'll need to hit "Generate New Password" to generate your secret.  Once that's done, go ahead and add all the app configs into your MicrosoftLogin.java.

If you followed the guide above, you should now be able to use the same steps to run the MVC app with Tomcat.  Once you hit ```/login``` it will initiate the auth flow. 

## References

This app uses the Scribe Java oAuth2.0 Library for all Identity code, Spring for the simple MVC app, and Apache Tomcat to host the local webserver.

###Frameworks

[Scribe Java](https://github.com/scribejava/scribejava)

[Apache Tomcat](http://tomcat.apache.org/)

[Spring](https://spring.io/)

###Helpful Samples 

[Setting up your environment & Spring Sample](http://crunchify.com/simplest-spring-mvc-hello-world-example-tutorial-spring-model-view-controller-tips/)

[Spring MVC Sample](https://www.tutorialspoint.com/spring/spring_mvc_hello_world_example.htm)

[Using the State Parameter](https://www.jasha.eu/blogposts/2013/09/facebook-profile-data-java-scribe.html)
