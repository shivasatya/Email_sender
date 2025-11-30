package com.emailSender.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;


@Component
public class ExcelReader {

    public List<String> readCompanyEmails(InputStream excelInput) throws Exception {
        List<String> emails = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(excelInput);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null) {
                emails.add(cell.getStringCellValue());
            }
        }

        workbook.close();
        return emails;
    }
}
