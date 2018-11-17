import java.util.ArrayList;


public class CellStack {
	
	private ArrayList<MazeCell> data;
	int size;
	public CellStack(){
		data = new ArrayList<MazeCell>();
		size = 0;
	}
	
	public MazeCell peek(){
		return data.get(size-1);
	}
	
	public MazeCell pop(){
		MazeCell ret = data.get(size-1);
		data.remove(size-1);
		size--;
		return ret;
	}
	
	public void push(MazeCell nu){
		data.add(nu);
		size++;
		
	}
	
	public int size(){return size;}
	
	public boolean isEmpty(){
		return(data.get(0)==null);
	}
}
