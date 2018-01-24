package product.catalog.unittest;


import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.pegaxchange.services.Product;
import com.pegaxchange.services.ProductCatalogInterface;
import com.pegaxchange.services.ProductCatalogServiceImpl;

@Category(UnitTest.class)
public class ProductCatalogJUnitTest 
{
	@Test
	public void ProductCatalogValidate() throws MalformedURLException 
	{
		Endpoint endpoint = Endpoint.publish("http://localhost:8083/any/name/you/like/productcatalogservice", new ProductCatalogServiceImpl());
		URL url = new URL("http://localhost:8083/any/name/you/like/productcatalogservice?WSDL");
		assertTrue(endpoint.isPublished());
	    assertEquals("http://schemas.xmlsoap.org/wsdl/soap/http", endpoint.getBinding().getBindingID());
		
		//first parameter is the TargetName from WSDL, and the second param is the Server Name from WSDL
		QName qname = new QName("http://services.pegaxchange.com/",
				"ProductCatalogServiceImplService");
		
		System.out.println("LOCAL " + qname.getLocalPart());
		
		Service service = Service.create(url, qname);
		
		//port is the webservice class where all the methods are provided by the service.
		ProductCatalogInterface server = service.getPort(ProductCatalogInterface.class);
		
		//getAllProducts returns a list of products, the server class is created as an instance of
		//the service class interface.
		server.getAllProducts();
		
		//creating an object of Product and calling the method provided by the server class.
		Product product = server.searchById(5);
		System.out.println("SEARCH BY ID: " + product.getName());
		System.out.println();
		
		assertEquals("Slot Screwdriver", product.getName());
		
		//inserting new product
		product.setId(10);
		product.setCategory("Tools");
		product.setName("Drill#35");
		product.setUnitPrice(59.99);
		
		server.insertProduct(product);
		
		Product[] productList2 = server.getAllProducts();
		
		assertEquals("Drill#35", product.getName());
	}

}
