public class Vector
{
    private double[] elements;

    public Vector(double... d)
    {
        elements = d;

    }
    
    /**
     * @return the elements
     */
    public double[] getElements() {
        return elements;
    }
    public int length(){
        return elements.length;
    }

    public double dotprod(Vector v)
    {
        if(this.length() != v.length())
            throw new IndexOutOfBoundsException();
        double sum = 0.0;
        for (int i = 0; i < elements.length; i++) 
            sum += this.elements[i] * v.elements[i];
        return sum;
    }
}