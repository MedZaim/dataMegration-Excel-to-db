package com.api.helper;

import com.api.entity.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helper {

    //check that file is of Excel format or not
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            return true;
        return false;

    }
    //convert excel to list of products
    public static List<Product> convertExcelToListOfProduct(InputStream is) throws IOException {
        List<Product> list = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("product");

            int rowNumber = 0;

        for (Row row : sheet) {
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cells = row.iterator();

            int cid = 0;

            Product p = new Product();

            while (cells.hasNext()) {
                Cell cell = cells.next();
                switch (cid) {
                    case 0:
                        p.setProductId((int) cell.getNumericCellValue());
                        break;
                    case 1:
                        p.setProductName(cell.getStringCellValue());
                        break;
                    case 2:
                        p.setProductDesc(cell.getStringCellValue());
                        break;
                    case 3:
                        p.setProductPrice(cell.getNumericCellValue());
                        break;
                    default:
                        break;
                }
                cid++;
            }
            list.add(p);
        }

        return list;
    }
}