package io.trishul.crud.controller;

import java.util.Set;

import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.model.base.dto.BaseDto;

public abstract class BaseController {
    public static final String PROPNAME_SORT_BY = "sort";
    public static final String PROPNAME_ORDER_ASC = "order_asc";
    public static final String PROPNAME_PAGE_INDEX = "page";
    public static final String PROPNAME_PAGE_SIZE = "size";
    public static final String PROPNAME_ATTR = "attr";

    public static final String VALUE_DEFAULT_SORT_BY = "id";
    public static final String VALUE_DEFAULT_ORDER_ASC = "true";
    public static final String VALUE_DEFAULT_PAGE_INDEX = "0";
    public static final String VALUE_DEFAULT_PAGE_SIZE = "100";
    public static final String VALUE_DEFAULT_ATTR = "";

    private AttributeFilter filter;

    public BaseController() {
    }

    @Deprecated(forRemoval = true)
    public BaseController(AttributeFilter filter) {
        this.filter = filter;
    }

    @Deprecated(forRemoval = true)
    public void filter(BaseDto dto, Set<String> retainAttr) {
        if (retainAttr != null && !retainAttr.isEmpty()) {
            this.filter.retain(dto, retainAttr);
        }
    }
}
