package com.nickan.epiphany.model.items;

import com.badlogic.gdx.math.Rectangle;

public class ItemBounds {
	private ItemBounds() { }

	public static float oneUnit = 32;
	public static float twoUnits = 64;

	public static class BoundType {
		private BoundType() { }
		public static final int ICON = 0;
		public static final int UNCHOSEN = 1;
		public static final int CHOSEN = 2;
	}

	public static class PotionBounds {
		public static final Rectangle[] SMALL_HP = new Rectangle[] {
			new Rectangle(0, 320, oneUnit, oneUnit),
			new Rectangle(0, 256, oneUnit, oneUnit),
			new Rectangle(0, 288, oneUnit, oneUnit)
		};

		public static final Rectangle[] BIG_HP = new Rectangle[] {
			new Rectangle(32, 320, oneUnit, oneUnit),
			new Rectangle(32, 256, oneUnit, oneUnit),
			new Rectangle(32, 288, oneUnit, oneUnit)
		};

		public static final Rectangle[] SMALL_MP = new Rectangle[] {
			new Rectangle(64, 320, oneUnit, oneUnit),
			new Rectangle(64, 256, oneUnit, oneUnit),
			new Rectangle(64, 320, oneUnit, oneUnit)
		};

		public static final Rectangle[] BIG_MP = new Rectangle[] {
			new Rectangle(96, 320, oneUnit, oneUnit),
			new Rectangle(96, 256, oneUnit, oneUnit),
			new Rectangle(96, 288, oneUnit, oneUnit)
		};
	}

