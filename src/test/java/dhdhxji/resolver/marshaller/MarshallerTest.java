package dhdhxji.resolver.marshaller;

import static org.junit.Assert.assertEquals;

import java.io.InvalidObjectException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import dhdhxji.resolver.marshaller.commandDataImpl.LoginCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.SetCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.SizeCmd;
import dhdhxji.resolver.marshaller.commandDataImpl.StripCmd;

public class MarshallerTest {
    private static Marshaller testMarshal = new Marshaller();

    @BeforeClass
    public static void initMarshaller() {
        testMarshal.registerCommand("set", SetCmd.class)
                   .registerCommand("login", LoginCmd.class)
                   .registerCommand("size", SizeCmd.class)
                   .registerCommand("strip", StripCmd.class);
    }

    @Test
    public void testSetMarshall() throws InvalidObjectException {
        Command cmd = new Command(
            "set",
            new SetCmd(0, 0, 256)
        );
        String serialized = testMarshal.serialize(cmd);
        Command deserialized = testMarshal.deserialize(serialized);

        assertEquals(cmd, deserialized);
    }

    @Test
    public void testLoginMarshall() throws InvalidObjectException {
        Command cmd = new Command(
            "login",
            new LoginCmd("testuser")
        );
        
        String serialized = testMarshal.serialize(cmd);
        Command deserialized = testMarshal.deserialize(serialized);

        assertEquals(cmd, deserialized);
    }

    @Test
    public void testSizeMarshall() throws InvalidObjectException {
        Command cmd = new Command(
            "size",
            new SizeCmd(200, 200)
        );
        
        String serialized = testMarshal.serialize(cmd); 
        Command deserialized = testMarshal.deserialize(serialized);

        assertEquals(cmd, deserialized);
    }

    @Test
    public void testStripMarshall() throws InvalidObjectException {
        int[] pixels = {1, 2, 3, 4, 5};
        Command cmd = new Command(
            "strip",
            new StripCmd(200, 200, pixels)
        );
        
        String serialized = testMarshal.serialize(cmd);
        Command deserialized = testMarshal.deserialize(serialized);

        assertEquals(cmd, deserialized);
    }

    @Test(expected = InvalidObjectException.class)
    public void testInvalidCommandSerialize() throws InvalidObjectException {
        int[] pixels = {1, 2, 3, 4, 5};
        Command cmd = new Command(
            "set",
            new StripCmd(200, 200, pixels)
        );

        testMarshal.serialize(cmd);
    }

    @Rule 
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testInvalidCommandDeserialize() throws InvalidObjectException {
        int[] pixels = {1, 2, 3, 4, 5};
        Command cmd = new Command(
            "strip",
            new StripCmd(200, 200, pixels)
        );

        String serialized = testMarshal.serialize(cmd);
        serialized = serialized.replace("strip", "set");

        thrown.expect(InvalidObjectException.class);
        testMarshal.deserialize(serialized);
    }
}
