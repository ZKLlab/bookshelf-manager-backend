package com.shuosc.books.web.service;

import com.shuosc.books.web.model.BarcodeResult;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public interface BarcodeService {
    BufferedImage getBarcodeImage(String string, int width, int height);
    ArrayList<BarcodeResult> getBarcodeResult(BufferedImage img);
    boolean isSorted(int n, ArrayList<BarcodeResult> barcodeResult, ArrayList<String> rightSequence);
}
