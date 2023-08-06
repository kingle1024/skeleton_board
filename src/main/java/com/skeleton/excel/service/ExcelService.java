package com.skeleton.excel.service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface ExcelService {
    void excelUpload(File destiFile) throws Exception;

    String excelDate(File destFile) throws Exception;

    void excelDownload(HttpServletResponse response);
}
