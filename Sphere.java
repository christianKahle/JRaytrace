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

    //TODO Fix eyes in the back of head
    @Override
    public boolean rayhit(Camera camera, int width, int height, int x, int y) {
        double tzx = (double)camera.getFov()/width*(x-width/2)*Math.PI/180;
        double tyx = ((double)camera.getFov()*((double)height/width)*(y-width/2))/height*Math.PI/180;
        double position[] = new double[3];
        for(int i = 0; i < 3; i++)
            position[i] = pos[i] - camera.getPos()[i];
        double rad = Math.sqrt(position[0]*position[0]+position[1]*position[1]+position[2]*position[2]);
        double tzx1 = tzx + Math.atan(position[2]/position[0]);
        double tyx1 = tyx + Math.atan(position[1]/position[0]);
        return Math.sqrt(Math.pow(rad*Math.sin(tyx1),2)+Math.pow(rad*Math.sin(tzx1),2)) < radius;
    }
    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
}