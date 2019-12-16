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
    boolean frustum = true;
    int windowWidth = 400;
    int windowHeight = 225;
    int reflections = 3;
    int x,y;
    int button = 0;
    Color[] reflectionColors = new Color[12];
    int cores = Runtime.getRuntime().availableProcessors();
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
                    frustum = !frustum;
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
    public Color[] getReflectionColors()
    {
        Color[] reflectionColors = new Color[reflections+2];
        reflectionColors[0] = backColor;
        reflectionColors[reflectionColors.length-1] = frontColor; 
        for (int i = 1; i < reflectionColors.length-1; i++) {
            reflectionColors[i] = new Color((int)(frontColor.getRed()-backColor.getRed())/(reflections+2)*(i+1),(int)(frontColor.getGreen()-backColor.getGreen())/(reflections+2)*(i+1),(int)(frontColor.getBlue()-backColor.getBlue())/(reflections+2)*(i+1));
        } 
        return reflectionColors;
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
        reflectionColors = getReflectionColors();

        entities = new ArrayList<Entity>();
        double sq2 = Math.sqrt(2)*5;
        for (int i = 0; i < 3; i++) {
            entities.add(new Sphere(new Vector(10.0,i*6,0.0), new Vector(2), 2.0,true));
            entities.add(new Sphere(new Vector(-10.0,i*6,0.0), new Vector(2), 2.0,true));
            entities.add(new Sphere(new Vector(0.0,i*6,10.0), new Vector(2), 2.0));
            entities.add(new Sphere(new Vector(0.0,i*6,-10.0), new Vector(2), 2.0));
            entities.add(new Sphere(new Vector(sq2,i*6,sq2), new Vector(2), 2.0));
            entities.add(new Sphere(new Vector(-sq2,i*6,sq2), new Vector(2), 2.0));
            entities.add(new Sphere(new Vector(-sq2,i*6,-sq2), new Vector(2), 2.0));
            entities.add(new Sphere(new Vector(sq2,i*6,-sq2), new Vector(2), 2.0));   
        }
        //entities.add(new Sphere(new Vector(0.0,-20,0.0),new Vector(2),10.0,true));
        selectedCamera.setRot(new Vector(Math.PI/2.0,Math.PI/2));
        selectedCamera.setPos(new Vector(0.0,6.0,0.0));

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
        selectedCamera.setRot(selectedCamera.getRot().add(new Vector(Math.PI/180.0,0.0)));
    }
    

    /**
     * This method will draw everything
     */
    void draw()
    {       

        g = getGraphics();

        bbg = backBuffer.getGraphics(); //reset canvas
        windowWidth = simulation.getContentPane().getWidth();
        windowHeight = simulation.getContentPane().getHeight();
        bbg.translate(windowWidth/2,windowHeight/2);

        bbg.setColor(backColor); //fill canvas with backcolor
        bbg.fillRect(-windowWidth/2, -windowHeight/2, windowWidth, windowHeight);

        bbg.setColor(frontColor); //Do the actual raytracing
        rays();

        bbg.setColor(Color.DARK_GRAY); //Draw Crosshair
        bbg.drawLine(windowHeight/100, 0, -windowHeight/100, 0);
        bbg.drawLine(0, windowHeight/100, 0, -windowHeight/100);
        

        g.drawImage(backBuffer, insets.left, insets.top, this);
    }

    public void rays()
    {
        Vector a = selectedCamera.rot.add(new Vector(Math.PI/2.0,Math.PI/2.0));
        ArrayList<Entity> frustumEntities;
        if (frustum)
            frustumEntities = frustumCulling(a,1,0);
        else
            frustumEntities = new ArrayList<Entity>(entities);
        Vector c = a.toCartesian();
        
        Vector b = a.add(new Vector(Math.PI/2.0,0)).toCartesian();
        Vector v = c.cross(b);

        Vector qx = b.prod(selectedCamera.getScreenWidth()/(windowWidth));
        Vector qy = v.prod(selectedCamera.getScreenHeight(windowWidth,windowHeight)/(windowHeight));
        double t,l;
        int closestIndex;
        Ray d = new Ray(selectedCamera.getPos(), new Vector(3));
        for (x = -windowWidth/2; x < windowWidth/2; x++) {
            for (y = -windowHeight/2; y < windowHeight/2; y++) {
                d.setDirection(c.add(qx.prod(x)).add(qy.prod(y)).normalize());
                
                closestIndex = -1;
                t = 0.0;
                l = 0.0;
                for (int i = 0; i < frustumEntities.size(); i++) 
                {
                    t = frustumEntities.get(i).distance(d);
                    if(t<l || (t>0.0 && closestIndex == -1))
                    {
                        l = t;
                        closestIndex = i;
                    }
                }
                if(closestIndex != -1)
                {  
                    if(frustumEntities.get(closestIndex).light)
                        bbg.drawLine(x,y,x,y);
                    else if(reflections(frustumEntities.get(closestIndex),d,l,0)>=0)
                        bbg.drawLine(x,y,x,y);
                }
            }
        }
        bbg.setColor(frontColor);
        bbg.drawString(c.toString(), 10-windowWidth/2, 10-windowHeight/2);   
    }
    void setpixelcolor(int reflects,int x, int y)
    {
        bbg.setColor(reflectionColors[reflects]);
        bbg.drawLine(x, y, x, y);
    }
    public int reflections(Entity e,Ray r,double d, int n)
    {
        if(n>reflections-1) return -1;
        Ray reflected = e.reflect(r, d);
        ArrayList<Entity> others = new ArrayList<Entity>(entities); others.remove(e);
        int closestIndex = -1;
        double l = 0.0,t = 0.0;
        for(int i = 0;i < others.size();i++)
        {
            t = others.get(i).distance(reflected);
                        if (t<l || (t>0.0 && closestIndex == -1))   //if another entity is closer:
                        {
                            l = t;                                  // store distance to closer entity
                            closestIndex = i;                       // store entity in temporary field
                        }
        }
        if(closestIndex != -1)
        {
            if(others.get(closestIndex).light)
            {
                return n;
            }
            return reflections(others.get(closestIndex),reflected,l,n+1);
        }
        return -1;
    }
    
    
    public ArrayList<Entity> frustumCulling(Vector dir, int subdivisions, int subdivision)
    {
        boolean[] frustumCull = new boolean[entities.size()];                                           //if true, entity at index i will be culled
        Vector n;                                                                                                       
        double d;
        Vector fovw = new Vector((1.0-selectedCamera.getFov()/180.0)/subdivisions*Math.PI,0);
        Vector fovh = new Vector(0.0,(1.0-selectedCamera.getFov()/180.0)/2*Math.PI*windowHeight/windowWidth);

        //Top Frustum
        n = dir.sub(fovh).add(new Vector(0.0, Math.PI/2)).toCartesian();                                // Calculate plane normal for top frustum
        d = -selectedCamera.getPos().dotprod(n);                                                        // calculate plane's distance to origin normal to plane
        for (int i = 0; i < frustumCull.length; i++) {                                                  // go through each entity to check if above plane
            if(entities.get(i).getPos().dotprod(n)+d+entities.get(i).getRadius()<0)                     // distance to origin - plane's distance to origin + radius > 0 (is the entity above or intersecting the plane?)
                frustumCull[i] = true;}                                                                 //sets entity at index i to be culled

        //Bottom Frustum
        n = dir.add(fovh).sub(new Vector(0.0,Math.PI/2)).toCartesian();                                 // Calculate plane normal for bottom frustum
        d = -selectedCamera.getPos().dotprod(n);                                                        // calculate plane's distance to origin normal to plane
        for (int i = 0; i < frustumCull.length; i++) {                                                  // go through each entity to check if above plane
            if(entities.get(i).getPos().dotprod(n)+d+entities.get(i).getRadius()<0)                     // distance to origin - plane's distance to origin + radius > 0 (is the entity above or intersecting the plane?)
                frustumCull[i] = true;                                                                  //sets entity at index i to be culled
        }
        
        dir = dir.sub(fovw.prod(subdivisions/2.0));
        
        //Left Frustum
        n = dir.add(fovw.prod(subdivision)).add(new Vector(Math.PI/2,0.0)).toCartesian();               // Calculate plane normal for left frustum
        d = -selectedCamera.getPos().dotprod(n);                                                        // calculate plane's distance to origin normal to plane
        for (int i = 0; i < frustumCull.length; i++) {                                                  // go through each entity to check if above plane
            if(entities.get(i).getPos().dotprod(n)+d+entities.get(i).getRadius()<0)                     // distance to origin - plane's distance to origin + radius > 0 (is the entity above or intersecting the plane?)
                frustumCull[i] = true;}                                                                 //sets entity at index i to be culled

        //Right Frustum
        n = dir.add(fovw.prod(subdivisions-subdivision)).sub(new Vector(Math.PI/2,0.0)).toCartesian();  // Calculate plane normal for left frustum
        d = -selectedCamera.getPos().dotprod(n);                                                        // calculate plane's distance to origin normal to plane
        for (int i = 0; i < frustumCull.length; i++) {                                                  // go through each entity to check if above plane
            if(entities.get(i).getPos().dotprod(n)+d+entities.get(i).getRadius()<0)                     // distance to origin - plane's distance to origin + radius > 0 (is the entity above or intersecting the plane?)
                frustumCull[i] = true;}                                                                 //sets entity at index i to be culled
        
        /* */
        ArrayList<Entity> frustumEntities = new ArrayList<Entity>();                                    //Create new empty entity frustum list
        for (int i = 0; i < frustumCull.length; i++) {                                                  //loop through entities
            if(!frustumCull[i])
                frustumEntities.add(entities.get(i));                                                   //add only non-culled entities to list
        }       
        return frustumEntities;                                                                         //return frustum list
    }
}