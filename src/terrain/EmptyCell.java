package terrain;

public class EmptyCell extends Cell{

	public EmptyCell(int id) {
		super(null, id);
	}

	@Override
	public boolean isSolid(){
		return false;
	}
}
