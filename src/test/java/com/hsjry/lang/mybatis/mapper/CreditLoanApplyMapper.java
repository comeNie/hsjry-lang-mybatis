package com.hsjry.lang.mybatis.mapper;

import tk.mybatis.mapper.common.base.insert.InsertMapper;

import com.hsjry.lang.mybatis.mapper.common.base.insert.InsertWithBlobsMapper;
import com.hsjry.lang.mybatis.model.CreditLoanApply;
import com.hsjry.lang.mybatis.model.CreditLoanApplyWithBLOBs;

/**
 *
 * @author jinyn22648
 * @version $$Id: CreditLoanApplyMapper.java, v 0.1 2018/1/27 下午6:45 jinyn22648 Exp $$
 */
public interface CreditLoanApplyMapper extends InsertMapper<CreditLoanApply>, InsertWithBlobsMapper<CreditLoanApplyWithBLOBs> {

}
