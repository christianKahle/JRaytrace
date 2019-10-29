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
    public Camera(  double xposition, double yposition, double zposition,
                    double xvelocity, double yvelocity, double zvelocity,
                    double yxrotation, double zxrotation,
                    double yxrotavelo, double zxrotavelo,
                    String name)
    {
        super(xposition,yposition,zposition,xvelocity,yvelocity,zvelocity,yxrotation,zxrotation,yxrotavelo,zxrotavelo);
        this.name = name;
    }
    public Camera(  double xposition, double yposition, double zposition,
                    double xvelocity, double yvelocity, double zvelocity,
                    double yxrotation, double zxrotation,
                    double yxrotavelo, double zxrotavelo,
                    String name, int fieldofview)
    {
        super(xposition,yposition,zposition,xvelocity,yvelocity,zvelocity,yxrotation,zxrotation,yxrotavelo,zxrotavelo);
        this.name = name;
        fov = fieldofview;
    }
    
   @Override
   public boolean rayhit(double[] rayorigin, double[] d, int n) {
       return false;
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
    
    public double getScreenWidth()
    {
        return 2.0*Math.tan(fov*Math.PI/360.0);
    }

    public double getScreenHeight(int pixelWidth, int pixelHeight)
    {
        return getScreenWidth()/2.0*pixelHeight/pixelWidth;
    }


}