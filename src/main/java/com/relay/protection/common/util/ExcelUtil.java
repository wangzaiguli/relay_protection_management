package com.relay.protection.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.relay.protection.common.enums.TemplateConfigEnum;
import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;
import com.relay.protection.config.excel.LocalDateTimeConverter;
import com.relay.protection.pojo.dto.TemplateConfig;
import com.relay.protection.pojo.dto.TemplateItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author liw
 * @version 1.0
 * @date 2022/7/12 20:54
 */
@Slf4j
public class ExcelUtil {


    public static void main(String[] args) {
//        ExcelUtil test = new ExcelUtil();
//        List<TemplateItem> templateItemList = getTemplateItemList("C:\\lw\\project\\other\\00more\\relay_protection\\电动机.xlsx");
//        for (TemplateItem item : templateItemList) {
//            System.out.println(item.getOrder() + item.getTitle());
//            for (TemplateConfig config : item.getList()) {
//                System.out.println(config);
//            }
//            System.out.println();
//        }

        String a = "C:\\lw\\project\\other\\00more\\relay_protection\\relay_protection_management\\mydata\\file\\20220811\\aaa.xlsx";
        String b = "C:\\lw\\project\\other\\00more\\relay_protection\\relay_protection_management\\mydata\\file\\20220811\\aaa1.xlsx";

        Map<String, Double> map = new HashMap<>();
        map.put("D1", 1.0);
        excelWrite(a, b, map);


        // 根据哪个模板进行填充
//        String template = "C:\\lw\\test\\aa.xlsx";
//        // 填充完成之后的exceltest.xlsx
//        String fillname = "C:\\lw\\test\\aa1.xlsx";
//        Map<String, Object> map = new HashMap<>();
//        map.put("D1", "a");
//        map.put("D2", LocalDateTime.now());
//        map.put("D3", BigDecimal.TEN);

//        TestModel node = new TestModel();
//        node.setName("a");
//        node.setBirthday(LocalDateTime.now());
//        node.setAge(BigDecimal.TEN);

//        ExcelUtil.excelWrite(template, fillname, map);

    }


    /**
     * 根据模板，生成excel
     *
     * @param templatePath  模板目录
     * @param temporaryPath 生成的excel临时文件
     * @param data          数据
     * @param <T>           类型
     */
    public static <T> void excelWrite(String templatePath, String temporaryPath, T data) {
        // 创建写对象
        ExcelWriter excelWriter = EasyExcel.write(temporaryPath)
                .withTemplate(templatePath).registerConverter(new LocalDateTimeConverter()).build();
        // 创建Sheet对象
        WriteSheet sheet = EasyExcel.writerSheet(0).build();
        excelWriter.fill(data, sheet);
        // 关闭流
        excelWriter.finish();
    }


