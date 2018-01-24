package com.pegaxchange.services;

import javax.xml.ws.Endpoint;

public class Exporter 
{
	//We create our own URL and make sure Tomcat is closed for now.
	//This URL is given to the third party clients.  As long as we are using the Oracle JDK,
	//this endpoint should work.
	//You don't access web services using browsers, you access web services using code.
	public static void main(String[] args)
	{
		Endpoint.publish("http://localhost:8083/any/name/you/like/productcatalogservice", 
				new ProductCatalogServiceImpl());
	}

}
