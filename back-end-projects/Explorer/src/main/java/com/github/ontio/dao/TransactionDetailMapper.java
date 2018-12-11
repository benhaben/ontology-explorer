package com.github.ontio.dao;

import com.github.ontio.model.TransactionDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;



@Mapper
@Component(value = "TransactionDetailMapper")
public interface TransactionDetailMapper {
    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    int updateByPrimaryKeySelective(TransactionDetail record);

    int updateByPrimaryKey(TransactionDetail record);

    List<Map> selectTxnWithoutOntId(int start , int size);

    int selectTxnWithoutOntIdAmount();

    Map<String,Object> selectTxnByHash(String txnHash);

    List<Map> selectTxnByBlockHeight(int height);

    Integer selectLastONTTransferTxnTime(String address);

    Integer selectSwapTransferTxnTime(String address);


    List<Map> selectTransferTxnDetailByHash(String txnHash);

    List<Map> selectTxnByAddressInfo(Map<String, Object> param);

    List<Map> selectTxnByToAddressInfo(Map<String, Object> param);

    List<Map> selectTxnByFromAddressInfo(Map<String, Object> param);



    List<Map> selectTxnByAddressInfoAndTimePageDragon(Map<String, Object> param);


    List<Map> selectTxnByAddressInfoAndTimePage(Map<String, Object> param);

    List<Map> selectTxnByAddressInfoAndTime(Map<String, Object> param);

    List<Map> selectTxnByAddressInfoAndTimeDragon(Map<String, Object> param);


    int selectTxnAmountByAddressInfo(Map<String, Object> param);

    List<String> selectTxnAmountByAddressInfo2(Map<String, Object> param);

    List<String> selectAllAddress();

    int selectAddressRecordAmount(String address);

    List<Map> selectContractByHash(Map<String, Object> param);

    int selectContractByHashAmount(String contractHash);
}