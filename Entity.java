
public class Entity
{
    protected double pos[], vel[], rot[], rvl[];
    public static final double D3ZERO[] = {0.0,0.0,0.0};
    public static final double D2ZERO[] = {0.0,0.0};
    public Entity()
    {
        this(D3ZERO,D3ZERO,D3ZERO,D3ZERO);
    }
    /**
     * @param position entitie's position as {x,y,z} 
     * @param velocity entitie's velocity as {x,y,z} 
     * @param rotation entitie's rotation along each axis as {x,y} 
     * @param rotationalvelocity entitie's rotational velocity along each axis as {x,y} 
     */
    public Entity(double position[], double velocity[], double rotation[], double rotationalVelocity[])
    {
        pos = position;
        vel = velocity;
        rot = rotation;
        rvl = rotationalVelocity;
    }


    public void move()
    {
        for (int i = 0; i < 3; i++) {
            pos[i] += vel[i];
            rot[i] += rvl[i];
        }
    }
}