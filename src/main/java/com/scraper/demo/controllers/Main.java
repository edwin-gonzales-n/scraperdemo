/* Scraper app for the AR/VR Virtual Pillar team
 * Main class
 * This class is a @Controller type class and its function is to execute
 * all scraping methods, then pushing them to the mysql DB.
 * All scraping methods are programmed to fit specific websites, since all of them are different.
 */

package com.scraper.demo.controllers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.scraper.demo.models.apartments;
import com.scraper.demo.repositories.ApartmentsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Entry point for this project.
 *
 * @author Edwin O. Gonzales
 */
@Controller
public class Main {

    public ApartmentsRepository apartmentsRepository;

    public Main(ApartmentsRepository apartmentsRepository){
        this.apartmentsRepository = apartmentsRepository;
    }

    // **************************************************
    // root
    // **************************************************
    //root directory.  It will open the index.html file.
    @GetMapping("/")
    public String apartmentController(){
        return "index";
    }

    // **************************************************
    // fakelenox
    // **************************************************
    /** Lenox Boardwalk site is extremely loaded with JS. I scraped the site after all the JS is loaded, then grabbed the html,
     * and finally placed it in my project as a dummy page.  The app then scrapes the data from this demo page. */
    @GetMapping("/fakelenox")
    public String fakeLenox(){
        return "lenox-boardwalk";
    }

    // **************************************************
    // sensors
    // **************************************************
    /** I created sensors for our class project using the IoT sensors.  It is all JS, and not relevant to the main scraping project. */
    @GetMapping("/sensors")
    public String sensorsController(){
        return "sensors";
    }

    // **************************************************
    // filldatabase
    // **************************************************
    /**  The filldatabase method calls the scraper methods and fills the database.
     *   It is wrapped around a timer that makes the method run once a day.
     *   Every time the method is called, it first clean the table 'apartments' in the DB.
     *   Please keep in mind that if you run the application for the first time, then you MUST run the
     *   http://<address>/filldatabase  in order to populate the DB.  This is only needed once.*/
    @GetMapping("/filldatabase")
    public String filldatabase(){
        // timer to refill database with new data everyday, once a day.
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                apartmentsRepository.truncateApartmentsTable();
                getNuecesApartment();
                getLakeShore();
                getAzul();
                getLenox();
                get7east();
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        long period = 1000L * 60L * 60L * 24L;
//        long period = 30000L;````````
        timer.scheduleAtFixedRate(repeatedTask, delay , period);
        return "fillDatabase";
    }

    /* run method below and comment the one above in order to fill database without a timer
    @GetMapping("/filldatabase")
    public String filldatabase(){
        //cleaning database if filled.
        apartmentsRepository.deleteAll();
        //filling up database with new data
        getNuecesApartment();
        getLakeShore();
        getAzul();
        getLenox();
        return "fillDatabase";
    } */

    // **************************************************
    // Scrapers and logic to fill database
    // **************************************************
    /**
     *
     * Property_ID is the property id value for each apartment complex
     * The application uses HTMLunit libraries in order to scrape website.   For more info just google htmlUnit.  http://htmlunit.sourceforge.net/
     */

