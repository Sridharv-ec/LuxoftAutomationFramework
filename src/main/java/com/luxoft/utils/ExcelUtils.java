package com.luxoft.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {

    @Autowired
    private ExcelUtils excelUtils;


    private HSSFSheet excelSheet;
    private HSSFWorkbook excelWorkbook;
    private HSSFCell cell;
    private HSSFRow row;
    private String sheetPath = "src/main/resources/Data/";
    private String sheetName = "testData";

    private void setExcelFile() throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(sheetPath).getAbsolutePath());
        excelWorkbook = new HSSFWorkbook(excelFile);
        excelSheet = excelWorkbook.getSheet(sheetName);
    }

    private int getDataRow(String dataKey, int dataColumn) {
        int rowCount = excelSheet.getLastRowNum();
        for(int row=0; row<= rowCount; row++){
            if(dataKey.equalsIgnoreCase(excelUtils.getCellData(row, dataColumn))){
                return row;
            }
        }
        return 0;
    }

    private String getCellData(int rowNumb, int colNumb) {
        cell = excelSheet.getRow(rowNumb).getCell(colNumb);
        //LOG.info("Getting cell data.");
        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        }
        String cellData = cell.getStringCellValue();
        return cellData;
    }

    public void setCellData(String result, int rowNumb, int colNumb, String sheetPath,String sheetName) throws Exception{
        try{
            row = excelSheet.getRow(rowNumb);
            cell = row.getCell(colNumb);
            if(cell==null){
                cell = row.createCell(colNumb);
                cell.setCellValue(result);
            }
            else{
                cell.setCellValue(result);
            }

            FileOutputStream fileOut = new FileOutputStream(sheetPath + sheetName);
            excelWorkbook.write(fileOut);
            fileOut.flush();
            fileOut.close();

        }catch(Exception exp){
            System.out.println("Exception occured in setCellData: "+exp);
        }
    }

    public Map getData(String dataKey) throws Exception {
        Map dataMap = new HashMap<String, String>();
        setExcelFile();
        int dataRow = getDataRow(dataKey.trim(), 0);
        if (dataRow == 0) {
            throw new Exception("NO DATA FOUND for dataKey: "+dataKey);
        }
        int columnCount = excelSheet.getRow(dataRow).getLastCellNum();
        for(int i=0;i<columnCount;i++) {
            cell = excelSheet.getRow(dataRow).getCell(i);
            String cellData = null;
            if (cell != null) {
                if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                }
                cellData = cell.getStringCellValue();
            }
            dataMap.put(excelSheet.getRow(0).getCell(i).getStringCellValue(), cellData);
        }
        return dataMap;
    }




}
