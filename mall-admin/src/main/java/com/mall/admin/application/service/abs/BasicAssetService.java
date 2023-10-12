package com.mall.admin.application.service.abs;

import com.mall.admin.domain.entity.abs.BasicAsset;
import com.mall.admin.infrastructure.repository.abs.BasicAssetRepo;
import com.mall.common.office.excel.xssf.RowObject;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
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
    /**
     * BasicAssetRepo
     */
    @Autowired
    private BasicAssetRepo basicAssetRepo;

    /**
     * 判断合法文件
     *
     * @param file
     * @return true/false
     */
    public boolean includeExtensions(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return false;
        }
        return originalFilename.contains(".xls") || originalFilename.contains(".xlsx");
    }

    /**
     * 查询基础资产数据
     *
     * @param pageParam
     * @param parameterObject
     * @return List
     */
    public ResponsePageResult getBasicAssetList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<BasicAsset> basicAssetList = basicAssetRepo.getBasicAssetList(pageParam, parameterObject);
        return ResponsePageResult.ok().isPage(true).setData(basicAssetList);
    }

    /**
     * 入池
     *
     * @param file
     */
    @Transactional
    public void inPool(MultipartFile file) {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(file.getBytes());
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                BasicAsset basicAsset = (BasicAsset) RowObject.getRowObject(row, BasicAsset.class);
                basicAsset.setAssetSte(BasicAsset.ASSET_STE_NORMAL);
                basicAssetRepo.addBasicAsset(basicAsset);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 出池
     *
     * @param id
     * @param acctNo
     */
    public void outPool(String id, String acctNo) {
        basicAssetRepo.deleteBasicAsset(acctNo);
    }
}
