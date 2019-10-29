
public abstract class Entity
{
    protected double pos[] = new double[3], vel[] = new double[3], rot[] = new double [2], rvl[] = new double [2];
    public static final double D3ZERO[] = {0.0,0.0,0.0};
    public static final double D2ZERO[] = {0.0,0.0};
    public Entity()
    {
        this(0.0,0.0,0.0, 0.0,0.0,0.0, 0.0,0.0, 0.0,0.0);
    }
    
    /**
     * @param xposition
     * @param yposition
     * @param zposition
     * @param xvelocity
     * @param yvelocity
     * @param zvelocity
     * @param yxrotation
     * @param zxrotation
     * @param yxrotavelo
     * @param zxrotavelo
     */
    public Entity(  double xposition, double yposition, double zposition,
                    double xvelocity, double yvelocity, double zvelocity,
                    double zenithrotation, double azimuthrotation,
                    double zenithvelocity, double azimuthvelocity)
    {
        pos[0] = xposition; pos[1] = yposition; pos[2] = zposition;
        vel[0] = xvelocity; vel[1] = yvelocity; vel[2] = zvelocity;

        rot[0] = zenithrotation; rot[1] = azimuthrotation;
        rvl[0] = zenithvelocity; rvl[1] = azimuthvelocity;
    }

    public abstract boolean rayhit(double[] rayorigin,double[] d, int n);

    public void move()
    {
        for (int i = 0; i < 3; i++) {
            pos[i] += vel[i];
        }
    }

    public static double dotproduct(double[] v1,double[] v2)
    {
        if(v1.length != v2.length)
            throw new IndexOutOfBoundsException();
        double sum = 0;
        for(int i = 0; i < v1.length; i++)
        {
            sum += v1[i]*v2[i];
        }
        return sum;
    }

    /**
     * @return the pos
     */
    public double[] getPos() {
        return pos;
    }
    /**
     * @return the rot
     */
    public double[] getRot() {
        return rot;
    }
    /**
     * @return the rvl
     */
    public double[] getRvl() {
        return rvl;
    }
    /**
     * @return the vel
     */
    public double[] getVel() {
        return vel;
    }
    public double[] getUnitVRotation()
    {
        double[] d = {Math.sin(rot[0])*Math.cos(rot[1]),Math.sin(rot[0])*Math.sin(rot[1]),Math.sin(rot[1])};
        return d;
    }

    /**
     * @param rot the rot to set
     */
    public void setRot(double[] rot) {
        this.rot = rot;
    }
    /**
     * @param vel the vel to set
     */
    public void setVel(double[] vel) {
        this.vel = vel;
    }
}