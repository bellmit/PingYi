package com.example.upc.util.operateExcel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class WasteExcel {

    //    public static String path = "/Users/75186/Desktop/wisdom";
   // public static String path = "C:\\Users\\my\\Desktop\\weixin2\\IMDY\\upload\\";
    //public static String path = "/Users/weixj/Desktop/wph/IMDY/upload";


    public static String path = "upload";
   // public static String mdlpath = path+"/template/"+ "【导出】废弃物处理模板.xlsx";
    //public static String mdlpath = "/Users/weixj/Desktop/解压/vehicleCharge-1/templeteWord/隐患排查治理.xlsx";

    public static void main(String[] args) throws IOException {
        List<List<String[]>> tabledataList = new ArrayList<>();
        List<String[]> sheet1 = new ArrayList<String[]>();
        for (int i = 0; i < 30; i++) {
            sheet1.add(new String[] { "2020-09-09", "餐厨垃圾", "10桶", "张三", "回收单位", "回收人员", "备注" });
        }
        tabledataList.add(sheet1);
        getXLsx(sheet1,"pp","2019-10",133);

    }

    public static String getXLsx(List<String[]> table1, String mdlpath, String fileName, Integer businessId) throws IOException {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        String currentTime = dateFormat.format( now );
        try (FileInputStream is = new FileInputStream(path+mdlpath);
             XSSFWorkbook workBook = new XSSFWorkbook(is)) {
            XSSFSheet sheet1 = workBook.getSheetAt(0);  //获取第一页
//            List<String[]> table1 = tabledataList.get(0);

            if (table1.size() > 24) {
                //插入行
               Row startRow = sheet1.getRow(2);


                //Row newRow=sheet1.createRow(1);
                //sheet1.copyRows(2,4,sheet1.getLastRowNum(),new CellCopyPolicy());
                System.out.println("总行数:" + sheet1.getLastRowNum());
                sheet1.shiftRows(1, sheet1.getLastRowNum(), table1.size() - 24);
                //XSSFSheet中的shiftRows方法在4.1.0版本中存在bug，至今还未修复，所以这一段话必须加上：
                if (sheet1 instanceof XSSFSheet) {
                    XSSFSheet xSSFSheet = (XSSFSheet) sheet1;
                    // correcting bug that shiftRows does not adjusting references of the cells
                    // if row 3 is shifted down, then reference in the cells remain r="A3", r="B3", ...
                    // they must be adjusted to the new row thoug: r="A4", r="B4", ...
                    // apache poi 3.17 has done this properly but had have other bugs in shiftRows.
                    for (int r = xSSFSheet.getFirstRowNum(); r < sheet1.getLastRowNum() + 1; r++) {
                        XSSFRow row = xSSFSheet.getRow(r);
                        if (row != null) {
                            long rRef = row.getCTRow().getR();
                            for (Cell cell : row) {
                                String cRef = ((XSSFCell) cell).getCTCell().getR();
                                ((XSSFCell) cell).getCTCell().setR(cRef.replaceAll("[0-9]", "") + rRef);
                            }
                        }
                    }
//                sheet1.createRow(2);
                }


                //添加完毕复制格式
                for (int j = 0; j < table1.size() - 24; j++) {
                    XSSFRow row = sheet1.createRow(1 + j);
                    copyRow(workBook, sheet1.getRow(sheet1.getLastRowNum() - 1), row, false);
                }
                // end correcting bug
            }

            System.out.println("总行数:" + sheet1.getLastRowNum());
            //填入数据
            int startindex=1;
            int sumcloum = sheet1.getRow(0).getPhysicalNumberOfCells();
            for (int i = 0; i < table1.size(); i++) {
                Row row = sheet1.getRow(i + startindex);
                for (int j = 0; j < sumcloum; j++) {
                    System.out.println(i+"行"+j+"列");
                    System.out.println(table1.get(i)[j]);
                    Cell cell = row.getCell(j);
                    cell.setCellValue(table1.get(i)[j]);
                }
            }

//                Long time = System.currentTimeMillis();

            File filed = new File(path+"/standingBook/"+currentTime);
            if(!filed.exists()){
                filed.mkdirs();
            }
            try (FileOutputStream out = new FileOutputStream(path+"/standingBook/"+currentTime+"/"+fileName+businessId+".xlsx");) {
                workBook.write(out);
                out.flush();
            }
//            }

        }
        return path+"/standingBook/"+currentTime+"/"+fileName+businessId+".xlsx";
    }



    /**
     * 行复制功能
     *
     * @param wb            工作簿
     * @param fromRow       从哪行开始
     * @param toRow         目标行
     * @param copyValueFlag true则连同cell的内容一起复制
     */
    public static void copyRow(Workbook wb, Row fromRow, Row toRow, boolean copyValueFlag) {

        for (Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
            Cell tmpCell = cellIt.next();
            Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
            copyCell(wb, tmpCell, newCell, copyValueFlag);
        }

        Sheet worksheet = fromRow.getSheet();
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(toRow.getRowNum(),
                        (toRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
                        cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
                worksheet.addMergedRegionUnsafe(newCellRangeAddress);
            }
        }
    }

    /**
     * 复制单元格
     * @param srcCell
     * @param distCell
     * @param copyValueFlag true则连同cell的内容一起复制
     */
    @SuppressWarnings("deprecation")
    public static void copyCell(Workbook wb, Cell srcCell, Cell distCell, boolean copyValueFlag) {
        CellStyle newStyle = wb.createCellStyle();
        CellStyle srcStyle = srcCell.getCellStyle();
        newStyle.cloneStyleFrom(srcStyle);
        newStyle.setFont(wb.getFontAt(srcStyle.getFontIndex()));

        // 样式
        distCell.setCellStyle(newStyle);

        // 内容
        if (srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        // 不同数据类型处理
        CellType srcCellType = srcCell.getCellTypeEnum();
        distCell.setCellType(srcCellType);
        if (copyValueFlag) {
            if (srcCellType == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(srcCell)) {
                    distCell.setCellValue(srcCell.getDateCellValue());
                } else {
                    distCell.setCellValue(srcCell.getNumericCellValue());
                }
            } else if (srcCellType == CellType.STRING) {
                distCell.setCellValue(srcCell.getRichStringCellValue());
            } else if (srcCellType == CellType.BLANK) {

            } else if (srcCellType == CellType.BOOLEAN) {
                distCell.setCellValue(srcCell.getBooleanCellValue());
            } else if (srcCellType == CellType.ERROR) {
                distCell.setCellErrorValue(srcCell.getErrorCellValue());
            } else if (srcCellType == CellType.FORMULA) {
                distCell.setCellFormula(srcCell.getCellFormula());
            }
        }
    }

    public static void drawImageOnExcelSheet(XSSFSheet sheet, int row, int col,
                                             int left/*in px*/, int top/*in pt*/, int width/*in px*/, int height/*in pt*/, int pictureIdx) throws Exception {

        CreationHelper helper = sheet.getWorkbook().getCreationHelper();

        Drawing drawing = sheet.createDrawingPatriarch();

        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

        anchor.setCol1(col); //first anchor determines upper left position
        anchor.setRow1(row);
        anchor.setDx1(Units.pixelToEMU(left)); //dx = left in px
        anchor.setDy1(Units.toEMU(top)); //dy = top in pt

        anchor.setCol2(col+3); //second anchor determines bottom right position
        anchor.setRow2(row);
        anchor.setDx2(Units.pixelToEMU(left + width)); //dx = left + wanted width in px
        anchor.setDy2(Units.toEMU(top + height)); //dy= top + wanted height in pt

        drawing.createPicture(anchor, pictureIdx);

    }
}
