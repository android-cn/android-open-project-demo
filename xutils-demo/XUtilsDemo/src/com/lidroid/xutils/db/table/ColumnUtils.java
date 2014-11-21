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

import android.text.TextUtils;
import com.lidroid.xutils.db.annotation.*;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.converter.ColumnConverter;
import com.lidroid.xutils.db.converter.ColumnConverterFactory;
import com.lidroid.xutils.db.sqlite.FinderLazyLoader;
import com.lidroid.xutils.db.sqlite.ForeignLazyLoader;
import com.lidroid.xutils.util.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.List;

public class ColumnUtils {

    private ColumnUtils() {
    }

    private static final HashSet<String> DB_PRIMITIVE_TYPES = new HashSet<String>(14);

    static {
        DB_PRIMITIVE_TYPES.add(int.class.getName());
        DB_PRIMITIVE_TYPES.add(long.class.getName());
        DB_PRIMITIVE_TYPES.add(short.class.getName());
        DB_PRIMITIVE_TYPES.add(byte.class.getName());
        DB_PRIMITIVE_TYPES.add(float.class.getName());
        DB_PRIMITIVE_TYPES.add(double.class.getName());

        DB_PRIMITIVE_TYPES.add(Integer.class.getName());
        DB_PRIMITIVE_TYPES.add(Long.class.getName());
        DB_PRIMITIVE_TYPES.add(Short.class.getName());
        DB_PRIMITIVE_TYPES.add(Byte.class.getName());
        DB_PRIMITIVE_TYPES.add(Float.class.getName());
        DB_PRIMITIVE_TYPES.add(Double.class.getName());
        DB_PRIMITIVE_TYPES.add(String.class.getName());
        DB_PRIMITIVE_TYPES.add(byte[].class.getName());
    }

    public static boolean isDbPrimitiveType(Class<?> fieldType) {
        return DB_PRIMITIVE_TYPES.contains(fieldType.getName());
    }

    public static Method getColumnGetMethod(Class<?> entityType, Field field) {
        String fieldName = field.getName();
        Method getMethod = null;
        if (field.getType() == boolean.class) {
            getMethod = getBooleanColumnGetMethod(entityType, fieldName);
        }
        if (getMethod == null) {
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                getMethod = entityType.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                LogUtils.d(methodName + " not exist");
            }
        }

        if (getMethod == null && !Object.class.equals(entityType.getSuperclass())) {
            return getColumnGetMethod(entityType.getSuperclass(), field);
        }
        return getMethod;
    }

    public static Method getColumnSetMethod(Class<?> entityType, Field field) {
        String fieldName = field.getName();
        Method setMethod = null;
        if (field.getType() == boolean.class) {
            setMethod = getBooleanColumnSetMethod(entityType, field);
        }
        if (setMethod == null) {
            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                setMethod = entityType.getDeclaredMethod(methodName, field.getType());
            } catch (NoSuchMethodException e) {
                LogUtils.d(methodName + " not exist");
            }
        }

        if (setMethod == null && !Object.class.equals(entityType.getSuperclass())) {
            return getColumnSetMethod(entityType.getSuperclass(), field);
        }
        return setMethod;
    }


    public static String getColumnNameByField(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null && !TextUtils.isEmpty(column.column())) {
            return column.column();
        }

        Id id = field.getAnnotation(Id.class);
        if (id != null && !TextUtils.isEmpty(id.column())) {
            return id.column();
        }

        Foreign foreign = field.getAnnotation(Foreign.class);
        if (foreign != null && !TextUtils.isEmpty(foreign.column())) {
            return foreign.column();
        }

        Finder finder = field.getAnnotation(Finder.class);
        if (finder != null) {
            return field.getName();
        }

        return field.getName();
    }

    public static String getForeignColumnNameByField(Field field) {

        Foreign foreign = field.getAnnotation(Foreign.class);
        if (foreign != null) {
            return foreign.foreign();
        }

        return field.getName();
    }

    public static String getColumnDefaultValue(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null && !TextUtils.isEmpty(column.defaultValue())) {
            return column.defaultValue();
        }
        return null;
    }

    public static boolean isTransient(Field field) {
        return field.getAnnotation(Transient.class) != null;
    }

    public static boolean isForeign(Field field) {
        return field.getAnnotation(Foreign.class) != null;
    }

    public static boolean isFinder(Field field) {
        return field.getAnnotation(Finder.class) != null;
    }

    public static boolean isUnique(Field field) {
        return field.getAnnotation(Unique.class) != null;
    }

    public static boolean isNotNull(Field field) {
        return field.getAnnotation(NotNull.class) != null;
    }

    /**
     * @param field
     * @return check.value or null
     */
    public static String getCheck(Field field) {
        Check check = field.getAnnotation(Check.class);
        if (check != null) {
            return check.value();
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static Class<?> getForeignEntityType(com.lidroid.xutils.db.table.Foreign foreignColumn) {
        Class<?> result = foreignColumn.getColumnField().getType();
        if (result.equals(ForeignLazyLoader.class) || result.equals(List.class)) {
            result = (Class<?>) ((ParameterizedType) foreignColumn.getColumnField().getGenericType()).getActualTypeArguments()[0];
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static Class<?> getFinderTargetEntityType(com.lidroid.xutils.db.table.Finder finderColumn) {
        Class<?> result = finderColumn.getColumnField().getType();
        if (result.equals(FinderLazyLoader.class) || result.equals(List.class)) {
            result = (Class<?>) ((ParameterizedType) finderColumn.getColumnField().getGenericType()).getActualTypeArguments()[0];
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static Object convert2DbColumnValueIfNeeded(final Object value) {
        Object result = value;
        if (value != null) {
            Class<?> valueType = value.getClass();
            if (!isDbPrimitiveType(valueType)) {
                ColumnConverter converter = ColumnConverterFactory.getColumnConverter(valueType);
                if (converter != null) {
                    result = converter.fieldValue2ColumnValue(value);
                } else {
                    result = value;
                }
            }
        }
        return result;
    }

    private static boolean isStartWithIs(final String fieldName) {
        return fieldName != null && fieldName.startsWith("is");
    }

    private static Method getBooleanColumnGetMethod(Class<?> entityType, final String fieldName) {
        String methodName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        if (isStartWithIs(fieldName)) {
            methodName = fieldName;
        }
        try {
            return entityType.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            LogUtils.d(methodName + " not exist");
        }
        return null;
    }

    private static Method getBooleanColumnSetMethod(Class<?> entityType, Field field) {
        String fieldName = field.getName();
        String methodName = null;
        if (isStartWithIs(field.getName())) {
            methodName = "set" + fieldName.substring(2, 3).toUpperCase() + fieldName.substring(3);
        } else {
            methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
        try {
            return entityType.getDeclaredMethod(methodName, field.getType());
        } catch (NoSuchMethodException e) {
            LogUtils.d(methodName + " not exist");
        }
        return null;
    }

}
