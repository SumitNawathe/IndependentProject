import java.util.*;

public class Level {
	private Tile[][] tileMap;
	private ArrayList<GameObject> objectArray;
	private int collectibleQuota;
	private ArrayList<PVector> spawn;
	
	public Tile[][] getTileMap () {
		return tileMap;
	}
	
	public ArrayList<PVector> getSpawn () {
		return spawn;
	}
	
	public int getCollectibleQuota () {
		return collectibleQuota;
	}
	
	public ArrayList<GameObject> getObjectArray () {
		return objectArray;
	}
	
	public Level (char[][] tileCharMap, GameObject[] extraGameObjects) {
		spawn = new ArrayList<PVector>();
		int collectibleQuota = 0;
		objectArray = new ArrayList<GameObject>();
		this.collectibleQuota = collectibleQuota;
		tileMap = new Tile[tileCharMap.length][tileCharMap[0].length];
		for (int x = 0; x < tileMap[0].length; x++)
			for (int y = 0; y < tileMap.length; y++) {
				switch (tileCharMap[y][x]) {
					case 1: //ground
						tileMap[y][x] = new GroundTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						break;
					case 2: //coin
						tileMap[y][x] = new AirTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						objectArray.add(new Collectible(x*GamePanel.tileSize + GamePanel.tileSize/2, 
								y*GamePanel.tileSize + GamePanel.tileSize/2));
						collectibleQuota++;
						break;
					case 3: //spring
						tileMap[y][x] = new GroundTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						objectArray.add(new Spring(x*GamePanel.tileSize, y*GamePanel.tileSize));
						break;
					case 4: //spawn
						tileMap[y][x] = new AirTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						spawn.add(new PVector(x*GamePanel.tileSize, y*GamePanel.tileSize));
						break;
					case 5: //walljump
						tileMap[y][x] = new GroundTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						objectArray.add(new WallJump(x*GamePanel.tileSize, y*GamePanel.tileSize));
						break;
					case 6: //lava
						tileMap[y][x] = new AirTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						objectArray.add(new Lava(x*GamePanel.tileSize, y*GamePanel.tileSize));
						break;
					case 7: //ice
						tileMap[y][x] = new IceTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						break;
					case 8: //door L
						tileMap[y][x] = new AirTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						objectArray.add(new DoorL(x*GamePanel.tileSize, y*GamePanel.tileSize));
						break;
					case 9:
						tileMap[y][x] = new AirTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						objectArray.add(new DoorR(x*GamePanel.tileSize, y*GamePanel.tileSize));
						break;
					default: //air
						tileMap[y][x] = new AirTile(x*GamePanel.tileSize, y*GamePanel.tileSize);
						break;
				}
			}
		this.collectibleQuota = collectibleQuota;
		if (extraGameObjects != null)
			for(GameObject go : extraGameObjects)
				objectArray.add(go);
	}
}