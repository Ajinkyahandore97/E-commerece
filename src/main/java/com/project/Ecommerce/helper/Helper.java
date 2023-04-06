package com.project.Ecommerce.helper;

import com.project.Ecommerce.payload.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {

        List<U> entity = page.getContent();

        List<V> collect = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();

        response.setContent(collect);
        response.setPageNumber(page.getNumber()+1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPage(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }

}
