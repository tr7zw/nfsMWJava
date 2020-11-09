import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextBox;
import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.Background;
import javax.microedition.m3g.Camera;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Graphics3D;
import javax.microedition.m3g.Group;
import javax.microedition.m3g.Image2D;
import javax.microedition.m3g.IndexBuffer;
import javax.microedition.m3g.Loader;
import javax.microedition.m3g.Mesh;
import javax.microedition.m3g.Object3D;
import javax.microedition.m3g.PolygonMode;
import javax.microedition.m3g.Sprite3D;
import javax.microedition.m3g.Texture2D;
import javax.microedition.m3g.Transform;
import javax.microedition.m3g.TriangleStripArray;
import javax.microedition.m3g.VertexArray;
import javax.microedition.m3g.VertexBuffer;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreNotFoundException;

public class TheGame extends Canvas implements Runnable {
   int[] asset_DataArray;
   Image[] asset_ImageArray = new Image[66];
   byte[][] asset_TileMapArray = new byte[1][];
   byte[][] asset_DataBufArray = new byte[15][];
   int nPngDataSize;
   byte[] pngImageDataArray;
   int asset_PaletteFilter = -1;
   int[] asset_PaletteFilterParams;
   Image[] asset_FlippedImageArray = new Image[66];
   int asset_nTileImageID;
   int system_nCouncurentSounds;
   int system_nSoundTypes;
   int system_bHasMMAPI;
   long system_nLastTime;
   int system_nThisFrameTime;
   int[] system_HighScoreArray = new int[4];
   int system_nLastHighScore;
   boolean system_bDemoMode;
   int system_nSaveLevel;
   int system_nContLevel;
   int system_nSaveDifficulty;
   int system_nSaveScore;
   int system_nSaveHealth;
   boolean system_bInitialised;
   int system_nCanvasWidth;
   int system_nCanvasHeight;
   boolean system_bAppPaused;
   boolean system_bExit;
   boolean system_bConstantUpdate;
   boolean system_bScreenDirty;
   int system_xClip;
   int system_yClip;
   int system_nClipWidth;
   int system_nClipHeight;
   int system_xDirty;
   int system_yDirty;
   int system_nDirtyWidth;
   int system_nDirtyHeight;
   boolean system_bUpdateDirty;
   boolean system_bReRenderSoftKeys;
   boolean system_bCheat;
   boolean system_bSoundOn;
   boolean system_bTipsOn;
   int system_nVolume;
   int system_nScoreSlot;
   boolean system_bAssetLoading;
   boolean system_bSoundPlaying;
   int system_nKeyCount;
   int system_nLoopCount;
   long system_nTimeStart;
   long system_nTimeEnd;
   int scene_nCurrentScene;
   public Graphics3D scene_g3d;
   int scene_timer;
   boolean scene_timerUpdate;
   int scene_currentMusicTrack = 0;
   int[] scene_musicTracks = new int[]{1005, 1008, 1011, 1014, 1002};
   boolean game_done;
   boolean game_waitForInit;
   byte game_level;
   int level_start_score;
   short game_nBackgroundMap;
   byte game_nKeyPressed;
   byte game_nLastKeyTap;
   short game_nTimeKeyHeld;
   short game_nTimeInState;
   byte game_state;
   int game_score;
   byte game_state_before_pause;
   boolean gameover_pause;
   int tipInitTime;
   int game_posX;
   int game_posY;
   int game_posZ;
   int game_angX;
   int game_angY;
   int game_angZ;
   int game_lastAngZ;
   int game_cameraAngZ;
   int game_cameraAngX;
   int game_camlagX;
   int game_camlagY;
   int game_timeSinceLastCopSpawn;
   int game_numSpawnedCops;
   int frameCount;
   int game_tachometer_vel;
   int car_dashboard = 0;
   boolean showFlashEffect = false;
   TheGame.CCollisionResult colResultCamera = new TheGame.CCollisionResult();
   short[] sin_Array = new short[]{0, 402, 803, 1205, 1605, 2005, 2404, 2801, 3196, 3589, 3980, 4369, 4756, 5139, 5519, 5896, 6269, 6639, 7005, 7366, 7723, 8075, 8423, 8765, 9102, 9434, 9759, 10079, 10393, 10701, 11002, 11297, 11585, 11866, 12139, 12406, 12665, 12916, 13159, 13395, 13622, 13842, 14053, 14255, 14449, 14634, 14810, 14978, 15136, 15286, 15426, 15557, 15678, 15790, 15892, 15985, 16069, 16142, 16206, 16260, 16305, 16339, 16364, 16379, 16383, 16379, 16364, 16339, 16305, 16260, 16206, 16142, 16069, 15985, 15892, 15790, 15678, 15557, 15426, 15286, 15136, 14978, 14810, 14634, 14449, 14255, 14053, 13842, 13622, 13395, 13159, 12916, 12665, 12406, 12139, 11866, 11585, 11297, 11002, 10701, 10393, 10079, 9759, 9434, 9102, 8765, 8423, 8075, 7723, 7366, 7005, 6639, 6269, 5896, 5519, 5139, 4756, 4369, 3980, 3589, 3196, 2801, 2404, 2005, 1605, 1205, 803, 402, 0, -402, -803, -1205, -1605, -2005, -2404, -2801, -3196, -3589, -3980, -4369, -4756, -5139, -5519, -5896, -6269, -6639, -7005, -7366, -7723, -8075, -8423, -8765, -9102, -9434, -9759, -10079, -10393, -10701, -11002, -11297, -11585, -11866, -12139, -12406, -12665, -12916, -13159, -13395, -13622, -13842, -14053, -14255, -14449, -14634, -14810, -14978, -15136, -15286, -15426, -15557, -15678, -15790, -15892, -15985, -16069, -16142, -16206, -16260, -16305, -16339, -16364, -16379, -16383, -16379, -16364, -16339, -16305, -16260, -16206, -16142, -16069, -15985, -15892, -15790, -15678, -15557, -15426, -15286, -15136, -14978, -14810, -14634, -14449, -14255, -14053, -13842, -13622, -13395, -13159, -12916, -12665, -12406, -12139, -11866, -11585, -11297, -11002, -10701, -10393, -10079, -9759, -9434, -9102, -8765, -8423, -8075, -7723, -7366, -7005, -6639, -6269, -5896, -5519, -5139, -4756, -4369, -3980, -3589, -3196, -2801, -2404, -2005, -1605, -1205, -803, -402};
   int[] exp_Array = new int[]{65535, 61564, 57834, 54330, 51038, 47946, 45041, 42312, 39748, 37340, 35078, 32953, 30956, 29080, 27319, 25663, 24108, 22648, 21276, 19987, 18776, 17638, 16569, 15565, 14622, 13736, 12904, 12122, 11388, 10698, 10050, 9441, 8869, 8331, 7827, 7352, 6907, 6488, 6095, 5726, 5379, 5053, 4747, 4459, 4189, 3935, 3697, 3473, 3262, 3065, 2879, 2704, 2541, 2387, 2242, 2106, 1978, 1859, 1746, 1640, 1541, 1447, 1360, 1277, 1200, 1127, 1059, 995, 934, 878, 824, 774, 728, 683, 642, 603, 566, 532, 500, 470, 441, 414, 389, 366, 343, 323, 303, 285, 267, 251, 236, 222, 208, 195, 184, 172, 162, 152, 143, 134, 126, 118, 111, 104, 98, 92, 86, 81, 76, 72, 67, 63, 59, 56, 52, 49, 46, 43, 41, 38, 36, 34, 31, 30, 28, 26, 24, 23, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 11, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 5, 4, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   int game_yArrowPos;
   int game_xArrowPos;
   byte game_difficulty;
   boolean game_animationsLoaded;
   boolean game_bPauseNextUpdate;
   int game_profilerPos = 0;
   int[] game_profilerColours = new int[30];
   long[][] game_profilerStartTimes = new long[30][32];
   long[][] game_profilerEndTimes = new long[30][32];
   int[] game_profilerTotalTimes = new int[30];
   int game_renderCount = 0;
   boolean game_playingStateHadUpdate;
   long game_TimeUpdate;
   int game_Time;
   int game_TimeLimit;
   boolean game_TimerCountdown;
   int game_closestEvasionPickup;
   int game_currentGear;
   int map_dataPointer;
   int game_mode;
   int game_fire;
   Camera camera;
   Group trackGroup;
   TheGame.CGroundCollision groundCollision;
   int game_loadState;
   int game_loadedWorld = -1;
   boolean game_trackFirstRender;
   boolean game_isLoading;
   boolean game_reloading;
   int sizex;
   int sizey;
   int game_seed;
   int game_random_number_gen;
   int popupTimer;
   String popupString = null;
   int popupImg = -1;
   boolean popupPriority = false;
   long game_random_state;
   TheGame.CSoundPlayerMIDP2 soundPlayer;
   Image game_imageMapBuffer;
   Graphics game_gMapBuffer;
   static String[] game_msg;
   static byte[] game_keyHistory = new byte[8];
   int game_nKeyFlags;
   int[] splash_CheatKeyArray;
   int splash_nTimeInState;
   boolean splash_bRendered = false;
   int splash_nState;
   int splash_nCheatKey;
   byte splash_langSelection = 0;
   final int GAMETEXT_YES = 0;
   final int GAMETEXT_NO = 1;
   final int GAMETEXT_OK = 2;
   final int GAMETEXT_SELECT = 3;
   final int GAMETEXT_BACK = 4;
   final int GAMETEXT_EXIT = 5;
   final int GAMETEXT_CONTINUE = 6;
   final int GAMETEXT_RETRY = 7;
   final int GAMETEXT_PAUSE = 8;
   final int GAMETEXT_RESUME = 9;
   final int GAMETEXT_TOTAL_FINE = 10;
   final int GAMETEXT_MUSIC_VOLUME = 11;
   final int GAMETEXT_PRESSTOSTART = 12;
   final int GAMETEXT_LOCKED = 13;
   final int GAMETEXT_MISSION_PASSED = 14;
   final int GAMETEXT_MISSION_FAILED = 15;
   final int GAMETEXT_DEMO = 16;
   final int GAMETEXT_CASH = 17;
   final int GAMETEXT_REP = 18;
   final int GAMETEXT_RACE_TYPE = 19;
   final int GAMETEXT_LAPS = 20;
   final int GAMETEXT_TRACK = 21;
   final int GAMETEXT_TIMELIMIT = 22;
   final int GAMETEXT_FINE = 23;
   final int GAMETEXT_MISSIONS = 24;
   final int GAMETEXT_COMPLETE = 25;
   final int GAMETEXT_PERCENTAGE = 26;
   final int GAMETEXT_PLAYER = 27;
   final int GAMETEXT_CARS = 28;
   final int GAMETEXT_LOADING = 29;
   final int GAMETEXT_INUSE = 30;
   final int GAMETEXT_INGARAGE = 31;
   final int GAMETEXT_NOT_AVAILABLE = 32;
   final int GAMETEXT_OWNED = 33;
   final int GAMETEXT_INSTALLED = 34;
   final int GAMETEXT_NOT_INSTALLED = 35;
   final int GAMETEXT_NOT_OWNED = 36;
   final int GAMETEXT_PRICE = 37;
   final int GAMETEXT_BANKROLL = 38;
   final int GAMETEXT_TRACK_LENGTH = 39;
   final int GAMETEXT_REPWON = 40;
   final int GAMETEXT_CASHWON = 41;
   final int GAMETEXT_SCORE = 42;
   final int GAMETEXT_TIME = 43;
   final int GAMETEXT_TOP_SPEED = 44;
   final int GAMETEXT_FASTEST_LAP = 45;
   final int GAMETEXT_COPS_CRASHED = 46;
   final int GAMETEXT_STEER_RIGHT = 47;
   final int GAMETEXT_STEER_LEFT = 48;
   final int GAMETEXT_BRAKE = 49;
   final int GAMETEXT_TAP_NITRO = 50;
   final int GAMETEXT_TAP_ACCELERATE = 51;
   final int GAMETEXT_INSTALLPART = 52;
   final int GAMETEXT_BUYPART = 53;
   final int GAMETEXT_SELECTCAR = 54;
   final int GAMETEXT_CAR_LOCKED = 55;
   final int GAMETEXT_CAR_LOCKED_DEMO = 56;
   final int GAMETEXT_CHANGE_LANGUAGE = 57;
   final int GAMETEXT_CHANGE_PROFILE = 58;
   final int GAMETEXT_CLEAR_PROFILE = 59;
   final int GAMETEXT_NEED_REP = 60;
   final int GAMETEXT_NEED_REP_DEMO = 61;
   final int GAMETEXT_NEED_CASH = 62;
   final int GAMETEXT_NEED_CASH_DEMO = 63;
   final int GAMETEXT_NEED_MISSION = 64;
   final int GAMETEXT_NEED_MISSION_DEMO = 65;
   final int GAMETEXT_ALREADY_INSTALLED = 66;
   final int GAMETEXT_DOWNGRADING = 67;
   final int GAMETEXT_EXIT_WARNING = 68;
   final int GAMETEXT_TRACK_DEMOLOCK = 69;
   final int GAMETEXT_CANT_REPEAT = 70;
   final int GAMETEXT_CAR_USED = 71;
   final int GAMETEXT_CAR_MITSU = 72;
   final int GAMETEXT_CAR_CORVET = 73;
   final int GAMETEXT_CAR_SUPRA = 74;
   final int GAMETEXT_CAR_LANCER = 75;
   final int GAMETEXT_CAR_RX8 = 76;
   final int GAMETEXT_CAR_BMW = 77;
   final int GAMETEXT_CAR_COP = 78;
   final int GAMETEXT_CAR_TRAFFIC = 79;
   final int GAMETEXT_TRACK_CITY = 80;
   final int GAMETEXT_TRACK_BEACH = 81;
   final int GAMETEXT_TRACK_CITYA = 82;
   final int GAMETEXT_TRACK_CITYB = 83;
   final int GAMETEXT_TRACK_CITYC = 84;
   final int GAMETEXT_TRACK_BEACHA = 85;
   final int GAMETEXT_TRACK_BEACHB = 86;
   final int GAMETEXT_MENUTITLE_CAREER = 87;
   final int GAMETEXT_MENUTITLE_STATS = 88;
   final int GAMETEXT_MENUTITLE_CUSOMTIZE = 89;
   final int GAMETEXT_MENUTITLE_MAINMENU = 90;
   final int GAMETEXT_MENUTITLE_MISSIONS = 91;
   final int GAMETEXT_MENUTITLE_OPTIONS = 92;
   final int GAMETEXT_MENUTITLE_PROFILES = 93;
   final int GAMETEXT_MENUTITLE_QUICKRACE = 94;
   final int GAMETEXT_MENUTITLE_SELECT_CAR = 95;
   final int GAMETEXT_MENUTITLE_SELECT_TRACK = 96;
   final int GAMETEXT_MENUTITLE_LAYOUT = 97;
   final int GAMETEXT_MENUTITLE_COLOUR1 = 98;
   final int GAMETEXT_MENUTITLE_COLOUR2 = 99;
   final int GAMETEXT_MENUTITLE_COLOUR3 = 100;
   final int GAMETEXT_MENUTITLE_TINT = 101;
   final int GAMETEXT_MENUTITLE_PAINT = 102;
   final int GAMETEXT_MENUTITLE_VINYLS = 103;
   final int GAMETEXT_MENUTITLE_SPOILER = 104;
   final int GAMETEXT_MENUTITLE_RIMS = 105;
   final int GAMETEXT_MENUTITLE_FBUMPER = 106;
   final int GAMETEXT_MENUTITLE_RBUMPER = 107;
   final int GAMETEXT_MENUTITLE_ENGINE = 108;
   final int GAMETEXT_MENUTITLE_TURBO = 109;
   final int GAMETEXT_MENUTITLE_NITRO = 110;
   final int GAMETEXT_MENUTITLE_TRANS = 111;
   final int GAMETEXT_MENUTITLE_TIRES = 112;
   final int GAMETEXT_MENUTITLE_BRAKES = 113;
   final int GAMETEXT_MENUTITLE_FUEL = 114;
   final int GAMETEXT_MENUTITLE_VIS = 115;
   final int GAMETEXT_MENUTITLE_PERF = 116;
   final int GAMETEXT_MENUTITLE_CIRCUIT = 117;
   final int GAMETEXT_MENUTITLE_CHECKPOINT = 118;
   final int GAMETEXT_MENUTITLE_KNOCKOUT = 119;
   final int GAMETEXT_MENUTITLE_SPEEDCAM = 120;
   final int GAMETEXT_MENUTITLE_OUTRUN = 121;
   final int GAMETEXT_MENUTITLE_CHALLENGE = 122;
   final int GAMETEXT_MENUTITLE_GENERAL = 123;
   final int GAMETEXT_MENUTITLE_BLACKLIST = 124;
   final int GAMETEXT_MENUTITLE_CUSTOMIZE = 125;
   final int GAMETEXT_MENUTITLE_SELECTCAR = 126;
   final int GAMETEXT_MENUTITLE_PROFILE1 = 127;
   final int GAMETEXT_MENUTITLE_PROFILE2 = 128;
   final int GAMETEXT_MENUTITLE_PROFILE3 = 129;
   final int GAMETEXT_MENUTITLE_CONTROLS = 130;
   final int GAMETEXT_MENUTITLE_RACETYPE = 131;
   final int GAMETEXT_MENUTITLE_RAPSHEET = 132;
   final int GAMETEXT_MENUTITLE_HUD = 133;
   final int GAMETEXT_MENUTITLE_VOLUME = 134;
   final int GAMETEXT_MENUTITLE_CHANGE_PROFILE = 135;
   final int GAMETEXT_MENUTITLE_CLEAR_PROFILE = 136;
   final int GAMETEXT_MENUTITLE_HELP = 137;
   final int GAMETEXT_MENUTITLE_LANGUAGE = 138;
   final int GAMETEXT_MENUTITLE_SUPPORT = 139;
   final int GAMETEXT_MENUTITLE_EXIT = 140;
   final int GAMETEXT_PART_NONE = 141;
   final int GAMETEXT_PART_STOCK = 142;
   final int GAMETEXT_PART_DYNAMIC = 143;
   final int GAMETEXT_PART_WAVES = 144;
   final int GAMETEXT_PART_SKULL = 145;
   final int GAMETEXT_PART_SLASH = 146;
   final int GAMETEXT_PART_FIRE = 147;
   final int GAMETEXT_PART_BLAZE = 148;
   final int GAMETEXT_PART_STRIPE = 149;
   final int GAMETEXT_PART_COLOUR1 = 150;
   final int GAMETEXT_PART_COLOUR2 = 151;
   final int GAMETEXT_PART_COLOUR3 = 152;
   final int GAMETEXT_PART_COLOUR4 = 153;
   final int GAMETEXT_PART_COLOUR5 = 154;
   final int GAMETEXT_PART_COLOUR6 = 155;
   final int GAMETEXT_PART_COLOUR7 = 156;
   final int GAMETEXT_PART_COLOUR8 = 157;
   final int GAMETEXT_PART_COLOUR9 = 158;
   final int GAMETEXT_PART_COLOUR10 = 159;
   final int GAMETEXT_PART_COLOUR11 = 160;
   final int GAMETEXT_PART_COLOUR12 = 161;
   final int GAMETEXT_PART_COLOUR13 = 162;
   final int GAMETEXT_PART_FLAME1 = 163;
   final int GAMETEXT_PART_FLAME2 = 164;
   final int GAMETEXT_PART_MODERN1 = 165;
   final int GAMETEXT_PART_MODERN2 = 166;
   final int GAMETEXT_PART_STRIPES1 = 167;
   final int GAMETEXT_PART_STRIPES2 = 168;
   final int GAMETEXT_PART_TRIBAL1 = 169;
   final int GAMETEXT_PART_TRIBAL2 = 170;
   final int GAMETEXT_PART_TYPE1 = 171;
   final int GAMETEXT_PART_TYPE2 = 172;
   final int GAMETEXT_PART_TYPE3 = 173;
   final int GAMETEXT_PART_TYPE4 = 174;
   final int GAMETEXT_PART_TYPE5 = 175;
   final int GAMETEXT_PART_TYPE6 = 176;
   final int GAMETEXT_PART_TYPE7 = 177;
   final int GAMETEXT_PART_TYPE8 = 178;
   final int GAMETEXT_PART_PACK1 = 179;
   final int GAMETEXT_PART_PACK2 = 180;
   final int GAMETEXT_PART_PACK3 = 181;
   final int GAMETEXT_NITRO_EMPTY = 182;
   final int GAMETEXT_OPPONENT_KNOCKEDOUT = 183;
   final int GAMETEXT_CHECKPOINT = 184;
   final int GAMETEXT_GO = 185;
   final int GAMETEXT_PAUSED = 186;
   final int GAMETEXT_BUSTED = 187;
   final int GAMETEXT_1ST = 188;
   final int GAMETEXT_2ND = 189;
   final int GAMETEXT_3RD = 190;
   final int GAMETEXT_4TH = 191;
   final int GAMETEXT_TOO_SLOW = 192;
   final int GAMETEXT_CAR_BROKEN = 193;
   final int GAMETEXT_EVADE = 194;
   final int GAMETEXT_BUSTED_IN = 195;
   final int GAMETEXT_TIMESUP = 196;
   final int GAMETEXT_TOOSLOW = 197;
   final int GAMETEXT_CAUGHT = 198;
   final int GAMETEXT_GOTAWAY = 199;
   final int GAMETEXT_RACE_COMPLETE = 200;
   final int GAMETEXT_RECKLESS_DRIVING = 201;
   final int GAMETEXT_ASSULT = 202;
   final int GAMETEXT_SPEEDING = 203;
   final int GAMETEXT_SCRIPT_BOSS1_BLURB = 204;
   final int GAMETEXT_SCRIPT_BOSS1_UNLOCK = 205;
   final int GAMETEXT_SCRIPT_BOSS1_COMPLETE = 206;
   final int GAMETEXT_SCRIPT_BOSS2_BLURB = 207;
   final int GAMETEXT_SCRIPT_BOSS2_UNLOCK = 208;
   final int GAMETEXT_SCRIPT_BOSS2_COMPLETE = 209;
   final int GAMETEXT_SCRIPT_BOSS2_COP = 210;
   final int GAMETEXT_SCRIPT_BOSS3_BLURB = 211;
   final int GAMETEXT_SCRIPT_BOSS3_UNLOCK = 212;
   final int GAMETEXT_SCRIPT_BOSS3_COMPLETE = 213;
   final int GAMETEXT_SCRIPT_BOSS3_COP = 214;
   final int GAMETEXT_SCRIPT_BOSS4_BLURB = 215;
   final int GAMETEXT_SCRIPT_BOSS4_UNLOCK = 216;
   final int GAMETEXT_SCRIPT_BOSS4_COMPLETE = 217;
   final int GAMETEXT_SCRIPT_BOSS4_COP = 218;
   final int GAMETEXT_SCRIPT_BOSS5_BLURB = 219;
   final int GAMETEXT_SCRIPT_BOSS5_UNLOCK = 220;
   final int GAMETEXT_SCRIPT_BOSS5_COMPLETE = 221;
   final int GAMETEXT_SCRIPT_BOSS5_COP = 222;
   final int GAMETEXT_SCRIPT_BOSS6_BLURB = 223;
   final int GAMETEXT_SCRIPT_BOSS6_UNLOCK = 224;
   final int GAMETEXT_SCRIPT_BOSS6_COMPLETE = 225;
   final int GAMETEXT_SCRIPT_BOSS6_COP = 226;
   final int GAMETEXT_SCRIPT_CIRCUIT_1 = 227;
   final int GAMETEXT_SCRIPT_CIRCUIT_2 = 228;
   final int GAMETEXT_SCRIPT_CIRCUIT_3 = 229;
   final int GAMETEXT_SCRIPT_CIRCUIT_4 = 230;
   final int GAMETEXT_SCRIPT_CIRCUIT_5 = 231;
   final int GAMETEXT_SCRIPT_CIRCUIT_6 = 232;
   final int GAMETEXT_SCRIPT_CHECKPOINT_1 = 233;
   final int GAMETEXT_SCRIPT_CHECKPOINT_2 = 234;
   final int GAMETEXT_SCRIPT_CHECKPOINT_3 = 235;
   final int GAMETEXT_SCRIPT_CHECKPOINT_4 = 236;
   final int GAMETEXT_SCRIPT_CHECKPOINT_5 = 237;
   final int GAMETEXT_SCRIPT_CHECKPOINT_6 = 238;
   final int GAMETEXT_SCRIPT_KNOCKOUT_1 = 239;
   final int GAMETEXT_SCRIPT_KNOCKOUT_2 = 240;
   final int GAMETEXT_SCRIPT_KNOCKOUT_3 = 241;
   final int GAMETEXT_SCRIPT_KNOCKOUT_4 = 242;
   final int GAMETEXT_SCRIPT_KNOCKOUT_5 = 243;
   final int GAMETEXT_SCRIPT_KNOCKOUT_6 = 244;
   final int GAMETEXT_SCRIPT_CAMERA_1 = 245;
   final int GAMETEXT_SCRIPT_CAMERA_2 = 246;
   final int GAMETEXT_SCRIPT_CAMERA_3 = 247;
   final int GAMETEXT_SCRIPT_CAMERA_4 = 248;
   final int GAMETEXT_SCRIPT_CAMERA_5 = 249;
   final int GAMETEXT_SCRIPT_CAMERA_6 = 250;
   final int GAMETEXT_SCRIPT_OUTRUN_1 = 251;
   final int GAMETEXT_SCRIPT_OUTRUN_2 = 252;
   final int GAMETEXT_SCRIPT_OUTRUN_3 = 253;
   final int GAMETEXT_SCRIPT_OUTRUN_4 = 254;
   final int GAMETEXT_SCRIPT_OUTRUN_5 = 255;
   final int GAMETEXT_SCRIPT_OUTRUN_6 = 256;
   final int GAMETEXT_TOTAL_LINES = 257;
   int[] g_docsEnglish = new int[]{963, 965, 971, 967, 969, 973};
   int[] g_docsChineseSimplified = new int[]{989, 991, 997, 993, 995, 999};
   int[] g_docs;
   int[] g_HudImgsEnglish;
   int[] g_HudImgsChineseSimplified;
   int[] g_HudImgs;
   int[] g_WarningImgs;
   int[] g_MenuImagesEnglish;
   int[] g_MenuImagesSChinese;
   int[] g_MenuImages;
   byte g_nLanguage;
   String[] g_Text;
   String[] g_statNamesEnglish;
   String[] g_statNamesSChinese;
   String[] g_statNames;
   int[] stats_stock;
   int[] stats_paint;
   int[] stats_windTint;
   int[] stats_vinyl;
   int[] stats_spoiler;
   int[] stats_rims;
   int[] stats_bumpers;
   int[] stats_engine1;
   int[] stats_turbo1;
   int[] stats_nitrous1;
   int[] stats_trans1;
   int[] stats_tires1;
   int[] stats_brakes1;
   int[] stats_fuel1;
   int[] stats_engine2;
   int[] stats_turbo2;
   int[] stats_nitrous2;
   int[] stats_trans2;
   int[] stats_tires2;
   int[] stats_brakes2;
   int[] stats_fuel2;
   int[] stats_engine3;
   int[] stats_turbo3;
   int[] stats_nitrous3;
   int[] stats_trans3;
   int[] stats_tires3;
   int[] stats_brakes3;
   int[] stats_fuel3;
   int[] stats_max;
   TheGame.NFSMW_CarPart[][] g_carParts;
   Image g_carImgHi;
   Image g_carImgLow;
   Image g_carImgTraffic;
   Graphics g_carGfxHi;
   Graphics g_carGfxLow;
   Graphics g_carGfxTraffic;
   byte[][] g_carSetups;
   byte[][] g_QRcarSetups;
   TheGame.NFSMW_CarAppearance[] g_cars;
   String[] g_worlds;
   int[] g_worldsPretty;
   TheGame.NFSMW_TrackPath[] g_trackPaths;
   TheGame.NFSMW_Track[] g_tracks;
   int[] g_missionTypeNames;
   int[][] raceSetups;
   int[][] scriptScenes;
   static int g_missionIncID = 0;
   TheGame.NFSMW_Mission[] g_missions;
   TheGame.NFSMW_Profile g_player;
   TheGame.NFSMW_Race g_race;
   TheGame.NFSMW_Menu menuTrackSelect;
   TheGame.NFSMW_Menu menuCarSelect;
   TheGame.NFSMW_Menu menuQRCarSelect;
   TheGame.NFSMW_Menu menuCarPaintLayout;
   TheGame.NFSMW_Menu menuCarPaintSelect1;
   TheGame.NFSMW_Menu menuCarPaintSelect2;
   TheGame.NFSMW_Menu menuCarPaintSelect3;
   TheGame.NFSMW_Menu menuCarWindTintSelect;
   TheGame.NFSMW_Menu menuCarVinylSelect;
   TheGame.NFSMW_Menu menuCarSpoilerSelect;
   TheGame.NFSMW_Menu menuCarRimsSelect;
   TheGame.NFSMW_Menu menuCarFrontBmprsSelect;
   TheGame.NFSMW_Menu menuCarRearBmprsSelect;
   TheGame.NFSMW_MenuItem[] menuCarPaintItems;
   TheGame.NFSMW_Menu menuCarPaint;
   TheGame.NFSMW_MenuItem[] menuCustomizeVisItems;
   TheGame.NFSMW_Menu menuCustomizeVis;
   TheGame.NFSMW_Menu menuCarEngineSelect;
   TheGame.NFSMW_Menu menuCarTurboSelect;
   TheGame.NFSMW_Menu menuCarNitrousSelect;
   TheGame.NFSMW_Menu menuCarTransmissionSelect;
   TheGame.NFSMW_Menu menuCarTireSelect;
   TheGame.NFSMW_Menu menuCarBrakesSelect;
   TheGame.NFSMW_Menu menuCarFuelSelect;
   TheGame.NFSMW_MenuItem[] menuCustomizePerfItems;
   TheGame.NFSMW_Menu menuCustomizePerf;
   TheGame.NFSMW_MenuItem[] menuCustomizeItems;
   TheGame.NFSMW_Menu menuCustomize;
   TheGame.NFSMW_Menu menuMissionCircuits;
   TheGame.NFSMW_Menu menuMissionCheckpoints;
   TheGame.NFSMW_Menu menuMissionKnockout;
   TheGame.NFSMW_Menu menuMissionSpeedCam;
   TheGame.NFSMW_Menu menuMissionOutrun;
   TheGame.NFSMW_Menu menuMissionChallenge;
   TheGame.NFSMW_MenuItem[] menuMissionTypeItems;
   TheGame.NFSMW_Menu menuMissionTypes;
   TheGame.NFSMW_MenuItem[] menuCareerStatItems;
   TheGame.NFSMW_Menu menuCareerStats;
   TheGame.NFSMW_MenuItem[] menuCareerItems;
   TheGame.NFSMW_Menu menuCareer;
   TheGame.NFSMW_MenuItem[] menuProfileItems;
   TheGame.NFSMW_Menu menuProfile;
   TheGame.NFSMW_MenuItem[] menuHelpItems;
   TheGame.NFSMW_Menu menuHelp;
   TheGame.NFSMW_MenuItem[] menuOptionsItems;
   TheGame.NFSMW_Menu menuOptions;
   TheGame.NFSMW_MenuItem[] menuRootItems;
   TheGame.NFSMW_Menu menuRoot;
   TheGame.NFSMW_Menu menuCurrent;
   String[] menu_docArray;
   int[] menu_docLineAttr;
   byte menu_show_mode;
   short menu_max_row_count;
   short menu_current_top_row;
   int goingToNextMenu;
   int menuTimer;
   int lastMenuDirection;
   short[] dialogData;
   int dialogItemCount;
   int currentDialogItem;
   int currentColor;
   byte prevShowMode;
   int currentConfirm;
   int currentConfirmString;
   short menuRowCount;
   short menu_level;
   long tryMusicUntil;
   int menu_nDocument;
   int docID;
   int docStart;
   int docEnd;
   int menu_yDocStart;
   int menu_rowHeight;
   boolean menu_scrolling;
   int menu_yOffset;
   boolean menu_intromusicloaded;
   int menu_docLineCount;
   int menu_canvasHeight;
   int menu_xOffset;
   int menu_paintCarTimer;
   boolean menu_updateCarPaint;
   int menu_lastCar;
   boolean menu_playmusic;
   int menu_startupStage;
   int itemColor;
   int selectedColor;
   int menu_yScrollOffset;
   int menu_yCanvasOffset;
   int menu_scrollSpeed;
   int menu_sceneLoadStage;
   int timeInMenu;
   int timeLastKeyPress;
   int menu_nKeyFlags;
   int menu_car;
   int menu_nextCar;
   int menu_carUpdateTime;
   boolean menu_refreshCar;
   boolean menu_drawCar;
   boolean menu_hourGlass;
   Mesh menu_reflectionMesh;
   int menu_nItemsHeight;
   int menu_nItemsYSpacing;
   TheGame.CFont system_SoftkeyFontBackup;
   boolean menu_noRender;
   boolean menu_bBlacklistLoaded;
   boolean menu_bMissionLoaded;
   static int menu_camerCarAngle;
   short menu_mode;
   short menu_mode_param;
   int[] menu_tmpCarStatData;
   String menu_msg;
   int car_rot;
   int car_rot_offset;
   Group menu_Group;
   boolean menu_IsPaused;
   boolean menu_SplashPause;
   boolean transition_done;
   boolean firstUpdate;
   boolean transitionDoneNextPaint;
   static int fade_x_offset;
   static int fade_y_offset;
   static int fade_max_distance;
   static int fade_block_width;
   static int fade_block_height;
   static int fade_max_block;
   static int[] fadeDistance;
   static int realMaxCols;
   static int realMaxRows;
   long[] scene_timing;
   int[] scene_timingcolor;
   long scene_textureMemoryUsage;
   long scene_timerVal;
   long scene_timerTmp;
   IndexBuffer scene_index_buffer;
   VertexArray POSITION_ARRAY;
   VertexArray TEX_ARRAY;
   short trackPVSFrame1;
   short trackPVSFrame2;
   short trackPVSFrame3;
   int trackColTriangle;
   int trackPVSSegment;
   int cameraScopeMask;
   int numTrackItems;
   Mesh[] trackMeshs;
   boolean[] trackIsMesh;
   boolean[] trackIsRendered;
   Mesh[] trackBarrier1;
   int trackBarrier1Count;
   Mesh[] trackBarrier2;
   int trackBarrier2Count;
   Mesh[] trackBarrier3;
   int trackBarrier3Count;
   Mesh[] trackBarrier4;
   int trackBarrier4Count;
   Mesh skyDome;
   int scene_lastSnd;
   Sprite3D scene_OpenModel_Sprite3D;
   short[][] track_pvsXYZ;
   short[][] track_pvsMask;
   short[][] track_pvsStruct;
   short[][][] track_splines;
   short[][] track_checkpoints;
   short[][] track_copPoints;
   short[][] track_evasionPoints;
   short track_maxPVSi;
   int numPlayers;
   int game_playerPosition;
   TheGame.Car[] players;
   TheGame.Car[] cops;
   TheGame.Car[] traffic;
   TheGame.Car[] sortedCarsList;
   TheGame.Car[] allCarsList;
   TheGame.Car g_roadsideCop;
   Mesh scene_UI_arrow;
   Mesh scene_UI_indicator;
   Mesh scene_UI_evasion_ind;
   Transform scene_cameraTrans;
   Sprite3D headlights;
   Sprite3D brakelights;
   Sprite3D policelights;
   Sprite3D[] fire_sprites;
   Sprite3D sparks_sprite;
   Sprite3D flash_effect_sprite;
   VertexBuffer wheelVertes;
   TheGame.TextureRef tex_ref;
   PolygonMode polygonMode_Persp;
   PolygonMode polygonMode_Persp_Back;
   PolygonMode polygonMode_NoPersp;
   CompositingMode compositingMode_ZNONE;
   CompositingMode compositingMode_ZWRITE;
   CompositingMode compositingMode_ZWRITE_AlphaAdd;
   CompositingMode compositingMode_ZNONE_AlphaAdd;
   CompositingMode compositingMode_ZWRITEREAD;
   CompositingMode compositingMode_ZREAD_Alpha;
   CompositingMode compositingMode_ZWRITE_Alpha;
   Appearance appearance;
   Background background;
   Vector vTextureList;
   Vector vObjectList;
   TheGame.CFont font_text;
   TheGame.CFont font_headings;
   TheGame.CFont font_ingame;
   long[] crc_table;
   int crc_table_computed;
   Random system_Random;
   String[] system_sSoftkeyArray;
   int system_nFirstSoftkey;
   int system_nSecondSoftkey;
   int system_nSoftkeyWidth;
   int system_nSoftkeyHeight;
   TheGame.CFont system_SoftkeyFont;
   static Image system_imageFirstSoftkey;
   static Image system_imageSecondSoftkey;
   static Graphics system_graphicsFirstSoftkey;
   static Graphics system_graphicsSecondSoftkey;
   int system_softkeyBackground;
   int system_softkeyColor;
   int ActivePlayer;
   TheGame.CSoundObject[] asset_SoundArray;
   TheGame.CSoundPlayerMIDP2[] Player;
   int[] stockColors;
   short[] hudMPHImages;
   short[] hudLGImages;
   short[] hudSMImages;
   int[] gearImgsOn;
   int car_rot_count;
   int cheatPosition;
   int[] cheatCode;
   Transform trans;
   Appearance shadowAppearance;
   CompositingMode shadowCompmode;
   public int g_nParam1;
   public int g_nParam2;
   public int g_nParam3;
   public int g_nTimePlayerStuck;
   long loading_timeTotal;
   long loading_previousTime;
   int loadingCar;
   int numRoadsideCops;
   int slowCount;
   public int g_nLastRoadSideCopIndex;
   Transform scene_workerTrans;
   int lastTacko;
   int lastBustedness;
   int gameOver_stage;
   int pagePos;
   String script;
   String currentPage;
   boolean paused_hourglass;
   int paused_action;
   boolean game_bDrawBackgroundOnly;
   TheGame.CCollisionResult aiPathCollision;
   private static AppMain m_Midlet;
   static Graphics m_CurrentGraphics;
   boolean m_bPaintHourglass;
   boolean m_bWait;
   boolean m_bKeyUnhang;
   static boolean bAssertionsStarted = false;
   static boolean bAssertionSet = false;

   void LoadLanguage(byte var1) {
      this.g_nLanguage = var1;
      short var2 = 1052;
      if (var1 == 0) {
         var2 = 1052;
         this.g_docs = this.g_docsEnglish;
         this.g_HudImgs = this.g_HudImgsEnglish;
         this.g_statNames = this.g_statNamesEnglish;
         this.g_MenuImages = this.g_MenuImagesEnglish;
      } else if (var1 == 1) {
         var2 = 1058;
         this.g_docs = this.g_docsChineseSimplified;
         this.g_HudImgs = this.g_HudImgsChineseSimplified;
         this.g_statNames = this.g_statNamesSChinese;
         this.g_MenuImages = this.g_MenuImagesSChinese;
      } else {
         this.g_nLanguage = 0;
         this.g_docs = this.g_docsEnglish;
         this.g_HudImgs = this.g_HudImgsEnglish;
         this.g_statNames = this.g_statNamesEnglish;
      }

      if (this.g_nLanguage == 0) {
         this.font_text.SetSystem(Font.getFont(64, 0, 8));
         this.font_text.SetColor(255, 255, 255);
         this.font_headings.SetSystem(Font.getFont(64, 0, 8));
         this.font_headings.SetColor(255, 255, 255);
         this.font_ingame.SetSystem(Font.getFont(64, 0, 8));
         this.font_ingame.SetColor(255, 255, 255);
      } else {
         this.font_text.SetSystem(Font.getFont(64, 0, 0));
         this.font_text.SetColor(255, 255, 255);
         this.font_headings.SetSystem(Font.getFont(64, 0, 0));
         this.font_headings.SetColor(255, 255, 255);
         this.font_ingame.SetSystem(Font.getFont(64, 0, 0));
         this.font_ingame.SetColor(255, 255, 255);
      }

      try {
         this.asset_LoadData(var2);
         short var3 = (short)((this.asset_DataArray[var2 + 1] & '\uffff') >> 0);
         short var4 = (short)((this.asset_DataArray[var3 + 0] & -65536) >> 16);
         int var5 = (this.asset_DataArray[var2 + 0] & -1) >> 0;
         short var6 = (short)((this.asset_DataArray[var2 + 1] & -65536) >> 16);
         this.map_dataPointer = var5;
         int var7 = 0;
         String var8 = "";
         ++this.map_dataPointer;
         ++this.map_dataPointer;

         while(this.map_dataPointer < var6) {
            int var10 = (this.asset_DataBufArray[var4][this.map_dataPointer + 1] & 255) + ((this.asset_DataBufArray[var4][this.map_dataPointer + 0] & 255) << 8);
            String var9 = "" + (char)var10;
            if (var9.charAt(0) != '\n') {
               if (var9.charAt(0) == '\r') {
                  this.g_Text[var7] = var8;
                  var8 = "";
                  ++var7;
               } else if (var9.charAt(0) == '\\') {
                  var9 = "" + (char)this.asset_DataBufArray[var4][this.map_dataPointer + 1];
                  if (var9.charAt(0) == 'n') {
                     var8 = var8 + '\n';
                     ++this.map_dataPointer;
                  } else {
                     var8 = var8 + '\\';
                  }
               } else {
                  var8 = var8 + var9;
               }
            }

            ++this.map_dataPointer;
            ++this.map_dataPointer;
         }

         this.asset_FreeData(var2);
         if (this.system_bDemoMode) {
            this.g_Text[55] = this.g_Text[56];
            this.g_Text[60] = this.g_Text[61];
            this.g_Text[62] = this.g_Text[63];
            this.g_Text[64] = this.g_Text[65];
         }
      } catch (Exception var11) {
         var11.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\gametext.hpp", 397, var11.toString());
      } catch (Error var12) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\gametext.hpp", 397, var12.toString());
      }

   }

   void drawSoftKey(int var1, int var2) {
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.font_text.SetColor(16777215);
      int var3 = this.system_nCanvasHeight - 20;
      if (this.g_nLanguage == 0) {
         ++var3;
      }

      if (var2 == 0) {
         this.asset_DrawImage(948, 0, this.system_nCanvasHeight - 18);
         this.font_text.DrawSubstring(this.g_Text[var1], 0, this.g_Text[var1].length(), 45, var3, 1);
      } else {
         this.asset_DrawImage(948, this.system_nCanvasWidth - 90, this.system_nCanvasHeight - 18);
         this.font_text.DrawSubstring(this.g_Text[var1], 0, this.g_Text[var1].length(), this.system_nCanvasWidth - 45, var3, 1);
      }

   }

   void clearSavedLang() {
      try {
         RecordStore.deleteRecordStore("currentLanguage");
      } catch (Exception var2) {
         ;
      }

   }

   boolean loadLastLang() {
      try {
         byte[] var1 = new byte[1];
         RecordStore var2 = RecordStore.openRecordStore("currentLanguage", false);
         RecordEnumeration var3 = var2.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);
         if (var3.hasNextElement()) {
            int var4 = var3.nextRecordId();
            var1 = var2.getRecord(var4);
            this.g_nLanguage = var1[0];
            var3.destroy();
            var2.closeRecordStore();
            if (this.g_nLanguage > -1 && this.g_nLanguage < 2) {
               this.LoadLanguage(this.g_nLanguage);
               return true;
            } else {
               this.g_nLanguage = 0;
               return false;
            }
         } else {
            this.g_nLanguage = 0;
            return false;
         }
      } catch (Exception var5) {
         this.clearSavedLang();
         this.g_nLanguage = 0;
         return false;
      }
   }

   void saveCurLang() {
      try {
         RecordStore var1 = RecordStore.openRecordStore("currentLanguage", true);
         RecordEnumeration var2 = var1.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);
         byte[] var3 = new byte[]{this.g_nLanguage};
         if (var2.hasNextElement()) {
            int var4 = var2.nextRecordId();
            var3 = var1.getRecord(var4);
            var3[0] = this.g_nLanguage;
            var1.setRecord(var4, var3, 0, 1);
         } else {
            var1.addRecord(var3, 0, 1);
         }

         var2.destroy();
         var1.closeRecordStore();
      } catch (Exception var5) {
         this.clearSavedLang();
      }

   }

   void asset_LoadData() throws Exception {
      String var1 = "data_bin/data.bin";
      DataInputStream var2 = new DataInputStream(this.getClass().getResourceAsStream(var1));
      int var3 = var2.readInt();
      this.asset_DataArray = new int[var3 >> 2];
      byte[] var4 = new byte[var3];
      var2.readFully(var4);
      var3 >>= 2;

      for(int var5 = 0; var5 < var3; ++var5) {
         int var6 = var5 << 2;
         this.asset_DataArray[var5] = var4[var6] << 24 | (var4[var6 + 1] & 255) << 16 | (var4[var6 + 2] & 255) << 8 | var4[var6 + 3] & 255;
      }

      var2.close();
   }

   void asset_FreeData() {
      this.asset_DataArray = null;
   }

   int asset_GetArrayElement(int var1, int var2) {
      ++var2;
      var1 += var2 >> 1;
      return (var2 & 1) != 0 ? this.asset_DataArray[var1] & '\uffff' : this.asset_DataArray[var1] >> 16 & '\uffff';
   }

   void asset_SetArrayElement(int var1, int var2, int var3) {
      ++var2;
      var1 += var2 >> 1;
      if ((var2 & 1) != 0) {
         this.asset_DataArray[var1] = this.asset_DataArray[var1] & -65536 | var3 & '\uffff';
      } else {
         this.asset_DataArray[var1] = this.asset_DataArray[var1] & '\uffff' | var3 << 16;
      }

   }

   void make_crc_table() {
      int var3;
      for(var3 = 0; var3 < 256; ++var3) {
         long var1 = (long)var3;

         for(int var4 = 0; var4 < 8; ++var4) {
            if ((var1 & 1L) != 0L) {
               var1 = 3988292384L ^ var1 >> 1;
            } else {
               var1 >>= 1;
            }
         }

         this.crc_table[var3] = var1;
      }

      this.crc_table_computed = 1;

      for(var3 = 0; var3 < 256; ++var3) {
         ;
      }

   }

   long update_crc(long var1, byte[] var3, int var4, int var5) {
      long var6 = var1;
      if (this.crc_table_computed == 0) {
         this.make_crc_table();
      }

      for(int var8 = 0; var8 < var4; ++var8) {
         int var9 = (int)(var6 ^ (long)var3[var8 + var5]) & 255;
         var6 = this.crc_table[var9] ^ var6 >> 8;
      }

      return var6;
   }

   boolean crc(byte[] var1, int var2, int var3) {
      long var4 = this.update_crc(4294967295L, var1, var2, var3) ^ 4294967295L;
      var1[var3 + var2 + 0] = (byte)((int)(var4 >> 24 & 255L));
      var1[var3 + var2 + 1] = (byte)((int)(var4 >> 16 & 255L));
      var1[var3 + var2 + 2] = (byte)((int)(var4 >> 8 & 255L));
      var1[var3 + var2 + 3] = (byte)((int)(var4 >> 0 & 255L));
      return true;
   }

   boolean asset_FindChunk(int var1, int[] var2) {
      for(int var3 = 0; var3 < this.nPngDataSize; ++var3) {
         if (this.pngImageDataArray[var3] == var1 >> 24 && this.pngImageDataArray[var3 + 1] == (var1 >> 16 & 255) && this.pngImageDataArray[var3 + 2] == (var1 >> 8 & 255) && this.pngImageDataArray[var3 + 3] == (var1 & 255)) {
            var2[1] = ((this.pngImageDataArray[var3 - 4] & 255) << 24) + ((this.pngImageDataArray[var3 - 3] & 255) << 16) + ((this.pngImageDataArray[var3 - 2] & 255) << 8) + ((this.pngImageDataArray[var3 - 1] & 255) << 0) + 4;
            var2[0] = var3;
            return true;
         }
      }

      return false;
   }

   int asset_CreateImage(String var1, int var2, boolean var3) {
      int var4 = var1.indexOf(".png");
      if (var4 <= 0) {
         AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1033, "CreateImage: Invalid image name: " + var1);
         return -1;
      } else {
         String var5 = var1.substring(0, var4) + "_png";
         String var6 = var5 + var1;
         if (this.asset_PaletteFilter == -1) {
            String var8;
            try {
               if (var3) {
                  this.asset_FlippedImageArray[var2] = Image.createImage(var6);
               } else {
                  this.asset_ImageArray[var2] = Image.createImage(var6);
               }
            } catch (Exception var16) {
               var8 = var1.concat(var16.toString());
               AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1064, var8);
            } catch (Error var17) {
               var8 = var1.concat(var17.toString());
               AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1071, var8);
            }
         } else {
            this.nPngDataSize = 0;
            if (this.pngImageDataArray != null) {
               System.gc();
               this.pngImageDataArray = null;
            }

            try {
               DataInputStream var7 = new DataInputStream(this.getClass().getResourceAsStream(var6));

               int var9;
               for(int var21 = 0; (var9 = var7.read()) != -1; ++this.nPngDataSize) {
                  if (this.nPngDataSize < 4) {
                     var21 <<= 8;
                     var21 += var9;
                     if (this.nPngDataSize == 3 && var21 != -1991225785) {
                        this.nPngDataSize = var21;
                        break;
                     }
                  }
               }

               var7.close();
               var7 = new DataInputStream(this.getClass().getResourceAsStream(var6));
               this.pngImageDataArray = new byte[this.nPngDataSize];
               var7.readFully(this.pngImageDataArray);
               var7.close();
               var7 = null;
            } catch (Exception var18) {
               Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1130);
               this.pngImageDataArray = null;
               return -1;
            } catch (Error var19) {
               Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1137);
               this.pngImageDataArray = null;
               return -1;
            }

            this.pngImageDataArray[0] = -119;
            this.pngImageDataArray[1] = 80;
            this.pngImageDataArray[2] = 78;
            this.pngImageDataArray[3] = 71;
            int[] var20 = new int[2];
            this.asset_FindChunk(1347179589, var20);
            this.asset_FilterPalette(this.pngImageDataArray, var20[0] + 4, var20[1] - 4);
            this.crc(this.pngImageDataArray, var20[1], var20[0]);
            if (var3) {
               try {
                  this.asset_FlippedImageArray[var2] = Image.createImage(this.pngImageDataArray, 0, this.nPngDataSize);
               } catch (ArrayIndexOutOfBoundsException var13) {
                  Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1174);
               } catch (IllegalArgumentException var14) {
                  Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1179);
               } catch (NullPointerException var15) {
                  Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1184);
               }
            } else {
               try {
                  this.asset_ImageArray[var2] = Image.createImage(this.pngImageDataArray, 0, this.nPngDataSize);
               } catch (ArrayIndexOutOfBoundsException var10) {
                  Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1200);
               } catch (IllegalArgumentException var11) {
                  Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1205);
               } catch (NullPointerException var12) {
                  Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1210);
               }
            }

            this.pngImageDataArray = null;
         }

         return 0;
      }
   }

   void asset_SetPaletteFilter(int var1, int var2) {
      int[] var3 = new int[]{var2};
      this.asset_SetPaletteFilter(var1, var3);
   }

   void asset_SetPaletteFilter(int var1, int[] var2) {
      this.asset_PaletteFilter = var1;
      this.asset_PaletteFilterParams = var2;
   }

   void asset_LoadCombinedImage(int var1, boolean var2) throws Exception {
      short var3 = (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
      short var4 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      short var5 = (short)((this.asset_DataArray[var1 + 0 + 1] & '\uffff') >> 0);
      Assert(var3 >= 0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1265);
      short var10001 = var3;
      int var7 = var3 + 1;
      if (0 == var10001) {
         this.asset_CreateImage("/" + Integer.toString(var5) + ".png", var4, false);
         if (var2) {
            this.asset_CreateImage("/f/" + Integer.toString(var5) + ".png", var4, true);
         }
      }

      int var6 = this.asset_DataArray[var1 + 0];
      var6 = var6 & -65536 | var7 << 0;
      this.asset_DataArray[var1 + 0] = var6;
      var3 = (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
      if (var3 < 0) {
         ;
      }

   }

   void asset_FreeCombinedImage(int var1) {
      short var2 = (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
      short var3 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      int var5 = var2 - 1;
      if (var5 == 0) {
         if (this.asset_ImageArray[var3] == null) {
            ;
         }

         Assert(this.asset_ImageArray[var3] != null, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1448);
         this.asset_ImageArray[var3] = null;
      }

      if (var5 < 0) {
         var5 = 0;
      }

      int var4 = this.asset_DataArray[var1 + 0];
      var4 = var4 & -65536 | var5 << 0;
      this.asset_DataArray[var1 + 0] = var4;
      var2 = (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
   }

   void asset_LoadCombinedData(int var1) throws Exception {
      short var2 = (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
      Assert(var2 >= 0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1487);
      short var10001 = var2;
      int var7 = var2 + 1;
      if (0 == var10001) {
         short var3 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
         Assert(this.asset_DataBufArray[var3] == null, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1496);
         String var4 = Integer.toString(var3) + "_dat/" + Integer.toString(var3) + ".dat";
         DataInputStream var5 = new DataInputStream(this.getClass().getResourceAsStream(var4));
         int var6 = var5.readInt();
         this.asset_DataBufArray[var3] = new byte[var6];
         var5.readFully(this.asset_DataBufArray[var3]);
         var5.close();
      }

      int var8 = this.asset_DataArray[var1 + 0];
      var8 = var8 & -65536 | var7 << 0;
      this.asset_DataArray[var1 + 0] = var8;
   }

   void asset_FreeCombinedData(int var1) {
      short var2 = (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
      Assert(var2 != 0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1539);
      int var4 = var2 - 1;
      if (var4 == 0) {
         short var3 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
         Assert(this.asset_DataBufArray[var3] != null, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1544);
         this.asset_DataBufArray[var3] = null;
         System.gc();
      }

      int var5 = this.asset_DataArray[var1 + 0];
      var5 = var5 & -65536 | var4 << 0;
      this.asset_DataArray[var1 + 0] = var5;
   }

   void asset_LoadTileMap(int var1) throws Exception {
      int var2 = (byte)((this.asset_DataArray[var1 + 1] & 16711680) >> 16);
      Assert(var2 >= 0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1655);
      if (0 == var2) {
         byte var3 = (byte)((this.asset_DataArray[var1 + 1] & -16777216) >> 24);
         Assert(this.asset_TileMapArray[var3] == null, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1666);
         String var4 = Integer.toString(var3) + "_map/" + Integer.toString(var3) + ".map";
         DataInputStream var5 = new DataInputStream(this.getClass().getResourceAsStream(var4));
         int var6 = var5.readInt();
         this.asset_TileMapArray[var3] = new byte[var6];
         var5.readFully(this.asset_TileMapArray[var3]);
         var5.close();
         var5 = null;
         Assert(var6 == (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16) * (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0), "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1700);
         this.asset_LoadCombinedImage((short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0), false);
         ++var2;
      }

      int var7 = this.asset_DataArray[var1 + 1];
      var7 = var7 & -16711681 | var2 << 16;
      this.asset_DataArray[var1 + 1] = var7;
   }

   void asset_FreeTileMap(int var1) {
      this.asset_FreeCombinedImage((short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0));
      byte var2 = (byte)((this.asset_DataArray[var1 + 1] & 16711680) >> 16);
      Assert(var2 != 0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1742);
      int var4 = var2 - 1;
      if (var4 == 0) {
         byte var3 = (byte)((this.asset_DataArray[var1 + 1] & -16777216) >> 24);
         Assert(this.asset_TileMapArray[var3] != null, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_java.hpp", 1747);
         this.asset_TileMapArray[var3] = null;
      }

      if (var4 < 0) {
         var4 = 0;
      }

      int var5 = this.asset_DataArray[var1 + 1];
      var5 = var5 & -16711681 | var4 << 16;
      this.asset_DataArray[var1 + 1] = var5;
   }

   void asset_BlitTile(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = (var1 & 63 & 7) << 4;
      int var9 = (var1 & 63) >> 3 << 4;
      var5 -= var4;
      var7 -= var6;
      var4 += var8;
      var6 += var9;
      m_CurrentGraphics.setClip(var2, var3, var5, var7);
      m_CurrentGraphics.drawImage(this.asset_ImageArray[this.asset_nTileImageID], var2 - var4, var3 - var6, 20);
   }

   void asset_LoadData(int var1) throws Exception {
      this.asset_LoadCombinedData((short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0));
   }

   void asset_FreeData(int var1) {
      this.asset_FreeCombinedData((short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0));
   }

   void asset_LoadDocument(int var1) throws Exception {
      short var2 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      short var3 = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.asset_LoadData(this.asset_GetArrayElement(var2, var4));
      }

   }

   void asset_FreeDocument(int var1) {
      short var2 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      short var3 = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.asset_FreeData(this.asset_GetArrayElement(var2, var4));
      }

   }

   void asset_LoadImage(int var1, boolean var2) throws Exception {
      this.asset_LoadCombinedImage((short)((this.asset_DataArray[var1 + 2] & -65536) >> 16), var2);
   }

   void asset_FreeImage(int var1) {
      this.asset_FreeCombinedImage((short)((this.asset_DataArray[var1 + 2] & -65536) >> 16));
   }

   void asset_DrawImage(int var1, int var2, int var3) {
      var2 += (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      var3 += (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
      short var4 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
      short var5 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
      short var6 = (short)((this.asset_DataArray[var1 + 2] & '\uffff') >> 0);
      short var7 = (short)((this.asset_DataArray[var1 + 3] & -65536) >> 16);
      short var8 = (short)((this.asset_DataArray[var1 + 2] & -65536) >> 16);
      short var9 = (short)((this.asset_DataArray[var8 + 0] & -65536) >> 16);
      this.asset_DrawClipped(var9, var2 - var6, var3 - var7, var2, var3, var4, var5, false);
   }

   void asset_DrawImageFlipped(int var1, int var2, int var3) {
      var2 -= (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      var3 += (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
      short var4 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
      short var5 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
      short var6 = (short)((this.asset_DataArray[var1 + 2] & '\uffff') >> 0);
      short var7 = (short)((this.asset_DataArray[var1 + 3] & -65536) >> 16);
      short var8 = (short)((this.asset_DataArray[var1 + 2] & -65536) >> 16);
      short var9 = (short)((this.asset_DataArray[var8 + 0] & -65536) >> 16);
      this.asset_DrawClipped(var9, var2 + var6 - this.asset_ImageArray[var9].getWidth(), var3 - var7, var2 - var4, var3, var4, var5, true);
   }

   void asset_DrawClipped(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      if (var4 < this.system_xClip) {
         var6 -= this.system_xClip - var4;
         var4 = this.system_xClip;
      }

      if (var4 + var6 > this.system_xClip + this.system_nClipWidth) {
         var6 = this.system_xClip + this.system_nClipWidth - var4;
      }

      if (var5 < this.system_yClip) {
         var7 -= this.system_yClip - var5;
         var5 = this.system_yClip;
      }

      if (var5 + var7 > this.system_yClip + this.system_nClipHeight) {
         var7 = this.system_yClip + this.system_nClipHeight - var5;
      }

      if (var6 > 0 && var7 > 0) {
         this.system_UpdateDirty(var4, var5, var6, var7);
         m_CurrentGraphics.setClip(var4, var5, var6, var7);
         if (this.asset_ImageArray[var1] != null && (!var8 || this.asset_FlippedImageArray[var1] != null)) {
            if (var8) {
               Assert(this.asset_FlippedImageArray[var1] != null, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_share.hpp", 319);
               m_CurrentGraphics.drawImage(this.asset_FlippedImageArray[var1], var2, var3, 20);
            } else {
               m_CurrentGraphics.drawImage(this.asset_ImageArray[var1], var2, var3, 20);
            }
         }

      }
   }

   void asset_LoadFrame(int var1, boolean var2) throws Exception {
      short var3 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
      short var4 = (short)((this.asset_DataArray[var3 + 0] & -65536) >> 16);

      for(int var5 = 0; var5 < var4; ++var5) {
         this.asset_LoadImage(this.asset_GetArrayElement(var3, var5), var2);
      }

   }

   void asset_FreeFrame(int var1) {
      short var2 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
      short var3 = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.asset_FreeImage(this.asset_GetArrayElement(var2, var4));
      }

   }

   void asset_ReplaceChildImage(int var1, int var2, int var3) {
      short var4 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      short var5 = (short)((this.asset_DataArray[var4 + 0] & -65536) >> 16);

      for(int var10 = 0; var10 < var5; ++var10) {
         int var6 = this.asset_GetArrayElement(var4, var10);
         short var7 = (short)((this.asset_DataArray[var6 + 1] & -65536) >> 16);
         short var8 = (short)((this.asset_DataArray[var6 + 1] & '\uffff') >> 0);
         short var9 = (short)((this.asset_DataArray[var7 + 0] & -65536) >> 16);

         for(int var11 = 0; var11 < var9; ++var11) {
            int var12 = this.asset_GetArrayElement(var7, var11);
            if (var12 == var2) {
               this.asset_SetArrayElement(var7, var11, var3);
            }
         }
      }

   }

   void asset_DrawFrame(int var1, int var2, int var3) {
      this.asset_DrawFrame(var1, var2, var3, -1);
   }

   void asset_DrawFrame(int var1, int var2, int var3, int var4) {
      short var5 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
      short var6 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
      short var7 = (short)((this.asset_DataArray[var5 + 0] & -65536) >> 16);

      for(int var8 = 0; var8 < var7; ++var8) {
         if (var8 != var4) {
            int var9 = this.asset_GetArrayElement(var6, var8);
            this.asset_DrawImage(this.asset_GetArrayElement(var5, var8), var2 + (short)((this.asset_DataArray[var9 + 0] & -65536) >> 16), var3 + (short)((this.asset_DataArray[var9 + 0] & '\uffff') >> 0));
         }
      }

   }

   void asset_DrawFrameFlipped(int var1, int var2, int var3) {
      this.asset_DrawFrameFlipped(var1, var2, var3, -1);
   }

   void asset_DrawFrameFlipped(int var1, int var2, int var3, int var4) {
      short var5 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
      short var6 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
      short var7 = (short)((this.asset_DataArray[var5 + 0] & -65536) >> 16);

      for(int var8 = 0; var8 < var7; ++var8) {
         if (var8 != var4) {
            int var9 = this.asset_GetArrayElement(var6, var8);
            this.asset_DrawImageFlipped(this.asset_GetArrayElement(var5, var8), var2 - (short)((this.asset_DataArray[var9 + 0] & -65536) >> 16), var3 + (short)((this.asset_DataArray[var9 + 0] & '\uffff') >> 0));
         }
      }

   }

   void asset_LoadAnim(int var1, boolean var2) throws Exception {
      short var3 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      short var4 = (short)((this.asset_DataArray[var3 + 0] & -65536) >> 16);

      for(int var5 = 0; var5 < var4; ++var5) {
         this.asset_LoadFrame(this.asset_GetArrayElement(var3, var5), var2);
      }

   }

   void asset_FreeAnim(int var1) {
      short var2 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
      short var3 = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.asset_FreeFrame(this.asset_GetArrayElement(var2, var4));
      }

   }

   void asset_LoadFixedFontColor(int var1) throws Exception {
      this.asset_LoadImage((short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0), false);
   }

   void asset_FreeFixedFontColor(int var1) {
      this.asset_FreeImage((short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0));
   }

   void asset_LoadFixedFont(int var1) throws Exception {
      short var2 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
      short var3 = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = this.asset_GetArrayElement(var2, var4);
         this.asset_LoadFixedFontColor(var5);
      }

   }

   void asset_FreeFixedFont(int var1) {
      short var2 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
      short var3 = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = this.asset_GetArrayElement(var2, var4);
         this.asset_FreeFixedFontColor(var5);
      }

   }

   void asset_DrawTileMap(int var1, int var2, int var3) {
      int var4 = 0;
      int var5 = 0;
      int var6 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16) << 4;
      int var7 = (short)((this.asset_DataArray[var1 + 0] & '\uffff') >> 0) << 4;
      int var8 = var2;
      int var9 = var3;
      int var10 = var2 + var6;
      int var11 = var3 + var7;
      int var12 = this.system_xClip;
      int var13 = this.system_yClip;
      int var14 = this.system_xClip + this.system_nClipWidth;
      int var15 = this.system_yClip + this.system_nClipHeight;
      if (var10 > var12 && var2 < var14 && var11 > var13 && var3 < var15) {
         if (var2 < var12) {
            var4 += var12 - var2;
            var8 = var12;
         }

         if (var10 > var14) {
            var6 += var14 - var10;
            var10 = var14;
         }

         if (var3 < var13) {
            var5 += var13 - var3;
            var9 = var13;
         }

         if (var11 > var15) {
            var7 += var15 - var11;
            var11 = var15;
         }

         var2 = var8;
         if (var6 > var4 && var7 > var5) {
            this.system_UpdateDirty(var8, var9, var10 - var8, var11 - var9);
            int var16 = var4 >> 4;
            int var17 = var5 >> 4;
            int var18 = var6 >> 4;
            int var19 = var7 >> 4;
            int var20 = var4 & 15;
            int var21 = var5 & 15;
            int var22 = var6 & 15;
            int var23 = var7 & 15;
            short var24 = (short)((this.asset_DataArray[var1 + 0] & -65536) >> 16);
            short var30 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
            this.asset_nTileImageID = (short)((this.asset_DataArray[var30 + 0] & -65536) >> 16);
            byte[] var31 = this.asset_TileMapArray[(byte)((this.asset_DataArray[var1 + 1] & -16777216) >> 24)];
            Assert(var31 != null, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_share.hpp", 870);
            int var32 = var16 + var17 * var24;
            int var26 = var9;
            int var33 = var19 == var17 ? var23 : 16;
            byte var29 = var31[var32];
            if (var29 != -1) {
               this.asset_BlitTile(var29, var8, var9, var20, var18 == var16 ? var22 : 16, var21, var33);
            }

            int var25 = var8 + (16 - var20);

            int var27;
            for(var27 = 1; var27 < var18 - var16; ++var27) {
               var29 = var31[var32 + var27];
               if (var29 != -1) {
                  this.asset_BlitTile(var29, var25, var26, 0, 16, var21, var33);
               }

               var25 += 16;
            }

            if (var22 != 0 && var18 != var16) {
               var29 = var31[var32 + var27];
               if (var29 != -1) {
                  this.asset_BlitTile(var29, var25, var26, 0, var22, var21, var33);
               }
            }

            var26 += 16 - var21;
            var32 += var24;

            for(int var28 = var17 + 1; var28 < var19; ++var28) {
               var29 = var31[var32];
               if (var29 != -1) {
                  this.asset_BlitTile(var29, var2, var26, var20, var18 == var16 ? var22 : 16, 0, 16);
               }

               var25 = var2 + (16 - var20);
               Image var34 = this.asset_ImageArray[this.asset_nTileImageID];
               int var35 = var18 - var16 + var32;

               for(var27 = var32 + 1; var27 < var35; ++var27) {
                  byte var36 = var31[var27];
                  if (var36 != -1) {
                     m_CurrentGraphics.setClip(var25, var26, 16, 16);
                     int var37 = (var36 & 7) << 4;
                     int var38 = var36 >> 3 << 4;
                     m_CurrentGraphics.drawImage(var34, var25 - var37, var26 - var38, 20);
                     var25 += 16;
                  }
               }

               var27 = var18 - var16;
               if (var22 != 0 && var18 != var16) {
                  var29 = var31[var32 + var27];
                  if (var29 != -1) {
                     this.asset_BlitTile(var29, var25, var26, 0, var22, 0, 16);
                  }
               }

               var26 += 16;
               var32 += var24;
            }

            if (var23 != 0 && var19 != var17) {
               var29 = var31[var32];
               if (var29 != -1) {
                  this.asset_BlitTile(var29, var2, var26, var20, var18 == var16 ? var22 : 16, 0, var23);
               }

               var25 = var2 + (16 - var20);

               for(var27 = 1; var27 < var18 - var16; ++var27) {
                  var29 = var31[var32 + var27];
                  if (var29 != -1) {
                     this.asset_BlitTile(var29, var25, var26, 0, 16, 0, var23);
                  }

                  var25 += 16;
               }

               if (var22 != 0 && var18 != var16) {
                  var29 = var31[var32 + var27];
                  if (var29 != -1) {
                     this.asset_BlitTile(var29, var25, var26, 0, var22, 0, var23);
                  }
               }
            }

         }
      }
   }

   void asset_FilterPalette(byte[] var1, int var2, int var3) {
      int var6;
      int var8;
      switch(this.asset_PaletteFilter) {
      case 1:
         for(int var19 = var2; var19 < var2 + var3; var19 += 3) {
            int var20 = var1[var19 + 0] & 255;
            if (var20 < 0) {
               var20 += 255;
            }

            var6 = var1[var19 + 1] & 255;
            if (var6 < 0) {
               var6 += 255;
            }

            int var21 = var1[var19 + 2] & 255;
            if (var21 < 0) {
               var21 += 255;
            }

            if (var20 == var6 && var20 == var21 && var6 == var21) {
               var8 = var20;
               var20 = this.asset_PaletteFilterParams[0] >> 16 & 255;
               var6 = this.asset_PaletteFilterParams[0] >> 8 & 255;
               var21 = this.asset_PaletteFilterParams[0] & 255;
               var1[var19 + 0] = (byte)(var20 * var8 >> 8);
               var1[var19 + 1] = (byte)(var6 * var8 >> 8);
               var1[var19 + 2] = (byte)(var21 * var8 >> 8);
            }
         }

         return;
      case 2:
         try {
            this.asset_LoadData(this.asset_PaletteFilterParams[0]);
         } catch (Exception var18) {
            Assert(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\asset_share.hpp", 1475);
            return;
         }

         short var4 = (short)((this.asset_DataArray[this.asset_PaletteFilterParams[0] + 1] & '\uffff') >> 0);
         short var5 = (short)((this.asset_DataArray[var4 + 0] & -65536) >> 16);
         var6 = (this.asset_DataArray[this.asset_PaletteFilterParams[0] + 0] & -1) >> 0;
         short var7 = (short)((this.asset_DataArray[this.asset_PaletteFilterParams[0] + 1] & -65536) >> 16);
         int var14 = 268435455;
         int var15 = 268435455;

         for(int var16 = var2; var16 < var2 + var3; var16 += 3) {
            var15 = 268435455;
            int var9 = var1[var16 + 0] & 255;
            int var11 = var1[var16 + 1] & 255;
            int var13 = var1[var16 + 2] & 255;

            for(int var17 = var6; var17 < var6 + (var7 >> 1); var17 += 3) {
               var8 = this.asset_DataBufArray[var5][var17 + 0] & 255;
               int var10 = this.asset_DataBufArray[var5][var17 + 1] & 255;
               int var12 = this.asset_DataBufArray[var5][var17 + 2] & 255;
               var14 = (var9 - var8) * (var9 - var8) + (var11 - var10) * (var11 - var10) + (var13 - var12) * (var13 - var12);
               if (var14 < var15) {
                  var8 = this.asset_DataBufArray[var5][(var7 >> 1) + var17 + 0] & 255;
                  var10 = this.asset_DataBufArray[var5][(var7 >> 1) + var17 + 1] & 255;
                  var12 = this.asset_DataBufArray[var5][(var7 >> 1) + var17 + 2] & 255;
                  var1[var16 + 0] = (byte)var8;
                  var1[var16 + 1] = (byte)var10;
                  var1[var16 + 2] = (byte)var12;
                  var15 = var14;
               }
            }
         }

         this.asset_FreeData(this.asset_PaletteFilterParams[0]);
      }

   }

   void system_SeedRandom(int var1) {
      this.system_Random.setSeed((long)var1);
   }

   int system_GetRandom() {
      return this.system_Random.nextInt();
   }

   void system_Hide() {
      this.scene_Show(false);
      this.system_bAppPaused = true;
   }

   void system_Show() {
      this.system_bAppPaused = false;
      this.system_nLastTime = System.currentTimeMillis();
      this.scene_Show(true);
   }

   void system_SetVolume(int var1) {
      this.system_nVolume = var1;
      if (var1 > 0) {
         this.system_bSoundOn = true;
      } else {
         this.system_bSoundOn = false;
      }

      this.asset_ChangeVolume(this.system_nVolume);
   }

   int system_GetVolume() {
      return this.system_nVolume;
   }

   synchronized void system_StopSound() {
   }

   private void system_CalculateSoftkeySizes() {
      int var1 = 0;
      int var2 = this.system_SoftkeyFont.GetHeight() - 4;

      for(int var3 = 0; var3 < 9; ++var3) {
         int var4 = this.system_SoftkeyFont.GetSubstringWidth(this.system_sSoftkeyArray[var3], 0, this.system_sSoftkeyArray[var3].length());
         if (var4 > var1) {
            var1 = var4;
         }
      }

      this.system_nSoftkeyWidth = var1 + (var2 >> 1);
      this.system_nSoftkeyHeight = var2 + (var2 + 7 >> 3);
   }

   void system_InitSoftkeys() throws Exception {
      this.system_SoftkeyFont = new TheGame.CFont();
      this.system_SoftkeyFont.Construct();
      this.system_SoftkeyFont.SetSystem(Font.getFont(64, 0, 8));
      this.system_CalculateSoftkeySizes();
      if (this.system_nCanvasWidth - (this.system_nSoftkeyWidth << 1) < this.system_nSoftkeyWidth >> 1) {
         this.system_CalculateSoftkeySizes();
      }

      this.system_softkeyBackground = 16777215;
      this.system_softkeyColor = 0;
      system_imageFirstSoftkey = Image.createImage(this.system_nSoftkeyWidth, this.system_nSoftkeyHeight);
      system_imageSecondSoftkey = Image.createImage(this.system_nSoftkeyWidth, this.system_nSoftkeyHeight);
      system_graphicsFirstSoftkey = system_imageFirstSoftkey.getGraphics();
      system_graphicsSecondSoftkey = system_imageSecondSoftkey.getGraphics();
   }

   void system_FreeSoftkeys() {
      this.system_SetSoftkeys(0, 0);
      system_imageFirstSoftkey = null;
      system_imageSecondSoftkey = null;
   }

   void system_RenderToSoftkey(Graphics var1, int var2) {
      Graphics var3 = m_CurrentGraphics;
      m_CurrentGraphics = var1;
      var1.setClip(0, 0, this.system_nSoftkeyWidth, this.system_nSoftkeyHeight);
      var1.setColor(this.system_softkeyBackground);
      var1.setColor(0, 0, 0);
      var1.fillRect(0, 0, this.system_nSoftkeyWidth, this.system_nSoftkeyHeight);

      for(int var4 = 0; var4 < this.system_nSoftkeyHeight; ++var4) {
         int var5 = 130 - Math.abs((this.system_nSoftkeyHeight >> 1) - var4) * (130 / (this.system_nSoftkeyHeight >> 1));
         var1.setColor(var5, var5, var5 * 120 / 100);
         var1.drawLine(0, var4, this.system_nSoftkeyWidth, var4);
      }

      var1.setColor(0, 0, 0);
      var1.drawRect(0, 0, this.system_nSoftkeyWidth - 1, this.system_nSoftkeyHeight - 1);
      this.system_SoftkeyFont.SetColor(this.system_softkeyColor);
      this.system_SetClip(0, 0, this.system_nSoftkeyWidth, this.system_nSoftkeyHeight);
      this.system_SoftkeyFont.DrawSubstring(this.system_sSoftkeyArray[var2 & '\uffff'], 0, this.system_sSoftkeyArray[var2 & '\uffff'].length(), this.system_nSoftkeyWidth >> 1, (this.system_nSoftkeyHeight >> 1) + 1, 5);
      m_CurrentGraphics = var3;
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
   }

   void system_SetSoftkeys(int var1, int var2) {
      this.system_nFirstSoftkey = var2;
      this.system_nSecondSoftkey = var1;
      if (this.system_nFirstSoftkey != 0 && system_imageFirstSoftkey != null) {
         this.system_RenderToSoftkey(system_graphicsFirstSoftkey, this.system_nFirstSoftkey);
      }

      if (this.system_nSecondSoftkey != 0 && system_imageSecondSoftkey != null) {
         this.system_RenderToSoftkey(system_graphicsSecondSoftkey, this.system_nSecondSoftkey);
      }

   }

   void system_RenderSoftkeys(boolean var1) {
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      if (this.system_nFirstSoftkey != 0) {
         m_CurrentGraphics.drawImage(system_imageFirstSoftkey, 0, this.system_nCanvasHeight, 36);
      }

      if (this.system_nSecondSoftkey != 0) {
         m_CurrentGraphics.drawImage(system_imageSecondSoftkey, this.system_nCanvasWidth, this.system_nCanvasHeight, 40);
      }

   }

   int system_TranslateKeyPressed(int var1) {
      if (var1 == -6 && this.system_nFirstSoftkey != 0) {
         return this.system_nFirstSoftkey;
      } else if (var1 == -7 && this.system_nSecondSoftkey != 0) {
         return this.system_nSecondSoftkey;
      } else {
         return var1 == -23 ? 53 : var1;
      }
   }

   void system_StartDelay() {
      this.m_bPaintHourglass = true;
      this.repaint();
      this.serviceRepaints();
      this.m_bPaintHourglass = false;
   }

   void system_SaveAllConfigs() {
      int[] var1 = new int[8];
      byte var2 = 0;
      int var8 = var2 + 1;
      var1[var2] = 3;
      System.out.println("Saving system volume = " + this.system_nVolume);
      var1[var8++] = this.system_bSoundOn ? 1 : 0;
      var1[var8++] = this.system_nVolume;
      Assert(var8 <= 8, "c:\\mobiledevelopment\\nfsmw_cn\\ndplatformjava\\system_java.hpp", 566);
      byte[] var4 = new byte[var8 << 2];

      for(int var3 = 0; var3 < var8; ++var3) {
         int var5 = var1[var3];
         int var6 = var3 << 2;
         var4[var6] = (byte)(var5 >> 24);
         var4[var6 + 1] = (byte)(var5 >> 16);
         var4[var6 + 2] = (byte)(var5 >> 8);
         var4[var6 + 3] = (byte)var5;
      }

      try {
         RecordStore var9 = RecordStore.openRecordStore("config", true);
         if (var9.getNumRecords() == 0) {
            var9.addRecord(var4, 0, var4.length);
         } else {
            var9.setRecord(1, var4, 0, var4.length);
         }

         var9.closeRecordStore();
      } catch (Exception var7) {
         ;
      }

   }

   void system_LoadAllConfigs() {
      this.system_InitSaveGame();
      this.system_InitSoundSettings();
      byte var1 = 8;
      byte[] var2 = new byte[var1 << 2];
      int[] var3 = new int[var1];

      try {
         RecordStore var5 = RecordStore.openRecordStore("config", false);
         var5.getRecord(1, var2, 0);
         var5.closeRecordStore();
      } catch (RecordStoreNotFoundException var6) {
         this.system_SaveAllConfigs();
         return;
      } catch (Exception var7) {
         this.system_InitSaveGame();
         this.system_InitSoundSettings();
         this.system_SaveAllConfigs();
         return;
      }

      int var9;
      for(int var4 = 0; var4 < var1; ++var4) {
         var9 = var4 << 2;
         var3[var4] = var2[var9] << 24 | (var2[var9 + 1] & 255) << 16 | (var2[var9 + 2] & 255) << 8 | var2[var9 + 3] & 255;
      }

      var1 = 0;
      int var8 = var1 + 1;
      var9 = var3[var1];
      if (var9 != 3) {
         this.system_SaveAllConfigs();
      } else {
         this.system_bSoundOn = var3[var8++] != 0;
         this.system_nVolume = var3[var8++];
         this.system_bSoundOn = true;
         System.out.println("Loading system volume = " + this.system_nVolume + " sound on = " + this.system_bSoundOn);
         Assert(var8 <= 8, "c:\\mobiledevelopment\\nfsmw_cn\\ndplatformjava\\system_java.hpp", 693);
      }
   }

   void system_FillRect(int var1, int var2, int var3, int var4, int var5) {
      m_CurrentGraphics.setColor(var5);
      m_CurrentGraphics.setClip(this.system_xClip, this.system_yClip, this.system_nClipWidth, this.system_nClipHeight);
      m_CurrentGraphics.fillRect(var1, var2, var3, var4);
   }

   void system_DrawRect(int var1, int var2, int var3, int var4, int var5) {
      m_CurrentGraphics.setColor(var5);
      m_CurrentGraphics.setClip(this.system_xClip, this.system_yClip, this.system_nClipWidth, this.system_nClipHeight);
      m_CurrentGraphics.drawRect(var1, var2, var3, var4);
   }

   void system_DrawLine(int var1, int var2, int var3, int var4, int var5) {
      m_CurrentGraphics.setColor(var5);
      m_CurrentGraphics.setClip(this.system_xClip, this.system_yClip, this.system_nClipWidth, this.system_nClipHeight);
      m_CurrentGraphics.drawLine(var1, var2, var3, var4);
   }

   int system_FindFirst(String var1, int var2, int var3, char var4) {
      while(var2 < var3 && var1.charAt(var2) != var4) {
         ++var2;
      }

      return var2;
   }

   void system_SetClip(int var1, int var2, int var3, int var4) {
      this.system_xClip = var1;
      this.system_yClip = var2;
      this.system_nClipWidth = var3;
      this.system_nClipHeight = var4;
   }

   void system_UpdateDirty(int var1, int var2, int var3, int var4) {
      if (this.system_bUpdateDirty) {
         if (this.system_nDirtyWidth < 0) {
            this.system_xDirty = var1;
            this.system_yDirty = var2;
            this.system_nDirtyWidth = var3;
            this.system_nDirtyHeight = var4;
         } else {
            if (this.system_xDirty > var1) {
               this.system_nDirtyWidth += this.system_xDirty - var1;
               this.system_xDirty = var1;
            }

            if (this.system_xDirty + this.system_nDirtyWidth < var1 + var3) {
               this.system_nDirtyWidth = var1 + var3 - this.system_xDirty;
            }

            if (this.system_yDirty > var2) {
               this.system_nDirtyHeight += this.system_yDirty - var2;
               this.system_yDirty = var2;
            }

            if (this.system_yDirty + this.system_nDirtyHeight < var2 + var4) {
               this.system_nDirtyHeight = var2 + var4 - this.system_yDirty;
            }
         }

      }
   }

   void system_ClearDirtyRects() {
      this.system_xDirty = 0;
      this.system_yDirty = 0;
      this.system_nDirtyWidth = -1;
      this.system_nDirtyHeight = -1;
   }

   void system_StartDirtyRectUpdates() {
      this.system_bUpdateDirty = true;
   }

   void system_StopDirtyRectUpdates() {
      this.system_bUpdateDirty = false;
   }

   void system_SetScreenDirty() {
      this.system_bScreenDirty = true;
   }

   void system_Construct() {
      this.system_bInitialised = false;
      this.system_bAppPaused = true;
      this.system_bExit = false;
      this.system_bUpdateDirty = false;
      this.system_bReRenderSoftKeys = false;
      this.system_bCheat = false;
   }

   void system_Destroy() {
   }

   void system_Start() throws Exception {
      if (!this.system_bInitialised) {
         this.system_nCanvasWidth = this.getWidth();
         this.system_nCanvasHeight = this.getHeight();
         this.setFullScreenMode(true);
         this.system_nCanvasHeight = 320;
         this.system_xClip = 0;
         this.system_yClip = 0;
         this.system_nClipWidth = this.system_nCanvasWidth;
         this.system_nClipHeight = this.system_nCanvasHeight;
         this.asset_InitSoundEngine();
         this.asset_LoadData();
         this.system_LoadAllConfigs();
         this.system_SetVolume(this.system_nVolume);
         this.system_InitSoftkeys();
         this.system_nLastHighScore = -1;
         this.scene_Construct();
         this.system_bInitialised = true;
         if (!bAssertionSet) {
            Display.getDisplay(m_Midlet).setCurrent(this);
         }
      }

   }

   void system_End() {
      this.system_bAppPaused = true;
      if (this.system_bInitialised) {
         this.scene_End();
         if ((this.scene_nCurrentScene & 8192) != 0) {
            this.game_End();
         }

         this.scene_Destroy();
         this.system_FreeSoftkeys();
         this.asset_FreeData();
         this.system_bInitialised = false;
         this.system_bExit = true;
         this.scene_nCurrentScene = 0;
      }

   }

   void system_InitSaveGame() {
      this.system_nSaveLevel = 0;
      this.system_nContLevel = 0;
      this.system_nSaveDifficulty = 0;
      this.system_nSaveScore = 0;
      this.system_nScoreSlot = -1;
   }

   void system_InitSoundSettings() {
      this.system_bSoundOn = true;
      this.system_nVolume = 2;
   }

   void system_TransitionFromGameOver(int var1) throws Exception {
   }

   int system_Abs(int var1) {
      return var1 < 0 ? -var1 : var1;
   }

   void system_UpdateHiScores() {
      int var1;
      int var2;
      if (this.system_nScoreSlot >= 0) {
         if (this.game_score > this.system_HighScoreArray[this.system_nScoreSlot]) {
            for(var1 = this.system_nScoreSlot; var1 > 0 && this.system_HighScoreArray[var1 - 1] < this.game_score; --var1) {
               for(var2 = this.system_HighScoreArray.length - 1; var2 >= var1; --var2) {
                  this.system_HighScoreArray[var2] = this.system_HighScoreArray[var2 - 1];
               }
            }

            this.system_HighScoreArray[var1] = this.game_score;
            this.system_nScoreSlot = var1;
            this.system_SaveAllConfigs();
         }
      } else {
         for(var1 = 0; var1 < this.system_HighScoreArray.length; ++var1) {
            if (this.system_HighScoreArray[var1] < this.game_score) {
               this.system_nScoreSlot = var1;
               this.system_HighScoreArray[var1] = this.game_score;

               for(var2 = this.system_HighScoreArray.length - 1; var2 > var1; --var2) {
                  this.system_HighScoreArray[var2] = this.system_HighScoreArray[var2 - 1];
               }

               this.system_SaveAllConfigs();
               break;
            }
         }
      }

   }

   int asset_FindSound(int var1) {
      for(int var2 = 0; var2 < 10; ++var2) {
         if (this.asset_SoundArray[var2] != null && this.asset_SoundArray[var2].soundID == var1) {
            return var2;
         }
      }

      return -1;
   }

   void asset_SetActivePlayer(int var1) {
      this.ActivePlayer = var1;
   }

   void asset_InitSoundEngine() throws Exception {
      System.out.println("Init Snd engine\n");
      System.out.flush();
      this.ActivePlayer = 0;

      for(int var1 = 0; var1 < 1; ++var1) {
         this.Player[var1] = new TheGame.CSoundPlayerMIDP2();
         this.Player[var1].Construct();
      }

   }

   void asset_FreeSoundEngine() {
      for(int var1 = 0; var1 < 1; ++var1) {
         this.Player[var1].Destroy();
         this.Player[var1] = null;
      }

   }

   void asset_LoadSound(int var1, int var2) throws Exception {
      if (var1 >= 0) {
         int var3;
         for(var3 = 0; var3 < 10; ++var3) {
            if (this.asset_SoundArray[var3] != null && this.asset_SoundArray[var3].soundID == var1) {
               ++this.asset_SoundArray[var3].refCount;
               return;
            }
         }

         this.asset_LoadData(var1);
         short var4 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
         short var5 = (short)((this.asset_DataArray[var4 + 0] & -65536) >> 16);
         int var6 = (this.asset_DataArray[var1 + 0] & -1) >> 0;
         int var7 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
         if (var7 < 0) {
            var7 += 65536;
         }

         for(var3 = 0; var3 < 10; ++var3) {
            if (this.asset_SoundArray[var3] == null) {
               this.asset_SoundArray[var3] = new TheGame.CSoundObject();
               this.asset_SoundArray[var3].soundID = (short)var1;
               this.asset_SoundArray[var3].refCount = 1;
               byte[] var8 = new byte[var7];

               for(int var9 = 0; var9 < var7; ++var9) {
                  var8[var9] = this.asset_DataBufArray[var5][var9 + var6];
               }

               if (var1 > 999 && var1 <= 999) {
                  this.asset_SoundArray[var3].LoadSound(var8, 2);
               } else if (var1 > 999 && var1 <= 1014) {
                  this.asset_SoundArray[var3].LoadSound(var8, 3);
               } else if (var1 > 1014 && var1 <= 1014) {
                  this.asset_SoundArray[var3].LoadSound(var8, 1);
               } else if (var1 > 1014 && var1 <= 1014) {
                  this.asset_SoundArray[var3].LoadSound(var8, 4);
               } else if (var1 > 1014 && var1 <= 1014) {
                  this.asset_SoundArray[var3].LoadSound(var8, 5);
               }

               this.Player[this.ActivePlayer].PrepareSound(this.asset_SoundArray[var3], var2);
               this.asset_FreeData(var1);
               return;
            }
         }

      }
   }

   synchronized void asset_FreeSound(int var1) {
      for(int var2 = 0; var2 < 10; ++var2) {
         if (this.asset_SoundArray[var2] != null && this.asset_SoundArray[var2].soundID == var1) {
            if (--this.asset_SoundArray[var2].refCount == 0) {
               this.Player[this.ActivePlayer].DisposeSound(this.asset_SoundArray[var2]);
               this.asset_SoundArray[var2].Dispose();
               this.asset_SoundArray[var2].soundID = -1;
               this.asset_SoundArray[var2] = null;
            }

            return;
         }
      }

   }

   synchronized void asset_PlaySFX(int var1) {
   }

   synchronized boolean asset_IsSoundPlaying(int var1) {
      if (!this.system_bSoundOn) {
         return false;
      } else {
         int var2 = this.asset_FindSound(var1);
         if (-1 != var2) {
            return this.Player[this.ActivePlayer].IsSoundPlaying(this.asset_SoundArray[var2]);
         } else {
            return false;
         }
      }
   }

   synchronized boolean asset_IsAnySoundPlaying() {
      return !this.system_bSoundOn ? false : this.Player[this.ActivePlayer].IsAnySoundPlaying();
   }

   synchronized void asset_ChangeVolume(int var1) {
      int var2 = var1 * 100 / 5;
      this.Player[this.ActivePlayer].SetVolume(var2);
   }

   synchronized void asset_PlaySoundNowIfPossible(int var1) {
      if (this.system_bSoundOn) {
         int var2 = this.asset_FindSound(var1);
         if (-1 != var2) {
            this.Player[this.ActivePlayer].PlayBackgroundMusic(this.asset_SoundArray[var2]);
         }

      }
   }

   synchronized void asset_PlaySoundImmediate(int var1) {
      if (this.system_bSoundOn) {
         int var2 = this.asset_FindSound(var1);
         if (-1 != var2) {
            this.Player[this.ActivePlayer].PlaySoundImmediate(this.asset_SoundArray[var2]);
         }

      }
   }

   void asset_StopSound(int var1) {
      int var2;
      if (-1 == var1) {
         for(var2 = 0; var2 < 1; ++var2) {
            this.Player[var2].StopAllSounds();
         }
      } else {
         var2 = this.asset_FindSound(var1);
         if (var2 != -1) {
            this.Player[this.ActivePlayer].StopSound(this.asset_SoundArray[var2]);
         }
      }

   }

   void splash_Start() throws Exception {
      this.asset_LoadImage(616, false);
      this.splash_CheatKeyArray = new int[5];
      this.splash_CheatKeyArray[0] = 35;
      this.splash_CheatKeyArray[1] = 49;
      this.splash_CheatKeyArray[2] = 50;
      this.splash_CheatKeyArray[3] = 51;
      this.splash_CheatKeyArray[4] = 35;
      this.splash_nCheatKey = 0;
      this.splash_nTimeInState = 0;
      this.splash_nState = 0;
      this.splash_bRendered = false;
   }

   void splash_End() {
      this.splash_CheatKeyArray = null;
      this.asset_FreeImage(616);
   }

   void splash_Update(int var1) throws Exception {
      if (this.splash_bRendered) {
         this.splash_nTimeInState += var1;
         if (this.splash_nState != 1 && this.splash_nTimeInState > 4000) {
            this.splash_nTimeInState = 0;
            this.splash_bRendered = false;
            ++this.splash_nState;
            if (this.splash_nState == 1 && this.loadLastLang()) {
               ++this.splash_nState;
            }

            if (this.splash_nState >= 2) {
               this.scene_Transition(4097);
            }
         }

      }
   }

   void splash_Render() {
      this.splash_bRendered = true;
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      switch(this.splash_nState) {
      case 0:
         this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 0);
         this.asset_DrawImage(616, (this.system_nCanvasWidth >> 1) - 88, (this.system_nCanvasHeight >> 1) - 74);
         break;
      case 1:
         this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 0);
         byte var1 = 30;
         byte var2 = 46;
         this.system_FillRect(var1, var2 - 4 + this.splash_langSelection * 42, this.system_nCanvasWidth - var1 * 2, 36, 8429708);
         this.font_ingame.DrawSubstring("ENGLISH", 0, "ENGLISH".length(), this.system_nCanvasWidth >> 1, var2 + 5, 1);
         int var3 = var2 + 42;
         this.font_ingame.DrawSubstring("繁體中文", 0, "繁體中文".length(), this.system_nCanvasWidth >> 1, var3 + 5, 1);
         var3 += 42;
         if (this.splash_langSelection == 0) {
            this.g_Text[3] = "SELECT";
         } else {
            this.g_Text[3] = "選擇";
         }

         this.drawSoftKey(3, 0);
      }

   }

   void splash_PointerPressed(int var1, int var2) throws Exception {
      if (this.splash_nTimeInState >= 500) {
         if (this.splash_nState == 1) {
            if (var2 > 38 && var2 < 78) {
               this.splash_langSelection = 0;
            } else if (var2 > 78 && var2 < 120) {
               this.splash_langSelection = 1;
            }
         } else if (this.splash_nTimeInState > 3000) {
            this.splash_nTimeInState = 0;
            this.splash_bRendered = false;
            ++this.splash_nState;
            if (this.splash_nState == 1 && this.loadLastLang()) {
               ++this.splash_nState;
            }
         }

         if (this.splash_nState >= 2) {
            this.scene_Transition(4097);
         }

      }
   }

   void splash_PointerReleased(int var1, int var2) throws Exception {
   }

   void splash_KeyPressed(int var1, int var2) throws Exception {
      if (var1 == this.splash_CheatKeyArray[this.splash_nCheatKey]) {
         this.splash_nTimeInState -= 500;
         if (this.splash_nTimeInState < 0) {
            this.splash_nTimeInState = 0;
         }

         ++this.splash_nCheatKey;
         if (this.splash_nCheatKey == 5) {
            this.system_bCheat = true;
            this.scene_Transition(4097);
         }
      } else {
         if (this.splash_nTimeInState < 500) {
            return;
         }

         if (this.splash_nState != 1) {
            if (this.splash_nTimeInState > 3000) {
               this.splash_nTimeInState = 0;
               this.splash_bRendered = false;
               ++this.splash_nState;
               if (this.splash_nState == 1 && this.loadLastLang()) {
                  ++this.splash_nState;
               }
            }
         } else if (var1 != -6 && var2 != 8 && var1 != 53) {
            if ((var2 == 2 || var1 == 52) && this.splash_langSelection > 0) {
               --this.splash_langSelection;
            } else if ((var2 == 5 || var1 == 54) && this.splash_langSelection < 1) {
               ++this.splash_langSelection;
            }

            if ((var2 == 1 || var1 == 50) && this.splash_langSelection > 0) {
               --this.splash_langSelection;
            } else if ((var2 == 6 || var1 == 56) && this.splash_langSelection < 1) {
               ++this.splash_langSelection;
            }
         } else {
            this.LoadLanguage(this.splash_langSelection);
            this.saveCurLang();
            this.g_race.m_bMission = false;
            this.menuCurrent = this.menuRoot;
            this.menuRoot.m_selected = 0;
            this.splash_nTimeInState = 0;
            this.splash_bRendered = false;
            ++this.splash_nState;
         }

         if (this.splash_nState >= 2) {
            this.scene_Transition(4097);
         }
      }

   }

   void game_CreateBuffer() {
      this.game_imageMapBuffer = Image.createImage(this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.game_gMapBuffer = this.game_imageMapBuffer.getGraphics();
   }

   void game_FreeBuffer() {
      m_CurrentGraphics = null;
      this.game_gMapBuffer = null;
      this.game_imageMapBuffer = null;
   }

   int game_GetInteger(int var1) {
      int var2 = this.game_GetByteAsInteger(var1) + (this.game_GetByteAsInteger(var1) << 8) + (this.game_GetByteAsInteger(var1) << 16) + (this.game_GetByteAsInteger(var1) << 24);
      return var2;
   }

   short game_GetShort(int var1) {
      short var2 = (short)this.game_GetByteAsInteger(var1);
      var2 = (short)(var2 + (this.game_GetByteAsInteger(var1) << 8));
      return var2;
   }

   int game_GetByteAsInteger(int var1) {
      System.out.print(this.asset_DataBufArray[var1][this.map_dataPointer] + ", ");
      int var2 = this.asset_DataBufArray[var1][this.map_dataPointer++];
      if (var2 < 0) {
         var2 += 256;
      }

      return var2 & 255;
   }

   String game_GetString(int var1) {
      int var2 = this.game_GetByteAsInteger(var1);
      byte[] var3 = new byte[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this.asset_DataBufArray[var1][this.map_dataPointer + var4];
      }

      String var6 = new String(var3, 0, var2);
      Object var5 = null;
      this.map_dataPointer += var2;
      return var6;
   }

   int game_URandom() {
      ++this.game_random_number_gen;
      return Math.abs(this.system_GetRandom());
   }

   void game_DrawGameBackground(boolean var1) {
   }

   void game_DrawNumber(short[] var1, int var2, int var3, int var4, int var5, int var6) {
      while(var2 > 0) {
         int var7 = var2 % 10;
         this.asset_DrawImage(var1[var7], var3, var4);
         --var5;
         var2 /= 10;
         var3 -= var6;
      }

      while(var5 > 0) {
         this.asset_DrawImage(var1[0], var3, var4);
         --var5;
         var3 -= var6;
      }

   }

   void game_DrawNextIcon(int var1, int var2) {
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setColor(255, 174, 64);
      m_CurrentGraphics.drawLine(var1, var2, var1, var2);
      m_CurrentGraphics.drawLine(var1, var2 + 1, var1 + 1, var2 + 1);
      m_CurrentGraphics.drawLine(var1, var2 + 2, var1 + 2, var2 + 2);
      m_CurrentGraphics.drawLine(var1, var2 + 3, var1 + 1, var2 + 3);
      m_CurrentGraphics.drawLine(var1, var2 + 4, var1, var2 + 4);
   }

   void game_DrawPrevIcon(int var1, int var2) {
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setColor(255, 174, 64);
      m_CurrentGraphics.drawLine(var1 + 2, var2, var1 + 2, var2);
      m_CurrentGraphics.drawLine(var1 + 1, var2 + 1, var1 + 2, var2 + 1);
      m_CurrentGraphics.drawLine(var1, var2 + 2, var1 + 2, var2 + 2);
      m_CurrentGraphics.drawLine(var1 + 1, var2 + 3, var1 + 2, var2 + 3);
      m_CurrentGraphics.drawLine(var1 + 2, var2 + 4, var1 + 2, var2 + 4);
   }

   void game_DrawDownIcon(int var1, int var2) {
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setColor(255, 174, 64);
      m_CurrentGraphics.drawLine(var1 - 2, var2, var1 + 2, var2);
      m_CurrentGraphics.drawLine(var1 - 2, var2, var1, var2 + 3);
      m_CurrentGraphics.drawLine(var1 + 2, var2, var1, var2 + 3);
      m_CurrentGraphics.drawLine(var1, var2 + 1, var1, var2 + 2);
   }

   void game_DrawUpIcon(int var1, int var2) {
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setColor(255, 174, 64);
      m_CurrentGraphics.drawLine(var1 - 2, var2, var1 + 2, var2);
      m_CurrentGraphics.drawLine(var1 - 2, var2, var1, var2 - 3);
      m_CurrentGraphics.drawLine(var1 + 2, var2, var1, var2 - 3);
      m_CurrentGraphics.drawLine(var1, var2 - 1, var1, var2 - 2);
   }

   int game_MyRandom() {
      this.game_random_state = this.game_random_state * 1103515245L + 12345L & 2147483647L;
      return Math.abs((int)(this.game_random_state >> 11 & 255L));
   }

   void game_MyRandomSeed(long var1) {
      this.game_random_state = var1 & 4294967295L;
   }

   void game_WriteInt(byte[] var1, int var2) {
      var1[this.map_dataPointer++] = (byte)(var2 >> 24);
      var1[this.map_dataPointer++] = (byte)(var2 >> 16);
      var1[this.map_dataPointer++] = (byte)(var2 >> 8);
      var1[this.map_dataPointer++] = (byte)var2;
   }

   int game_ReadInt(byte[] var1) {
      int var2 = var1[this.map_dataPointer] << 24 | (var1[this.map_dataPointer + 1] & 255) << 16 | (var1[this.map_dataPointer + 2] & 255) << 8 | var1[this.map_dataPointer + 3] & 255;
      this.map_dataPointer += 4;
      return var2;
   }

   void game_LoadMission(int var1) throws Exception {
      this.asset_LoadData(var1);
      short var2 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
      short var3 = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);
      int var4 = (this.asset_DataArray[var1 + 0] & -1) >> 0;
      short var5 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
      this.map_dataPointer = var4;
      this.game_GetString(var3);
      this.game_GetString(var3);
      this.game_GetByteAsInteger(var3);
      this.game_GetString(var3);
      this.game_GetString(var3);
      this.game_GetByteAsInteger(var3);
      this.game_GetByteAsInteger(var3);
      int var13 = this.game_GetByteAsInteger(var3);

      for(int var14 = 0; var14 < var13; ++var14) {
         this.game_GetInteger(var3);
         this.game_GetInteger(var3);
         this.game_GetInteger(var3);
      }

      this.game_GetByteAsInteger(var3);
      int var15 = this.game_GetByteAsInteger(var3);

      int var16;
      for(var16 = 0; var16 < var15; ++var16) {
         this.game_GetInteger(var3);
      }

      var16 = this.game_GetByteAsInteger(var3);

      for(int var17 = 0; var17 < var16; ++var17) {
         this.game_GetInteger(var3);
         this.game_GetInteger(var3);
         this.game_GetInteger(var3);
      }

      this.game_GetString(var3);
      this.game_GetByteAsInteger(var3);
      this.game_GetInteger(var3);
      this.game_GetInteger(var3);
      this.game_GetInteger(var3);
      this.game_GetByteAsInteger(var3);
      this.game_GetByteAsInteger(var3);
      this.game_GetString(var3);
      this.game_GetByteAsInteger(var3);
      this.asset_FreeData(var1);
   }

   void menu_OnExecuteItem() throws Exception {
      TheGame.NFSMW_MenuItem var1 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
      int var2 = var1.m_type;
      if ((var2 & 1) != 0) {
         this.menu_ShowConfirmDlg(68);
      }

      if ((var2 & 4) != 0) {
         if (this.menuCurrent == this.menuProfile) {
            this.menu_ShowConfirmDlg(58);
         } else {
            this.menu_ShowConfirmDlg(59);
         }
      }

      if ((var2 & 2048) != 0) {
         this.menu_ShowConfirmDlg(57);
      }

      if ((var2 & 256) != 0) {
         try {
            switch(var1.m_cmd) {
            case 0:
               this.menu_InitDoc(this.g_docs[2], false, true);
               break;
            case 1:
               this.menu_InitDoc(this.g_docs[3], false, true);
               break;
            case 2:
               this.menu_InitDoc(this.g_docs[4], false, true);
            }
         } catch (Exception var7) {
            var7.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 127, var7.toString());
         } catch (Error var8) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 127, var8.toString());
         }
      }

      if ((var2 & 512) != 0) {
         try {
            this.menu_InitDoc(this.g_docs[1], false, true);
         } catch (Exception var5) {
            var5.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 136, var5.toString());
         } catch (Error var6) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 136, var6.toString());
         }
      }

      if ((var2 & 32) != 0) {
         if (this.menuRoot.m_selected == 0) {
            if (this.g_player.m_missionStatus[var1.m_cmd] != 1 && this.g_player.m_equipedCar != var1.m_cmd) {
               this.menu_ShowMessageDlg(55);
            } else {
               this.menuTrackSelect.m_parent = this.menuCurrent;
               this.menu_setCurrentMenu(this.menuTrackSelect);
               if (this.system_bDemoMode) {
                  this.menuCurrent.m_selected = 3;
               }

               this.g_player.m_currentCar = (byte)var1.m_cmd;
               this.menu_refreshCar(var1.m_cmd);
               this.menu_OnSelectionChange();
            }
         } else if (this.menuRoot.m_selected == 1) {
            if (this.g_player.m_equipedCar == var1.m_cmd) {
               this.menu_ShowMessageDlg(71);
            } else if (this.g_player.m_missionStatus[var1.m_cmd] == 1) {
               this.menu_ShowConfirmDlg(54);
            } else {
               this.menu_ShowMessageDlg(55);
            }
         }
      }

      if ((var2 & 64) != 0) {
         if (this.g_player.getTotalRep() < this.g_missions[var1.m_cmd].m_reqRep) {
            this.menu_ShowMessageDlg(60);
            var2 = 0;
         } else if (this.g_player.m_missionStatus[var1.m_cmd] == 1 && this.menuCurrent == this.menuMissionChallenge) {
            this.menu_ShowMessageDlg(70);
            var2 = 0;
         } else if (this.g_missions[var1.m_cmd].m_reqMission != -1 && this.g_player.m_missionStatus[this.g_missions[var1.m_cmd].m_reqMission] == 0) {
            this.menu_ShowMessageDlg(64);
            var2 = 0;
         } else {
            TheGame.NFSMW_Mission var3 = this.g_missions[var1.m_cmd];
            this.g_race.m_type = var3.m_raceType;
            this.g_race.m_laps = var3.m_nLaps;
            this.g_race.m_trackPath = var3.m_trackPath;
            this.g_race.m_nTimeLimit = var3.m_nTimeLimit;
            this.g_race.m_nSpeedLimit = var3.m_nreqChkpSpeed;
            this.g_race.m_nFastestLapTime = 0;
            this.g_race.m_nLastLapTime = 0;
            this.g_race.m_quickraceID = 0;
            this.g_race.m_numRacers = this.raceSetups[var3.m_raceSetup][1];
            this.g_race.m_numCops = this.raceSetups[var3.m_raceSetup][2];
            this.g_race.m_numTraffic = this.raceSetups[var3.m_raceSetup][3];
            this.g_race.m_bMission = true;
            this.g_race.m_nMissionID = var1.m_cmd;
            this.g_race.m_difficulty = 1 + this.g_race.m_nMissionID % 6;
            if (this.g_race.m_nMissionID < 6) {
               this.g_race.m_numRacers = 2;
            }
         }
      }

      if ((var2 & 128) != 0) {
         if (this.system_bDemoMode && var1.m_cmd != 3) {
            this.menu_ShowMessageDlg(69);
            var2 = 0;
         }

         this.g_race.m_type = 0;
         this.g_race.m_laps = 3;
         this.g_race.m_numCops = 2;
         this.g_race.m_numRacers = 3;
         this.g_race.m_numTraffic = 1;
         this.g_race.m_difficulty = 3;
         this.g_race.m_nSpeedLimit = 50;
         this.g_race.m_nFastestLapTime = 0;
         this.g_race.m_nLastLapTime = 0;
         this.g_race.m_quickraceID = var1.m_cmd;
         this.g_race.m_trackPath = this.g_tracks[var1.m_cmd].m_trackPath;
         this.g_race.m_bMission = false;
         this.g_race.m_nMissionID = -1;
      }

      if ((var2 & 536866816) != 0) {
         var2 = this.menuCmdToPart(var2);
         TheGame.NFSMW_CarPart var9 = this.g_carParts[var2][var1.m_cmd];
         boolean var4 = false;
         if ((var2 == 2 || var2 == 3) && this.g_cars[this.g_player.m_currentCar].m_parts[0] == 0) {
            var4 = true;
         }

         if (this.g_cars[this.g_player.m_currentCar].m_cmd == 5 && (var2 == 9 || var2 == 8 || var2 == 6)) {
            var4 = true;
         }

         if (!var4) {
            if (!this.g_cars[this.g_player.m_currentCar].isPartInstalled(var2, (byte)var1.m_cmd)) {
               if (var2 >= 10 && this.g_cars[this.g_player.m_currentCar].m_parts[var2] > (byte)var1.m_cmd) {
                  this.menu_ShowMessageDlg(67);
               } else if (this.g_player.m_partStatus[var2][var1.m_cmd] == 1) {
                  this.menu_ShowConfirmDlg(52);
               } else if (this.g_player.m_cash >= var9.m_cost) {
                  this.menu_ShowConfirmDlg(53);
               } else {
                  this.menu_ShowMessageDlg(62);
               }
            } else {
               this.menu_ShowMessageDlg(66);
            }
         }

         var2 = 0;
      }

      int var10;
      if ((var2 & 8) != 0) {
         for(var10 = 0; var10 < this.g_tracks.length; ++var10) {
            this.g_tracks[var10].freeTrackPreview();
         }

         this.players[0].m_ca = this.g_cars[this.g_player.m_currentCar];
         this.scene_Transition(8192);
      }

      if ((var2 & 16) != 0) {
         this.game_Return();
      }

      if ((var2 & 2) != 0) {
         if (var1.m_subMenu == null) {
            throw new Exception("***** omg submenu NOT VALID");
         }

         var1.m_subMenu.m_parent = this.menuCurrent;
         this.menu_setCurrentMenu(var1.m_subMenu);
         if (this.menuCurrent == this.menuCarSelect) {
            this.menuCurrent.m_selected = this.g_player.m_currentCar = this.g_player.m_equipedCar;
            this.menu_refreshCar(this.g_player.m_equipedCar);
         } else if (this.menuCurrent != this.menuQRCarSelect) {
            if (this.menuCurrent == this.menuCarPaintLayout) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[0];
            } else if (this.menuCurrent == this.menuCarVinylSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[5];
            } else if (this.menuCurrent == this.menuCarSpoilerSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[6];
            } else if (this.menuCurrent == this.menuCarRimsSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[7];
            } else if (this.menuCurrent == this.menuCarFrontBmprsSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[8];
            } else if (this.menuCurrent == this.menuCarRearBmprsSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[9];
            } else if (this.menuCurrent == this.menuCarEngineSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[10];
            } else if (this.menuCurrent == this.menuCarTurboSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[11];
            } else if (this.menuCurrent == this.menuCarNitrousSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[12];
            } else if (this.menuCurrent == this.menuCarTransmissionSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[13];
            } else if (this.menuCurrent == this.menuCarTireSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[14];
            } else if (this.menuCurrent == this.menuCarBrakesSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[15];
            } else if (this.menuCurrent == this.menuCarFuelSelect) {
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[16];
            } else if (this.menuCurrent == this.menuCarPaintSelect1) {
               this.menu_show_mode = 8;
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[1];
            } else if (this.menuCurrent == this.menuCarPaintSelect2) {
               this.menu_show_mode = 8;
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[2];
            } else if (this.menuCurrent == this.menuCarPaintSelect3) {
               this.menu_show_mode = 8;
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[3];
            } else if (this.menuCurrent == this.menuCarWindTintSelect) {
               this.menu_show_mode = 8;
               this.menuCurrent.m_selected = this.g_cars[this.g_player.m_equipedCar].m_parts[4];
            } else if (this.menuCurrent == this.menuCareer) {
               this.g_player.m_currentCar = this.g_player.m_equipedCar;
               this.g_cars[this.g_player.m_equipedCar].setParts(this.g_player.m_carSetups[this.g_player.m_equipedCar]);
               this.menu_refreshCar(this.g_player.m_currentCar);
               this.g_player.m_carRep = this.g_cars[this.g_player.m_currentCar].m_modStats[3];
            } else if (this.menuCurrent != this.menuMissionCircuits && this.menuCurrent != this.menuMissionCheckpoints && this.menuCurrent != this.menuMissionKnockout && this.menuCurrent != this.menuMissionSpeedCam && this.menuCurrent != this.menuMissionOutrun && this.menuCurrent != this.menuMissionChallenge) {
               if (this.menuCurrent == this.menuProfile) {
                  this.menuCurrent.m_selected = this.g_player.m_profileNum;
               }
            } else {
               byte var11 = 0;
               if (this.menuCurrent == this.menuMissionChallenge) {
                  var11 = 0;
               } else if (this.menuCurrent == this.menuMissionCircuits) {
                  var11 = 6;
               } else if (this.menuCurrent == this.menuMissionCheckpoints) {
                  var11 = 12;
               } else if (this.menuCurrent == this.menuMissionKnockout) {
                  var11 = 18;
               } else if (this.menuCurrent == this.menuMissionSpeedCam) {
                  var11 = 24;
               } else if (this.menuCurrent == this.menuMissionOutrun) {
                  var11 = 30;
               }

               for(int var12 = var11; var12 < var11 + 6; ++var12) {
                  if (this.g_player.m_missionStatus[var12] == 0) {
                     this.menuCurrent.m_selected = var12 - var11;
                     break;
                  }
               }
            }
         } else {
            for(var10 = 0; var10 < 6; ++var10) {
               this.g_cars[var10].setParts(this.g_QRcarSetups[var10]);
            }

            this.menuCurrent.m_selected = this.g_player.m_currentCar;
            this.menu_refreshCar(this.g_player.m_currentCar);
         }

         this.menu_OnSelectionChange();
      }

   }

   void menu_OnSelectionChange() {
      TheGame.NFSMW_MenuItem var1 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
      int var2 = var1.m_type;
      if (this.menu_bBlacklistLoaded) {
         this.asset_FreeImage(this.g_MenuImages[2]);
         this.asset_FreeImage(778);
         this.menu_bBlacklistLoaded = false;
      }

      if (this.menu_bMissionLoaded) {
         this.asset_FreeImage(720);
         this.menu_bMissionLoaded = false;
      }

      if ((var2 & 32) != 0) {
         if (this.menuCurrent == this.menuCarSelect) {
            this.g_cars[var1.m_cmd].setParts(this.g_player.m_carSetups[var1.m_cmd]);
         }

         this.g_player.m_currentCar = (byte)var1.m_cmd;
         this.menu_refreshCar(this.g_player.m_currentCar);
      } else if ((var2 & 128) != 0) {
         for(int var3 = 0; var3 < this.g_tracks.length; ++var3) {
            this.g_tracks[var3].freeTrackPreview();
         }

         this.g_tracks[var1.m_cmd].createTrackPreview();
         this.g_race.m_trackPath = this.g_tracks[var1.m_cmd].m_trackPath;
      } else if ((var2 & 536866816) != 0) {
         var2 = this.menuCmdToPart(var2);
         if (var2 != 0 && var2 != 1 && var2 != 2 && var2 != 3 && var2 != 4) {
            this.g_cars[this.g_player.m_currentCar].testPart(var2, (byte)var1.m_cmd);
         } else {
            this.menu_updateCarPaint = true;
            this.menu_paintCarTimer = 0;
         }
      }

      this.menu_carUpdateTime = this.timeInMenu + 2000;
   }

   void menu_MenuInc() throws Exception {
      TheGame.NFSMW_MenuItem var1 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
      int var2 = var1.m_type;
      if ((var2 & 1024) != 0) {
         int var3 = this.system_nVolume;
         if (this.system_nVolume < 5) {
            ++this.system_nVolume;
            this.system_bSoundOn = true;
            this.asset_ChangeVolume(this.system_nVolume);
         }

         if (var3 == 0 && this.system_nVolume > 0) {
            this.game_StartMusic(false);
         }

         this.system_SaveAllConfigs();
      }

   }

   void menu_MenuDec() throws Exception {
      TheGame.NFSMW_MenuItem var1 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
      int var2 = var1.m_type;
      if ((var2 & 1024) != 0) {
         if (this.system_nVolume > 0) {
            --this.system_nVolume;
            this.asset_ChangeVolume(this.system_nVolume);
         }

         if (this.system_nVolume == 0) {
            this.game_StopMusic();
         }

         this.system_SaveAllConfigs();
      }

   }

   void menu_OnBack() throws Exception {
      if (this.menu_bBlacklistLoaded) {
         this.asset_FreeImage(this.g_MenuImages[2]);
         this.asset_FreeImage(778);
         this.menu_bBlacklistLoaded = false;
      }

      if (this.menu_bMissionLoaded) {
         this.asset_FreeImage(720);
         this.menu_bMissionLoaded = false;
      }

      if (this.menuCurrent.m_parent != null) {
         boolean var1 = false;
         if (this.menuCurrent == this.menuCarPaint) {
            var1 = true;
         }

         if (this.menuCurrent == this.menuCarSelect || this.menuCurrent == this.menuQRCarSelect) {
            this.g_player.m_currentCar = this.g_player.m_equipedCar;
            if (this.menuCurrent == this.menuQRCarSelect) {
               this.g_cars[this.g_player.m_equipedCar].setParts(this.g_player.m_carSetups[this.g_player.m_equipedCar]);
            }

            this.menu_refreshCar(this.g_player.m_currentCar);
         }

         this.menu_setCurrentMenu(this.menuCurrent.m_parent);
         this.car_rot_offset = 0;
         if (this.menuCurrent == this.menuCustomizeVis && !var1 || this.menuCurrent == this.menuCarPaint) {
            this.menu_refreshCar(this.g_player.m_currentCar);
            this.menu_show_mode = 0;
         }

         this.menu_carUpdateTime = this.timeInMenu + 2000;
      } else {
         this.menuRoot.m_selected = 5;
         this.menu_OnExecuteItem();
      }

   }

   void menu_OnConfirmed() throws Exception {
      TheGame.NFSMW_MenuItem var1 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
      int var2 = var1.m_type;
      if ((var2 & 32) != 0) {
         this.g_player.m_currentCar = this.g_player.m_equipedCar = (byte)var1.m_cmd;
         this.g_player.saveRecords();
         this.menu_OnBack();
      } else if ((var2 & 536866816) != 0) {
         var2 = this.menuCmdToPart(var2);
         this.g_cars[this.g_player.m_currentCar].setPart(var2, (byte)var1.m_cmd);
         this.g_player.m_carSetups[this.g_player.m_currentCar][var2] = (byte)var1.m_cmd;
         this.g_player.m_carRep = this.g_cars[this.g_player.m_currentCar].m_modStats[3];
         if (this.g_player.m_partStatus[var2][var1.m_cmd] != 1) {
            this.g_player.m_cash -= this.g_carParts[var2][var1.m_cmd].m_cost;
            this.g_player.m_partStatus[var2][var1.m_cmd] = 1;
         }

         this.g_player.saveRecords();
         this.menu_OnBack();
      } else if ((var2 & 4) != 0) {
         if (this.menuCurrent == this.menuProfile) {
            this.g_player.saveRecords();
            this.g_player.changeCurrentProfile((byte)var1.m_cmd);
            this.g_player.loadRecords();
         } else {
            this.g_player.clearProfile();
            this.g_player.saveRecords();
         }

         this.g_player.m_currentCar = this.g_player.m_equipedCar;
         this.g_cars[this.g_player.m_currentCar].setParts(this.g_player.m_carSetups[this.g_player.m_currentCar]);
         this.menu_refreshCar(this.g_player.m_equipedCar);
         this.menu_OnBack();
      } else if ((var2 & 2048) != 0) {
         this.clearSavedLang();
         this.scene_Transition(16384);
      } else if ((var2 & 1) != 0) {
         this.system_bExit = true;
      }

   }

   void menu_DrawMenuBG(int var1) {
      menu_camerCarAngle = 7;
      if (this.menuCurrent.m_backgroundStyle == 3) {
         this.system_SetClip(0, 0, this.system_nCanvasWidth, var1);
         this.system_FillRect(0, 0, this.system_nCanvasWidth, var1, 1777431);
         int var2 = this.menuTrackSelect.m_selected;
         this.asset_DrawImage(this.g_tracks[var2].m_previewImg, this.system_nCanvasWidth - 120, 50);
      } else if (this.menuCurrent.m_backgroundStyle != 2 && this.menuCurrent.m_backgroundStyle != 1) {
         this.system_FillRect(0, 0, this.system_nCanvasWidth, 60, 0);
         this.system_FillRect(0, var1 - 17, this.system_nCanvasWidth, 32, 0);
         this.menu_DrawCar();
         TheGame.NFSMW_MenuItem var17 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
         int var3 = var17.m_type;
         int var4;
         int var5;
         int var6;
         int var7;
         if ((var3 & 1024) != 0) {
            var4 = this.system_nCanvasWidth >> 1;
            var5 = var4 - 108;
            var6 = var1 - 135;

            for(var7 = 0; var7 < 12; ++var7) {
               this.asset_DrawImage(916, var5 + var7 * 18, var6);
            }

            this.font_text.SetColor(16777215);
            this.font_text.DrawSubstring(this.g_Text[11] + " : " + this.system_nVolume, 0, (this.g_Text[11] + " : " + this.system_nVolume).length(), var4, var6 + 5, 1);
            if (this.system_nVolume < 5 && (this.scene_timer >> 8 & 1) == 1) {
               this.game_DrawUpIcon(var4 - 99, var6 + 11);
            }

            if (this.system_nVolume > 0 && (this.scene_timer >> 8 & 1) == 1) {
               this.game_DrawDownIcon(var4 + 99, var6 + 8);
            }
         } else {
            byte var18;
            byte var19;
            if (this.menuCurrent.m_backgroundStyle == 6) {
               if (this.menuCurrent.m_selected == 0) {
                  this.system_FillRect(0, 0, this.system_nCanvasWidth, var1, 0);
                  this.font_text.SetColor(0, 0, 0);

                  try {
                     if (!this.menu_bMissionLoaded) {
                        this.asset_LoadImage(720, false);
                        this.menu_bMissionLoaded = true;
                     }

                     this.asset_DrawImage(720, 0, 0);
                  } catch (Exception var13) {
                     var13.printStackTrace();
                     AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 831, var13.toString());
                  } catch (Error var14) {
                     AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 831, var14.toString());
                  }

                  var18 = 18;
                  var19 = 7;
                  this.font_text.DrawSubstring(this.g_Text[27], 0, this.g_Text[27].length(), var18, var19, 0);
                  var5 = var19 + 17;
                  this.font_text.DrawSubstring(this.g_Text[17], 0, this.g_Text[17].length(), var18, var5, 0);
                  this.font_text.DrawSubstring("$" + this.g_player.m_cash, 0, ("$" + this.g_player.m_cash).length(), var18 + 190, var5, 2);
                  var5 += 17;
                  this.font_text.DrawSubstring(this.g_Text[18], 0, this.g_Text[18].length(), var18, var5, 0);
                  this.font_text.DrawSubstring("" + this.g_player.getTotalRep(), 0, ("" + this.g_player.getTotalRep()).length(), var18 + 190, var5, 2);
                  var6 = 0;

                  for(var7 = 0; var7 < 36; ++var7) {
                     if (this.g_player.m_missionStatus[var7] != 0) {
                        ++var6;
                     }
                  }

                  var5 += 22;
                  this.font_text.DrawSubstring(this.g_Text[24], 0, this.g_Text[24].length(), var18, var5, 0);
                  var5 += 17;
                  this.font_text.DrawSubstring(this.g_Text[25], 0, this.g_Text[25].length(), var18, var5, 0);
                  this.font_text.DrawSubstring("  " + var6 + "/36", 0, ("  " + var6 + "/36").length(), var18 + 190, var5, 2);
                  var5 += 17;
                  this.font_text.DrawSubstring(this.g_Text[26], 0, this.g_Text[26].length(), var18, var5, 0);
                  this.font_text.DrawSubstring("  " + var6 * 100 / 36 + "%", 0, ("  " + var6 * 100 / 36 + "%").length(), var18 + 190, var5, 2);
                  var6 = 0;

                  for(var7 = 0; var7 < 6; ++var7) {
                     if (this.g_player.m_missionStatus[var7] == 1) {
                        ++var6;
                     }
                  }

                  if (var6 == 0) {
                     var6 = 1;
                  }

                  var5 += 22;
                  this.font_text.DrawSubstring(this.g_Text[28], 0, this.g_Text[28].length(), var18, var5, 0);
                  var5 += 17;
                  this.font_text.DrawSubstring(this.g_Text[33], 0, this.g_Text[33].length(), var18, var5, 0);
                  this.font_text.DrawSubstring("  " + var6 + "/" + 6, 0, ("  " + var6 + "/" + 6).length(), var18 + 190, var5, 2);
                  var5 += 17;
                  this.font_text.DrawSubstring(this.g_Text[26], 0, this.g_Text[26].length(), var18, var5, 0);
                  this.font_text.DrawSubstring("  " + var6 * 100 / 6 + "%", 0, ("  " + var6 * 100 / 6 + "%").length(), var18 + 190, var5, 2);
               } else {
                  try {
                     if (!this.menu_bBlacklistLoaded) {
                        this.asset_LoadImage(this.g_MenuImages[2], false);
                        this.asset_LoadImage(778, false);
                        this.menu_bBlacklistLoaded = true;
                     }

                     this.system_FillRect(0, 0, this.system_nCanvasWidth, var1, 16777215);
                     this.asset_DrawImage(this.g_MenuImages[2], 0, 0);

                     for(var4 = 1; var4 < 6 && this.g_player.m_missionStatus[var4] == 1; ++var4) {
                        this.asset_DrawImage(778, 14, 215 - 29 * var4);
                     }
                  } catch (Exception var15) {
                     var15.printStackTrace();
                     AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 919, var15.toString());
                  } catch (Error var16) {
                     AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 919, var16.toString());
                  }
               }
            } else if (this.menuCurrent.m_backgroundStyle == 4) {
               this.system_FillRect(0, 0, this.system_nCanvasWidth, var1, 0);
               this.font_text.SetColor(0, 0, 0);

               try {
                  if (!this.menu_bMissionLoaded) {
                     this.asset_LoadImage(720, false);
                     this.menu_bMissionLoaded = true;
                  }

                  this.asset_DrawImage(720, 0, 0);
               } catch (Exception var11) {
                  var11.printStackTrace();
                  AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 940, var11.toString());
               } catch (Error var12) {
                  AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 940, var12.toString());
               }

               var18 = 18;
               var19 = 1;
               TheGame.NFSMW_Mission var20 = this.g_missions[this.menuCurrent.m_items[this.menuCurrent.m_selected].m_cmd];
               this.font_text.DrawSubstring(this.g_Text[19] + this.g_Text[this.g_missionTypeNames[var20.m_raceType]], 0, (this.g_Text[19] + this.g_Text[this.g_missionTypeNames[var20.m_raceType]]).length(), var18, var19, 0);
               var5 = var19 + 17;
               this.font_text.DrawSubstring(this.g_Text[21] + this.g_Text[this.g_worldsPretty[this.g_trackPaths[var20.m_trackPath].m_nWorld]], 0, (this.g_Text[21] + this.g_Text[this.g_worldsPretty[this.g_trackPaths[var20.m_trackPath].m_nWorld]]).length(), var18, var5, 0);
               var5 += 17;
               this.font_text.DrawSubstring(this.g_Text[18] + var20.m_repWon, 0, (this.g_Text[18] + var20.m_repWon).length(), var18, var5, 0);
               var5 += 17;
               this.font_text.DrawSubstring(this.g_Text[17] + var20.m_cashWon, 0, (this.g_Text[17] + var20.m_cashWon).length(), var18, var5, 0);
               this.font_text.DrawSubstring(this.g_Text[20] + var20.m_nLaps, 0, (this.g_Text[20] + var20.m_nLaps).length(), var18 + 125, var5, 0);
               if (var20.m_nTimeLimit > 0) {
                  var7 = var20.m_nTimeLimit / 1000 % 60;
                  int var8 = var20.m_nTimeLimit / '\uea60';
                  String var9 = "";
                  if (var7 < 10) {
                     var9 = "0";
                  }

                  var9 = var9 + var7;
                  String var10 = "";
                  if (var8 < 10) {
                     var10 = "0";
                  }

                  var10 = var10 + var8;
                  var5 += 20;
                  this.font_text.DrawSubstring(this.g_Text[22], 0, this.g_Text[22].length(), var18, var5, 0);
                  this.font_text.DrawSubstring("" + var10 + ":" + var9, 0, ("" + var10 + ":" + var9).length(), var18 + 125 + 24, var5, 0);
               }

               if (var20.m_raceType == 3 && var20.m_nreqScore > 0) {
                  var5 += 17;
                  this.font_text.DrawSubstring(this.g_Text[23], 0, this.g_Text[23].length(), var18, var5, 0);
                  this.font_text.DrawSubstring("" + var20.m_nreqScore, 0, ("" + var20.m_nreqScore).length(), var18 + 125 + 24, var5, 0);
               }

               var5 += 20;
               if (this.g_player.getTotalRep() < this.g_missions[var20.m_cmd].m_reqRep) {
                  this.system_FillRect(0, (this.system_nCanvasHeight >> 2) - 1, this.system_nCanvasWidth, 19, 0);
                  this.font_ingame.DrawSubstring(this.g_Text[13], 0, this.g_Text[13].length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 1, 1);
               } else if (this.g_missions[var20.m_cmd].m_reqMission != -1) {
                  if (this.g_player.m_missionStatus[this.g_missions[var20.m_cmd].m_reqMission] == 0) {
                     this.system_FillRect(0, (this.system_nCanvasHeight >> 2) - 1, this.system_nCanvasWidth, 19, 0);
                     this.font_ingame.DrawSubstring(this.g_Text[13], 0, this.g_Text[13].length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 1, 1);
                  } else {
                     this.font_text.DrawSubstringWrapped(this.g_Text[this.scriptScenes[var20.m_script][0]], 0, this.g_Text[this.scriptScenes[var20.m_script][0]].length(), this.system_nCanvasWidth - 18, var18, var5, 0, 0);
                     if (this.g_player.m_missionStatus[var20.m_cmd] == 1) {
                        this.system_FillRect(0, (this.system_nCanvasHeight >> 2) - 1, this.system_nCanvasWidth, 19, 0);
                        this.font_ingame.DrawSubstring(this.g_Text[25], 0, this.g_Text[25].length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 1, 1);
                     }
                  }
               } else {
                  this.font_text.DrawSubstringWrapped(this.g_Text[this.scriptScenes[var20.m_script][0]], 0, this.g_Text[this.scriptScenes[var20.m_script][0]].length(), this.system_nCanvasWidth - 18, var18, var5, 0, 0);
                  if (this.g_player.m_missionStatus[var20.m_cmd] == 1) {
                     this.system_FillRect(0, (this.system_nCanvasHeight >> 2) - 1, this.system_nCanvasWidth, 19, 0);
                     this.font_ingame.DrawSubstring(this.g_Text[25], 0, this.g_Text[25].length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 1, 1);
                  }
               }
            }
         }
      } else {
         this.system_FillRect(0, 0, this.system_nCanvasWidth, 60, 0);
         this.system_FillRect(0, var1 - 17, this.system_nCanvasWidth, 32, 0);
         if (this.menuCurrent.m_backgroundStyle == 2) {
            if (this.menuCurrent == this.menuCarRimsSelect) {
               this.car_rot = 2880;
               menu_camerCarAngle = 7;
            } else if (this.menuCurrent == this.menuCarFrontBmprsSelect) {
               this.car_rot = 6560;
               menu_camerCarAngle = 13;
            } else if (this.menuCurrent == this.menuCarRearBmprsSelect) {
               this.car_rot = 800;
               menu_camerCarAngle = 12;
            } else if (this.menuCurrent == this.menuCarSpoilerSelect) {
               this.car_rot = 960;
               menu_camerCarAngle = 19;
            } else {
               menu_camerCarAngle = 17;
            }

            this.car_rot += this.car_rot_offset << 5;
         } else if (this.menuCurrent.m_backgroundStyle == 1 && this.menuCurrent == this.menuCarPaintLayout) {
            menu_camerCarAngle = 16;
         }

         this.menu_DrawCar();
      }

   }

   void menu_ShowConfirmDlg(int var1) {
      if (this.menu_show_mode == 10 || this.menu_show_mode == 11) {
         this.menu_show_mode = this.prevShowMode;
      }

      this.prevShowMode = this.menu_show_mode;
      this.menu_show_mode = 10;
      this.currentConfirm = 0;
      this.currentConfirmString = var1;
   }

   void menu_ShowMessageDlg(int var1) {
      if (this.menu_show_mode == 10 || this.menu_show_mode == 11) {
         this.menu_show_mode = this.prevShowMode;
      }

      this.prevShowMode = this.menu_show_mode;
      this.menu_show_mode = 11;
      this.currentConfirm = 0;
      this.currentConfirmString = var1;
   }

   int menuCmdToPart(int var1) {
      switch(var1) {
      case 4096:
         return 0;
      case 8192:
         return 1;
      case 16384:
         return 2;
      case 32768:
         return 3;
      case 65536:
         return 4;
      case 131072:
         return 5;
      case 262144:
         return 6;
      case 524288:
         return 7;
      case 1048576:
         return 8;
      case 2097152:
         return 9;
      case 4194304:
         return 10;
      case 8388608:
         return 11;
      case 16777216:
         return 12;
      case 33554432:
         return 13;
      case 67108864:
         return 14;
      case 134217728:
         return 15;
      case 268435456:
         return 16;
      default:
         System.out.println("UNKNOWN MENU TYPE can't conver to car part type properly will fail");
         return -1;
      }
   }

   void menu_refreshCar(int var1) {
      this.menu_refreshCar = false;
      this.menu_drawCar = false;
      if (this.g_player.m_missionStatus[var1] == 1) {
         this.menu_drawCar = true;
      } else if (this.g_player.m_equipedCar == var1) {
         this.menu_drawCar = true;
      }

      if (this.menu_drawCar) {
         this.menu_refreshCar = true;
         this.menu_drawCar = false;
         this.menu_nextCar = var1;
         this.menu_carUpdateTime = this.timeInMenu + 2000;
      }

   }

   boolean menu_isMissionLocked(int var1) {
      boolean var2 = false;
      if (this.g_player.m_missionStatus[var1] == 1) {
         var2 = false;
      } else if (this.g_player.getTotalRep() < this.g_missions[var1].m_reqRep) {
         var2 = true;
      } else if (this.g_missions[var1].m_reqMission != -1 && this.g_player.m_missionStatus[this.g_missions[var1].m_reqMission] == 0) {
         var2 = true;
      }

      return var2;
   }

   void menu_setCurrentMenu(TheGame.NFSMW_Menu var1) {
      if (this.menuCurrent != null) {
         this.menuCurrent.freeIcons(false);
      }

      System.gc();
      this.menuCurrent = var1;
      this.menuCurrent.loadIcons(false);
   }

   void menu_showPauseScreen() {
      if (this.menu_startupStage < 4) {
         this.menu_SplashPause = true;
      } else if (!this.menu_IsPaused) {
         this.asset_ChangeVolume(0);
         this.game_StopMusic();
         this.menu_IsPaused = true;
         this.menu_ShowMessageDlg(186);
      }

   }

   void menu_Construct() throws Exception {
      this.menu_startupStage = 1;
      this.menu_sceneLoadStage = 0;
      this.menu_paintCarTimer = 0;
      this.menu_updateCarPaint = false;
      game_msg = new String[42];
      this.menu_intromusicloaded = false;
      this.tryMusicUntil = 0L;
      this.currentColor = 0;
      this.menu_IsPaused = false;
      this.menu_show_mode = 0;
      this.asset_LoadImage(2, false);
      if (this.system_bDemoMode) {
         for(int var1 = 0; var1 < this.g_missions.length; ++var1) {
            if (var1 != 6) {
               this.g_missions[var1].m_reqMission = 0;
            }
         }
      }

      this.menu_nItemsHeight = this.font_text.GetHeight();
      this.menu_nItemsYSpacing = 0;
      this.menu_rowHeight = this.menu_nItemsHeight + this.menu_nItemsYSpacing;
      this.menu_canvasHeight = (this.system_nCanvasHeight - 20 - 17) / this.menu_rowHeight * this.menu_rowHeight;
      this.itemColor = 16776960;
      this.selectedColor = 16777215;
      this.menu_max_row_count = (short)(68 / this.menu_rowHeight);
      this.menu_current_top_row = 0;
   }

   void menu_Destroy() {
      if (this.menu_intromusicloaded) {
         ;
      }

      this.asset_FreeImage(2);
      game_msg = null;
   }

   void menu_Update(int var1) throws Exception {
      if (var1 > 100) {
         var1 = 100;
      }

      this.timeInMenu += var1;
      if (this.menu_startupStage != 2) {
         if (this.menu_startupStage == 3) {
            if (this.menu_hourGlass) {
               this.asset_FreeImage(2);
               this.menu_startupStage = 0;
               this.menu_Start();
            }

         } else {
            int var2;
            if (this.menu_startupStage > 3) {
               if (this.menuCurrent != null) {
                  if (this.menu_nKeyFlags == 49 && this.menuCurrent.m_backgroundStyle == 2) {
                     this.car_rot_offset += 5;
                  } else if (this.menu_nKeyFlags == 51 && this.menuCurrent.m_backgroundStyle == 2) {
                     this.car_rot_offset -= 5;
                  } else if (this.menu_nKeyFlags == 50 && this.menuCurrent.m_backgroundStyle == 2) {
                     this.car_rot_offset = 0;
                  }
               }

               this.car_rot += var1 >> 1;
               this.RefreshCarPaint(var1);
               if (this.menu_refreshCar && this.menu_hourGlass && this.menu_carUpdateTime < this.timeInMenu) {
                  for(var2 = 0; var2 < this.g_cars.length; ++var2) {
                     if (var2 != this.menu_nextCar && this.g_cars[var2] != null) {
                        this.g_cars[var2].free();
                     }
                  }

                  this.menu_car = this.menu_nextCar;
                  TheGame.NFSMW_MenuItem var5 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
                  int var3 = var5.m_type;
                  if ((var3 & 536866816) != 0) {
                     int var4 = this.menuCmdToPart(var3);
                     this.g_cars[this.g_player.m_currentCar].testPart(var4, (byte)var5.m_cmd);
                  } else {
                     this.g_cars[this.menu_car].constructCar(false);
                     this.menu_reflectionMesh = this.g_cars[this.menu_car].m_reflectionMesh;
                     this.g_player.m_carRep = this.g_cars[this.menu_car].m_modStats[3];
                  }

                  this.menu_refreshCar = false;
                  this.menu_hourGlass = false;
                  this.menu_drawCar = true;
               }

               this.menuTimer += var1;
               if (this.goingToNextMenu != 0) {
                  if (this.menuTimer > 100) {
                     this.menuCurrent.m_selected += this.goingToNextMenu;
                     this.goingToNextMenu = 0;
                     this.menu_OnSelectionChange();
                  }

                  return;
               }
            }

            if (this.menu_show_mode < 3) {
               if (this.menu_scrolling) {
                  var2 = this.menu_scrollSpeed * var1 / 1000;
                  this.menu_yScrollOffset -= var2;
                  if (this.menu_yScrollOffset >> 8 <= -this.menu_rowHeight) {
                     this.menu_yScrollOffset = 0;
                     this.menu_ShowDocPage(this.menu_yOffset, this.menu_yDocStart + (this.menu_canvasHeight - this.menu_yCanvasOffset) / this.menu_rowHeight, 1);
                     ++this.menu_yDocStart;
                     this.menu_yOffset += this.menu_rowHeight << 8;
                     if (this.menu_yOffset >> 8 >= this.menu_canvasHeight + this.menu_rowHeight) {
                        this.menu_yOffset = this.menu_yCanvasOffset << 8;
                     }

                     if (this.menu_yDocStart >= this.menu_docLineCount) {
                        this.menu_scrolling = false;
                        this.menu_KeyPressed(589829, 0);
                     }
                  } else {
                     this.menu_ShowDocPage(-(this.menu_canvasHeight << 8), this.menu_yDocStart + (this.menu_canvasHeight - this.menu_yCanvasOffset) / this.menu_rowHeight, 1);
                  }
               } else {
                  this.game_nTimeKeyHeld = (short)(this.game_nTimeKeyHeld + var1);
                  if (this.menu_show_mode != 1) {
                     if (this.game_nTimeKeyHeld > 400) {
                        if (this.game_nKeyPressed == 1) {
                           this.menu_MenuInc();
                        } else if (this.game_nKeyPressed == 2) {
                           this.menu_MenuDec();
                        } else if (this.game_nKeyPressed == 3) {
                           this.menu_MenuPrev();
                        } else if (this.game_nKeyPressed == 4) {
                           this.menu_MenuNext();
                        }
                     }

                     return;
                  }

                  if (this.game_nTimeKeyHeld > 100) {
                     this.game_nTimeKeyHeld = 0;
                     if (this.game_nKeyPressed == 1) {
                        if (this.menu_yDocStart > 0) {
                           --this.menu_yDocStart;
                           this.menu_yOffset -= this.menu_rowHeight << 8;
                           if (this.menu_yOffset >> 8 < 0) {
                              this.menu_yOffset = this.menu_canvasHeight << 8;
                           }

                           this.menu_ShowDocPage(this.menu_yOffset, this.menu_yDocStart, 1);
                        }
                     } else if (this.game_nKeyPressed == 2 && this.menu_yDocStart + this.menu_canvasHeight / this.menu_rowHeight < this.menu_docLineCount) {
                        var2 = this.menu_yOffset + (this.menu_canvasHeight << 8);
                        if (var2 >= this.menu_canvasHeight + this.menu_rowHeight << 8) {
                           var2 -= this.menu_canvasHeight + this.menu_rowHeight << 8;
                        }

                        this.menu_ShowDocPage(var2, this.menu_yDocStart + this.menu_canvasHeight / this.menu_rowHeight, 1);
                        ++this.menu_yDocStart;
                        this.menu_yOffset += this.menu_rowHeight << 8;
                        if (this.menu_yOffset >> 8 >= this.menu_canvasHeight + this.menu_rowHeight) {
                           this.menu_yOffset = 0;
                        }
                     }
                  }
               }

            }
         }
      }
   }

   void menu_Start() throws Exception {
      switch(this.menu_startupStage) {
      case 0:
         this.menu_LoadMainMenu();
         break;
      case 1:
         this.asset_LoadImage(this.g_WarningImgs[this.g_nLanguage], false);
         this.menu_Init();
         break;
      case 2:
         this.asset_LoadImage(this.g_MenuImages[0], false);
         if (!this.menu_SplashPause) {
            this.asset_ChangeVolume(this.system_nVolume);
            this.game_StartMusic(true);
         }
         break;
      case 3:
         this.asset_LoadImage(2, false);
         this.menu_hourGlass = false;
      }

   }

   void menu_Init() throws Exception {
      this.menu_IsPaused = false;
      this.menu_show_mode = 0;
      this.background.setColor(2236964);
      this.menu_xOffset = 3;
      this.menu_scrolling = false;
      this.menu_nDocument = -1;
      this.menu_noRender = false;
      this.menu_scrollSpeed = 3072;
      this.menu_level = -1;
      this.game_CreateBuffer();
      m_CurrentGraphics = this.game_gMapBuffer;
      this.menu_yCanvasOffset = 0;
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_bReRenderSoftKeys = false;
      this.menu_refreshCar(this.g_player.m_currentCar);
      this.menu_car = this.g_player.m_currentCar;
      this.g_cars[this.menu_car].setParts(this.g_player.m_carSetups[this.menu_car]);
   }

   void menu_LoadMainMenu() throws Exception {
      this.menu_Init();
      this.system_SoftkeyFontBackup = this.system_SoftkeyFont;
      this.system_SoftkeyFont = this.font_text;
      this.system_softkeyBackground = 160;
      this.system_softkeyColor = 16777215;
      if (!this.menu_intromusicloaded) {
         this.menu_intromusicloaded = true;
      }

      this.asset_LoadImage(740, false);
      this.asset_LoadImage(736, false);
      this.asset_LoadImage(732, false);
      this.asset_LoadImage(772, false);
      this.asset_LoadImage(768, false);
      this.asset_LoadImage(764, false);
      this.asset_LoadImage(676, false);
      this.asset_LoadImage(672, false);
      this.asset_LoadImage(684, false);
      this.asset_LoadImage(680, false);
      this.asset_LoadImage(958, false);
      this.asset_LoadImage(708, false);
      this.asset_LoadImage(704, false);
      this.asset_LoadImage(700, false);
      this.asset_LoadImage(696, false);
      this.asset_LoadImage(692, false);
      this.asset_LoadImage(688, false);
      this.asset_LoadImage(this.g_MenuImages[1], false);
      this.asset_LoadImage(668, false);
      this.asset_LoadImage(714, false);
      this.asset_LoadImage(916, false);
      if (this.menu_Group.getChildCount() == 0) {
         if (this.scene_FindMintMesh("menu_background") == null) {
            while(this.scene_OpenModel("menu_background", 3, false)) {
               ;
            }
         }

         for(int var2 = 0; var2 < this.trackMeshs.length; ++var2) {
            if (this.trackMeshs[var2] != null) {
               Appearance var1 = this.trackMeshs[var2].getAppearance(0);
               var1.setPolygonMode(this.polygonMode_Persp);
               this.menu_Group.addChild(this.trackMeshs[var2]);
               this.trackMeshs[var2] = null;
            }
         }
      }

      if (this.g_race.m_bMission) {
         this.menu_setCurrentMenu(this.menuMissionTypes);
      } else {
         this.menu_setCurrentMenu(this.menuRoot);
      }

      this.menu_Render();
      this.transition_Start();
      this.game_nKeyPressed = 0;
      this.game_nTimeKeyHeld = 0;
      this.goingToNextMenu = 0;
      this.lastMenuDirection = 0;
      this.menu_hourGlass = false;
      this.menu_startupStage = 4;
      this.menu_car = this.g_player.m_currentCar;
      this.g_cars[this.menu_car].setParts(this.g_player.m_carSetups[this.menu_car]);
      if (this.menu_SplashPause) {
         this.asset_ChangeVolume(this.system_nVolume);
         this.game_StartMusic(true);
         this.menu_SplashPause = false;
      }

   }

   void menu_End() {
      this.menuCurrent = null;
      this.asset_FreeImage(772);
      this.asset_FreeImage(768);
      this.asset_FreeImage(764);
      this.asset_FreeImage(676);
      this.asset_FreeImage(672);
      this.asset_FreeImage(684);
      this.asset_FreeImage(680);
      this.asset_FreeImage(958);
      this.asset_FreeImage(708);
      this.asset_FreeImage(704);
      this.asset_FreeImage(700);
      this.asset_FreeImage(696);
      this.asset_FreeImage(692);
      this.asset_FreeImage(688);
      this.asset_FreeImage(this.g_MenuImages[1]);
      this.asset_FreeImage(668);
      this.asset_FreeImage(714);
      this.asset_FreeImage(916);
      this.menuRoot.freeIcons(true);
      this.menu_reflectionMesh = null;
      this.scene_FreeTextures();
      this.scene_FreeMintMesh("menu_background");
      this.scene_FreeMintMesh("track_01");
      this.scene_FreeMintMesh("track_02");
      this.menu_Group = null;
      this.menu_Group = new Group();
      this.system_SoftkeyFont = this.system_SoftkeyFontBackup;
      this.game_FreeBuffer();
      if (this.system_bSoundOn) {
         ;
      }

      this.scene_timerUpdate = true;
      this.menu_startupStage = 3;
      System.gc();
   }

   void menu_LoadDoc(int var1) throws Exception {
      this.menu_nDocument = var1;
      this.asset_LoadData(var1);
      short var2 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
      this.docID = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);
      this.docStart = (this.asset_DataArray[var1 + 0] & -1) >> 0;
      this.docEnd = this.docStart + (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
      this.menu_docLineCount = this.menu_PreProcessDocPage(true);
      this.menu_docArray = new String[this.menu_docLineCount];
      this.menu_docLineAttr = new int[this.menu_docLineCount];
      this.menu_PreProcessDocPage(false);
      this.menu_yDocStart = 0;
   }

   void menu_FreeDoc() {
      this.menu_docArray = null;
      this.menu_docLineAttr = null;
      this.menu_scrolling = false;
      this.menu_IsPaused = false;
      this.menu_show_mode = 0;
      if (this.menu_nDocument != -1) {
         this.asset_FreeData(this.menu_nDocument);
      }

      this.itemColor = 16776960;
      this.selectedColor = 16777215;
      this.menu_nDocument = -1;
   }

   void menu_InitDoc(int var1, boolean var2, boolean var3) throws Exception {
      this.menu_xOffset = 3;
      this.system_StartDelay();
      this.itemColor = 16777215;
      this.selectedColor = 16776960;
      this.menu_LoadDoc(var1);
      this.menu_scrolling = var2;
      this.menu_IsPaused = false;
      this.menu_show_mode = 1;
      this.menu_yOffset = 0;
      this.menu_yScrollOffset = 0;
      this.game_gMapBuffer.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.game_gMapBuffer.setColor(0);
      this.game_gMapBuffer.fillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      if (var2) {
         this.menu_yDocStart = -this.menu_canvasHeight / this.menu_rowHeight;
         this.menu_yScrollOffset = -(this.menu_rowHeight << 8);
      } else {
         this.menu_ShowDocPage(0, 0, this.menu_canvasHeight / this.menu_rowHeight);
      }

      if (var3) {
         ;
      }

   }

   int menu_PreProcessDocPage(boolean var1) {
      int var2 = this.docStart;
      int var4 = 0;
      char[] var5 = new char[400];
      String var6 = "";
      int var8 = this.menu_xOffset;
      byte var9 = 0;
      byte var10 = 0;
      int var11 = 0;

      while(var2 < this.docEnd) {
         String var7 = null;
         if (!var1) {
            this.menu_docLineAttr[var4] = var11;
         }

         byte var12 = this.asset_DataBufArray[this.docID][var2];
         if (var12 < 0) {
            switch(var12) {
            case -9:
               var7 = "" + m_Midlet.getAppProperty("MIDlet-Version");
               break;
            case -8:
               if (var1) {
                  ++var4;
               } else {
                  this.menu_docArray[var4++] = var6;
               }

               var8 = this.menu_xOffset;
               var6 = "";
               break;
            case -7:
               var10 = 1;
               if (var6.length() > 0 || var4 > 0) {
                  if (var1) {
                     ++var4;
                  } else {
                     this.menu_docArray[var4++] = var6;
                  }

                  var8 = this.menu_xOffset;
                  var6 = "";
               }
               break;
            case -6:
               var10 = 0;
               if (var6.length() > 0 || var4 > 0) {
                  if (var1) {
                     ++var4;
                  } else {
                     this.menu_docArray[var4++] = var6;
                  }

                  var8 = this.menu_xOffset;
                  var6 = "";
               }
               break;
            case -5:
               if (var1) {
                  var4 += 2;
               } else {
                  this.menu_docArray[var4++] = var6;
                  this.menu_docArray[var4++] = "";
               }

               var8 = this.menu_xOffset;
               var6 = "";
            case -4:
            case -3:
            default:
               break;
            case -2:
               var9 = 4;
               break;
            case -1:
               var9 = 2;
            }

            ++var2;
         } else {
            int var13 = 0;
            boolean var14 = false;
            if (this.asset_DataBufArray[this.docID][var2 + 0] < 96) {
               var14 = true;
            }

            do {
               if (this.asset_DataBufArray[this.docID][var2 + 0] < 96) {
                  if (this.asset_DataBufArray[this.docID][var2] != 32) {
                     if (this.asset_DataBufArray[this.docID][var2] == 92) {
                        var5[var13++] = 169;
                     } else if (this.asset_DataBufArray[this.docID][var2] == 91) {
                        var5[var13++] = 8482;
                     } else {
                        var5[var13++] = (char)this.asset_DataBufArray[this.docID][var2];
                     }
                  }

                  ++var2;
                  var14 = true;
               } else {
                  var14 = false;
                  var5[var13++] = (char)((this.asset_DataBufArray[this.docID][var2 + 0] - 96 & 15) << 12 | (this.asset_DataBufArray[this.docID][var2 + 1] - 96 & 15) << 8 | (this.asset_DataBufArray[this.docID][var2 + 2] - 96 & 15) << 4 | (this.asset_DataBufArray[this.docID][var2 + 3] - 96 & 15) << 0);
                  var2 += 4;
               }
            } while(var14 && var2 < this.docEnd && this.asset_DataBufArray[this.docID][var2] > 32);

            if (var13 > 0) {
               if (var14) {
                  var5[var13++] = ' ';
               }

               var7 = new String(var5, 0, var13);
            }
         }

         if (var7 != null) {
            int var3;
            if (this.menu_xOffset < 0) {
               var3 = 0;
            } else {
               var3 = this.font_text.GetSubstringWidth(var7, 0, var7.length());
            }

            if (var8 + var3 >= this.system_nCanvasWidth - this.menu_xOffset - 8) {
               if (!var1) {
                  this.menu_docArray[var4] = var6;
               }

               ++var4;
               var6 = var7;
               var8 = this.menu_xOffset + var3;
               var11 = var9 | var10;
            } else {
               if (var6.length() == 0) {
                  var11 = var9 | var10;
               }

               var8 += var3;
               var6 = var6 + var7;
            }
         }
      }

      if (var6.length() > 0) {
         if (var1) {
            ++var4;
         } else {
            this.menu_docLineAttr[var4] = var11;
            this.menu_docArray[var4++] = var6;
         }
      }

      return var4;
   }

   void menu_ShowDocPage(int var1, int var2, int var3) {
      m_CurrentGraphics = this.game_gMapBuffer;
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      int var4 = var1 >> 8;

      for(int var5 = 0; var5 < var3; ++var5) {
         this.system_FillRect(0, var4, this.system_nCanvasWidth, this.menu_rowHeight, 0);
         if (var2 >= this.menu_docLineCount) {
            break;
         }

         if ((this.menu_docLineAttr[var2] & 1) == 1) {
            if (var2 == 0) {
               this.system_FillRect(3, var4, this.system_nCanvasWidth - 3 - 5, this.menu_rowHeight, 8421504);
            }

            this.font_text.SetColor(this.selectedColor);
         } else {
            this.font_text.SetColor(this.itemColor);
         }

         if (this.menu_docArray[var2] != null && this.menu_docArray[var2].length() > 0) {
            if ((this.menu_docLineAttr[var2] & 2) == 2) {
               this.font_text.DrawSubstring(this.menu_docArray[var2], 0, this.menu_docArray[var2].length(), this.system_nCanvasWidth / 2 + this.menu_xOffset / 2, var4, 1);
            } else {
               this.font_text.DrawSubstring(this.menu_docArray[var2], 0, this.menu_docArray[var2].length(), this.menu_xOffset, var4, 0);
            }
         }

         var4 += this.menu_rowHeight;
         ++var2;
      }

   }

   void menu_KeyReleased(int var1, int var2) {
      this.menu_nKeyFlags = 0;
      this.game_nKeyPressed = 0;
      this.game_nTimeKeyHeld = 0;
   }

   void menu_MenuPrev() {
      if (this.menuCurrent.m_selected > 0) {
         this.goingToNextMenu = -1;
         this.lastMenuDirection = -1;
         this.menuTimer = 0;
      }

   }

   void menu_MenuNext() {
      if (this.menuCurrent.m_selected < this.menuCurrent.m_itemCount - 1) {
         this.goingToNextMenu = 1;
         this.lastMenuDirection = 1;
         this.menuTimer = 0;
      }

   }

   void menu_PointerPressed(int var1, int var2) throws Exception {
      if (this.menu_startupStage == 2) {
         if (this.menu_sceneLoadStage > 2) {
            this.asset_FreeImage(this.g_MenuImages[0]);
            this.menu_startupStage = 3;
            this.menu_Start();
         }

      } else {
         if (this.menu_show_mode == 10) {
            if (var2 > 160 && var2 < 180 && var1 > 55 && var1 < 105) {
               if (this.currentConfirm == 1) {
                  this.menu_show_mode = this.prevShowMode;
                  this.menu_show_mode = 0;
                  this.currentConfirm = 1;
                  this.menu_OnConfirmed();
               } else {
                  this.currentConfirm = 1;
               }
            } else if (var2 > 160 && var2 < 180 && var1 > 135 && var1 < 185) {
               if (this.currentConfirm == 0) {
                  this.menu_show_mode = this.prevShowMode;
               } else {
                  this.currentConfirm = 0;
               }
            }
         } else if (this.menu_show_mode == 11) {
            if (var2 > 160 && var2 < 180 && var1 > 95 && var1 < 145) {
               this.menu_show_mode = this.prevShowMode;
               if (this.menu_IsPaused) {
                  this.menu_IsPaused = false;
                  this.asset_ChangeVolume(this.system_nVolume);
                  this.game_StartMusic(false);
               }
            }
         } else {
            int var3;
            if (this.menu_show_mode == 1) {
               var3 = (this.menu_canvasHeight - 7) * this.menu_yDocStart / this.menu_docLineCount + 20;
               if (var3 > var2 && var1 > 225) {
                  this.game_nKeyPressed = 1;
               } else if (var3 < var2 && var1 > 225) {
                  this.game_nKeyPressed = 2;
               }
            } else if (this.menu_show_mode == 8) {
               var3 = (this.system_nCanvasWidth >> 1) - 88;
               if (var2 > this.system_nCanvasHeight - 58 && var2 < this.system_nCanvasHeight - 58 + 30) {
                  if (var1 > var3 && var1 < var3 + 20) {
                     this.menu_KeyPressed(0, 2);
                  } else if (var1 > var3 + 156 && var1 < var3 + 176) {
                     this.menu_KeyPressed(0, 5);
                  }
               }
            } else {
               TheGame.NFSMW_MenuItem var5 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
               int var4 = var5.m_type;
               if ((var4 & 536866816) != 0) {
                  this.menuCmdToPart(var4);
               } else {
                  if (var2 > 258 && var2 < 290) {
                     if (var1 > 63 && var1 < 117) {
                        this.menu_MenuPrev();
                     } else if (var1 > 127 && var1 < 156) {
                        this.menu_OnExecuteItem();
                     } else if (var1 > 163 && var1 < 228) {
                        this.menu_MenuNext();
                     }
                  }

                  if ((var4 & 1024) != 0 && var2 > 187 && var2 < 204) {
                     if (var1 > 11 && var1 < 28) {
                        this.menu_MenuInc();
                     } else if (var1 > 211 && var1 < 228) {
                        this.menu_MenuDec();
                     }
                  }
               }
            }
         }

      }
   }

   void menu_PointerReleased(int var1, int var2) {
      this.menu_nKeyFlags = 0;
      this.game_nKeyPressed = 0;
      this.game_nTimeKeyHeld = 0;
   }

   void menu_KeyPressed(int var1, int var2) throws Exception {
      this.menu_nKeyFlags = var1;
      this.timeLastKeyPress = this.timeInMenu;
      this.menu_noRender = true;
      if (this.menu_startupStage == 1) {
         if (var1 != -6 && var2 != 8 && var1 != 53) {
            if (!this.menu_scrolling) {
               if (var2 != 1 && var1 != 50) {
                  if (var2 != 6 && var1 != 56) {
                     this.game_nKeyPressed = 0;
                     this.game_nTimeKeyHeld = 0;
                  } else {
                     this.game_nKeyPressed = 2;
                  }
               } else {
                  this.game_nKeyPressed = 1;
               }
            }

         } else {
            this.menu_startupStage = 2;
            this.menu_FreeDoc();
            this.asset_FreeImage(this.g_WarningImgs[this.g_nLanguage]);
            this.menu_Start();
         }
      } else if (this.menu_startupStage == 2) {
         if (this.menu_sceneLoadStage > 2) {
            this.asset_FreeImage(this.g_MenuImages[0]);
            this.menu_startupStage = 3;
            this.menu_Start();
         }

      } else if (this.menu_startupStage != 0 && this.menu_startupStage != 3) {
         if (this.menu_show_mode == 10) {
            if (var1 == -6 || var1 == -7 || var2 == 8 || var1 == 53) {
               this.menu_show_mode = this.prevShowMode;
               if ((var1 == -6 || var2 == 8 || var1 == 53) && this.currentConfirm == 1) {
                  this.menu_show_mode = 0;
                  this.menu_OnConfirmed();
               }
            }

            if ((var2 == 2 || var1 == 52 || var2 == 1 || var1 == 50) && this.currentConfirm < 1) {
               ++this.currentConfirm;
            }

            if ((var2 == 5 || var1 == 54 || var2 == 6 || var1 == 56) && this.currentConfirm > 0) {
               --this.currentConfirm;
            }
         } else if (this.menu_show_mode == 11) {
            if (var1 == -6 || var1 == -7 || var2 == 8 || var1 == 53) {
               this.menu_show_mode = this.prevShowMode;
               if (this.menu_IsPaused) {
                  this.menu_IsPaused = false;
                  this.asset_ChangeVolume(this.system_nVolume);
                  this.game_StartMusic(false);
               }
            }
         } else if (this.menu_show_mode == 1) {
            boolean var3 = false;
            if (!this.menu_scrolling) {
               if (var2 != 1 && var1 != 50) {
                  if (var2 != 6 && var1 != 56) {
                     this.game_nKeyPressed = 0;
                     this.game_nTimeKeyHeld = 0;
                  } else {
                     this.game_nKeyPressed = 2;
                  }
               } else {
                  this.game_nKeyPressed = 1;
               }
            }

            if (var3 || var1 == -7 || var1 == 589824 || var1 == 589829) {
               this.menu_FreeDoc();
               this.scene_nCurrentScene = 4096;
            }
         } else {
            if (var2 != 2 && var1 != 52) {
               if (var2 == 5 || var1 == 54) {
                  this.menu_MenuNext();
                  this.game_nKeyPressed = 4;
                  this.game_nTimeKeyHeld = 0;
               }
            } else {
               this.menu_MenuPrev();
               this.game_nKeyPressed = 3;
               this.game_nTimeKeyHeld = 0;
            }

            if (var2 == 1 || var1 == 50) {
               this.menu_MenuInc();
               this.game_nKeyPressed = 1;
               this.game_nTimeKeyHeld = 0;
            }

            if (var2 == 6 || var1 == 56) {
               this.menu_MenuDec();
               this.game_nKeyPressed = 2;
               this.game_nTimeKeyHeld = 0;
            }

            if (var1 == -6 || var2 == 8 || var1 == 53) {
               this.menu_OnExecuteItem();
            }

            if (var1 == -7) {
               if (this.menu_level == 0) {
                  this.system_bExit = true;
               } else {
                  this.menu_OnBack();
               }
            }

            if (!this.system_bDemoMode) {
               if (this.cheatPosition == this.cheatCode.length) {
                  switch(var1) {
                  case 48:
                     this.g_player.m_cash += 100000;
                     this.g_player.m_rep += 100000;
                     break;
                  case 55:
                     this.g_player.m_missionStatus[5] = 1;
                  case 54:
                     this.g_player.m_rep += this.g_missions[5].m_reqRep;
                     this.g_player.m_missionStatus[4] = 1;
                  case 53:
                     this.g_player.m_rep += this.g_missions[4].m_reqRep;
                     this.g_player.m_missionStatus[3] = 1;
                  case 52:
                     this.g_player.m_rep += this.g_missions[3].m_reqRep;
                     this.g_player.m_missionStatus[2] = 1;
                  case 51:
                     this.g_player.m_rep += this.g_missions[2].m_reqRep;
                     this.g_player.m_missionStatus[1] = 1;
                  case 50:
                     this.g_player.m_rep += this.g_missions[1].m_reqRep;
                     this.g_player.m_missionStatus[0] = 1;
                  case 49:
                     this.g_player.m_rep += this.g_missions[0].m_reqRep;
                  }

                  this.cheatPosition = 0;
               } else if (var1 == this.cheatCode[this.cheatPosition]) {
                  ++this.cheatPosition;
               } else {
                  this.cheatPosition = 0;
               }
            }
         }

         this.menu_noRender = false;
      }
   }

   void menu_DrawCar() {
      this.game_cameraAngZ = 0;
      TheGame.NFSMW_CarAppearance var1 = this.g_cars[this.menu_car];
      this.trans.setIdentity();
      this.trans.postTranslate(0.0F, -0.2F, -4.5F);
      this.trans.postRotate((float)menu_camerCarAngle, 1.0F, 0.0F, 0.0F);
      this.trans.postRotate((float)(this.car_rot >> 5), 0.0F, 1.0F, 0.0F);
      this.camera.setPerspective(80.0F, 1.0F, 0.5F, 1000.0F);
      this.camera.setTransform(this.trans);

      try {
         this.scene_g3d.bindTarget(m_CurrentGraphics, true, 2);
         this.background.setColor(11386309);
         this.background.setColorClearEnable(true);
         this.background.setDepthClearEnable(true);
         this.scene_g3d.clear(this.background);
         this.scene_g3d.setCamera(this.camera, (Transform)null);
         this.scene_g3d.render(this.menu_Group, this.trans);
         if (this.menu_drawCar) {
            var1.m_lightAngle = (this.car_rot >> 5) * 256 / 360 * 256 * 256 & 16777215;
            if (!var1.render(this.trans, (TheGame.Car)null, true, true, false)) {
               this.menu_refreshCar(this.menu_car);
            }
         }

         if (this.menu_reflectionMesh != null) {
            Appearance var2 = this.menu_reflectionMesh.getAppearance(0);
            Texture2D var3 = var2.getTexture(0);
            if (var3 != null) {
               var3.setOrientation((float)(-this.car_rot >> 5), 0.0F, 0.0F, 1.0F);
            }

            this.scene_g3d.render(this.menu_reflectionMesh, this.trans);
         }
      } finally {
         this.scene_g3d.releaseTarget();
      }

      if (this.menu_refreshCar && this.menu_carUpdateTime < this.timeInMenu) {
         this.asset_DrawImage(2, (this.system_nCanvasWidth >> 1) - 8, (this.system_nCanvasHeight >> 1) - 12);
         this.menu_hourGlass = true;
      }

   }

   void DrawCarStats() {
      boolean var1 = false;
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      float var5 = 0.0F;
      boolean var6 = false;
      this.font_text.SetColor(16777215);
      int var8;
      int var9;
      if (this.menuCurrent == this.menuQRCarSelect) {
         this.menu_tmpCarStatData = this.g_cars[this.g_player.m_currentCar].m_modStats;
      } else {
         for(var8 = 0; var8 < 4; ++var8) {
            this.menu_tmpCarStatData[var8] = this.g_cars[this.g_player.m_currentCar].m_baseStats[var8];

            for(var9 = 0; var9 < 17; ++var9) {
               this.menu_tmpCarStatData[var8] += this.g_carParts[var9][this.g_cars[this.g_player.m_currentCar].m_parts[var9]].m_stats[var8];
            }

            if (this.menu_tmpCarStatData[var8] < 0) {
               this.menu_tmpCarStatData[var8] = 0;
            }

            if (this.menu_tmpCarStatData[var8] > this.stats_max[var8]) {
               this.menu_tmpCarStatData[var8] = this.stats_max[var8];
            }
         }
      }

      var8 = 0;
      boolean var17 = false;
      int var10 = 30;
      if (this.g_nLanguage == 0) {
         var10 -= 5;
      }

      for(int var11 = 0; var11 < 4; ++var11) {
         int var13 = this.menu_tmpCarStatData[var11];
         int var12 = this.g_cars[this.g_player.m_currentCar].m_modStats[var11];
         int var15 = this.stats_max[var11];
         this.font_text.DrawSubstring(this.g_statNames[var11], 0, this.g_statNames[var11].length(), var10 - 10, var8, 1);
         int var7 = 8355711;
         if (this.g_nLanguage == 0) {
            this.system_FillRect(var10 + 2, var8 + 5, 50, 3, var7);
         } else {
            this.system_FillRect(var10 + 10, var8 + 5, 50, 3, var7);
         }

         var5 = (float)var13 / (float)var15;
         var5 *= 50.0F;
         int var16 = (int)var5;
         var7 = 16776960;
         if (this.g_nLanguage == 0) {
            this.system_FillRect(var10 + 2, var8 + 5, var16, 3, var7);
         } else {
            this.system_FillRect(var10 + 10, var8 + 5, var16, 3, var7);
         }

         var9 = var10 + var16;
         int var14 = var12 - var13;
         var5 = (float)var14 / (float)var15;
         var5 *= 50.0F;
         var16 = (int)var5;
         var7 = 16744192;
         if (var14 < 0) {
            var9 += var16;
            var7 = 16711680;
         }

         if (var9 < var10) {
            var9 = var10;
         }

         this.system_FillRect(var9, var8 + 5, Math.abs(var16), 3, var7);
         var8 += 16;
      }

   }

   void menu_Render() {
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      int var1 = this.system_nCanvasHeight;
      int var4;
      int var17;
      if (this.menu_startupStage == 1 && this.menu_show_mode != 1) {
         this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 0);

         try {
            this.menu_InitDoc(this.g_docs[0], false, false);
         } catch (Exception var14) {
            var14.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 3212, var14.toString());
         } catch (Error var15) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 3212, var15.toString());
         }
      } else if (this.menu_startupStage == 2) {
         this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 0);
         this.asset_DrawImage(this.g_MenuImages[0], 0, 0);
         short var3 = 250;
         if (this.menu_sceneLoadStage < 2) {
            this.font_ingame.DrawSubstringWrapped(this.g_Text[29], 0, this.g_Text[29].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, var3, 1, 0);
         } else if (this.menu_sceneLoadStage > 2) {
            this.font_ingame.DrawSubstringWrapped(this.g_Text[12], 0, this.g_Text[12].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, var3, 1, 0);
         }

         if (this.menu_sceneLoadStage == 1) {
            while(this.scene_OpenModel("menu_background", 3, false)) {
               ;
            }
         }

         ++this.menu_sceneLoadStage;
      } else if (this.menu_startupStage == 3) {
         this.asset_DrawImage(2, (this.system_nCanvasWidth >> 1) - 8, (this.system_nCanvasHeight >> 1) - 12);
         this.menu_hourGlass = true;
      } else if (this.scene_nCurrentScene == 4098 && this.menu_show_mode != 1) {
         this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 0);

         try {
            this.menu_InitDoc(this.g_docs[5], true, true);
         } catch (Exception var12) {
            var12.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 3253, var12.toString());
         } catch (Error var13) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu_share.hpp", 3253, var13.toString());
         }
      } else {
         int var5;
         int var6;
         if (this.menu_show_mode == 1) {
            int var2 = this.menu_yCanvasOffset + (this.menu_yScrollOffset >> 8);
            var17 = this.menu_yOffset >> 8;
            var5 = this.menu_yDocStart;
            this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 0);

            for(var6 = 0; var6 < (this.menu_canvasHeight - this.menu_yCanvasOffset) / this.menu_rowHeight + 1; ++var6) {
               var4 = this.menu_canvasHeight - var2;
               if (var4 > this.menu_rowHeight) {
                  var4 = this.menu_rowHeight;
               }

               if (var5 >= 0) {
                  if (var6 == 0) {
                     m_CurrentGraphics.setClip(0, this.menu_yCanvasOffset + 20, this.system_nCanvasWidth, var4);
                  } else {
                     m_CurrentGraphics.setClip(0, var2 + 20, this.system_nCanvasWidth, var4);
                  }

                  m_CurrentGraphics.drawImage(this.game_imageMapBuffer, 0, var2 - var17 + 20, 20);
               } else {
                  ++var5;
               }

               var17 += this.menu_rowHeight;
               var2 += this.menu_rowHeight;
               if (var17 >= this.menu_canvasHeight + this.menu_rowHeight) {
                  var17 = this.menu_yCanvasOffset;
               }
            }

            if (this.menu_docLineCount * this.menu_rowHeight > this.menu_canvasHeight && !this.menu_scrolling) {
               this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
               this.system_FillRect(this.system_nCanvasWidth - 4, 20, 3, this.menu_canvasHeight, 16711680);
               var2 = (this.menu_canvasHeight - 7) * this.menu_yDocStart / this.menu_docLineCount + 20;
               var6 = (this.menu_canvasHeight - 7) * (this.menu_canvasHeight / this.menu_rowHeight) / this.menu_docLineCount;
               if (var6 > this.menu_canvasHeight - 7) {
                  var6 = this.menu_canvasHeight - 7;
               }

               this.system_FillRect(this.system_nCanvasWidth - 3, 2 + var2, 1, var6, 16777215);
            }

            if (this.menu_startupStage == 1) {
               this.asset_DrawImage(this.g_WarningImgs[this.g_nLanguage], 0, 0);
               this.drawSoftKey(6, 0);
            } else {
               this.drawSoftKey(4, 1);
            }
         } else {
            if (this.menuCurrent == null) {
               return;
            }

            this.system_SetClip(0, 0, this.system_nCanvasWidth, var1);
            this.menu_DrawMenuBG(var1);
            TheGame.NFSMW_MenuItem var18 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
            var4 = var18.m_type;
            if (this.menuCurrent.m_backgroundStyle != 4 && this.menuCurrent.m_backgroundStyle != 6) {
               this.system_SetClip(0, 0, this.system_nCanvasWidth, var1);
               this.system_FillRect(0, 0, this.system_nCanvasWidth, 18, 0);
               this.system_FillRect(0, 18, 91, 37, 0);
               this.asset_DrawImage(740, 91, 18);
               this.asset_DrawImage(736, 0, 55);
               this.asset_DrawImage(732, 0, 83);
               boolean var20 = true;
               if (this.menuCurrent == this.menuCareer || this.menuCurrent == this.menuMissionTypes || this.menuCurrent == this.menuCustomize || this.menuCurrent == this.menuCustomizeVis || this.menuCurrent == this.menuCustomizePerf || this.menuCurrent == this.menuCarPaint) {
                  var20 = false;
                  this.font_text.SetColor(16777215);
                  this.font_text.DrawSubstring(this.g_Text[17] + this.g_player.m_cash, 0, (this.g_Text[17] + this.g_player.m_cash).length(), this.system_nCanvasWidth - 122, 0, 0);
                  if (this.menu_drawCar) {
                     this.font_text.DrawSubstring(this.g_Text[18] + this.g_player.getTotalRep(), 0, (this.g_Text[18] + this.g_player.getTotalRep()).length(), this.system_nCanvasWidth - 122, 16, 0);
                  }
               }

               if ((var4 & 32) != 0) {
                  var20 = false;
                  this.font_text.SetColor(16777215);
                  if (this.g_player.m_equipedCar == var18.m_cmd) {
                     this.font_text.DrawSubstring(this.g_Text[30], 0, this.g_Text[30].length(), this.system_nCanvasWidth - 10, 0, 2);
                  } else if (this.g_player.m_missionStatus[var18.m_cmd] == 1) {
                     this.font_text.DrawSubstring(this.g_Text[31], 0, this.g_Text[31].length(), this.system_nCanvasWidth - 10, 0, 2);
                  } else {
                     this.font_ingame.DrawSubstring(this.g_Text[13], 0, this.g_Text[13].length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 1, 1);
                  }
               }

               if ((var4 & 536866816) != 0) {
                  var20 = false;
                  var4 = this.menuCmdToPart(var4);
                  TheGame.NFSMW_CarPart var22 = this.g_carParts[var4][var18.m_cmd];
                  if (this.g_cars[this.g_player.m_currentCar].m_cmd == 5 && (var4 == 9 || var4 == 8 || var4 == 6)) {
                     this.font_text.SetColor(16777215);
                     this.font_text.DrawSubstring(this.g_Text[32], 0, this.g_Text[32].length(), this.system_nCanvasWidth - 10, 0, 2);
                  } else if (this.g_player.m_partStatus[var4][var18.m_cmd] == 1) {
                     this.font_text.SetColor(16777215);
                     this.font_text.DrawSubstring(this.g_Text[33], 0, this.g_Text[33].length(), this.system_nCanvasWidth - 10, 0, 2);
                     if (this.g_cars[this.g_player.m_currentCar].isPartInstalled(var4, (byte)var18.m_cmd)) {
                        this.font_text.SetColor(16777215);
                        this.font_text.DrawSubstring(this.g_Text[34], 0, this.g_Text[34].length(), this.system_nCanvasWidth - 10, 16, 2);
                     }
                  } else if (this.g_cars[this.g_player.m_currentCar].isPartInstalled(var4, (byte)var18.m_cmd)) {
                     this.font_text.SetColor(16777215);
                     this.font_text.DrawSubstring(this.g_Text[36], 0, this.g_Text[36].length(), this.system_nCanvasWidth - 10, 0, 2);
                     this.font_text.SetColor(16777215);
                     this.font_text.DrawSubstring(this.g_Text[34], 0, this.g_Text[34].length(), this.system_nCanvasWidth - 10, 16, 2);
                  } else {
                     if (this.g_player.m_cash < var22.m_cost) {
                        this.font_text.SetColor(16711680);
                     } else {
                        this.font_text.SetColor(16777215);
                     }

                     this.font_text.DrawSubstring(this.g_Text[37] + var22.m_cost, 0, (this.g_Text[37] + var22.m_cost).length(), this.system_nCanvasWidth - 10, 0, 2);
                     this.font_text.DrawSubstring(this.g_Text[38] + this.g_player.m_cash, 0, (this.g_Text[38] + this.g_player.m_cash).length(), this.system_nCanvasWidth - 10, 16, 2);
                  }

                  var4 = var18.m_type;
               }

               if (this.menuCurrent.m_backgroundStyle == 2 || this.menuCurrent.m_backgroundStyle == 1 || this.menuCurrent.m_backgroundStyle == 6 || this.menuCurrent.m_backgroundStyle == 3) {
                  var20 = false;
               }

               if (var20) {
                  this.asset_DrawImage(this.g_MenuImages[1], 14, 0);
               }

               if (this.menuCurrent.m_backgroundStyle == 3) {
                  this.asset_DrawImage(this.g_MenuImages[1], 14, 0);
               }
            }

            int var7;
            int var28;
            if (this.menuCurrent.m_backgroundStyle == 3) {
               var5 = this.g_player.m_LapTimes[this.menuTrackSelect.m_selected];
               if (var5 < 0) {
                  var5 = 0;
               }

               var6 = var5 / 1000 % 60;
               var7 = var5 / '\uea60';
               String var8 = "";
               if (var6 < 10) {
                  var8 = "0";
               }

               var8 = var8 + var6;
               String var9 = "";
               if (var7 < 10) {
                  var9 = "0";
               }

               var9 = var9 + var7;
               String var10 = "" + var9 + ":" + var8;
               byte var11 = 90;
               this.font_text.SetColor(16777215);
               this.font_text.DrawSubstring(this.g_Text[39], 0, this.g_Text[39].length(), 15, var11, 0);
               var28 = var11 + 14;
               this.font_text.DrawSubstring("" + this.g_tracks[this.menuTrackSelect.m_selected].m_trackLength + " KM", 0, ("" + this.g_tracks[this.menuTrackSelect.m_selected].m_trackLength + " KM").length(), 15, var28, 0);
               var28 += 18;
               this.font_text.DrawSubstring(this.g_Text[45], 0, this.g_Text[45].length(), 15, var28, 0);
               var28 += 14;
               this.font_text.DrawSubstring(var10, 0, var10.length(), 15, var28, 0);
               var28 += 18;
               this.font_text.DrawSubstring(this.g_Text[20] + " 3", 0, (this.g_Text[20] + " 3").length(), 15, var28, 0);
            }

            this.system_SetClip(0, 0, this.system_nCanvasWidth, var1);
            this.system_FillRect(0, this.system_nCanvasHeight - 20, 52, 20, 0);
            this.system_FillRect(52, this.system_nCanvasHeight - 63, this.system_nCanvasWidth, 63, 0);
            this.asset_DrawImage(772, 0, this.system_nCanvasHeight - 20 - 93);
            this.asset_DrawImage(768, 52, this.system_nCanvasHeight - 63 - 46);
            if ((this.menuCurrent.m_backgroundStyle == 2 || this.menuCurrent.m_backgroundStyle == 1) && this.menu_drawCar) {
               this.DrawCarStats();
            }

            if (this.menuCurrent.m_items[this.menuCurrent.m_selected].m_type != 0 && this.menuCurrent.m_items[this.menuCurrent.m_selected].m_type != 1024) {
               this.drawSoftKey(3, 0);
            }

            if (this.menuCurrent == this.menuRoot) {
               this.drawSoftKey(5, 1);
            } else {
               this.drawSoftKey(4, 1);
            }

            boolean var16 = true;
            if (this.menu_show_mode == 3) {
               var16 = true;
               this.font_text.SetColor(this.itemColor);
               this.font_text.DrawSubstring(this.menu_msg, 0, this.menu_msg.length(), this.system_nCanvasWidth / 2, this.system_nCanvasHeight / 2 - this.menu_rowHeight, 1);
               return;
            }

            this.font_headings.DrawSubstring(this.g_Text[this.menuCurrent.m_title], 0, this.g_Text[this.menuCurrent.m_title].length(), this.system_nCanvasWidth - 4, this.system_nCanvasHeight - 58 - 30, 2);
            if (this.menu_show_mode == 8) {
               if ((var4 & 536866816) != 0) {
                  var4 = this.menuCmdToPart(var4);
                  if (var4 == 1 || var4 == 2 || var4 == 3 || var4 == 4) {
                     var5 = (this.system_nCanvasWidth >> 1) - 88;
                     this.asset_DrawImage(714, var5, this.system_nCanvasHeight - 58 + 2);
                     this.system_DrawRect(var5 + var18.m_cmd * 10 + 19, this.system_nCanvasHeight - 58 + 2 + 1, 9, 24, 16756288);
                     if ((this.scene_timer >> 8 & 1) == 1) {
                        this.asset_DrawImage(708, var5 + 3, this.system_nCanvasHeight - 58 + 10);
                        this.asset_DrawImage(696, var5 + 159, this.system_nCanvasHeight - 58 + 10);
                     } else {
                        this.asset_DrawImage(704, var5 + 3, this.system_nCanvasHeight - 58 + 10);
                        this.asset_DrawImage(692, var5 + 159, this.system_nCanvasHeight - 58 + 10);
                     }
                  }

                  if ((var4 == 2 || var4 == 3) && this.g_cars[this.g_player.m_currentCar].m_parts[0] == 0) {
                     this.font_ingame.DrawSubstring(this.g_Text[13], 0, this.g_Text[13].length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 1, 1);
                  }
               }

               return;
            }

            byte var23 = 95;
            var6 = this.system_nCanvasHeight - 58 - 8;
            var7 = this.menuCurrent.m_selected - 1;
            if (var7 >= 0) {
               if (this.menuTimer < 300 && this.lastMenuDirection == -1) {
                  this.asset_DrawImage(700, var23 - 23, this.system_nCanvasHeight - 58 + 5);
               } else if ((this.scene_timer >> 8 & 1) == 1) {
                  this.asset_DrawImage(708, var23 - 23, this.system_nCanvasHeight - 58 + 5);
               } else {
                  this.asset_DrawImage(704, var23 - 23, this.system_nCanvasHeight - 58 + 5);
               }

               if (this.goingToNextMenu != 0) {
                  this.asset_DrawImage(958, var23, var6);
               } else {
                  this.asset_DrawImage(this.menuCurrent.m_items[var7].m_icon, var23, var6);
                  this.asset_DrawImage(668, var23, var6);
               }
            }

            ++var7;
            var5 = var23 + 35;
            if (this.goingToNextMenu != 0) {
               this.asset_DrawImage(958, var5, var6);
               this.asset_DrawImage(954, var5 + 17 - 15, var6 + 31);
            } else {
               this.asset_DrawImage(this.menuCurrent.m_items[var7].m_icon, var5, var6);
               this.font_text.SetColor(this.selectedColor);
               if (this.menuCurrent != this.menuMissionTypes) {
                  if (this.menuCurrent == this.menuProfile) {
                     if (this.g_player.m_profileNum == this.menuCurrent.m_selected) {
                        this.font_text.SetColor(255, 255, 0);
                     }

                     this.font_text.DrawSubstring(this.g_Text[this.menuCurrent.m_items[var7].m_text], 0, this.g_Text[this.menuCurrent.m_items[var7].m_text].length(), var5 + 12, var6 + 30, 1);
                  } else if (this.menuCurrent != this.menuMissionCircuits && this.menuCurrent != this.menuMissionCheckpoints && this.menuCurrent != this.menuMissionKnockout && this.menuCurrent != this.menuMissionSpeedCam && this.menuCurrent != this.menuMissionOutrun && this.menuCurrent != this.menuMissionChallenge) {
                     this.font_text.DrawSubstring(this.g_Text[this.menuCurrent.m_items[var7].m_text], 0, this.g_Text[this.menuCurrent.m_items[var7].m_text].length(), var5 + 12, var6 + 30, 1);
                  } else {
                     this.font_text.DrawSubstring(this.g_Text[this.menuCurrent.m_items[var7].m_text] + " " + (this.menuCurrent.m_selected + 1), 0, (this.g_Text[this.menuCurrent.m_items[var7].m_text] + " " + (this.menuCurrent.m_selected + 1)).length(), var5 + 12, var6 + 30, 1);
                  }
               } else {
                  int var24 = 0;
                  int var26 = 0;
                  if (this.menuCurrent.m_items[var7].m_subMenu != null) {
                     TheGame.NFSMW_Menu var27 = this.menuCurrent.m_items[var7].m_subMenu;

                     for(var28 = 0; var28 < 6; ++var28) {
                        if (!this.menu_isMissionLocked(var27.m_items[var28].m_cmd)) {
                           ++var26;
                           if (this.g_player.m_missionStatus[var27.m_items[var28].m_cmd] == 1) {
                              ++var24;
                           }
                        }
                     }
                  }

                  this.font_text.DrawSubstring(this.g_Text[this.menuCurrent.m_items[var7].m_text] + " " + var24 + "/" + var26, 0, (this.g_Text[this.menuCurrent.m_items[var7].m_text] + " " + var24 + "/" + var26).length(), var5 + 12, var6 + 30, 1);
               }
            }

            this.asset_DrawImage(676, var5 - 3, var6 - 1);
            this.asset_DrawImage(672, var5 + 23 - 2, var6 - 1);
            this.asset_DrawImage(680, var5 + 23 - 2, var6 + 26 - 4);
            this.asset_DrawImage(684, var5 - 3, var6 + 26 - 4);
            ++var7;
            if (var7 < this.menuCurrent.m_itemCount) {
               var5 += 35;
               var6 = this.system_nCanvasHeight - 58 - 8;
               if (this.goingToNextMenu != 0) {
                  this.asset_DrawImage(958, var5, var6);
               } else {
                  this.asset_DrawImage(this.menuCurrent.m_items[var7].m_icon, var5, var6);
                  this.asset_DrawImage(668, var5, var6);
               }

               if (this.menuTimer < 300 && this.lastMenuDirection == 1) {
                  this.asset_DrawImage(688, var5 + 35, this.system_nCanvasHeight - 58 + 5);
               } else if ((this.scene_timer >> 8 & 1) == 1) {
                  this.asset_DrawImage(696, var5 + 35, this.system_nCanvasHeight - 58 + 5);
               } else {
                  this.asset_DrawImage(692, var5 + 35, this.system_nCanvasHeight - 58 + 5);
               }
            }
         }
      }

      if (this.menu_show_mode == 10 || this.menu_show_mode == 11) {
         boolean var19 = true;
         var4 = this.system_nCanvasHeight - 58 - 8;
         this.system_FillRect(19, 69, 202, 115, 16777215);
         this.system_FillRect(20, 70, 200, 95, 2631720);
         this.system_FillRect(20, 160, 200, 23, 6316128);
         this.font_text.SetColor(this.itemColor);
         this.font_text.DrawSubstringWrapped(this.g_Text[this.currentConfirmString], 0, this.g_Text[this.currentConfirmString].length(), this.system_nCanvasWidth - 40, this.system_nCanvasWidth / 2, 73, 1, -5);
         short var21;
         if (this.menu_show_mode == 10) {
            this.font_text.SetColor(this.selectedColor);
            int[] var25 = new int[]{116, 58};
            var25[1] = this.system_nCanvasWidth / 3;
            var25[0] = var25[1] * 2;
            var21 = 164;
            this.system_FillRect(var25[this.currentConfirm] - 20, var21 - 2, 40, 19, 0);
            this.font_text.DrawSubstring(this.g_Text[1], 0, this.g_Text[1].length(), var25[0], var21, 1);
            this.font_text.DrawSubstring(this.g_Text[0], 0, this.g_Text[0].length(), var25[1], var21, 1);
         } else {
            this.font_text.SetColor(this.selectedColor);
            var17 = this.system_nCanvasWidth >> 1;
            var21 = 164;
            this.system_FillRect(var17 - 20, var21 - 2, 40, 19, 0);
            this.font_text.DrawSubstring(this.g_Text[2], 0, this.g_Text[2].length(), var17, var21, 1);
         }
      }

   }

   void RefreshCarPaint(int var1) {
      if (this.menuCurrent != null) {
         TheGame.NFSMW_MenuItem var2 = this.menuCurrent.m_items[this.menuCurrent.m_selected];
         int var3 = var2.m_type;
         if ((var3 & 536866816) != 0) {
            int var4 = this.menuCmdToPart(var3);
            if (this.menu_updateCarPaint) {
               this.menu_paintCarTimer += var1;
               if (this.menu_paintCarTimer > 700) {
                  byte var5 = this.g_cars[this.g_player.m_currentCar].m_parts[1];
                  byte var6 = this.g_cars[this.g_player.m_currentCar].m_parts[2];
                  byte var7 = this.g_cars[this.g_player.m_currentCar].m_parts[3];
                  if (var4 == 0) {
                     this.g_cars[this.g_player.m_currentCar].m_parts[1] = 0;
                     this.g_cars[this.g_player.m_currentCar].m_parts[2] = 4;
                     this.g_cars[this.g_player.m_currentCar].m_parts[3] = 13;
                  }

                  this.g_cars[this.g_player.m_currentCar].testPart(var4, (byte)var2.m_cmd);
                  this.g_cars[this.g_player.m_currentCar].m_parts[1] = (byte)var5;
                  this.g_cars[this.g_player.m_currentCar].m_parts[2] = (byte)var6;
                  this.g_cars[this.g_player.m_currentCar].m_parts[3] = (byte)var7;
                  this.menu_updateCarPaint = false;
                  this.menu_paintCarTimer = 0;
               }
            }
         }
      }

   }

   void transition_Start() {
      this.firstUpdate = true;
      this.transitionDoneNextPaint = false;
      this.system_bScreenDirty = false;
      this.scene_nCurrentScene |= 32768;
      fade_block_width = this.system_nCanvasWidth / 4;
      fade_block_height = this.system_nCanvasHeight / 4;
      if ((fade_block_width & 1) == 1) {
         ++fade_block_width;
      }

      if ((fade_block_height & 1) == 1) {
         ++fade_block_height;
      }

      fade_x_offset = fade_block_width / 2;
      fade_y_offset = fade_block_height / 2;
      fade_max_distance = fade_x_offset > fade_y_offset ? fade_x_offset : fade_y_offset;
      realMaxCols = this.system_nCanvasWidth / fade_block_width + (this.system_nCanvasWidth % fade_block_width == 0 ? 0 : 1);
      realMaxRows = this.system_nCanvasHeight / fade_block_height + (this.system_nCanvasHeight % fade_block_height == 0 ? 0 : 1);
      fade_max_block = realMaxCols * realMaxRows;
      fadeDistance = new int[fade_max_block];

      for(int var1 = 0; var1 < fade_max_block; ++var1) {
         fadeDistance[var1] = -(var1 << 8);
      }

      this.transition_done = false;
   }

   void transition_End() {
      this.scene_nCurrentScene &= -32769;
      fadeDistance = null;
      if ((this.scene_nCurrentScene & 4096) == 4096) {
         this.system_bReRenderSoftKeys = true;
      }

   }

   void transition_KeyPressed(int var1, int var2) throws Exception {
      if (var1 == 589826 || var2 == 8 || var1 == 53) {
         this.transitionDoneNextPaint = true;
      }

   }

   void transition_Update(int var1) throws Exception {
      if (this.firstUpdate) {
         var1 = 0;
         this.firstUpdate = false;
      }

      if (this.transition_done) {
         this.transition_End();
      } else {
         int var2 = 0;

         for(int var3 = 0; var3 < fade_max_block; ++var3) {
            fadeDistance[var3] += 30720 * var1 / 1000;
            if (fadeDistance[var3] >> 8 >= fade_max_distance) {
               fadeDistance[var3] = fade_max_distance << 8;
               ++var2;
            }
         }

         if (var2 >= fade_max_block) {
            this.transition_done = true;
         }

      }
   }

   void transition_Render() {
      int var2 = fade_y_offset;
      int var3 = 0;

      for(int var4 = 0; var4 < realMaxRows; ++var4) {
         int var1 = fade_x_offset;

         for(int var5 = 0; var5 < realMaxCols; ++var5) {
            if (fadeDistance[var3] >> 8 > 0 && fadeDistance[var3] >> 8 <= fade_max_distance) {
               m_CurrentGraphics.setClip(var1 - (fadeDistance[var3] >> 8), var2 - (fadeDistance[var3] >> 8), fadeDistance[var3] >> 8 << 1, fadeDistance[var3] >> 8 << 1);
               m_CurrentGraphics.drawImage(this.game_imageMapBuffer, 0, 0, 0);
            }

            var1 += fade_block_width;
            ++var3;
         }

         var2 += fade_block_height;
      }

   }

   public int ApproximateMagnitude(int var1, int var2) {
      if (var1 < 0) {
         var1 = -var1;
      }

      if (var2 < 0) {
         var2 = -var2;
      }

      int var3;
      int var4;
      if (var1 < var2) {
         var3 = var1;
         var4 = var2;
      } else {
         var3 = var2;
         var4 = var1;
      }

      int var5 = var4 * 1007 + var3 * 441;
      if (var4 < var3 << 4) {
         var5 -= var4 * 40;
      }

      return var5 + 512 >> 10;
   }

   void game_UpdateLevelLoading(int var1) throws Exception {
      this.loading_timeTotal += System.currentTimeMillis() - this.loading_previousTime;
      this.loading_previousTime = System.currentTimeMillis();
      if (this.game_reloading) {
         if (!this.game_ReloadLevel()) {
            return;
         }
      } else if (!this.game_LoadLevel()) {
         return;
      }

      this.game_ClearLevelLoading();
      this.game_StartMusic(true);
      this.game_InitState(2);
   }

   void game_ClearLevelLoading() throws Exception {
      this.game_clearPopupText();
      this.asset_FreeImage(788);
      this.asset_FreeImage(922);
      this.asset_FreeImage(930);
      this.asset_FreeImage(926);
      this.game_isLoading = false;
      this.game_UpdateCamera(100);
   }

   void game_RenderLevelLoading() {
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 0);
      this.asset_DrawImage(788, 0, 14);
      this.font_text.SetColor(255, 255, 255);
      this.font_text.DrawSubstringWrapped(this.g_Text[48], 0, this.g_Text[48].length(), 70, 5, 172, 8, 0);
      this.font_text.DrawSubstringWrapped(this.g_Text[47], 0, this.g_Text[47].length(), 70, this.system_nCanvasWidth - 5, 172, 10, 0);
      this.font_text.DrawSubstringWrapped(this.g_Text[49], 0, this.g_Text[49].length(), 70, this.system_nCanvasWidth >> 1, 240, 1, 0);
      this.font_text.DrawSubstringWrapped(this.g_Text[51], 0, this.g_Text[51].length(), 100, this.system_nCanvasWidth >> 1, 120, 9, 0);
      this.font_text.DrawSubstringWrapped(this.g_Text[50], 0, this.g_Text[50].length(), 70, this.system_nCanvasWidth - 5, 208, 2, 0);
      this.asset_DrawImage(922, 0, this.system_nCanvasHeight - 22);
      if ((this.scene_timer >> 8 & 1) == 1) {
         this.asset_DrawImage(930, this.system_nCanvasWidth - 66, this.system_nCanvasHeight - 28);
      } else {
         this.asset_DrawImage(926, this.system_nCanvasWidth - 66, this.system_nCanvasHeight - 28);
      }

      this.font_ingame.DrawSubstring(this.g_Text[29], 0, this.g_Text[29].length(), 8, this.system_nCanvasHeight - 20, 0);
      boolean var1 = false;
      int var2;
      if (this.game_reloading) {
         var2 = this.system_nCanvasWidth / 2 * this.game_loadState;
      } else {
         var2 = this.system_nCanvasWidth / 14 * this.game_loadState;
      }

      this.system_FillRect(0, 290, var2, 10, 16777215);
   }

   void game_InitStateLevelLoading(boolean var1) throws Exception {
      this.game_loadState = 0;
      this.game_nKeyFlags = 0;
      this.game_isLoading = true;
      this.game_reloading = var1;
      this.gameover_pause = false;
      this.game_numSpawnedCops = 0;
      this.asset_LoadImage(788, false);
      this.asset_LoadImage(922, false);
      this.asset_LoadImage(930, false);
      this.asset_LoadImage(926, false);
      m_CurrentGraphics = this.game_gMapBuffer;
      this.game_RenderLevelLoading();
      this.system_bScreenDirty = false;
      this.loading_timeTotal = 0L;
      this.loading_previousTime = System.currentTimeMillis();
      this.transition_Start();
   }

   boolean game_LoadLevel() throws Exception {
      String var1 = this.g_worlds[this.g_trackPaths[this.g_race.m_trackPath].m_nWorld];
      if (var1 == "") {
         var1 = "track_02";
      }

      int var2;
      switch(this.game_loadState) {
      case 0:
         this.scene_LoadTrackSplines(this.g_race.m_trackPath);
         ++this.game_loadState;
         break;
      case 1:
         this.track_copPoints = (short[][])null;
         this.track_evasionPoints = (short[][])null;
         this.track_checkpoints = (short[][])null;
         if (this.g_trackPaths[this.g_race.m_trackPath].m_checkpoints >= 0) {
            this.track_checkpoints = this.loadPointSet(this.g_trackPaths[this.g_race.m_trackPath].m_checkpoints);
         }

         if (this.g_race.m_type == 0 || this.g_race.m_type == 2) {
            if (this.g_trackPaths[this.g_race.m_trackPath].m_nWorld == 0) {
               this.track_copPoints = this.loadPointSet(1071);
               this.track_evasionPoints = this.loadPointSet(1065);
            } else {
               this.track_copPoints = this.loadPointSet(1112);
               this.track_evasionPoints = this.loadPointSet(1104);
            }
         }

         ++this.game_loadState;
         break;
      case 2:
         this.game_posX = this.track_splines[0][0][0] << 4;
         this.game_posY = this.track_splines[0][0][1] << 4;
         this.game_posZ = 0;
         this.game_angZ = 7864320;
         ++this.game_loadState;
         break;
      case 3:
         this.g_race.m_nCopsSmashed = 0;
         this.numRoadsideCops = 0;
         if (this.g_race.m_type == 0 || this.g_race.m_type == 2) {
            this.g_roadsideCop = new TheGame.Car(-5);
            this.g_roadsideCop.Init();
            this.g_roadsideCop.SetAppearance(this.g_cars[7], 7, false);
            this.g_roadsideCop.m_ca.constructCar(true);
            this.g_roadsideCop.m_bDisabled = true;
            this.numRoadsideCops = 1;
         }

         ++this.game_loadState;
         break;
      case 4:
         this.sortedCarsList = new TheGame.Car[this.g_race.m_numRacers + this.g_race.m_numCops + this.g_race.m_numTraffic + this.numRoadsideCops];
         this.allCarsList = new TheGame.Car[this.g_race.m_numRacers + this.g_race.m_numCops + this.g_race.m_numTraffic + this.numRoadsideCops];

         for(var2 = 0; var2 < this.g_race.m_numRacers; ++var2) {
            this.sortedCarsList[var2] = this.players[var2];
         }

         for(var2 = 0; var2 < this.g_race.m_numCops; ++var2) {
            this.sortedCarsList[var2 + this.g_race.m_numRacers] = this.cops[var2];
         }

         for(var2 = 0; var2 < this.g_race.m_numTraffic; ++var2) {
            this.sortedCarsList[var2 + this.g_race.m_numRacers + this.g_race.m_numCops] = this.traffic[var2];
         }

         for(var2 = 0; var2 < this.g_race.m_numRacers; ++var2) {
            this.allCarsList[var2] = this.players[var2];
         }

         for(var2 = 0; var2 < this.g_race.m_numCops; ++var2) {
            this.allCarsList[var2 + this.g_race.m_numRacers] = this.cops[var2];
         }

         for(var2 = 0; var2 < this.g_race.m_numTraffic; ++var2) {
            this.allCarsList[var2 + this.g_race.m_numRacers + this.g_race.m_numCops] = this.traffic[var2];
         }

         if (this.numRoadsideCops != 0) {
            this.sortedCarsList[this.g_race.m_numRacers + this.g_race.m_numCops + this.g_race.m_numTraffic] = this.g_roadsideCop;
            this.allCarsList[this.g_race.m_numRacers + this.g_race.m_numCops + this.g_race.m_numTraffic] = this.g_roadsideCop;
         }

         for(var2 = 0; var2 < this.g_cars.length; ++var2) {
            if (this.g_cars[var2] != null && var2 != this.g_player.m_currentCar) {
               this.g_cars[var2].free();
            }
         }

         ++this.game_loadState;
         this.loadingCar = 0;
         break;
      case 5:
         this.players[this.loadingCar].Init();
         if (this.loadingCar == 0) {
            this.players[this.loadingCar].SetAppearance(this.g_cars[this.g_player.m_currentCar], this.g_player.m_currentCar, true);
            if (this.g_player.m_missionStatus[5] == 1) {
               this.players[this.loadingCar].m_nCurrentHeatRating = 3;
            }
         } else if (this.g_race.m_type == 4) {
            this.players[this.loadingCar].SetAppearance(this.g_cars[7], 7, false);
         } else if (this.loadingCar == 1 && this.g_race.m_bMission && this.g_race.m_nMissionID < 6) {
            if (this.g_race.m_nMissionID == 0) {
               this.players[this.loadingCar].SetAppearance(this.g_cars[this.g_race.m_nMissionID], this.g_race.m_nMissionID, false);
               this.players[this.loadingCar].m_ca.setParts(this.g_carSetups[6]);
            } else {
               this.players[this.loadingCar].SetAppearance(this.g_cars[this.g_race.m_nMissionID], this.g_race.m_nMissionID, false);
               this.players[this.loadingCar].m_ca.setParts(this.g_carSetups[this.g_race.m_nMissionID]);
            }
         } else {
            while(true) {
               int var29 = Math.abs(this.system_GetRandom() % 6);
               boolean var28 = true;

               for(int var30 = 0; var30 < this.loadingCar; ++var30) {
                  if (this.players[var30].m_nCA == var29) {
                     var28 = false;
                     break;
                  }
               }

               if (var28) {
                  this.players[this.loadingCar].SetAppearance(this.g_cars[var29], var29, false);
                  this.players[this.loadingCar].m_ca.randomize(this.loadingCar);
                  break;
               }
            }
         }

         this.players[this.loadingCar].m_ca.constructCar(this.loadingCar != 0);
         if (this.g_race.m_type == 4) {
            this.players[this.loadingCar].m_aiSpline = this.track_splines[0];
         } else {
            this.players[this.loadingCar].m_aiSpline = this.track_splines[0];
         }

         ++this.loadingCar;
         if (this.loadingCar >= this.g_race.m_numRacers) {
            if (this.numRoadsideCops != 0) {
               this.g_roadsideCop.m_aiSpline = this.players[0].m_aiSpline;
            }

            ++this.game_loadState;
            this.loadingCar = 0;
         }
         break;
      case 6:
         this.cops[this.loadingCar].Init();
         this.cops[this.loadingCar].m_nCopNum = (byte)(this.loadingCar + 1);
         this.cops[this.loadingCar].SetAppearance(this.g_cars[7], 7, false);
         this.cops[this.loadingCar].m_ca.constructCar(true);
         this.cops[this.loadingCar].m_aiSpline = this.track_splines[0];
         this.cops[this.loadingCar].m_bDisabled = true;
         ++this.loadingCar;
         if (this.loadingCar >= this.g_race.m_numCops) {
            ++this.game_loadState;
            this.loadingCar = 0;
         }
         break;
      case 7:
         byte var3 = 3;
         int var27 = Math.abs(this.system_GetRandom() >> 5) % var3;
         var27 += 8;
         this.traffic[this.loadingCar].Init();
         this.traffic[this.loadingCar].SetAppearance(this.g_cars[var27], var27, false);
         this.traffic[this.loadingCar].m_ca.randomize(0);
         this.traffic[this.loadingCar].m_ca.constructCar(false);
         this.traffic[this.loadingCar].m_bTraffic = true;
         this.traffic[this.loadingCar].m_aiSpline = this.track_splines[2];
         this.traffic[this.loadingCar].m_nTopSpeed = 5000;
         ++this.loadingCar;
         if (this.loadingCar >= this.traffic.length) {
            ++this.game_loadState;
            this.loadingCar = 0;
         }
         break;
      case 8:
         for(var2 = 0; var2 < this.g_race.m_numRacers; ++var2) {
            this.players[var2].m_xPos = this.game_posX;
            this.players[var2].m_yPos = this.game_posY;
            this.players[var2].m_zPos = -100000;
         }

         if (this.g_race.m_type == 4) {
            this.players[0].m_nNitroCount = 0;
         } else {
            this.players[0].m_nNitroCount = this.players[0].m_ca.m_parts[12] * 2;
         }

         this.cops[0].m_xPos = this.game_posX - 500;
         this.cops[0].m_yPos = this.game_posY + 6000;
         this.cops[0].m_zPos = -100000;
         this.cops[1].m_xPos = this.game_posX;
         this.cops[1].m_yPos = this.game_posY + 6000;
         this.cops[1].m_zPos = -100000;
         ++this.game_loadState;
         break;
      case 9:
         this.scene_BuildTrack(var1);
         ++this.game_loadState;
         break;
      case 10:
         System.gc();
         this.game_loadedWorld = this.g_trackPaths[this.g_race.m_trackPath].m_nWorld;
         this.game_trackFirstRender = true;
         ++this.game_loadState;
         break;
      case 11:
         this.scene_LoadPVS(var1);
         if (this.groundCollision == null) {
            this.groundCollision = new TheGame.CGroundCollision();
         } else {
            this.groundCollision.Free();
         }

         String var4 = var1 + "_fmc/" + var1 + ".fmc";
         DataInputStream var5 = new DataInputStream(this.getClass().getResourceAsStream(var4));
         int var6 = var5.available();
         byte[] var7 = new byte[var6];
         var5.readFully(var7);
         int var8 = var6 >> 1;
         short[] var9 = new short[var8];

         for(var2 = 0; var2 < var8; ++var2) {
            var9[var2] = (short)(var7[var2 << 1] & 255 | (var7[(var2 << 1) + 1] & 255) << 8);
         }

         Object var31 = null;
         var5.close();
         var5 = null;
         this.groundCollision.Load(var9);
         this.groundCollision.m_nAttributeMask = this.g_trackPaths[this.g_race.m_trackPath].m_nTrackFlags;
         Object var32 = null;
         ++this.game_loadState;
         break;
      case 12:
         this.game_clearPopupText();
         TheGame.MintMesh[] var34;
         Appearance var36;
         if (this.scene_UI_arrow == null) {
            while(true) {
               if (!this.scene_OpenModel("arrow", 4, true)) {
                  var34 = this.scene_FindMintMesh("arrow");
                  var36 = var34[0].m_mesh.getAppearance(0);
                  this.scene_UI_arrow = var34[0].m_mesh;
                  this.scene_UI_arrow.setAppearance(0, var36);
                  var36 = this.scene_UI_arrow.getAppearance(0);
                  var36.setPolygonMode(this.polygonMode_NoPersp);
                  CompositingMode var37 = new CompositingMode();
                  var37.setDepthTestEnable(false);
                  var37.setDepthWriteEnable(false);
                  var36.setCompositingMode(var37);
                  break;
               }
            }
         }

         if (this.scene_UI_indicator == null) {
            try {
               if (this.g_race.m_type == 3) {
                  while(true) {
                     if (!this.scene_OpenModel("camera", 4, true)) {
                        var34 = this.scene_FindMintMesh("camera");
                        this.scene_UI_indicator = var34[0].m_mesh;
                        break;
                     }
                  }
               } else {
                  while(true) {
                     if (!this.scene_OpenModel("indicator", 4, true)) {
                        var34 = this.scene_FindMintMesh("indicator");
                        this.scene_UI_indicator = var34[0].m_mesh;
                        break;
                     }
                  }
               }

               Appearance var38 = this.scene_UI_indicator.getAppearance(0);
               var38.setPolygonMode(this.polygonMode_NoPersp);
               var38.setCompositingMode(this.compositingMode_ZWRITEREAD);
            } catch (Exception var25) {
               var25.printStackTrace();
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_levelloading_share.hpp", 598, var25.toString());
            } catch (Error var26) {
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_levelloading_share.hpp", 598, var26.toString());
            }
         }

         if ((this.g_race.m_type == 0 || this.g_race.m_type == 2) && this.scene_UI_evasion_ind == null) {
            try {
               while(true) {
                  if (!this.scene_OpenModel("evasion", 4, true)) {
                     var34 = this.scene_FindMintMesh("evasion");
                     this.scene_UI_evasion_ind = var34[0].m_mesh;
                     var36 = this.scene_UI_evasion_ind.getAppearance(0);
                     var36.setPolygonMode(this.polygonMode_NoPersp);
                     var36.setCompositingMode(this.compositingMode_ZWRITEREAD);
                     break;
                  }
               }
            } catch (Exception var23) {
               var23.printStackTrace();
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_levelloading_share.hpp", 619, var23.toString());
            } catch (Error var24) {
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_levelloading_share.hpp", 619, var24.toString());
            }
         }

         ++this.game_loadState;
         break;
      case 13:
         this.scene_InitTrackSplines();

         for(var2 = 0; var2 < this.g_race.m_numRacers; ++var2) {
            this.players[var2].InitCollision();
         }

         int var10;
         for(var2 = 0; var2 < this.g_race.m_numTraffic; ++var2) {
            var10 = (var2 + 1) * this.traffic[var2].m_aiSpline.length / 5;
            this.traffic[var2].m_xPos = this.traffic[var2].m_aiSpline[var10][0] << 4;
            this.traffic[var2].m_yPos = this.traffic[var2].m_aiSpline[var10][1] << 4;
            this.traffic[var2].m_zAng = this.traffic[var2].m_aiSpline[var10][2] << 8;
            this.traffic[var2].InitCollisionToSplinePos(var10);
            this.traffic[var2].m_nAiNode = this.traffic[var2].FindClosedAISpline();
         }

         boolean var33 = false;
         boolean var11 = false;
         int var12 = -(this.sin_Array[this.players[0].m_zAng >> 8 >> 8 & 255] + ((this.sin_Array[1 + (this.players[0].m_zAng >> 8 >> 8) & 255] - this.sin_Array[this.players[0].m_zAng >> 8 >> 8 & 255]) * ((this.players[0].m_zAng >> 8) - (this.players[0].m_zAng >> 8 & -256)) >> 8));
         int var13 = this.sin_Array[(this.players[0].m_zAng >> 8) + 49152 >> 8 & 255] + ((this.sin_Array[1 + ((this.players[0].m_zAng >> 8) + 49152 >> 8) & 255] - this.sin_Array[(this.players[0].m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.players[0].m_zAng >> 8) + 49152 - ((this.players[0].m_zAng >> 8) + 49152 & -256)) >> 8);
         int var14 = this.players[0].m_zAng + 4194304;
         int var15 = -(this.sin_Array[var14 >> 8 >> 8 & 255] + ((this.sin_Array[1 + (var14 >> 8 >> 8) & 255] - this.sin_Array[var14 >> 8 >> 8 & 255]) * ((var14 >> 8) - (var14 >> 8 & -256)) >> 8));
         int var16 = this.sin_Array[(var14 >> 8) + 49152 >> 8 & 255] + ((this.sin_Array[1 + ((var14 >> 8) + 49152 >> 8) & 255] - this.sin_Array[(var14 >> 8) + 49152 >> 8 & 255]) * ((var14 >> 8) + 49152 - ((var14 >> 8) + 49152 & -256)) >> 8);
         short var17 = 1000;
         boolean var18 = false;
         boolean var19 = false;
         int var35;
         short var40;
         if (this.g_race.m_type != 4) {
            for(var2 = 0; var2 < this.g_race.m_numRacers; ++var2) {
               var10 = var12 * (1 + var2 * var17) >> 14;
               var35 = var13 * (1 + var2 * var17) >> 14;
               this.players[var2].m_xPos = this.game_posX + var10;
               this.players[var2].m_yPos = this.game_posY + var35;
               if (var19) {
                  var40 = 400;
               } else {
                  var40 = -400;
               }

               this.players[var2].m_xPos += var15 * var40 >> 14;
               this.players[var2].m_yPos += var16 * var40 >> 14;
               var19 = !var19;
            }
         } else {
            int var39 = var17 + 500;
            var2 = 0;

            for(int var20 = this.g_race.m_numRacers - 1; var2 < this.g_race.m_numRacers; --var20) {
               var10 = var12 * (1 + var20 * var39) >> 14;
               var35 = var13 * (1 + var20 * var39) >> 14;
               this.players[var2].m_xPos = this.game_posX + var10;
               this.players[var2].m_yPos = this.game_posY + var35;
               if (var19) {
                  var40 = 400;
               } else {
                  var40 = -400;
               }

               this.players[var2].m_xPos += var15 * var40 >> 14;
               this.players[var2].m_yPos += var16 * var40 >> 14;
               var19 = !var19;
               ++var2;
            }
         }

         for(var2 = 0; var2 < 2; ++var2) {
            byte var41 = 50;
            int var21 = 65536 / var41;
            this.players[0].m_bPlayer = true;

            int var22;
            for(var22 = 0; var22 < this.g_race.m_numRacers; ++var22) {
               this.players[var22].UpdatePhysics(var41, var21);
            }

            for(var22 = 0; var22 < this.g_race.m_numRacers; ++var22) {
               this.players[var22].m_xVel = 0;
               this.players[var22].m_yVel = 0;
               this.players[var22].m_zVel = 0;
            }

            for(var22 = 1; var22 < this.g_race.m_numRacers; ++var22) {
               this.players[0].CollideWith(this.players[var22], var41, var21);
            }

            for(var22 = 0; var22 < this.g_race.m_numRacers; ++var22) {
               this.players[var22].UpdateTrackCollision(var41, var21);
            }
         }

         if (this.g_roadsideCop != null) {
            this.g_roadsideCop.InitCollision();
         }

         ++this.game_loadState;
         break;
      case 14:
         ++this.game_loadState;
         break;
      case 15:
         if (this.system_bDemoMode) {
            this.g_race.m_laps = 1;
         }

         this.game_currentGear = 1;
         System.gc();
         return true;
      }

      return false;
   }

   boolean game_ReloadLevel() throws Exception {
      int var1;
      switch(this.game_loadState) {
      case 0:
         this.game_posX = this.track_splines[0][0][0] << 4;
         this.game_posY = this.track_splines[0][0][1] << 4;
         this.game_posZ = 0;
         this.game_angZ = 7864320;
         if (this.g_player.m_missionStatus[5] == 1) {
            this.players[0].m_nCurrentHeatRating = 3;
         }

         for(var1 = 0; var1 < this.g_race.m_numRacers; ++var1) {
            this.players[var1].m_nAiNode = 0;
            this.players[var1].m_xPos = this.game_posX;
            this.players[var1].m_yPos = this.game_posY;
            this.players[var1].m_zPos = -100000;
            this.players[var1].AdjustCarStats();
         }

         if (this.g_race.m_type == 4) {
            this.players[0].m_nNitroCount = 0;
         } else {
            this.players[0].m_nNitroCount = this.players[0].m_ca.m_parts[12] * 2;
         }

         this.cops[0].m_xPos = this.game_posX - 500;
         this.cops[0].m_yPos = this.game_posY + 6000;
         this.cops[0].m_zPos = -100000;
         this.cops[1].m_xPos = this.game_posX;
         this.cops[1].m_yPos = this.game_posY + 6000;
         this.cops[1].m_zPos = -100000;
         ++this.game_loadState;
         break;
      case 1:
         this.game_clearPopupText();

         for(var1 = 0; var1 < this.g_race.m_numRacers; ++var1) {
            this.players[var1].InitCollision();
         }

         int var2;
         for(var1 = 0; var1 < this.g_race.m_numTraffic; ++var1) {
            var2 = (var1 + 1) * this.traffic[var1].m_aiSpline.length / 5;
            this.traffic[var1].m_xPos = this.traffic[var1].m_aiSpline[var2][0] << 4;
            this.traffic[var1].m_yPos = this.traffic[var1].m_aiSpline[var2][1] << 4;
            this.traffic[var1].m_zAng = this.traffic[var1].m_aiSpline[var2][2] << 8;
            this.traffic[var1].InitCollisionToSplinePos(var2);
            this.traffic[var1].m_nAiNode = this.traffic[var1].FindClosedAISpline();
         }

         boolean var15 = false;
         boolean var16 = false;
         int var3 = -(this.sin_Array[this.players[0].m_zAng >> 8 >> 8 & 255] + ((this.sin_Array[1 + (this.players[0].m_zAng >> 8 >> 8) & 255] - this.sin_Array[this.players[0].m_zAng >> 8 >> 8 & 255]) * ((this.players[0].m_zAng >> 8) - (this.players[0].m_zAng >> 8 & -256)) >> 8));
         int var4 = this.sin_Array[(this.players[0].m_zAng >> 8) + 49152 >> 8 & 255] + ((this.sin_Array[1 + ((this.players[0].m_zAng >> 8) + 49152 >> 8) & 255] - this.sin_Array[(this.players[0].m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.players[0].m_zAng >> 8) + 49152 - ((this.players[0].m_zAng >> 8) + 49152 & -256)) >> 8);
         int var5 = this.players[0].m_zAng + 4194304;
         int var6 = -(this.sin_Array[var5 >> 8 >> 8 & 255] + ((this.sin_Array[1 + (var5 >> 8 >> 8) & 255] - this.sin_Array[var5 >> 8 >> 8 & 255]) * ((var5 >> 8) - (var5 >> 8 & -256)) >> 8));
         int var7 = this.sin_Array[(var5 >> 8) + 49152 >> 8 & 255] + ((this.sin_Array[1 + ((var5 >> 8) + 49152 >> 8) & 255] - this.sin_Array[(var5 >> 8) + 49152 >> 8 & 255]) * ((var5 >> 8) + 49152 - ((var5 >> 8) + 49152 & -256)) >> 8);
         short var8 = 1000;
         boolean var9 = false;
         boolean var10 = false;
         int var11;
         short var18;
         if (this.g_race.m_type != 4) {
            for(var11 = 0; var11 < this.g_race.m_numRacers; ++var11) {
               var1 = var3 * (1 + var11 * var8) >> 14;
               var2 = var4 * (1 + var11 * var8) >> 14;
               this.players[var11].m_xPos = this.game_posX + var1;
               this.players[var11].m_yPos = this.game_posY + var2;
               if (var10) {
                  var18 = 400;
               } else {
                  var18 = -400;
               }

               this.players[var11].m_xPos += var6 * var18 >> 14;
               this.players[var11].m_yPos += var7 * var18 >> 14;
               var10 = !var10;
            }
         } else {
            int var17 = var8 + 500;
            var11 = 0;

            for(int var12 = this.g_race.m_numRacers - 1; var11 < this.g_race.m_numRacers; --var12) {
               var1 = var3 * (1 + var12 * var17) >> 14;
               var2 = var4 * (1 + var12 * var17) >> 14;
               this.players[var11].m_xPos = this.game_posX + var1;
               this.players[var11].m_yPos = this.game_posY + var2;
               if (var10) {
                  var18 = 400;
               } else {
                  var18 = -400;
               }

               this.players[var11].m_xPos += var6 * var18 >> 14;
               this.players[var11].m_yPos += var7 * var18 >> 14;
               var10 = !var10;
               ++var11;
            }
         }

         for(var11 = 0; var11 < 2; ++var11) {
            byte var19 = 50;
            int var13 = 65536 / var19;
            this.players[0].m_bPlayer = true;

            int var14;
            for(var14 = 0; var14 < this.g_race.m_numRacers; ++var14) {
               this.players[var14].UpdatePhysics(var19, var13);
            }

            for(var14 = 0; var14 < this.g_race.m_numRacers; ++var14) {
               this.players[var14].m_xVel = 0;
               this.players[var14].m_yVel = 0;
               this.players[var14].m_zVel = 0;
            }

            for(var14 = 1; var14 < this.g_race.m_numRacers; ++var14) {
               this.players[0].CollideWith(this.players[var14], var19, var13);
            }

            for(var14 = 0; var14 < this.g_race.m_numRacers; ++var14) {
               this.players[var14].UpdateTrackCollision(var19, var13);
            }
         }

         ++this.game_loadState;
         break;
      case 2:
         this.game_currentGear = 1;
         System.gc();
         return true;
      }

      return false;
   }

   void game_LoadAnimationResource() throws Exception {
      this.game_animationsLoaded = true;
   }

   void game_FreeAnimationResource() throws Exception {
      if (this.game_animationsLoaded) {
         this.game_animationsLoaded = false;
      }

   }

   void game_LevelCleanup(boolean var1) {
   }

   void game_EndOfLevel(boolean var1) throws Exception {
      this.game_FreeAnimationResource();
      this.game_LevelCleanup(true);
      System.gc();
   }

   int game_GetKeyFlag(int var1, int var2) {
      if (var1 >= 48 && var1 <= 57) {
         if (var1 == 50) {
            return 2;
         } else if (var1 == 56) {
            return 16;
         } else if (var1 == 52) {
            return 64;
         } else if (var1 == 54) {
            return 256;
         } else if (var1 == 57) {
            return 512;
         } else {
            return var1 == 51 ? 1024 : 0;
         }
      } else if (var2 == 1) {
         return 1;
      } else if (var2 == 6) {
         return 4;
      } else if (var2 == 2) {
         return 32;
      } else if (var2 == 5) {
         return 128;
      } else if (var2 == 8) {
         return 2048;
      } else {
         return var1 == 35 ? 4096 : 0;
      }
   }

   void game_KeyPressedPlay(int var1, int var2) throws Exception {
      if (var1 == -6) {
         this.game_Pause();
      } else {
         this.game_nKeyFlags |= this.game_GetKeyFlag(var1, var2);
      }
   }

   void game_ClearKeyHistory() {
      this.game_nKeyFlags = 0;
   }

   void game_KeyReleasedPlay(int var1, int var2) {
      for(int var3 = 0; var3 < 7; ++var3) {
         game_keyHistory[var3] = game_keyHistory[var3 + 1];
      }

      game_keyHistory[7] = this.game_nKeyPressed;
      if (game_keyHistory[0] == 4 && game_keyHistory[1] == 1 && game_keyHistory[2] == 5 && game_keyHistory[3] == 7 && game_keyHistory[4] == 3 && game_keyHistory[5] == 2 && game_keyHistory[6] == 8 && game_keyHistory[7] == 4) {
         ;
      }

      this.game_nKeyFlags &= ~this.game_GetKeyFlag(var1, var2);
   }

   void game_CheckControl() throws Exception {
      if ((this.game_nKeyFlags & 512) != 0) {
         ++this.trackPVSSegment;
         if (this.trackPVSSegment > 99) {
            this.trackPVSSegment = 99;
         }
      } else if ((this.game_nKeyFlags & 1024) != 0) {
         --this.trackPVSSegment;
         if (this.trackPVSSegment < 0) {
            this.trackPVSSegment = 0;
         }
      }

   }

   void game_UpdateCop_RaceSpecific(int var1, boolean var2) {
      int var3 = this.players[0].m_nAiNode;
      int var4;
      if (!var2 && (this.system_GetRandom() < 1644167168 || this.players[0].m_nCurrentHeatRating <= 2)) {
         if (this.system_GetRandom() >= 1778384896 && this.players[0].m_nCurrentHeatRating > 1) {
            var3 += 12;
            this.cops[var1].m_nState = 1;
            this.cops[var1].m_nLap = this.players[0].m_nLap;
            if (var3 >= this.cops[var1].m_aiSpline.length) {
               var3 -= this.cops[var1].m_aiSpline.length;
               ++this.cops[var1].m_nLap;
            }

            var4 = this.cops[var1].m_nTopSpeed >> 1;
            this.cops[var1].m_zAng = (this.cops[var1].m_aiSpline[var3][2] << 8) + 8388608 + 4194304 & 16777215;
            this.cops[var1].m_bReverseTrack = true;
            this.cops[var1].m_bGoingBackward = true;
         } else if (this.system_GetRandom() >= 1073741824) {
            var3 += 12;
            this.cops[var1].m_nState = 6;
            this.cops[var1].m_nLap = this.players[0].m_nLap;
            if (var3 >= this.cops[var1].m_aiSpline.length) {
               var3 -= this.cops[var1].m_aiSpline.length;
               ++this.cops[var1].m_nLap;
            }

            var4 = this.players[0].m_velocityLastFrame >> 2;
            this.cops[var1].m_zAng = (this.cops[var1].m_aiSpline[var3][2] << 8) + 4194304 & 16777215;
            this.cops[var1].m_bReverseTrack = false;
            this.cops[var1].m_bGoingBackward = false;
         } else {
            var3 -= 2;
            this.cops[var1].m_nState = 0;
            this.cops[var1].m_nLap = this.players[0].m_nLap;
            if (var3 < 0) {
               var3 += this.cops[var1].m_aiSpline.length;
               --this.cops[var1].m_nLap;
            }

            var4 = this.players[0].m_velocityLastFrame;
            if (var4 < this.cops[var1].m_nTopSpeed >> 2) {
               var4 = this.cops[var1].m_nTopSpeed >> 2;
            }

            this.cops[var1].m_zAng = (this.cops[var1].m_aiSpline[var3][2] << 8) + 4194304 & 16777215;
            this.cops[var1].m_bReverseTrack = false;
            this.cops[var1].m_bGoingBackward = false;
         }
      } else {
         var3 += 12;
         this.cops[var1].m_nState = 4;
         this.cops[var1].m_nLap = this.players[0].m_nLap;
         if (var3 >= this.cops[var1].m_aiSpline.length) {
            var3 -= this.cops[var1].m_aiSpline.length;
            ++this.cops[var1].m_nLap;
         }

         var4 = 0;
         this.cops[var1].m_zAng = (this.cops[var1].m_aiSpline[var3][2] << 8) + 8388608 & 16777215;
         this.cops[var1].m_bReverseTrack = true;
         this.cops[var1].m_bGoingBackward = true;
      }

      this.cops[var1].m_xPos = this.cops[var1].m_aiSpline[var3][0] << 4;
      this.cops[var1].m_yPos = this.cops[var1].m_aiSpline[var3][1] << 4;
      this.cops[var1].m_nAiNode = var3;
      this.cops[var1].InitCollisionToSplinePos(var3);
      this.cops[var1].m_nTimeNotVisable = 2000;
      this.cops[var1].m_nTimeSinceSpawn = 0;
      this.cops[var1].m_bHitPlayer = false;
      this.cops[var1].m_xVel = -(this.sin_Array[this.cops[var1].m_zAng >> 8 >> 8 & 255] + ((this.sin_Array[1 + (this.cops[var1].m_zAng >> 8 >> 8) & 255] - this.sin_Array[this.cops[var1].m_zAng >> 8 >> 8 & 255]) * ((this.cops[var1].m_zAng >> 8) - (this.cops[var1].m_zAng >> 8 & -256)) >> 8)) * var4 >> 14;
      this.cops[var1].m_yVel = (this.sin_Array[(this.cops[var1].m_zAng >> 8) + 49152 >> 8 & 255] + ((this.sin_Array[1 + ((this.cops[var1].m_zAng >> 8) + 49152 >> 8) & 255] - this.sin_Array[(this.cops[var1].m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.cops[var1].m_zAng >> 8) + 49152 - ((this.cops[var1].m_zAng >> 8) + 49152 & -256)) >> 8)) * var4 >> 14;
      this.cops[var1].m_velocityLastFrame = 0;
      this.cops[var1].m_bReversing = false;
      this.cops[var1].m_nAverageSplineMovementSpeed = 0;
      this.cops[var1].m_nAverageSpeed = this.cops[var1].m_nTopSpeed;
      this.cops[var1].m_nStateTime = 0;
      this.game_timeSinceLastCopSpawn = 0;
   }

   void game_UpdateCops(int var1) {
      boolean var2 = false;
      int var3 = 0;
      int var7;
      if (this.g_race.m_numRacers == 1) {
         for(var7 = 0; var7 < this.g_race.m_numCops; ++var7) {
            ++var3;
         }
      } else if (this.g_race.m_numRacers > 1) {
         boolean var4 = false;

         for(var7 = 1; var7 < this.g_race.m_numRacers; ++var7) {
            int var8 = this.players[var7].m_nTimeNotVisable;
            if (var8 > 5000 && var3 < this.g_race.m_numCops) {
               ++var3;
            }
         }
      }

      if (var3 > 1 && this.players[0].m_nCurrentHeatRating < 3) {
         var3 = 1;
      }

      this.game_timeSinceLastCopSpawn += var1;
      short var9 = 5000;
      if (this.g_race.m_type == 4) {
         var9 = 10000;
      }

      if (var3 > 0 && this.players[0].m_nCurrentHeatRating > 0 && this.game_timeSinceLastCopSpawn > var9 && this.players[0].m_nAverageSplineMovementSpeed > 2000) {
         for(var7 = 0; var7 < var3; ++var7) {
            if (this.cops[var7].m_bDisabled) {
               boolean var5 = false;
               if (this.g_race.m_type == 4) {
                  if (this.system_GetRandom() < 1644167168) {
                     break;
                  }

                  var5 = true;
               }

               this.cops[var7].m_bDisabled = false;
               this.cops[var7].m_nDamagedLevel = 0;
               ++this.game_numSpawnedCops;
               this.game_UpdateCop_RaceSpecific(var7, var5);
               break;
            }
         }
      }

      if (this.g_race.m_type == 4) {
         for(var7 = 0; var7 < this.g_race.m_numCops; ++var7) {
            int var10 = this.cops[var7].m_xPos - this.players[0].m_xPos;
            int var6 = this.cops[var7].m_yPos - this.players[0].m_yPos;
            if (!this.cops[var7].m_bDisabled && this.cops[var7].m_nTimeNotVisable > 200 && Math.abs(var10) + Math.abs(var6) - (Math.min(Math.abs(var10), Math.abs(var6)) >> 1) < 7680) {
               this.cops[var7].m_bDisabled = true;
               --this.game_numSpawnedCops;
            }
         }
      } else if (this.players[0].m_nAverageSplineMovementSpeed > 2000) {
         for(var7 = 0; var7 < this.g_race.m_numCops; ++var7) {
            if (!this.cops[var7].m_bDisabled && this.cops[var7].m_nTimeNotVisable > 4000 && this.cops[var7].m_nTimeSinceSpawn > 8000) {
               this.cops[var7].m_bDisabled = true;
               --this.game_numSpawnedCops;
            }
         }
      }

   }

   void game_UpdateTraffic(int var1) {
      if (this.game_numSpawnedCops == 0) {
         for(int var2 = 0; var2 < this.traffic.length; ++var2) {
            if (this.traffic[var2].m_nTimeNotVisable > 2000) {
               int var3 = Math.abs(this.system_GetRandom() >> 16) % this.traffic[var2].m_aiSpline.length;
               int var4 = this.players[0].m_xPos - (this.traffic[var2].m_aiSpline[var3][0] << 4);
               int var5 = this.players[0].m_yPos - (this.traffic[var2].m_aiSpline[var3][1] << 4);
               int var6 = Math.abs(var4) + Math.abs(var5);
               if (var6 > 14500 && var6 < 17000) {
                  int var7 = -(this.sin_Array[this.game_cameraAngZ >> 8 >> 8 & 255] + ((this.sin_Array[1 + (this.game_cameraAngZ >> 8 >> 8) & 255] - this.sin_Array[this.game_cameraAngZ >> 8 >> 8 & 255]) * ((this.game_cameraAngZ >> 8) - (this.game_cameraAngZ >> 8 & -256)) >> 8));
                  int var8 = this.sin_Array[(this.game_cameraAngZ >> 8) + 49152 >> 8 & 255] + ((this.sin_Array[1 + ((this.game_cameraAngZ >> 8) + 49152 >> 8) & 255] - this.sin_Array[(this.game_cameraAngZ >> 8) + 49152 >> 8 & 255]) * ((this.game_cameraAngZ >> 8) + 49152 - ((this.game_cameraAngZ >> 8) + 49152 & -256)) >> 8);
                  var4 = (this.traffic[var2].m_aiSpline[var3][0] << 4) - this.game_posX;
                  var5 = (this.traffic[var2].m_aiSpline[var3][1] << 4) - this.game_posY;
                  if (var4 * var7 + var5 * var8 > 0) {
                     this.traffic[var2].m_nState = 0;
                     this.traffic[var2].m_nLap = 0;
                     int var9 = this.traffic[var2].m_nTopSpeed >> 3;
                     this.traffic[var2].m_zAng = (this.traffic[var2].m_aiSpline[var3][2] << 8) + 4194304 & 16777215;
                     this.traffic[var2].m_bReverseTrack = false;
                     this.traffic[var2].m_bGoingBackward = false;
                     this.traffic[var2].m_xPos = this.traffic[var2].m_aiSpline[var3][0] << 4;
                     this.traffic[var2].m_yPos = this.traffic[var2].m_aiSpline[var3][1] << 4;
                     this.traffic[var2].m_nAiNode = var3;
                     this.traffic[var2].InitCollisionToSplinePos(var3);
                     this.traffic[var2].m_nTimeNotVisable = 0;
                     this.traffic[var2].m_nTimeSinceSpawn = 0;
                     this.traffic[var2].m_xVel = -(this.sin_Array[this.traffic[var2].m_zAng >> 8 >> 8 & 255] + ((this.sin_Array[1 + (this.traffic[var2].m_zAng >> 8 >> 8) & 255] - this.sin_Array[this.traffic[var2].m_zAng >> 8 >> 8 & 255]) * ((this.traffic[var2].m_zAng >> 8) - (this.traffic[var2].m_zAng >> 8 & -256)) >> 8)) * var9 >> 14;
                     this.traffic[var2].m_yVel = (this.sin_Array[(this.traffic[var2].m_zAng >> 8) + 49152 >> 8 & 255] + ((this.sin_Array[1 + ((this.traffic[var2].m_zAng >> 8) + 49152 >> 8) & 255] - this.sin_Array[(this.traffic[var2].m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.traffic[var2].m_zAng >> 8) + 49152 - ((this.traffic[var2].m_zAng >> 8) + 49152 & -256)) >> 8)) * var9 >> 14;
                     this.traffic[var2].m_velocityLastFrame = 0;
                     this.traffic[var2].m_bReversing = false;
                     this.traffic[var2].m_nAverageSplineMovementSpeed = 0;
                     this.traffic[var2].m_nAverageSpeed = this.traffic[var2].m_nTopSpeed;
                     this.traffic[var2].RealizeNewTransform(var1, 65536 / var1);
                     this.traffic[var2].m_nStateTime = 0;
                  }
               }
            }
         }
      }

   }

   void game_UpdateGameTypeSpecifics(int var1) {
      if (this.g_race.m_type == 0) {
         if (this.players[0].m_nLap >= this.g_race.m_laps) {
            this.game_EndRace();
         } else if (this.game_state != 6 && this.game_state != 5) {
            this.game_Time += var1;
         }
      } else if (this.g_race.m_type == 1) {
         if (this.players[0].m_nCurCheckpoint >= this.track_checkpoints.length) {
            this.game_EndRace();
         } else if (this.game_state != 6 && this.game_state != 5) {
            this.game_Time += var1;
         }
      } else if (this.g_race.m_type == 3) {
         if (this.players[0].m_nCurCheckpoint >= this.track_checkpoints.length) {
            this.game_EndRace();
         } else if (this.game_Time >= this.g_race.m_nTimeLimit + 5000) {
            this.game_EndRace();
         } else if (this.game_state != 6 && this.game_state != 5) {
            this.game_Time += var1;
         }
      } else if (this.g_race.m_type == 4) {
         if (this.game_Time >= this.g_race.m_nTimeLimit + 5000) {
            this.game_EndRace();
         } else if (this.game_playerPosition != 1) {
            this.game_EndRace();
         } else if (this.game_state != 6 && this.game_state != 5) {
            this.game_Time += var1;
         }
      } else if (this.g_race.m_type == 2 && this.game_state != 6 && this.game_state != 5) {
         this.game_Time += var1;
      }

   }

   void game_UpdateCamera(int var1) {
      this.game_posX = this.players[0].m_xPos;
      this.game_posY = this.players[0].m_yPos;
      this.game_posZ = this.players[0].m_zPos;
      this.game_angZ = this.players[0].m_zAng;
      int var2 = (this.players[0].m_velocityLastFrame >> 2) - 256;
      int var3;
      if ((this.players[0].m_xVel != 0 || this.players[0].m_yVel != 0) && var2 > 0) {
         this.game_angZ = this.arctan2(-this.players[0].m_xVel << 16, -this.players[0].m_yVel << 16) & 16777215;
         if (var2 < 1024) {
            var3 = this.players[0].m_zAng & 16777215;
            if (var3 - this.game_angZ > 8388608) {
               var3 -= 16777216;
            } else if (var3 - this.game_angZ < -8388608) {
               var3 += 16777216;
            }

            this.game_angZ += (1024 - var2) * (var3 - this.game_angZ >> 10);
         }
      } else {
         this.game_angZ = this.players[0].m_zAng;
      }

      this.game_angZ &= 16777215;
      if (this.game_cameraAngZ - this.game_angZ > 8388608) {
         this.game_cameraAngZ -= 16777216;
      } else if (this.game_cameraAngZ - this.game_angZ < -8388608) {
         this.game_cameraAngZ += 16777216;
      }

      if (this.game_Time < 5000) {
         var3 = var1;
         var1 = (int)((float)var1 * 1000.0F / (5000.0F - (float)this.game_Time));
         if (var1 * 12 >> 8 > 256) {
            var1 = var3;
         }
      }

      this.game_cameraAngZ -= this.game_angZ;
      this.game_cameraAngZ = (this.game_cameraAngZ >> 4) * (this.exp_Array[-(-var1 * 12) >> 8] + ((this.exp_Array[1 + (-(-var1 * 12) >> 8)] - this.exp_Array[-(-var1 * 12) >> 8]) * (-(-var1 * 12) - (-(-var1 * 12) & -256)) >> 8) >> 4) >> 8;
      this.game_cameraAngZ += this.game_angZ;
      this.game_cameraAngZ &= 16777215;
   }

   void game_UpdateTachometer(int var1) {
      int var2 = (this.players[0].m_velocityLastFrame << 4 & 32767) + 4095;
      if (this.game_tachometer_vel < var2) {
         this.game_tachometer_vel += 40 * var1;
         if (this.game_tachometer_vel > var2) {
            this.game_tachometer_vel = var2;
         }
      } else if (this.game_tachometer_vel > var2) {
         this.game_tachometer_vel -= 40 * var1;
         if (this.game_tachometer_vel < var2) {
            this.game_tachometer_vel = var2;
         }
      }

   }

   void game_UpdatePlay(int var1) throws Exception {
      if (var1 > 150) {
         ++this.slowCount;
         if (this.slowCount > 5) {
            this.game_StartMusic(false);
            this.slowCount = -5;
         }
      } else if (this.slowCount > 0) {
         --this.slowCount;
      }

      this.game_profilerStartTimes[1][this.game_profilerPos] = System.currentTimeMillis();
      this.game_playingStateHadUpdate = true;
      this.game_nTimeInState = (short)(this.game_nTimeInState + var1);
      this.game_nTimeKeyHeld = (short)(this.game_nTimeKeyHeld + var1);
      this.game_CheckControl();
      this.game_profilerEndTimes[1][this.game_profilerPos] = System.currentTimeMillis();
      if (this.game_profilerEndTimes[1][this.game_profilerPos] - this.game_profilerStartTimes[1][this.game_profilerPos] > 100L) {
         System.out.println("large timer val:1," + (this.game_profilerEndTimes[1][this.game_profilerPos] - this.game_profilerStartTimes[1][this.game_profilerPos]));
      }

      int var2;
      int var3;
      int var4;
      try {
         if (var1 != 0) {
            this.game_profilerStartTimes[11][this.game_profilerPos] = System.currentTimeMillis();
            var2 = 65536 / var1;

            for(var3 = 1; var3 < this.sortedCarsList.length; ++var3) {
               var4 = this.sortedCarsList[var3].m_nSortDistance;
               TheGame.Car var5 = this.sortedCarsList[var3];
               int var6 = var3;

               while(this.sortedCarsList[var6 - 1].m_nSortDistance > var4) {
                  this.sortedCarsList[var6] = this.sortedCarsList[var6 - 1];
                  --var6;
                  if (var6 <= 0) {
                     break;
                  }
               }

               this.sortedCarsList[var6] = var5;
            }

            this.game_profilerEndTimes[11][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[11][this.game_profilerPos] - this.game_profilerStartTimes[11][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:11," + (this.game_profilerEndTimes[11][this.game_profilerPos] - this.game_profilerStartTimes[11][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[12][this.game_profilerPos] = System.currentTimeMillis();

            for(var3 = 0; var3 < this.allCarsList.length; ++var3) {
               this.allCarsList[var3].RealizeNewTransform(var1, var2);
            }

            this.game_profilerEndTimes[12][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[12][this.game_profilerPos] - this.game_profilerStartTimes[12][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:12," + (this.game_profilerEndTimes[12][this.game_profilerPos] - this.game_profilerStartTimes[12][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[13][this.game_profilerPos] = System.currentTimeMillis();

            for(var3 = 0; var3 < this.allCarsList.length; ++var3) {
               this.allCarsList[var3].UpdatePhysics(var1, var2);
            }

            this.game_profilerEndTimes[13][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[13][this.game_profilerPos] - this.game_profilerStartTimes[13][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:13," + (this.game_profilerEndTimes[13][this.game_profilerPos] - this.game_profilerStartTimes[13][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[14][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_Time < 5000) {
               for(var3 = 0; var3 < this.sortedCarsList.length; ++var3) {
                  this.sortedCarsList[var3].m_xVel = 0;
                  this.sortedCarsList[var3].m_yVel = 0;
                  this.sortedCarsList[var3].m_nReverseTimer = 0;
               }
            }

            this.game_profilerEndTimes[14][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[14][this.game_profilerPos] - this.game_profilerStartTimes[14][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:14," + (this.game_profilerEndTimes[14][this.game_profilerPos] - this.game_profilerStartTimes[14][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[15][this.game_profilerPos] = System.currentTimeMillis();
            this.game_profilerEndTimes[15][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[15][this.game_profilerPos] - this.game_profilerStartTimes[15][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:15," + (this.game_profilerEndTimes[15][this.game_profilerPos] - this.game_profilerStartTimes[15][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[16][this.game_profilerPos] = System.currentTimeMillis();

            for(var3 = 0; var3 < this.sortedCarsList.length; ++var3) {
               this.sortedCarsList[var3].m_nSortedPos = var3;
               var4 = this.sortedCarsList[var3].m_nSortDistance;

               for(int var23 = var3 + 1; var23 < this.sortedCarsList.length && var4 + 3 >= this.sortedCarsList[var23].m_nSortDistance; ++var23) {
                  this.sortedCarsList[var3].CollideWith(this.sortedCarsList[var23], var1, var2);
               }
            }

            this.game_profilerEndTimes[16][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[16][this.game_profilerPos] - this.game_profilerStartTimes[16][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:16," + (this.game_profilerEndTimes[16][this.game_profilerPos] - this.game_profilerStartTimes[16][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[17][this.game_profilerPos] = System.currentTimeMillis();
            this.game_profilerEndTimes[17][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[17][this.game_profilerPos] - this.game_profilerStartTimes[17][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:17," + (this.game_profilerEndTimes[17][this.game_profilerPos] - this.game_profilerStartTimes[17][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[18][this.game_profilerPos] = System.currentTimeMillis();
            this.game_UpdateCops(var1);
            this.game_UpdateTraffic(var1);

            for(var3 = 0; var3 < this.allCarsList.length; ++var3) {
               this.allCarsList[var3].UpdateTrackCollision(var1, var2);
            }

            this.game_profilerEndTimes[18][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[18][this.game_profilerPos] - this.game_profilerStartTimes[18][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:18," + (this.game_profilerEndTimes[18][this.game_profilerPos] - this.game_profilerStartTimes[18][this.game_profilerPos]));
            }
         }
      } catch (Exception var20) {
         var20.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_play_share.hpp", 834, var20.toString());
      } catch (Error var21) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_play_share.hpp", 834, var21.toString());
      }

      this.game_profilerStartTimes[19][this.game_profilerPos] = System.currentTimeMillis();

      try {
         if (this.g_race.m_bMission && this.g_race.m_nMissionID == 0 && this.players[0].m_nLap > 0) {
            this.game_nKeyFlags &= -24;
            this.players[0].m_nAccelerateTimer = 0;
            this.players[0].m_nAcceleration = 0;
            this.players[0].m_bReversing = false;
            if (this.game_state == 2) {
               this.players[0].m_xVel = (int)((float)this.players[0].m_xVel * 0.99F);
               this.players[0].m_yVel = (int)((float)this.players[0].m_yVel * 0.99F);
            } else {
               this.players[0].m_xVel = 0;
               this.players[0].m_yVel = 0;
            }

            if (this.players[0].m_velocityLastFrame < 1000) {
               this.game_EndRace();
            } else if (this.players[0].m_velocityLastFrame < 8000) {
               this.game_popupText(this.g_Text[193], true);
            }
         }

         if (this.g_race.m_type == 4 && this.game_playerPosition != 1) {
            this.game_nKeyFlags &= -24;
            this.players[0].m_nAccelerateTimer = 0;
            this.players[0].m_nAcceleration = 0;
            this.players[0].m_bReversing = false;
            if (this.players[0].m_velocityLastFrame < 1000) {
               this.players[0].m_xVel = 0;
               this.players[0].m_yVel = 0;
            } else {
               this.players[0].m_xVel = (int)((float)this.players[0].m_xVel * 0.99F);
               this.players[0].m_yVel = (int)((float)this.players[0].m_yVel * 0.99F);
            }
         }

         this.game_UpdateGameTypeSpecifics(var1);
         boolean var22 = true;
         if (this.game_state == 2) {
            if (Math.abs(this.players[0].m_velocityLastFrame) < 3000) {
               for(var3 = 0; var3 < this.g_race.m_numCops; ++var3) {
                  if (!this.cops[var3].m_bDisabled && this.cops[var3].m_nDamagedLevel < 51200 && this.cops[var3].m_camDist < 2500 && Math.abs(this.cops[var3].m_velocityLastFrame) < 3000) {
                     var22 = false;
                     this.players[0].m_nBustedness += var1;
                     break;
                  }
               }
            }

            if (this.players[0].m_nBustedness > 0) {
               if (var22) {
                  if (this.players[0].m_nBustedness > 1000 && this.players[0].m_nBustedness < 4000) {
                     this.players[0].m_nBustedness += var1;
                  } else {
                     if (this.players[0].m_nBustedness > 1000) {
                        this.game_popupText(this.g_Text[194], false);
                     }

                     this.players[0].m_nBustedness = 0;
                  }
               }

               if (this.players[0].m_nBustedness > 1000) {
                  this.game_popupText(this.g_Text[195] + " " + ((5000 - this.players[0].m_nBustedness) / 1000 + 1), false);
                  if (this.g_race.m_type != 4 && this.players[0].m_nBustedness > 5000) {
                     for(var3 = 0; var3 < this.g_race.m_numCops; ++var3) {
                        this.cops[var3].m_nState = 4;
                     }

                     this.game_InitState(6);
                  }
               }
            }
         } else if (this.game_state == 6) {
            this.players[0].m_xVel = (int)((float)this.players[0].m_xVel * 0.9F);
            this.players[0].m_yVel = (int)((float)this.players[0].m_yVel * 0.9F);
         }
      } catch (Exception var18) {
         var18.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_play_share.hpp", 957, var18.toString());
      } catch (Error var19) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_play_share.hpp", 957, var19.toString());
      }

      this.game_UpdateCamera(var1);
      var2 = 500000000;
      var3 = 500000000;
      var4 = 500000000;
      short var24 = 0;
      short var25 = 0;
      short var7 = 0;
      int var8 = this.game_posX >> 8;
      int var9 = this.game_posY >> 8;
      int var10 = this.game_posZ >> 8;

      int var12;
      for(short var11 = 0; var11 < 200; ++var11) {
         var12 = (this.track_pvsXYZ[var11][0] - var8) * (this.track_pvsXYZ[var11][0] - var8) + (this.track_pvsXYZ[var11][1] - var9) * (this.track_pvsXYZ[var11][1] - var9) + (this.track_pvsXYZ[var11][2] - var10) * (this.track_pvsXYZ[var11][2] - var10);
         if (var12 < var2) {
            var4 = var3;
            var7 = var25;
            var3 = var2;
            var25 = var24;
            var2 = var12;
            var24 = var11;
         } else if (var12 < var3) {
            var4 = var3;
            var7 = var25;
            var3 = var12;
            var25 = var11;
         } else if (var12 < var4) {
            var4 = var12;
            var7 = var11;
         }
      }

      this.trackPVSFrame1 = var24;
      this.trackPVSFrame2 = var25;
      this.trackPVSFrame3 = var7;
      int var26 = (this.game_cameraAngZ >> 16) - 64 >> 4 & 15;
      var12 = (this.game_cameraAngZ >> 16) - 64 - 11 >> 4 & 15;
      int var13 = (this.game_cameraAngZ >> 16) - 64 + 11 >> 4 & 15;
      int var14 = (this.game_cameraAngZ >> 16) - 64 - 22 >> 4 & 15;
      int var15 = (this.game_cameraAngZ >> 16) - 64 + 22 >> 4 & 15;
      this.cameraScopeMask = 1 << var26 | 1 << var12 | 1 << var13 | 1 << var14 | 1 << var15;
      this.game_profilerEndTimes[19][this.game_profilerPos] = System.currentTimeMillis();
      if (this.game_profilerEndTimes[19][this.game_profilerPos] - this.game_profilerStartTimes[19][this.game_profilerPos] > 100L) {
         System.out.println("large timer val:19," + (this.game_profilerEndTimes[19][this.game_profilerPos] - this.game_profilerStartTimes[19][this.game_profilerPos]));
      }

      this.game_profilerStartTimes[20][this.game_profilerPos] = System.currentTimeMillis();
      this.game_UpdateTachometer(var1);
      this.game_profilerEndTimes[20][this.game_profilerPos] = System.currentTimeMillis();
      if (this.game_profilerEndTimes[20][this.game_profilerPos] - this.game_profilerStartTimes[20][this.game_profilerPos] > 100L) {
         System.out.println("large timer val:20," + (this.game_profilerEndTimes[20][this.game_profilerPos] - this.game_profilerStartTimes[20][this.game_profilerPos]));
      }

   }

   int arctan2(int var1, int var2) {
      int var5 = 2097152;
      int var6 = 3 * var5;
      int var7 = var1;
      if (var2 >> 16 == 0) {
         if (var1 >> 16 == 0) {
            return 0;
         } else {
            return var1 < 0 ? -4194304 : 4194304;
         }
      } else {
         if (var1 < 0) {
            var7 = -var1;
         }

         int var3;
         int var4;
         if (var2 >= 0) {
            var4 = 0;
            if (var2 + var7 >> 16 != 0) {
               var3 = (var2 - var7) / (var2 + var7 >> 16);
               var4 = var5 - (var5 >> 8) * (var3 >> 8);
            }
         } else {
            var4 = 0;
            if (var7 - var2 >> 16 != 0) {
               var3 = (var2 + var7) / (var7 - var2 >> 16);
               var4 = var6 - (var5 >> 8) * (var3 >> 8);
            }
         }

         return var1 < 0 ? -var4 : var4;
      }
   }

   void game_SetCamera() {
      float var1 = (float)this.players[0].m_velocityLastFrame / 1000.0F;
      var1 += 60.0F;
      if (this.players[0].m_nNitroTimer > 0) {
         if (this.players[0].m_nNitroTimer > 500) {
            var1 += (float)(2500 - this.players[0].m_nNitroTimer) / 100.0F;
         } else {
            var1 += (float)this.players[0].m_nNitroTimer / 25.0F;
         }
      }

      float var2 = (float)this.system_nCanvasWidth / (float)(this.system_nCanvasHeight - 84 - 18) / 1.11F;
      this.camera.setPerspective(var1, var2, 0.5F, 1000.0F);
      this.scene_cameraTrans.setIdentity();
      this.scene_cameraTrans.postTranslate((float)this.game_posX * 0.00390625F, (float)this.game_posZ * 0.00390625F, (float)this.game_posY * 0.00390625F);
      this.scene_cameraTrans.postRotate((float)this.game_cameraAngZ * 2.1457672E-5F, 0.0F, 1.0F, 0.0F);
      if (this.game_Time < 5000) {
         float var3 = 5.5F + (5000.0F - (float)this.game_Time) / 2000.0F;
         float var4 = 2.0F + (5000.0F - (float)this.game_Time) / 3000.0F;
         this.scene_cameraTrans.postTranslate(0.0F, var4, var3);
      } else {
         var1 = 6.5F - var1 / 60.0F;
         int var14 = (int)(var1 * 256.0F);
         this.colResultCamera.x = this.players[0].m_colResult[2].x;
         this.colResultCamera.y = this.players[0].m_colResult[2].y;
         this.colResultCamera.z = this.players[0].m_colResult[2].z;
         this.colResultCamera.nTriangleID = this.players[0].m_colResult[2].nTriangleID;
         int var15 = this.sin_Array[this.game_cameraAngZ >> 8 >> 8 & 255] + ((this.sin_Array[1 + (this.game_cameraAngZ >> 8 >> 8) & 255] - this.sin_Array[this.game_cameraAngZ >> 8 >> 8 & 255]) * ((this.game_cameraAngZ >> 8) - (this.game_cameraAngZ >> 8 & -256)) >> 8);
         int var5 = -(this.sin_Array[(this.game_cameraAngZ >> 8) + 49152 >> 8 & 255] + ((this.sin_Array[1 + ((this.game_cameraAngZ >> 8) + 49152 >> 8) & 255] - this.sin_Array[(this.game_cameraAngZ >> 8) + 49152 >> 8 & 255]) * ((this.game_cameraAngZ >> 8) + 49152 - ((this.game_cameraAngZ >> 8) + 49152 & -256)) >> 8));
         int var6 = -var5;
         int var8 = this.game_posX + (var15 * var14 >> 14);
         int var9 = this.game_posY + (var5 * var14 >> 14);
         var8 += var6 * -300 >> 14;
         var9 += var15 * -300 >> 14;
         this.groundCollision.TestPointForCollision(var8 << 8, -var9 << 8, this.colResultCamera);
         if (this.colResultCamera.nTriangleID == -1) {
            this.colResultCamera.x = var8 << 8;
            this.colResultCamera.y = -var9 << 8;
            this.colResultCamera.z = this.game_posZ << 8;
         }

         int var10 = ((this.colResultCamera.x >> 8) - this.game_posX) * var15 + ((-this.colResultCamera.y >> 8) - this.game_posY) * var5;
         var10 >>= 14;
         int var11 = this.colResultCamera.z;
         this.colResultCamera.x = this.players[0].m_colResult[3].x;
         this.colResultCamera.y = this.players[0].m_colResult[3].y;
         this.colResultCamera.z = this.players[0].m_colResult[3].z;
         this.colResultCamera.nTriangleID = this.players[0].m_colResult[3].nTriangleID;
         var8 = this.game_posX + (var15 * var14 >> 14);
         var9 = this.game_posY + (var5 * var14 >> 14);
         var8 -= var6 * -300 >> 14;
         var9 -= var15 * -300 >> 14;
         this.groundCollision.TestPointForCollision(var8 << 8, -var9 << 8, this.colResultCamera);
         if (this.colResultCamera.nTriangleID == -1) {
            this.colResultCamera.x = var8 << 8;
            this.colResultCamera.y = -var9 << 8;
            this.colResultCamera.z = this.game_posZ << 8;
         }

         int var12 = ((this.colResultCamera.x >> 8) - this.game_posX) * var15 + ((-this.colResultCamera.y >> 8) - this.game_posY) * var5;
         var12 >>= 14;
         if (var10 > var12) {
            var10 = var12;
         }

         var11 += this.colResultCamera.z;
         var11 >>= 9;
         int var13 = 0;
         if (var11 > this.game_posZ) {
            var13 += this.arctan2(var11 - this.game_posZ << 8, var10 << 8);
         }

         this.game_cameraAngX -= var13;
         this.game_cameraAngX = this.game_cameraAngX * 200 >> 8;
         this.game_cameraAngX += var13;
         this.scene_cameraTrans.postRotate(-((float)this.game_cameraAngX * 2.1457672E-5F), 1.0F, 0.0F, 0.0F);
         this.scene_cameraTrans.postTranslate(0.0F, 2.0F, (float)var10 * 0.00390625F);
      }

   }

   void game_RenderPlay() {
      this.game_profilerStartTimes[2][this.game_profilerPos] = System.currentTimeMillis();
      if (this.game_playingStateHadUpdate) {
         this.game_SetCamera();
         m_CurrentGraphics.setClip(0, 84, this.system_nCanvasWidth, this.system_nCanvasHeight - 84 - 18);

         try {
            this.background.setColorClearEnable(false);
            this.background.setDepthClearEnable(true);
            this.scene_g3d.bindTarget(m_CurrentGraphics, true, 0);
            this.game_profilerEndTimes[2][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[2][this.game_profilerPos] - this.game_profilerStartTimes[2][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:2," + (this.game_profilerEndTimes[2][this.game_profilerPos] - this.game_profilerStartTimes[2][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[3][this.game_profilerPos] = System.currentTimeMillis();
            this.scene_g3d.clear(this.background);
            this.scene_g3d.setCamera(this.camera, this.scene_cameraTrans);
            this.game_profilerEndTimes[3][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[3][this.game_profilerPos] - this.game_profilerStartTimes[3][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:3," + (this.game_profilerEndTimes[3][this.game_profilerPos] - this.game_profilerStartTimes[3][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[4][this.game_profilerPos] = System.currentTimeMillis();
            if (this.skyDome != null) {
               this.scene_g3d.render(this.skyDome, (Transform)null);
            }

            this.game_profilerEndTimes[4][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[4][this.game_profilerPos] - this.game_profilerStartTimes[4][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:4," + (this.game_profilerEndTimes[4][this.game_profilerPos] - this.game_profilerStartTimes[4][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[5][this.game_profilerPos] = System.currentTimeMillis();

            int var4;
            for(var4 = 0; var4 < this.trackMeshs.length; ++var4) {
               this.trackIsRendered[var4] = false;
            }

            short var2;
            short var3;
            for(var4 = this.track_maxPVSi; var4 > -1; --var4) {
               var2 = this.track_pvsStruct[this.trackPVSFrame1][var4];
               var3 = this.track_pvsMask[this.trackPVSFrame1][var4];
               if (var2 > -1 && (var3 & this.cameraScopeMask) > 0 && this.trackIsMesh[var2]) {
                  this.trackIsRendered[var2] = true;
                  this.scene_g3d.render(this.trackMeshs[var2], (Transform)null);
               }
            }

            for(var4 = this.track_maxPVSi; var4 > -1; --var4) {
               var2 = this.track_pvsStruct[this.trackPVSFrame2][var4];
               var3 = this.track_pvsMask[this.trackPVSFrame2][var4];
               if (var2 > -1 && (var3 & this.cameraScopeMask) > 0 && this.trackIsMesh[var2] && !this.trackIsRendered[var2]) {
                  this.trackIsRendered[var2] = true;
                  this.scene_g3d.render(this.trackMeshs[var2], (Transform)null);
               }
            }

            for(var4 = this.track_maxPVSi; var4 > -1; --var4) {
               var2 = this.track_pvsStruct[this.trackPVSFrame3][var4];
               var3 = this.track_pvsMask[this.trackPVSFrame3][var4];
               if (var2 > -1 && (var3 & this.cameraScopeMask) > 0 && this.trackIsMesh[var2] && !this.trackIsRendered[var2]) {
                  this.trackIsRendered[var2] = true;
                  this.scene_g3d.render(this.trackMeshs[var2], (Transform)null);
               }
            }

            for(var4 = 0; var4 < 20; ++var4) {
               switch(this.g_trackPaths[this.g_race.m_trackPath].m_nTrack) {
               case 0:
                  if (this.trackBarrier1[var4] != null) {
                     this.scene_g3d.render(this.trackBarrier1[var4], (Transform)null);
                  }
                  break;
               case 1:
                  if (this.trackBarrier2[var4] != null) {
                     this.scene_g3d.render(this.trackBarrier2[var4], (Transform)null);
                  }
                  break;
               case 2:
                  if (this.trackBarrier3[var4] != null) {
                     this.scene_g3d.render(this.trackBarrier3[var4], (Transform)null);
                  }
                  break;
               case 3:
                  if (this.trackBarrier4[var4] != null) {
                     this.scene_g3d.render(this.trackBarrier4[var4], (Transform)null);
                  }
               }
            }

            this.game_profilerEndTimes[5][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[5][this.game_profilerPos] - this.game_profilerStartTimes[5][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:5," + (this.game_profilerEndTimes[5][this.game_profilerPos] - this.game_profilerStartTimes[5][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[6][this.game_profilerPos] = System.currentTimeMillis();

            int var1;
            for(var1 = 0; var1 < this.g_race.m_numRacers; ++var1) {
               this.players[var1].renderShadow();
            }

            for(var1 = 0; var1 < this.g_race.m_numCops; ++var1) {
               this.cops[var1].renderShadow();
            }

            this.game_profilerEndTimes[6][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[6][this.game_profilerPos] - this.game_profilerStartTimes[6][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:6," + (this.game_profilerEndTimes[6][this.game_profilerPos] - this.game_profilerStartTimes[6][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[7][this.game_profilerPos] = System.currentTimeMillis();
            if (this.players[0].m_bGoingBackward) {
               for(var1 = 0; var1 < this.sortedCarsList.length; ++var1) {
                  this.sortedCarsList[var1].render();
               }
            } else {
               for(var1 = this.sortedCarsList.length - 1; var1 > -1; --var1) {
                  this.sortedCarsList[var1].render();
               }
            }

            this.game_profilerEndTimes[7][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[7][this.game_profilerPos] - this.game_profilerStartTimes[7][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:7," + (this.game_profilerEndTimes[7][this.game_profilerPos] - this.game_profilerStartTimes[7][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[8][this.game_profilerPos] = System.currentTimeMillis();
            if (this.players[0].m_bGoingBackward) {
               for(var1 = 0; var1 < this.sortedCarsList.length; ++var1) {
                  this.sortedCarsList[var1].renderAlpha();
               }
            } else {
               for(var1 = this.sortedCarsList.length - 1; var1 > -1; --var1) {
                  this.sortedCarsList[var1].renderAlpha();
               }
            }

            this.game_profilerEndTimes[8][this.game_profilerPos] = System.currentTimeMillis();
            if (this.game_profilerEndTimes[8][this.game_profilerPos] - this.game_profilerStartTimes[8][this.game_profilerPos] > 100L) {
               System.out.println("large timer val:8," + (this.game_profilerEndTimes[8][this.game_profilerPos] - this.game_profilerStartTimes[8][this.game_profilerPos]));
            }

            this.game_profilerStartTimes[9][this.game_profilerPos] = System.currentTimeMillis();
            var4 = 0;
            int var5 = 0;
            int var6;
            if (this.g_race.m_type == 4) {
               var4 = (this.game_xArrowPos << 8) - (this.game_posX << 8);
               var5 = (this.game_yArrowPos << 8) - (this.game_posY << 8);
            } else if (this.g_race.m_type != 0 && this.g_race.m_type != 2) {
               if (this.g_race.m_type == 1 || this.g_race.m_type == 3) {
                  var6 = this.players[0].m_nCurCheckpoint;
                  if (var6 < this.track_checkpoints.length) {
                     var4 = (this.track_checkpoints[var6][0] << 12) - (this.game_posX << 8);
                     var5 = (this.track_checkpoints[var6][1] << 12) - (this.game_posY << 8);
                     this.scene_workerTrans.setIdentity();
                     this.scene_workerTrans.postTranslate((float)this.track_checkpoints[var6][0] / 16.0F, (float)this.track_checkpoints[var6][2] / 16.0F + 1.0F, (float)this.track_checkpoints[var6][1] / 16.0F);
                     this.scene_workerTrans.postRotate((float)(this.game_Time >> 2), 0.0F, 1.0F, 0.0F);
                     this.scene_g3d.render(this.scene_UI_indicator, this.scene_workerTrans);
                  }
               }
            } else {
               var4 = (this.game_xArrowPos << 8) - (this.game_posX << 8);
               var5 = (this.game_yArrowPos << 8) - (this.game_posY << 8);
               var6 = Integer.MAX_VALUE;
               int var7 = 0;
               boolean var8 = false;
               int var12;
               if (this.g_roadsideCop != null) {
                  if (this.g_roadsideCop.m_nState != 9 && this.g_roadsideCop.m_nTimeNotVisable > 5000) {
                     this.g_roadsideCop.m_bDisabled = true;
                  }

                  if (this.g_roadsideCop.m_bDisabled || this.g_roadsideCop.m_nState == 9) {
                     for(var1 = 0; var1 < this.track_copPoints.length; ++var1) {
                        var12 = this.ApproximateMagnitude((this.track_copPoints[var1][0] << 4) - this.players[0].m_xPos, (this.track_copPoints[var1][1] << 4) - this.players[0].m_yPos);
                        if (var6 > var12 && var6 > 15000) {
                           var6 = var12;
                           var7 = var1;
                        }
                     }

                     if (this.g_nLastRoadSideCopIndex != var7) {
                        this.g_nLastRoadSideCopIndex = var7;
                        this.g_roadsideCop.m_xPos = this.track_copPoints[var7][0] << 4;
                        this.g_roadsideCop.m_yPos = this.track_copPoints[var7][1] << 4;
                        this.g_roadsideCop.m_zPos = (this.track_copPoints[var7][2] << 4) + 1;
                        this.g_roadsideCop.InitCollision();
                        this.g_roadsideCop.m_zLastAveragePos = this.g_roadsideCop.m_zPos;
                        this.g_roadsideCop.m_xVel = 0;
                        this.g_roadsideCop.m_yVel = 0;
                        this.g_roadsideCop.m_zVel = 0;
                        this.g_roadsideCop.m_nLastWheelCount = 0;
                        this.g_roadsideCop.m_nLastAverageCount = 0;
                        this.g_roadsideCop.m_xAngVel = 0;
                        this.g_roadsideCop.m_yAngVel = 0;
                        this.g_roadsideCop.m_zAngVel = 0;
                        this.g_roadsideCop.m_nAverageSplineMovementSpeed = 0;
                        this.g_roadsideCop.UpdatePhysics(100, 655);
                        this.g_roadsideCop.UpdateTrackCollision(100, 655);
                        this.g_roadsideCop.m_xAngVel = 0;
                        this.g_roadsideCop.m_yAngVel = 0;
                        this.g_roadsideCop.m_zAngVel = 0;
                        this.g_roadsideCop.m_xVel = 0;
                        this.g_roadsideCop.m_yVel = 0;
                        this.g_roadsideCop.m_zVel = 0;
                        this.g_roadsideCop.m_zLastAveragePos = this.g_roadsideCop.m_zPos;
                        this.g_roadsideCop.m_nState = 9;
                        this.g_roadsideCop.m_bDisabled = false;
                     }
                  }
               }

               var6 = Integer.MAX_VALUE;
               var8 = false;

               for(var1 = 0; var1 < this.track_evasionPoints.length; ++var1) {
                  var12 = this.ApproximateMagnitude((this.track_evasionPoints[var1][0] << 4) - this.players[0].m_xPos, (this.track_evasionPoints[var1][1] << 4) - this.players[0].m_yPos);
                  if (var6 > var12) {
                     var6 = var12;
                     this.game_closestEvasionPickup = var1;
                  }
               }

               this.scene_workerTrans.setIdentity();
               this.scene_workerTrans.postTranslate((float)this.track_evasionPoints[this.game_closestEvasionPickup][0] / 16.0F, (float)this.track_evasionPoints[this.game_closestEvasionPickup][2] / 16.0F + 1.0F, (float)this.track_evasionPoints[this.game_closestEvasionPickup][1] / 16.0F);
               this.scene_workerTrans.postRotate((float)(this.game_Time >> 2), 0.0F, 1.0F, 0.0F);
               this.scene_g3d.render(this.scene_UI_evasion_ind, this.scene_workerTrans);
            }

            var6 = this.arctan2(var5, var4);
            if (this.game_Time > 6000 && this.game_state == 2) {
               this.scene_cameraTrans.postTranslate(0.0F, 2.0F, -5.0F);
               this.scene_cameraTrans.postRotate((float)(-this.game_angZ - var6) * 2.1457672E-5F - 90.0F, 0.0F, 1.0F, 0.0F);
               this.scene_g3d.render(this.scene_UI_arrow, this.scene_cameraTrans);
            }
         } finally {
            this.scene_g3d.releaseTarget();
         }

         this.game_profilerEndTimes[9][this.game_profilerPos] = System.currentTimeMillis();
         if (this.game_profilerEndTimes[9][this.game_profilerPos] - this.game_profilerStartTimes[9][this.game_profilerPos] > 100L) {
            System.out.println("large timer val:9," + (this.game_profilerEndTimes[9][this.game_profilerPos] - this.game_profilerStartTimes[9][this.game_profilerPos]));
         }

         this.game_profilerStartTimes[10][this.game_profilerPos] = System.currentTimeMillis();
         this.game_DrawHUD();
         this.game_profilerEndTimes[10][this.game_profilerPos] = System.currentTimeMillis();
         if (this.game_profilerEndTimes[10][this.game_profilerPos] - this.game_profilerStartTimes[10][this.game_profilerPos] > 100L) {
            System.out.println("large timer val:10," + (this.game_profilerEndTimes[10][this.game_profilerPos] - this.game_profilerStartTimes[10][this.game_profilerPos]));
         }

         ++this.frameCount;
      }
   }

   void game_DrawHUD() {
      boolean var1 = false;
      long var2 = System.currentTimeMillis();
      if (var2 - this.game_TimeUpdate > 1000L) {
         this.game_DrawHUDMain();
         this.game_TimeUpdate = var2;
         var1 = true;
      }

      if ((this.frameCount & 1) == 0 || var1) {
         this.game_DrawHUDMainEx();
      }

      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      if (this.game_Time < 6000) {
         int var4 = 5 - this.game_Time / 1000;
         switch(var4) {
         case 0:
            this.font_ingame.DrawSubstring(this.g_Text[185], 0, this.g_Text[185].length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 10, 1);
            break;
         case 1:
            this.font_ingame.DrawSubstring("1", 0, "1".length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 10, 1);
            break;
         case 2:
            this.font_ingame.DrawSubstring("2", 0, "2".length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 10, 1);
            break;
         case 3:
            this.font_ingame.DrawSubstring("3", 0, "3".length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 10, 1);
         }
      } else if (this.popupTimer > this.game_Time && this.game_state == 2) {
         if (this.popupString != null) {
            this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
            this.font_ingame.SetColor(255, 255, 255);
            this.font_ingame.DrawSubstringWrapped(this.popupString, 0, this.popupString.length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 10, 1, 0);
         } else if (this.popupImg != -1) {
            this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
            this.asset_DrawImage(this.popupImg, (this.system_nCanvasWidth >> 1) - 68, (this.system_nCanvasHeight >> 1) - 20);
         } else {
            this.popupTimer = this.game_Time - 1;
         }
      } else if (this.popupTimer > 0) {
         this.game_clearPopupText();
      }

   }

   void game_DrawHUDMainEx() {
      int var1 = -(this.sin_Array[this.game_tachometer_vel + 10000 >> 8 & 255] + ((this.sin_Array[1 + (this.game_tachometer_vel + 10000 >> 8) & 255] - this.sin_Array[this.game_tachometer_vel + 10000 >> 8 & 255]) * (this.game_tachometer_vel + 10000 - (this.game_tachometer_vel + 10000 & -256)) >> 8)) << 1;
      int var2 = -(this.sin_Array[this.game_tachometer_vel + 10000 + 49152 >> 8 & 255] + ((this.sin_Array[1 + (this.game_tachometer_vel + 10000 + 49152 >> 8) & 255] - this.sin_Array[this.game_tachometer_vel + 10000 + 49152 >> 8 & 255]) * (this.game_tachometer_vel + 10000 + 49152 - (this.game_tachometer_vel + 10000 + 49152 & -256)) >> 8)) << 1;
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.asset_DrawImage(130, 157, 0);
      this.asset_DrawImage(126, 180, 0);
      this.asset_DrawImage(278, 157, 7);
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setColor(16775581);
      m_CurrentGraphics.drawLine(202, 40, 202 + (var1 >> 10), 40 + (var2 >> 10));
      int var3 = 2 * this.players[0].GetMPH();
      var3 = var3 * 2 >> 1;
      this.game_DrawNumber(this.hudMPHImages, var3, 132, 16, 3, 20);
      if (this.players[0].m_nNitroTimer > 0) {
         if ((this.scene_timer >> 8 & 1) != 1 && this.game_state != 3) {
            this.asset_DrawImage(this.g_HudImgs[5], 4, 56);
         } else {
            this.asset_DrawImage(this.g_HudImgs[4], 4, 56);
         }
      } else {
         this.asset_DrawImage(this.g_HudImgs[3], 4, 56);
      }

   }

   void game_DrawHUDMain() {
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.asset_DrawImage(138, 0, 0);
      this.asset_DrawImage(134, 89, 0);
      if (this.g_race.m_type == 1) {
         this.asset_DrawImage(this.g_HudImgs[1], 27, 30);
      } else if (this.g_race.m_type == 3) {
         this.asset_DrawImage(this.g_HudImgs[2], 27, 30);
      } else {
         this.asset_DrawImage(this.g_HudImgs[0], 27, 30);
      }

      this.asset_DrawImage(this.g_HudImgs[6], 4, 43);

      int var1;
      for(var1 = 0; var1 < this.players[0].m_nCurrentHeatRating; ++var1) {
         this.asset_DrawImage(232, 35 + var1 * 12, 43);
      }

      if (this.players[0].m_velocityLastFrame < 0) {
         var1 = 0;
      } else if (this.players[0].m_velocityLastFrame < 100) {
         var1 = 1;
      } else {
         var1 = 2 + Math.abs(this.players[0].m_velocityLastFrame >> 11);
      }

      if (var1 >= this.gearImgsOn.length) {
         var1 = this.gearImgsOn.length - 1;
      }

      this.game_currentGear = var1;
      int var2 = 90 + var1 * 8;
      this.asset_DrawImage(this.gearImgsOn[var1], var2, 58);
      int var3;
      if (this.g_race.m_type != 3 && this.g_race.m_type != 4) {
         var3 = this.game_Time - 5000;
      } else if (this.game_Time < 5000) {
         var3 = this.g_race.m_nTimeLimit;
      } else {
         var3 = this.g_race.m_nTimeLimit - (this.game_Time - 5000);
      }

      if (var3 < 0) {
         var3 = 0;
      }

      int var4 = var3 / 1000 % 60;
      int var5 = var3 / '\uea60';
      this.game_DrawNumber(this.hudLGImages, var5, 42, 6, 2, 12);
      this.game_DrawNumber(this.hudLGImages, var4, 72, 6, 2, 12);
      int var6;
      int var7;
      if (this.game_state == 2) {
         var6 = 1;
         if (this.g_race.m_type != 1) {
            for(var7 = 1; var7 < this.g_race.m_numRacers; ++var7) {
               if (this.players[var7].m_nPlayerDist < 0) {
                  ++var6;
               } else if (this.players[var7].m_nPlayerDist == 0 && this.g_race.m_type != 4 && this.players[0].m_nCurrentSplineDistance < this.players[var7].m_nCurrentSplineDistance) {
                  ++var6;
               }
            }
         } else {
            for(var7 = 1; var7 < this.g_race.m_numRacers; ++var7) {
               if (this.players[var7].m_nCurCheckpoint > this.players[0].m_nCurCheckpoint || this.players[var7].m_nCurCheckpoint == this.players[0].m_nCurCheckpoint && this.players[var7].m_nCheckpointTime < this.players[0].m_nCheckpointTime) {
                  ++var6;
               }
            }
         }

         this.game_playerPosition = var6;
      }

      if (this.g_race.m_type == 2) {
         var6 = 0;

         for(var7 = 0; var7 < this.g_race.m_numRacers; ++var7) {
            if (!this.players[var7].m_bDisabled) {
               ++var6;
            }
         }

         this.game_DrawNumber(this.hudLGImages, var6, 7, 27, 1, 12);
         this.game_DrawNumber(this.hudLGImages, this.game_playerPosition, 7, 7, 1, 12);
      } else if (this.g_race.m_type == 4) {
         this.game_DrawNumber(this.hudLGImages, 1, 7, 27, 1, 12);
         this.game_DrawNumber(this.hudLGImages, 1, 7, 7, 1, 12);
      } else {
         this.game_DrawNumber(this.hudLGImages, this.g_race.m_numRacers, 7, 27, 1, 12);
         this.game_DrawNumber(this.hudLGImages, this.game_playerPosition, 7, 7, 1, 12);
      }

      if (this.g_race.m_type == 4) {
         this.asset_DrawImage(326, 51, 20);
         this.asset_DrawImage(326, 37, 20);
      } else if (this.g_race.m_type != 0 && this.g_race.m_type != 2) {
         if (this.g_race.m_type == 1 || this.g_race.m_type == 3) {
            this.game_DrawNumber(this.hudSMImages, this.track_checkpoints.length, 77, 30, 1, 8);
            this.game_DrawNumber(this.hudSMImages, this.players[0].m_nCurCheckpoint, 57, 30, 1, 8);
         }
      } else {
         if (this.players[0].m_nLap < 1) {
            var6 = 1;
         } else if (this.players[0].m_nLap >= this.g_race.m_laps) {
            var6 = this.g_race.m_laps;
         } else {
            var6 = this.players[0].m_nLap + 1;
         }

         this.game_DrawNumber(this.hudSMImages, this.g_race.m_laps, 77, 30, 1, 8);
         this.game_DrawNumber(this.hudSMImages, var6, 57, 30, 1, 8);
      }

      if (this.players[0].m_nNitroCount == 0) {
         this.asset_DrawImage(this.g_HudImgs[5], 4, 56);
      } else {
         this.asset_DrawImage(this.g_HudImgs[3], 4, 56);
      }

      this.game_DrawNumber(this.hudSMImages, this.players[0].m_nNitroCount, 74, 57, 1, 8);
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_FillRect(0, this.system_nCanvasHeight - 18, this.system_nCanvasWidth, 18, 0);
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      if (this.game_state == 2) {
         if (this.g_race.m_type == 3) {
            this.font_text.DrawSubstring(this.g_Text[10], 0, this.g_Text[10].length(), 94, this.system_nCanvasHeight - 20, 0);
            this.font_ingame.DrawSubstring("" + this.players[0].m_nSpeedingScore, 0, ("" + this.players[0].m_nSpeedingScore).length(), this.system_nCanvasWidth - 1, this.system_nCanvasHeight - 16, 2);
         } else if (this.g_race.m_type == 4) {
            var6 = 10000;
            boolean var9 = true;

            for(int var8 = 1; var8 < this.g_race.m_numRacers; ++var8) {
               if (this.players[var8].m_nPlayerDist < var6) {
                  var6 = this.players[var8].m_nPlayerDist;
               }
            }

            var7 = this.g_missions[this.g_race.m_nMissionID].m_nreqScore;
            var6 = this.g_missions[this.g_race.m_nMissionID].m_nreqScore - var6;
            var6 = 55 * var6 / var7;
            if (var6 > 0 && this.game_state != 5) {
               if (var6 > 55) {
                  var6 = 55;
               }
            } else {
               var6 = 0;
            }

            var6 = (this.lastBustedness + (var6 << 1)) / 3;
            this.lastBustedness = var6;
            this.font_text.DrawSubstring(this.g_Text[187], 0, this.g_Text[187].length(), 94, this.system_nCanvasHeight - 20, 0);
            this.system_SetClip(this.system_nCanvasWidth - var6 - 2, this.system_nCanvasHeight - 9, var6, 3);
            this.asset_DrawImage(204, 104, this.system_nCanvasHeight - 9);
            this.asset_DrawImage(204, 154, this.system_nCanvasHeight - 9);
         }

         this.drawSoftKey(8, 0);
      }

   }

   void game_popupText(String var1, boolean var2) {
      if (!this.popupPriority || var2) {
         this.popupPriority = var2;
         this.popupTimer = this.game_Time + 1500;
         this.popupString = var1;
         this.popupImg = -1;
      }
   }

   void game_clearPopupText() {
      this.popupTimer = 0;
      this.popupString = null;
      this.popupImg = -1;
      this.popupPriority = false;
   }

   void game_popupImg(int var1) {
      this.popupTimer = this.game_Time + 1500;
      this.popupString = null;
      this.popupImg = var1;
   }

   void game_InitStatePlay() throws Exception {
      this.game_nKeyPressed = 0;
      this.game_nLastKeyTap = 0;
      this.game_nKeyFlags = 0;
      this.game_seed = (int)System.currentTimeMillis();
      this.system_SeedRandom(this.game_seed);
      this.game_LoadAnimationResource();
      this.system_bScreenDirty = false;
      this.system_bReRenderSoftKeys = false;
      this.game_score = this.level_start_score;
      this.game_TimeUpdate = (long)(this.game_Time - 1000);
      this.frameCount = 0;
      this.game_tachometer_vel = 0;
      this.transition_Start();
   }

   void game_EndRace() {
      if (this.players[0].m_bPlayer) {
         this.players[0].m_zAng -= 8388608;
         this.players[0].m_zAng &= 16777215;
         this.players[0].m_zAng += 8388608;
         if (this.g_race.m_nMissionID != 0 && (this.g_race.m_type != 4 || this.game_playerPosition == 1)) {
            this.players[0].m_bPlayer = false;
         }
      }

      if (this.game_state != 5) {
         this.game_InitState(5);
      }

   }

   void game_KeyPressedGameOver(int var1, int var2) throws Exception {
      if (this.scene_timer >= 500) {
         if (this.scene_timer >= 10000 || !this.system_bDemoMode || this.gameOver_stage != 1) {
            if (this.gameOver_stage == 0) {
               if (var1 == -6 || var2 == 8 || var1 == 53) {
                  this.scene_timer = 0;
                  ++this.gameOver_stage;
                  if (this.g_race.m_bMissionPassed && this.g_race.m_nMissionID == 0) {
                     this.g_player.m_currentCar = this.g_player.m_equipedCar = 0;
                     this.g_cars[this.g_player.m_equipedCar].free();
                     this.g_cars[this.g_player.m_equipedCar].setParts(this.g_player.m_carSetups[this.g_player.m_equipedCar]);
                     this.g_cars[this.menu_car].m_refreshTexture = this.g_cars[this.menu_car].m_refreshWheels = this.g_cars[this.menu_car].m_refreshSpoiler = true;
                     this.menu_refreshCar(this.g_player.m_currentCar);
                  }

                  this.g_player.saveRecords();
               }
            } else {
               TheGame.NFSMW_Mission var3;
               int var4;
               if (this.gameOver_stage == 1) {
                  if (var1 != -6 && var2 != 8 && var1 != 53) {
                     if (var1 == -7 && !this.g_race.m_bMissionPassed) {
                        this.g_race.m_bMissionPassed = false;
                        this.g_race.m_timeString = "";
                        this.g_race.m_LapTimeString = "";
                        this.g_race.m_nFastestLapTime = 0;
                        this.g_race.m_nLastLapTime = 0;
                        this.g_race.m_nTopSpeed = 0;
                        this.g_race.m_nCopsSmashed = 0;
                        this.game_state = 4;
                        this.game_Time = 0;
                        this.game_cameraAngZ = 0;
                        this.players[0].m_zAng = 0;
                        this.players[0].m_nLap = 0;
                        this.players[0].m_nCurCheckpoint = 0;
                        this.players[0].m_bPlayer = true;
                        this.game_camlagX = 0;
                        this.game_camlagY = 0;
                        this.game_playerPosition = 1;
                        this.game_closestEvasionPickup = 0;
                        this.game_clearPopupText();
                        this.game_posX = this.track_splines[0][0][0] << 4;
                        this.game_posY = this.track_splines[0][0][1] << 4;
                        this.game_posZ = 0;
                        this.game_angZ = 7864320;
                        if (this.g_roadsideCop != null && (this.g_race.m_type == 0 || this.g_race.m_type == 2)) {
                           this.g_roadsideCop.Init();
                           this.g_roadsideCop.m_aiSpline = this.track_splines[0];
                           this.g_roadsideCop.m_nCopNum = 4;
                        }

                        int var8;
                        for(var8 = 0; var8 < this.g_race.m_numRacers; ++var8) {
                           this.players[var8].Init();
                           this.players[var8].m_aiSpline = this.track_splines[0];
                        }

                        for(var8 = 0; var8 < this.g_race.m_numCops; ++var8) {
                           this.cops[var8].Init();
                           this.cops[var8].m_bDisabled = true;
                           this.cops[var8].m_aiSpline = this.track_splines[0];
                        }

                        for(var8 = 0; var8 < this.g_race.m_numTraffic; ++var8) {
                           this.traffic[var8].Init();
                        }

                        this.game_waitForInit = true;

                        try {
                           this.game_InitStateLevelLoading(true);
                        } catch (Exception var6) {
                           var6.printStackTrace();
                           AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_gameover_share.hpp", 160, var6.toString());
                        } catch (Error var7) {
                           AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_gameover_share.hpp", 160, var7.toString());
                        }

                        this.game_waitForInit = false;
                     }
                  } else if (!this.g_race.m_bMission) {
                     this.ClearGameOver(4097);
                  } else {
                     var3 = this.g_missions[this.g_race.m_nMissionID];
                     var4 = this.scriptScenes[var3.m_script][2];
                     if (!this.g_race.m_bMissionPassed || var4 == -1 && this.g_race.m_nMissionUnlocked <= -1) {
                        this.ClearGameOver(4097);
                     } else {
                        this.scene_timer = 0;
                        ++this.gameOver_stage;
                        if (this.g_race.m_nMissionUnlocked == -1) {
                           ++this.gameOver_stage;
                           this.script = this.g_Text[var4];
                           this.currentPage = "";
                           this.getNextScriptPage();
                        } else {
                           TheGame.NFSMW_Mission var5 = this.g_missions[this.g_race.m_nMissionUnlocked];
                           this.script = this.g_Text[this.scriptScenes[var5.m_script][1]];
                           this.currentPage = "";
                           this.getNextScriptPage();
                        }
                     }
                  }
               } else if (this.gameOver_stage == 2) {
                  if (!this.getNextScriptPage()) {
                     var3 = this.g_missions[this.g_race.m_nMissionID];
                     var4 = this.scriptScenes[var3.m_script][2];
                     if (var4 != -1) {
                        this.script = this.g_Text[var4];
                        this.currentPage = "";
                        this.getNextScriptPage();
                        this.scene_timer = 2000;
                        ++this.gameOver_stage;
                     } else {
                        this.ClearGameOver(4097);
                     }
                  }
               } else if (!this.getNextScriptPage()) {
                  if (this.g_race.m_nMissionID > 0 && this.g_race.m_nMissionID < 6) {
                     var3 = this.g_missions[35 + this.g_race.m_nMissionID];
                     this.g_race.m_type = var3.m_raceType;
                     this.g_race.m_laps = var3.m_nLaps;
                     this.g_race.m_trackPath = var3.m_trackPath;
                     this.g_race.m_nTimeLimit = var3.m_nTimeLimit;
                     this.g_race.m_numRacers = this.raceSetups[var3.m_raceSetup][1];
                     this.g_race.m_numCops = this.raceSetups[var3.m_raceSetup][2];
                     this.g_race.m_numTraffic = this.raceSetups[var3.m_raceSetup][3];
                     this.g_race.m_bMission = true;
                     this.g_race.m_nMissionID += 35;
                     this.g_race.m_difficulty = 1 + this.g_race.m_nMissionID % 6;
                     this.ClearGameOver(8192);
                  } else if (this.g_race.m_nMissionID == 40) {
                     this.ClearGameOver(4098);
                  } else {
                     this.ClearGameOver(4097);
                  }
               }
            }

         }
      }
   }

   void ClearGameOver(int var1) throws Exception {
      this.gameOver_stage = 0;
      this.scene_Transition(var1);
   }

   void game_UpdateGameOver(int var1) throws Exception {
      this.scene_timer += var1;
      if (this.gameOver_stage == 0) {
         this.game_UpdatePlay(var1);
      }

   }

   void game_RenderGameOver() {
      if (this.g_race.m_bMission && this.g_race.m_nMissionID == 0) {
         this.game_playerPosition = this.g_race.m_numRacers;
      }

      short var2;
      if (this.game_playerPosition == 1) {
         var2 = 188;
      } else if (this.game_playerPosition == 2) {
         var2 = 189;
      } else if (this.game_playerPosition == 3) {
         var2 = 190;
      } else {
         var2 = 191;
      }

      if (this.gameOver_stage == 0) {
         this.game_RenderPlay();
         this.font_ingame.SetColor(255, 255, 255);
         this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
         m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
         boolean var3 = true;
         short var8;
         if (this.game_state == 6) {
            var8 = 187;
         } else if (this.g_race.m_type == 3) {
            if (!this.g_race.m_bMissionPassed) {
               if (this.game_Time > this.g_race.m_nTimeLimit + 5000) {
                  var8 = 196;
               } else {
                  var8 = 197;
               }
            } else {
               var8 = 200;
            }
         } else if (this.g_race.m_type == 4) {
            if (!this.g_race.m_bMissionPassed) {
               if (this.game_Time >= this.g_race.m_nTimeLimit + 5000) {
                  var8 = 196;
               } else {
                  var8 = 198;
               }
            } else {
               var8 = 199;
            }
         } else {
            var8 = 200;
            if (this.game_state != 7) {
               this.font_ingame.DrawSubstring(this.g_Text[var2], 0, this.g_Text[var2].length(), this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 10, 1);
            }
         }

         this.font_ingame.DrawSubstringWrapped(this.g_Text[var8], 0, this.g_Text[var8].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, (this.system_nCanvasHeight >> 2) + 10 + 16, 1, 0);
         this.drawSoftKey(6, 0);
      } else if (this.gameOver_stage == 1) {
         byte var9 = 15;
         this.drawBG();
         this.font_text.SetColor(255, 255, 255);
         if (this.system_bDemoMode) {
            this.font_headings.DrawSubstringWrapped(this.g_Text[200], 0, this.g_Text[200].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, var9, 1, 0);
            this.font_text.DrawSubstringWrapped(this.g_Text[16], 0, this.g_Text[16].length(), this.system_nCanvasWidth - 40, this.system_nCanvasWidth / 2, 80, 1, 0);
            if (this.scene_timer > 10000) {
               this.drawSoftKey(6, 0);
               if (!this.g_race.m_bMissionPassed) {
                  this.drawSoftKey(7, 1);
               }
            }

            return;
         }

         int var10;
         if (this.game_state == 7) {
            this.font_headings.DrawSubstringWrapped(this.g_Text[15], 0, this.g_Text[15].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, var9, 1, 0);
            var10 = var9 + 50;
         } else {
            if (this.g_race.m_bMission) {
               if (this.g_race.m_bMissionPassed && this.g_race.m_nMissionID != 0) {
                  this.font_headings.DrawSubstringWrapped(this.g_Text[14], 0, this.g_Text[14].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, var9, 1, 0);
               } else {
                  this.font_headings.DrawSubstringWrapped(this.g_Text[15], 0, this.g_Text[15].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, var9, 1, 0);
               }
            } else if (this.game_state == 6) {
               this.font_headings.DrawSubstringWrapped(this.g_Text[15], 0, this.g_Text[15].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, var9, 1, 0);
            } else {
               this.font_headings.DrawSubstringWrapped(this.g_Text[200], 0, this.g_Text[200].length(), this.system_nCanvasWidth - 16, this.system_nCanvasWidth >> 1, var9, 1, 0);
            }

            var10 = var9 + 50;
            if (this.g_race.m_type != 3 && this.g_race.m_type != 4 && this.game_state != 6) {
               this.font_ingame.DrawSubstring(this.g_Text[var2], 0, this.g_Text[var2].length(), this.system_nCanvasWidth >> 1, var10, 1);
            }
         }

         var10 += 30;
         if (this.g_race.m_bMission) {
            if (this.g_race.m_bMissionPassed && this.g_race.m_bNewMission) {
               int var4 = this.g_missions[this.g_race.m_nMissionID].m_repWon;
               int var5 = this.g_missions[this.g_race.m_nMissionID].m_cashWon;
               this.font_text.DrawSubstring(this.g_Text[40], 0, this.g_Text[40].length(), 20, var10, 0);
               this.font_text.DrawSubstring("" + var4, 0, ("" + var4).length(), 220, var10, 2);
               var10 += 18;
               this.font_text.DrawSubstring(this.g_Text[41], 0, this.g_Text[41].length(), 20, var10, 0);
               this.font_text.DrawSubstring("" + var5, 0, ("" + var5).length(), 220, var10, 2);
               var10 += 18;
            } else {
               var10 += 36;
            }
         }

         if (this.g_race.m_type == 3) {
            var10 += 18;
            this.font_text.DrawSubstring(this.g_Text[42], 0, this.g_Text[42].length(), 20, var10, 0);
            this.font_text.DrawSubstring("" + this.players[0].m_nSpeedingScore, 0, ("" + this.players[0].m_nSpeedingScore).length(), 220, var10, 2);
         }

         var10 += 18;
         this.font_text.DrawSubstring(this.g_Text[43], 0, this.g_Text[43].length(), 20, var10, 0);
         this.font_text.DrawSubstring("" + this.g_race.m_timeString, 0, ("" + this.g_race.m_timeString).length(), 220, var10, 2);
         var10 += 18;
         this.font_text.DrawSubstring(this.g_Text[44], 0, this.g_Text[44].length(), 20, var10, 0);
         this.font_text.DrawSubstring("" + this.g_race.m_nTopSpeed + "KM/H", 0, ("" + this.g_race.m_nTopSpeed + "KM/H").length(), 220, var10, 2);
         var10 += 18;
         this.font_text.DrawSubstring(this.g_Text[46], 0, this.g_Text[46].length(), 20, var10, 0);
         this.font_text.DrawSubstring("" + this.g_race.m_nCopsSmashed, 0, ("" + this.g_race.m_nCopsSmashed).length(), 220, var10, 2);
         if (this.g_race.m_laps > 1) {
            var10 += 18;
            this.font_text.DrawSubstring(this.g_Text[45], 0, this.g_Text[45].length(), 20, var10, 0);
            this.font_text.DrawSubstring("" + this.g_race.m_LapTimeString, 0, ("" + this.g_race.m_LapTimeString).length(), 220, var10, 2);
         }

         this.drawSoftKey(6, 0);
         if (!this.g_race.m_bMissionPassed) {
            this.drawSoftKey(7, 1);
         }
      } else {
         try {
            this.drawBG();
            this.asset_DrawImage(394, 0, 0);
         } catch (Exception var6) {
            var6.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_gameover_share.hpp", 470, var6.toString());
         } catch (Error var7) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_state_gameover_share.hpp", 470, var7.toString());
         }

         this.font_text.SetColor(255, 255, 255);
         this.font_text.DrawSubstringWrapped(this.currentPage, 0, this.currentPage.length(), this.system_nCanvasWidth - 16, 8, 104, 0, 0);
         this.drawSoftKey(6, 0);
      }

   }

   void drawBG() {
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 2106908);
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_FillRect(0, 0, this.system_nCanvasWidth, 18, 0);
      this.system_FillRect(0, 18, 91, 37, 0);
      this.asset_DrawImage(740, 91, 18);
      this.asset_DrawImage(736, 0, 55);
      this.asset_DrawImage(732, 0, 83);
   }

   void createTimeString() {
      int var1 = this.game_Time - 5000;
      if (var1 < 0) {
         var1 = 0;
      }

      int var2 = var1 / 1000 % 60;
      int var3 = var1 / '\uea60';
      String var4 = "";
      if (var2 < 10) {
         var4 = "0";
      }

      var4 = var4 + var2;
      String var5 = "";
      if (var3 < 10) {
         var5 = "0";
      }

      var5 = var5 + var3;
      this.g_race.m_timeString = "" + var5 + ":" + var4;
      var1 = this.g_race.m_nFastestLapTime;
      if (var1 < 0) {
         var1 = 0;
      }

      var2 = var1 / 1000 % 60;
      var3 = var1 / '\uea60';
      var4 = "";
      if (var2 < 10) {
         var4 = "0";
      }

      var4 = var4 + var2;
      var5 = "";
      if (var3 < 10) {
         var5 = "0";
      }

      var5 = var5 + var3;
      this.g_race.m_LapTimeString = "" + var5 + ":" + var4;
      if (!this.g_race.m_bMission && this.g_race.m_nFastestLapTime > 0 && (this.g_player.m_LapTimes[this.g_race.m_quickraceID] > this.g_race.m_nFastestLapTime || this.g_player.m_LapTimes[this.g_race.m_quickraceID] == 0)) {
         this.g_player.m_LapTimes[this.g_race.m_quickraceID] = this.g_race.m_nFastestLapTime;
      }

      this.g_race.m_nTopSpeed = this.players[0].m_nHighestRaceSpeed * 2;
      this.g_race.m_nTopSpeed = this.g_race.m_nTopSpeed * 2 >> 1;
   }

   boolean getNextScriptPage() {
      if (this.pagePos == -1) {
         this.pagePos = 0;
         return false;
      } else {
         if (this.currentPage == "") {
            this.pagePos = 0;
         }

         int var1 = this.script.indexOf("\\n", this.pagePos);
         if (var1 == -1) {
            this.currentPage = this.script.substring(this.pagePos);
            this.pagePos = -1;
         } else {
            this.currentPage = this.script.substring(this.pagePos, var1);
            this.pagePos = var1 + 2;
         }

         return true;
      }
   }

   void game_InitStateGameOver() throws Exception {
      if (this.gameover_pause) {
         this.scene_timer = 0;
         this.game_nKeyFlags = 0;
      } else {
         this.scene_timer = 0;
         this.gameOver_stage = 0;
         this.game_nKeyFlags = 0;
         this.players[0].m_nAccelerateTimer = 0;
         this.players[0].m_nNitroTimer = 0;
         this.createTimeString();
         this.g_race.m_bNewMission = false;
         this.g_race.m_bMissionPassed = false;
         this.g_race.m_nMissionUnlocked = -1;
         this.g_player.m_nCopsCrashed += this.g_race.m_nCopsSmashed;
         if (this.game_state == 6 || this.game_state == 7) {
            this.game_playerPosition = this.g_race.m_numRacers;
         }

         int var1;
         int var2;
         if (this.g_race.m_bMission && this.game_state != 6 && this.game_state != 7) {
            if (this.g_race.m_nMissionID == 0) {
               this.g_race.m_bMissionPassed = true;
            } else if (this.g_race.m_type == 3) {
               if (this.game_Time < this.g_race.m_nTimeLimit + 5000 && this.players[0].m_nSpeedingScore >= this.g_missions[this.g_race.m_nMissionID].m_nreqScore) {
                  this.g_race.m_bMissionPassed = true;
               }
            } else if (this.g_race.m_type != 4) {
               if (this.game_playerPosition == 1) {
                  this.g_race.m_bMissionPassed = true;
               }
            } else {
               var1 = 10000;

               for(var2 = 1; var2 < this.g_race.m_numRacers; ++var2) {
                  if (this.players[var2].m_nPlayerDist < var1) {
                     var1 = this.players[var2].m_nPlayerDist;
                  }
               }

               if (this.game_playerPosition == 1 && var1 > this.g_missions[this.g_race.m_nMissionID].m_nreqScore) {
                  this.g_race.m_bMissionPassed = true;
               }
            }
         }

         if (this.g_race.m_bMissionPassed && this.g_player.m_missionStatus[this.g_race.m_nMissionID] == 0) {
            var1 = this.g_missions[this.g_race.m_nMissionID].m_repWon;
            var2 = this.g_missions[this.g_race.m_nMissionID].m_cashWon;

            int var3;
            for(var3 = 0; var3 < 6; ++var3) {
               if (this.g_player.getTotalRep() < this.g_missions[var3].m_reqRep) {
                  if (this.g_player.getTotalRep() + var1 >= this.g_missions[var3].m_reqRep && (this.g_missions[var3].m_reqMission == -1 || this.g_player.m_missionStatus[this.g_missions[var3].m_reqMission] == 1)) {
                     this.g_race.m_nMissionUnlocked = var3;
                  }
                  break;
               }
            }

            this.g_race.m_bNewMission = true;
            if (this.g_race.m_nMissionID > 5 || this.g_race.m_nMissionID == 0) {
               this.g_player.m_rep += var1;
               this.g_player.m_cash += var2;
               this.g_player.m_missionStatus[this.g_race.m_nMissionID] = 1;
               if (this.g_race.m_nMissionID > 35) {
                  var3 = this.g_race.m_nMissionID - 35;
                  this.g_player.m_rep += this.g_missions[var3].m_repWon;
                  this.g_player.m_cash += this.g_missions[var3].m_cashWon;
                  this.g_player.m_missionStatus[var3] = 1;
               }
            }
         }

         if (this.game_state == 7) {
            this.scene_timer = 0;
            this.gameOver_stage = 1;
         }
      }

   }

   void game_KeyPressedPaused(int var1, int var2) throws Exception {
      if (this.scene_timer >= 500) {
         if (var1 == -7) {
            this.paused_action = 1;
         } else if (var1 == -6 || var2 == 8 || var1 == 53) {
            this.paused_action = 2;
         }

      }
   }

   void game_UpdatePaused(int var1) throws Exception {
      this.scene_timer += var1;
      if (this.paused_action != 0 && this.paused_hourglass) {
         this.wait(100L);
         this.asset_ChangeVolume(this.system_nVolume);
         if (this.paused_action == 1) {
            if (this.game_isLoading) {
               this.game_waitForInit = true;
               this.game_state = 4;
               this.game_ClearLevelLoading();
               this.game_waitForInit = false;
               this.scene_Transition(4096);
            } else {
               this.game_InitState(7);
            }
         } else if (this.paused_action == 2) {
            this.game_StartMusic(false);
            if (this.game_isLoading) {
               int var2 = this.game_loadState;
               this.game_waitForInit = true;
               this.game_state = 4;
               this.game_InitStateLevelLoading(this.game_reloading);
               this.game_loadState = var2;
               this.game_waitForInit = false;
            } else {
               if (this.game_state_before_pause == 7 || this.game_state_before_pause == 6 || this.game_state_before_pause == 5) {
                  this.gameover_pause = true;
               }

               this.game_state = this.game_state_before_pause;
            }
         } else {
            System.out.println("Unknown action for pause menu - just doing nothing instead");
            this.paused_action = 0;
            this.paused_hourglass = false;
         }
      }

   }

   void game_RenderPaused() {
      this.game_RenderPlay();
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_FillRect((this.system_nCanvasWidth >> 1) - 60, (this.system_nCanvasHeight >> 1) - 15, 120, 30, 0);
      this.font_ingame.SetColor(255, 255, 255);
      this.font_ingame.DrawSubstring(this.g_Text[186], 0, this.g_Text[186].length(), this.system_nCanvasWidth >> 1, this.system_nCanvasHeight >> 1, 5);
      this.drawSoftKey(5, 1);
      this.drawSoftKey(9, 0);
      if (this.paused_action != 0) {
         this.asset_DrawImage(2, (this.system_nCanvasWidth >> 1) - 8, (this.system_nCanvasHeight >> 1) - 12);
         this.paused_hourglass = true;
      } else {
         this.paused_hourglass = false;
      }

   }

   void game_InitStatePaused() throws Exception {
      this.game_nKeyFlags = 0;
      this.scene_timer = 0;
      this.paused_hourglass = false;
      this.paused_action = 0;
      this.asset_ChangeVolume(0);
      this.game_StopMusic();
   }

   void game_Construct() throws Exception {
      this.game_posZ = 768;
      this.game_posX = 6400;
      this.game_posY = 6400;
      this.game_cameraAngZ = 0;
      this.camera = new Camera();
      this.camera.setPerspective(80.0F, 1.0F, 0.5F, 1000.0F);
      this.game_loadState = 0;
      this.game_isLoading = false;

      for(int var1 = 0; var1 < 50; ++var1) {
         this.scene_timing[var1] = -1L;
      }

   }

   void game_Destroy() {
      this.game_LevelCleanup(true);
   }

   void game_Start() throws Exception {
      this.game_CreateBuffer();
      this.scene_freeWorld();
      this.game_animationsLoaded = false;
      this.background.setColor(5732756);
      this.asset_LoadImage(138, false);
      this.asset_LoadImage(134, false);
      this.asset_LoadImage(130, false);
      this.asset_LoadImage(278, false);
      this.asset_LoadImage(126, false);
      this.asset_LoadImage(394, false);
      this.asset_LoadImage(394, false);
      this.asset_LoadImage(232, false);

      int var1;
      for(var1 = 0; var1 < this.g_HudImgs.length; ++var1) {
         this.asset_LoadImage(this.g_HudImgs[var1], false);
      }

      for(var1 = 0; var1 < this.hudMPHImages.length; ++var1) {
         this.asset_LoadImage(this.hudMPHImages[var1], false);
      }

      for(var1 = 0; var1 < this.hudLGImages.length; ++var1) {
         this.asset_LoadImage(this.hudLGImages[var1], false);
      }

      for(var1 = 0; var1 < this.hudSMImages.length; ++var1) {
         this.asset_LoadImage(this.hudSMImages[var1], false);
      }

      for(var1 = 0; var1 < this.gearImgsOn.length; ++var1) {
         this.asset_LoadImage(this.gearImgsOn[var1], false);
      }

      this.game_state = 4;
      this.game_InitState(4);
      this.game_Time = 0;
      this.game_TimeUpdate = -100000L;
      this.game_cameraAngZ = 0;
      this.players[0].m_zAng = 0;
      this.players[0].m_nLap = 0;
      this.players[0].m_nCurCheckpoint = 0;
      this.players[0].m_bPlayer = true;
      this.game_camlagX = 0;
      this.game_camlagY = 0;
      this.gameover_pause = false;
      this.game_closestEvasionPickup = 0;
      this.game_playerPosition = 1;
      this.game_playingStateHadUpdate = false;
      this.scene_CreateFlashEffect();
   }

   void game_End() {
      int var1;
      if (this.trackMeshs != null && this.trackIsMesh != null) {
         for(var1 = 0; var1 < this.trackMeshs.length; ++var1) {
            this.trackMeshs[var1] = null;
            this.trackIsMesh[var1] = false;
         }
      }

      if (this.trackBarrier1 != null) {
         for(var1 = 0; var1 < 20; ++var1) {
            this.trackBarrier1[var1] = null;
            this.trackBarrier2[var1] = null;
            this.trackBarrier3[var1] = null;
            this.trackBarrier4[var1] = null;
         }
      }

      int var2;
      if (this.track_splines != null) {
         for(var1 = 0; var1 < 3; ++var1) {
            if (this.track_splines[var1] != null) {
               for(var2 = 0; var2 < this.track_splines[var1].length; ++var2) {
                  for(int var3 = 0; var3 < this.track_splines[var1][var2].length; ++var3) {
                     this.track_splines[var1][var2][var3] = 0;
                  }

                  this.track_splines[var1][var2] = null;
               }

               this.track_splines[var1] = (short[][])null;
            }
         }
      }

      this.numTrackItems = 0;
      this.scene_FreeTextures();
      this.scene_FreeAllMintMesh();
      if (this.g_cars != null) {
         for(var1 = 0; var1 < this.g_cars.length; ++var1) {
            if (this.g_cars[var1] != null) {
               this.g_cars[var1].free();
            }
         }
      }

      if (this.allCarsList != null) {
         for(var1 = 0; var1 < this.allCarsList.length; ++var1) {
            if (this.allCarsList[var1] != null) {
               this.allCarsList[var1].DeInitialize();
               this.allCarsList[var1] = null;
            }
         }

         this.allCarsList = null;
      }

      if (this.sortedCarsList != null) {
         for(var1 = 0; var1 < this.sortedCarsList.length; ++var1) {
            if (this.sortedCarsList[var1] != null) {
               this.sortedCarsList[var1].DeInitialize();
               this.sortedCarsList[var1] = null;
            }
         }

         this.sortedCarsList = null;
      }

      if (this.groundCollision != null) {
         this.groundCollision.Free();
      }

      if (this.track_pvsStruct != null) {
         for(var1 = 0; var1 < this.track_pvsStruct.length; ++var1) {
            for(var2 = 0; var2 < this.track_pvsStruct[var1].length; ++var2) {
               this.track_pvsStruct[var1][var2] = 0;
            }

            this.track_pvsStruct[var1] = null;
         }

         this.track_pvsStruct = (short[][])null;
      }

      if (this.track_pvsMask != null) {
         for(var1 = 0; var1 < this.track_pvsMask.length; ++var1) {
            for(var2 = 0; var2 < this.track_pvsMask[var1].length; ++var2) {
               this.track_pvsMask[var1][var2] = 0;
            }

            this.track_pvsMask[var1] = null;
         }

         this.track_pvsMask = (short[][])null;
      }

      if (this.track_pvsXYZ != null) {
         for(var1 = 0; var1 < this.track_pvsXYZ.length; ++var1) {
            for(var2 = 0; var2 < this.track_pvsXYZ[var1].length; ++var2) {
               this.track_pvsXYZ[var1][var2] = 0;
            }

            this.track_pvsXYZ[var1] = null;
         }

         this.track_pvsXYZ = (short[][])null;
      }

      if (this.track_evasionPoints != null) {
         for(var1 = 0; var1 < this.track_evasionPoints.length; ++var1) {
            this.track_evasionPoints[var1] = null;
         }

         this.track_evasionPoints = (short[][])null;
      }

      if (this.track_checkpoints != null) {
         for(var1 = 0; var1 < this.track_checkpoints.length; ++var1) {
            this.track_checkpoints[var1] = null;
         }

         this.track_checkpoints = (short[][])null;
      }

      if (this.track_copPoints != null) {
         for(var1 = 0; var1 < this.track_copPoints.length; ++var1) {
            this.track_copPoints[var1] = null;
         }

         this.track_copPoints = (short[][])null;
      }

      if (this.g_roadsideCop != null) {
         this.g_roadsideCop.DeInitialize();
         this.g_roadsideCop = null;
      }

      if (this.fire_sprites != null) {
         for(var1 = 0; var1 < 4; ++var1) {
            if (this.fire_sprites[var1] != null) {
               this.fire_sprites[var1] = null;
            }
         }
      }

      this.scene_UI_indicator = null;
      this.scene_UI_arrow = null;
      this.scene_UI_evasion_ind = null;
      this.flash_effect_sprite = null;
      this.asset_FreeImage(138);
      this.asset_FreeImage(134);
      this.asset_FreeImage(130);
      this.asset_FreeImage(278);
      this.asset_FreeImage(126);
      this.asset_FreeImage(394);
      this.asset_FreeImage(394);
      this.asset_FreeImage(232);
      if (this.g_HudImgs != null) {
         for(var1 = 0; var1 < this.g_HudImgs.length; ++var1) {
            this.asset_FreeImage(this.g_HudImgs[var1]);
         }
      }

      if (this.hudMPHImages != null) {
         for(var1 = 0; var1 < this.hudMPHImages.length; ++var1) {
            this.asset_FreeImage(this.hudMPHImages[var1]);
         }
      }

      if (this.hudLGImages != null) {
         for(var1 = 0; var1 < this.hudLGImages.length; ++var1) {
            this.asset_FreeImage(this.hudLGImages[var1]);
         }
      }

      if (this.hudSMImages != null) {
         for(var1 = 0; var1 < this.hudSMImages.length; ++var1) {
            this.asset_FreeImage(this.hudSMImages[var1]);
         }
      }

      if (this.gearImgsOn != null) {
         for(var1 = 0; var1 < this.gearImgsOn.length; ++var1) {
            this.asset_FreeImage(this.gearImgsOn[var1]);
         }
      }

      this.game_FreeBuffer();
      this.game_StartMusic(true);
      System.gc();
   }

   void game_Update(int var1) throws Exception {
      if (!this.game_waitForInit) {
         if (this.game_state == 2) {
            this.game_UpdatePlay(var1);
         } else if (this.game_state == 4) {
            this.game_UpdateLevelLoading(var1);
         } else if (this.game_state == 3) {
            this.game_UpdatePaused(var1);
         } else if (this.game_state == 5 || this.game_state == 6 || this.game_state == 7) {
            this.game_UpdateGameOver(var1);
         }

         this.game_profilerStartTimes[0][this.game_profilerPos] = (long)var1;
      }
   }

   void game_PointerPressed(int var1, int var2) throws Exception {
   }

   void game_PointerReleased(int var1, int var2) throws Exception {
   }

   void game_KeyPressed(int var1, int var2) throws Exception {
      if (!this.game_waitForInit) {
         if (this.game_state == 2) {
            this.game_KeyPressedPlay(var1, var2);
         }

         if (this.game_state == 3) {
            this.game_KeyPressedPaused(var1, var2);
         }

         if (this.game_state == 5 || this.game_state == 6 || this.game_state == 7) {
            this.game_KeyPressedGameOver(var1, var2);
         }

      }
   }

   void game_KeyReleased(int var1, int var2) {
      if (!this.game_waitForInit) {
         if (this.game_state == 2) {
            this.game_KeyReleasedPlay(var1, var2);
         }

      }
   }

   void game_RenderHUD() {
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);

      for(int var1 = 1; var1 < 30; ++var1) {
         this.game_profilerTotalTimes[var1] = 0;

         for(int var2 = 0; var2 < 32; ++var2) {
            this.game_profilerTotalTimes[var1] = (int)((long)this.game_profilerTotalTimes[var1] + (this.game_profilerEndTimes[var1][var2] - this.game_profilerStartTimes[var1][var2]));
         }

         this.game_profilerColours[var1] = 8421504;
      }

      ++this.game_profilerPos;
      if (this.game_profilerPos > 31) {
         this.game_profilerPos = 0;
      }

   }

   void game_Render() {
      if (!this.game_waitForInit) {
         if (this.game_state == 2) {
            this.game_RenderPlay();
         } else if (this.game_state == 3) {
            this.game_RenderPaused();
         } else if (this.game_state == 4) {
            this.game_RenderLevelLoading();
         } else if (this.game_state == 5 || this.game_state == 7 || this.game_state == 6) {
            this.game_RenderGameOver();
         }

         this.game_RenderHUD();
      }
   }

   void game_Pause() throws Exception {
      if (this.game_state != 3) {
         this.game_state_before_pause = this.game_state;
      }

      this.game_InitState(3);
   }

   void game_Return() throws Exception {
      this.menu_End();
      this.scene_nCurrentScene = 8192;
      this.game_waitForInit = true;
      this.game_LoadAnimationResource();
      this.game_waitForInit = false;
   }

   void game_InitState(int var1) {
      this.game_waitForInit = true;
      this.game_state = (byte)var1;

      try {
         if (var1 == 2) {
            this.game_InitStatePlay();
         } else if (var1 == 3) {
            this.game_InitStatePaused();
         } else if (var1 == 4) {
            this.game_InitStateLevelLoading(false);
         } else if (var1 == 5 || var1 == 6 || this.game_state == 7) {
            this.game_InitStateGameOver();
         }
      } catch (Exception var3) {
         var3.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_share.hpp", 633, var3.toString());
      } catch (Error var4) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_share.hpp", 633, var4.toString());
      }

      this.game_waitForInit = false;
   }

   void game_StartMusic(boolean var1) {
      try {
         this.game_StopMusic();
         if (var1) {
            while(true) {
               int var2 = (this.system_GetRandom() & 255) % this.scene_musicTracks.length;
               if (var2 != this.scene_currentMusicTrack) {
                  this.scene_currentMusicTrack = var2;
                  break;
               }
            }
         }

         if (this.system_nVolume > 0) {
            this.asset_LoadSound(this.scene_musicTracks[this.scene_currentMusicTrack], -1);
            this.asset_PlaySoundNowIfPossible(this.scene_musicTracks[this.scene_currentMusicTrack]);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_share.hpp", 664, var3.toString());
      } catch (Error var4) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\game_share.hpp", 664, var4.toString());
      }

   }

   void game_StopMusic() {
      if (this.scene_currentMusicTrack != -1) {
         this.asset_StopSound(-1);
         this.asset_FreeSound(this.scene_musicTracks[this.scene_currentMusicTrack]);
      }

   }

   void limbo_Render() {
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      this.system_FillRect(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight, 1777631);
      this.system_SetClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      m_CurrentGraphics.setClip(0, 0, this.system_nCanvasWidth, this.system_nCanvasHeight);
      String var1 = "0";
      this.font_text.SetColor(255, 255, 255);
      int var2 = (int)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
      int var3 = var2 % 1000;
      String var4 = "";
      if (var3 < 100) {
         var4 = "0";
      }

      if (var3 < 10) {
         var4 = "00";
      }

      var4 = var4 + var3;
      int var5 = (var2 - var3) % 1000000 / 1000;
      String var6 = "";
      if (var5 < 100) {
         var6 = "0";
      }

      if (var5 < 10) {
         var6 = "00";
      }

      var6 = var6 + var5;
      int var7 = (var2 - var3 - var5) % 1000000000 / 1000000;
      String var8 = "";
      if (var7 > 0) {
         var8 = var8 + var7 + ",";
      }

      var1 = "MEM = " + var8 + var6 + "," + var4 + "]";
      this.font_text.DrawSubstring(var1, 0, var1.length(), this.system_nCanvasWidth / 2, this.system_nCanvasHeight / 2 - 32, 1);
   }

   void limbo_Update(int var1) throws Exception {
   }

   void limbo_KeyPressed(int var1, int var2) throws Exception {
      this.scene_Transition(4097);
   }

   void scene_Construct() throws Exception {
      int var1;
      for(var1 = 0; var1 < 23; ++var1) {
         this.system_GetRandom();
      }

      this.system_bDemoMode = false;
      if (m_Midlet.getAppProperty("Demo-Mode") != null && m_Midlet.getAppProperty("Demo-Mode").equalsIgnoreCase("True")) {
         this.system_bDemoMode = true;
         this.g_player.clearProfile();
      }

      this.scene_g3d = Graphics3D.getInstance();
      this.scene_ConstructAppearanceParms();
      this.background = new Background();
      this.background.setColor(5732756);
      this.asset_LoadImage(948, false);
      this.font_text = new TheGame.CFont();
      this.font_text.Construct();
      this.font_headings = new TheGame.CFont();
      this.font_headings.Construct();
      this.font_ingame = new TheGame.CFont();
      this.font_ingame.Construct();
      this.font_text.SetSystem(Font.getFont(64, 0, 8));
      this.font_text.SetColor(255, 255, 255);
      this.font_headings.SetSystem(Font.getFont(64, 0, 8));
      this.font_headings.SetColor(255, 255, 255);
      this.font_ingame.SetSystem(Font.getFont(64, 0, 8));
      this.font_ingame.SetColor(255, 255, 255);
      this.menu_Construct();
      this.game_Construct();

      for(var1 = 0; var1 < 3; ++var1) {
         this.players[var1] = new TheGame.Car(var1);
      }

      for(var1 = 0; var1 < 2; ++var1) {
         this.cops[var1] = new TheGame.Car(-var1);
      }

      for(var1 = 0; var1 < 1; ++var1) {
         this.traffic[var1] = new TheGame.Car(-var1 - 3);
      }

      this.scene_nCurrentScene = 16384;
      this.scene_timer = 0;
      this.scene_timerUpdate = true;
      this.scene_Start();
   }

   void scene_ConstructAppearanceParms() {
      this.polygonMode_Persp = new PolygonMode();
      this.polygonMode_NoPersp = new PolygonMode();
      this.compositingMode_ZWRITEREAD = new CompositingMode();
      this.compositingMode_ZWRITE = new CompositingMode();
      this.compositingMode_ZNONE = new CompositingMode();
      this.compositingMode_ZREAD_Alpha = new CompositingMode();
      this.compositingMode_ZWRITE_Alpha = new CompositingMode();
      this.compositingMode_ZWRITE_AlphaAdd = new CompositingMode();
      this.compositingMode_ZNONE_AlphaAdd = new CompositingMode();
      this.polygonMode_NoPersp.setPerspectiveCorrectionEnable(false);
      this.polygonMode_NoPersp.setCulling(160);
      this.polygonMode_NoPersp.setShading(164);
      this.polygonMode_Persp.setPerspectiveCorrectionEnable(true);
      this.polygonMode_Persp.setCulling(160);
      this.polygonMode_Persp.setShading(164);
      this.compositingMode_ZNONE.setDepthTestEnable(false);
      this.compositingMode_ZNONE.setDepthWriteEnable(false);
      this.compositingMode_ZWRITE_AlphaAdd.setDepthTestEnable(true);
      this.compositingMode_ZWRITE_AlphaAdd.setDepthWriteEnable(false);
      this.compositingMode_ZWRITE_AlphaAdd.setBlending(65);
      this.compositingMode_ZNONE_AlphaAdd.setDepthTestEnable(false);
      this.compositingMode_ZNONE_AlphaAdd.setDepthWriteEnable(false);
      this.compositingMode_ZNONE_AlphaAdd.setBlending(65);
      this.compositingMode_ZREAD_Alpha.setDepthTestEnable(true);
      this.compositingMode_ZREAD_Alpha.setDepthWriteEnable(true);
      this.compositingMode_ZREAD_Alpha.setBlending(64);
      this.compositingMode_ZWRITE_Alpha.setDepthTestEnable(false);
      this.compositingMode_ZWRITE_Alpha.setDepthWriteEnable(true);
      this.compositingMode_ZWRITE_Alpha.setBlending(64);
      this.compositingMode_ZWRITEREAD.setDepthTestEnable(true);
      this.compositingMode_ZWRITEREAD.setDepthWriteEnable(true);
      this.compositingMode_ZWRITE.setDepthTestEnable(false);
      this.compositingMode_ZWRITE.setDepthWriteEnable(true);
   }

   void scene_Destroy() {
      this.system_SaveAllConfigs();
      this.game_Destroy();
      this.menu_Destroy();
      this.scene_freeWorld();
   }

   void scene_freeWorld() {
   }

   void scene_Start() throws Exception {
      if ((this.scene_nCurrentScene & 8192) != 0) {
         try {
            this.game_Start();
         } catch (Exception var6) {
            var6.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4363, var6.toString());
         } catch (Error var7) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4363, var7.toString());
         }
      } else if ((this.scene_nCurrentScene & 4096) != 0) {
         try {
            this.menu_Start();
         } catch (Exception var4) {
            var4.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4372, var4.toString());
         } catch (Error var5) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4372, var5.toString());
         }
      } else if ((this.scene_nCurrentScene & 16384) != 0) {
         try {
            this.asset_LoadSound(this.scene_musicTracks[0], -1);
            this.asset_FreeSound(this.scene_musicTracks[0]);
            this.splash_Start();
         } catch (Exception var2) {
            var2.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4387, var2.toString());
         } catch (Error var3) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4387, var3.toString());
         }
      }

   }

   void scene_End() {
      if ((this.scene_nCurrentScene & 8192) != 0) {
         this.game_End();
      } else if ((this.scene_nCurrentScene & 4096) != 0) {
         this.g_player.saveRecords();
         this.menu_End();
      } else if ((this.scene_nCurrentScene & 16384) != 0) {
         this.splash_End();
      }

   }

   void scene_Pause(boolean var1) {
      this.game_nKeyPressed = 0;
      this.game_nTimeKeyHeld = 0;
      if (var1) {
         if ((this.scene_nCurrentScene & 8192) != 0) {
            try {
               this.game_Pause();
            } catch (Exception var3) {
               var3.printStackTrace();
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4423, var3.toString());
            } catch (Error var4) {
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4423, var4.toString());
            }
         }
      } else {
         this.system_bReRenderSoftKeys = true;
      }

   }

   void scene_Show(boolean var1) {
      this.game_nKeyPressed = 0;
      this.game_nTimeKeyHeld = 0;
      if (!var1) {
         if ((this.scene_nCurrentScene & 8192) != 0) {
            try {
               this.scene_Pause(true);
            } catch (Exception var3) {
               this.system_End();
               m_Midlet.notifyDestroyed();
               return;
            }
         } else if ((this.scene_nCurrentScene & 4096) != 0) {
            this.menu_showPauseScreen();
         } else if ((this.scene_nCurrentScene & 16384) != 0) {
            this.menu_SplashPause = true;
         }
      }

   }

   void scene_Transition(int var1) throws Exception {
      this.scene_End();
      System.gc();
      this.scene_nCurrentScene = var1;
      this.scene_Start();
   }

   void scene_Update(int var1) throws Exception {
      if (this.scene_timerUpdate) {
         this.scene_timer += var1;
      }

      if ((this.scene_nCurrentScene & 32768) != 0) {
         try {
            this.transition_Update(var1);
         } catch (Exception var11) {
            var11.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4524, var11.toString());
         } catch (Error var12) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4524, var12.toString());
         }
      } else if ((this.scene_nCurrentScene & 8192) != 0) {
         try {
            this.game_Update(var1);
         } catch (Exception var9) {
            var9.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4532, var9.toString());
         } catch (Error var10) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4532, var10.toString());
         }
      } else if ((this.scene_nCurrentScene & 4096) != 0) {
         try {
            this.menu_Update(var1);
         } catch (Exception var7) {
            var7.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4540, var7.toString());
         } catch (Error var8) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4540, var8.toString());
         }
      } else if ((this.scene_nCurrentScene & 16384) != 0) {
         try {
            this.splash_Update(var1);
         } catch (Exception var5) {
            var5.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4548, var5.toString());
         } catch (Error var6) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4548, var6.toString());
         }
      } else if ((this.scene_nCurrentScene & 131072) != 0) {
         try {
            this.limbo_Update(var1);
         } catch (Exception var3) {
            var3.printStackTrace();
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4556, var3.toString());
         } catch (Error var4) {
            AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4556, var4.toString());
         }
      }

   }

   void scene_PointerPressed(int var1, int var2) throws Exception {
      try {
         if ((this.scene_nCurrentScene & 8192) != 0) {
            this.game_PointerPressed(var1, var2);
         } else if ((this.scene_nCurrentScene & 4096) != 0) {
            this.menu_PointerPressed(var1, var2);
         } else if ((this.scene_nCurrentScene & 16384) != 0) {
            this.splash_PointerPressed(var1, var2);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4577, var4.toString());
      } catch (Error var5) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4577, var5.toString());
      }

   }

   void scene_PointerReleased(int var1, int var2) throws Exception {
      try {
         if ((this.scene_nCurrentScene & 8192) != 0) {
            this.game_PointerReleased(var1, var2);
         } else if ((this.scene_nCurrentScene & 4096) != 0) {
            this.menu_PointerReleased(var1, var2);
         } else if ((this.scene_nCurrentScene & 16384) != 0) {
            this.splash_PointerReleased(var1, var2);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4594, var4.toString());
      } catch (Error var5) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4594, var5.toString());
      }

   }

   void scene_KeyPressed(int var1, int var2) throws Exception {
      try {
         if ((this.scene_nCurrentScene & 32768) != 0) {
            this.transition_KeyPressed(var1, var2);
         } else if ((this.scene_nCurrentScene & 8192) != 0) {
            this.game_KeyPressed(var1, var2);
         } else if ((this.scene_nCurrentScene & 4096) != 0) {
            this.menu_KeyPressed(var1, var2);
         } else if ((this.scene_nCurrentScene & 16384) != 0) {
            this.splash_KeyPressed(var1, var2);
         } else if ((this.scene_nCurrentScene & 131072) != 0) {
            this.limbo_KeyPressed(var1, var2);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4620, var4.toString());
      } catch (Error var5) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4620, var5.toString());
      }

   }

   void scene_KeyReleased(int var1, int var2) {
      if ((this.scene_nCurrentScene & 8192) != 0) {
         this.game_KeyReleased(var1, var2);
      } else if ((this.scene_nCurrentScene & 4096) != 0) {
         this.menu_KeyReleased(var1, var2);
      }

   }

   void scene_Render() {
      try {
         if ((this.scene_nCurrentScene & 32768) != 0) {
            this.transition_Render();
         } else if ((this.scene_nCurrentScene & 8192) != 0) {
            this.game_Render();
         } else if ((this.scene_nCurrentScene & 4096) != 0) {
            this.menu_Render();
         } else if ((this.scene_nCurrentScene & 16384) != 0) {
            this.splash_Render();
         } else if ((this.scene_nCurrentScene & 131072) != 0) {
            this.limbo_Render();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4776, var2.toString());
      } catch (Error var3) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4776, var3.toString());
      }

   }

   void scene_PlaySoundNowIfPossible(int var1) {
      if (this.scene_lastSnd != var1) {
         this.asset_StopSound(-1);
         this.asset_PlaySoundNowIfPossible(var1);
         this.scene_lastSnd = var1;
      }
   }

   int binLoad(int var1) {
      try {
         this.asset_LoadData(var1);
         short var2 = (short)((this.asset_DataArray[var1 + 1] & '\uffff') >> 0);
         short var3 = (short)((this.asset_DataArray[var2 + 0] & -65536) >> 16);
         int var4 = (this.asset_DataArray[var1 + 0] & -1) >> 0;
         short var5 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16);
         this.map_dataPointer = var4;
         return var3;
      } catch (Exception var6) {
         var6.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4830, var6.toString());
      } catch (Error var7) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 4830, var7.toString());
      }

      return -1;
   }

   void binFree(int var1) {
      this.asset_FreeData(var1);
   }

   short binGetShort(int var1) {
      int var2 = this.asset_DataBufArray[var1][this.map_dataPointer];
      int var3 = this.asset_DataBufArray[var1][this.map_dataPointer + 1];
      if (var2 < 0) {
         var2 += 256;
      }

      if (var3 < 0) {
         var3 += 256;
      }

      this.map_dataPointer += 2;
      return (short)(var2 & 255 | (var3 & 255) << 8);
   }

   short[][] loadPointSet(int var1) {
      int var2 = this.binLoad(var1);
      short var3 = this.binGetShort(var2);
      short[][] var4 = new short[var3][3];

      for(int var5 = 0; var5 < var3; ++var5) {
         var4[var5][0] = this.binGetShort(var2);
         var4[var5][1] = (short)(-this.binGetShort(var2));
         var4[var5][2] = this.binGetShort(var2);
      }

      this.binFree(var1);
      return var4;
   }

   void loadSplineSet(int var1, int var2) {
      int var3 = this.binLoad(var1);
      int var4 = (this.asset_DataArray[var1 + 0] & -1) >> 0;
      int var5 = (short)((this.asset_DataArray[var1 + 1] & -65536) >> 16) - 6;

      do {
         short var6 = this.binGetShort(var3);
         this.track_splines[var2] = new short[var6][5];

         for(int var7 = 0; var7 < var6; ++var7) {
            this.track_splines[var2][var7][0] = this.binGetShort(var3);
            this.track_splines[var2][var7][1] = (short)(-this.binGetShort(var3));
            this.track_splines[var2][var7][2] = this.binGetShort(var3);
            this.track_splines[var2][var7][3] = -1;
            this.track_splines[var2][var7][4] = 0;
         }

         ++var2;
      } while(this.map_dataPointer - var4 < var5);

      this.binFree(var1);
   }

   void scene_LoadTrackSplines(int var1) {
      this.loadSplineSet(this.g_trackPaths[var1].m_AISpline, 0);
      if (this.g_race.m_type == 1) {
         this.loadSplineSet(this.g_trackPaths[var1].m_AISpline, 1);
      }

      this.loadSplineSet(this.g_trackPaths[var1].m_trafficSpline, 2);
   }

   void scene_InitTrackSplines() {
      int var1;
      int var2;
      int var3;
      for(var1 = 0; var1 < 3; ++var1) {
         this.aiPathCollision.Init();
         var2 = -1;
         var3 = -1;

         for(int var4 = 0; var4 < this.track_splines[var1].length; ++var4) {
            if (var2 != this.aiPathCollision.x || var3 != this.aiPathCollision.y) {
               this.aiPathCollision.Init();
            }

            var2 = this.track_splines[var1][var4][0] << 12;
            var3 = -this.track_splines[var1][var4][1] << 12;
            this.groundCollision.TestPointForCollision(var2, var3, this.aiPathCollision);
            this.track_splines[var1][var4][3] = this.aiPathCollision.nTriangleID;
         }
      }

      for(var1 = 0; var1 < this.track_splines[2].length; ++var1) {
         var2 = Integer.MAX_VALUE;
         var3 = 0;
         boolean var7 = false;

         for(int var5 = 0; var5 < this.track_splines[0].length; ++var5) {
            int var6 = this.ApproximateMagnitude(this.track_splines[0][var5][0] - this.track_splines[2][var1][0], this.track_splines[0][var5][1] - this.track_splines[2][var1][1]);
            if (var2 > var6) {
               var2 = var6;
               var3 = var5;
               var7 = false;
            }
         }

         if (!var7) {
            this.track_splines[2][var1][4] = (short)var3;
         } else {
            this.track_splines[2][var1][4] = (short)(-var3 - 1);
         }
      }

   }

   short[][] scene_LoadPVS(String var1) {
      if (this.track_pvsStruct == null) {
         this.track_pvsStruct = new short[200][90];
         this.track_pvsMask = new short[200][90];
         this.track_pvsXYZ = new short[200][3];
      }

      int var6;
      for(int var5 = 0; var5 < 200; ++var5) {
         for(var6 = 0; var6 < 90; ++var6) {
            this.track_pvsStruct[var5][var6] = -1;
            this.track_pvsMask[var5][var6] = 0;
         }
      }

      try {
         String var14 = var1 + "_pvs/" + var1 + ".pvs";
         DataInputStream var2 = new DataInputStream(this.getClass().getResourceAsStream(var14));
         int var3 = var2.available();
         var6 = 0;
         int var7 = 0;
         short var8 = 0;
         this.track_pvsXYZ[var6][0] = (short)(var2.readByte() & 255 | (var2.readByte() & 255) << 8);
         this.track_pvsXYZ[var6][1] = (short)(-((short)(var2.readByte() & 255 | (var2.readByte() & 255) << 8)));
         this.track_pvsXYZ[var6][2] = (short)(var2.readByte() & 255 | (var2.readByte() & 255) << 8);
         int var11 = 6;

         while(var11 < var3) {
            short var9 = (short)(var2.readByte() & 255 | (var2.readByte() & 255) << 8);
            short var10 = (short)(var2.readByte() & 255 | (var2.readByte() & 255) << 8);
            var11 += 4;
            if (var10 != -1) {
               this.track_pvsMask[var6][var7] = var9;
               this.track_pvsStruct[var6][var7] = var10;
               var8 = (short)Math.max(var7, var8);
               ++var7;
            } else {
               this.track_pvsStruct[var6][var7] = -1;
               var7 = 0;
               ++var6;
               if (var11 + 6 < var3) {
                  this.track_pvsXYZ[var6][0] = (short)(var2.readByte() & 255 | (var2.readByte() & 255) << 8);
                  this.track_pvsXYZ[var6][1] = (short)(-((short)(var2.readByte() & 255 | (var2.readByte() & 255) << 8)));
                  this.track_pvsXYZ[var6][2] = (short)(var2.readByte() & 255 | (var2.readByte() & 255) << 8);
                  var11 += 6;
               }
            }
         }

         var2.close();
         var2 = null;
         this.track_maxPVSi = var8++;
      } catch (Exception var12) {
         var12.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5106, var12.toString());
      } catch (Error var13) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5106, var13.toString());
      }

      return this.track_pvsStruct;
   }

   void scene_FreeTextures() {
      TheGame.TextureRef var1 = null;

      for(int var2 = 0; var2 < this.vTextureList.size(); ++var2) {
         var1 = (TheGame.TextureRef)this.vTextureList.elementAt(var2);
         var1.m_name = null;
         var1.m_texture = null;
      }

      this.vTextureList.removeAllElements();
   }

   TheGame.MintMesh[] scene_FindMintMesh(String var1) {
      TheGame.ObjectRef var2 = null;

      for(int var3 = 0; var3 < this.vObjectList.size(); ++var3) {
         var2 = (TheGame.ObjectRef)this.vObjectList.elementAt(var3);
         if (var1.compareTo(var2.m_name) == 0) {
            return var2.m_object;
         }
      }

      return null;
   }

   boolean scene_FreeMintMesh(String var1) {
      TheGame.ObjectRef var2 = null;

      for(int var3 = 0; var3 < this.vObjectList.size(); ++var3) {
         var2 = (TheGame.ObjectRef)this.vObjectList.elementAt(var3);
         if (var1.compareTo(var2.m_name) == 0) {
            for(int var4 = 0; var4 < var2.m_object.length; ++var4) {
               var2.m_object[var4].m_mesh = null;
               var2.m_object[var4].m_textureName = null;
               var2.m_object[var4].m_meshName = null;
               var2.m_object[var4] = null;
               var2.m_name = null;
            }

            this.vObjectList.removeElementAt(var3);
            return true;
         }
      }

      return false;
   }

   boolean scene_FreeAllMintMesh() {
      TheGame.ObjectRef var1 = null;

      byte var4;
      for(int var2 = 0; var2 < this.vObjectList.size(); var2 = var4 + 1) {
         var1 = (TheGame.ObjectRef)this.vObjectList.elementAt(var2);

         for(int var3 = 0; var3 < var1.m_object.length; ++var3) {
            var1.m_object[var3].m_mesh = null;
            var1.m_object[var3].m_textureName = null;
            var1.m_object[var3].m_meshName = null;
            var1.m_object[var3] = null;
         }

         this.vObjectList.removeElementAt(var2);
         var4 = 0;
      }

      return true;
   }

   Texture2D scene_FindTexture(String var1) {
      TheGame.TextureRef var2 = null;

      for(int var3 = 0; var3 < this.vTextureList.size(); ++var3) {
         var2 = (TheGame.TextureRef)this.vTextureList.elementAt(var3);
         if (var1.compareTo(var2.m_name) == 0) {
            this.tex_ref = var2;
            return var2.m_texture;
         }
      }

      this.tex_ref = null;
      return null;
   }

   Image scene_LoadTexImage(String var1) {
      Image var2 = null;

      try {
         int var3 = var1.indexOf(".png");
         if (var3 <= 0) {
            return null;
         }

         String var4 = var1.substring(0, var3) + "_png";
         String var5 = var4 + var1;
         var2 = Image.createImage(var5);
      } catch (Exception var6) {
         var6.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5260, var6.toString());
      } catch (Error var7) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5260, var7.toString());
      }

      return var2;
   }

   Texture2D scene_CreateTexture(String var1, Image var2, boolean var3, boolean var4) {
      Object var5 = null;
      Texture2D var6 = null;
      if (var1.startsWith("/a_") || var1.startsWith("/A_")) {
         var4 = true;
      }

      Image2D var7;
      try {
         if (var4) {
            var7 = new Image2D(100, var2);
         } else {
            var7 = new Image2D(99, var2);
         }

         var6 = new Texture2D(var7);
         var6.setFiltering(208, 210);
         var6.setWrapping(241, 241);
         var6.setBlending(228);
      } catch (Exception var8) {
         var8.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5289, var8.toString());
      } catch (Error var9) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5289, var9.toString());
      }

      if (var3) {
         TheGame.TextureRef var10 = new TheGame.TextureRef(var1, var6, var4);
         this.vTextureList.addElement(var10);
         this.tex_ref = var10;
         var7 = null;
      } else {
         this.tex_ref = null;
      }

      return var6;
   }

   boolean scene_LoadMeshTexture(Mesh var1, String var2, int var3) {
      Texture2D var4 = null;
      if (var3 != 1 && var3 != 2) {
         var4 = this.scene_FindTexture(var2);
      }

      if (var4 == null) {
         Image var5 = this.scene_LoadTexImage(var2);
         var4 = this.scene_CreateTexture(var2, var5, var3 != 1 && var3 != 2, false);
         var5 = null;
      }

      try {
         this.appearance = var1.getAppearance(0);
         if (this.appearance == null) {
            this.appearance = new Appearance();
            var1.setAppearance(0, this.appearance);
         }

         this.appearance.setTexture(0, var4);
         this.appearance.setPolygonMode(this.polygonMode_NoPersp);
         if (this.tex_ref == null) {
            this.appearance.setCompositingMode(this.compositingMode_ZWRITEREAD);
         } else if (this.tex_ref.m_alpha) {
            this.appearance.setCompositingMode(this.compositingMode_ZREAD_Alpha);
         } else {
            this.appearance.setCompositingMode(this.compositingMode_ZWRITEREAD);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5350, var6.toString());
      } catch (Error var7) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5350, var7.toString());
      }

      this.appearance = null;
      return true;
   }

   boolean scene_OpenModel(String var1, int var2, boolean var3) {
      while(!this.scene_LoadM3GMesh(var1, var2, var3)) {
         ;
      }

      return false;
   }

   boolean scene_LoadM3GMesh(String var1, int var2, boolean var3) {
      byte[] var4 = null;
      Object var5 = null;
      TheGame.MintMesh[] var6 = null;
      int var7 = 0;
      int var8 = 0;
      boolean var9 = false;
      boolean var10 = false;
      String var11 = null;
      String var12 = null;

      try {
         Object3D[] var13 = null;
         if (var3) {
            var6 = this.scene_FindMintMesh(var1);
         }

         if (var6 == null) {
            String var14 = "/" + var1 + "_m3g/" + var1 + ".m3g";

            try {
               var13 = Loader.load(var14);
            } catch (Exception var28) {
               var28.printStackTrace();
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5417, var28.toString());
            } catch (Error var29) {
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5417, var29.toString());
            }

            int var36 = var13.length;
            var6 = new TheGame.MintMesh[var36];
            if (var3) {
               TheGame.ObjectRef var15 = new TheGame.ObjectRef(var1, var6);

               try {
                  this.vObjectList.addElement(var15);
               } catch (Exception var26) {
                  var26.printStackTrace();
                  AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5430, var26.toString());
               } catch (Error var27) {
                  AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5430, var27.toString());
               }

               var15 = null;
            }

            Mesh var16;
            for(int var37 = 0; var37 < var13.length; ++var37) {
               try {
                  var16 = (Mesh)var13[var37];
                  if (var16 != null) {
                     var6[var37] = new TheGame.MintMesh();
                     var6[var37].m_mesh = var16;
                  }
               } catch (Exception var24) {
                  var24.printStackTrace();
                  AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5447, var24.toString());
               } catch (Error var25) {
                  AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5447, var25.toString());
               }
            }

            String var38 = var1 + "_fm3d/" + var1 + ".fm3d";
            DataInputStream var39 = new DataInputStream(this.getClass().getResourceAsStream(var38));
            if (var39 == null) {
               return false;
            }

            try {
               var7 = var39.available();
               var4 = new byte[var7];
               var39.readFully(var4);
               var39.close();
               var16 = null;
               byte var35 = 0;
               var36 = (short)(var4[var35] & 255 | (var4[var35 + 1] & 255) << 8);
               var8 = var35 + 2;
            } catch (Exception var22) {
               var22.printStackTrace();
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5472, var22.toString());
            } catch (Error var23) {
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5472, var23.toString());
            }

            byte[] var17 = new byte[30];
            var17[0] = 47;

            try {
               for(int var19 = 0; var19 < var36; ++var19) {
                  int var18 = 1;
                  int var20 = var4[var8] & 255 | (var4[var8 + 1] & 255) << 8 | (var4[var8 + 2] & 255) << 16 | (var4[var8 + 3] & 255) << 24;
                  var8 += 4;
                  int var21 = var4[var8] & 255 | (var4[var8 + 1] & 255) << 8 | (var4[var8 + 2] & 255) << 16 | (var4[var8 + 3] & 255) << 24;

                  for(var8 += 4; var8 < var7; ++var8) {
                     if (var4[var8] != 10 && (var18 < 4 || var2 != 3)) {
                        var17[var18++] = var4[var8];
                     }

                     if (var4[var8] == 10) {
                        ++var8;
                        break;
                     }
                  }

                  var12 = new String(var17, 0, var18);
                  var6[var19].m_meshName = var12;
                  var12 = null;

                  for(var18 = 1; var8 < var7; ++var8) {
                     if (var4[var8] != 10 && (var18 < 4 || var2 != 3)) {
                        var17[var18++] = var4[var8];
                     }

                     if (var4[var8] == 10) {
                        ++var8;
                        break;
                     }
                  }

                  if (var2 != 3) {
                     var11 = new String(var17, 0, var18);
                     var6[var19].m_textureName = var11;
                     var11 = null;
                  }
               }

               Object var40 = null;
            } catch (Exception var30) {
               var30.printStackTrace();
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5548, var30.toString());
            } catch (Error var31) {
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5548, var31.toString());
            }
         }

         var13 = null;
         if (var2 == 3) {
            this.scene_LoadTrackTextures(var6);
         }
      } catch (Exception var32) {
         var32.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5558, var32.toString());
      } catch (Error var33) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5558, var33.toString());
      }

      Object var34 = null;
      var6 = null;
      var5 = null;
      var11 = null;
      System.gc();
      return true;
   }

   void scene_LoadTrackTextures(TheGame.MintMesh[] var1) {
      if (var1 != null) {
         int var2 = var1.length;
         this.trackBarrier1Count = 0;
         this.trackBarrier2Count = 0;
         this.trackBarrier3Count = 0;
         this.trackBarrier4Count = 0;
         this.numTrackItems = 0;

         int var3;
         for(var3 = 0; var3 < 20; ++var3) {
            this.trackBarrier1[var3] = null;
            this.trackBarrier2[var3] = null;
            this.trackBarrier3[var3] = null;
            this.trackBarrier4[var3] = null;
         }

         for(var3 = 0; var3 < this.trackMeshs.length; ++var3) {
            this.trackMeshs[var3] = null;
            this.trackIsMesh[var3] = false;
         }

         System.gc();
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            try {
               Appearance var4;
               if (!var1[var3].m_meshName.startsWith("/c1_") && !var1[var3].m_meshName.startsWith("/C1_")) {
                  if (!var1[var3].m_meshName.startsWith("/c2_") && !var1[var3].m_meshName.startsWith("/C2_")) {
                     if (!var1[var3].m_meshName.startsWith("/c3_") && !var1[var3].m_meshName.startsWith("/C3_")) {
                        if (!var1[var3].m_meshName.startsWith("/c4_") && !var1[var3].m_meshName.startsWith("/C4_")) {
                           if (var1[var3].m_meshName.startsWith("/sky")) {
                              this.skyDome = var1[var3].m_mesh;
                              var4 = this.skyDome.getAppearance(0);
                              var4.setCompositingMode(this.compositingMode_ZNONE);
                              var4.setPolygonMode(this.polygonMode_NoPersp);
                              var4.setTexture(0, (Texture2D)null);
                              if (this.g_trackPaths[this.g_race.m_trackPath].m_nWorld == 0) {
                                 this.skyDome.getVertexBuffer().setDefaultColor(12437180);
                              } else {
                                 this.skyDome.getVertexBuffer().setDefaultColor(11386309);
                              }
                           } else {
                              this.trackMeshs[this.numTrackItems] = var1[var3].m_mesh;
                              this.trackIsMesh[this.numTrackItems] = true;
                              var4 = var1[var3].m_mesh.getAppearance(0);
                              var4.setPolygonMode(this.polygonMode_Persp);
                           }
                        } else {
                           this.trackBarrier4[this.trackBarrier4Count] = var1[var3].m_mesh;
                           ++this.trackBarrier4Count;
                           var4 = var1[var3].m_mesh.getAppearance(0);
                           var4.setPolygonMode(this.polygonMode_Persp);
                        }
                     } else {
                        this.trackBarrier3[this.trackBarrier3Count] = var1[var3].m_mesh;
                        ++this.trackBarrier3Count;
                        var4 = var1[var3].m_mesh.getAppearance(0);
                        var4.setPolygonMode(this.polygonMode_Persp);
                     }
                  } else {
                     this.trackBarrier2[this.trackBarrier2Count] = var1[var3].m_mesh;
                     ++this.trackBarrier2Count;
                     var4 = var1[var3].m_mesh.getAppearance(0);
                     var4.setPolygonMode(this.polygonMode_Persp);
                  }
               } else {
                  this.trackBarrier1[this.trackBarrier1Count] = var1[var3].m_mesh;
                  ++this.trackBarrier1Count;
                  var4 = var1[var3].m_mesh.getAppearance(0);
                  var4.setPolygonMode(this.polygonMode_Persp);
               }
            } catch (Exception var5) {
               var5.printStackTrace();
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5675, var5.toString());
            } catch (Error var6) {
               AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5675, var6.toString());
            }

            ++this.numTrackItems;
         }

         var1 = null;
      }
   }

   boolean scene_BuildCar(TheGame.NFSMW_CarAppearance var1, boolean var2, boolean var3) {
      try {
         int var4 = var2 ? 2 : 1;
         if (var4 == 2) {
            if (!var1.m_modelName.startsWith("lod_")) {
               var1.m_modelName = "lod_" + var1.m_modelName;
            }
         } else if (var1.m_modelName.startsWith("lod_")) {
            var1.m_modelName = var1.m_modelName.substring(4);
         }

         while(true) {
            if (!this.scene_OpenModel(var1.m_modelName, var4, true)) {
               TheGame.MintMesh[] var5 = this.scene_FindMintMesh(var1.m_modelName);
               var1.m_carMesh = var5[0].m_mesh;
               var1.m_imgClean = this.scene_LoadTexImage(var5[0].m_textureName);
               if (var3) {
                  return false;
               }
               break;
            }
         }
      } catch (Exception var15) {
         var15.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5772, var15.toString());
      } catch (Error var16) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5772, var16.toString());
      }

      Appearance var8;
      VertexBuffer var18;
      Image2D var23;
      try {
         short[] var17 = new short[4];
         int var19 = this.binLoad(var1.m_pointsID);

         for(int var6 = 0; var6 < 11; ++var6) {
            short var7 = this.binGetShort(var19);
            switch(var7) {
            case 1:
               var17[3] = this.binGetShort(var19);
               var17[0] = this.binGetShort(var19);
               var17[1] = this.binGetShort(var19);
               var17[2] = this.binGetShort(var19);
               var1.m_carWheelPos[var6][0] = (float)var17[0] / 1024.0F;
               var1.m_carWheelPos[var6][1] = (float)var17[2] / 1024.0F;
               var1.m_carWheelPos[var6][2] = -((float)var17[1]) / 1024.0F;
               var1.m_carWheelPos[var6][3] = (float)var17[3];
               break;
            case 2:
               var17[3] = this.binGetShort(var19);
               var17[0] = this.binGetShort(var19);
               var17[1] = this.binGetShort(var19);
               var17[2] = this.binGetShort(var19);
               var1.m_carLightPos[var6 - 4][0] = (float)var17[0] / 1024.0F;
               var1.m_carLightPos[var6 - 4][1] = (float)var17[2] / 1024.0F;
               var1.m_carLightPos[var6 - 4][2] = -((float)var17[1]) / 1024.0F;
               break;
            case 3:
               var17[0] = this.binGetShort(var19);
               var17[1] = this.binGetShort(var19);
               var17[2] = this.binGetShort(var19);
               var1.m_carSpoilerPos[0] = (float)var17[0] / 1024.0F;
               var1.m_carSpoilerPos[1] = (float)var17[2] / 1024.0F;
               var1.m_carSpoilerPos[2] = -((float)var17[1]) / 1024.0F;
               break;
            case 4:
               var17[0] = this.binGetShort(var19);
               var17[1] = this.binGetShort(var19);
               var17[2] = this.binGetShort(var19);
               var1.m_carStreakPos[var6 - 9][0] = (float)var17[0] / 1024.0F;
               var1.m_carStreakPos[var6 - 9][1] = (float)var17[2] / 1024.0F;
               var1.m_carStreakPos[var6 - 9][2] = -((float)var17[1]) / 1024.0F;
            }
         }

         var18 = null;
         this.binFree(var1.m_pointsID);
         Image var22;
         if (this.headlights == null) {
            var22 = this.scene_LoadTexImage("/headlights.png");
            var23 = new Image2D(100, var22);
            var22 = null;
            var8 = new Appearance();
            var8.setCompositingMode(this.compositingMode_ZWRITE_AlphaAdd);
            this.headlights = new Sprite3D(true, var23, var8);
            var23 = null;
            var8 = null;
         }

         if (this.brakelights == null) {
            var22 = this.scene_LoadTexImage("/brakelights.png");
            var23 = new Image2D(100, var22);
            var22 = null;
            var8 = new Appearance();
            var8.setCompositingMode(this.compositingMode_ZWRITE_AlphaAdd);
            this.brakelights = new Sprite3D(true, var23, var8);
            var23 = null;
            var8 = null;
         }

         if (this.policelights == null) {
            var22 = this.scene_LoadTexImage("/policelight.png");
            var23 = new Image2D(100, var22);
            var8 = new Appearance();
            var8.setCompositingMode(this.compositingMode_ZWRITE_AlphaAdd);
            this.policelights = new Sprite3D(true, var23, var8);
            var23 = null;
            var8 = null;
         }

         if (this.sparks_sprite == null) {
            var22 = this.scene_LoadTexImage("/sparks.png");
            var23 = new Image2D(100, var22);
            var22 = null;
            var8 = new Appearance();
            var8.setCompositingMode(this.compositingMode_ZREAD_Alpha);
            this.sparks_sprite = new Sprite3D(true, var23, var8);
            var23 = null;
            var8 = null;
         }

         if (this.fire_sprites[0] == null) {
            var22 = this.scene_LoadTexImage("/fire_01.png");
            var23 = new Image2D(100, var22);
            var22 = null;
            var8 = new Appearance();
            var8.setCompositingMode(this.compositingMode_ZWRITE_AlphaAdd);
            this.fire_sprites[0] = new Sprite3D(true, var23, var8);
            var22 = this.scene_LoadTexImage("/fire_02.png");
            var23 = new Image2D(100, var22);
            var22 = null;
            var8 = new Appearance();
            var8.setCompositingMode(this.compositingMode_ZWRITE_AlphaAdd);
            this.fire_sprites[1] = new Sprite3D(true, var23, var8);
            var22 = this.scene_LoadTexImage("/fire_03.png");
            var23 = new Image2D(100, var22);
            var22 = null;
            var8 = new Appearance();
            var8.setCompositingMode(this.compositingMode_ZWRITE_AlphaAdd);
            this.fire_sprites[2] = new Sprite3D(true, var23, var8);
            var23 = null;
            var8 = null;
         }
      } catch (Exception var13) {
         var13.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5926, var13.toString());
      } catch (Error var14) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 5926, var14.toString());
      }

      try {
         if (this.wheelVertes == null) {
            short var20 = (short)((int)var1.m_carWheelPos[0][3]);
            short var26 = (short)(-var20);
            VertexArray var28 = new VertexArray(4, 3, 2);
            VertexArray var31 = new VertexArray(4, 2, 2);
            short[] var9 = new short[]{0, var26, var26, 0, var26, var20, 0, var20, var26, 0, var20, var20};
            short[] var10 = new short[]{0, 0, 0, 1, 1, 0, 1, 1};
            var28.set(0, 4, var9);
            var31.set(0, 4, var10);
            this.wheelVertes = new VertexBuffer();
            this.wheelVertes.setDefaultColor(-1);
            this.wheelVertes.setPositions(var28, 0.0078125F, (float[])null);
            this.wheelVertes.setTexCoords(0, var31, 1.0F, (float[])null);
         }

         var18 = this.wheelVertes;
         int[] var21 = new int[]{0, 1, 2, 3};
         int[] var29 = new int[]{4};
         TriangleStripArray var30 = new TriangleStripArray(var21, var29);
         var8 = null;
         String var24 = this.g_carParts[7][var1.m_parts[7]].m_partName;
         Texture2D var32 = this.scene_FindTexture(var24);
         Image var25;
         if (var32 == null) {
            var25 = this.scene_LoadTexImage(var24);
            var32 = this.scene_CreateTexture(var24, var25, true, true);
            var25 = null;
         }

         Appearance var27 = new Appearance();
         var27.setTexture(0, var32);
         var27.setPolygonMode(this.polygonMode_NoPersp);
         var27.setCompositingMode(this.compositingMode_ZREAD_Alpha);
         var1.m_wheelMesh = new Mesh(var18, var30, var27);
         var18 = null;
         var23 = null;
         var25 = null;
         if (!var2) {
            this.scene_createStreaks(var1);
         }

         if (!var2) {
            this.scene_createReflectionMesh(var1);
         }
      } catch (Exception var11) {
         var11.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 6015, var11.toString());
      } catch (Error var12) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 6015, var12.toString());
      }

      return false;
   }

   boolean scene_createStreaks(TheGame.NFSMW_CarAppearance var1) {
      VertexArray var2 = new VertexArray(3, 3, 2);
      VertexArray var3 = new VertexArray(3, 2, 2);
      short[] var4 = new short[]{-100, 0, 0, 0, 0, 3000, 100, 0, 0};
      short[] var5 = new short[]{0, 0, 1, 1, 2, 0};
      short[] var10000 = new short[]{255, 255, 0, 0, 255, 255, 0, 0, 255, 255, 0, 0};
      var2.set(0, 3, var4);
      var3.set(0, 3, var5);
      var1.m_streakVertices = new VertexBuffer();
      var1.m_streakVertices.setDefaultColor(-65536);
      var1.m_streakVertices.setPositions(var2, 0.001F, (float[])null);
      var1.m_streakVertices.setTexCoords(0, var3, 0.5F, (float[])null);
      int[] var7 = new int[]{0, 1, 2};
      int[] var8 = new int[]{3};
      var1.m_streakIndexBuffer = new TriangleStripArray(var7, var8);
      Texture2D var9 = null;
      String var10 = "/speed_effect.png";
      var9 = this.scene_FindTexture(var10);
      if (var9 == null) {
         Image var11 = this.scene_LoadTexImage(var10);
         var9 = this.scene_CreateTexture(var10, var11, true, true);
         var11 = null;
      }

      if (var9 != null) {
         var1.m_streakTexture = var9;
         var1.m_streakTexture.setBlending(227);
      }

      var1.m_streakAppearance = new Appearance();
      var1.m_streakAppearance.setTexture(0, var9);
      var1.m_streakAppearance.setPolygonMode(this.polygonMode_NoPersp);
      var1.m_streakAppearance.setCompositingMode(this.compositingMode_ZREAD_Alpha);
      var9 = null;
      String var13 = "/speedNitro_effect.png";
      var9 = this.scene_FindTexture(var13);
      if (var9 == null) {
         Image var12 = this.scene_LoadTexImage(var13);
         var9 = this.scene_CreateTexture(var13, var12, true, true);
         var12 = null;
      }

      if (var9 != null) {
         var1.m_streakNitroTexture = var9;
         var1.m_streakNitroTexture.setBlending(227);
      }

      var1.m_streakNitroAppearance = new Appearance();
      var1.m_streakNitroAppearance.setTexture(0, var9);
      var1.m_streakNitroAppearance.setPolygonMode(this.polygonMode_NoPersp);
      var1.m_streakNitroAppearance.setCompositingMode(this.compositingMode_ZREAD_Alpha);
      var2 = null;
      var3 = null;
      return true;
   }

   boolean scene_createReflectionMesh(TheGame.NFSMW_CarAppearance var1) {
      Object var2 = null;
      var1.m_reflectionMesh = null;

      while(this.scene_OpenModel(var1.m_modelName + "_reflection", 1, true)) {
         ;
      }

      TheGame.MintMesh[] var3 = this.scene_FindMintMesh(var1.m_modelName + "_reflection");
      var1.m_reflectionMesh = var3[0].m_mesh;
      Appearance var4 = var1.m_reflectionMesh.getAppearance(0);
      var4.setPolygonMode(this.polygonMode_NoPersp);
      var4.setCompositingMode(this.compositingMode_ZNONE_AlphaAdd);

      try {
         String var5 = "/reflection_png/reflection.png";
         Image var6 = Image.createImage(var5);
         Image2D var7 = new Image2D(100, var6);
         Texture2D var8 = new Texture2D(var7);
         var8.setFiltering(208, 210);
         var8.setWrapping(241, 241);
         var8.setBlending(228);
         var4.setTexture(0, var8);
      } catch (Exception var9) {
         var9.printStackTrace();
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 6133, var9.toString());
      } catch (Error var10) {
         AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 6133, var10.toString());
      }

      return true;
   }

   boolean scene_BuildTrack(String var1) {
      return this.scene_OpenModel(var1, 3, false);
   }

   void scene_CreateFlashEffect() {
      if (this.flash_effect_sprite == null) {
         Image var1;
         if (this.g_race.m_type == 3) {
            var1 = this.scene_LoadTexImage("/speed_effect.png");
         } else {
            var1 = this.scene_LoadTexImage("/badge_game.png");
         }

         Image2D var2 = new Image2D(100, var1);
         var1 = null;
         Appearance var3 = new Appearance();
         var3.setCompositingMode(this.compositingMode_ZNONE_AlphaAdd);
         this.flash_effect_sprite = new Sprite3D(true, var2, var3);
         var2 = null;
         var3 = null;
      }

   }

   public TheGame(AppMain var1) {
      this.g_docs = this.g_docsEnglish;
      this.g_HudImgsEnglish = new int[]{108, 116, 120, 104, 96, 100, 112};
      this.g_HudImgsChineseSimplified = new int[]{412, 420, 424, 408, 400, 404, 416};
      this.g_HudImgs = this.g_HudImgsEnglish;
      this.g_WarningImgs = new int[]{610, 628};
      this.g_MenuImagesEnglish = new int[]{604, 634, 640};
      this.g_MenuImagesSChinese = new int[]{622, 936, 942};
      this.g_MenuImages = this.g_MenuImagesEnglish;
      this.g_nLanguage = 0;
      this.g_Text = new String[257];
      this.g_statNamesEnglish = new String[]{"SP", "HD", "AC", "RP"};
      this.g_statNamesSChinese = new String[]{"速度", "操纵", "加速", "名声"};
      this.stats_stock = new int[]{0, 0, 0, 0};
      this.stats_paint = new int[]{0, 0, 0, 1};
      this.stats_windTint = new int[]{0, 0, 0, 1};
      this.stats_vinyl = new int[]{0, 0, 0, 2};
      this.stats_spoiler = new int[]{0, 1, 0, 2};
      this.stats_rims = new int[]{0, 0, 0, 2};
      this.stats_bumpers = new int[]{0, 0, 0, 1};
      this.stats_engine1 = new int[]{1, 0, 0, 0};
      this.stats_turbo1 = new int[]{0, 0, 1, 0};
      this.stats_nitrous1 = new int[]{0, 0, 0, 0};
      this.stats_trans1 = new int[]{0, 0, 1, 0};
      this.stats_tires1 = new int[]{0, 1, 0, 0};
      this.stats_brakes1 = new int[]{0, 1, 0, 0};
      this.stats_fuel1 = new int[]{1, 0, 0, 0};
      this.stats_engine2 = new int[]{2, 0, 0, 0};
      this.stats_turbo2 = new int[]{0, 0, 2, 0};
      this.stats_nitrous2 = new int[]{0, 0, 0, 0};
      this.stats_trans2 = new int[]{0, 0, 2, 0};
      this.stats_tires2 = new int[]{0, 2, 0, 0};
      this.stats_brakes2 = new int[]{0, 2, 0, 0};
      this.stats_fuel2 = new int[]{2, 0, 0, 0};
      this.stats_engine3 = new int[]{3, 0, 0, 0};
      this.stats_turbo3 = new int[]{0, 0, 3, 0};
      this.stats_nitrous3 = new int[]{0, 0, 0, 0};
      this.stats_trans3 = new int[]{0, 0, 3, 0};
      this.stats_tires3 = new int[]{0, 3, 0, 0};
      this.stats_brakes3 = new int[]{0, 3, 0, 0};
      this.stats_fuel3 = new int[]{3, 0, 0, 0};
      this.stats_max = new int[]{10, 11, 10, 13};
      this.g_carParts = new TheGame.NFSMW_CarPart[][]{{new TheGame.NFSMW_CarPart(4096, 0, 142, "", -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(4096, 1, 143, "paintlayout_01", 0, 750, this.stats_paint), new TheGame.NFSMW_CarPart(4096, 2, 144, "paintlayout_02", 0, 750, this.stats_paint), new TheGame.NFSMW_CarPart(4096, 3, 145, "paintlayout_03", 0, 750, this.stats_paint), new TheGame.NFSMW_CarPart(4096, 4, 146, "paintlayout_04", 0, 750, this.stats_paint), new TheGame.NFSMW_CarPart(4096, 5, 147, "paintlayout_05", 0, 750, this.stats_paint), new TheGame.NFSMW_CarPart(4096, 6, 148, "paintlayout_06", 0, 750, this.stats_paint), new TheGame.NFSMW_CarPart(4096, 7, 149, "paintlayout_07", 0, 750, this.stats_paint)}, {new TheGame.NFSMW_CarPart(8192, 0, 142, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(8192, 1, 150, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 2, 151, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 3, 152, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 4, 153, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 5, 154, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 6, 155, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 7, 156, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 8, 157, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 9, 158, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 10, 159, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 11, 160, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 12, 161, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(8192, 13, 162, 0, 200, this.stats_paint)}, {new TheGame.NFSMW_CarPart(16384, 0, 142, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(16384, 1, 150, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 2, 151, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 3, 152, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 4, 153, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 5, 154, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 6, 155, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 7, 156, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 8, 157, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 9, 158, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 10, 159, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 11, 160, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 12, 161, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(16384, 13, 162, 0, 200, this.stats_paint)}, {new TheGame.NFSMW_CarPart(32768, 0, 142, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(32768, 1, 150, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 2, 151, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 3, 152, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 4, 153, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 5, 154, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 6, 155, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 7, 156, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 8, 157, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 9, 158, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 10, 159, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 11, 160, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 12, 161, 0, 200, this.stats_paint), new TheGame.NFSMW_CarPart(32768, 13, 162, 0, 200, this.stats_paint)}, {new TheGame.NFSMW_CarPart(65536, 0, 142, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(65536, 1, 150, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 2, 151, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 3, 152, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 4, 153, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 5, 154, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 6, 155, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 7, 156, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 8, 157, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 9, 158, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 10, 159, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 11, 160, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 12, 161, 0, 1000, this.stats_paint), new TheGame.NFSMW_CarPart(65536, 13, 162, 0, 1000, this.stats_paint)}, {new TheGame.NFSMW_CarPart(131072, 0, 142, -1, -1, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(131072, 1, 163, 66, 12, 0, 2000, this.stats_vinyl), new TheGame.NFSMW_CarPart(131072, 2, 164, 72, 8, 0, 2000, this.stats_vinyl), new TheGame.NFSMW_CarPart(131072, 3, 165, 84, 40, 0, 2000, this.stats_vinyl), new TheGame.NFSMW_CarPart(131072, 4, 166, 78, 36, 0, 2000, this.stats_vinyl), new TheGame.NFSMW_CarPart(131072, 5, 167, 90, 50, 0, 2000, this.stats_vinyl), new TheGame.NFSMW_CarPart(131072, 6, 168, 18, 46, 0, 2000, this.stats_vinyl), new TheGame.NFSMW_CarPart(131072, 7, 169, 24, 60, 0, 2000, this.stats_vinyl), new TheGame.NFSMW_CarPart(131072, 8, 170, 30, 56, 0, 2000, this.stats_vinyl)}, {new TheGame.NFSMW_CarPart(262144, 0, 141, "", -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(262144, 1, 171, "wing_01", 0, 2000, this.stats_spoiler), new TheGame.NFSMW_CarPart(262144, 2, 172, "wing_02", 0, 2000, this.stats_spoiler), new TheGame.NFSMW_CarPart(262144, 3, 173, "wing_03", 0, 2000, this.stats_spoiler), new TheGame.NFSMW_CarPart(262144, 4, 174, "wing_04", 0, 2000, this.stats_spoiler), new TheGame.NFSMW_CarPart(262144, 5, 175, "wing_05", 0, 2000, this.stats_spoiler), new TheGame.NFSMW_CarPart(262144, 6, 176, "wing_06", 0, 2000, this.stats_spoiler), new TheGame.NFSMW_CarPart(262144, 7, 177, "wing_07", 0, 2000, this.stats_spoiler), new TheGame.NFSMW_CarPart(262144, 8, 178, "wing_08", 0, 2000, this.stats_spoiler)}, {new TheGame.NFSMW_CarPart(524288, 0, 142, "/rim_01.png", -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(524288, 1, 171, "/rim_02.png", 0, 2500, this.stats_rims), new TheGame.NFSMW_CarPart(524288, 2, 172, "/rim_03.png", 0, 2500, this.stats_rims), new TheGame.NFSMW_CarPart(524288, 3, 173, "/rim_04.png", 0, 2500, this.stats_rims), new TheGame.NFSMW_CarPart(524288, 4, 174, "/rim_05.png", 0, 2500, this.stats_rims), new TheGame.NFSMW_CarPart(524288, 5, 175, "/rim_06.png", 0, 2500, this.stats_rims), new TheGame.NFSMW_CarPart(524288, 6, 176, "/rim_07.png", 0, 2500, this.stats_rims), new TheGame.NFSMW_CarPart(524288, 7, 177, "/rim_08.png", 0, 2500, this.stats_rims)}, {new TheGame.NFSMW_CarPart(1048576, 0, 142, -1, -1, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(1048576, 1, 171, 460, 446, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(1048576, 2, 172, 456, 442, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(1048576, 3, 173, 452, 438, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(1048576, 4, 174, 568, 554, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(1048576, 5, 175, 564, 550, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(1048576, 6, 176, 560, 546, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(1048576, 7, 177, 482, 540, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(1048576, 8, 178, 478, 536, 0, 3000, this.stats_bumpers)}, {new TheGame.NFSMW_CarPart(2097152, 0, 142, -1, -1, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(2097152, 1, 171, 474, 526, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(2097152, 2, 172, 470, 522, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(2097152, 3, 173, 466, 518, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(2097152, 4, 174, 504, 434, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(2097152, 5, 175, 500, 430, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(2097152, 6, 176, 496, 514, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(2097152, 7, 177, 492, 510, 0, 3000, this.stats_bumpers), new TheGame.NFSMW_CarPart(2097152, 8, 178, 488, 532, 0, 3000, this.stats_bumpers)}, {new TheGame.NFSMW_CarPart(4194304, 0, 141, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(4194304, 1, 179, 0, 2500, this.stats_engine1), new TheGame.NFSMW_CarPart(4194304, 2, 180, 0, 5000, this.stats_engine2), new TheGame.NFSMW_CarPart(4194304, 3, 181, 0, 9500, this.stats_engine3)}, {new TheGame.NFSMW_CarPart(8388608, 0, 141, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(8388608, 1, 179, 0, 2000, this.stats_turbo1), new TheGame.NFSMW_CarPart(8388608, 2, 180, 0, 4000, this.stats_turbo2), new TheGame.NFSMW_CarPart(8388608, 3, 181, 0, 8000, this.stats_turbo3)}, {new TheGame.NFSMW_CarPart(16777216, 0, 141, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(16777216, 1, 179, 0, 2500, this.stats_nitrous1), new TheGame.NFSMW_CarPart(16777216, 2, 180, 0, 5000, this.stats_nitrous2), new TheGame.NFSMW_CarPart(16777216, 3, 181, 0, 9500, this.stats_nitrous3)}, {new TheGame.NFSMW_CarPart(33554432, 0, 141, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(33554432, 1, 179, 0, 2500, this.stats_trans1), new TheGame.NFSMW_CarPart(33554432, 2, 180, 0, 5000, this.stats_trans2), new TheGame.NFSMW_CarPart(33554432, 3, 181, 0, 9500, this.stats_trans3)}, {new TheGame.NFSMW_CarPart(67108864, 0, 141, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(67108864, 1, 179, 0, 2000, this.stats_tires1), new TheGame.NFSMW_CarPart(67108864, 2, 180, 0, 4000, this.stats_tires2), new TheGame.NFSMW_CarPart(67108864, 3, 181, 0, 8000, this.stats_tires3)}, {new TheGame.NFSMW_CarPart(134217728, 0, 141, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(134217728, 1, 179, 0, 1000, this.stats_brakes1), new TheGame.NFSMW_CarPart(134217728, 2, 180, 0, 2000, this.stats_brakes2), new TheGame.NFSMW_CarPart(134217728, 3, 181, 0, 5000, this.stats_brakes3)}, {new TheGame.NFSMW_CarPart(268435456, 0, 141, -1, 0, this.stats_stock), new TheGame.NFSMW_CarPart(268435456, 1, 179, -1, 2000, this.stats_fuel1), new TheGame.NFSMW_CarPart(268435456, 2, 180, -1, 4000, this.stats_fuel2), new TheGame.NFSMW_CarPart(268435456, 3, 181, -1, 8000, this.stats_fuel3)}};
      this.g_carImgHi = Image.createImage(128, 128);
      this.g_carImgLow = Image.createImage(64, 64);
      this.g_carImgTraffic = Image.createImage(64, 32);
      this.g_carGfxHi = null;
      this.g_carGfxLow = null;
      this.g_carGfxTraffic = null;
      this.g_carSetups = new byte[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {3, 0, 13, 9, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0}, {5, 7, 0, 3, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {4, 6, 3, 0, 0, 0, 3, 2, 8, 2, 0, 0, 0, 0, 0, 0, 0}, {2, 6, 0, 13, 2, 0, 7, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 4, 13, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, {0, 13, 0, 3, 5, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
      this.g_QRcarSetups = new byte[][]{{0, 9, 0, 0, 0, 6, 8, 1, 2, 6, 2, 2, 2, 1, 1, 1, 1}, {0, 13, 13, 9, 13, 0, 6, 7, 5, 0, 2, 2, 2, 1, 1, 1, 1}, {0, 1, 0, 3, 0, 0, 5, 6, 1, 5, 2, 2, 2, 2, 1, 1, 1}, {0, 6, 5, 0, 0, 4, 7, 4, 0, 0, 2, 2, 2, 2, 1, 1, 1}, {0, 10, 0, 13, 2, 0, 2, 5, 7, 4, 2, 2, 2, 2, 2, 1, 1}, {0, 4, 3, 0, 13, 0, 2, 5, 0, 0, 2, 2, 2, 2, 2, 2, 2}};
      this.g_cars = new TheGame.NFSMW_CarAppearance[]{new TheGame.NFSMW_CarAppearance(32, 0, 72, "car_mitsu_eclipse", 1041, new int[]{0, 0, 0, 0}, false), new TheGame.NFSMW_CarAppearance(32, 1, 73, "car_corvette_c6r", 1047, new int[]{1, 1, 0, 0}, false), new TheGame.NFSMW_CarAppearance(32, 2, 74, "car_toyota_supra", 1037, new int[]{2, 2, 1, 0}, false), new TheGame.NFSMW_CarAppearance(32, 3, 75, "car_lancer_evoviii", 1045, new int[]{2, 3, 2, 0}, false), new TheGame.NFSMW_CarAppearance(32, 4, 76, "car_mazda_rx8", 1043, new int[]{3, 3, 4, 0}, false), new TheGame.NFSMW_CarAppearance(32, 5, 77, "car_bmw_3series", 1049, new int[]{4, 4, 4, 0}, false), null, new TheGame.NFSMW_CarAppearance(32, 7, 78, "car_police", 1039, new int[]{0, 0, 0, 0}, false), new TheGame.NFSMW_CarAppearance(32, 8, 79, "traffic_hatch", 1019, new int[]{0, 0, 0, 0}, true), new TheGame.NFSMW_CarAppearance(32, 9, 79, "traffic_4x4", 1021, new int[]{0, 0, 0, 0}, true), new TheGame.NFSMW_CarAppearance(32, 10, 79, "traffic_van", 1017, new int[]{0, 0, 0, 0}, true), new TheGame.NFSMW_CarAppearance(32, 11, 74, "car_toyota_supra", 1037, new int[]{2, 2, 1, 0}, false)};
      this.g_worlds = new String[]{"track_01", "track_02"};
      this.g_worldsPretty = new int[]{81, 80};
      this.g_trackPaths = new TheGame.NFSMW_TrackPath[]{new TheGame.NFSMW_TrackPath(0, 0, 1083, 1063, -1, 9), new TheGame.NFSMW_TrackPath(0, 1, 1081, 1061, -1, 3), null, new TheGame.NFSMW_TrackPath(0, 3, 1095, 1063, 1077, 255), new TheGame.NFSMW_TrackPath(0, 3, 1091, 1061, 1075, 255), new TheGame.NFSMW_TrackPath(0, 3, 1087, 1061, 1073, 255), new TheGame.NFSMW_TrackPath(1, 0, 1126, 1102, -1, 59), new TheGame.NFSMW_TrackPath(1, 1, 1124, 1100, -1, 39), new TheGame.NFSMW_TrackPath(1, 2, 1122, 1098, -1, 106), new TheGame.NFSMW_TrackPath(1, 3, 1138, 1100, 1118, 255), new TheGame.NFSMW_TrackPath(1, 3, 1134, 1102, 1116, 255), new TheGame.NFSMW_TrackPath(1, 3, 1130, 1102, 1114, 255), new TheGame.NFSMW_TrackPath(0, 3, 1081, 1061, 1079, 255), new TheGame.NFSMW_TrackPath(1, 3, 1126, 1102, 1120, 255), null};
      this.g_tracks = new TheGame.NFSMW_Track[]{new TheGame.NFSMW_Track(136, 0, 82, 586, 6, "1.0"), new TheGame.NFSMW_Track(136, 1, 83, 592, 7, "1.0"), new TheGame.NFSMW_Track(136, 2, 84, 598, 8, "0.8"), new TheGame.NFSMW_Track(136, 3, 85, 574, 0, "1.2"), new TheGame.NFSMW_Track(136, 4, 86, 580, 1, "1.7")};
      this.g_missionTypeNames = new int[]{117, 118, 119, 120, 121, 122};
      this.raceSetups = new int[][]{{1, 3, 2, 1}, {2, 3, 2, 1}, {3, 3, 2, 1}, {4, 3, 2, 1}, {1, 2, 1, 1}, {2, 3, 1, 1}, {3, 3, 1, 1}, {1, 1, 2, 1}, {2, 1, 2, 1}, {3, 1, 2, 1}, {5, 3, 2, 1}, {1, 2, 2, 1}, {2, 2, 2, 1}, {3, 2, 2, 1}};
      this.scriptScenes = new int[][]{{204, 205, 206}, {207, 208, 209}, {-1, -1, 210}, {211, 212, 213}, {-1, -1, 214}, {215, 216, 217}, {-1, -1, 218}, {219, 220, 221}, {-1, -1, 222}, {223, 224, 225}, {-1, -1, 226}, {227, -1, -1}, {228, -1, -1}, {229, -1, -1}, {230, -1, -1}, {231, -1, -1}, {232, -1, -1}, {233, -1, -1}, {234, -1, -1}, {235, -1, -1}, {236, -1, -1}, {237, -1, -1}, {238, -1, -1}, {239, -1, -1}, {240, -1, -1}, {241, -1, -1}, {242, -1, -1}, {243, -1, -1}, {244, -1, -1}, {245, -1, -1}, {246, -1, -1}, {247, -1, -1}, {248, -1, -1}, {249, -1, -1}, {250, -1, -1}, {251, -1, -1}, {252, -1, -1}, {253, -1, -1}, {254, -1, -1}, {255, -1, -1}, {256, -1, -1}};
      this.g_missions = new TheGame.NFSMW_Mission[]{new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 70, -1, 0, 1, 2, 0, 0, 0, 0, 0, 60, 0), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 150, 0, 0, 6, 5, 1, 0, 30, 0, 0, 60, 1), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 350, 1, 1, 3, 0, 2, 0, 40, 0, 0, 50, 3), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 550, 2, 0, 7, 3, 2, 0, 60, 0, 0, 50, 5), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 800, 3, 0, 8, 5, 3, 0, 80, 0, 0, 40, 7), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 1100, 4, 0, 1, 5, 3, 0, 100, 0, 0, 40, 9), new TheGame.NFSMW_Mission(72, g_missionIncID++, 117, 0, -1, 0, 0, 2, 0, 1000, 15, 0, 0, 60, 11), new TheGame.NFSMW_Mission(72, g_missionIncID++, 117, 0, -1, 0, 7, 3, 0, 2000, 20, 0, 0, 60, 12), new TheGame.NFSMW_Mission(72, g_missionIncID++, 117, 0, 0, 0, 6, 3, 1, 3000, 25, 0, 0, 50, 13), new TheGame.NFSMW_Mission(72, g_missionIncID++, 117, 0, 0, 0, 1, 4, 2, 3500, 30, 0, 0, 50, 14), new TheGame.NFSMW_Mission(72, g_missionIncID++, 117, 0, 3, 0, 8, 4, 3, 4000, 35, 0, 0, 40, 15), new TheGame.NFSMW_Mission(72, g_missionIncID++, 117, 0, 3, 0, 7, 5, 10, 4500, 40, 0, 0, 40, 16), new TheGame.NFSMW_Mission(72, g_missionIncID++, 118, 0, -1, 1, 9, 0, 0, 1000, 15, 0, 0, 60, 17), new TheGame.NFSMW_Mission(72, g_missionIncID++, 118, 0, -1, 1, 3, 0, 0, 2000, 20, 0, 0, 60, 18), new TheGame.NFSMW_Mission(72, g_missionIncID++, 118, 0, 1, 1, 10, 0, 1, 3000, 25, 0, 0, 50, 19), new TheGame.NFSMW_Mission(72, g_missionIncID++, 118, 0, 1, 1, 4, 0, 2, 3500, 30, 0, 0, 50, 20), new TheGame.NFSMW_Mission(72, g_missionIncID++, 118, 0, 2, 1, 11, 0, 3, 4000, 35, 0, 0, 40, 21), new TheGame.NFSMW_Mission(72, g_missionIncID++, 118, 0, 2, 1, 5, 0, 10, 4500, 40, 0, 0, 40, 22), new TheGame.NFSMW_Mission(72, g_missionIncID++, 119, 0, 0, 2, 0, 2, 0, 1000, 15, 0, 0, 60, 23), new TheGame.NFSMW_Mission(72, g_missionIncID++, 119, 0, 0, 2, 7, 2, 0, 2000, 20, 0, 0, 60, 24), new TheGame.NFSMW_Mission(72, g_missionIncID++, 119, 0, 1, 2, 1, 2, 1, 3000, 25, 0, 0, 50, 25), new TheGame.NFSMW_Mission(72, g_missionIncID++, 119, 0, 1, 2, 6, 2, 2, 3500, 30, 0, 0, 50, 26), new TheGame.NFSMW_Mission(72, g_missionIncID++, 119, 0, 3, 2, 8, 2, 3, 4000, 35, 0, 0, 40, 27), new TheGame.NFSMW_Mission(72, g_missionIncID++, 119, 0, 3, 2, 1, 2, 10, 4500, 40, 0, 0, 0, 28), new TheGame.NFSMW_Mission(72, g_missionIncID++, 120, 0, 0, 3, 12, 1, 7, 1000, 15, 90000, 210, 40, 29), new TheGame.NFSMW_Mission(72, g_missionIncID++, 120, 0, 0, 3, 13, 1, 7, 2000, 20, 90000, 190, 40, 30), new TheGame.NFSMW_Mission(72, g_missionIncID++, 120, 0, 1, 3, 12, 1, 8, 3000, 25, 70000, 210, 50, 31), new TheGame.NFSMW_Mission(72, g_missionIncID++, 120, 0, 1, 3, 13, 1, 8, 3500, 30, 70000, 200, 50, 32), new TheGame.NFSMW_Mission(72, g_missionIncID++, 120, 0, 2, 3, 12, 1, 9, 4000, 35, 70000, 215, 60, 33), new TheGame.NFSMW_Mission(72, g_missionIncID++, 120, 0, 2, 3, 13, 1, 9, 4500, 40, 70000, 185, 60, 34), new TheGame.NFSMW_Mission(72, g_missionIncID++, 121, 0, 2, 4, 1, 1, 4, 0, 15, 110000, 12, 0, 35), new TheGame.NFSMW_Mission(72, g_missionIncID++, 121, 0, 2, 4, 6, 1, 4, 0, 20, 110000, 12, 0, 36), new TheGame.NFSMW_Mission(72, g_missionIncID++, 121, 0, 3, 4, 0, 1, 5, 0, 25, 90000, 12, 0, 37), new TheGame.NFSMW_Mission(72, g_missionIncID++, 121, 0, 3, 4, 8, 1, 5, 0, 30, 90000, 15, 0, 38), new TheGame.NFSMW_Mission(72, g_missionIncID++, 121, 0, 4, 4, 1, 1, 6, 0, 35, 80000, 17, 0, 39), new TheGame.NFSMW_Mission(72, g_missionIncID++, 121, 0, 4, 4, 8, 1, 6, 0, 40, 80000, 15, 0, 40), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 0, 1, 4, 1, 2, 3, 1000, 15, 100000, 5, 0, 2), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 0, 1, 4, 6, 3, 3, 2000, 20, 100000, 8, 0, 4), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 0, 2, 4, 0, 3, 4, 3000, 25, 80000, 10, 0, 6), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 0, 2, 4, 8, 4, 4, 4000, 30, 80000, 17, 0, 8), new TheGame.NFSMW_Mission(72, g_missionIncID++, 122, 0, 3, 4, 1, 4, 5, 6000, 35, 70000, 20, 0, 10)};
      this.g_player = new TheGame.NFSMW_Profile();
      this.g_race = new TheGame.NFSMW_Race();
      this.menuTrackSelect = new TheGame.NFSMW_Menu(96, this.g_tracks, 3);
      this.menuCarSelect = new TheGame.NFSMW_Menu(95, this.g_cars, 1);
      this.menuQRCarSelect = new TheGame.NFSMW_Menu(95, this.g_cars, 1);
      this.menuCarPaintLayout = new TheGame.NFSMW_Menu(125, this.g_carParts[0], 1);
      this.menuCarPaintSelect1 = new TheGame.NFSMW_Menu(125, this.g_carParts[1], 1);
      this.menuCarPaintSelect2 = new TheGame.NFSMW_Menu(125, this.g_carParts[2], 1);
      this.menuCarPaintSelect3 = new TheGame.NFSMW_Menu(125, this.g_carParts[3], 1);
      this.menuCarWindTintSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[4], 1);
      this.menuCarVinylSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[5], 1);
      this.menuCarSpoilerSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[6], 2);
      this.menuCarRimsSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[7], 2);
      this.menuCarFrontBmprsSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[8], 2);
      this.menuCarRearBmprsSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[9], 2);
      this.menuCarPaintItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(97, 866, 2, 0, this.menuCarPaintLayout), new TheGame.NFSMW_MenuItem(98, 866, 2, 0, this.menuCarPaintSelect1), new TheGame.NFSMW_MenuItem(99, 866, 2, 0, this.menuCarPaintSelect2), new TheGame.NFSMW_MenuItem(100, 866, 2, 0, this.menuCarPaintSelect3), new TheGame.NFSMW_MenuItem(101, 846, 2, 0, this.menuCarWindTintSelect)};
      this.menuCarPaint = new TheGame.NFSMW_Menu(125, this.menuCarPaintItems, 1);
      this.menuCustomizeVisItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(102, 866, 2, 0, this.menuCarPaint), new TheGame.NFSMW_MenuItem(103, 850, 2, 0, this.menuCarVinylSelect), new TheGame.NFSMW_MenuItem(104, 854, 2, 0, this.menuCarSpoilerSelect), new TheGame.NFSMW_MenuItem(105, 858, 2, 0, this.menuCarRimsSelect), new TheGame.NFSMW_MenuItem(106, 870, 2, 0, this.menuCarFrontBmprsSelect), new TheGame.NFSMW_MenuItem(107, 862, 2, 0, this.menuCarRearBmprsSelect)};
      this.menuCustomizeVis = new TheGame.NFSMW_Menu(125, this.menuCustomizeVisItems, 1);
      this.menuCarEngineSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[10], 1);
      this.menuCarTurboSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[11], 1);
      this.menuCarNitrousSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[12], 1);
      this.menuCarTransmissionSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[13], 1);
      this.menuCarTireSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[14], 1);
      this.menuCarBrakesSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[15], 1);
      this.menuCarFuelSelect = new TheGame.NFSMW_Menu(125, this.g_carParts[16], 1);
      this.menuCustomizePerfItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(108, 836, 2, 0, this.menuCarEngineSelect), new TheGame.NFSMW_MenuItem(109, 816, 2, 0, this.menuCarTurboSelect), new TheGame.NFSMW_MenuItem(110, 828, 2, 0, this.menuCarNitrousSelect), new TheGame.NFSMW_MenuItem(111, 820, 2, 0, this.menuCarTransmissionSelect), new TheGame.NFSMW_MenuItem(112, 824, 2, 0, this.menuCarTireSelect), new TheGame.NFSMW_MenuItem(113, 840, 2, 0, this.menuCarBrakesSelect), new TheGame.NFSMW_MenuItem(114, 832, 2, 0, this.menuCarFuelSelect)};
      this.menuCustomizePerf = new TheGame.NFSMW_Menu(125, this.menuCustomizePerfItems, 1);
      this.menuCustomizeItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(115, 906, 2, 0, this.menuCustomizeVis), new TheGame.NFSMW_MenuItem(116, 910, 2, 0, this.menuCustomizePerf)};
      this.menuCustomize = new TheGame.NFSMW_Menu(125, this.menuCustomizeItems, 1);
      this.menuMissionCircuits = new TheGame.NFSMW_Menu(91, this.g_missions, 6, 6, 4);
      this.menuMissionCheckpoints = new TheGame.NFSMW_Menu(91, this.g_missions, 12, 6, 4);
      this.menuMissionKnockout = new TheGame.NFSMW_Menu(91, this.g_missions, 18, 6, 4);
      this.menuMissionSpeedCam = new TheGame.NFSMW_Menu(91, this.g_missions, 24, 6, 4);
      this.menuMissionOutrun = new TheGame.NFSMW_Menu(91, this.g_missions, 30, 6, 4);
      this.menuMissionChallenge = new TheGame.NFSMW_Menu(91, this.g_missions, 0, 6, 4);
      this.menuMissionTypeItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(117, 802, 2, 0, this.menuMissionCircuits), new TheGame.NFSMW_MenuItem(118, 806, 2, 0, this.menuMissionCheckpoints), new TheGame.NFSMW_MenuItem(119, 798, 2, 0, this.menuMissionKnockout), new TheGame.NFSMW_MenuItem(120, 810, 2, 0, this.menuMissionSpeedCam), new TheGame.NFSMW_MenuItem(121, 794, 2, 0, this.menuMissionOutrun), new TheGame.NFSMW_MenuItem(122, 750, 2, 0, this.menuMissionChallenge)};
      this.menuMissionTypes = new TheGame.NFSMW_Menu(91, this.menuMissionTypeItems, 1);
      this.menuCareerStatItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(123, 758, 0, 0, (TheGame.NFSMW_Menu)null), new TheGame.NFSMW_MenuItem(124, 758, 0, 1, (TheGame.NFSMW_Menu)null)};
      this.menuCareerStats = new TheGame.NFSMW_Menu(88, this.menuCareerStatItems, 6);
      this.menuCareerItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(91, 750, 2, 0, this.menuMissionTypes), new TheGame.NFSMW_MenuItem(125, 754, 2, 0, this.menuCustomize), new TheGame.NFSMW_MenuItem(126, 746, 2, 0, this.menuCarSelect), new TheGame.NFSMW_MenuItem(132, 758, 2, 0, this.menuCareerStats)};
      this.menuCareer = new TheGame.NFSMW_Menu(87, this.menuCareerItems, 1);
      this.menuProfileItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(127, 892, 4, 0, (TheGame.NFSMW_Menu)null), new TheGame.NFSMW_MenuItem(128, 888, 4, 1, (TheGame.NFSMW_Menu)null), new TheGame.NFSMW_MenuItem(129, 884, 4, 2, (TheGame.NFSMW_Menu)null)};
      this.menuProfile = new TheGame.NFSMW_Menu(93, this.menuProfileItems, 0);
      this.menuHelpItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(130, 896, 256, 0, (TheGame.NFSMW_Menu)null), new TheGame.NFSMW_MenuItem(131, 750, 256, 1, (TheGame.NFSMW_Menu)null), new TheGame.NFSMW_MenuItem(133, 746, 256, 2, (TheGame.NFSMW_Menu)null)};
      this.menuHelp = new TheGame.NFSMW_Menu(137, this.menuHelpItems, 0);
      this.menuOptionsItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(134, 876, 1024, 0, (TheGame.NFSMW_Menu)null), new TheGame.NFSMW_MenuItem(135, 900, 2, 0, this.menuProfile), new TheGame.NFSMW_MenuItem(136, 900, 4, 0, (TheGame.NFSMW_Menu)null), new TheGame.NFSMW_MenuItem(138, 758, 2048, 0, (TheGame.NFSMW_Menu)null)};
      this.menuOptions = new TheGame.NFSMW_Menu(92, this.menuOptionsItems, 0);
      this.menuRootItems = new TheGame.NFSMW_MenuItem[]{new TheGame.NFSMW_MenuItem(94, 650, 2, 0, this.menuQRCarSelect), new TheGame.NFSMW_MenuItem(87, 662, 2, 0, this.menuCareer), new TheGame.NFSMW_MenuItem(92, 654, 2, 0, this.menuOptions), new TheGame.NFSMW_MenuItem(137, 896, 2, 0, this.menuHelp), new TheGame.NFSMW_MenuItem(139, 880, 512, 1, (TheGame.NFSMW_Menu)null), new TheGame.NFSMW_MenuItem(140, 658, 1, 0, (TheGame.NFSMW_Menu)null)};
      this.menuRoot = new TheGame.NFSMW_Menu(90, this.menuRootItems, 0);
      this.menu_tmpCarStatData = new int[4];
      this.menu_Group = new Group();
      this.menu_IsPaused = false;
      this.menu_SplashPause = false;
      this.scene_timing = new long[50];
      this.scene_timingcolor = new int[50];
      this.trackPVSFrame1 = 0;
      this.trackPVSFrame2 = 0;
      this.trackPVSFrame3 = 0;
      this.trackColTriangle = 0;
      this.trackPVSSegment = 0;
      this.cameraScopeMask = 0;
      this.numTrackItems = 0;
      this.trackMeshs = new Mesh[400];
      this.trackIsMesh = new boolean[400];
      this.trackIsRendered = new boolean[400];
      this.trackBarrier1 = new Mesh[20];
      this.trackBarrier1Count = 0;
      this.trackBarrier2 = new Mesh[20];
      this.trackBarrier2Count = 0;
      this.trackBarrier3 = new Mesh[20];
      this.trackBarrier3Count = 0;
      this.trackBarrier4 = new Mesh[20];
      this.trackBarrier4Count = 0;
      this.scene_lastSnd = -1;
      this.track_splines = new short[3][][];
      this.numPlayers = 0;
      this.game_playerPosition = 1;
      this.players = new TheGame.Car[3];
      this.cops = new TheGame.Car[2];
      this.traffic = new TheGame.Car[1];
      this.g_roadsideCop = null;
      this.scene_cameraTrans = new Transform();
      this.headlights = null;
      this.brakelights = null;
      this.policelights = null;
      this.fire_sprites = new Sprite3D[4];
      this.sparks_sprite = null;
      this.flash_effect_sprite = null;
      this.wheelVertes = null;
      this.vTextureList = new Vector();
      this.vObjectList = new Vector();
      this.crc_table = new long[256];
      this.crc_table_computed = 0;
      this.system_Random = new Random(System.currentTimeMillis());
      this.system_sSoftkeyArray = new String[]{"OK", "SELECT", "NEXT", "OPTIONS", "EXIT", "BACK", "PAUSE", "DONE", "CANCEL"};
      this.system_nFirstSoftkey = 0;
      this.system_nSecondSoftkey = 0;
      this.asset_SoundArray = new TheGame.CSoundObject[10];
      this.Player = new TheGame.CSoundPlayerMIDP2[1];
      this.stockColors = new int[]{16777215, 1546498, 740097, 132761, 2134711, 8324610, 13769216, 13284864, 10184192, 10045442, 7275138, 10027638, 10921638, 328965};
      this.hudMPHImages = new short[]{320, 316, 312, 308, 304, 300, 296, 292, 288, 284};
      this.hudLGImages = new short[]{274, 270, 266, 262, 258, 254, 250, 246, 242, 238};
      this.hudSMImages = new short[]{366, 362, 358, 354, 350, 346, 342, 338, 334, 330};
      this.gearImgsOn = new int[]{162, 166, 190, 186, 182, 178, 174, 170};
      this.cheatPosition = 0;
      this.cheatCode = new int[]{49, 51, 52, 55, 57, 35, 42};
      this.trans = new Transform();
      this.shadowAppearance = new Appearance();
      this.shadowCompmode = new CompositingMode();
      this.g_nTimePlayerStuck = 0;
      this.slowCount = 0;
      this.g_nLastRoadSideCopIndex = -1;
      this.scene_workerTrans = new Transform();
      this.lastTacko = 32768;
      this.lastBustedness = 0;
      this.aiPathCollision = new TheGame.CCollisionResult();
      this.m_bPaintHourglass = false;
      this.m_bWait = false;
      this.m_bKeyUnhang = false;
      m_Midlet = var1;
      Assert(this.isDoubleBuffered(), "c:\\mobiledevelopment\\nfsmw_cn\\ndplatformjava\\thegamej2me.hpp", 172);
      this.system_bAssetLoading = false;
   }

   public synchronized void showNotify() {
      this.system_Show();
   }

   public synchronized void hideNotify() {
      try {
         this.system_Hide();
         this.asset_StopSound(-1);
      } catch (Exception var2) {
         ;
      } catch (Error var3) {
         ;
      }

   }

   synchronized void End() {
      try {
         this.system_End();
         this.system_nTimeEnd = System.currentTimeMillis();
      } catch (Exception var2) {
         ;
      }

   }

   public void pointerPressed(int var1, int var2) {
      try {
         if (var2 > 300) {
            if (var1 < 90) {
               this.scene_KeyPressed(-6, 0);
            } else if (var1 > 150) {
               this.scene_KeyPressed(-7, 0);
            }
         } else {
            this.scene_PointerPressed(var1, var2);
         }
      } catch (Exception var4) {
         ;
      }

   }

   public void pointerReleased(int var1, int var2) {
      try {
         this.scene_PointerReleased(var1, var2);
      } catch (Exception var4) {
         ;
      }

   }

   public synchronized void keyPressed(int var1) {
      var1 = this.system_TranslateKeyPressed(var1);
      int var2 = 0;

      try {
         if ((var1 & 1048560) != 589824 && var1 != -14) {
            var2 = this.getGameAction(var1);
         }

         this.scene_KeyPressed(var1, var2);
      } catch (Exception var4) {
         var4.printStackTrace();
      } catch (Error var5) {
         this.End();
         m_Midlet.notifyDestroyed();
         return;
      }

   }

   public synchronized void keyReleased(int var1) {
      int var2 = this.getGameAction(var1);
      var1 = this.system_TranslateKeyPressed(var1);
      this.scene_KeyReleased(var1, var2);
   }

   synchronized void Start() throws Exception {
      this.system_bAssetLoading = true;
      this.system_Start();
      this.system_bAssetLoading = false;
      this.system_nLoopCount = 0;
      this.system_nTimeStart = System.currentTimeMillis();
   }

   public synchronized void paint(Graphics var1) {
      try {
         if (this.system_bExit) {
            if (var1 == null) {
               ;
            }

            Display.getDisplay(m_Midlet).callSerially(this);
            return;
         }

         m_CurrentGraphics = var1;
         if (this.m_bPaintHourglass) {
            this.asset_DrawImage(2, this.system_nCanvasWidth - 16 >> 1, this.system_nCanvasHeight - 24 >> 1);
         } else if (!this.system_bExit) {
            this.scene_Render();
            if (this.system_bReRenderSoftKeys) {
               this.system_RenderSoftkeys(true);
               this.system_bReRenderSoftKeys = false;
            }
         }

         m_CurrentGraphics = null;
         Display.getDisplay(m_Midlet).callSerially(this);
      } catch (Exception var3) {
         this.system_bExit = true;
      } catch (Error var4) {
         ReportError("Error", "Caught in theGame.run()");
         this.system_bExit = true;
      }

   }

   public static void ReportError(String var0, String var1) {
      if (!bAssertionsStarted) {
         System.out.println(var0.concat(var1));
      } else {
         if (!bAssertionSet) {
            long var2 = Runtime.getRuntime().freeMemory();
            var1 = var1.concat(" free mem = ");
            TextBox var4 = new TextBox(var0, var1.concat(Long.toString(var2)), 1000, 131072);
            Display.getDisplay(m_Midlet).setCurrent(var4);
            bAssertionSet = true;
         }

      }
   }

   public static void Assert(int var0, String var1, int var2) {
      if (var0 == 0) {
         String var3 = var1 + " line=" + var2 + "\n ";
         ReportError("Assert", var3);
         ReportError("Assert", var1.concat(Integer.toString(var2)));
      }

   }

   public static void Assert(boolean var0, String var1, int var2) {
      if (!var0) {
         String var3 = var1 + " line=" + var2 + "\n ";
         ReportError("Assert", var3);
      }

   }

   public static void AssertExtra(boolean var0, String var1, int var2, String var3) {
      if (!var0) {
         String var4 = var1 + " line=" + var2 + "\n " + var3;
         ReportError("Assert", var4);
      }

   }

   public static void AssertExtra(int var0, String var1, int var2, String var3) {
      if (var0 == 0) {
         String var4 = var1 + " line=" + var2 + "\n " + var3;
         ReportError("Assert", var4);
      }

   }

   public synchronized void run() {
      if (!bAssertionsStarted) {
         bAssertionsStarted = true;
      }

      if (this.system_bAppPaused) {
         ;
      }

      if (this.system_bExit) {
         if (!bAssertionSet) {
            Display.getDisplay(m_Midlet).setCurrent((Displayable)null);
            this.hideNotify();
            this.End();
            m_Midlet.notifyDestroyed();
         }

      } else {
         try {
            long var1 = System.currentTimeMillis();
            this.system_nThisFrameTime = (int)(var1 - this.system_nLastTime);
            this.system_nLastTime = var1;
            byte var3 = 1;
            if (this.system_nThisFrameTime > 200) {
               this.system_nThisFrameTime = 200;
            }

            int var4 = this.system_nThisFrameTime;
            if (!this.system_bAssetLoading && !this.system_bExit && var4 > 0) {
               for(int var5 = 0; var5 < var3; ++var5) {
                  this.scene_Update(var4);
               }
            }
         } catch (Exception var6) {
            this.system_bExit = true;
         } catch (Error var7) {
            ReportError("Error", "Caught in theGame.run()");
            this.system_bExit = true;
         }

         ++this.system_nLoopCount;
         if (this.system_bExit) {
            if (!bAssertionSet) {
               Display.getDisplay(m_Midlet).setCurrent((Displayable)null);
               this.hideNotify();
               this.End();
               m_Midlet.notifyDestroyed();
            }

         } else {
            this.repaint();
         }
      }
   }

   public class CGroundCollision {
      int nScaleShift;
      int xOffset;
      int yOffset;
      int zOffset;
      int nTriangles;
      int nVertices;
      TheGame.CGroundCollision.CVertex[] VertexArray;
      TheGame.CGroundCollision.CTriangle[] TriangleArray;
      int m_nAttributeMask;

      void Load(short[] var1) {
         byte var3 = 0;
         this.m_nAttributeMask = -1;
         int var4 = var3 + 1;
         this.xOffset = var1[var3] << 16;
         this.xOffset |= var1[var4++] & '\uffff';
         this.yOffset = var1[var4++] << 16;
         this.yOffset |= var1[var4++] & '\uffff';
         this.zOffset = var1[var4++] << 16;
         this.zOffset |= var1[var4++] & '\uffff';
         this.nScaleShift = var1[var4++] - 8;
         if (this.nScaleShift >= 0) {
            this.xOffset >>= this.nScaleShift;
            this.xOffset <<= this.nScaleShift;
            this.yOffset >>= this.nScaleShift;
            this.yOffset <<= this.nScaleShift;
            this.zOffset >>= this.nScaleShift;
            this.zOffset <<= this.nScaleShift;
         }

         this.nVertices = var1[var4++];
         this.VertexArray = new TheGame.CGroundCollision.CVertex[this.nVertices];

         int var2;
         for(var2 = 0; var2 < this.nVertices; ++var2) {
            this.VertexArray[var2] = new TheGame.CGroundCollision.CVertex();
            this.VertexArray[var2].x = var1[var4++];
            this.VertexArray[var2].y = var1[var4++];
            this.VertexArray[var2].z = var1[var4++];
         }

         this.nTriangles = var1[var4++];
         this.TriangleArray = new TheGame.CGroundCollision.CTriangle[this.nTriangles];

         for(var2 = 0; var2 < this.nTriangles; ++var2) {
            this.TriangleArray[var2] = new TheGame.CGroundCollision.CTriangle();
            this.TriangleArray[var2].nAttribute = var1[var4++];
            this.TriangleArray[var2].nVertices[0] = var1[var4++];
            this.TriangleArray[var2].nVertices[1] = var1[var4++];
            this.TriangleArray[var2].nVertices[2] = var1[var4++];
            this.TriangleArray[var2].nAdjacentTriangles[0] = var1[var4++];
            this.TriangleArray[var2].nAdjacentTriangles[1] = var1[var4++];
            this.TriangleArray[var2].nAdjacentTriangles[2] = var1[var4++];
         }

      }

      void Free() {
         int var1;
         if (this.VertexArray != null) {
            for(var1 = 0; var1 < this.nVertices; ++var1) {
               this.VertexArray[var1] = null;
            }
         }

         this.VertexArray = null;
         this.nVertices = 0;
         if (this.TriangleArray != null) {
            for(var1 = 0; var1 < this.nTriangles; ++var1) {
               this.TriangleArray[var1] = null;
            }
         }

         this.TriangleArray = null;
         this.nTriangles = 0;
      }

      private int ConvertCollisionToWorldX(int var1) {
         if (this.nScaleShift >= 0) {
            var1 <<= this.nScaleShift;
         } else {
            var1 >>= -this.nScaleShift;
         }

         var1 += this.xOffset;
         return var1;
      }

      private int ConvertWorldToCollisionX(int var1) {
         var1 -= this.xOffset;
         if (this.nScaleShift >= 0) {
            var1 >>= this.nScaleShift;
         } else {
            var1 <<= -this.nScaleShift;
         }

         return var1;
      }

      private int ConvertCollisionToWorldY(int var1) {
         if (this.nScaleShift >= 0) {
            var1 <<= this.nScaleShift;
         } else {
            var1 >>= -this.nScaleShift;
         }

         var1 += this.yOffset;
         return var1;
      }

      private int ConvertWorldToCollisionY(int var1) {
         var1 -= this.yOffset;
         if (this.nScaleShift >= 0) {
            var1 >>= this.nScaleShift;
         } else {
            var1 <<= -this.nScaleShift;
         }

         return var1;
      }

      private int ConvertCollisionToWorldZ(int var1) {
         if (this.nScaleShift >= 0) {
            var1 <<= this.nScaleShift;
         } else {
            var1 >>= -this.nScaleShift;
         }

         var1 += this.zOffset;
         return var1;
      }

      private int ConvertWorldToCollisionZ(int var1) {
         var1 -= this.zOffset;
         if (this.nScaleShift >= 0) {
            var1 >>= this.nScaleShift;
         } else {
            var1 <<= -this.nScaleShift;
         }

         return var1;
      }

      private int FindHeight(int var1, int var2, int var3) {
         TheGame.CGroundCollision.CTriangle var4 = this.TriangleArray[var1];
         TheGame.CGroundCollision.CVertex var5 = this.VertexArray[var4.nVertices[0] & 32767];
         TheGame.CGroundCollision.CVertex var6 = this.VertexArray[var4.nVertices[1] & 32767];
         TheGame.CGroundCollision.CVertex var7 = this.VertexArray[var4.nVertices[2] & 32767];
         long var8 = (long)(var2 - (var5.x << 8));
         long var10 = (long)(var3 - (var5.y << 8));
         long var12 = (long)(var7.x - var6.x) << 8;
         long var14 = (long)(var7.y - var6.y) << 8;
         long var16 = (long)(var6.x - var5.x) << 8;
         long var18 = (long)(var6.y - var5.y) << 8;
         long var20 = var12 * var18 - var14 * var16;
         int var22 = var5.z << 8;
         if (var20 != 0L) {
            long var23 = (long)(var7.z - var6.z) << 8;
            long var25 = var8 * var18 - var10 * var16;
            long var27 = (long)(var6.z - var5.z) << 8;
            long var29 = var10 * var12 - var8 * var14;
            var22 += (int)((var23 * var25 + var27 * var29) / var20);
         }

         return var22;
      }

      private void GetTriangle(int var1, TheGame.CCollisionResult var2) {
         TheGame.CGroundCollision.CTriangle var3 = this.TriangleArray[var1];
         TheGame.CGroundCollision.CVertex var4 = this.VertexArray[var3.nVertices[0] & 32767];
         var2.nTriangleID = (short)var1;
         var2.nAttribute = var3.nAttribute;
         var2.x = this.ConvertCollisionToWorldX(var4.x << 8);
         var2.y = this.ConvertCollisionToWorldY(var4.y << 8);
         var2.z = this.ConvertCollisionToWorldZ(var4.z << 8);
      }

      private void FindTriangle(int var1, int var2, TheGame.CCollisionResult var3) {
         var3.nTriangleID = -1;

         for(int var4 = 0; var4 < this.nTriangles; ++var4) {
            TheGame.CGroundCollision.CTriangle var5 = this.TriangleArray[var4];

            int var6;
            for(var6 = 0; var6 < 3; ++var6) {
               int var7 = var6 < 2 ? var6 + 1 : 0;
               TheGame.CGroundCollision.CVertex var8;
               TheGame.CGroundCollision.CVertex var9;
               boolean var10;
               if (var5.nVertices[var6] < 0) {
                  var8 = this.VertexArray[var5.nVertices[var7] & 32767];
                  var9 = this.VertexArray[var5.nVertices[var6] & 32767];
                  var10 = true;
               } else {
                  var8 = this.VertexArray[var5.nVertices[var6] & 32767];
                  var9 = this.VertexArray[var5.nVertices[var7] & 32767];
                  var10 = false;
               }

               long var11 = ((long)var1 - ((long)var8.x << 8)) * ((long)(var9.y - var8.y) << 8) + ((long)var2 - ((long)var8.y << 8)) * ((long)(var8.x - var9.x) << 8);
               if (var10) {
                  if (var11 <= 0L) {
                     break;
                  }
               } else if (var11 > 0L) {
                  break;
               }
            }

            if (var6 == 3) {
               var3.nTriangleID = (short)var4;
               var3.nAttribute = var5.nAttribute;
               var3.x = this.ConvertCollisionToWorldX(var1);
               var3.y = this.ConvertCollisionToWorldY(var2);
               var3.z = this.ConvertCollisionToWorldZ(this.FindHeight(var4, var1, var2));
               return;
            }
         }

      }

      public void UpdateHeight(TheGame.CCollisionResult var1) {
         if (var1.nTriangleID != -1) {
            var1.z = this.ConvertCollisionToWorldZ(this.FindHeight(var1.nTriangleID, this.ConvertWorldToCollisionX(var1.x), this.ConvertWorldToCollisionY(var1.y)));
         }

      }

      void TestPointForCollision(int var1, int var2, TheGame.CCollisionResult var3) {
         var3.x = this.ConvertWorldToCollisionX(var3.x);
         var3.y = this.ConvertWorldToCollisionY(var3.y);
         var1 = this.ConvertWorldToCollisionX(var1);
         var2 = this.ConvertWorldToCollisionY(var2);
         short var4 = var3.nTriangleID;
         int var5 = -1;

         for(int var6 = 0; var6 <= 6 && var3.nTriangleID != -1; ++var6) {
            TheGame.CGroundCollision.CTriangle var7 = this.TriangleArray[var3.nTriangleID];
            boolean var8 = false;

            int var9;
            for(var9 = 0; var9 < 3; ++var9) {
               int var10 = var9 < 2 ? var9 + 1 : 0;
               TheGame.CGroundCollision.CVertex var11;
               TheGame.CGroundCollision.CVertex var12;
               boolean var13;
               if (var7.nVertices[var9] < 0) {
                  var11 = this.VertexArray[var7.nVertices[var10] & 32767];
                  var12 = this.VertexArray[var7.nVertices[var9] & 32767];
                  var13 = true;
               } else {
                  var11 = this.VertexArray[var7.nVertices[var9] & 32767];
                  var12 = this.VertexArray[var7.nVertices[var10] & 32767];
                  var13 = false;
               }

               long var14 = ((long)var1 - ((long)var11.x << 8)) * ((long)(var12.y - var11.y) << 8) + ((long)var2 - ((long)var11.y << 8)) * ((long)(var11.x - var12.x) << 8);
               if (var13 && var14 <= 0L || !var13 && var14 > 0L) {
                  int var16 = var2 - var3.y;
                  int var17 = var3.x - var1;
                  long var18 = (long)((var11.x << 8) - var3.x) * (long)var16 + (long)((var11.y << 8) - var3.y) * (long)var17;
                  long var20 = (long)((var12.x << 8) - var3.x) * (long)var16 + (long)((var12.y << 8) - var3.y) * (long)var17;
                  long var22 = ((long)var3.x - ((long)var11.x << 8)) * ((long)(var12.y - var11.y) << 8) + ((long)var3.y - ((long)var11.y << 8)) * ((long)(var11.x - var12.x) << 8);
                  boolean var24;
                  if (var13) {
                     var24 = var18 <= 0L && var20 > 0L || var22 <= 0L;
                  } else {
                     var24 = var18 > 0L && var20 <= 0L || var22 > 0L;
                  }

                  if (var24) {
                     short var25 = var7.nAdjacentTriangles[var9];
                     if (var25 >= 0 && (this.TriangleArray[var25].nAttribute & this.m_nAttributeMask) != 0) {
                        var4 = var3.nTriangleID;
                        var3.nTriangleID = var25;
                        var5 = -1;
                        break;
                     }

                     if (var5 != var9 || var4 != var3.nTriangleID) {
                        if (var5 != -1 && var4 == var3.nTriangleID) {
                           var1 = var3.x;
                           var2 = var3.y;
                           var9 = 3;
                           break;
                        }

                        long var26 = (long)(var12.x - var11.x) << 8;
                        long var28 = (long)(var12.y - var11.y) << 8;
                        long var30 = (var28 * (long)(var1 - (var11.x << 8)) - var26 * (long)(var2 - (var11.y << 8))) * 5L;
                        long var32 = (var28 * var28 + var26 * var26) * 4L;
                        var1 -= (int)(var28 * var30 / var32);
                        var2 += (int)(var26 * var30 / var32);
                        var5 = var9;
                        break;
                     }

                     var5 = var9;
                  }
               }
            }

            if (var9 == 3) {
               if (var3.nTriangleID >= 0) {
                  var3.nAttribute = var7.nAttribute;
                  var3.x = this.ConvertCollisionToWorldX(var1);
                  var3.y = this.ConvertCollisionToWorldY(var2);
                  var3.z = this.ConvertCollisionToWorldZ(this.FindHeight(var3.nTriangleID, var1, var2));
               }

               return;
            }
         }

         this.FindTriangle(var1, var2, var3);
      }

      public class CTriangle {
         short nAttribute;
         short[] nVertices = new short[3];
         short[] nAdjacentTriangles = new short[3];
      }

      public class CVertex {
         short x;
         short y;
         short z;
      }
   }

   public class CCollisionResult {
      short nAttribute;
      short nTriangleID;
      int x;
      int y;
      int z;

      void Init() {
         this.nTriangleID = -1;
         this.nAttribute = -1;
         this.x = this.y = this.z = 0;
      }
   }

   class MintMesh {
      Mesh m_mesh;
      String m_meshName;
      String m_textureName;
   }

   class Car {
      VertexArray positions = new VertexArray(4, 3, 2);
      VertexArray texCoords = new VertexArray(4, 2, 2);
      VertexBuffer vertexBuffer = new VertexBuffer();
      int[] stripLengths = new int[]{4};
      TriangleStripArray indexBuffer;
      short[] positionsData;
      int nxAI;
      int nyAI;
      int nAngleAI;
      int nAngleAIDiff;
      int nCrossAI;
      int nDotAI;
      int xOldVel;
      int yOldVel;
      int xForwad;
      int yForwad;
      int xRight2;
      int yRight2;
      int nForwardDot;
      int nSideDot;
      int nFrontWheelAngle;
      int xRight;
      int yRight;
      boolean bBackOffFromOverTacking;
      short[][] aiSpline2;
      int xDistA;
      int yDistA;
      int xDistB;
      int yDistB;
      int nAngOffset1;
      int nAngOffset2;
      int nAngOffset3;
      int xCollisionExpandA;
      int yCollisionExpandA;
      int xColSizeA;
      int yColSizeA;
      int xCollisionExpandB;
      int yCollisionExpandB;
      int xColSizeB;
      int yColSizeB;
      int xPosTmp;
      int yPosTmp;
      int xPosBInASpace;
      int yPosBInASpace;
      int xPosAInBSpace;
      int yPosAInBSpace;
      int xNormal;
      int yNormal;
      int xColPos;
      int yColPos;
      int nIntersection;
      int nCollisionExpand;
      Transform tmpTrans;
      Transform scene_workerTrans;
      public byte m_nCopNum;
      public boolean m_bPlayer;
      public boolean m_bBreaking;
      public boolean m_bReversing;
      public boolean m_bReverseTrack;
      public boolean m_bGoingBackward;
      public boolean m_bTraffic;
      public boolean m_bDisabled;
      public boolean m_bNearCulled;
      public boolean m_bCheapUpdate;
      public boolean m_bUsingSecondarySpline;
      public boolean m_bHitPlayer;
      public int m_nTimeSinceLastSplineSwap;
      public int m_nAccelerateTimer;
      public int m_nNitroTimer;
      public int m_nNitroCount;
      public int m_zSteeringAng;
      public int m_xPos;
      public int m_yPos;
      public int m_zPos;
      public int m_xVel;
      public int m_yVel;
      public int m_zVel;
      public int m_xAng;
      public int m_yAng;
      public int m_zAng;
      public int m_xAngVel;
      public int m_yAngVel;
      public int m_zAngVel;
      public float m_distanceMovedLastFrame;
      public int m_velocityLastFrame;
      public int m_velocityMax;
      int m_nLastWheelCount;
      int m_nLastAverageCount;
      int m_zLastAveragePos;
      public int m_camDist;
      public float[] rotationArray;
      public Transform rotation;
      public TheGame.CCollisionResult[] m_colResult;
      public TheGame.CCollisionResult m_tempColResult;
      int[] zWheelForce;
      public int m_nSlideOutDisableForCollision;
      public short[][] m_aiSpline;
      public int m_nAiNode;
      public int m_nLap;
      public int m_nCurCheckpoint;
      public int m_nCheckpointTime;
      public int m_nPlayerDist;
      public int m_nID;
      public int m_nSortDistance;
      public int m_nSortedPos;
      public int m_nTopSpeed;
      public int m_nAcceleration;
      public int m_nHandling;
      public int m_nAverageSpeed;
      public int m_nReverseTimer;
      public int m_nAverageSplineMovementSpeed;
      public int m_nTimeNotVisable;
      public int m_nTimeSinceSpawn;
      public int m_nState;
      public int m_nStateTime;
      public int m_nSpeedingScore;
      public int m_nBustedness;
      public int m_nPursuitTimer;
      public int m_nDamagedLevel;
      public int m_nHighestRaceSpeed;
      public int m_nCurrentHeatRating;
      public int m_nCoolTimer;
      public int m_nSpeedingTimer;
      public int m_nAssaultTimer;
      public int m_nRecklessTimer;
      int m_nCrossAI;
      int m_nOverTackingAngleOffset;
      int m_nCurrentSplineDistance;
      public TheGame.NFSMW_CarAppearance m_ca;
      public int m_nCA;
      TheGame.Car.CSpark[] m_carSparks;

      public Car(int var2) {
         this.indexBuffer = new TriangleStripArray(0, this.stripLengths);
         this.positionsData = new short[]{-128, 128, 0, -128, -128, 0, 128, 128, 0, 128, -128, 0};
         this.tmpTrans = new Transform();
         this.scene_workerTrans = new Transform();
         this.m_bCheapUpdate = false;
         this.m_bUsingSecondarySpline = false;
         this.m_bHitPlayer = false;
         this.m_nTimeSinceLastSplineSwap = 0;
         this.m_nNitroCount = 5;
         this.rotationArray = new float[16];
         this.rotation = new Transform();
         this.m_colResult = new TheGame.CCollisionResult[4];
         this.m_tempColResult = TheGame.this.new CCollisionResult();
         this.zWheelForce = new int[4];
         this.m_nTopSpeed = 15510;
         this.m_nAcceleration = 90;
         this.m_nHandling = 64;
         this.m_nAverageSpeed = this.m_nTopSpeed;
         this.m_nReverseTimer = 0;
         this.m_carSparks = new TheGame.Car.CSpark[5];
         this.m_nID = var2;
         this.m_ca = null;
         this.m_bBreaking = false;
         this.m_bReversing = false;
         TheGame.this.shadowCompmode.setBlending(68);
         TheGame.this.shadowCompmode.setDepthTestEnable(false);
         TheGame.this.shadowCompmode.setDepthWriteEnable(true);
         TheGame.this.shadowAppearance.setPolygonMode(TheGame.this.polygonMode_NoPersp);
         TheGame.this.shadowAppearance.setCompositingMode(TheGame.this.shadowCompmode);
         this.positions.set(0, 4, this.positionsData);
         this.vertexBuffer.setPositions(this.positions, 0.015625F, (float[])null);
         this.vertexBuffer.setDefaultColor(-14869219);

         for(int var3 = 0; var3 < this.m_carSparks.length; ++var3) {
            this.m_carSparks[var3] = new TheGame.Car.CSpark();
         }

      }

      public void Init() {
         this.m_nTimeNotVisable = 0;
         this.m_nCurCheckpoint = 0;
         this.m_nCheckpointTime = 0;
         this.m_nAiNode = 0;
         this.m_nLap = 0;
         this.m_velocityMax = 0;
         this.m_velocityLastFrame = 0;
         this.m_nSpeedingScore = 0;
         this.m_nPlayerDist = 0;
         this.m_nHighestRaceSpeed = 0;
         this.m_bDisabled = false;
         this.m_nCurrentHeatRating = 0;
         this.m_nCoolTimer = 0;
         this.m_nSpeedingTimer = 0;
         this.m_nAssaultTimer = 0;
         this.m_nRecklessTimer = 0;
         this.m_bBreaking = false;
         this.m_bReversing = false;
         this.m_nReverseTimer = 0;
         this.m_bReverseTrack = false;
         this.m_bGoingBackward = false;
         this.m_bCheapUpdate = false;
         this.m_bUsingSecondarySpline = false;
         this.m_nTimeSinceLastSplineSwap = 0;
         this.m_distanceMovedLastFrame = 0.0F;
         this.m_nSlideOutDisableForCollision = 0;
         this.m_nAverageSpeed = this.m_nTopSpeed;
         this.m_nAverageSplineMovementSpeed = 0;
         this.m_nTimeSinceSpawn = 0;
         this.m_bHitPlayer = false;
         this.m_nBustedness = 0;
         this.m_nDamagedLevel = 0;
         this.m_nCurrentSplineDistance = 0;
         this.m_nCrossAI = 0;
         this.m_nOverTackingAngleOffset = 0;
         this.m_nState = 0;
         this.m_nStateTime = 0;
         this.m_xVel = this.m_yVel = this.m_zVel = 0;
         this.m_nTopSpeed = 15510;
         this.m_nAcceleration = 90;
         this.m_nHandling = 64;

         for(int var1 = 0; var1 < this.m_carSparks.length; ++var1) {
            if (this.m_carSparks[var1] == null) {
               this.m_carSparks[var1] = new TheGame.Car.CSpark();
            }

            this.m_carSparks[var1].timer = 0;
         }

      }

      public void DeInitialize() {
         int var1;
         for(var1 = 0; var1 < this.m_carSparks.length; ++var1) {
            this.m_carSparks[var1] = null;
         }

         for(var1 = 0; var1 < 4; ++var1) {
            this.m_colResult[var1] = null;
         }

         this.m_ca = null;
      }

      public void SetAppearance(TheGame.NFSMW_CarAppearance var1, int var2, boolean var3) {
         if (var3) {
            this.m_ca = TheGame.this.new NFSMW_CarAppearance(var1);
         } else {
            this.m_ca = var1;
         }

         this.m_nCA = var2;
         this.AdjustCarStats();
      }

      public void AdjustCarStats() {
         float var2;
         float var3;
         float var4;
         for(int var1 = 0; var1 < 4; ++var1) {
            if (!this.m_bPlayer) {
               var2 = (float)TheGame.this.g_race.m_difficulty;
               var3 = (float)TheGame.this.stats_max[var1] / 6.0F;
               var4 = var2 * var3;
               if (TheGame.this.g_race.m_nMissionID < 2) {
                  this.m_ca.m_modStats[var1] = (int)var4 - 1;
               } else if ((TheGame.this.g_race.m_nMissionID <= 1 || TheGame.this.g_race.m_nMissionID >= 5) && TheGame.this.g_race.m_nMissionID <= 35) {
                  if (TheGame.this.g_race.m_nMissionID == 5) {
                     this.m_ca.m_modStats[var1] = TheGame.this.stats_max[var1];
                  } else if (TheGame.this.g_player.m_missionStatus[1] == 0) {
                     this.m_ca.m_modStats[var1] = (int)var4 - 2;
                  } else if (TheGame.this.g_race.m_nMissionID == 35) {
                     this.m_ca.m_modStats[var1] = (int)var4 - 2;
                  } else {
                     this.m_ca.m_modStats[var1] = (int)var4 - 1;
                  }
               } else {
                  this.m_ca.m_modStats[var1] = (int)var4 + 1;
               }
            }

            if (this.m_ca.m_modStats[var1] <= 0) {
               this.m_ca.m_modStats[var1] = 1;
            }

            if (this.m_ca.m_modStats[var1] > TheGame.this.stats_max[var1]) {
               this.m_ca.m_modStats[var1] = TheGame.this.stats_max[var1];
            }
         }

         if (this.m_bPlayer) {
            this.m_nCurrentHeatRating = 0;
            if (TheGame.this.g_race.m_type == 4 || TheGame.this.system_bDemoMode || TheGame.this.g_player.m_missionStatus[5] == 1) {
               this.m_nCurrentHeatRating = 3;
            }
         }

         float var7 = 5.0F;

         try {
            if (this.m_ca.m_modStats[0] > TheGame.this.stats_max[0] || this.m_ca.m_modStats[2] > TheGame.this.stats_max[2] || this.m_ca.m_modStats[1] > TheGame.this.stats_max[1]) {
               throw new Exception();
            }
         } catch (Exception var5) {
            var5.printStackTrace();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 385, var5.toString());
         } catch (Error var6) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\scene_share.hpp", 385, var6.toString());
         }

         var2 = 1.0F - (float)this.m_ca.m_modStats[0] / (float)TheGame.this.stats_max[0];
         var3 = 1.0F - (float)this.m_ca.m_modStats[2] / (float)TheGame.this.stats_max[2];
         var4 = 1.0F - (float)this.m_ca.m_modStats[1] / (float)TheGame.this.stats_max[1];
         this.m_nHandling = (int)((float)this.m_nHandling - (float)this.m_nHandling * var4 / var7);
         this.m_nAcceleration = (int)((float)this.m_nAcceleration - (float)this.m_nAcceleration * var3 / var7);
         this.m_nTopSpeed = (int)((float)this.m_nTopSpeed - (float)this.m_nTopSpeed * var2 / var7);
         if (this.m_nCopNum != 0) {
            this.m_nTopSpeed = TheGame.this.players[0].m_nTopSpeed + 1000;
            this.m_nAcceleration = 170;
            this.m_nHandling = 64;
         }

      }

      public void InitCollision() {
         if (this.m_colResult[0] == null) {
            this.m_colResult[0] = TheGame.this.new CCollisionResult();
         }

         if (this.m_colResult[1] == null) {
            this.m_colResult[1] = TheGame.this.new CCollisionResult();
         }

         if (this.m_colResult[2] == null) {
            this.m_colResult[2] = TheGame.this.new CCollisionResult();
         }

         if (this.m_colResult[3] == null) {
            this.m_colResult[3] = TheGame.this.new CCollisionResult();
         }

         this.m_colResult[0].Init();
         this.m_colResult[1].Init();
         this.m_colResult[2].Init();
         this.m_colResult[3].Init();
         this.m_nAiNode = this.FindClosedAISpline();
         this.m_zAng = (this.m_aiSpline[this.m_nAiNode][2] & '\uffff') + 16384 << 8;
      }

      public void InitCollisionToSplinePos(int var1) {
         if (this.m_colResult[0] == null) {
            this.m_colResult[0] = TheGame.this.new CCollisionResult();
         }

         if (this.m_colResult[1] == null) {
            this.m_colResult[1] = TheGame.this.new CCollisionResult();
         }

         if (this.m_colResult[2] == null) {
            this.m_colResult[2] = TheGame.this.new CCollisionResult();
         }

         if (this.m_colResult[3] == null) {
            this.m_colResult[3] = TheGame.this.new CCollisionResult();
         }

         this.m_colResult[0].nAttribute = 0;
         this.m_colResult[0].nTriangleID = this.m_aiSpline[var1][3];
         this.m_colResult[0].x = this.m_aiSpline[var1][0] << 12;
         this.m_colResult[0].y = -this.m_aiSpline[var1][1] << 12;
         this.m_colResult[1].nAttribute = 0;
         this.m_colResult[1].nTriangleID = this.m_aiSpline[var1][3];
         this.m_colResult[1].x = this.m_aiSpline[var1][0] << 12;
         this.m_colResult[1].y = -this.m_aiSpline[var1][1] << 12;
         this.m_colResult[2].nAttribute = 0;
         this.m_colResult[2].nTriangleID = this.m_aiSpline[var1][3];
         this.m_colResult[2].x = this.m_aiSpline[var1][0] << 12;
         this.m_colResult[2].y = -this.m_aiSpline[var1][1] << 12;
         this.m_colResult[3].nAttribute = 0;
         this.m_colResult[3].nTriangleID = this.m_aiSpline[var1][3];
         this.m_colResult[3].x = this.m_aiSpline[var1][0] << 12;
         this.m_colResult[3].y = -this.m_aiSpline[var1][1] << 12;
         TheGame.this.groundCollision.UpdateHeight(this.m_colResult[0]);
         this.m_colResult[1].z = this.m_colResult[0].z;
         this.m_colResult[2].z = this.m_colResult[0].z;
         this.m_colResult[3].z = this.m_colResult[0].z;
         this.m_zPos = this.m_colResult[0].z >> 8;
         this.m_zLastAveragePos = this.m_zPos;
         this.m_nLastWheelCount = 0;
         this.m_xAng = 0;
         this.m_yAng = 0;
         this.m_xAngVel = 0;
         this.m_yAngVel = 0;
         this.m_zAngVel = 0;
         this.m_xVel = 0;
         this.m_yVel = 0;
         this.m_zVel = 0;
         this.m_nAverageSplineMovementSpeed = 0;
         this.UpdatePhysics(100, 655);
         this.UpdateTrackCollision(100, 655);
         this.m_xAngVel = 0;
         this.m_yAngVel = 0;
         this.m_zAngVel = 0;
         this.m_xVel = 0;
         this.m_yVel = 0;
         this.m_zVel = 0;
         this.m_zLastAveragePos = this.m_zPos;
         this.UpdatePhysics(100, 655);
         this.UpdateTrackCollision(100, 655);
         this.m_xAngVel = 0;
         this.m_yAngVel = 0;
         this.m_zAngVel = 0;
         this.m_xVel = 0;
         this.m_yVel = 0;
         this.m_zVel = 0;
      }

      public void InitCollisionToSplinePosWhileMoving(int var1) {
         this.m_colResult[0].nAttribute = 0;
         this.m_colResult[0].nTriangleID = this.m_aiSpline[var1][3];
         this.m_colResult[0].x = this.m_aiSpline[var1][0] << 12;
         this.m_colResult[0].y = -this.m_aiSpline[var1][1] << 12;
         this.m_colResult[1].nAttribute = 0;
         this.m_colResult[1].nTriangleID = this.m_aiSpline[var1][3];
         this.m_colResult[1].x = this.m_aiSpline[var1][0] << 12;
         this.m_colResult[1].y = -this.m_aiSpline[var1][1] << 12;
         this.m_colResult[2].nAttribute = 0;
         this.m_colResult[2].nTriangleID = this.m_aiSpline[var1][3];
         this.m_colResult[2].x = this.m_aiSpline[var1][0] << 12;
         this.m_colResult[2].y = -this.m_aiSpline[var1][1] << 12;
         this.m_colResult[3].nAttribute = 0;
         this.m_colResult[3].nTriangleID = this.m_aiSpline[var1][3];
         this.m_colResult[3].x = this.m_aiSpline[var1][0] << 12;
         this.m_colResult[3].y = -this.m_aiSpline[var1][1] << 12;
         TheGame.this.groundCollision.UpdateHeight(this.m_colResult[0]);
         this.m_colResult[1].z = this.m_colResult[0].z;
         this.m_colResult[2].z = this.m_colResult[0].z;
         this.m_colResult[3].z = this.m_colResult[0].z;
         this.m_zVel = 0;
         this.m_zPos = this.m_colResult[0].z >> 8;
         this.m_zLastAveragePos = this.m_zPos;
         this.m_nLastWheelCount = 0;
      }

      public void renderShadow() {
         if (!this.m_bDisabled) {
            int var1 = -(TheGame.this.sin_Array[TheGame.this.game_cameraAngZ >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (TheGame.this.game_cameraAngZ >> 8 >> 8) & 255] - TheGame.this.sin_Array[TheGame.this.game_cameraAngZ >> 8 >> 8 & 255]) * ((TheGame.this.game_cameraAngZ >> 8) - (TheGame.this.game_cameraAngZ >> 8 & -256)) >> 8));
            int var2 = TheGame.this.sin_Array[(TheGame.this.game_cameraAngZ >> 8) + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((TheGame.this.game_cameraAngZ >> 8) + 49152 >> 8) & 255] - TheGame.this.sin_Array[(TheGame.this.game_cameraAngZ >> 8) + 49152 >> 8 & 255]) * ((TheGame.this.game_cameraAngZ >> 8) + 49152 - ((TheGame.this.game_cameraAngZ >> 8) + 49152 & -256)) >> 8);
            int var3 = this.m_xPos - TheGame.this.game_posX;
            int var4 = this.m_yPos - TheGame.this.game_posY;
            this.m_bNearCulled = var3 * var1 + var4 * var2 < -25165824;
            if (this.m_camDist < 8000 && !this.m_bNearCulled) {
               this.positionsData[0] = (short)(this.m_colResult[0].x >> 10);
               this.positionsData[1] = (short)((this.m_colResult[0].z >> 10) + 4);
               this.positionsData[2] = (short)(-this.m_colResult[0].y >> 10);
               this.positionsData[3] = (short)(this.m_colResult[1].x >> 10);
               this.positionsData[4] = (short)((this.m_colResult[1].z >> 10) + 4);
               this.positionsData[5] = (short)(-this.m_colResult[1].y >> 10);
               this.positionsData[6] = (short)(this.m_colResult[2].x >> 10);
               this.positionsData[7] = (short)((this.m_colResult[2].z >> 10) + 4);
               this.positionsData[8] = (short)(-this.m_colResult[2].y >> 10);
               this.positionsData[9] = (short)(this.m_colResult[3].x >> 10);
               this.positionsData[10] = (short)((this.m_colResult[3].z >> 10) + 4);
               this.positionsData[11] = (short)(-this.m_colResult[3].y >> 10);
               this.positions.set(0, 4, this.positionsData);
               TheGame.this.scene_g3d.render(this.vertexBuffer, this.indexBuffer, TheGame.this.shadowAppearance, (Transform)null);
            }

         }
      }

      public int FindClosedAISpline() {
         int var1 = 0;
         int var2 = 1073741824;
         int var3 = this.m_aiSpline.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = this.m_xPos - (this.m_aiSpline[var4][0] << 4);
            int var6 = this.m_yPos - (this.m_aiSpline[var4][1] << 4);
            int var7 = TheGame.this.ApproximateMagnitude(var5, var6);
            if (var2 > var7) {
               var2 = var7;
               var1 = var4;
            }
         }

         return var1;
      }

      void PlayerAcceleration(int var1) {
         this.m_bBreaking = false;
         this.m_bReversing = false;
         if ((TheGame.this.game_nKeyFlags & 16) != 0) {
            this.m_nAccelerateTimer = -1024;
            this.m_bBreaking = true;
         } else if ((TheGame.this.game_nKeyFlags & 2) != 0) {
            this.m_nAccelerateTimer = 2500;
         } else if ((TheGame.this.game_nKeyFlags & 4) != 0) {
            this.m_nAccelerateTimer = -1024;
            this.m_bBreaking = true;
         } else if ((TheGame.this.game_nKeyFlags & 1) != 0) {
            this.m_nAccelerateTimer = 250000;
         } else if ((TheGame.this.game_nKeyFlags & 6144) != 0) {
            if (TheGame.this.g_race.m_type == 4) {
               TheGame.this.game_popupText(TheGame.this.g_Text[182], false);
            } else if (this.m_nNitroCount > 0 && this.m_nNitroTimer <= 0) {
               this.m_nNitroTimer = 2500;
               --this.m_nNitroCount;
            }
         } else if (this.m_nAccelerateTimer >= 0) {
            this.m_nAccelerateTimer -= var1;
            if (this.m_nAccelerateTimer < 0) {
               this.m_nAccelerateTimer = 0;
            }
         } else {
            this.m_nAccelerateTimer += var1;
            if (this.m_nAccelerateTimer >= 0) {
               this.m_nAccelerateTimer = 0;
            }
         }

         if (this.m_nNitroTimer > 0) {
            this.m_nNitroTimer -= var1;
            if (this.m_nNitroTimer < 0) {
               this.m_nNitroTimer = 0;
            }
         }

         int var2;
         int var3;
         if (this.m_nAccelerateTimer <= 0 && this.m_nNitroTimer <= 0) {
            if (this.m_bBreaking && this.nForwardDot < 64) {
               var2 = this.nForwardDot;
               var2 += 15000;
               var2 = var2 * (TheGame.this.exp_Array[-(-var1 >> 1) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 >> 1) >> 8)] - TheGame.this.exp_Array[-(-var1 >> 1) >> 8]) * (-(-var1 >> 1) - (-(-var1 >> 1) & -256)) >> 8)) >> 16;
               var2 -= 15000;
               this.m_xVel += this.xForwad * (var2 - this.nForwardDot) >> 14;
               this.m_yVel += this.yForwad * (var2 - this.nForwardDot) >> 14;
               this.m_bReversing = true;
               this.m_bBreaking = false;
            } else if (this.nForwardDot > 0) {
               var2 = var1 * 4;
               var2 = var2 * -this.m_nAccelerateTimer >> 10;
               if (var2 > this.nForwardDot) {
                  var2 = this.nForwardDot;
               }

               this.m_xVel -= this.xForwad * var2 >> 14;
               this.m_yVel -= this.yForwad * var2 >> 14;
            } else if (this.nForwardDot < 0) {
               var2 = var1 * 4;
               var2 = var2 * -this.m_nAccelerateTimer >> 10;
               if (var2 > -this.nForwardDot) {
                  var2 = -this.nForwardDot;
               }

               this.m_xVel += this.xForwad * var2 >> 14;
               this.m_yVel += this.yForwad * var2 >> 14;
            }
         } else if (this.nForwardDot > -16) {
            var2 = this.m_nAcceleration;
            if (this.m_nNitroTimer > 0) {
               var2 += 100;
            }

            var3 = this.nForwardDot;
            var3 -= this.m_nTopSpeed;
            var3 = var3 * (TheGame.this.exp_Array[-(-(var1 * var2) >> 8) >> 8] + ((TheGame.this.exp_Array[1 + (-(-(var1 * var2) >> 8) >> 8)] - TheGame.this.exp_Array[-(-(var1 * var2) >> 8) >> 8]) * (-(-(var1 * var2) >> 8) - (-(-(var1 * var2) >> 8) & -256)) >> 8)) >> 16;
            var3 += this.m_nTopSpeed;
            this.m_xVel += this.xForwad * (var3 - this.nForwardDot) >> 14;
            this.m_yVel += this.yForwad * (var3 - this.nForwardDot) >> 14;
         } else {
            var2 = var1 * 4;
            if (var2 > -this.nForwardDot) {
               var2 = -this.nForwardDot;
            }

            this.m_xVel += this.xForwad * var2 >> 14;
            this.m_yVel += this.yForwad * var2 >> 14;
            this.m_bBreaking = true;
         }

         var2 = this.m_xVel * this.xRight + this.m_yVel * this.yRight >> 14;
         if (var2 > 0) {
            var3 = var1 * this.m_nHandling >> 3;
            if (var3 > var2) {
               var3 = var2;
            }

            this.m_xVel -= this.xRight * var3 >> 14;
            this.m_yVel -= this.yRight * var3 >> 14;
         } else {
            var3 = var1 * this.m_nHandling >> 3;
            if (var3 > -var2) {
               var3 = -var2;
            }

            this.m_xVel += this.xRight * var3 >> 14;
            this.m_yVel += this.yRight * var3 >> 14;
         }

      }

      void UpdatePositionOnAiSpline_backwards(int var1) {
         int var2 = 0;

         while(true) {
            int var3 = this.m_nAiNode - 1;
            if (var3 <= 0) {
               var3 = this.m_aiSpline.length - 1;
            }

            int var4 = this.m_xPos - (this.m_aiSpline[var3][0] << 4);
            int var5 = this.m_yPos - (this.m_aiSpline[var3][1] << 4);
            this.nAngleAI = this.m_aiSpline[var3][2] + 32768 & '\uffff';
            this.nyAI = -TheGame.this.sin_Array[-this.nAngleAI >> 8 & 255] >> 4;
            this.nxAI = TheGame.this.sin_Array[(-this.nAngleAI >> 8) + 192 & 255] >> 4;
            this.nDotAI = var4 * this.nxAI + var5 * this.nyAI;
            if (this.nDotAI < 0 || var2 > 0) {
               this.nCrossAI = var4 * this.nyAI - var5 * this.nxAI >> 10;
               this.m_nCrossAI = this.nCrossAI;
               int var6 = this.m_xPos - (this.m_aiSpline[this.m_nAiNode][0] << 4);
               int var7 = this.m_yPos - (this.m_aiSpline[this.m_nAiNode][1] << 4);
               int var8 = var6 * this.nxAI + var7 * this.nyAI;
               this.m_nCurrentSplineDistance = (var8 << 4) / (-this.nDotAI + var8 >> 10);
               if (this.m_bPlayer) {
                  this.setPlayerTargetAngle(var3, false);
               } else {
                  this.setAITargetAngle(var8, var3);
               }

               return;
            }

            ++var2;
            this.m_nAiNode = var3;
            if (var3 == this.m_aiSpline.length - 1) {
               --this.m_nLap;
            }
         }
      }

      void setPlayerTargetAngle(int var1, boolean var2) {
         if (var2) {
            this.nAngleAI = this.m_aiSpline[var1][2] & '\uffff';
         } else {
            this.nAngleAI = this.m_aiSpline[var1][2] + 32768 & '\uffff';
         }

         int var3 = var1 + 7;
         if (var3 >= this.m_aiSpline.length) {
            var3 -= this.m_aiSpline.length;
         }

         TheGame.this.game_xArrowPos = this.m_aiSpline[var3][0] << 4;
         TheGame.this.game_yArrowPos = this.m_aiSpline[var3][1] << 4;
      }

      void setAITargetAngle(int var1, int var2) {
         int var3 = this.nForwardDot * 200 >> 8;
         int var4 = this.nForwardDot >> 12;
         var3 -= var4 << 12;
         if (var4 < 0) {
            var4 = 0;
            var3 = 0;
         }

         int var5 = this.m_nAiNode - 1 - var4;
         int var6 = this.m_nAiNode - 2 - var4;
         if (var5 < 0) {
            var5 += this.m_aiSpline.length;
         }

         if (var6 < 0) {
            var6 += this.m_aiSpline.length;
         }

         this.nAngleAI = this.m_aiSpline[var5][2] + 32768 & '\uffff';
         int var7 = this.m_aiSpline[var6][2] + 32768 & '\uffff';
         if (var7 - this.nAngleAI > 32768) {
            var7 -= 65536;
         }

         if (var7 - this.nAngleAI < -32768) {
            var7 += 65536;
         }

         var3 *= -this.nDotAI + var1;
         var3 >>= 12;
         int var8 = (var1 + var3 << 4) / (-this.nDotAI + var1 >> 10);
         this.nAngleAI += (var7 - this.nAngleAI) * var8 >> 14;
         int var9 = var2 - 5;
         if (var9 < 0) {
            var9 += this.m_aiSpline.length;
         }

         this.nAngleAIDiff = (this.m_aiSpline[this.m_nAiNode][2] & '\uffff') - (this.m_aiSpline[var9][2] & '\uffff') & '\uffff';
         if (this.nAngleAIDiff > 32768) {
            this.nAngleAIDiff -= 65536;
         } else if (this.nAngleAIDiff < -32768) {
            this.nAngleAIDiff += 65536;
         }

      }

      void UpdatePositionOnAiSpline_Forwards_NextSplinePoint() {
         if (this.m_nCopNum == 0 && this.m_nID >= 0) {
            ++this.m_nLap;
            if (TheGame.this.g_race.m_type != 1 && TheGame.this.g_race.m_type != 3) {
               this.m_nCheckpointTime = TheGame.this.game_Time;
            }

            if (this.m_bPlayer) {
               TheGame.this.g_race.m_nLastLapTime = TheGame.this.game_Time - 5000 - TheGame.this.g_race.m_nLastLapTime;
               if (TheGame.this.g_race.m_nFastestLapTime > TheGame.this.g_race.m_nLastLapTime || TheGame.this.g_race.m_nFastestLapTime == 0) {
                  TheGame.this.g_race.m_nFastestLapTime = TheGame.this.g_race.m_nLastLapTime;
               }
            }

            if (TheGame.this.g_race.m_type == 2) {
               int var1 = -1;
               int var2 = 0;

               int var3;
               for(var3 = 0; var3 < TheGame.this.g_race.m_numRacers; ++var3) {
                  if (!TheGame.this.players[var3].m_bDisabled && TheGame.this.players[var3].m_nID != this.m_nID && TheGame.this.players[var3].m_nLap < this.m_nLap) {
                     ++var2;
                     var1 = TheGame.this.players[var3].m_nID;
                  }
               }

               if (var2 == 1) {
                  if (!TheGame.this.players[var1].m_bPlayer) {
                     TheGame.this.players[var1].m_bDisabled = true;
                     TheGame.this.game_popupText(TheGame.this.g_Text[183], false);
                  } else {
                     TheGame.this.game_EndRace();
                  }
               }

               var2 = 0;

               for(var3 = 0; var3 < TheGame.this.g_race.m_numRacers; ++var3) {
                  if (!TheGame.this.players[var3].m_bDisabled) {
                     ++var2;
                  }
               }

               if (var2 == 1) {
                  TheGame.this.game_EndRace();
               }
            } else if (TheGame.this.g_race.m_numRacers > 1 && this.m_nLap < TheGame.this.g_race.m_laps) {
               this.CalculateTimeDiff();
            }
         }

      }

      void UpdatePositionOnAiSpline_Forwards_LookAhead(short[][] var1, int var2, int var3) {
         int var4 = this.nForwardDot * 200 >> 8;
         int var5 = this.nForwardDot >> 12;
         var4 -= var5 << 12;
         if (var5 < 0) {
            var5 = 0;
            var4 = 0;
         }

         int var6 = this.m_nAiNode + var5;
         int var7 = this.m_nAiNode + 1 + var5;
         if (var6 >= this.m_aiSpline.length) {
            var6 -= this.m_aiSpline.length;
         }

         if (var7 >= this.m_aiSpline.length) {
            var7 -= this.m_aiSpline.length;
         }

         this.nAngleAI = var1[var6][2] & '\uffff';
         int var8 = var1[var7][2] & '\uffff';
         if (var8 - this.nAngleAI > 32768) {
            var8 -= 65536;
         }

         if (var8 - this.nAngleAI < -32768) {
            var8 += 65536;
         }

         var4 *= -this.nDotAI + var2;
         var4 >>= 12;
         int var9 = (var2 + var4 << 4) / (-this.nDotAI + var2 >> 10);
         this.nAngleAI += (var8 - this.nAngleAI) * var9 >> 14;
         int var10 = var3 + 5;
         if (var10 >= this.m_aiSpline.length) {
            var10 -= this.m_aiSpline.length;
         }

         this.nAngleAIDiff = (var1[this.m_nAiNode][2] & '\uffff') - (var1[var10][2] & '\uffff') & '\uffff';
         if (this.nAngleAIDiff > 32768) {
            this.nAngleAIDiff -= 65536;
         } else if (this.nAngleAIDiff < -32768) {
            this.nAngleAIDiff += 65536;
         }

      }

      void UpdatePositionOnAiSpline_Forwards_SplineSwapping(int var1) {
         if (this.m_aiSpline == TheGame.this.track_splines[0]) {
            this.aiSpline2 = TheGame.this.track_splines[1];
            short var2 = this.aiSpline2[this.m_nAiNode][2];
            int var3 = this.m_xPos - (this.aiSpline2[this.m_nAiNode][0] << 4);
            int var4 = this.m_yPos - (this.aiSpline2[this.m_nAiNode][1] << 4);
            int var5 = -TheGame.this.sin_Array[-var2 >> 8 & 255] >> 4;
            int var6 = TheGame.this.sin_Array[(-var2 >> 8) + 192 & 255] >> 4;
            int var7 = var3 * var5 - var4 * var6 >> 10;
            this.m_nTimeSinceLastSplineSwap += var1;
            short var8 = 300;
            if (Math.abs(this.nCrossAI - var7) < 100) {
               if (this.m_nTimeSinceLastSplineSwap > 2000) {
                  var8 = 100;
               }

               if (this.m_nTimeSinceLastSplineSwap > 4000) {
                  var8 = 0;
               }
            }

            if (this.m_bUsingSecondarySpline) {
               if (Math.abs(this.nCrossAI) < Math.abs(var7) - var8) {
                  this.m_bUsingSecondarySpline = false;
                  this.aiSpline2 = TheGame.this.track_splines[0];
                  this.m_nTimeSinceLastSplineSwap = 0;
               } else {
                  this.aiSpline2 = TheGame.this.track_splines[1];
                  this.nCrossAI = var7;
               }
            } else if (Math.abs(var7) < Math.abs(this.nCrossAI) - var8) {
               this.m_bUsingSecondarySpline = true;
               this.aiSpline2 = TheGame.this.track_splines[1];
               this.m_nTimeSinceLastSplineSwap = 0;
               this.nCrossAI = var7;
            } else {
               this.aiSpline2 = TheGame.this.track_splines[0];
            }
         } else {
            this.aiSpline2 = this.m_aiSpline;
         }

      }

      void UpdatePositionOnAiSpline_Forwards(int var1) {
         int var2 = 0;

         while(true) {
            int var3 = this.m_nAiNode + 1;
            if (var3 >= this.m_aiSpline.length) {
               var3 = 0;
            }

            int var4 = this.m_xPos - (this.m_aiSpline[var3][0] << 4);
            int var5 = this.m_yPos - (this.m_aiSpline[var3][1] << 4);
            this.nAngleAI = this.m_aiSpline[this.m_nAiNode][2] & '\uffff';
            this.nyAI = -TheGame.this.sin_Array[-this.nAngleAI >> 8 & 255] >> 4;
            this.nxAI = TheGame.this.sin_Array[(-this.nAngleAI >> 8) + 192 & 255] >> 4;
            this.nDotAI = var4 * this.nxAI + var5 * this.nyAI;
            if (this.nDotAI < 0 || var2 > 0) {
               this.nCrossAI = var4 * this.nyAI - var5 * this.nxAI >> 10;
               this.m_nCrossAI = this.nCrossAI;
               this.UpdatePositionOnAiSpline_Forwards_SplineSwapping(var1);
               int var6 = this.m_xPos - (this.m_aiSpline[this.m_nAiNode][0] << 4);
               int var7 = this.m_yPos - (this.m_aiSpline[this.m_nAiNode][1] << 4);
               int var8 = var6 * this.nxAI + var7 * this.nyAI;
               this.m_nCurrentSplineDistance = (var8 << 4) / (-this.nDotAI + var8 >> 10);
               if (this.m_bPlayer) {
                  this.setPlayerTargetAngle(this.m_nAiNode, true);
               } else {
                  this.UpdatePositionOnAiSpline_Forwards_LookAhead(this.aiSpline2, var8, var3);
               }

               this.aiSpline2 = (short[][])null;
               return;
            }

            ++var2;
            this.m_nAiNode = var3;
            if (var3 == 0 && TheGame.this.game_state == 2) {
               this.UpdatePositionOnAiSpline_Forwards_NextSplinePoint();
            }
         }
      }

      void UpdatePositionOnAiSpline(int var1) {
         if (this.m_bGoingBackward) {
            this.UpdatePositionOnAiSpline_backwards(var1);
         } else {
            this.UpdatePositionOnAiSpline_Forwards(var1);
         }

         this.nAngleAI += 16384;
      }

      void PlayerSteering(int var1) {
         if (this.m_nLastWheelCount >= 2) {
            int var2 = 20;
            if (this.m_nAccelerateTimer <= 0) {
               var2 += 12 * (-this.m_nAccelerateTimer >> 4) >> 6;
            }

            int var3 = this.calcPlayerMaxTurningAngle();
            if ((TheGame.this.game_nKeyFlags & 96) != 0) {
               if (this.m_zAngVel < 0) {
                  var3 -= this.m_zAngVel * 2;
               }

               if (var3 > 16384) {
                  var3 = 16384;
               }

               this.m_zSteeringAng -= var3;
               this.m_zSteeringAng = this.m_zSteeringAng * (TheGame.this.exp_Array[-(-var1 * var2) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * var2) >> 8)] - TheGame.this.exp_Array[-(-var1 * var2) >> 8]) * (-(-var1 * var2) - (-(-var1 * var2) & -256)) >> 8)) >> 16;
               this.m_zSteeringAng += var3;
            }

            if ((TheGame.this.game_nKeyFlags & 384) != 0) {
               if (this.m_zAngVel > 0) {
                  var3 += this.m_zAngVel * 2;
               }

               if (var3 > 16384) {
                  var3 = 16384;
               }

               this.m_zSteeringAng += var3;
               this.m_zSteeringAng = this.m_zSteeringAng * (TheGame.this.exp_Array[-(-var1 * var2) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * var2) >> 8)] - TheGame.this.exp_Array[-(-var1 * var2) >> 8]) * (-(-var1 * var2) - (-(-var1 * var2) & -256)) >> 8)) >> 16;
               this.m_zSteeringAng -= var3;
            }

            if ((TheGame.this.game_nKeyFlags & 480) == 0) {
               this.m_zSteeringAng = this.m_zSteeringAng * (TheGame.this.exp_Array[-(-var1 * 24) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 24) >> 8)] - TheGame.this.exp_Array[-(-var1 * 24) >> 8]) * (-(-var1 * 24) - (-(-var1 * 24) & -256)) >> 8)) >> 24;
            }

            this.m_zAngVel -= this.m_zSteeringAng;
            this.m_zAngVel = this.m_zAngVel * (TheGame.this.exp_Array[-(-var1 * 75) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 75) >> 8)] - TheGame.this.exp_Array[-(-var1 * 75) >> 8]) * (-(-var1 * 75) - (-(-var1 * 75) & -256)) >> 8)) >> 16;
            if (this.m_zAngVel > 0) {
               this.m_zAngVel -= var1 * 4;
               if (this.m_zAngVel < 0) {
                  this.m_zAngVel = 0;
               }
            } else {
               this.m_zAngVel += var1 * 4;
               if (this.m_zAngVel > 0) {
                  this.m_zAngVel = 0;
               }
            }

            this.m_zAngVel += this.m_zSteeringAng;
            this.applyPlayerSlidingForce(var1);
         } else {
            this.m_zAngVel = this.m_zAngVel * (TheGame.this.exp_Array[-(-var1 * 8) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 8) >> 8)] - TheGame.this.exp_Array[-(-var1 * 8) >> 8]) * (-(-var1 * 8) - (-(-var1 * 8) & -256)) >> 8)) >> 16;
         }

      }

      int calcPlayerMaxTurningAngle() {
         int var1 = Math.abs(this.nForwardDot);
         int var2 = 1104000 - var1 * 44 >> 8;
         var2 = var2 * this.m_nHandling >> 6;
         if (var2 > 16384) {
            var2 = 16384;
         }

         int var3 = this.nForwardDot >> 2;
         if (var3 > 256) {
            var3 = 256;
         } else if (var3 < -256) {
            var3 = -256;
         }

         var2 = var2 * var3 >> 8;
         if (this.m_nAccelerateTimer <= 0) {
            var2 += var2 * (-this.m_nAccelerateTimer >> 4) >> 7;
            if (var2 > 16384) {
               var2 = 16384;
            }
         }

         return var2;
      }

      void applyPlayerSlidingForce(int var1) {
         int var2 = this.nSideDot * 80 >> 8;
         if (var2 > 16384) {
            var2 = 16384;
         } else if (var2 < -16384) {
            var2 = -16384;
         }

         var2 = var2 * (1024 - this.m_nSlideOutDisableForCollision) >> 10;
         this.m_zAngVel -= var2 * var1 * 25 >> 11;
      }

      void CopAISteering(int var1) {
         int var2 = TheGame.this.g_nParam1;
         int var3 = TheGame.this.g_nParam2;
         int var4 = TheGame.this.g_nParam3;
         int var5;
         int var6;
         if (this.m_nState == 7) {
            var5 = TheGame.this.players[0].m_xPos - this.m_xPos;
            var6 = TheGame.this.players[0].m_yPos - this.m_yPos;
            this.nAngleAI = (TheGame.this.arctan2(-var5 << 16, -var6 << 16) & 16777215) >> 8;
            var2 = this.nAngleAI;
            var3 = this.nAngleAI - (this.m_zAng >> 8);
            if (var3 > 32768) {
               var2 -= 65536;
            } else if (var3 < -32768) {
               var2 += 65536;
            }

            this.m_nOverTackingAngleOffset = 0;
         } else if (!this.m_bReverseTrack) {
            if (this.m_nState != 0 && this.m_nState != 6) {
               if (this.m_nState == 5) {
                  var4 = TheGame.this.players[0].m_nCrossAI - this.m_nCrossAI << 1;
                  if (var4 > 4000) {
                     var4 = 4000;
                  } else if (var4 < -4000) {
                     var4 = -4000;
                  }

                  var2 += var4;
                  this.m_nOverTackingAngleOffset = var4;
               }
            } else {
               var5 = this.getDistanceToPlayer();
               if (var5 < 128) {
                  if (TheGame.this.players[0].m_nCrossAI - this.m_nCrossAI > 0) {
                     var4 = -1500;
                  } else {
                     var4 = 1500;
                  }

                  var2 += var4;
               } else if (var5 < -90) {
                  if (this.m_nState == 6) {
                     var4 = TheGame.this.players[0].m_nCrossAI - this.m_nCrossAI << 1;
                     var6 = 4000 + (var5 >> 2);
                     if (var6 < 0) {
                        var6 = 0;
                     }

                     if (var4 > var6) {
                        var4 = var6;
                     } else if (var4 < -var6) {
                        var4 = -var6;
                     }

                     var2 += var4;
                     this.m_nOverTackingAngleOffset = var4;
                  } else {
                     var4 = TheGame.this.players[0].m_nCrossAI - this.m_nCrossAI;
                     if (var4 > 4000) {
                        var4 = 4000;
                     } else if (var4 < -4000) {
                        var4 = -4000;
                     }

                     var2 += var4;
                  }
               }
            }
         }

         TheGame.this.g_nParam1 = var2;
         TheGame.this.g_nParam2 = var3;
         TheGame.this.g_nParam3 = var4;
      }

      int getDistanceToPlayer() {
         int var1 = TheGame.this.players[0].m_nAiNode - this.m_nAiNode;
         if (var1 < -this.m_aiSpline.length >> 1) {
            var1 += this.m_aiSpline.length;
         } else if (var1 > this.m_aiSpline.length >> 1) {
            var1 -= this.m_aiSpline.length;
         }

         var1 = (var1 << 8) + (TheGame.this.players[0].m_nCurrentSplineDistance - this.m_nCurrentSplineDistance >> 6);
         return var1;
      }

      void AIOvertaking(int var1) {
         int var2 = TheGame.this.g_nParam1;
         if (this.m_nSortedPos < TheGame.this.sortedCarsList.length - 1) {
            int var3 = this.getDistanceToClosestCarInFront();
            if (TheGame.this.sortedCarsList[this.m_nSortedPos + 1].m_nCopNum == 0 && !TheGame.this.sortedCarsList[this.m_nSortedPos + 1].m_bTraffic) {
               if (var3 < 160) {
                  if (TheGame.this.sortedCarsList[this.m_nSortedPos + 1].m_nCrossAI - this.m_nCrossAI > 0) {
                     var2 = -1100;
                  } else {
                     var2 = 1100;
                  }

                  if (var3 < 90) {
                     var2 >>= 1;
                  }
               }
            } else if (var3 < 200) {
               int var4 = TheGame.this.sortedCarsList[this.m_nSortedPos + 1].m_nCrossAI - this.m_nCrossAI;
               short var5 = 3000;
               if (this.m_velocityLastFrame - TheGame.this.sortedCarsList[this.m_nSortedPos + 1].m_velocityLastFrame > 5000 || TheGame.this.sortedCarsList[this.m_nSortedPos + 1].m_velocityLastFrame < 1000) {
                  var5 = 5000;
               }

               if (var4 > 0) {
                  var2 = -var5 + var4;
                  if (var2 > 0) {
                     var2 = 0;
                  }
               } else {
                  var2 = var5 + var4;
                  if (var2 < 0) {
                     var2 = 0;
                  }
               }

               if (var2 > 5000) {
                  var2 = 5000;
               } else if (var2 < -5000) {
                  var2 = -5000;
               }

               this.m_nOverTackingAngleOffset = var2;
            }
         }

         TheGame.this.g_nParam1 = var2;
      }

      int getDistanceToClosestCarInFront() {
         int var1 = TheGame.this.sortedCarsList[this.m_nSortedPos + 1].m_nAiNode - this.m_nAiNode;
         if (var1 < 0) {
            var1 += this.m_aiSpline.length;
         }

         var1 = (var1 << 8) + (TheGame.this.sortedCarsList[this.m_nSortedPos + 1].m_nCurrentSplineDistance - this.m_nCurrentSplineDistance >> 6);
         return var1;
      }

      void AISteering(int var1) {
         if (this.m_nLastWheelCount >= 2 && this.m_nState != 4) {
            int var2 = this.m_zAng;
            int var3 = this.nAngleAI;
            if (this.m_nCopNum != 0) {
               if (this.m_nState == 3) {
                  var3 += 16384;
               } else if (this.m_nState == 2) {
                  var3 -= 16384;
               }
            }

            this.m_zAng &= 16777215;
            int var4 = this.nAngleAI - (this.m_zAng >> 8);
            if (var4 > 32768) {
               var3 -= 65536;
            } else if (var4 < -32768) {
               var3 += 65536;
            }

            int var5 = this.calcAIMaxTurningAngle(var4);
            int var6 = this.nCrossAI << 2;
            if (var6 > var5) {
               var6 = var5;
            } else if (var6 < -var5) {
               var6 = -var5;
            }

            if (this.m_nReverseTimer > 0) {
               var6 = -var6;
            }

            var3 -= var6;
            byte var7 = 0;
            int var9;
            if (this.m_nCopNum != 0) {
               TheGame.this.g_nParam1 = var3;
               TheGame.this.g_nParam2 = var4;
               TheGame.this.g_nParam3 = var7;
               this.CopAISteering(var1);
               var3 = TheGame.this.g_nParam1;
               var4 = TheGame.this.g_nParam2;
               var9 = TheGame.this.g_nParam3;
            } else {
               TheGame.this.g_nParam1 = var7;
               this.AIOvertaking(var1);
               var9 = TheGame.this.g_nParam1;
            }

            var3 += this.getOverTakingOffset(var1, var9);
            int var8 = var3 - (this.m_zAng >> 8);
            if (var8 > var5) {
               var8 = var5;
            } else if (var8 < -var5) {
               var8 = -var5;
            }

            if (this.m_nCopNum != 0 && this.m_nState != 8 && Math.abs(this.nSideDot) * 3 > Math.abs(this.nForwardDot) << 1) {
               this.m_nState = 8;
               this.m_nStateTime = 0;
            }

            if (this.m_nCopNum != 0 && this.m_nState == 8) {
               if (this.m_nStateTime > 2000) {
                  this.m_nState = 0;
                  this.m_nStateTime = 0;
               }

               this.m_zAngVel = this.m_zAngVel * (TheGame.this.exp_Array[-(-var1 * 8) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 8) >> 8)] - TheGame.this.exp_Array[-(-var1 * 8) >> 8]) * (-(-var1 * 8) - (-(-var1 * 8) & -256)) >> 8)) >> 16;
            } else {
               this.m_zAngVel -= var8;
               this.m_zAngVel = this.m_zAngVel * (TheGame.this.exp_Array[-(-var1 * 75) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 75) >> 8)] - TheGame.this.exp_Array[-(-var1 * 75) >> 8]) * (-(-var1 * 75) - (-(-var1 * 75) & -256)) >> 8)) >> 16;
               if (this.m_zAngVel > 0) {
                  this.m_zAngVel -= var1 * 4;
                  if (this.m_zAngVel < 0) {
                     this.m_zAngVel = 0;
                  }
               } else {
                  this.m_zAngVel += var1 * 4;
                  if (this.m_zAngVel > 0) {
                     this.m_zAngVel = 0;
                  }
               }

               this.m_zAngVel += var8;
            }

            this.applyAISlidingForce(var1);
         } else {
            this.m_zAngVel = this.m_zAngVel * (TheGame.this.exp_Array[-(-var1 * 8) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 8) >> 8)] - TheGame.this.exp_Array[-(-var1 * 8) >> 8]) * (-(-var1 * 8) - (-(-var1 * 8) & -256)) >> 8)) >> 16;
         }

      }

      int getOverTakingOffset(int var1, int var2) {
         if (this.m_nOverTackingAngleOffset < var2) {
            this.m_nOverTackingAngleOffset += var1 << 1;
            if (this.m_nOverTackingAngleOffset > var2) {
               this.m_nOverTackingAngleOffset = var2;
            }
         } else if (this.m_nOverTackingAngleOffset > var2) {
            this.m_nOverTackingAngleOffset -= var1 << 1;
            if (this.m_nOverTackingAngleOffset < var2) {
               this.m_nOverTackingAngleOffset = var2;
            }
         }

         return this.m_nOverTackingAngleOffset;
      }

      int calcAIMaxTurningAngle(int var1) {
         int var2 = Math.abs(this.nForwardDot);
         int var3 = 1104000 - var2 * 44 >> 8;
         if (var3 > 16384) {
            var3 = 16384;
         }

         if (this.m_bBreaking) {
            var3 += var3 >> 9;
         }

         if (var3 > 16384) {
            var3 = 16384;
         }

         if (this.m_nCopNum != 0 && Math.abs(var1) > 32768) {
            var3 *= 3;
            if (var3 > 16384) {
               var3 = 16384;
            }
         }

         int var4 = Math.abs(this.nForwardDot >> 2);
         if (var4 > 256) {
            var4 = 256;
         }

         var3 = var3 * var4 >> 8;
         return var3;
      }

      void applyAISlidingForce(int var1) {
         int var2 = this.nSideDot * 80 >> 8;
         if (var2 > 16384) {
            var2 = 16384;
         } else if (var2 < -16384) {
            var2 = -16384;
         }

         var2 = var2 * (1024 - this.m_nSlideOutDisableForCollision) >> 10;
         this.m_zAngVel -= var2 * var1 * 25 >> 11;
      }

      void AIAcceleration_NotBraking_Cops(int var1, int var2) {
         var2 += this.m_nTopSpeed >> 8;
         int var3 = this.m_nAcceleration;
         var3 += 80;
         if (Math.abs(TheGame.this.players[0].m_nAverageSplineMovementSpeed) < 1000 && Math.abs(this.m_nPlayerDist) < 2) {
            this.m_nState = 7;
            if (this.m_velocityLastFrame > TheGame.this.players[0].m_velocityLastFrame + 5000) {
               this.m_bBreaking = true;
            }
         }

         if (this.m_nState == 7) {
            if (Math.abs(TheGame.this.players[0].m_nAverageSplineMovementSpeed) > 4000) {
               this.m_nState = 0;
            }
         } else if (this.m_nState == 1) {
            if (this.m_nPlayerDist < 8) {
               if (TheGame.this.system_GetRandom() >= 0) {
                  this.m_nState = 2;
               } else {
                  this.m_nState = 3;
               }

               this.m_nStateTime = 0;
            }

            var2 = (this.m_nTopSpeed >> 1) + (this.m_nTopSpeed >> 2);
         } else if (this.m_nPlayerDist < -1 && this.m_nState != 6) {
            this.m_bReverseTrack = !this.m_bReverseTrack;
         } else if (!this.m_bReverseTrack && (this.m_nState == 6 || this.m_nState == 0 || this.m_nState == 5)) {
            int var4 = TheGame.this.players[0].m_nAiNode - this.m_nAiNode;
            if (var4 < -this.m_aiSpline.length >> 1) {
               var4 += this.m_aiSpline.length;
            } else if (var4 > this.m_aiSpline.length >> 1) {
               var4 -= this.m_aiSpline.length;
            }

            var4 = (var4 << 8) + (TheGame.this.players[0].m_nCurrentSplineDistance - this.m_nCurrentSplineDistance >> 6);
            if (var4 > -32) {
               this.nAngleAIDiff >>= 2;
            }

            if (var4 > 32) {
               int var10000 = var2 + (this.m_nTopSpeed >> 4);
            }

            if (this.m_nState == 6) {
               this.nAngleAIDiff >>= 2;
               var3 += 120;
               if (var4 > -250) {
                  this.m_nState = 0;
               } else if (this.m_velocityLastFrame > TheGame.this.players[0].m_velocityLastFrame - 3000) {
                  this.m_bBreaking = true;
               }
            } else if (this.m_nState == 5) {
               this.nAngleAIDiff = 0;
               if (var4 > -74) {
                  this.m_nState = 0;
               } else if (Math.abs(TheGame.this.players[0].m_nCrossAI - this.m_nCrossAI) < 200 || this.m_velocityLastFrame > TheGame.this.players[0].m_velocityLastFrame - 200) {
                  this.m_bBreaking = true;
               }
            } else {
               if (var4 < 1024 && this.m_velocityLastFrame > TheGame.this.players[0].m_velocityLastFrame + 2000) {
                  this.m_bBreaking = true;
               }

               if (var4 < -120) {
                  this.m_nState = 5;
               }
            }
         }

      }

      void AIAcceleration_NotBraking(int var1) {
         this.m_nReverseTimer -= var1;
         if (this.m_nReverseTimer < -4000) {
            this.m_nReverseTimer = -4000;
            if (this.m_nAverageSpeed < this.m_nTopSpeed >> 5) {
               this.m_nReverseTimer = 1500;
               if (this.m_nCopNum != 0) {
                  this.m_nReverseTimer += 1500;
               }
            }
         }

         int var2;
         if (this.m_nReverseTimer <= 0 || (this.m_nCopNum != 0 || this.m_bTraffic) && this.m_nPlayerDist <= 2) {
            if (this.nForwardDot > -64) {
               var2 = this.m_nTopSpeed;
               if (this.m_bTraffic) {
                  var2 >>= 3;
               } else {
                  var2 += (this.m_nPlayerDist - 1) * 512;
                  if (var2 < this.m_nTopSpeed - (this.m_nTopSpeed >> 3)) {
                     var2 = this.m_nTopSpeed - (this.m_nTopSpeed >> 3);
                  } else if (var2 > this.m_nTopSpeed + (this.m_nTopSpeed >> 3)) {
                     var2 = this.m_nTopSpeed + (this.m_nTopSpeed >> 3);
                  }
               }

               if (this.m_nCopNum == 0 && this.m_nID != -5) {
                  var2 -= (this.m_nID - 1) * (this.m_nTopSpeed >> 3);
               } else {
                  this.AIAcceleration_NotBraking_Cops(var1, var2);
               }

               if (!this.m_bBreaking && this.nForwardDot * (Math.abs(this.nAngleAIDiff) >> 8) <= 358400) {
                  int var3 = this.m_nAcceleration;
                  int var4 = this.nForwardDot;
                  var4 -= var2;
                  var4 = var4 * (TheGame.this.exp_Array[-(-(var1 * var3) >> 8) >> 8] + ((TheGame.this.exp_Array[1 + (-(-(var1 * var3) >> 8) >> 8)] - TheGame.this.exp_Array[-(-(var1 * var3) >> 8) >> 8]) * (-(-(var1 * var3) >> 8) - (-(-(var1 * var3) >> 8) & -256)) >> 8)) >> 16;
                  var4 += var2;
                  this.m_xVel += this.xForwad * (var4 - this.nForwardDot) >> 14;
                  this.m_yVel += this.yForwad * (var4 - this.nForwardDot) >> 14;
               } else {
                  this.m_bBreaking = true;
               }
            } else {
               this.m_bBreaking = true;
            }
         } else {
            var2 = this.nForwardDot;
            var2 += 15000;
            var2 = var2 * (TheGame.this.exp_Array[-(-var1 >> 1) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 >> 1) >> 8)] - TheGame.this.exp_Array[-(-var1 >> 1) >> 8]) * (-(-var1 >> 1) - (-(-var1 >> 1) & -256)) >> 8)) >> 16;
            var2 -= 15000;
            this.m_xVel += this.xForwad * (var2 - this.nForwardDot) >> 14;
            this.m_yVel += this.yForwad * (var2 - this.nForwardDot) >> 14;
         }

      }

      void AIAcceleration_Brake(int var1) {
         byte var2 = 4;
         if (this.m_nCopNum != 0) {
            var2 = 6;
         }

         int var3;
         if (this.nForwardDot > 0) {
            var3 = var1 * var2;
            if (var3 > this.nForwardDot) {
               var3 = this.nForwardDot;
            }

            this.m_xVel -= this.xForwad * var3 >> 14;
            this.m_yVel -= this.yForwad * var3 >> 14;
         } else if (this.nForwardDot < 0) {
            var3 = var1 * var2;
            if (var3 > -this.nForwardDot) {
               var3 = -this.nForwardDot;
            }

            this.m_xVel += this.xForwad * var3 >> 14;
            this.m_yVel += this.yForwad * var3 >> 14;
         }

      }

      void AIAcceleration(int var1) {
         this.m_bBreaking = this.bBackOffFromOverTacking;
         if (this.m_nState == 2 || this.m_nState == 3) {
            if (this.m_nStateTime > 3000) {
               this.m_nState = 0;
            }

            this.m_bBreaking = true;
         }

         if (this.m_nState == 4 || this.m_nState == 9) {
            this.m_bBreaking = true;
         }

         if (this.m_bReverseTrack) {
            this.m_nPlayerDist = -TheGame.this.players[0].m_nAiNode + (TheGame.this.players[0].m_nLap + 1) * this.m_aiSpline.length - -this.m_nAiNode - (this.m_nLap + 1) * this.m_aiSpline.length;
         } else {
            this.m_nPlayerDist = TheGame.this.players[0].m_nAiNode + TheGame.this.players[0].m_nLap * this.m_aiSpline.length - this.m_nAiNode - this.m_nLap * this.m_aiSpline.length;
         }

         if (!this.m_bBreaking) {
            this.AIAcceleration_NotBraking(var1);
         }

         if (this.m_bBreaking) {
            this.AIAcceleration_Brake(var1);
         }

         int var2 = this.m_xVel * this.xRight + this.m_yVel * this.yRight >> 14;
         int var3;
         if (var2 > 0) {
            var3 = var1 * this.m_nHandling >> 3;
            if (var3 > var2) {
               var3 = var2;
            }

            this.m_xVel -= this.xRight * var3 >> 14;
            this.m_yVel -= this.yRight * var3 >> 14;
         } else {
            var3 = var1 * this.m_nHandling >> 3;
            if (var3 > -var2) {
               var3 = -var2;
            }

            this.m_xVel += this.xRight * var3 >> 14;
            this.m_yVel += this.yRight * var3 >> 14;
         }

      }

      void InitPhysicsVariables() {
         this.xOldVel = this.m_xVel;
         this.yOldVel = this.m_yVel;
         this.xForwad = -(TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (this.m_zAng >> 8 >> 8) & 255] - TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255]) * ((this.m_zAng >> 8) - (this.m_zAng >> 8 & -256)) >> 8));
         this.yForwad = TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((this.m_zAng >> 8) + 49152 >> 8) & 255] - TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.m_zAng >> 8) + 49152 - ((this.m_zAng >> 8) + 49152 & -256)) >> 8);
         this.xRight2 = this.yForwad;
         this.yRight2 = -this.xForwad;
         this.nForwardDot = this.m_xVel * this.xForwad + this.m_yVel * this.yForwad >> 14;
         this.nSideDot = this.m_xVel * this.xRight2 + this.m_yVel * this.yRight2 >> 14;
         this.nFrontWheelAngle = this.m_zAng + this.m_zSteeringAng * 8 >> 8;
         this.xRight = TheGame.this.sin_Array[this.nFrontWheelAngle + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (this.nFrontWheelAngle + 49152 >> 8) & 255] - TheGame.this.sin_Array[this.nFrontWheelAngle + 49152 >> 8 & 255]) * (this.nFrontWheelAngle + 49152 - (this.nFrontWheelAngle + 49152 & -256)) >> 8);
         this.yRight = TheGame.this.sin_Array[this.nFrontWheelAngle >> 8 & 255] + ((TheGame.this.sin_Array[1 + (this.nFrontWheelAngle >> 8) & 255] - TheGame.this.sin_Array[this.nFrontWheelAngle >> 8 & 255]) * (this.nFrontWheelAngle - (this.nFrontWheelAngle & -256)) >> 8);
         this.bBackOffFromOverTacking = false;
      }

      void UpdatePhysics_RaceSpecifics(int var1, int var2) {
         int var3;
         int var4;
         if (TheGame.this.g_race.m_type == 1) {
            if (this.m_nCurCheckpoint < TheGame.this.track_checkpoints.length) {
               var3 = (TheGame.this.track_checkpoints[this.m_nCurCheckpoint][0] << 4) - this.m_xPos;
               var4 = (TheGame.this.track_checkpoints[this.m_nCurCheckpoint][1] << 4) - this.m_yPos;
               if (Math.abs(var3) + Math.abs(var4) - (Math.min(Math.abs(var3), Math.abs(var4)) >> 1) < 6400) {
                  ++this.m_nCurCheckpoint;
                  this.m_nCheckpointTime = TheGame.this.game_Time;
                  if (this.m_bPlayer && this.m_nCurCheckpoint < TheGame.this.track_checkpoints.length) {
                     TheGame.this.game_popupText(TheGame.this.g_Text[184], false);
                  }
               }
            }
         } else if (this.m_bPlayer && (TheGame.this.g_race.m_type == 0 || TheGame.this.g_race.m_type == 2)) {
            this.HandleHeatRating();
         } else if (TheGame.this.g_race.m_type == 3) {
            if (this.m_nCurCheckpoint < TheGame.this.track_checkpoints.length) {
               var3 = (TheGame.this.track_checkpoints[this.m_nCurCheckpoint][0] << 4) - this.m_xPos;
               var4 = (TheGame.this.track_checkpoints[this.m_nCurCheckpoint][1] << 4) - this.m_yPos;
               if (Math.abs(var3) + Math.abs(var4) - (Math.min(Math.abs(var3), Math.abs(var4)) >> 1) < 6400) {
                  ++this.m_nCurCheckpoint;
                  this.m_nCheckpointTime = TheGame.this.game_Time;
                  if (this.m_bPlayer) {
                     TheGame.this.showFlashEffect = true;
                     if (this.GetMPH() > TheGame.this.g_race.m_nSpeedLimit) {
                        this.m_nSpeedingScore += this.GetMPH() - TheGame.this.g_missions[TheGame.this.g_race.m_nMissionID].m_nreqChkpSpeed;
                        if (this.m_nCurCheckpoint < TheGame.this.track_checkpoints.length) {
                           TheGame.this.game_popupText("" + (this.GetMPH() - TheGame.this.g_missions[TheGame.this.g_race.m_nMissionID].m_nreqChkpSpeed), false);
                        }
                     } else if (this.m_nCurCheckpoint < TheGame.this.track_checkpoints.length) {
                        TheGame.this.game_popupText(TheGame.this.g_Text[192], false);
                     }
                  }
               }
            }
         } else if (TheGame.this.g_race.m_type == 4 && this.m_bPlayer && TheGame.this.game_state != 5) {
            var3 = 10000;

            for(var4 = 1; var4 < TheGame.this.g_race.m_numRacers; ++var4) {
               if (TheGame.this.players[var4].m_nPlayerDist < var3) {
                  var3 = TheGame.this.players[var4].m_nPlayerDist;
               }
            }

            if (var3 > TheGame.this.g_missions[TheGame.this.g_race.m_nMissionID].m_nreqScore) {
               TheGame.this.game_EndRace();
            }
         }

      }

      void UpdatePhysics_CarsSplineDirection() {
         if (this.m_bGoingBackward) {
            this.nxAI = -this.nxAI;
            this.nyAI = -this.nyAI;
            this.nCrossAI = -this.nCrossAI;
            this.nAngleAI += 32768;
            this.m_nCrossAI = -this.m_nCrossAI;
            this.m_nAverageSplineMovementSpeed = this.m_nAverageSplineMovementSpeed * 200 + (this.m_xVel * this.nxAI + this.m_yVel * this.nyAI >> 10) * 56;
            this.m_nAverageSplineMovementSpeed >>= 8;
            if (this.m_nAverageSplineMovementSpeed > 64) {
               this.m_bGoingBackward = false;
            }
         } else {
            this.m_nAverageSplineMovementSpeed = this.m_nAverageSplineMovementSpeed * 200 + (this.m_xVel * this.nxAI + this.m_yVel * this.nyAI >> 10) * 56;
            this.m_nAverageSplineMovementSpeed >>= 8;
            if (this.m_nAverageSplineMovementSpeed < -64) {
               this.m_bGoingBackward = true;
            }
         }

         if (this.m_bReverseTrack) {
            this.nxAI = -this.nxAI;
            this.nyAI = -this.nyAI;
            this.nCrossAI = -this.nCrossAI;
            this.nAngleAI += 32768;
         }

         this.nAngleAI &= 65535;
      }

      void UpdatePhysics_SortCarsOnSpline() {
         if (this.m_bTraffic) {
            this.setTrafficSortDistance();
         } else {
            this.m_nSortDistance = this.m_nAiNode - TheGame.this.players[0].m_nAiNode;
         }

         if (this.m_nSortDistance > this.m_aiSpline.length >> 1) {
            this.m_nSortDistance -= this.m_aiSpline.length;
         } else if (this.m_nSortDistance < -this.m_aiSpline.length >> 1) {
            this.m_nSortDistance += this.m_aiSpline.length;
         }

      }

      void setTrafficSortDistance() {
         int var1 = this.m_aiSpline[this.m_nAiNode][4];
         if (var1 < 0) {
            var1 = -var1 - 1;
         }

         this.m_nSortDistance = var1 - TheGame.this.players[0].m_nAiNode;
         short var2 = TheGame.this.track_splines[0][var1][2];
         int var3 = this.m_xPos - (TheGame.this.track_splines[0][var1][0] << 4);
         int var4 = this.m_yPos - (TheGame.this.track_splines[0][var1][1] << 4);
         int var5 = -TheGame.this.sin_Array[-var2 >> 8 & 255] >> 4;
         int var6 = TheGame.this.sin_Array[(-var2 >> 8) + 192 & 255] >> 4;
         this.m_nCrossAI = var3 * var5 - var4 * var6 >> 10;
         this.m_nCurrentSplineDistance = 0;
      }

      void UpdatePhysics_Other() {
         int var1 = this.m_xVel - this.xOldVel;
         int var2 = this.m_yVel - this.yOldVel;
         int var3 = var1 * this.xForwad + var2 * this.yForwad >> 14;
         this.m_xAngVel += var3 << 1;
         var3 = var1 * this.xRight + var2 * this.yRight >> 14;
         this.m_yAngVel -= var3 >> 1;
         this.m_ca.m_lightAngle = this.m_zAng & 16777215;
      }

      public void UpdatePhysics(int var1, int var2) {
         this.InitPhysicsVariables();
         if (this.m_bDisabled) {
            this.m_nSortDistance = Integer.MAX_VALUE;
         } else {
            this.m_nStateTime += var1;
            this.m_nTimeNotVisable += var1;
            this.m_nTimeSinceSpawn += var1;
            this.m_ca.m_wheelAngle += this.nForwardDot;
            if (this.m_velocityMax < this.nForwardDot) {
               this.m_velocityMax = this.nForwardDot;
            }

            this.m_velocityLastFrame = this.nForwardDot;
            this.m_distanceMovedLastFrame = -((float)(this.nForwardDot * var1)) / 8388608.0F;
            if (this.GetMPH() > this.m_nHighestRaceSpeed) {
               this.m_nHighestRaceSpeed = this.GetMPH();
            }

            this.m_nAverageSpeed = this.m_nAverageSpeed * 224 + this.nForwardDot * 32 >> 8;
            this.UpdatePhysics_RaceSpecifics(var1, var2);
            this.UpdatePositionOnAiSpline(var1);
            this.UpdatePhysics_SortCarsOnSpline();
            this.UpdatePhysics_CarsSplineDirection();
            if (this.m_bPlayer) {
               this.PlayerSteering(var1);
            } else {
               this.AISteering(var1);
            }

            if (this.m_nLastWheelCount >= 2) {
               if (this.m_bPlayer) {
                  this.PlayerAcceleration(var1);
               } else {
                  this.AIAcceleration(var1);
               }
            }

            this.UpdatePhysics_Other();
            this.m_nSlideOutDisableForCollision -= var1;
            if (this.m_nSlideOutDisableForCollision < 0) {
               this.m_nSlideOutDisableForCollision = 0;
            }

         }
      }

      public void playEngineSound() {
      }

      public void RealizeNewTransform(int var1, int var2) {
         this.m_camDist = Math.abs(this.m_xPos - TheGame.this.game_posX) + Math.abs(this.m_yPos - TheGame.this.game_posY) + Math.abs(this.m_zPos - TheGame.this.game_posZ);
         this.m_xAng += this.m_xAngVel * var1;
         this.m_yAng += this.m_yAngVel * var1;
         this.m_zAng += this.m_zAngVel * var1;
         this.rotation.setIdentity();
         this.rotation.postTranslate((float)this.m_xPos * 0.00390625F, (float)this.m_zPos * 0.00390625F, (float)this.m_yPos * 0.00390625F);
         this.rotation.postRotate((float)this.m_zAng * 2.1457672E-5F, 0.0F, 1.0F, 0.0F);
         this.rotation.postRotate((float)this.m_xAng * 2.1457672E-5F, 1.0F, 0.0F, 0.0F);
         this.rotation.postRotate((float)this.m_yAng * 2.1457672E-5F, 0.0F, 0.0F, 1.0F);
         if (this.m_nDamagedLevel < 51200) {
            if (this.m_xAng > 2097152) {
               this.m_xAng = 2097152;
            } else if (this.m_xAng < -2097152) {
               this.m_xAng = -2097152;
            }

            if (this.m_yAng > 2097152) {
               this.m_yAng = 2097152;
            } else if (this.m_yAng < -2097152) {
               this.m_yAng = -2097152;
            }
         } else {
            this.m_xAng += 8388608;
            this.m_xAng &= 16777215;
            this.m_xAng -= 8388608;
            this.m_yAng += 8388608;
            this.m_yAng &= 16777215;
            this.m_yAng -= 8388608;
         }

      }

      public void CalculateSuspension(int var1, int var2, int var3, int var4, int var5) {
         if (var3 == 4) {
            var5 >>= 2;
            short var6 = 64;
            if (this.rotationArray[5] < 0.5F) {
               var6 = -200;
            }

            if (this.m_nLastAverageCount == 4 && var4 > 0) {
               int var7 = (this.zWheelForce[0] + this.zWheelForce[1] + this.zWheelForce[2] + this.zWheelForce[3] - 10) * var1 >> 6;
               int var8 = (var5 - this.m_zLastAveragePos) * var2 >> 6;
               int var9 = this.m_zVel;
               var9 -= var8;
               var9 = var9 * (TheGame.this.exp_Array[-(-var1 * var4 * 15) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * var4 * 15) >> 8)] - TheGame.this.exp_Array[-(-var1 * var4 * 15) >> 8]) * (-(-var1 * var4 * 15) - (-(-var1 * var4 * 15) & -256)) >> 8)) >> 16;
               var9 += var8;
               var7 += var9 - this.m_zVel;
               if (var7 > 0) {
                  this.m_zVel += var7;
                  int var10 = var8 + 400;
                  var10 += 1680 * var1 >> 10;
                  if (this.m_zVel > var10) {
                     this.m_zVel = var10;
                  }
               }

               if (this.m_zPos < var5 - var6) {
                  this.m_zPos = var5 - var6;
                  if (this.m_zVel < var8) {
                     this.m_zVel = var8;
                  }
               }
            } else if (this.m_zPos < var5 - var6) {
               this.m_zPos = var5 - var6;
            }
         }

         this.m_nLastAverageCount = var3;
         this.m_zLastAveragePos = var5;
         this.m_nLastWheelCount = var4;
         if (Math.abs(this.m_xAng) > 2097152) {
            this.m_xAngVel -= (this.zWheelForce[0] - this.zWheelForce[2] + (this.zWheelForce[1] - this.zWheelForce[3])) * var1 >> 3;
            if (Math.abs(this.m_yAng) < 2097152) {
               this.m_yAngVel -= (this.zWheelForce[0] - this.zWheelForce[1] + (this.zWheelForce[2] - this.zWheelForce[3])) * var1 >> 2;
            } else {
               this.m_yAngVel += (this.zWheelForce[0] - this.zWheelForce[1] + (this.zWheelForce[2] - this.zWheelForce[3])) * var1 >> 2;
            }
         } else {
            this.m_xAngVel += (this.zWheelForce[0] - this.zWheelForce[2] + (this.zWheelForce[1] - this.zWheelForce[3])) * var1 >> 3;
            if (Math.abs(this.m_yAng) > 2097152) {
               this.m_yAngVel -= (this.zWheelForce[0] - this.zWheelForce[1] + (this.zWheelForce[2] - this.zWheelForce[3])) * var1 >> 2;
            } else {
               this.m_yAngVel += (this.zWheelForce[0] - this.zWheelForce[1] + (this.zWheelForce[2] - this.zWheelForce[3])) * var1 >> 2;
            }
         }

         if (this.rotationArray[5] > 0.0F) {
            this.m_xAngVel = this.m_xAngVel * (TheGame.this.exp_Array[-(-var1 * 12 * var4) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 12 * var4) >> 8)] - TheGame.this.exp_Array[-(-var1 * 12 * var4) >> 8]) * (-(-var1 * 12 * var4) - (-(-var1 * 12 * var4) & -256)) >> 8)) >> 16;
            this.m_yAngVel = this.m_yAngVel * (TheGame.this.exp_Array[-(-var1 * 5 * var4) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 5 * var4) >> 8)] - TheGame.this.exp_Array[-(-var1 * 5 * var4) >> 8]) * (-(-var1 * 5 * var4) - (-(-var1 * 5 * var4) & -256)) >> 8)) >> 16;
         } else {
            this.m_xAngVel = this.m_xAngVel * (TheGame.this.exp_Array[-(-var1 * 20 * var4) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 20 * var4) >> 8)] - TheGame.this.exp_Array[-(-var1 * 20 * var4) >> 8]) * (-(-var1 * 20 * var4) - (-(-var1 * 20 * var4) & -256)) >> 8)) >> 16;
            this.m_yAngVel = this.m_yAngVel * (TheGame.this.exp_Array[-(-var1 * 10 * var4) >> 8] + ((TheGame.this.exp_Array[1 + (-(-var1 * 10 * var4) >> 8)] - TheGame.this.exp_Array[-(-var1 * 10 * var4) >> 8]) * (-(-var1 * 10 * var4) - (-(-var1 * 10 * var4) & -256)) >> 8)) >> 16;
         }

      }

      void UpdatePlayerTrackCollision(int var1) {
         if (this.m_bPlayer) {
            int var2 = -(TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (this.m_zAng >> 8 >> 8) & 255] - TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255]) * ((this.m_zAng >> 8) - (this.m_zAng >> 8 & -256)) >> 8));
            int var3 = TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((this.m_zAng >> 8) + 49152 >> 8) & 255] - TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.m_zAng >> 8) + 49152 - ((this.m_zAng >> 8) + 49152 & -256)) >> 8);
            int var5 = -var2;
            int var6 = 0;
            this.m_tempColResult.x = this.m_colResult[0].x;
            this.m_tempColResult.y = this.m_colResult[0].y;
            this.m_tempColResult.z = this.m_colResult[0].z;
            this.m_tempColResult.nTriangleID = this.m_colResult[0].nTriangleID;
            TheGame.this.groundCollision.TestPointForCollision(this.m_colResult[1].x, this.m_colResult[1].y, this.m_tempColResult);
            if (this.m_tempColResult.x != this.m_colResult[1].x || this.m_tempColResult.y != this.m_colResult[1].y) {
               this.m_xVel -= var2 * 10 * var1 >> 14;
               this.m_yVel -= var3 * 10 * var1 >> 14;
               var6 |= 1;
            }

            this.m_tempColResult.x = this.m_colResult[2].x;
            this.m_tempColResult.y = this.m_colResult[2].y;
            this.m_tempColResult.z = this.m_colResult[2].z;
            this.m_tempColResult.nTriangleID = this.m_colResult[2].nTriangleID;
            TheGame.this.groundCollision.TestPointForCollision(this.m_colResult[3].x, this.m_colResult[3].y, this.m_tempColResult);
            if (this.m_tempColResult.x != this.m_colResult[3].x || this.m_tempColResult.y != this.m_colResult[3].y) {
               this.m_xVel += var2 * 10 * var1 >> 14;
               this.m_yVel += var3 * 10 * var1 >> 14;
               var6 |= 2;
            }

            this.m_tempColResult.x = this.m_colResult[0].x;
            this.m_tempColResult.y = this.m_colResult[0].y;
            this.m_tempColResult.z = this.m_colResult[0].z;
            this.m_tempColResult.nTriangleID = this.m_colResult[0].nTriangleID;
            TheGame.this.groundCollision.TestPointForCollision(this.m_colResult[2].x, this.m_colResult[2].y, this.m_tempColResult);
            if (this.m_tempColResult.x != this.m_colResult[2].x || this.m_tempColResult.y != this.m_colResult[2].y) {
               this.m_xVel += var3 * 10 * var1 >> 14;
               this.m_yVel += var5 * 10 * var1 >> 14;
               var6 |= 4;
            }

            this.m_tempColResult.x = this.m_colResult[1].x;
            this.m_tempColResult.y = this.m_colResult[1].y;
            this.m_tempColResult.z = this.m_colResult[1].z;
            this.m_tempColResult.nTriangleID = this.m_colResult[1].nTriangleID;
            TheGame.this.groundCollision.TestPointForCollision(this.m_colResult[3].x, this.m_colResult[3].y, this.m_tempColResult);
            if (this.m_tempColResult.x != this.m_colResult[3].x || this.m_tempColResult.y != this.m_colResult[3].y) {
               this.m_xVel -= var3 * 10 * var1 >> 14;
               this.m_yVel -= var5 * 10 * var1 >> 14;
               var6 |= 8;
            }

            if (var6 != 0) {
               TheGame.this.g_nTimePlayerStuck += var1;
               if (TheGame.this.g_nTimePlayerStuck > 4000) {
                  this.m_xPos = this.m_aiSpline[this.m_nAiNode][0] << 4;
                  this.m_yPos = -this.m_aiSpline[this.m_nAiNode][1] << 4;
                  this.InitCollisionToSplinePos(this.m_nAiNode);
               }
            } else {
               TheGame.this.g_nTimePlayerStuck = 0;
            }
         }

      }

      public void UpdateTrackCollision(int var1, int var2) {
         if (!this.m_bDisabled) {
            boolean var3 = false;
            int var4 = this.m_xVel;
            int var5 = this.m_yVel;
            if (this.m_nTimeNotVisable > 3000) {
               this.trackCollision_doCheapUpdate(var1);
            } else {
               if (this.m_bCheapUpdate) {
                  this.m_bCheapUpdate = false;
                  var3 = true;
               }

               this.m_zVel -= 3584 * var1 >> 10;
               this.m_zPos += this.m_zVel * var1 >> 10;
               this.rotation.get(this.rotationArray);
               int var6 = (int)(this.rotationArray[8] * 65536.0F);
               int var7 = (int)(-this.rotationArray[10] * 65536.0F);
               int var8 = -((int)(this.rotationArray[6] * 65536.0F));
               int var9 = (int)(this.rotationArray[0] * 65536.0F);
               int var10 = (int)(-this.rotationArray[2] * 65536.0F);
               int var11 = (int)(this.rotationArray[4] * 65536.0F);
               int var12 = 70;
               if (this.rotationArray[5] < 0.0F) {
                  var12 = 70 + (int)(this.rotationArray[5] * -350.0F);
               }

               int var13 = 0;
               int var14 = 0;
               int var15 = 0;
               boolean var16 = false;
               boolean var17 = false;
               int var18 = 0;
               int var19 = 0;
               int var20 = 0;
               int var21 = this.m_xPos + (this.m_xVel * var1 >> 10);
               int var22 = this.m_yPos + (this.m_yVel * var1 >> 10);

               for(int var23 = 0; var23 < 4; ++var23) {
                  TheGame.this.g_nParam1 = var13;
                  TheGame.this.g_nParam2 = var14;
                  TheGame.this.g_nParam3 = var15;
                  this.trackCollision_setWheelPositions(var23, var12, var6, var7, var8, var9, var10, var11);
                  var13 = TheGame.this.g_nParam1;
                  var14 = TheGame.this.g_nParam2;
                  var15 = TheGame.this.g_nParam3;
                  this.zWheelForce[var23] = 0;
                  int var29 = var21 + var13;
                  int var30 = var22 + var14;
                  TheGame.this.groundCollision.TestPointForCollision(var29 << 8, -var30 << 8, this.m_colResult[var23]);
                  if (this.m_colResult[var23].nTriangleID != -1) {
                     int var24 = this.m_colResult[var23].z >> 8;
                     if (var15 < var24) {
                        ++var19;
                        if ((this.m_colResult[var23].nAttribute & '\ue000') != 0) {
                           var24 += 40;
                        }

                        this.zWheelForce[var23] = var24 - var15;
                        if ((this.m_colResult[var23].nAttribute & 0) != 0) {
                           this.zWheelForce[var23] += (TheGame.this.sin_Array[(this.m_xPos + this.m_yPos) * 1000 + 67108864 >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((this.m_xPos + this.m_yPos) * 1000 + 67108864 >> 8 >> 8) & 255] - TheGame.this.sin_Array[(this.m_xPos + this.m_yPos) * 1000 + 67108864 >> 8 >> 8 & 255]) * (((this.m_xPos + this.m_yPos) * 1000 + 67108864 >> 8) - ((this.m_xPos + this.m_yPos) * 1000 + 67108864 >> 8 & -256)) >> 8)) * 64 >> 14;
                        }
                     }

                     ++var20;
                     var18 += var24;
                     int var25 = (var29 << 8) - this.m_colResult[var23].x;
                     int var26 = (var30 << 8) + this.m_colResult[var23].y;
                     int var27 = (var25 >> 6) * var2 >> 8;
                     int var28 = (var26 >> 6) * var2 >> 8;
                     this.m_xVel -= var27;
                     this.m_yVel -= var28;
                     if ((var27 != 0 || var28 != 0) && this.m_velocityLastFrame > 1000) {
                        this.AddSparks(this.m_colResult[var23].x, -this.m_colResult[var23].y, this.m_zPos << 8);
                     }

                     this.trackCollision_AddAngularForce(var25, var26, var13, var14);
                  }

                  var21 = this.m_xPos + (this.m_xVel * var1 >> 10);
                  var22 = this.m_yPos + (this.m_yVel * var1 >> 10);
               }

               this.m_xPos = var21;
               this.m_yPos = var22;
               if (var3) {
                  this.m_xVel = var4;
                  this.m_yVel = var5;
               }

               this.CalculateSuspension(var1, var2, var20, var19, var18);
               this.rotation.setIdentity();
               this.rotation.postTranslate((float)this.m_xPos * 0.00390625F, (float)this.m_zPos * 0.00390625F, (float)this.m_yPos * 0.00390625F);
               this.rotation.postRotate((float)this.m_zAng * 2.1457672E-5F, 0.0F, 1.0F, 0.0F);
               this.rotation.postRotate((float)this.m_xAng * 2.1457672E-5F, 1.0F, 0.0F, 0.0F);
               this.rotation.postRotate((float)this.m_yAng * 2.1457672E-5F, 0.0F, 0.0F, 1.0F);
               this.UpdatePlayerTrackCollision(var1);
            }
         }
      }

      void trackCollision_setWheelPositions(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         int var9 = TheGame.this.g_nParam1;
         int var10 = TheGame.this.g_nParam2;
         int var11 = TheGame.this.g_nParam3;
         switch(var1) {
         case 0:
            var9 = var3 * 580 + var6 * 210 >> 16;
            var10 = var4 * 580 + var7 * 210 >> 16;
            var11 = var5 * 580 + var8 * 210 >> 16;
            break;
         case 1:
            var9 = var3 * 580 + var6 * -210 >> 16;
            var10 = var4 * 580 + var7 * -210 >> 16;
            var11 = var5 * 580 + var8 * -210 >> 16;
            break;
         case 2:
            var9 = var3 * -580 + var6 * 210 >> 16;
            var10 = var4 * -580 + var7 * 210 >> 16;
            var11 = var5 * -580 + var8 * 210 >> 16;
            break;
         case 3:
         default:
            var9 = var3 * -580 + var6 * -210 >> 16;
            var10 = var4 * -580 + var7 * -210 >> 16;
            var11 = var5 * -580 + var8 * -210 >> 16;
         }

         var11 += this.m_zPos - var2;
         TheGame.this.g_nParam1 = var9;
         TheGame.this.g_nParam2 = var10;
         TheGame.this.g_nParam3 = var11;
      }

      void trackCollision_AddAngularForce(int var1, int var2, int var3, int var4) {
         int var5 = (var2 >> 8) * var3 - (var1 >> 8) * var4 >> 6;
         if (var5 > 0) {
            this.m_nSlideOutDisableForCollision = 1024;
            if (this.m_nLastWheelCount >= 0 && this.m_zAngVel < var5) {
               this.m_zAngVel = var5;
            }
         } else if (var5 < 0) {
            this.m_nSlideOutDisableForCollision = 1024;
            if (this.m_nLastWheelCount >= 0 && this.m_zAngVel > var5) {
               this.m_zAngVel = var5;
            }
         }

      }

      void trackCollision_doCheapUpdate(int var1) {
         this.m_bCheapUpdate = true;
         this.m_zVel = 0;
         this.m_xPos += this.m_xVel * var1 >> 10;
         this.m_yPos += this.m_yVel * var1 >> 10;
         this.m_nLastAverageCount = 0;
         this.m_zLastAveragePos = 0;
         this.m_nLastWheelCount = 4;
         this.m_xAngVel = 0;
         this.m_yAngVel = 0;
         this.m_xAng = 0;
         this.m_yAng = 0;
         this.rotation.setIdentity();
         this.rotation.postTranslate((float)this.m_xPos * 0.00390625F, (float)this.m_zPos * 0.00390625F, (float)this.m_yPos * 0.00390625F);
         this.rotation.postRotate((float)this.m_zAng * 2.1457672E-5F, 0.0F, 1.0F, 0.0F);
         this.rotation.postRotate((float)this.m_xAng * 2.1457672E-5F, 1.0F, 0.0F, 0.0F);
         this.rotation.postRotate((float)this.m_yAng * 2.1457672E-5F, 0.0F, 0.0F, 1.0F);
      }

      void CalcCollisionPositionAndNormal(TheGame.Car var1, int var2, int var3) {
         int var6 = TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (this.m_zAng >> 8 >> 8) & 255] - TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255]) * ((this.m_zAng >> 8) - (this.m_zAng >> 8 & -256)) >> 8);
         int var7 = TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((this.m_zAng >> 8) + 49152 >> 8) & 255] - TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.m_zAng >> 8) + 49152 - ((this.m_zAng >> 8) + 49152 & -256)) >> 8);
         int var8 = TheGame.this.sin_Array[var1.m_zAng >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (var1.m_zAng >> 8 >> 8) & 255] - TheGame.this.sin_Array[var1.m_zAng >> 8 >> 8 & 255]) * ((var1.m_zAng >> 8) - (var1.m_zAng >> 8 & -256)) >> 8);
         int var9 = TheGame.this.sin_Array[(var1.m_zAng >> 8) + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((var1.m_zAng >> 8) + 49152 >> 8) & 255] - TheGame.this.sin_Array[(var1.m_zAng >> 8) + 49152 >> 8 & 255]) * ((var1.m_zAng >> 8) + 49152 - ((var1.m_zAng >> 8) + 49152 & -256)) >> 8);
         this.xNormal = 0;
         this.yNormal = 1;
         this.xColPos = 0;
         this.yColPos = 0;
         if (this.xDistB > this.yDistB && this.xDistB > this.xDistA && this.xDistB > this.yDistA || this.xDistB <= this.yDistB && this.yDistB > this.xDistA && this.yDistB > this.yDistA) {
            if (this.xDistB > this.yDistB) {
               this.calcCollisionB1(this, var1, var9, var8);
            } else {
               this.calcCollisionB2(this, var1, var9, var8);
            }
         } else if (this.xDistA > this.yDistA) {
            this.calcCollisionA1(this, var1, var7, var6);
         } else {
            this.calcCollisionA2(this, var1, var7, var6);
         }

      }

      void calcCollisionB1(TheGame.Car var1, TheGame.Car var2, int var3, int var4) {
         int var5 = this.nAngOffset1 + this.nAngOffset2 + this.xCollisionExpandB;
         short var6 = TheGame.this.sin_Array[(var1.m_zAng - var2.m_zAng >> 16 & 127) + 64];
         int var7 = var6 * 290 >> 14;
         if (this.xPosAInBSpace < 0) {
            var5 = -var5;
            var7 = -var7;
            this.xNormal = var3;
            this.yNormal = var4;
         } else {
            this.xNormal = -var3;
            this.yNormal = -var4;
         }

         this.xNormal = -this.xNormal;
         this.yNormal = -this.yNormal;
         this.xColPos = var1.m_xPos + (var3 * var5 - var4 * var7 >> 14);
         this.yColPos = var1.m_yPos + (var4 * var5 + var3 * var7 >> 14);
         this.nIntersection = this.xDistB;
         this.nCollisionExpand = this.xCollisionExpandB;
      }

      void calcCollisionB2(TheGame.Car var1, TheGame.Car var2, int var3, int var4) {
         int var5 = this.nAngOffset1 + this.nAngOffset3 + this.yCollisionExpandB;
         short var6 = TheGame.this.sin_Array[((var2.m_zAng - var1.m_zAng >> 16) + 64 & 127) + 64];
         int var7 = var6 * 290 >> 14;
         if (this.yPosAInBSpace < 0) {
            var5 = -var5;
            var7 = -var7;
            this.xNormal = -var4;
            this.yNormal = var3;
         } else {
            this.xNormal = var4;
            this.yNormal = -var3;
         }

         this.xNormal = -this.xNormal;
         this.yNormal = -this.yNormal;
         this.xColPos = var1.m_xPos + (var3 * var7 - var4 * var5 >> 14);
         this.yColPos = var1.m_yPos + (var4 * var7 + var3 * var5 >> 14);
         this.nIntersection = this.yDistB;
         this.nCollisionExpand = this.yCollisionExpandB;
      }

      void calcCollisionA1(TheGame.Car var1, TheGame.Car var2, int var3, int var4) {
         int var5 = this.nAngOffset1 + this.nAngOffset2 + this.xCollisionExpandA;
         short var6 = TheGame.this.sin_Array[(var2.m_zAng - var1.m_zAng >> 16 & 127) + 64];
         int var7 = var6 * 290 >> 14;
         if (this.xPosBInASpace < 0) {
            var5 = -var5;
            var7 = -var7;
            this.xNormal = var3;
            this.yNormal = var4;
         } else {
            this.xNormal = -var3;
            this.yNormal = -var4;
         }

         this.xColPos = var2.m_xPos + (var3 * var5 - var4 * var7 >> 14);
         this.yColPos = var2.m_yPos + (var4 * var5 + var3 * var7 >> 14);
         this.nIntersection = this.xDistA;
         this.nCollisionExpand = this.xCollisionExpandA;
      }

      void calcCollisionA2(TheGame.Car var1, TheGame.Car var2, int var3, int var4) {
         int var5 = this.nAngOffset1 + this.nAngOffset3 + this.yCollisionExpandA;
         short var6 = TheGame.this.sin_Array[((var1.m_zAng - var2.m_zAng >> 16) + 64 & 127) + 64];
         int var7 = var6 * 290 >> 14;
         if (this.yPosBInASpace < 0) {
            var5 = -var5;
            var7 = -var7;
            this.xNormal = -var4;
            this.yNormal = var3;
         } else {
            this.xNormal = var4;
            this.yNormal = -var3;
         }

         this.xColPos = var2.m_xPos + (var3 * var7 - var4 * var5 >> 14);
         this.yColPos = var2.m_yPos + (var4 * var7 + var3 * var5 >> 14);
         this.nIntersection = this.yDistA;
         this.nCollisionExpand = this.yCollisionExpandA;
      }

      void CalcCollisionResponse(TheGame.Car var1, int var2, int var3) {
         this.nIntersection -= this.nCollisionExpand;
         int var6 = -this.nIntersection - this.nCollisionExpand;
         int var7 = 0;
         if (var6 < 0) {
            var6 = 0;
         } else {
            var7 = var6;
            var6 = var6 * var2 >> 2;
         }

         int var8 = this.nIntersection + this.nCollisionExpand;
         if (var8 < 0) {
            var8 = 0;
         } else {
            var8 = var8 * var3 >> 6;
         }

         int var9 = (this.m_xVel - var1.m_xVel) * this.xNormal + (this.m_yVel - var1.m_yVel) * this.yNormal >> 14;
         int var10 = this.xColPos - this.m_xPos;
         int var11 = this.yColPos - this.m_yPos;
         int var12 = this.yNormal * var10 - this.xNormal * var11 >> 14;
         int var13 = this.xColPos - var1.m_xPos;
         int var14 = this.yColPos - var1.m_yPos;
         int var15 = this.yNormal * var13 - this.xNormal * var14 >> 14;
         var9 -= (var12 * (this.m_zAngVel >> 8) - var15 * (var1.m_zAngVel >> 8) >> 8) * 1608 >> 16;
         var9 -= var8;
         if (var9 > 0) {
            this.addAngularCollisionForce(this, var1, var9, var12, var15);
         }

         var9 = (this.m_xVel - var1.m_xVel) * this.xNormal + (this.m_yVel - var1.m_yVel) * this.yNormal >> 14;
         var9 -= (var12 * (this.m_zAngVel >> 8) - var15 * (var1.m_zAngVel >> 8) >> 8) * 1608 >> 16;
         var9 -= var8;
         var9 += var6;
         if (var9 > 0) {
            this.addLinearCollisionForce(this, var1, var9, var12, var15);
            if (var1.m_velocityLastFrame > 1000) {
               var1.AddSparks(this.xColPos << 8, this.yColPos << 8, this.m_zPos << 8);
            }

            var1.DamageCar(var9, this.xNormal, this.yNormal, var7, this);
            this.DamageCar(var9, -this.xNormal, -this.yNormal, var7, var1);
         }

      }

      void addAngularCollisionForce(TheGame.Car var1, TheGame.Car var2, int var3, int var4, int var5) {
         if (var1.m_nCopNum == 0) {
            var1.m_nSlideOutDisableForCollision = 1024;
         }

         if (var2.m_nCopNum == 0) {
            var2.m_nSlideOutDisableForCollision = 1024;
         }

         if (var2.m_bPlayer && var1.m_nCopNum != 0) {
            var1.m_zAngVel += (var4 * var3 >> 8) * 3 >> 2;
            var2.m_zAngVel -= (var5 * var3 >> 8) * 1 >> 2;
         } else if (var1.m_bPlayer && var2.m_nCopNum != 0) {
            var1.m_zAngVel += (var4 * var3 >> 8) * 1 >> 2;
            var2.m_zAngVel -= (var5 * var3 >> 8) * 3 >> 2;
         } else {
            var1.m_zAngVel += var4 * var3 >> 8;
            var2.m_zAngVel -= var5 * var3 >> 8;
         }

      }

      void addLinearCollisionForce(TheGame.Car var1, TheGame.Car var2, int var3, int var4, int var5) {
         if (var1.m_nCopNum == 0) {
            var1.m_nSlideOutDisableForCollision = 1024;
         }

         if (var2.m_nCopNum == 0) {
            var2.m_nSlideOutDisableForCollision = 1024;
         }

         if (var2.m_bPlayer && var1.m_nCopNum != 0) {
            var1.m_xVel -= (this.xNormal * var3 >> 15) * 3 >> 2;
            var1.m_yVel -= (this.yNormal * var3 >> 15) * 3 >> 2;
            var2.m_xVel += this.xNormal * var3 >> 15 >> 2;
            var2.m_yVel += this.yNormal * var3 >> 15 >> 2;
         } else if (var1.m_bPlayer && var2.m_nCopNum != 0) {
            var1.m_xVel -= this.xNormal * var3 >> 15 >> 2;
            var1.m_yVel -= this.yNormal * var3 >> 15 >> 2;
            var2.m_xVel += (this.xNormal * var3 >> 15) * 3 >> 2;
            var2.m_yVel += (this.yNormal * var3 >> 15) * 3 >> 2;
         } else {
            var1.m_xVel -= this.xNormal * var3 >> 15;
            var1.m_yVel -= this.yNormal * var3 >> 15;
            var2.m_xVel += this.xNormal * var3 >> 15;
            var2.m_yVel += this.yNormal * var3 >> 15;
         }

      }

      void CollideWith(TheGame.Car var1, int var2, int var3) {
         if (!this.m_bDisabled) {
            if (!var1.m_bDisabled) {
               if (var1.m_nTimeNotVisable <= 2000 || this.m_nTimeNotVisable <= 2000) {
                  int var6 = TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (this.m_zAng >> 8 >> 8) & 255] - TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255]) * ((this.m_zAng >> 8) - (this.m_zAng >> 8 & -256)) >> 8);
                  int var7 = TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((this.m_zAng >> 8) + 49152 >> 8) & 255] - TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.m_zAng >> 8) + 49152 - ((this.m_zAng >> 8) + 49152 & -256)) >> 8);
                  int var8 = TheGame.this.sin_Array[var1.m_zAng >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (var1.m_zAng >> 8 >> 8) & 255] - TheGame.this.sin_Array[var1.m_zAng >> 8 >> 8 & 255]) * ((var1.m_zAng >> 8) - (var1.m_zAng >> 8 & -256)) >> 8);
                  int var9 = TheGame.this.sin_Array[(var1.m_zAng >> 8) + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((var1.m_zAng >> 8) + 49152 >> 8) & 255] - TheGame.this.sin_Array[(var1.m_zAng >> 8) + 49152 >> 8 & 255]) * ((var1.m_zAng >> 8) + 49152 - ((var1.m_zAng >> 8) + 49152 & -256)) >> 8);
                  this.xCollisionExpandA = (Math.abs(var7 * (this.m_xVel - var1.m_xVel) + var6 * (this.m_yVel - var1.m_yVel)) >> 14) * var2 >> 10;
                  this.yCollisionExpandA = (Math.abs(-var6 * (this.m_xVel - var1.m_xVel) + var7 * (this.m_yVel - var1.m_yVel)) >> 14) * var2 >> 10;
                  this.xColSizeA = 250 + this.xCollisionExpandA;
                  this.yColSizeA = 540 + this.yCollisionExpandA;
                  this.xCollisionExpandB = (Math.abs(var9 * (this.m_xVel - var1.m_xVel) + var8 * (this.m_yVel - var1.m_yVel)) >> 14) * var2 >> 10;
                  this.yCollisionExpandB = (Math.abs(-var8 * (this.m_xVel - var1.m_xVel) + var9 * (this.m_yVel - var1.m_yVel)) >> 14) * var2 >> 10;
                  this.xColSizeB = 250 + this.xCollisionExpandB;
                  this.yColSizeB = 540 + this.yCollisionExpandB;
                  this.nAngOffset1 = TheGame.this.sin_Array[(var1.m_zAng - this.m_zAng >> 16 & 63) + 32];
                  this.nAngOffset1 = this.nAngOffset1 * 353 >> 14;
                  this.nAngOffset2 = TheGame.this.sin_Array[var1.m_zAng - this.m_zAng >> 16 & 127];
                  this.nAngOffset2 = this.nAngOffset2 * 290 >> 14;
                  this.nAngOffset3 = TheGame.this.sin_Array[(var1.m_zAng - this.m_zAng >> 16) + 64 & 127];
                  this.nAngOffset3 = this.nAngOffset3 * 290 >> 14;
                  this.xPosTmp = var1.m_xPos - this.m_xPos;
                  this.yPosTmp = var1.m_yPos - this.m_yPos;
                  this.xPosBInASpace = -this.xPosTmp * var7 - this.yPosTmp * var6 >> 14;
                  this.yPosBInASpace = this.xPosTmp * var6 - this.yPosTmp * var7 >> 14;
                  this.xPosAInBSpace = this.xPosTmp * var9 + this.yPosTmp * var8 >> 14;
                  this.yPosAInBSpace = -this.xPosTmp * var8 + this.yPosTmp * var9 >> 14;
                  this.xDistA = Math.abs(this.xPosBInASpace) - this.nAngOffset1 - this.nAngOffset2 - 250;
                  this.yDistA = Math.abs(this.yPosBInASpace) - this.nAngOffset1 - this.nAngOffset3 - 540;
                  this.xDistB = Math.abs(this.xPosAInBSpace) - this.nAngOffset1 - this.nAngOffset2 - 250;
                  this.yDistB = Math.abs(this.yPosAInBSpace) - this.nAngOffset1 - this.nAngOffset3 - 540;
                  if (this.xDistA - this.xCollisionExpandA < 0 && this.yDistA - this.yCollisionExpandA < 0 && this.xDistB - this.xCollisionExpandB < 0 && this.yDistB - this.yCollisionExpandB < 0) {
                     this.CalcCollisionPositionAndNormal(var1, var2, var3);
                     this.CalcCollisionResponse(var1, var2, var3);
                     if (this.m_nCopNum != 0 && var1.m_bPlayer) {
                        this.m_bHitPlayer = true;
                     } else if (this.m_bPlayer && var1.m_nCopNum != 0) {
                        var1.m_bHitPlayer = true;
                     }
                  }

               }
            }
         }
      }

      public void DamageCar(int var1, int var2, int var3, int var4, TheGame.Car var5) {
         if (var5 == TheGame.this.players[0] && this.m_nID == -5) {
            this.m_nState = 0;
            if (TheGame.this.game_Time > var5.m_nRecklessTimer && TheGame.this.game_state == 2) {
               if (var5.m_nCurrentHeatRating < 3) {
                  ++var5.m_nCurrentHeatRating;
               }

               TheGame.this.game_popupText(TheGame.this.g_Text[201], false);
               var5.m_nRecklessTimer = TheGame.this.game_Time + 10000;
            }

         } else if (this.m_nCopNum != 0 && var5.m_velocityLastFrame >= 1000) {
            int var6 = var5.m_xVel * var2 + var5.m_yVel * var3 >> 14;
            if (var6 >= 0) {
               if (this.m_nDamagedLevel < 51200) {
                  if (this.m_nState == 4) {
                     this.m_nState = 0;
                     return;
                  }

                  if (var5 == TheGame.this.players[0] && TheGame.this.game_Time > var5.m_nRecklessTimer && TheGame.this.game_state == 2) {
                     if (var5.m_nCurrentHeatRating < 3) {
                        ++var5.m_nCurrentHeatRating;
                     }

                     TheGame.this.game_popupText(TheGame.this.g_Text[201], false);
                     var5.m_nRecklessTimer = TheGame.this.game_Time + 10000;
                  }

                  this.m_nDamagedLevel += var1;
                  if (this.m_nDamagedLevel > 51200) {
                     this.m_nState = 4;
                     this.m_zVel += 896;
                     if (var5 == TheGame.this.players[0] && TheGame.this.game_Time > var5.m_nAssaultTimer && TheGame.this.game_state == 2) {
                        if (var5.m_nCurrentHeatRating < 3) {
                           ++var5.m_nCurrentHeatRating;
                        }

                        TheGame.this.game_popupText(TheGame.this.g_Text[202], false);
                        var5.m_nAssaultTimer = TheGame.this.game_Time + 10000;
                     }

                     int var7 = TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (this.m_zAng >> 8 >> 8) & 255] - TheGame.this.sin_Array[this.m_zAng >> 8 >> 8 & 255]) * ((this.m_zAng >> 8) - (this.m_zAng >> 8 & -256)) >> 8);
                     int var8 = TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + ((this.m_zAng >> 8) + 49152 >> 8) & 255] - TheGame.this.sin_Array[(this.m_zAng >> 8) + 49152 >> 8 & 255]) * ((this.m_zAng >> 8) + 49152 - ((this.m_zAng >> 8) + 49152 & -256)) >> 8);
                     int var9 = -var2 * var8 - var3 * var7 >> 14;
                     int var10 = var2 * var7 - var3 * var8 >> 14;
                     this.m_xAngVel += var10 >> 3;
                     this.m_yAngVel -= var9 >> 3;
                     if (var5.m_velocityLastFrame < 5000 && this.m_velocityLastFrame < 5000) {
                        var4 = 0;
                     }

                     var4 = (var4 - 60) * 8;
                     if (var4 > 0) {
                        if (var4 > 2500) {
                           var4 = 2500;
                        }

                        this.m_xAngVel += (var10 >> 7) * var4;
                        this.m_yAngVel -= (var9 >> 6) * var4;
                        if (var4 > 1400) {
                           var4 = 1400;
                        }

                        this.m_zVel += var4;
                     }

                     ++TheGame.this.g_race.m_nCopsSmashed;
                  }
               }

            }
         }
      }

      public void AddSparks(int var1, int var2, int var3) {
         int var4 = 268435455;
         int var5 = 0;

         for(int var6 = 0; var6 < this.m_carSparks.length; ++var6) {
            if (this.m_carSparks[var6].timer < var4) {
               var4 = this.m_carSparks[var6].timer;
               var5 = var6;
            }
         }

         this.m_carSparks[var5].timer = TheGame.this.game_Time + 500;
         this.m_carSparks[var5].x = (float)var1 * 1.5258789E-5F;
         this.m_carSparks[var5].z = (float)var2 * 1.5258789E-5F;
         this.m_carSparks[var5].y = (float)var3 * 1.5258789E-5F;
      }

      public void render() {
         if (!this.m_bDisabled) {
            if (this.m_camDist < 15000 && !this.m_bNearCulled) {
               this.m_nTimeNotVisable = 0;
               if (this.m_bCheapUpdate) {
                  this.InitCollisionToSplinePosWhileMoving(this.m_nAiNode);
                  this.rotation.setIdentity();
                  this.rotation.postTranslate((float)this.m_xPos * 0.00390625F, (float)this.m_zPos * 0.00390625F, (float)this.m_yPos * 0.00390625F);
                  this.rotation.postRotate((float)this.m_zAng * 2.1457672E-5F, 0.0F, 1.0F, 0.0F);
                  this.rotation.postRotate((float)this.m_xAng * 2.1457672E-5F, 1.0F, 0.0F, 0.0F);
                  this.rotation.postRotate((float)this.m_yAng * 2.1457672E-5F, 0.0F, 0.0F, 1.0F);
               }

               this.m_ca.m_carMesh.setTransform(this.rotation);
               this.m_ca.render(this.rotation, this, this.m_camDist < 5000, this.m_bBreaking, this.m_bReversing);
            }

         }
      }

      public void renderAlpha() {
         if (!this.m_bDisabled) {
            if (this.m_camDist < 15000 && !this.m_bNearCulled) {
               for(int var1 = 0; var1 < this.m_carSparks.length; ++var1) {
                  if (this.m_carSparks[var1].timer > TheGame.this.game_Time) {
                     float var2 = (4.0F - (float)(this.m_carSparks[var1].timer - TheGame.this.game_Time) / 125.0F) / 2.0F;
                     this.tmpTrans.setIdentity();
                     this.tmpTrans.postTranslate(this.m_carSparks[var1].x, this.m_carSparks[var1].y, this.m_carSparks[var1].z);
                     this.tmpTrans.postScale(var2, var2, var2);
                     TheGame.this.scene_g3d.render(TheGame.this.sparks_sprite, this.tmpTrans);
                  }
               }

               this.m_ca.renderAlpha(this.rotation, this);
            }

         }
      }

      public void CalculateTimeDiff() {
         boolean var1 = false;
         int var2 = 0;
         int var3 = 0;
         boolean var4 = false;
         int var5;
         if (TheGame.this.game_playerPosition != 1) {
            for(var5 = 1; var5 < TheGame.this.g_race.m_numRacers; ++var5) {
               if (TheGame.this.players[var5].m_nLap >= TheGame.this.players[0].m_nLap) {
                  int var7 = TheGame.this.players[0].m_nCheckpointTime - TheGame.this.players[var5].m_nCheckpointTime;
                  if (var7 >= var2) {
                     var4 = true;
                     var2 = var7;
                     var3 = var5;
                  }
               }
            }

            if (this.m_bPlayer && var3 > 0) {
               TheGame.this.game_popupText(this.constructTimeString(var2, var4), true);
            }
         } else {
            var5 = 0;

            for(int var6 = 1; var6 < TheGame.this.g_race.m_numRacers; ++var6) {
               if (TheGame.this.players[var6].m_nLap == TheGame.this.players[0].m_nLap) {
                  var3 = var6;
                  ++var5;
               }
            }

            if (var5 == 1 && TheGame.this.popupTimer < TheGame.this.game_Time) {
               var2 = TheGame.this.players[0].m_nCheckpointTime - TheGame.this.players[var3].m_nCheckpointTime;
               TheGame.this.game_popupText(this.constructTimeString(var2, var4), true);
            }
         }

      }

      public String constructTimeString(int var1, boolean var2) {
         String var3;
         if (var2) {
            var3 = "-";
         } else {
            var3 = "+";
         }

         var1 = Math.abs(var1);
         int var4 = var1 / 1000 % 60;
         int var5 = var1 / '\uea60';
         int var6 = var1 - (var5 * '\uea60' + var4 * 1000);
         if (var5 < 10) {
            var3 = var3 + "0";
         }

         var3 = var3 + var5 + ":";
         if (var4 < 10) {
            var3 = var3 + "0";
         }

         var3 = var3 + var4 + ":";
         if (var6 < 10) {
            var3 = var3 + "0";
         }

         var3 = var3 + var6;
         return var3;
      }

      public void HandleHeatRating() {
         int var1;
         int var2;
         if (TheGame.this.g_roadsideCop != null && !TheGame.this.g_roadsideCop.m_bDisabled) {
            var1 = TheGame.this.g_roadsideCop.m_xPos - this.m_xPos;
            var2 = TheGame.this.g_roadsideCop.m_yPos - this.m_yPos;
            if (Math.abs(var1) + Math.abs(var2) - (Math.min(Math.abs(var1), Math.abs(var2)) >> 1) < 7680) {
               if (this.m_bPlayer && this.GetMPH() > TheGame.this.g_race.m_nSpeedLimit && TheGame.this.game_Time > this.m_nSpeedingTimer && TheGame.this.game_state == 2) {
                  if (this.m_nCurrentHeatRating < 3) {
                     ++this.m_nCurrentHeatRating;
                  }

                  TheGame.this.game_popupText(TheGame.this.g_Text[203], false);
                  this.m_nSpeedingTimer = TheGame.this.game_Time + 30000;
                  TheGame.this.g_roadsideCop.m_nState = 0;
               }

               if (this.m_nCurrentHeatRating > 1) {
                  TheGame.this.g_roadsideCop.m_nState = 0;
               }
            }
         }

         var1 = (TheGame.this.track_evasionPoints[TheGame.this.game_closestEvasionPickup][0] << 4) - this.m_xPos;
         var2 = (TheGame.this.track_evasionPoints[TheGame.this.game_closestEvasionPickup][1] << 4) - this.m_yPos;
         if (Math.abs(var1) + Math.abs(var2) - (Math.min(Math.abs(var1), Math.abs(var2)) >> 1) < 2048 && this.m_bPlayer && TheGame.this.game_Time > this.m_nCoolTimer && TheGame.this.game_state == 2 && this.m_nCurrentHeatRating > 0) {
            TheGame.this.showFlashEffect = true;
            --this.m_nCurrentHeatRating;
            this.m_nCoolTimer = TheGame.this.game_Time + 3000;
         }

      }

      public int GetMPH() {
         int var1 = TheGame.this.players[0].m_velocityLastFrame * 1000 >> 2;
         return (var1 >> 8) * 572 >> 16;
      }

      public class CSpark {
         float x;
         float y;
         float z;
         int timer;
      }
   }

   class ObjectRef {
      public String m_name;
      public TheGame.MintMesh[] m_object;

      public ObjectRef(String var2, TheGame.MintMesh[] var3) {
         this.m_name = var2;
         this.m_object = var3;
      }
   }

   class TextureRef {
      public String m_name;
      public Texture2D m_texture;
      public boolean m_alpha;

      public TextureRef(String var2, Texture2D var3, boolean var4) {
         this.m_name = var2;
         this.m_texture = var3;
         this.m_alpha = var4;
      }
   }

   class CSoundPlayerMIDP2 extends TheGame.CSoundPlayer implements PlayerListener {
      TheGame.CSoundPlayerMIDP2.SoundCache[] sCache = new TheGame.CSoundPlayerMIDP2.SoundCache[8];
      String[] mimeType = new String[6];

      CSoundPlayerMIDP2() {
         super();
      }

      public void playerUpdate(Player var1, String var2, Object var3) {
         this.callbackString = var2;
         int var4;
         if (var2 == "started") {
            System.out.println("callback: STARTED");
            System.out.flush();

            for(var4 = 0; var4 < 8; ++var4) {
               if (this.sCache[var4] != null && this.sCache[var4].m_theSound == var1) {
                  this.sCache[var4].object.isPlaying = true;
                  break;
               }
            }
         }

         if (var2 == "stopped") {
            System.out.println("callback: STOPPED");
            System.out.flush();

            for(var4 = 0; var4 < 8; ++var4) {
               if (this.sCache[var4] != null && this.sCache[var4].m_theSound == var1) {
                  this.sCache[var4].object.isPlaying = false;
                  break;
               }
            }
         }

         if (var2 == "stoppedAtTime") {
            System.out.println("callback: STOPPED_AT_TIME");
            System.out.flush();
         }

         if (var2 == "endOfMedia") {
            try {
               System.out.println("callback: END_OF_MEDIA");
               System.out.flush();
            } catch (Exception var7) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\javasoundmidp2.hpp", 89, var7.toString());
            }
         }

         if (var2 == "durationUpdated") {
            ;
         }

         if (var2 == "deviceUnavailable") {
            System.out.println("callback: DEVICE_UNAVAILABLE");
            System.out.flush();

            try {
               Thread.sleep(1000L);
               var1.start();
            } catch (Exception var6) {
               ;
            }
         }

         if (var2 == "deviceAvailable") {
            System.out.println("callback: DEVICE_AVAILABLE");
            System.out.flush();
         }

         if (var2 == "volumeChanged") {
            System.out.println("callback: VOLUME_CHANGED");
            System.out.flush();
         }

         if (var2 == "sizeChanged") {
            ;
         }

         if (var2 == "error") {
            System.out.println("callback: ERROR");
            System.out.flush();
         }

         if (var2 == "closed") {
            ;
         }

         if (var2 == "recordStarted") {
            ;
         }

         if (var2 == "recordStopped") {
            ;
         }

         if (var2 == "recordError") {
            ;
         }

         if (var2 == "bufferingStarted") {
            ;
         }

         if (var2 == "bufferingStopped") {
            ;
         }

      }

      boolean Construct() {
         super.Construct();
         System.out.println("MIDP2:Construct");
         System.out.flush();
         System.out.println("c:\\mobiledevelopment\\ndplatform\\ndsrc\\javasoundmidp2.hpp compiled for ???");
         System.out.flush();
         this.mimeType[1] = null;
         this.mimeType[2] = "audio/x-wav";
         this.mimeType[3] = "audio/midi";
         this.mimeType[4] = null;
         this.mimeType[5] = "audio/amr";
         return false;
      }

      void Destroy() {
      }

      void PlaySoundNowIfPossible(TheGame.CSoundObject var1) {
         this.PlaySoundNext(var1);
      }

      boolean IsSoundPlaying(TheGame.CSoundObject var1) {
         return var1.isPlaying;
      }

      boolean IsAnySoundPlaying() {
         for(int var1 = 0; var1 < 8; ++var1) {
            if (this.sCache[var1] != null && this.sCache[var1].object.isPlaying) {
               return true;
            }
         }

         return false;
      }

      void PrepareSound(TheGame.CSoundObject var1, int var2) {
         this.PlaySoundNext(var1, var2, true);
      }

      void StopSound(TheGame.CSoundObject var1) {
         try {
            for(int var2 = 0; var2 < 8; ++var2) {
               if (this.sCache[var2] != null && this.sCache[var2].object == var1 && this.sCache[var2].m_theSound != null) {
                  this.sCache[var2].m_theSound.stop();
                  System.out.println("  SS: stopped sound in slot " + var2);
                  System.out.flush();
                  return;
               }
            }
         } catch (MediaException var4) {
            this.exceptionString = "ME:" + var4.getMessage();
         }

      }

      void StopCurrentSound() {
      }

      void StopAllSounds() {
         try {
            for(int var1 = 0; var1 < 8; ++var1) {
               if (this.sCache[var1] != null && this.sCache[var1].m_theSound != null) {
                  int var10000 = this.sCache[var1].m_theSound.getState();
                  Player var10001 = this.sCache[var1].m_theSound;
                  if (var10000 == 400) {
                     System.out.println("SAS: stopping sound in slot " + var1 + "...");
                     System.out.flush();
                     this.sCache[var1].m_theSound.stop();
                  }
               }
            }
         } catch (MediaException var3) {
            this.exceptionString = "ME:" + var3.getMessage();
         }

      }

      void SetVolume(int var1) {
         System.out.println("PSN: Setting Volume to:" + var1);
         System.out.flush();
         this.GlobalVolume = var1;

         for(int var2 = 0; var2 < 8; ++var2) {
            if (this.sCache[var2] != null && this.sCache[var2].m_theSound != null) {
               int var10000 = this.sCache[var2].m_theSound.getState();
               Player var10001 = this.sCache[var2].m_theSound;
               if (var10000 == 400) {
                  VolumeControl var3 = (VolumeControl)this.sCache[var2].m_theSound.getControl("VolumeControl");
                  var3.setLevel(this.GlobalVolume);
               }
            }
         }

         System.out.println("PSN: Global Volume:" + this.GlobalVolume);
         System.out.flush();
      }

      void PlaySoundImmediate(TheGame.CSoundObject var1) {
         this.StopAllSounds();
         this.PlaySoundNext(var1);
      }

      void PlayBackgroundMusic(TheGame.CSoundObject var1) {
         this.PlaySoundNext(var1, -1, false);
      }

      void PlaySoundNext(TheGame.CSoundObject var1) {
         this.PlaySoundNext(var1, 1, false);
      }

      void PlaySoundNext(TheGame.CSoundObject var1, int var2, boolean var3) {
         this.exceptionString = null;
         this.callbackString = null;
         System.out.println("PSN: want to play sound " + var1);
         System.out.flush();

         try {
            int var10000;
            Player var10001;
            for(int var5 = 0; var5 < 8; ++var5) {
               if (this.sCache[var5] != null && this.sCache[var5].m_theSound != null) {
                  var10000 = this.sCache[var5].m_theSound.getState();
                  var10001 = this.sCache[var5].m_theSound;
                  if (var10000 == 400 && this.sCache[var5].m_theSound.getMediaTime() < this.sCache[var5].m_theSound.getDuration()) {
                     System.out.println("PSN: there is already a sound playing, ignoring this request...");
                     System.out.flush();
                     return;
                  }
               }
            }

            int var4;
            VolumeControl var10;
            for(var4 = 0; var4 < 8; ++var4) {
               if (this.sCache[var4] != null && this.sCache[var4].object == var1 && this.sCache[var4].m_theSound != null) {
                  var10000 = this.sCache[var4].m_theSound.getState();
                  var10001 = this.sCache[var4].m_theSound;
                  if (var10000 != 400) {
                     System.out.println("PSN: SETTING LOOP COUNT TO: " + var2 + " FOR CACHED SOUND");
                     System.out.flush();
                     this.sCache[var4].m_theSound.setLoopCount(var2);
                     if (!var3) {
                        this.sCache[var4].m_theSound.setMediaTime(0L);
                        this.sCache[var4].m_theSound.start();
                     }

                     System.out.println("PSN: Setting cached sound volume:" + this.GlobalVolume);
                     System.out.flush();
                     var10 = (VolumeControl)this.sCache[var4].m_theSound.getControl("VolumeControl");
                     var10.setLevel(this.GlobalVolume);
                     System.out.println("PSN: started cached sound " + var4 + " " + this.sCache[var4].object);
                     System.out.flush();
                  } else {
                     System.out.println("PSN: WARNING - SHOULD NOT GET HERE!");
                     System.out.flush();
                     TheGame.AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\javasoundmidp2.hpp", 351, "Sound is not started!");
                  }

                  return;
               }
            }

            System.out.println("PSN: sound is not cached");
            System.out.flush();

            for(var4 = 0; var4 < 8; ++var4) {
               if (this.sCache[var4] == null) {
                  var1.memstream.reset();
                  this.sCache[var4] = new TheGame.CSoundPlayerMIDP2.SoundCache();
                  this.sCache[var4].object = var1;
                  this.sCache[var4].m_theSound = Manager.createPlayer(var1.memstream, this.mimeType[var1.soundType]);
                  this.sCache[var4].m_theSound.realize();
                  this.sCache[var4].m_theSound.prefetch();
                  this.sCache[var4].m_theSound.addPlayerListener(this);
                  this.sCache[var4].m_theSound.setLoopCount(var2);
                  System.out.println("PSN: SETTING LOOP COUNT TO: " + var2 + " FOR NON-CACHED SOUND");
                  System.out.flush();
                  if (!var3) {
                     this.sCache[var4].m_theSound.setMediaTime(0L);
                     this.sCache[var4].m_theSound.start();
                  }

                  System.out.println("PSN: Setting Volume:" + this.GlobalVolume);
                  System.out.flush();
                  var10 = (VolumeControl)this.sCache[var4].m_theSound.getControl("VolumeControl");
                  var10.setLevel(this.GlobalVolume);
                  System.out.println("PSN: loaded sound to slot " + var4 + " " + this.sCache[var4].object);
                  System.out.flush();
                  return;
               }
            }

            System.out.println("PSN: failed to find an unused cache slot (too many sounds)");
            System.out.flush();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\javasoundmidp2.hpp", 396, "failed to find an unused cache slot (too many sounds)");
         } catch (MediaException var6) {
            System.out.println("MediaExc:" + var6.getMessage());
            System.out.flush();
            this.exceptionString = "ME:" + var6.getMessage();
            this.DisposeSound(var1);
         } catch (IOException var7) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\javasoundmidp2.hpp", 408, var7.toString());
            System.out.println("IOExc:" + var7.getMessage());
            System.out.flush();
            this.exceptionString = "IOE:" + var7.getMessage();
         } catch (IllegalStateException var8) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\javasoundmidp2.hpp", 414, var8.toString());
            System.out.println("IllStateExc:" + var8.getMessage());
            System.out.flush();
            this.exceptionString = "ISE:" + var8.getMessage();
         } catch (Exception var9) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\javasoundmidp2.hpp", 420, var9.toString());
            System.out.println("Exc:" + var9.getMessage());
            System.out.flush();
            this.exceptionString = "GE:" + var9.getMessage();
         }

      }

      void DisposeSound(TheGame.CSoundObject var1) {
         for(int var2 = 0; var2 < 8; ++var2) {
            if (this.sCache[var2] != null && this.sCache[var2].object == var1 && this.sCache[var2].m_theSound != null) {
               try {
                  this.sCache[var2].m_theSound.stop();
               } catch (Exception var4) {
                  ;
               }

               this.sCache[var2].m_theSound.removePlayerListener(this);
               this.sCache[var2].m_theSound.deallocate();
               this.sCache[var2].m_theSound.close();
               this.sCache[var2] = null;
               return;
            }
         }

      }

      private class SoundCache {
         Player m_theSound;
         TheGame.CSoundObject object;

         private SoundCache() {
            this.m_theSound = null;
            this.object = null;
         }
      }
   }

   class CSoundPlayer {
      TheGame.CSoundObject CurrentSound;
      TheGame.CSoundObject NextSound;
      String callbackString;
      String exceptionString;
      static final int MAX_SOUNDS = 8;
      int CurrentSoundsPlaying;
      int maxSounds;
      int GlobalVolume;

      boolean Construct() {
         this.CurrentSound = null;
         this.NextSound = null;
         this.callbackString = null;
         this.exceptionString = null;
         this.CurrentSoundsPlaying = 0;
         this.maxSounds = 0;
         return false;
      }

      void Destroy() {
         this.callbackString = null;
         this.exceptionString = null;
      }

      void PlaySoundNowIfPossible(TheGame.CSoundObject var1) {
      }

      void StopSound(TheGame.CSoundObject var1) {
      }

      void StopCurrentSound() {
      }

      void StopAllSounds() {
      }

      void PlaySoundImmediate(TheGame.CSoundObject var1) {
      }

      void PlaySoundNext(TheGame.CSoundObject var1) {
      }

      boolean IsSoundPlaying(TheGame.CSoundObject var1) {
         return false;
      }

      boolean IsAnySoundPlaying() {
         return false;
      }

      void DisposeSound(TheGame.CSoundObject var1) {
      }

      void PlayBackgroundMusic(TheGame.CSoundObject var1) {
      }

      void StopBackgroundMusic(TheGame.CSoundObject var1) {
      }

      void SetVolume(TheGame.CSoundObject var1, int var2) {
      }

      void SetVolume(int var1) {
         this.GlobalVolume = var1;
      }

      String GetCallbackString() {
         return this.callbackString;
      }

      String GetErrorString() {
         return this.exceptionString;
      }
   }

   class CSoundObject {
      static final int SOUND_TYPE_UNK = 0;
      static final int SOUND_TYPE_TONE = 1;
      static final int SOUND_TYPE_WAV = 2;
      static final int SOUND_TYPE_MIDI = 3;
      static final int SOUND_TYPE_QCP = 4;
      static final int SOUND_TYPE_AMR = 5;
      static final int NUM_SOUND_TYPES = 6;
      TheGame.NDInputStream memstream = null;
      int soundType;
      String soundUrl;
      int soundID = -1;
      int refCount = -1;
      boolean isPlaying;

      boolean LoadSound(byte[] var1, int var2) {
         int var3 = var1.length;
         this.memstream = null;
         this.memstream = TheGame.this.new NDInputStream(var1);
         this.soundType = var2;
         return true;
      }

      boolean LoadSound(byte[] var1, int var2, int var3, int var4) {
         this.memstream = null;
         this.memstream = TheGame.this.new NDInputStream(var1, var2, var3);
         this.soundType = var4;
         return true;
      }

      void SetUrl(String var1) {
         this.soundUrl = var1;
      }

      void Dispose() {
         this.memstream = null;
      }
   }

   class NDInputStream extends InputStream {
      public int stid;
      byte[] internal;
      public int total;
      public int current;
      public int buffersize;

      public NDInputStream(byte[] var2) {
         this.total = var2.length;
         this.internal = new byte[this.total];
         System.arraycopy(var2, 0, this.internal, 0, this.total);
         this.stid = -1;
         this.current = 0;
      }

      public NDInputStream(byte[] var2, int var3, int var4) {
         this.internal = new byte[var4];
         System.arraycopy(var2, var3, this.internal, 0, var4);
         this.stid = -1;
         this.current = 0;
      }

      public int read() {
         if (this.current >= this.total) {
            return -1;
         } else {
            byte var1 = this.internal[this.current];
            ++this.current;
            return var1;
         }
      }

      public int read(byte[] var1) throws IOException {
         return this.read(var1, 0, var1.length);
      }

      public int read(byte[] var1, int var2, int var3) {
         this.buffersize = var1.length;
         if (this.current >= this.total) {
            return -1;
         } else {
            int var4 = this.current;
            int var5 = this.current + var3;
            if (var5 > this.total) {
               var5 = this.total;
            }

            System.arraycopy(this.internal, var4, var1, var2, var5 - var4);
            this.current = var5;
            return var5 - var4;
         }
      }

      public int available() {
         int var1 = this.total - this.current;
         return var1 <= 0 ? 0 : var1;
      }

      public void reset() {
         this.current = 0;
      }

      public boolean markSupported() {
         return false;
      }

      public long skip(long var1) {
         return 0L;
      }

      public void mark(int var1) {
      }

      public void copy(byte[] var1) {
         this.total = var1.length;
         System.arraycopy(var1, 0, this.internal, 0, this.total);
         this.current = 0;
      }

      public void copy(byte[] var1, int var2, int var3) {
         System.arraycopy(var1, var2, this.internal, 0, var3);
         this.current = 0;
      }
   }

   private class CFont {
      Font m_SystemFont;
      int m_red;
      int m_green;
      int m_blue;
      boolean m_bShadow;
      int m_shadowColor;
      int m_nMaxWidth;

      private CFont() {
      }

      void Construct() {
         this.m_SystemFont = null;
         this.m_red = 0;
         this.m_green = 0;
         this.m_blue = 0;
         this.m_bShadow = false;
         this.m_shadowColor = 0;
         this.m_nMaxWidth = 0;
      }

      void SetSystem(Font var1) {
         this.m_SystemFont = var1;
      }

      void Free() {
         this.m_SystemFont = null;
      }

      void SetColor(int var1, int var2, int var3) {
         this.m_red = var1;
         this.m_green = var2;
         this.m_blue = var3;
      }

      void SetColor(int var1) {
         this.m_red = var1 >> 16 & 255;
         this.m_green = var1 >> 8 & 255;
         this.m_blue = var1 & 255;
      }

      void SetShadow(int var1) {
         this.m_bShadow = true;
         this.m_shadowColor = var1;
      }

      void ResetShadow() {
         this.m_bShadow = false;
      }

      int GetHeight() {
         return this.m_SystemFont != null ? this.m_SystemFont.getHeight() : -1;
      }

      int GetSubstringWrappedHeight(String var1, int var2, int var3, int var4, int var5) {
         int var6 = var2;
         int var7 = var2 + var3;
         int var8 = 0;
         this.m_nMaxWidth = 0;

         while(true) {
            while(var6 >= var7 || var1.charAt(var6) != ' ') {
               if (var6 >= var7) {
                  return var8;
               }

               int var9;
               int var10;
               int var11;
               for(var9 = TheGame.this.system_FindFirst(var1, var6, var7, ' '); var9 < var7 - 1; var9 = var11) {
                  var11 = TheGame.this.system_FindFirst(var1, var9 + 1, var7, ' ');
                  var10 = this.GetSubstringWidth(var1, var6, var11 - var6);
                  if (var10 > var4) {
                     break;
                  }
               }

               while(true) {
                  var10 = this.GetSubstringWidth(var1, var6, var9 - var6);
                  if (var10 <= var4) {
                     if (this.m_nMaxWidth < var10) {
                        this.m_nMaxWidth = var10;
                     }

                     var6 = var9;
                     var8 += this.GetHeight() + var5;
                     break;
                  }

                  --var9;
                  TheGame.Assert(var9 > var6, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\cfont_share.hpp", 243);
               }
            }

            ++var6;
         }
      }

      void DrawSubstringWrapped(String var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         int var9 = var2;
         int var10 = var2 + var3;
         int var11;
         if ((var7 & 12) != 0) {
            var11 = this.GetSubstringWrappedHeight(var1, var2, var3, var4, var8);
            if ((var7 & 4) != 0) {
               var6 -= var11 >> 1;
            } else if ((var7 & 8) != 0) {
               var6 -= var11;
            }

            var7 = var7 & 3 | 0;
         }

         while(true) {
            while(var9 < var10 && var1.charAt(var9) == ' ') {
               ++var9;
            }

            if (var9 >= var10) {
               break;
            }

            int var12;
            for(var11 = TheGame.this.system_FindFirst(var1, var9, var10, ' '); var11 < var10 - 1; var11 = var12) {
               var12 = TheGame.this.system_FindFirst(var1, var11 + 1, var10, ' ');
               int var13 = this.GetSubstringWidth(var1, var9, var12 - var9);
               if (var13 > var4) {
                  break;
               }
            }

            while(this.GetSubstringWidth(var1, var9, var11 - var9) > var4) {
               --var11;
               TheGame.Assert(var11 > var9, "c:\\mobiledevelopment\\ndplatform\\ndsrc\\cfont_share.hpp", 312);
            }

            for(var12 = var11; var12 >= var9 && var1.charAt(var12 - 1) == ' '; --var12) {
               ;
            }

            if (var9 >= var12) {
               break;
            }

            this.DrawSubstring(var1, var9, var12 - var9, var5, var6, var7);
            var9 = var11;
            var6 += this.GetHeight() + var8;
         }

      }

      void DrawSubstringCustom(int var1, int var2, String var3, int var4, int var5, int var6, int var7) {
         short var8 = (short)((TheGame.this.asset_DataArray[var1 + 0] & -65536) >> 16);
         short var9 = (short)((TheGame.this.asset_DataArray[var1 + 0] & '\uffff') >> 0);
         boolean var10 = (short)((TheGame.this.asset_DataArray[var1 + 1] & -65536) >> 16) != 0;
         short var11 = (short)((TheGame.this.asset_DataArray[var2 + 1] & '\uffff') >> 0);
         short var12 = (short)((TheGame.this.asset_DataArray[var11 + 0] & -65536) >> 16);
         int var13 = (short)((TheGame.this.asset_DataArray[var11 + 0] & '\uffff') >> 0) - (short)((TheGame.this.asset_DataArray[var11 + 3] & -65536) >> 16);
         short var14 = (short)((TheGame.this.asset_DataArray[var11 + 2] & -65536) >> 16);
         short var15 = (short)((TheGame.this.asset_DataArray[var14 + 0] & -65536) >> 16);
         int var16 = var4 + var5;

         for(int var17 = var4; var17 < var16; var6 += var8) {
            char var18 = var3.charAt(var17);
            if (var10) {
               var18 = Character.toUpperCase(var18);
            }

            if (var18 >= 0 && var18 <= '\u007f') {
               int var19 = var6 - (var18 & 15) * var8 + var12;
               int var20 = var7 - (var18 >> 4) * var9 + var13;
               TheGame.this.asset_DrawClipped(var15, var19, var20, var6, var7, var8, var9, false);
            }

            ++var17;
         }

      }

      int GetSubstringWidth(String var1, int var2, int var3) {
         return this.m_SystemFont != null ? this.m_SystemFont.substringWidth(var1, var2, var3) : -1;
      }

      void DrawSubstring(String var1, int var2, int var3, int var4, int var5, int var6) {
         int var7 = this.GetSubstringWidth(var1, var2, var3);
         int var8 = this.GetHeight();
         if ((var6 & 1) != 0) {
            var4 -= var7 >> 1;
         } else if ((var6 & 2) != 0) {
            var4 -= var7;
         }

         if ((var6 & 4) != 0) {
            var5 -= var8 >> 1;
         } else if ((var6 & 8) != 0) {
            var5 -= var8;
         } else if ((var6 & 0) != 0) {
            ;
         }

         TheGame.this.system_UpdateDirty(var4, var5, var7, var8);
         if (this.m_SystemFont != null) {
            TheGame.m_CurrentGraphics.setFont(this.m_SystemFont);
            TheGame.m_CurrentGraphics.setClip(TheGame.this.system_xClip, TheGame.this.system_yClip, TheGame.this.system_nClipWidth, TheGame.this.system_nClipHeight);
            Graphics var10006;
            Graphics var10007;
            if (this.m_bShadow) {
               TheGame.m_CurrentGraphics.setColor(this.m_shadowColor);
               int var10004 = var4 + 1;
               int var10005 = var5 + 1;
               var10006 = TheGame.m_CurrentGraphics;
               var10007 = TheGame.m_CurrentGraphics;
               TheGame.m_CurrentGraphics.drawSubstring(var1, var2, var3, var10004, var10005, 4 | 16);
            }

            TheGame.m_CurrentGraphics.setColor(this.m_red, this.m_green, this.m_blue);
            var10006 = TheGame.m_CurrentGraphics;
            var10007 = TheGame.m_CurrentGraphics;
            TheGame.m_CurrentGraphics.drawSubstring(var1, var2, var3, var4, var5, 4 | 16);
         }

      }
   }

   class NFSMW_Menu {
      public int m_title;
      public TheGame.NFSMW_MenuItem[] m_items = null;
      public TheGame.NFSMW_Menu m_parent = null;
      public int m_selected = 0;
      public int m_itemCount;
      public int m_backgroundStyle;
      public boolean m_loaded;

      public NFSMW_Menu(int var2, TheGame.NFSMW_MenuItem[] var3, int var4) {
         this.m_backgroundStyle = var4;
         this.m_items = var3;
         this.m_itemCount = this.m_items.length;
         this.m_title = var2;
         this.m_loaded = false;

         for(int var5 = 0; var5 < this.m_itemCount; ++var5) {
            if (this.m_items[var5].m_subMenu != null) {
               this.m_items[var5].m_subMenu.m_parent = this;
            }
         }

      }

      public NFSMW_Menu(int var2, TheGame.MenuFriendly[] var3, int var4) {
         this.m_backgroundStyle = var4;
         this.m_itemCount = var3.length;
         this.m_title = var2;
         this.m_loaded = false;

         int var5;
         for(var5 = 0; var5 < this.m_itemCount; ++var5) {
            if (var3[var5] == null) {
               this.m_itemCount = var5;
               break;
            }
         }

         this.m_items = new TheGame.NFSMW_MenuItem[this.m_itemCount];

         for(var5 = 0; var5 < this.m_itemCount; ++var5) {
            this.m_items[var5] = TheGame.this.new NFSMW_MenuItem(var3[var5]);
         }

      }

      public NFSMW_Menu(int var2, TheGame.MenuFriendly[] var3, int var4, int var5, int var6) {
         this.m_backgroundStyle = var6;
         this.m_itemCount = var3.length;
         this.m_title = var2;
         this.m_loaded = false;
         this.m_items = new TheGame.NFSMW_MenuItem[var5];
         this.m_itemCount = this.m_items.length;
         var5 += var4;

         for(int var7 = var4; var7 < var5; ++var7) {
            this.m_items[var7 - var4] = TheGame.this.new NFSMW_MenuItem(var3[var7]);
         }

      }

      public void loadIcons(boolean var1) {
         if (this.m_items != null && !this.m_loaded) {
            for(int var2 = 0; var2 < this.m_items.length; ++var2) {
               if (this.m_items[var2] != null) {
                  try {
                     TheGame.this.asset_LoadImage(this.m_items[var2].m_icon, false);
                  } catch (Exception var4) {
                     var4.printStackTrace();
                     TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu.h", 186, var4.toString());
                  } catch (Error var5) {
                     TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu.h", 186, var5.toString());
                  }

                  if (var1 && this.m_items[var2].m_subMenu != null) {
                     this.m_items[var2].m_subMenu.loadIcons(var1);
                  }
               }
            }

            this.m_loaded = true;
         }
      }

      public void freeIcons(boolean var1) {
         if (this.m_items != null && this.m_loaded) {
            for(int var2 = 0; var2 < this.m_items.length; ++var2) {
               if (this.m_items[var2] != null) {
                  try {
                     TheGame.this.asset_FreeImage(this.m_items[var2].m_icon);
                  } catch (Exception var4) {
                     var4.printStackTrace();
                     TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu.h", 211, var4.toString());
                  } catch (Error var5) {
                     TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\menu.h", 211, var5.toString());
                  }

                  if (var1 && this.m_items[var2].m_subMenu != null) {
                     this.m_items[var2].m_subMenu.freeIcons(var1);
                  }
               }
            }

            this.m_loaded = false;
         }
      }

      public void sortCareerMenu() {
      }
   }

   class NFSMW_MenuItem {
      public int m_text;
      public int m_icon;
      public int m_type;
      public int m_cmd;
      public TheGame.NFSMW_Menu m_subMenu;

      public NFSMW_MenuItem(int var2, int var3, int var4, int var5, TheGame.NFSMW_Menu var6) {
         this.m_text = var2;
         this.m_icon = var3;
         this.m_type = var4;
         this.m_cmd = var5;
         this.m_subMenu = var6;
      }

      public NFSMW_MenuItem(TheGame.MenuFriendly var2) {
         this.m_text = var2.m_title;
         this.m_type = var2.m_type;
         this.m_cmd = var2.m_cmd;
         this.m_icon = var2.m_icon;
         this.m_subMenu = null;
      }
   }

   class NFSMW_Race {
      public int m_type;
      public int m_laps;
      public int m_trackPath;
      public int m_difficulty = 0;
      public int m_numRacers = 3;
      public int m_numCops = 2;
      public int m_numTraffic = 1;
      public boolean m_bMission;
      public boolean m_bMissionPassed;
      public boolean m_bNewMission;
      public int m_nMissionID;
      public int m_nMissionUnlocked;
      public String m_timeString;
      public int m_nTopSpeed;
      public int m_nCopsSmashed;
      public int m_nTimeLimit;
      public int m_nSpeedLimit;
      public String m_LapTimeString;
      public int m_nFastestLapTime;
      public int m_nLastLapTime;
      public int m_quickraceID;
   }

   class NFSMW_Profile {
      public int m_rep;
      public int m_cash;
      public int m_carRep;
      public byte m_profileNum;
      public byte[] m_missionStatus;
      public byte[][] m_partStatus;
      public byte m_equipedCar;
      public byte m_currentCar;
      public int m_nCopsCrashed;
      public byte[][] m_carSetups;
      public int[] m_LapTimes;

      NFSMW_Profile() {
         this.m_missionStatus = new byte[TheGame.this.g_missions.length];
         this.m_partStatus = new byte[TheGame.this.g_carParts.length][];
         this.m_carSetups = new byte[6][17];
         this.m_LapTimes = new int[5];
         this.Init();
         this.getLastOpenProfile();
         this.loadRecords();
      }

      public void Init() {
         this.m_rep = 0;
         this.m_cash = 0;
         this.m_carRep = 0;
         this.m_currentCar = this.m_equipedCar = 5;
         this.m_nCopsCrashed = 0;

         int var1;
         int var2;
         for(var1 = 0; var1 < 6; ++var1) {
            for(var2 = 0; var2 < 17; ++var2) {
               this.m_carSetups[var1][var2] = 0;
            }
         }

         for(var1 = 0; var1 < this.m_LapTimes.length; ++var1) {
            this.m_LapTimes[var1] = 0;
         }

         for(var1 = 0; var1 < this.m_missionStatus.length; ++var1) {
            this.m_missionStatus[var1] = 0;
         }

         for(var1 = 0; var1 < this.m_partStatus.length; ++var1) {
            this.m_partStatus[var1] = new byte[TheGame.this.g_carParts[var1].length];
            this.m_partStatus[var1][0] = 1;

            for(var2 = 1; var2 < this.m_partStatus[var1].length; ++var2) {
               this.m_partStatus[var1][var2] = 0;
            }
         }

         TheGame.g_missionIncID = 0;
      }

      public void createProfile() {
         boolean var1 = false;

         try {
            RecordStore var2 = RecordStore.openRecordStore("currentProfile", true);
            if (var2.getNumRecords() > 0 && var2.getNumRecords() != 2) {
               boolean var3 = false;
               RecordEnumeration var4 = var2.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);

               while(var4.hasNextElement()) {
                  int var7 = var4.nextRecordId();
                  var2.deleteRecord(var7);
               }

               var4.destroy();
               var1 = true;
            }

            if (var2.getNumRecords() == 0) {
               this.m_profileNum = 0;
               byte[] var8 = new byte[]{0, this.m_profileNum};
               var2.addRecord(var8, 0, 2);
               var8[0] = 1;
               var8[1] = 13;
               var2.addRecord(var8, 0, 2);
            }

            var2.closeRecordStore();
         } catch (Exception var5) {
            var5.printStackTrace();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2497, var5.toString());
         } catch (Error var6) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2497, var6.toString());
         }

         if (var1) {
            this.clearAllProfiles();
         }

      }

      public void getLastOpenProfile() {
         this.createProfile();
         boolean var1 = false;

         try {
            byte[] var3 = new byte[2];
            RecordStore var4 = RecordStore.openRecordStore("currentProfile", false);
            RecordEnumeration var5 = var4.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);

            while(var5.hasNextElement()) {
               int var2 = var5.nextRecordId();
               var3 = var4.getRecord(var2);
               if (var3[0] == 0) {
                  this.m_profileNum = var3[1];
               } else if (var3[0] == 1) {
                  if (var3[1] != 13) {
                     var1 = true;
                     var3[0] = 1;
                     var3[1] = 13;
                     var4.setRecord(var2, var3, 0, 2);
                  }
               } else {
                  System.out.println("Unknown profile data: " + var2);
               }
            }

            var5.destroy();
            var4.closeRecordStore();
         } catch (Exception var6) {
            var6.printStackTrace();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2547, var6.toString());
         } catch (Error var7) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2547, var7.toString());
         }

         if (var1) {
            this.clearAllProfiles();
         }

      }

      public void createRecords(boolean var1) {
         try {
            RecordStore var2 = RecordStore.openRecordStore("NFSMW_profile" + this.m_profileNum, true);
            if (var2.getNumRecords() != 7) {
               int var11;
               if (var2.getNumRecords() > 0) {
                  boolean var3 = false;
                  RecordEnumeration var4 = var2.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);

                  while(var4.hasNextElement()) {
                     var11 = var4.nextRecordId();
                     var2.deleteRecord(var11);
                  }

                  var4.destroy();
               }

               if (var1) {
                  this.Init();
                  this.setInitialCarSetups();
               }

               var11 = 1;

               for(int var12 = 0; var12 < this.m_partStatus.length; ++var12) {
                  var11 += this.m_partStatus[var12].length;
               }

               byte[] var13 = new byte[var11];
               var13[0] = 0;
               var13[1] = this.m_equipedCar;
               var2.addRecord(var13, 0, 2);
               var13[0] = 6;
               var13[1] = (byte)(this.m_nCopsCrashed >> 24);
               var13[2] = (byte)(this.m_nCopsCrashed >> 16);
               var13[3] = (byte)(this.m_nCopsCrashed >> 8);
               var13[4] = (byte)this.m_nCopsCrashed;
               var2.addRecord(var13, 0, 5);
               var13[0] = 1;
               int var5 = 0;

               while(true) {
                  int var6;
                  if (var5 >= 6) {
                     var2.addRecord(var13, 0, var13.length);
                     var13[0] = 2;

                     for(var5 = 0; var5 < this.m_missionStatus.length; ++var5) {
                        var13[var5 + 1] = this.m_missionStatus[var5];
                     }

                     var2.addRecord(var13, 0, this.m_missionStatus.length + 1);
                     var13[0] = 3;
                     var5 = 1;

                     int var7;
                     for(var6 = 0; var6 < this.m_partStatus.length; ++var6) {
                        for(var7 = 0; var7 < this.m_partStatus[var6].length; ++var7) {
                           var13[var5++] = this.m_partStatus[var6][var7];
                        }
                     }

                     var2.addRecord(var13, 0, var5);
                     var13[0] = 4;
                     byte[] var14 = this.intToByteArray(this.m_cash);

                     for(var7 = 0; var7 < 4; ++var7) {
                        var13[var7 + 1] = var14[var7];
                     }

                     var14 = this.intToByteArray(this.m_rep);

                     for(var7 = 0; var7 < 4; ++var7) {
                        var13[var7 + 5] = var14[var7];
                     }

                     var2.addRecord(var13, 0, 9);
                     var13[0] = 5;

                     for(var7 = 0; var7 < 5; ++var7) {
                        var14 = this.intToByteArray(this.m_LapTimes[var7]);

                        for(int var8 = 0; var8 < 4; ++var8) {
                           var13[1 + var7 * 4 + var8] = var14[var8];
                        }
                     }

                     var2.addRecord(var13, 0, this.m_LapTimes.length * 4 + 1);
                     break;
                  }

                  for(var6 = 0; var6 < 17; ++var6) {
                     var13[var5 * 17 + var6 + 1] = this.m_carSetups[var5][var6];
                  }

                  ++var5;
               }
            }

            var2.closeRecordStore();
         } catch (Exception var9) {
            var9.printStackTrace();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2679, var9.toString());
         } catch (Error var10) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2679, var10.toString());
         }

      }

      private void setInitialCarSetups() {
         for(int var1 = 0; var1 < 6; ++var1) {
            for(int var2 = 0; var2 < 17; ++var2) {
               this.m_carSetups[var1][var2] = TheGame.this.g_carSetups[var1][var2];
            }
         }

      }

      public void loadRecords() {
         if (!TheGame.this.system_bDemoMode) {
            this.createRecords(true);

            try {
               RecordStore var1 = RecordStore.openRecordStore("NFSMW_profile" + this.m_profileNum, false);
               int var2 = 1;

               for(int var3 = 0; var3 < this.m_partStatus.length; ++var3) {
                  var2 += this.m_partStatus[var3].length;
               }

               byte[] var12 = new byte[var2];
               boolean var4 = false;
               RecordEnumeration var5 = var1.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);

               while(true) {
                  label78:
                  while(var5.hasNextElement()) {
                     int var13 = var5.nextRecordId();
                     var12 = var1.getRecord(var13);
                     byte var6 = var12[0];
                     int var7;
                     int var8;
                     switch(var6) {
                     case 0:
                        this.m_currentCar = this.m_equipedCar = var12[1];
                        break;
                     case 1:
                        for(var7 = 0; var7 < 6; ++var7) {
                           for(var8 = 0; var8 < 17; ++var8) {
                              this.m_carSetups[var7][var8] = var12[var7 * 17 + var8 + 1];
                           }
                        }
                        break;
                     case 2:
                        var7 = 0;

                        while(true) {
                           if (var7 >= this.m_missionStatus.length) {
                              continue label78;
                           }

                           this.m_missionStatus[var7] = var12[var7 + 1];
                           ++var7;
                        }
                     case 3:
                        var7 = 1;
                        var8 = 0;

                        while(true) {
                           if (var8 >= this.m_partStatus.length) {
                              continue label78;
                           }

                           for(int var9 = 0; var9 < this.m_partStatus[var8].length; ++var9) {
                              this.m_partStatus[var8][var9] = var12[var7++];
                           }

                           ++var8;
                        }
                     case 4:
                        this.m_cash = this.byteArrayToInt(var12, 1);
                        this.m_rep = this.byteArrayToInt(var12, 5);
                        break;
                     case 5:
                        var8 = 0;

                        while(true) {
                           if (var8 >= 5) {
                              continue label78;
                           }

                           this.m_LapTimes[var8] = this.byteArrayToInt(var12, var8 * 4 + 1);
                           ++var8;
                        }
                     case 6:
                        this.m_nCopsCrashed = var12[1] << 24 | (var12[2] & 255) << 16 | (var12[3] & 255) << 8 | var12[4] & 255;
                        break;
                     default:
                        System.out.println("ERROR: unknown record ID " + var6);
                     }
                  }

                  var1.closeRecordStore();
                  break;
               }
            } catch (Exception var10) {
               var10.printStackTrace();
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2783, var10.toString());
            } catch (Error var11) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2783, var11.toString());
            }

         }
      }

      public void saveRecords() {
         if (!TheGame.this.system_bDemoMode) {
            try {
               RecordStore.deleteRecordStore("NFSMW_profile" + this.m_profileNum);
            } catch (RecordStoreNotFoundException var2) {
               ;
            } catch (Exception var3) {
               var3.printStackTrace();
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2801, var3.toString());
            } catch (Error var4) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2801, var4.toString());
            }

            this.createRecords(false);
         }
      }

      public void changeCurrentProfile(byte var1) {
         try {
            byte[] var3 = new byte[2];
            RecordStore var4 = RecordStore.openRecordStore("currentProfile", false);
            RecordEnumeration var5 = var4.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);

            while(var5.hasNextElement()) {
               int var2 = var5.nextRecordId();
               var3 = var4.getRecord(var2);
               if (var3[0] == 0) {
                  var3[0] = 0;
                  var3[1] = var1;
                  var4.setRecord(var2, var3, 0, 2);
                  this.m_profileNum = var1;
               }
            }

            var5.destroy();
            var4.closeRecordStore();
         } catch (Exception var6) {
            var6.printStackTrace();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2950, var6.toString());
         } catch (Error var7) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2950, var7.toString());
         }

      }

      public void clearAllProfiles() {
         try {
            RecordStore.deleteRecordStore("currentProfile");
         } catch (RecordStoreNotFoundException var3) {
            ;
         } catch (Exception var4) {
            var4.printStackTrace();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2965, var4.toString());
         } catch (Error var5) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2965, var5.toString());
         }

         this.getLastOpenProfile();
         byte var1 = this.m_profileNum;

         for(byte var2 = 0; var2 < 3; ++var2) {
            this.m_profileNum = var2;
            this.clearProfile();
         }

         this.m_profileNum = var1;
         this.changeCurrentProfile(this.m_profileNum);
      }

      public void clearProfile() {
         try {
            RecordStore.deleteRecordStore("NFSMW_profile" + this.m_profileNum);
         } catch (RecordStoreNotFoundException var2) {
            ;
         } catch (Exception var3) {
            var3.printStackTrace();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2994, var3.toString());
         } catch (Error var4) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2994, var4.toString());
         }

         this.Init();
         this.setInitialCarSetups();
      }

      public void encodeBytes(byte[] var1, int var2, int var3) {
         int var5 = var3 << 2;
         var1[var5] = (byte)(var2 >> 24);
         var1[var5 + 1] = (byte)(var2 >> 16);
         var1[var5 + 2] = (byte)(var2 >> 8);
         var1[var5 + 3] = (byte)var2;
      }

      public byte[] intToByteArray(int var1) {
         byte[] var2 = new byte[4];

         for(int var3 = 0; var3 < 4; ++var3) {
            int var4 = (var2.length - 1 - var3) * 8;
            var2[var3] = (byte)(var1 >> var4 & 255);
         }

         return var2;
      }

      public int byteArrayToInt(byte[] var1, int var2) {
         int var3 = 0;

         for(int var4 = 0; var4 < 4; ++var4) {
            int var5 = (3 - var4) * 8;
            var3 += (var1[var4 + var2] & 255) << var5;
         }

         return var3;
      }

      public int decodeBytes(byte[] var1, int var2) {
         int var3 = var2 << 2;
         int var4 = var1[var3] << 24 | (var1[var3 + 1] & 255) << 16 | (var1[var3 + 2] & 255) << 8 | var1[var3 + 3] & 255;
         return var4;
      }

      public int getTotalRep() {
         return this.m_carRep + this.m_rep;
      }
   }

   class NFSMW_Mission extends TheGame.MenuFriendly {
      int m_raceType;
      int m_trackPath;
      int m_nLaps;
      int m_raceSetup = 0;
      int m_reqRep;
      int m_reqMission;
      int m_cashWon;
      int m_repWon;
      int m_nTimeLimit;
      int m_nreqScore;
      int m_nreqChkpSpeed;
      int m_script;

      public NFSMW_Mission(int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16) {
         super(var4, var2, var3, 858);
         this.m_icon = this.getMissionIcon(var7);
         this.init(var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16);
      }

      public void init(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12) {
         this.m_raceType = var3;
         this.m_trackPath = var4;
         this.m_nLaps = var5;
         this.m_nTimeLimit = var9;
         this.m_nreqScore = var10;
         this.m_nreqChkpSpeed = var11;
         this.m_reqRep = var1;
         this.m_reqMission = var2;
         this.m_cashWon = var7;
         this.m_repWon = var8;
         this.m_raceSetup = var6;
         this.m_nreqScore = var10;
         this.m_script = var12;
      }

      public int getMissionIcon(int var1) {
         short var2 = 858;
         switch(var1) {
         case 0:
            var2 = 802;
            break;
         case 1:
            var2 = 806;
            break;
         case 2:
            var2 = 798;
            break;
         case 3:
            var2 = 810;
            break;
         case 4:
            var2 = 794;
         }

         return var2;
      }
   }

   class NFSMW_Track extends TheGame.MenuFriendly {
      public int m_previewImg;
      public boolean m_previewLoaded;
      public int m_trackPath;
      public String m_trackLength;

      public NFSMW_Track(int var2, int var3, int var4, int var5, int var6, String var7) {
         super(var4, var2, var3, 858);
         this.m_previewImg = var5;
         this.m_trackPath = var6;
         this.m_previewLoaded = false;
         this.m_trackLength = var7;
      }

      public void createTrackPreview() {
         if (!this.m_previewLoaded) {
            try {
               TheGame.this.asset_LoadImage(this.m_previewImg, false);
               this.m_previewLoaded = true;
            } catch (Exception var2) {
               var2.printStackTrace();
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2005, var2.toString());
            } catch (Error var3) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2005, var3.toString());
            }
         }

      }

      public void freeTrackPreview() {
         if (this.m_previewLoaded) {
            try {
               TheGame.this.asset_FreeImage(this.m_previewImg);
               this.m_previewLoaded = false;
            } catch (Exception var2) {
               var2.printStackTrace();
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2017, var2.toString());
            } catch (Error var3) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 2017, var3.toString());
            }
         }

      }
   }

   class NFSMW_TrackPath {
      public int m_AISpline;
      public int m_trafficSpline;
      public int m_checkpoints;
      public int m_nWorld;
      public int m_nTrack;
      public int m_nTrackFlags;

      public NFSMW_TrackPath(int var2, int var3, int var4, int var5, int var6, int var7) {
         this.m_AISpline = var4;
         this.m_trafficSpline = var5;
         this.m_checkpoints = var6;
         this.m_nWorld = var2;
         this.m_nTrack = var3;
         this.m_nTrackFlags = var7;
      }
   }

   class NFSMW_CarAppearance extends TheGame.MenuFriendly {
      int m_zStreakAngle;
      int[] m_yStreakTranslation = new int[2];
      Transform tmpTrans = new Transform();
      public String m_modelName;
      public int m_pointsID;
      public byte[] m_parts = new byte[17];
      public boolean m_refreshTexture;
      public boolean m_refreshWheels;
      public boolean m_refreshSpoiler;
      public Mesh m_carMesh;
      public Mesh m_reflectionMesh;
      public Mesh m_wheelMesh;
      public Mesh m_streakMesh;
      public Texture2D m_streakTexture;
      public Texture2D m_streakNitroTexture;
      public VertexBuffer m_streakVertices;
      public Appearance m_streakAppearance;
      public Appearance m_streakNitroAppearance;
      public TriangleStripArray m_streakIndexBuffer;
      public Mesh m_spoilerMesh;
      public Image m_imgClean;
      public Image2D m_img2D;
      public Texture2D m_texture;
      public boolean m_bUseingLowLOD;
      public boolean m_isTrafficCar;
      public int[] m_baseStats = new int[4];
      public int[] m_modStats = new int[4];
      public int m_lightAngle;
      public int m_wheelAngle;
      float[][] m_carWheelPos = new float[4][4];
      float[][] m_carLightPos = new float[4][3];
      float[] m_carSpoilerPos = new float[3];
      float[][] m_carStreakPos = new float[2][3];
      float[][] m_carStreakPosOffset = new float[2][2];

      public NFSMW_CarAppearance(int var2, int var3, int var4, String var5, int var6, int[] var7, boolean var8) {
         super(var4, var2, var3, 858);
         this.m_modelName = var5;
         this.m_pointsID = var6;

         int var9;
         for(var9 = 0; var9 < 17; ++var9) {
            this.m_parts[var9] = 0;
         }

         for(var9 = 0; var9 < 4; ++var9) {
            this.m_modStats[var9] = this.m_baseStats[var9] = var7[var9];
         }

         this.m_carMesh = this.m_reflectionMesh = this.m_wheelMesh = null;
         this.m_imgClean = null;
         this.m_isTrafficCar = var8;
         if (TheGame.this.g_carGfxHi == null) {
            TheGame.this.g_carGfxHi = TheGame.this.g_carImgHi.getGraphics();
         }

         if (TheGame.this.g_carGfxLow == null) {
            TheGame.this.g_carGfxLow = TheGame.this.g_carImgLow.getGraphics();
         }

         if (TheGame.this.g_carGfxTraffic == null) {
            TheGame.this.g_carGfxTraffic = TheGame.this.g_carImgTraffic.getGraphics();
         }

      }

      public NFSMW_CarAppearance(TheGame.NFSMW_CarAppearance var2) {
         super(var2.m_title, var2.m_type, var2.m_cmd, var2.m_icon);
         this.copy(var2);
      }

      public void copy(TheGame.NFSMW_CarAppearance var1) {
         int var2;
         for(var2 = 0; var2 < 17; ++var2) {
            this.m_parts[var2] = var1.m_parts[var2];
         }

         this.m_modelName = var1.m_modelName;
         this.m_pointsID = var1.m_pointsID;
         this.m_refreshTexture = this.m_refreshWheels = this.m_refreshSpoiler = true;
         if (var1.m_carMesh != null) {
            this.m_carMesh = (Mesh)var1.m_carMesh.duplicate();
         } else {
            this.m_carMesh = null;
         }

         if (var1.m_reflectionMesh != null) {
            this.m_reflectionMesh = (Mesh)var1.m_reflectionMesh.duplicate();
         } else {
            this.m_reflectionMesh = null;
         }

         if (var1.m_wheelMesh != null) {
            this.m_wheelMesh = (Mesh)var1.m_wheelMesh.duplicate();
         } else {
            this.m_wheelMesh = null;
         }

         if (var1.m_spoilerMesh != null) {
            this.m_spoilerMesh = (Mesh)var1.m_spoilerMesh.duplicate();
         } else {
            this.m_spoilerMesh = null;
         }

         if (var1.m_imgClean != null) {
            this.m_imgClean = Image.createImage(var1.m_imgClean);
         } else {
            this.m_imgClean = null;
         }

         this.m_bUseingLowLOD = var1.m_bUseingLowLOD;

         for(var2 = 0; var2 < 4; ++var2) {
            this.m_baseStats[var2] = var1.m_baseStats[var2];
         }

         for(var2 = 0; var2 < 4; ++var2) {
            this.m_modStats[var2] = var1.m_modStats[var2];
         }

         this.m_lightAngle = var1.m_lightAngle;
         this.m_wheelAngle = var1.m_wheelAngle;
         this.m_carWheelPos = var1.m_carWheelPos;
         this.m_carLightPos = var1.m_carLightPos;
         this.m_carSpoilerPos = var1.m_carSpoilerPos;
         this.m_carStreakPos = var1.m_carStreakPos;
         this.m_streakVertices = var1.m_streakVertices;
         this.m_streakIndexBuffer = var1.m_streakIndexBuffer;
         this.m_streakTexture = var1.m_streakTexture;
         this.m_streakAppearance = var1.m_streakAppearance;
         this.m_streakNitroAppearance = var1.m_streakNitroAppearance;
         this.m_streakNitroTexture = var1.m_streakNitroTexture;
         this.m_carStreakPosOffset = var1.m_carStreakPosOffset;
      }

      public void randomize(int var1) {
         for(int var2 = 0; var2 < 17; ++var2) {
            if (var2 == 0) {
               this.m_parts[var2] = 0;
            } else {
               label52:
               while(true) {
                  byte var3 = (byte)Math.abs(TheGame.this.system_GetRandom() >> 15 & 127);
                  this.m_parts[var2] = var3;
                  this.m_parts[var2] = (byte)(this.m_parts[var2] % TheGame.this.g_carParts[var2].length);
                  int var4;
                  if (var2 == 1) {
                     if (this.m_isTrafficCar && (this.m_parts[var2] == 0 || this.m_parts[var2] == 12 || this.m_parts[var2] == 13)) {
                        continue;
                     }

                     var4 = 0;

                     while(true) {
                        if (var4 >= var1) {
                           break label52;
                        }

                        if (TheGame.this.players[var4].m_ca.m_parts[var2] == this.m_parts[var2]) {
                           ;
                        }

                        ++var4;
                     }
                  }

                  if (var2 == 5) {
                     var4 = TheGame.this.system_GetRandom();
                     if (var4 < 0) {
                        this.m_parts[var2] = 0;
                     }
                  }
                  break;
               }

               if (this.m_parts[var2] < 0 || this.m_parts[var2] >= TheGame.this.g_carParts[var2].length) {
                  System.err.println("***********   WRONG " + this.m_parts[var2]);
               }
            }
         }

         this.m_refreshSpoiler = this.m_refreshTexture = this.m_refreshWheels = true;
      }

      public void constructCar(boolean var1) {
         try {
            if (this.m_carMesh == null || this.m_bUseingLowLOD != var1 || var1) {
               while(true) {
                  if (!TheGame.this.scene_BuildCar(this, var1, this.m_isTrafficCar)) {
                     this.m_refreshTexture = true;
                     this.m_refreshWheels = true;
                     this.m_refreshSpoiler = true;
                     this.m_bUseingLowLOD = var1;
                     break;
                  }
               }
            }

            Appearance var15;
            Appearance var18;
            PolygonMode var20;
            if (this.m_refreshTexture && this.m_carMesh != null) {
               int var2 = this.m_bUseingLowLOD ? 2 : 1;
               Image var3;
               int var4;
               int[] var5;
               if (this.m_bUseingLowLOD) {
                  var4 = this.m_imgClean.getWidth() * this.m_imgClean.getHeight();
                  var5 = new int[var4];
                  this.m_imgClean.getRGB(var5, 0, this.m_imgClean.getWidth(), 0, 0, this.m_imgClean.getWidth(), this.m_imgClean.getHeight());
                  int var6 = TheGame.this.g_carImgLow.getWidth() * TheGame.this.g_carImgLow.getHeight();
                  int[] var7 = new int[var6];

                  for(int var8 = 0; var8 < TheGame.this.g_carImgLow.getWidth(); ++var8) {
                     for(int var9 = 0; var9 < TheGame.this.g_carImgLow.getHeight(); ++var9) {
                        var7[TheGame.this.g_carImgLow.getWidth() * var9 + var8] = var5[this.m_imgClean.getWidth() * var9 * var2 + var8 * var2];
                     }
                  }

                  var18 = null;
                  int[] var22 = new int[var6];
                  System.arraycopy(var7, 0, var22, 0, var6);
                  this.TintWindows(var7, var22);
                  this.PaintCar(var7, var22, this.m_isTrafficCar);
                  TheGame.this.g_carGfxLow.drawRGB(var7, 0, TheGame.this.g_carImgLow.getWidth(), 0, 0, TheGame.this.g_carImgLow.getWidth(), TheGame.this.g_carImgLow.getHeight(), true);
                  this.ApplyVinyl(TheGame.this.g_carGfxLow);
                  var3 = TheGame.this.g_carImgLow;
                  Object var23 = null;
                  Object var21 = null;
               } else {
                  TheGame.this.g_carGfxHi.setClip(0, 0, this.m_imgClean.getWidth(), this.m_imgClean.getHeight());
                  TheGame.this.g_carGfxHi.drawImage(this.m_imgClean, 0, 0, 20);
                  TheGame.this.g_carGfxHi.setClip(0, 0, this.m_imgClean.getWidth(), this.m_imgClean.getHeight());
                  if (!this.m_isTrafficCar) {
                     this.ApplyBumpers(TheGame.this.g_carGfxHi);
                  }

                  var4 = this.m_imgClean.getWidth() * this.m_imgClean.getHeight();
                  var5 = new int[var4];
                  TheGame.this.g_carImgHi.getRGB(var5, 0, this.m_imgClean.getWidth(), 0, 0, this.m_imgClean.getWidth(), this.m_imgClean.getHeight());
                  int[] var19 = new int[var4];
                  System.arraycopy(var5, 0, var19, 0, var4);
                  if (this.m_isTrafficCar) {
                     this.PaintCar(var5, var19, this.m_isTrafficCar);
                     TheGame.this.g_carGfxTraffic.drawRGB(var5, 0, TheGame.this.g_carImgTraffic.getWidth(), 0, 0, TheGame.this.g_carImgTraffic.getWidth(), TheGame.this.g_carImgTraffic.getHeight(), true);
                     var3 = TheGame.this.g_carImgTraffic;
                  } else {
                     this.TintWindows(var5, var19);
                     this.PaintCar(var5, var19, this.m_isTrafficCar);
                     TheGame.this.g_carGfxHi.drawRGB(var5, 0, TheGame.this.g_carImgHi.getWidth(), 0, 0, TheGame.this.g_carImgHi.getWidth(), TheGame.this.g_carImgHi.getHeight(), true);
                     this.ApplyVinyl(TheGame.this.g_carGfxHi);
                     var3 = TheGame.this.g_carImgHi;
                  }

                  var20 = null;
                  var18 = null;
               }

               this.m_img2D = new Image2D(99, var3);
               if (this.m_img2D == null) {
                  System.out.println("FAILED TO CREATE IMG");
                  return;
               }

               this.m_texture = new Texture2D(this.m_img2D);
               if (this.m_texture == null) {
                  System.out.println("FAILED TO CREATE TEXTURE");
                  return;
               }

               this.m_img2D = null;
               this.m_texture.setFiltering(208, 210);
               this.m_texture.setWrapping(241, 241);
               this.m_texture.setBlending(228);
               var15 = this.m_carMesh.getAppearance(0);
               var15.setTexture(0, this.m_texture);
               var15.setCompositingMode(TheGame.this.compositingMode_ZWRITEREAD);
               this.m_refreshTexture = false;
               if (this.m_bUseingLowLOD) {
                  this.m_imgClean = null;
               }
            }

            if (this.m_isTrafficCar) {
               return;
            }

            if (this.m_refreshWheels && this.m_wheelMesh != null) {
               Texture2D var12 = null;
               String var14 = TheGame.this.g_carParts[7][this.m_parts[7]].m_partName;
               var12 = TheGame.this.scene_FindTexture(var14);
               if (var12 == null) {
                  Image var17 = TheGame.this.scene_LoadTexImage(var14);
                  var12 = TheGame.this.scene_CreateTexture(var14, var17, true, true);
                  var15 = null;
               }

               var15 = this.m_wheelMesh.getAppearance(0);
               var15.setTexture(0, var12);
            }

            if (this.m_refreshSpoiler && this.m_carMesh != null) {
               byte var13 = this.m_parts[6];
               if (var13 == 0) {
                  this.m_spoilerMesh = null;
               } else {
                  while(true) {
                     if (!TheGame.this.scene_OpenModel(TheGame.this.g_carParts[6][var13].m_partName, 4, true)) {
                        TheGame.MintMesh[] var16 = TheGame.this.scene_FindMintMesh(TheGame.this.g_carParts[6][var13].m_partName);
                        this.m_spoilerMesh = (Mesh)var16[0].m_mesh.duplicate();
                        var15 = this.m_carMesh.getAppearance(0);
                        var18 = (Appearance)this.m_spoilerMesh.getAppearance(0).duplicate();
                        var18.setTexture(0, var15.getTexture(0));
                        if (var18.getPolygonMode() == null) {
                           var18.setPolygonMode(TheGame.this.polygonMode_NoPersp);
                        }

                        var20 = (PolygonMode)var18.getPolygonMode().duplicate();
                        var20.setCulling(162);
                        var18.setPolygonMode(var20);
                        this.m_spoilerMesh.setAppearance(0, var18);
                        break;
                     }
                  }
               }
            }

            this.m_carStreakPosOffset[0] = new float[2];
            this.m_carStreakPosOffset[1] = new float[2];
            this.m_yStreakTranslation[0] = 0;
            this.m_yStreakTranslation[1] = 32768;
            this.calcStats();
         } catch (Exception var10) {
            var10.printStackTrace();
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 948, var10.toString());
         } catch (Error var11) {
            TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 948, var11.toString());
         }

      }

      void calcStats() {
         for(int var1 = 0; var1 < 4; ++var1) {
            this.m_modStats[var1] = this.m_baseStats[var1];

            for(int var2 = 0; var2 < 17; ++var2) {
               this.m_modStats[var1] += TheGame.this.g_carParts[var2][this.m_parts[var2]].m_stats[var1];
            }

            if (this.m_modStats[var1] < 0) {
               this.m_modStats[var1] = 0;
            } else if (this.m_modStats[var1] > TheGame.this.stats_max[var1]) {
               this.m_modStats[var1] = TheGame.this.stats_max[var1];
            }
         }

      }

      void setRefresh(int var1) {
         if (var1 == 7) {
            this.m_refreshWheels = true;
         } else if (var1 == 6) {
            this.m_refreshSpoiler = true;
         } else if (var1 < 10) {
            this.m_refreshTexture = true;
         }

      }

      public boolean isPartInstalled(int var1, byte var2) {
         return this.m_parts[var1] == var2;
      }

      public void testPart(int var1, byte var2) {
         this.setRefresh(var1);
         byte var3 = this.m_parts[var1];
         this.m_parts[var1] = var2;
         this.constructCar(false);
         this.m_parts[var1] = var3;
         this.setRefresh(var1);
      }

      public void setPart(int var1, byte var2) {
         if (this.m_parts[var1] != var2) {
            this.setRefresh(var1);
            this.m_parts[var1] = var2;
         }

      }

      public void setParts(byte[] var1) {
         for(int var2 = 0; var2 < 17; ++var2) {
            if (this.m_parts[var2] != var1[var2]) {
               this.setRefresh(var2);
               this.m_parts[var2] = var1[var2];
            }
         }

      }

      public final boolean render(Transform var1, TheGame.Car var2, boolean var3, boolean var4, boolean var5) {
         if (this.m_carMesh == null) {
            return false;
         } else {
            try {
               try {
                  TheGame.this.scene_g3d.render(this.m_carMesh, var1);
               } catch (Exception var20) {
                  var20.printStackTrace();
                  TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1053, var20.toString());
               } catch (Error var21) {
                  TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1053, var21.toString());
               }

               if (!var3 || this.m_isTrafficCar) {
                  return true;
               }

               int var8 = TheGame.this.game_cameraAngZ & 16777215;
               int var9 = this.m_lightAngle;
               int var10 = this.m_lightAngle - (TheGame.this.game_cameraAngZ & 16777215);
               if (var10 > 8388608) {
                  var10 -= 16777216;
               } else if (var10 < -8388608) {
                  var10 += 16777216;
               }

               float var11 = (float)Math.abs(var10) * 2.1457672E-5F;
               boolean var12 = true;
               boolean var13 = true;
               if (var2 != null && var2.m_bPlayer || (TheGame.this.scene_nCurrentScene & 4096) != 0) {
                  if (var11 > -10.0F && var11 < 10.0F) {
                     var12 = false;
                  } else if (var10 >= 0) {
                     var13 = false;
                  }
               }

               if (var12 && this.m_wheelMesh != null) {
                  if ((var2 == null || !var2.m_bPlayer) && (TheGame.this.scene_nCurrentScene & 4096) == 0) {
                     for(int var6 = 0; var6 < 4; ++var6) {
                        this.renderWheel(var1, var6, this.tmpTrans);
                     }
                  } else if (var13) {
                     this.renderWheel(var1, 1, this.tmpTrans);
                     this.renderWheel(var1, 3, this.tmpTrans);
                  } else {
                     this.renderWheel(var1, 0, this.tmpTrans);
                     this.renderWheel(var1, 2, this.tmpTrans);
                  }
               }

               if (this.m_spoilerMesh != null) {
                  this.tmpTrans.set(var1);
                  this.tmpTrans.postTranslate(this.m_carSpoilerPos[0], this.m_carSpoilerPos[1], this.m_carSpoilerPos[2]);
                  TheGame.this.scene_g3d.render(this.m_spoilerMesh, this.tmpTrans);
               }

               if (var2 != null && var2.m_nCopNum != 0) {
                  return true;
               }

               if (this.m_streakVertices != null && var2 != null && this.m_carStreakPos[0] != null && var2.GetMPH() > 60) {
                  int var14 = var2.m_velocityLastFrame - 5000;
                  if (var2.m_nNitroTimer > 0) {
                     var14 += (2500 - var2.m_nNitroTimer) * 2;
                  }

                  if (var14 > 0) {
                     float var15 = (float)var14 * 2.0E-4F;
                     if (this.m_zStreakAngle - this.m_lightAngle > 8388608) {
                        this.m_zStreakAngle -= 16777216;
                     } else if (this.m_zStreakAngle - this.m_lightAngle < -8388608) {
                        this.m_zStreakAngle += 16777216;
                     }

                     this.m_zStreakAngle -= this.m_lightAngle;
                     this.m_zStreakAngle = (this.m_zStreakAngle >> 4) * (TheGame.this.exp_Array[3] + ((TheGame.this.exp_Array[4] - TheGame.this.exp_Array[3]) * 32 >> 8) >> 4) >> 8;
                     this.m_zStreakAngle += this.m_lightAngle;
                     Appearance var16 = this.m_streakAppearance;
                     if (var2.m_nNitroTimer > 0) {
                        var16 = this.m_streakNitroAppearance;
                     }

                     for(int var17 = 0; var17 < 2; ++var17) {
                        this.tmpTrans.set(var1);
                        this.tmpTrans.postTranslate(this.m_carStreakPos[var17][0] + this.m_carStreakPosOffset[var17][0], this.m_carStreakPos[var17][1] + this.m_carStreakPosOffset[var17][1], this.m_carStreakPos[var17][2] - 2.0F);
                        this.tmpTrans.postRotate((float)(this.m_zStreakAngle - this.m_lightAngle) * 2.1457672E-5F, 0.0F, 1.0F, 0.0F);
                        this.tmpTrans.postRotate(-((float)var2.m_xAng * 2.1457672E-5F), 1.0F, 0.0F, 0.0F);
                        this.tmpTrans.postScale(var15, 1.0F, var15);
                        if (TheGame.this.game_state != 3) {
                           this.m_yStreakTranslation[var17] += var2.m_velocityLastFrame * 20 * 85 >> 12;
                           if (this.m_yStreakTranslation[var17] > 65535) {
                              this.m_carStreakPosOffset[var17][0] = (float)(TheGame.this.system_GetRandom() >> 24) / 512.0F;
                              this.m_carStreakPosOffset[var17][1] = (float)(TheGame.this.system_GetRandom() >> 24) / 512.0F;
                           }

                           this.m_yStreakTranslation[var17] &= 65535;
                        }

                        var16.getTexture(0).setTranslation(0.0F, (float)this.m_yStreakTranslation[var17] * -7.6293945E-6F, 0.0F);
                        var16.getTexture(0).translate(0.0F, (float)(-var2.m_velocityLastFrame * 4) * 1.0172526E-4F, 0.0F);
                        int var18 = 128 + (TheGame.this.sin_Array[this.m_yStreakTranslation[var17] + 49152 >> 8 & 255] + ((TheGame.this.sin_Array[1 + (this.m_yStreakTranslation[var17] + 49152 >> 8) & 255] - TheGame.this.sin_Array[this.m_yStreakTranslation[var17] + 49152 >> 8 & 255]) * (this.m_yStreakTranslation[var17] + 49152 - (this.m_yStreakTranslation[var17] + 49152 & -256)) >> 8) >> 7);
                        int var19 = var18 << 24 | 16777215;
                        this.m_streakVertices.setDefaultColor(var19);
                        TheGame.this.scene_g3d.render(this.m_streakVertices, this.m_streakIndexBuffer, var16, this.tmpTrans);
                     }
                  }
               }

               float var24 = 0.5F;
               if (var11 < 90.0F) {
                  var24 *= (60.0F - var11) / 60.0F;
                  if (TheGame.this.brakelights != null && var24 > 0.0F && var4) {
                     this.tmpTrans.set(var1);
                     this.tmpTrans.postTranslate(this.m_carLightPos[2][0], this.m_carLightPos[2][1], this.m_carLightPos[2][2]);
                     this.tmpTrans.postScale(var24, var24, var24);
                     TheGame.this.scene_g3d.render(TheGame.this.brakelights, this.tmpTrans);
                     this.tmpTrans.set(var1);
                     this.tmpTrans.postTranslate(this.m_carLightPos[3][0], this.m_carLightPos[3][1], this.m_carLightPos[3][2]);
                     this.tmpTrans.postScale(var24, var24, var24);
                     TheGame.this.scene_g3d.render(TheGame.this.brakelights, this.tmpTrans);
                  }
               } else if ((TheGame.this.scene_nCurrentScene & 4096) == 4096) {
                  var24 *= (80.0F - (180.0F - var11)) / 80.0F;
                  if (TheGame.this.headlights != null && var24 > 0.0F) {
                     this.tmpTrans.set(var1);
                     this.tmpTrans.postTranslate(this.m_carLightPos[0][0], this.m_carLightPos[0][1], this.m_carLightPos[0][2]);
                     this.tmpTrans.postScale(var24, var24, var24);
                     TheGame.this.scene_g3d.render(TheGame.this.headlights, this.tmpTrans);
                     this.tmpTrans.set(var1);
                     this.tmpTrans.postTranslate(this.m_carLightPos[1][0], this.m_carLightPos[1][1], this.m_carLightPos[1][2]);
                     this.tmpTrans.postScale(var24, var24, var24);
                     TheGame.this.scene_g3d.render(TheGame.this.headlights, this.tmpTrans);
                  }
               }
            } catch (Exception var22) {
               var22.printStackTrace();
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1232, var22.toString());
            } catch (Error var23) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1232, var23.toString());
            }

            return true;
         }
      }

      public void renderWheel(Transform var1, int var2, Transform var3) {
         var3.set(var1);
         var3.postTranslate(this.m_carWheelPos[var2][0], this.m_carWheelPos[var2][1], this.m_carWheelPos[var2][2]);
         var3.postRotate((float)(-this.m_wheelAngle >> 5), 1.0F, 0.0F, 0.0F);
         if (this.m_carWheelPos[var2][0] > 0.0F) {
            var3.postRotate(180.0F, 0.0F, 1.0F, 0.0F);
         }

         TheGame.this.scene_g3d.render(this.m_wheelMesh, var3);
      }

      public final boolean renderAlpha(Transform var1, TheGame.Car var2) {
         if (var2 != null && var2.m_ca.m_cmd == 7 && TheGame.this.brakelights != null && TheGame.this.policelights != null && TheGame.this.fire_sprites[0] != null) {
            int var3 = (Math.abs(var2.m_nID) + 1) * 100;
            if (var2.m_nDamagedLevel >= 38400 && var2.m_nCopNum != 0) {
               if (var2.m_nCopNum > 0) {
                  float var7 = (float)(var2.m_nDamagedLevel - 38400) * 1.953125E-4F;
                  if (var7 > 2.5F) {
                     var7 = 2.5F;
                  }

                  this.tmpTrans.set(var1);
                  this.tmpTrans.postTranslate(this.m_carSpoilerPos[0], this.m_carSpoilerPos[1] + var7 / 3.0F, this.m_carSpoilerPos[2]);
                  this.tmpTrans.postScale(var7, var7, var7);
                  int var5 = (TheGame.this.game_Time + var3 >> 7) % 3;
                  if (var5 == 0) {
                     TheGame.this.scene_g3d.render(TheGame.this.fire_sprites[0], this.tmpTrans);
                     this.tmpTrans.postTranslate((float)(TheGame.this.game_Time & 15) / 256.0F, 0.0F, 0.0F);
                     TheGame.this.scene_g3d.render(TheGame.this.fire_sprites[1], this.tmpTrans);
                  } else if (var5 == 1) {
                     TheGame.this.scene_g3d.render(TheGame.this.fire_sprites[1], this.tmpTrans);
                     this.tmpTrans.postTranslate((float)(TheGame.this.game_Time & 15) / 256.0F, 0.0F, 0.0F);
                     TheGame.this.scene_g3d.render(TheGame.this.fire_sprites[2], this.tmpTrans);
                  } else if (var5 == 2) {
                     TheGame.this.scene_g3d.render(TheGame.this.fire_sprites[2], this.tmpTrans);
                     this.tmpTrans.postTranslate((float)(TheGame.this.game_Time & 15) / 256.0F, 0.0F, 0.0F);
                     TheGame.this.scene_g3d.render(TheGame.this.fire_sprites[0], this.tmpTrans);
                  }
               }
            } else {
               boolean var4 = (TheGame.this.scene_timer + var3 >> 7 & 2) == 2;
               if (TheGame.this.game_state == 3) {
                  var4 = true;
               }

               if (var4) {
                  this.tmpTrans.set(var1);
                  this.tmpTrans.postTranslate(this.m_carLightPos[2][0], this.m_carLightPos[2][1], this.m_carLightPos[2][2]);
                  TheGame.this.scene_g3d.render(TheGame.this.policelights, this.tmpTrans);
               }

               if (TheGame.this.game_state == 3) {
                  var4 = true;
               } else {
                  var4 = ((TheGame.this.scene_timer + var3 >> 7) + 1 & 2) == 2;
               }

               if (var4) {
                  this.tmpTrans.set(var1);
                  this.tmpTrans.postTranslate(this.m_carLightPos[3][0], this.m_carLightPos[3][1], this.m_carLightPos[3][2]);
                  TheGame.this.scene_g3d.render(TheGame.this.brakelights, this.tmpTrans);
               }
            }
         }

         if (TheGame.this.showFlashEffect && TheGame.this.flash_effect_sprite != null) {
            TheGame.this.showFlashEffect = false;
            float var6 = 100.0F;
            this.tmpTrans.set(var1);
            this.tmpTrans.postTranslate(0.0F, 2.0F, -5.0F);
            this.tmpTrans.postScale(var6, var6, var6);
            TheGame.this.scene_g3d.render(TheGame.this.flash_effect_sprite, this.tmpTrans);
         }

         return true;
      }

      void ApplyVinyl(Graphics var1) {
         if (this.m_parts[5] != 0) {
            try {
               Graphics var2 = TheGame.m_CurrentGraphics;
               TheGame.m_CurrentGraphics = var1;
               TheGame.this.g_carParts[5][this.m_parts[5]].drawPartImg(this.m_bUseingLowLOD);
               var1.setClip(0, 0, TheGame.this.g_carImgHi.getWidth(), TheGame.this.g_carImgHi.getHeight());
               TheGame.m_CurrentGraphics = var2;
            } catch (Exception var3) {
               var3.printStackTrace();
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1349, var3.toString());
            } catch (Error var4) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1349, var4.toString());
            }

         }
      }

      void ApplyBumpers(Graphics var1) {
         Graphics var2;
         if (this.m_parts[8] != 0) {
            try {
               var2 = TheGame.m_CurrentGraphics;
               TheGame.m_CurrentGraphics = var1;
               TheGame.this.g_carParts[8][this.m_parts[8]].drawPartImg(this.m_bUseingLowLOD);
               var1.setClip(0, 0, TheGame.this.g_carImgHi.getWidth(), TheGame.this.g_carImgHi.getHeight());
               TheGame.m_CurrentGraphics = var2;
            } catch (Exception var5) {
               var5.printStackTrace();
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1368, var5.toString());
            } catch (Error var6) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1368, var6.toString());
            }
         }

         if (this.m_parts[9] != 0) {
            try {
               var2 = TheGame.m_CurrentGraphics;
               TheGame.m_CurrentGraphics = var1;
               TheGame.this.g_carParts[9][this.m_parts[9]].drawPartImg(this.m_bUseingLowLOD);
               var1.setClip(0, 0, TheGame.this.g_carImgHi.getWidth(), TheGame.this.g_carImgHi.getHeight());
               TheGame.m_CurrentGraphics = var2;
            } catch (Exception var3) {
               var3.printStackTrace();
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1385, var3.toString());
            } catch (Error var4) {
               TheGame.AssertExtra(0, "c:\\mobiledevelopment\\nfsmw_cn\\src\\player_profile.h", 1385, var4.toString());
            }
         }

      }

      void RGBtoHSL(int[] var1, int var2, int var3, int var4) {
         int var5 = 0;
         boolean var6 = false;
         int var10 = Math.min(var2, Math.min(var3, var4));
         int var11 = Math.max(var2, Math.max(var3, var4));
         int var12 = var11 - var10;
         int var13 = (var11 + var10) / 2;
         int var17;
         if (var12 == 0) {
            var5 = 0;
            var17 = 0;
         } else {
            if (var13 < 128) {
               var17 = (var12 << 8) / (var11 + var10);
            } else {
               var17 = (var12 << 8) / (512 - var11 - var10);
            }

            int var14 = ((var11 - var2 >> 3) + (var12 >> 1) << 8) / var12;
            int var15 = ((var11 - var3 >> 3) + (var12 >> 1) << 8) / var12;
            int var16 = ((var11 - var4 >> 3) + (var12 >> 1) << 8) / var12;
            if (var2 == var11) {
               var5 = var16 - var15;
            } else if (var3 == var11) {
               var5 = 85 + var14 - var16;
            } else if (var4 == var11) {
               var5 = 170 + var15 - var14;
            }

            if (var5 < 0) {
               var5 += 256;
            }

            if (var5 > 256) {
               var5 -= 256;
            }
         }

         var1[0] = var5;
         var1[1] = var17;
         var1[2] = var13;
      }

      int HSLtoRGB(int var1, int var2, int var3) {
         boolean var4 = false;
         boolean var5 = false;
         boolean var6 = false;
         boolean var7 = false;
         boolean var8 = false;
         int var9;
         int var10;
         int var11;
         if (var2 == 0) {
            var9 = var3;
            var10 = var3;
            var11 = var3;
         } else {
            int var12;
            if (var3 < 128) {
               var12 = var3 * (255 + var2) >> 8;
            } else {
               var12 = var3 + var2 - (var2 * var3 >> 8);
            }

            int var13 = 2 * var3 - var12;
            var9 = this.Hue_2_RGB(var13, var12, var1 + 85);
            var10 = this.Hue_2_RGB(var13, var12, var1);
            var11 = this.Hue_2_RGB(var13, var12, var1 - 85);
         }

         return var9 << 16 | var10 << 8 | var11;
      }

      int Hue_2_RGB(int var1, int var2, int var3) {
         if (var3 < 0) {
            var3 += 256;
         }

         if (var3 > 256) {
            var3 -= 256;
         }

         if (var3 * 6 < 256) {
            return var1 + ((var2 - var1) * 6 * var3 >> 8);
         } else if (var3 * 2 < 256) {
            return var2;
         } else {
            return var3 * 3 < 512 ? var1 + ((var2 - var1) * (170 - var3) * 6 >> 8) : var1;
         }
      }

      void PaintCar(int[] var1, int[] var2, boolean var3) {
         int var4 = var1.length;
         byte var5 = this.m_parts[1];
         byte var6 = this.m_parts[2];
         byte var7 = this.m_parts[3];
         String var8 = TheGame.this.g_carParts[0][this.m_parts[0]].m_partName;
         int[] var9 = null;
         Image var10;
         if (!var3 && var8.length() > 0) {
            var10 = TheGame.this.scene_LoadTexImage("/" + var8 + ".png");
            if (var10 == null) {
               return;
            }

            int var11 = var10.getWidth() * var10.getHeight();
            var9 = new int[var11];
            var10.getRGB(var9, 0, var10.getWidth(), 0, 0, var10.getWidth(), var10.getHeight());
         }

         int[] var35 = new int[3];
         int[][] var36 = new int[3][3];
         int[] var12 = new int[3];
         int[] var13 = new int[3];
         int[] var14 = new int[3];
         int var15 = (16711680 & var2[0]) >> 16;
         int var16 = ('\uff00' & var2[0]) >> 8;
         int var17 = 255 & var2[0];
         this.RGBtoHSL(var35, var15, var16, var17);
         int var18 = (16711680 & TheGame.this.stockColors[var5]) >> 16;
         int var19 = ('\uff00' & TheGame.this.stockColors[var5]) >> 8;
         int var20 = 255 & TheGame.this.stockColors[var5];
         this.RGBtoHSL(var36[0], var18, var19, var20);
         var13[0] = 255 + var36[0][1] - var35[1];
         var14[0] = 255 + var36[0][2] - var35[2];
         var18 = (16711680 & TheGame.this.stockColors[var6]) >> 16;
         var19 = ('\uff00' & TheGame.this.stockColors[var6]) >> 8;
         var20 = 255 & TheGame.this.stockColors[var6];
         this.RGBtoHSL(var36[1], var18, var19, var20);
         var13[1] = 255 + var36[1][1] - var35[1];
         var14[1] = 255 + var36[1][2] - var35[2];
         var18 = (16711680 & TheGame.this.stockColors[var7]) >> 16;
         var19 = ('\uff00' & TheGame.this.stockColors[var7]) >> 8;
         var20 = 255 & TheGame.this.stockColors[var7];
         this.RGBtoHSL(var36[2], var18, var19, var20);
         var13[2] = 255 + var36[2][1] - var35[1];
         var14[2] = 255 + var36[2][2] - var35[2];
         byte var21 = 0;
         byte var22 = 1;
         if (this.m_bUseingLowLOD) {
            var22 = 2;
         }

         boolean var23 = false;

         for(int var24 = 0; var24 < var4; ++var24) {
            int var25 = (16711680 & var1[var24]) >> 16;
            int var26 = ('\uff00' & var1[var24]) >> 8;
            int var27 = 255 & var1[var24];
            int var28 = (16711680 & var2[var24]) >> 16;
            int var29 = ('\uff00' & var2[var24]) >> 8;
            int var30 = 255 & var2[var24];
            if (var9 != null) {
               int var40 = var9[var24 * var22] & 16777215;
               if (var40 == 16777215) {
                  var21 = 0;
               } else if (var40 == 0) {
                  var21 = 2;
               } else {
                  var21 = 1;
               }
            }

            if (var29 > var28 * 2) {
               this.RGBtoHSL(var12, var28, var29, var30);
               int var31 = var12[1] * var13[var21] >> 8;
               if (var31 > 255) {
                  var31 = 255;
               }

               int var32 = var12[2] * var14[var21] >> 8;
               if (var32 > 255) {
                  var32 = 255;
               }

               int var33 = this.HSLtoRGB(var36[var21][0], var31, var32);
               var1[var24] = -16777216 | var33;
            }
         }

         var10 = null;
         var36 = (int[][])null;
         Object var37 = null;
         Object var38 = null;
         Object var39 = null;
         Object var34 = null;
      }

      void TintWindows(int[] var1, int[] var2) {
         int[] var3 = null;
         int[] var4 = null;
         Image var5;
         int var6;
         if (var3 == null) {
            var5 = TheGame.this.scene_LoadTexImage("/window_tint.png");
            var6 = var5.getWidth() * var5.getHeight();
            var3 = new int[var6];
            var5.getRGB(var3, 0, var5.getWidth(), 0, 0, var5.getWidth(), var5.getHeight());
         }

         if (var4 == null) {
            var5 = TheGame.this.scene_LoadTexImage("/window_tint_lod.png");
            var6 = var5.getWidth() * var5.getHeight();
            var4 = new int[var6];
            var5.getRGB(var4, 0, var5.getWidth(), 0, 0, var5.getWidth(), var5.getHeight());
         }

         if (this.m_parts[4] != 0) {
            int[] var18 = this.m_bUseingLowLOD ? var4 : var3;
            var6 = var1.length;
            int var7 = TheGame.this.stockColors[this.m_parts[4]];
            int var8 = (16711680 & var7) >> 16;
            int var9 = ('\uff00' & var7) >> 8;
            int var10 = 255 & var7;

            for(int var11 = 0; var11 < var6; ++var11) {
               if ((16777215 & var18[var11]) == 0) {
                  int var12 = (16711680 & var1[var11]) >> 16;
                  int var13 = ('\uff00' & var1[var11]) >> 8;
                  int var14 = 255 & var1[var11];
                  int var15 = (16711680 & var2[var11]) >> 16;
                  int var16 = ('\uff00' & var2[var11]) >> 8;
                  int var17 = 255 & var2[var11];
                  if (var16 < var15 << 1) {
                     var12 = var15 * var8 >> 7;
                     var13 = var16 * var9 >> 7;
                     var14 = var17 * var10 >> 7;
                     if (var12 > 255) {
                        var12 = 255;
                     }

                     if (var13 > 255) {
                        var13 = 255;
                     }

                     if (var14 > 255) {
                        var14 = 255;
                     }

                     var1[var11] = -16777216 | 16711680 & var12 << 16 | '\uff00' & var13 << 8 | 255 & var14;
                  }
               }
            }

         }
      }

      void free() {
         TheGame.this.scene_FreeMintMesh(this.m_modelName);
         this.m_carMesh = null;
         this.m_reflectionMesh = null;
         this.m_wheelMesh = null;
         this.m_streakMesh = null;
         this.m_streakTexture = null;
         this.m_streakNitroTexture = null;
         this.m_streakVertices = null;
         this.m_streakAppearance = null;
         this.m_streakNitroAppearance = null;
         this.m_streakIndexBuffer = null;
         this.m_carStreakPosOffset[0] = null;
         this.m_carStreakPosOffset[1] = null;
         this.m_spoilerMesh = null;
         this.m_imgClean = null;
         this.m_img2D = null;
         this.m_texture = null;
         this.m_bUseingLowLOD = false;
      }
   }

   class NFSMW_CarPart extends TheGame.MenuFriendly {
      public String m_partName;
      public int m_partVisualID;
      public int m_partVisualID_LOD;
      public boolean m_bLoaded;
      Mesh m_mesh;
      public int[] m_stats = new int[4];
      public int m_cost;
      public int m_repRequired;

      public NFSMW_CarPart(int var2, int var3, int var4, int var5, int var6, int[] var7) {
         super(var4, var2, var3, 858);
         this.m_icon = this.getPartIcon(var2);

         for(int var8 = 0; var8 < 4; ++var8) {
            this.m_stats[var8] = var7[var8];
         }

         this.m_partName = null;
         this.m_partVisualID = this.m_partVisualID_LOD = -1;
         this.m_repRequired = var5;
         this.m_cost = var6;
         this.m_bLoaded = false;
      }

      public NFSMW_CarPart(int var2, int var3, int var4, String var5, int var6, int var7, int[] var8) {
         super(var4, var2, var3, 858);
         this.m_icon = this.getPartIcon(var2);

         for(int var9 = 0; var9 < 4; ++var9) {
            this.m_stats[var9] = var8[var9];
         }

         this.m_partName = var5;
         this.m_partVisualID = this.m_partVisualID_LOD = -1;
         this.m_repRequired = var6;
         this.m_cost = var7;
         this.m_bLoaded = false;
      }

      public NFSMW_CarPart(int var2, int var3, int var4, int var5, int var6, int var7, int var8, int[] var9) {
         super(var4, var2, var3, 858);
         this.m_icon = this.getPartIcon(var2);

         for(int var10 = 0; var10 < 4; ++var10) {
            this.m_stats[var10] = var9[var10];
         }

         this.m_partName = null;
         this.m_partVisualID = var5;
         this.m_partVisualID_LOD = var6;
         this.m_repRequired = var7;
         this.m_cost = var8;
         this.m_bLoaded = false;
      }

      public void drawPartImg(boolean var1) throws Exception {
         int var2 = var1 ? this.m_partVisualID_LOD : this.m_partVisualID;
         if (var2 != -1) {
            TheGame.this.asset_LoadImage(var2, false);
            TheGame.this.asset_DrawImage(var2, 0, 0);
            TheGame.this.asset_FreeImage(var2);
         }
      }

      public int getPartIcon(int var1) {
         short var2 = 858;
         switch(var1) {
         case 4096:
            var2 = 866;
            break;
         case 8192:
            var2 = 866;
            break;
         case 16384:
            var2 = 866;
            break;
         case 32768:
            var2 = 866;
            break;
         case 65536:
            var2 = 846;
            break;
         case 131072:
            var2 = 850;
            break;
         case 262144:
            var2 = 854;
            break;
         case 524288:
            var2 = 858;
            break;
         case 1048576:
            var2 = 870;
            break;
         case 2097152:
            var2 = 862;
            break;
         case 4194304:
            var2 = 836;
            break;
         case 8388608:
            var2 = 816;
            break;
         case 16777216:
            var2 = 828;
            break;
         case 33554432:
            var2 = 820;
            break;
         case 67108864:
            var2 = 824;
            break;
         case 134217728:
            var2 = 840;
            break;
         case 268435456:
            var2 = 832;
         }

         return var2;
      }
   }

   class MenuFriendly {
      public int m_title;
      public int m_type;
      public int m_cmd;
      public int m_icon;

      public MenuFriendly(int var2, int var3, int var4, int var5) {
         this.m_title = var2;
         this.m_type = var3;
         this.m_cmd = var4;
         this.m_icon = var5;
      }
   }
}
