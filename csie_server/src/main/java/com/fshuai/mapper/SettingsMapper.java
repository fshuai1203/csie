package com.fshuai.mapper;

import com.fshuai.entity.Settings;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingsMapper {

    void add(String key,String value);

    void deleteByKey(String key);

    Settings selectByKey(String key);
}
