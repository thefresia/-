

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


class CalTest {		
	    public void random() throws IOException {
		CalTest test = new CalTest();
        
        
		File writename = new File("suanshi.txt");// ���·�������û����Ҫ����һ���µ�result��
		 writename.createNewFile(); // �������ļ�
		 File name = new File("answer.txt");//
		 name.createNewFile(); // �������ļ�
        BufferedWriter out1 = new BufferedWriter(new FileWriter(writename));
        BufferedWriter out2 = new BufferedWriter(new FileWriter(name));

		for(int i=0;i<20;i++)
		{
			int a=(int)(Math.random()*100);
			int b=(int)(Math.random()*100);
			int a1=(int)(Math.random()*100);
			int b1=(int)(Math.random()*100);
			int c=(int)(Math.random()*4);
			switch(c)
			{
			    case 0:
			    	a1= (int) (Math.random()*100 + 0);
			        b1= (int) (Math.random()*100 + 1);
			            
			        if(a1%b1==0)
			    	{
			    		out1.write(a+"+"+b+"+"+a1+"/"+b1+"="+"\r\n");
			    		out2.write(a+"+"+b+"+"+a1+"/"+b1+"="+(a+b+a1/b1)+"\r\n");
			    	}
			    	else
			    		i--;
			    	break;
			    case 1:
			    	if(a<b)
			    	{
			    		int temp=a;
			    		a=b;
			    		b=temp;
			    	}
			    	out1.write("("+a+"-"+b+")"+"+"+a1+"��"+b1+"="+"\r\n");
			    	out2.write("("+a+"-"+b+")"+"+"+a1+"��"+b1+"="+((a-b)+a1*b1)+"\r\n");
			    	break;
			    case 2:
			    	out1.write(a + "��" + b+"+" +a1+"+ "+b1+" = "+"\r\n");
			    	out2.write(a + "��" + b+"+" +a1+"+ "+b1+" = "+(a*b+a1+b1)+"\r\n");
			    	break;
			    case 3:
			    	a = (int) (Math.random()*100 + 0);
			        b = (int) (Math.random()*100 + 1);
			    	if(a%b==0)
			    	{
			    		if(a/b+a1>=b1)
			    		{
			    			out1.write(a + "/" + b +"+"+ a1+"+"+b1+"=" +"\r\n");
			    			out2.write(a + "/" + b +"+"+ a1+"+"+b1+"=" +(a/b+a1+b1)+"\r\n");
			    		}
			    		else {
			    			i--;
			    		}			    				 
			    	}
			    	else
			    		i--;
			    	break;
			  }
	          out1.flush(); // �ѻ���������ѹ���ļ�
	          out2.flush(); // �ѻ���������ѹ���ļ�
		}
	}
}
