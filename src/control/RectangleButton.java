package control;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RectangleButton extends Button {
    private Color textColor;

    public RectangleButton(String text, int posX, int posY, int width, int height, int paddingTextX, int paddingTextY, Color bgColor, Color textColor) {
        super(text, posX, posY, width, height, paddingTextX, paddingTextY, bgColor);
        this.textColor = textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    @Override
    public boolean isInButton(int x, int y) {
        return (enabled && x >= posX && x <= posX + width && y >= posY && y <= posY + height);
    }
    
    @Override
    public void draw(Graphics g) {
        if (enabled) {
            switch (state) {
                case NONE:
                    g.setColor(bgColor);
                    break;
                case PRESSED:
                    g.setColor(pressedBgColor);
                    break;
                case HOVER:
                    g.setColor(hoverBgColor);
                    break;
            }
        } else {
            g.setColor(Color.GRAY);
        }
        g.fillRect(posX, posY, width, height);
        
        g.setColor(Color.PINK);
        g.drawRect(posX, posY, width, height);
        g.drawRect(posX + 1, posY + 1, width - 2, height - 2);
        
        g.setColor(textColor); 
        Font arcadeFont = FontLoader.loadFont("ArcadeClassic.ttf", 14);
        g.setFont(arcadeFont);
        
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            FontMetrics fm = g2d.getFontMetrics(arcadeFont);
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int textX = posX + (width - textWidth) / 2;
            int textY = posY + (height - textHeight) / 2 + fm.getAscent();
            // Vẽ chuỗi văn bản
            g.drawString(text, textX, textY);
        } else {
            // Nếu g không phải là đối tượng Graphics2D, chỉ đơn giản vẽ chuỗi văn bản
            g.drawString(text, posX + paddingTextX, posY + paddingTextY);
        }
    }
}
