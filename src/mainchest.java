import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.List;

import org.osbot.rs07.api.filter.AreaFilter;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.PrayerButton;
import org.osbot.rs07.utility.Area;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.input.mouse.BotMouseListener;
import org.osbot.rs07.input.mouse.ClickMouseEvent;
import org.osbot.rs07.input.mouse.EntityDestination;
import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.api.WorldHopper;
import org.osbot.rs07.api.ui.Option;
import org.osbot.rs07.api.Menu;
import org.osbot.rs07.input.mouse.RectangleDestination;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@ScriptManifest(name = "Rogues'ChestsThiever", author = "dokato", version = 1.0, info = "", logo = "") 
public class mainchest extends Script {
	
	Random randomGenerator = new Random();
	public int getRandomNumber(int min,int max){
        int randomNumber = randomGenerator.nextInt(max+1);
        while(randomNumber<min){
            randomNumber = randomGenerator.nextInt(max+1);
        }
        return randomNumber;
    }
	
	public int getRandomWorld(){
		int[] worlds = {309,310,311,312,313,314,317,319,320,321,327,328,334,336,338,341,342,343,345,346,349,350,352,353,354,357,359,360,362,366,367,368,370,373,374,375,376,377,378};
		int world;
		world=worlds[getRandomNumber(0,worlds.length)];
		return world;
	}
	
	GUIR gui = new GUIR();
	
	private boolean useHRunes;
	private boolean useHTabs;
	private boolean useGRunes;
	private boolean useGTabs;
	
	final Area RUN_AREA1 = new Area(3282,3946,3297,3927);
	final Area RUN_AREA2 = new Area(3275,3927,3297,3939);
	final Area RUN_AREA3_GATE = new Area(3280,3927,3296,3919);
	final Area RUN_AREA4_TOWER = new Area(3277,3941,3284,3933);
	
	final Area CHEST_AREA = new Area(3282,3946,3290,3943);
	final Area NEAR_CHEST_AREA = new Area(3297,3946,3275,3934);
	final Area OBELISK_ROG = new Area(3303,3920,3311,3912); //final Area OBELISK_ROG_MID = new Area(3306,3917,3308,3915); 
	final Area OBELISK_ICE = new Area(2976,3870,2984,3862);	final Area OBELISK_ICE_MID = new Area(2979,3867,2981,3865);
	final Area ICE_ALL_AREA = new Area(2963,3880,2986,3859);
	final Area OBELISK_ENT = new Area(3223,3671,3231,3663);	//final Area OBELISK_ENT_MID = new Area(3226,3668,3228,3666);
	final Area OBELISK_BAN = new Area(3031,3736,3039,3728);	//final Area OBELISK_BAN_MID = new Area(3034,3733,3036,3731);
	final Area OBELISK_MIN = new Area(3102,3798,3110,3790);	//final Area OBELISK_MIN_MID = new Area(3105,3795,3107,3793);
	final Area OBELISK_MAM = new Area(3151,3624,3161,3615);	final Area OBELISK_MAM_MID = new Area(3155,3621,3157,3619); final Position OBELISK_MAM_MIDPP = new Position(3156,3620,0);
	final Area NEAR_ROG_AREA = new Area(3293,3931,3297,3927);
	final Area ALL_ROG_AREA = new Area(3274,3950,3316,3905); 
	final Area RELLEKA_HOUSE = new Area(2665,3633,2674,3626);
	final Area EDGEVILLE_AREA = new Area(3060,3520,3122,3476);
	final Area NEAR_EDGEVILLE_AREA = new Area(3095,3506,3108,3498);
	final Area EDG_BANK = new Area(3092,3494,3094,3489);
	final Area EDG_BANK2 = new Area(3091,3499,3098,3494);
	final Area CROSS_DITCH_AREA1 = new Area(3134,3525,3149,3518);
	final Area CROSS_DITCH_AREA2 = new Area(3086,3518,3092,3526);
	final Position CROSS_DITCH_POINT = new Position(3141,3520,0);
	final Area LUMBBRIDGE_CASTLE_AREA = new Area(3202,3233,3226,3209);
	final Area LUMBRIDGE_CASTLE_KITCHEN = new Area(3206,3216,3210,3213);
	final Area LUMBRIDGE_BASEMENT = new Area(3206,9628,3224,9612);
	final Area LUMBRIDGE_BASEMENT_BANK = new Area(3218,9623,3219,9620); final Position LUMBRIDGE_BASEMENT_BANKP = new Position(3218,9623,0);
	
	private Position[] OBELISK_ROG_MIDP = new Position[]{
			new Position(3307,3916,0)
	};
	private Position[] OBELISK_ICE_MIDP = new Position[]{
			new Position(2980,3866,0)
	};
	private Position[] OBELISK_ENT_MIDP = new Position[]{      
			new Position(3227,3667,0)
	};
	private Position[] OBELISK_BAN_MIDP = new Position[]{
			new Position(3035,3732,0)
	};
	private Position[] OBELISK_MIN_MIDP= new Position[]{
			new Position(3106,3794,0)
	};
	private Position[] OBELISK_MAM_MIDP= new Position[]{
			new Position(3156,3620,0)
	};
	private Position[] RUN_TO_OUTSIDE= new Position[]{
			new Position(3294,3938,0),
			new Position(3296,3928,0),
			new Position(3299,3926,0)
	};
	private Position[] RUN_TO_TOWER= new Position[]{
			new Position(3279,3940,0)
	};
	private Position[] RUN_TO_TOWER_STAIRCASE= new Position[]{
			new Position(3282,3935,0)
	};
	
	private ArrayList<String> itemsToPickup;

	public void thiev() throws InterruptedException{
		Entity chest = objects.closest(CHEST_AREA, 26757);
		//EntityDestination mouseChest = new EntityDestination(getBot(),chest);
		//Rectangle optionBox = menu.getOptionRectangle(3);
		//RectangleDestination mouseBox = new RectangleDestination(getBot(),optionBox);
		prayer.set(PrayerButton.PROTECT_FROM_MELEE, true);
    	sleep(random(200,400));
		tabs.open(Tab.INVENTORY);
		sleep(random(400,700));
    	status="Thieving from chests";
		if((skills.getDynamic(Skill.PRAYER))>15){
			if(chest.isVisible()){
				if((isInteracting==false) && (!myPlayer().isMoving())){
					getAmounts();
					chest.interact("Search for traps");
					sleep(random(600,900));
					//getMouse().click(mouseChest, true);
					//mouse.move(mouseBox);
				}
			}else{
				camera.toEntity(chest,true);
			}
		}else{
			drinkPPot();
		}
	}
	
	private int tempStonesThieved;
    private int tempDiamonds;
    private int tempEmeralds;
    private int tempSapphires;
    private int tempSharks;
    private int tempTunas;
    private int tempPikes;
    private int tempCoals;
    private int tempIrons;
    private int tempAshes;
    private int tempTinderboxes;
    private int tempMinds;
    private int tempChaos;
    private int tempDeaths;
    private int tempFires;
    private int tempCoins;
    private int tempClues;
	
	public void getAmounts(){
		tempStonesThieved=0;
	    tempDiamonds=0;
	    tempEmeralds=0;
	    tempSapphires=0;
	    tempSharks=0;
	    tempTunas=0;
	    tempPikes=0;
	    tempCoals=0;
	    tempIrons=0;
	    tempAshes=0;
	    tempTinderboxes=0;
	    tempMinds=0;
	    tempChaos=0;
	    tempDeaths=0;
	    tempFires=0;
	    tempCoins=0;
	    tempClues=0;
	    
		if(getInventory().contains("Dragonstone")){
			tempStonesThieved=inventory.getItem("Dragonstone").getAmount();
		}
		if(getInventory().contains("Diamond")){
			tempDiamonds=inventory.getItem("Diamond").getAmount();
		}
		if(getInventory().contains("Uncut emerald")){
			tempEmeralds=inventory.getItem("Uncut emerald").getAmount();
		}
		if(getInventory().contains("Uncut sapphire")){
			tempSapphires=inventory.getItem("Uncut sapphire").getAmount();
		}
		if(getInventory().contains("Shark")){
			tempSharks=inventory.getItem("Shark").getAmount();
		}
		if(getInventory().contains("Pike")){
			tempPikes=inventory.getItem("Pike").getAmount();
		}
		if(getInventory().contains("Raw tuna")){
			tempTunas=inventory.getItem("Raw tuna").getAmount();
		}
		if(getInventory().contains("Coal")){
			tempCoals=inventory.getItem("Coal").getAmount();
		}
		if(getInventory().contains("Iron ore")){
			tempIrons=inventory.getItem("Iron ore").getAmount();
		}
		if(getInventory().contains("Ashes")){
			tempAshes=inventory.getItem("Ashes").getAmount();
		}
		if(getInventory().contains("Tinderbox")){
			tempTinderboxes=inventory.getItem("Tinderbox").getAmount();
		}
		if(getInventory().contains("Mind rune")){
			tempMinds=inventory.getItem("Mind rune").getAmount();
		}
		if(getInventory().contains("Chaos rune")){
			tempChaos=inventory.getItem("Chaos rune").getAmount();
		}
		if(getInventory().contains("Death rune")){
			tempDeaths=inventory.getItem("Death rune").getAmount();
		}
		if(getInventory().contains("Fire rune")){
			tempFires=inventory.getItem("Fire rune").getAmount();
		}
		if(getInventory().contains("Coins")){
			tempCoins=inventory.getItem("Coins").getAmount();
		}
		if(getInventory().contains("Clue scroll(hard)")){
			tempClues=inventory.getItem("Clue scroll (hard)").getAmount();
		}
	}
	
