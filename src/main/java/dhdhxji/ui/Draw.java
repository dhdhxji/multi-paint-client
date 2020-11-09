package dhdhxji.ui;

import java.awt.*;
import java.util.Vector;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import dhdhxji.ui.canvas.Canvas;
import dhdhxji.ui.canvas.CanvasClickInterface;
import dhdhxji.ui.common.PixelChangedInterface;

public class Draw extends JFrame implements CanvasClickInterface{
    public Draw(int w, int h) {
        super("Drawing");

        _canvas = new Canvas(w, h);
        _canvas.registerClickListener(this);
        _canvas.setSize(new Dimension(w, h));
        
        _colorChooser.setPreviewPanel(new JPanel());
        _colorChooser.setColor(0, 0, 0);
        
        //main window setup
        JScrollPane canvasPane = new JScrollPane(_canvas);
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); 

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.95;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        add(canvasPane, c);

        //controls
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.05;
        c.weighty = 0;
        c.gridwidth = 1;
        add(new JLabel("Brush size:"), c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.95;
        c.weighty = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(_brushSize, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(_colorChooser, c);

        setSize(w, h);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        pack();
    }

    public void click(int x, int y) {
        final int radius = _brushSize.getValue()/2;
        final int sqPointRadius = radius*radius;
        final int color = _colorChooser.getColor().getRGB() & 0xffffff;
        
        for(int yd = 0; yd < radius; ++yd) {
            final int chordArm = (int)Math.sqrt(sqPointRadius - yd*yd);
            for(int xd = 0; xd < chordArm; ++xd) {
                setPixWithNotify(x + xd, y + yd, color);
                setPixWithNotify(x - xd, y + yd, color);
                setPixWithNotify(x + xd, y - yd, color);
                setPixWithNotify(x - xd, y - yd, color);
            }
        }
    }

    public void registerPixUpdate(PixelChangedInterface cb) {
        _pixChangedListeners.add(cb);
    }

    public void setPixWithoutNotify(int x, int y, int color) {
        try {
            _canvas.setPixel(x, y, color);
        } catch(IndexOutOfBoundsException e) {}
    }

    public void setPixWithNotify(int x, int y, int color) {
        try{
            if((_canvas.getPixel(x, y) & 0xffffff) != color) {
                //color update
                for (PixelChangedInterface listener : _pixChangedListeners) {
                    listener.pixUpdated(x, y, color);
                }
            }

            _canvas.setPixel(x, y, color);
        } catch(IndexOutOfBoundsException e) {}
    }


    public static final long serialVersionUID = 43L;

    private Vector<PixelChangedInterface> _pixChangedListeners = new Vector<PixelChangedInterface>();
    private Canvas _canvas = null;
    private JSlider _brushSize = new JSlider(10, 100);
    JColorChooser _colorChooser = new JColorChooser();
}
