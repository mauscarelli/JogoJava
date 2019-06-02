import javafx.scene.effect.GlowBuilder;

public class Player{
    private int playerID;
    private int townCenterHP;
    private int troopHp;
    private int troopAttack;
    private int citizen;
    private int gold;
    private int food;
    private int goldProd;
    private int foodProd;


    public Player(int id){
        playerID = id;
        townCenterHP = 1000;
        troopHp=0;
        troopAttack=0;
        citizen = 4;
        gold = 10;
        food = 10;
        goldProd = 0;
        foodProd = 0;
    }

    public int getCenterHealth(){
        return townCenterHP;
    }
    public int getCitizen(){
        return citizen;
    }
    public int getTroopAttack(){
        return troopAttack;
    }
    public int getTroopHealth(){
        return troopHp;
    }

}