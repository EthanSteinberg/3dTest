package com.steinberg.ethan.graphics;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Math.*;
import java.util.ArrayList;

public class FirstTest extends Applet implements KeyListener {

	double[] xcords = new double[8];
	double[] ycords = new double[8];
	double[] zcords = new double[8];

	ArrayList<Triangle> triags = new ArrayList<Triangle>();

	private static final long serialVersionUID = 1L;


	Matrix view4 = Matrix.perspective(200, 200, -100, 100);
	Matrix view = Matrix.identity(4);

	
	

	@Override
	public void init() {

		generateCube(20,25,20,5,5,5);

		
		int[] points = new int[4];
		
		points[0] = 0;
		points[1] = 2;
		points[2] = 3;
		points[3] = 1;
		createSquare(points,Color.black);
		points[0] = 4;
		points[1] = 6;
		points[2] = 7;
		points[3] = 5;
		createSquare(points,Color.green);
		points[0] = 0;
		points[1] = 2;
		points[2] = 4;
		points[3] = 6;
		createSquare(points,Color.yellow);
		points[0] = 3;
		points[1] = 1;
		points[2] = 7;
		points[3] = 5;
		createSquare(points,Color.blue);
		points[0] = 6;
		points[1] = 2;
		points[2] = 5;
		points[3] = 1;
		createSquare(points,Color.cyan);
		points[0] = 4;
		points[1] = 0;
		points[2] = 7;
		points[3] = 3;
		createSquare(points,Color.orange);

		
		
		addKeyListener(this);

	}
	
	void createSquare(int[] points, Color c)
	{
		double[] xpoints = new double[3];
		double[] ypoints = new double[3];
		double[] zpoints = new double[3];

		
		
		xpoints[0] = xcords[points[0]];
		xpoints[1] = xcords[points[1]];
		xpoints[2] = xcords[points[2]];

		ypoints[0] = ycords[points[0]];
		ypoints[1] = ycords[points[1]];
		ypoints[2] = ycords[points[2]];

		zpoints[0] = zcords[points[0]];
		zpoints[1] = zcords[points[1]];
		zpoints[2] = zcords[points[2]];

		triags.add(new Triangle(xpoints, ypoints, zpoints,c));
		
		xpoints[0] = xcords[points[1]];
		xpoints[1] = xcords[points[2]];
		xpoints[2] = xcords[points[3]];

		ypoints[0] = ycords[points[1]];
		ypoints[1] = ycords[points[2]];
		ypoints[2] = ycords[points[3]];

		zpoints[0] = zcords[points[1]];
		zpoints[1] = zcords[points[2]];
		zpoints[2] = zcords[points[3]];

		triags.add(new Triangle(xpoints, ypoints, zpoints,c));
		
	}

	void generateCube(int x1, int y1, int z1, int x2, int y2, int z2) {
		for (int i = 0; i < 8; i++) {
			if (i < 4)
				zcords[i] = z1;
			else
				zcords[i] = z2;

			if (i % 2 == 0)
				ycords[i] = y1;
			else
				ycords[i] = y2;

			if (i % 4 == 0 || i % 4 == 3)
				xcords[i] = x1;
			else
				xcords[i] = x2;
		}

	}


	@Override
	public void paint(Graphics g) {

		
		Matrix transform = Matrix.multiply(view4, view);
		
		for (Triangle tri : triags)
			tri.prepare(transform);
		
		java.util.Collections.sort(triags);
		
		for (Triangle tri : triags)
			tri.draw(g);

	}

	void drawRect(Graphics g, int x1, int y1, int x2, int y2) {

		g.fillRect(min(x1, x2), min(y1, y2), abs(x1 - x2), Math.abs(y1 - y2));
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_S: {
			view = Matrix.multiply(view, Matrix.generate(.1));

			repaint();
			break;
		}

		case KeyEvent.VK_R: {
			view = Matrix.multiply(view, Matrix.generate3(.1));
			repaint();
			break;
		}

		case KeyEvent.VK_T: {
			view = Matrix.multiply(view, Matrix.generate2(.1));
			repaint();
			break;
		}
		
		case KeyEvent.VK_U: {
			view = Matrix.multiply(view, Matrix.translate(0,0,-5));
			
			repaint();
			break;
		}
		
		case KeyEvent.VK_E: {
			view = Matrix.multiply(view, Matrix.translate(0,0,5));
			
			repaint();
			break;
		}
		case KeyEvent.VK_N: {
			view = Matrix.multiply(view, Matrix.translate(-5,0,0));
			
			repaint();
			break;
		}
		case KeyEvent.VK_I: {
			view = Matrix.multiply(view, Matrix.translate(5,0,0));
			
			repaint();
			break;
		}
		
		case KeyEvent.VK_L: {
			view = Matrix.multiply(view, Matrix.translate(5,0,0));
			
			repaint();
			break;
		}
		case KeyEvent.VK_Y: {
			view = Matrix.multiply(view, Matrix.translate(5,0,0));
		
			repaint();
			break;
		}
		
		case KeyEvent.VK_H:
			System.out.println( Matrix.multiply(view, view4));
			b();
		}

	}

	void b(){
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}

class Triangle implements Comparable<Triangle> {

	double[] xpoints, ypoints, zpoints;

	
	static int id = 0;
	int myId = id++;
	
