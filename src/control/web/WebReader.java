/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.web;

/**
 * Allows for the retrieval of web source codes in html
 *
 * @author lucha
 */
import static control.MainApp.developersList;
import control.sqlLogic.PopulateTables;
import java.io.*;
import java.net.*;
import org.apache.commons.lang3.StringUtils;

public class WebReader
{

    /**
     * Publicly accessible method to scrape the steam database specifically
     *
     * @return totalSearched to represent the total number. Just a nice thing
     * for the user to see. has no REAL value to the program.
     */
    public int SteamScraper()
    {
	/*
   TODO:
   Create some array to store the name of every developer. Potentially use
   a LIST *additional skils*. Into this array store the developers according
   to devID so that they are quickly checked for. Perhaps there is a nice
   library for this?
	 */

	int totalSearched = 0;
	String currentSource = null;
	String[] currentData = null;
	PopulateTables tablePopulator = new PopulateTables();
	boolean validSource = true;

	for (int currentAppID = 38530; currentAppID < 1000000; currentAppID += 10)
	{
	    // Begin at original Counter Strike/SteamAppID 1
	    String currentURL = "http://store.steampowered.com/app/" + currentAppID + "/";
	    validSource = true;
	    try
	    {
		Thread.sleep(1000);
		// Begin by fetching the source code for the first page
		currentSource = ReturnSource(currentURL);
	    }
	    catch (InterruptedException exx)
	    {
		System.err.println("Error in sleep function: " + exx);
	    }
	    catch (Exception ex)
	    {
		System.err.println("Some sort of error got thrown and should be ignored" + ex);
		currentSource = null;
	    }

	    if (!isHomePage(currentSource) && currentSource != null)
	    {
		try
		{
		currentData = ReturnData(currentSource);
		}
		catch (NullPointerException ex)
		{
		    System.err.println("Caught a null value whilst parsing html,"
			    + "probably not a game. Ending the input and moving to next ID");
		    currentData = null;
		    validSource = false;
		}
		if (!validSource)
		{
//		    if (Integer.parseInt(currentData[0]) >= currentAppID)
//		    {
		    System.err.println("There was something weird ignore it you probably lost a game");
//		    }
		}
		else
		{
		    developersList = tablePopulator.insertNewGame(currentData, developersList);
		}

	    }
	    else
	    {
		System.out.println("CurrentSource is home page(" + currentAppID + ")");
	    }
	}

	return totalSearched;
    }

    /**
     * Creates a string in the exact layout of the original html that can then
     * be parsed in order to locate data for links as required as well as the
     * necessary information pertaining to the game at hand.
     *
     * @param WebPageUrl the URL of the page to be fetched.
     * @return The String built by the method.
     */
    private String ReturnSource(String WebPageUrl) throws Exception
    {
	BufferedReader inS;
	try
	{
	    // Define webPageURL as a URL type (Not String)
	    URL WebPageURL = new URL(WebPageUrl);
	    // Creating buffered reader to read the stream returned by the webpage.
	    inS = new BufferedReader(new InputStreamReader(WebPageURL.openStream()));

	    String inputLine;

	    // String to hold the source code as it is read in.
	    StringBuilder htmlSource = new StringBuilder();

	    /**
	     * Print the line being read until the line returns null (when the
	     * web page html ends).
	     */
	    while ((inputLine = inS.readLine()) != null)
	    {
		htmlSource.append(inputLine).append("\n");
		//System.out.println(inputLine);
	    }

	    return htmlSource.toString();
	}
	catch (IOException ex)
	{
	    System.err.println("Error: control/web/WebReader: " + ex);
	    return null;
	}

    }

