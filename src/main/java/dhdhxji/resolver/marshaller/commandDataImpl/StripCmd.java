package dhdhxji.resolver.marshaller.commandDataImpl;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

import dhdhxji.resolver.marshaller.CommandData;

public class StripCmd extends CommandData {

    @JsonProperty("start-x")
    public int startX;

    @JsonProperty("start-y")
    public int startY;

    @JsonProperty("pixels")
    public int[] pixels;

    public StripCmd(int xVal, int yVal, int[] pixelsVal) {
        startX = xVal;
        startY = yVal;

        pixels = pixelsVal;
    }

    public StripCmd() {}

    @Override
    public String toString() {
        return "start-x: " + startX + " start-y: " + startY + " pixels: " + pixels.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;


        StripCmd command = (StripCmd) o;
        // field comparison
        return startX == command.startX && 
               startY == command.startY && 
               Arrays.equals(pixels, command.pixels);
    }
}
