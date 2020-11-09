package dhdhxji;

import dhdhxji.resolver.CommandExecutor;
import dhdhxji.ui.Login;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CommandExecutor executor = new CommandExecutor();
        Login loginScreen = new Login();
        loginScreen.registerLoginCallback(executor); 
    }
}
