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
                    double zenithrotation, double azumithrotation,
                    double zenithvelocity, double azumithvelocity,
                    double radius)
    {
        super(xposition,yposition,zposition,xvelocity,yvelocity,zvelocity,zenithrotation,azumithrotation,zenithvelocity,zenithvelocity);
        this.radius = radius;
    }

    @Override
    public boolean rayhit(double[] rayorigin, double zenith, double azumith, int n) {
        double v[] = {pos[0]-rayorigin[0],pos[1]-rayorigin[1],pos[2]-rayorigin[2]};
        double d[] = {Math.sin(zenith)*Math.cos(azumith),Math.sin(zenith)*Math.sin(azumith),Math.cos(zenith)};
        double vd  = dotproduct(v, d);
        return -vd - Math.sqrt(vd*vd-(dotproduct(v, v)-radius*radius)) > 0;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}