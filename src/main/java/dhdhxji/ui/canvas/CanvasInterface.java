package dhdhxji.ui.canvas;

public interface CanvasInterface {
    public void registerClickListener(CanvasClickInterface listener);

    public void setPixel(int x, int y, int color);
    public int getPixel(int x, int y) throws IndexOutOfBoundsException;
}
