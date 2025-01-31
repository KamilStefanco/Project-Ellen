package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;


public class Door extends AbstractActor implements Openable, Usable<Actor> {

    private Animation vDoor;
    private boolean isOpen;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
    private Orientation orientation;

    public Door(){
        vDoor = new Animation("sprites/vdoor.png",16,32,0.1f, Animation.PlayMode.ONCE);
        setAnimation(vDoor);
        vDoor.stop();
        isOpen = false;
    }

    public Door(String name, Orientation orientation){
        super(name);

        vDoor = new Animation("sprites/vdoor.png",16,32,0.1f, Animation.PlayMode.ONCE);
        Animation hDoor = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);

        if(orientation == Orientation.VERTICAL){
            setAnimation(vDoor);
            vDoor.stop();
        }
        else {
            setAnimation(hDoor);
            hDoor.stop();
        }
        this.orientation = orientation;

        isOpen = false;
    }

    @Override
    public void useWith(Actor actor) {
        if(isOpen){
            close();
        }
        else{
            open();
        }
    }

    @Override
    public void open() {
        if(getScene() != null){

            if(getScene() != null && orientation == Orientation.VERTICAL){
                getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.CLEAR);
                getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16+1).setType(MapTile.Type.CLEAR);
            }

            if(getScene() != null && orientation == Orientation.HORIZONTAL){
                getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.CLEAR);
                getScene().getMap().getTile(this.getPosX()/16+1,this.getPosY()/16).setType(MapTile.Type.CLEAR);
            }


            getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            getAnimation().play();
            getAnimation().stop();
            isOpen =true;
            getScene().getMessageBus().publish(DOOR_OPENED,this);
        }
    }

    @Override
    public void close() {
        if(getScene() != null){
            if(getScene() != null && orientation == Orientation.VERTICAL){
                getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.WALL);
                getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16+1).setType(MapTile.Type.WALL);
            }

            if(getScene() != null && orientation == Orientation.HORIZONTAL){
                getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.WALL);
                getScene().getMap().getTile(this.getPosX()/16+1,this.getPosY()/16).setType(MapTile.Type.WALL);
            }


            getAnimation().setPlayMode(Animation.PlayMode.ONCE);
            getAnimation().play();
            getAnimation().stop();
            isOpen = false;
            getScene().getMessageBus().publish(DOOR_CLOSED,this);
        }
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    /*private void wallMaker(){
        if(Objects.requireNonNull(getScene()).getMap().getTile(6,4).isWall()){
            getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.CLEAR);
            getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.CLEAR);
        }
        else{
            getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.WALL);
        }
    }*/

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(getScene() != null && orientation == Orientation.VERTICAL){
            getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16+1).setType(MapTile.Type.WALL);
        }

        if(getScene() != null && orientation == Orientation.HORIZONTAL){
            getScene().getMap().getTile(this.getPosX()/16,this.getPosY()/16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX()/16+1,this.getPosY()/16).setType(MapTile.Type.WALL);
        }
    }


    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
