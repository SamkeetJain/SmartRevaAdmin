package com.samkeet.smartrevaadmin;

import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by Frost on 18-01-2017.
 */

public class ExcelAPI {

    private WritableCellFormat times;

    public String filename;
    public String data;
    public String[][] input;

    public String[] titles;
    public int jsonlength;

    public ExcelAPI(String filename, String data, String[] titles) {
        String temp = filename;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String timestamp = simpleDateFormat.format(new Date());
        temp = temp.concat(timestamp);
        temp=temp.concat(".xls");
        this.data = data;
        this.titles = titles;
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), temp);

        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.filename = file.getAbsolutePath();
    }

    public void write() throws IOException, WriteException {
        {
            File file = new File(filename);
            WorkbookSettings wbSettings = new WorkbookSettings();

            wbSettings.setLocale(new Locale("en", "EN"));

            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            workbook.createSheet("data", 0);
            WritableSheet excelsheet = workbook.getSheet(0);
            createContent(excelsheet);

            workbook.write();
            workbook.close();
        }


    }


    private void createContent(WritableSheet excelsheet) throws WriteException {
        WritableFont times10p = new WritableFont(WritableFont.TIMES, 10);
        times = new WritableCellFormat(times10p);
        times.setWrap(true);

        for (int i = 0; i < titles.length; i++) {
            addData(excelsheet, i, 0, titles[i]);
        }

        try {
            JSONArray jsonArray = new JSONArray(data);
            jsonlength = jsonArray.length();
            input = new String[titles.length][jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jasonObject = jsonArray.getJSONObject(i);
                for (int j = 0; j < titles.length; j++) {
                    input[j][i] = jasonObject.getString(titles[j]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int m = input.length; //colums
        int n = input[0].length; //rows

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                addData(excelsheet, i, j+1 , input[i][j]);
            }
        }
    }

    private void addData(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException {

        Label data;
        data = new Label(column, row, s, times);
        sheet.addCell(data);

    }

}
