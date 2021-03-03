package com.shuosc.books.web.controller;

import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
@RequestMapping(value = "/api")
public class LabelController {
    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping(path = "/label", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImageWithCallNumberAndBarcode(@RequestParam String callNumber,
                                                                   @RequestParam String barcode) throws IOException, FontFormatException {
        if (callNumber.isBlank() || barcode.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        var image = labelService.generateLabel(callNumber, barcode);
        var outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return ResponseEntity.ok(outputStream.toByteArray());
    }
}
