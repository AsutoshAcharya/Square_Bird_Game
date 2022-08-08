package flappyBird;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

//import sun.audio.*;
public class flappyBird implements ActionListener,MouseListener
{	
	public static flappyBird flappyBird;
	
	public final int WIDTH=1200,HEIGHT=800;
	
	public Renderer renderer;
	
	public Rectangle bird;
	
	public int ticks,ymotion,score;
	
	public Boolean gameOver,started=true;
	
	public ArrayList<Rectangle> columns;
	
	public Random Rand;
	public flappyBird()
	{
		JFrame jframe = new JFrame();
		Timer timer=new Timer(20,this);
		renderer = new Renderer();
		
		Rand =new Random();
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH,HEIGHT);
		jframe.setTitle("FlappySquare");
		jframe.addMouseListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);
		
		
		
		bird=new Rectangle(WIDTH / 2 -10,HEIGHT/2 -10,20,20);
		
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		int speed=10;
		ticks++;
		
		if(started)
		{
			
		for(int i=0;i<columns.size();i++)
		{
			Rectangle column=columns.get(i);
			column.x-=speed;
		}
		
		if(ticks%2==0&&ymotion<15)
		{
			ymotion+=2;
		}
		
		for(int i=0;i<columns.size();i++)
		{
			Rectangle column=columns.get(i);
			if(column.x+column.width<0)
			{
				columns.remove(column);
			if(column.y==0)
			{
				addColumn(false);
			}
		}
		}
		bird.y+=ymotion;
		
		for(Rectangle column : columns)
		{
			if(column.y==0 && bird.x + bird.width / 2 >column.x + column.width / 2 -10 && bird.x+bird.width/2 <column.x + column.width / 2 + 10)
			{
				score++;
			}
			
			if(column.intersects(bird))
			{
				gameOver=true;
				
				bird.x=column.x-bird.width;
				
			}
		}
		if(bird.y>HEIGHT-120 || bird.y<0)
		{	
			bird.y=HEIGHT-120-bird.height;
			gameOver=true;
		}
		if(bird.y + ymotion>=HEIGHT-120)
		{
			bird.y=HEIGHT-120-bird.height;
		}
		
		}
		renderer.repaint();
		
	}
//	public static void music()
//	{
//		AdioPlayer mgp=AudioPlayer.player;
//		AudioStream bgm;
//		AudioData md;
//		
//	}
	
	public void addColumn(boolean start)
	{
		int space=300;
		int width=100;
		int height= 50+Rand.nextInt(300);
		if(start)
		{
		columns.add(new Rectangle(WIDTH +width +columns.size() *300,HEIGHT-height-120,width,height));
		columns.add(new Rectangle(WIDTH + width +(columns.size()-1)*300,0,width,HEIGHT-height-space));
		}
		else
		{
		columns.add(new Rectangle(columns.get(columns.size()-1).x+600,HEIGHT-height-120,width,height));
		columns.add(new Rectangle(columns.get(columns.size()-1).x,0,width,HEIGHT-height-space));
		}
	}
	
	
	public void paintColumn(Graphics g,Rectangle column)
	{
		g.setColor(Color.green.darker());
		g.fillRect(column.x,column.y,column.width,column.height);
	}
	
	public void jump()
	{
		if(gameOver)
		{
			bird=new Rectangle(WIDTH / 2 -10,HEIGHT/2 -10,20,20);
			
			columns.clear();
			ymotion=0;
			score=0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver=false;
		}
		if(!started)
		{
			started=true;
		}
		else if(!gameOver)
		{
			if(ymotion>0)
			{
				ymotion=0;
				
			}
			ymotion-=10;
		}
	}
	
	public void repaint(Graphics g) 
	{
		g.setColor(Color.cyan);
		g.fillRect(0,0, WIDTH, HEIGHT);
		
		g.setColor(Color.orange);
		g.fillRect(0,HEIGHT-120,WIDTH,150);
		
		g.setColor(Color.green);
		g.fillRect(0,HEIGHT-120,WIDTH,20);
		
		g.setColor(Color.red);
		g.fillRect(bird.x,bird.y,bird.width,bird.height);
		
		for(Rectangle column : columns)
		{
			paintColumn(g, column); 
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,100));
		
		if(!started)
		{
			g.drawString("click to start",75, HEIGHT/2-50);
		}
		
		if(gameOver)
		{
			g.drawString("GAME OVER",75,HEIGHT/2-50);
		}
		
		if(!gameOver && started)
		{
			g.drawString(String.valueOf(score),WIDTH /2 -25 , 100);
		}
	}
	public static void main(String[] args)
	{
		flappyBird = new flappyBird();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}



	
	

	
	
	
}
