/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Luc Hayward
 */
public class Developer
{

    private IntegerProperty devID;
    private StringProperty name;
    private IntegerProperty foundedYear;
    private StringProperty founder;
    private StringProperty hqCountry;
    private StringProperty website;


    public Developer(int devID, String name)
    {
	this.devID.set(devID);
	this.name.set(name);
    }

    public Developer(int devID, String name, int foundedYear, String founder, String hqCountry, String website)
    {
	this.devID = new SimpleIntegerProperty(devID);
	this.name = new SimpleStringProperty(name);
	this.foundedYear = new SimpleIntegerProperty(foundedYear);
	if (founder == null)
	{
	    founder = "User to manually enter missing data";
	}
	this.founder = new SimpleStringProperty(founder);
	this.hqCountry = new SimpleStringProperty(hqCountry);
	if (hqCountry == null)
	{
	    hqCountry = "User to manually enter missing data";
	}
	this.website = new SimpleStringProperty(website);
	if (website == null)
	{
	    website = "User to manually enter missing data";
	}
    }

    public Developer()
    {
	this.devID = new SimpleIntegerProperty(0);
	this.name = new SimpleStringProperty("");
	this.foundedYear = new SimpleIntegerProperty(0);
	this.founder = new SimpleStringProperty("");
	this.hqCountry = new SimpleStringProperty("");
	this.website = new SimpleStringProperty("");

    }

    public int getDevID()
    {
	return devID.get();
    }

    public IntegerProperty devIDProperty()
    {
	return devID;
    }

    public void setDevID(int devID)
    {
	this.devID.set(devID);
    }

    public String getName()
    {
	return name.get();
    }

    public StringProperty nameProperty()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name.set(name);
    }

    public int getFoundedYear()
    {
	return foundedYear.get();
    }

    public IntegerProperty foundedYearProperty()
    {
	return foundedYear;
    }

    public void setFoundedYear(int foundedYear)
    {
	this.foundedYear.set(foundedYear);
    }

    public String getFounder()
    {
	return founder.get();
    }

    public StringProperty founderProperty()
    {
	return founder;
    }

    public void setFounder(String founder)
    {
	this.founder.set(founder);
    }

    public String gethqCountry()
    {
	return hqCountry.get();
    }

    public StringProperty hqCountryProperty()
    {
	return hqCountry;
    }

    public void setHqCountry(String hqCountry)
    {
	this.hqCountry.set(hqCountry);
    }

    public String getWebsite()
    {
	return website.get();
    }

    public StringProperty websiteProperty()
    {
	return website;
    }

    public void setWebsite(String website)
    {
	this.website.set(website);
    }
}
