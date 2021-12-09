package dhdhxji.ui.canvas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CanvasTest {
    @Test
    public void testSetPix() {
        Canvas c = new Canvas(200, 200, 1);
        c.setPixel(0, 0, 100);

        assertEquals(100, c.getPixel(0, 0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfIndex() throws IndexOutOfBoundsException {
        Canvas c = new Canvas(200, 200, 1);

        c.getPixel(500, 500);
    }
}
