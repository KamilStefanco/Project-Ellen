package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper,Alive,Armed {
    private int speed;
    private int ammo;
    private Animation playerAnimation;
    private Animation playerDeadAnimation;
    private Backpack backpack;
    private Health HP;
    private Firearm firearm;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    public Ripley(){
        super("Ellen");
        playerAnimation = new Animation("sprites/player.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        playerDeadAnimation = new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE);
        setAnimation(playerAnimation);
        playerAnimation.pause();
        this.speed =2;
        this.ammo = 250;
        backpack = new Backpack("Ripley's backpack",10);
        this.HP = new Health(100,100);
        this.firearm = new Gun(30,200);


        HP.onExhaustion(new Health.ExhaustionEffect() {

            @Override
            public void apply() {
                died();
            }
        });

    }

    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    public int getAmmo(){
        return ammo;
    }

    public void setAmmo(int value){
        this.ammo = value;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        playerAnimation.setRotation(direction.getAngle());
        playerAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        this.playerAnimation.pause();
    }

    public void showRipleyState(Scene scene){
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("Energy: " +HP.getValue(), 120, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: " +getFirearm().getAmmo(), 300, yTextPos);
    }

    private void died(){
        Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED,this);
        setAnimation(playerDeadAnimation);
    }

    @Override
    public Health getHealth() {
        return HP;
    }

    @Override
    public Firearm getFirearm() {
        return firearm;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.firearm = weapon;
    }

}
