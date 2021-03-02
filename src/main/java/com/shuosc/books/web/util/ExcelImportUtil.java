package com.shuosc.books.web.util;

import com.shuosc.books.web.model.Book;
import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.model.HoldingState;
import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.BookService;
import com.shuosc.books.web.service.HoldingService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelImportUtil {

    private ExcelImportUtil() {
    }

    public static Return importBooksFromFile(MultipartFile file,
                                             BookService bookService) {
        List<String> strings = new ArrayList<>();
        Workbook wb;
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        //把文件转换为输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //判断是否是xlsx后缀
            if (suffix.equals("xlsx"))
                wb = new XSSFWorkbook(inputStream);
            else
                wb = new HSSFWorkbook(inputStream);
            //获得第一张sheet表
            Sheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                String[] words = new String[16];
                //数据是从第三行开始，所以这里从第三行开始遍历
                for (int line = 2; line <= sheet.getLastRowNum(); line++) {
                    Row row = sheet.getRow(line);
                    if (row == null)
                        continue;
                    int i = 0;
                    for (; i < 16; i++) {
                        if (CellType.STRING != row.getCell(i).getCellType())
                            break;
                        words[i] = row.getCell(i).getStringCellValue();
                    }
                    if (i != 16)
                        continue;

                    String[] splits = words[7].split("[，,]");
                    Integer[] dates = new Integer[splits.length];
                    for (int j = 0; j < dates.length; j++)
                        dates[j] = Integer.parseInt(splits[j]);

                    Book book = new Book(words[0], words[1],
                            words[2], words[3], words[4],
                            words[5], words[6].split("[，,]"), dates,
                            words[8], words[9], words[10],
                            Integer.parseInt(words[11]),
                            Integer.parseInt(words[12]),
                            Integer.parseInt(words[13]),
                            words[14].matches("(是|TRUE|true)"), words[15]);
                    bookService.save(book);
                    System.out.println(line);
                }
            }
            //return Return.success("导入成功", "x", strings);
            return Return.success("导入成功");
        } catch (Exception e) {
            return Return.failure("未知异常, 导入失败");
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

    public static Return importHoldingsFromFile(MultipartFile file,
                                                HoldingService holdingService,
                                                BookService bookService) {
        List<String> strings = new ArrayList<>();
        Workbook wb;
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        //把文件转换为输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //判断是否是xlsx后缀
            if (suffix.equals("xlsx"))
                wb = new XSSFWorkbook(inputStream);
            else
                wb = new HSSFWorkbook(inputStream);
            //获得第一张sheet表
            Sheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                String[] words = new String[7];
                //数据是从第三行开始，所以这里从第三行开始遍历
                for (int line = 2; line <= sheet.getLastRowNum(); line++) {
                    Row row = sheet.getRow(line);
                    if (row == null)
                        continue;
                    int i = 0;
                    for (; i < 7; i++) {
                        if (CellType.STRING != row.getCell(i).getCellType())
                            break;
                        words[i] = row.getCell(i).getStringCellValue();
                    }

                    if (i != 7)
                        continue;

                    Book book = bookService.findById(new ObjectId(words[0]));
                    if (book == null)
                        continue;

                    HoldingState state = parseHoldingState(words[6]);
                    if (state == null)
                        continue;

                    Holding holding = new Holding(book, Long.parseLong(words[1]),
                            words[2], Integer.parseInt(words[3]), Integer.parseInt(words[4]),
                            words[5], state);
                    holdingService.save(holding);
                    System.out.println(line);
                }
            }
            //return Return.success("导入成功", "x", strings);
            return Return.success("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Return.failure("未知异常, 导入失败");
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

    private static HoldingState parseHoldingState(String state) {
        if (state.matches("(?i)lending"))
            return HoldingState.Lending;
        if (state.matches("(?i)closed"))
            return HoldingState.Closed;
        if (state.matches("(?i)lost"))
            return HoldingState.Lost;
        if (state.matches("(?i)unlisted"))
            return HoldingState.Unlisted;
        if (state.matches("(?i)damaged"))
            return HoldingState.Damaged;
        if (state.matches("(?i)reference"))
            return HoldingState.Reference;
        return null;
    }

    public static void main(String[] args) {
        System.out.println(replaceSpace("a a sas    sd"));
    }

    public static String replaceSpace(String s) {
        return s.replaceAll("\\s+", "%20");
    }
}

