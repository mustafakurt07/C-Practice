package file; /**
 * File: Negative.java
 * 
 * Description:
 * Convert color image to negative.
 * 
 * @author Yusuf Shakeel
 * Date: 27-01-2014 mon
 *
 * www.github.com/yusufshakeel/Java-Image-Processing-Project
 */

import java.awt.image.BufferedImage;

public class Negative{
    public static BufferedImage negative(BufferedImage img){
        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();
        
        //convert to negative
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);
                
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                
                //subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                
                //set new RGB value
                p = (a<<24) | (r<<16) | (g<<8) | b;
                
                img.setRGB(x, y, p);
            }
        }
        return img;
    }
}