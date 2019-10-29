import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/**
 * Main class
 */
public class Window extends JFrame
{
    boolean isRunning = true;
    ArrayList<Entity> entities;
    Color backColor = Color.BLACK;
    Color frontColor = Color.WHITE;
    Camera selectedCamera = new Camera("NONE", 120);
    int fps = 20;
    int windowWidth = 500;
    int windowHeight = 500;
    int x,y;
    int button = 0;
    static Window simulation;
    Graphics g, bbg;

    BufferedImage backBuffer;
    Insets insets;
    
    public Window()
    {
        super();
        MouseAdapter mouse = new MouseAdapter() {
            public void mousePressed(MouseEvent event)
            {
                if(event.getButton() == MouseEvent.BUTTON1)    
                    update();
                if(event.getButton() == MouseEvent.BUTTON3)
                    {
                    System.out.println("["+windowWidth+","+windowHeight+"]");
                    }
            }
            public void mouseReleased(MouseEvent event)
            {
                button = 0;
            }
            public void mouseWheelMoved(MouseWheelEvent event)
            {
                selectedCamera.setFov(selectedCamera.getFov()+event.getWheelRotation()*2);
                System.out.println(selectedCamera.getFov());
            }
        };
        this.addMouseListener(mouse);   
        this.addMouseWheelListener(mouse);
    }
    public static void main(String[] args)
    {
        simulation = new Window();
        simulation.simulate();
        System.exit(0);
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
    /**
     * This method starts the simulation and runs it in a loop
     */
    public void simulate()
    {
        initialize();
        int frames = 0;
        while(isRunning)
        {
            long time = System.currentTimeMillis();
            
            //update();
            draw();
            
            //  delay for each frame  -   time it took for one frame
            time = (1000 / fps) - (System.currentTimeMillis() - time);
            
            if (time > 0)
            {
                try
                {
                        Thread.sleep(time);
                }
                catch(Exception e){}
            } 
            frames = (frames+1)%fps;
            if(frames%(fps/10+1)==0)
                backBuffer = new BufferedImage(simulation.getWidth(), simulation.getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        
        setVisible(false);
    }
    
    /**
     * This method will set up everything need for the simulation to run
     */
    void initialize()
    {
        setTitle("Simulation");
        setSize(windowWidth, windowHeight);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        entities = new ArrayList<Entity>();
        //for (int i = 0; i < 2; i++) {entities.add(new Sphere(50.0,5.0,i*75.0, 0.0,0.25,-1.0, 0.0,0.0, 0.0,0.0, 10.0));entities.add(new Sphere(100.0,5.0,i*75.0, 0.0,-0.25,-1.0, 0.0,0.0, 0.0,0.0, 10.0));}
        
        entities.add(new Sphere(0.0,0.0,10.0, 0.0,0.0,1.0, 0.0,0.0, 0.0,0.0, 5.0));
        //entities.add(new Sphere(0.0,10.0,0.0, 0.0,1.0,0.0, 0.0,0.0, 0.0,0.0, 5.0));

        double[] rot = {0,0};
        selectedCamera.setRot(rot);
        insets = getInsets();
        setSize(insets.left + windowWidth + insets.right,
                insets.top + windowHeight + insets.bottom);
        
        backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
    }
    
    /**
     * This method will check for input, move things
     * around and check for win conditions, etc
     */
    void update()
    {       
        for (Entity e : entities) {
                    
            e.move();
                    
        }
    }
    

    /**
     * This method will draw everything
     */
    void draw()
    {       

        g = getGraphics();

        bbg = backBuffer.getGraphics();
        windowWidth = simulation.getContentPane().getWidth();
        windowHeight = simulation.getContentPane().getHeight();
        bbg.translate(windowWidth/2,windowHeight/2);

        bbg.setColor(backColor);
        bbg.fillRect(0, 0, windowWidth, windowHeight);
        bbg.setColor(frontColor);
        rays();
        bbg.setColor(Color.DARK_GRAY);
        bbg.drawLine(windowHeight/100, 0, -windowHeight/100, 0);
        bbg.drawLine(0, windowHeight/100, 0, -windowHeight/100);

        g.drawImage(backBuffer, insets.left, insets.top, this);
    }

    public void rays()
    {
        double[] p = selectedCamera.getPos();
        double[] d = new double[3];

        for (x = -windowWidth/2; x < windowWidth; x++) {
            for (y = -windowHeight/2; y < windowHeight; y++) {
                d = normalize();
                for (Entity en : entities) {if (en.rayhit(p, d, 0)) bbg.drawLine(x, y, x, y);} 
            }
        }   
    }   

    public double[] crossproduct(double[] a, double[] b)
    {
        if(!(a.length == b.length && a.length == 3))
            throw new IndexOutOfBoundsException();
        double[] s = {a[1]*b[2]-a[2]*b[1],a[2]*b[0]-a[0]*b[2],a[0]*b[1]-a[1]*b[0]};
        return s;
    }
    public double magnitude(double[] v)
    {
        double sum = 0.0;
        for (double d : v) {
            sum += d*d;
        }
        return Math.sqrt(sum);
    }
    public double[] normalize(double[] v)
    {
        double m = magnitude(v);
        if(m == 1)
            return v;
        for (int i = 0; i < v.length; i++) {
            v[i] = v[i]/m;
        }
        return v;
    }
}