    private void getNuecesApartment() {

        long property_id=1;
        String location="30.287978, -97.743497";

        /*
         * The ctsdate value calculates the time and creates a live timestamp each time the app auto-loads once a day.
         */
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
        String cstdate = dateFormatGmt.format(new Date());

        String baseUrl = "http://www.2400nuecesapartments.com/Floor-Plans/";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> itemList = page.getByXPath("//div[contains(@class,'has-description')]"); // div containing all the sub-divs with wanted info.
            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{
                int counter=1;
                String url = "No url available";

                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div/div[contains(@class,'fp-title')]")).asText();
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("./div/div[contains(@class,'fp-avil')]")).asText();
                    String price = ((HtmlElement) htmlItem.getFirstByXPath("./p[contains(text(),'Starting')]")).asText();

                    apartments hnItem = new apartments(title,info,price,cstdate,url,property_id,location);
                    apartmentsRepository.save(hnItem); // save to database
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLakeShore(){

        long property_id=2;
        String location = "30.242056, -97.723472";

        /*
         * The ctsdate value calculates the time and creates a live timestamp each time the app auto-loads once a day.
         */
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
        String cstdate = dateFormatGmt.format(new Date());

        String baseUrl = "https://www.lakeshorepearl.com/Marketing/FloorPlans";

        /*
         * Webclient method.  Must disable CSS and JS in order to only scrape HTML elements.
         */
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> itemList = page.getByXPath("//div[contains(@class,'col-lg-4 col-sm-6 col-xs-12 floorplan item')]");

            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{

                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div[contains(@class,'col-xs-9 col-sm-10')]"))
                            .asText().replaceAll("\\n"," ");
                    String pricing = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div[contains(@class,'info row pricing')]"))
                            .asText().replaceAll("\\n"," ");
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div/div[contains(@class,'col-xs-8')]"))
                            .asText().replaceAll("\\n"," ");
                    String url = ((DomAttr) htmlItem.getFirstByXPath("./div/div/div/a[contains(@class,'btn btn-default btn-block')]/@href"))
                            .getValue();
                    String completeUrl = String.format("https://www.lakeshorepearl.com%s" ,url);

                    if (!pricing.contains("Inquire") && !info.contains("Inquire")){
                        apartments hnItem = new apartments(title,info,pricing,cstdate,completeUrl,property_id,location);
                        apartmentsRepository.save(hnItem); // save to database
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAzul(){

        long property_id=3;
        String location = "30.242992, -97.722486";

        /*
         * The ctsdate value calculates the time and creates a live timestamp each time the app auto-loads once a day.
         */
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
        String cstdate = dateFormatGmt.format(new Date());

        String baseUrl = "https://azullakeshore.com/floorplans/";

        /*
         * Webclient method.  Must disable CSS and JS in order to only scrape HTML elements.
         */
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> itemList = page.getByXPath("//a[contains(@class,'floorplan')]");

            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{

                /*
                 * pricing and info details are not available on the website so i had to replace them with the info below.
                 */
                String pricing = "Must inquire on-site";
                String info = "Must inquire on-site";

                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div[contains(@class,'fp_info')]")).
                            asText().replaceAll("\\n"," ");
                    String url = ((DomAttr) htmlItem.getFirstByXPath("./@href")).getValue();

                    apartments hnItem = new apartments(title,info,pricing,cstdate,url,property_id,location);
                    apartmentsRepository.save(hnItem); //save to database
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLenox(){

        long property_id=4;
        String location="30.239060, -97.720513";

        /*
         * The ctsdate value calculates the time and creates a live timestamp each time the app auto-loads once a day.
         */
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
        String cstdate = dateFormatGmt.format(new Date());

        /*
         * Webclient method.  Must disable CSS and JS in order to only scrape HTML elements.
         * For this website i had to add the parameters "BrowserVersion.getDefault()" in order to
         * wait for most JS elements to load, the grab the HTML elements, and fist save them locally.
         * These elements were saved to lenox-boardwalk.html and lives in http://localhost:8080/fakelenox
         * The function then uses this address to scrape the information locally instead of using the real website.
         * This solution was due that the JS elements would take some time to load and the wrong data was parsed.
         */
        WebClient client = new WebClient(BrowserVersion.getDefault());
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            /*
             *  Grabbing html elements from local address.
             */
            HtmlPage page = client.getPage("http://localhost:8080/fakelenox");
            List<HtmlElement> itemList = page.getByXPath("//ul[contains(@id,'floorplan_slider_list')]/li");

            /*  This is how you save the html output to a new file within the app tree.  Restart app once done.  Do not run this segment.
            System.out.println(page.asXml());
            FileWriter fileWriter = new FileWriter("/Users/eogonzal/IdeaProjects/Scraper_demo/src/main/resources/templates/lenox-boardwalk.html");
            fileWriter.write(page.asXml());
            fileWriter.close();
             */

            if(itemList.isEmpty()){
                System.out.println("No item found");
            }
            else{

                String info = "Must inquire on-site";

                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./ul[contains(@class,'floorplan-details')]"))
                            .asText().replaceAll("\\n"," ").replaceAll("Rent.*$","");
                    String pricing = ((HtmlElement) htmlItem.getFirstByXPath("./ul[contains(@class,'floorplan-details')]"))
                            .asText().replaceAll("\\n"," ").replaceAll(".*Rent ","");
                    String viewavailable = ((DomAttr) htmlItem.getFirstByXPath("./a[contains(@class,'fp-button floorplan-availability-link')]/@href"))
                            .getValue();

                    String url = ("https://www.lenoxboardwalk.com/floorplans/" + viewavailable);

                    apartments hnItem = new apartments(title,info,pricing,cstdate,url,property_id, location);
                    apartmentsRepository.save(hnItem); // save to db

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void get7east(){

        long property_id = 5;
        String location = "30.261815, -97.719815";

        /*
         * The ctsdate value calculates the time and creates a live timestamp each time the app auto-loads once a day.
         */
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
        String cstdate = dateFormatGmt.format(new Date());

        String baseUrl = "https://www.7eastaustin.com/Floor-plans.aspx";

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {

            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> itemList = page.getByXPath("//div[contains(@class,'floorplan-block')]");

            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{

                for(HtmlElement htmlItem : itemList){

                    String price = ((DomAttr) htmlItem.getFirstByXPath("./meta[contains(@name, 'minimumMarketRent')]/@content")).getValue();
                    String price2 = ((DomAttr) htmlItem.getFirstByXPath("./meta[contains(@name, 'maximumMarketRent')]/@content")).getValue();
                    String pricing = String.format("$%s - $%s", price, price2);
                    String url = ((DomAttr) htmlItem.getFirstByXPath("./div/div/div/div/ul/li/span/a/img/@src")).getValue();
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div[contains(@class,'specification')]")).asText().replaceAll("\\n"," ");
//                    String amenities = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div/div[contains(@class,'amenities-container')]")).asText().replaceAll("\\n",", ");
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div/p[contains(@class,'pt')]")).asText();

                    /*
                     * StringTokenizer will count the words within a string.  I used this object in order to filter the 'info' variable.
                     * It would come back with the apartment info but for some it would contain a full description.
                     * So by using the tokenizer we could filter the data input that is longer than 4 words and replace them
                     * with a simple 'require within'
                     * Please see logic in the if statement below.
                     */
                    StringTokenizer stringTokenizer = new StringTokenizer(info);
                    System.out.println("This is how many words: " + stringTokenizer.countTokens());

                    apartments hnItem;
                    if(stringTokenizer.countTokens() > 4){
                        info = "Inquire within";
                        System.out.println("Title: " + title + "\nprice range: " + pricing +  "\nDescription: " + info + "\nLocation: " + location + "\nUrl: " + url);
                        hnItem = new apartments(title,info,pricing,cstdate,url,property_id, location);
                        apartmentsRepository.save(hnItem); // save to db
                    } else
                        System.out.println("Title: " + title + "\nprice range: " + pricing +  "\nDescription: " + info + "\nLocation: " + location + "\nUrl: " + url);
                        hnItem = new apartments(title,info,pricing,cstdate,url,property_id, location);
                        apartmentsRepository.save(hnItem); // save to db

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
