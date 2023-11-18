package com.groupal.king.store.catalog.application.port.in;

import com.groupal.king.store.catalog.domain.Category;

import java.util.List;

public interface GetCategoriesQuery {

    List<Category> execute();

}