    /**
     * 读取 Excel -> workbook
     *
     * @param path 路径
     * @return Workbook
     */
    public static Workbook getWorkbook(String path) {
        Workbook wb = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                return createWorkbook(path);
            }
            FileInputStream fis = new FileInputStream(file);
            if (path.endsWith("xls")) {
                wb = new HSSFWorkbook(fis);
            } else if (path.endsWith("xlsx")) {
                wb = new XSSFWorkbook(fis);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(JsonReturnCode.FILE_EXCEL_READ);
        }
        return wb;
    }

    /**
     * 创建  workbook
     *
     * @param path 路径，提取格式用
     * @return Workbook
     */
    public static Workbook createWorkbook(String path) {
        Workbook wb = null;
        try {
            if (path.endsWith("xls")) {
                wb = new HSSFWorkbook();
            } else if (path.endsWith("xlsx")) {
                wb = new XSSFWorkbook();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(JsonReturnCode.FILE_EXCEL_GET);
        }
        return wb;
    }

    /**
     * 创建 workbook -> 文件
     *
     * @param workbook   代码
     * @param createPath 路径 + 名称
     */
    public static void createExcle(Workbook workbook, String createPath) {
        try {
            //生成文件
            File file = new File(createPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            OutputStream os = new FileOutputStream(file);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(JsonReturnCode.FILE_EXCEL_CREAT, createPath);
        }
    }

    /**
     * 读取  Excel -> sheet
     *
     * @param path
     * @param sheetPage
     * @return
     */
    public static Sheet getSheet(String path, int sheetPage) {
        Sheet sheet = null;
        try {
            Workbook wb = getWorkbook(path);
            if (wb != null) {
                //获取第一个表单sheet
                sheet = wb.getSheetAt(sheetPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(JsonReturnCode.FILE_SHEET_CREAT);
        }
        return sheet;
    }

    /**
     * 读取  Excel -> sheet
     *
     * @param path
     * @return
     */
    public static int getSheetNumbers(String path) {
        int numberOfSheets = 0;
        try {
            Workbook wb = getWorkbook(path);
            if (wb != null) {
                //获取第一个表单sheet
                numberOfSheets = wb.getNumberOfSheets();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberOfSheets;
    }


    public static BigDecimal getDecimal(Cell cell) {
        CellType cellTypeEnum = cell.getCellTypeEnum();
        if (cellTypeEnum == CellType.NUMERIC) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        }
        if (cellTypeEnum == CellType.STRING) {
            return new BigDecimal(cell.getStringCellValue());
        }
        throw new BizException(JsonReturnCode.FILE_EXCEL_TYPE, cellTypeEnum.toString());
    }

    public static LocalDateTime getDateTime(Cell cell) {
        CellType cellTypeEnum = cell.getCellTypeEnum();
        if (cellTypeEnum == CellType.NUMERIC) {
            return DateUtil.date2DateTime(cell.getDateCellValue());
        }
        if (cellTypeEnum == CellType.STRING) {
            return DateUtil.str2Date(cell.getStringCellValue());
        }
        throw new BizException(JsonReturnCode.FILE_EXCEL_TYPE, cellTypeEnum.toString());
    }


    public static Map<Integer, String> getExcelTemplate(String path, int sheetPage) {
        Sheet sheet = getSheet(path, sheetPage);
        int lastRowNum = sheet.getLastRowNum();
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j <= lastCellNum; j++) {
                Cell cell = row.getCell(j);
                if (cell == null || cell.getCellTypeEnum() != CellType.STRING || cell.getStringCellValue() == null) {
                    continue;
                }
                String stringCellValue = cell.getStringCellValue().trim();
                if (stringCellValue.startsWith("{") && stringCellValue.endsWith("}")) {
                    map.put((i << 16) + j, stringCellValue.substring(1, stringCellValue.length() - 1));
                }
            }
        }
        return map;
    }

    public static List<TemplateItem> getTemplateItemList(String path) {
        // 读取excel
        List<TemplateConfig> list = new ArrayList<>();

        ExcelReader excelReader = EasyExcel.read(path).build();

        ReadSheet excelReaderSheetBuilder = EasyExcel.readSheet(1)
                .head(TemplateConfig.class)
                .registerReadListener(getReadListener(list)).build();

        excelReader.read(excelReaderSheetBuilder);
        excelReader.finish();

        int size = list.size();
        List<TemplateItem> itemList = new ArrayList<>();
        int i = 0;
        while (i < size) {
            TemplateConfig config = list.get(i);
            TemplateConfigEnum value;
            i++;
            if (ObjectUtils.isEmpty(config.getName())
                    || (value = getTemplateConfig(config.getName())) == null) {
                continue;
            }
            TemplateItem item = new TemplateItem();
            itemList.add(item);
            item.setCode(value.getKey());
            item.setOrder(value.getOrder());
            item.setTitle(value.getValue());
            List<TemplateConfig> configList = new ArrayList<>();
            item.setList(configList);
            while (i < size - 1) {
                i++;
                config = list.get(i);
                if (!ObjectUtils.isEmpty(getTemplateConfig(config.getName()))) {
                    break;
                }
                if (!ObjectUtils.isEmpty(config.getCode())) {
                    configList.add(config);
                }
            }
        }
        return itemList.stream().sorted(Comparator.comparing(TemplateItem::getOrder)).collect(Collectors.toList());
    }

    private static TemplateConfigEnum getTemplateConfig(String name) {
        for (TemplateConfigEnum value : TemplateConfigEnum.values()) {
            if (value.getValue().equals(name)) {
                return value;
            }
        }
        return null;
    }


    /**
     * 获取读取Excel的监听器对象
     * 为了解耦及减少每个数据模型bean都要创建一个监听器的臃肿, 使用泛型指定数据模型类型
     * 使用jdk8新特性中的函数式接口 Consumer
     * 可以实现任何数据模型bean的数据解析, 不用重复定义监听器
     *
     * @param dataList 数据
     * @param <T>      数据模型泛型
     * @return 返回监听器
     */
    public static <T> AnalysisEventListener<T> getReadListener(List<T> dataList) {

        return new AnalysisEventListener<T>() {


            /**
             * 每解析一行数据事件调度中心都会通知到这个方法, 订阅者1
             * @param data 解析的每行数据
             * @param context
             */
            @Override
            public void invoke(T data, AnalysisContext context) {
                dataList.add(data);
            }

            /**
             * excel文件解析完成后,事件调度中心会通知到该方法, 订阅者2
             * @param context
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {

                log.info("excel read over..");

            }
        };

    }


    /**
     * 获取读取Excel的监听器对象
     * 为了解耦及减少每个数据模型bean都要创建一个监听器的臃肿, 使用泛型指定数据模型类型
     * 使用jdk8新特性中的函数式接口 Consumer
     * 可以实现任何数据模型bean的数据解析, 不用重复定义监听器
     *
     * @param consumer  处理解析数据的函数, 一般可以是数据入库逻辑的函数
     * @param threshold 阈值,达到阈值就处理一次存储的数据
     * @param <T>       数据模型泛型
     * @return 返回监听器
     */
    public static <T> AnalysisEventListener<T> getReadListener(Consumer<List<T>> consumer, int threshold) {

        return new AnalysisEventListener<T>() {

            /**
             * 存储解析的数据 T t
             */
            // ArrayList基于数组实现, 查询更快
            List<T> dataList = new ArrayList<>(threshold);

            /**
             * 每解析一行数据事件调度中心都会通知到这个方法, 订阅者1
             * @param data 解析的每行数据
             * @param context
             */
            @Override
            public void invoke(T data, AnalysisContext context) {
                dataList.add(data);
                // 达到阈值就处理一次存储的数据
                if (dataList.size() >= threshold) {
                    consumer.accept(dataList);
                    dataList.clear();
                }
            }

            /**
             * excel文件解析完成后,事件调度中心会通知到该方法, 订阅者2
             * @param context
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 最后阈值外的数据做处理
                if (dataList.size() > 0) {
                    consumer.accept(dataList);
                }
            }
        };

    }


}
