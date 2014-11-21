/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lidroid.xutils.db.sqlite;

import android.text.TextUtils;

/**
 * Author: wyouflf
 * Date: 13-8-10
 * Time: 下午2:15
 */
public class DbModelSelector {

    private String[] columnExpressions;
    private String groupByColumnName;
    private WhereBuilder having;

    private Selector selector;

    private DbModelSelector(Class<?> entityType) {
        selector = Selector.from(entityType);
    }

    protected DbModelSelector(Selector selector, String groupByColumnName) {
        this.selector = selector;
        this.groupByColumnName = groupByColumnName;
    }

    protected DbModelSelector(Selector selector, String[] columnExpressions) {
        this.selector = selector;
        this.columnExpressions = columnExpressions;
    }

    public static DbModelSelector from(Class<?> entityType) {
        return new DbModelSelector(entityType);
    }

    public DbModelSelector where(WhereBuilder whereBuilder) {
        selector.where(whereBuilder);
        return this;
    }

    public DbModelSelector where(String columnName, String op, Object value) {
        selector.where(columnName, op, value);
        return this;
    }

    public DbModelSelector and(String columnName, String op, Object value) {
        selector.and(columnName, op, value);
        return this;
    }

    public DbModelSelector and(WhereBuilder where) {
        selector.and(where);
        return this;
    }

    public DbModelSelector or(String columnName, String op, Object value) {
        selector.or(columnName, op, value);
        return this;
    }

    public DbModelSelector or(WhereBuilder where) {
        selector.or(where);
        return this;
    }

    public DbModelSelector expr(String expr) {
        selector.expr(expr);
        return this;
    }

    public DbModelSelector expr(String columnName, String op, Object value) {
        selector.expr(columnName, op, value);
        return this;
    }

    public DbModelSelector groupBy(String columnName) {
        this.groupByColumnName = columnName;
        return this;
    }

    public DbModelSelector having(WhereBuilder whereBuilder) {
        this.having = whereBuilder;
        return this;
    }

    public DbModelSelector select(String... columnExpressions) {
        this.columnExpressions = columnExpressions;
        return this;
    }

    public DbModelSelector orderBy(String columnName) {
        selector.orderBy(columnName);
        return this;
    }

    public DbModelSelector orderBy(String columnName, boolean desc) {
        selector.orderBy(columnName, desc);
        return this;
    }

    public DbModelSelector limit(int limit) {
        selector.limit(limit);
        return this;
    }

    public DbModelSelector offset(int offset) {
        selector.offset(offset);
        return this;
    }

    public Class<?> getEntityType() {
        return selector.getEntityType();
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("SELECT ");
        if (columnExpressions != null && columnExpressions.length > 0) {
            for (int i = 0; i < columnExpressions.length; i++) {
                result.append(columnExpressions[i]);
                result.append(",");
            }
            result.deleteCharAt(result.length() - 1);
        } else {
            if (!TextUtils.isEmpty(groupByColumnName)) {
                result.append(groupByColumnName);
            } else {
                result.append("*");
            }
        }
        result.append(" FROM ").append(selector.tableName);
        if (selector.whereBuilder != null && selector.whereBuilder.getWhereItemSize() > 0) {
            result.append(" WHERE ").append(selector.whereBuilder.toString());
        }
        if (!TextUtils.isEmpty(groupByColumnName)) {
            result.append(" GROUP BY ").append(groupByColumnName);
            if (having != null && having.getWhereItemSize() > 0) {
                result.append(" HAVING ").append(having.toString());
            }
        }
        if (selector.orderByList != null) {
            for (int i = 0; i < selector.orderByList.size(); i++) {
                result.append(" ORDER BY ").append(selector.orderByList.get(i).toString());
            }
        }
        if (selector.limit > 0) {
            result.append(" LIMIT ").append(selector.limit);
            result.append(" OFFSET ").append(selector.offset);
        }
        return result.toString();
    }
}
