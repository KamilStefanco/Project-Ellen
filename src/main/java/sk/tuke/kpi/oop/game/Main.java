package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;

public class Main {
    public static void main(String[] args) {
        // nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 1200, 800);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup, new LwjglBackend());  // v pripade Mac OS bude druhy parameter "new Lwjgl2Backend()"

        // vytvorenie sceny pre hru
        // pouzijeme implementaciu rozhrania `Scene` triedou `World`
        //Scene scene = new World("world","maps/mission-impossible.tmx");
        //Scene insp = new InspectableScene(new World("world"), List.of("sk.tuke.kpi"));

        //Scene missionImpossible = new World("mission-impossible", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        Scene escapeRoom = new World("escape-room", "maps/escape-room.tmx", new EscapeRoom.Factory());


        // pridanie sceny do hry
        //game.addScene(scene);
        //game.addScene(insp);

        //game.addScene(missionImpossible);
        game.addScene(escapeRoom);

        //FirstSteps firstSteps = new FirstSteps();
        //scene.addListener(firstSteps);

        //MissionImpossible mission = new MissionImpossible();
        //missionImpossible.addListener(mission);

        EscapeRoom escapeRoom1 = new EscapeRoom();
        escapeRoom.addListener(escapeRoom1);


        // spustenie hry
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
