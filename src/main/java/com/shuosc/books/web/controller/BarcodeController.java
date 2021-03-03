package com.shuosc.books.web.controller;

import com.shuosc.books.web.model.BarcodeResult;
import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/barcode")
public class BarcodeController {
    private final BarcodeService barcodeService;

    @Autowired
    public BarcodeController(BarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    @PostMapping(path = "/image")
    public Return getResultFromImage(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            var image = ImageIO.read(inputStream);
            var result = barcodeService.getBarcodeResult(image)
                    .stream()
                    .map(BarcodeResult::getText)
                    .collect(Collectors.toCollection(ArrayList::new));
            return Return.success("", result);
        } catch (IOException e) {
            return Return.failure("未知错误");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