	int[] tempX = new int[3];
	int[] tempY = new int[3];
	double zcord;
	
	Color col;

	Triangle(double[] xpos, double[] ypos, double[] zpos, Color c) {
		xpoints = xpos.clone();
		ypoints = ypos.clone();
		zpoints = zpos.clone();
		col = c;
	}

	@Override
	public int compareTo(Triangle o) {

		if (zcord > o.zcord)
			return 1;
		
		else if (zcord < o.zcord)
			return -1;
		
		else
			return 0;
	}

	public void draw(Graphics g) {

		g.setColor(col);
		g.fillPolygon(tempX, tempY, 3);
	}
	
	void prepare(Matrix projection)
	{
		applyMatrix(projection, tempX, tempY);
	}

	private void applyMatrix(Matrix projection, int[] tempX,
			int[] tempY) {
		
		zcord = 0;
		Matrix temp = new Matrix(1, 4);
		for (int i = 0; i < 3; i++) {
			temp.list[0][0] = xpoints[i];
			temp.list[1][0] = ypoints[i];
			temp.list[2][0] = zpoints[i];
			temp.list[3][0] = 1;

			temp = Matrix.multiply(projection,temp );

			tempX[i] = (int) Math.round((temp.list[0][0]/temp.list[3][0] * -200 +100));
			tempY[i] = (int) Math.round((temp.list[1][0]/temp.list[3][0] * -200 + 100));
			zcord += temp.list[2][0]/temp.list[3][0];
			//System.out.println(zcord);

		}
		zcord/= 3.0;
	}

}

class Matrix {

	public double[][] list;

	int numX;
	int numY;

	Matrix(int x, int y) {
		numX = x;
		numY = y;
		list = new double[y][x];
	}

	Matrix(int x) {
		numX = x;
		numY = x;
		list = new double[x][x];
	}
	
	static Matrix translate(double x, double y, double z)
	{
		Matrix mat = Matrix.identity(4);
		mat.list[0][3] = x;
		mat.list[1][3] = y;
		mat.list[2][3] = z;
		return mat;
	}

	static Matrix orthogonal(double width, double height, double far,
			double near) {
		Matrix mat = new Matrix(4);

		mat.list[0][0] = 2 / width;
		mat.list[1][1] = 2 / height;
		mat.list[2][2] = -2 / (far - near);
		mat.list[2][3] = -(far + near) / (far - near);
		mat.list[3][3] = 1;
		return mat;
	}
	
	static Matrix perspective(double width, double height, double far,
			double near) {
		Matrix mat = new Matrix(4);

		mat.list[0][0] = 2  * near / width;
		mat.list[1][1] = 2 * near/ height;
		mat.list[2][2] = -(far + near) / (far - near);
		mat.list[2][3] = -2 * far * near/(far - near);
		mat.list[3][2] = -1;
		return mat;
	}

	static Matrix identity(int side) {
		Matrix mat = new Matrix(side, side);
		for (int i = 0; i < side; i++)
			mat.list[i][i] = 1;

		return mat;
	}

	static Matrix generate(double angle) {
		Matrix translate = new Matrix(4);

		translate.list[0][0] = cos(angle);
		translate.list[0][1] = -sin(angle);
		translate.list[0][2] = 0;

		translate.list[1][0] = sin(angle);
		translate.list[1][1] = cos(angle);
		translate.list[1][2] = 0;

		translate.list[2][0] = 0;
		translate.list[2][1] = 0;
		translate.list[2][2] = 1;

		translate.list[3][3] = 1;
		return translate;
	}

	static Matrix generate2(double angle) {
		Matrix translate = new Matrix(4);

		translate.list[0][0] = cos(angle);
		translate.list[0][1] = 0;
		translate.list[0][2] = sin(angle);

		translate.list[1][0] = 0;
		translate.list[1][1] = 1;
		translate.list[1][2] = 0;

		translate.list[2][0] = -sin(angle);
		translate.list[2][1] = 0;
		translate.list[2][2] = cos(angle);
		
		translate.list[3][3] = 1;
		return translate;
	}

	static Matrix generate3(double angle) {
		Matrix translate = new Matrix(4);

		translate.list[0][0] = 1;
		translate.list[0][1] = 0;
		translate.list[0][2] = 0;

		translate.list[1][0] = 0;
		translate.list[1][1] = cos(angle);
		translate.list[1][2] = -sin(angle);

		translate.list[2][0] = 0;
		translate.list[2][1] = sin(angle);
		translate.list[2][2] = cos(angle);

		translate.list[3][3] = 1;

		return translate;
	}

	static Matrix multiply(final Matrix first, final Matrix second) {
		if (second.numY != first.numX) {
			System.out.println("They are the wrong sizes");
			throw new RuntimeException();
		}
		Matrix result = new Matrix(second.numX, first.numY);

		for (int y = 0; y < first.numY; y++)
			for (int x = 0; x < second.numX; x++) {
				float sum = 0;
				for (int i = 0; i < first.numX; i++)
					sum += first.list[y][i] * second.list[i][x];

				result.list[y][x] = sum;
			}

		return result;
	}

	public String toString() {
		StringBuilder blah = new StringBuilder();
		for (double[] row : list) {

			for (double b : row) {
				blah.append(b);
				blah.append(' ');
			}
			blah.append('\n');

		}
		return blah.toString();

	}
}
