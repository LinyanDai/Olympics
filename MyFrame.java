import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFrame extends JFrame implements ActionListener{

	String show = "";
	JTextField text;
	JButton[] operator;
	JButton[] numbers;

	MyFrame(){
		setLocation(500,500);
		setLayout(new GridLayout(2, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		text = new JTextField();
		text.setEditable(false);
		add(text);
		JPanel p = new JPanel();
		add (p);

		p.setLayout(new GridLayout(1, 2));
		JPanel op = new JPanel();
		JPanel num = new JPanel();
		p.add(num);
		p.add(op);
		
		op.setLayout(new GridLayout(3, 1));
		JPanel jp1 = new JPanel(new GridLayout(1, 4));
		JPanel jp2 = new JPanel(new GridLayout(1, 3));
		JPanel jp3 = new JPanel(new GridLayout(1, 2));
		op.add(jp1);
		op.add(jp2);
		op.add(jp3);
		String[] ops = new String[]{"+", "-", "*", "/", "^", "(", ")", "Undo", "Clear All", "="};
		operator = new JButton[10];

		for(int i = 0; i < 4; i ++){
			operator[i] = new JButton(ops[i]);
			jp1.add(operator[i]);
		}
		for(int i = 4; i < 7; i ++){
			operator[i] = new JButton(ops[i]);
			jp2.add(operator[i]);
		}
		for(int i = 7; i < 9; i ++){
			operator[i] = new JButton(ops[i]);
			jp3.add(operator[i]);
		}
		operator[9] = new JButton("=");
		num.setLayout(new GridLayout(4, 3));
		numbers = new JButton[11];
		for(int i = 0; i < 10; i++){
			numbers[i] = new JButton(i + " ");
		} 
		numbers[10] = new JButton(".");
		num.add(numbers[7]);
		num.add(numbers[8]);
		num.add(numbers[9]);
		num.add(numbers[4]);
		num.add(numbers[5]);
		num.add(numbers[6]);
		num.add(numbers[1]);
		num.add(numbers[2]);
		num.add(numbers[3]);
		num.add(numbers[10]);
		num.add(numbers[0]);
		num.add(operator[9]);

		setSize(500,500);

		setVisible(true);
	}

	static ArrayList<String> validate(String exp){
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern p = Pattern.compile("(-?[0-9]+\\.?[0-9]*|\\+|-|\\*|/|\\(|\\)|\\^)");
		Matcher m = p.matcher(exp);
		while (m.find()) {
			String xxx = m.group(1);
   			tokens.add(xxx);
 		}
 		return tokens;
	}

	static ArrayList<String> getPostfix(ArrayList<String> tokens){
		ArrayList<String> postfix = new ArrayList<String>();
		Map<String, Integer> ops = new HashMap<String, Integer>();
		ops.put("+", 1);
		ops.put("-", 1);
		ops.put("*", 2);
		ops.put("/", 2);
		ops.put("^", 3);
		ops.put("(", 0);
		ops.put(")", 0);

		Stack<String> opStack = new Stack<String>();

		for(String t : tokens){
			if(! ops.containsKey(t)){
				postfix.add(t);
			} else if("(".equals(t)){
				opStack.add(t);
			} else if(")".equals(t)){
				String top = opStack.pop();
				while(! "(".equals(top)){
					postfix.add(top);
					top = opStack.pop();
				}
			} else{
				while((!opStack.isEmpty()) && ops.get(opStack.peek()) >= ops.get(t)){
					postfix.add(opStack.pop());
				}
				opStack.add(t);
			}
		}
		while(! opStack.isEmpty()){
			postfix.add(opStack.pop());
		}
		return postfix;
	}

	static Double calculatePostfix(ArrayList<String> postfix){
		Stack<Double> num = new Stack<Double>();
		Double a, b;
		for(String top : postfix){
			if("+".equals(top)){
				b = num.pop();
				a = num.pop();
				num.add(a + b);
			}else if("-".equals(top)){
				b = num.pop();
				a = num.pop();
				num.add(a - b);
			}else if("*".equals(top)){
				b = num.pop();
				a = num.pop();
				num.add(a * b);
			}else if("/".equals(top)){
				b = num.pop();
				a = num.pop();
				num.add(a / b);
			}else if("^".equals(top)){
				b = num.pop();
				a = num.pop();
				num.add(Math.pow(a, b));
			}else{
				num.add(Double.parseDouble(top));
			}
		}
		return num.pop();
	}
	
	void addAction(){
		for(int i = 0; i < numbers.length; i++){
			numbers[i].addActionListener(this);
		}

		for(int i = 0; i < operator.length; i++){
			operator[i].addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent e) { 
    	String ch = e.getActionCommand().trim();
    	if("Undo".equals(ch)){
    		show = show.substring(0, show.length() - 1);
    		text.setText(show);
    	} else if("Clear All".equals(ch)){
    		show = "";
    		text.setText(show);
    	} else if("=".equals(ch)){
    		show = calculate(show);
    		text.setText(show);
    		show = "";
    	} else{
    		show = show + ch;
    		text.setText(show);
    	}
    } 

    String calculate(String str){
    	ArrayList<String> p = getPostfix(validate(str.trim()));
    	return Double.toString(calculatePostfix(p));
    }

  	public static void main(String[] args){
		MyFrame f = new MyFrame();
		f.addAction();
	}
}