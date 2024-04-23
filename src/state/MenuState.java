package state;


import control.Button;
import control.RectangleButton;
import userinterface.GameFrame;
import userinterface.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MenuState extends State {

    public final int NUMBER_OF_BUTTON = 3;
    private BufferedImage bufferedImage;
    private Graphics graphicsPaint;

    private Button[] buttons;
    private int buttonSelected = 0;

    private BufferedImage backgroundImage;
    

    public MenuState(GamePanel gamePanel) {
        super(gamePanel);
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        loadBackgroundImage();

        buttons = new Button[NUMBER_OF_BUTTON];
        initButtons();
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("data/background_menu.jpg"));
            backgroundImage = resizeImage(backgroundImage, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        int buttonX = (GameFrame.SCREEN_WIDTH - 100) / 2; 
        int buttonY = (GameFrame.SCREEN_HEIGHT - 40) / 2; 

        buttons[0] = new RectangleButton("Start", buttonX - 25, buttonY - 20, 100, 40, 15, 25, new Color(205, 133, 63), Color.BLACK);
        buttons[1] = new RectangleButton("Credis", buttonX - 25, buttonY + 50 , 100, 40, 15, 25, new Color(205, 133, 63), Color.BLACK);
        buttons[2] = new RectangleButton("Exit", buttonX - 25, buttonY + 120, 100, 40, 15, 25, new Color(205, 133, 63), Color.BLACK);

        


        for (Button button : buttons) {
            button.setHoverBgColor(new Color(255, 204, 153));
            button.setPressedBgColor(Color.GREEN);
        }
    }
    

    @Override
    public void Update() {
        for (int i = 0; i < NUMBER_OF_BUTTON; i++) {
            buttons[i].setState(i == buttonSelected ? Button.HOVER : Button.NONE);
        }
    }

    @Override
    public void Render() {
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            return;
        }
        graphicsPaint = bufferedImage.getGraphics();
        if (graphicsPaint == null) {
            graphicsPaint = bufferedImage.getGraphics();
            return;
        }
        graphicsPaint.setColor(Color.CYAN);
        graphicsPaint.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        renderBackground();
        renderButtons();
    }
    
    

    private void renderBackground() {
        if (backgroundImage != null) {	
            graphicsPaint.drawImage(backgroundImage,0 , 0, null);
        }
        
        BufferedImage menuBackgroundImage = null;
        try {
            menuBackgroundImage = ImageIO.read(new File("data/menu_background.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (menuBackgroundImage != null) {
            graphicsPaint.drawImage(menuBackgroundImage,(GameFrame.SCREEN_WIDTH ) / 3 , (GameFrame.SCREEN_HEIGHT ) / 5, null);
        }
    }

    private void renderButtons() {
        for (int i = 0; i < NUMBER_OF_BUTTON; i++) {
            Button button = buttons[i];
            boolean isSelected = (i == buttonSelected);
            if (isSelected) {
                // Nếu nút được chọn, tăng kích thước và dịch chuyển vị trí
                button.setWidth(button.getWidth() + 20);
                button.setHeight(button.getHeight() + 10);
                button.setX(button.getX() - 10);
                button.setY(button.getY() - 5);
            }
            button.draw(graphicsPaint);
            if (isSelected) {
                // Khôi phục lại kích thước và vị trí của nút
                button.setWidth(button.getWidth() - 20);
                button.setHeight(button.getHeight() - 10);
                button.setX(button.getX() + 10);
                button.setY(button.getY() + 5);
            }
            
            
            graphicsPaint.setColor(new Color(0, 128, 128)); // Xanh Navi
            graphicsPaint.setFont(new Font("ArcadeClassic", Font.BOLD, 60)); 
            graphicsPaint.drawString("MEGAMAN", GameFrame.SCREEN_WIDTH / 2 - 150, GameFrame.SCREEN_HEIGHT / 2 - 180);

            
        }
    }




    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
        switch (code) {
            case KeyEvent.VK_DOWN:
                buttonSelected = (buttonSelected + 1) % NUMBER_OF_BUTTON;
                break;
            case KeyEvent.VK_UP:
                buttonSelected = (buttonSelected - 1 + NUMBER_OF_BUTTON) % NUMBER_OF_BUTTON;
                break;
            case KeyEvent.VK_ENTER:
                actionMenu();
                break;
        }
    }

    @Override
    public void setReleasedButton(int code) {
    }

    private void actionMenu() {
        switch (buttonSelected) {
            case 0:
                gamePanel.setState(new GameWorldState(gamePanel));
                break;
            case 1:
                // Xử lý tiếp tục game
                break;
            case 2:
                System.exit(0);
                break;
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }
}
