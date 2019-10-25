
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
                    double yxrotation, double zxrotation,
                    double yxrotavelo, double zxrotavelo)
    {
        pos[0] = xposition; pos[1] = yposition; pos[2] = zposition;
        vel[0] = xvelocity; vel[1] = yvelocity; vel[2] = zvelocity;

        rot[0] = yxrotation; rot[1] = zxrotation;
        rvl[0] = yxrotavelo; rvl[1] = zxrotavelo;
    }

    public abstract boolean rayhit(double[] cameraposition,double[] camerarotation, double fov, int width, int height, int x, int y);

    public void move()
    {
        for (int i = 0; i < 3; i++) {
            pos[i] += vel[i];
        }
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