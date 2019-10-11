public class Camera extends Entity
{
    private String name;
    private int fov = 90;

    public Camera(String name)
    {
        super();
        this.name = name;
    }
    public Camera(String name, int fieldofview)
    {
        super();
        this.name = name;
        fov = fieldofview;
    }
    public Camera(double position[], double velocity[], double rotation[], double rotationalVelocity[], String name)
    {
        super(position,velocity,rotation,rotationalVelocity);
        this.name = name;
    }
    public Camera(double position[], double velocity[], double rotation[], double rotationalVelocity[], String name, int fieldofview)
    {
        super(position,velocity,rotation,rotationalVelocity);
        this.name = name;
        fov = fieldofview;
    }
    

    /**
     * @param fov the fov to set
     */
    public void setFov(int fov) {
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
    public int getFov() {
        return fov;
    }


}