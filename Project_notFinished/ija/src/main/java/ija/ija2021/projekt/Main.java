/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represent working of main function. Opens all files, read data from XML and transfer control to MainController.
 * Date: 07.05.2021
 */

package ija.ija2021.projekt;

import ija.ija2021.projekt.classes.Goods;
import ija.ija2021.projekt.classes.GoodsItem;
import ija.ija2021.projekt.classes.Shelf;
import ija.ija2021.projekt.classes.*;

import ija.ija2021.projekt.settings.Drawable;
import ija.ija2021.projekt.settings.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

public class Main extends Application {

//	private static final String FILENAME = "./data/map_base.xml";
    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiLayout.fxml"));
        
        BorderPane root_element = loader.load(); 
        Scene scene = new Scene(root_element);
        primaryStage.setScene(scene); 
        primaryStage.show();
        

        MainController controller = loader.getController();
        controller.addMain(this);
        this.startTime(controller);
    }

    public void startTime(MainController controller){
        ArrayList<Drawable> elements = new ArrayList<>();
        Base base = null;

     // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Add XML data to program
        try {	
        	
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File("./data/map_base.xml"));
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            NodeList baseEl = doc.getElementsByTagName("base");
            
			Node baseNode = baseEl.item(0);
			if (baseNode.getNodeType() == Node.ELEMENT_NODE) {

				Element baseElement = (Element) baseNode;
				String x = baseElement.getElementsByTagName("x").item(0).getTextContent();
				String y = baseElement.getElementsByTagName("y").item(0).getTextContent();

				base = new Base(Double.parseDouble(x), Double.parseDouble(y), controller);			
			}
            // adding streets to Base
            NodeList list = doc.getElementsByTagName("street");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

//                    // get staff's attribute
                    String id = element.getAttribute("id");
                    
                    String start_x = element.getElementsByTagName("start_x").item(0).getTextContent();
                    String start_y = element.getElementsByTagName("start_y").item(0).getTextContent();
                    String end_x = element.getElementsByTagName("end_x").item(0).getTextContent();
                    String end_y = element.getElementsByTagName("end_y").item(0).getTextContent();

                    
                    Street createStreet = new Street(Double.parseDouble(start_x), Double.parseDouble(start_y), 
                    Double.parseDouble(end_x), Double.parseDouble(end_y), Integer.parseInt(id), controller, base);
                    base.addStreet(createStreet);
                    NodeList childList =  element.getChildNodes();
                    for(int tmp = 0; tmp < childList.getLength(); tmp++) {
                        Node myNode = childList.item(tmp);
                        if(myNode.getNodeName() == "shelf") {
                            Element myElement = (Element) myNode;
                            String shelf_id = myElement.getAttribute("id");
                            String shelf_x = myElement.getElementsByTagName("x").item(0).getTextContent();
                            String shelf_y = myElement.getElementsByTagName("y").item(0).getTextContent();

                            String stop_x = myElement.getElementsByTagName("stop_x").item(0).getTextContent();
                            String stop_y = myElement.getElementsByTagName("stop_y").item(0).getTextContent();

                            Shelf createShelf = new Shelf(Double.parseDouble(shelf_x), Double.parseDouble(shelf_y),
                            Double.parseDouble(stop_x), Double.parseDouble(stop_y), Integer.parseInt(shelf_id), controller, createStreet);
                            
                            base.addToStopList(createShelf.getStop());
                            base.addToShelfList(createShelf);

                            NodeList goodsInsideList = myElement.getElementsByTagName("goods_type");
                            for(int inside = 0; inside < goodsInsideList.getLength(); inside++){
                                Node goodsInside = goodsInsideList.item(inside);
                                Element goodsElement = (Element) goodsInside;
                                String goodsName = goodsElement.getAttribute("name");
                                String itemCount = goodsElement.getTextContent();

                                createShelf.addToProductInform(new ProductInform(goodsName, Integer.parseInt(itemCount)));
                                base.addToGoodsList(goodsName);
                                Goods goods = base.getGoods(goodsName);
                                for (int itemCounter = 0; itemCounter < Integer.parseInt(itemCount); itemCounter++){
                                    createShelf.put(new GoodsItem(goods, LocalDate.now()));
                                }

                            }
                            createStreet.addToShelfList(createShelf);
                        } 
                    }
                }
            }

            // adding orders
            NodeList orders = doc.getElementsByTagName("orders");
            Node orderList = orders.item(0);
            Element orderElements = (Element) orderList;
            NodeList allOrders = orderElements.getElementsByTagName("order");
            for(int tmp = 0; tmp < allOrders.getLength(); tmp++){
                
                Element myElement = (Element) allOrders.item(tmp);
                String order_id = myElement.getAttribute("id");

                
                String time = myElement.getElementsByTagName("start").item(0).getTextContent();

                Order createOrder = new Order(Integer.parseInt(order_id), time);

                NodeList goodsInsideList = myElement.getElementsByTagName("goods_type");
                for(int inside = 0; inside < goodsInsideList.getLength(); inside++){
                    Node goodsInside = goodsInsideList.item(inside);
                    Element goodsElement = (Element) goodsInside;
                    String goodsName = goodsElement.getAttribute("name");
                    String itemCount = goodsElement.getTextContent();
                    
                    createOrder.addToProductInform(new ProductInform(goodsName, Integer.parseInt(itemCount)));
                    Goods goods = base.getGoods(goodsName);
                    for (int itemCounter = 0; itemCounter < Integer.parseInt(itemCount); itemCounter++){
                        createOrder.addItemToBuy(goods);
                    }

                }
                createOrder.checkFreeLastBuy();
                base.addToOrderList(createOrder);
            }

            // adding carts to Base
            NodeList carts = doc.getElementsByTagName("carts");
            Node cartList = carts.item(0);
            Element cartElements = (Element) cartList;
            NodeList allCarts = cartElements.getElementsByTagName("cart");
            for(int tmp = 0; tmp < allCarts.getLength(); tmp++){
                
                Element myElement = (Element) allCarts.item(tmp);
                String cart_id = myElement.getAttribute("id");
                int id = Integer.parseInt(cart_id);

                String x = myElement.getElementsByTagName("x").item(0).getTextContent();
                String y = myElement.getElementsByTagName("y").item(0).getTextContent();
                Cart createCart = new Cart(id, "red", base, new Coordinates(Double.parseDouble(x), Double.parseDouble(y)), controller);
                base.addToCartList(createCart);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        
        //---
        
        // print base information
        System.out.println(base);
        

        // add everything drawable to array to draw it in Controller
        elements.add(base.getBase());
        elements.addAll(base.getStreetList());
        elements.addAll(base.getStopList());
        elements.addAll(base.getShelfList());
        elements.addAll(base.getCartList());

        controller.addBase(base);
        controller.setElements(elements);
        // program run
        controller.startClock();
    }
}

