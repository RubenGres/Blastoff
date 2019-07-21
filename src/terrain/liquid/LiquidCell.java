package terrain.liquid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Assets;
import map.GameWorld;
import terrain.Cell;
import terrain.EmptyCell;
import main.Handler;

public abstract class LiquidCell extends EmptyCell {

	public float viscuosity;
	public byte level;
	public int x, y;

	public LiquidCell(int x, int y, Color color, int id, float viscuosity) {
		super(id);
		this.color = Color.RED;
		this.viscuosity = viscuosity;
		this.level = Byte.MAX_VALUE;
		this.x = x;
		this.y = y;
		Handler.getInstance().getWorld().setCell(x, y, this);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public float getViscuosity() {
		return viscuosity;
	}

	@Override
	public void render(Graphics g, int displayX, int displayY, int x, int y) {
		if (this.level == 0)
			System.out.println("lave vide !!");

		g.setColor(this.color);
		int levelHeight = (int) (CELLHEIGHT * ((float) this.level / Byte.MAX_VALUE));
		g.fillRect(displayX, displayY + CELLHEIGHT - levelHeight, CELLWIDTH, levelHeight);
	}

	public void flow() {

		GameWorld map = Handler.getInstance().getWorld();

		/**************************/
		/* ECOULEMENT VERS LE BAS */
		/**************************/

		/* si la cellule du bas est vide, tout le liquide y coule */
		if (map.getCell(x, y + 1) == Cell.emptyCell) {
			LavaCell newCell = new LavaCell(x, y + 1);
			newCell.level = this.level;
			map.setCell(x, y, Cell.emptyCell);
		}

		/*
		 * si la cellule du bas peut accueillir plus de liquide, elle se remplit au
		 * maximum
		 */
		else if (map.getCell(x, y + 1) instanceof LiquidCell
				&& ((LiquidCell) map.getCell(x, y + 1)).level != Byte.MAX_VALUE) {
			LiquidCell downCell = (LiquidCell) map.getCell(x, y + 1);

			int sum = downCell.level + this.level;

			if (sum > Byte.MAX_VALUE) {
				downCell.level = Byte.MAX_VALUE;
				this.level = (byte) (sum - Byte.MAX_VALUE);
			} else {
				downCell.level = (byte) sum;
				map.setCell(x, y, Cell.emptyCell);
			}
		}

		/******************************************/
		/* ECOULEMENT VERS LA GAUCHE ET LA DROITE */
		/******************************************/

		else {
			Cell leftCell = map.getCell(x - 1, y);
			Cell rightCell = map.getCell(x + 1, y);
			
			LiquidCell leftLiquid = null, rightLiquid = null;
			if(leftCell instanceof LiquidCell)
				leftLiquid = (LiquidCell) leftCell;
			
			if(rightCell instanceof LiquidCell)
				rightLiquid = (LiquidCell) rightCell;
			
			boolean canFlowLeft = leftLiquid != null && leftLiquid.level != Byte.MAX_VALUE && leftLiquid.level < this.level;
			boolean canFlowRight = rightLiquid != null &&rightLiquid.level != Byte.MAX_VALUE && rightLiquid.level < this.level;

			/* si les deux cellules adjacentes sont des cellules liquides */
			if (canFlowRight && canFlowLeft || canFlowRight && leftCell == Cell.emptyCell || rightCell == Cell.emptyCell && canFlowLeft || rightCell == leftCell && leftCell == Cell.emptyCell) {				
				
				//crée des cellules de lave si besoin
				if(leftLiquid == null) {
					leftLiquid = new LavaCell(x-1,y);
					leftLiquid.level = 0;
				}
				
				if(rightLiquid == null) {
					rightLiquid = new LavaCell(x+1,y);
					rightLiquid.level = 0;
				}
				
				int sum = this.level + rightLiquid.level + leftLiquid.level;
				byte newLevel = (byte) (sum/3);
				
				leftLiquid.level = newLevel;
				rightLiquid.level = newLevel;
				this.level = newLevel;
				
				this.level += sum % 3;
			}

			/*****************************/
			/* ECOULEMENT VERS LA GAUCHE */
			/*****************************/

			/* si la cellule a gauche est vide le liquide y coule de moitié */
			else if (this.level > 1 && leftCell == Cell.emptyCell) {
				LavaCell newCell = new LavaCell(x - 1, y);
				int oldlevel = this.level;
				newCell.level = (byte) (this.level / 2);
				this.level = (byte) (this.level / 2);
				this.level += oldlevel % 2;
			}

			/*
			 * si la cellule de gauche est une cellule de liquide le liquide se répend
			 * equitablement
			 */
			else if (canFlowLeft) {
				int total = this.level + leftLiquid.level;
				byte moyenne = (byte) (total / 2);
				leftLiquid.level = moyenne;
				this.level = moyenne;				
				this.level += total % 2;
			}

			/*****************************/
			/* ECOULEMENT VERS LA DROITE */
			/*****************************/

			/* si la cellule a droite est vide le liquide y coule de moitié */
			else if (this.level > 1 && map.getCell(x + 1, y) == Cell.emptyCell) {
				LavaCell newCell = new LavaCell(x + 1, y);
				int oldlevel = this.level;
				newCell.level = (byte) (this.level / 2);
				this.level = (byte) (this.level / 2);
				this.level += oldlevel % 2;
			}

			/*
			 * si la cellule de gauche est une cellule de liquide le liquide se répend
			 * equitablement
			 */
			else if (canFlowRight) {
				int total = this.level + rightLiquid.level;
				byte moyenne = (byte) (total / 2);
				rightLiquid.level = moyenne;
				this.level = moyenne;
				this.level += total % 2;
			}

		}
	}

}