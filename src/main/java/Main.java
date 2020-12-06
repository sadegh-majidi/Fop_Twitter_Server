import server.Server;
import utils.LogLevel;
import utils.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger(LogLevel.Info);
        Server server = new Server(logger);
        try {
            server.init();
            server.Run();
        } catch (Exception ignored) {
        }
    }
}
