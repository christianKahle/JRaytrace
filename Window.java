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
    Camera selectedCamera = new Camera("NONE", 90);
    int fps = 30;
    int windowWidth = 800;
    int windowHeight = 450;
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
                    //update();
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
                //selectedCamera.setFov(selectedCamera.getFov()+event.getWheelRotation()*2);
                //System.out.println(selectedCamera.getFov());
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
        draw();
        draw();
        while(isRunning)
        {
            long time = System.currentTimeMillis();
            
            update();
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
        
        entities.add(new Sphere(new Vector(10.0,0.0,0.0), new Vector(0.0,0.0), 2.0));
        entities.add(new Sphere(new Vector(-10.0,0.0,0.0), new Vector(0.0,0.0), 2.0));
        entities.add(new Sphere(new Vector(0.0,0.0,10.0), new Vector(0.0,0.0), 2.0));
        entities.add(new Sphere(new Vector(0.0,0.0,-10.0), new Vector(0.0,0.0), 2.0));
        entities.add(new Sphere(new Vector(10.0,0.0,10.0), new Vector(0.0,0.0), 2.0));
        entities.add(new Sphere(new Vector(-10.0,0.0,10.0), new Vector(0.0,0.0), 2.0));
        entities.add(new Sphere(new Vector(-10.0,0.0,-10.0), new Vector(0.0,0.0), 2.0));
        entities.add(new Sphere(new Vector(10.0,0.0,-10.0), new Vector(0.0,0.0), 2.0));

        selectedCamera.setRot(new Vector(0,-Math.PI/2));

        insets = getInsets();
        setSize(insets.left + windowWidth + insets.right,
                insets.top + windowHeight + insets.bottom);
        
        backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
    }
    
    /**
     * This method will check for input, move things
     * around and check for win conditions, etc
     */
    void update(){            
        //include code to change scenario how you wish
        selectedCamera.setRot(selectedCamera.getRot().add(new Vector(Math.PI/100.0,0.0)));
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
        bbg.fillRect(-windowWidth/2, -windowHeight/2, windowWidth, windowHeight);
        bbg.setColor(frontColor);
        rays();
        bbg.setColor(Color.DARK_GRAY);
        bbg.drawLine(windowHeight/100, 0, -windowHeight/100, 0);
        bbg.drawLine(0, windowHeight/100, 0, -windowHeight/100);

        g.drawImage(backBuffer, insets.left, insets.top, this);
    }

    public void rays()
    {
        Vector a = new Vector(selectedCamera.getRot().get(0)+Math.PI/2,selectedCamera.getRot().get(1)+Math.PI/2);
        ArrayList<Entity> frustumEntities = frustumCulling(a);
        //ArrayList<Entity> frustumEntities = entities;
        Vector p = selectedCamera.getPos();
        
        Vector t = (new Vector(Math.sin(a.get(0))*Math.cos(a.get(1)),Math.sin(a.get(0))*Math.sin(a.get(1)),Math.cos(a.get(0)))).normalise();
        Vector b = (new Vector(Math.sin(a.get(0)+Math.PI/2)*Math.cos(a.get(1)),Math.sin(a.get(0)+Math.PI/2)*Math.sin(a.get(1)),Math.cos(a.get(0)+Math.PI/2))).normalise();
        Vector v = t.cross(b).normalise();
        Vector qx = b.prod(selectedCamera.getScreenWidth()/(windowWidth));
        Vector qy = v.prod(selectedCamera.getScreenHeight(windowWidth,windowHeight)/(windowHeight));
        for (x = -windowWidth/2; x < windowWidth/2; x++) {
            for (y = -windowHeight/2; y < windowHeight/2; y++) {
                Vector d = ((t.add(qx.prod(x))).add(qy.prod(y))).normalise();
                //if(d.get(1) > 0) bbg.drawLine(x, y, x, y);
                for (Entity en : frustumEntities) {if (en.rayhit(p, d, 0)) bbg.drawLine(x, y, x, y);} 
            }
        }   
    }
    
    public ArrayList<Entity> frustumCulling(Vector dir)
    {
        boolean[] frustumCull = new boolean[entities.size()]; //if true, entity will be culled
        Vector n1,n2;
        double d;

        //Right Frustum
        n1 = dir.sub(new Vector(selectedCamera.getFov()/180/2*Math.PI,0.0)); // Calculate plane normal
        n2 = new Vector(Math.sin(n1.get(0))*Math.cos(n1.get(1)),Math.sin(n1.get(0))*Math.sin(n1.get(1)),Math.cos(n1.get(0))); // convert angular vector into unit vector
        d = -selectedCamera.getPos().dotprod(n2); // calculate plane's negative distance to origin normal to plane
        for (int i = 0; i < frustumCull.length; i++) { // go through each entity to check if above plane
            if(entities.get(i).getPos().dotprod(n2)+d+entities.get(i).getRadius()<0) // distance to origin - plane's distance to origin + radius > 0 (is the entity above or intersecting the plane?)
                frustumCull[i] = true; //sets entity at index i to be culled
        }

        //Left Frustum
        n1 = dir.add(new Vector(selectedCamera.getFov()/180/2*Math.PI,0.0)); // Calculate plane normal
        n2 = new Vector(Math.sin(n1.get(0))*Math.cos(n1.get(1)),Math.sin(n1.get(0))*Math.sin(n1.get(1)),Math.cos(n1.get(0))); // convert angular vector into unit vector
        d = -selectedCamera.getPos().dotprod(n2); // calculate plane's distance to origin normal to plane
        for (int i = 0; i < frustumCull.length; i++) { // go through each entity to check if above plane
            if(entities.get(i).getPos().dotprod(n2)+d+entities.get(i).getRadius()<0) // distance to origin - plane's distance to origin + radius > 0 (is the entity above or intersecting the plane?)
                frustumCull[i] = true; //sets entity at index i to be culled

        }

        //Top Frustum
        n1 = dir.add(new Vector(0.0,selectedCamera.getFov()/180/2*Math.PI*windowHeight/windowWidth)); // Calculate plane normal
        n2 = new Vector(Math.sin(n1.get(0))*Math.cos(n1.get(1)),Math.sin(n1.get(0))*Math.sin(n1.get(1)),Math.cos(n1.get(0))); // convert angular vector into unit vector
        d = -selectedCamera.getPos().dotprod(n2); // calculate plane's distance to origin normal to plane
        for (int i = 0; i < frustumCull.length; i++) { // go through each entity to check if above plane
            if(entities.get(i).getPos().dotprod(n2)+d+entities.get(i).getRadius()<0) // distance to origin - plane's distance to origin + radius > 0 (is the entity above or intersecting the plane?)
                frustumCull[i] = true; //sets entity at index i to be culled
        }

        //Bottom Frustum
        n1 = dir.sub(new Vector(0.0,selectedCamera.getFov()/180/2*Math.PI*windowHeight/windowWidth)); // Calculate plane normal
        n2 = new Vector(Math.sin(n1.get(0))*Math.cos(n1.get(1)),Math.sin(n1.get(0))*Math.sin(n1.get(1)),Math.cos(n1.get(0))); // convert angular vector into unit vector
        d = -selectedCamera.getPos().dotprod(n2); // calculate plane's distance to origin normal to plane
        for (int i = 0; i < frustumCull.length; i++) { // go through each entity to check if above plane
            if(entities.get(i).getPos().dotprod(n2)+d+entities.get(i).getRadius()<0) // distance to origin - plane's distance to origin + radius > 0 (is the entity above or intersecting the plane?)
                frustumCull[i] = true; //sets entity at index i to be culled
        }

        ArrayList<Entity> frustumEntities = new ArrayList<Entity>(); //Create new empty entity list
        for (int i = 0; i < frustumCull.length; i++) { //loop through entities, culling them using frustumCull boolean array
            if(!frustumCull[i]) frustumEntities.add(entities.get(i));
        }
        return frustumEntities;
    }
}