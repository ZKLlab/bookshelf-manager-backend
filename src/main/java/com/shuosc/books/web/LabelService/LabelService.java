package com.shuosc.books.web.LabelService;

import com.shuosc.books.web.service.impl.BarcodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LabelService {
    public static final int A = 75;
    public static final int B = 35;
    public static final int C = 5;
    public static final int D = 30;
    public static final int E = 5;
    public static final int F = 20;
    public static final int G = 5;
    public static final int H = 4;
    public static final int I = 8;
    public static final double M = 23.622;

    @Autowired
    public BufferedImage generateLabel(String callNum, String barcodeNum) throws IOException {
        var barcodeService = new BarcodeServiceImpl();
        var barcodeImg = barcodeService.getBarcodeImage(barcodeNum, 300, 100);
        var image = new BufferedImage((int) (200 * M), (int) (20 * M), BufferedImage.TYPE_BYTE_BINARY);
        var graphics = image.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, (int) (200 * M), (int) (20 * M));
        graphics.drawImage(barcodeImg, (int) ((A - C - D) * M), (int) (G * M), (int) (D * M), (int) ((F - 2 * G) * M), null);
        graphics.drawImage(barcodeImg, (int) ((A + E) * M), (int) (H * M), (int) ((B - 2 * E) * M), (int) ((F - H - I) * M), null);
        graphics.drawImage(barcodeImg, (int) ((A + E + B) * M), (int) (H * M), (int) ((B - 2 * E) * M), (int) ((F - H - I) * M), null);
        graphics.drawImage(barcodeImg, (int) ((A + E + 2 * B) * M), (int) (H * M), (int) ((B - 2 * E) * M), (int) ((F - H - I) * M), null);
        var classPathResource = new ClassPathResource("resource/SourceSans3-Regular.ttf");
        var file = new File(classPathResource.getPath());
        Font font = new Font("SourceSans3-Regular", Font.PLAIN, 20);
        graphics.setFont(font);
        graphics.setColor(Color.black);
        graphics.drawString(callNum, 0, 0);
        graphics.dispose();
        return image;
    }
}
