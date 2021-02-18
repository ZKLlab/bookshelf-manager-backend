package com.shuosc.books.web.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.oned.Code128Writer;
import com.shuosc.books.web.service.BarcodeService;
import org.springframework.stereotype.Service;

import java.awt.*;


@Service
public class BarcodeServiceImpl implements BarcodeService {
    @Override
    public Image getBarcodeImage(String string, int width, int height) {
        var writer = new Code128Writer();
        var bitMatrix = writer.encode(string, BarcodeFormat.CODE_128, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
