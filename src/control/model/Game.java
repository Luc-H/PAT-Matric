/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Luc Hayward
 */
public class Game
{

    private IntegerProperty steamID;
    private StringProperty title;
    private StringProperty shortDescription;
    private StringProperty userReviews;
    private IntegerProperty metaCritic;
    private StringProperty releaseDate;
    private BooleanProperty multiplayerSupport;
    private BooleanProperty controllerSupport;
    private StringProperty genre1;
    private StringProperty genre2;
    private StringProperty genre3;
    private StringProperty longDescription;
    private BooleanProperty isOwned;
    private IntegerProperty devID;

    private int steamIDSimple;
    private String titleSimple;
    private String shortDescriptionSimple;
    private String userReviewsSimple;
    private int metaCriticSimple;
    private String releaseDateSimple;
    private Boolean multiplayerSupportSimple;
    private Boolean controllerSupportSimple;
    private String genre1Simple;
    private String genre2Simple;
    private String genre3Simple;
    private String longDescriptionSimple;
    private Boolean isOwnedSimple;
    private int devIDSimple;

    /**
     * Blank Constructor
     */
    public Game()
    {
	this.steamID = new SimpleIntegerProperty(0);
	this.title = new SimpleStringProperty("");
	this.shortDescription = new SimpleStringProperty("");
	this.userReviews = new SimpleStringProperty("");
	this.metaCritic = new SimpleIntegerProperty(0);
	this.releaseDate = new SimpleStringProperty("");
	this.multiplayerSupport = new SimpleBooleanProperty(false);
	this.controllerSupport = new SimpleBooleanProperty(false);
	this.genre1 = new SimpleStringProperty("");
	this.genre2 = new SimpleStringProperty("");
	this.genre3 = new SimpleStringProperty("");
	this.longDescription = new SimpleStringProperty("");
	this.isOwned = new SimpleBooleanProperty(false);
	this.devID = new SimpleIntegerProperty(0);
    }


    /**
     *
     * @param steamID the value of steamID
     * @param title the value of title
     * @param shortDescription the value of shortDescription
     * @param userReviews the value of userReviews
     * @param metaCritic the value of metaCritic
     * @param releaseDate the value of releaseDate
     * @param multiplayerSupport the value of multiplayerSupport
     * @param controllerSupport the value of controllerSupport
     * @param genre1 the value of genre1
     * @param genre2 the value of genre2
     * @param genre3 the value of genre3
     * @param longDescription the value of longDescription
     * @param isOwned the value of isOwned
     * @param devID the value of devID
     */
    public Game(int steamID, String title, String shortDescription, String userReviews,
		int metaCritic, String releaseDate, boolean multiplayerSupport,
		boolean controllerSupport, String genre1, String genre2, String genre3,
		String longDescription, boolean isOwned, int devID)
    {
	if (metaCritic > 100)
	{
	    metaCritic = 0;
	}
	this.steamID = new SimpleIntegerProperty(steamID);
	this.title = new SimpleStringProperty(title);
	this.shortDescription = new SimpleStringProperty(shortDescription);
	this.userReviews = new SimpleStringProperty(userReviews);
	this.metaCritic = new SimpleIntegerProperty(metaCritic);
	this.releaseDate = new SimpleStringProperty(releaseDate);
	this.multiplayerSupport = new SimpleBooleanProperty(multiplayerSupport);
	this.controllerSupport = new SimpleBooleanProperty(controllerSupport);
	this.genre1 = new SimpleStringProperty(genre1);
	this.genre2 = new SimpleStringProperty(genre2);
	this.genre3 = new SimpleStringProperty(genre3);
	this.longDescription = new SimpleStringProperty(longDescription);
	this.isOwned = new SimpleBooleanProperty(isOwned);
	this.devID = new SimpleIntegerProperty(devID);
    }

    public Game(int steamID, String title)
    {
	System.out.println("steamID = " + steamID + " and title = " + title);
	this.steamID = new SimpleIntegerProperty(steamID);
	this.title = new SimpleStringProperty(title);
    }

//    public Game(int steamID, String title, String shortDescription, String userReviews,
//		int metaCritic, String releaseDate, boolean multiplayerSupport,
//		boolean controllerSupport, String genre1, String genre2, String genre3,
//		String longDescription, boolean isOwned, int devID)
//    {
//	steamIDSimple = steamID;
//	titleSimple = title;
//	shortDescriptionSimple = shortDescription;
//	userReviewsSimple = userReviews;
//	metaCriticSimple = metaCritic;
//	releaseDateSimple = releaseDate;
//	multiplayerSupportSimple = multiplayerSupport;
//	controllerSupportSimple = controllerSupport;
//	genre1Simple = genre1;
//	genre2Simple = genre2;
//	genre3Simple = genre3;
//	longDescriptionSimple = longDescription;
//	isOwnedSimple = isOwned;
//	devIDSimple = devID;
//    }

