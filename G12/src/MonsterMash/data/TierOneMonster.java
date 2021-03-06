/**
 * @HeadUrl https://github.com/DownGoat/MonsterMash/blob/development/G12/src/MonsterMash/data/Monster.java
 * 
 * Copyright (c) 2013 Aberystwyth University
 * All rights reserved.
 */
package data;

import java.util.Date;
import java.util.Random;
import data.Monster;
import data.Tier;
import data.TierTwoMonster;
import data.TierThreeMonster;
import data.MonsterFactory;

/**
 * Encapsulation of the Monster data. The data is stored in the database and
 * represents one of the the player's Monster. 
 * 
 * @author $Author sis13 $
 */
public class TierOneMonster implements Monster {
    private final int START_HEALTH = 100;
    public static final int LIFESPAN = 1000*60*60*60*24*7;
    
    
    /** Attributes **/    
    private String id;
    private String name;
    private Date dob;
    private Date dod;
    private double baseStrength;
    private double currentStrength;
    private double baseDefence;
    private double currentDefence;
    private double baseHealth;
    private double currentHealth;
    //public double injuries = 0; 
    private float fertility;
    private String userID;
    private int saleOffer;
    private int breedOffer;
    
    private int serverID;
    
    private final int MAX_CHILDREN = 10;
    
    /**
     * Constructor taking parameters. This constructor is used for creating 
     * Monster objects about remote Monsters before we get all the data.
     * 
     * @param id The Monsters ID.
     * @param name The Monster's name.
     * @param userID  The ID of the owner of the Monster.
     * 
     * @see Player
     */
    public TierOneMonster(String id, String name, String userID){
        this.id = id;
        this.name = name;
        this.userID = userID;
    }
    
    /**
     * Constructor setting all the Monsters fields, the stats are random.
     * @param name
     * @param userID 
     */
    public TierOneMonster(String name, String userID){
        this.id = "0";
	    this.name = name;
	    this.dob = new Date();
        this.dod = new Date(dob.getTime()+LIFESPAN);
        Random random = new Random();
        this.baseStrength = (double)(200 + random.nextDouble() * (300 - 200));
        this.currentStrength = (double)(200 + random.nextDouble() * (300 - 200));
        this.baseDefence = (double)(200 + random.nextDouble() * (300 - 200));
        this.currentDefence = (double)(200 + random.nextDouble() * (300 - 200));
        this.baseHealth = (double)(2000 + random.nextDouble() * (3000 - 2000));
        this.currentHealth = (double)(2000 + random.nextDouble() * (this.baseHealth - 2000));
	    this.fertility = (float)(0.6 + random.nextFloat() * (1.0 - 0.6));
        this.userID = userID;
        this.saleOffer = 0;
        this.breedOffer = 0;
    }

    public TierOneMonster(){
        this.id = "0";
	    this.dob = new Date();
        this.dod = new Date(dob.getTime()+LIFESPAN);
        Random random = new Random();
        this.baseStrength = (double)(200 + random.nextDouble() * (300 - 200));
        this.currentStrength = (double)(200 + random.nextDouble() * (300 - 200));
        this.baseDefence = (double)(200 + random.nextDouble() * (300 - 200));
        this.currentDefence = (double)(200 + random.nextDouble() * (300 - 200));
        this.baseHealth = (double)(2000 + random.nextDouble() * (3000 - 2000));
        this.currentHealth = (double)(2000 + random.nextDouble() * (this.baseHealth - 2000));
	    this.fertility = (float)(0.6 + random.nextFloat() * (1.0 - 0.6));
        this.saleOffer = 0;
        this.breedOffer = 0;
    }

