import java.awt.Color;

public abstract class Entity
{
    protected Vector pos, rot;
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
        this(new Vector(0.0,0.0,0.0),new Vector(0.0,0.0),Color.WHITE,0.5);
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

    public abstract boolean rayhit(Vector rayorigin,Vector direction, int n);

    public abstract double getRadius();
    /**
     * @return the pos
     */
    public Vector getPos() {
        return pos;
    }
    /**
     * @return the rot
     */
    public Vector getRot() {
        return rot;
    }
    /**
     * @param pos the pos to set
     */
    public void setPos(Vector pos) {
        this.pos = pos;
    }
    /**
     * @param rot the rot to set
     */
    public void setRot(Vector rot) {
        this.rot = new Vector(rot.get(0)%(2*Math.PI),rot.get(1)%(2*Math.PI));
    }
}