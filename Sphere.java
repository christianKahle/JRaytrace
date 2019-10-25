public class Sphere extends Entity
{
    protected double radius;

    public Sphere(double radius)
    {
        super();
        this.radius = radius;
    }
    public Sphere(  double xposition, double yposition, double zposition,
                    double xvelocity, double yvelocity, double zvelocity,
                    double yxrotation, double zxrotation,
                    double yxrotavelo, double zxrotavelo,
                    double radius)
    {
        super(xposition,yposition,zposition,xvelocity,yvelocity,zvelocity,yxrotation,zxrotation,yxrotavelo,zxrotavelo);
        this.radius = radius;
    }

    @Override
    public boolean rayhit(double[] cameraposition,double[] camerarotation, double fov, int width, int height, int x, int y) {
        double position[] = new double[3];
        for(int i = 0; i < 3; i++)
            position[i] = pos[i] - cameraposition[i];
        double ayx = Math.atan(position[1]/position[0]);
        double azx = Math.atan(position[2]/position[0]);
        if (Math.signum(ayx+camerarotation[0]) == -1.0)// || Math.signum(azx+camera.getRot()[1]) == -1.0)
            return false;

        double tyx = ((double)fov/((double)width/height)*(y-height/2))/height*Math.PI/180+camerarotation[1];
        double tzx = (double)fov/width*(x-width/2)*Math.PI/180+camerarotation[0];
        double rad = Math.sqrt(position[0]*position[0]+position[1]*position[1]+position[2]*position[2]);
        double cyx = Math.sin(ayx+tyx);
        double czx = Math.sin(azx+tzx);    
        double d = Math.sqrt(rad*rad*cyx*cyx+rad*rad*czx*czx);
        return d < radius;
    }
    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}