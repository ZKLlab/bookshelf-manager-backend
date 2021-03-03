package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.service.BarcodeService;
import com.shuosc.books.web.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


@Service
public class LabelServiceImpl implements LabelService {
    private static final double A = 75;
    private static final double B = 35;
    private static final double C = 3;
    private static final double D = 25;
    private static final double E = 2;
    private static final double F = 20;
    private static final double G = 5;
    private static final double H = 2;
    private static final double I = 6;

    private static final double M = 23.62204724409449;
    private static final int N = 3;

    private static final float FONT_CALL_NUMBER_DEFAULT_SIZE = 110.0f;
    private static final float FONT_BARCODE_NUMBER_SIZE = 100.0f;

    private final BarcodeService barcodeService;

    private Font semiboldFont;
    private Font boldFont;

    @Autowired
    public LabelServiceImpl(BarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    public BufferedImage generateLabel(String callNum, String barcodeNum) throws IOException, FontFormatException {
        ensureFontsLoaded();
        var image = new BufferedImage((int) ((A + B * N) * M), (int) (F * M), BufferedImage.TYPE_BYTE_BINARY);
        var graphics = image.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        var sideBarcodeImg = barcodeService.generateBarcodeImage(barcodeNum, (int) (D * M), (int) ((F - 2 * G) * M));
        graphics.drawImage(sideBarcodeImg, (int) ((A - C - D) * M), (int) (G * M), null);
        graphics.setFont(getSuitableFont(graphics, semiboldFont, callNum, FONT_CALL_NUMBER_DEFAULT_SIZE, (int) ((A - 3 * C - D) * M)));
        graphics.setColor(Color.black);
        drawCenterText(graphics, callNum, (int) (C * M), (int) (F / 2 * M), false);

        var innerBarcodeImg = barcodeService.generateBarcodeImage(barcodeNum, (int) ((B - 2 * E) * M), (int) ((F - H - I) * M));
        graphics.setFont(boldFont.deriveFont(FONT_BARCODE_NUMBER_SIZE));
        var barcodeWithSpaces = barcodeNum.replaceAll("", " ").trim();
        for (var i = 0; i < N; i++) {
            graphics.drawImage(innerBarcodeImg, (int) ((A + B * i + E) * M), (int) (H * M), null);
            drawCenterText(graphics, barcodeWithSpaces, (int) ((A + B * (i + 0.5)) * M), (int) ((F - 0.5 * I) * M), true);
        }
        graphics.dispose();
        return image;
    }

    private Font getSuitableFont(Graphics2D graphics, Font font, String str,
                                 @SuppressWarnings("SameParameterValue") float initialFontSize,
                                 @SuppressWarnings("SameParameterValue") int maxWidth) {
        font = font.deriveFont(initialFontSize);
        var initialStringWidth = graphics.getFontMetrics(font).stringWidth(str);
        if (initialStringWidth > maxWidth) {
            font = font.deriveFont(initialFontSize * maxWidth / initialStringWidth);
        }
        return font;
    }

    private void drawCenterText(Graphics2D graphics, String str, int x, int y, boolean horizontal) {
        var metrics = graphics.getFontMetrics(graphics.getFont());
        y -= metrics.getHeight() * 0.5 - metrics.getAscent();
        if (horizontal) {
            x -= metrics.stringWidth(str) * 0.5;
        }
        graphics.drawString(str, x, y);
    }

    private void ensureFontsLoaded() throws IOException, FontFormatException {
        if (semiboldFont == null) {
            semiboldFont = Font.createFont(Font.TRUETYPE_FONT,
                    new ClassPathResource("font/SourceSans3-Semibold.ttf").getInputStream());
        }
        if (boldFont == null) {
            boldFont = Font.createFont(Font.TRUETYPE_FONT,
                    new ClassPathResource("font/SourceSans3-Bold.ttf").getInputStream());
        }
    }
}