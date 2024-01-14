package com.groupal.king.store.catalog.application.port.in;

import com.groupal.king.store.catalog.domain.Category;

public interface GetCategoryByIdQuery {

    Category execute(Long id);

}
