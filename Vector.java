public class Vector
{
    private double[] elements;

    public Vector(double... d)
    {
        for (int i = 0; i < d.length; i++) {
            if(Math.abs(d[i]) < 0.000001)
                d[i] = 0;
        }
        elements = d;
    }
    
    /**
     * @return the elements
     */
    public double[] getElements() {
        return elements;
    }
    public double get(int n) {
        return elements[n];
    }
    public int length(){
        return elements.length;
    }

    public Vector add(Vector that)
    {
        double[] s = new double[elements.length];
        for (int i = 0; i < elements.length; i++) 
            s[i] = this.elements[i] + that.elements[i];
        return new Vector(s);
    }

    public Vector sub(Vector that)
    {
        double[] s = new double[elements.length];
        for (int i = 0; i < elements.length; i++) 
            s[i] += this.elements[i] - that.elements[i];
        return new Vector(s);
    }

    public double dotprod(Vector that)
    {
        if(this.length() != that.length())
            throw new IndexOutOfBoundsException();
        double sum = 0.0;
        for (int i = 0; i < elements.length; i++) 
            sum += this.elements[i] * that.elements[i];
        return sum;
    }

    public double abs()
    {
        double sum = 0;
        for (double d : elements) {
            sum += d*d;
        }
        return Math.sqrt(sum);
    }

    public Vector prod(double that)
    {
        double[] e = new double[elements.length];
        for (int i = 0; i < e.length; i++) {
            e[i] = elements[i] * that;
        }
        return new Vector(e);
    }

    public Vector div(double that)
    {
        double[] e = new double[elements.length];
        for (int i = 0; i < e.length; i++) {
            e[i] = elements[i] / that;
        }
        return new Vector(e);
    }

    public Vector normalise()
    {
        return div(abs());
    }

    public Vector cross(Vector that)
    {
        if(!(this.elements.length == that.elements.length && this.elements.length == 3))
            throw new IndexOutOfBoundsException();
        double[] s = {  this.elements[1]*that.elements[2]-this.elements[2]*that.elements[1],
                        this.elements[2]*that.elements[0]-this.elements[0]*that.elements[2 ],
                        this.elements[0]*that.elements[1]-this.elements[1]*that.elements[0]};
        return new Vector(s);
    }

    public String toString() {
        String s = "[";
        for (int i = 0; i < elements.length-1; i++) {
            s += elements[i]+",";
        }
        return s+elements[elements.length-1]+"]";
    }
}