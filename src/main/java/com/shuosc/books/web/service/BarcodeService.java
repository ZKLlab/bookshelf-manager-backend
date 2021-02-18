package com.shuosc.books.web.service;

import java.awt.*;


public interface BarcodeService {
    Image getBarcodeImage(String string, int width, int height);
}
