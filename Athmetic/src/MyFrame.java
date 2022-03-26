
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

class MyFrame extends JFrame {
	
		private JTextArea area=null;
		public JTextField field[];
		private JButton btn_tj =null;//提交
		private JButton btn_begin =null;//开始测试
		private JButton btn_js =null;//统计结果
		private JPanel p_area = null;
		private JPanel p_btn = null;
		private GridLayout layout =null;
		private JLabel lbl=null;//显示时间
		ArrayList<String> count_array= new ArrayList<String>();
		Date now = new Date();
							
		public MyFrame(String title) {
			super(title);
			init();
			registerListener();
		}
		//初始化函数
		public void init() {
			now.setHours(0);
			now.setMinutes(0);
			now.setSeconds(0);
			 			
			area=new JTextArea();
			area.setFont(new Font("宋体",Font.BOLD,20));
			
			layout = new GridLayout();

			btn_tj = new JButton("提交");
			btn_begin = new JButton("开始测试");
			btn_js = new JButton("统计结果");
			
			p_area= new JPanel();
			p_btn=new JPanel();
			p_btn.setBackground(Color.LIGHT_GRAY);
			
			lbl = new JLabel("计时区域");
		    lbl.setBackground(Color.black);
			p_area.setLayout(layout);
			p_area.add(area);
			
			p_btn.add(btn_begin);
			p_btn.add(btn_tj);;
			p_btn.add(btn_js);
			p_btn.add(lbl);
			
			this.add(p_area,BorderLayout.CENTER);
			this.add(p_btn,BorderLayout.SOUTH);			
			this.setBackground(Color.BLUE);
			this.setBounds(100,20,1000,700);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setVisible(true);
		}
		
		//注册监听
		private void registerListener() {
			//计时
			Timer timer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date now2 = new Date(now.getTime() + 1000);
					now = now2;
					SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
					lbl.setText(formatter.format(now));
				}
			});
			btn_begin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//开始测试函数调用
					begin();
					timer.start();
				}				
			});
			btn_tj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					timer.stop();
					try {
						submit();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			btn_js.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//统计结果函数
					counts();
				}				
			});
		}
		//开始测试函数
		public void begin() {			
			//算式生成代码
			area.setText("");
			CalTest calrandom= new CalTest();
			try {
				calrandom.random();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File file = new File("suanshi.txt");
		    if (file.exists() && file.isFile()) 
		    {
		    	try {
		            BufferedReader input = new BufferedReader(new FileReader(file));
		            String text;
		            while ((text = input.readLine()) != null)
		            	area.setText(area.getText() + text + "\n");
		    	} catch (IOException ioException) {
		    	System.err.println("File Error!");
		        }		
		    }	    
		}
		
		//提交函数
		public void submit() throws IOException {
			FileWriter fw = null;
			try {
				fw = new FileWriter("result.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String str=area.getText();
			for(int i=0;i<str.length();i++)
			{
				if(str.charAt(i)==10)
				{
					fw.write(13);//写入
			        fw.write(10);//写入
			    }
				else
			    {
					fw.write(str.charAt(i));
			    }
			}
			fw.close();						
			SubFrame subframe= new SubFrame("结果");
			int a=subframe.compare();
			String s=String.valueOf(a);
			count_array.add(s);
		}
		//统计结果函数
		public void counts() {
			Chart chart=new Chart();
			for(String e:count_array) {
				System.out.println(e);
			}
		}
		class Chart extends JFrame{   
		    //绘制柱形统计图   
		    public Chart(){  
		        super();  
		        setTitle("测试结果统计");  
		        setBounds(400, 60, 700, 550);
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		        this.setVisible(true);
		        this.setResizable(false);
		    }  
			public void paint(Graphics g){ 
		    	SubFrame subframe= new SubFrame("结果");
		    	subframe.setVisible(false);
		    	int a=0;
				try {
					a = subframe.compare();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String s=String.valueOf(a);
				
		        int Width = getWidth();  
		        int Height = getHeight();  
		        int leftMargin = 20;//柱形图左边界  
		        //int topMargin = 200;//柱形图上边界  
		        Graphics2D g2 = (Graphics2D) g;  
		        int ruler = Height;  
		        int rulerStep = ruler/11;//将当前的高度平分为10个单位  
		        g2.setColor(Color.LIGHT_GRAY);//绘制灰色背景  
		        g2.fillRect(0, 0, Width, Height);//绘制矩形图  
		        g2.setColor(Color.GRAY);  
		        
		        for(int i=0;i<=10;i++){  
		            g2.drawString(String.valueOf(100-(10*i)), 20, rulerStep*i+44);//写下分数
		            g2.drawLine(0, rulerStep*i+44, Width, rulerStep*i+44);//绘制横线  
		        }  
		        g2.setColor(Color.DARK_GRAY);
		        for(int i=0;i<count_array.size();i++) {
		        	//绘制柱形图  
		        	int f = Integer.parseInt(count_array.get(i));
		        	//设置每隔柱形图的水平间隔为30
		            int step = (i+1)*30;  
		            //绘制矩形  
		            g2.fillRoundRect(leftMargin+step*2, Height-(Height/110)*f-5, 30, (Height/110)*f-3,30, 0);    
		            g2.drawString("第"+(i+1)+"轮", leftMargin+step*2,Height-(Height/110)*f-10);  
		        }
		         
		    }  
		}  
	}
		