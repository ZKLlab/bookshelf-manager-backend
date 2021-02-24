package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.LabelService.LabelService;
import com.shuosc.books.web.service.BarcodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


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

    @Test
    void getBarcodeResult() throws IOException {
        var img = ImageIO.read(new File("test-local.jpg"));
        var result = barcodeService.getBarcodeResult(img); // img to list
        var i = 0;
        System.out.println("Books detected: (Not sorted)");
        for (var barcodeResult : result) {
            System.out.println("Num: " + i++);
            System.out.println("Text: " + barcodeResult.getText());
            System.out.println("Pos: " + barcodeResult.getResultPoint());
        }
        var rightSequence = new ArrayList<String>();
        rightSequence.add("5833616");
        rightSequence.add("9149478");
        rightSequence.add("5297170"); // for testing
        // n stands for the seq of the new book put into the existed seq
        System.out.println("Is the array sorted? " + barcodeService.isSorted(3, result, rightSequence));
        Assert.isTrue(barcodeService.isSorted(3, result, rightSequence), "The array should be sorted");
    }

    @Test
    void testLabelService() throws IOException {
        var callNum = "K836.126.1=51/TP123.321";
        var barcodeNum = "5833616";
        var labelService = new LabelService();
        var outImage = labelService.generateLabel(callNum, barcodeNum);
        var outFile = new File("label-local.png");
        ImageIO.write(outImage, "png", outFile);
    }
}