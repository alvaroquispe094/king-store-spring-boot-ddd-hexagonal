package com.groupal.king.store.application.port.in;

import com.groupal.king.store.catalog.domain.Category;

public interface GetCategoryByIdQuery {

    Category execute(Long id);

}
