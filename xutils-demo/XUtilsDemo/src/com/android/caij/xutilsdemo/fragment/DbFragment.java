package com.android.caij.xutilsdemo.fragment;

import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.caij.xutilsdemo.R;
import com.android.caij.xutilsdemo.domain.Student;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DbFragment extends Fragment {

	@ViewInject(R.id.db_add)
	private Button db_add;
	@ViewInject(R.id.db_find)
	private Button db_find;
	@ViewInject(R.id.db_update)
	private Button db_update;
	@ViewInject(R.id.db_delete)
	private Button db_delete;

	@ViewInject(R.id.tv_info)
	private TextView info;
	private DbUtils db;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_fragment_view, container,
				false);
		ViewUtils.inject(this, view);

		db = DbUtils.create(this.getActivity());
		// 创建方式有多个重载， 实际项目中可能选择这个 因为数据库升级，后期数据维护都用的到
		// DbUtils.create(context, dbDir, dbName, dbVersion, dbUpgradeListener)
		db.configDebug(true); // debug模式 会输入sql语句
		db.configAllowTransaction(true); // 允许事务
		return view;
	}

	@OnClick(R.id.db_add)
	public void add(View view) {
		try {
			Student student = new Student();
			student.setName("张三");
			student.setId(10086);
			student.setAge(18);
			student.setMan(true);
			student.setTime(new Date());
			student.setDate(new java.sql.Date(new Date().getTime()));
			db.save(student);

			student = new Student();
			student.setName("李四");
			student.setId(10010);
			student.setAge(15);
			student.setMan(true);
			student.setTime(new Date());
			student.setDate(new java.sql.Date(new Date().getTime()));
			db.save(student);

			student = new Student();
			student.setName("小红");
			student.setId(10000);
			student.setAge(15);
			student.setMan(false);
			student.setTime(new Date());
			student.setDate(new java.sql.Date(new Date().getTime()));
			db.save(student);
			Toast.makeText(this.getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@OnClick(R.id.db_find)
	public void find(View view) {
		try {
			String temp = "";
			// 查找全部
			// List<Student> students = db.findAll(Student.class);
			// for (Student student : students) {
			// temp = temp + student.toString() + "\n";
			// }

			// 主键查找
			// Student student = db.findById(Student.class, 10086);
			// temp = student.toString();

			//条件查找
			List<Student> students = db.findAll(Selector.from(Student.class)
					.where("name", "=", "李四")
					.where(WhereBuilder.b("id", "=", 10010)).orderBy("name").limit(0).offset(10));
			if (students == null) {
				Toast.makeText(this.getActivity(), "没有数据请先添加数据", Toast.LENGTH_SHORT).show();
				return;
			}
			for (Student student : students) {
				temp = temp + student.toString() + "\n";
			}
			info.setText(temp);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	//下面两个暂时不实现， 看时间。其实也类似。 
	@OnClick(R.id.db_update)
	public void update(View view) {
		
	}
	
	@OnClick(R.id.db_delete)
	public void delete(View view) {
		
	}
}
