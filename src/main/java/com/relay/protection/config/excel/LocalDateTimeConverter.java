package com.relay.protection.config.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.relay.protection.common.util.DateUtil;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/12 14:55
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty
            , GlobalConfiguration globalConfiguration) throws Exception {
        return LocalDateTime.parse(cellData.getStringValue(), DateUtil.getDtfdt());
    }

    @Override
    public CellData convertToExcelData(LocalDateTime localDate, ExcelContentProperty excelContentProperty
            , GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(localDate.format(DateUtil.getDtfdt()));
    }

}
