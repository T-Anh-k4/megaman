package state;

import effect.CacheDataLoader;
import effect.FrameImage;
import gameobject.BackgroundMap;
import gameobject.BulletManager;
import gameobject.Camera;
import gameobject.DarkRaise;
import gameobject.FinalBoss;
import gameobject.MegaMan;
import gameobject.ParticularObject;
import gameobject.ParticularObjectManager;
import gameobject.PhysicalMap;
import gameobject.RedEyeDevil;
import gameobject.RobotR;
import gameobject.SmallRedGun;
import userinterface.GameFrame;
import userinterface.GamePanel;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;


public class GameWorldState extends State {
	
    private BufferedImage bufferedImage;
    private int lastState;

    public ParticularObjectManager particularObjectManager;
    public BulletManager bulletManager;

    public MegaMan megaMan;
   
    public PhysicalMap physicalMap;
    public BackgroundMap backgroundMap;
    public Camera camera;

    public static final int finalBossX = 3600;
    
    public static final int INIT_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEWIN = 4;
    public static final int PAUSEGAME = 5;
    
    public static final int INTROGAME = 0;
    public static final int MEETFINALBOSS = 1;
    
    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;
    
    public int storyTutorial = 0;
    public String[] texts1 = new String[4];

    public String textTutorial;
    public int currentSize = 1;
    
    private boolean finalbossTrigger = true;
    ParticularObject boss;
    
    FrameImage avatar = CacheDataLoader.getInstance().getFrameImage("avatar");
    
    private boolean canMove;
    
    private int numberOfLife = 3;
    
    public AudioClip bgMusic;
    
    private DialogBox dialogBox;
    
    public GameWorldState(GamePanel gamePanel){
            super(gamePanel);
        
        texts1[0] = "We are heros, and our mission is protecting our Home\nEarth....";
        texts1[1] = "There was a Monster from University on Earth in 10 years\n"
                     + "and we lived in the scare in that 10 years....";
        texts1[2] = "Now is the time for us, kill it and get freedom!....";
        texts1[3] = "      LET'S GO!.....";
        textTutorial = texts1[0];

        
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        megaMan = new MegaMan(400, 400, this);
        physicalMap = new PhysicalMap(0, 0, this);
        backgroundMap = new BackgroundMap(0, 0, this);
        camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
        bulletManager = new BulletManager(this);
        
        particularObjectManager = new ParticularObjectManager(this);
        particularObjectManager.addObject(megaMan);
        
        initEnemies();

        bgMusic = CacheDataLoader.getInstance().getSound("bgmusic");
        
        dialogBox = new DialogBox();
    }
    
    private void initEnemies(){
        ParticularObject redeye = new RedEyeDevil(1250, 410, this);
        redeye.setDirection(ParticularObject.LEFT_DIR);
        redeye.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye);
        
