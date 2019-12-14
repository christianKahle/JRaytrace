import java.awt.Color;

public abstract class Entity extends BaseEntity
{
    protected Color color;
    protected double reflectivity;

    /** Defines an entity positioned at (x,y,z) with rotation (φ,θ)
     * +x is FWD, -x is BCK
     * +y is UP, -y is DWN
     * +z is RGT, -z is LFT
     * 
     */

    public Entity()
    {
        this(new Vector(3),new Vector(2),Color.WHITE,0.5);
    }

    /**
     * 
     * @param pos the cartesian position of the entity (x,y,z)
     * @param rot the rotation of the entity in radians (φ,θ)
     */
    public Entity(Vector pos, Vector rot, Color color, double reflectivity)
    {
        this.pos = pos;
        this.rot = rot;
        this.color = color;
        this.reflectivity = reflectivity;
    }

    public abstract double distance(Ray ray);
    public abstract Ray reflect(Ray ray,double distance);
    public abstract Ray refract(Ray ray,double distance);
    public abstract double getRadius();
}