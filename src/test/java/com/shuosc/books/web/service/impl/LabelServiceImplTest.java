package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.service.LabelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


@SpringBootTest
@SpringJUnitConfig(classes = {BarcodeServiceImpl.class, LabelServiceImpl.class})
class LabelServiceImplTest {
    @Autowired
    private LabelService labelService;

    @Test
    void generateLabel() throws IOException, FontFormatException {
        var callNum = "TP311.62/F75.2(2)"; // K836.126.1=51/TP123.321
        var barcodeNum = "5833616";
        var outImage = labelService.generateLabel(callNum, barcodeNum);
        var outFile = new File("label-local.png");
        ImageIO.write(outImage, "png", outFile);
    }
}