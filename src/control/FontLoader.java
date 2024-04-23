package control;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {

    public static Font loadFont(String fontFileName, float size) {
        try {
            File fontFile = new File("data/" + fontFileName);
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
