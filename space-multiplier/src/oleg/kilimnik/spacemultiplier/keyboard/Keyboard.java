package oleg.kilimnik.spacemultiplier.keyboard;

import oleg.kilimnik.spacemultiplier.Constants;
import oleg.kilimnik.spacemultiplier.GameInstance;
import oleg.kilimnik.spacemultiplier.Resources;
import oleg.kilimnik.spacemultiplier.buffs.Asteroid;
import oleg.kilimnik.spacemultiplier.particles.Laser;
import oleg.kilimnik.spacemultiplier.ships.Ship;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;

public class Keyboard extends Group {
	
	private ImageButton num0;
	private ImageButton num1;
	private ImageButton num2;
	private ImageButton num3;
	private ImageButton num4;
	private ImageButton num5;
	private ImageButton num6;
	private ImageButton num7;
	private ImageButton num8;
	private ImageButton num9;
	public ImageButton shoot;
	private Skin skin;
	private final int width = Constants.VIRTUAL_WIDTH;
	private final int height = Constants.VIRTUAL_HEIGHT;
	private final int numsSize = Constants.numsSize;
	private final float cellSize = Constants.gridSize/9;
	private final float halfCell = cellSize/2;
	private final Vector2 gridCoords = new Vector2(0, height/2-Constants.gridSize/2);
	public String NUMS = "";
	private Array<Vector2> targets = new Array<Vector2>();
	private Sound shootSound;
	private Sound buttonSound;
	private Sound falseShot;
	private final int w;
	private final int h;
	private final int trans;
	private Ship player;
	private Ship enemy;
	private String playerName = "";
	private int type;
	private boolean musicOn;
	private int shootImage = 100000;
	
