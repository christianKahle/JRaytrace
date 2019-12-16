import java.awt.Color;

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
        super(pos,rot,false);
        this.radius = radius;
    }
    public Sphere(Vector pos, Vector rot, double radius, boolean light)
    {
        super(pos,rot,light);
        this.radius = radius;
    }

    @Override
    public double distance(Ray ray) {
        Vector v = ray.getPos().sub(pos);
        double vd  = v.dotprod(ray.getDirection());
        double v2 = v.dotprod(v);
        double t = Math.min(-vd + Math.sqrt(vd*vd-(v2-radius*radius)), -vd - Math.sqrt(vd*vd-(v2-radius*radius)));
        if (t < 0)
            return 0.0;
        return t;
    }

    @Override
    public Ray reflect(Ray ray, double distance)
    {
        Vector x = ray.getPos().add(ray.getDirection().prod(distance));
        Vector n = x.sub(pos).normalize();
        Vector r = ray.getDirection().sub(n.prod(2*n.dotprod(ray.getDirection())));
        return new Ray(x,r);
    }
    @Override
    public Ray refract(Ray ray, double distance) {
        // TODO Refractions
        return null;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}