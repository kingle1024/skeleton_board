package com.skeleton.excel;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelRead {
    public static List<Map<String, String>> read(ExcelReadOption excelReadOption) {
        if(excelReadOption != null) {
            Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
            final int sheetNum = wb.getNumberOfSheets();

            List<Map<String, String>> result = new ArrayList<>();

            for (int i = 0; i < sheetNum; i++) {
                Sheet sheet = wb.getSheetAt(i);

                int numOfRows = sheet.getLastRowNum() + 1;
                if(numOfRows <= 1) {
                    Map<String, String> map = new HashMap<>();
                    map.put("errorMessage", "numOfRows 1이 반환되는 오류 발생(유효한 행이 없음)");
                    result.add(map);
                    return result;
                }

                final int startRow = excelReadOption.getStartRow()-1;

                for (int j = startRow; j < numOfRows; j++) {
                    Row row = sheet.getRow(j);
                    if(row != null) {
                        int numOfCells = row.getLastCellNum();
                        Map<String, String> map = new HashMap<>();

                        for (int k = 0; k < numOfCells; k++) {
                            Cell cell = row.getCell(k);

                            if(cell == null) {
                                continue;
                            } else {
                                switch (cell.getCellType()) {
                                    case Cell.CELL_TYPE_NUMERIC: {
                                        if (DateUtil.isCellDateFormatted(cell)) {
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            cell.setCellValue(formatter.format(cell.getDateCellValue()));
                                        } else {
                                            if ((String.valueOf(cell.getNumericCellValue())).contains(".0")) {
                                                cell.setCellValue(String.valueOf((int) cell.getNumericCellValue()));
                                            } else {
                                                cell.setCellValue(String.valueOf((float) cell.getNumericCellValue()));
                                            }
                                        }
                                        break;
                                    }
                                    case Cell.CELL_TYPE_STRING: {
                                        cell.setCellType(Cell.CELL_TYPE_STRING);
                                        cell.setCellValue(cell.getStringCellValue());
                                        break;
                                    }
                                    case Cell.CELL_TYPE_FORMULA: {
                                        cell.setCellType(Cell.CELL_TYPE_STRING);
                                        String temp_value = cell.getStringCellValue();
                                        if(temp_value.indexOf(".") > 0) {
                                            double value = Double.parseDouble(
                                                    String.format("%.1f",
                                                            Double.parseDouble(cell.getRichStringCellValue().toString())
                                                    )
                                            );
                                            cell.setCellValue(value);
                                        } else {
                                            cell.setCellValue(cell.getStringCellValue());
                                        }
                                        break;
                                    }
                                }
                            }
                            String cellName = ExcelCellRef.getName(cell, j);
                            map.put(cellName, ExcelCellRef.getValue(cell));
                            map.put("successMessage", "불러오기 성공");
                        }

                        result.add(map);
                    } else {
                        break;
                    }
                }
            }
            return result;
        }
        return Collections.emptyList();
    }
}
