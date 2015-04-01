package oleg.kilimnik.spacemultiplier.buffs;

import oleg.kilimnik.spacemultiplier.ships.Ship;

public class BuffTimer {
	
	public float timer = 5; 
	public int type = 0;
	private Ship ship;
	
	public BuffTimer(int type, Ship ship) {
		this.type = type;
		this.ship = ship;
	}

	public void unbuff() {
		switch (type) {
		case 1:
			ship.velocity-=100;
			break;
		case 2:
			ship.power/=4;
			break;
		case 3:
			ship.setAlpha(1);
			ship.isStealthy = false;
			break;
		case 4:
			ship.armored = false;
			break;
		}
	}

}
