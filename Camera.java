public class Camera extends BaseEntity
{
    private String name;
    private double fov = 90;

    public Camera(String name)
    {
        this(name,90.0);
    }
    public Camera(String name, double fieldofview)
    {
        super();
        this.name = name;
        fov = Math.min(fieldofview,150);
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
        return getScreenWidth()*pixelHeight/pixelWidth;
    }
    public double getRadius()
    {
        return 0;
    }


}