package com.fshuai.service.impl;

import com.fshuai.mapper.DepartMapper;
import com.fshuai.service.DepartService;
import com.fshuai.vo.DepartmentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class DepartServiceImpl implements DepartService {

    @Autowired
    DepartMapper departMapper;

    private static Map<Integer, String> departmentsMap;

    @Override
    public List<DepartmentVO> getAllDepart() {
        return departMapper.select();
    }

    @Override
    public String getDepartNameById(Integer id) {
        if (departmentsMap == null) {
            List<DepartmentVO> departs = getAllDepart();
            departmentsMap = new HashMap();
            for (DepartmentVO de : departs) {
                departmentsMap.put(de.getId(), de.getName());
            }
        }
        return departmentsMap.get(id);
    }
}