    /**
     * @return the steamID
     */
    public int getSteamID()
    {
	return steamID.get();
    }

    /**
     * @param steamID the steamID to set
     */
    public void setSteamID(int steamID)
    {
	this.steamID.set(steamID);
    }

    public IntegerProperty steamIDProperty()
    {
	return steamID;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
	return title.get();
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
	this.title.set(title);
    }

    public StringProperty titleProperty()
    {
	return title;
    }

    /**
     * @return the shortDescription
     */
    public String getShortDescription()
    {
	return shortDescription.get();
    }

    /**
     * @param shortDescription the shortDescription to set
     */
    public void setShortDescription(String shortDescription)
    {
	this.shortDescription.set(shortDescription);
    }

    public StringProperty shortDescriptionProperty()
    {
	return shortDescription;
    }

    /**
     * @return the userReviews
     */
    public String getUserReviews()
    {
	return userReviews.get();
    }

    /**
     * @param userReviews the userReviews to set
     */
    public void setUserReviews(String userReviews)
    {
	this.userReviews.set(userReviews);
    }

    public StringProperty userReviewsProperty()
    {
	return userReviews;
    }

    /**
     * @return the metaCritic
     */
    public int getMetaCritic()
    {
	return metaCritic.get();
    }

    /**
     * @param metaCritic the metaCritic to set
     */
    public void setMetaCritic(int metaCritic)
    {
	this.metaCritic.set(metaCritic);
    }

    public IntegerProperty metaCriticProperty()
    {
	return metaCritic;
    }

    /**
     * @return the releaseDate
     */
    public String getReleaseDate()
    {
	return releaseDate.get();
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate(String releaseDate)
    {
	this.releaseDate.set(releaseDate);
    }

    public StringProperty releaseDateProperty()
    {
	return releaseDate;
    }

    /**
     * @return the multiplayerSupport
     */
    public boolean isMultiplayerSupport()
    {
	return multiplayerSupport.get();
    }

    /**
     * @param multiplayerSupport the multiplayerSupport to set
     */
    public void setMultiplayerSupport(boolean multiplayerSupport)
    {
	this.multiplayerSupport.set(multiplayerSupport);
    }

    public BooleanProperty multiplayerSupportProperty()
    {
	return multiplayerSupport;
    }

    /**
     * @return the controllerSupport
     */
    public boolean isControllerSupport()
    {
	return controllerSupport.get();
    }

    /**
     * @param controllerSupport the controllerSupport to set
     */
    public void setControllerSupport(boolean controllerSupport)
    {
	this.controllerSupport.set(controllerSupport);
    }

    public BooleanProperty controllerSupportProperty()
    {
	return controllerSupport;
    }

    /**
     * @return the genre1
     */
    public String getGenre1()
    {
	return genre1.get();
    }

    /**
     * @param genre1 the genre1 to set
     */
    public void setGenre1(String genre1)
    {
	this.genre1.set(genre1);
    }

    public StringProperty genre1Property()
    {
	return genre1;
    }

    /**
     * @return the genre2
     */
    public String getGenre2()
    {
	return genre2.get();
    }

    /**
     * @param genre2 the genre2 to set
     */
    public void setGenre2(String genre2)
    {
	this.genre2.set(genre2);
    }

    public StringProperty genre2Property()
    {
	return genre2;
    }

    /**
     * @return the genre3
     */
    public String getGenre3()
    {
	return genre3.get();
    }

    /**
     * @param genre3 the genre3 to set
     */
    public void setGenre3(String genre3)
    {
	this.genre3.set(genre3);
    }

    public StringProperty genre3Property()
    {
	return genre3;
    }

    /**
     * @return the longDescription
     */
    public String getLongDescription()
    {
	return longDescription.get();
    }

    /**
     * @param longDescription the longDescription to set
     */
    public void setLongDescription(String longDescription)
    {
	this.longDescription.set(longDescription);
    }

    public StringProperty longDescriptionProperty()
    {
	return longDescription;
    }

    /**
     * @return the isOwned
     */
    public boolean isIsOwned()
    {
	return isOwned.get();
    }

    /**
     * @param isOwned the isOwned to set
     */
    public void setIsOwned(boolean isOwned)
    {
	this.isOwned.set(isOwned);
    }

    public BooleanProperty isOwnedProperty()
    {
	return isOwned;
    }

    /**
     * @return the devID
     */
    public int getDevID()
    {
	return devID.get();
    }

    /**
     * @param devID the devID to set
     */
    public void setDevID(int devID)
    {
	this.devID.set(devID);
    }

    public IntegerProperty devIDProperty()
    {
	return devID;
    }

    @Override
    public String toString()
    {
	String s = "";

	s = "this exists";

	return s;
    }
}
