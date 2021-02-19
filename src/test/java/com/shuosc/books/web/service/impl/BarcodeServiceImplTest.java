package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.service.BarcodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


@SpringBootTest
@SpringJUnitConfig(classes = {BarcodeServiceImpl.class})
class BarcodeServiceImplTest {
    @Autowired
    private BarcodeService barcodeService;

    @Test
    void getBarcodeImage() throws IOException {
        var outImage = barcodeService.getBarcodeImage("1234567", 300, 100);
        var outFile = new File("getBarcodeImage-local.png");
        ImageIO.write(outImage, "png", outFile);
    }
}