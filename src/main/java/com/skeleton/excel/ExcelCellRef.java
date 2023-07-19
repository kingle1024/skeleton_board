package com.skeleton.excel;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.util.CellReference;
import java.text.SimpleDateFormat;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelCellRef {
    public static String getName(Cell cell, int cellIndex) {
        int cellNum;
        if (cell != null) {
            cellNum = cell.getColumnIndex();
        } else {
            cellNum = cellIndex;
        }

        return CellReference.convertNumToColString(cellNum);
    }

    public static String getValue(Cell cell) {
        String value = "";

        switch (Objects.requireNonNull(cell).getCellType()) {
            case Cell.CELL_TYPE_FORMULA:
                if(1 == HSSFCell.CELL_TYPE_NUMERIC){
                    value = String.valueOf((float) cell.getNumericCellValue());
                } else if (2 == HSSFCell.CELL_TYPE_STRING) {
                    value = cell.getStringCellValue();
                }
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    value = formatter.format(cell.getDateCellValue());
                } else {
                    if((String.valueOf(cell.getNumericCellValue())).contains(".0")){
                        value = String.valueOf((int) cell.getNumericCellValue());
                    } else {
                        value = String.valueOf((float) cell.getNumericCellValue());
                    }
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                value = cell.getStringCellValue();
        }

        return value;
    }
}
