import java.util.Random;

class SkipListNode{
	public String element;
	public SkipListNode[] next;
	public int l = 0;

	public SkipListNode(String e){
		element = e;
		Random r = new Random();
		l = 1;
		while (r.nextFloat() < 0.5){
			l++;
		}
		if (l > 5){
			l = 5;
		}
		next = new SkipListNode[l];
	}
}

