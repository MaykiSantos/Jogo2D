package ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.solitario.word.Tile;
import com.solitario.word.TileParede;
import com.solitario.word.Word;

public class AStar_old {
	/*
	public static double lastTime = System.currentTimeMillis();
	private static Comparator<Node> nodeSorter = new Comparator<Node>() {
		
		@Override
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost)
				return +1;
			if(n1.fCost > n0.fCost)
				return -1;
			return 0;
		}
	};
	
	public static boolean clear() {
		if(System.currentTimeMillis() - lastTime >= 1000)
			return true;
		return false;
	}
	
	public static List<Node> acharCamnho(Word word, Vector2i start, Vector2i end){
		AStar.lastTime = System.currentTimeMillis();
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		
		Node current = new Node(start, null, 0, AStar.getDistance(start, end));
		openList.add(current);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(end)) {
				//chegameos ao ponto final
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			
			for(int i =0; i<9; i++) {
				if(i == 4)
					continue;
				int x = current.tile.x;
				int y = current.tile.y;
				int xi = (i%3) - 1;
				int yi = (i/3) - 1;
				System.out.println(Word.tiles.length);
				Tile tile = word.tiles[x+xi+((y+yi)*word.WIDTH)];
				if(tile == null)
					continue;
				if(tile instanceof TileParede)
					continue;
				if(i == 0) {
					Tile test = word.tiles[x+xi+1+((y+yi)*word.WIDTH)];
					Tile test2 = word.tiles[x+xi+1+((y+yi)*word.WIDTH)];
					if(test instanceof TileParede || test2 instanceof TileParede)
						continue;
				}else if(i == 2) {
					Tile test = word.tiles[x+xi+1+((y+yi)*word.WIDTH)];
					Tile test2 = word.tiles[x+xi+((y+yi)*word.WIDTH)];
					if(test instanceof TileParede || test2 instanceof TileParede)
						continue;
				}else if(i == 6) {
					Tile test = word.tiles[x+xi+((y+yi-1)*word.WIDTH)];
					Tile test2 = word.tiles[x+xi+1+((y+yi)*word.WIDTH)];
					if(test instanceof TileParede || test2 instanceof TileParede)
						continue;
				}else if(i == 8) {
					Tile test = word.tiles[x+xi+((y+yi-1)*word.WIDTH)];
					Tile test2 = word.tiles[x+xi-1+((y+yi)*word.WIDTH)];
					if(test instanceof TileParede || test2 instanceof TileParede)
						continue;
				}
				
				Vector2i a = new Vector2i(x+xi, y+yi);
				double gCost = current.gCost + AStar.getDistance(current.tile, a);
				double hCost = AStar.getDistance(a, end);
				
				Node node = new Node(a, current, gCost, hCost);
				if(AStar.vecInList(closedList, a) && gCost >= current.gCost)
					continue;
				
				if(!AStar.vecInList(openList, a))
					openList.add(node);
				else if(gCost < current.gCost) {
					openList.remove(current);
					openList.add(node);
				}
			}
		}
		closedList.clear();
		return null;
	}
	
	private static double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.x - goal.x;
		double dy = tile.y - goal.y;
		
		return Math.sqrt((dx*dx)+(dy*dy));
	}
	
	private static boolean vecInList(List<Node> list, Vector2i vector) {
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).equals(vector))
				return true;
		}
		return false;
	}
	
*/
}