        ParticularObject smallRedGun = new SmallRedGun(1600, 180, this);
        smallRedGun.setDirection(ParticularObject.LEFT_DIR);
        smallRedGun.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun);
        
        ParticularObject darkraise = new DarkRaise(2000, 200, this);
        darkraise.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise);
        
        ParticularObject darkraise2 = new DarkRaise(2800, 350, this);
        darkraise2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise2);
        
        ParticularObject robotR = new RobotR(900, 400, this);
        robotR.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR);
        
        ParticularObject robotR2 = new RobotR(3400, 350, this);
        robotR2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR2);
               
        ParticularObject redeye2 = new RedEyeDevil(2500, 500, this);
        redeye2.setDirection(ParticularObject.LEFT_DIR);
        redeye2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye2);
        
        ParticularObject redeye3 = new RedEyeDevil(3450, 500, this);
        redeye3.setDirection(ParticularObject.LEFT_DIR);
        redeye3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye3);
        
        ParticularObject redeye4 = new RedEyeDevil(500, 1190, this);
        redeye4.setDirection(ParticularObject.RIGHT_DIR);
        redeye4.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye4);
        
        ParticularObject darkraise3 = new DarkRaise(750, 650, this);
        darkraise3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise3);
       
        ParticularObject robotR3 = new RobotR(1500, 1150, this);
        robotR3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR3);
                        
        ParticularObject smallRedGun2 = new SmallRedGun(1700, 980, this);
        smallRedGun2.setDirection(ParticularObject.LEFT_DIR);
        smallRedGun2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun2);
    }

    public void switchState(int state){
        previousState = this.state;
        this.state = state;
    }
    
    private void TutorialUpdate(){
        switch(tutorialState){
            case INTROGAME:
                
                if(storyTutorial == 0){
                    if(openIntroGameY < 450) {
                        openIntroGameY+=4;
                    }else storyTutorial ++;
                    
                }else{
                
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
            case MEETFINALBOSS:
                if(storyTutorial == 0){
                    if(openIntroGameY >= 450) {
                        openIntroGameY-=1;
                    }
                    if(camera.getPosX() < finalBossX){
                        camera.setPosX(camera.getPosX() + 2);
                    }
                    
                    if(megaMan.getPosX() < finalBossX + 150){
                        megaMan.setDirection(ParticularObject.RIGHT_DIR);
                        megaMan.run();
                        megaMan.Update();
                    }else{
                        megaMan.stopRun();
                    }
                    
                    if(openIntroGameY < 450 && camera.getPosX() >= finalBossX && megaMan.getPosX() >= finalBossX + 150){ 
                        camera.lock();
                        storyTutorial++;
                        megaMan.stopRun();
                        physicalMap.phys_map[14][120] = 1;
                        physicalMap.phys_map[15][120] = 1;
                        physicalMap.phys_map[16][120] = 1;
                        physicalMap.phys_map[17][120] = 1;
                        
                        backgroundMap.map[14][120] = 17;
                        backgroundMap.map[15][120] = 17;
                        backgroundMap.map[16][120] = 17;
                        backgroundMap.map[17][120] = 17;
                    }
                    
                }else{
                
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
          }
    }
    
    private void drawString(Graphics2D g2, String text, int x, int y){
        for(String str : text.split("\n"))
            g2.drawString(str, x, y+=g2.getFontMetrics().getHeight());
    }
    
    private void TutorialRender(Graphics2D g2){
        switch(tutorialState){
            case INTROGAME:
                int yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
                int y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                int y2 = yMid + openIntroGameY/2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                
                if(storyTutorial >= 1){
                    g2.drawImage(avatar.getImage(), 600, 350, null);
                    g2.setColor(Color.BLUE);
                    g2.fillRect(280, 450, 350, 80);
                    g2.setColor(Color.WHITE);
                    String text = textTutorial.substring(0, currentSize - 1);
                    drawString(g2, text, 290, 480);
                }
                
                break;
            case MEETFINALBOSS:
                yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
                y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                y2 = yMid + openIntroGameY/2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                break;
        }
    }
    
    public void Update(){
        
        switch(state){
            case INIT_GAME:                
                break;
            case TUTORIAL:
                TutorialUpdate();                
                break;
            case GAMEPLAY:
                particularObjectManager.UpdateObjects();
                bulletManager.UpdateObjects();
        
                physicalMap.Update();
                camera.Update();
                                
                if(megaMan.getPosX() > finalBossX && finalbossTrigger){
                    finalbossTrigger = false;
                    switchState(TUTORIAL);
                    tutorialState = MEETFINALBOSS;
                    storyTutorial = 0;
                    openIntroGameY = 550;
                    
                    boss = new FinalBoss(finalBossX + 700, 460, this);
                    boss.setTeamType(ParticularObject.ENEMY_TEAM);
                    boss.setDirection(ParticularObject.LEFT_DIR);
                    particularObjectManager.addObject(boss);

                }
                
                if(megaMan.getState() == ParticularObject.DEATH){
                    numberOfLife --;
                    if(numberOfLife >= 0){
                        megaMan.setBlood(100);
                        megaMan.setPosY(megaMan.getPosY() - 50);
                        megaMan.setState(ParticularObject.NOBEHURT);
                        particularObjectManager.addObject(megaMan);
                    }else{
                        switchState(GAMEOVER);
                        bgMusic.stop();
                    }
                }
                if(!finalbossTrigger && boss.getState() == ParticularObject.DEATH)
                    switchState(GAMEWIN);
                
                break;
            case GAMEOVER:                
                break;
            case GAMEWIN:                
                break;
        }       

    }

    public void Render(){

        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

        if(g2!=null){

            // NOTE: two lines below make the error splash white screen....
            // need to remove this line
            
            switch(state){
                case INIT_GAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("WELCOME TO MEGAMAN WORD", 395, 300);
                    g2.drawString("PRESS ENTER TO CONTINUE", 400, 320);
                    break;
                case PAUSEGAME:
                    break;
                case TUTORIAL:
                    backgroundMap.draw(g2);
                    if(tutorialState == MEETFINALBOSS){
                        particularObjectManager.draw(g2);
                    }
                    TutorialRender(g2);
                    
                    break;
                case GAMEWIN:
                case GAMEPLAY:
                    backgroundMap.draw(g2);
                    particularObjectManager.draw(g2);  
                    bulletManager.draw(g2);
                    
                    g2.setColor(Color.WHITE);
                    g2.drawRoundRect(19, 59, 102, 15,20,20);
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(19, 59, 102, 15,20,20);
                    g2.setColor(Color.red);
                    g2.drawRoundRect(20, 60, megaMan.getBlood(), 12,20,20);
                    g2.setColor(Color.red);
                    g2.fillRoundRect(20, 60, megaMan.getBlood(), 12,20,20);
                    
                    for(int i = 0; i < numberOfLife; i++){
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hearth").getImage(), 20 + i*40, 18, null);
                    }
                                       
                    if(state == GAMEWIN){
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("gamewin").getImage(), 300, 300, null);
                    }
                    
                    break;
                case GAMEOVER:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("GAME OVER!", 450, 300);
                    break;

            }
        }
    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }
    
    public class DialogBox {
        public void showOptions(String option1, String option2) {
            // Hiển thị hộp thoại cho người chơi với các lựa chọn option1 và option2
            int choice = JOptionPane.showOptionDialog(
                null, 
                "Pause Game:",  
                "Lựa chọn", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                new String[] {option1, option2}, 
                option1 // Lựa chọn mặc định
            );

            // Xử lý dựa trên lựa chọn của người chơi
            switch (choice) {
                case JOptionPane.YES_OPTION:
                    // Xử lý khi người chơi chọn option1 (Tiếp tục)
                    if (option1.equals("Continue")) {
                        if (state == PAUSEGAME) {
                            state = lastState;
                        }
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    // Xử lý khi người chơi chọn option2 (Thoát)
                    if (option2.equals("Exit")) {
                        if (state == PAUSEGAME) {
                            gamePanel.setState(new MenuState(gamePanel));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    

    //xử lý điều khiển
    @Override
    public void setPressedButton(int code) {
        switch(code){
            case KeyEvent.VK_W:
                megaMan.jump();
                break;
            case KeyEvent.VK_S:
                megaMan.dick();
                break;
            case KeyEvent.VK_D:
                megaMan.setDirection(megaMan.RIGHT_DIR);
                megaMan.run();
                break;
            case KeyEvent.VK_A:
                megaMan.setDirection(megaMan.LEFT_DIR);
                megaMan.run();
                break;
            case KeyEvent.VK_ENTER:
                if (state == PAUSEGAME) {
                    dialogBox.showOptions("Continue", "Exit");
                } else if (state == TUTORIAL && storyTutorial >= 1) {
                    // Xử lý nhấn phím Enter trong trạng thái hướng dẫn
                    if (storyTutorial <= 3) {
                        storyTutorial++;
                        currentSize = 1;
                        textTutorial = texts1[storyTutorial - 1];
                    } else {
                        switchState(GameWorldState.GAMEPLAY);
                    }

                    // for meeting boss tutorial
                    if (tutorialState == GameWorldState.MEETFINALBOSS) {
                        switchState(GameWorldState.GAMEPLAY);
                    }
                } else if (state == INIT_GAME) {
                    if (previousState == GameWorldState.GAMEPLAY)
                        switchState(GameWorldState.GAMEPLAY);
                    else switchState(GameWorldState.TUTORIAL);

                    bgMusic.loop();
                }
                break;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_J:
                megaMan.attackBulletNormal();
                break;
            case KeyEvent.VK_K:
                megaMan.attack();
                break;
            case KeyEvent.VK_L:
                megaMan.handleLButtonPress();
                canMove = false;
                break;
            // Xử lý khi nhấn phím P
            case KeyEvent.VK_P:
                if (state != PAUSEGAME) {
                    lastState = state;
                    state=PAUSEGAME;
                
                    dialogBox.showOptions("Continue", "Exit");
                }         
                break;
        }
    }
    

    @Override
    public void setReleasedButton(int code) {
        switch(code){
            case KeyEvent.VK_W:
                break;
            case KeyEvent.VK_S:
                megaMan.standUp();
                break;
            case KeyEvent.VK_D:
                if(megaMan.getSpeedX() > 0)
                    megaMan.stopRun();
                break;
            case KeyEvent.VK_A:
                if(megaMan.getSpeedX() < 0)
                    megaMan.stopRun();
                break;
            case KeyEvent.VK_ENTER:
                if(state == GAMEOVER || state == GAMEWIN) {
                    gamePanel.setState(new MenuState(gamePanel));
                } else if(state == PAUSEGAME) {
                    state = lastState;
                }
                break;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_ESCAPE:
                lastState = state;
                state = PAUSEGAME;
                break;
            case KeyEvent.VK_J:
                break;
            case KeyEvent.VK_K:
                break;
            case KeyEvent.VK_L:
                megaMan.handleLButtonRelease();
                canMove = true;
                break;
        }
    }

}
