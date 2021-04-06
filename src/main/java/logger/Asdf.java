package logger;

import org.slf4j.event.Level;

public class Asdf {
    public static void main(String[] args) {
        System.out.println("wefwef");
        Log.Config config = new Log.Config(Level.ERROR);
        config.addPolicy("logger", Level.INFO);
        //config.addPolicy(Asdf.class, Level.DEBUG);
        Log.setConfig(config);

        Log.debug("debug");
        Log.info("info");
        Log.error("error", new Exception("asdf"));
    }
}
