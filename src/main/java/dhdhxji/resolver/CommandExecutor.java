package dhdhxji.resolver;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import dhdhxji.resolver.marshaller.Marshaller;
import dhdhxji.resolver.marshaller.commandDataImpl.CircleCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.LoginCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.SetCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.SizeCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.StripCmd;
import dhdhxji.ui.common.LoginInterface;


public class CommandExecutor implements LoginInterface{
    @Override
    public void login(String name, String address) {
        try {
            Socket clientSock = new Socket(address, 3113);
            new ClientHandler(clientSock, name, _defaultMarshaller).start();

        } catch(UnknownHostException e) {
            System.err.println("Can`t connect to the host");
        } catch(IOException e) {
            System.err.println("Unknown io error while connect");
        }
        
    }
    
    private Marshaller _defaultMarshaller = new Marshaller()
            .registerCommand("login", LoginCmd.class)
            .registerCommand("set", SetCmd.class)
            .registerCommand("size", SizeCmd.class)
            .registerCommand("strip", StripCmd.class)
            .registerCommand("circle", CircleCmd.class);
}