    private String[] ReturnData(String html) throws NullPointerException
    {
	String[] currentData = new String[14];
	String[] genreMulti = new String[8];
	String steamID;//0
	String imageURL;//1
	String shortDescription;//2
	String userReviews;//3
	String releaseDate;//4
	String multiplayerSupp;//5
	String controllerSupp;//6
	String genre1 = "";//7
	String genre2 = "";//8
	String genre3 = "";//9
	String devName;//10
	String title;//11
	String metaCritic;//12
	String longDescription;//13

	steamID = StringUtils.substringBetween(html, "<link rel=\"canonical\" href=", ">");
	if (steamID != null)
	{
	    steamID = convertHTML(steamID).substring(35, steamID.length() - 2);
	}
	//System.out.println("SteamID = " + steamID);
	currentData[0] = steamID;

	imageURL = StringUtils.substringBetween(html, "<img class=\"game_header_image_full\" src=", ">");
	if (imageURL != null)
	{
	    imageURL = convertHTML(imageURL).substring(1, imageURL.length() - 1);
	}
	//System.out.println("imageURL = " + imageURL);
	currentData[1] = imageURL;

	shortDescription = StringUtils.substringBetween(html, "<meta name=\"Description\" content=", ">");
	if (shortDescription != null)
	{
	    shortDescription = convertHTML(shortDescription);
	    shortDescription = shortDescription.substring(1, shortDescription.length() - 1);
	}
	//System.out.println("shortDescription = " + shortDescription);
	currentData[2] = shortDescription;

	userReviews = StringUtils.substringBetween(html, "itemprop=\"description\">", "</span>");
	userReviews = convertHTML(userReviews);
	//System.out.println("userReviews = " + userReviews);
	currentData[3] = userReviews;

	releaseDate = StringUtils.substringBetween(html, "Release Date: <span class=\"date\">", "</span>");
	releaseDate = convertHTML(releaseDate);
	//System.out.println("releaseDate = " + releaseDate);
	currentData[4] = releaseDate;

	String tempDetails = StringUtils.substringBetween(html, "<div class=\"block responsive_apppage_details_left\" id=\"category_block\">", "<div class=\"block responsive_apppage_details_right\">");
	if (tempDetails != null)
	{
	    tempDetails = tempDetails.toUpperCase();
	    if (tempDetails.contains("MULTIPLAYER"))
	    {
		multiplayerSupp = "True";
	    }
	    else
	    {
		multiplayerSupp = "False";
	    }
	    //  System.out.println("multiplayerSupp = " + multiplayerSupp);
	    currentData[5] = multiplayerSupp;

	    if (tempDetails.contains("CONTROLLER"))
	    {
		controllerSupp = "True";
	    }
	    else
	    {
		controllerSupp = "False";
	    }
	    // System.out.println("controllerSupp = " + controllerSupp);
	    currentData[6] = controllerSupp;
	}

	tempDetails = StringUtils.substringBetween(html, "<b>Genre:</b>", "<b>Developer:</b>");
	if (tempDetails != null)
	{
	    genreMulti = StringUtils.substringsBetween(tempDetails, "http://store.steampowered.com/genre/", "/?snr=");
	    //   System.out.println(Arrays.toString(genreMulti));

	    int genreMultiLength = genreMulti.length;
	    //  System.out.println(genreMulti[0]);
	    genre1 = genreMulti[0];

	    currentData[7] = genre1;

	    if (genreMultiLength > 1)
	    {
		genre2 = genreMulti[1];
		currentData[8] = genre2;

		if (genreMultiLength > 2)
		{
		    genre3 = genreMulti[2];
		    currentData[9] = genre3;
		}
	    }

	}

	devName = StringUtils.substringBetween(html, "<a href=\"http://store.steampowered.com/search/?developer=", "&snr=");
	devName = convertHTML(devName);
	//System.out.println("devName = " + devName);
	currentData[10] = devName;

	title = StringUtils.substringBetween(html, "<b>Title:</b>", "<br>");
	title = convertHTML(title).trim();
	//System.out.println("title = " + title);
	currentData[11] = title;

	metaCritic = StringUtils.substringBetween(html, "<div id=\"game_area_metascore\">", "</span>");
	if (metaCritic != null && metaCritic.contains("NA") != true)
	{
	    metaCritic = convertHTML(metaCritic);
	    metaCritic = metaCritic.trim();
	    // Clever little regex expression that removes anything that is not
	    // a number in order to isolate a value.
	    metaCritic = metaCritic.replaceAll("[^0-9.]", "");
	}
	else
	{
	    /* Set metaCritic value to be the erroneous case of 999. This
       prevents any null errors as well as ensuring the value will always
       be far outside the acceptable range 0-100 and thus will be exempted
       from any calculations and comparisons.
	     */
	    metaCritic = "999";
	}

	//System.out.println("metaCritic = " + metaCritic);
	currentData[12] = metaCritic;

	longDescription = StringUtils.substringBetween(html, "<h2>About This Game</h2>", "</div>");
	if (longDescription == null)
	{
	    longDescription = StringUtils.substringBetween(html, "<h2>About This Content</h2>", "</div>");
	}
	longDescription = convertHTML(longDescription);
	longDescription = longDescription.trim();
	//System.out.println("longDescription = " + longDescription);
	currentData[13] = longDescription;

	//System.out.println("end");
	return currentData;
    }

    /**
     * Method to simplify the mundane conversion of typical characters and
     * "tags" used in html to denote characters such as spaces or linebreaks.
     *
     * Also includes code necessary to sanitise strings for use in SQL such as
     * escaping single quotes.
     *
     * @param sample The String to be converted
     * @return Returns the original input after conversion.
     */
    public String convertHTML(String sample)
    {
	String tempData;
	if (sample == null)
	{
	    return null;
	}

	// convert spaces
	sample = sample.replace("%20", " ");

	// remove tabs?
	sample = sample.replace("\u0009", " ");

	//convert breaks
	sample = sample.replace("<br>", "");

	//convert paragraphs
	sample = sample.replace("<p>", "");
	sample = sample.replace("</p>", "");

	//convert bullet point lists
	sample = sample.replace("<ul>", "");
	sample = sample.replace("</ul>", "");
	sample = sample.replace("<li>", "\u2022 ");
	sample = sample.replace("</li>", "");

	// remove heading 2
	sample = sample.replace("<h2 class=\"bb_tag\">", "");
	sample = sample.replace("</h2>", "");
	sample = sample.replace("<ul class=\"bb_ul\">", "");

	//remove hyperlink
	tempData = StringUtils.substringBetween(sample, "<a href=\"http://", "noreferrer\"  >");
	if (tempData != null)
	{
	    while (!tempData.equals(""))
	    {
		sample = sample.replace(tempData, "");

		sample = sample.replace("</a>", "");

		tempData = StringUtils.substringBetween(sample, "<a href=\"http://", "noreferrer\"  >");
	    }
	    sample = sample.replace("<a href=\"http://", "");
	    sample = sample.replace("noreferrer\"  >", "");
	}

	sample = sample.replace("&quot;", "");
	sample = sample.replace("'", "’");

	//final result after parsing formatting tags using regex
	sample = sample.replaceAll("\\<.*?>", "");

	return sample;
    }

    public boolean isHomePage(String source)
    {
	if (source != null)
	{
	return StringUtils.substringBetween(source, "<title>", "</title>").equals("Welcome to Steam");
	}
	return true;
    }

}
