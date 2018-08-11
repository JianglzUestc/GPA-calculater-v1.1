import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//尝试实现输入框的快速清除功能。尝试范围超出提醒。尝试提交次数提醒。尝试清空输入。
public class GPA implements ActionListener
{
	private float CGPA,AGPA;
	private int tip = 0;
	JTextField creditText;
	JFrame frame;
	JTextField CGPAText;
	JTextField AGPAText;
	JTextField pointText;
	JRadioButton radioButton1;
	JRadioButton radioButton2;
	JRadioButton radioButton3;
	JRadioButton radioButton4;
	MyDrawPanel panel;
	Calculator cal = new Calculator();
	public static void main(String[] args)
	{
		GPA gui = new GPA();
		gui.placeComponents();
	}
	public void placeComponents()
	{
		frame = new JFrame("GPA Calculator by JLZ");		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("k17.png"));
		frame.setIconImage(imageIcon.getImage());
		
		panel = new MyDrawPanel();
		frame.add(panel);
		panel.setLayout(null);   //这句保证布局
		//输入提示显示
		JLabel tipText = new JLabel("*每输入一科成绩提交一次");
		tipText.setFont(new Font("宋体",Font.BOLD, 20));
		tipText.setBounds(10,180,250,25);
		panel.add(tipText);
		//学分及输入框显示
		JLabel creditLabel = new JLabel("Credit(s):");
		creditLabel.setFont(new Font("宋体",Font.BOLD, 20));
		creditLabel.setBounds(300,20,120,25);
		panel.add(creditLabel);
		creditText = new JTextField();
		creditText.setBounds(460,20,80,30);
		creditText.setFont(new Font("宋体",Font.BOLD, 18));
		panel.add(creditText);
        //成绩及输入框显示
		JLabel pointLabel = new JLabel("Grade(s):");
		pointLabel.setFont(new Font("宋体",Font.BOLD, 20));
		pointLabel.setBounds(300,60,120,25);
		panel.add(pointLabel);
		pointText = new JTextField();
		pointText.setBounds(460,60,80,30);
		pointText.setFont(new Font("宋体",Font.BOLD, 18));
		panel.add(pointText);
		//当前科目绩点显示
		JLabel CGPALabel = new JLabel("Current GPA:");
		CGPALabel.setFont(new Font("宋体",Font.BOLD, 20));
		CGPALabel.setBounds(300,100,150,25);
		panel.add(CGPALabel);
		CGPAText = new JTextField();
		CGPAText.setBounds(460,100,80,30);
		CGPAText.setFont(new Font("宋体",Font.BOLD, 18));
		CGPAText.setEditable(false);
		panel.add(CGPAText);
		//总绩点显示
		JLabel AGPALabel = new JLabel("Average GPA:");
		AGPALabel.setFont(new Font("宋体",Font.BOLD, 20));
		AGPALabel.setBounds(300,140,150,25);
		panel.add(AGPALabel);
		AGPAText = new JTextField();
		AGPAText.setBounds(460,140,80,30);
		AGPAText.setFont(new Font("宋体",Font.BOLD, 18));
		AGPAText.setEditable(false);
		panel.add(AGPAText);
		//创建单选按钮（多个）
		radioButton1 = new JRadioButton("浙大/电子科大算法",true);
		radioButton2 = new JRadioButton("标准算法");
		radioButton3 = new JRadioButton("北大算法");
		radioButton4 = new JRadioButton("平均分算法");
		radioButton1.setFont(new Font("宋体",Font.BOLD, 20));
		radioButton2.setFont(new Font("宋体",Font.BOLD, 20));
		radioButton3.setFont(new Font("宋体",Font.BOLD, 20));
		radioButton4.setFont(new Font("宋体",Font.BOLD, 20));
		radioButton1.setBounds(10,20,210,25);
		radioButton2.setBounds(10,60,110,25);
		radioButton3.setBounds(10,100,110,25);
		radioButton4.setBounds(10,140,130,25);
		//除掉背景色
		radioButton1.setContentAreaFilled(false);
		radioButton2.setContentAreaFilled(false);
		radioButton3.setContentAreaFilled(false);
		radioButton4.setContentAreaFilled(false);
		//除掉选中框
		radioButton1.setFocusPainted(false);
		radioButton2.setFocusPainted(false);
		radioButton3.setFocusPainted(false);
		radioButton4.setFocusPainted(false);
		panel.add(radioButton1);
        panel.add(radioButton2);
		panel.add(radioButton3);
		panel.add(radioButton4);
		ButtonGroup group = new ButtonGroup();
		group.add(radioButton1);
		group.add(radioButton2);
		group.add(radioButton3);
		group.add(radioButton4);
		//添加提交按钮
		JButton loginButton = new JButton("提交");
		loginButton.setFont(new Font("宋体",Font.BOLD, 20));
		loginButton.setBounds(380,180,80,30);
		panel.add(loginButton);
		loginButton.addActionListener(this);
        
