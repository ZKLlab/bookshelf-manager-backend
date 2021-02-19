package com.shuosc.books.web.service;

import java.awt.image.BufferedImage;


public interface BarcodeService {
    BufferedImage getBarcodeImage(String string, int width, int height);
}
