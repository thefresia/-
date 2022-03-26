

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;

public class Submit {
	SubFrame subframe= new SubFrame("结果");
}
class SubFrame extends JFrame{
	private JButton btn_again =null;//再来一轮
	private JButton btn_xianshi =null;//显示结果
	private JButton btn_exit =null;//统计结果
	private JPanel p_left= null;
	private JPanel p_on= null;
	private JPanel p_right= null;
	private JPanel p_south= null;
	private GridLayout layout=null;
    private JTextArea r_answer=null;
    private JTextArea my_answer=null;
    //答题正确率显示
    private JLabel correct=null;
	//构造函数
	public SubFrame(String title) {
		super(title);
		init();
		registerListener();
	}
	//初始化函数
	public void init() {
		layout= new GridLayout(1,2,20,14);
		correct= new JLabel("正确率显示");
		correct.setForeground(Color.red);
		correct.setFont(new Font("楷体",Font.BOLD,20));
		r_answer= new JTextArea("显示正确结果\n");
		r_answer.setFont(new Font("宋体",Font.BOLD,20));
		my_answer = new  JTextArea("测试结果显示\n");
		my_answer.setFont(new Font("宋体",Font.BOLD,20));
		
	    btn_xianshi= new JButton("显示结果");
		btn_again= new JButton("再来一轮");
		btn_exit= new JButton("返回");
		p_left = new JPanel();
		p_right= new JPanel();
		p_right.add(my_answer);
		p_on = new JPanel();
		p_on.setLayout(layout);

		my_answer.setBackground(Color.GRAY);
		p_right.setBackground(Color.GRAY);
		p_left.setLocation(20, 20);
		p_left.add(r_answer);
		p_left.setBackground(Color.white);
		p_south= new JPanel();
		
		
		p_on.add(p_left);
		p_on.add(p_right);
		
		p_south.add(btn_xianshi);
		p_south.setBackground(Color.LIGHT_GRAY);
		p_south.add(btn_again);
		p_south.add(btn_exit);
		p_south.add(correct);
		
		this.add(p_on);
		this.add(p_south, BorderLayout.SOUTH);
		this.setBounds(100,20,1000,700);
		this.setVisible(true);
		
	}
	//设置监听
	private void registerListener() {
		btn_again.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onceagain();				
			}	
		});
		
		btn_xianshi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File file = new File("answer.txt");
			    if (file.exists() && file.isFile()) {
			    	try {
			            BufferedReader input = new BufferedReader(new FileReader(file));
			            String text;
			            while ((text = input.readLine()) != null)
			            	r_answer.setText(r_answer.getText() + text + "\n");
			    	} catch (IOException ioException) {
			    	System.err.println("File Error!");
			       }		
			    }	 			    
			    result();//显示测试结果函数调用
			    try {
					compare();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}			
		});
		btn_exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onceagain();
				
			}
			
		});
	}
	//函数实现
    public void onceagain() {
		 this.dispose();
	}
    //显示测试结果
    public void result() {
    	File file = new File("result.txt");
    	if (file.exists() && file.isFile()) {
	    	try {
	            BufferedReader input = new BufferedReader(new FileReader(file));
	            String text;
	            while ((text = input.readLine()) != null)
	            	my_answer.setText(my_answer.getText() + text + "\n");
	    	} catch (IOException ioException) {
	    	System.err.println("File Error!");
	       }		
	    }
    }
    
    //比较结果
    public int compare() throws IOException {
    	//封装数据源
        BufferedReader br_a = new BufferedReader(new FileReader("answer.txt"));
        BufferedReader br_r = new BufferedReader(new FileReader("result.txt"));
        //封装目的地
        ArrayList<String> answer = new ArrayList<String>();
        ArrayList<String> result = new ArrayList<String>();
        //读取数据写到集合中
        String line1 = null;
        String line2 = null;
       
	    while((line1 = br_a.readLine())!= null){
			answer.add(line1);
		}
		while((line2 = br_r.readLine())!= null){
			result.add(line2);
		}
		//遍历进行比较并计算正确率
		int i=0;
		int count=0;
		for(i=0;i<answer.size();i++) 
		{
			String s1=answer.get(i);
			String s2=result.get(i);
			if(s1.equals(s2)) 
			{
				count++;						
			}				
		}
		NumberFormat numberFormat = NumberFormat.getInstance();    
        numberFormat.setMaximumFractionDigits(2);  
        String s3 = numberFormat.format((float) count / (float)answer.size() * 100);   
		correct.setText("您做对了"+count+"道， "+"正确率为："+String.valueOf(s3)+"%");
		int b=count*5;
		return b;
    }
    
  /*  
	private Object line2(String readLine) {
		return null;
	}
	private Object newFileReader1(String string) {
		return null;
	}
	
	*/
}