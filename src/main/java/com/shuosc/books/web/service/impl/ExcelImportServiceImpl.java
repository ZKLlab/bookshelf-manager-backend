package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.enums.HoldingState;
import com.shuosc.books.web.model.Book;
import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.BookService;
import com.shuosc.books.web.service.ExcelImportService;
import com.shuosc.books.web.service.HoldingService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ExcelImportServiceImpl implements ExcelImportService {
    private final BookService bookService;
    private final HoldingService holdingService;

    @Autowired
    public ExcelImportServiceImpl(BookService bookService, HoldingService holdingService) {
        this.bookService = bookService;
        this.holdingService = holdingService;
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

    @Override
    @Transactional
    public Return importBooksFromFile(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            var workbook = new XSSFWorkbook(inputStream);
            // 获得第一张sheet表
            var sheet = workbook.getSheetAt(0);
            if (sheet != null) {
                var words = new String[15];
                // 数据是从第三行开始，所以这里从第三行开始遍历
                for (int line = 2; line <= sheet.getLastRowNum(); line++) {
                    Row row = sheet.getRow(line);
                    if (row == null)
                        continue;
                    int i = 0;
                    for (; i < 15; i++) {
                        if (CellType.STRING != row.getCell(i).getCellType())
                            break;
                        words[i] = row.getCell(i).getStringCellValue();
                    }
                    if (i != 15)
                        continue;

                    var subjects = Stream.of(words[6].split("[，,]"))
                            .collect(Collectors.toList());

                    var date = Stream.of(words[7].split("[，,]"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    var book = new Book(words[0], words[1], words[2],
                            words[3], words[4], words[5],
                            subjects, date, words[8],
                            words[9], words[10],
                            Integer.parseInt(words[11]),
                            Integer.parseInt(words[12]),
                            Integer.parseInt(words[13]),
                            words[14].matches("(是|TRUE|true)"));

                    bookService.save(book);
                }
                return Return.success("导入成功");
            } else {
                return Return.failure("未知异常, 导入失败");
            }
        } catch (IOException e) {
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

    @Override
    @Transactional
    public Return importHoldingsFromFile(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            var workbook = new XSSFWorkbook(inputStream);
            // 获得第一张sheet表
            var sheet = workbook.getSheetAt(0);
            if (sheet != null) {
                var words = new String[6];
                // 数据是从第三行开始，所以这里从第三行开始遍历
                for (int line = 2; line <= sheet.getLastRowNum(); line++) {
                    Row row = sheet.getRow(line);
                    if (row == null)
                        continue;
                    int i = 0;
                    for (; i < 6; i++) {
                        if (CellType.STRING != row.getCell(i).getCellType())
                            break;
                        words[i] = row.getCell(i).getStringCellValue();
                    }

                    if (i != 6)
                        continue;

                    var book = bookService.findById(words[0]);
                    if (book == null)
                        continue;

                    var state = parseHoldingState(words[5]);
                    if (state == null)
                        continue;

                    Holding holding = new Holding(book, words[0],
                            words[1], Integer.parseInt(words[2]), Integer.parseInt(words[3]),
                            words[4], state);
                    holdingService.save(holding);
                }
                return Return.success("导入成功");
            } else {
                return Return.failure("未知异常, 导入失败");
            }
        } catch (IOException e) {
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
}
