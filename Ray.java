// This class uses a (x,y,z) unit Vector instead of a (φ,θ) angular Vector to define direction

public class Ray extends BaseEntity
{
    public Ray()
    {
        this(new Vector(3),new Vector(3));
    }
    public Ray(Vector position,Vector direction)
    {
        pos = position;
        rot = direction;
    }
    @Override
    public Vector getRot() {//from cartesian
        return rot.toSpherical();
    }
    @Override
    public Vector getDirection() {
        return rot;
    }
    @Override
    public void setRot(Vector rot) {
        this.rot = rot.toCartesian().normalize();
    }
    @Override
    public void setDirection(Vector dir) {
        rot = dir.normalize();
    }


}