    public TierOneMonster(String id, String name, Date dob, Date dod, Double baseStrength, Double currentStrength, Double baseDefence, Double currentDefence, Double baseHealth, Double currentHealth, float fertility, String userID, int saleOffer, int breedOffer) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.dod = dod;
        this.baseStrength = baseStrength;
        this.currentStrength = currentStrength;
        this.baseDefence = baseDefence;
        this.currentDefence = currentDefence;
        this.baseHealth = baseHealth;
        this.currentHealth = currentHealth;
        this.fertility = fertility;
        this.userID = userID;
        this.saleOffer = saleOffer;
        this.breedOffer = breedOffer;
    }

    /**
     * Breeding class to breed new monsters
     * 
     * @param other Monster that is being bred with
     * @return Monster[] and array of the children
     */
    public Monster[] breeding(Monster other) {
        Random r = new Random(); 
        int numberofchildren = (int) (Math.sqrt(fertility * other.getFertility()) * MAX_CHILDREN);
    	Monster[] children = new Monster[numberofchildren + 1]; 
        for (int i = 0; i<= numberofchildren; i++){
            children[i]=new TierOneMonster();
            children[i].setId("0");
            children[i].setDob(new Date());
            children[i].setDod(new Date(children[i].getDob().getTime()+LIFESPAN));
            //this is assuming that the children go to the owner of the monster that calls the method
            children[i].setUserID(this.userID); 
            children[i].setName(NameGenerator.getName());
            //generating inherited defense
            if(r.nextInt(100)<5){
                 children[i].setBaseDefence(r.nextDouble());
            } else if(r.nextInt(100)<50){
                 children[i].setBaseDefence(baseDefence);
            } else {
                 children[i].setBaseDefence(other.getBaseDefence());
            }
            children[i].setCurrentDefence(children[i].getBaseDefence());
            //generating inherited strength
            if(r.nextInt(100)<5){
                 children[i].setBaseStrength(r.nextDouble());
            }
            else if(r.nextInt(100)<50){
                 children[i].setBaseStrength(baseStrength);
            } else {
                 children[i].setBaseStrength(other.getBaseStrength());
            }
            children[i].setCurrentStrength(children[i].getBaseStrength());
            //generating inherited health
            if(r.nextInt(100)<5){
                 children[i].setBaseHealth(r.nextDouble());
            }else if(r.nextInt(100)<50){
                 children[i].setBaseHealth(baseHealth);
            } else {
                 children[i].setBaseHealth(other.getBaseHealth());
            }
            children[i].setCurrentHealth(children[i].getBaseHealth());
            //generating inherited fertility
            if(r.nextInt(100)<5){
                 children[i].setFertility(r.nextFloat());
            } else if(r.nextInt(100)<50){
                 children[i].setFertility(fertility);
            } else {
                children[i].setFertility(other.getFertility());
            }
        }      
        return children;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public float getFertility() {
        return fertility;
    }

    public void setFertility(float fertility) {
        this.fertility = fertility;
    }
    
    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getDod() {
        return this.dod;
    }

    public Double getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(Double baseStrength) {
        this.baseStrength = baseStrength;
    }

    public Double getCurrentStrength() {
        return currentStrength;
    }

    public void setCurrentStrength(Double currentStrength) {
        this.currentStrength = currentStrength;
    }

    public Double getBaseDefence() {
        return baseDefence;
    }

    public void setBaseDefence(Double baseDefence) {
        this.baseDefence = baseDefence;
    }

    public Double getCurrentDefence() {
        return currentDefence;
    }

    public void setCurrentDefence(Double currentDefence) {
        this.currentDefence = currentDefence;
    }

    public Double getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(Double baseHealth) {
        this.baseHealth = baseHealth;
    }

    public Double getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(Double currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getSaleOffer() {
        return saleOffer;
    }

    public void setSaleOffer(int saleOffer) {
        this.saleOffer = saleOffer;
    }

    public int getBreedOffer() {
        return breedOffer;
    }

    public void setBreedOffer(int breedOffer) {
        this.breedOffer = breedOffer;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setDod(Date dod) {
        this.dod = dod;
    }

    public void updateStats(Double strength, Double defence, Double health) {
        this.currentStrength = strength;
        this.currentDefence = defence;
        this.currentHealth = health;
    }
    
    /**
     * Enrolls two Monsters in a epic battle. 
     * @param opponent The Monster this monster will fight versus.
     * @return The opponent is returned with new stats..
     */
    public double fight(Monster opponent)
    {
        double playerInjuries = 0; 
        double opponentInjuries = 0; 
        
        Random randomGenerator = new Random(); 
        double random = randomGenerator.nextDouble(); 
        
        while(this.currentHealth > 0 && opponent.getCurrentHealth() > 0) {
            opponent.setCurrentHealth(opponent.getCurrentHealth() - this.currentStrength * (1-opponent.getCurrentDefence()) * random);
            this.currentHealth -= opponent.getCurrentStrength() * (1-this.currentDefence) * random; 
            System.out.println("Player: " + this.currentHealth + "; Opponent: " + opponent.getCurrentHealth());
        }
        
        return opponent.getCurrentHealth(); 
    }
}