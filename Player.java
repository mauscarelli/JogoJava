public class Player {
    private int playerID;
    private int townCenterHP;
    private int troopHp;
    private int troopAttack;
    private int citizen;
    private int gold;
    private int food;
    private int goldProd;
    private int foodProd;

    public Player(int id) {
        playerID = id;
        townCenterHP = 1000;
        troopHp = 0;
        troopAttack = 0;
        citizen = 4;
        gold = 10;
        food = 10;
        goldProd = 0;
        foodProd = 0;
    }

    public int getCenterHealth() {
        return townCenterHP;
    }
    public void setHealt(int v){
        townCenterHP = v;
    }

    public int getCitizen() {
        return citizen;
    }
    public int getFood() {
        return food;
    }
    public int getGold() {
        return gold;
    }
    public int getCitizenFood() {
        return foodProd;
    }
    public int getCitizenGold() {
        return goldProd;
    }

    public int getTroopAttack() {
        return troopAttack;
    }

    public int getTroopHealth() {
        return troopHp;
    }

    public void addCitizen() {
        if (food >= 30) {
            citizen++;
            food -= 30;
        }
    }

    public void addSoldier() {
        if (food >= 20 && gold >= 40) {
            troopAttack += 2;
            troopHp += 20;
            food -= 20;
            gold -= 40;
        }
    }

    public void tickProd() {
        gold += goldProd;
        food += foodProd;
    }

    public void addCitizenGold() {
        if (goldProd + foodProd < citizen)
            goldProd++;
    }

    public void addCitizenFood() {
        if (goldProd + foodProd < citizen)
            foodProd++;
    }

    public void removeCitizenGold() {
        if (goldProd > 0)
            goldProd--;
    }

    public void removeCitizenFood() {
        if (foodProd > 0)
            foodProd--;
    }

    public void tickAttack(int troopDmg) {
        if (troopHp > 0)
            troopHp -= troopDmg;
        townCenterHP -= troopDmg;
        if (troopHp <= 0) {
            troopHp = 0;
            troopAttack = 0;
        }
        if (townCenterHP <= 0) {
            townCenterHP = 0;
            Derrota();
        }
    }

    public void Derrota() {

    }

    public void Vitoria() {

    }

}