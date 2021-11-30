package dhdhxji.ui.canvas;

import javax.swing.JComponent;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.awt.*;


public class Canvas extends JComponent implements CanvasInterface {
    public Canvas(int width, int height) {
        _img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                _img.setRGB(x, y, 0xffffff);
            }
        }

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    @Override
    public void registerClickListener(CanvasClickInterface listener) {
        _listeners.add(listener);
    }
    
    @Override
    public void setPixel(int x, int y, int color) throws IndexOutOfBoundsException{
        if(x < 0 || x >= _img.getWidth() ||
           y < 0 || y >= _img.getHeight()) {
            throw new IndexOutOfBoundsException();   
        }
        _img.setRGB(x, y, color);

        repaint(x, y, 1, 1);
    }

    @Override
    public int getPixel(int x, int y) throws IndexOutOfBoundsException {
        if(x < 0 || x >= _img.getWidth() ||
           y < 0 || y >= _img.getHeight()) {
            throw new IndexOutOfBoundsException();   
        }
        
        return _img.getRGB(x, y) & 0xffffff;
    }

    public int width() {
        return _img.getWidth();
    }

    public int height() {
        return _img.getHeight();
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(_img.getWidth(), _img.getHeight());
    }
    
    @Override
    public void paint(Graphics p) {
        p.drawImage(_img, 0, 0, null);
    }

    @Override
    protected void processMouseEvent(MouseEvent ev) {
        if((ev.getButton() & MouseEvent.BUTTON1) > 0 && 
           (ev.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) > 0) {
            processClicked(ev.getX(), ev.getY());
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent ev) {
        if((ev.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) > 0) {
            processClicked(ev.getX(), ev.getY());
        }
    }



    private void processClicked(int x, int y) {
        for (CanvasClickInterface listener : _listeners) {
            listener.click(x, y);
        }
    }



    public static final long serialVersionUID = 42L;


    private Vector<CanvasClickInterface> _listeners = new Vector<CanvasClickInterface>();
    private BufferedImage _img = null;
}
