import java.util.*;

class Node{
	Node left;
	Node right;
	int val;

	Node(int val){
		this.val = val;
		left = null;
		right = null;
	}

	Node(int val, Node left, Node right){
		this.val = val;
		this.left = left;
		this.right = right;
	}
}

public class Tree{
	public static void main(String[] args){
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);

		Node n2 = new Node(2, n3, n4);
		Node n1 = new Node(1, n2, n5);

		//predfs(n1);
		predfsitr(n1);
	}

	public static void predfs(Node root){
		if(root == null){
			return;
		}
		System.out.println(root.val);
		predfs(root.left);
		predfs(root.right);
	}

	public static void predfsitr(Node root){
		if(root == null){
			return;
		}
		Stack<Node> stack = new Stack<Node>();
		stack.add(root);

		while(! stack.isEmpty()){
			Node top = stack.pop();
			System.out.println(top.val);

			if(top.right != null){
				stack.add(top.right);
			}

			if(top.left != null){
				stack.add(top.left);
			}
		} 
	}
}