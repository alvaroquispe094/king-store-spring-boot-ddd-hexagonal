package com.groupal.king.store.application.port.in;

import com.groupal.king.store.catalog.domain.Category;

import java.util.List;

public interface GetCategoriesQuery {

    List<Category> execute();

}
