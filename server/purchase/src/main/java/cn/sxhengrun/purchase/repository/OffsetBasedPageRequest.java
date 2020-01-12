package cn.sxhengrun.purchase.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class OffsetBasedPageRequest implements Pageable, Serializable {
    private final int limit;
    private final long offset;
    private final Sort sort;

    public OffsetBasedPageRequest(long offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }


    @Override
    public int getPageNumber() {
        return -1;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
