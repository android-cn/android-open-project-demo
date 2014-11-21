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

package com.lidroid.xutils.db.table;

import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.util.LogUtils;

import java.lang.reflect.Field;
import java.util.HashSet;

public class Id extends Column {

    private String columnFieldClassName;
    private boolean isAutoIncrementChecked = false;
    private boolean isAutoIncrement = false;

    /* package */ Id(Class<?> entityType, Field field) {
        super(entityType, field);
        columnFieldClassName = columnField.getType().getName();
    }

    public boolean isAutoIncrement() {
        if (!isAutoIncrementChecked) {
            isAutoIncrementChecked = true;
            isAutoIncrement = columnField.getAnnotation(NoAutoIncrement.class) == null
                    && AUTO_INCREMENT_TYPES.contains(columnFieldClassName);
        }
        return isAutoIncrement;
    }

    public void setAutoIncrementId(Object entity, long value) {
        Object idValue = value;
        if (INTEGER_TYPES.contains(columnFieldClassName)) {
            idValue = (int) value;
        }

        if (setMethod != null) {
            try {
                setMethod.invoke(entity, idValue);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
            }
        } else {
            try {
                this.columnField.setAccessible(true);
                this.columnField.set(entity, idValue);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
            }
        }
    }

    @Override
    public Object getColumnValue(Object entity) {
        Object idValue = super.getColumnValue(entity);
        if (idValue != null) {
            if (this.isAutoIncrement() && (idValue.equals(0) || idValue.equals(0L))) {
                return null;
            } else {
                return idValue;
            }
        }
        return null;
    }

    private static final HashSet<String> INTEGER_TYPES = new HashSet<String>(2);
    private static final HashSet<String> AUTO_INCREMENT_TYPES = new HashSet<String>(4);

    static {
        INTEGER_TYPES.add(int.class.getName());
        INTEGER_TYPES.add(Integer.class.getName());

        AUTO_INCREMENT_TYPES.addAll(INTEGER_TYPES);
        AUTO_INCREMENT_TYPES.add(long.class.getName());
        AUTO_INCREMENT_TYPES.add(Long.class.getName());
    }
}
