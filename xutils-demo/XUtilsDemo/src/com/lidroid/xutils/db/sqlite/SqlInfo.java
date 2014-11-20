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

import com.lidroid.xutils.db.table.ColumnUtils;

import java.util.LinkedList;

public class SqlInfo {

    private String sql;
    private LinkedList<Object> bindArgs;

    public SqlInfo() {
    }

    public SqlInfo(String sql) {
        this.sql = sql;
    }

    public SqlInfo(String sql, Object... bindArgs) {
        this.sql = sql;
        addBindArgs(bindArgs);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public LinkedList<Object> getBindArgs() {
        return bindArgs;
    }

    public Object[] getBindArgsAsArray() {
        if (bindArgs != null) {
            return bindArgs.toArray();
        }
        return null;
    }

    public String[] getBindArgsAsStrArray() {
        if (bindArgs != null) {
            String[] strings = new String[bindArgs.size()];
            for (int i = 0; i < bindArgs.size(); i++) {
                Object value = bindArgs.get(i);
                strings[i] = value == null ? null : value.toString();
            }
            return strings;
        }
        return null;
    }

    public void addBindArg(Object arg) {
        if (bindArgs == null) {
            bindArgs = new LinkedList<Object>();
        }

        bindArgs.add(ColumnUtils.convert2DbColumnValueIfNeeded(arg));
    }

    /* package */ void addBindArgWithoutConverter(Object arg) {
        if (bindArgs == null) {
            bindArgs = new LinkedList<Object>();
        }

        bindArgs.add(arg);
    }

    public void addBindArgs(Object... bindArgs) {
        if (bindArgs != null) {
            for (Object arg : bindArgs) {
                addBindArg(arg);
            }
        }
    }

}
