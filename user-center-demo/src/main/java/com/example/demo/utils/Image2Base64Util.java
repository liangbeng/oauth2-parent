package com.example.demo.utils;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Image2Base64Util {


    /**
     * BufferedImage 转换为 base64编码
     * @param bufferedImage
     * @return
     */
    public static String bufferedImageToBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", stream);
        Base64 base = new Base64();
        String base64 = base.encodeToString(stream.toByteArray());
        return "data:image/png;base64," + base64;
    }

    /**
     * base64编码 转换为 BufferedImage
     * @param base64
     * @return
     */
    public static BufferedImage base64ToBufferedImage(String base64) throws IOException {
        Base64 base = new Base64();
        byte[] image = base.decode(base64.replace("data:image/png;base64,", ""));
        InputStream stream = new ByteArrayInputStream(image);
        return ImageIO.read(stream);
    }
}
