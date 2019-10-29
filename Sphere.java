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
                    double zenithrotation, double azimuthrotation,
                    double zenithvelocity, double azimuthvelocity,
                    double radius)
    {
        super(xposition,yposition,zposition,xvelocity,yvelocity,zvelocity,zenithrotation,azimuthrotation,zenithvelocity,zenithvelocity);
        this.radius = radius;
    }

    @Override
    public boolean rayhit(double[] rayorigin, double d[], int n) {
        double v[] = {rayorigin[0]-pos[0],rayorigin[1]-pos[1],rayorigin[2]-pos[2]};
        double vd  = dotproduct(v, d);
        return -vd - Math.sqrt(vd*vd-(dotproduct(v, v)-radius*radius)) > 0 || -vd + Math.sqrt(vd*vd-(dotproduct(v, v)-radius*radius)) > 0;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}