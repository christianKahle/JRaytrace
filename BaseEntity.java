

public abstract class BaseEntity
{
    protected Vector pos, rot;


    /** Defines an entity positioned at (x,y,z) with rotation (φ,θ)
     * +x is FWD, -x is BCK
     * +y is UP, -y is DWN
     * +z is RGT, -z is LFT
     * 
     */

    public BaseEntity()
    {
        this(new Vector(3),new Vector(2));
    }

    /**
     * 
     * @param pos the cartesian position of the entity (x,y,z)
     * @param rot the rotation of the entity in radians (φ,θ)
     */
    public BaseEntity(Vector pos, Vector rot)
    {
        this.pos = pos;
        this.rot = rot;
    }

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
    public Vector getDirection() {
        return rot.toCartesian();
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
    public void setDirection(Vector dir){
        rot = dir.toSpherical().normalize();
    }
}