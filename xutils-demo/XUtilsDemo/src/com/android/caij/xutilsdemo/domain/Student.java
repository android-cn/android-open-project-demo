package com.android.caij.xutilsdemo.domain;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

//建议加上注解， 混淆后表名不受影响
@Table(name="student")
public class Student {
	
//	@Foreign(column = "parentId", foreign = "id") 外键约束
	
	//@Id // 如果主键没有命名名为id或_id的时，需要为主键添加此注解
    //@NoAutoIncrement // int,long类型的id默认自增，不想使用自增时添加此注解
	@Id
	@NoAutoIncrement
	@Column(column="id")
	private int id;  // 建议加上注解， 混淆后列名不受影响
	
	@Column(column="name")
	private String name;
	
	@Column(column="age")
	private int age;
	
	@Column(column="isMan")
	private boolean isMan;
	
	@Column(column = "time")
    private Date time;

    @Column(column = "date")
    private java.sql.Date date;
    
 // Transient使这个列被忽略，不存入数据库
    @Transient
    public String willIgnore;

    public static String staticFieldWillIgnore; // 静态字段也不会存入数据库
	
	public Student() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public boolean isMan() {
		return isMan;
	}
	public void setMan(boolean isMan) {
		this.isMan = isMan;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public java.sql.Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age
				+ ", isMan=" + isMan + ", time=" + time + ", date=" + date
				+ "]";
	}
	
}
