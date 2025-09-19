package RamChavanTestAutomation.UI_API_HybdriFramework.dataproviders;

//package com.yourcompany.dataproviders;

import org.testng.annotations.DataProvider;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExcelUtils;

public class ExcelDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        ExcelUtils excel = new ExcelUtils("src/test/resources/testdata/testdata.xlsx");
        int rows = excel.getRowCount("LoginData");
        Object[][] data = new Object[rows-1][2]; // assuming Username, Password

        for (int i = 1; i < rows; i++) {
            data[i-1][0] = excel.getCellData("LoginData", i, 0);
            data[i-1][1] = excel.getCellData("LoginData", i, 1);
        }
        return data;
    }
}

