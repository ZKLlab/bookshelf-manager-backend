package com.shuosc.books.web.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public interface LabelService {
    BufferedImage generateLabel(String callNum, String barcodeNum) throws IOException, FontFormatException;
}
