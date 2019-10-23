public class Sphere extends Entity
{
    protected double radius;

    public Sphere(double radius)
    {
        super();
        this.radius = radius;
    }
    public Sphere(double position[], double velocity[], double rotation[], double rotationalVelocity[], double radius)
    {
        super(position,velocity,rotation,rotationalVelocity);
        this.radius = radius;
    }


    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}