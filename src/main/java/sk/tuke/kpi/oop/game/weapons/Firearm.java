package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {

    private int ammo;
    private int ammoMax;

    public Firearm(int ammo){
        this.ammo = ammo;
        this.ammoMax = ammo;
    }

    public Firearm(int ammo,int ammoMax){
        this.ammo = ammo;
        this.ammoMax = ammoMax;
    }

    public int getAmmo(){
        return this.ammo;
    }

    public void setAmmo(int value){
        this.ammo = value;
    }

    public void reload(int newAmmo){
        if((newAmmo + ammo) >= ammoMax){
            ammo = ammoMax;
        }
        else{
            ammo = ammo + newAmmo;
        }
    }

    protected abstract Fireable createBullet();

    public Fireable fire(){
        if(getAmmo() > 0){
            setAmmo(getAmmo()-1);
            return createBullet();
        }
        return null;
    }
}
