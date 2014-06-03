package Som;

public class Sounds {

    private static AudioPlayer prebackground = null;
    private static AudioPlayer background = null;
    private static AudioPlayer engine = null;
    private static AudioPlayer acceleration = null;
    private static AudioPlayer rear = null;
    private static AudioPlayer acceleration2 = null;
    
    
    public static void playIntro() {
            
            if (background == null){
            background = new AudioPlayer("./data/fefo/music/iron.mp3", true);
            background.start();
        
            }
    }
    public static void playRear() {
            
        if (rear == null){
        rear = new AudioPlayer("./data/fefo/music/rear.mp3", true);
        rear.start();

        }
    }
    public static void playPreIntro() {
            
        if (prebackground == null){
        prebackground = new AudioPlayer("./data/fefo/music/batidas.mp3", true);
        prebackground.start();

        }
    }
    
    public static void startEngine() {

        if (engine == null){
        engine = new AudioPlayer("./data/fefo/music/car_start.mp3", false);
        engine.start();

        }
    }

    public static void acceleration() {

        if (acceleration == null){
        acceleration = new AudioPlayer("./data/fefo/music/acc.mp3", false);
        acceleration.start();
        stopEngine();
        }
    }

    public static void acceleration2() {

        if (acceleration2 == null){
        acceleration2 = new AudioPlayer("./data/fefo/music/engineon.mp3", true);
        acceleration2.start();
        stopEngine();
        }
    }

    public static void stopEngine() {
        if (engine != null) {
            engine.Finish(false);
            engine = null;
        }
    }
    public static void stopPreIntro() {
        if (prebackground != null) {
            prebackground.Finish(false);
            prebackground = null;
        }
    }

        public static void stopEffects() {
        if (engine != null) {
            engine.Finish(false);
            engine = null;
        }

        if (acceleration != null) {
            acceleration.Finish(false);
            acceleration = null;
        }

        if (acceleration2 != null) {
            acceleration2.Finish(false);
            acceleration2 = null;
        }
    }

}
