package org.iakuh.skeleton.persistence.rdb.repository;

import org.iakuh.skeleton.persistence.rdb.mapper.GenericMapper;

public abstract class GenericRepository<T, PK> {

    protected abstract GenericMapper<T, PK> getGenericMapper();

    public int deleteByPrimaryKey(PK id) {
        return this.getGenericMapper().deleteByPrimaryKey(id);
    }

    public T insert(T entity) {
        int flag = this.getGenericMapper().insert(entity);
        if (flag > 0) {
            return entity;
        } else {
            return null;
        }
    }

    public T insertSelective(T entity) {
        int flag = this.getGenericMapper().insertSelective(entity);
        if (flag > 0) {
            return entity;
        } else {
            return null;
        }
    }

    public T selectByPrimaryKey(PK id) {
        return this.getGenericMapper().selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T entity) {
        return this.getGenericMapper().updateByPrimaryKeySelective(entity);
    }

    public int updateByPrimaryKey(T entity) {
        return this.getGenericMapper().updateByPrimaryKey(entity);
    }

}
