
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
		private JButton btn_tj =null;//�ύ
		private JButton btn_begin =null;//��ʼ����
		private JButton btn_js =null;//ͳ�ƽ��
		private JPanel p_area = null;
		private JPanel p_btn = null;
		private GridLayout layout =null;
		private JLabel lbl=null;//��ʾʱ��
		ArrayList<String> count_array= new ArrayList<String>();
		Date now = new Date();
							
		public MyFrame(String title) {
			super(title);
			init();
			registerListener();
		}
		//��ʼ������
		public void init() {
			now.setHours(0);
			now.setMinutes(0);
			now.setSeconds(0);
			 			
			area=new JTextArea();
			area.setFont(new Font("����",Font.BOLD,20));
			
			layout = new GridLayout();

			btn_tj = new JButton("�ύ");
			btn_begin = new JButton("��ʼ����");
			btn_js = new JButton("ͳ�ƽ��");
			
			p_area= new JPanel();
			p_btn=new JPanel();
			p_btn.setBackground(Color.LIGHT_GRAY);
			
			lbl = new JLabel("��ʱ����");
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
		
		//ע�����
		private void registerListener() {
			//��ʱ
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
					//��ʼ���Ժ�������
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
					//ͳ�ƽ������
					counts();
				}				
			});
		}
		//��ʼ���Ժ���
		public void begin() {			
			//��ʽ���ɴ���
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
		
		//�ύ����
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
					fw.write(13);//д��
			        fw.write(10);//д��
			    }
				else
			    {
					fw.write(str.charAt(i));
			    }
			}
			fw.close();						
			SubFrame subframe= new SubFrame("���");
			int a=subframe.compare();
			String s=String.valueOf(a);
			count_array.add(s);
		}
		//ͳ�ƽ������
		public void counts() {
			Chart chart=new Chart();
			for(String e:count_array) {
				System.out.println(e);
			}
		}
		class Chart extends JFrame{   
		    //��������ͳ��ͼ   
		    public Chart(){  
		        super();  
		        setTitle("���Խ��ͳ��");  
		        setBounds(400, 60, 700, 550);
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		        this.setVisible(true);
		        this.setResizable(false);
		    }  
			public void paint(Graphics g){ 
		    	SubFrame subframe= new SubFrame("���");
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
		        int leftMargin = 20;//����ͼ��߽�  
		        //int topMargin = 200;//����ͼ�ϱ߽�  
		        Graphics2D g2 = (Graphics2D) g;  
		        int ruler = Height;  
		        int rulerStep = ruler/11;//����ǰ�ĸ߶�ƽ��Ϊ10����λ  
		        g2.setColor(Color.LIGHT_GRAY);//���ƻ�ɫ����  
		        g2.fillRect(0, 0, Width, Height);//���ƾ���ͼ  
		        g2.setColor(Color.GRAY);  
		        
		        for(int i=0;i<=10;i++){  
		            g2.drawString(String.valueOf(100-(10*i)), 20, rulerStep*i+44);//д�·���
		            g2.drawLine(0, rulerStep*i+44, Width, rulerStep*i+44);//���ƺ���  
		        }  
		        g2.setColor(Color.DARK_GRAY);
		        for(int i=0;i<count_array.size();i++) {
		        	//��������ͼ  
		        	int f = Integer.parseInt(count_array.get(i));
		        	//����ÿ������ͼ��ˮƽ���Ϊ30
		            int step = (i+1)*30;  
		            //���ƾ���  
		            g2.fillRoundRect(leftMargin+step*2, Height-(Height/110)*f-5, 30, (Height/110)*f-3,30, 0);    
		            g2.drawString("��"+(i+1)+"��", leftMargin+step*2,Height-(Height/110)*f-10);  
		        }
		         
		    }  
		}  
	}
		