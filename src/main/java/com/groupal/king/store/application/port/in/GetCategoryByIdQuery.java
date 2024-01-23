package com.groupal.king.store.application.port.in;


import com.groupal.king.store.domain.Category;

public interface GetCategoryByIdQuery {

    Category execute(Long id);

}
