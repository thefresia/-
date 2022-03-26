

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
	SubFrame subframe= new SubFrame("���");
}
class SubFrame extends JFrame{
	private JButton btn_again =null;//����һ��
	private JButton btn_xianshi =null;//��ʾ���
	private JButton btn_exit =null;//ͳ�ƽ��
	private JPanel p_left= null;
	private JPanel p_on= null;
	private JPanel p_right= null;
	private JPanel p_south= null;
	private GridLayout layout=null;
    private JTextArea r_answer=null;
    private JTextArea my_answer=null;
    //������ȷ����ʾ
    private JLabel correct=null;
	//���캯��
	public SubFrame(String title) {
		super(title);
		init();
		registerListener();
	}
	//��ʼ������
	public void init() {
		layout= new GridLayout(1,2,20,14);
		correct= new JLabel("��ȷ����ʾ");
		correct.setForeground(Color.red);
		correct.setFont(new Font("����",Font.BOLD,20));
		r_answer= new JTextArea("��ʾ��ȷ���\n");
		r_answer.setFont(new Font("����",Font.BOLD,20));
		my_answer = new  JTextArea("���Խ����ʾ\n");
		my_answer.setFont(new Font("����",Font.BOLD,20));
		
	    btn_xianshi= new JButton("��ʾ���");
		btn_again= new JButton("����һ��");
		btn_exit= new JButton("����");
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
	//���ü���
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
			    result();//��ʾ���Խ����������
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
	//����ʵ��
    public void onceagain() {
		 this.dispose();
	}
    //��ʾ���Խ��
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
    
    //�ȽϽ��
    public int compare() throws IOException {
    	//��װ����Դ
        BufferedReader br_a = new BufferedReader(new FileReader("answer.txt"));
        BufferedReader br_r = new BufferedReader(new FileReader("result.txt"));
        //��װĿ�ĵ�
        ArrayList<String> answer = new ArrayList<String>();
        ArrayList<String> result = new ArrayList<String>();
        //��ȡ����д��������
        String line1 = null;
        String line2 = null;
       
	    while((line1 = br_a.readLine())!= null){
			answer.add(line1);
		}
		while((line2 = br_r.readLine())!= null){
			result.add(line2);
		}
		//�������бȽϲ�������ȷ��
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
		correct.setText("��������"+count+"���� "+"��ȷ��Ϊ��"+String.valueOf(s3)+"%");
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