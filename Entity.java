import java.awt.Color;

public abstract class Entity extends BaseEntity
{
    protected boolean light;

    /** Defines an entity positioned at (x,y,z) with rotation (φ,θ)
     * +x is FWD, -x is BCK
     * +y is UP, -y is DWN
     * +z is RGT, -z is LFT
     * 
     */

    public Entity()
    {
        this(new Vector(3),new Vector(2),false);
    }

    /**
     * 
     * @param pos the cartesian position of the entity (x,y,z)
     * @param rot the rotation of the entity in radians (φ,θ)
     */
    public Entity(Vector pos, Vector rot, boolean light)
    {
        this.pos = pos;
        this.rot = rot;
        this.light = light;
 
    }

    public abstract double distance(Ray ray);
    public abstract Ray reflect(Ray ray,double distance);
    public abstract Ray refract(Ray ray,double distance);
    public abstract double getRadius();
}