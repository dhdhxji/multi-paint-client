package dhdhxji.resolver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import dhdhxji.resolver.marshaller.Command;
import dhdhxji.resolver.marshaller.Marshaller;
import dhdhxji.resolver.marshaller.commandDataImpl.LoginCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.SetCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.SizeCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.StripCmd;
import dhdhxji.ui.Draw;
import dhdhxji.ui.common.PixelChangedInterface;

public class ClientHandler extends Thread implements PixelChangedInterface {
    public ClientHandler(Socket clientSock, String name, Marshaller m) throws IOException, InvalidObjectException {
        _marshaller = m;

        _read = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
        _write = new BufferedWriter(new OutputStreamWriter(clientSock.getOutputStream()));

        sendCommand(_marshaller.serialize(new Command(
            "login",
            new LoginCmd(name)
        )));

        
        String response = _read.readLine();

        Command size = _marshaller.deserialize(response);
        SizeCmd data = (SizeCmd)size.commandData;

        _draw = new Draw(data.w, data.h);
        _draw.registerPixUpdate(this);
    } 

    class Uploader extends Thread {
        public void run() {
            while(true) {
                try {
                    String data = _sendQueue.take();
                    sendCommand(data);

                    if(isInterrupted()) {
                        throw new InterruptedException();
                    }
                } catch(InterruptedException e) {
                    //thread was interrupted from outside, leave it
                    break;
                } catch (IOException e) {
                    //connection lost
                    break;
                }
            }
        }
    }
    
    @Override
    public void run() {
        Uploader mainUploader = new Uploader();
        mainUploader.start();


        while(true) {
            String req = null;
            try {
                req = _read.readLine();
            } catch(IOException e) {
                //connection closed
                break;
            }

            try {
                processCommand(req);
            } catch(InvalidObjectException e) {
                System.err.println("Cannot parse command: " + req);
            }

            if(isInterrupted()) {
                break;
            }
        }

        mainUploader.interrupt();

        try {
            mainUploader.join();
        } catch (InterruptedException e) {}
    }

    public void processCommand(String req) throws InvalidObjectException {
        Command reqCommand = _marshaller.deserialize(req);
        if(reqCommand.commandName.equals("set")) {
            SetCmd data = (SetCmd)reqCommand.commandData;

            _draw.setPixWithoutNotify(data.x, data.y, data.color);
        } else if(reqCommand.commandName.equals("strip")) {
            StripCmd data = (StripCmd)reqCommand.commandData;

            final int width = _draw.width();
            
            for(int i = 0; i < data.pixels.length; ++i) {
                int x = (data.startX + i)%width;
                int y = data.startY + (data.startX + i)/width;

                _draw.setPixWithoutNotify(x, y, data.pixels[i]);
            }
        }
    }

    @Override
    public void pixUpdated(int x, int y, int color) {
        Command setPix = new Command(
            "set",
            new SetCmd(x, y, color)
        );

        try {
            _sendQueue.add(_marshaller.serialize(setPix));
        } catch(InvalidObjectException e) {
            System.err.println("Can not serialize set command");
        }
    }

    private void sendCommand(String cmd) throws IOException {
        _write.write(cmd + "\n");
        _write.flush();
    }

    
    private BlockingQueue<String> _sendQueue = new LinkedBlockingQueue<String>();

    private Draw _draw = null;
    private Marshaller _marshaller = null;

    private BufferedReader _read = null;
    private BufferedWriter _write = null;
}