package ir.alirezagharib.hackerrank;

class Shape {
    private int Length;
    private int Breadth;

    public Shape(int length, int breadth) {
        this.Breadth = breadth;
        this.Length = length;
    }

    public void area() {
        System.out.println(this.Length + " " + this.Breadth);
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public int getBreadth() {
        return Breadth;
    }

    public void setBreadth(int breadth) {
        Breadth = breadth;
    }
}


class Rectangle extends Shape{

    public Rectangle(int length, int breadth) {
        super(length, breadth);
    }

    @Override
    public void area() {
        System.out.println(this.getBreadth() * this.getLength());
    }
}

class A {
    static int add(int i, int j) {
        return i + j;
    }
}


public class Main extends A{

    public static void main(String[] args) {
	// write your code here
        short i = 9;
        System.out.println(add(i, 6));
    }
}
