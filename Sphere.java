public class Sphere extends Entity
{
    protected double radius;

    public Sphere(double radius)
    {
        super();
        this.radius = radius;
    }
    public Sphere(Vector pos, Vector rot, double radius)
    {
        super(pos,rot);
        this.radius = radius;
    }

    @Override
    public boolean rayhit(Vector rayorigin, Vector direction, int n) {
        Vector v = rayorigin.sub(this.pos);
        double vd  = v.dotprod(direction);
        double v2 = v.dotprod(v);
        return -vd + Math.sqrt(vd*vd-(v2-radius*radius)) > 0 || -vd - Math.sqrt(vd*vd-(v2-radius*radius)) > 0;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}