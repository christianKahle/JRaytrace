import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Main class
 */
public class Window extends JFrame
{
        boolean isRunning = true;
        ArrayList<Entity> entities;
        Color backColor = Color.WHITE;
        Color frontColor = Color.BLACK;
        Camera selectedCamera = new Camera("jim", 90);
        int fps = 30;
        int windowWidth = 500;
        int windowHeight = 500;
        int x,y;
        static Window simulation;

        BufferedImage backBuffer;
        Insets insets;
       
        public Window()
        {
                super();
                this.addMouseListener(new MouseAdapter() {
                        /*
                        public void mousePressed(MouseEvent event)
                        {
                                if(button == 0){
                                        button = event.getButton();
                                        switch (button){
                                                case (1):
                                                        break;
                                                case (3):
                                                        break;
                                                default:
                                                        break;
                                        }
                                }
                        }
                        public void mouseReleased(MouseEvent event)
                        {
                        }
                        */
                });   
        }
        public static void main(String[] args)
        {
                simulation = new Window();
                simulation.run();
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
        public void run()
        {
                initialize();
                int frames = 0;
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
                setTitle("Run");
                setSize(windowWidth, windowHeight);
                setResizable(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setVisible(true);
                entities = new ArrayList<Entity>();
                double[] p = {100.0,0.1,0.0}, v = {-1.0,0.0,0.0}, r = {0.0,0.0}, p2 = {100.0,20.0,0.0};
                entities.add(new Sphere(p2,v,r,r,5.0));

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

                Graphics g = getGraphics();

                Graphics bbg = backBuffer.getGraphics();

                bbg.setColor(backColor);
                bbg.fillRect(0, 0, simulation.getWidth(), simulation.getHeight());
                bbg.setColor(frontColor);
                for (x = 0; x < simulation.getWidth(); x++) {
                        for (y = 0; y < simulation.getHeight(); y++) {
                                if(ray(x,simulation.getWidth(),y,simulation.getHeight()))
                                    {
                                        bbg.drawLine(x, y, x, y);
                                    }
                        }
                }

                g.drawImage(backBuffer, insets.left, insets.top, this);
        }

        public boolean ray(int x, int w, int y, int h)
        {
            double tzx = (double)selectedCamera.getFov()/w*(x-w/2)*Math.PI/180;
            double tyx = ((double)selectedCamera.getFov()*((double)h/w)*(y-w/2))/h*Math.PI/180;
            for (Entity en : entities)
            {
                if (en.rayhit(selectedCamera.getPos(),tyx,tzx))
                    return true;   
            }
            return false;
        }
}