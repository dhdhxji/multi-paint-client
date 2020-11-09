package dhdhxji.resolver.marshaller.commandDataImpl;

import com.fasterxml.jackson.annotation.JsonProperty;

import dhdhxji.resolver.marshaller.CommandData;

public class SizeCmd extends CommandData{

    @JsonProperty("w")
    public int w;

    @JsonProperty("h")
    public int h;

    public SizeCmd(int wVal, int hVal) {
        w = wVal;
        h = hVal;
    }

    public SizeCmd() {}

    @Override
    public String toString() {
        return "w: " + w + " h: " + h;
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


        SizeCmd sizeCmd = (SizeCmd) o;
        // field comparison
        return w == sizeCmd.w && 
               h == sizeCmd.h;
    }
}
