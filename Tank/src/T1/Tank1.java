﻿package T1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Tank1 extends JFrame implements ActionListener
{
    MyPanel mp=null;
	GuankaPanel gkmp=null;	
	JMenuBar cdl=null;
	JMenu cd1=null;
	JMenuItem cdx1=null;
	JMenuItem cdx2=null;
	JMenuItem cdx3=null;
	JMenuItem cdx4=null;
	
	public static void main(String[] args) 
	{
		Tank1 t1=new Tank1();
	}
	
	public Tank1()
	{
		cdl=new JMenuBar();
		cd1 =new JMenu("游戏(G)");
		cd1.setMnemonic('G');
		cdx1 =new JMenuItem("新游戏(N)");
		cdx2 =new JMenuItem("退出游戏(E)");
		cdx3 =new JMenuItem("存盘退出(C)");
		cdx4 =new JMenuItem("继续游戏(S)");
		cdx4.addActionListener(this);
		cdx4.setActionCommand("goonGame");
		cdx3.addActionListener(this);
		cdx3.setActionCommand("saveExit");
		cdx2.addActionListener(this);
		cdx2.setActionCommand("exit");
		cdx1.addActionListener(this);
		cdx1.setActionCommand("newGame");
		cd1.add(cdx1);
		cd1.add(cdx2);
		cd1.add(cdx3);
		cd1.add(cdx4);
		cdl.add(cd1);
		gkmp=new GuankaPanel();
		Thread t=new Thread(gkmp);
		t.start();	
		this.setJMenuBar(cdl);		
		this.add(gkmp);
		this.setTitle("坦克大战");
		this.setIconImage((new ImageIcon("tank.jpg")).getImage());
		this.setSize(600,500);
		this.setLocation(270,170);
		 this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("newGame"))
		{
			mp=new MyPanel("newGame");
			this.remove(gkmp);
			this.add(mp);
			this.addKeyListener(mp);
			Thread t=new Thread(mp);
			t.start();
			this.setVisible(true);
		}
		else if(e.getActionCommand().equals("goonGame"))
		{
			mp=new MyPanel("goonGame");
			Thread t=new Thread(mp);
			t.start();
			this.remove(gkmp);
			this.add(mp);
			this.addKeyListener(mp);
			this.setVisible(true);
		}
		else if(e.getActionCommand().equals("saveExit"))
		{
			Jilu jl=new Jilu();
			jl.setDtk(mp.dtk);
			jl.cunpan();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("exit"))
		{
			Jilu.bcjl();
			System.exit(0);
		}			
	}	
}
class GuankaPanel extends JPanel implements Runnable
{
	int times=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		
		if(times%2==0)
		{
			g.setColor(Color.yellow);		
		    Font myFont=new Font("华文行楷",Font.BOLD,38);
		    g.setFont(myFont);
		    g.drawString("第1关", 140, 140);			
		}		
	}
	public void run() 
	{
		while(true)
		{
			try 
	        {
	           Thread.sleep(600);
	        }
	        catch (Exception e){}
			times++;
			this.repaint();
		}		
		
	}		
}
class MyPanel extends JPanel implements KeyListener,Runnable
{
	MyTank mt=null;
	Vector<DiTank> dtk=new Vector<DiTank>();
	Vector<Weizhi> wzjh=new Vector<Weizhi>();
	Vector<Baozha> bzjh=new Vector<Baozha>();
	int tksl=3;
	
	Image tp1=null;
	Image tp2=null;
	Image tp3=null;
	