        frame.setSize(600,350);
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent event)
	{
		//前面还缺少对JRadioButton Group的监听
		
		//取得学分和分数并做转换
		String credit = new String();
		credit = creditText.getText();
		int creditNum = Integer.parseInt(credit);
		String grade = new String();
		grade = pointText.getText();
		int gradeNum = Integer.parseInt(grade);

//      Calculator cal = new Calculator();
		cal.setInputCredit(creditNum);
		cal.setInputGrade(gradeNum);
		if(radioButton1.isSelected()) CGPA = cal.getCGPA_Uestc();
		else if(radioButton2.isSelected()) CGPA = cal.getCGPA_Normal();		
		else if(radioButton3.isSelected()) CGPA = cal.getCGPA_PKU();
		else CGPA = cal.getCGPA_Average();
		AGPA = cal.getAGPA();
		CGPAText.setText(String.valueOf(CGPA));
		AGPAText.setText(String.valueOf(AGPA));
	}
}

class MyDrawPanel extends JPanel
{
		ImageIcon icon;
		Image img;
		MyDrawPanel()
		{
			icon = new ImageIcon(getClass().getResource("back3.jpg"));
			img = icon.getImage();
		}
		public void paintComponent(Graphics g) //此函数名称是固定的。
		{
			super.paintComponent(g);
			g.drawImage(img,0,0,600,400,this);
		}
}

class Calculator
{
	private int inputCredit,inputGrade,totalCredit = 0;
	private float outputCGPA,outputAGPA = 0.0f,num = 0.0f;

	public void setInputCredit(int i)
	{
		inputCredit = i;
	}
	public int getInputCredit()
	{
		return inputCredit;
	}
	public void setInputGrade(int i)
	{
		inputGrade = i;
	}
	public int getInputGrade()
	{
		return inputGrade;
	}
	public float getOutputCGPA()
	{
		return outputCGPA;
	}
	public float getOutputAGPA()
	{
		return outputAGPA;
	}
	public float getCGPA_Uestc()
	{
		if(inputGrade>=60&&inputGrade<85)
			switch(inputGrade)
			{
				case 84 : outputCGPA = 3.9f; break;
				case 83 : outputCGPA = 3.8f; break;
				case 82 : outputCGPA = 3.7f; break;
				case 81 : outputCGPA = 3.6f; break;
				case 80 : outputCGPA = 3.5f; break;
				case 79 : outputCGPA = 3.4f; break;
				case 78 : outputCGPA = 3.3f; break;
				case 77 : outputCGPA = 3.2f; break;
				case 76 : outputCGPA = 3.1f; break;
				case 75 : outputCGPA = 3.0f; break;
				case 74 : outputCGPA = 2.9f; break;
				case 73 : outputCGPA = 2.8f; break;
				case 72 : outputCGPA = 2.7f; break;
				case 71 : outputCGPA = 2.6f; break;
				case 70 : outputCGPA = 2.5f; break;
				case 69 : outputCGPA = 2.4f; break;
				case 68 : outputCGPA = 2.3f; break;
				case 67 : outputCGPA = 2.2f; break;
				case 66 : outputCGPA = 2.1f; break;
				case 65 : outputCGPA = 2.0f; break;
				case 64 : outputCGPA = 1.9f; break;
				case 63 : outputCGPA = 1.8f; break;
				case 62 : outputCGPA = 1.7f; break;
				case 61 : outputCGPA = 1.6f; break;
				case 60 : outputCGPA = 1.5f; break;
			}
		else if(inputGrade>=85) outputCGPA = 4.0f;
		else if(inputGrade<60) outputCGPA = 0.0f;
		return outputCGPA;
	}
	public float getCGPA_Normal()
	{
		if(inputGrade<=100&&inputGrade>=90) outputCGPA = 4.0f;
		else if(inputGrade<90&&inputGrade>=80) outputCGPA = 3.0f;
		else if(inputGrade<80&&inputGrade>=70) outputCGPA = 2.0f;
		else if(inputGrade<70&&inputGrade>=60) outputCGPA = 1.0f;
		else outputCGPA = 0.0f;
		return outputCGPA;
	}
	public float getCGPA_PKU()
	{
		if(inputGrade<=100&&inputGrade>=90) outputCGPA = 4.0f;
		else if(inputGrade<90&&inputGrade>=85) outputCGPA = 3.7f;
		else if(inputGrade<85&&inputGrade>=82) outputCGPA = 3.3f;
		else if(inputGrade<82&&inputGrade>=78) outputCGPA = 3.0f;
		else if(inputGrade<78&&inputGrade>=75) outputCGPA = 2.7f;
		else if(inputGrade<75&&inputGrade>=72) outputCGPA = 2.3f;
		else if(inputGrade<72&&inputGrade>=68) outputCGPA = 2.0f;
		else if(inputGrade<68&&inputGrade>=64) outputCGPA = 1.5f;
		else if(inputGrade<63&&inputGrade>=60) outputCGPA = 1.0f;
		else outputCGPA = 0.0f;
		return outputCGPA;
	}
	public float getCGPA_Average()
	{
		outputCGPA = ((float)inputGrade/100)*4;   //这里要先把inputGrade转化成浮点型才可。
		return outputCGPA;
	}
	public float getAGPA()
	{
		totalCredit = totalCredit + inputCredit;
		num = num + inputCredit*outputCGPA;
		outputAGPA = num/totalCredit;
		outputAGPA = (float)(Math.round(outputAGPA*100))/100;
		return outputAGPA;
	} 
}