
public abstract class Entity
{
    protected Vector pos, rot;
    public static final Vector  FWD = new Vector (0.0,0.0),
                                UP = new Vector( );


    /** Defines an entity positioned at (x,y,z) with rotation (φ,θ)
     * +x is FWD, -x is BCK
     * +y is UP, -y is DWN
     * +z is RGT, -z is LFT
     * 
     */

    public Entity()
    {
        this(new Vector(0.0,0.0,0.0),new Vector(0.0,0.0));
    }

    /**
     * 
     * @param pos the cartesian position of the entity (x,y,z)
     * @param rot the rotation of the entity in radians (φ,θ)
     */
    public Entity(Vector pos, Vector rot)
    {
        this.pos = pos;
        this.rot = rot;
    }

    public abstract boolean rayhit(Vector rayorigin,Vector direction, int n);

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
        this.rot = rot;
    }
}