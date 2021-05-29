package HomeWork2;

/**
 * Сервис авторизации
 */
public interface AuthService {
    /**
     * запустить сервис
     */
    void start();

    /**
     * остановить сервис
     */
    void stop();

    /**
     * получить ник
     */
    String getNickByLoginAndPass(String login, String pass);
}
