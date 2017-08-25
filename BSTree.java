import java.util.*;

class Node{
	Node left;
	Node right;
	Node parent;
	char val;
	int level;
	int balance;

	Node(char val){
		this.val = val;
		left = null;
		right = null;
		parent = null;
		level = 1;
		balance = 0;
	}
}

public class BSTree{
	public static void main(String[] args){
		Node root = new Node('a');
		while(root.parent != null){
			root = root.parent;
		}
		add(root, 'b');
		while(root.parent != null){
			root = root.parent;
		}
		add(root, 'c');
		while(root.parent != null){
			root = root.parent;
		}
		add(root, 'd');
		while(root.parent != null){
			root = root.parent;
		}
		add(root, 'e');
		while(root.parent != null){
			root = root.parent;
		}
		add(root, 'f');
		while(root.parent != null){
			System.out.println(1);
			root = root.parent;
		}
		add(root, 'g');
		while(root.parent != null){
			root = root.parent;
		}

		add(root, 'i');
		while(root.parent != null){
			System.out.println(1);
			root = root.parent;
		}
		add(root, 'h');
		while(root.parent != null){
			root = root.parent;
		}
		print(root);
	}

	public static void add(Node root, char c){
		Node newNode = new Node(c);
		if(root == null){
			root = newNode;
			return;
		}

		while(true){
			if(root.val < c && root.right == null){
				root.right = newNode;
				newNode.parent = root;
				break;
			} else if(root.val < c){
				root = root.right;
			} else if(root.val > c && root.left == null){
				root.left = newNode;
				newNode.parent = root;
				break;
			} else {
				root = root.left;
			}
		}
		balance(newNode);
		//balance(newNode);
	}

	public static void balance(Node newNode){
		Node thisNode = newNode;
		while(newNode.parent != null){
			newNode = newNode.parent;
			//if(thisNode.val == 'd') System.out.println(newNode.val);
			int ll = newNode.left == null ? 0 : newNode.left.level;
			int rr = newNode.right == null ? 0 : newNode.right.level;
			newNode.level = Math.max(ll, rr) + 1;
			newNode.balance = ll - rr; 

			int ll_b = newNode.left == null? 0 : newNode.left.balance;
			int rr_b = newNode.right == null ? 0 : newNode.right.balance;
			if(newNode.balance >= 2 && ll_b < 0){
				rightRotate(newNode);
				break;
			} else if(newNode.balance >=2){
				leftRotate(newNode.left);
				rightRotate(newNode);
				break;
			} else if(newNode.balance <= -2 && rr_b > 0){
				//System.out.println("here");
				rightRotate(newNode.right);
				leftRotate(newNode);
				break;
			}else if(newNode.balance <= -2){
				//System.out.println("hereHere " + thisNode.val);
				leftRotate(newNode);
				break;
			}
			
		}	
	}

	public static void rightRotate(Node node){
		Node y = node.left;
		node.left = y.right;
		if(y.right != null){
			y.right.parent = node;
		}
		y.right = node;
		y.parent = node.parent;
		if(node.parent != null && node.parent.left == node){
			node.parent.left = y;
		}else if(node.parent != null && node.parent.right == node){
			node.parent.right = y;
		}
		node.parent = y;
		int ll = node.left == null ? 0 : node.left.level;
		int rr = node.right == null ? 0 : node.right.level;
		node.level = Math.max(ll, rr) + 1;
	}

	public static void leftRotate(Node node){
		Node y = node.right;
		node.right = y.left;
		if(y.left != null){
			y.left.parent = node;
		}
		y.left = node;
		y.parent = node.parent;
		if(node.parent != null && node.parent.left == node){
			node.parent.left = y;
		}else if(node.parent != null && node.parent.right == node){
			node.parent.right = y;
		}
		node.parent = y;
		int ll = node.left == null ? 0 : node.left.level;
		int rr = node.right == null ? 0 : node.right.level;
		node.level = Math.max(ll, rr) + 1;
	}

	public static void print(Node root){
		if(root == null) return;
		Node n = null;
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		queue.add(n);
		while(! queue.isEmpty()){
			Node cur = queue.poll();
			if(cur == null && ! queue.isEmpty()){
				System.out.println();
				queue.add(cur);
			}else if(cur == null){
				break;
			}else{
				String xxx ;
				if(cur.parent == null){
					xxx = "()";
				}else if(cur == cur.parent.right){
					xxx =  "(" + cur.parent.val + "_r) ";
				}else{
					xxx =  "(" + cur.parent.val + "_l) ";
				}
				
				System.out.print(cur.val + xxx + cur.level + " ");
				if(cur.left != null){
					queue.add(cur.left);
				}
				if(cur.right != null){
					queue.add(cur.right);
				}
			}
			
		}
	}
	
}