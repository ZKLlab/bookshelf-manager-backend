package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.model.BarcodeResult;
import com.shuosc.books.web.service.BarcodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;


@SpringBootTest
@SpringJUnitConfig(classes = {BarcodeServiceImpl.class})
class BarcodeServiceImplTest {
    @Autowired
    private BarcodeService barcodeService;

    @Test
    void getBarcodeImage() throws IOException {
        var outImage = barcodeService.generateBarcodeImage("1234567", 300, 100);
        var outFile = new File("getBarcodeImage-local.png");
        ImageIO.write(outImage, "png", outFile);
    }

    @Test
    void getBarcodeResult() throws IOException {
        var img = ImageIO.read(new File("test-4-local.png"));
        var result = barcodeService.getBarcodeResult(img); // img to list
        var i = 0;
        System.out.println("Books detected: (Not sorted)");
        for (var barcodeResult : result) {
            System.out.println("Num: " + i++);
            System.out.println("Text: " + barcodeResult.getText());
            System.out.println("Pos: " + barcodeResult.getResultPoint());
        }
        var resultString = result
                .stream()
                .map(BarcodeResult::getText)
                .toArray(String[]::new);
        System.out.println(Arrays.toString(resultString));
        var sortedResultString = result
                .stream()
                .sorted(Comparator.comparing(BarcodeResult::isHorizontal)
                        .thenComparingInt(BarcodeResult::getResultPoint))
                .map(BarcodeResult::getText)
                .toArray(String[]::new);
        System.out.println(Arrays.toString(sortedResultString));

        // var rightSequence = new ArrayList<String>();
        // rightSequence.add("5833616");
        // rightSequence.add("9149478");
        // rightSequence.add("5297170"); // for testing
        // // n stands for the seq of the new book put into the existed seq
        // System.out.println("Is the array sorted? " + barcodeService.isSorted(3, result, rightSequence));
        // Assert.isTrue(barcodeService.isSorted(3, result, rightSequence), "The array should be sorted");
    }
}