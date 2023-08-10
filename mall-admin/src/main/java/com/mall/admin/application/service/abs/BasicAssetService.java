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
    public void inPool(MultipartFile file)  {
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
                XSSFCell certIdCell=row.getCell(3);
                XSSFCell monthIncomeCell=row.getCell(4);
                XSSFCell grantLimitCell=row.getCell(5);
                XSSFCell surplusLimitCell=row.getCell(6);
                XSSFCell loanSurplusAmtCell=row.getCell(7);
                XSSFCell loanSdateCell=row.getCell(8);
                XSSFCell loanEdateCell=row.getCell(9);
                XSSFCell creditRateCell=row.getCell(10);
                XSSFCell fiveClassifyCell=row.getCell(11);
                XSSFCell loanTypeCell=row.getCell(12);

                BasicAsset basicAsset=new BasicAsset();
                basicAsset.setId(StringUtils.getUUID());
                basicAsset.setAssetSte(BasicAsset.ASSET_STE_NORMAL);
                basicAsset.setAcctNo(RowCell.getString(acctNoCell));
                basicAsset.setCustomName(RowCell.getString(customNoCell));
                basicAsset.setSex(RowCell.getString(sexCell));
                basicAsset.setCertId(RowCell.getString(certIdCell));
                basicAsset.setMonthIncome(RowCell.getDouble(monthIncomeCell));
                basicAsset.setGrantLimit(RowCell.getDouble(grantLimitCell));
                basicAsset.setSurplusLimit(RowCell.getDouble(surplusLimitCell));
                basicAsset.setLoanSurplusAmt(RowCell.getDouble(loanSurplusAmtCell));
                basicAsset.setLoanSdate(RowCell.getString(loanSdateCell));
                basicAsset.setLoanEdate(RowCell.getString(loanEdateCell));
                basicAsset.setCreditRate(RowCell.getString(creditRateCell));
                basicAsset.setFiveClassify(RowCell.getString(fiveClassifyCell));
                basicAsset.setLoanType(RowCell.getString(loanTypeCell));
                basicAssetRepo.addBasicAsset(basicAsset);
            }

        }catch (IOException e){
            log.error(e.getMessage());
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
