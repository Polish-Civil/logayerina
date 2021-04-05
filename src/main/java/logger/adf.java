package logger;

public class adf {
    public static void main(String[] args) {
        Log.Config config = new Log.Config(Log.Level.DEBUG);

        config.set();

        Log.setConfig(config);
    }
}
