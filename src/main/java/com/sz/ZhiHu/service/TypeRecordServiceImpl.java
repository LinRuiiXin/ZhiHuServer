package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.TypeRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeRecordServiceImpl implements TypeRecordService{
    @Autowired
    TypeRecordDao typeRecordDao;
    @Override
    public List<Long> getUserRecordType(Long userId) {
        List<Long> typeRecord = typeRecordDao.queryUserTypeRecord(userId);
        List<Long> doTypeIds = new ArrayList<>();
        typeRecord.stream().distinct().forEach(doTypeIds::add);
        return doTypeIds;
    }
}
