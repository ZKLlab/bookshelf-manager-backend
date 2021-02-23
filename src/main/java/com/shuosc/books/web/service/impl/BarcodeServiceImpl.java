package com.shuosc.books.web.service.impl;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.oned.Code128Writer;
import com.shuosc.books.web.model.BarcodeResult;
import com.shuosc.books.web.service.BarcodeService;
import org.springframework.stereotype.Service;


import java.awt.image.BufferedImage;
import java.util.*;


@Service
public class BarcodeServiceImpl implements BarcodeService{
    @Override
    public BufferedImage getBarcodeImage(String string, int width, int height) {
        var writer = new Code128Writer();
        var bitMatrix = writer.encode(string, BarcodeFormat.CODE_128, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
    @Override
    public ArrayList<BarcodeResult> getBarcodeResult(BufferedImage img) {
        try{
            var multiFormatReader = new MultiFormatReader();
            var multiBarcodeReader = new GenericMultipleBarcodeReader(multiFormatReader);
            LuminanceSource source = new BufferedImageLuminanceSource(img);
            var bBitmap = new BinaryBitmap(new HybridBinarizer(source));
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            var zResults = multiBarcodeReader.decodeMultiple(bBitmap,hints);//get the result
            var Result = new ArrayList<BarcodeResult>();
            for (com.google.zxing.Result zResult : zResults) {
                var barcodeResult = new BarcodeResult();
                var string = zResult.toString();
                barcodeResult.setText(string);
                var p = zResult.getResultPoints();
                int[] start = {(int) p[0].getX(),(int) p[0].getY()};
                int[] end = {(int) p[1].getX(),(int) p[1].getY()};
                barcodeResult.setHorizontal(start[0] != end[0]);
                barcodeResult.setResultPoint(barcodeResult.isHorizontal() ? start[1] : start[0]);
                Result.add(barcodeResult);
            }//write the result into the objs
            return Result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Unexpected Result");
        return new ArrayList<>();
    }
    @Override
    public boolean isSorted(int n, ArrayList<BarcodeResult> barcodeResult, ArrayList<String> rightSequence){
        sortBarcodeResult(barcodeResult);
        n-=1;
        var seq = new ArrayList<String>();
        barcodeResult.forEach(result -> seq.add(result.getText()));
        var specifiedSeq = new ArrayList<String>();
        if(n==0){
            specifiedSeq.add(rightSequence.get(0));
            specifiedSeq.add(rightSequence.get(1));
        }
        else if (n == barcodeResult.size()-1){
            specifiedSeq.add(rightSequence.get(barcodeResult.size()-2));
            specifiedSeq.add(rightSequence.get(barcodeResult.size()-1));
        }
        else {
            specifiedSeq.add(rightSequence.get(n-1));
            specifiedSeq.add(rightSequence.get(n));
            specifiedSeq.add(rightSequence.get(n+1));
        }
        System.out.println("Sequence recognized: "+seq);
        if (Collections.indexOfSubList(seq,specifiedSeq) >=0){
            return true;
        }
        Collections.reverse(specifiedSeq);
        return Collections.indexOfSubList(seq, specifiedSeq) >=0 ;
    }
    private void sortBarcodeResult(ArrayList<BarcodeResult> barcodeResult){
        barcodeResult.sort((b1, b2) -> {
            if (b1.getResultPoint() == b2.getResultPoint())
                return 0;
            return b1.getResultPoint() > b2.getResultPoint() ? -1 : 1;
        });
    }//used to sort the codes
}
