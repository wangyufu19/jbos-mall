package com.mall.admin.application.service.abs;

import com.mall.admin.domain.entity.abs.BasicAsset;
import com.mall.admin.infrastructure.repository.abs.BasicAssetRepo;
import com.mall.common.office.excel.xssf.RowCell;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * BasicAssetService
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
@Service
@Slf4j
public class BasicAssetService {
    @Autowired
    private BasicAssetRepo basicAssetRepo;
    /**
     * 判断合法文件
     * @param file
     * @return
     */
    public boolean includeExtensions(MultipartFile file){
        String originalFilename=file.getOriginalFilename();
        if(originalFilename.indexOf(".xls")!=-1||originalFilename.indexOf(".xlsx")!=-1){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 查询基础资产数据
     * @param parameterObject
     * @return
     */
    public ResponseResult getBasicAssetList(PageParam pageParam, Map<String,Object> parameterObject){
        List<BasicAsset> basicAssetList = basicAssetRepo.getBasicAssetList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).data(basicAssetList);
    }

    /**
     * 基础资产入池
     * @param file
     */
    @Transactional
    public void inPool(MultipartFile file) throws IOException {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(file.getBytes());
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet =workbook.getSheetAt(0);
            int rows=sheet.getPhysicalNumberOfRows();
            for(int i=0;i<rows;i++){
                XSSFRow row=sheet.getRow(i);
                XSSFCell acctNoCell=row.getCell(0);
                XSSFCell customNoCell=row.getCell(1);
                XSSFCell sexCell=row.getCell(2);
                log.info("acctNo={};customNo={},sex={}", RowCell.getCellValue(acctNoCell),RowCell.getCellValue(customNoCell),RowCell.getCellValue(sexCell));
                BasicAsset basicAsset=new BasicAsset();
                basicAsset.setId(StringUtils.getUUID());
                basicAsset.setAcctNo(StringUtils.replaceNull(RowCell.getCellValue(acctNoCell)));
                basicAsset.setCustomName(StringUtils.replaceNull(RowCell.getCellValue(customNoCell)));
                basicAsset.setSex(StringUtils.replaceNull(RowCell.getCellValue(sexCell)));
                basicAssetRepo.addBasicAsset(basicAsset);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(in!=null){
                in.close();
            }
        }
    }
}