	public void drinkPPot() throws InterruptedException{
		int q=random(13,17);
		status="Drinking prayer potion";
		while((skills.getDynamic(Skill.PRAYER))<q){
			if(inventory.contains("Prayer potion(1)")){
				inventory.interact("Drink","Prayer potion(1)");
				pPotsUsed=pPotsUsed+1;
				sleep(random(750,1200));
			}else if(inventory.contains("Prayer potion(2)")){
				inventory.interact("Drink","Prayer potion(2)");
				sleep(random(750,1200));
			}else if(inventory.contains("Prayer potion(3)")){
				inventory.interact("Drink","Prayer potion(3)");
				sleep(random(750,1200));
			}else if(inventory.contains("Prayer potion(4)")){
				inventory.interact("Drink","Prayer potion(4)");
				sleep(random(750,1200));
			}
		}
	}
	
	public void bank(int nFood) throws InterruptedException{
		Entity bankChest = objects.closest("Chest");
    	Entity booth = objects.closest("Bank booth");
    	nPots=gui.getNpots();
    	status="Banking";
    	while(((!isSupplied()) || (isBankNeeded())) || ((!foodInInv()) && (isFoodNeeded(nFoods())))){
    		if(getBank().isOpen()){
				if(getInventory().isEmpty()){
					sleep(random(678,944));
					if(((((LUMBRIDGE_BASEMENT.contains(myPlayer()))&&(useHRunes==true))&&(useGTabs==false))) || ((((LUMBRIDGE_BASEMENT.contains(myPlayer()))&&(useHTabs==true))&&(useGTabs==false)))){
						if(useHRunes==true){
							while(!inventory.contains("Air rune")){
								bank.withdraw("Air rune",2);
								sleep(random(389,644));
							}
							while(!inventory.contains("Earth rune")){
								bank.withdraw("Earth rune",2);
								sleep(random(389,644));
							}
							while(!inventory.contains("Law rune")){
								bank.withdraw("Law rune",2);
								sleep(random(389,644));
							}
						}else if(useHTabs==true){
							while(!inventory.contains("Teleport to house")){
								bank.withdraw("Teleport to house", 2);
								sleep(random(389,644));
							}
						}
					}else{
						if(useHRunes==true){
							while(!inventory.contains("Air rune")){
							bank.withdraw("Air rune",1);
							sleep(random(389,644));
							}
							while(!inventory.contains("Earth rune")){
							bank.withdraw("Earth rune",1);
							sleep(random(389,644));
							}
							while(!inventory.contains("Law rune")){
							bank.withdraw("Law rune",1);
							sleep(random(389,644));
							}
						}
					}
					while(!inventory.contains("Prayer potion(4)")){
					bank.withdraw("Prayer potion(4)",nPots);
					sleep(random(389,644));
					}
					if(useHTabs==true){
						while(!inventory.contains("Teleport to house")){
							bank.withdraw("Teleport to house", 1);
							sleep(random(389,644));
						}
					}
					if(useGTabs==true){
						while(!inventory.contains("Ghorrock teleport")){
							bank.withdraw("Ghorrock teleport", 1);
							sleep(random(389,644));
						}
					}
					if(useGRunes==true){
						while(!inventory.contains("Law rune")){
							bank.withdraw("Law rune", 2);
							sleep(random(389,644));
						}
						while(!inventory.contains("Water rune")){
							bank.withdraw("Water rune", 8);
							sleep(random(389,644));
						}
					}
					if(isFoodNeeded(nFoods())){
						while(!inventory.contains("Pike")){
						bank.withdraw("Pike",nFood);
						sleep(random(389,644));
						}
					}
					if((((isSupplied()) && (!isBankNeeded())))){
						bank.close();
						sleep(random(389,644));
					}
				}else{
					int kl=0;
					int gh=0;
					if(lootInInv()){
						kl=1;
					}
					getAmounts();
					getBankedAmounts();
					getBank().depositAll();
					sleep(random(789,844));
					if(inventory.isEmpty()){
						gh=1;
					}
					if((kl==1)&&(gh==1)){
						bankTrips=bankTrips+1;
						sleep(random(200,400));
					}
				}
			}else if(booth != null){
	    			if(booth.isVisible()){
	    				booth.interact("Bank");
	    				sleep(random(1000,1500));
	    			}
			}else if(LUMBRIDGE_BASEMENT.contains(myPlayer())){
				bankChest.interact("Bank");
				sleep(random(789,844));
			}
	    }
	}
	
    private int DiamondsBanked;
    private int EmeraldsBanked;
    private int SapphiresBanked;
    private int SharksBanked;
    private int TunasBanked;
    private int PikesBanked;
    private int CoalsBanked;
    private int IronsBanked;
    private int AshesBanked;
    private int TinderboxesBanked;
    private int MindsBanked;
    private int ChaosBanked;
    private int DeathsBanked;
    private int FiresBanked;
    private int CoinsBanked;
    private int CluesBanked;
	
	public void getBankedAmounts() throws InterruptedException{
		getAmounts();
		dStonesBanked=dStonesBanked+tempStonesThieved;
		DiamondsBanked=DiamondsBanked+tempDiamonds;
		EmeraldsBanked=EmeraldsBanked+tempEmeralds;
		SapphiresBanked=SapphiresBanked+tempSapphires;
		SharksBanked=SharksBanked+tempSharks;
		TunasBanked=TunasBanked+tempTunas;
		PikesBanked=PikesBanked+tempPikes;
		CoalsBanked=CoalsBanked+tempCoals;
		IronsBanked=IronsBanked+tempIrons;
		AshesBanked=AshesBanked+tempAshes;
		TinderboxesBanked=TinderboxesBanked+tempTinderboxes;
		MindsBanked=MindsBanked+tempMinds;
		ChaosBanked=ChaosBanked+tempChaos;
		DeathsBanked=DeathsBanked+tempDeaths;
		FiresBanked=FiresBanked+tempFires;
		CoinsBanked=CoinsBanked+tempCoins;
		CluesBanked=CluesBanked+tempClues;
	}
	
	public int nFoods(){
		int dHP;
		int nFood;
		dHP=(skills.getStatic(Skill.HITPOINTS))-(skills.getDynamic(Skill.HITPOINTS));
		nFood=(dHP/8); //cuz pike heals 8
		return nFood;
	}
	
	public void eatFood() throws InterruptedException{
		status="Eating food";
		while((skills.getDynamic(Skill.HITPOINTS))<((skills.getStatic(Skill.HITPOINTS))-8/*cuz pike heals 8*/)){
			inventory.interact("Eat","Pike");
			sleep(random(600,900));
		}
	}
	
    public void houseProcedure() throws InterruptedException{
    	Entity glory = objects.closest("Amulet of Glory");
    	if(RELLEKA_HOUSE.contains(myPlayer())){
    		status="Going to house";
				objects.closest("Portal").interact("Enter");
				sleep(random(1004,1321));
				if(dialogues.inDialogue()){
	    			dialogues.selectOption("Go to your house");
	    		}
    	}else if(map.isInHouse()){ 
    		status="In house";
				if ((skills.getDynamic(Skill.PRAYER))<(skills.getStatic(Skill.PRAYER))){
					sleep(random(1004,1321));
					prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
					prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
					status="Walking to altar";
					localWalker.walk(objects.closest("Altar"));
					sleep(random(1004,1321));
					while((skills.getDynamic(Skill.PRAYER))<(skills.getStatic(Skill.PRAYER))){
					status="Praying at altar";
					objects.closest("Altar").interact("Pray");
					sleep(random(1004,1321));
					}
				}
				while(map.isInHouse()){
						status="Walking to glory";
						mouse.moveRandomly();
						localWalker.walk(glory);
						sleep(random(2004,3321));
						status="Trying to teleport";
						if(glory.isVisible()){
							status="Teleporting with glory";
							camera.toBottom();
							glory.interact("Edgeville");
							sleep(random(2004,4321));
						}else{
							camera.toEntity(glory,true);
						}
				}
		}
    }
    