	public MyPanel(String ss)
	{
		Jilu.dqjl();
		mt=new MyTank(140,232);		
		
		if(ss.equals("newGame"))
		{
			for(int i=0;i<tksl;i++)
			{
				DiTank dt=new DiTank((i)*180+5,0);
				dt.setFangxiang(2);
				dt.dtkxl(dtk);
				Thread t2=new Thread(dt);
				t2.start();
				Zidan zd=new Zidan(dt.x+10,dt.y+30,2);
				dt.dzd.add(zd);
				Thread t3=new Thread(zd);
				t3.start();
				dtk.add(dt);			
			}
		}
		else if(ss.equals("goonGame"))
		{
			wzjh=Jilu.dupan();
			for(int i=0;i<wzjh.size();i++)
			{
				Weizhi wz=wzjh.get(i);
				DiTank dt=new DiTank(wz.x,wz.y);
				dt.setFangxiang(wz.fangxiang);
				dt.dtkxl(dtk);
				Thread t2=new Thread(dt);
				t2.start();
				Zidan zd=new Zidan(dt.x+10,dt.y+30,2);
				dt.dzd.add(zd);
				Thread t3=new Thread(zd);
				t3.start();
				dtk.add(dt);			
			}
		}
		try {
			tp1=ImageIO.read(new File("bzxg1.gif"));
			tp2=ImageIO.read(new File("bzxg2.gif"));
			tp3=ImageIO.read(new File("bzxg3.gif"));
		} catch (Exception e) {}
		Shengyin sy=new Shengyin("./tank.wav");
		sy.start();
	}
	public void tjsj(Graphics g)
	{
		this.drawTank(80,330, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Jilu.getMtsl()+"",116,350);
		this.drawTank(150, 330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Jilu.getDtsl()+"",186,350);
        this.drawTank(450, 86, g, 0,1);
		g.setColor(Color.black);
		g.drawString(Jilu.getSdtj()+"",486,107);
		g.setColor(Color.black);
		Font f=new Font("华文彩云",Font.BOLD,20);
		g.setFont(f);
		g.drawString("您消灭的坦克总数", 410, 40);
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0,0,400,300);
		this.tjsj(g);
		if(mt.shengming)
		{
			this.drawTank(mt.getX(),mt.getY(),g,mt.fangxiang,0);
		}
		for(int i=0;i<dtk.size();i++)
		{
			DiTank dt=dtk.get(i);
			if(dt.shengming)
			{
				this.drawTank(dt.getX(),dt.getY(),g,dt.fangxiang,1);
				for(int j=0;j<dt.dzd.size();j++)
				{
					Zidan dtzd=dt.dzd.get(j);
					if(dtzd.shengming)
					{
						g.setColor(Color.white);
						g.fill3DRect(dtzd.x,dtzd.y,3,3,false);
					}else{
						
						dt.dzd.remove(dtzd);
					}
				}
			}			
		}		
		for(int i=0;i<mt.aa.size();i++)
		{
			Zidan zd=mt.aa.get(i);
			
			if(zd!=null && zd.shengming==true)
			{
				g.setColor(Color.white);
				g.fill3DRect(zd.x,zd.y,3,3,false);
			}
			if(zd.shengming==false)
			{
				mt.aa.remove(zd);
			}
		}
		for(int i=0;i<bzjh.size();i++)
		{
			Baozha bz=bzjh.get(i);
			
			if(bz.shengcunqi>6)
			{
				g.drawImage(tp1,bz.x, bz.y, 30, 30, this);
				
			}else if(bz.shengcunqi>3)
			{
				g.drawImage(tp2, bz.x, bz.y, 30, 30, this);
			}else{
				g.drawImage(tp3, bz.x, bz.y, 30, 30, this);
			}
			bz.scqjd();
			if(bz.shengcunqi==0)
			{
				bzjh.remove(bz);
			}
		}		
	} 
	public void jzwf()
	{
		for(int i=0;i<this.dtk.size();i++)
		{
			DiTank dt=dtk.get(i);
			
			for(int j=0;j<dt.dzd.size();j++)
			{
				Zidan zd=dt.dzd.get(j);
				if(mt.shengming)
				{
					this.jzdf(zd,mt);					
				}				
			}
		}
	}
	public void jzdf1()
	{
		for(int i=0;i<mt.aa.size();i++)
		{
			Zidan zd=mt.aa.get(i);
			if(zd.shengming)
			{
				for(int j=0;j<dtk.size();j++)
				{
					DiTank dt=dtk.get(j);
					
					if(dt.shengming)
					{
						if(dt.shengming)
						{
							if(this.jzdf(zd,dt))
							{
								Jilu.dtjs();
								Jilu.sdsl();
							}
						}
					}
					
				}
			}			
		this.repaint();
    	}
	}
	public boolean jzdf(Zidan zd,Tank dt)
	{
		boolean b2=false;
		switch(dt.fangxiang)
		{
		case 0:
		case 2:
			if(zd.x>dt.x && zd.x<dt.x+20 && zd.y>dt.y && zd.y<dt.y+30)
			{
				zd.shengming=false;
				dt.shengming=false;
				b2=true;
				Baozha bz=new Baozha(dt.x,dt.y);
				bzjh.add(bz);
			}
			break;
		case 1:
		case 3:
			if(zd.x>dt.x && zd.x<dt.x+30 && zd.y>dt.y && zd.y<dt.y+20)
			{
				zd.shengming=false;
				dt.shengming=false;
				b2=true;
				Baozha bz=new Baozha(dt.x,dt.y);
				bzjh.add(bz);	
			}			
		}
		return b2;
	}
	public void drawTank(int x,int y,Graphics g,int fangxiang,int leixing)
	{
		switch(leixing)
		{
		   case 0:
			   g.setColor(Color.yellow);
			   break;
		   case 1:
			   g.setColor(Color.green);
			   break;
		}
		switch(fangxiang)
		{
		case 0:
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect(x+15,y , 5, 30,false);
			g.fill3DRect(x+5,y+5 , 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y-3);
			break;
		case 1:
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+10, y+5, 10, 10);
			g.drawLine(x+15, y+10, x-3, y+10);
			break;
		case 2:
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect(x+15,y , 5, 30,false);
			g.fill3DRect(x+5,y+5 , 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y+33);
			break;
		case 3:
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+10, y+5, 10, 10);
			g.drawLine(x+15, y+10, x+33, y+10);
			break;			
		}
		this.repaint();
	}
	
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			this.mt.setFangxiang(0);
			this.mt.xiangshang();
			
		}else if(e.getKeyCode()==KeyEvent.VK_A)
		{
			this.mt.setFangxiang(1);
			this.mt.xiangzuo();
		}else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			this.mt.setFangxiang(2);
			this.mt.xiangxia();
		}else if(e.getKeyCode()==KeyEvent.VK_D)
		{
			this.mt.setFangxiang(3);
			this.mt.xiangyou();
		}	
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			if(this.mt.aa.size()<8)
			{
				this.mt.fszd();
			}
		}
	}
	public void run()
	{
		while(true)
		{
			try	{
				Thread.sleep(100);}
			catch (Exception e) {}
			this.jzdf1();	
			this.jzwf();
			this.repaint();
		}
		
	}
}
