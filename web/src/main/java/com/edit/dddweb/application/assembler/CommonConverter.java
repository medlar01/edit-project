package com.edit.dddweb.application.assembler;

import com.edit.dddweb.interfaces.dto.BasicSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommonConverter {
    CommonConverter INST = Mappers.getMapper(CommonConverter.class);
    default List<BasicSearchDTO> toBsDTO(List<Map<String, Serializable>> val) {
        if (val == null) return null;
        final List<BasicSearchDTO> list = new ArrayList<>();
        val.forEach(map -> {
            BasicSearchDTO dto = new BasicSearchDTO();
            dto.setCode((String) map.get("code"));
            dto.setName((String) map.get("name"));
            dto.setDesc((String) map.get("desc"));
            list.add(dto);
        });
        return list;
    }
}
