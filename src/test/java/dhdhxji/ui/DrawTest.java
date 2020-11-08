package dhdhxji.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dhdhxji.ui.common.PixelChangedInterface;

public class DrawTest {
    public int tx = -1;
    public int ty = -1;
    public int tcolor = -1;
    
    class PixelChangedWrapper implements PixelChangedInterface {
        @Override
        public void pixUpdated(int x, int y, int color) {
            tx = x;
            ty = y;
            tcolor = color;
        }
    }

    @Before
    public void resetState() {
        tx = -1;
        ty = -1;
        tcolor = -1;
    }

    @Test
    public void testClick() {
        Draw d = new Draw(200, 200);
        d.registerPixUpdate(new PixelChangedWrapper());
        d.click(10, 10);

        assertTrue(tx != -1);
        assertTrue(ty != -1);
        assertEquals(0, tcolor);
    }

    @Test
    public void testClickSequence() {
        Draw d = new Draw(200, 200);
        d.registerPixUpdate(new PixelChangedWrapper());
        System.out.print("1\n");
        d.click(10, 10);

        assertTrue(tx != -1);
        assertTrue(ty != -1);
        assertEquals(0, tcolor);

        resetState();
        System.out.print("2\n");
        d.click(10, 10);

        assertEquals(-1, tx);
        assertEquals(-1, ty);
        assertEquals(-1, tcolor);
    }
}