    public void walkToMamArea() throws InterruptedException{
    	status="Walking to the obelisk";
    	if(new Area(3134,3517,3155,3526).contains(myPlayer())){  //crossditcharea 1
	    	while(!OBELISK_MAM.contains(myPlayer())){
		    	if(map.canReach(OBELISK_MAM_MIDPP)){
		    		localWalker.walk(OBELISK_MAM_MID, true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3151,3618,0))){
		    		localWalker.walk(new Area(3148,3620,3153,3614), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3146,3613,0))){
		    		localWalker.walk(new Area(3143,3614,3149,3610), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3141,3607,0))){
		    		localWalker.walk(new Area(3139,3609,3144,3605), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3139,3602,0))){
		    		localWalker.walk(new Area(3136,3605,3142,3601), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3137,3598,0))){
		    		localWalker.walk(new Area(3134,3599,3139,3593), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3135,3590,0))){
		    		localWalker.walk(new Area(3132,3593,3138,3587), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3134,3589,0))){
		    		localWalker.walk(new Area(3131,3591,313,3587), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3134,3586,0))){
		    		localWalker.walk(new Area(3131,3588,3137,3584), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3137,3580,0))){
		    		localWalker.walk(new Area(3135,3584,3140,3580), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3134,3575,0))){
		    		localWalker.walk(new Area(3133,3577,3137,3573), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3134,3569,0))){
		    		localWalker.walk(new Area(3131,3570,3137,3566), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3133,3562,0))){
		    		localWalker.walk(new Area(3129,3564,3134,3561), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3129,3557,0))){
		    		localWalker.walk(new Area(3128,3558,3135,3554), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3131,3550,0))){
		    		localWalker.walk(new Area(3130,3551,3137,3547), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3137,3543,0))){
		    		localWalker.walk(new Area(3135,3544,3143,3541), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3142,3537,0))){
		    		localWalker.walk(new Area(3141,3538,3149,3534), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3142,3532,0))){
		    		localWalker.walk(new Area(3139,3534,3146,3530), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3143,3526,0))){
		    		localWalker.walk(new Area(3141,3528,3146,3525), true);
		    		sleep(random(2100,2900));
		    	}
	    	}
    	}else if(new Area(3078,3515,3099,3529).contains(myPlayer())){ //crossditcharea 2
    		while(!OBELISK_MAM.contains(myPlayer())){
	    		if(map.canReach(OBELISK_MAM_MIDPP)){
		    		localWalker.walk(OBELISK_MAM_MID, true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3151,3619,0))){
		    		localWalker.walk(new Area(3148,3622,3152,3617), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3142,3618,0))){
		    		localWalker.walk(new Area(3140,3620,3145,3615), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3137,3614,0))){
		    		localWalker.walk(new Area(3134,3616,3140,3612), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3131,3610,0))){
		    		localWalker.walk(new Area(3134,3608,3128,3611), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3128,3606,0))){
		    		localWalker.walk(new Area(3132,3604,3126,3607), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3124,3601,0))){
		    		localWalker.walk(new Area(3123,3602,3128,3597), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3121,3596,0))){
		    		localWalker.walk(new Area(3125,3591,3120,3597), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3117,3593,0))){
		    		localWalker.walk(new Area(3116,3594,3120,3588), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3113,3589,0))){
		    		localWalker.walk(new Area(3117,3586,3112,3590), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3108,3585,0))){
		    		localWalker.walk(new Area(3107,3586,3113,3581), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3108,3580,0))){
		    		localWalker.walk(new Area(3113,3576,3107,3582), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3107,3575,0))){
		    		localWalker.walk(new Area(3105,3576,3111,3571), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3109,3569,0))){
		    		localWalker.walk(new Area(3106,3570,3112,3566), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3107,3563,0))){
		    		localWalker.walk(new Area(3113,3559,3106,3564), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3107,3557,0))){
		    		localWalker.walk(new Area(3102,3561,3107,3554), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3102,3551,0))){
		    		localWalker.walk(new Area(3104,3549,3100,3553), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3096,3546,0))){
		    		localWalker.walk(new Area(3093,3548,3099,3542), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3087,3542,0))){
		    		localWalker.walk(new Area(3084,3544,3090,3539), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3088,3534,0))){
		    		localWalker.walk(new Area(3092,3531,3086,3536), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(2088,3530,0))){
		    		localWalker.walk(new Area(3086,3532,3090,3527), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3088,3526,0))){
		    		localWalker.walk(new Area(3085,3528,3090,3525), true);
		    		sleep(random(2100,2900));
		    	}
    		}
    	}
    }
    
    public void walkMamBank() throws InterruptedException{
    	status="Walking to bank";
    	if(OBELISK_MAM.contains(myPlayer())){
			while(!(new Area(3078,3515,3099,3529).contains(myPlayer()))){
				if(map.canReach(new Position(3087,3523,0))){
		    		localWalker.walk(CROSS_DITCH_AREA2, true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(2088,3530,0))){
		    		localWalker.walk(new Area(3086,3532,3090,3527), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3088,3534,0))){
		    		localWalker.walk(new Area(3092,3531,3086,3536), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3087,3542,0))){
		    		localWalker.walk(new Area(3084,3544,3090,3539), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3096,3546,0))){
		    		localWalker.walk(new Area(3093,3548,3099,3542), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3102,3551,0))){
		    		localWalker.walk(new Area(3104,3549,3100,3553), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3107,3557,0))){
		    		localWalker.walk(new Area(3102,3561,3107,3554), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3107,3563,0))){
		    		localWalker.walk(new Area(3113,3559,3106,3564), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3107,3575,0))){
		    		localWalker.walk(new Area(3105,3576,3111,3571), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3108,3580,0))){
		    		localWalker.walk(new Area(3113,3576,3107,3582), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3108,3585,0))){
		    		localWalker.walk(new Area(3107,3586,3113,3581), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3113,3589,0))){
		    		localWalker.walk(new Area(3117,3586,3112,3590), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3117,3593,0))){
		    		localWalker.walk(new Area(3116,3594,3120,3588), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3121,3596,0))){
		    		localWalker.walk(new Area(3125,3591,3120,3597), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3124,3601,0))){
		    		localWalker.walk(new Area(3123,3602,3128,3597), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3128,3606,0))){
		    		localWalker.walk(new Area(3132,3604,3126,3607), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3131,3610,0))){
		    		localWalker.walk(new Area(3134,3608,3128,3611), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3137,3614,0))){
		    		localWalker.walk(new Area(3134,3616,3140,3612), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3142,3618,0))){
		    		localWalker.walk(new Area(3140,3620,3145,3615), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3151,3619,0))){
		    		localWalker.walk(new Area(3148,3622,3152,3617), true);
		    		sleep(random(2100,2900));
		    	}
			}
    	}
	}
    
    public void walkToCrossDitchArea() throws InterruptedException{
    	status="Walking to ditch";
    	if(g>74){
	    	while(!CROSS_DITCH_AREA1.contains(myPlayer())){
		    	if(map.canReach(CROSS_DITCH_POINT)){
		    		localWalker.walk(CROSS_DITCH_AREA1, true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3136,3517,0))){
		    		localWalker.walk(new Area(3134,3519,3137,3516), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3131,3517,0))){
		    		localWalker.walk(new Area(3130,3518,3133,3516), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3124,3517,0))){
		    		localWalker.walk(new Area(3122,3518,3125,3515), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3118,3514,0))){
		    		localWalker.walk(new Area(3116,3515,3120,3512), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3114,3509,0))){
		    		localWalker.walk(new Area(3112,3510,3116,3507), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3108,3506,0))){
		    		localWalker.walk(new Area(3106,3507,3111,3502), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3101,3503,0))){
		    		localWalker.walk(new Area(3098,3504,3102,3499), true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3094,3502,0))){
		    		localWalker.walk(new Area(3092,3503,3097,3501), true);
		    		sleep(random(2100,2900));
		    	}
	    	}
    	}else{
    		while(!CROSS_DITCH_AREA2.contains(myPlayer())){
    			if(map.canReach(new Position(3088,3519,0))){
		    		localWalker.walk(CROSS_DITCH_AREA2, true);
		    		sleep(random(2100,2900));
		    	}else if(map.canReach(new Position(3086,3511,0))){
		    		localWalker.walk(new Area(3085,3513,3090,3507), true);
		    		sleep(random(3100,4900));
		    	}
    		}
    	}
    }
    
    @SuppressWarnings("deprecation")
	public void ObeliskTeleBank() throws InterruptedException{
    	status="Teleporting with obelisk";
    	while((!OBELISK_ENT.contains(myPlayer())) && (!OBELISK_MAM.contains(myPlayer()))){
			if (OBELISK_ROG.contains(myPlayer())){
				if((!isSafeROG())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
				prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
				prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
        		tabs.open(Tab.INVENTORY);
        			if(!myPlayer().isMoving()){
        				do{
        					mouse.moveRandomly();
	        				status="Teleporting with obelisk (Lvl 50)";
	        				objects.closest("Obelisk").interact("Activate");
			        		sleep(random(1150,1450));
							localWalker.walkPath(OBELISK_ROG_MIDP);
	    					sleep(random(3000,4000));
        				}while(OBELISK_ROG.contains(myPlayer()));
					}
			}else if(OBELISK_ICE.contains(myPlayer())){
				if((!isSafeICE())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
					prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
					prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
	        		tabs.open(Tab.INVENTORY);
	        				if(!myPlayer().isMoving()){
	        					do{
	        						mouse.moveRandomly();
									status="Teleporting with obelisk (Lvl 44)";
									objects.closest("Obelisk").interact("Activate");
				        			sleep(random(1150,1450));
									localWalker.walkPath(OBELISK_ICE_MIDP);
			    					sleep(random(3000,4000));
	        					}while(OBELISK_ICE.contains(myPlayer()));
							}
			}else if(OBELISK_MIN.contains(myPlayer())){
				if((!isSafeMIN())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
					prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
					prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
	        		tabs.open(Tab.INVENTORY);
	        			if(!myPlayer().isMoving()){
	        				do{
	        					mouse.moveRandomly();
								status="Teleporting with obelisk (Lvl 35)";
								objects.closest("Obelisk").interact("Activate");
				        		sleep(random(1150,1450));
								localWalker.walkPath(OBELISK_MIN_MIDP);
		    					sleep(random(3000,4000));
	        				}while(OBELISK_MIN.contains(myPlayer()));
						}
			}else if(OBELISK_BAN.contains(myPlayer())){
				if((!isSafeBAN())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
					prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
					prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
	        		tabs.open(Tab.INVENTORY);
	        			if(!myPlayer().isMoving()){
	        				do{
	        					mouse.moveRandomly();
								status="Teleporting with obelisk (Lvl 27)";
								objects.closest("Obelisk").interact("Activate");
				        		sleep(random(1150,1450));
								localWalker.walkPath(OBELISK_BAN_MIDP);
		    					sleep(random(3000,4000));
	        				}while(OBELISK_BAN.contains(myPlayer()));
						}
			}
		}
    }
    
    @SuppressWarnings("deprecation")
	public void ObeliskTeleCastle() throws InterruptedException{
    	status="Teleporting with obelisk";
    	while(!OBELISK_ROG.contains(myPlayer())){							
			if (OBELISK_ENT.contains(myPlayer())){
				if((!isSafeENT())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
				if(myPlayer().isUnderAttack()){
				prayer.set(PrayerButton.PROTECT_FROM_MELEE, true);
				sleep(random(200,400));
				}
				if(!myPlayer().isMoving()){
					do{
						mouse.moveRandomly();
						status="Teleporting with obelisk (Lvl 19)";
						objects.closest("Obelisk").interact("Activate");
		        		sleep(random(1150,1450));
						localWalker.walkPath(OBELISK_ENT_MIDP);
		    			sleep(random(3000,4000));
					}while(OBELISK_ENT.contains(myPlayer()));
				}
			}else if(OBELISK_MAM.contains(myPlayer())){
				if((!isSafeMAM())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
				if(myPlayer().isUnderAttack()){
					prayer.set(PrayerButton.PROTECT_FROM_MELEE, true);
					sleep(random(200,400));
					}
					if(!myPlayer().isMoving()){
						do{
							mouse.moveRandomly();
							status="Teleporting with obelisk (Lvl 13)";
							objects.closest("Obelisk").interact("Activate");
			        		sleep(random(1150,1450));
							localWalker.walkPath(OBELISK_MAM_MIDP);
		    				sleep(random(3000,4000));
						}while(OBELISK_MAM.contains(myPlayer()));
					}
			}else if(OBELISK_ICE.contains(myPlayer())){
				if((!isSafeICE())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
				prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
				prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
        		tabs.open(Tab.INVENTORY);
        		if(!myPlayer().isMoving()){
					do{
						mouse.moveRandomly();
	        			status="Teleporting with obelisk (Lvl 44)";
						objects.closest("Obelisk").interact("Activate");
		        		sleep(random(1150,1450));
						localWalker.walkPath(OBELISK_ICE_MIDP);
		    			sleep(random(3000,4000));
					}while(OBELISK_ICE.contains(myPlayer()));
				}
			}else if(OBELISK_MIN.contains(myPlayer())){
				if((!isSafeMIN())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
				prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
				prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
        		tabs.open(Tab.INVENTORY);
        		if(!myPlayer().isMoving()){
        			do{
        				mouse.moveRandomly();
						status="Teleporting with obelisk (Lvl 35)";
						objects.closest("Obelisk").interact("Activate");
		        		sleep(random(1150,1450));
						localWalker.walkPath(OBELISK_MIN_MIDP);
		    			sleep(random(3000,4000));
        			}while(OBELISK_MIN.contains(myPlayer()));
				}
			}else if(OBELISK_BAN.contains(myPlayer())){
				if((!isSafeBAN())&&(!myPlayer().isUnderAttack())){
					worldHopper.hop(getRandomWorld());
				}
				prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
				prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
        		tabs.open(Tab.INVENTORY);
        		if(!myPlayer().isMoving()){
        			do{
        				mouse.moveRandomly();
						status="Teleporting with obelisk (Lvl 27)";
						objects.closest("Obelisk").interact("Activate");
		        		sleep(random(1150,1450));
						localWalker.walkPath(OBELISK_BAN_MIDP);
		    			sleep(random(3000,4000));
        			}while(OBELISK_BAN.contains(myPlayer()));
				}
			}
		}
    }
    
    public void crossDitch() throws InterruptedException{
    	status="Crossing the ditch";
    	while(!crossedDitch()){
			if(objects.closest("Wilderness Ditch").isVisible()){
			objects.closest("Wilderness Ditch").interact("Cross");
    		sleep(random(900,1200));
			}else{
				camera.toEntity(objects.closest("Wilderness Ditch"),true);
				sleep(random(200,400));
			}
		}
    }

    public boolean isSupplied(){
		if((useHRunes==true)&&(useGTabs==false)){
	    	if((((getInventory().contains("Air rune"))&&(getInventory().contains("Earth rune")))&&(getInventory().contains("Law rune")))&&(getInventory().contains("Prayer potion(4)"))){
				return true;
			}else{
				return false;
			}
		}else if((useHRunes==true)&&(useGTabs==true)){
			if(((getInventory().contains("Ghorrock teleport"))&&((((getInventory().contains("Air rune"))&&(getInventory().contains("Earth rune")))&&(getInventory().contains("Law rune")))&&(getInventory().contains("Prayer potion(4)"))))){
				return true;
			}else{
				return false;
			}
		}else if(((((useHRunes==false)&&(useHTabs==false))&&(useGRunes==false))&&(useGTabs==false))){
			if(getInventory().contains("Prayer potion(4)")){
				return true;
			}else{
				return false;
			}
		}else if((useGRunes==true)&&(useHTabs==false)){
			if((((getInventory().contains("Water rune"))&&(getInventory().contains("Law rune")))&&(getInventory().contains("Prayer potion(4)")))){
				return true;
			}else{
				return false;
			}
		}else if((useGRunes==true)&&(useHTabs==true)){
			if(((((getInventory().contains("Water rune"))&&(getInventory().contains("Law rune")))&&(getInventory().contains("Prayer potion(4)"))))&&(getInventory().contains("Teleport to house"))){
				return true;
			}else{
				return false;
			}
		}else if((useHTabs==true)&&(useGTabs==true)){
			if((((getInventory().contains("Prayer potion(4)"))&&(getInventory().contains("Teleport to house")))&&(getInventory().contains("Ghorrock teleport")))){
				return true;
			}else{
				return false;
			}
		}else if((useHTabs==true)&&(useGTabs==false)){
			if((getInventory().contains("Prayer potion(4)"))&&(getInventory().contains("Teleport to house"))){
				return true;
			}else{
				return false;
			}
		}else if((useHTabs==false)&&(useGTabs==true)){
			if((getInventory().contains("Prayer potion(4)"))&&(getInventory().contains("Ghorrock teleport"))){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public boolean isBankNeeded(){
		if(((((getInventory().isEmpty()) || (getInventory().contains("Dragonstone")))))){
			return true;
		}else{
			return false;
		}
	}
	
    public boolean crossedDitch(){
    	if(((map.canReach(new Position(3139,3523,0))) || (((((map.canReach(new Position(3099,3523,0)) || (map.canReach(new Position(3096,3523,0)))) || (map.canReach(new Position(3091,3523,0))))) || (map.canReach(new Position(3090,3523,0))))))){
	    	return true;
	    }else{
	    	return false;
	    }	
    }
    
    public boolean lootInInv(){
    	if(inventory.contains("Mind rune")){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean isFoodNeeded(int b){
		if(b>1){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean foodInInv(){
		if((inventory.contains(351))){
			return true;
		}else{
			return false;
		}
	}
	
	//safe areas...
	public boolean isSafeArea1(){
    	List<Player> playersInArea1 = getPlayers().filter(new AreaFilter<Player>(RUN_AREA1));
    	if((((!playersInArea1.isEmpty())&&(playersInArea1.contains(myPlayer())))&&(playersInArea1.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
    }
	public boolean isSafeArea2(){
		List<Player> playersInArea2 = getPlayers().filter(new AreaFilter<Player>(RUN_AREA2));
		if((((!playersInArea2.isEmpty())&&(playersInArea2.contains(myPlayer())))&&(playersInArea2.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeArea3Gate(){
		List<Player> playersInArea3Gate = getPlayers().filter(new AreaFilter<Player>(RUN_AREA3_GATE));
    	if((((!playersInArea3Gate.isEmpty())&&(playersInArea3Gate.contains(myPlayer())))&&(playersInArea3Gate.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeArea4Tower(){
    	List<Player> playersInArea4Tower = getPlayers().filter(new AreaFilter<Player>(RUN_AREA4_TOWER));
    	if((((!playersInArea4Tower.isEmpty())&&(playersInArea4Tower.contains(myPlayer())))&&(playersInArea4Tower.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeArea5(){
    	List<Player> playersInArea5 = getPlayers().filter(new AreaFilter<Player>(new Area(3291,3946,3297,3927)));
    	if((((!playersInArea5.isEmpty())&&(playersInArea5.contains(myPlayer())))&&(playersInArea5.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeArea6(){
    	List<Player> playersInArea6 = getPlayers().filter(new AreaFilter<Player>(new Area(3275,3939,3281,3926)));
    	if((((!playersInArea6.isEmpty())&&(playersInArea6.contains(myPlayer())))&&(playersInArea6.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	///////
	//obelisk
	public boolean isSafeROG(){
		List<Player> playersInROG = getPlayers().filter(new AreaFilter<Player>(OBELISK_ROG));
    	if((((!playersInROG.isEmpty())&&(playersInROG.contains(myPlayer())))&&(playersInROG.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeICE(){
		List<Player> playersInICE = getPlayers().filter(new AreaFilter<Player>(OBELISK_ICE));
    	if((((!playersInICE.isEmpty())&&(playersInICE.contains(myPlayer())))&&(playersInICE.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeMIN(){
		List<Player> playersInMIN = getPlayers().filter(new AreaFilter<Player>(OBELISK_MIN));
    	if((((!playersInMIN.isEmpty())&&(playersInMIN.contains(myPlayer())))&&(playersInMIN.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeBAN(){
		List<Player> playersInBAN = getPlayers().filter(new AreaFilter<Player>(OBELISK_BAN));
    	if((((!playersInBAN.isEmpty())&&(playersInBAN.contains(myPlayer())))&&(playersInBAN.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeMAM(){
		List<Player> playersInMAM= getPlayers().filter(new AreaFilter<Player>(OBELISK_MAM));
    	if((((!playersInMAM.isEmpty())&&(playersInMAM.contains(myPlayer())))&&(playersInMAM.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public boolean isSafeENT(){
		List<Player> playersInENT= getPlayers().filter(new AreaFilter<Player>(OBELISK_ENT));
    	if((((!playersInENT.isEmpty())&&(playersInENT.contains(myPlayer())))&&(playersInENT.size()!=1))){
    		return false; //not safe
    	}else{
    		return true; // safe
    	}
	}
	public void runFromPker(){
		if((!isSafeArea4Tower())&&(isSafeArea3Gate())){
			
		}
	}
	
	
	public void pickupItemsOnGround() throws InterruptedException{
		status="Picking up item(s)";
		for(String s : itemsToPickup){
			GroundItem loot = this.groundItems.closest(s);
			if(loot != null){
				if(loot.interact("Take"));
				sleep(random(300,600));
			}
		}
	}
    
    private long timeBegan;
    private long timeRan;
    private int beginningXp;
    private int currentXp;
    private int xpGained;
    private int xpPerHour;
    private int totalXpPerHour;
    private int playerDied;
    private int bankTrips;
    private int pPotsUsed;
    private int potsLost;
    String status;
    private int nPots;
    private int dStonesBanked;
    
    //loot
    private int DStonesThieved;
    private int nDiamonds;
    private int nEmeralds;
    private int nSapphires;
    private int nSharks;
    private int nTunas;
    private int nPikes;
    private int nCoals;
    private int nIrons;
    private int nAshes;
    private int nTinderboxes;
    private int nMinds;
    private int nChaos;
    private int nDeaths;
    private int nFires;
    private int nCoins;
    private int nClues;
    
    Rectangle hideButton = new Rectangle(482,339,31,29);
    boolean showPaint=true;
    public boolean hopped;
    Rectangle chestButton = new Rectangle(446,419,64,53);
    boolean showLoot=false;
   
    @Override
    public void onStart(){
    	gui.setVisible(true);
    	hopped=false;
    	
    	this.bot.addMouseListener(new BotMouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
		        if(hideButton.contains(e.getPoint())) {
		        showPaint = !showPaint;
		        }
		        if(chestButton.contains(e.getPoint())){
		        	showLoot = !showLoot;
		        }
			}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public boolean blockInput(Point arg0) {
				return false;
			}
		});			
    	
    	timeBegan = System.currentTimeMillis();
    	playerDied=0;
    	tempDied=0;
    	bankTrips=0;
    	pPotsUsed=0;
    	potsLost=0;
    	dStonesBanked=0;
    	beginningXp = skills.getExperience(Skill.THIEVING); 
    	isInteracting=false;
    	
    	DStonesThieved = 0;
    	nDiamonds=0;
    	nSapphires=0;
    	nSharks=0;
    	nTunas=0;
    	nPikes=0;
    	nCoals=0;
    	nIrons=0;
    	nAshes=0;
    	nTinderboxes=0;
    	nMinds=0;
    	nChaos=0;
    	nDeaths=0;
    	nFires=0;
    	nCoins=0;
    	nClues=0;
    	
    	itemsToPickup = new ArrayList<String>();
    	itemsToPickup.add("Dragonstone");
    	itemsToPickup.add("Diamond");
    	itemsToPickup.add("Uncut emerald");
    	itemsToPickup.add("Uncut sapphire");
    	itemsToPickup.add("Shark");
    	itemsToPickup.add("Raw tuna");
    	itemsToPickup.add("Pike");
    	itemsToPickup.add("Coal");
    	itemsToPickup.add("Iron ore");
    	itemsToPickup.add("Ashes");
    	itemsToPickup.add("Mind rune");
    	itemsToPickup.add("Chaos rune");
    	itemsToPickup.add("Death rune");
    	itemsToPickup.add("Fire rune");
    	itemsToPickup.add("Coins");
    	itemsToPickup.add("Clue scroll(hard)");
    }
    
    public int getDstolen(){
    	return DStonesThieved;
    }
    public int getPdied(){
    	return playerDied;
    }
    public int getBankTrips(){
    	return bankTrips;
    }
    public long getTimeRan(){
    	return timeRan;
    }
    
    ///paint stuff
    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }

    private final Color color1 = new Color(102, 0, 102, 147);
    private final Color color2 = new Color(102, 0, 102, 180);
    private final Color color3 = new Color(255, 255, 255);
    private final Color color4 = new Color(0, 0, 0, 148);
    private final Color color5 = new Color(0, 0, 0, 117);
    private final Color color6 = new Color(153, 0, 153);

    private final BasicStroke stroke1 = new BasicStroke(1);
    private final BasicStroke stroke2 = new BasicStroke(2);

    private final Font font1 = new Font("Arial", 0, 13);
    private final Font font2 = new Font("Arial Black", 0, 15);
    private final Font font3 = new Font("Arial", 0, 15);

    private final Image img1 = getImage("http://vignette2.wikia.nocookie.net/2007scape/images/2/25/Dragonstone.png/revision/latest?cb=20140804055326");
    private final Image img2 = getImage("http://i.imgur.com/Xscyspx.png");
    private final Image img3 = getImage("http://i.imgur.com/MLo0MED.png");
    private final Image img4 = getImage("http://i.imgur.com/fXo42cu.png");
    private final Image img5 = getImage("http://vignette2.wikia.nocookie.net/2007scape/images/e/ea/Diamond.png/revision/latest?cb=20140427180651");
    private final Image img6 = getImage("http://vignette4.wikia.nocookie.net/2007scape/images/c/c4/Uncut_emerald.png/revision/latest?cb=20141007025745");
    private final Image img7 = getImage("http://vignette3.wikia.nocookie.net/2007scape/images/7/77/Uncut_sapphire.png/revision/latest?cb=20141007025813");
    private final Image img8 = getImage("http://vignette3.wikia.nocookie.net/2007scape/images/1/14/Shark.png/revision/latest?cb=20140510170208");
    private final Image img9 = getImage("http://vignette2.wikia.nocookie.net/2007scape/images/3/31/Pike.png/revision/latest?cb=20130914095650");
    private final Image img10 = getImage("http://vignette3.wikia.nocookie.net/2007scape/images/a/a7/Coal.png/revision/latest?cb=20141007033438");
    private final Image img11 = getImage("http://vignette1.wikia.nocookie.net/2007scape/images/e/e7/Iron_ore.png/revision/latest?cb=20141007033207");
    private final Image img12 = getImage("http://vignette3.wikia.nocookie.net/2007scape/images/f/f9/Ashes.png/revision/latest?cb=20140308033040");
    private final Image img13 = getImage("http://vignette1.wikia.nocookie.net/2007scape/images/d/dd/Tinderbox.png/revision/latest?cb=20140309033044");
    private final Image img14 = getImage("http://vignette4.wikia.nocookie.net/2007scape/images/9/99/Mind_rune.png/revision/latest?cb=20130228061027");
    private final Image img15 = getImage("http://vignette4.wikia.nocookie.net/2007scape/images/d/de/Chaos_rune.png/revision/latest?cb=20130309154137");
    private final Image img16 = getImage("http://vignette2.wikia.nocookie.net/2007scape/images/5/55/Death_rune.png/revision/latest?cb=20130309154233");
    private final Image img17 = getImage("http://vignette4.wikia.nocookie.net/2007scape/images/a/a2/Fire_rune.png/revision/latest?cb=20130303210635");
    private final Image img18 = getImage("http://vignette1.wikia.nocookie.net/2007scape/images/b/b6/Coins_100.png/revision/latest?cb=20130321223939");
    private final Image img19 = getImage("http://vignette3.wikia.nocookie.net/2007scape/images/7/7c/Clue_scroll_%28hard%29.png/revision/latest?cb=20140308010259");
    private final Image img20 = getImage("http://vignette3.wikia.nocookie.net/2007scape/images/b/b0/Raw_tuna.png/revision/latest?cb=20130802175810");




    ///
    
    @Override
    public void onPaint(Graphics2D g1){
    	timeRan = System.currentTimeMillis() - this.timeBegan;
    	currentXp = skills.getExperience(Skill.THIEVING);
    	xpGained = currentXp - beginningXp; 
    	xpPerHour = (int)(xpGained / ((System.currentTimeMillis() - timeBegan) / 3600000.0D));
    	totalXpPerHour = xpPerHour / 1000;
    	
    	Graphics2D g = (Graphics2D)g1;
	    if(showPaint){
	        g.drawImage(img2, 3, 338, null);
	        g.setColor(color1);
	        g.fillRect(16, 357, 485, 97);
	        g.setColor(color2);
	        g.setStroke(stroke1);
	        g.drawRect(16, 357, 485, 97);
	        g.setFont(font1);
	        g.setColor(color3);
	        g.drawString("Runtime: "+ft(timeRan), 20, 373); 
	        g.drawString("Status: "+status, 205, 373);
	        g.drawString("Dragonstones stolen/banked: "+DStonesThieved+"/"+dStonesBanked, 20, 394);
	        g.drawString("Pots consumed/lost: "+pPotsUsed+"/"+potsLost,20,413);
	        g.drawString("Succesfull banktrips: "+bankTrips, 20, 432);
	        g.drawString("Amount of deaths: "+playerDied,20,450);
	        g.setFont(font2);
	        g.drawString("Rogues'ChestThiever", 169, 354);
	        g.setFont(font3);
	        g.drawString("by dokato", 351, 354);
	        g.setColor(color4);
	        g.fillRect(482, 339, 31, 29);
	        g.setColor(color5);
	        g.drawRect(482, 339, 31, 29);
	        g.setColor(color6);
	        g.setStroke(stroke2);
	        g.drawLine(483, 340, 512, 366);
	        g.drawLine(511, 340, 484, 366);
	        if(!showLoot){
	        	g.drawImage(img3, 442, 413, null);
	        }else{
	        	int x=25;
	        	int x2=145;
	        	int y0=82;
	        	int n=31;
	        	int xt=x+40;
	        	int xt2=x2+40;
	        	int yt=y0+17;
	        	g.setColor(color1);
	        	g.fillRect(10, 20, 280, 325);
	        	g.drawImage(img4, 437, 394, null);
	        	g.setFont(font1);
	 	        g.setColor(color3);
	 	        g.drawString(""+DStonesThieved+"/"+dStonesBanked, xt, yt-n);
	 	        g.drawImage(img1, x, y0-n, null);
	 	        g.drawString(""+nDiamonds+"/"+DiamondsBanked, xt, yt);
	 	        g.drawImage(img5, x, y0, null);
	 	        g.drawString(""+nEmeralds+"/"+EmeraldsBanked, xt, yt+n);
	 	        g.drawImage(img6, x, y0+n, null);
	 	        g.drawString(""+nSapphires+"/"+SapphiresBanked, xt, yt+n*2);
	 	        g.drawImage(img7, x, y0+n*2, null);
	 	        g.drawString(""+nSharks+"/"+SharksBanked, xt+4, yt+n*3);
	 	        g.drawImage(img8, x, y0+n*3, null);
	 	        g.drawString(""+nPikes+"/"+PikesBanked, xt+4, yt+n*4);
	 	        g.drawImage(img9, x, y0+n*4, null);
	 	        g.drawString(""+nTunas+"/"+TunasBanked, xt+4, yt+n*5);
	 	        g.drawImage(img20, x, y0+n*5, null);
	 	        g.drawString(""+nCoals+"/"+CoalsBanked, xt+6, yt+n*6);
		 	    g.drawImage(img10, x, y0+n*6, null);
		 	    g.drawString(""+nIrons+"/"+IronsBanked, xt+6, yt+n*7);
		 	    g.drawImage(img11, x, y0+n*7, null);
		 	    g.drawString(""+nAshes+"/"+AshesBanked, xt2+2, yt);
		 	    g.drawImage(img12, x2+6, y0+6, null);
		 	    g.drawString(""+nTinderboxes+"/"+TinderboxesBanked, xt2+3, yt+n);
		 	    g.drawImage(img13, x2, y0+n, null);
		 	    g.drawString(""+nMinds+"/"+MindsBanked, xt2+3, yt+n*2);
		 	    g.drawImage(img14, x2, y0+n*2, null);
		 	    g.drawString(""+nChaos+"/"+ChaosBanked, xt2+3, yt+n*3);
		 	    g.drawImage(img15, x2, y0+n*3, null);
		 	    g.drawString(""+nDeaths+"/"+DeathsBanked, xt2+3, yt+n*4);
		 	    g.drawImage(img16, x2, y0+n*4, null);
		 	    g.drawString(""+nFires+"/"+FiresBanked, xt2+3, yt+n*5);
		 	    g.drawImage(img17, x2, y0+n*5, null);
		 	    g.drawString(""+nCoins+"/"+CoinsBanked, xt2+2, yt+n*6);
		 	    g.drawImage(img18, x2, y0+n*6, null);
		 	    g.drawString(""+nClues+"/"+CluesBanked, xt2+6, yt+n*7);
		 	    g.drawImage(img19, x2, y0+n*7, null);
		 	    g.setFont(font3);
		 	    g.drawString("Stolen/Banked:", x, 40);
	        }
	    }else{
		    g.drawImage(img1, 488, 341, null);

	    }
    	
    }
    
    private String ft(long duration){ //the method will format seconds, minutes & hours into seconds. Otherwise the time ran would show in milliseconds and that would get majorly confusing.
		String res = "";
		long days = TimeUnit.MILLISECONDS.toDays(duration);
		long hours = TimeUnit.MILLISECONDS.toHours(duration)
		- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
		- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
		.toHours(duration));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
		- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
		.toMinutes(duration));
		if (days == 0){
		res = (hours + ":" + minutes + ":" + seconds);
		}else{
		res = (days + ":" + hours + ":" + minutes + ":" + seconds);
		}
		return res;
	} 
    
    public boolean isInteracting;
    
    public void onMessage(Message message) throws java.lang.InterruptedException {
		String deathTxt = message.getMessage().toLowerCase();
		String startThievTxt = message.getMessage().toLowerCase();
		
		String stoneTxt = message.getMessage().toLowerCase();
		String diamondTxt = message.getMessage().toLowerCase();
		String emeraldTxt = message.getMessage().toLowerCase();
		String sapphireTxt = message.getMessage().toLowerCase();
		String sharkTxt = message.getMessage().toLowerCase();
		String tunaTxt = message.getMessage().toLowerCase();
		String pikeTxt = message.getMessage().toLowerCase();
		String coalTxt = message.getMessage().toLowerCase();
		String ironTxt = message.getMessage().toLowerCase();
		String ashesTxt = message.getMessage().toLowerCase();
		String tinderboxTxt = message.getMessage().toLowerCase();
		String mindTxt = message.getMessage().toLowerCase();
		String chaosTxt = message.getMessage().toLowerCase();
		String deathrTxt = message.getMessage().toLowerCase();
		String fireTxt = message.getMessage().toLowerCase();
		String coinsTxt = message.getMessage().toLowerCase();
		String clueTxt = message.getMessage().toLowerCase();
		
		
		if(startThievTxt.contains("search the chest")){
			isInteracting=true;
			sleep(random(5900,7200));
			isInteracting=false;
		}
		if(deathTxt.contains("you are dead")){
			playerDied=playerDied+1;
		}
		
		if(stoneTxt.contains("dragonstones")){
    		DStonesThieved=DStonesThieved+(inventory.getItem("Dragonstone").getAmount()-tempStonesThieved);
    		sleep(random(600,900));
    	}
		if(diamondTxt.contains("diamond")){
			nDiamonds=nDiamonds+(inventory.getItem("Diamond").getAmount()-tempDiamonds);
    		sleep(random(600,900));
    	}
		if(emeraldTxt.contains("emerald")){
			nEmeralds=nEmeralds+(inventory.getItem("Uncut emerald").getAmount()-tempEmeralds);
    		sleep(random(600,900));
    	}
		if(sapphireTxt.contains("sapphire")){
			nSapphires=nSapphires+(inventory.getItem("Uncut sapphire").getAmount()-tempSapphires);
    		sleep(random(600,900));
    	}
		if(sharkTxt.contains("shark")){
			nSharks=nSharks+(inventory.getItem("Shark").getAmount()-tempSharks);
    		sleep(random(600,900));
    	}
		if(tunaTxt.contains("tuna")){
			nTunas=nTunas+(inventory.getItem("Raw tuna").getAmount()-tempTunas);
    		sleep(random(600,900));
    	}
		if(pikeTxt.contains("pike")){
			nPikes=nPikes+(inventory.getItem("Pike").getAmount()-tempPikes);
    		sleep(random(600,900));
    	}
		if(coalTxt.contains("coal")){
			nCoals=nCoals+(inventory.getItem("Coal").getAmount()-tempCoals);
    		sleep(random(600,900));
    	}
		if(ironTxt.contains("iron")){
			nIrons=nIrons+(inventory.getItem("Iron ore").getAmount()-tempIrons);
    		sleep(random(600,900));
    	}
		if(ashesTxt.contains("ashes")){
			nAshes=nAshes+(inventory.getItem("Ashes").getAmount()-tempAshes);
    		sleep(random(600,900));
    	}
		if(tinderboxTxt.contains("tinderboxe")){
			nTinderboxes=nTinderboxes+(inventory.getItem("Tinderbox").getAmount()-tempTinderboxes);
    		sleep(random(600,900));
    	}
		if(mindTxt.contains("mind runes")){
			nMinds=nMinds+(inventory.getItem("Mind rune").getAmount()-tempMinds);
    		sleep(random(600,900));
    	}
		if(chaosTxt.contains("chaos runes")){
			nChaos=nChaos+(inventory.getItem("Chaos rune").getAmount()-tempChaos);
    		sleep(random(600,900));
    	}
		if(deathrTxt.contains("death runes")){
			nDeaths=nDeaths+(inventory.getItem("Death rune").getAmount()-tempDeaths);
    		sleep(random(600,900));
    	}
		if(fireTxt.contains("fire runes")){
			nFires=nFires+(inventory.getItem("Fire rune").getAmount()-tempFires);
    		sleep(random(600,900));
    	}
		if(coinsTxt.contains("coins")){
			nCoins=nCoins+(inventory.getItem("Coins").getAmount()-tempCoins);
    		sleep(random(600,900));
    	}if(clueTxt.contains("clue")){
			nClues=nClues+(inventory.getItem("Clue scroll (hard)").getAmount()-tempClues);
    		sleep(random(600,900));
    	}
	}
    
    public int potsInv(){
    	int amPots=0;
    	if((((getInventory().contains("Prayer potion(4)"))||(getInventory().contains("Prayer potion(3)")))||(getInventory().contains("Prayer potion(2)")))||(getInventory().contains("Prayer potion(1)"))){
    		int pots4=0;
    		int pots3=0;
    		int pots2=0;
    		int pots1=0;
    		if(getInventory().contains("Prayer potion(4)")){
    			pots4=inventory.getItem("Prayer potion(4)").getAmount();
    		}
    		if(getInventory().contains("Prayer potion(3)")){
    			pots3=inventory.getItem("Prayer potion(3)").getAmount();
    		}
    		if(getInventory().contains("Prayer potion(2)")){
    			pots2=inventory.getItem("Prayer potion(2)").getAmount();
    		}
    		if(getInventory().contains("Prayer potion(1)")){
    			pots1=inventory.getItem("Prayer potion(1)").getAmount();
    		}
    		pPotsInInv=(((pots4+pots3)+pots2)+pots1);
    	}
    	return amPots;
    }
    
    int i=3;
	int b=5;
    int f = random(44,99);
    int g = random(44,99);
    int t = 0;
    int x=1;
    int tempDied;
    int pPotsInInv;
    int diedPots;
    
    @SuppressWarnings("deprecation")
	@Override
    public int onLoop() throws InterruptedException {
    	pPotsInInv=potsInv();
    	if((((!isSafeArea1())||(!isSafeArea2()))||(!isSafeArea3Gate()))||(!isSafeArea4Tower())){
    			if(!myPlayer().isUnderAttack()){
    				worldHopper.hop(getRandomWorld());
    			}else{
    				do{
    					mouse.moveRandomly();
		    			status="running away";
		    			if(!isSafeArea4Tower()){
			    			localWalker.walkPath(RUN_TO_OUTSIDE);
				    		while(myPlayer().isOnScreen()){
				    			worldHopper.hop(getRandomWorld());
				    		}
		    			}else{
		    				do{
		    					mouse.moveRandomly();
			    				if(!map.canReach(new Position(3281,3935,0))){
			    					localWalker.walkPath(RUN_TO_TOWER);
			    					objects.closest(14749).interact("Open");
			    					sleep(random(900,1200));
			    					objects.closest("Staircase").interact("Climb-up");
			    					sleep(random(600,900));
			    				}else{
			    					localWalker.walkPath(RUN_TO_TOWER_STAIRCASE);
			    					objects.closest("Staircase").interact("Climb-up");
			    					sleep(random(300,600));
			    				}
			    				if(myPosition().getZ()==1){
			    					objects.closest("Staircase").interact("Climb-up");
			    					sleep(random(300,600));
			    				}else if(myPosition().getZ()==2){
			    					objects.closest("Staircase").interact("Climb-up");
			    					sleep(random(300,600));
			    				}else if(myPosition().getZ()==3){
			    					while(myPlayer().isOnScreen()){
			    			    		worldHopper.hop(getRandomWorld());
			    			    	}
			    				}
		    				}while(myPlayer().isOnScreen());
		    			}
    				}while(myPlayer().isOnScreen());
    			}
    	
    	}else{
    		if(gui.disposed()==true){
		    	f = random(44,99);
		    	g = random(44,99);
		    	Entity trapdoor = objects.closest("Trapdoor");
		    	Entity chest = objects.closest(CHEST_AREA, 26757);
		    	useHRunes=gui.getUseHRunes();
		    	useHTabs=gui.getUseHTabs();
		    	useGRunes=gui.getUseGRunes();
		    	useGTabs=gui.getUseGTabs();
		    	
		    	if(myPosition().getZ()==3){
		    		objects.closest("Staircase").interact("Climb-down");
					sleep(random(300,600));
		    	}else if(myPosition().getZ()==2){
					objects.closest("Staircase").interact("Climb-down");
					sleep(random(300,600));
				}else if(myPosition().getZ()==1){
					objects.closest("Staircase").interact("Climb-down");
					sleep(random(300,600));
				}else if((RUN_AREA4_TOWER.contains(myPlayer()))&&(!map.canReach(chest))){
					objects.closest(new Area(3281,3938,3278,3941),"Door").interact("Open");
				}
		    	
		    	/*if(ALL_ROG_AREA.contains(myPlayer())){
		    	pickupItemsOnGround();
		    	}else*/ 
		    	if(LUMBRIDGE_CASTLE_KITCHEN.contains(myPlayer())){
		    		status="Going to the bank chest";
					trapdoor.interact("Climb-down");
					sleep(random(963,1236));
				}else if(LUMBBRIDGE_CASTLE_AREA.contains(myPlayer())){
					potsLost=potsLost+(diedPots-potsInv());
					sleep(random(200,400));
					camera.toTop();
					prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
					prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
					if((playerDied>0)&&(playerDied>tempDied)){
						worldHopper.hop(getRandomWorld());
						tempDied=playerDied;
					}else{	
					status="Going to the bank chest";
		    		localWalker.walk(LUMBRIDGE_CASTLE_KITCHEN, true);
		    		sleep(random(5801,7311));
					}
		    	}else if(LUMBRIDGE_BASEMENT.contains(myPlayer())){
		    		status="Going to the bank chest";
		    		localWalker.walk(LUMBRIDGE_BASEMENT_BANKP, true);
		    		sleep(random(2453,5154));
		    		if(((isSupplied()) && (!isBankNeeded())) && (!isFoodNeeded(nFoods()))){
		    			if((useHRunes==true)&&(useGTabs==false)){
			    			status="Teleporting to house";
			    			magic.castSpell(Spells.NormalSpells.HOUSE_TELEPORT);
			        		sleep(random(823,1263));
			    			tabs.open(Tab.INVENTORY);
			    			sleep(random(4125,5621));
		    			}else if(useGTabs==true){
		    				status="Teleporting to ice plateau";
	        				inventory.interact("Break","Ghorrock teleport");
	        				status="Teleporting to Ice Plateau";
	        				sleep(random(4800,5500));
		    			}else if(useGRunes==true){
		    				status="Teleporting to ice plateau";
	    					magic.castSpell(Spells.AncientSpells.GHORROCK_TELEPORT);
	    					sleep(random(300,600));
	    					status="Teleporting to Ice Plateau";
	    					tabs.open(Tab.INVENTORY);
	    					sleep(random(3500,4000));
	        			}else if((useHTabs==true)&&(useGTabs==false)){
	        				status="Teleporting to house";
	        				inventory.interact("Break","Teleport to house");
	    					status="Teleporting to house";
	    					sleep(random(823,1263));
	        			}
		    		}else{
		    			bank(nFoods());
		    			sleep(random(600,900));
		    			if(isFoodNeeded(nFoods())){
			    			eatFood();
			    		}
		    		}
		    	}else if((map.isInHouse()) || (RELLEKA_HOUSE.contains(myPlayer()))){
		    		houseProcedure();
		    	}else if ((((((((inventory.contains("Prayer potion(1)")) || (inventory.contains("Prayer potion(2)"))) || (inventory.contains("Prayer potion(3)"))) || (inventory.contains("Prayer potion(4)")))) || (skills.getDynamic(Skill.PRAYER))>15))){
		    		diedPots=pPotsInInv;
		    		sleep(random(200,400));
		    		if (CHEST_AREA.contains(myPlayer())){
			    			prayer.set(PrayerButton.PROTECT_FROM_MELEE, true);
			        		sleep(random(200,400));
			    			tabs.open(Tab.INVENTORY);
			        		sleep(random(200,400));
			    			if(!myPlayer().isMoving()){
			    				if(inventory.isItemSelected()){
			    					inventory.deselectItem();
			    				}
			    				camera.toTop();
			    				thiev();
			    			}
			    		}else if(((NEAR_ROG_AREA.contains(myPlayer())) && (!CHEST_AREA.contains(myPlayer()))) || (NEAR_CHEST_AREA.contains(myPlayer()))){
			    			prayer.set(PrayerButton.PROTECT_FROM_MAGIC,false);
			        		tabs.open(Tab.INVENTORY);
			    			status="Walking to the chests";
			    			localWalker.walk(CHEST_AREA, true);
			    			sleep(random(1750,2875));
			    		}else if((((ALL_ROG_AREA.contains(myPlayer()) && !CHEST_AREA.contains(myPlayer())) && !NEAR_ROG_AREA.contains(myPlayer()))) || OBELISK_ROG.contains(myPlayer())){
			    			prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
			    			tabs.open(Tab.INVENTORY);
			    			if(myPlayer().isUnderAttack()){
			    			prayer.set(PrayerButton.PROTECT_FROM_MAGIC, true);
			    			}
			    			status="Walking to the chests";
			    			localWalker.walk(NEAR_ROG_AREA, true);
			    			sleep(random(2950,3775));
			    		}else if(ICE_ALL_AREA.contains(myPlayer())&&(!OBELISK_ICE.contains(myPlayer()))){
			    			localWalker.walk(OBELISK_ICE_MID, true);
			    			sleep(random(800,1250));
			    		}else if((((((OBELISK_ENT.contains(myPlayer()))) || (OBELISK_ICE.contains(myPlayer()))) || (OBELISK_MIN.contains(myPlayer()))) || (OBELISK_BAN.contains(myPlayer()))) || (OBELISK_MAM.contains(myPlayer()))){
			    			ObeliskTeleCastle();
			    		}else if(((((CROSS_DITCH_AREA1.contains(myPlayer()) || (new Area(3134,3517,3155,3526).contains(myPlayer())))) && (crossedDitch()))) || (((CROSS_DITCH_AREA2.contains(myPlayer()) || (new Area(3078,3516,3099,3529).contains(myPlayer()))) && (crossedDitch())))){
			    			walkToMamArea();
			        	}else if((((CROSS_DITCH_AREA1.contains(myPlayer()) || (new Area(3134,3517,3155,3526).contains(myPlayer()))) && (!crossedDitch()))) || (((CROSS_DITCH_AREA2.contains(myPlayer()) || (new Area(3078,3515,3099,3529).contains(myPlayer()))) && (!crossedDitch())))){
			        		crossDitch();
			        	}else if((EDG_BANK.contains(myPlayer())) || (EDG_BANK2.contains(myPlayer()))){
			        		if(((((isSupplied()) && (!isBankNeeded()))) && (!isFoodNeeded(nFoods())))){
			        			if(useGTabs==true){
			        				if(getInventory().contains("Ghorrock teleport")){
			        					inventory.interact("Break","Ghorrock teleport");
			        					status="Teleporting to Ice Plateau";
			        					sleep(random(4500,5000));
			        				}else{
			        					bank(nFoods());
			        				}
			        			}else if(useGRunes==true){
			        					magic.castSpell(Spells.AncientSpells.GHORROCK_TELEPORT);
			        					status="Teleporting to Ice Plateau";
			        					sleep(random(2500,3000));
			        			}else{
			        			walkToCrossDitchArea();
			        			}
			        		}else{
			        			if((foodInInv()) && (isFoodNeeded(nFoods()))){
					    			eatFood();
					    		}else{
					    			bank(nFoods());
				        			sleep(random(600,900));
					    		}
			        		}
			        	}else if((((EDGEVILLE_AREA.contains(myPlayer())) && ((!EDG_BANK.contains(myPlayer())) && (!EDG_BANK2.contains(myPlayer())))) && ((!isSupplied()) || (isBankNeeded())))){
			        		camera.toTop();
			        		status="Walking to bank";
			        		localWalker.walk(EDG_BANK, true);
			        		sleep(random(960,1250));
			        		bank(nFoods());
			        	}else if(((((EDGEVILLE_AREA.contains(myPlayer()))) && (((!EDG_BANK.contains(myPlayer())) && (!EDG_BANK2.contains(myPlayer())))) && ((isSupplied()) && (!isBankNeeded()))))){
			        		if(useGTabs==true){
		        				inventory.interact("Break","Ghorrock teleport");
		        				status="Teleporting to Ice Plateau";
		        				sleep(random(4800,5500));
		        			}else if(useGRunes==true){
	        					magic.castSpell(Spells.AncientSpells.GHORROCK_TELEPORT);
	        					status="Teleporting to Ice Plateau";
	        					sleep(random(2500,3000));
		        			}else{
		        				walkToCrossDitchArea();
		        			}
			        		
			        	}
			    		
			    }else{
				    	if((CHEST_AREA.contains(myPlayer())) && (!NEAR_ROG_AREA.contains(myPlayer()))){ 
				    			status="Walking to obelisk";
				    			localWalker.walk(NEAR_ROG_AREA, true); 
				    			sleep(random(2750,3875));
				    	}else if(NEAR_ROG_AREA.contains(myPlayer()) && (!OBELISK_ROG.contains(myPlayer())) && (ALL_ROG_AREA.contains(myPlayer()))){ //DIT
				    			status="Walking to obelisk";
				    			prayer.set(PrayerButton.PROTECT_FROM_MAGIC, true);
					    		localWalker.walk(OBELISK_ROG, true);
					    		sleep(random(2750,3875));
				    	}else if((!OBELISK_ROG.contains(myPlayer())) && (ALL_ROG_AREA.contains(myPlayer())) && (!NEAR_ROG_AREA.contains(myPlayer())) && (!CHEST_AREA.contains(myPlayer()))){  //EN DIT kon samengevoegd worden, maar heb ik toch maar niet gedaan
				    			status="Walking to obelisk";
				    			if(!myPlayer().isUnderAttack()){
				    			prayer.set(PrayerButton.PROTECT_FROM_MAGIC, false);
				    			}
					    		localWalker.walk(OBELISK_ROG, true);
					    		sleep(random(2750,3875));
				    	}else if((((OBELISK_ROG.contains(myPlayer())) || (OBELISK_ICE.contains(myPlayer()))) || (OBELISK_MIN.contains(myPlayer()))) || (OBELISK_BAN.contains(myPlayer())) ){
					    		prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
					    		ObeliskTeleBank();
				    	}else if((OBELISK_ENT.contains(myPlayer())) || (OBELISK_MAM.contains(myPlayer()))){
					    		if(useHRunes==true){
				    				magic.castSpell(Spells.NormalSpells.HOUSE_TELEPORT);
					    			sleep(random(823,1263));
					    			tabs.open(Tab.INVENTORY);
					    			sleep(random(4125,5621));
					    		}else if(useHTabs==true){
					    			inventory.interact("Break","Teleport to house");
			    					status="Teleporting to house";
			    					sleep(random(823,1263));
					    		}else if((useHRunes==false)&&(useHTabs==false)){
					    			walkMamBank();
					    		}
				    	}else if(((new Area(3078,3515,3099,3529).contains(myPlayer())))&&(crossedDitch())){ //all cross ditch area 2
		    				status="crossing ditch";
				    		if(objects.closest("Wilderness Ditch").isVisible()){
		    					objects.closest("Wilderness Ditch").interact("Cross");
		    		    		sleep(random(900,1200));
		    					}else{
		    						camera.toEntity(objects.closest("Wilderness Ditch"),true);
		    						sleep(random(200,400));
		    				}
		    			}else if((map.isInHouse()) || (RELLEKA_HOUSE.contains(myPlayer()))){
					    		houseProcedure();
				    	}else if((EDGEVILLE_AREA.contains(myPlayer())) && (!EDG_BANK.contains(myPlayer()))){
				    		camera.toTop();
				    		sleep(random(1487,1896));
				    		status="Walking to bank";
				    		localWalker.walk(EDG_BANK, true);
				    		sleep(random(850,1210));
				    	}else if(EDG_BANK.contains(myPlayer()) || (EDG_BANK2.contains(myPlayer()))){
				    		bank(nFoods());
				    		sleep(random(850,1210));
				    		if(isFoodNeeded(nFoods()) && (foodInInv())){
				    			eatFood();
				    		}
				    	}
		    	}
    		}
    	}
    return 100; 
    }
    
    @Override
    public void onExit(){
        //Code here will execute after the script ends
    }
    
}