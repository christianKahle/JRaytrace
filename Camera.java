public class Camera extends Entity
{
    private String name;
    private double fov = 90;

    public Camera(String name)
    {
        super();
        this.name = name;
    }
    public Camera(String name, double fieldofview)
    {
        super();
        this.name = name;
        fov = fieldofview;
    }
    public Camera(Vector pos, Vector rot, String name)
    {
        super(pos,rot);
        this.name = name;
    }
    public Camera(Vector pos, Vector rot, String name, double fieldofview)
    {
        super(pos,rot);
        this.name = name;
        fov = fieldofview;
    }
    
   @Override
   public boolean rayhit(Vector rayorigin, Vector direction, int n) {
       return false;
   }

    /**
     * @param fov the fov to set
     */
    public void setFov(double fov) {
        this.fov = fov;
    }

    /**
     * @return the name of the Camera
     */
    public String getName() {
        return name;
    }

    /**
     * @return the fov
     */
    public double getFov() {
        return fov;
    }
    
    public double getScreenWidth()
    {
        return 2.0*Math.tan(fov*Math.PI/360.0);
    }

    public double getScreenHeight(int pixelWidth, int pixelHeight)
    {
        return getScreenWidth()/2.0*pixelHeight/pixelWidth;
    }


}