	public static class HelmBounds {
		public static final Rectangle[][] HELMS = new Rectangle[][] {
			new Rectangle[] {	// 1
					new Rectangle(512, 704			, oneUnit, oneUnit),
					new Rectangle(512, 0			, twoUnits, oneUnit * 1.5f),
					new Rectangle(512, oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 2
					new Rectangle(512 + oneUnit, 704		, oneUnit, oneUnit),
					new Rectangle(512 + twoUnits, 0			, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + twoUnits, oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 3
					new Rectangle(512 + (oneUnit * 2), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 2), 0, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 2), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 4
					new Rectangle(512 + (oneUnit * 3), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 3), 0, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 3), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 5
					new Rectangle(512 + (oneUnit * 4), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 4), 0, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 4), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 6
					new Rectangle(512 + (oneUnit * 5), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 5), 0, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 5), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 7
					new Rectangle(512 + (oneUnit * 6), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 6), 0, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 6), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 8
					new Rectangle(512 + (oneUnit * 7), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 7), 0, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 7), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 9
					new Rectangle(512, 704			, oneUnit, oneUnit),
					new Rectangle(512, oneUnit * 1.5f			, twoUnits, oneUnit * 1.5f),
					new Rectangle(512, oneUnit * 3f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 10
					new Rectangle(512 + oneUnit, 704		, oneUnit, oneUnit),
					new Rectangle(512 + twoUnits, oneUnit * 1.5f	, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + twoUnits, oneUnit * 3f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 11
					new Rectangle(512 + (oneUnit * 2), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 2), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 2), oneUnit * 3f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 12
					new Rectangle(512 + (oneUnit * 3), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 3), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 3), oneUnit * 3f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 13
					new Rectangle(512 + (oneUnit * 4), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 4), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 4), oneUnit * 3f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 14
					new Rectangle(512 + (oneUnit * 5), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 5), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 5), oneUnit * 3f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 15
					new Rectangle(512 + (oneUnit * 6), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 6), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 6), oneUnit * 3f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {	// 16
					new Rectangle(512 + (oneUnit * 7), 704, oneUnit, oneUnit),
					new Rectangle(512 + (twoUnits * 7), oneUnit * 1.5f, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 7), oneUnit * 3f, twoUnits, oneUnit * 1.5f)
			}
		};

	}

	public static class ShieldBounds {
		public static final Rectangle[][] SHIELDS = new Rectangle[][] {
			new Rectangle[] {
					new Rectangle(512, 800, twoUnits, twoUnits),
					new Rectangle(512, 192, twoUnits, oneUnit * 1.5f),
					new Rectangle(512, 192 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512 + twoUnits, 800, twoUnits, twoUnits),
					new Rectangle(512 + twoUnits, 192, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + twoUnits, 192 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512 + (twoUnits * 2), 800, twoUnits, twoUnits),
					new Rectangle(512 + (twoUnits * 2), 192, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 2), 192 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512 + (twoUnits * 3), 800, twoUnits, twoUnits),
					new Rectangle(512 + (twoUnits * 3), 192, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 3), 192 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512 + (twoUnits * 4), 800, twoUnits, twoUnits),
					new Rectangle(512 + (twoUnits * 4), 192, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 4), 192 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512 + (twoUnits * 5), 800, twoUnits, twoUnits),
					new Rectangle(512 + (twoUnits * 5), 192, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 5), 192 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512 + (twoUnits * 6), 800, twoUnits, twoUnits),
					new Rectangle(512 + (twoUnits * 6), 192, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 6), 192 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512 + (twoUnits * 7), 800, twoUnits, twoUnits),
					new Rectangle(512 + (twoUnits * 7), 192, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + (twoUnits * 7), 192 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512, 864, twoUnits, twoUnits),
					new Rectangle(512, 288, twoUnits, oneUnit * 1.5f),
					new Rectangle(512, 288 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(512, 864, twoUnits, twoUnits),
					new Rectangle(512 + twoUnits, 288, twoUnits, oneUnit * 1.5f),
					new Rectangle(512 + twoUnits, 288 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},
		};

	}

	public static class ArmorBounds {
		public static final Rectangle[][] ARMORS = new Rectangle[][] {
			new Rectangle[] {
					new Rectangle(640, 864, twoUnits, twoUnits),
					new Rectangle(640, 288, twoUnits, oneUnit * 1.5f),
					new Rectangle(640, 288 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(640 + twoUnits, 864, twoUnits, twoUnits),
					new Rectangle(640 + twoUnits, 288, twoUnits, oneUnit * 1.5f),
					new Rectangle(640 + twoUnits, 288 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(640 + (twoUnits * 2), 864, twoUnits, twoUnits),
					new Rectangle(640 + (twoUnits * 2), 288, twoUnits, oneUnit * 1.5f),
					new Rectangle(640 + (twoUnits * 2), 288 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(640 + (twoUnits * 3), 864, twoUnits, twoUnits),
					new Rectangle(640 + (twoUnits * 3), 288, twoUnits, oneUnit * 1.5f),
					new Rectangle(640 + (twoUnits * 3), 288 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(640 + (twoUnits * 4), 864, twoUnits, twoUnits),
					new Rectangle(640 + (twoUnits * 4), 288, twoUnits, oneUnit * 1.5f),
					new Rectangle(640 + (twoUnits * 4), 288 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},

			new Rectangle[] {
					new Rectangle(640 + (twoUnits * 5), 864, twoUnits, twoUnits),
					new Rectangle(640 + (twoUnits * 5), 288, twoUnits, oneUnit * 1.5f),
					new Rectangle(640 + (twoUnits * 5), 288 + oneUnit * 1.5f, twoUnits, oneUnit * 1.5f)
			},
		};
	}

	public static class RingBounds {
		public static final Rectangle[][] RINGS = new Rectangle[][] {
			new Rectangle[] {
					new Rectangle(512, 480, oneUnit, oneUnit),
					new Rectangle(512, 512, oneUnit, oneUnit),
					new Rectangle(512, 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + oneUnit, 480, oneUnit, oneUnit),
					new Rectangle(512 + oneUnit, 512, oneUnit, oneUnit),
					new Rectangle(512 + oneUnit, 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 2), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 2), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 2), 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 3), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 3), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 3), 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 4), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 4), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 4), 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 5), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 5), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 5), 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 6), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 6), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 6), 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 7), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 7), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 7), 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 8), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 8), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 8), 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 9), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 9), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 9), 544, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(512 + (oneUnit * 10), 480, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 10), 512, oneUnit, oneUnit),
					new Rectangle(512 + (oneUnit * 10), 544, oneUnit, oneUnit)
			}
		};

	}

	public static class ManteauBounds {
		public static class ManteauId {
			public static final int LOW_QUALITY = 0;
			public static final int MED_QUALITY = 1;
			public static final int HIGH_QUALITY = 2;
			public static final int EXCELLENT_QUALITY = 3;
		}
		
		public static final Rectangle[][] MANTEAUS = new Rectangle[][] {
			new Rectangle[] {
					new Rectangle(192, 256, oneUnit, twoUnits),
					new Rectangle(0, 0, twoUnits, twoUnits),
					new Rectangle(0, twoUnits, twoUnits, twoUnits)
			},

			new Rectangle[] {
					new Rectangle(192, 256 + oneUnit, oneUnit, twoUnits),
					new Rectangle(twoUnits, 0, twoUnits, twoUnits),
					new Rectangle(twoUnits, twoUnits, twoUnits, twoUnits)
			},

			new Rectangle[] {
					new Rectangle(192, 256 + (oneUnit * 2), oneUnit, twoUnits),
					new Rectangle(twoUnits * 2, 0, twoUnits, twoUnits),
					new Rectangle(twoUnits * 2, twoUnits, twoUnits, twoUnits)
			},

			new Rectangle[] {
					new Rectangle(192, 256 + (oneUnit * 3), oneUnit, twoUnits),
					new Rectangle(twoUnits * 3, 0, twoUnits, twoUnits),
					new Rectangle(twoUnits * 3, twoUnits, twoUnits, twoUnits)
			}
		};

	}
	
	public static class GlovesBounds {
		public static class GlovesId {
			public static final int LEATHER_GLOVES = 0;
			public static final int ARMOR_GLOVES = 1;
			public static final int REINFORCED_GLOVES = 2;
			public static final int PLATED_GLOVES = 3;
		}
		
		public static final Rectangle[][] GLOVES = new Rectangle[][] {
			new Rectangle[] {
					new Rectangle(0, 416, oneUnit, oneUnit),
					new Rectangle(160, 128, oneUnit, oneUnit),
					new Rectangle(160, 128 + oneUnit, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(0 + oneUnit, 416, oneUnit, oneUnit),
					new Rectangle(160 + oneUnit, 128, oneUnit, oneUnit),
					new Rectangle(160 + oneUnit, 128 + oneUnit, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(0 + twoUnits, 416, oneUnit, oneUnit),
					new Rectangle(160 + twoUnits, 128, oneUnit, oneUnit),
					new Rectangle(160 + twoUnits, 128 + oneUnit, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(0 + (twoUnits * 1.5f), 416, oneUnit, oneUnit),
					new Rectangle(160 + (twoUnits * 1.5f), 128, oneUnit, oneUnit),
					new Rectangle(160 + (twoUnits * 1.5f), 128 + oneUnit, oneUnit, oneUnit)
			}
		};

	}
	
	public static class BootsBounds {
		public static class BootsId {
			public static final int BOOTS = 0;
			public static final int GREAVES = 1;
			public static final int LEATHER_BOOTS = 2;
			public static final int LEATHER_GREAVES = 3;
		}
		
		public static final Rectangle[][] BOOTS = new Rectangle[][] {
			new Rectangle[] {
					new Rectangle(0, 448, oneUnit, oneUnit),
					new Rectangle(0, 128, oneUnit, oneUnit),
					new Rectangle(0, 128 + oneUnit, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(0 + oneUnit, 448, oneUnit, oneUnit),
					new Rectangle(0 + oneUnit, 128, oneUnit, oneUnit),
					new Rectangle(0 + oneUnit, 128 + oneUnit, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(0 + twoUnits, 448, oneUnit, oneUnit),
					new Rectangle(0 + twoUnits, 128, oneUnit, oneUnit),
					new Rectangle(0 + twoUnits, 128 + oneUnit, oneUnit, oneUnit)
			},

			new Rectangle[] {
					new Rectangle(0 + (twoUnits * 1.5f), 448, oneUnit, oneUnit),
					new Rectangle(0 + (twoUnits * 1.5f), 128, oneUnit, oneUnit),
					new Rectangle(0 + (twoUnits * 1.5f), 128 + oneUnit, oneUnit, oneUnit)
			}
		};

	}
	
	public static class WeaponBounds {
		public static class SwordId {
			public static final int SHORT_SWORD = 0;
			public static final int CUTLASS = 1;
			public static final int BASTARD_SWORD = 2;
			public static final int SCIMITAR = 3;
			public static final int BLADE = 4;
			public static final int BALMUNG = 5;
		}
		
		public static final Rectangle[][] SWORDS = new Rectangle[][] {
			new Rectangle[] {
					new Rectangle(160, 416, oneUnit, twoUnits),
					new Rectangle(320, 0, twoUnits, twoUnits),
					new Rectangle(320, 0 + twoUnits, twoUnits, twoUnits)
			},

			new Rectangle[] {
					new Rectangle(160, 864, oneUnit, twoUnits),
					new Rectangle(64, 576, twoUnits, twoUnits),
					new Rectangle(64, 576 + twoUnits, twoUnits, twoUnits)
			},

			new Rectangle[] {
					new Rectangle(160 + (oneUnit), 864, oneUnit, twoUnits),
					new Rectangle(64 + (twoUnits), 576, twoUnits, twoUnits),
					new Rectangle(64 + (twoUnits), 576 + twoUnits, twoUnits, twoUnits)
			},

			new Rectangle[] {
					new Rectangle(160 + (oneUnit * 2), 864, oneUnit, twoUnits),
					new Rectangle(64 + (twoUnits * 2), 576, twoUnits, twoUnits),
					new Rectangle(64 + (twoUnits * 2), 576 + twoUnits, twoUnits, twoUnits)
			},

			new Rectangle[] {
					new Rectangle(160 + (oneUnit * 3), 864, oneUnit, twoUnits),
					new Rectangle(64 + (twoUnits * 3), 576, twoUnits, twoUnits),
					new Rectangle(64 + (twoUnits * 3), 576 + twoUnits, twoUnits, twoUnits)
			},

			new Rectangle[] {
					new Rectangle(160 + (oneUnit * 4), 864, oneUnit, twoUnits),
					new Rectangle(64 + (twoUnits * 4), 576, twoUnits, twoUnits),
					new Rectangle(64 + (twoUnits * 4), 576 + twoUnits, twoUnits, twoUnits)
			}
		};

	}

	
	public static class ButtonBounds {
		private ButtonBounds() { }
		public static final Rectangle pause = new Rectangle(96, 800, 32, 32);
		
		public static final Rectangle menuBg = new Rectangle(32, 800, 32, 32);
		
		public static final Rectangle resume = new Rectangle(64, 800, 32, 32);
		
		public static final Rectangle addStatus = new Rectangle(0, 800, 32, 32);
		
		public static final Rectangle statusBg = new Rectangle(128, 800, 32, 32);
		
		public static final Rectangle slotBg = new Rectangle(160, 800, 32, 32);
		
		public static final Rectangle atk = new Rectangle(0, 832, 64, 64);
		
		public static final Rectangle equipmentBg = new Rectangle(64, 832, 64, 64);
		
		public static final Rectangle arrow = new Rectangle(192, 800, 32, 32);
	}

}

