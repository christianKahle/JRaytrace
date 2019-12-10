public class Vector
{
    private double[] elements;

    /**
     * Creates a new Vector from double array d
     * @param d
     */
    public Vector(double... d)
    {
        for (int i = 0; i < d.length; i++) {
            if(Math.abs(d[i]) < 0.000001)
                d[i] = 0;
        }
        elements = d;
    }
    
    /**
     * @return Double array of this Vector's elements
     */
    public double[] getElements() {
        return elements;
    }
    /**
     * Returns elements[n]
     * @param n
     * @return nth element from this Vector's elements
     */
    public double get(int n) {
        return elements[n];
    }
    /**
     * @return The length of this Vector
     */
    public int length(){
        return elements.length;
    }
    /**
     * Adds Vector this and Vector that. this + that.
     * Resultant length will be the the length of the longer Vector.
     * @param that
     * @return The addition of this and that
     */
    public Vector add(Vector that)
    {
        double[] s = new double[elements.length];
        for (int i = 0; i < elements.length; i++) 
            s[i] = this.elements[i] + that.elements[i];
        return new Vector(s);
    }
    /**
     * Subtracts Vector that from Vector this. this - that.
     * Resultant length will be the the length of the longer Vector.
     * @param that
     * @return The subtraction of this from that
     */
    public Vector sub(Vector that)
    {
        double[] s = new double[elements.length];
        for (int i = 0; i < elements.length; i++) 
            s[i] += this.elements[i] - that.elements[i];
        return new Vector(s);
    }
    /**
     * Returns the result of the dot product of Vector this and Vector that: this * that. The result is a scalar.
     * Returns (this[0]*that[0] + this[1]*that[1] + ... + this[n-1]*that[n-1]).
     * @param that
     * @throws IndexOutOfBoundsException if the lengths of this and that do not match
     * @return The dot product of this and that
     */
    public double dotprod(Vector that)
    {
        if(this.length() != that.length())
            throw new IndexOutOfBoundsException();
        double sum = 0.0;
        for (int i = 0; i < elements.length; i++) 
            sum += this.elements[i] * that.elements[i];
        return sum;
    }
    /**
     * Returns the magnitude of Vector this: |this|. The result is a scalar.
     * @return The magnitude of Vector this
     */
    public double abs()
    {
        double sum = 0;
        for (double d : elements) {
            sum += d*d;
        }
        return Math.sqrt(sum);
    }

    /**
     * Returns the result of the scalar multiplication of Vector this * double that
     * Each element of the Vector is divided by the scalar multiplier.
     * @param that
     * @return The Vector product of this * that
     */
    public Vector prod(double that)
    {
        double[] e = new double[elements.length];
        for (int i = 0; i < e.length; i++) {
            e[i] = elements[i] * that;
        }
        return new Vector(e);
    }
    /**
     * Returns the result of the scalar division of Vector this / double that.
     * Each element of the vector is divided by the scalar divider.
     * @param that
     * @return The Vector quotient of this / that
     */
    public Vector div(double that)
    {
        double[] e = new double[elements.length];
        for (int i = 0; i < e.length; i++) {
            e[i] = elements[i] / that;
        }
        return new Vector(e);
    }

    /**
     * Returns the unit Vector of this, this's direction.
     * A unit vector always has a magnitude of 1. For example: |u| = 1 where u is a unit vector.
     * @return
     */
    public Vector normalize()
    {
        return div(abs());
    }
    /**
     * Returns the cross product of Vector this and Vector that: this x that.
     * @param that 
     * @return The cross product of this and that
     */
    public Vector cross(Vector that) 
    {
        if(!(this.elements.length == that.elements.length && this.elements.length == 3))
            throw new IndexOutOfBoundsException();
        double[] s = {  this.elements[1]*that.elements[2]-this.elements[2]*that.elements[1] ,
                        this.elements[2]*that.elements[0]-this.elements[0]*that.elements[2] ,
                        this.elements[0]*that.elements[1]-this.elements[1]*that.elements[0]};
        return new Vector(s);
    }

    /**
     * Vector this is converted from spherical angles (azumuth,zenith) to a spherical unit vector (x.y,z).
     * @return 3d unit Vector representation of this spherical angle Vector
     */
    public Vector toSpherical() 
    {
        if(this.elements.length != 2)
            throw new IndexOutOfBoundsException("Vector Length Exception: vector length must be 2");
        return new Vector(Math.sin(elements[0])*Math.cos(elements[1]),Math.sin(elements[0])*Math.sin(elements[1]),Math.cos(elements[0]));
    }

    /**
     * Returns a String in the form: "(e[0],e[1],e[2],...,e[n-1])".
     * @return String representation of Vector this
     */
    public String toString() {
        String s = "(";
        for (int i = 0; i < elements.length-1; i++) {
            s += elements[i]+",";
        }
        return s+elements[elements.length-1]+")";
    }
}