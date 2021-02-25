package com.shuosc.books.web.service;

import com.shuosc.books.web.model.BarcodeResult;

import java.awt.image.BufferedImage;
import java.util.List;


public interface BarcodeService {
    BufferedImage generateBarcodeImage(String string, int width, int height);

    List<BarcodeResult> getBarcodeResult(BufferedImage img);

    boolean isSorted(int n, List<BarcodeResult> barcodeResult, List<String> rightSequence);
}