	public Keyboard(int type) {
		
		this.type = type;
		
		musicOn = GameInstance.getInstance().sound;
		skin = new Skin();
		skin.addRegions(Resources.getInstance().atlas);

		
		if(type==0) {
			playerName = "P1";
			w = 0;
			h = 0;
			trans = 1;
			shootSound = Resources.getInstance().shootSoundP1;
			buttonSound = Resources.getInstance().buttonSoundP1;
			falseShot = Resources.getInstance().falseShootP1;
			player = GameInstance.getInstance().ships.get(0);
			enemy = GameInstance.getInstance().ships.get(1);
			shoot = new ShootButtonP1(skin.getDrawable("shootButtons/P1/100000"), skin.getDrawable("shootButtons/P1/100012"), 96, 128);
			shoot.setPosition(width-80, 0); 
			shoot.addAction(Actions.forever(Actions.delay(0.05f, Actions.run(new Runnable() {

				@Override
				public void run() {
					shootImage++;
					shoot.setStyle(new ImageButtonStyle(null, null, null, skin.getDrawable(String.valueOf("shootButtons/P1/"+shootImage)), skin.getDrawable("shootButtons/P1/"+"100012"), null));
					if(shootImage==100015) shootImage=99999;
				}
				
			}))));
			
		} else {
			playerName = "P2";
			w = width;
			h = height;
			trans = -1;
			shootSound = Resources.getInstance().shootSoundP2;
			buttonSound = Resources.getInstance().buttonSoundP2;
			falseShot = Resources.getInstance().falseShootP2;
			player = GameInstance.getInstance().ships.get(1);
			enemy = GameInstance.getInstance().ships.get(0);
			shoot = new ShootButtonP2(skin.getDrawable("shootButtons/P2/"+"100000"), skin.getDrawable("shootButtons/P2/"+"100008"), 96, 128);
			shoot.setPosition(-16, height-128); 
			shoot.addAction(Actions.forever(Actions.delay(0.05f, Actions.run(new Runnable() {

				@Override
				public void run() {
					shootImage++;	
					shoot.setStyle(new ImageButtonStyle(null, null, null, skin.getDrawable("shootButtons/P2/"+String.valueOf(shootImage)), skin.getDrawable("shootButtons/P2/"+"100008"), null));
					if(shootImage==100015) shootImage=99999;
				}
				
			}))));

		}
	
		num0 = new ImageButton(skin.getDrawable("0"+playerName), skin.getDrawable("0"+playerName+"Pressed"));
		num1 = new ImageButton(skin.getDrawable("1"+playerName), skin.getDrawable("1"+playerName+"Pressed"));
		num2 = new ImageButton(skin.getDrawable("2"+playerName), skin.getDrawable("2"+playerName+"Pressed"));
		num3 = new ImageButton(skin.getDrawable("3"+playerName), skin.getDrawable("3"+playerName+"Pressed"));
		num4 = new ImageButton(skin.getDrawable("4"+playerName), skin.getDrawable("4"+playerName+"Pressed"));
		num5 = new ImageButton(skin.getDrawable("5"+playerName), skin.getDrawable("5"+playerName+"Pressed"));
		num6 = new ImageButton(skin.getDrawable("6"+playerName), skin.getDrawable("6"+playerName+"Pressed"));
		num7 = new ImageButton(skin.getDrawable("7"+playerName), skin.getDrawable("7"+playerName+"Pressed"));
		num8 = new ImageButton(skin.getDrawable("8"+playerName), skin.getDrawable("8"+playerName+"Pressed"));
		num9 = new ImageButton(skin.getDrawable("9"+playerName), skin.getDrawable("9"+playerName+"Pressed"));
		
		if(type==0) {
			num9.setPosition(width-64-55, 0);
		} else {
			num9.setPosition(64, height-64);
		}
		
		num0.setWidth(55);
		num1.setWidth(55);
		num2.setWidth(55);
		num3.setWidth(55);
		num4.setWidth(55);
		num5.setWidth(55);
		num6.setWidth(55);
		num7.setWidth(55);
		num8.setWidth(55);
		num9.setWidth(55);
		
		num8.setPosition(num9.getX()-55*trans, num9.getY());	
		num7.setPosition(num8.getX()-55*trans, num9.getY());
		num6.setPosition(num7.getX()-55*trans, num9.getY());
		num5.setPosition(num6.getX()-55*trans, num9.getY()); 
		num4.setPosition(num9.getX(), num9.getY()+numsSize*trans);
		num3.setPosition(num4.getX()-55*trans, num4.getY());
		num2.setPosition(num3.getX()-55*trans, num4.getY());
		num1.setPosition(num2.getX()-55*trans, num4.getY());
		num0.setPosition(num1.getX()-55*trans, num4.getY());
	
		num0.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "0";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num1.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "1";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num2.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "2";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num3.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "3";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num4.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "4";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num5.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "5";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num6.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "6";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num7.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "7";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num8.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "8";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});
		num9.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				NUMS += "9";
				if(musicOn)buttonSound.play();
				return false;				
			}
		});

		
		shoot.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {		
					if(NUMS.equals("") || NUMS.length()>2) {
						if(musicOn)falseShot.play();
					} else {
						shoot(NUMS);
					}
					NUMS = "";
					return false;
				}
		});

		addActor(shoot);
		addActor(num0);
		addActor(num1);
		addActor(num2);
		addActor(num3);
		addActor(num4);
		addActor(num5);
		addActor(num6);
		addActor(num7);
		addActor(num8);
		addActor(num9);	
		
	}

	public void shoot(String nums) {
		int n = Integer.parseInt(nums);
		switch (n) {
			case 1:
				targets.add(new Vector2(w+halfCell*trans, h+(gridCoords.y+halfCell)*trans));
				for(Vector2 target : targets) {
					GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
					if(Math.abs(enemy.position.x+32-target.x)<48 && 
							Math.abs(enemy.position.y+32-target.y)<48) {
						enemy.damage(player.power);
					}
					for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
						if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
								Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
							player.buff(asteroid); 
						}
					}
				}
				targets.clear();
				if(musicOn)shootSound.play();
				break;
			case 2:
				targets.add(new Vector2(w+trans*halfCell, h+(gridCoords.y+halfCell*3)*trans));
				targets.add(new Vector2(w+trans*halfCell*3, h+(gridCoords.y+halfCell)*trans));
				for(Vector2 target : targets) {
					GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
					if(Math.abs(enemy.position.x+32-target.x)<48 && 
							Math.abs(enemy.position.y+32-target.y)<48) {
						enemy.damage(player.power);
					}
					for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
						if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
								Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
							player.buff(asteroid); 
						}
					}
				}
				targets.clear();
				if(musicOn)shootSound.play();
				break;
			case 3:
						targets.add(new Vector2(w+trans*halfCell, h+(gridCoords.y+halfCell*5)*trans));
						targets.add(new Vector2(w+trans*halfCell*5, h+(gridCoords.y+halfCell)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 4:
						targets.add(new Vector2(w+trans*halfCell, h+(gridCoords.y+halfCell*7)*trans));
						targets.add(new Vector2(w+trans*halfCell*7, h+(gridCoords.y+halfCell)*trans));
						targets.add(new Vector2(w+trans*halfCell*3,h+(gridCoords.y+halfCell*3)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 5:
						targets.add(new Vector2(w+trans*halfCell, h+(gridCoords.y+halfCell*9)*trans));
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 6:
						targets.add(new Vector2(w+trans*halfCell, h+(gridCoords.y+halfCell*11)*trans));
						targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell)*trans));
						targets.add(new Vector2(w+trans*halfCell*3,h+(gridCoords.y+halfCell*5)*trans));
						targets.add(new Vector2(w+trans*halfCell*5,h+(gridCoords.y+halfCell*3)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 7:
						targets.add(new Vector2(w+trans*halfCell, h+(gridCoords.y+halfCell*13)*trans));
						targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 8:
						targets.add(new Vector2(w+trans*halfCell, h+(gridCoords.y+halfCell*15)*trans));
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell)*trans));
						targets.add(new Vector2(w+trans*halfCell*3,h+(gridCoords.y+halfCell*7)*trans));
						targets.add(new Vector2(w+trans*halfCell*7,h+(gridCoords.y+halfCell*3)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 9:
						targets.add(new Vector2(w+trans*halfCell, h+(gridCoords.y+halfCell*17)*trans));
						targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell)*trans));
						targets.add(new Vector2(w+trans*halfCell*5,h+(gridCoords.y+halfCell*5)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 10:
						targets.add(new Vector2(w+trans*halfCell*3, h+(gridCoords.y+halfCell*9)*trans));
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell*3)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 12:
						targets.add(new Vector2(w+trans*halfCell*3, h+(gridCoords.y+halfCell*11)*trans));
						targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell*3)*trans));
						targets.add(new Vector2(w+trans*halfCell*5,h+(gridCoords.y+halfCell*7)*trans));
						targets.add(new Vector2(w+trans*halfCell*7,h+(gridCoords.y+halfCell*5)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear(); if(musicOn)shootSound.play();
				break;
			case 14:
						targets.add(new Vector2(w+trans*halfCell*3, h+(gridCoords.y+halfCell*13)*trans));
						targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell*3)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 15:
						targets.add(new Vector2(w+trans*halfCell*5, h+(gridCoords.y+halfCell*9)*trans));
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell*5)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 16:
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell*3)*trans));
						targets.add(new Vector2(w+trans*halfCell*3, h+(gridCoords.y+halfCell*15)*trans));
						targets.add(new Vector2(w+trans*halfCell*7,h+(gridCoords.y+halfCell*7)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear(); if(musicOn)shootSound.play();
				break;
			case 18:
						targets.add(new Vector2(w+trans*halfCell*3, h+(gridCoords.y+halfCell*17)*trans));
						targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell*3)*trans));
						targets.add(new Vector2(w+trans*halfCell*5, h+(gridCoords.y+halfCell*11)*trans));
						targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell*5)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 20:
						targets.add(new Vector2(w+trans*halfCell*7, h+(gridCoords.y+halfCell*9)*trans));
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell*7)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 21:
						targets.add(new Vector2(w+trans*halfCell*5, h+(gridCoords.y+halfCell*13)*trans));
						targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell*5)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 24:
						targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell*7)*trans));
						targets.add(new Vector2(w+trans*halfCell*7, h+(gridCoords.y+halfCell*11)*trans));
						targets.add(new Vector2(w+trans*halfCell*5, h+(gridCoords.y+halfCell*15)*trans));
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell*5)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear(); if(musicOn)shootSound.play();
				break;
			case 25:
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell*9)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 27:
					targets.add(new Vector2(w+trans*halfCell*5, h+(gridCoords.y+halfCell*17)*trans));
						targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell*5)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 28:
						targets.add(new Vector2(w+trans*halfCell*7, h+(gridCoords.y+halfCell*13)*trans));
						targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell*7)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear(); if(musicOn)shootSound.play();
				break;
			case 30:
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell*11)*trans));
						targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell*9)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 32:
					targets.add(new Vector2(w+trans*halfCell*7, h+(gridCoords.y+halfCell*15)*trans));
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell*7)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 35:
							targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell*9)*trans));
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell*13)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 36:
						targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell*7)*trans));
						targets.add(new Vector2(w+trans*halfCell*7, h+(gridCoords.y+halfCell*17)*trans));
						targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell*11)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 40:
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell*9)*trans));
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell*15)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 42:
							targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell*13)*trans));
						targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell*11)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 45:
						targets.add(new Vector2(w+trans*halfCell*9, h+(gridCoords.y+halfCell*17)*trans));
						targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell*9)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 48:
						targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell*15)*trans));
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell*11)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 49:
					targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell*13)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 54:
						targets.add(new Vector2(w+trans*halfCell*11, h+(gridCoords.y+halfCell*17)*trans));
						targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell*11)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 56:
					targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell*15)*trans));
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell*13)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 63:
						targets.add(new Vector2(w+trans*halfCell*13, h+(gridCoords.y+halfCell*17)*trans));
						targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell*13)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 64:
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell*15)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 72:
						targets.add(new Vector2(w+trans*halfCell*15, h+(gridCoords.y+halfCell*17)*trans));
						targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell*15)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			case 81:
							targets.add(new Vector2(w+trans*halfCell*17, h+(gridCoords.y+halfCell*17)*trans));
						
						for(Vector2 target : targets) {
							GameInstance.getInstance().lasers.add(new Laser(player.isStealthy, type, player.position, target, player.facing));
							if(Math.abs(enemy.position.x+32-target.x)<48 && 
									Math.abs(enemy.position.y+32-target.y)<48) {
								enemy.damage(player.power);
							}
							for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
								if(Math.abs(asteroid.getX()+32-target.x)<asteroid.aWidth/2+halfCell-10 && 
										Math.abs(asteroid.getY()+32-target.y)<asteroid.aWidth/2+halfCell-10) {
									
									player.buff(asteroid); 
								}
							}
						}
						targets.clear();if(musicOn)shootSound.play();
				break;
			default:
				falseShot.play();
				break;
		}
	}	
}
